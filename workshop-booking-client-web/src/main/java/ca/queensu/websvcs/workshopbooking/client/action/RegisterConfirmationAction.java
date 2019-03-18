/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dwesl
 */
public class RegisterConfirmationAction extends ActionSupport{
    
    private static final long serialVersionUID = 1L;
    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.client.action.RegisterConfirmationAction.class);

    public RegisterConfirmationAction() {
        System.out.println("### RegisterConfirmationAction constructor running");
    }
    
    @Override
    public String execute() throws Exception {
        try {
            System.out.println("### RegisterConfirmationAction execute running");
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
}
