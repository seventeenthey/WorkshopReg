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
@Table(name = "reviews")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reviews.findAll", query = "SELECT r FROM Reviews r")
    , @NamedQuery(name = "Reviews.findByWorkshopId", query = "SELECT r FROM Reviews r WHERE r.reviewsPK.workshopId = :workshopId")
    , @NamedQuery(name = "Reviews.findByWorkshopAndNetId", query = "SELECT a FROM Reviews a WHERE a.reviewsPK.workshopId = :workshopId AND a.reviewsPK.netId = :netId")
    , @NamedQuery(name = "Reviews.findByNetId", query = "SELECT r FROM Reviews r WHERE r.reviewsPK.netId = :netId")
    , @NamedQuery(name = "Reviews.findByReview", query = "SELECT r FROM Reviews r WHERE r.review = :review")})
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReviewsPK reviewsPK;
    @Basic(optional = false)
    @Column(name = "review")
    private String review;
    @JoinColumn(name = "net_id", referencedColumnName = "net_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person person;
    @JoinColumn(name = "workshop_id", referencedColumnName = "workshop_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Workshops workshops;

    public Reviews() {
    }

    public Reviews(ReviewsPK reviewsPK) {
        this.reviewsPK = reviewsPK;
    }

    public Reviews(ReviewsPK reviewsPK, String review) {
        this.reviewsPK = reviewsPK;
        this.review = review;
    }

    public Reviews(int workshopId, String netId) {
        this.reviewsPK = new ReviewsPK(workshopId, netId);
    }

    public ReviewsPK getReviewsPK() {
        return reviewsPK;
    }

    public void setReviewsPK(ReviewsPK reviewsPK) {
        this.reviewsPK = reviewsPK;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
        hash += (reviewsPK != null ? reviewsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reviews)) {
            return false;
        }
        Reviews other = (Reviews) object;
        if ((this.reviewsPK == null && other.reviewsPK != null) || (this.reviewsPK != null && !this.reviewsPK.equals(other.reviewsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.Reviews[ reviewsPK=" + reviewsPK + " ]";
    }
    
}
