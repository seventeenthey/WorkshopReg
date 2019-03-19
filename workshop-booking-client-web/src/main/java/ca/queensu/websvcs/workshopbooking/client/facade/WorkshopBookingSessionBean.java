package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.StudentDataBean;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.core.entity.Detail;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        
        return workshop;
    }
    
    //does not actually search, simply creates a new one
    public WorkshopInfoForm findWorkshopByNum(String workshopNum){
        try {
            WorkshopInfoForm workshop = generateWorkshopInfo(Integer.valueOf(workshopNum));
            return workshop;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }
}
