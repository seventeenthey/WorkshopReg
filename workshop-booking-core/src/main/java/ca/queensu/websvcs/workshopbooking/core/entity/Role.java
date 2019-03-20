/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.core.entity;

/**
 *
 * @author dwesl
 */
public class Role {
    private int role;
    private String department;
    
    public Role(){}
    
    public Role(int role, String department){
        this.role = role;
        this.department = department;
    }
    
    public int getRole(){
        return role;
    }
    
    public void setRole(int role){
        this.role = role;
    }
    
    public String getDepartment(){
        return department;
    }
    
    public void setDepartment(String department){
        this.department = department;
    }
}
