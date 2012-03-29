package oscar.model;

import oscar.persistance.DbRecord;

/**
 * @author Stefano
 * 
 * Class of a generic person. Will be inherited by WebCustomer and Staff,
 * plus it will represents a customer not registered in the website
 */
public class Person extends DbRecord {
	protected int personid;
	protected String name;
	protected String surname;
	protected String dateOfBirth;
	protected String email;
	protected String address;
	protected String phone;
        protected static String TABLE ="person";
        
        public Person(){
            super(TABLE);
        }
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
		super(TABLE);
		this.personid = personid;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}
}
