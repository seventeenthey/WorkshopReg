package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.*;
import ca.queensu.websvcs.workshopbooking.core.entity.Attendance;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
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

    // function.jsp
    public List<String> findstatusList();
    public List<String> findlocationList();
    public boolean createWorkshop(Person creator, Workshops workshop, WorkshopInfoForm workshopForm);
    public boolean updateWorkshop(Integer workshopId, Workshops workshop, WorkshopInfoForm workshopForm);
    public Integer copyWorkshop(Integer workshopId, Integer copyStrategy);
    //

    // emailedit.jsp
    public boolean updateEmailForm(Integer workshopId, Workshops workshop, EmailInfoForm emailForm);

    // questionnaire.jsp
    public boolean registerIn();

    //personalDetai.jsp
    public List<String> finddepartmentList();
    public List<String> findroleList();

    //for dashboard/Events
    public List<Workshops> findWorkshopList();

    // ADDED BY VINCENT
    public Person getPersonByNetId(String netId);
    public void savePerson(Person p);
    public Workshops findByWorkshopId(Integer id);
    public Workshops findByWorkshopId(String id);

    public List<facilitatorDataBean> findFacilitatorList(Integer workshopId);
    public List<String> findFacilitatorNetidList(Integer workshopId);

    public List<Workshops> getUpcomingWorkshopsByPerson(Person p);
    public List<Workshops> getPastWorkshopsByPerson(Person p);
    public List<Workshops> getWorkshopsForPerson(Person p);
    public List<Workshops> getWorkshopsHostedByPerson(Person p);

    public boolean addParticipant(Integer workshopId, String netId);
    public List<Person> getParticipantsForWorkshop(Integer workshopId);
    public boolean removeParticipant(Integer workshopId, String netId);

    public boolean addFacilitator(Integer workshopId, String netId);
    public boolean removeFacilitator(Integer workshopId, String netId);

    public List<Attendance> getAttendance(Integer workshopId);
    public boolean addAttendee(Integer workshopId, String netId);
    public boolean editAttendeeStatus(Integer workshopId, String netId, boolean status);
    public boolean updateAttendance(Integer workshopId, Workshops workshopData, List<Attendance> attendance);

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
