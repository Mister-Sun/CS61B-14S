/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author THIAGOVULCAO
 */
@Entity
public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checkin_date")
    private Date checkInDate;
    @JoinColumn(name = "registered_id")
    @OneToOne
    private Registered registered;
    @Column(name = "press")
    private boolean press;
    @Column(name = "code", length = 12, unique = true)
    private String code;
    @Enumerated(EnumType.ORDINAL)
    
    
    
////  logica cadastro mais de um evento
////  Modificado para many to one
//    @ManyToMany(mappedBy = "credenciais", targetEntity = Evento.class, fetch = FetchType.LAZY)
//    private List<Evento> eventos = new ArrayList<Evento>();
    
    
    @ManyToMany(cascade ={ CascadeType.REFRESH,  CascadeType.MERGE})
    @JoinTable(name = "credencial_evento", joinColumns = {
        @JoinColumn(name = "credenciais")}, inverseJoinColumns = {
        @JoinColumn(name = "eventos")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Event> events = new ArrayList<Event>();
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public String getCode() {
        return code;
    }

    public void setCodeToQR(String code) {
        this.code = code;
    }
    
    
    public void setCodeToBar(Long id) {
        String barcode = id.toString();
        Random rand = new Random();
        int complete = 12 - barcode.length();
        for (int i = 0; i < complete; i++) {
            barcode = barcode.concat(Integer.toString(rand.nextInt(10)));
        }
        this.code = barcode;
    }
    

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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
        if (!(object instanceof Credential)) {
            return false;
        }
        Credential other = (Credential) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ebi.fomentapa.credencial.modelo.Credencial[ id=" + id + " ]";
    }
}
