/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

/**
 *
 * @author sujan
 */
public interface Connectable {

    java.sql.Connection getConnection(String url,String username,String password);
    
}
