/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vincent
 */
@Entity
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByNetId", query = "SELECT p FROM Person p WHERE p.netId = :netId")
    , @NamedQuery(name = "Person.findByEmplId", query = "SELECT p FROM Person p WHERE p.emplId = :emplId")
    , @NamedQuery(name = "Person.findByCommonName", query = "SELECT p FROM Person p WHERE p.commonName = :commonName")
    , @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email")
})

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "net_id")
    private String netId;
    @Basic(optional = false)
    @Column(name = "empl_id")
    private int emplId;
    @Basic(optional = false)
    @Column(name = "common_name")
    private String commonName;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    
    /**
    @ManyToMany(mappedBy = "personCollection")
    private Collection<Workshops> workshopsCollection;
    @ManyToMany(mappedBy = "personCollection1")
    private Collection<Workshops> workshopsCollection1;
    @ManyToMany(mappedBy = "personCollection2")
    private Collection<Workshops> workshopsCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<Reviews> reviewsCollection;
    **/
    
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne(optional = false)
    private Departments departmentId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Roles roleId;
    
    @ManyToMany(mappedBy = "myRegistrants")
    private List<Workshops> myWorkshops;
    
    @ManyToMany(mappedBy = "myFacilitators")
    private List<Workshops> myOwnedWorkshops;

    public Person() {
    }

    public Person(String netId) {
        this.netId = netId;
    }

    public Person(String netId, int emplId, String commonName, String email) {
        this.netId = netId;
        this.emplId = emplId;
        this.commonName = commonName;
        this.email = email;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public int getEmplId() {
        return emplId;
    }

    public void setEmplId(int emplId) {
        this.emplId = emplId;
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

    /**
    @XmlTransient
    public Collection<Workshops> getWorkshopsCollection() {
        return workshopsCollection;
    }

    public void setWorkshopsCollection(Collection<Workshops> workshopsCollection) {
        this.workshopsCollection = workshopsCollection;
    }

    @XmlTransient
    public Collection<Workshops> getWorkshopsCollection1() {
        return workshopsCollection1;
    }

    public void setWorkshopsCollection1(Collection<Workshops> workshopsCollection1) {
        this.workshopsCollection1 = workshopsCollection1;
    }

    @XmlTransient
    public Collection<Workshops> getWorkshopsCollection2() {
        return workshopsCollection2;
    }

    public void setWorkshopsCollection2(Collection<Workshops> workshopsCollection2) {
        this.workshopsCollection2 = workshopsCollection2;
    }

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

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (netId != null ? netId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.netId == null && other.netId != null) || (this.netId != null && !this.netId.equals(other.netId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ca.queensu.websvcs.workshopbooking.Person[ netId=" + netId + " ]";
    }
    
    
    @XmlTransient
    public List<Workshops> getAllWorkshops() {
        return myWorkshops;
    }
    
    public void setMyWorkshops(List<Workshops> workshops) {
        this.myWorkshops = workshops;
    }
    
    public void addWorkshop(Workshops w) {
        this.myWorkshops.add(w);
        w.getMyRegistrants().add(this);
    }
    
    @XmlTransient
    public List<Workshops> getOwnedWorkshops() {
        return myOwnedWorkshops;
    }
    
    public void setOwnedWorkshops(List<Workshops> workshops) {
        this.myOwnedWorkshops = workshops;
    }
    
    public void addOwnedWorkshop(Workshops w) {
        this.myOwnedWorkshops.add(w);
        w.getMyFacilitators().add(this);
    }

    public List<Workshops> getUpcomingWorkshops(){
        Date today = new Date();
        List<Workshops> allWorkshops = getAllWorkshops();
        List<Workshops> upcoming = new ArrayList();
        
        for(int i = 0;i < allWorkshops.size(); i++)
            if (allWorkshops.get(i).getEventEnd().after(today))
                upcoming.add(allWorkshops.get(i));
        
        return upcoming;
    }
    
    public List<Workshops> getPastWorkshops(){
        Date today = new Date();
        List<Workshops> allWorkshops = getAllWorkshops();
        List<Workshops> past = new ArrayList();
        
        for(int i = 0;i < allWorkshops.size(); i++)
            if (allWorkshops.get(i).getEventEnd().before(today))
                past.add(allWorkshops.get(i));
        
        return past;
    }
    
}
