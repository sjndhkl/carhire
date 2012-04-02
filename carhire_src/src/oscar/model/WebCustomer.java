package oscar.model;

/**
 * @author Stefano
 * 
 * Class WebCustomer for customers registered online
 */
public class WebCustomer extends Person {
	private String username;
	private String dateOfRegistration;
	/**
	 * @param personid
	 * @param name
	 * @param surname
	 * @param dateOfBirth
	 * @param email
	 * @param address
	 * @param phone
	 * @param username
	 * @param dateOfRegistration
	 */
	public WebCustomer(int personid, String name, String surname,
			String dateOfBirth, String email, String address, String phone,
			String username, String dateOfRegistration) {
		super(personid, name, surname, dateOfBirth, email, address, phone);
		this.username = username;
		this.dateOfRegistration = dateOfRegistration;
	}
}
