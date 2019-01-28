/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.domain;

import java.io.Serializable;

/**
 * <p>RequestDataBean class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class RequestDataBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String courseSection;
    private String courseTxt;
    private String instructor;
    private String instructorTxt;
    private String dates;
    
    /**
     * <p>Constructor for RequestDataBean.</p>
     */
    public RequestDataBean() {
        
    }
    
    /**
     * <p>Getter for the field <code>courseSection</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCourseSection() {
        return courseSection;
    }

    /**
     * <p>Setter for the field <code>courseSection</code>.</p>
     *
     * @param courseSection a {@link java.lang.String} object.
     */
    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }

    /**
     * <p>Getter for the field <code>courseTxt</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCourseTxt() {
        return courseTxt;
    }

    /**
     * <p>Setter for the field <code>courseTxt</code>.</p>
     *
     * @param courseTxt a {@link java.lang.String} object.
     */
    public void setCourseTxt(String courseTxt) {
        this.courseTxt = courseTxt;
    }

    /**
     * <p>Getter for the field <code>instructor</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * <p>Setter for the field <code>instructor</code>.</p>
     *
     * @param instructor a {@link java.lang.String} object.
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * <p>Getter for the field <code>dates</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDates() {
        return dates;
    }

    /**
     * <p>Setter for the field <code>dates</code>.</p>
     *
     * @param dates a {@link java.lang.String} object.
     */
    public void setDates(String dates) {
        this.dates = dates;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "RequestDataBean{" + "courseSection=" + courseSection + ", courseTxt=" + courseTxt + ", instructor=" + instructor + ", dates=" + dates + '}';
    }

    /**
     * <p>Getter for the field <code>instructorTxt</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getInstructorTxt() {
        return instructorTxt;
    }

    /**
     * <p>Setter for the field <code>instructorTxt</code>.</p>
     *
     * @param instructorTxt a {@link java.lang.String} object.
     */
    public void setInstructorTxt(String instructorTxt) {
        this.instructorTxt = instructorTxt;
    }
    
    
    
}
