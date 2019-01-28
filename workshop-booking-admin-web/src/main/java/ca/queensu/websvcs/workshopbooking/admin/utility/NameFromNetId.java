/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.admin.utility;

import ca.queensu.uis.common.objects.ldap.LDAPConnFactory;
import static ca.queensu.uis.common.objects.ldap.LDAPGateway.ATTR_CN;
import static ca.queensu.uis.common.objects.ldap.LDAPGateway.ATTR_DN;
import static ca.queensu.uis.common.objects.ldap.LDAPGateway.attributes;
import java.util.ArrayList;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>NameFromNetId class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class NameFromNetId {
    
    private    LDAPConnFactory ldapsubsystem;    
    private static final String[] attributes =  
    {
        "cn"
    };

    
    private final Logger log = LogManager.getLogger(NameFromNetId.class);
    
/**
 * <p>findNameFor.</p>
 *
 * @param netId a {@link java.lang.String} object.
 * @return a {@link java.lang.String} object.
 */
public String findNameFor(String netId)
{
   
    ArrayList<String> aResults=new ArrayList();


    if (ldapsubsystem==null) 
    {
        log.debug("ldapsubsystem V2: detected as null, we need to create a new one...");
        ldapsubsystem = new LDAPConnFactory();  // Create LDAP connection  
    }
    else
    {
        log.debug("Already have an LDAPService in place!"); 
    }
    
    log.debug("Currently Connected to:"+ldapsubsystem.getactiveLiveServer() );
    if ( null!=netId && !netId.isEmpty()  ) 
    {

        // construct our SearchString

        String searchString = "(uid=" +netId +")";
        log.debug( "Search String: " + searchString.toString());

        DirContext conn = null;
        NamingEnumeration results = null;

        conn = ldapsubsystem.getConnection();
        // set up search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(attributes);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); 
        // do lookup
        try 
        {
                /**
                * Danger Will Robinson!:  This will not always return a null set...we need to check if it is empty for validity!
                */

              //basedn = ou=Class Enrollments,ou=Groups,o=main,dc=queensu,dc=ca
              //search string skeleton:(uniqueMember=queensucauniuid=13ajl6,ou=people,o=main,dc=queensu,dc=ca)
              results = conn.search("ou=People,o=main,dc=queensu,dc=ca", searchString, searchCtls);
            if (results != null) {
                while (results.hasMore()) 
                { 
                    NamingEnumeration values = null;
                    String aValue = "";
                    
                    // we check for more results to process...
                    SearchResult entry = (SearchResult)results.next();
                    Attributes attrs = entry.getAttributes();
                    Attribute attrib = attrs.get(attributes[0]);
                    if (attrib != null) 
                    {
                        for (values = attrib.getAll(); values.hasMoreElements();) 
                        {
                            aResults.add(values.nextElement().toString());
                            break;                  // take only the first attribute value
                        }
                    }
                        //print out the results here
                }  // end of while (results !null)
            }
            else
            {
                log.debug( "No results found for searchString:"+searchString);
            } //end of 1st results!=null 

        } catch (Exception e)
        {
          log.error( "LDAP Error" + e + " with searchString " + searchString);
        } 
        finally
        {        
            ldapsubsystem.releaseConnection(conn);
        }
    } // end of userid and password null empty check
    else 
    {
        log.debug( "Invalid blank netId in method call returning empty arrayList!");
    }

    log.debug("Size:"+aResults.size());

    return aResults.get(0);
  }
/**
   * <p>Return a single value of an attribute from possibly multiple values,
   * grossly ignoring anything else.  If there are no values, then
   * return an empty string.</p>
   *
   * @param results LDAP query results
   * @param attribute LDAP attribute we are interested in
   * @return a single value of the attribute
   */
  private String getAttributeValue (Attributes attrs, int attribute) throws NamingException {
    NamingEnumeration values = null;
    String aValue = "";
    if (!isAttribute(attribute))
      return  aValue;
    Attribute attrib = attrs.get(attributes[attribute]);
    if (attrib != null) {
      for (values = attrib.getAll(); values.hasMoreElements();) {
        aValue = (String)values.nextElement();
        break;                  // take only the first attribute value
      }
    }
    return  aValue;
  }
    
    /**
   *  Determines if one of the attributes ordinals is actually valid.  This prevents someone from attempting to overload the class with invalid calls
   *  to something they shouldn't get (e.g. overflow the call to ask for something else)
   * @see #aaa
   * @param paramName comments
   * @exception XxxxxxException if ...
   * @return comments
   */
  private boolean isAttribute (int attribute) {
    if (attribute < ATTR_DN || attribute > ATTR_CN) {return  false;}
    return  true;
  }

}
