/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author DanielJaime
 */
@Embeddable
public class Event_RegisteredID implements Serializable{
    @ManyToOne
    private Registered registered;
    @ManyToOne
    private Event event;
    
    public Registered getResgistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event_RegisteredID that = (Event_RegisteredID) o;

        if (event != null ? !event.equals(that.event) : that.event != null) {
            return false;
        }
        if (registered != null ? !registered.equals(that.registered) : that.registered != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (event != null ? event.hashCode() : 0);
        result = 31 * result + (registered != null ? registered.hashCode() : 0);
        return result;
    }
    
    
}
