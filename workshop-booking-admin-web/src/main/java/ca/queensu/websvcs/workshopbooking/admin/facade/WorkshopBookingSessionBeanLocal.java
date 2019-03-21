package ca.queensu.websvcs.workshopbooking.admin.facade;

import ca.queensu.websvcs.workshopbooking.admin.domain.EmailUtility;
import ca.queensu.websvcs.workshopbooking.admin.domain.ClientDataBean;
import ca.queensu.websvcs.workshopbooking.core.entity.Catalogue;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import java.util.List;
import javax.ejb.Local;

/**
 * <p>WorkshopBookingSessionBeanLocal interface.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
@Local
public interface WorkshopBookingSessionBeanLocal {

    /**
     * <p>getPersonByNetId.</p>
     *
     * @param netId a {@link java.lang.String} object.
     * @return a boolean.
     */
    public Person getPersonByNetId(String netId);
    
    public void savePerson(Person p);
    
    public Catalogue findByWorkshopId(Integer id);
    
    public List<Catalogue> getAllWorkshops();
    
}
