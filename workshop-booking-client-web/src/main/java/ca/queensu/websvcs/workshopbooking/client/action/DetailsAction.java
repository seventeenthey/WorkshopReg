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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author dwesl
 */
public class DetailsAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.DetailsAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;

    private String workshopNumber;
    private WorkshopInfoForm workshop;

    public DetailsAction() {
        System.out.println("### DetailsAction constructor running");
    }

    @Override
    public void prepare() throws Exception {
        System.out.println("### DetailsAction Prepare running");
        System.out.println("wkrshop # : " + workshopNumber);
        //workshop = ejb.findWorkshopByNum(workshopNumber);
    }

    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### DetailsAction execute running");

            System.out.println("### DetailsAction load running");
            System.out.println(workshopNumber);
            workshop = ejb.findWorkshopByNum(workshopNumber);
            System.out.println(workshop.getEventTitle());
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
            System.out.println("### DetailsAction load running");
            System.out.println(workshopNumber);
            workshop = ejb.findWorkshopByNum(workshopNumber);
            System.out.println(workshop.getEventTitle());
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

    public WorkshopInfoForm getWorkshop(){
        return workshop;
    }

    public void setWorkshop(WorkshopInfoForm workshop){
        this.workshop = workshop;
    }

    public String getWorkshopNumber(){
        return workshopNumber;
    }

    public void setWorkshopNumber(String workshopNumber){
        this.workshopNumber = workshopNumber;
    }
}
