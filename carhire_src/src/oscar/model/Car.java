package oscar.model;

/**
 * @author Stefano
 * 
 * Class Car for the cars to hire
 */
public class Car {
    /** The PK of the table */
    private String plateNumber;
    private String brand;
    private String model;
    private int year;
    private int mileage;
    private int lastServiceMileage;
    private String lastServiceDate;
    private int carClass;
    private String color;
    private int branch;
    private String servicePeriod;
    private int serviceMiles;

    /**
     * Status of the car
     */
    public enum CarStatus {

        /** Ready to rent */
        READY,
        /** Cleaning due */
        CLEANING,
        /** Temporary hired */
        HIRED,
        /** Under service*/
        SERVICING,
        /** Unavailable*/
        UNAVAILABLE
    }
    private CarStatus status;

    /**
     * Class contructor
     * @param plateNumber
     * @param brand
     * @param model
     * @param year
     * @param mileage
     * @param lastServiceMileage
     * @param lastServiceDate
     * @param carClass
     * @param color
     * @param branch
     * @param servicePeriod
     * @param serviceMiles
     * @param status
     */
    public Car(String plateNumber, String brand, String model, int year,
            int mileage, int lastServiceMileage, String lastServiceDate,
            int carClass, String color, int branch, String servicePeriod,
            int serviceMiles, CarStatus status) {
        super();
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.lastServiceMileage = lastServiceMileage;
        this.lastServiceDate = lastServiceDate;
        this.carClass = carClass;
        this.color = color;
        this.branch = branch;
        this.servicePeriod = servicePeriod;
        this.serviceMiles = serviceMiles;
        this.status = status;
    }
}
