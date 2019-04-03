/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author dwesl
 */
public class QuestionnaireAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.QuestionnaireAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    private Integer workshopId;
    private String netId;
    private Person person;

    public QuestionnaireAction() {
        System.out.println("### QuestionnaireAction constructor running");
    }

    /**
     * Prepare Load Execute Validation
     *
     * @return
     * @throws Exception
     */
    @Override
    public void prepare() throws Exception {
        try {
            System.out.println("### QuestionnaireAction prepare running");

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            
            //get user's netIid
            netId = (String) session.getAttribute(SSOConstants.NET_ID);
            person = ejb.getPersonByNetId(netId);
        } catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while preparing data for edit screen."));
            log.error("***************Exception occurred in prepare method " + e.getMessage());
            log.error(out);
        }
    }

    @SkipValidation
    public String load() throws Exception {
        try {
            System.out.println("### QuestionnaireAction load running");

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            
            netId = (String) session.getAttribute(SSOConstants.NET_ID);
            
        } catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while loading register questionaire screen."));
            log.error("***************Exception occurred in load method " + e.getMessage());
            log.error(out);
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### QuestionnaireAction execute running");
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            netId = (String) session.getAttribute(SSOConstants.NET_ID);
            
            // Check if the attendee successful registered in workshop or not
            boolean saveSuccessful = ejb.addWorkshopRegistrant(workshopId, netId);
            if (saveSuccessful) {
                addActionMessage("Successfully registered in workshop");
            } else {
                addActionError("Data was not saved.");
            }
            System.out.println("### QuestionnaireAction execute running");
        } catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while granting access to the application. Please contact the Archetype Client for assistance."));
            log.error("***************Exception occurred in execute method " + e.getMessage());
            log.error(out);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Creates a custom error message to be used as an action error
     *
     * @param customMessage message to be used as the action error text
     * @return the created error message
     */
    private String createErrorMessage(String customMessage) {
        Date now = new Date();

        String msgAppend = " This error occurred at: " + now.toString() + ". Please note the date and time that this error occurred and take a screenshot of this message. Thank you.";

        return customMessage + msgAppend;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public WorkshopBookingSessionBeanLocal getEjb() {
        return ejb;
    }

    public Person getPerson() {
        return person;
    }

    public void setEjb(WorkshopBookingSessionBeanLocal ejb) {
        this.ejb = ejb;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
