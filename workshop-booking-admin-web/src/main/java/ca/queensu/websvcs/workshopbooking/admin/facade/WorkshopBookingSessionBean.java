package ca.queensu.websvcs.workshopbooking.admin.facade;

import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import java.math.BigDecimal;
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
    public void savePerson(Person p) {
        em.persist(p);
        em.flush();
    }
    
    @Override
    public Workshops findByWorkshopId(Integer id) {
        Workshops catalogue = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", id).getSingleResult();
        return catalogue;
    }
    
    @Override
    public List<Workshops> getAllWorkshops() {
        List<Workshops> catalogue = em.createNamedQuery("Workshops.findAll", Workshops.class).getResultList();
        return catalogue;
    }

    @Override
    public void removeWorkshopById(Integer id){
        //TODO - add remove workshop query
    }
    
    @Override
    public void removeWorkshopById(String id){
        removeWorkshopById(Integer.valueOf(id));
    }
}
