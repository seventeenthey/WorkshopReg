/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.EventStatus;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.ejb.EJB;
import java.util.List;
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
public class FunctionAction extends ActionSupport implements Preparable{

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.FunctionAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;

    private WorkshopInfoForm workshopForm;

    // This list populates the radio buttons for workshop status
    private List<String> statusList;
    private List<String> locationList;

    private Integer workshopId;
    private Workshops workshop;

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

            if (workshopId != null){
                workshop = ejb.findByWorkshopId(workshopId);
//                rgStDate = workshop.getRegistrationStart();
//                System.out.println("TestRgStDateInput "+rgStDate.toString());
//                workshopForm.setEventTitle(workshop.getTitle());
//                addActionMessage("Event Title " + workshop.getTitle());
            }
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
    
//    public String populate(){
//        
//    }

    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### FunctionAction execute running");

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            
//            Todo: Still in testing; need implement
//            String facilitatorId = (String) session.getAttribute(SSOConstants.NET_ID);
//            workshopForm.setFacilitatorId(facilitatorId);

            // Check if the workshopInfoForm successfully saved or not
            boolean saveSuccessful = ejb.updateWorkshopForm(workshop,workshopForm);
            //workshop
//            boolean saveSuccessful = true;
            if(saveSuccessful){
                addActionMessage("Workshop Information Successfully saved");
//                EventStatus status = workshop.getEventStatus();
//                addActionMessage("WorkshopStatusTest" + status.getEventStatus());
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

//            if(workshop.getEventStatus().getEventStatus().isEmpty()) {
//                addFieldError("status", "Status is required.");
//            }

            if(workshop.getTitle().isEmpty()) {
                addFieldError("eventTitle", "Event Title is required.");
            }else if(workshop.getTitle().length() > 30){
                addFieldError("eventTitle", "Event Title cannot exceed 30 characters.");
            }

            if(workshop.getDetails().isEmpty()) {
                addFieldError("teaser", "Workshop Teaser is required.");
            }

            if (workshop.getMaxParticipants() == null){
                addFieldError("maxParticipant", "Maximun Participant is required.");
            }else if(workshop.getMaxParticipants()>300) {
                addFieldError("maxParticipant", "Exceed maximun Participant (Should no more than 300).");
            }else if(workshop.getMaxParticipants()<10){
                addFieldError("maxParticipant","Less than minimun Participant (Should no less than 10).");
            }

            if (workshop.getWaitlistLimit() == null){
                addFieldError("waitlistLimit", "Wait List Limit is required.");
            }else if(workshop.getWaitlistLimit()>300) {
                addFieldError("waitlistLimit", "Exceed maximun Wait List Limit (Should no more than 300).");
            }else if(workshop.getWaitlistLimit()>workshop.getMaxParticipants()) {
                addFieldError("waitlistLimit", "Wait List Limit cannot exceed Maximun Participants number.");
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
