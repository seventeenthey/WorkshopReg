package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.*;
import ca.queensu.websvcs.workshopbooking.core.entity.Attendance;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
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

    public List<StudentDataBean> findStudentList();
    public StudentDataBean findStudentByPk(String studentPk);
    public boolean updateStudent(StudentDataBean studentBean);
    
    // function.jsp
    public List<String> findstatusList();
    public List<String> findlocationList();
    public boolean createWorkshop(Person creator, Workshops workshop, WorkshopInfoForm workshopForm);
    public boolean updateWorkshop(Integer workshopId, Workshops workshop, WorkshopInfoForm workshopForm);
    //
    
    // emailedit.jsp
    public boolean updateEmailForm(EmailInfoForm emailForm);
    
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
    
    
    
    public List<Workshops> getUpcomingWorkshopsByPerson(Person p);
    public List<Workshops> getPastWorkshopsByPerson(Person p);
    public List<Workshops> getWorkshopsForPerson(Person p);
    public List<Workshops> getWorkshopsHostedByPerson(Person p);
    
    public List<Attendance> getAttendance(Integer workshopId);
    public List<Person> getParticipantsForWorkshop(Integer workshopId);
    public boolean addFacilitator(Integer workshopId, String netId);
    public boolean removeFacilitator(Integer workshopId, String netId);
    public boolean addParticipant(Integer workshopId, String netId);
    
    public boolean addAttendee(Integer workshopId, String netId);
    public boolean editAttendeeStatus(Integer workshopId, String netId, boolean status);
}
