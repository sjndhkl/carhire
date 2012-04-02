package oscar.model;

import java.util.HashMap;
import oscar.persistance.DbRecord;

/**
 * @author Stefano
 * 
 * Class CarClass representing the classes of the car
 */
public class CarClass extends DbRecord {
    private static String TABLE = "carclass";
    private int carClassId;
    private String name;
    private String description;
    private float price;

    /**
     * @param carClassId
     * @param name
     * @param description
     * @param price
     */
    public CarClass(int carClassId, String name, String description, float price) {
        super(TABLE);
        this.carClassId = carClassId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * setup constructor
     * @param PkValue Value of the primary key
     */
    public CarClass(int PkValue) {
        super(TABLE);
        this.useTable = TABLE;
        HashMap<String, String> attributes = this.findByPK(Integer.toString(PkValue));

        this.carClassId = PkValue;
        this.name = attributes.get("name");
        this.description = attributes.get("description");
        this.price = Integer.parseInt(attributes.get("price"));
    }
}
