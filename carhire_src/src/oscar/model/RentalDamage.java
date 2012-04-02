package oscar.model;

/**
 * @author Stefano
 * 
 * Class RentalDamage associated with a Rental in case a damage has occurred
 */
public class RentalDamage {
	private int rentalDamageId;
	private int rentalId;
	private float cost;
	private String description;
	/**
	 * @param rentalDamageId
	 * @param rentalId
	 * @param cost
	 * @param description
	 */
	public RentalDamage(int rentalDamageId, int rentalId, float cost,
			String description) {
		super();
		this.rentalDamageId = rentalDamageId;
		this.rentalId = rentalId;
		this.cost = cost;
		this.description = description;
	}
}
