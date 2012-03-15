package oscar.model;

import java.util.HashMap;
import oscar.persistance.DbRecord;

/**
 *
 * @author sujan
 */
public class Branch extends DbRecord {

    private int branchId;
    private String location;
    private String country;
    private static String TABLE = "branch";
    
    /*
     * setters and getters
     */

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*
     * setup constructor
     */
    public Branch() {
        super(TABLE);
    }

    public Branch(int branchId, String location, String country) {
        super(TABLE);
        this.branchId = branchId;
        this.location = location;
        this.country = country;
    }

    /*
     * convert object to hashmap
     */
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> recordHashMap = new HashMap<String, String>();
        if (this.branchId > 0) {
            recordHashMap.put("branchId", this.branchId + "");
        }
        recordHashMap.put("location", this.location);
        recordHashMap.put("country", this.country);
        return recordHashMap;
    }
    
    /*
     * add record
     */
    public boolean add(){
        return this.add(this.toHashMap());
    }
    
    /*
     * delete record
     */
    public boolean delete(){
        if(this.branchId<=0)
            return false;
        return this.deleteBy("branchId", this.branchId+"");
    }
    
    /*
     * update record
     */
    public boolean update(){
       if(this.branchId<=0)
            return false;
       return this.updateBy(this.toHashMap(), "branchId", this.branchId+"");
    }
}
