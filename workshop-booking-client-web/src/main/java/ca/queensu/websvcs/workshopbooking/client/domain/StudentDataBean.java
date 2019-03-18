/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author dan
 */
public class StudentDataBean implements Serializable{
    private String studentPk;
    private String firstName;
    private String lastName;
    private String age;
    private String hairColour;
    private String country;
    private boolean organDonor;

    public String getStudentPk() {
        return studentPk;
    }

    public void setStudentPk(String studentPk) {
        this.studentPk = studentPk;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHairColour() {
        return hairColour;
    }

    public void setHairColour(String hairColour) {
        this.hairColour = hairColour;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isOrganDonor() {
        return organDonor;
    }

    public void setOrganDonor(boolean organDonor) {
        this.organDonor = organDonor;
    }
}
