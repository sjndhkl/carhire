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
public class MySqlConnection implements DbConnectable {
    
    private static java.sql.Connection connection;
    private String url;
    private String username;
    private String password;
    
    public MySqlConnection(String url, String username, String password){
        this.url = url;
        this.username=username;
        this.password=password;
    }

    static
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public java.sql.Connection getConnection() {
        try {
                if(connection==null || connection.isClosed()) {

                        connection = DriverManager.getConnection(this.url, this.username, this.password);

                }     
        } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    @Override
    public void closeConnection(){
        
        try{
            if(connection!=null && !connection.isClosed()){
                connection.close();
                connection = null;
            }
        }catch(Exception ex){
            //nothing
        }
        
    }
    
    
    
    
    
}
