package ca.queensu.websvcs.workshopbooking.admin.facade;

import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import ca.queensu.websvcs.workshopbooking.core.entity.Departments;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
import ca.queensu.websvcs.workshopbooking.core.entity.Roles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
     * {@inheritDoc}
     *
     * Returns a list of people in the archetype database.
     */
    @Override
    public Person getPersonByNetId(String netId) {
        
        //example gather data from the archetype DB
        
        Person person = em.createNamedQuery("Person.findByNetId", Person.class).setParameter("netId", netId).getSingleResult();
        return person;
    }
    
    @Override
    public List<Person> getAllPeople(){
        List<Person> allPeople = em.createNamedQuery("Person.findAll", Person.class).getResultList();
        return allPeople;
    }
    
    @Override
    @Transactional
    public void savePerson(Person p) {
        em.persist(p);
        em.flush();
    }
    
    @Override
    public Workshops findByWorkshopId(Integer id) {
        Workshops workshop = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", id).getSingleResult();
        return workshop;
    }
    
    @Override
    public List<Workshops> getAllWorkshops() {
        List<Workshops> workshop = em.createNamedQuery("Workshops.findAll", Workshops.class).getResultList();
        return workshop;
    }

    @Override
    @Transactional
    public void removeWorkshopById(Integer workshopId){
        Workshops workshop = findByWorkshopId(workshopId);
        em.remove(workshop);
    }
    
    @Override
    @Transactional
    public void removeWorkshopById(String id){
        removeWorkshopById(Integer.valueOf(id));
    }
    
    @Override
    public List<String> findroleList(){
//      List of all possible locations to hold a workshop
        List<String> roleList = new ArrayList<>();
        for (Roles role: em.createNamedQuery("Roles.findAll", Roles.class).getResultList()) {
            roleList.add(role.getRoleName());
        }
        return roleList;
    }
    
    @Override 
    public List<String> findDepartmentList(){
        List<String> departmentList = new ArrayList();
        for (Departments dep: em.createNamedQuery("Departments.findAll", Departments.class).getResultList())
            departmentList.add(dep.getDepartmentName());
        return departmentList;
    }
    
    @Override
    @Transactional
    public boolean addLocation(String locationName) {
        Locations loc = new Locations(locationName);
        em.persist(loc);
        em.flush();
        return true;
    }
}
