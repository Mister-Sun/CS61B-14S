/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.BankAccount;
import Entity.Client;
import Entity.Login;
import Entity.Person;
import Util.Contexto;
import Util.MessageUtility;
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
@ManagedBean(name = "cadClientAccount")
@SessionScoped
public class CadAccountService implements Serializable {

    private DaoImpl dao;

    private Login login;

    private Person person;
    private Client client;

    private BankAccount bankAccount;

    public CadAccountService() {
        this.dao = new EventDaoImpl();
        this.bankAccount = new BankAccount();
        login = this.loadLogin();
        person = login.getPerson();
        this.client = person.getClient();
    }

    //Variables getters and setters
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    //Add new account information
    public void addAccount() {
        this.client.getBankAccounts().add(bankAccount);
        this.bankAccount.setClient(client);
        this.bankAccount = new BankAccount();
    }

    public void saveAccount() {
        this.dao.update(person);
        MessageUtility.callInfo("sucess");
    }

}
