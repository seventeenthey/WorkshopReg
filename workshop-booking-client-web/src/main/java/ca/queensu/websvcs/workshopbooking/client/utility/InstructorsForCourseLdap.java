/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.utility;

import ca.queensu.uis.common.objects.ldap.LDAPConnFactory;
import static ca.queensu.uis.common.objects.ldap.LDAPGateway.ATTR_CN;
import static ca.queensu.uis.common.objects.ldap.LDAPGateway.ATTR_DN;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * <p>InstructorsForCourseLdap class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class InstructorsForCourseLdap {
    
    private    LDAPConnFactoryLocal ldapsubsystem;    
    private static final String[] attributes =  
    {
        "uniqueMember"
    };

    
    private final Logger log = LogManager.getLogger(InstructorsForCourseLdap.class);
    
/**
 * <p>searchCourseForInstructor.</p>
 *
 * @param subject a {@link java.lang.String} object.
 * @param catalogue a {@link java.lang.String} object.
 * @param section a {@link java.lang.String} object.
 * @return a {@link java.util.ArrayList} object.
 */
public ArrayList searchCourseForInstructor(String subject, String catalogue, String section)
{
   
    // the raw list of results from ldap
    ArrayList<String> aResults=new ArrayList();
    
    //the refined list (common name) of instructors
    ArrayList<String> cleanInstructors = new ArrayList<>();

    if(subject == null || catalogue == null || section == null)
    {
        log.error("Incomplete course information has been passed to searchCourseForInstructor");
        log.debug("subject: " + subject + " catalogue: " + catalogue + " section: " + section);
    }
    else
    {
        // build the course code
        String courseCode = subject + "_" + catalogue + "_" + section;


        if (ldapsubsystem==null) 
        {
            log.debug("ldapsubsystem V2: detected as null, we need to create a new one...");
            ldapsubsystem = new LDAPConnFactoryLocal();  // Create LDAP connection  
        }
        else
        {
            log.debug("Already have an LDAPService in place!"); 
        }

        log.debug("Currently Connected to:"+ldapsubsystem.getactiveLiveServer() );
        

        // construct our SearchString
        String searchString = "(cn=*" + courseCode + "*)";
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
              results = conn.search("ou=Instructors,ou=Groups,o=main,dc=queensu,dc=ca", searchString, searchCtls);
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
                            //break;                  // take only the first attribute value
                        }
                    }
                        //print out the results here
                }  // end of while (results !null)
            }
            else
            {
                log.debug( "No results found for searchString:"+searchString);
            } //end of 1st results!=null 

            // we have a list of ugly embedded netids  now go find the names
            if(aResults.size()>0)
            {
                cleanInstructors = extractCommonName(aResults);
            }
        } catch (Exception e)
        {
          log.error( "LDAP Error" + e + " with searchString " + searchString);
        } 
        finally
        {        
            ldapsubsystem.releaseConnection(conn);
        }
    } //end of course code null test
    
    log.debug("Size:"+cleanInstructors.size());

    return cleanInstructors;
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
  private boolean isAttribute (int attribute) 
  {
    if (attribute < ATTR_DN || attribute > ATTR_CN) {return  false;}
    return  true;
  }

    private ArrayList<String> extractCommonName(ArrayList<String> aResults) 
    {
        
        // iterate over list and find the common name
         // ldap = new LDAPQuery(); put new one here
        NameFromUniUid netIdLdap = new NameFromUniUid();
        
         ArrayList<String> commonNameList = new ArrayList<>();
         
         //parse out just the netid out of the cn coming in
         Pattern pat = Pattern.compile("QUEENSUCAUNIUID=([A-Z0-9]+).*");
         
         ArrayList<String> netIdList = new ArrayList<>();
         for(String text: aResults)
         {
            Matcher mat = pat.matcher(text);
        
            if (mat.matches())
            {    
               String found = mat.group(1);
               netIdList.add(found);
               log.trace(found);
            }
         }
         
         //iterate over the list of netIDs and get the common name for each
         //netIdList.forEach((v)->commonNameList.add(netIdLdap.findNameFor(v)));
         
         for(String netId : netIdList){
             commonNameList.add(netIdLdap.findNameFor(netId));
         }
         
         log.debug("finished converting netid's to common names");
         
         return commonNameList;
    }

}
