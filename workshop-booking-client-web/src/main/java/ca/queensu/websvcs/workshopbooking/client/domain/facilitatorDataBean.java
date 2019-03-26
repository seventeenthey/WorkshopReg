/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.domain;

/**
 *
 * @author dd123
 */
public class facilitatorDataBean {
    
    private String facilID;
    private String facilFirstName;
    private String facilLastName;

    public String getFacilID() {
        return facilID;
    }

    public void setFacilID(String facilID) {
        this.facilID = facilID;
    }

    public String getFacilFirstName() {
        return facilFirstName;
    }

    public void setFacilFirstName(String facilFirstName) {
        this.facilFirstName = facilFirstName;
    }

    public String getFacilLastName() {
        return facilLastName;
    }

    public void setFacilLastName(String facilLastName) {
        this.facilLastName = facilLastName;
    }
    
}
