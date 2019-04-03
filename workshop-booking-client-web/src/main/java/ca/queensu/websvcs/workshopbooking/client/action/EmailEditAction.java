/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.websvcs.workshopbooking.client.domain.EmailInfoForm;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.ejb.EJB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author sylvi
 */
public class EmailEditAction extends ActionSupport implements Preparable{

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.EmailEditAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    private EmailInfoForm emailForm;
    
    private Integer workshopId;
    private Workshops workshop;


    public EmailEditAction() {
        System.out.println("### EmailEditAction constructor running");
    }
    
    /**
     * Prepare Load Execute Validation
     * @throws Exception 
     */
    @Override
    public void prepare() throws Exception {
        try {
            System.out.println("### FunctionAction prepare running");
        }
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while preparing data for edit screen."));
            log.error("***************Exception occurred in prepare method " + e.getMessage());
            log.error(out);
        }
    }
    
    
    @SkipValidation
    public String load() throws Exception{
        try {
            System.out.println("### EmailEditAction load running");

            if (workshopId != null){
                workshop = ejb.getWorkshopById(workshopId);
            }
        }
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while loading eamil edit screen."));
            log.error("***************Exception occurred in load method " + e.getMessage());
            log.error(out);
            return ERROR;
        }
        return SUCCESS;
    }
    
    
    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### EmailEditAction execute running");
            
            // Check if the emailForm successfully saved or not
            boolean saveSuccessful = ejb.updateEmailForm(workshopId, workshop, emailForm);
            if(saveSuccessful){
                addActionMessage("Email Information Successfully saved");
            }
            else {
                addActionError("Data was not saved.");
            }
            
            workshop = ejb.getWorkshopById(workshopId);
            
        } 
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while granting access to the application. Please contact the Archetype Client for assistance."));
            log.error("***************Exception occurred in execute method " + e.getMessage());
            log.error(out);
            return ERROR;
        }
        return SUCCESS;
    }
    
    
    @Override
    public void validate() {
        try {
            System.out.println("### EmailEdit validate running");
            
            /**
            if(workshop.getEmailNotificationName().isEmpty()){
                addFieldError("notifyFromName", "[Notification Email From Name] is required.");
            }
            
            if(workshop.getEmailConfirmationMsg().isEmpty()){
                addFieldError("confirmMsg", "[Confirmation Message] is required.");
            }
            
            if(workshop.getEmailWaitlistMsg().isEmpty()){
                addFieldError("waitListMsg", "[Wait List Message] is required.");
            }
            
            if(workshop.getEmailCancellationMsg().isEmpty()){
                addFieldError("cancelMsg", "[Cancellation Message] is required.");
            }
            
            if(workshop.getEmailEvaluationMsg().isEmpty()){
                addFieldError("evalMsg", "[Evaluation Message] is required.");
            }
            **/
            
            //Todo: Add validation for [Internal Notification Options]
        } 
        catch (Exception e) { 
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while validating student data."));
            log.error("***************Exception occurred in validate method " + e.getMessage());
            log.error(out);
        }
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
    
    /**
     * Getter and Setter
     * @return 
     */
    public WorkshopBookingSessionBeanLocal getEjb() {
        return ejb;
    }

    public void setEjb(WorkshopBookingSessionBeanLocal ejb) {
        this.ejb = ejb;
    }

    public EmailInfoForm getEmailForm() {
        return emailForm;
    }

    public void setEmailForm(EmailInfoForm emailForm) {
        this.emailForm = emailForm;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public Workshops getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshops workshop) {
        this.workshop = workshop;
    }
    

}