/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.ejb.EJB;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author dwesl
 */
public class FunctionAction extends ActionSupport implements Preparable{

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.FunctionAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    private WorkshopInfoForm workshopForm;
    
    // This list populates the radio buttons for workshop status
    List<String> statusList;
    List<String> locationList;

    public FunctionAction() {
        System.out.println("### FunctionAction constructor running");
    }

    @Override
    public void prepare() throws Exception {
        try {
            System.out.println("### FunctionAction prepare running");
            statusList = ejb.findstatusList();
            locationList = ejb.findlocationList();
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
            System.out.println("### FunctionAction load running");
        } 
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while loading workshop function screen."));
            log.error("***************Exception occurred in load method " + e.getMessage());
            log.error(out);
            return ERROR;
        }
        return SUCCESS;
    }
    
    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### FunctionAction execute running");
            
            // Check if the workshopInfoForm successfully saved or not
            boolean saveSuccessful = ejb.updateWorkshopForm(workshopForm);
            if(saveSuccessful){
                addActionMessage("Workshop Information Successfully saved");
            }
            else {
                addActionError("Data was not saved.");
            }
        } 
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));          
            addActionError(createErrorMessage("Exception occurred while granting access to the application. Please contact the Archetype Client for assistance."));
            log.error("***************Exception occurred in execute method " + e.getMessage());
            log.error(out);
            return ERROR;
        }//end try/catch
        return SUCCESS;
    }
    
    
    @Override
    public void validate() {
        try {
            
            System.out.println("### StudentEditAction validate running");
            
            if(workshopForm.getStatus().isEmpty()) {
                addFieldError("status", "Status is required.");
            }
            
            if(workshopForm.getEventTitle().isEmpty()) {
                addFieldError("eventTitle", "Event Title is required.");
            }else if(workshopForm.getEventTitle().length() > 30){
                addFieldError("eventTitle", "Event Title cannot exceed 30 characters.");
            }
            
            if(workshopForm.getTeaser().isEmpty()) {
                addFieldError("teaser", "Workshop Teaser is required.");
            }
            
            if (workshopForm.getMaxParticipant() == null){
                addFieldError("maxParticipant", "Maximun Participant is required.");
            }else if(workshopForm.getMaxParticipant()>300) {
                addFieldError("maxParticipant", "Exceed maximun Participant (Should no more than 300).");
            }else if(workshopForm.getMaxParticipant()<10){
                addFieldError("maxParticipant","Less than minimun Participant (Should no less than 10).");
            }
            
            if (workshopForm.getWaitlistLimit() == null){
                addFieldError("waitlistLimit", "Wait List Limit is required.");
            }else if(workshopForm.getWaitlistLimit()>300) {
                addFieldError("waitlistLimit", "Exceed maximun Wait List Limit (Should no more than 300).");
            } 
            
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
    

    public WorkshopBookingSessionBeanLocal getEjb() {
        return ejb;
    }

    public void setEjb(WorkshopBookingSessionBeanLocal ejb) {
        this.ejb = ejb;
    }

    public WorkshopInfoForm getWorkshopForm() {
        return workshopForm;
    }

    public void setWorkshopForm(WorkshopInfoForm workshopForm) {
        this.workshopForm = workshopForm;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
    
    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
        this.locationList = locationList;
    }
    
}
