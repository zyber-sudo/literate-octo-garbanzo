package data_access;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class for accessing the SQL database for appointment information.
 *
 * <p>
 * This class is tasked with all of the database access and modification the mySQL queries pertaining
 * to Appointments.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */

public class AppointmentDAO {


    /**
     * Method for grabbing all the appointments.
     *
     * <p>
     *     This method uses an SQL query to ascertain all of the appointment data stored in said database and
     *     instantiate new appointment objects to use.
     * </p>
     *
     * @return The list of appointments garnered from the SQL query
     */
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int appointmentId = results.getInt("Appointment_ID");
                String appointmentTitle = results.getString("Title");
                String appointmentDescription = results.getString("Description");
                String appointmentLocation = results.getString("Location");
                String appointmentType = results.getString("Type");
                Timestamp appointmentStartDate = results.getTimestamp("Start");
                Timestamp appointmentEndDate = results.getTimestamp("End");
                Timestamp appointmentCreateDate = results.getTimestamp("Create_Date");
                String appointmentCreatedBy = results.getString("Created_By");
                Timestamp appointmentUpdatedDate = results.getTimestamp("Last_Update");
                String appointmentUpdater = results.getString("Last_Updated_By");
                int appointmentCustomerId = results.getInt("Customer_ID");
                int appointmentUserId = results.getInt("User_ID");
                int appointmentContactId = results.getInt("Contact_ID");

                Appointment c = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContactId, appointmentType, appointmentStartDate, appointmentEndDate, appointmentCreateDate, appointmentCreatedBy, appointmentUpdatedDate, appointmentUpdater, appointmentCustomerId, appointmentUserId);
                appList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appList;
    }

    /**
     * Method for grabbing all the appointments with a join for contact names.
     *
     * <p>
     * This method like the <i>getAllAppointment</i> method, does all of the same tasks with the addition of using a join
     * statement to grab the contact names for the users ease of use in the table views.
     * </p>
     *
     * @return The list of modified appointments garnered from the SQL query
     */
    public static ObservableList<Appointment> getAllAppointmentsModified() {

        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        try {
            String query = "SELECT appointments.*, contacts.Contact_Name FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int appointmentId = results.getInt("Appointment_ID");
                String appointmentTitle = results.getString("Title");
                String appointmentDescription = results.getString("Description");
                String appointmentLocation = results.getString("Location");
                String appointmentType = results.getString("Type");
                Timestamp appointmentStartDate = results.getTimestamp("Start");
                Timestamp appointmentEndDate = results.getTimestamp("End");
                Timestamp appointmentCreateDate = results.getTimestamp("Create_Date");
                String appointmentCreatedBy = results.getString("Created_By");
                Timestamp appointmentUpdatedDate = results.getTimestamp("Last_Update");
                String appointmentUpdater = results.getString("Last_Updated_By");
                int appointmentCustomerId = results.getInt("Customer_ID");
                int appointmentUserId = results.getInt("User_ID");
                int appointmentContactId = results.getInt("Contact_ID");
                String appointmentContactName = results.getString("Contact_Name");

                Appointment c = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContactId, appointmentType, appointmentStartDate, appointmentEndDate, appointmentCreateDate, appointmentCreatedBy, appointmentUpdatedDate, appointmentUpdater, appointmentCustomerId, appointmentUserId, appointmentContactName);
                appList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appList;
    }


    /**
     * Method for inserting an appointment.
     *
     * <p>
     * This method uses an SQL query to insert (add) an appointment into the database.
     * </p>
     *
     * @param appointmentTitle       The name of the new appointment being inserted
     * @param appointmentDescription The description of the new appointment being inserted
     * @param appointmentLocation    The location of the new appointment being inserted
     * @param appointmentType        The type of the new appointment being inserted
     * @param appointmentStart       The new appointments start time
     * @param appointmentEnd         The new appointments start time
     * @param currentUser            The current user that is making the new appointment
     * @param customerId             The ID of the customer attached to the new appointment
     * @param userId                 The ID of the user attached to the new appointment
     * @param contactId              The ID of the contact attached to the new appointment
     * @return Called as an integer, the true purpose of the return value is to execute the SQL query after it is set.
     * @see controller.AddAppointmentController
     */
    public static int insertAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, String currentUser, int customerId, int userId, int contactId) {
        try {
            long currentTime = System.currentTimeMillis();
            Timestamp time = new Timestamp(currentTime);

            String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);

            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            ps.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            ps.setTimestamp(7, time);
            ps.setString(8, currentUser);
            ps.setTimestamp(9, time);
            ps.setString(10, currentUser);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);


            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Method for deleting an appointment.
     *
     * <p>
     * This method uses an SQL query to delete an appointment from the database.
     * </p>
     *
     * @param appointmentId The ID of the appointment to be deleted in the database
     * @return Called as an integer, the true purpose of the return value is to execute the SQL query after it is set.
     * @see controller.MainScreenController
     */
    public static int deleteAppointment(int appointmentId) {
        try {
            String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, appointmentId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Method for modifying an appointment.
     *
     * <p>
     * This method uses an SQL query to update (modify) an appointment in the database.
     * </p>
     *
     * @param appointmentId          The ID of the appointment being modified
     * @param appointmentTitle       The name of the appointment being modified
     * @param appointmentDescription The description of the appointment being modified
     * @param appointmentLocation    The location of the appointment being modified
     * @param appointmentType        The type of the appointment being modified
     * @param appointmentStart       The start time of the appointment being modified
     * @param appointmentEnd         The end time of the appointment being modified
     * @param currentUser            The current user modifying the appointment
     * @param customerId             The ID of the customer attached to the modified appointment
     * @param userId                 The ID of the user attached to the modified appointment
     * @param contactId              The ID of the contact attached to the modified appointment
     * @return Called as an integer, the true purpose of the return value is to execute the SQL query after it is set.
     * @see controller.ModifyAppointmentController
     */
    public static int modifyAppointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, String currentUser, int customerId, int userId, int contactId) {
        try {
            long currentTime = System.currentTimeMillis();
            Timestamp time = new Timestamp(currentTime);

            String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(12, appointmentId);

            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            ps.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            ps.setTimestamp(7, time);
            ps.setString(8, currentUser);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);


            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Method that filters the appointments
     *
     * <p>
     * This method uses SQL queries to filter the table view of the <i>Main Screen</i>. It uses a conditional to
     * determined the query instead of two methods.
     * </p>
     *
     * @param filterType The string that sets the query entered into the database.
     * @return The list of now filtered appointments
     */
    public static ObservableList<Appointment> filterAppointmentsByWeek(String filterType) {

        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = null;

            if (filterType.equals("YW")) {
                String query = "SELECT * FROM appointments WHERE YEARWEEK(appointments.start) = YEARWEEK(NOW()) ORDER BY YEARWEEK(appointments.start) ASC;";
                ps = JDBC.getConnection().prepareStatement(query);
            } else if (filterType.equals("MM")) {
                String query = "SELECT * FROM appointments WHERE MONTH(appointments.start) = MONTH(NOW()) ORDER BY MONTH(appointments.start) ASC;";
                ps = JDBC.getConnection().prepareStatement(query);
            }


            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int appointmentId = results.getInt("Appointment_ID");
                String appointmentTitle = results.getString("Title");
                String appointmentDescription = results.getString("Description");
                String appointmentLocation = results.getString("Location");
                String appointmentType = results.getString("Type");
                Timestamp appointmentStartDate = results.getTimestamp("Start");
                Timestamp appointmentEndDate = results.getTimestamp("End");
                Timestamp appointmentCreateDate = results.getTimestamp("Create_Date");
                String appointmentCreatedBy = results.getString("Created_By");
                Timestamp appointmentUpdatedDate = results.getTimestamp("Last_Update");
                String appointmentUpdater = results.getString("Last_Updated_By");
                int appointmentCustomerId = results.getInt("Customer_ID");
                int appointmentUserId = results.getInt("User_ID");
                int appointmentContactId = results.getInt("Contact_ID");

                Appointment c = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContactId, appointmentType, appointmentStartDate, appointmentEndDate, appointmentCreateDate, appointmentCreatedBy, appointmentUpdatedDate, appointmentUpdater, appointmentCustomerId, appointmentUserId);
                appList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appList;
    }

}
