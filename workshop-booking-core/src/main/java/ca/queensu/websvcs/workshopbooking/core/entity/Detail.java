/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CISC-498
 */
@Entity
@Table(name = "DETAIL", catalog = "", schema = "ARCHETYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detail.findAll", query = "SELECT d FROM Detail d")
    , @NamedQuery(name = "Detail.findByDetailPk", query = "SELECT d FROM Detail d WHERE d.detailPk = :detailPk")
    , @NamedQuery(name = "Detail.findByAttr", query = "SELECT d FROM Detail d WHERE d.attr = :attr")})
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DETAIL_PK")
    private BigDecimal detailPk;
    @Column(name = "ATTR")
    private String attr;
    @JoinColumn(name = "PERSON_FK", referencedColumnName = "PERSON_PK")
    @ManyToOne
    private Person personFk;

    public Detail() {
    }

    public Detail(BigDecimal detailPk) {
        this.detailPk = detailPk;
    }

    public BigDecimal getDetailPk() {
        return detailPk;
    }

    public void setDetailPk(BigDecimal detailPk) {
        this.detailPk = detailPk;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Person getPersonFk() {
        return personFk;
    }

    public void setPersonFk(Person personFk) {
        this.personFk = personFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailPk != null ? detailPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detail)) {
            return false;
        }
        Detail other = (Detail) object;
        if ((this.detailPk == null && other.detailPk != null) || (this.detailPk != null && !this.detailPk.equals(other.detailPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.core.entity.Detail[ detailPk=" + detailPk + " ]";
    }
    
}
