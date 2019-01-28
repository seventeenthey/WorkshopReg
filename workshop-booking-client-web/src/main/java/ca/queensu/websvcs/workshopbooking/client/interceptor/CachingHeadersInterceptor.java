package ca.queensu.websvcs.workshopbooking.client.interceptor;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;

/**
 * <p>CachingHeadersInterceptor class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class CachingHeadersInterceptor implements Interceptor
{
    //TODO
    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @Override
    public void destroy() 
    {
    }

    /** {@inheritDoc} */
    @Override
    public void init() 
    {
    }

    /** {@inheritDoc} */
    @Override
    public String intercept(ActionInvocation ai) throws Exception 
    {
        final ActionContext context = ai.getInvocationContext();
        
        HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
        
        // Add headers to the response that instruct the browser and any CDNs to NOT cache the response.
        if(response != null)
        {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma","no-cache");
            response.setHeader("Expires","0");
        }
        
        return ai.invoke();
    }
    
}
