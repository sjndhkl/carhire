/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author sujan
 */
public class DependentDbRecord extends DbRecord {
    //SELECT COLUMN_NAME from information_schema.KEY_COLUMN_USAGE where constraint_name = "PRIMARY" and table_name = "branch" and table_schema="carhire"
    
    protected String dependentTable;
    
     public DependentDbRecord(String table){
            super(table);
        }
     
     public void setDependentTable(String table){
         this.dependentTable = table;
     }
     
     public boolean add(HashMap<String, String> primaryHashMap,HashMap<String, String> secondaryHashMap) throws SQLException {

         try{
             
             this.connectionObject.getConnection().setAutoCommit(false);  
             
             
             
             Statement stmt = this.connectionObject.getConnection().createStatement();
             this.connectionObject.getConnection().commit();
         }catch(Exception e){
             this.connectionObject.getConnection().rollback();
         }finally{
             this.connectionObject.getConnection().close();
         }
         
        String cols = "";
        String values = "";
        int num_cols = objHashMap.size();
        int i = 1;
        for (String key : objHashMap.keySet()) {

            cols += key;
            values += "'" + objHashMap.get(key) + "'";
            if (i != num_cols) {
                cols += ",";
                values += ",";
            }

            i++;
        }
        String query = "insert into " + this.useTable + "(" + cols + ") values(" + values + ")";
        //System.out.println(query);
        return this.nonQuery(query);
    }
     
    
}
