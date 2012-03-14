/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.model;

import java.util.ArrayList;
import java.util.HashMap;
import oscar.persistance.DbRecord;

/**
 *
 * @author sujan
 */
public class CategoryModel extends DbRecord {
    
    private String useTable = "categories";
    
    public String getTable(){
        return this.useTable;
    }
    
    public void setTable(String newTable){
        this.useTable = newTable;
    }
    
    @Override
    public Object find(){
        return this.find(FindAction.ALL);
    }

   
    public Object find(FindAction action) {
        ArrayList<HashMap<String,String>> rs = null;
        switch(action){
            case ALL:
                    rs = this.query("select * from "+this.useTable);
                    return rs;
            case COUNT:
                    rs = this.query("select count(*) as count from "+this.useTable);
                    for(HashMap<String,String> row:rs){

                        return Integer.parseInt(row.get("count"));

                    }
                    break;
        }
        
        return null;
        
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
