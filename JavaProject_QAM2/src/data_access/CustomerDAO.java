package data_access;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * Class for accessing the SQL database for Customer information.
 *
 * <p>
 * This class is tasked with all of the database access and modification the mySQL queries pertaining to Customers.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class CustomerDAO {

    /**
     * Method for grabbing all the Customers.
     *
     * <p>
     *     This method uses an SQL query to ascertain all of the customer data stored in said database and
     *     instantiate new customer objects to use.
     * </p>
     *
     * @return The list of contacts garnered from the SQL query
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> custList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int customerId = results.getInt("Customer_ID");
                String customerName = results.getString("Customer_Name");
                String customerAddress = results.getString("Address");
                String customerZip = results.getString("Postal_Code");
                String customerPhone = results.getString("Phone");
                Timestamp customerCreatedDate = results.getTimestamp("Create_Date");
                String customerCreatedBy = results.getString("Created_By");
                Timestamp customerUpdatedDate = results.getTimestamp("Last_Update");
                String customerUpdatedBy = results.getString("Last_Updated_By");
                int customerDivisionId = results.getInt("Division_ID");

                Customer c = new Customer(customerId, customerName, customerAddress, customerZip, customerPhone, customerCreatedDate, customerCreatedBy, customerUpdatedDate, customerUpdatedBy, customerDivisionId);
                custList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return custList;
    }

    /**
     * Method for grabbing all the Customers with a join for Countries and First Level Division.
     *
     * <p>
     * This method like the <i>getAllCustomers</i> method, does all of the same tasks with the addition of using a join
     * statement to grab Countries and First Level Divisions names for the users ease of use in the table views.
     * </p>
     *
     * @return The list of modified customer garnered from the SQL query
     */
    public static ObservableList<Customer> getAllCustomersModified() {
        ObservableList<Customer> custList = FXCollections.observableArrayList();

        try {

            String query = "SELECT customers.*, countries.Country, first_level_divisions.Division FROM customers JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int customerId = results.getInt("Customer_ID");
                String customerName = results.getString("Customer_Name");
                String customerAddress = results.getString("Address");
                String customerZip = results.getString("Postal_Code");
                String customerPhone = results.getString("Phone");
                Timestamp customerCreatedDate = results.getTimestamp("Create_Date");
                String customerCreatedBy = results.getString("Created_By");
                Timestamp customerUpdatedDate = results.getTimestamp("Last_Update");
                String customerUpdatedBy = results.getString("Last_Updated_By");
                int customerDivisionId = results.getInt("Division_ID");
                String customerCountry = results.getString("Country");
                String customerDivisionName = results.getString("Division");

                Customer c = new Customer(customerId, customerName, customerAddress, customerZip, customerPhone, customerCreatedDate, customerCreatedBy, customerUpdatedDate, customerUpdatedBy, customerDivisionId, customerCountry, customerDivisionName);
                custList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return custList;
    }


    /**
     * Method for inserting a new customer.
     *
     * <p>
     * This method uses an SQL query to insert (add) a customer into the database.
     * </p>
     *
     * @param customerName       Name of customer to be inserted
     * @param customerAddress    Address of customer to be inserted
     * @param customerZip        Postal code of customer to be inserted
     * @param customerPhone      Phone number of customer to be inserted
     * @param customerDivisionId Division ID number attached to the customer being added
     * @param currentUser        The current user of the program
     * @return Called as an integer, the true purpose of the return value is to execute the SQL query after it is set.
     */
    public static int insertCustomer(String customerName, String customerAddress, String customerZip, String customerPhone, int customerDivisionId, String currentUser) {
        try {

            long currentTime = System.currentTimeMillis();
            Timestamp time = new Timestamp(currentTime);

            String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES( ?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);

            ps.setString(1, customerName);  //Customer Name
            ps.setString(2, customerAddress);   //Customer Address
            ps.setString(3, customerZip);   //Customer Zip
            ps.setString(4, customerPhone);   //Customer Phone Number
            ps.setTimestamp(5, time);
            ps.setString(6, currentUser);
            ps.setTimestamp(7, time);
            ps.setString(8, currentUser);
            ps.setInt(9, customerDivisionId);


            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Method for modifying a new customer.
     *
     * <p>
     * This method uses an SQL query to update (modify) a customer into the database.
     * </p>
     *
     * @param customerId      ID of customer being modified
     * @param customerName    Name of customer being modified
     * @param customerAddress Address of customer being modified
     * @param customerZip     Postal code of customer being modified
     * @param customerPhone   Phone number of customer being modified
     * @param currentUser     Current user of the program
     * @param divisionId      Division ID number attached to the customer being modified
     * @return Called as an integer, the true purpose of the return value is to execute the SQL query after it is set.
     */
    public static int modifyCustomer(int customerId, String customerName, String customerAddress, String customerZip, String customerPhone, String currentUser, int divisionId) {
        try {
            long currentTime = System.currentTimeMillis();
            Timestamp time = new Timestamp(currentTime);

            String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(8, customerId);

            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerZip);
            ps.setString(4, customerPhone);
            ps.setTimestamp(5, time);
            ps.setString(6, currentUser);
            ps.setInt(7, divisionId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Method for deleting a customer
     *
     * <p>
     * This method uses an SQL query to delete a customer from the database.
     * </p>
     *
     * @param customerId ID of the customer to be deleted
     * @return Called as an integer, the true purpose of the return value is to execute the SQL query after it is set.
     */
    public static int deleteCustomer(int customerId) {
        try {
            String query = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, customerId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
