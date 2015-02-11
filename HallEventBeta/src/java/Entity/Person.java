/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Enums.RegisteredType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author EBI
 */
@Entity
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "individual_id")
    private Individual individual;

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "registered_type")
    private RegisteredType registeredType;

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Contact> contacts = new ArrayList<>();

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "cliente_id")
    private Client client;

    @JoinColumn
    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToOne(fetch = FetchType.LAZY)
    private PositionInCompany position;

    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToOne
    @JoinColumn(name = "user_id")
    private Login login;

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<Registered> registered = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<Event> events = new ArrayList<>();

    @Column(name = "fb_id")
    private String fbId;

    @OneToMany(mappedBy = "person")
    private List<EventEvaluation> eventEvaluations;

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "num_of_events_created_id")
    private NumEventsCreated numEventCreated;

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "num_of_events_attended_id")
    private NumEventsAttended numEventsAttended;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "friendInvitedId.personInvitor")
    @Cascade(CascadeType.ALL)
    private List<FriendInvited> invitors = new ArrayList<FriendInvited>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "friendInvitedId.personInvited")
    @Cascade(CascadeType.ALL)
    private List<FriendInvited> inviteds = new ArrayList<FriendInvited>();

    public Person() {
        this.address = new Address();
        this.login = new Login();
        this.numEventCreated = new NumEventsCreated();
        this.numEventsAttended = new NumEventsAttended();
    }

    public List<FriendInvited> getInvitors() {
        return invitors;
    }

    public void setInvitors(List<FriendInvited> invitors) {
        this.invitors = invitors;
    }

    public List<FriendInvited> getInviteds() {
        return inviteds;
    }

    public void setInviteds(List<FriendInvited> inviteds) {
        this.inviteds = inviteds;
    }

    public NumEventsAttended getNumEventsAttended() {
        return numEventsAttended;
    }

    public void setNumEventsAttended(NumEventsAttended numEventsAttended) {
        this.numEventsAttended = numEventsAttended;
    }

    public NumEventsCreated getNumEventCreated() {
        return numEventCreated;
    }

    public void setNumEventCreated(NumEventsCreated numEventCreated) {
        this.numEventCreated = numEventCreated;
    }

    public List<EventEvaluation> getEventEvaluations() {
        return eventEvaluations;
    }

    public void setEventEvaluations(List<EventEvaluation> eventEvaluations) {
        this.eventEvaluations = eventEvaluations;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public RegisteredType getRegisteredType() {
        return registeredType;
    }

    public void setRegisteredType(RegisteredType registeredType) {
        this.registeredType = registeredType;
    }

    public PositionInCompany getPosition() {
        return position;
    }

    public void setPosition(PositionInCompany position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Registered> getRegistered() {
        return registered;
    }

    public void setRegistered(List<Registered> registered) {
        this.registered = registered;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
