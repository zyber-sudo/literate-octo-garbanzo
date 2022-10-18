package model;

import java.sql.Timestamp;

/**
 * Class for creating a User object.
 *
 * <p>
 * This class declares and defines the fields and methods for a User.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class User {

    private final int userId;
    private final String userName;
    private final String userPassword;
    private final Timestamp userCreatedDate;
    private final String userCreatedBy;
    private final Timestamp userUpdatedDate;
    private final String userUpdatedBy;


    /**
     * Base constructor for the User class.
     *
     * @param userId          The ID of the user
     * @param userName        The name of the user
     * @param userPassword    The password of the user
     * @param userCreatedDate The date/time the user has been created
     * @param userCreatedBy   The user that created this user
     * @param userUpdatedDate The date/time the user was last updated
     * @param userUpdatedBy   The user that last updated the user
     */
    public User(int userId, String userName, String userPassword, Timestamp userCreatedDate, String userCreatedBy, Timestamp userUpdatedDate, String userUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userCreatedDate = userCreatedDate;
        this.userCreatedBy = userCreatedBy;
        this.userUpdatedDate = userUpdatedDate;
        this.userUpdatedBy = userUpdatedBy;
    }

    /**
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the users password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @return the time/date of when the user is created
     */
    public Timestamp getUserCreatedDate() {
        return userCreatedDate;
    }

    /**
     * @return the user that is responsible for creation of this user
     */
    public String getUserCreatedBy() {
        return userCreatedBy;
    }

    /**
     * @return the time/date of when the user was last updated
     */
    public Timestamp getUserUpdatedDate() {
        return userUpdatedDate;
    }

    /**
     * @return the user that last updated this user
     */
    public String getUserUpdatedBy() {
        return userUpdatedBy;
    }

    /**
     * The overload for the toString method for the User.
     *
     * <p>
     * This method overloads the toString method to display just the name of the User in combo boxes throughout
     * the program.
     * </p>
     *
     * @return what to display when to string is called for a user
     */
    @Override
    public String toString() {
        return (userName);
    }
}
