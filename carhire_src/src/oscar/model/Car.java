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
    private String plateNumber;
    private String brand;
    private String model;
    private int year;
    private int mileage;
    private int lastServiceMileage;
    private String lastServiceDate;
    private int carClass;
    private String color;
    private int branch;
    private String servicePeriod;
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
    private CarStatus status;

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
            int serviceMiles, CarStatus status) {
        super(TABLE);
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.lastServiceMileage = lastServiceMileage;
        this.lastServiceDate = lastServiceDate;
        this.carClass = carClass;
        this.color = color;
        this.branch = branch;
        this.servicePeriod = servicePeriod;
        this.serviceMiles = serviceMiles;
        this.status = status;
    }

    /**
     * setup constructor
     * @param PkValue Value of the primary key
     */
    public Car(String PkValue) {
        super(TABLE);
        HashMap<String, String> attributes = this.findByPK(PkValue);
        
        this.plateNumber = PkValue;
        this.brand = attributes.get("brand");
        this.model = attributes.get("model");
        this.year = Integer.parseInt(attributes.get("year"));
        this.mileage = Integer.parseInt(attributes.get("mileage"));
        this.lastServiceMileage = Integer.parseInt(attributes.get("lastServiceMileage"));
        this.lastServiceDate = attributes.get("lastServiceDate");
        this.carClass = Integer.parseInt(attributes.get("carClass"));
        this.color = attributes.get("color");
        this.branch = Integer.parseInt(attributes.get("branch"));
        this.servicePeriod = attributes.get("servicePeriod");
        this.serviceMiles = Integer.parseInt(attributes.get("serviceMiles"));
        this.status = CarStatus.valueOf(attributes.get("status"));
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
}
