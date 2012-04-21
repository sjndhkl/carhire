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
    private static String TABLE = "carclass";
    private int carClassId;
    private String name;
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
}
