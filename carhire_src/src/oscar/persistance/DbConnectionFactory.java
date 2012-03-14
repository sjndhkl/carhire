/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

/**
 *
 * @author sujan
 */
public class DbConnectionFactory {
    
    public static String CONNECTION_URL = "jdbc:mysql://localhost:3306/wiki";
    public static String DB_USERNAME = "root";
    public static String DB_PASSWORD = "sujand";
    
    public enum Database{
        MYSQL
    }
    
    
    
    public static MySqlConnection connect(Database dbType){
        
        switch(dbType){
            case MYSQL: return new MySqlConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD); 
        }
        return null;
    }
    
    
}
