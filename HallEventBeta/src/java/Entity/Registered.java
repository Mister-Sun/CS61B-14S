/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Enums.RegisteredType;
import Enums.ParticipationType;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Registered implements Serializable{
    //private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.registered")
    @Cascade(CascadeType.ALL)
    private List<Event_Registered> eventRegistered;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "registered_type")
    private RegisteredType registeredType;
    
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    
    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;
    
    @Transient
    private boolean certification;
    
    @Transient
    private boolean present = false;
    
    @Transient
    private ParticipationType participationType;
    
    
    public Registered(){
        //person = new Person();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    

    public ParticipationType getParticipationType() {
        return participationType;
    }

    public void setParticipationType(ParticipationType participationType) {
        this.participationType = participationType;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean pre) {
        this.present = pre;
    }
    
    public boolean isCertification() {
        return certification;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public List<Event_Registered> getEventRegisteredList() {
        return eventRegistered;
    }

    public void setEventRegisteredList(List<Event_Registered> eventReg) {
        this.eventRegistered = eventReg;
    }

    public List<Event_Registered> getEventRegistered() {
        return eventRegistered;
    }

    public void setEventRegistered(List<Event_Registered> eventRegistered) {
        this.eventRegistered = eventRegistered;
    }

    public RegisteredType getRegisteredType() {
        return registeredType;
    }

    public void setRegisteredType(RegisteredType registeredType) {
        this.registeredType = registeredType;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


//    public List<Evento> getEventos() {
//        return eventos;
//    }

//    public void setEventos(List<Evento> eventos) {
//        for(Evento e : eventos){
//            Presenca p = new Presenca();
//            p.setInscrito(this);
//            p.setEvento(e);
//        }
//        this.eventos = eventos;
//    }
    
    public List<Event> getEvents(){
        List<Event> ev = new ArrayList<Event>();
        for(Event_Registered p : this.getEventRegisteredList()){
            ev.add(p.getEvent());
        }
        return ev;
    }
    
    public Event_Registered getPresencaEvento(Event ev){
        for(Event_Registered p : this.eventRegistered){
            if(p.getEvent().getId().equals(ev.getId())){
                return p;
                
            }
        }
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Registered)) {
            return false;
        }
        final Registered other = (Registered) obj;
        if (getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 21 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
    }
    
}
