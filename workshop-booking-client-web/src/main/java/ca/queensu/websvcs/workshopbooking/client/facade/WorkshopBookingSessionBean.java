package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.StudentDataBean;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import ca.queensu.websvcs.workshopbooking.client.domain.EmailInfoForm;
import ca.queensu.websvcs.workshopbooking.client.domain.facilitatorDataBean;
import ca.queensu.websvcs.workshopbooking.core.entity.EventStatus;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
import java.math.BigDecimal;
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
    public List<String> findCountries() {
        List<String> countries = new ArrayList<>();
        countries.add("Dominica");
        countries.add("Norfolk Island");
        countries.add("Comoros");
        countries.add("Morocco");
        countries.add("Cayman Islands");
        countries.add("Costa Rica");
        countries.add("Mayotte");
        countries.add("Niger");
        countries.add("Slovakia");
        countries.add("Belgium");
        countries.add("Martinique");
        countries.add("Gibraltar");
        countries.add("England");
        countries.add("Panama");
        countries.add("Saint Barthelemy");
        countries.add("Palau");
        countries.add("Curacao");
        countries.add("Reunion");
        countries.add("Bonaire");
        countries.add("Saint Vincent and the Grenadines");
        countries.add("Slovenia");
        countries.add("Lesotho");
        countries.add("Japan");
        countries.add("North Korea");
        countries.add("French Guiana");
        countries.add("Uruguay");
        countries.add("Canada");
        countries.add("France");
        countries.add("Qatar");
        countries.add("Guam");
        return  countries;
    }
    
    @Override
    public List<String> findHairColours() {
        List<String> hairColours = new ArrayList<>();
        hairColours.add("Pink");
        hairColours.add("Purple");
        hairColours.add("Green");
        return  hairColours;
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
    public boolean updateWorkshopForm(WorkshopInfoForm workshopForm){
//      Todo: Need Modified with actural success verification
        System.out.println("Received");
        System.out.println(workshopForm.getRgStDate());
        System.out.println(workshopForm.getEventTitle());
        try{
            return true;
        }catch(Exception e) {
            throw  new EJBException(e);
        }
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
        departmentList.add("Choose a department");
        departmentList.add("Arts");
        departmentList.add("Computing");
        departmentList.add("Engineering");
        departmentList.add("Nursing");
        return departmentList;
    }
    
    @Override
    public List<String> findroleList(){
//      List of all possible locations to hold a workshop
        List<String> roleList = new ArrayList<>();
        roleList.add("Faculty");
        roleList.add("Staff");
        return roleList;
    }
    
    //actually just creates a new list
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
    
    //to create dummy workshop data
    private WorkshopInfoForm generateWorkshopInfo(int num) {
        WorkshopInfoForm workshop = new WorkshopInfoForm();
        workshop.setEventTitle("How to be Cool " + num);
        workshop.setWorkshopNumber(num);
        
        String year, month, day, hour, min;
        int tempHour;
        
        Random rand = new Random();
        
        //Commented out code is for using if dates are java Date objects
        
        //year = 2019;
        //month = rand.nextInt(2) + 2;
        //day = rand.nextInt(28);
        
        year = "2019";
        month = Integer.toString(rand.nextInt(2) + 2);
        day = Integer.toString(rand.nextInt(28));
        
        tempHour = rand.nextInt(24);
        //hour = tempHour;
        //min = rand.nextInt(4) * 15;
        hour = Integer.toString(tempHour);
        min = Integer.toString(rand.nextInt(4) * 15);
        
        /*
        Date d = new Date();
        d.setYear(year);
        d.setMonth(month);
        d.setDate(day);
        d.setHours(hour);
        d.setMinutes(min);
        */
        
        workshop.setRgStDate(year + "," + month + "," + day);
        workshop.setRgStTime(hour + "," + min);
        
        /*
        workshop.setRgStDate(d);
        workshop.setRgEndDate(d);
        */
        
        hour = Integer.toString(tempHour + 2);
        workshop.setRgEndTime(hour + "," + min);
        
        List<String> departments = finddepartmentList();
        workshop.setDepartment(departments.get(rand.nextInt(departments.size()-1)+1));
        
        return workshop;
    }
    
    // ADDED BY VINCENT (3 ITEMS)
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
        Workshops workshop = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", id).getSingleResult();
        return workshop;
    }
    

    @Override
    public Workshops findByWorkshopId(String id) {
        return findByWorkshopId(Integer.valueOf(id));
    }
    
    @Override
    public List<facilitatorDataBean> findFacilitatorList() {
        try {
            List<facilitatorDataBean> facilitatorList = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                facilitatorDataBean facilBean = generateFacilBean(i);
                facilitatorList.add(facilBean);
            }
            return facilitatorList;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }
    
    private facilitatorDataBean generateFacilBean(int facilNum) {
        facilitatorDataBean facilBean = new facilitatorDataBean();
        facilBean.setFacilID(String.valueOf(facilNum));
        facilBean.setFacilFirstName("Be");
        facilBean.setFacilLastName("Cool" + facilNum);
       
        return facilBean;
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
}
