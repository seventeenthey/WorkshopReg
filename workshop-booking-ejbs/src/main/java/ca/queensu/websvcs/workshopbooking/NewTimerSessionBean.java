/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.ejb;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author CISC-498
 */
@Stateless
public class NewTimerSessionBean {

    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "9-17", dayOfMonth = "*", year = "*", minute = "*", second = "0", persistent = false)
    
    public void myTimer() {
        System.out.println("Timer event: " + new Date());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
