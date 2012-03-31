package oscar.model;

/**
 * @author Stefano
 * 
 * Class CarClass representing the classes of the car
 */
public class CarClass {
	private int carClassId;
	private String name;
	private String description;
	private float price;
	
	/**
	 * @param carClassId
	 * @param name
	 * @param description
	 * @param price
	 */
	public CarClass(int carClassId, String name, String description, float price) {
		super();
		this.carClassId = carClassId;
		this.name = name;
		this.description = description;
		this.price = price;
	}
}
