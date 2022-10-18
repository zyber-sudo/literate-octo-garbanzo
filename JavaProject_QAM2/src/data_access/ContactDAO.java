package data_access;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for accessing the SQL database for Contact information.
 *
 * <p>
 * This class is tasked with all of the database access and modification the mySQL queries pertaining to Contacts.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class ContactDAO {

    /**
     * Method for grabbing all the Contacts.
     *
     * <p>
     *     This method uses an SQL query to ascertain all of the contact data stored in said database and
     *     instantiate new contacts objects to use.
     * </p>
     *
     * @return The list of contacts garnered from the SQL query
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        //  Code that goes to the database:

        try {
            String query = "SELECT * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int contactId = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");
                String contactEmail = results.getString("Email");


                Contact c = new Contact(contactId, contactName, contactEmail);
                contactList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return contactList;
    }

}
