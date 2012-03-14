
/*
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
public abstract class DbRecord {
    protected DbConnectable connectionObject;
    protected String        useTable;

    public enum ColumnType { STRING, INT }

    public enum FindAction { ALL, COUNT }

    public DbRecord(String table) {
        this.connectionObject = DbConnectionFactory.connect(DbConnectionFactory.Database.MYSQL);
        this.useTable         = table;
    }

    public ArrayList<HashMap<String, String>> query(String sql) {
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

        try {
            Statement         stmt     = this.connectionObject.getConnection().createStatement();
            ResultSet         rs       = stmt.executeQuery(sql);
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

    public Object find(FindAction action) {
        ArrayList<HashMap<String, String>> rs = null;

        switch (action) {
        case ALL :
            rs = this.query("select * from " + this.useTable);

            return rs;

        case COUNT :
            rs = this.query("select count(*) as count from " + this.useTable);

            for (HashMap<String, String> row : rs) {
                return Integer.parseInt(row.get("count"));
            }

            break;
        }

        return null;
    }

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
     * returns one record based on column name
     */

    public HashMap<String, String> findOneBy(String colName, String value) {
        return this.findAllBy(colName, value, 1).get(0);
    }

    /*
     * returns arraylist of record based on column name and column type
     */
    public HashMap<String, String> findOneBy(String colName, String value, ColumnType type) {

        // ArrayList<HashMap<String, String>> records = this.query("select * from "+this.useTable+" where "+colName+" = "+value);
        String qValue = "";

        switch (type) {
        case INT :
            qValue = value;

            break;

        case STRING :
            qValue = " '" + value + "'";
        }

        return this.findAllBy(colName, qValue, 1).get(0);
    }

    public boolean deleteBy(String colName, String value) {
        boolean status      = false;
        int     queryStatus = -1;

        try {
            Statement stmt = this.connectionObject.getConnection().createStatement();

            queryStatus = stmt.executeUpdate("delete from " + this.useTable + " where " + colName + " = " + value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        status = (queryStatus >= 1)
                 ? true
                 : false;

        return status;
    }

    public boolean deleteBy(String colName, String value, ColumnType type) {
        String qValue = "";

        switch (type) {
        case INT :
            qValue = value;

            break;

        case STRING :
            qValue = " '" + value + "'";
        }

        return this.deleteBy(colName, qValue);
    }

    public abstract boolean update(Object obj);

    public abstract boolean add(Object obj);
}


//~ Formatted by Jindent --- http://www.jindent.com
