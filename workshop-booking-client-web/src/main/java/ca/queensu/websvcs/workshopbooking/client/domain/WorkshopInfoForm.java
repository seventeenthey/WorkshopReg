/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 *
 * @author sylvi
 */
public class WorkshopInfoForm implements Serializable{
    private String status;
    private String eventTitle;
    private String location;
    private String teaser;
    private Integer maxParticipant;
    private Integer waitlistLimit;
    private Date rgStDate;    //Registration Start Date & Time
    private Date rgStTime;    
    private Date rgEndDate;   //Registration End Date & Time
    private Date rgEndTime;   
    private String eventStDate; //Event Start Date & Time
    private String eventStTime;
    private int workshopNumber;

    public Date getRgStDate() {
        return rgStDate;
    }

    public void setRgStDate(Date rgStDate) {
        this.rgStDate = rgStDate;
    }

    public Date getRgStTime() {
        return rgStTime;
    }

    public void setRgStTime(Date rgStTime) {
        this.rgStTime = rgStTime;
    }

    public Date getRgEndDate() {
        return rgEndDate;
    }

    public void setRgEndDate(Date rgEndDate) {
        this.rgEndDate = rgEndDate;
    }

    public Date getRgEndTime() {
        return rgEndTime;
    }

    public void setRgEndTime(Date rgEndTime) {
        this.rgEndTime = rgEndTime;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public Integer getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(Integer maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public Integer getWaitlistLimit() {
        return waitlistLimit;
    }

    public void setWaitlistLimit(Integer waitlistLimit) {
        this.waitlistLimit = waitlistLimit;
    }

    public String getEventStDate() {
        return eventStDate;
    }

    public void setEventStDate(String eventStDate) {
        this.eventStDate = eventStDate;
    }

    public String getEventStTime() {
        return eventStTime;
    }

    public void setEventStTime(String eventStTime) {
        this.eventStTime = eventStTime;
    }

    public void setWorkshopNumber(int num){
        workshopNumber = num;
    }
    
    public int getWorkshopNumber(){
        return workshopNumber;
    }



}//end WorkshopInfoBean Class