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
    public int getBranchId() {
        return branchId;
    }
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getCountry() {
        return country;
    }

    /**
     * setup constructor
     */
    public Branch() {
        super(TABLE);
        this.useTable = TABLE;
    }

    /**
     * setup constructor
     * @param PkValue Value of the primary key
     */
    public Branch(String PkValue) {
        super(TABLE);
        this.useTable = TABLE;
        HashMap<String, String> attributes = this.findByPK(PkValue);
        this.branchId = Integer.parseInt(PkValue);
        this.location = attributes.get("location");
        this.country = attributes.get("country");        
    }

    /**
     * Class constructor
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
        if (this.getBranchId() > 0) {
            recordHashMap.put("branchId", this.getBranchId() + "");
        }
        recordHashMap.put("location", this.getLocation());
        recordHashMap.put("country", this.getCountry());
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
        if(this.getBranchId()<=0)
            return false;
        return this.deleteBy("branchId", this.getBranchId()+"");
    }
    /**
     * update record
     * @return The success of the operation
     */
    @Override
    public boolean update(){
       if(this.getBranchId()<=0)
            return false;
       return this.updateBy(this.toHashMap(), "branchId", this.getBranchId()+"");
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
