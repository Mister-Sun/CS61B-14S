/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Util.BaseEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author ebi
 */

@Entity(name = "event_date")
public class EventDate implements Serializable, BaseEntity{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
    
    @Column(name = "begin_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date beginDate;
    
    @Column(name = "end_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;
    
    
    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public double getCargaHorariaMinima() {
        long total = this.endDate.getTime() - this.beginDate.getTime();

        return total * 0.7;
    }

    public String getDataEventoFormatada() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return dateFormat.format(this.beginDate);
    }
    
    public String getFimEventoFormatada() {
        //System.out.println("klsjfhglskdjfghlskdfjghldghgçdlfkjhdçlgh");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return dateFormat.format(this.endDate);
    }

    public String getHoraFimFormatada() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(this.endDate);
        
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 21 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventDate other = (EventDate) obj;
        if (this.Id != other.Id && (this.Id == null || !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }
}
