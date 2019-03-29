package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.StudentDataBean;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import ca.queensu.websvcs.workshopbooking.client.domain.EmailInfoForm;
import ca.queensu.websvcs.workshopbooking.client.domain.facilitatorDataBean;
import ca.queensu.websvcs.workshopbooking.core.entity.Departments;
import ca.queensu.websvcs.workshopbooking.core.entity.EventStatus;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
import ca.queensu.websvcs.workshopbooking.core.entity.Roles;
import java.math.BigDecimal;
import java.text.DateFormat;
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
        List<Person> participants = new ArrayList<>();
        for (Person p: w.getMyRegistrants()) {
            participants.add(p);
        }
        return participants;
    }

    @Override
    public boolean updateWorkshopForm(Workshops workshop, WorkshopInfoForm workshopForm){
        //
//      Todo: Need Modified with actural success verification

        String netId = workshop.getTitle();
        System.out.println("WorkshopTitleTestVar "+netId);
        Date date=workshopForm.getRgStDate();
        System.out.println("WorkshopRgStDateTestVar "+date.toString());
//        Date datetime = workshop.getRegistrationStart();
//        System.out.println("WorkshopDateTestVar2 "+datetime.toString());
//        Person facilitator = em.createNamedQuery("Person.findByNetId", Person.class).setParameter("netId", netId).getSingleResult();
//        Departments department = facilitator.getDepartmentId();
//        String title = workshopForm.getEventTitle();
//        String details = workshopForm.getTeaser();
//        String location = workshopForm.getLocation();
//        int maxParticipants = workshopForm.getMaxParticipant();
//        EventStatus eventStatus = new EventStatus(workshopForm.getStatus());

//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date registrationStart = formatter.parse(workshopForm.getRgStDateTime());
//            Date registrationEnd = formatter.parse(workshopForm.getRgEndDateTime());
//            Date eventStart = formatter.parse(workshopForm.getEventStDateTime());
//            Date eventEnd = formatter.parse(workshopForm.getEventEndDateTime());
//            System.out.println("hello2");
//            Workshops w = new Workshops(facilitator, department, title, details, location, maxParticipants, registrationStart, registrationEnd, eventStart, eventEnd, eventStatus);
//            System.out.println("hello3");
//            em.persist(w);
//            em.flush();
//            return true;
//        } catch (Exception e) {
//            System.out.println("Error parsing date");
//            throw new EJBException(e);
//        }
        boolean success = true;
        return success;
    }

/**
 * emailedit.jsp
 * @param emailForm
 * @return
 */
    @Override
    public boolean updateEmailForm(EmailInfoForm emailForm){
//        Todo: Need Modified with actural success verification
        try{
            return true;
        }catch(Exception e) {
            throw  new EJBException(e);
        }
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

}
