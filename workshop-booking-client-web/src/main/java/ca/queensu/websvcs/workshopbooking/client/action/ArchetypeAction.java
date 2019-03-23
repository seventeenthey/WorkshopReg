package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.uis.sso.tools.common.SSOConstants;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import ca.queensu.websvcs.workshopbooking.client.facade.*;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>ArchetypeAction class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class ArchetypeAction extends ActionSupport implements Preparable {

    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.ArchetypeAction.class);
     
    
    
    //TODO these are entity objects from EASi  make archetype entities
    private Person person;
   
    
    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    List<WorkshopInfoForm> workshopBeanList;
    String searchKey;

    /**
     * <p>Constructor for DashboardAction.</p>
     */
    public ArchetypeAction() {
        
    }

/** {@inheritDoc} */
    @Override
    public void prepare() throws Exception{
        workshopBeanList = ejb.findWorkshopList();
    } 
    
    /**
     * {@inheritDoc}
     *
     * Grabs emplid and netid from session 
     * 
     */
    @Override
    public String execute() throws Exception {
        
        System.out.println("EXECUTING");
        
        log.info("Entering execute of ArchetypeAction");
        
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        
        try {
            
            workshopBeanList = ejb.findWorkshopList();
            System.out.println("workshop bean list length: "+workshopBeanList.size());
            System.out.println(workshopBeanList.get(5).getEventTitle());
            System.out.println("Should've printed some BS");
            
            String studentId = (String) session.getAttribute(SSOConstants.EMPL_ID);
            
            String studentNetId = (String) session.getAttribute(SSOConstants.NET_ID);
            
            log.debug("Log level debug");
            System.out.println("executing");
            //setPerson(ejb.archetypeBusinessMethodGetPerson(studentId));


        } catch (Exception e) {
            
            // hmm  may not need this
            for (StackTraceElement s : e.getStackTrace()) {
                s.toString();
            }
            
            log.error("archetypeaction execute: exception\n {}",e.toString());
        }

        log.info("leaving ArchetypeAction");
        return SUCCESS;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }
    
        public WorkshopBookingSessionBeanLocal getEjb() {
        return ejb;
    }

    public void setEjb(WorkshopBookingSessionBeanLocal ejb) {
        this.ejb = ejb;
    }

    public List<WorkshopInfoForm> getWorkshopBeanList() {
        return workshopBeanList;
    }

    public void setWorkshopBeanList(List<WorkshopInfoForm> workshopBeanList) {
        this.workshopBeanList = workshopBeanList;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

}
