/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a")
    , @NamedQuery(name = "Attendance.findByWorkshopId", query = "SELECT a FROM Attendance a WHERE a.attendancePK.workshopId = :workshopId")
    , @NamedQuery(name = "Attendance.findByWorkshopAndNetId", query = "SELECT a FROM Attendance a WHERE a.attendancePK.workshopId = :workshopId AND a.attendancePK.netId = :netId")
    , @NamedQuery(name = "Attendance.findByNetId", query = "SELECT a FROM Attendance a WHERE a.attendancePK.netId = :netId")
    , @NamedQuery(name = "Attendance.findByAttended", query = "SELECT a FROM Attendance a WHERE a.attended = :attended")})
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AttendancePK attendancePK;
    @Basic(optional = false)
    @Column(name = "attended")
    private boolean attended;
    @JoinColumn(name = "net_id", referencedColumnName = "net_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person person;
    @JoinColumn(name = "workshop_id", referencedColumnName = "workshop_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Workshops workshops;

    public Attendance() {
    }

    public Attendance(AttendancePK attendancePK) {
        this.attendancePK = attendancePK;
    }

    public Attendance(AttendancePK attendancePK, boolean attended) {
        this.attendancePK = attendancePK;
        this.attended = attended;
    }

    public Attendance(int workshopId, String netId) {
        this.attendancePK = new AttendancePK(workshopId, netId);
    }

    public AttendancePK getAttendancePK() {
        return attendancePK;
    }

    public void setAttendancePK(AttendancePK attendancePK) {
        this.attendancePK = attendancePK;
    }

    public boolean getAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
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
        hash += (attendancePK != null ? attendancePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.attendancePK == null && other.attendancePK != null) || (this.attendancePK != null && !this.attendancePK.equals(other.attendancePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testpack.Attendance[ attendancePK=" + attendancePK + " ]";
    }
    
}
