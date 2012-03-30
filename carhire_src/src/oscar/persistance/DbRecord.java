/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

//~--- JDK imports ------------------------------------------------------------
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sujan
 */
public class DbRecord {

    protected DbConnectable connectionObject;
    protected String useTable;
    protected String primaryKey;

    public enum ColumnType {

        STRING, INT
    }

    public DbRecord(String table) {
        this.connectionObject = DbConnectionFactory.connect(DbConnectionFactory.Database.MYSQL);
        this.useTable = table;
<<<<<<< HEAD
=======
        //todo: find primary key and set the primaryKey attribute
        /*"SELECT column_name FROM information_schema.key_column_usage"
            + "WHERE table_schema = schema()             -- only look in the current db"
            + "AND constraint_name = 'PRIMARY'         -- always 'PRIMARY' for PRIMARY KEY constraints"
            + "AND table_name = " + table + "    -- specify your table."*/


>>>>>>> f392a38c094d979ee7ddd26563587122194faecb
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
    
    public HashMap<String, String> findByPK(String value) {
        String PK = "SELECT column_name FROM information_schema.key_column_usage"
            + "WHERE table_schema = schema()             -- only look in the current db"
            + "AND constraint_name = 'PRIMARY'         -- always 'PRIMARY' for PRIMARY KEY constraints"
            + "AND table_name = " + this.useTable + "    -- specify your table.";

        return this.findOneBy(PK, value);
    }
    
    /*
     * Populate the hashmap with the DB record
     */
    public boolean populate () {
        
        return true;
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
    
    protected String getUpdateParams(HashMap<String, String> objHashMap){
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
        return updateString;
    }

    /*
     * Update records based on Column name and value specified
     */
    public boolean updateBy(HashMap<String, String> objHashMap, String colName, String value) {
       
        String query = "update " + this.useTable + " set " + this.getUpdateParams(objHashMap) + " where " + colName + " = '" + value + "'";

        return this.nonQuery(query);
    }
    
    protected HashMap<String,String> getInsertParams(Map<String,String> objHashMap){
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
        HashMap<String,String> insertParams = new HashMap<String, String>();
        insertParams.put("cols", cols);
        insertParams.put("values", values);
        return insertParams;
    }

    public boolean add(HashMap<String, String> objHashMap) {

       HashMap<String,String> insertParams = this.getInsertParams(objHashMap);
        String query = "insert into " + this.useTable + "(" + insertParams.get("cols") + ") values(" + insertParams.get("values") + ")";
        //System.out.println(query);
        return this.nonQuery(query);
    }
    
    
}
