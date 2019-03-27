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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "event_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventStatus.findAll", query = "SELECT e FROM EventStatus e")
    , @NamedQuery(name = "EventStatus.findByEventStatus", query = "SELECT e FROM EventStatus e WHERE e.eventStatus = :eventStatus")})
public class EventStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "event_status")
    private String eventStatus;

    public EventStatus() {
    }

    public EventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventStatus != null ? eventStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventStatus)) {
            return false;
        }
        EventStatus other = (EventStatus) object;
        if ((this.eventStatus == null && other.eventStatus != null) || (this.eventStatus != null && !this.eventStatus.equals(other.eventStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.core.entity.EventStatus[ eventStatus=" + eventStatus + " ]";
    }
    
}
