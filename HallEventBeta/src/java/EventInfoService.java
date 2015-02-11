
import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.Event;
import Entity.Login;
import Entity.Person;
import Util.Contexto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sun
 */
@ManagedBean(name = "eventInfo")
@SessionScoped
public class EventInfoService {

    private DaoImpl dao;
    
    private Login login;
    
    private String eventId;
    
    private Person person;

    private Event event;

    public EventInfoService() {
        this.dao = new EventDaoImpl();
        this.login = this.loadLogin();
        this.person = this.login.getPerson();
        
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        this.setEventId(request.getParameter("eventId"));
        this.loadEvent();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

    public void loadEvent(){
        Long id = Long.parseLong(eventId);
        this.event = (Event) this.dao.getSession().createCriteria(Event.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
    
}
