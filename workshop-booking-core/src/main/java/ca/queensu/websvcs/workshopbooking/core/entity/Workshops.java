/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "workshops")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workshops.findAll", query = "SELECT c FROM Workshops c")
    , @NamedQuery(name = "Workshops.findByWorkshopId", query = "SELECT c FROM Workshops c WHERE c.workshopId = :workshopId")
    , @NamedQuery(name = "Workshops.findByWorkshopHostId", query = "SELECT c FROM Workshops c WHERE c.workshopHostId.netId = :netId")
    , @NamedQuery(name = "Workshops.findByTitle", query = "SELECT c FROM Workshops c WHERE c.title = :title")
    , @NamedQuery(name = "Workshops.findByDetails", query = "SELECT c FROM Workshops c WHERE c.details = :details")
    , @NamedQuery(name = "Workshops.findByLocation", query = "SELECT c FROM Workshops c WHERE c.location = :location")
    , @NamedQuery(name = "Workshops.findByMaxParticipants", query = "SELECT c FROM Workshops c WHERE c.maxParticipants = :maxParticipants")
    , @NamedQuery(name = "Workshops.findByCurrentParticipants", query = "SELECT c FROM Workshops c WHERE c.currentParticipants = :currentParticipants")
    , @NamedQuery(name = "Workshops.findByStartTime", query = "SELECT c FROM Workshops c WHERE c.eventStart = :eventStart")
    , @NamedQuery(name = "Workshops.findByEndTime", query = "SELECT c FROM Workshops c WHERE c.eventEnd = :eventEnd")
})


public class Workshops implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "workshop_id")
    private Integer workshopId;
    @Column(name = "title")
    private String title;
    @Column(name = "details")
    private String details;
    @Column(name = "location")
    private String location;
    @Column(name = "max_participants")
    private Integer maxParticipants;
    @Column(name = "current_participants")
    private Integer currentParticipants;
    @Column(name = "registration_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationStart;
    @Column(name = "registration_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationEnd;
    @Column(name = "event_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventStart;
    @Column(name = "event_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventEnd;
    @JoinTable(name = "waitlist", joinColumns = {
        @JoinColumn(name = "workshop_id", referencedColumnName = "workshop_id")}, inverseJoinColumns = {
        @JoinColumn(name = "net_id", referencedColumnName = "net_id")})
    @ManyToMany
    private Collection<Person> personCollection1;
    /**
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workshops")
    private Collection<Reviews> reviewsCollection;
    **/
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne
    private Departments departmentId;
    @JoinColumn(name = "workshop_host_id", referencedColumnName = "net_id")
    @ManyToOne
    private Person workshopHostId;
    @JoinColumn(name = "event_status", referencedColumnName = "event_status")
    @ManyToOne
    private EventStatus eventStatus;
    
    @ManyToMany
    @JoinTable(name = "REGISTRATIONS", joinColumns = {
        @JoinColumn(name = "workshop_id", referencedColumnName = "workshop_id")}, inverseJoinColumns = {
        @JoinColumn(name = "net_id", referencedColumnName = "net_id")})
    private List<Person> myRegistrants;
    
    public Workshops() {
    }

    public Workshops(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(Integer currentParticipants) {
        this.currentParticipants = currentParticipants;
    }
    
    public Date getRegistrationStart() {
        return registrationStart;
    }

    public void setRegistrationStart(Date registrationStart) {
        this.registrationStart = registrationStart;
    }
    
    public Date getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(Date registrationEnd) {
        this.registrationEnd = registrationEnd;
    }

    public String startTimeToString(){
        String output = "";
        //String[] startTime = rgStTime.split(",");
        
        int hours = eventStart.getHours();//Integer.parseInt(startTime[0]);
        int minutes = eventStart.getMinutes();//Integer.parseInt(startTime[1]);
        
        if(minutes == 0)
            output += " : 00";
        else
            output += " : " + minutes;
        
        
        if (hours > 12){
            hours -= 12;
            output = hours + output + " PM";
        }
        else
            output = hours + output + " AM";

        return output;
    }
    
    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Date getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Date eventEnd) {
        this.eventEnd = eventEnd;
    }

    @XmlTransient
    public List<Person> getMyRegistrants() {
        return myRegistrants;
    }

    public void setMyRegistrants(List<Person> registrants) {
        this.myRegistrants = registrants;
    }

    @XmlTransient
    public Collection<Person> getPersonCollection1() {
        return personCollection1;
    }

    public void setPersonCollection1(Collection<Person> personCollection1) {
        this.personCollection1 = personCollection1;
    }

    /**
    @XmlTransient
    public Collection<Reviews> getReviewsCollection() {
        return reviewsCollection;
    }

    public void setReviewsCollection(Collection<Reviews> reviewsCollection) {
        this.reviewsCollection = reviewsCollection;
    }
    **/

    public Departments getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Departments departmentId) {
        this.departmentId = departmentId;
    }

    public Person getWorkshopHostId() {
        return workshopHostId;
    }

    public void setWorkshopHostId(Person workshopHostId) {
        this.workshopHostId = workshopHostId;
    }
    
    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
    
    public void addRegistrant(Person p) {
        this.myRegistrants.add(p);
        p.getAllWorkshops().add(this);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workshopId != null ? workshopId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workshops)) {
            return false;
        }
        Workshops other = (Workshops) object;
        if ((this.workshopId == null && other.workshopId != null) || (this.workshopId != null && !this.workshopId.equals(other.workshopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.core.entity.Workshops[ workshopId=" + workshopId + " ]";
    }
    
}
