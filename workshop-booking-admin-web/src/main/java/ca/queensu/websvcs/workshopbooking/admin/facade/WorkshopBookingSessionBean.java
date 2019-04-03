package ca.queensu.websvcs.workshopbooking.admin.facade;

import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import ca.queensu.websvcs.workshopbooking.core.entity.Departments;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
import ca.queensu.websvcs.workshopbooking.core.entity.Roles;
import java.util.ArrayList;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import javax.transaction.Transactional;

/**
 * <p>WorkshopBookingSessionBean class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
@Stateless(mappedName = "WorkshopBookingSessionBean")
@Local
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkshopBookingSessionBean implements WorkshopBookingSessionBeanLocal {

    private final Logger log = LogManager.getLogger(WorkshopBookingSessionBean.class);
     
    @PersistenceContext(unitName = "WorkshopBooking-WebPU")
    private EntityManager em;

    @Inject
    QueensEmailInterface emailStub;// = new CaQueensuUisWebservicesEmailStub();
    
    /**
     * @return the list of all possible user roles
     */
    @Override
    public List<String> getRoleList() {
        List<String> roleList = new ArrayList<>();
        for (Roles role: em.createNamedQuery("Roles.findAll", Roles.class).getResultList()) {
            roleList.add(role.getRoleName());
        }
        return roleList;
    }
    
    /**
     * @return a list of all Queen's departments
     */
    @Override 
    public List<String> getDepartmentList() {
        List<String> departmentList = new ArrayList();
        for (Departments dep: em.createNamedQuery("Departments.findAll", Departments.class).getResultList())
            departmentList.add(dep.getDepartmentName());
        return departmentList;
    }
    
    /**
     * @return a list of all people in the database
     */
    @Override
    public List<Person> getAllPeople(){
        List<Person> allPeople = em.createNamedQuery("Person.findAll", Person.class).getResultList();
        return allPeople;
    }
    
    /**
     * @param netId
     * @return a specific person from the database
     */
    @Override
    public Person getPersonByNetId(String netId) {
        Person person = em.createNamedQuery("Person.findByNetId", Person.class).setParameter("netId", netId).getSingleResult();
        return person;
    }
    
    @Override
    public List<Workshops> getAllWorkshops() {
        List<Workshops> workshop = em.createNamedQuery("Workshops.findAll", Workshops.class).getResultList();
        return workshop;
    }
    
    /**
     * @param id
     * @return a workshop using a workshopID
     */
    @Override
    public Workshops getWorkshopById(Integer id) {
        Workshops workshop = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", id).getSingleResult();
        return workshop;
    }

    /**
     * @param workshopId 
     * Deletes a workshop from the database using a workshopID
     */
    @Override
    @Transactional
    public void removeWorkshopById(Integer workshopId) {
        Workshops workshop = getWorkshopById(workshopId);
        em.remove(workshop);
    }
    
    /**
     * @param id 
     * Deletes a workshop from the database using a string type workshopID
     */
    @Override
    @Transactional
    public void removeWorkshopById(String id) {
        removeWorkshopById(Integer.valueOf(id));
    }
    
    /**
     * @param locationName
     * @return boolean: whether a location was added or not
     * 
     * Adds a new location to the database
     */
    @Override
    @Transactional
    public boolean addLocation(String locationName) {
        Locations loc = new Locations(locationName);
        em.persist(loc);
        em.flush();
        return true;
    }
    
    /**
     * @param netId
     * @param roleId
     * @param department 
     * 
     * Updates a user's role and department
     */
    @Override
    @Transactional
    public void updateRole(String netId, int roleId, String department) {
        Person p = getPersonByNetId(netId);
        Roles r = em.createNamedQuery("Roles.findByRoleId", Roles.class).setParameter("roleId", roleId).getSingleResult();
        Departments d = em.createNamedQuery("Departments.findByDepartmentName", Departments.class).setParameter("departmentName", department).getSingleResult();
        p.setRoleId(r);
        p.setDepartmentId(d);
        em.merge(p);
    }
    
    /**
     * @param netId
     * @param roleName
     * @param department 
     * 
     * Updates a user's role and department
     */
    @Override
    @Transactional
    public void updateRole(String netId, String roleName, String department) {
        Person p = getPersonByNetId(netId);
        Roles r = em.createNamedQuery("Roles.findByRoleName", Roles.class).setParameter("roleName", roleName).getSingleResult();
        Departments d = em.createNamedQuery("Departments.findByDepartmentName", Departments.class).setParameter("departmentName", department).getSingleResult();
        p.setRoleId(r);
        p.setDepartmentId(d);
        em.merge(p);
    }
}
