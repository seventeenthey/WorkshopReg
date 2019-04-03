package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.*;
import ca.queensu.websvcs.workshopbooking.core.entity.Attendance;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.websvcs.workshopbooking.core.entity.Reviews;
import ca.queensu.websvcs.workshopbooking.core.entity.Waitlist;
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

    public List<String> getStatusList();
    public List<String> getLocationList();
    public List<String> getDepartmentList();
    public List<String> getRoleList();
    public List<Workshops> getWorkshopsList();
    
    public List<Person> getWorkshopRegistrants(Integer workshopId);
    public boolean addWorkshopRegistrant(Integer workshopId, String netId);
    public boolean removeWorkshopRegistrant(Integer workshopId, String netId);
    
    public boolean createWorkshop(Person creator, Workshops workshop, WorkshopInfoForm workshopForm);
    public boolean updateWorkshop(Integer workshopId, Workshops workshop, WorkshopInfoForm workshopForm);
    public Integer copyWorkshop(Integer workshopId, Integer copyStrategy);

    public boolean updateEmailForm(Integer workshopId, Workshops workshop, EmailInfoForm emailForm);

    public boolean savePerson(Person p);
    public Person getPersonByNetId(String netId);
    public Workshops getWorkshopById(Integer id);
    public Workshops getWorkshopById(String id);

    public List<facilitatorDataBean> getFacilitatorList(Integer workshopId);
    public List<String> getFacilitatorListOfNetIds(Integer workshopId);

    public List<Workshops> getWorkshopsForPerson(Person p);
    public List<Workshops> getWorkshopsHostedByPerson(Person p);
    public List<Workshops> getPastWorkshopsByPerson(Person p);
    public List<Workshops> getUpcomingWorkshopsByPerson(Person p);

    public boolean addFacilitator(Integer workshopId, String netId);
    public boolean removeFacilitator(Integer workshopId, String netId);

    public List<Attendance> getAttendance(Integer workshopId);
    public boolean addAttendee(Integer workshopId, String netId);
    public boolean editAttendeeStatus(Integer workshopId, String netId, boolean status);
    public boolean removeAttendee(Integer workshopId, String netId);

    public List<Reviews> getReviews(Integer workshopId);
    public List<String> getIdReviews(Integer workshopId);    
    public boolean addReview(Integer workshopId, String netId, String review);
    public boolean editReview(Integer workshopId, String netId, String editedReview);
    public boolean removeReview(Integer workshopId, String netId);

    public List<Waitlist> getWaitlist(Integer workshopId);
    public boolean addToWaitlist(Integer workshopId, String netId);
    public boolean removeFromWaitlist(Integer workshopId, String netId);
    
    public boolean isOnWaitlist(Integer workshopId, String netId);
    public boolean isRegistered(Integer workshopId, String netId);
    public boolean workshopHasPast(Integer workshopId);
}
