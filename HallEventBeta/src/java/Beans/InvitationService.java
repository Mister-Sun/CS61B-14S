/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.Login;
import Entity.Person;
import Util.Contexto;
import java.io.Serializable;
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
@ManagedBean(name = "InvitationService")
@SessionScoped
public class InvitationService implements Serializable{
 
    private DaoImpl dao;
    private Login login;
    
    private Person person;
    private String url;

    public InvitationService() {
        this.dao = new EventDaoImpl();
        this.login = this.loadLogin();
        this.person = this.login.getPerson();
        this.url = "http://localhost:8084/HallEvent/formCadParticipante.htm?uid=" + this.person.getId().toString();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
