/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


/**
 *
 * @author EBI
 */
@Embeddable
public class Event_EventID implements Serializable{
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Event fatherevent;
    @ManyToOne(fetch = FetchType.LAZY)
    private Event sonevent;

    public Event getFatherevent() {
        return fatherevent;
    }

    public void setFatherevent(Event fatherevent) {
        this.fatherevent = fatherevent;
    }

    public Event getSonevent() {
        return sonevent;
    }

    public void setSonevent(Event sonevent) {
        this.sonevent = sonevent;
    }

    
    
}
