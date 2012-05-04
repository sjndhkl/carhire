package oscar.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import oscar.MVC.DbRecord;
import oscar.util.TableModelHelper;

/**
 * @author Stefano
 *
 * Class CarClass representing the classes of the car
 */
public class CarClass extends DbRecord {
    private static String TABLE = "carClass";
    private int classId;
    private String displayName;
    private String name;
    private String description;
    private float price;

    /**
     * 
     */
    public CarClass() {
        super(TABLE);
    }
    /**
     * @param displayName
     * @param name
     * @param description
     * @param price
     */
    public CarClass(String name, String displayName, String description, float price) {
        super(TABLE);
        this.displayName = displayName;
        this.name = name;
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

        this.classId = Integer.parseInt(PkValue);
        this.name = attributes.get("name");
        this.description = attributes.get("description");
        this.price = Integer.parseInt(attributes.get("price"));
        //this.price = attributes.get("price");
    }

    /**
     * 
     * @return
     */
    @Override
    public TableModel getTableModel() {
        ArrayList<HashMap<String, String>> map = this.findAll();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Name", "displayName", "description", "price"}, 0);
        for (HashMap<String, String> row : map)
            model.addRow(new Object[]{
                        row.get("classId"),
                        row.get("name"),
                        row.get("displayName"),
                        row.get("description"),
                        row.get("price"),
                    });
        return model;
    }

    /**
     * 
     * @param filters
     * @return
     */
    @Override
    public TableModel getTableModel(HashMap<String, String> filters) {
        ArrayList<HashMap<String, String>> map = this.findAllLike(filters);
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Name", "displayName", "description", "price"}, 0);
        for (HashMap<String, String> row : map)
            model.addRow(new Object[]{
                        row.get("classId"),
                        row.get("name"),
                        row.get("displayName"),
                        row.get("description"),
                        row.get("price"),
                    });
        return model;
    }
    
    public ArrayList<HashMap<String, String>> getCars(int classId,Date startDate,Date endDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String query ="select car.* from car inner join "+this.useTable+" on car.classId = "+this.useTable+".classId where car.classId = '"+classId+"' and (select count(*) from rental where car.plate = rental.carPlate and (  (rental.startDateTime between '"+df.format(startDate) +"' AND '"+df.format(endDate) +"') OR ( rental.endDateTime between '"+df.format(startDate) +"' AND '"+df.format(endDate) +"')   ) group by rental.carPlate) is NULL";
        //System.out.println(query);
        return this.query(query);
        
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
    public String getName() {
        return name;
    }

    /**
     * @param name the className to set
     */
    public void setName(String name) {
        this.name = name;
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
