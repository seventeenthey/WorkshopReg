/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.websvcs.workshopbooking.client.domain.facilitatorDataBean;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author sylvi
 */
public class FacilitatorAction extends ActionSupport implements Preparable{

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.FacilitatorAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;

    List<facilitatorDataBean> facilBeanList;
    private String addFacilId;
    private String delFacilId;  // Facilitator ID will be deleted
    private Integer workshopId;


    public FacilitatorAction() {
        System.out.println("### FacilitatorAction constructor running");
    }

    @Override
    public void prepare() throws Exception {
        try {
            System.out.println("### FacilitatorAction prepare running");
//            addActionMessage(workshopId.toString());

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
            System.out.println("### FacilitatorAction load running");
            //addActionMessage("Test Input"+workshopId);
            System.out.println("hello13");
            if (workshopId != null){
                facilBeanList = ejb.findFacilitatorList(workshopId);
            }
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
            System.out.println("### FacilitatorAction execute running");
            System.out.println("hello14");
            if (workshopId != null){
                boolean success = ejb.addFaciliator(workshopId, newFacilId);
                facilBeanList = ejb.findFacilitatorList(workshopId);
            }
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

            System.out.println("### StudentEditAction validate running");
            if (workshopId == null){
                addFieldError("addFacil","Unable to modify facilitator.");
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


    public List<facilitatorDataBean> getFacilBeanList() {
        return facilBeanList;
    }

    public void setFacilBeanList(List<facilitatorDataBean> facilBeanList) {
        this.facilBeanList = facilBeanList;
    }

    public WorkshopBookingSessionBeanLocal getEjb() {
        return ejb;
    }

    public void setEjb(WorkshopBookingSessionBeanLocal ejb) {
        this.ejb = ejb;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public String getAddFacilId() {
        return addFacilId;
    }

    public void setAddFacilId(String addFacilId) {
        this.addFacilId = addFacilId;
    }

    public String getDelFacilId() {
        return delFacilId;
    }

    public void setDelFacilId(String delFacilId) {
        this.delFacilId = delFacilId;
    }



}
