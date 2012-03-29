/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

/**
 *
 * @author sujan
 */
public class DependentDbRecord extends DbRecord {
    //SELECT COLUMN_NAME from information_schema.KEY_COLUMN_USAGE where constraint_name = "PRIMARY" and table_name = "branch" and table_schema="carhire"
    
     public DependentDbRecord(String table){
            super(table);
        }
    
}
