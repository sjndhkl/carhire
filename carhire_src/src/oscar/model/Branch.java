package oscar.model;

import java.util.HashMap;
import oscar.MVC.DbRecord;
import oscar.persistance.DbRecordable;
import oscar.util.Utility;

/**
 *
 * @author sujan
 */
public class Branch extends DbRecord implements DbRecordable {

    private int branchId;
    private String location;
    private String country;
    //the name of the table in the DB
    private static String TABLE = "branch";

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
     * @param location location of the branch
     * @param country country of the branch
     */
    public Branch(int branchId, String location, String country) {
        super(TABLE);
        this.branchId = branchId;
        this.location = location;
        this.country = country;
    }
 
    /**
     * add record
     * @return The success of the operation
     */
    @Override
    public boolean add(){
        return this.add(Utility.convertToHashMap(this));
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
       return this.updateBy(Utility.convertToHashMap(this), "branchId", this.getBranchId()+"");
    }
    
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
}
