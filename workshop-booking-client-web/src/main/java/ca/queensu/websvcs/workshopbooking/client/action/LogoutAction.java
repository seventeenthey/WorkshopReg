/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.action;

import ca.queensu.uis.common.UISDeployment;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

/**
 * <p>LogoutAction class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class LogoutAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private String logoutURL;

    /**
     * <p>Getter for the field <code>logoutURL</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getLogoutURL()
    {
        return logoutURL;
    }

    /**
     * <p>Setter for the field <code>logoutURL</code>.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setLogoutURL(String value)
    {
        logoutURL = value;
    }
    
    /** {@inheritDoc} */
    @Override
    public String execute() throws Exception
    {
        try
        {
            logoutURL = UISDeployment.createSSOLogoutURL("/apps/eas/student/", null);
            ServletActionContext.getRequest().getSession().invalidate();
            return SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error";
        }
    }
}
