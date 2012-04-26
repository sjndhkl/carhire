package oscar.model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import oscar.MVC.DbRecord;
import oscar.persistance.DbRecordable;

/**
 * @author Stefano
 * 
 * Class Rental representing the car hire and booking
 */
public class Rental extends DbRecord implements DbRecordable  {

    /** Database table name*/
    public static String TABLE = "rental";
    //TODO: this entity has two FK in the DB
    public static String FK = "";
    private String referenceCode;
    private String startDatetime;
    private String endDateTime;
    private String carPlateNumber;
    private int customerid;
    private float amountPaid;
    private boolean isBooking;
    private float depositAmount;
    private boolean isChauffeur;
    private boolean isInsured;
    private int rentalId;

    public boolean getIsChauffeur() {
        return isChauffeur;
    }

    public void setIsChauffeur(boolean isChauffeur) {
        this.isChauffeur = isChauffeur;
    }

    public boolean getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(boolean isInsured) {
        this.isInsured = isInsured;
    }
    

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public float getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(float depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isIsBooking() {
        return isBooking;
    }

    public void setIsBooking(boolean isBooking) {
        this.isBooking = isBooking;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }
    
    
    
    
    
    public Rental() {
        super(TABLE);
    }
    
    /**
     * @param rentalId
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
        this.carPlateNumber = carPlateNumber;
        this.customerid = customerid;
        this.amountPaid = amountPaid;
        this.isBooking = isBooking;
        this.depositAmount = depositAmount;
    }

    @Override
    public boolean add() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TableModel getTableModel() {
        ArrayList<HashMap<String, String>> map = this.findAll();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Start", "End", "Chauffeur", "Insurance"}, 0);
        for (HashMap<String, String> row : map) {
            model.addRow(new Object[]{
                        row.get("rentalId"),
                        row.get("startDatetime"),
                        row.get("endDatetime"),
                        (row.get("isInsured").contains("1")) ? true : false,
                        (row.get("isChauffeur").contains("1")) ? true : false,
                        //row.get("isInsured"),
                        //row.get("isChauffeur"),
                    });
        }
        return model;
    }

    @Override
    public TableModel getTableModel(HashMap<String, String> filters) {
        ArrayList<HashMap<String, String>> map = this.findAllLike(filters);
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Start", "End", "Chauffeur", "Insurance"}, 0);
        for (HashMap<String, String> row : map) {
            model.addRow(new Object[]{
                        row.get("rentalId"),
                        row.get("startDatetime"),
                        row.get("endDatetime"),
                        (row.get("isInsured").contains("1")) ? true : false,
                        (row.get("isChauffeur").contains("1")) ? true : false,
                        //row.get("isInsured"),
                        //row.get("isChauffeur"),
                    });
        }
        return model;
    }
}
