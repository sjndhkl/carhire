/**
 * @author Stefano
 * 
 * Class of a generic person. Will be inherited by WebCustomer and Staff,
 * plus it will represents a customer not registered in the website
 */
public class Person {
	private int personid;
	private String name;
	private String surname;
	private String dateOfBirth;
	private String email;
	private String address;
	private String phone;
	/**
	 * @param personid
	 * @param name
	 * @param surname
	 * @param dateOfBirth
	 * @param email
	 * @param address
	 * @param phone
	 */
	public Person(int personid, String name, String surname,
			String dateOfBirth, String email, String address, String phone) {
		super();
		this.personid = personid;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}
}
