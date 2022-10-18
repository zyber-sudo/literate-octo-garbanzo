package model;

import java.sql.Timestamp;

/**
 * Class for creating an Country object.
 *
 * <p>
 * This class declares and defines the fields and methods for a Country.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class Country {

    private final int countryId;
    private final String countryName;
    private final Timestamp countryCreatedDate;
    private final String countryCreatedBy;
    private final Timestamp countryUpdatedDate;
    private final String countryUpdatedBy;

    /**
     * Base constructor for the Country class.
     *
     * @param countryId          The ID of the country
     * @param countryName        The name of the country
     * @param countryCreatedDate The date/time the country was created
     * @param countryCreatedBy   The user that created the Country in the database
     * @param countryUpdatedDate The date/time the country was last updated
     * @param countryUpdatedBy   The user that last updated the Country in the database
     */
    public Country(int countryId, String countryName, Timestamp countryCreatedDate, String countryCreatedBy, Timestamp countryUpdatedDate, String countryUpdatedBy) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCreatedDate = countryCreatedDate;
        this.countryCreatedBy = countryCreatedBy;
        this.countryUpdatedDate = countryUpdatedDate;
        this.countryUpdatedBy = countryUpdatedBy;
    }

    /**
     * @return the ID of the country
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @return the name of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the date/time the contry was created
     */
    public Timestamp getCountryCreatedDate() {
        return countryCreatedDate;
    }

    /**
     * @return the user that contry was created by
     */
    public String getCountryCreatedBy() {
        return countryCreatedBy;
    }

    /**
     * @return the date/time the contry was last updated
     */
    public Timestamp getCountryUpdatedDate() {
        return countryUpdatedDate;
    }

    /**
     * @return the user that last updated the country
     */
    public String getCountryUpdatedBy() {
        return countryUpdatedBy;
    }


    /**
     * The overload for the toString method for the Country.
     *
     * <p>
     * This method overloads the toString method to disply just the name of the Country in combo boxes throughout
     * the program.
     * </p>
     *
     * @return what to display when to string is called for a Country
     */
    @Override
    public String toString() {
        return (countryName);
    }
}
