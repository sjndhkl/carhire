/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

/**
 *
 * @author sujan
 */
public interface DbConnectable {

    java.sql.Connection getConnection();
    void closeConnection();
    
}
