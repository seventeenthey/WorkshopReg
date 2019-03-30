/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.admin.action;

import ca.queensu.uis.sso.tools.common.SSOConstants;
import ca.queensu.websvcs.workshopbooking.admin.facade.WorkshopBookingSessionBeanLocal;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.websvcs.workshopbooking.core.entity.Roles;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import static java.lang.System.console;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author dwesl
 */
public class AssignRolesAction extends ActionSupport {

    private final Logger log = LogManager.getLogger(ca.queensu.websvcs.workshopbooking.admin.action.AssignRolesAction.class);
     
    private List<Person> allPeople;
    private String netId;
    private String roleName;
    private String department;
    private List<String> allRoles;
    private List<String> allDepartments;
    
    private int listKey;
    private String listValue;
    
    public int getListKey(){
        return listKey;
    }
    
    public void setListKey(int listKey){
        this.listKey = listKey;
    }
    
    public String getListValue(){
        return listValue;
    }
    
    public void setListValue(String listValue){
        this.listValue = listValue;
    }
    
    @EJB(mappedName = "WorkshopBookingSessionBean")
    private WorkshopBookingSessionBeanLocal ejb;
    
    /**
     * <p>Constructor for DashboardAction.</p>
     */
    public AssignRolesAction() {
        
    }

    /**
     * {@inheritDoc}
     *
     * 
     * 
     */
    @Override
    public String execute() throws Exception {
        System.out.println("Execute of AssignRolesAction - Admin");
        
        log.info("Entering execute of AssignRolesAction");
        try {            
            allPeople = ejb.getAllPeople();
            allRoles = ejb.findroleList();
            allDepartments = ejb.findDepartmentList();
        } catch (Exception e) {
            
            // hmm  may not need this
            for (StackTraceElement s : e.getStackTrace()) {
                s.toString();
            }
            
            log.error("AssignRolesAction execute: exception\n {}",e.toString());
        }

        log.info("leaving AssignRolesAction");
        return SUCCESS;
    }
    
    public String updateRole() throws Exception {
        System.out.println("UpdateRole of AssignRolesAction - Admin");
        try {
            Person person = ejb.getPersonByNetId(netId);    //find the person to update
            person.updateRole(roleName, department);          //update the role
            
            allPeople = ejb.getAllPeople();
            allRoles = ejb.findroleList();
            allDepartments = ejb.findDepartmentList();
        } catch (Exception e){
            log.error("AssignRolesAction updateRole: exception\n {}",e.toString());
        }
        
        return SUCCESS;
    }
    
    public List<Person> getAllPeople(){
        return allPeople;
    }
    
    public void setAllPeople(List<Person> allPeople){
        this.allPeople = allPeople;
    }
    
    public String getNetId(){
        return netId;
    }
    
    public void setNetId(String netId){
        this.netId = netId;
    }
    
    public String getDepartment(){
        System.out.println("getDepartment ss" + department);
        return department;
    }
    
    public void setDepartment(String department){
        System.out.println("setDepartment ss" + department);
        this.department = department;
    }
    
    public String getRoleName(){
        return roleName;
    }
    
    public void setRoleName(String roleName){
        this.roleName = roleName;
    }
    
    public List<String> getAllRoles(){
        return allRoles;
    }
    
    public void setAllRoles(List<String> allRoles){
        this.allRoles = allRoles;
    }
    
    public List<String> getAllDepartments(){
        return allDepartments;
    }
    
    public void setAllDepartments(List<String> allDepartments){
        this.allDepartments = allDepartments;
    }
}