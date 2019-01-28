package ca.queensu.websvcs.workshopbooking.admin.interceptor;


import ca.queensu.uis.sso.tools.common.SSOConstants;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>ClientHeaderFilter class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class ClientHeaderFilter implements Filter {

    protected FilterConfig config;
    protected String filterName;

    /** {@inheritDoc} */
    @Override
    public void doFilter(ServletRequest req,
            ServletResponse res,
            FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        
        String sn = request.getHeader(SSOConstants.SN);
        String gn = request.getHeader(SSOConstants.GIVENNAME);
        session.setAttribute(SSOConstants.SN, sn);
        session.setAttribute(SSOConstants.GIVENNAME, gn);
        
        chain.doFilter(req, res); 
    }

    /** {@inheritDoc} */
    @Override
    public void init(FilterConfig config)
            throws ServletException {
        this.config = config;
        this.filterName = config.getFilterName();
    }

    /** {@inheritDoc} */
    @Override
    public void destroy() {
    }

    /**
     * <p>Getter for the field <code>filterName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFilterName() {
        return filterName;
    }

}
