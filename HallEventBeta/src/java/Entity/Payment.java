/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tid", nullable = false)
    private String tid;

    @OneToOne
    @JoinColumn(name = "registered_id", nullable = false)
    private Registered registered;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Event_Registered> eventRegistereds = new ArrayList<>();

    @Column(name = "base_value")
    private long baseValue;

    @Column(name = "paid_value")
    private long paidValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public List<Event_Registered> getEventRegistereds() {
        return eventRegistereds;
    }

    public void setEventRegistereds(List<Event_Registered> eventRegistereds) {
        this.eventRegistereds = eventRegistereds;
    }

    public long getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(long baseValue) {
        this.baseValue = baseValue;
    }

    public long getPaidValue() {
        return paidValue;
    }

    public void setPaidValue(long paidValue) {
        this.paidValue = paidValue;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
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
