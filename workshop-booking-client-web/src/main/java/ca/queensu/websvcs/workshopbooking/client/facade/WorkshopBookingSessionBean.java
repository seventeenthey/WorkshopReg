package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.StudentDataBean;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import ca.queensu.websvcs.workshopbooking.client.domain.EmailInfoForm;
import ca.queensu.websvcs.workshopbooking.client.domain.facilitatorDataBean;
import ca.queensu.websvcs.workshopbooking.core.entity.Attendance;
import ca.queensu.websvcs.workshopbooking.core.entity.Departments;
import ca.queensu.websvcs.workshopbooking.core.entity.EventStatus;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
import ca.queensu.websvcs.workshopbooking.core.entity.Reviews;
import ca.queensu.websvcs.workshopbooking.core.entity.Roles;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Random;
import static java.util.stream.Collectors.toList;
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


@Override
    public List<StudentDataBean> findStudentList() {
        try {
            List<StudentDataBean> studBeanList = new ArrayList<>();
            for(int i = 0; i < 20; i++) {
                StudentDataBean studBean = generateStudentBean(i);
                studBeanList.add(studBean);
            }
            return studBeanList;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

    private StudentDataBean generateStudentBean(int studentNum) {
        StudentDataBean studBean = new StudentDataBean();
        studBean.setStudentID(String.valueOf(studentNum));
        studBean.setStudentFirstName("Jane");
        studBean.setStudentLastName("Doe" + studentNum);
        return studBean;
    }

    @Override
    public StudentDataBean findStudentByPk(String studentPk) {
        try {
            StudentDataBean stuBean = generateStudentBean(Integer.valueOf(studentPk));
            return stuBean;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

    @Override
    public boolean updateStudent(StudentDataBean studentBean) {
        try {
            return true;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

/**
 * function.jsp
 * @return statusList; locationList; submit condition
 */
    @Override
    public List<String> findstatusList(){
//      List of possible status for workshops
        List<String> statusList = new ArrayList<>();
        for (EventStatus eventStatus: em.createNamedQuery("EventStatus.findAll", EventStatus.class).getResultList()) {
            statusList.add(eventStatus.getEventStatus());
        }
        return statusList;
    }

    @Override
    public List<String> findlocationList(){
//      List of all possible locations to hold a workshop
        List<String> locationList = new ArrayList<>();
        for (Locations location: em.createNamedQuery("Locations.findAll", Locations.class).getResultList()) {
            locationList.add(location.getLocationName());
        }
        return locationList;
    }

    @Override
    @Transactional
    public List<Person> getParticipantsForWorkshop(Integer workshopId){
        // Get all participants in a workshop
        Workshops w = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", workshopId).getSingleResult();
        return w.getMyRegistrants();
    }
    
    @Override
    @Transactional
    public boolean addParticipant(Integer workshopId, String netId) {
        Workshops workshop = findByWorkshopId(workshopId);
        Person p = getPersonByNetId(netId);
        workshop.addRegistrant(p);
        return true;
    }
    
    @Override
    @Transactional
    public boolean removeParticipant(Integer workshopId, String netId) {
        Workshops workshop = findByWorkshopId(workshopId);
        Person p = getPersonByNetId(netId);
        workshop.removeRegistrant(p);
        return true;
    }
    
    
    @Override
    @Transactional
    public boolean createWorkshop(Person creator, Workshops workshop, WorkshopInfoForm workshopForm) {
        
        workshop.setWorkshopCreatorId(creator);
        workshop.setDepartmentId(creator.getDepartmentId());
        workshop.setEmailNotificationName("");
        workshop.setEmailConfirmationMsg("");
        workshop.setEmailWaitlistMsg("");
        workshop.setEmailCancellationMsg("");
        workshop.setEmailEvaluationMsg("");
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            workshop.setRegistrationStart(formatter.parse(workshopForm.getRgStDateTime()));
            workshop.setRegistrationEnd(formatter.parse(workshopForm.getRgEndDateTime()));
            workshop.setEventStart(formatter.parse(workshopForm.getEventStDateTime()));
            workshop.setEventEnd(formatter.parse(workshopForm.getEventEndDateTime()));
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            throw new EJBException(e);
        }
        
        em.persist(workshop);
        return true;
    }

    @Override
    @Transactional
    public boolean updateWorkshop(Integer workshopId, Workshops workshop, WorkshopInfoForm workshopForm) {
        
        Workshops oldWorkshop = findByWorkshopId(workshopId);
        workshop.setWorkshopId(workshopId);
        workshop.setWorkshopCreatorId(oldWorkshop.getWorkshopCreatorId());
        workshop.setDepartmentId(oldWorkshop.getDepartmentId());
        workshop.setEmailNotificationName(oldWorkshop.getEmailNotificationName());
        workshop.setEmailConfirmationMsg(oldWorkshop.getEmailConfirmationMsg());
        workshop.setEmailWaitlistMsg(oldWorkshop.getEmailWaitlistMsg());
        workshop.setEmailCancellationMsg(oldWorkshop.getEmailCancellationMsg());
        workshop.setEmailEvaluationMsg(oldWorkshop.getEmailEvaluationMsg());

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            workshop.setRegistrationStart(formatter.parse(workshopForm.getRgStDateTime()));
            workshop.setRegistrationEnd(formatter.parse(workshopForm.getRgEndDateTime()));
            workshop.setEventStart(formatter.parse(workshopForm.getEventStDateTime()));
            workshop.setEventEnd(formatter.parse(workshopForm.getEventEndDateTime()));
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            throw new EJBException(e);
        }

        em.merge(workshop);
        return true;
    }

/**
 * emailedit.jsp
    * @param workshopId
    * @param emailForm
    * @return
 */
    @Override
    @Transactional
    public boolean updateEmailForm(Integer workshopId, Workshops workshopData, EmailInfoForm emailForm) {
        Workshops workshop = findByWorkshopId(workshopId);
        workshop.setEmailNotificationName(workshopData.getEmailNotificationName());
        workshop.setEmailConfirmationMsg(workshopData.getEmailConfirmationMsg());
        workshop.setEmailWaitlistMsg(workshopData.getEmailWaitlistMsg());
        workshop.setEmailCancellationMsg(workshopData.getEmailCancellationMsg());
        workshop.setEmailEvaluationMsg(workshopData.getEmailEvaluationMsg());
        em.merge(workshop);
        return true;
    }

    @Override
    public List<String> finddepartmentList(){
//      List of all possible locations to hold a workshop
        List<String> departmentList = new ArrayList<>();
        for (Departments department: em.createNamedQuery("Departments.findAll", Departments.class).getResultList()) {
            departmentList.add(department.getDepartmentName());
        }
        return departmentList;
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
    public List<Workshops> findWorkshopList() {
        try {
            List<Workshops> workshopList = em.createNamedQuery("Workshops.findAll", Workshops.class).getResultList();
            return workshopList;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

    @Override
    public Person getPersonByNetId(String netId) {
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
        Workshops workshop = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", id).getSingleResult();
        return workshop;
    }

    @Override
    public Workshops findByWorkshopId(String id) {
        return findByWorkshopId(Integer.valueOf(id));
    }

    @Override
    public List<facilitatorDataBean> findFacilitatorList(Integer workshopId) {
        try {
            Workshops w = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", workshopId).getSingleResult();
            List<facilitatorDataBean> facilitatorList = new ArrayList<>();
            for (Person p: w.getMyFacilitators()) {
                facilitatorList.add(new facilitatorDataBean(p.getNetId(),p.getCommonName()));
            }
            return facilitatorList;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

    // find all workshops that person is attending
    @Override
    public List<Workshops> getWorkshopsForPerson(Person p) {
        return p.getAllWorkshops();
    }

    // find all workshops that person has created
    @Override
    public List<Workshops> getWorkshopsHostedByPerson(Person p) {
        List<Workshops> workshops = em.createNamedQuery("Workshops.findByWorkshopCreatorId", Workshops.class).setParameter("netId", p.getNetId()).getResultList();
        return workshops;
    }

    @Override
    public List<Workshops> getPastWorkshopsByPerson(Person p){
        return p.getPastWorkshops();
    }

    @Override
    public List<Workshops> getUpcomingWorkshopsByPerson(Person p){
        return p.getUpcomingWorkshops();
    }

    // questionaire.jsp
    public boolean registerIn(){
        return true;
    }
    
    @Override
    @Transactional
    public boolean addFacilitator(Integer workshopId, String netId) {
        Workshops workshop = findByWorkshopId(workshopId);
        Person p = getPersonByNetId(netId);
        workshop.addFacilitator(p);
        return true;
    }
    
    @Override
    @Transactional
    public boolean removeFacilitator(Integer workshopId, String netId) {
        Workshops workshop = findByWorkshopId(workshopId);
        Person p = getPersonByNetId(netId);
        workshop.removeFacilitator(p);
        return true;
    }
    
    @Override
    public List<Attendance> getAttendance(Integer workshopId) {
        List<Attendance> attendance = em.createNamedQuery("Attendance.findByWorkshopId", Attendance.class).setParameter("workshopId", workshopId).getResultList();
        return attendance;
    }
    
    @Override
    @Transactional
    public boolean addAttendee(Integer workshopId, String netId) {
        Attendance a = new Attendance(workshopId, netId);
        em.persist(a);
        return true;
    }
    
    @Override
    @Transactional
    public boolean editAttendeeStatus(Integer workshopId, String netId, boolean status) {
        Attendance a = em.createNamedQuery("Attendance.findByWorkshopAndNetId", Attendance.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        a.setAttended(status);
        em.merge(a);
        return true;
    }
    
    @Override
    public List<Reviews> getReviews(Integer workshopId) {
        List<Reviews> myReviews = em.createNamedQuery("Reviews.findByWorkshopId", Reviews.class).setParameter("workshopId", workshopId).getResultList();
        return myReviews;
    }
    
    @Override
    @Transactional
    public boolean addReview(Integer workshopId, String netId, String review) {
        Reviews newReview = em.createNamedQuery("Reviews.findByWorkshopAndNetId", Reviews.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        newReview.setReview(review);
        em.merge(newReview);
        return true;
    }
    
    @Override
    @Transactional
    public boolean editReview(Integer workshopId, String netId, String editedReview) {
        Reviews review = em.createNamedQuery("Reviews.findByWorkshopAndNetId", Reviews.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        review.setReview(editedReview);
        em.merge(review);
        return true;
    }

}
