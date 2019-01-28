package ca.queensu.websvcs.workshopbooking.admin.facade;

import ca.queensu.websvcs.workshopbooking.core.entity.Detail;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
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
    public Person archetypeBusinessMethodGetPerson(String stuId) {
        
        //example gather data from the archetype DB
        
        Person person = em.createNamedQuery("Person.findByPersonPk", Person.class).setParameter("personPk", new BigDecimal(stuId)).getSingleResult();
        
        return person;
    }


}
