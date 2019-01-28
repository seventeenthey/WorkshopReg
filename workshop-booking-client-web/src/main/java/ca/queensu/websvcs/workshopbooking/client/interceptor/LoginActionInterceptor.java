/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.interceptor;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsStatics;

/**
 * <p>LoginActionInterceptor class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class LoginActionInterceptor extends AbstractInterceptor implements Interceptor {

    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.interceptor.LoginActionInterceptor.class);
     
    private static final long serialVersionUID = 1L;

    // for parsing the queensucaacadstructure
    // Delimiters - '|' breaks up different degrees ':' breaks up individual elements in the degree
    private static final String DEGREE_DELIMITER = ";";
    private static final String INTERNAL_DELIMITER = ":";
    
    /** {@inheritDoc} */
    @Override
    public void destroy() {
    }

    /** {@inheritDoc} */
    @Override
    public void init() {
    }

    /** {@inheritDoc} */
    @Override
    public String intercept(ActionInvocation ai) throws Exception {
        ActionContext context = ai.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
        HttpSession session = request.getSession();

        log.info("Entered LoginActionInterceptor");
        
        String emplId = (String) session.getAttribute(SSOConstants.EMPL_ID);
        log.debug("Student Id coming from Session Header: " + emplId);

        // No Student Id, so we return an error.
        if (emplId == null) {
            addActionError(ai, createErrorMessage("You are not authorized to access this application. If you believe this is an error, please contact the Awards Office for assistance."));
           log.error("In LoginActionIntercepter. StudentId is null.");
            return Action.ERROR;
        }

        // Retrieve the previousStudentId. It will be null if this is the first time this interceptor has run for this user.
        // If the previousStudentId does not match the current student id, we invalidate the current session and create a new one.
        // This prevents two different users from sharing a session. This is a security measure to prevent "Session Fixation".
        log.trace("about to do some session fixation stuff");
        String previousStudentId = (String) session.getAttribute("previousStudentId");
        if (previousStudentId != null && !emplId.equals(previousStudentId)) {
            log.info("invalidating session - session fixation prevention");
            session.invalidate();
            session = request.getSession();
            session.setAttribute(SSOConstants.EMPL_ID, emplId);
        }


        // Set the "previousStudentId". This is what is used above to determine if two users are sharing a session.
        session.setAttribute("previousStudentId", emplId);
        
        log.trace("Finished the LoginActionInterceptor");
        return ai.invoke();
    }

    private void addActionError(ActionInvocation invocation, String message) {
        Object action = invocation.getAction();
        if (action instanceof ValidationAware) {
            ((ValidationAware) action).addActionError(message);
        }
    }

    private String createErrorMessage(String customMessage) {
        Date now = new Date();

        String msgAppend = " This error occurred at: " + now.toString() + ". Please note the date and time that this error occurred and take a screenshot of this message. Thank you.";

        return customMessage + msgAppend;
    }

}
