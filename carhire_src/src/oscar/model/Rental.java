package oscar.model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import oscar.MVC.DbRecord;
import oscar.persistance.DbRecordable;
import oscar.util.Utility;

/**
 * @author Stefano
 * 
 * Class Rental representing the car hire and booking
 */
public class Rental extends DbRecord implements DbRecordable  {

    /** Database table name*/
    public static String TABLE = "rental";
    //TODO: this entity has two FK in the DB
    /**
     * 
     */
    public static String FK = "";
    private String referenceCode;
    private String startDatetime;
    private String endDateTime;
    private String carPlate;
    private int customerid;
    private float amountPaid;
    private boolean isBooking;
    private float depositAmount;
    private boolean isChauffeur;
    private boolean isInsured;

    /**
     * 
     * @return
     */
    public String getReferenceCode() {
        return referenceCode;
    }

    /**
     * 
     * @param referenceCode
     */
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
    private int rentalId;
    

    /**
     * 
     * @return
     */
    public boolean getIsChauffeur() {
        return isChauffeur;
    }

    /**
     * 
     * @param isChauffeur
     */
    public void setIsChauffeur(boolean isChauffeur) {
        this.isChauffeur = isChauffeur;
    }

    /**
     * 
     * @return
     */
    public boolean getIsInsured() {
        return isInsured;
    }

    /**
     * 
     * @param isInsured
     */
    public void setIsInsured(boolean isInsured) {
        this.isInsured = isInsured;
    }
    

    /**
     * 
     * @return
     */
    public float getAmountPaid() {
        return amountPaid;
    }

    /**
     * 
     * @param amountPaid
     */
    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     * 
     * @return
     */
    public String getCarPlate() {
        return carPlate;
    }

    /**
     * 
     * @param carPlateNumber
     */
    public void setCarPlate(String carPlateNumber) {
        this.carPlate = carPlateNumber;
    }

    /**
     * 
     * @return
     */
    public int getCustomerid() {
        return customerid;
    }

    /**
     * 
     * @param customerid
     */
    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    /**
     * 
     * @return
     */
    public float getDepositAmount() {
        return depositAmount;
    }

    /**
     * 
     * @param depositAmount
     */
    public void setDepositAmount(float depositAmount) {
        this.depositAmount = depositAmount;
    }

    /**
     * 
     * @return
     */
    public String getEndDateTime() {
        return endDateTime;
    }

    /**
     * 
     * @param endDateTime
     */
    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * 
     * @return
     */
    public boolean getIsBooking() {
        return isBooking;
    }

    /**
     * 
     * @param isBooking
     */
    public void setIsBooking(boolean isBooking) {
        this.isBooking = isBooking;
    }

    /**
     * 
     * @return
     */
    public int getRentalId() {
        return rentalId;
    }

    /**
     * 
     * @param rentalId
     */
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    /**
     * 
     * @return
     */
    public String getStartDatetime() {
        return startDatetime;
    }

    /**
     * 
     * @param startDatetime
     */
    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }
    
    
    
    
    
    /**
     * 
     */
    public Rental() {
        super(TABLE);
    }
    
    /**
     * @param referenceCode 
     * @param startDatetime
     * @param endDateTime
     * @param carPlateNumber
     * @param customerid
     * @param amountPaid
     * @param isBooking
     * @param depositAmount
     */
    public Rental(String referenceCode, String startDatetime, String endDateTime,
            String carPlateNumber, int customerid, float amountPaid,
            boolean isBooking, float depositAmount) {
        super(TABLE);
        this.referenceCode = referenceCode;
        this.startDatetime = startDatetime;
        this.endDateTime = endDateTime;
        this.carPlate = carPlateNumber;
        this.customerid = customerid;
        this.amountPaid = amountPaid;
        this.isBooking = isBooking;
        this.depositAmount = depositAmount;
    }
    
    public HashMap<String,String> getRentalRecordByReferenceNumber(String referenceNumber){
        HashMap<String,String> record = null;
        
        String sql = "select person.*,rental.* from rental inner join person on person.personId = rental.customerId where rental.isBooking = 1 and rental.referenceCode = '"+referenceNumber+"'";
        ArrayList<HashMap<String,String>> records = this.query(sql);
        if(records!=null){
            if(records.size()>0){
                return records.get(0);
            }
        }
        return record;
    }

    @Override
    public boolean add() {
        HashMap<String,String> record = Utility.convertToHashMap(this);
        return super.add(record);
    }

    /**
     * 
     * @return
     */
    @Override
    public boolean delete() {
        return false;
    }

    /**
     * 
     * @return
     */
    @Override
    public boolean update() {
        HashMap<String,String> record = Utility.convertToHashMap(this);
        return super.updateBy(record, "rentalId", this.rentalId+"");
    }

    /**
     * 
     * @return
     */
    @Override
    public TableModel getTableModel() {
        
        ArrayList<HashMap<String, String>> dependencies = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> customerDep = new HashMap<String, String>();

        customerDep.put("table", "person");
        customerDep.put("pk", "personId");
        customerDep.put("joinType", "join");

        //personDep.put("joinTo", "");
        customerDep.put("fk", "customerId");
        dependencies.add(customerDep);

        ArrayList<HashMap<String, String>> map = this.queryDependent(dependencies, "*", "*");
        
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Ref. code", "Surname", "Start", "End", "Chauffeur", "Insurance"}, 0);
        for (HashMap<String, String> row : map) {
            model.addRow(new Object[]{
                        row.get("rentalId"),
                        row.get("referenceCode"),
                        row.get("surname"),
                        row.get("startDatetime"),
                        row.get("endDatetime"),
                        (row.get("isInsured").contains("1")) ? true : false,
                        (row.get("isChauffeur").contains("1")) ? true : false,
                    });
        }
        return model;
    }

    /**
     * 
     * @param filters
     * @return
     */
    @Override
    public TableModel getTableModel(HashMap<String, String> filters) {
        
        ArrayList<HashMap<String, String>> dependencies = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> customerDep = new HashMap<String, String>();

        customerDep.put("table", "person");
        customerDep.put("pk", "personId");
        customerDep.put("joinType", "join");

        //personDep.put("joinTo", "");
        customerDep.put("fk", "customerId");
        dependencies.add(customerDep);

        ArrayList<HashMap<String, String>> map = this.queryDependentLike(dependencies, filters, "*", "*");
        
        
        
        
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Ref. code", "Surname", "Start", "End", "Chauffeur", "Insurance"}, 0);
        for (HashMap<String, String> row : map) {
            model.addRow(new Object[]{
                        row.get("rentalId"),
                        row.get("referenceCode"),
                        row.get("surname"),
                        row.get("startDatetime"),
                        row.get("endDatetime"),
                        (row.get("isInsured").contains("1")) ? true : false,
                        (row.get("isChauffeur").contains("1")) ? true : false,
                    });
        }
        return model;
    }
}
