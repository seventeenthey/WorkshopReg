/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
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
public class ListViewAction extends ActionSupport implements Preparable{
    
    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.ListViewAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    private Person person;
    private List<Workshops> workshopsRegistered;
    private List<Workshops> workshopsHosted;
    private List<Workshops> pastWorkshops;
    
    public ListViewAction() {
        System.out.println("### ListViewLoadAction constructor running");
    }
    
    @Override
    public void prepare() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        
        //set person based on NetID
        String userNetId = (String) session.getAttribute(SSOConstants.NET_ID);
        person = ejb.getPersonByNetId(userNetId);
        
        workshopsRegistered = ejb.getUpcomingWorkshopsByPerson(person);
        workshopsHosted = ejb.getWorkshopsHostedByPerson(person);
        pastWorkshops = ejb.getPastWorkshopsByPerson(person);
    } 
    
    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### ListViewLoadAction execute running");
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
    
    public Person getPerson(){
        return person;
    }
    
    public void setPerson(Person person){
        this.person = person;
    }
    
    public List<Workshops> getWorkshopsRegistered(){
        return workshopsRegistered;
    }
    
    public void setWorkshopsRegistered(List<Workshops> workshops){
        workshopsRegistered = workshops;
    }
    
    public List<Workshops> getWorkshopsHosted(){
        return workshopsHosted;
    }
    
    public void setWorkshopsHosted(List<Workshops> workshops){
        workshopsHosted = workshops;
    }
    
    public List<Workshops> getPastWorkshops(){
        return pastWorkshops;
    }
    
    public void setPastWorkshops(List<Workshops> pastWorkshops){
        this.pastWorkshops = pastWorkshops;
    }
    
    public WorkshopBookingSessionBeanLocal getEjb() {
        return ejb;
    }

    public void setEjb(WorkshopBookingSessionBeanLocal ejb) {
        this.ejb = ejb;
    }
}
