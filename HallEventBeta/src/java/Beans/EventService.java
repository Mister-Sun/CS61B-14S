/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.Event;
import Entity.EventDate;
import Entity.Event_Event;
import Util.MessageUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author EBI
 */
@ManagedBean(name = "crudEvent")
@SessionScoped
public class EventService {

    private DaoImpl dao;
    private Event event;
    private Event_Event eventEvent;

    private String search;
    private String searchType;

    private List<Event> eventList;
    private List<Event> eventForTree;

    private TreeNode root;

    public EventService() {
        this.eventEvent = new Event_Event();
        this.event = new Event();
        this.dao = new EventDaoImpl();
        this.eventList = new ArrayList<>();
        this.root = new DefaultTreeNode("Root", null);

        this.eventForTree = new ArrayList<>();
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event_Event getEventEvent() {
        return eventEvent;
    }

    public void setEventEvent(Event_Event eventEvent) {
        this.eventEvent = eventEvent;
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

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEventForTree() {
        return eventForTree;
    }

    public void setEventForTree(List<Event> eventForTree) {
        this.eventForTree = eventForTree;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void searchEvents() {
        this.dao.setClasse(Event.class);
        this.eventList = dao.getSession().createCriteria(Event.class).add(Restrictions.like(searchType, "%" + search + "%")).list();
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

    public String loadEvent(Event e) {
        this.event = e;
        event.setBeginDate(event.getDates().get(0).getBeginDate());
        event.setEndDate(event.getDates().get(event.getDates().size() - 1).getEndDate());
        event.setBeginTime(event.getDates().get(0).getBeginDate());
        event.setEndTime(event.getDates().get(0).getEndDate());

        return "/formAtualizarEvento.htm";
    }

    public String updateEvent() {
                Date dateTest = event.getBeginDate();
        System.out.println(dateTest.toString());
        this.dao.setClasse(EventDate.class);

        int size = event.getDates().size();
        
        for (int i = 0; i < size; i++) {
            System.out.println("Update's for loop");
            EventDate de = event.getDates().get(0);
            event.getDates().remove(de);
            this.dao.destroy(de);
        }

        DateTime beginDate = new DateTime(event.getBeginDate());
        DateTime endDate = new DateTime(event.getEndDate());
        System.out.println(beginDate.toString());
        System.out.println(endDate.toString());
        Days days = Days.daysBetween(beginDate, endDate);
        endDate = beginDate;

        List<EventDate> dates = new ArrayList<EventDate>();
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

        try {
            this.dao.setClasse(Event.class);
            this.dao.update(this.event);
            this.cleanEventCadastre();
            MessageUtility.callInfo("sucess");
        } catch (Exception e) {
            MessageUtility.callError("error");
        }

        return "/formEvento.htm";

    }

    public void cleanEventCadastre() {
        this.event = new Event();
    }

    public void deleteEvent(Event ev) {
        this.dao.setClasse(Event.class);
        ev.setActive(false);
        try {
            this.dao.update(ev);
            this.cleanEventCadastre();
            MessageUtility.callInfo("sucess");
        } catch (Exception e) {
            MessageUtility.callError("error");
        }
    }

    public void deleteEventInterno(Event ev) {
        this.dao.setClasse(Event.class);
        try {
            this.dao.destroy(ev);
            this.cleanEventCadastre();
            MessageUtility.callInfo("sucess");
        } catch (Exception e) {
            MessageUtility.callError("error");
        }

    }
}
