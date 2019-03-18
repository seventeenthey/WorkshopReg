package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.StudentDataBean;
import ca.queensu.websvcs.workshopbooking.client.domain.EmailUtility;
import ca.queensu.websvcs.workshopbooking.client.domain.ClientDataBean;
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
    public List<StudentDataBean> findStudentList();
    public StudentDataBean findStudentByPk(String studentPk);
    public List<String> findCountries();
    public List<String> findHairColours();
    public boolean updateStudent(StudentDataBean studentBean);
    
    // function.jsp
    public List<String> findstatusList();
    public List<String> findlocationList();
    
    //personalDetai.jsp
    public List<String> finddepartmentList();
    public List<String> findroleList();
    
    
}
