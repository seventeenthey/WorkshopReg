package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.StudentDataBean;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import ca.queensu.websvcs.workshopbooking.core.entity.Catalogue;
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
    
    private StudentDataBean generateStudentBean(int studentPk) {
        StudentDataBean studBean = new StudentDataBean();
        studBean.setStudentPk(String.valueOf(studentPk));
        studBean.setFirstName("Jane");
        studBean.setLastName("Doe" + studentPk);
        studBean.setAge(String.valueOf(20 + studentPk));
        studBean.setCountry(findCountries().get(studentPk));
        studBean.setHairColour("Pink");
        studBean.setOrganDonor(true);
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
    
/*
    Function Page
    */
    @Override
    public List<String> findstatusList(){
//      List of possible status for workshops
        List<String> statusList = new ArrayList<>();
        statusList.add("Not Posted");
        statusList.add("Posted");
        statusList.add("Archived");
        return statusList;
    }
    
    @Override
    public List<String> findlocationList(){
//      List of all possible locations to hold a workshop
        List<String> locationList = new ArrayList<>();
        locationList.add("Dunning Hall");
        locationList.add("WalterLight Hall");
        locationList.add("Stauffer Lib");
        return locationList;
    }
    
    @Override
    public boolean updateWorkshopForm(WorkshopInfoForm workshopForm){
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
    public List<WorkshopInfoForm> findWorkshopList() {
        try {
            List<WorkshopInfoForm> workshopBeanList = new ArrayList<>();
            for(int i = 0; i < 20; i++) {
                WorkshopInfoForm workshop = generateWorkshopInfo(i);
                workshopBeanList.add(workshop);
            }
            return workshopBeanList;
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
    
    //does not actually search, simply creates a new one
    @Override
    public WorkshopInfoForm findWorkshopByNum(String workshopNum){
        try {
            WorkshopInfoForm workshop = generateWorkshopInfo(Integer.valueOf(workshopNum));
            return workshop;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
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
    public Catalogue findByWorkshopId(Integer id) {
        Catalogue catalogue = em.createNamedQuery("Catalogue.findByWorkshopId", Catalogue.class).setParameter("workshopId", id).getSingleResult();
        return catalogue;
    }
    
    //TODO - Vincent or Taylor
    //make this find all workshops that person is attending
    @Override
    public List<WorkshopInfoForm> findRegisteredWorkshops(Person person){
        List<WorkshopInfoForm> output = new ArrayList();
        
        for(int i = 0; i < 10; i++)
            output.add(generateWorkshopInfo(i));
        
        return output;
    }
    
    //TODO - Vincent or Taylor
    //make this find all workshops that person has created
    @Override public List<WorkshopInfoForm> findCreatedWorkshops(Person person){
        List<WorkshopInfoForm> output = new ArrayList();
        
        for(int i = 0; i < 5; i++)
            output.add(generateWorkshopInfo(i));
        
        return output;
    }
}
