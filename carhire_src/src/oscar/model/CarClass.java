package oscar.model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import oscar.MVC.DbRecord;

/**
 * @author Stefano
 * 
 * Class CarClass representing the classes of the car
 */
public class CarClass extends DbRecord {
    private static String TABLE = "carClass";
    private int classId;
    private String displayName;
    private String className;
    private String description;
    private float price;

    public CarClass() {
        super(TABLE);
    }
    /**
     * @param carClassId
     * @param name
     * @param description
     * @param price
     */
    public CarClass(String className, String displayName, String description, float price) {
        super(TABLE);
        this.displayName = displayName;
        this.className = className;
        this.description = description;
        this.price = price;
    }

    /**
     * setup constructor
     * @param PkValue Value of the primary key
     */
    public CarClass(String PkValue) {
        super(TABLE);
        this.useTable = TABLE;
        HashMap<String, String> attributes = this.findByPK(PkValue);

        this.className = PkValue;
        this.className = attributes.get("name");
        this.description = attributes.get("description");
        this.price = Integer.parseInt(attributes.get("price"));
        //this.price = attributes.get("price");
    }

    @Override
    public TableModel getTableModel() {
        ArrayList<HashMap<String, String>> map = this.findAll();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Name", "Display name", "description", "price"}, 0);
        for (HashMap<String, String> row : map)
            model.addRow(new Object[]{
                        row.get("className"),
                        row.get("displayName"),
                        row.get("description"),
                        row.get("price"),
                    });
        return model;
    }

    /**
     * @return the TABLE
     */
    public static String getTABLE() {
        return TABLE;
    }

    /**
     * @return the classId
     */
    public int getClassId() {
        return classId;
    }

    /**
     * @param classId the classId to set
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * @param aTABLE the TABLE to set
     */
    public static void setTABLE(String aTABLE) {
        TABLE = aTABLE;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }
}
