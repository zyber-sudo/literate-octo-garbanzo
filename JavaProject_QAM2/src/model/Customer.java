package model;

import java.sql.Timestamp;

/**
 * Class for creating an Customer object.
 *
 * <p>
 * This class declares and defines the fields and methods for a Customer.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class Customer {

    private final int customerId;
    private final String customerName;
    private final String customerAddress;
    private final String customerZip;
    private final String customerPhone;
    private final Timestamp customerCreatedDate;
    private final String customerCreatedBy;
    private final Timestamp customerUpdatedDate;
    private final String customerUpdatedBy;
    private final int customerDivisionId;
    private String customerDivisionName;
    private String customerCountryName;


    /**
     * Base constructor for the Customer class. Omitting the additional fields for the Country Name and the Division Name.
     *
     * @param customerId          The ID of the customer
     * @param customerName        The name of the customer
     * @param customerAddress     The address of the customer
     * @param customerZip         The postal code of the customer
     * @param customerPhone       The phone number of the customer
     * @param customerCreatedDate The date/time the customer was created
     * @param customerCreatedBy   The user that created the customer
     * @param customerUpdatedDate The date/time the customer was last updated
     * @param customerUpdatedBy   The user that last updated the customer
     * @param customerDivisionId  The division ID asssociated with the customer
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerZip, String customerPhone, Timestamp customerCreatedDate, String customerCreatedBy, Timestamp customerUpdatedDate, String customerUpdatedBy, int customerDivisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZip = customerZip;
        this.customerPhone = customerPhone;
        this.customerCreatedDate = customerCreatedDate;
        this.customerCreatedBy = customerCreatedBy;
        this.customerUpdatedDate = customerUpdatedDate;
        this.customerUpdatedBy = customerUpdatedBy;
        this.customerDivisionId = customerDivisionId;
    }


    /**
     * The modified constructor for the Customer class with the additional fields for the Country Name and the Division Name.
     *
     * @param customerId           The ID of the customer
     * @param customerName         The name of the customer
     * @param customerAddress      The address of the customer
     * @param customerZip          The postal code of the customer
     * @param customerPhone        The phone number of the customer
     * @param customerCreatedDate  The date/time the customer was created
     * @param customerCreatedBy    The user that created the customer
     * @param customerUpdatedDate  The date/time the customer was last updated
     * @param customerUpdatedBy    The user that last updated the customer
     * @param customerDivisionId   The division ID asssociated with the customer
     * @param customerCountry      The name of the country that is associated with the customer
     * @param customerDivisionName The name of the First Level Division that is associated with the customer
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerZip, String customerPhone, Timestamp customerCreatedDate, String customerCreatedBy, Timestamp customerUpdatedDate, String customerUpdatedBy, int customerDivisionId, String customerCountry, String customerDivisionName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZip = customerZip;
        this.customerPhone = customerPhone;
        this.customerCreatedDate = customerCreatedDate;
        this.customerCreatedBy = customerCreatedBy;
        this.customerUpdatedDate = customerUpdatedDate;
        this.customerUpdatedBy = customerUpdatedBy;
        this.customerDivisionId = customerDivisionId;
        customerCountryName = customerCountry;
        this.customerDivisionName = customerDivisionName;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @return the customer postal code
     */
    public String getCustomerZip() {
        return customerZip;
    }

    /**
     * @return the customer phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * @return the time/date the customer was created
     */
    public Timestamp getCustomerCreatedDate() {
        return customerCreatedDate;
    }

    /**
     * @return the user that created the customer
     */
    public String getCustomerCreatedBy() {
        return customerCreatedBy;
    }

    /**
     * @return the time/date the customer was last updated
     */
    public Timestamp getCustomerUpdatedDate() {
        return customerUpdatedDate;
    }

    /**
     * @return the user that last updated the customer
     */
    public String getCustomerUpdatedBy() {
        return customerUpdatedBy;
    }

    /**
     * @return the divivsion ID associated with the customer
     */
    public int getCustomerDivisionId() {
        return customerDivisionId;
    }

    /**
     * @return the divivsion name associated with the customer
     */
    public String getCustomerDivisionName() {
        return customerDivisionName;
    }

    /**
     * @return the country name associated with the customer
     */
    public String getCustomerCountryName() {
        return customerCountryName;
    }

    /**
     * The overload for the toString method for the Customer.
     *
     * <p>
     * This method overloads the toString method to disply just the name of the Customer in combo boxes throughout
     * the program.
     * </p>
     *
     * @return what to display when to string is called for a customer
     */
    @Override
    public String toString() {
        return (customerName);
    }
}
