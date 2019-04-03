package ca.queensu.websvcs.workshopbooking.admin.facade;

import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
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

    public List<String> getRoleList();
    public List<String> getDepartmentList();
    
    public List<Person> getAllPeople();
    public Person getPersonByNetId(String netId);
    
    public List<Workshops> getAllWorkshops();
    public Workshops getWorkshopById(Integer id);
    
    public void removeWorkshopById(Integer id);
    public void removeWorkshopById(String id);
    
    public boolean addLocation(String locationName);
    
    public void updateRole(String netId, int roleId, String department);
    public void updateRole(String netId, String roleName, String department);
}
