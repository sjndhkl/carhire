package oscar.persistance;
import oscar.MVC.DbRecord;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.TableModel;
import oscar.util.Utility;

/**
 *
 * @author sujan
 */
public class DependentDbRecord extends DbRecord {
    /** The table that depends to others */
    protected String dependentTable;
    /** the primary key of the table it depends on */
    protected String dependentTablePK;
    /** the foreign key that reference the other table */
    protected String foreignKey;

    /**
     * Class constructor
     * @param table table name
     */
    public DependentDbRecord(String table) {
        super(table);
    }

    /**
     * 
     * @param table the table of the model in the DB
     * @param dependentTable The table that depends to others
     * @param dependentTablePK the primary key of the table it depends on
     * @param foreignKey the foreign key that reference the other table
     */
    public DependentDbRecord(String table, String dependentTable, String dependentTablePK, String foreignKey) {
        super(table);
        this.dependentTable = dependentTable;
        this.dependentTablePK = dependentTablePK;
        this.foreignKey = foreignKey;
    }

    /**
     * Table name setter
     * @param key primary key
     */
    public void setPrimaryKey(String key) {
        this.primaryKey = key;
    }

    /**
     * Add a dependendency
     * @return whether successful
     * @throws SQLException
     */
    public boolean addDependent() throws SQLException {
        HashMap<String, HashMap<String, String>> records = Utility.convertToHashMapWithParent(this);
        try {
            this.connectionObject.getConnection().setAutoCommit(false);
            int primaryKeyValue = -1;
            int i = 0;
            for (String className : records.keySet()) {
                //insert into dependent table then into current one
                Class cls = Class.forName(className);
                Field f = cls.getDeclaredField("TABLE");
                Object table = f.get(null);
                HashMap<String, String> insertParamSecondary = this.getInsertParams(records.get(className));
                if (i == 1 && primaryKeyValue > 0) {
                    //process the child
                    String sqlPrimary = "insert into " + table.toString() + " (" + this.foreignKey + "," + insertParamSecondary.get("cols") + ") values(" + primaryKeyValue + "," + insertParamSecondary.get("values") + ")";
                    Statement stmt = this.connectionObject.getConnection().prepareStatement(sqlPrimary);
                    stmt.executeUpdate(sqlPrimary);
                } else if (i == 0) {
                    String sql = "insert into " + table.toString() + " (" + insertParamSecondary.get("cols") + ") values(" + insertParamSecondary.get("values") + ")";
                    Statement stmt = this.connectionObject.getConnection().prepareStatement(sql);
                    stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet rs = stmt.getGeneratedKeys();
                    rs.next();
                    primaryKeyValue = rs.getInt(1);
                } else {
                    throw new Exception("Failed");
                }
                i++;
            }

            this.connectionObject.getConnection().commit();
        } catch (Exception e) {
            e.printStackTrace();
            this.connectionObject.getConnection().rollback();
            this.connectionObject.getConnection().close();
            return false;
        }
        return true;
    }

    /**
     * the colName must be present in both the table;
     * @param records 
     * @param colName 
     * @param value
     * @return
     * @throws SQLException  
     */
    public boolean updateDependentBy(HashMap<String, HashMap<String, String>> records, String colName, String value) throws SQLException {
        try {
            this.connectionObject.getConnection().setAutoCommit(false);
            for (String className : records.keySet()) {

                Class cls = Class.forName(className);
                Field f = cls.getDeclaredField("TABLE");
                Object table = f.get(null);
                //HashMap<String, String> insertParamSecondary = this.getInsertParams(records.get(className));
                String query = "update " + table.toString() + " set " + this.getUpdateParams(records.get(className), colName) + " where " + colName + " = '" + value + "'";
                Statement stmt = this.connectionObject.getConnection().createStatement();
                int status = stmt.executeUpdate(query);
            }
            this.connectionObject.getConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.connectionObject.getConnection().rollback();
            this.connectionObject.getConnection().close();
            return false;
        }
        return true;
    }

    /**
     * 
     * @param colName
     * @param value
     * @return
     */
    public List findDependentBy(String colName, String value) {
        if (this.useTable.equals("")) {
            System.out.println("Foreign Key Not Set OR table not Specified");
            return null;
        }
        
        ArrayList<HashMap<String,String>> dependencies = new ArrayList<HashMap<String, String>>();
        
        HashMap<String,String> personDep = new HashMap<String, String>();
        
        personDep.put("table", "person");
        personDep.put("pk", "personId");
        personDep.put("joinType", "inner join");
        
        //personDep.put("joinTo", "");
        personDep.put("fk", "personId");
        dependencies.add(personDep);
        return this.queryDependent(dependencies, colName, value);
    }
}
