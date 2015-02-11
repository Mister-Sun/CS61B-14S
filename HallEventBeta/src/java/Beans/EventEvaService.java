/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.Event;
import Entity.EventEvaluation;
import Entity.Login;
import Entity.Person;
import Entity.Registered;
import Util.Contexto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author sun
 */
@ManagedBean(name = "evaEvents")
@SessionScoped
public class EventEvaService implements Serializable {

    private DaoImpl dao;
    private Person person;
    private Login login;
    
    private EventEvaluation eventEvaluation;
    
    private List<Event> events;

    public EventEvaService() {
        this.dao = new EventDaoImpl();
        this.login = this.loadLogin();
        this.person = this.login.getPerson();
        this.events = new ArrayList<>();
        this.loadEventRegistered();
        this.eventEvaluation = new EventEvaluation();
    }

    public EventEvaluation getEventEvaluation() {
        return eventEvaluation;
    }

    public void setEventEvaluation(EventEvaluation eventEvaluation) {
        this.eventEvaluation = eventEvaluation;
    }
    
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    // load user's login information
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
    
    public void evaluateEvent(Event e){
        this.eventEvaluation.setEvent(e);
        this.eventEvaluation.setPerson(this.person);
        this.dao.save(this.eventEvaluation);
        eventEvaluation = new EventEvaluation();
    }

    public void loadEventRegistered(){
        for (Registered r: this.person.getRegistered()){
            this.events.addAll(r.getEvents()); 
        }
    }
}
