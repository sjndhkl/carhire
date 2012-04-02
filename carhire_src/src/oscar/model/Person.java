package oscar.model;

import java.util.HashMap;
import oscar.persistance.DbRecord;
import oscar.persistance.DbRecordable;
import oscar.util.Utility;

/**
 * @author Stefano
 * 
 * Class of a generic person. Will be inherited by WebCustomer and Staff,
 * plus it will represents a customer not registered in the website
 */
public class Person extends DbRecord implements DbRecordable {

    /** Record id*/
    protected int personid;
    protected String name;
    protected String surname;
    protected String dateOfBirth;
    protected String email;
    protected String address;
    protected String phone;
    /** Databse table name*/
    protected static String TABLE = "person";

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getPersonid() {
        return personid;
    }

    public String getPhone() {
        return phone;
    }

    public String getSurname() {
        return surname;
    }


    /**
     * Class contructor
     */
    public Person() {
        super(TABLE);
    }

    /**
     * Class contructor
     * @param personid record id
     * @param name
     * @param surname
     * @param dateOfBirth
     * @param email
     * @param address
     * @param phone
     */
    public Person(int personid, String name, String surname,
            String dateOfBirth, String email, String address, String phone) {
        super(TABLE);
        this.personid = personid;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Class constructor
     * @param PkValue Value of the primary key
     */
    public Person(String colName,String value) {
        super(TABLE);
        HashMap<String, String> attributes = this.findOneBy(colName, value);
        //HashMap<String, String> attributes = this.findByPK(Integer.toString(PkValue));
        Utility.fill(attributes, this);
    }
    
    public Person(int PkValue) {
        super(TABLE);
        HashMap<String, String> attributes = this.findByPK(Integer.toString(PkValue));
        Utility.fill(attributes, this);
    }

    /* Setters and getters */
    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * 
     * @return The success of the operation
     */
    // TODO implement this operation
    @Override
    public boolean add() {
        return this.add(Utility.convertToHashMap(this));
    }
    /*
     * @param return key: 0,1
     */
    public int addPk() {
        return this.addPk(Utility.convertToHashMap(this));
    }

    /**
     * 
     * @return The success of the operation
     */
    // TODO implement this operation
    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return The success of the operation
     */
    // TODO implement this operation
    @Override
    public boolean update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
