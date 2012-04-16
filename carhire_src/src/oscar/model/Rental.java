package oscar.model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import oscar.persistance.DbRecord;
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
    private int rentalId;
    private String startDatetime;
    private String endDateTime;
    private String carPlateNumber;
    private int customerid;
    private float amountPaid;
    private boolean isBooking;
    private float depositAmount;
    
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
    public Rental(int rentalId, String startDatetime, String endDateTime,
            String carPlateNumber, int customerid, float amountPaid,
            boolean isBooking, float depositAmount) {
        super(TABLE);
        this.rentalId = rentalId;
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
}
