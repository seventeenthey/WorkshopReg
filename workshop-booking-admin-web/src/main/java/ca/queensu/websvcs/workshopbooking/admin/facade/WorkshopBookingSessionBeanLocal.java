package ca.queensu.websvcs.workshopbooking.admin.facade;

import ca.queensu.websvcs.workshopbooking.admin.domain.EmailUtility;
import ca.queensu.websvcs.workshopbooking.admin.domain.ClientDataBean;
import ca.queensu.websvcs.workshopbooking.core.entity.Detail;
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
     * <p>archetypeBusinessMethodGetPerson.</p>
     *
     * @param stuId a {@link java.lang.String} object.
     * @return a boolean.
     */
    public Person archetypeBusinessMethodGetPerson(String stuId);
    
    
}
