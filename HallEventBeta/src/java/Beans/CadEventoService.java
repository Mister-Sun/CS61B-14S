/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.Client;
import Entity.Event;
import Entity.EventDate;
import Entity.Event_Event;
import Entity.Event_Price;
import Entity.Login;
import Entity.PartiType;
import Entity.Person;
import Util.Contexto;
import Util.MessageUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author EBI
 */
@ManagedBean(name = "cadEvento")
@ViewScoped
public class CadEventoService implements Serializable {

    private DaoImpl dao;
    private Event event;
    private Event_Event eventEvent;
    private Login login;

    private String search;
    private String searchType;

    private List<Event> eventList;
    private List<Event> eventForTree;

    private Event_Price price;
    private PartiType partiType;
    
    private Person person;
    private Client client;

    private TreeNode root;

    public CadEventoService() {
        this.eventEvent = new Event_Event();
        this.event = new Event();
        this.dao = new EventDaoImpl();
        this.eventList = new ArrayList<>();
        this.root = new DefaultTreeNode("Root", null);
        this.eventForTree = new ArrayList<>();
        this.login = loadLogin();
        this.price = new Event_Price();
        this.partiType = new PartiType();
        this.person = this.loadLogin().getPerson();
        this.client = new Client();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PartiType getPartiType() {
        return partiType;
    }

    public void setPartiType(PartiType partiType) {
        this.partiType = partiType;
    }

    public Event_Price getPrice() {
        return price;
    }

    public void setPrice(Event_Price price) {
        this.price = price;
    }

    public Event_Event getEventEvent() {
        return eventEvent;
    }

    public void setEventEvent(Event_Event eventEvent) {
        this.eventEvent = eventEvent;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Login loadLogin() {
        String t = "";
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                try {
                    t = ((Login) Contexto.getSessao().getAttribute("search")).getUsername();
                } catch (NullPointerException e) {
                    t = ((User) authentication.getPrincipal()).getUsername();
                }
            }
        }
        Login l = (Login) this.dao.getSession().createCriteria(Login.class).add(Restrictions.eq("username", t)).uniqueResult();

        return l;
    }

    public List<Event> getlistEvents() {
        this.dao.setClasse(Event.class);
        List<Event> evs = dao.getSession().createCriteria(Event.class).list();
        return evs;
    }

    public void readNode(List<Event_Event> lee, DefaultTreeNode father) {

        for (Event_Event ee : lee) {
            Event son = ee.getSonEvent();
            DefaultTreeNode node = new DefaultTreeNode(son.getName());
            father.getChildren().add(node);
            if (son.getSonEvents().size() > 0) {
                this.readNode(son.getSonEvents(), node);
            }
        }
    }

    public void setUpTree() {
        this.eventForTree = this.dao.getSession().createCriteria(Event.class).add(Restrictions.eq("main", true)).list();

        for (Event e : this.eventForTree) {
            DefaultTreeNode node = new DefaultTreeNode(e.getName());
            root.getChildren().add(node);
            if (e.getSonEvents().size() > 0) {
                this.readNode(e.getSonEvents(), node);
            }
        }

    }

    public void saveEvent() {
        

        System.out.println(event.getPrices().get(0).getPriceName());
        DateTime beginDate = new DateTime(event.getBeginDate());
        DateTime endDate = new DateTime(event.getEndDate());
        Days days = Days.daysBetween(beginDate, endDate);
        endDate = beginDate;

        List<EventDate> dates = new ArrayList<>();
        EventDate date = new EventDate();
        Date temp;

        temp = beginDate.toDate();
        temp.setHours(event.getBeginTime().getHours());
        temp.setMinutes(event.getBeginTime().getMinutes());
        date.setBeginDate(temp);

        temp = endDate.toDate();
        temp.setHours(event.getEndTime().getHours());
        temp.setMinutes(event.getEndTime().getMinutes());
        date.setEndDate(temp);

        date.setEvent(event);
        dates.add(date);

        for (int i = 0; i < days.getDays(); i++) {
            beginDate = beginDate.plusDays(1);
            endDate = endDate.plusDays(1);
            date = new EventDate();

            temp = beginDate.toDate();
            temp.setHours(event.getBeginTime().getHours());
            temp.setMinutes(event.getBeginTime().getMinutes());
            date.setBeginDate(temp);

            temp = endDate.toDate();
            temp.setHours(event.getEndTime().getHours());
            temp.setMinutes(event.getEndTime().getMinutes());
            date.setEndDate(temp);

            date.setEvent(event);
            dates.add(date);
        }

        event.setDates(dates);
        event.setPerson(person);
        if (this.eventEvent.getFatherEvent() != null) {
            this.eventEvent.setSonEvent(event);
            event.getFatherEvents().add(this.eventEvent);
        } else {
            event.setMain(true);
        }

        try {
            person.setClient(client);
            this.client.setPerson(person);
            int num = this.person.getNumEventCreated().getNumOfCreations();
            num += 1;
            this.person.getNumEventCreated().setNumOfCreations(num);
            this.dao.update(person);
            this.dao.setClasse(Event.class);
            dao.save(this.event);
            this.cleanEventCadastre();
            MessageUtility.callInfo("sucess");
        } catch (Exception e) {
            MessageUtility.callError("error");
        }

    }

    public void cleanEventCadastre() {
        this.event = new Event();

    }

    public void addPrice() {

        this.price.setEvent(this.event);
        this.event.getPrices().add(this.price);
        this.price = new Event_Price();
    }

    public void addPartiType() {

        this.partiType.setEvent(this.event);
        this.event.getPartiTypes().add(this.partiType);
        this.partiType = new PartiType();
    }
}
