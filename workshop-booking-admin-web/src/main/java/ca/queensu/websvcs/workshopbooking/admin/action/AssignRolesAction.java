/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.admin.action;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import ca.queensu.websvcs.workshopbooking.admin.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author dwesl
 */
public class AssignRolesAction extends ActionSupport {

    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.admin.action.AssignRolesAction.class);
     
    private Person person;
    private List<Person> allPeople;
    private String searchKey;
    
    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    /**
     * <p>Constructor for DashboardAction.</p>
     */
    public AssignRolesAction() {
        
    }

    /**
     * {@inheritDoc}
     *
     * 
     * 
     */
    @Override
    public String execute() throws Exception {
        
        log.info("Entering execute of AssignRolesAction");
        try {            
            log.info("Executing AssignRolesAction");            
        } catch (Exception e) {
            
            // hmm  may not need this
            for (StackTraceElement s : e.getStackTrace()) {
                s.toString();
            }
            
            log.error("AssignRolesAction execute: exception\n {}",e.toString());
        }

        log.info("leaving AssignRolesAction");
        return SUCCESS;
    }
}