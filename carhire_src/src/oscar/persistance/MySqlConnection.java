/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sujan
 */
public class MySqlConnection implements Connectable {
    
    private static java.sql.Connection connection;

    static
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public java.sql.Connection getConnection(String url, String username, String password) {
        
        if(connection==null){
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        return connection;
    }
    
    
    
    
    
}
