package oscar.persistance;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sujan
 */
public class DependentDbRecord extends DbRecord {
    //SELECT COLUMN_NAME from information_schema.KEY_COLUMN_USAGE where constraint_name = "PRIMARY" and table_name = "branch" and table_schema="carhire"

    /** The table that depends to others*/
    protected String dependentTable;
    protected String dependentTablePK;
    protected String foreignKey;

    /**
     * Class constructor
     * @param table table name
     */
    public DependentDbRecord(String table) {
        super(table);
    }

    public DependentDbRecord(String table, String dependentTable, String dependentTablePK, String foreignKey) {
        super(table);
        this.dependentTable = dependentTable;
        this.dependentTablePK = dependentTablePK;
        this.foreignKey = foreignKey;
    }

    /**
     * Table name setter
     * @param table table name
     */
    public void setPrimaryKey(String key) {
        this.primaryKey = key;
    }

    /**
     * 
     * @param records
     * @return
     * @throws SQLException
     */
    public boolean addDependent(HashMap<String, HashMap<String, String>> records) throws SQLException {
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
     */
    public boolean updateDependentBy(HashMap<String, HashMap<String, String>> records, String colName, String value) throws SQLException {
        try {
            this.connectionObject.getConnection().setAutoCommit(false);
            for (String className : records.keySet()) {

                Class cls = Class.forName(className);
                Field f = cls.getDeclaredField("TABLE");
                Object table = f.get(null);
                //HashMap<String, String> insertParamSecondary = this.getInsertParams(records.get(className));
                String query = "update " + this.useTable + " set " + this.getUpdateParams(records.get(className)) + " where " + colName + " = '" + value + "'";
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

    public List findDependentBy(String colName, String value) {
        if (this.dependentTable.equals("") || this.dependentTablePK.equals("")) {
            System.out.println("Dependent Table Metadata Not Set");
            return null;
        }
        if (this.foreignKey.equals("") || this.useTable.equals("")) {
            System.out.println("Foreign Key Not Set OR table not Specified");
            return null;
        }

        List list = null;
        try {
            String query = "select * from " + this.useTable + " inner join " + this.dependentTable + " on " + this.useTable + "." + this.foreignKey + " = " + this.dependentTable + "." + this.dependentTablePK + " where " + colName + " = '" + value + "'";
            if (colName.equals("*")) {
                query = "select * from " + this.useTable + " inner join " + this.dependentTable + " on " + this.useTable + "." + this.foreignKey + " = " + this.dependentTable + "." + this.dependentTablePK;
            }
            return this.query(query);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
