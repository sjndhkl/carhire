/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.model;

import java.util.HashMap;
import oscar.persistance.DbRecord;

/**
 *
 * @author sujan
 */
public class BranchModel extends DbRecord {
    
    private int branchId;

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    private String location;
    private String country;
    private static String TABLE = "branch";
    
    public BranchModel(){
        super(TABLE);
    }
    
    public BranchModel(int branchId, String location, String country) {
            super(TABLE);
            this.branchId = branchId;
            this.location = location;
            this.country = country;
    }
    
    public HashMap<String,String> toHashMap(){
        HashMap<String,String> recordHashMap = new HashMap<String, String>();
        if(this.branchId>0)
            recordHashMap.put("branchId", this.branchId+"");
        recordHashMap.put("location", this.location);
        recordHashMap.put("country",this.country);        
        return recordHashMap;
    }
   
    
}
