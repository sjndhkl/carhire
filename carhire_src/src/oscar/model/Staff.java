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
}
