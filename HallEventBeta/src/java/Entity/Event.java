/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Util.BaseEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author ebi
 */
@Entity
public class Event implements Serializable, BaseEntity {

    private static long eventNum = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
     Long Id;
    @Column(name = "name")
    private String name;

    @Column(name = "max_quantity")
    private int maxQuantity;
    @Column(name = "event_place")
    private String eventPlace;
    @Column(name = "active")
    private boolean active;
    @Column(name = "free", nullable = false)
    private boolean free;
    @Column(name = "main", nullable = false)
    private boolean main;
    @Column(name = "cost")
    private int cost;
    @Column(name = "isprivate", nullable = false)
    private boolean isPrivate;
    @Column(name = "code", nullable = false)
    private long code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.event")
    //@LazyCollection(LazyCollectionOption.FALSE) qdo der erro e lazy
    private List<Event_Registered> EveReg = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    @Cascade(CascadeType.ALL)
    private List<EventDate> dates = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.fatherevent")
    @Cascade(CascadeType.ALL)
    private List<Event_Event> sonEvents = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.sonevent")
    @Cascade(CascadeType.ALL)
    private List<Event_Event> fatherEvents = new ArrayList<>();

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Event_Price> prices = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<PartiType> partiTypes = new ArrayList<>();
    
    @OneToMany(mappedBy = "event")
    private List<EventEvaluation> eventEvaluations;

    @ManyToOne
    private Person person;

    @Transient
    private boolean full = false;
    @Transient
    private String pagSeg;
    @Transient
    private Date beginDate;
    @Transient
    private Date endDate;
    @Transient
    private Date beginTime;
    @Transient
    private Date endTime;
    @Transient
    private int indexNode = 0;
    @Transient
    private boolean register;

    public Event() {
        this.active = true;
        this.free = true;
        this.person = new Person();
        eventNum++;
        this.code = eventCode();
    }

    public List<EventEvaluation> getEventEvaluations() {
        return eventEvaluations;
    }

    public void setEventEvaluations(List<EventEvaluation> eventEvaluations) {
        this.eventEvaluations = eventEvaluations;
    }

    public List<PartiType> getPartiTypes() {
        return partiTypes;
    }

    public void setPartiTypes(List<PartiType> partiTypes) {
        this.partiTypes = partiTypes;
    }
    
    public boolean isIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isRegister() {
        return register;
    }

    public List<Event_Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Event_Price> prices) {
        this.prices = prices;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Event_Event> getFatherEvents() {
        return fatherEvents;
    }

    public void setFatherEvents(List<Event_Event> fatherEvents) {
        this.fatherEvents = fatherEvents;
    }

    public int getIndexNode() {
        return indexNode;
    }

    public void setIndexNode(int indexNode) {
        this.indexNode = indexNode;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public List<Event_Event> getSonEvents() {
        return sonEvents;
    }

    public void setSonEvents(List<Event_Event> sonEvents) {
        this.sonEvents = sonEvents;
    }

    public List<Event_Registered> getEveReg() {
        return EveReg;
    }

    public void setEveReg(List<Event_Registered> EveReg) {
        this.EveReg = EveReg;
    }

    public String getPagSeg() {
        return pagSeg;
    }

    public void setPagSeg(String pagSeg) {
        this.pagSeg = pagSeg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getEventDateToEmail() {
        String saida = "";
        if (getDates().size() >= 1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            Date begin = getDates().get(0).getBeginDate();
            Date end = getDates().get(getDates().size() - 1).getEndDate();
            saida += "De " + dateFormat.format(begin) + " à " + dateFormat.format(end) + " ";

            dateFormat.applyPattern("HH:mm");
            saida += "Início " + dateFormat.format(begin) + " Término " + dateFormat.format(end);
        }

        return saida;
    }

    public Event(String name, String eventPlace, long id) {
        this.name = name;
        this.eventPlace = eventPlace;
        this.Id = id;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public List<Event_Registered> getEventRegisteredList() {
        return EveReg;
    }

    public void setEventRegisteredList(List<Event_Registered> EveReg) {
        this.EveReg = EveReg;
    }

    public List<EventDate> getDates() {
        return dates;
    }

    public void setDates(List<EventDate> dates) {
        this.dates = dates;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 21 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    public final long eventCode() {
        long eventCode = 5L;
        eventCode = 10000L * eventCode + eventNum * 18 + eventNum;
        return eventCode;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (this.Id != other.Id && (this.Id == null || !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }
}
