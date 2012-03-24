/**
 * @author Stefano
 * 
 * Class Rental representing the car hire
 */
public class Rental {
	private int rentalId;
	private String startDatetime;
	private String endDateTime;
	private String carPlateNumber;
	private int customerid;
	private float amountPaid;
	private boolean isBooking;
	private float depositAmount;
	/**
	 * @param rentalId
	 * @param startDatetime
	 * @param endDateTime
	 * @param carPlateNumber
	 * @param customerid
	 * @param amountPaid
	 * @param isBooking
	 * @param depositAmount
	 */
	public Rental(int rentalId, String startDatetime, String endDateTime,
			String carPlateNumber, int customerid, float amountPaid,
			boolean isBooking, float depositAmount) {
		super();
		this.rentalId = rentalId;
		this.startDatetime = startDatetime;
		this.endDateTime = endDateTime;
		this.carPlateNumber = carPlateNumber;
		this.customerid = customerid;
		this.amountPaid = amountPaid;
		this.isBooking = isBooking;
		this.depositAmount = depositAmount;
	}
}
