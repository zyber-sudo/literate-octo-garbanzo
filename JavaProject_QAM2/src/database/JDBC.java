
package database;

import java.sql.Connection;
import java.sql.DriverManager;

/*
    Database Location File
 */

/**
 * The JDBC class.
 * <p>
 * This class is solely responsible for the opening, maintaining, and closing
 * of the database connection.
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password

    /**
     * mySQL connection variable.
     * <p>The declaration of the connection variable used for connection controls of the mySQL database.</p>
     */
    public static Connection connection;  // Connection Interface

    /**
     * Database connection open method.
     * <p>This method takes the connection variables above and opens the connection to the mySQL database.</p>
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }


    /**
     * Method that get the open database connection.
     * <p>This method is tasked with accessing the open mySQL database connection once opened.</p>
     *
     * @return The sql connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Method that closes the database connection.
     * <p>This method closes the open mySQL database connection so that the connection does not remain open.</p>
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
