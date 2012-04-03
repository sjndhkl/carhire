package oscar.persistance;

import java.lang.reflect.Field;
import java.sql.ResultSet;
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
    public void setPrimaryKey(String key) {
        this.primaryKey = key;
    }

    /**
     * primary hashmap is for the current object's association
     * and secondary is dependent table
     * @param primaryHashMap
     * @param secondaryHashMap
     * @return
     * @throws SQLException
     */
    public boolean addDependent(HashMap<String, HashMap<String, String>> records) throws SQLException {
        if (this.primaryKey.equals("")) {
        System.out.println("Primary Key MIssing please specify");
        return false;
        }
        try {

            this.connectionObject.getConnection().setAutoCommit(false);
            // Statement stmt = this.connectionObject.getConnection().prepareStatement(useTable);
            //System.out.println("OOOK"+records.keySet().size());
            int primaryKeyValue = -1;
            int i = 0;
            for (String className : records.keySet()) {
                //insert into dependent table then into current one
                Class cls = Class.forName(className);
                Field f = cls.getDeclaredField("TABLE");
                Object table = f.get(null);
                // String table = "";
                //pK and fk must be scpecified
                //else wont work
                HashMap<String, String> insertParamSecondary = this.getInsertParams(records.get(className));
                if (i == 1 && primaryKeyValue>0) {
                    //process the child
                    String sqlPrimary = "insert into " + table.toString() + " (" + this.primaryKey + "," + insertParamSecondary.get("cols") + ") values(" + primaryKeyValue + "," + insertParamSecondary.get("values") + ")";
                  // System.out.println(sqlPrimary);
                    Statement stmt = this.connectionObject.getConnection().prepareStatement(sqlPrimary);
                    stmt.executeUpdate(sqlPrimary);
                } else if(i==0){
                    //process parent
                    String sql = "insert into " + table.toString() + " (" + insertParamSecondary.get("cols") + ") values(" + insertParamSecondary.get("values") + ")";
                  // System.out.println(sql);
                    Statement stmt = this.connectionObject.getConnection().prepareStatement(sql);
                    stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
                    ResultSet rs = stmt.getGeneratedKeys();
                    rs.next();
                    primaryKeyValue  = rs.getInt(1);
                }else{
                    throw new Exception("Failed");
                }
                i++;
                //  HashMap<String, String> insertParamPrimary = this.getInsertParams(primaryHashMap);
                //  String sqlPrimary = "insert into " + this.useTable + " (" + this.primaryKey + "," + insertParamPrimary.get("cols") + ") values(" + primaryKeyValue + "," + insertParamPrimary.get("values") + ")";
                //  stmt = this.connectionObject.getConnection().prepareStatement(sqlSecondary, Statement.RETURN_GENERATED_KEYS);
                //  stmt.executeUpdate(sqlPrimary);
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
}
