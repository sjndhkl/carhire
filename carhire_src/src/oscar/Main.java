/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar;

import oscar.persistance.Connection;


/**
 *
 * @author sujan
 */
public class Main {
    
    public static void main(String... args){
        
        java.sql.Connection conn = Connection.connect(Connection.Database.MYSQL);
        if(conn!=null){
            System.out.println("connected");
        }else{
            System.out.println("Not connected");
        }
        
    }
    
    
}
