package data_access;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.GetAllInterface;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Class for accessing the SQL database for User information.
 *
 * <p>
 * This class is tasked with all of the database access and modification the mySQL queries pertaining to Users.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class UserDAO {


    /**
     * <h3>Lambda Use #1:</h3>
     * <p>
     *     This is my first use of a lambda expression to create, execute, and return a list of all users.
     * </p>
     *
     */
    public static GetAllInterface<User> getAllUsers = () ->
    {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int userId = results.getInt("User_ID");
                String userName = results.getString("User_Name");
                String userPassword = results.getString("Password");
                Timestamp userCreatedDate = results.getTimestamp("Create_Date");
                String userCreatedBy = results.getString("Created_By");
                Timestamp userUpdatedDate = results.getTimestamp("Last_Update");
                String userUpdatedBy = results.getString("Last_Updated_By");

                User u = new User(userId, userName, userPassword, userCreatedDate, userCreatedBy, userUpdatedDate, userUpdatedBy);
                userList.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    };


}