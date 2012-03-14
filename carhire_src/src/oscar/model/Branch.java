/**
 * @author Stefano
 * 
 * Class Branch representing a company branch
 */
public class Branch {
	private int branchId;
	private String location;
	private String country;
	/**
	 * @param branchId
	 * @param location
	 * @param country
	 */
	public Branch(int branchId, String location, String country) {
		super();
		this.branchId = branchId;
		this.location = location;
		this.country = country;
	}
}
