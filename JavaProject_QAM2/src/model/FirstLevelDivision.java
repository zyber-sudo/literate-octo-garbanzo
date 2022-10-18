package model;

import java.sql.Timestamp;

/**
 * Class for creating a First Level Division object.
 *
 * <p>
 * This class declares and defines the fields and methods for a First Level Division.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class FirstLevelDivision {

    private final int divisionId;
    private final String divisionName;
    private final Timestamp divisionCreatedDate;
    private final String divisionCreatedBy;
    private final Timestamp divisionUpdatedDate;
    private final String divisionUpdatedBy;
    private final int countryId;

    /**
     * Base constructor for the First Level Division class.
     *
     * @param divisionId          The id of the division
     * @param divisionName        The name of the division
     * @param divisionCreatedDate The date/time the division was created
     * @param divisionCreatedBy   The user that created the division
     * @param divisionUpdatedDate The date/time the division was last updated
     * @param divisionUpdatedBy   The user that last updated the division
     * @param countryId           The ID of the country associated with the division
     */
    public FirstLevelDivision(int divisionId, String divisionName, Timestamp divisionCreatedDate, String divisionCreatedBy, Timestamp divisionUpdatedDate, String divisionUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.divisionCreatedDate = divisionCreatedDate;
        this.divisionCreatedBy = divisionCreatedBy;
        this.divisionUpdatedDate = divisionUpdatedDate;
        this.divisionUpdatedBy = divisionUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @return the date/time the division was created
     */
    public Timestamp getDivisionCreatedDate() {
        return divisionCreatedDate;
    }

    /**
     * @return the user the division was created by
     */
    public String getDivisionCreatedBy() {
        return divisionCreatedBy;
    }

    /**
     * @return the date/time the division was last updated
     */
    public Timestamp getDivisionUpdatedDate() {
        return divisionUpdatedDate;
    }

    /**
     * @return the user the division was last updated by
     */
    public String getDivisionUpdatedBy() {
        return divisionUpdatedBy;
    }

    /**
     * @return the country ID associated with the division
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * The overload for the toString method for the First Level Division.
     *
     * <p>
     * This method overloads the toString method to display just the names of the First Level Division in combo boxes throughout
     * the program.
     * </p>
     *
     * @return what to display when to string is called for a First Level Division
     */
    @Override
    public String toString() {
        return (divisionName);
    }
}
