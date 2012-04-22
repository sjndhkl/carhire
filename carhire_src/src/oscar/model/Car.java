package oscar.model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import oscar.MVC.DbRecord;

/**
 * @author Stefano
 * 
 * Class Car for the cars to hire
 */
public class Car extends DbRecord {
    private static String TABLE = "car";
    /** The PK of the table */
    private String plate;
    private String brand;
    private String model;
    private int year;
    private int mileage;
    private int lastServiceMiles;
    private String lastServiceDate;
    private int classId;
    private String color;
    private int branch;
    private String serviceMonths;
    private int serviceMiles;
    /**
     * Status of the car
     */
    public enum CarStatus {

        /** Ready to rent */
        READY,
        /** Cleaning due */
        CLEANING,
        /** Temporary hired */
        HIRED,
        /** Under service*/
        SERVICING,
        /** Unavailable*/
        UNAVAILABLE
    }
    //private CarStatus status;

    public Car() {
        super(TABLE);
    }
    /**
     * Class constructor
     * @param plateNumber
     * @param brand
     * @param model
     * @param year
     * @param mileage
     * @param lastServiceMileage
     * @param lastServiceDate
     * @param carClass
     * @param color
     * @param branch
     * @param servicePeriod
     * @param serviceMiles
     * @param status
     */
    public Car(String plateNumber, String brand, String model, int year,
            int mileage, int lastServiceMileage, String lastServiceDate,
            int carClass, String color, int branch, String servicePeriod,
            int serviceMiles/*, CarStatus status*/) {
        super(TABLE);
        this.plate = plateNumber;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.lastServiceMiles = lastServiceMileage;
        this.lastServiceDate = lastServiceDate;
        this.classId = carClass;
        this.color = color;
        this.branch = branch;
        this.serviceMonths = servicePeriod;
        this.serviceMiles = serviceMiles;
        //this.status = status;
    }

    /**
     * setup constructor
     * @param PkValue Value of the primary key
     */
    public Car(String PkValue) {
        super(TABLE);
        HashMap<String, String> attributes = this.findByPK(PkValue);
        
        this.plate = PkValue;
        this.brand = attributes.get("brand");
        this.model = attributes.get("model");
        this.year = Integer.parseInt(attributes.get("year"));
        this.mileage = Integer.parseInt(attributes.get("mileage"));
        this.lastServiceMiles = Integer.parseInt(attributes.get("lastServiceMileage"));
        this.lastServiceDate = attributes.get("lastServiceDate");
        this.classId = Integer.parseInt(attributes.get("carClass"));
        this.color = attributes.get("color");
        this.branch = Integer.parseInt(attributes.get("branch"));
        this.serviceMonths = attributes.get("servicePeriod");
        this.serviceMiles = Integer.parseInt(attributes.get("serviceMiles"));
        //this.status = CarStatus.valueOf(attributes.get("status"));
    }

    @Override
    public TableModel getTableModel() {
        ArrayList<HashMap<String, String>> map = this.findAll();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Plate", "Brand", "Model", "Year", "Miles",
                    "Last service miles", "Last service date", "Class", "Color", "Branch"}, 0);
        for (HashMap<String, String> row : map) {
            model.addRow(new Object[]{
                        row.get("plate"),
                        row.get("brand"),
                        row.get("model"),
                        row.get("year"),
                        row.get("mileage"),
                        row.get("lastServiceMiles"),
                        row.get("lastServiceDate"),
                        row.get("className"),
                        row.get("color"),
                        row.get("branch"),
                    });
        }
        return model;
    }

    /**
     * @return the plateNumber
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plateNumber to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * @param mileage the mileage to set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * @return the lastServiceMileage
     */
    public int getLastServiceMiles() {
        return lastServiceMiles;
    }

    /**
     * @param lastServiceMileage the lastServiceMileage to set
     */	
    public void setLastServiceMiles(int lastServiceMiles) {
        this.lastServiceMiles = lastServiceMiles;
    }

    /**
     * @return the lastServiceDate
     */
    public String getLastServiceDate() {
        return lastServiceDate;
    }

    /**
     * @param lastServiceDate the lastServiceDate to set
     */
    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    /**
     * @return the carClass
     */
    public int getClassId() {
        return classId;
    }

    /**
     * @param carClass the carClass to set
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the branch
     */
    public int getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(int branch) {
        this.branch = branch;
    }
    
    /**
     * @return the serviceMiles
     */
    public int getServiceMiles() {
        return serviceMiles;
    }

    /**
     * @param serviceMiles the serviceMiles to set
     */
    public void setServiceMiles(int serviceMiles) {
        this.serviceMiles = serviceMiles;
    }

    /**
     * @return the status
     */
    /*public CarStatus getStatus() {
        return status;
    }*/

    /**
     * @param status the status to set
     */
    /*public void setStatus(CarStatus status) {
        this.status = status;
    }*/

    /**
     * @return the TABLE
     */
    public static String getTABLE() {
        return TABLE;
    }

    /**
     * @return the serviceMonths
     */
    public String getServiceMonths() {
        return serviceMonths;
    }

    /**
     * @param serviceMonths the serviceMonths to set
     */
    public void setServiceMonths(String serviceMonths) {
        this.serviceMonths = serviceMonths;
    }
}
