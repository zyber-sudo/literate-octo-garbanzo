package model;

import java.sql.Timestamp;

/**
 * Class for creating an Appointment object.
 *
 * <p>
 * This class declares and defines the fields and methods for an Appointment.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class Appointment {

    private final int appId;
    private final String appTitle;
    private final String appDescription;
    private final String appLocation;
    private final int appContactId;
    private final String appType;
    private final Timestamp appStartDate;
    private final Timestamp appEndDate;
    private final Timestamp appCreatedDate;
    private final String appCreatedBy;
    private final Timestamp appUpdatedDate;
    private final String appUpdatedBy;
    private final int appCustomerId;
    private final int appUserId;
    private String appContactName;


    /**
     * Base constructor for the Appointment class.
     *
     * <p>
     * This constructor for the appointmnet class, without the addition of the contact name.
     * </p>
     *
     * @param appId          The ID of the appointment
     * @param appTitle       The name of the appointment
     * @param appDescription The description of the appointment
     * @param appLocation    The location of the appointment
     * @param appContactId   The contact ID connected to the appointment
     * @param appType        The appointment type of the appointment
     * @param appStartDate   The start date/time of the appointment
     * @param appEndDate     The end date/time of the appointment
     * @param appCreatedDate The date that the appointment is created
     * @param appCreatedBy   Who the appointment is created by
     * @param appUpdatedDate The date that the appointment was last update
     * @param appUpdatedBy   Who the appointment was last updated by
     * @param appCustomerId  The ID of the customer in the appointment
     * @param appUserId      The ID of the customer in the appointment
     */
    public Appointment(int appId, String appTitle, String appDescription, String appLocation, int appContactId, String appType, Timestamp appStartDate, Timestamp appEndDate, Timestamp appCreatedDate, String appCreatedBy, Timestamp appUpdatedDate, String appUpdatedBy, int appCustomerId, int appUserId) {
        this.appId = appId;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appContactId = appContactId;
        this.appType = appType;
        this.appStartDate = appStartDate;
        this.appEndDate = appEndDate;
        this.appCreatedDate = appCreatedDate;
        this.appCreatedBy = appCreatedBy;
        this.appUpdatedDate = appUpdatedDate;
        this.appUpdatedBy = appUpdatedBy;
        this.appCustomerId = appCustomerId;
        this.appUserId = appUserId;
    }

    /**
     * Modified constructor for the Appointment class.
     *
     * <p>
     * This constructor for the appointmnet class, with the new addition of the contact name for ease of use.
     * </p>
     *
     * @param appId          The ID of the appointment
     * @param appTitle       The name of the appointment
     * @param appDescription The description of the appointment
     * @param appLocation    The location of the appointment
     * @param appContactId   The contact ID connected to the appointment
     * @param appType        The appointment type of the appointment
     * @param appStartDate   The start date/time of the appointment
     * @param appEndDate     The end date/time of the appointment
     * @param appCreatedDate The date that the appointment is created
     * @param appCreatedBy   Who the appointment is created by
     * @param appUpdatedDate The date that the appointment was last update
     * @param appUpdatedBy   Who the appointment was last updated by
     * @param appCustomerId  The ID of the customer in the appointment
     * @param appUserId      The ID of the customer in the appointment
     * @param appContactName The name of the contact that is joined onto the appointment
     */
    public Appointment(int appId, String appTitle, String appDescription, String appLocation, int appContactId, String appType, Timestamp appStartDate, Timestamp appEndDate, Timestamp appCreatedDate, String appCreatedBy, Timestamp appUpdatedDate, String appUpdatedBy, int appCustomerId, int appUserId, String appContactName) {
        this.appId = appId;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appContactId = appContactId;
        this.appType = appType;
        this.appStartDate = appStartDate;
        this.appEndDate = appEndDate;
        this.appCreatedDate = appCreatedDate;
        this.appCreatedBy = appCreatedBy;
        this.appUpdatedDate = appUpdatedDate;
        this.appUpdatedBy = appUpdatedBy;
        this.appCustomerId = appCustomerId;
        this.appUserId = appUserId;
        this.appContactName = appContactName;
    }

    /**
     * @return the appointment ID
     */
    public int getAppId() {
        return appId;
    }

    /**
     * @return the appointment title
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * @return the appointment description
     */
    public String getAppDescription() {
        return appDescription;
    }

    /**
     * @return the appointment location
     */
    public String getAppLocation() {
        return appLocation;
    }

    /**
     * @return the appointment contact ID number
     */
    public int getAppContactId() {
        return appContactId;
    }

    /**
     * @return the appointment type
     */
    public String getAppType() {
        return appType;
    }

    /**
     * @return the appointment start time/date
     */
    public Timestamp getAppStartDate() {
        return appStartDate;
    }

    /**
     * @return the appointment end time/date
     */
    public Timestamp getAppEndDate() {
        return appEndDate;
    }

    /**
     * @return the appointment date/time created
     */
    public Timestamp getAppCreatedDate() {
        return appCreatedDate;
    }

    /**
     * @return the user that created the appointment
     */
    public String getAppCreatedBy() {
        return appCreatedBy;
    }

    /**
     * @return the appointment date/time it was last updated
     */
    public Timestamp getAppUpdatedDate() {
        return appUpdatedDate;
    }

    /**
     * @return the user that last updated the appointment
     */
    public String getAppUpdatedBy() {
        return appUpdatedBy;
    }

    /**
     * @return the appointment associated customer ID
     */
    public int getAppCustomerId() {
        return appCustomerId;
    }

    /**
     * @return the appointment associated user ID
     */
    public int getAppUserId() {
        return appUserId;
    }

    /**
     * This is used from the join method in the <i>AppointmentDAO</i>.
     *
     * <p>
     * This getter is to list the string of the contact name for the
     * table view.
     * </p>
     *
     * @return the appointment associated contact name
     * @see data_access.AppointmentDAO
     */
    public String getAppContactName() {
        return appContactName;
    }
}
