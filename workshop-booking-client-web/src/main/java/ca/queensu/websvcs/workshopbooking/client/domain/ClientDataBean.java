package ca.queensu.websvcs.workshopbooking.client.domain;

import java.io.Serializable;


/**
 * <p>ClientDataBean class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class ClientDataBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String emplid;
    private String netId;
    //common name
    private String name;
    private String givenName;
    private String surname;
    private String email;


    /**
     * <p>Constructor for StudentDataBean.</p>
     */
    public ClientDataBean()
    {
    }

    /**
     * <p>Getter for the field <code>emplid</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEmplid() {
        return emplid;
    }

    /**
     * <p>Setter for the field <code>emplid</code>.</p>
     *
     * @param emplid a {@link java.lang.String} object.
     */
    public void setEmplid(String emplid) {
        this.emplid = emplid;
    }

    /**
     * <p>Getter for the field <code>email</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>Setter for the field <code>email</code>.</p>
     *
     * @param email a {@link java.lang.String} object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>givenName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * <p>Setter for the field <code>givenName</code>.</p>
     *
     * @param givenName a {@link java.lang.String} object.
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * <p>Getter for the field <code>surname</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * <p>Setter for the field <code>surname</code>.</p>
     *
     * @param surname a {@link java.lang.String} object.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "ClientDataBean{" + "emplid=" + emplid + ", name=" + name + ", givenName=" + givenName + ", surname=" + surname + ", email=" + email + '}';
    }

    /**
     * <p>Getter for the field <code>netId</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNetId() {
        return netId;
    }

    /**
     * <p>Setter for the field <code>netId</code>.</p>
     *
     * @param netId a {@link java.lang.String} object.
     */
    public void setNetId(String netId) {
        this.netId = netId;
    }
    
    
}
