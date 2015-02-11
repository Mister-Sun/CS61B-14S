/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

/**
 *
 * @author EBI
 */
@Entity(name = "event_event")
@AssociationOverrides({
    @AssociationOverride(name = "pk.fatherevent", joinColumns =
            @JoinColumn(name = "FATHER_EVENT_ID")),
    @AssociationOverride(name = "pk.sonevent", joinColumns =
            @JoinColumn(name = "SON_EVENT_ID"))})
public class Event_Event implements Serializable{
    
    @EmbeddedId
    private Event_EventID pk = new Event_EventID();

    public Event_EventID getPk() {
        return pk;
    }

    public void setPk(Event_EventID pk) {
        this.pk = pk;
    }
    
    public Event getFatherEvent() {
        return this.getPk().getFatherevent();
    }

    public void setFatherEvent(Event ev) {
        //this.registered = registered;
        this.getPk().setFatherevent(ev);
    }
    
    public Event getSonEvent(){
        return this.getPk().getSonevent();
    }
    
    public void setSonEvent(Event ev) {
        //this.registered = registered;
        this.getPk().setSonevent(ev);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event_Event that = (Event_Event) o;

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
