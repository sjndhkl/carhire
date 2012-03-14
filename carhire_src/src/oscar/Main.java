/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar;

import java.util.ArrayList;
import java.util.HashMap;
import oscar.model.CategoryModel;
import oscar.persistance.DbRecord;


/**
 *
 * @author sujan
 */
public class Main {
    
    public static void main(String... args){

        CategoryModel record = new CategoryModel();
        try{
            int count = (Integer) record.find(DbRecord.FindAction.COUNT);
            System.out.println("Total Records were : "+count);
            
            ArrayList<HashMap<String,String>> rs = (ArrayList<HashMap<String,String>>) record.find();
            
           // System.out.println("No. of Records :"+rs.size());
            
            for(HashMap<String,String> row:rs){
                
                for(String key:row.keySet()){
                    System.out.println(key +" : "+row.get(key));
                }
                
            }
            rs = null;
            
        }catch(Exception e){
            System.out.println(e.getCause());
        }
        
    }
    
    
}
