package oscar.model;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import oscar.persistance.DbRecordable;
import oscar.util.Utility;

/**
 * @author Stefano
 *
 * Class Staff for staff members
 */
public class Staff extends Person implements DbRecordable  {

    /** Database table name*/
    public static String TABLE = "staff";
    public static String FK = "personId";
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isChauffeur;

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin.equals("admin") ? true : false;
    }

    public boolean isIsChauffeur() {
        return isChauffeur;
    }

    public void setIsChauffeur(String isChauffeur) {
        this.isChauffeur = isChauffeur.equals("chauffeur") ? true : false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = Utility.encodeSHA256(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Staff() {
        this.useTable = TABLE;
        this.initStaff();
    }

    /**
     * Class constructor
     * @param username
     */
    public Staff(String colName, String value) {
        //super(colName,value);
        this.useTable = TABLE;
        this.initStaff();
        HashMap<String, String> attributes = this.findOneBy(colName, value);
        this.username = attributes.get("username");
        this.isAdmin = (attributes.get("attributes").contains("admin")) ? true : false;
        this.isChauffeur = (attributes.get("attributes").contains("chauffeur")) ? true : false;
        this.personid = Integer.parseInt(attributes.get("personId"));
    }
    
    
    private void initStaff(){
        this.dependentTable = "person";
        this.dependentTablePK="personId";
        this.foreignKey="personId";
    }

    /**
     * Class constructor
     * @param personid
     * @param name
     * @param surname
     * @param dateOfBirth
     * @param email
     * @param address
     * @param phone
     * @param username
     * @param isAdmin
     * @param isChauffeur
     */
    public Staff(int personid, String name, String surname, String dateOfBirth,
            String email, String address, String phone, String username,
            boolean isAdmin, boolean isChauffeur) {
        super(personid, name, surname, dateOfBirth, email, address, phone);
        this.username = username;
        this.isAdmin = isAdmin;
        this.isChauffeur = isChauffeur;
    }

    /**
     *
     * @param password inputed password
     * @return whether the password match the one in the database
     * @throws NoSuchAlgorithmException
     */
    public boolean authorize(String password) {
        HashMap<String, String> hm = this.findOneBy("username", this.username);
        String inputPassword = "";
        try {
            inputPassword = Utility.encodeSHA256(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hm.get("password").equals(inputPassword))
            return true;
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean add() {
        try {
            HashMap<String, HashMap<String, String>> data = Utility.convertToHashMapWithParent(this);
            return this.addDependent(data);
        } catch (SQLException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return
     */
    @Override
    public boolean update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public TableModel getTableModel() {
        ArrayList<HashMap<String, String>> map = this.findAll();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Name", "Surname", "Admin", "Chauffeur", "Username", "Date of birth", "email"}, 0);
        for (HashMap<String, String> row : map) {
            /*System.out.println(row.get("personId")
                    + row.get("name")
                    + row.get("surname")
                    + row.get("attributes")
                    + row.get("username")
                    + row.get("dateOfBirth")
                    + row.get("email"));*/
            model.addRow(new Object[]{
                        row.get("personId"),
                        row.get("name"),
                        row.get("surname"),
                        (row.get("attributes").contains("admin")) ? true : false,
                        (row.get("attributes").contains("chauffeur")) ? true : false,
                        row.get("username"),
                        row.get("dateOfBirth"),
                        row.get("email")
                    });
        }
        return model;
    }
}
