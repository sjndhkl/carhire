package oscar.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stefano
 * 
 * Class Staff for staff members
 */
public class Staff extends Person {

    private String username;
    private boolean isAdmin;
    private boolean isChauffeur;
    
    /**
     * Class contructor
     * @param username
     */
    public Staff(String username){
        this.username = username;
    }

    /**
     * Class contructo
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
    public boolean authorize(String password) throws NoSuchAlgorithmException {
        HashMap hm = this.findOneBy("username", this.username);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(sb.toString());
        if (hm.get("password").equals(sb)) {
            return true;
        }
        return false;

    }

    /**
     * 
     * @return
     */
    @Override
    public boolean add() {
        throw new UnsupportedOperationException("Not supported yet.");
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
     * @param objHashMap
     * @return
     */
    @Override
    public Object toObject(HashMap<String,String> objHashMap) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
