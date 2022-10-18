package data_access;

import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.*;

/**
 * Class for accessing the SQL database for Country AND First Level Division information.
 *
 * <p>
 * This class is tasked with all of the database access and modification the mySQL queries pertaining to Countries
 * AND First Level Divisions.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */

public class CountryDAO {

    /**
     * Method for grabbing all the Countries.
     *
     * <p>
     *     This method uses an SQL query to ascertain all of the Country data stored in said database and
     *     instantiate new Country objects to use.
     * </p>
     *
     * @return The list of Countries garnered from the SQL query
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int countryId = results.getInt("Country_ID");
                String countryName = results.getString("Country");
                Timestamp countryCreateDate = results.getTimestamp("Create_Date");
                String countryCreator = results.getString("Created_By");
                Timestamp countryUpdatedDate = results.getTimestamp("Last_Update");
                String countryUpdater = results.getString("Last_Updated_By");

                Country c = new Country(countryId, countryName, countryCreateDate, countryCreator, countryUpdatedDate, countryUpdater);
                countryList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return countryList;
    }

    /**
     * Method for grabbing all the First Level Divisions.
     *
     * <p>
     * This method uses an SQL query to ascertain all of the First Level Division data stored in said database and
     * instantiate new First Level Division objects to use.
     * </p>
     *
     * @return The list of First Level Divisions garnered from the SQL query
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        ObservableList<FirstLevelDivision> division = FXCollections.observableArrayList();


        try {
            String query = "SELECT * FROM first_level_divisions;";      /*Sets what you would like the SQL query to entail */
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);    /* Puts the query into a prepared statement */
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                int divId = results.getInt("Division_ID");
                String divName = results.getString("Division");
                Timestamp divCreateDate = results.getTimestamp("Create_Date");
                String divCreatedBy = results.getString("Created_By");
                Timestamp divUpdateDate = results.getTimestamp("Last_Update");
                String divUpdateBy = results.getString("Last_Updated_By");
                int divCountryId = results.getInt("Country_ID");

                FirstLevelDivision d = new FirstLevelDivision(divId, divName, divCreateDate, divCreatedBy, divUpdateDate, divUpdateBy, divCountryId);
                division.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }


    /**
     * Method for grabbing all the First Level Divisions specific to a Country.
     *
     * <p>
     * This method uses an SQL query to ascertain all of the First Level Divisions that are in a specific
     * a selected country.
     * </p>
     *
     * @param countryId The country ID that the locations are needed from
     * @return The list of First Level Divisions garnered from the SQL query
     */
    public static ObservableList<FirstLevelDivision> getCountryDivisions(int countryId) throws SQLException {

        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();

        String query = "SELECT * FROM first_level_divisions WHERE Country_ID = ?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        ps.setInt(1, countryId);
        ResultSet results = ps.executeQuery();


        while (results.next()) {
            int divId = results.getInt("Division_ID");
            String divName = results.getString("Division");
            Timestamp divCreateDate = results.getTimestamp("Create_Date");
            String divCreatedBy = results.getString("Created_By");
            Timestamp divUpdateDate = results.getTimestamp("Last_Update");
            String divUpdateBy = results.getString("Last_Updated_By");
            int divCountryId = results.getInt("Country_ID");

            FirstLevelDivision d = new FirstLevelDivision(divId, divName, divCreateDate, divCreatedBy, divUpdateDate, divUpdateBy, divCountryId);
            divisions.add(d);
        }

        return divisions;
    }

}
