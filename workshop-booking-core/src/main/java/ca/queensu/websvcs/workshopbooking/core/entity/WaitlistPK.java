/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Vincent
 */
@Embeddable
public class WaitlistPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "workshop_id")
    private int workshopId;
    @Basic(optional = false)
    @Column(name = "net_id")
    private String netId;

    public WaitlistPK() {
    }

    public WaitlistPK(int workshopId, String netId) {
        this.workshopId = workshopId;
        this.netId = netId;
    }

    public int getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(int workshopId) {
        this.workshopId = workshopId;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) workshopId;
        hash += (netId != null ? netId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WaitlistPK)) {
            return false;
        }
        WaitlistPK other = (WaitlistPK) object;
        if (this.workshopId != other.workshopId) {
            return false;
        }
        if ((this.netId == null && other.netId != null) || (this.netId != null && !this.netId.equals(other.netId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testpack.WaitlistPK[ workshopId=" + workshopId + ", netId=" + netId + " ]";
    }
    
}
