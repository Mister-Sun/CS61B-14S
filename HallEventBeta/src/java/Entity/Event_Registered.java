/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Enums.ParticipationType;
import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author dbjaime
 */
@Entity(name = "event_registered")
@AssociationOverrides({
    @AssociationOverride(name = "pk.resgistered", joinColumns =
            @JoinColumn(name = "REGISTERED_ID")),
    @AssociationOverride(name = "pk.event", joinColumns =
            @JoinColumn(name = "EVENT_ID"))})
public class Event_Registered implements Serializable {
    
    @EmbeddedId
    private Event_RegisteredID pk = new Event_RegisteredID();
    
    @Column(name = "present")
    private boolean isPresent;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private ParticipationType participationType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    @Cascade(CascadeType.ALL)
    private Payment payment;
    
    @Transient
    private Event event;
    @Transient
    private Registered registered;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_type_id")
    private PartiType partiType;

//    @OneToOne
//    @JoinColumn(name = "participation_type_id")
//    private PartiType partiType;
//
    public PartiType getPartiType() {
        return partiType;
    }

    public void setPartiType(PartiType partiType) {
        this.partiType = partiType;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Event getEvent() {
        return this.getPk().getEvent();
    }

    public void setEvent(Event event) {
        this.event = event;
        this.getPk().setEvent(event);
    }

    public Registered getRegistered() {
        return this.getPk().getResgistered();
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
        this.getPk().setRegistered(registered);
    }
    
    
    public Event_Registered(){
        this.isPresent = false;
    }
    
    public Event_RegisteredID getPk() {
        return pk;
    }

    public void setPk(Event_RegisteredID pk) {
        this.pk = pk;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }



    public ParticipationType getParticipationType() {
        return participationType;
    }

    public void setParticipationType(ParticipationType pType) {
        this.participationType = pType;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event_Registered that = (Event_Registered) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
    
    
}
