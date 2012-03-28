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
	
	public boolean authorize (String password) {
		HashMap hm = this.findOneBy("username", this.username);
 
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
		if(hm.get("password").equals(sb))
			return true;
		return false;
		
	}
}
