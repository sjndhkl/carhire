/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar;

import java.util.ArrayList;
import java.util.HashMap;
import oscar.model.CategoryModel;


/**
 *
 * @author sujan
 */
public class Main {
    
    public static void main(String... args){

        CategoryModel record = new CategoryModel();
        try{
            ArrayList<HashMap<String,String>> rs = record.query("select * from "+CategoryModel.useTable);
            
            System.out.println("No. of Records :"+rs.size());
            
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
