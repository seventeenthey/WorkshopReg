/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.domain;

import java.io.Serializable;
/**
 *
 * @author sylvi
 */
public class EmailInfoForm implements Serializable{
    //Registrant Notification Messages
    private String notifyFromName;  //Notification Email From Name
    private String confirmMsg;  //Confirmation Message
    private String waitListMsg; //Wait List Message
    private String cancelMsg;   //Cancellation Message
    private String evalMsg;     //Evaluation Message
    
    //Internal Notify Options
    private String notifyGroup;
    private String notifyCondition;

    public String getNotifyFromName() {
        return notifyFromName;
    }

    public void setNotifyFromName(String notifyFromName) {
        this.notifyFromName = notifyFromName;
    }

    public String getConfirmMsg() {
        return confirmMsg;
    }

    public void setConfirmMsg(String confirmMsg) {
        this.confirmMsg = confirmMsg;
    }

    public String getWaitListMsg() {
        return waitListMsg;
    }

    public void setWaitListMsg(String waitListMsg) {
        this.waitListMsg = waitListMsg;
    }

    public String getCancelMsg() {
        return cancelMsg;
    }

    public void setCancelMsg(String cancelMsg) {
        this.cancelMsg = cancelMsg;
    }

    public String getEvalMsg() {
        return evalMsg;
    }

    public void setEvalMsg(String evalMsg) {
        this.evalMsg = evalMsg;
    }

    public String getNotifyGroup() {
        return notifyGroup;
    }

    public void setNotifyGroup(String notifyGroup) {
        this.notifyGroup = notifyGroup;
    }

    public String getNotifyCondition() {
        return notifyCondition;
    }

    public void setNotifyCondition(String notifyCondition) {
        this.notifyCondition = notifyCondition;
    }
    
    
}//end WorkshopInfoBean Class