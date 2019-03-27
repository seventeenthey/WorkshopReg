package ca.queensu.websvcs.workshopbooking.admin.action;


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
import ca.queensu.websvcs.workshopbooking.admin.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
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

    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.admin.action.ArchetypeAction.class);
     
    private Person person;
    private Workshops catalogue;
    
    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    /**
     * <p>Constructor for DashboardAction.</p>
     */
    public ArchetypeAction() {
        
    }

/** {@inheritDoc} */
    @Override
    public void prepare() throws Exception{

    } 
    
    /**
     * {@inheritDoc}
     *
     * Grabs student ID and net ID from session 
     * 
     */
    @Override
    public String execute() throws Exception {
        
        log.info("Entering execute of ArchetypeAction");
        
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        try {
            String stuId = (String) session.getAttribute(SSOConstants.EMPL_ID);
            
            String netId = (String) session.getAttribute(SSOConstants.NET_ID);
            
            log.debug("Log level debug");
            
            setPerson(ejb.getPersonByNetId(netId));
            setCatalogue(ejb.findByWorkshopId(1));

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

    /**
     * @return the person
     */
    public Workshops getCatalogue() {
        return catalogue;
    }

    /**
     * @param catalogue the person to set
     */
    public void setCatalogue(Workshops catalogue) {
        this.catalogue = catalogue;
    }
    
}
