package ca.queensu.websvcs.workshopbooking.admin.domain;


/**
 * <p>EmailUtility class.</p>
 *
 * @author CISC-498
* @version $Id: $Id
 */
public class EmailUtility {
    
    private String body;
    
    /**
     * Builds the body of the email to be sent and sets this.body
     * to the string created by this method. 
     *
     * @param name          respondent's name
     */
    public void buildEmail(String name){
        StringBuilder sb = new StringBuilder();
                
        sb.append("Thank you ");
        sb.append(name);
        sb.append(",<br><br>");
        sb.append("Your application has been received");
        
        body = sb.toString();
    }

   
}
