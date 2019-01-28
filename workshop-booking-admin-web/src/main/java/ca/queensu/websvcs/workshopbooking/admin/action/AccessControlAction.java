/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.admin.action;

import ca.queensu.websvcs.workshopbooking.admin.domain.ClientDataBean;
import ca.queensu.uis.sso.tools.common.SSOConstants;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
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
import ca.queensu.websvcs.workshopbooking.admin.facade.WorkshopBookingSessionBeanLocal;

/**
 * <p>AccessControlAction class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class AccessControlAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.admin.action.AccessControlAction.class);

    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;

    private ClientDataBean clientDataBean;
    
    private String dash;
    
    /**
     * {@inheritDoc}
     *
     * Grabs all the relevant  data from the session. 
     */
    @Override
    public String execute() throws Exception {
        
        log.info("Entering access control action");
        
        dash = "";

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        log.trace("about to get a new student databean");
        clientDataBean = new ClientDataBean();

        try {
            String emplId = (String) session.getAttribute(SSOConstants.EMPL_ID);

            if (emplId == null || "".equals(emplId)) {
                log.error("Error occurred while granting access.");
                addActionError("Error occurred while granting access.");
                return "accesserror";
            }

            
            String emplName = (String) session.getAttribute(SSOConstants.COMMON_NAME);
            log.trace("getting COMMON-NAME session attribute: "+emplName);
            clientDataBean.setName(emplName);

            String emplEmail = (String) session.getAttribute(SSOConstants.EMAIL);
            log.trace("getting QUEENSU-MAIL session attribute: "+emplEmail);
            clientDataBean.setEmail(emplEmail);

            String givenName = (String) session.getAttribute(SSOConstants.GIVENNAME);
            log.trace("getting GIVENNAME session attribute: "+givenName);
            clientDataBean.setGivenName(givenName);

            String surname = (String) session.getAttribute(SSOConstants.SN);
            log.trace("getting SURNAME session attribute: "+surname);
            clientDataBean.setSurname(surname);

            String netId = (String) session.getAttribute(SSOConstants.NET_ID);
            log.trace("getting QUEENSU-NETID session attribute: "+netId);
            clientDataBean.setNetId(netId);

            log.debug("value of GIVENNAME coming from request header: "+request.getHeader(SSOConstants.GIVENNAME));

            log.debug(clientDataBean.toString());
            
        } 
        catch (Exception e) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            addActionError(createErrorMessage("Exception occurred while granting access to the application. Please contact the Archetype Client for assistance."));
            log.error("***************Exception occurred in excute method " + e.getMessage());
            return ERROR;
        }
        
        log.info("leaving AccessControlAction");
        
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

    /**
     * <p>Getter for the field <code>clientDataBean</code>.</p>
     *
     * @return a {@link ca.queensu.websvcs.workshopbooking.admin.domain.ClientDataBean} object.
     */
    public ClientDataBean getStdDataBean() {
        return clientDataBean;
    }

    /**
     * <p>Setter for the field <code>clientDataBean</code>.</p>
     *
     * @param stdDataBean a {@link ca.queensu.websvcs.workshopbooking.admin.domain.ClientDataBean} object.
     */
    public void setStdDataBean(ClientDataBean stdDataBean) {
        this.clientDataBean = stdDataBean;
    }

    /**
     * <p>Getter for the field <code>dash</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDash() {
        return dash;
    }

    /**
     * <p>Setter for the field <code>dash</code>.</p>
     *
     * @param dash a {@link java.lang.String} object.
     */
    public void setDash(String dash) {
        this.dash = dash;
    }

    
}
