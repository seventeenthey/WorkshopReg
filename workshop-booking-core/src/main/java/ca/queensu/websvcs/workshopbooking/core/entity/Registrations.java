/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "registrations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registrations.findAll", query = "SELECT r FROM Registrations r")
    , @NamedQuery(name = "Registrations.findByWorkshopId", query = "SELECT r FROM Registrations r WHERE r.workshopId = :workshopId")})
public class Registrations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "workshop_id")
    private Integer workshopId;
    @JoinColumn(name = "workshop_id", referencedColumnName = "workshop_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Catalogue catalogue;
    @JoinColumn(name = "net_id", referencedColumnName = "net_id")
    @ManyToOne
    private Person netId;

    public Registrations() {
    }

    public Registrations(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Person getNetId() {
        return netId;
    }

    public void setNetId(Person netId) {
        this.netId = netId;
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
        if (!(object instanceof Registrations)) {
            return false;
        }
        Registrations other = (Registrations) object;
        if ((this.workshopId == null && other.workshopId != null) || (this.workshopId != null && !this.workshopId.equals(other.workshopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.core.entity.Registrations[ workshopId=" + workshopId + " ]";
    }
    
}
