package oscar.model;

import java.util.HashMap;
import oscar.persistance.DbRecord;
import oscar.persistance.DbRecordable;

/**
 *
 * @author sujan
 */
public class Branch extends DbRecord implements DbRecordable {

    private int branchId;
    private String location;
    private String country;
    private static String TABLE = "branch";
    
    /*
     * Setter and getters
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

    /**
     * setup constructor
     */
    public Branch() {
        super(TABLE);
    }

    /**
     * Class contuctor
     * @param branchId branch id
     * @param location location 
     * @param country country
     */
    public Branch(int branchId, String location, String country) {
        super(TABLE);
        this.branchId = branchId;
        this.location = location;
        this.country = country;
    }

    /**
     * convert object to hashmap
     * @return The object represented with an HasMap
     */
    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> recordHashMap = new HashMap<String, String>();
        if (this.branchId > 0) {
            recordHashMap.put("branchId", this.branchId + "");
        }
        recordHashMap.put("location", this.location);
        recordHashMap.put("country", this.country);
        return recordHashMap;
    }
    
    /**
     * add record
     * @return The success of the operation
     */
    @Override
    public boolean add(){
        return this.add(this.toHashMap());
    }
    
    /**
     * delete record
     * @return The success of the operation
     */
    @Override
    public boolean delete(){
        if(this.branchId<=0)
            return false;
        return this.deleteBy("branchId", this.branchId+"");
    }
    /**
     * update record
     * @return The success of the operation
     */
    @Override
    public boolean update(){
       if(this.branchId<=0)
            return false;
       return this.updateBy(this.toHashMap(), "branchId", this.branchId+"");
    }

    /**
     * Converts HashMap to object
     * @param objHashMap
     * @return the converted object
     */
    @Override
    public Object toObject(HashMap<String,String> objHashMap) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
