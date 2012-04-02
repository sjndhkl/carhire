package oscar.persistance;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author sujan
 */
public class DependentDbRecord extends DbRecord {
    //SELECT COLUMN_NAME from information_schema.KEY_COLUMN_USAGE where constraint_name = "PRIMARY" and table_name = "branch" and table_schema="carhire"

    /** The table that depends to others*/
    protected String dependentTable;

    /**
     * Class constructor
     * @param table table name
     */
    public DependentDbRecord(String table) {
        super(table);
    }

    /**
     * Table name setter
     * @param table table name
     */
    public void setDependentTable(String table) {
        this.dependentTable = table;
    }

    /**
     * primary hashmap is for the current object's association
     * and secondary is dependent table
     * @param primaryHashMap
     * @param secondaryHashMap
     * @return
     * @throws SQLException
     */
    public boolean add(HashMap<String, String> primaryHashMap, HashMap<String, String> secondaryHashMap) throws SQLException {
        if (this.dependentTable.equals("")) {
            System.out.println("No Dependent Table Set");
            return false;
        }
        try {

            this.connectionObject.getConnection().setAutoCommit(false);
            // Statement stmt = this.connectionObject.getConnection().prepareStatement(useTable);

            //insert into dependent table then into current one
            HashMap<String, String> insertParamSecondary = this.getInsertParams(primaryHashMap);
            String sqlSecondary = "insert into " + this.dependentTable + " (" + insertParamSecondary.get("cols") + ") values(" + insertParamSecondary.get("values") + ")";
            Statement stmt = this.connectionObject.getConnection().prepareStatement(sqlSecondary, Statement.RETURN_GENERATED_KEYS);
            int primaryKeyValue = stmt.executeUpdate(sqlSecondary);


            HashMap<String, String> insertParamPrimary = this.getInsertParams(primaryHashMap);
            String sqlPrimary = "insert into " + this.useTable + " (" + this.primaryKey + "," + insertParamPrimary.get("cols") + ") values(" + primaryKeyValue + "," + insertParamPrimary.get("values") + ")";
            stmt = this.connectionObject.getConnection().prepareStatement(sqlSecondary, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate(sqlPrimary);

            this.connectionObject.getConnection().commit();
        } catch (Exception e) {
            this.connectionObject.getConnection().rollback();
            this.connectionObject.getConnection().close();
            return false;
        }
        return true;
    }
}
