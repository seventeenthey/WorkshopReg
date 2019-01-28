/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.admin.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>LDAPConnFactoryLocal class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */
public class LDAPConnFactoryLocal {

    private static Logger logger = LogManager.getLogger(LDAPConnFactoryLocal.class);

    LDAPConnFactoryLocal service;                               // singleton reference
    boolean debug = false;                       // our flag for debug messages. 

// post fix strings that we slap on our required Variable Keys to extract them from the config file
// We only are aware of a primary and secondary server
    private String PRIMARY_SERVER_SETTINGS = "1";
    private String SECONDARY_SERVER_SETTINGS = "2";

// if the configuration is not sent in, we will fail over after 3 bad queries from the current server to the other.
    private int DEFAULT_NUM_QUERY_FAILURE_TRIGGER = 3;

// our default is the currentServerSetting, which is the primary server
    private String currentLiveServerSettings = PRIMARY_SERVER_SETTINGS;
    private String alternateLiveServerSettings = SECONDARY_SERVER_SETTINGS;
// this persists our selection of which server we point to.
    private String activeLiveServer;

    private Hashtable primarySettings;          // hashtable that we use to store our primary credentials to connect to LDAP
    private Hashtable secondarySettings;      // secondary set of credentials
    private Hashtable possibleSettings;         // a hash table that holds the loaded settings to allow us to flip from one to another

// This is to help us manage our large set of properties.  An XML file would be nice, but not necessary (at least for me)
// This allows us to have a String array of required Variables and also the integer locations for them to be used inside the code.
    private boolean bPropsLoaded;
    private Hashtable propertiesHash;
    private static String[] requiredVariableKeys = {"ldap.host", "ldap.port",
        "ldap.baseDN", "ldap.uidAttribute",
        "ldap.managerDN", "ldap.managerPW",
        "ldap.protocol", "ldap.numQueryFailureTrigger",
        "ldap.TCPConnectionTimeout", "ldap.recognizeOtherAsStaff"};

    private int iLdapHost = 0;
    private int iLdapPort = 1;
    private int iLdapBaseDN = 2;
    private int iLdapUidAttribute = 3;
    private int iLdapManagerDN = 4;
    private int iLdapManagerPW = 5;
    private int iLdapManagerProto = 6;
    private int iLdapNumQueryFailureTrigger = 7;
    private int iLdapTCPConnectionTimeout = 8;
    private static int iLdapRecognizeOtherAsStaff = 9;

// These attributes are to help determine which files are used to read in the properties.
    private String sPropertiesFileAttribute = "UIS-LDAP.properties";         // optional command line attributes -DUIS-LDAP.properties
    private String sDefaultPropertiesFile = "/META-INF/ldap.properties";  // located in the idMgr.jar...
    private String sPropertiesFile;                                                       // the actual file we use
    private String sDefaultTCPConnectionTimeout = "10000";               // default timeout for TCP connection in constructing the link 

    private boolean settingRecognizeOtherAsStaff = true;
    private boolean defaultOtherBehaviour = true;

    /**
     * Discover which properties file to use, read it in, and then populate our
     * primary and secondary connection environements
     */
    public LDAPConnFactoryLocal() {

        try {
            if (service == null) {
//              if (bPropsLoaded)
//              {  // don't do anything
//              }else{
                loadProperties();
                setupConnectionEnvironments();
                //        }

                service = this;
            } 
            else {
                logger.error("common.object.LDAPService found to be not null");
            }

        } 
        catch (Exception ex) {
            ex.printStackTrace();
        } 
        finally {
        }

    }

    /**
     * <p>getInstance.</p>
     *
     * @return a {@link ca.queensu.websvcs.workshopbooking.admin.utility.LDAPConnFactoryLocal} object.
     */
    public LDAPConnFactoryLocal getInstance() {
        return service;
    }

    /**
     * The workhorse and the class that implements the fault tolerance.  <br>
     * The primary and secondary connection settings are contained in a hashtable
     * and we can choose to flip between them on an as needed basis.<br>
     * The challenge however is really when SHOULD we?  <br>
     * The current strategy is to allow the first one to fail and then try
     * secondary settings. If that fails, then bomb out with a null
     * connection<br>
     *
     * If the secondary settings work fine, that is good, however, we should at
     * least try to go BACK to the primary one.
     * <br> In the case of exceptions, they are immediate and appear not to pose
     * a problem of having extensive time outs. This means that if something
     * fails, it will fail right away.
     * <br>We will leverage this aspect and will implement a
     *
     * @return a DirectoryContext that is used to connect to LDAP and search
     */
    public DirContext getConnection() {
        DirContext conn = null;
        DirContext conn2 = null;
        DirContext returnedContext = null;
        boolean conn1Failed = false;
        boolean conn2Failed = false;
        
        try {      // first, we try the active live Server to get a connection
            conn = new InitialDirContext((Hashtable) possibleSettings.get(currentLiveServerSettings));
            println("GOT CONNECTION");
        } 
        catch (Exception e) {
            conn1Failed = true;
            // if we fail, then we try to go against the alternate live server
            try {
                conn2 = new InitialDirContext((Hashtable) possibleSettings.get(alternateLiveServerSettings));
                flipBetweenServers();  // if we succeed, we flip active and alternate so we don't always have to fail on our connection.
            } 
            catch (Exception ex) // if we do not, the exception will be trapped, and we go into this, skipping the flip
            {
                conn2Failed = true;
                // if we get here we are really in sad shape and will return null up the chain.....what else can I say?
                ex.printStackTrace();
            } 
            finally {
            }
            logger.error(e);
        } 
        finally {
        }

        if (!conn1Failed) {
            return conn;
        } 
        else {
            return conn2;
        }

//    return conn;
    }

    /**
     * Releases the current connection, if there is one.
     *
     * @param conn a {@link javax.naming.directory.DirContext} object.
     */
    public void releaseConnection(DirContext conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } 
        catch (Exception e) {
            logger.error("closing connection failed:" + e);
        }
    }

    /**
     * Gets the base DN used to search the LDAP directory context.
     * <br> It needs to be fetched from the properties file, but for the
     * particular server we are pointing at
     *
     * @return a DN to use as reference point or context for queries
     */
    public String getBaseDN() {
        return (String) (propertiesHash.get(requiredVariableKeys[iLdapBaseDN] + currentLiveServerSettings));
    }

    /**
     * Gets the uid attribute used to search the LDAP directory context.
     * <br> It needs to be fetched from the properties file, but for the
     * particular server we are pointing at
     *
     * @return a DN to use as reference point or context for queries
     */
    public String getUidAttribute() {
        return (String) (propertiesHash.get(requiredVariableKeys[iLdapUidAttribute] + currentLiveServerSettings));
    }

    /**
     * Small utility class that we use to flip between current and alternate
     * settings that we have so we can travel back and forth between our
     * settings.
     */
    private void flipBetweenServers() {
        println("BEFORE\n CurrentLiveServer=" + currentLiveServerSettings + "\nAlternateLiveServer=" + alternateLiveServerSettings);

        String tmp = currentLiveServerSettings;
        currentLiveServerSettings = alternateLiveServerSettings;
        alternateLiveServerSettings = tmp;
        println("AFTER\n CurrentLiveServer=" + currentLiveServerSettings + "\nAlternateLiveServer=" + alternateLiveServerSettings);

    }

    /**
     * loadProperties perfoms the reading of the properties file.<br>
     * It will first look for the command line parameter passed into the JVM -D
     * UIS-LDAP.properties= &lt; full path to file &gt; <br>
     * This file is controlled by the private variable:
     * <i>sPropertiesFileAttribute</i>
     * <br> Then, it will look for a file called /META-INF/ldap.properties which
     * is commonly delivered in the jar file 'idMgr.jar'' not finding anything
     * it will fail to configure the LDAP service.
     *
     * @param none
     * @return nothing
     */
    private void loadProperties() {
        if (!bPropsLoaded) {

            propertiesHash = new Hashtable();
            String myPropertiesFile = "";
            boolean isGood = true;
            InputStream ins = null;
            File hndlPropsFile = null;
            boolean useClassLoader = false;
            try {
                // determine which file to choose first...
                // precedence is given to one passed in with a dash D command (-D)

                String propertiesFile = System.getProperty(sPropertiesFileAttribute);
                if (null == propertiesFile) {
                    propertiesFile = "";
                }

                if (propertiesFile.length() > 2) // we have a file to use that was passed in via command line
                {
                    hndlPropsFile = new File(propertiesFile);
                    if (hndlPropsFile.isFile()) {
                        println("Found " + sPropertiesFileAttribute + " with a value of:" + propertiesFile + " ");
                        myPropertiesFile = propertiesFile;
                        ins = new FileInputStream(hndlPropsFile);
                    } 
                    else {
                        logger.error("****ERROR****\n " + sPropertiesFileAttribute + " had a value of:" + propertiesFile
                                + " and it does not exist..using default ldap.properties located in idMgr.jar");
                    }
                } 
                else {   // we use the default file that we think we know about and go from there.
                    ins = this.getClass().getResourceAsStream(sDefaultPropertiesFile);
                }   // end of properties file check

                Properties ldapProps = new Properties();
                ldapProps.load(ins);

                //load up the properties and the secondary ones for failover of the LDAP server
                int count = 0;
                while (count < requiredVariableKeys.length) {
                    // Load the key and the 'failover setting' (key concat with #2)
                    String key = requiredVariableKeys[count] + PRIMARY_SERVER_SETTINGS;
                    String failoverkey = requiredVariableKeys[count] + SECONDARY_SERVER_SETTINGS;

                    propertiesHash.put(key, ldapProps.getProperty(key, ""));
                    propertiesHash.put(failoverkey, ldapProps.getProperty(failoverkey, ""));

                    println(key + " =" + propertiesHash.get(key));
                    println(failoverkey + " =" + propertiesHash.get(failoverkey));
                    count++;
                }
                activeLiveServer = (String) propertiesHash.get(requiredVariableKeys[iLdapHost] + currentLiveServerSettings);
                bPropsLoaded = true;

            } 
            catch (Exception ex) {
                ex.printStackTrace();
            } 
            finally {
            }

        } 
        else {
            // properties already loaded 
            // I suppose I could put in some logic here like ' hit LDAP 100 times, read properties next time to allow dynamic refresh'
        }

    }  // end loadProperties ()

    /**
     * a private method to help configure the hashtables that we need for the
     * connection environments I may be a bit sloppy here in how they are
     * accessed, but will refactor that at a later time.
     */
    private void setupConnectionEnvironments() {

        // setup our place to stash stuff
        possibleSettings = new Hashtable(2, 0.75f);

        // Set up the primary hash table
        primarySettings = new Hashtable(6, 0.75f);
        primarySettings.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        primarySettings.put(Context.SECURITY_AUTHENTICATION, "simple");
        primarySettings.put(Context.SECURITY_PRINCIPAL, propertiesHash.get(requiredVariableKeys[iLdapManagerDN] + PRIMARY_SERVER_SETTINGS));
        primarySettings.put(Context.SECURITY_CREDENTIALS, propertiesHash.get(requiredVariableKeys[iLdapManagerPW] + PRIMARY_SERVER_SETTINGS));
        // if we have an ssl variable, we add SSL to our environment, otherwise, it is absent and non SSL by default

        if ((propertiesHash.get(requiredVariableKeys[iLdapManagerProto] + PRIMARY_SERVER_SETTINGS)).equals("ssl")) {
            primarySettings.put(Context.SECURITY_PROTOCOL, "ssl");
        }
        StringBuffer urlBuffer = new StringBuffer("ldap://");
        urlBuffer.append(propertiesHash.get(requiredVariableKeys[iLdapHost] + PRIMARY_SERVER_SETTINGS));
        urlBuffer.append(":");
        urlBuffer.append(propertiesHash.get(requiredVariableKeys[iLdapPort] + PRIMARY_SERVER_SETTINGS));
        primarySettings.put(Context.PROVIDER_URL, urlBuffer.toString());
        // now we put in the TCP Timeout, from what was passed in or defaults
        String tmp = (String) propertiesHash.get(requiredVariableKeys[iLdapTCPConnectionTimeout] + PRIMARY_SERVER_SETTINGS);
        primarySettings.put("com.sun.jndi.ldap.connect.timeout", (((tmp != null) && (tmp.length() > 1)) ? tmp : sDefaultTCPConnectionTimeout));

        // added Jan 10, 2004 to deal with the Other designation of employees to recognize them as real emp #'s
        //  iLdapRecognizeOtherAsStaff
        String tmpOther = (String) propertiesHash.get(requiredVariableKeys[iLdapRecognizeOtherAsStaff] + PRIMARY_SERVER_SETTINGS);
        primarySettings.put(requiredVariableKeys[iLdapRecognizeOtherAsStaff], (((tmpOther != null) && (tmpOther.length() > 1)) ? tmpOther : "false"));

        // now store this in a hashtable so that we can pull it out when needed
        possibleSettings.put(PRIMARY_SERVER_SETTINGS, (Hashtable) primarySettings);

        // Set up the secondary hash table
        secondarySettings = new Hashtable(6, 0.75f);
        secondarySettings.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        secondarySettings.put(Context.SECURITY_AUTHENTICATION, "simple");
        secondarySettings.put(Context.SECURITY_PRINCIPAL, propertiesHash.get(requiredVariableKeys[iLdapManagerDN] + SECONDARY_SERVER_SETTINGS));
        secondarySettings.put(Context.SECURITY_CREDENTIALS, propertiesHash.get(requiredVariableKeys[iLdapManagerPW] + SECONDARY_SERVER_SETTINGS));

        // if we have an ssl variable, we add SSL to our environment, otherwise, it is absent and non SSL by default
        if ((propertiesHash.get(requiredVariableKeys[iLdapManagerProto] + SECONDARY_SERVER_SETTINGS)).equals("ssl")) {
            secondarySettings.put(Context.SECURITY_PROTOCOL, "ssl");
        }
        StringBuffer urlBuffer2 = new StringBuffer("ldap://");
        urlBuffer2.append(propertiesHash.get(requiredVariableKeys[iLdapHost] + SECONDARY_SERVER_SETTINGS));
        urlBuffer2.append(":");
        urlBuffer2.append(propertiesHash.get(requiredVariableKeys[iLdapPort] + SECONDARY_SERVER_SETTINGS));
        secondarySettings.put(Context.PROVIDER_URL, urlBuffer2.toString());
        // now we put in the TCP Timeout, from what was passed in or defaults
        tmp = (String) propertiesHash.get(requiredVariableKeys[iLdapTCPConnectionTimeout] + SECONDARY_SERVER_SETTINGS);
        secondarySettings.put("com.sun.jndi.ldap.connect.timeout", (((tmp != null) && (tmp.length() > 1)) ? tmp : sDefaultTCPConnectionTimeout));

        // added Jan 10, 2004 to deal with the Other designation of employees to recognize them as real emp #'s
        //  iLdapRecognizeOtherAsStaff
        tmpOther = (String) propertiesHash.get(requiredVariableKeys[iLdapRecognizeOtherAsStaff] + SECONDARY_SERVER_SETTINGS);
        secondarySettings.put(requiredVariableKeys[iLdapRecognizeOtherAsStaff], (((tmpOther != null) && (tmpOther.length() > 1)) ? tmpOther : "false"));

        // now store this in a hashtable so that we can pull it out when needed
        possibleSettings.put(SECONDARY_SERVER_SETTINGS, (Hashtable) secondarySettings);

    }

    /**
     * <p>isSettingRecognizeOtherAsStaff.</p>
     *
     * @return a boolean.
     */
    public boolean isSettingRecognizeOtherAsStaff() {
        Boolean myAnswer = new Boolean(this.defaultOtherBehaviour);

        // now we fetch the requested behavior from the ldap server settings we have
        String tmpOther = (String) propertiesHash.get(requiredVariableKeys[iLdapRecognizeOtherAsStaff] + this.currentLiveServerSettings);
        if (tmpOther != null) {
            try {
                myAnswer = new Boolean(tmpOther);
            } 
            catch (Exception ex) {
                logger.warn("LDAPService in idMgr exceptioned on trying to set a boolean value and defaulting Other settings to:" + this.defaultOtherBehaviour);
                ex.printStackTrace();
            }
        } 
        else {
            logger.warn("LDAPService in idMgr discovered a null value for ldap.recognizeOtherAsStaff ..setting to:" + this.defaultOtherBehaviour);
            myAnswer = new Boolean(this.defaultOtherBehaviour);
        }

        return myAnswer.booleanValue();
    }

    /**
     * <p>Getter for the field <code>activeLiveServer</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getactiveLiveServer() {
        return (String) (propertiesHash.get(requiredVariableKeys[iLdapHost] + currentLiveServerSettings));
    }

    /**
     * <p>Setter for the field <code>debug</code>.</p>
     *
     * @param trueOrFalse a boolean.
     */
    public void setDebug(boolean trueOrFalse) {
        debug = trueOrFalse;
    }

    /**
     * <p>Getter for the field <code>debug</code>.</p>
     *
     * @return a boolean.
     */
    public boolean getDebug() {
        return debug;
    }

    private void println(String msg) {
        if (debug) {    // only output if debug is set to true!
            String classname = "LDAPService";
            logger.debug(classname + "::" + msg);
        }
    }
}
