package ca.queensu.websvcs.workshopbooking.client.facade;

import ca.queensu.websvcs.workshopbooking.client.domain.WorkshopInfoForm;
import ca.queensu.websvcs.workshopbooking.core.entity.Person;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import ca.queensu.websvcs.workshopbooking.client.domain.EmailInfoForm;
import ca.queensu.websvcs.workshopbooking.client.domain.facilitatorDataBean;
import ca.queensu.websvcs.workshopbooking.core.entity.Attendance;
import ca.queensu.websvcs.workshopbooking.core.entity.Departments;
import ca.queensu.websvcs.workshopbooking.core.entity.EventStatus;
import ca.queensu.websvcs.workshopbooking.core.entity.Workshops;
import ca.queensu.websvcs.workshopbooking.core.entity.Locations;
import ca.queensu.websvcs.workshopbooking.core.entity.Reviews;
import ca.queensu.websvcs.workshopbooking.core.entity.Roles;
import ca.queensu.websvcs.workshopbooking.core.entity.Waitlist;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.transaction.Transactional;

/**
 * <p>WorkshopBookingSessionBean class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
@Stateless(mappedName = "WorkshopBookingSessionBean")
@Local
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkshopBookingSessionBean implements WorkshopBookingSessionBeanLocal {

    private final Logger log = LogManager.getLogger(WorkshopBookingSessionBean.class);

    @PersistenceContext(unitName = "WorkshopBooking-WebPU")
    private EntityManager em;

    @Inject
    QueensEmailInterface emailStub;// = new CaQueensuUisWebservicesEmailStub();

    /**
     * @return a list of all possible statuses for a workshop
     */
    @Override
    public List<String> getStatusList() {
        List<String> statusList = new ArrayList<>();
        for (EventStatus eventStatus: em.createNamedQuery("EventStatus.findAll", EventStatus.class).getResultList()) {
            statusList.add(eventStatus.getEventStatus());
        }
        return statusList;
    }

    /**
     * @return a list of locations on Queen's campus
     */
    @Override
    public List<String> getLocationList(){
        List<String> locationList = new ArrayList<>();
        for (Locations location: em.createNamedQuery("Locations.findAll", Locations.class).getResultList()) {
            locationList.add(location.getLocationName());
        }
        return locationList;
    }
    
    /**
     * @return a list of all Queen's departments
     */
    @Override
    public List<String> getDepartmentList() {
        List<String> departmentList = new ArrayList<>();
        for (Departments department: em.createNamedQuery("Departments.findAll", Departments.class).getResultList()) {
            departmentList.add(department.getDepartmentName());
        }
        return departmentList;
    }
    
    /**
     * @return the list of all possible user roles
     */
    @Override
    public List<String> getRoleList(){
        List<String> roleList = new ArrayList<>();
        for (Roles role: em.createNamedQuery("Roles.findAll", Roles.class).getResultList()) {
            roleList.add(role.getRoleName());
        }
        return roleList;
    }
    
    /**
     * @return a list of all workshops
     */
    @Override
    public List<Workshops> getWorkshopsList() {
        try {
            List<Workshops> workshopList = em.createNamedQuery("Workshops.findAll", Workshops.class).getResultList();
            return workshopList;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

    /**
     * @param workshopId
     * @return all registrants for a workshop
     */
    @Override
    @Transactional
    public List<Person> getWorkshopRegistrants(Integer workshopId) {
        // Get all participants in a workshop
        Workshops w = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", workshopId).getSingleResult();
        return w.getMyRegistrants();
    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a user was added to a workshop, wait list, or not
     * 
     * Registers a user into a workshop. If full, add them to a wait list (returns true for both of these cases)
     * If the wait list is full, return false
     */
    @Override
    @Transactional
    public boolean addWorkshopRegistrant(Integer workshopId, String netId) {
        Workshops workshop = WorkshopBookingSessionBean.this.getWorkshopById(workshopId);
        Person p = getPersonByNetId(netId);
        if (getWorkshopRegistrants(workshopId).size() < workshop.getMaxParticipants()) {
            System.out.println("registered!");
            workshop.addRegistrant(p);
            addAttendee(workshopId, netId);
        } else if (getWaitlist(workshopId).size() < workshop.getWaitlistLimit()) { // add to waitlist if it's not full
            System.out.println("added to waitlist!");
            addToWaitlist(workshopId, netId);
        } else { // waitlist full!
            System.out.println("waitlist full...");
            return false;
        }
        return true;
    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a user was removed or not
     * 
     * Removes a user's registration for a workshop
     */
    @Override
    @Transactional
    public boolean removeWorkshopRegistrant(Integer workshopId, String netId) {
        Workshops workshop = WorkshopBookingSessionBean.this.getWorkshopById(workshopId);
        Person p = getPersonByNetId(netId);
        workshop.removeRegistrant(p);
        removeAttendee(workshopId, netId);

        List<Waitlist> waitlist = getWaitlist(workshopId);
        if (waitlist.size() > 0) {
            waitlist.sort(Comparator.comparing(Waitlist::getDatetimeApplied));
            String nextPersonId = waitlist.get(0).getPerson().getNetId();
            removeFromWaitlist(workshopId, nextPersonId);
            addWorkshopRegistrant(workshopId, nextPersonId);
        }
        return true;
    }

    /**
     * @param creator
     * @param workshop
     * @param workshopForm
     * @return boolean: whether a workshop was created or not
     * 
     * Creates a new workshop
     */
    @Override
    @Transactional
    public boolean createWorkshop(Person creator, Workshops workshop, WorkshopInfoForm workshopForm) {
        workshop.setWorkshopCreatorId(creator);
        workshop.setDepartmentId(creator.getDepartmentId());
        workshop.setEmailNotificationName("");
        workshop.setEmailConfirmationMsg("");
        workshop.setEmailWaitlistMsg("");
        workshop.setEmailCancellationMsg("");
        workshop.setEmailEvaluationMsg("");
        if (workshopForm.getLocation() != null && !workshopForm.getLocation().isEmpty()) {
            workshop.setLocation(workshopForm.getLocation());
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            workshop.setRegistrationStart(formatter.parse(workshopForm.getRgStDateTime()));
            workshop.setRegistrationEnd(formatter.parse(workshopForm.getRgEndDateTime()));
            workshop.setEventStart(formatter.parse(workshopForm.getEventStDateTime()));
            workshop.setEventEnd(formatter.parse(workshopForm.getEventEndDateTime()));
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            throw new EJBException(e);
        }

        em.persist(workshop);
        return true;
    }

    /**
     * @param workshopId
     * @param workshop
     * @param workshopForm
     * @return boolean: whether a workshop was updated or not
     * 
     * Changes a workshop's details
     */
    @Override
    @Transactional
    public boolean updateWorkshop(Integer workshopId, Workshops workshop, WorkshopInfoForm workshopForm) {
        Workshops oldWorkshop = getWorkshopById(workshopId);
        workshop.setWorkshopId(workshopId);
        workshop.setWorkshopCreatorId(oldWorkshop.getWorkshopCreatorId());
        workshop.setDepartmentId(oldWorkshop.getDepartmentId());
        workshop.setEmailNotificationName(oldWorkshop.getEmailNotificationName());
        workshop.setEmailConfirmationMsg(oldWorkshop.getEmailConfirmationMsg());
        workshop.setEmailWaitlistMsg(oldWorkshop.getEmailWaitlistMsg());
        workshop.setEmailCancellationMsg(oldWorkshop.getEmailCancellationMsg());
        workshop.setEmailEvaluationMsg(oldWorkshop.getEmailEvaluationMsg());
        if (workshopForm.getLocation() != null && !workshopForm.getLocation().isEmpty()) {
            workshop.setLocation(workshopForm.getLocation());
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            workshop.setRegistrationStart(formatter.parse(workshopForm.getRgStDateTime()));
            workshop.setRegistrationEnd(formatter.parse(workshopForm.getRgEndDateTime()));
            workshop.setEventStart(formatter.parse(workshopForm.getEventStDateTime()));
            workshop.setEventEnd(formatter.parse(workshopForm.getEventEndDateTime()));
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            throw new EJBException(e);
        }

        em.merge(workshop);
        return true;
    }
    
    /**
     * @param workshopId
     * @param copyStrategy
     * @return boolean: whether a workshop was copied or not
     * 
     * Copies a workshop's details and makes a new one with a different workshopID
     */
    @Override
    @Transactional
    public Integer copyWorkshop(Integer workshopId, Integer copyStrategy) {
        Workshops toCopy = getWorkshopById(workshopId);
        em.detach(toCopy);
        toCopy.setWorkshopId(null);
        EventStatus newStatus = new EventStatus("Not Posted");
        toCopy.setEventStatus(newStatus);
        if (copyStrategy == 0) {
            toCopy.setEmailNotificationName("");
            toCopy.setEmailConfirmationMsg("");
            toCopy.setEmailWaitlistMsg("");
            toCopy.setEmailCancellationMsg("");
            toCopy.setEmailEvaluationMsg("");
        }
        em.persist(toCopy);
        em.flush();
        return toCopy.getWorkshopId();
    }

    /**
     * @param workshopId
     * @param workshopData
     * @param emailForm
     * @return boolean: whether the email settings were changed or not
     * 
     * Changes the email settings of a workshop
     */
    @Override
    @Transactional
    public boolean updateEmailForm(Integer workshopId, Workshops workshopData, EmailInfoForm emailForm) {
        Workshops workshop = getWorkshopById(workshopId);
        workshop.setEmailNotificationName(workshopData.getEmailNotificationName());
        workshop.setEmailConfirmationMsg(workshopData.getEmailConfirmationMsg());
        workshop.setEmailWaitlistMsg(workshopData.getEmailWaitlistMsg());
        workshop.setEmailCancellationMsg(workshopData.getEmailCancellationMsg());
        workshop.setEmailEvaluationMsg(workshopData.getEmailEvaluationMsg());
        em.merge(workshop);
        return true;
    }
    
    /**
     * @param p
     * @return boolean: whether a person was saved or not
     * 
     * Updates a person's details
     */
    @Override
    @Transactional
    public boolean savePerson(Person p) {
        em.merge(p);
        em.flush();
        return true;
    }

    /**
     * @param netId
     * @return a specific person from the database
     */
    @Override
    public Person getPersonByNetId(String netId) {
        Person person = em.createNamedQuery("Person.findByNetId", Person.class).setParameter("netId", netId).getSingleResult();
        return person;
    }

    /**
     * @param id
     * @return a workshop using a workshopID
     */
    @Override
    public Workshops getWorkshopById(Integer id) {
        Workshops workshop = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", id).getSingleResult();
        return workshop;
    }

    /**
     * @param id
     * @return a workshop using a string type of workshopID
     */
    @Override
    public Workshops getWorkshopById(String id) {
        return getWorkshopById(Integer.valueOf(id));
    }

    /**
     * @param workshopId
     * @return a list of facilitators for a workshop
     */
    @Override
    public List<facilitatorDataBean> getFacilitatorList(Integer workshopId) {
        try {
            Workshops w = em.createNamedQuery("Workshops.findByWorkshopId", Workshops.class).setParameter("workshopId", workshopId).getSingleResult();
            List<facilitatorDataBean> facilitatorList = new ArrayList<>();
            for (Person p: w.getMyFacilitators()) {
                facilitatorList.add(new facilitatorDataBean(p.getNetId(),p.getCommonName()));
            }
            return facilitatorList;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

    /**
     * @param workshopId
     * @return a list of netIDs of facilitators for a workshop
     */
    @Override
    public List<String> getFacilitatorListOfNetIds(Integer workshopId){
        try {
            Workshops w = getWorkshopById(workshopId);
            List<String> facilitatorNetIdList = new ArrayList<>();
            for (Person p: w.getMyFacilitators()) {
                facilitatorNetIdList.add(p.getNetId());
            }
            return facilitatorNetIdList;
        }
        catch(Exception e) {
            throw  new EJBException(e);
        }
    }

    /**
     * @param p
     * @return a list of workshops that a user is registered for
     */
    @Override
    public List<Workshops> getWorkshopsForPerson(Person p) {
        return p.getAllWorkshops();
    }

    /**
     * @param p
     * @return a list of workshops that a user is hosting
     */
    @Override
    public List<Workshops> getWorkshopsHostedByPerson(Person p) {
        List<Workshops> workshops = em.createNamedQuery("Workshops.findByWorkshopCreatorId", Workshops.class).setParameter("netId", p.getNetId()).getResultList();
        return workshops;
    }

    /**
     * @param p
     * @return a list of ended workshops that a user has registered for
     */
    @Override
    public List<Workshops> getPastWorkshopsByPerson(Person p) {
        return p.getPastWorkshops();
    }

    /**
     * @param p
     * @return a list of upcoming workshops that a user has registered for
     */
    @Override
    public List<Workshops> getUpcomingWorkshopsByPerson(Person p) {
        return p.getUpcomingWorkshops();
    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a facilitator was added or not
     * 
     * Adds a new facilitator for a workshop
     */
    @Override
    @Transactional
    public boolean addFacilitator(Integer workshopId, String netId) {
        Workshops workshop = getWorkshopById(workshopId);
        Person p = getPersonByNetId(netId);
        workshop.addFacilitator(p);
        return true;
    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a facilitator was removed or not
     * 
     * Removes a facilitator for a workshop
     */
    @Override
    @Transactional
    public boolean removeFacilitator(Integer workshopId, String netId) {
        Workshops workshop = getWorkshopById(workshopId);
        Person p = getPersonByNetId(netId);
        workshop.removeFacilitator(p);
        return true;
    }

    /**
     * @param workshopId
     * @return a list of all registrants for a workshop
     */
    @Override
    public List<Attendance> getAttendance(Integer workshopId) {
        List<Attendance> attendance = em.createNamedQuery("Attendance.findByWorkshopId", Attendance.class).setParameter("workshopId", workshopId).getResultList();
        return attendance;
    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a user's attendance status was updated or not
     * 
     * Adds a registrant to a workshop's attendance list
     */
    @Override
    @Transactional
    public boolean addAttendee(Integer workshopId, String netId) {
        Attendance a = new Attendance(workshopId, netId);
        em.persist(a);
        return true;
    }
    
    /**
     * @param workshopId
     * @param netId
     * @param status
     * @return boolean: whether an attendee's status was changed
     * 
     * Changes the attendance of a workshop registrant
     */
    @Override
    @Transactional
    public boolean editAttendeeStatus(Integer workshopId, String netId, boolean status) {
        Attendance a = em.createNamedQuery("Attendance.findByWorkshopAndNetId", Attendance.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        a.setAttended(status);
        em.merge(a);
        return true;
    }
    
    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether an attendee was removed or not
     * 
     * Removes a registrant from the attendance list
     */
    @Override
    @Transactional
    public boolean removeAttendee(Integer workshopId, String netId) {
        Attendance attendee = em.createNamedQuery("Attendance.findByWorkshopAndNetId", Attendance.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        em.remove(attendee);
        return true;
    }

    /**
     * @param workshopId
     * @return a list of reviews for a workshop
     */
    @Override
    public List<Reviews> getReviews(Integer workshopId) {
        List<Reviews> myReviews = em.createNamedQuery("Reviews.findByWorkshopId", Reviews.class).setParameter("workshopId", workshopId).getResultList();
        return myReviews;
    }
    
    /**
     * @param workshopId
     * @return a list of netIDs that have written reviews for a specific workshop
     */
    @Override
    public List<String> getIdReviews(Integer workshopId) {
        List<Reviews> myReviews = getReviews(workshopId);
        List<String> myIdReviews = new ArrayList<>();
        for (int i = 0; i < myReviews.size(); i++) {
            myIdReviews.add(myReviews.get(i).getPerson().getNetId());
        }
        return myIdReviews;
    }    

    /**
     * @param workshopId
     * @param netId
     * @param review
     * @return boolean: whether a review was added for a workshop or not
     * 
     * Adds a review for a workshop
     */
    @Override
    @Transactional
    public boolean addReview(Integer workshopId, String netId, String review) {
        Reviews newReview = new Reviews(workshopId, netId);
        newReview.setReview(review);
        em.persist(newReview);
        return true;
    }

    /**
     * @param workshopId
     * @param netId
     * @param editedReview
     * @return boolean: whether a review was edited or not
     * 
     * Changes a review for a workshop
     */
    @Override
    @Transactional
    public boolean editReview(Integer workshopId, String netId, String editedReview) {
        Reviews review = em.createNamedQuery("Reviews.findByWorkshopAndNetId", Reviews.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        review.setReview(editedReview);
        em.merge(review);
        return true;

    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a review was deleted or not
     * 
     * Deletes a review for a workshop
     */
    @Override
    @Transactional
    public boolean removeReview(Integer workshopId, String netId) {
        Reviews review = em.createNamedQuery("Review.findByWorkshopAndNetId", Reviews.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        em.remove(review);
        return true;
    }

    
    /**
     * @param workshopId
     * @return a wait list for a workshop
     */
    @Override
    @Transactional
    public List<Waitlist> getWaitlist(Integer workshopId) {
        List<Waitlist> waitlist = em.createNamedQuery("Waitlist.findByWorkshopId", Waitlist.class).setParameter("workshopId", workshopId).getResultList();
        return waitlist;
    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a user was added to a wait list or not
     * 
     * Adds a user to a workshop's wait list
     */
    @Override
    @Transactional
    public boolean addToWaitlist(Integer workshopId, String netId) {
        Waitlist waitlister = new Waitlist(workshopId, netId);
        waitlister.setDatetimeApplied(new Date());
        em.persist(waitlister);
        return true;
    }

    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a user was removed from a wait list or not
     * 
     * Removes a user from a workshop's wait list
     */
    @Override
    @Transactional
    public boolean removeFromWaitlist(Integer workshopId, String netId) {
        Waitlist waitlist = em.createNamedQuery("Waitlist.findByWorkshopAndNetId", Waitlist.class).setParameter("workshopId", workshopId).setParameter("netId", netId).getSingleResult();
        em.remove(waitlist);
        return true;
    }
    
    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a user is on a specific workshop's wait list or not
     */
    @Override
    public boolean isOnWaitlist(Integer workshopId, String netId){
        List<Waitlist> waitList = getWaitlist(workshopId);
        List<String> netIds = new ArrayList();
        for(Waitlist w : waitList)
            netIds.add(w.getPerson().getNetId());
        
        for(String n : netIds)
            if(n.equals(netId))
                return true;
        
        return false;
    }
    
    /**
     * @param workshopId
     * @param netId
     * @return boolean: whether a user is registered for a workshop or not
     */
    @Override
    public boolean isRegistered(Integer workshopId, String netId){
        List<Person> registrants = getWorkshopRegistrants(workshopId);
        
        List<String> netIds = new ArrayList();
        for(Person p : registrants)
            netIds.add(p.getNetId());
        
        for(String n : netIds)
            if(n.equals(netId))
                return true;
        
        return false;
    }
    
    /**
     * @param workshopId
     * @return boolean: whether a workshop has already ended or not
     */
    @Override
     public boolean workshopHasPast(Integer workshopId) {
         return getWorkshopById(workshopId).getEventStart().before(new Date());
     }
}
