package oscar.persistance;

import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sujan
 */
public class MySqlConnection implements DbConnectable {

    /** URL of the database*/
    public static String CONNECTION_URL = "jdbc:mysql://localhost:3306/carhire";
    /** Database username */
    public static String USERNAME = "root";
    /** Password of the database*/
    public static String PASSWORD = "sujand";
    /** Database connection */
    private static java.sql.Connection connection;

    /** Checks the driver */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the connection
     * @return Connection
     */
    @Override
    public java.sql.Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    /**
     * 
     */
    @Override
    public void closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (Exception ex) {
            //nothing
        }
    }
}
