package oscar.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import oscar.persistance.DbRecordable;
import oscar.persistance.DependentDbRecord;

/**
 * @author Stefano
 * 
 * Class of a generic person. Will be inherited by WebCustomer and Staff,
 * plus it will represents a customer not registered in the website
 */
public class Person extends DependentDbRecord implements DbRecordable {

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
    public Person(int PkValue) {
        super(TABLE);
        this.useTable = TABLE;
        HashMap<String, String> attributes = this.findByPK(Integer.toString(PkValue));

        this.personid = PkValue;
        this.name = attributes.get("name");
        this.surname = attributes.get("surname");
        this.dateOfBirth = attributes.get("dateOfBirth");
        this.email = attributes.get("email");
        this.address = attributes.get("address");
        this.phone = attributes.get("phone");
        
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
        throw new UnsupportedOperationException("Not supported yet.");
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

    /**
     * 
     * @return
     */
    @Override
    public HashMap<String, String> toHashMap() {
        Class currentClass = this.getClass();
        Field[] fields = currentClass.getDeclaredFields();
        HashMap<String, String> objHashMap = new HashMap<String, String>();
        for (Field f : fields) {

            if (!Modifier.isStatic(f.getModifiers())) {
                // switch(g.get)
                String type = f.getType().getSimpleName();
                try {
                    if (type.equals("String")) {

                        Object obj = f.get(this);
                        if (obj != null) {
                            objHashMap.put(f.getName(), obj.toString());
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return objHashMap;
    }

    /**
     * 
     * @param hashMap
     * @return
     */
    @Override
    public Object toObject(HashMap<String,String> hashMap) {
        
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
