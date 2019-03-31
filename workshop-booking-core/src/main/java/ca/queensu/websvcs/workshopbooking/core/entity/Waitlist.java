/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "waitlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Waitlist.findAll", query = "SELECT w FROM Waitlist w")
    , @NamedQuery(name = "Waitlist.findByWorkshopId", query = "SELECT w FROM Waitlist w WHERE w.waitlistPK.workshopId = :workshopId")
    , @NamedQuery(name = "Waitlist.findByWorkshopAndNetId", query = "SELECT w FROM Waitlist w WHERE w.waitlistPK.workshopId = :workshopId AND w.waitlistPK.netId = :netId")
    , @NamedQuery(name = "Waitlist.findByNetId", query = "SELECT w FROM Waitlist w WHERE w.waitlistPK.netId = :netId")
    , @NamedQuery(name = "Waitlist.findByDatetimeApplied", query = "SELECT w FROM Waitlist w WHERE w.datetimeApplied = :datetimeApplied")})
public class Waitlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WaitlistPK waitlistPK;
    @Basic(optional = false)
    @Column(name = "datetime_applied")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeApplied;
    @JoinColumn(name = "net_id", referencedColumnName = "net_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person person;
    @JoinColumn(name = "workshop_id", referencedColumnName = "workshop_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Workshops workshops;

    public Waitlist() {
    }

    public Waitlist(WaitlistPK waitlistPK) {
        this.waitlistPK = waitlistPK;
    }

    public Waitlist(WaitlistPK waitlistPK, Date datetimeApplied) {
        this.waitlistPK = waitlistPK;
        this.datetimeApplied = datetimeApplied;
    }

    public Waitlist(int workshopId, String netId) {
        this.waitlistPK = new WaitlistPK(workshopId, netId);
    }

    public WaitlistPK getWaitlistPK() {
        return waitlistPK;
    }

    public void setWaitlistPK(WaitlistPK waitlistPK) {
        this.waitlistPK = waitlistPK;
    }

    public Date getDatetimeApplied() {
        return datetimeApplied;
    }

    public void setDatetimeApplied(Date datetimeApplied) {
        this.datetimeApplied = datetimeApplied;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Workshops getWorkshops() {
        return workshops;
    }

    public void setWorkshops(Workshops workshops) {
        this.workshops = workshops;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (waitlistPK != null ? waitlistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Waitlist)) {
            return false;
        }
        Waitlist other = (Waitlist) object;
        if ((this.waitlistPK == null && other.waitlistPK != null) || (this.waitlistPK != null && !this.waitlistPK.equals(other.waitlistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testpack.Waitlist[ waitlistPK=" + waitlistPK + " ]";
    }
    
}
