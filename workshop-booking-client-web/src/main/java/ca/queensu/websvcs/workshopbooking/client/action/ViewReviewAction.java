/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import ca.queensu.websvcs.workshopbooking.client.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Attendance;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.websvcs.workshopbooking.core.entity.Reviews;
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
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author dd123
 */
public class ViewReviewAction extends ActionSupport implements Preparable{

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.ViewReviewAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;


    List<Reviews> reviews;
    List<String> reviewsId;
    private String reviewNew;
    private Integer workshopId;
    private Workshops workshop;
    private Person person;
    


    public ViewReviewAction() {
        System.out.println("### ViewReviewAction constructor running");
    }

    @Override
    public void prepare() throws Exception {
        try {
            System.out.println("### ViewReviewAction  prepare running");

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();

            //set person based on NetID
            String userNetId = (String) session.getAttribute(SSOConstants.NET_ID);
            person = ejb.getPersonByNetId(userNetId);
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
            System.out.println("### ViewReviewAction load running");
            
              if (workshopId != null){
                workshop = ejb.findByWorkshopId(workshopId);
                reviews = ejb.getReviews(workshopId);
                
//                reviewsId = ejb.getIdReviews(workshopId);
//                String userId = person.getNetId();
//                addActionMessage("ReviewIdEquals "+reviewsId + " " + userId);
                
              }
        }
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while loading view review screen."));
            log.error("***************Exception occurred in load method " + e.getMessage());
            log.error(out);
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### ViewReviewAction execute running");
            System.out.println("################################"+reviewNew);
            Boolean saveSuccessful = ejb.addReview(workshopId, person.getNetId(), reviewNew);
            reviews = ejb.getReviews(workshopId);
            
            if(saveSuccessful){
                addActionMessage("Attendance Successfully saved");
            }
            else {
                addActionError("Attendance was not saved.");
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
            reviews = ejb.getReviews(workshopId);
            reviewsId = ejb.getIdReviews(workshopId);
            if (reviewsId.contains(person.getNetId())){
                addFieldError("userId","Cannot Add Review to Same Workshop Twice.");
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

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getReviewNew() {
        return reviewNew;
    }

    public void setReviewNew(String reviewNew) {
        this.reviewNew = reviewNew;
    }

    public List<String> getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(List<String> reviewsId) {
        this.reviewsId = reviewsId;
    }
    
    
    
}
