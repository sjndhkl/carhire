/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sujan
 */
public abstract class DbRecord {
    
    protected DbConnectable connectionObject;
    
    public DbRecord(){
         this.connectionObject = DbConnectionFactory.connect(DbConnectionFactory.Database.MYSQL);
    }
    
    public ArrayList<HashMap<String,String>> query(String sql){
        
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
        try{
            Statement stmt = this.connectionObject.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
             while(rs.next()){
                 HashMap<String,String> row = new HashMap<String,String>();
                 for(int i=1;i<=metaData.getColumnCount();i++)
                    row.put(metaData.getColumnName(i), rs.getString(i));
                 result.add(row);
            }
            stmt.close();
            this.connectionObject.closeConnection();
        }catch(Exception ex){
            result = null;
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
    public abstract Object find();
    public abstract boolean delete(int id);
    public abstract boolean update(Object obj);
    public abstract boolean add(Object obj);
    
    
    
}
