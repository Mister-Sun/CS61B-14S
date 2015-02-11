/*
 * This class is an entity class for participation types in event registry
 */
package Entity;

import Util.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author sun
 */
@Entity(name = "participation_type")
public class PartiType implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Event_Registered> eventRegistereds;
    
    public List<Event_Registered> getEventRegistereds() {
        return eventRegistereds;
    }

    public void setEventRegistereds(List<Event_Registered> eventRegistereds) {
        this.eventRegistereds = eventRegistereds;
    }
    
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        final PartiType other = (PartiType) obj;
        return this.Id == other.Id || (this.Id != null && this.Id.equals(other.Id));
    }

    @Override
    public String toString() {
        return "br.com.ebi.fomentapa.empresa.modelo.Porte[ id=" + Id + " ]";
    }

}
