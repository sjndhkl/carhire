package oscar.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * @author Stefano
 * 
 * Class Staff for staff members
 */
public class Staff extends Person {

    /** Database table name*/
    protected static String TABLE = "staff";
    
    private String username;
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
    
    
    
    public Staff(){
        
    }

    /**
     * Class constructor
     * @param username
     */
    public Staff(String colName,String value) {
        //super(colName,value);
        this.useTable = TABLE;
        HashMap<String, String> attributes =
                this.findOneBy(colName, value);
        this.username = attributes.get("username");
        this.isAdmin = (attributes.get("attributes").contains("admin")) ? true : false;
        this.isChauffeur = (attributes.get("attributes").contains("chauffeur")) ? true : false;
        this.personid = Integer.parseInt(attributes.get("personId"));
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
    public boolean authorize(String password) throws NoSuchAlgorithmException {
        HashMap<String, String> hm = this.findOneBy("username", this.username);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        String dbPassword = hm.get("password");
        String inputPassword = sb.toString();
        if (dbPassword.equals(inputPassword)) {
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
