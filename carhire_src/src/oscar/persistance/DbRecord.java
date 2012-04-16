package oscar.persistance;

//~--- JDK imports ------------------------------------------------------------
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableModel;

/**
 *
 * @author sujan
 */
public class DbRecord {
    /** Database connection*/
    protected DbConnectable connectionObject;
    /** Table on which the record is stored in the database*/
    protected String useTable;
    /** Primary key of the table*/
    protected String primaryKey;

    /** Possible type of columns*/
    public enum ColumnType {
        /** String type */
        STRING,
        /** Integer type*/
        INT
    }

    /**
     *  Constructor of the class
     * @param table table where the record is stored
     */
    public DbRecord(String table) {
        this.connectionObject = DbConnectionFactory.connect(DbConnectionFactory.Database.MYSQL);
        this.useTable = table;
    }

    /**
     *  Constructor of the class
     * @param table table where the record is stored
     */
    // TODO: Complete this implementation
    public DbRecord(String table, String PkValue) {
        this.connectionObject = DbConnectionFactory.connect(DbConnectionFactory.Database.MYSQL);
        this.useTable = table;
        HashMap<String, String> objectAttributes = this.findByPK(PkValue);
    }

    /**
     * Getter of the table
     * @return table where the record is stored
     */
    public String getTable() {
        return this.useTable;
    }

    /**
     * Change the table where the record is stored
     * @param newTable table where the record is stored
     */
    public void setTable(String newTable) {
        this.useTable = newTable;
    }

    /**
     * Perform a query on this record
     * @param sql query sting
     * @return List of HashMap representing the records
     */
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
    
    public ArrayList<HashMap<String,String>> queryDependent(ArrayList<HashMap<String,String>> dependencies,String colName,String value){
        //ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String, String>>();
        String joins = "";
        String selections=this.useTable+".*,";
        int i=1;
        for(HashMap<String,String> tableInfo:dependencies){
            
           if(i<dependencies.size()){
                selections += tableInfo.get("table")+".*" +",";
            }else{
                selections += tableInfo.get("table")+".*";
            }
           String joinTo = tableInfo.get("joinTo");
           if(joinTo == null){
               joinTo = this.useTable;
           }
           joins += tableInfo.get("joinType")+ " "+tableInfo.get("table") +" on "+joinTo+"."+tableInfo.get("fk")+" = "+tableInfo.get("table")+"."+tableInfo.get("pk")+" ";
           i++;
        }
        
        String sql = "";
        if(colName.equals("*") && value.equals("*")){
            sql = "select "+selections+" from "+this.useTable+" "+joins;
        }else{
            sql = "select "+selections+" from "+this.useTable+" "+joins+" where "+colName+" = '"+value+"'";
        }
        return this.query(sql);
        
    }
    
    
    
    

    /**
     * Count the record of this table
     * @return the number of record of this table
     */
    public int count() {
        ArrayList<HashMap<String, String>> rs = this.query("select count(*) as count from " + this.useTable);

        for (HashMap<String, String> row : rs) {
            return Integer.parseInt(row.get("count"));
        }
        return 0;
    }

    /**
     * Returns all the data inside the table
     * @return List of HashMap representing all the records
     */
    public ArrayList<HashMap<String, String>> findAll() {
        return this.query("select * from " + this.useTable);
    }

    /**
     * Returns a certain number of record inside the table
     * @param limit maximum number of results
     * @return List of HashMap representing the records
     */
    public ArrayList<HashMap<String, String>> findAll(int limit) {
        return this.query("select * from " + this.useTable + " limit 0," + limit);
    }

    /**
     * Returns a certain number of record inside the table
     * starting from a specified record
     * @param startAt record number to start from
     * @param limit maximum number of records
     * @return List of HashMap representing the records
     */
    public ArrayList<HashMap<String, String>> findAll(int startAt, int limit) {
        return this.query("select * from " + this.useTable + " limit " + startAt + "," + limit);
    }

    /**
     * returns all the data inside the table depending on column name and value
     * specified
     * @param colName column to search in
     * @param value value to search for
     * @param limit maximum number of results
     * @return List of HashMap representing the records
     */
    public ArrayList<HashMap<String, String>> findAllBy(String colName, String value, int limit) {
        ArrayList<HashMap<String, String>> records = this.query("select * from " + this.useTable + " where " + colName
                + "='" + value + "' limit 0," + limit);
        if (records != null
            && records.size() >= 1) {
            return records;    // return row
        }

        return null;
    }

    /**
     * returns all the data inside the table depending on column name, value,
     * maximum results and starting record specified
     * @param colName  column to search in
     * @param value value to search for
     * @param startAt record to start at
     * @param limit maximum number of results
     * @return List of HashMap representing the records
     */
    public ArrayList<HashMap<String, String>> findAllBy(String colName, String value, int startAt, int limit) {
        ArrayList<HashMap<String, String>> records = this.query("select * from " + this.useTable + " where " + colName
                + " = " + value + " limit " + startAt + "," + limit);

        if (records.size() > 1) {
            return records;    // return row
        }

        return null;
    }

    /**
     * returns single row of data based on column and value specified
     * @param colName  column to search in
     * @param value value to search for
     * @return List of HashMap representing the records
     */
    public HashMap<String, String> findOneBy(String colName, String value) {
        return this.findAllBy(colName, value, 1).get(0);
    }

    /**
     * returns single row of data based on column and value specified along with
     * @param value value to search for
     * @return List of HashMap representing the records
     */
    public HashMap<String, String> findByPK(String value) {
        
            return this.findOneBy(this.getPrimaryKey(), value);

    }
    
    
    public String getPrimaryKey(){
        String PK="";
        try {
            Statement stmt = this.connectionObject.getConnection().createStatement();
            // TODO: make the database name softcoded
            ResultSet rs = stmt.executeQuery("SHOW INDEX FROM carhire."
                    + this.useTable + " WHERE Key_name = 'PRIMARY'");
            /*"SELECT column_name FROM information_schema.key_column_usage"
            + " WHERE table_schema = schema()             -- only look in the current db"
            + " AND constraint_name = 'PRIMARY'         -- always 'PRIMARY' for PRIMARY KEY constraints"
            + " AND table_name = " + this.useTable + "    -- specify your table.");*/
            rs.next();
            PK = rs.getString("Column_name");
        } catch (SQLException sQLException) {
            System.err.println("Mysql Exception :" + sQLException.getMessage());
        }
        return PK;
    }

    /**
     * Populate the hashmap with the DB record
     * @return
     */
    /*public boolean populate () {
    
    return true;
    }*/
    /**
     * returns single row of data based on column and value specified along with
     * column type
     * @param colName column name
     * @param value value to search for
     * @param type type of value to search
     * @return List of HashMap representing the records
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

    /**
     * Execute an sql command
     * @param sql sql string to execute
     * @return the success of the command
     */
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
    
    public int nonQueryPk(String sql) {
        
        try {
            Statement stmt = this.connectionObject.getConnection().prepareStatement(sql);
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
                    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * deletes record based on the column name and its value
     * @param colName column name
     * @param value value to search for
     * @return the success of the query
     */
    public boolean deleteBy(String colName, String value) {
        return this.nonQuery("delete from " + this.useTable + " where " + colName + " = " + value);
    }

    /**
     * deletes record based on the column name, its value and type of column
     * @param colName column name
     * @param value value to search for
     * @param type the type of the value
     * @return the success of the query
     */
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

    // TODO complete the javadoc of this method
    /**
     * 
     * @param objHashMap
     * @return
     */
    protected String getUpdateParams(HashMap<String, String> objHashMap) {
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

    /**
     * Update records based on Column name and value specified
     * @param objHashMap the new data to update
     * @param colName column name
     * @param value value to search for
     * @return The success of the query
     */
    public boolean updateBy(HashMap<String, String> objHashMap, String colName, String value) {

        String query = "update " + this.useTable + " set " + this.getUpdateParams(objHashMap) + " where " + colName + " = '" + value + "'";

        return this.nonQuery(query);
    }

    // TODO complete the javadoc
    /**
     * 
     * @param objHashMap
     * @return
     */
    protected HashMap<String, String> getInsertParams(Map<String, String> objHashMap) {
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
        HashMap<String, String> insertParams = new HashMap<String, String>();
        insertParams.put("cols", cols);
        insertParams.put("values", values);
        return insertParams;
    }

    //TODO complete this javadoc
    /**
     * 
     * @param objHashMap
     * @return
     */
    public boolean add(HashMap<String, String> objHashMap) {

        HashMap<String, String> insertParams = this.getInsertParams(objHashMap);
        String query = "insert into " + this.useTable + "(" + insertParams.get("cols") + ") values(" + insertParams.get("values") + ")";
        //System.out.println(query);
        return this.nonQuery(query);
    }
    
    /*
     * adds record and returns last insert id of PK
     */
     public int addPk(HashMap<String, String> objHashMap) {

        HashMap<String, String> insertParams = this.getInsertParams(objHashMap);
        String query = "insert into " + this.useTable + "(" + insertParams.get("cols") + ") values(" + insertParams.get("values") + ")";
        //System.out.println(query);
        return this.nonQueryPk(query);
    }
     
    public TableModel getTableModel() { return null;}
}
