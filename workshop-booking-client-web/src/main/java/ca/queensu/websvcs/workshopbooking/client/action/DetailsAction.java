/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.client.domain.facilitatorDataBean;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
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
public class DetailsAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.DetailsAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;

    private String workshopId;
    private Workshops workshop;
    private Person person;

    private Integer creatorAuth;
    private Integer facilAuth;
    private String registeredStatus;

    public DetailsAction() {
        System.out.println("### DetailsAction constructor running");
    }

    @Override
    public void prepare() throws Exception {
        System.out.println("### DetailsAction Prepare running");

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        //set person based on NetID
        String userNetId = (String) session.getAttribute(SSOConstants.NET_ID);
        person = ejb.getPersonByNetId(userNetId);
    }

    public String load() throws Exception{
        try {
            System.out.println("### DetailsAction execute running");

            workshop = ejb.findByWorkshopId(workshopId);
            findRegisteredStatus();
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

    public String unregister() throws Exception {
        try {
            System.out.println("### DetailsAction unregister running");

            workshop = ejb.findByWorkshopId(workshopId);
            System.out.println(workshop.getWorkshopId());
            ejb.removeParticipant(workshop.getWorkshopId(), person.getNetId());
            findRegisteredStatus();
        }
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while loading student edit screen."));
            log.error("***************Exception occurred in load method " + e.getMessage());
            log.error(out);
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### DetailsAction execute running");
            System.out.println("");
            System.out.println(workshopId);

            workshop = ejb.findByWorkshopId(workshopId);

            String creatorId = workshop.getWorkshopCreatorId().getNetId();
            System.out.println("### CreatorID for the Workshop "+creatorId);
            String userId = person.getNetId();
            System.out.println("### UserofSystem for the Workshop "+userId);
            List<String> facilNetIdList = ejb.findFacilitatorNetidList(Integer.parseInt(workshopId));
            System.out.println("### FacilitatorNetIds for the Workshop "+facilNetIdList);

            if (userId.equals(creatorId)){
                creatorAuth = 1;
            }else{
                creatorAuth = 0;
            }

            if (facilNetIdList.contains(userId)){
                facilAuth = 1;
            }else{
                facilAuth = 0;
            }
            System.out.println("### FacilAuth for the Workshop "+facilAuth);
            findRegisteredStatus();
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

    public Workshops getWorkshop(){
        return workshop;
    }

    public void setWorkshop(Workshops workshop){
        this.workshop = workshop;
    }

    public String getWorkshopId(){
        return workshopId;
    }

    public void setWorkshopId(String workshopId){
        this.workshopId = workshopId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public Integer getCreatorAuth() {
        return creatorAuth;
    }

    public void setCreatorAuth(Integer creatorAuth) {
        this.creatorAuth = creatorAuth;
    }

    public Integer getFacilAuth() {
        return facilAuth;
    }

    public void setFacilAuth(Integer facilAuth) {
        this.facilAuth = facilAuth;
    }


    public String getRegisteredStatus() {
        return registeredStatus;
    }

    public void setRegisteredStatus(String registeredStatus) {
        this.registeredStatus = registeredStatus;
    }

    public void findRegisteredStatus(){
        if(ejb.isOnWaitlist(Integer.valueOf(workshopId), person.getNetId()))
            registeredStatus = "WaitListed";
        else if (ejb.isRegistered(Integer.valueOf(workshopId), person.getNetId()))
            registeredStatus = "Registered";
        else
            registeredStatus = "Not Registered";
    }
}
