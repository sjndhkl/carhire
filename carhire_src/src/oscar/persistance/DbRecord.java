/*1
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

//~--- JDK imports ------------------------------------------------------------
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sujan
 */
public class DbRecord {

    protected DbConnectable connectionObject;
    protected String useTable;

    public enum ColumnType {

        STRING, INT
    }

    public DbRecord(String table) {
        this.connectionObject = DbConnectionFactory.connect(DbConnectionFactory.Database.MYSQL);
        this.useTable = table;
    }

    public String getTable() {
        return this.useTable;
    }

    public void setTable(String newTable) {
        this.useTable = newTable;
    }

    public ArrayList<HashMap<String, String>> query(String sql) {
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

        try {
            Statement stmt = this.connectionObject.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                HashMap<String, String> row = new HashMap<String, String>();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.put(metaData.getColumnName(i), rs.getString(i));
                }

                result.add(row);
            }

            stmt.close();
            this.connectionObject.closeConnection();
        } catch (Exception ex) {
            result = null;
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public int count() {
        ArrayList<HashMap<String, String>> rs = this.query("select count(*) as count from " + this.useTable);

        for (HashMap<String, String> row : rs) {
            return Integer.parseInt(row.get("count"));
        }
        return 0;
    }

    /*
     * returns all the data inside the table
     */
    public ArrayList<HashMap<String, String>> findAll() {
        return this.query("select * from " + this.useTable);
    }

    public ArrayList<HashMap<String, String>> findAll(int limit) {
        return this.query("select * from " + this.useTable + " limit 0," + limit);
    }

    public ArrayList<HashMap<String, String>> findAll(int startAt, int limit) {
        return this.query("select * from " + this.useTable + " limit " + startAt + "," + limit);
    }

    /*
     * returns all the data inside the table depending on column name and value
     * specified
     */
    public ArrayList<HashMap<String, String>> findAllBy(String colName, String value, int limit) {
        ArrayList<HashMap<String, String>> records = this.query("select * from " + this.useTable + " where " + colName
                + " = " + value + " limit 0," + limit);

        if (records.size() >= 1) {
            return records;    // return row
        }

        return null;
    }

    public ArrayList<HashMap<String, String>> findAllBy(String colName, String value, int startAt, int limit) {
        ArrayList<HashMap<String, String>> records = this.query("select * from " + this.useTable + " where " + colName
                + " = " + value + " limit " + startAt + "," + limit);

        if (records.size() > 1) {
            return records;    // return row
        }

        return null;
    }

    /*
     * returns single row of data based on column and value specified
     */
    public HashMap<String, String> findOneBy(String colName, String value) {
        return this.findAllBy(colName, value, 1).get(0);
    }

    /*
     * returns single row of data based on column and value specified along with
     * column type
     */
    public HashMap<String, String> findOneBy(String colName, String value, ColumnType type) {
        String qValue = "";

        switch (type) {
            case INT:
                qValue = value;
                break;
            case STRING:
                qValue = " '" + value + "'";
                break;
        }

        return this.findAllBy(colName, qValue, 1).get(0);
    }

    public boolean nonQuery(String sql) {
        boolean status = false;
        int queryStatus = -1;

        try {
            Statement stmt = this.connectionObject.getConnection().createStatement();

            queryStatus = stmt.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        status = (queryStatus >= 1)
                ? true
                : false;

        return status;
    }

    /*
     * deletes record based on the column name and its value
     */
    public boolean deleteBy(String colName, String value) {
        return this.nonQuery("delete from " + this.useTable + " where " + colName + " = " + value);
    }

    public boolean deleteBy(String colName, String value, ColumnType type) {
        String qValue = "";

        switch (type) {
            case INT:
                qValue = value;

                break;

            case STRING:
                qValue = " '" + value + "'";
                break;
        }

        return this.deleteBy(colName, qValue);
    }

    /*
     * Update records based on Column name and value specified
     */
    public boolean updateBy(HashMap<String, String> objHashMap, String colName, String value) {
        String updateString = "";
        int num_cols = objHashMap.size();
        int i = 1;
        for (String key : objHashMap.keySet()) {

            updateString += key + " = '" + objHashMap.get(key) + "'";
            if (i != num_cols) {
                updateString += ",";
            }

            i++;
        }
        String query = "update " + this.useTable + " set " + updateString + " where " + colName + " = '" + value + "'";

        return this.nonQuery(query);
    }

    public boolean add(HashMap<String, String> objHashMap) {

        String cols = "";
        String values = "";
        int num_cols = objHashMap.size();
        int i = 1;
        for (String key : objHashMap.keySet()) {

            cols += key;
            values += "'" + objHashMap.get(key) + "'";
            if (i != num_cols) {
                cols += ",";
                values += ",";
            }

            i++;
        }
        String query = "insert into " + this.useTable + "(" + cols + ") values(" + values + ")";
        //System.out.println(query);
        return this.nonQuery(query);
    }
}
