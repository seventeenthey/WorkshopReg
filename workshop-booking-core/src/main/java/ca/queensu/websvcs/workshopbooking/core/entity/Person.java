/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author CISC-498
 */
@Entity
@Table(name = "PERSON", catalog = "", schema = "ARCHETYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByPersonPk", query = "SELECT p FROM Person p WHERE p.personPk = :personPk")
    , @NamedQuery(name = "Person.findByCommonName", query = "SELECT p FROM Person p WHERE p.commonName = :commonName")
    , @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PERSON_PK")
    private BigDecimal personPk;
    @Basic(optional = false)
    @Column(name = "COMMON_NAME")
    private String commonName;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(mappedBy = "personFk")
    private List<Detail> detailList;

    public Person() {
    }

    public Person(BigDecimal personPk) {
        this.personPk = personPk;
    }

    public Person(BigDecimal personPk, String commonName) {
        this.personPk = personPk;
        this.commonName = commonName;
    }

    public BigDecimal getPersonPk() {
        return personPk;
    }

    public void setPersonPk(BigDecimal personPk) {
        this.personPk = personPk;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personPk != null ? personPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personPk == null && other.personPk != null) || (this.personPk != null && !this.personPk.equals(other.personPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.core.entity.Person[ personPk=" + personPk + " ]";
    }
    
}
