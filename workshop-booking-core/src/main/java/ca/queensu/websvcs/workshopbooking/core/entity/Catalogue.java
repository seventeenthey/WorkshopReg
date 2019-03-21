/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "catalogue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catalogue.findAll", query = "SELECT c FROM Catalogue c")
    , @NamedQuery(name = "Catalogue.findByWorkshopId", query = "SELECT c FROM Catalogue c WHERE c.workshopId = :workshopId")
    , @NamedQuery(name = "Catalogue.findByTitle", query = "SELECT c FROM Catalogue c WHERE c.title = :title")
    , @NamedQuery(name = "Catalogue.findByDetails", query = "SELECT c FROM Catalogue c WHERE c.details = :details")
    , @NamedQuery(name = "Catalogue.findByLocation", query = "SELECT c FROM Catalogue c WHERE c.location = :location")
    , @NamedQuery(name = "Catalogue.findByStartTime", query = "SELECT c FROM Catalogue c WHERE c.startTime = :startTime")
    , @NamedQuery(name = "Catalogue.findByEndTime", query = "SELECT c FROM Catalogue c WHERE c.endTime = :endTime")})
public class Catalogue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "workshop_id")
    private Integer workshopId;
    @Column(name = "title")
    private String title;
    @Column(name = "details")
    private String details;
    @Column(name = "location")
    private String location;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "catalogue")
    private Registrations registrations;
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne
    private Departments departmentId;
    @JoinColumn(name = "workshop_host_id", referencedColumnName = "net_id")
    @ManyToOne
    private Person workshopHostId;

    public Catalogue() {
    }

    public Catalogue(Integer workshopId) {
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Registrations getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Registrations registrations) {
        this.registrations = registrations;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workshopId != null ? workshopId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catalogue)) {
            return false;
        }
        Catalogue other = (Catalogue) object;
        if ((this.workshopId == null && other.workshopId != null) || (this.workshopId != null && !this.workshopId.equals(other.workshopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.core.entity.Catalogue[ workshopId=" + workshopId + " ]";
    }
    
}
