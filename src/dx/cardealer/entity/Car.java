package dx.cardealer.entity;

public class Car {

	private int carId;			// DB serial ID (stubbed)
	private CarMake make;
	private String model;
	private int year;
	private CarColor color;
	private double usdPrice;
	private CarSize size;
	
	public Car( int id, CarMake make, String model, int year, 
								CarColor color, double price, CarSize size){
		this.carId = id;
		this.make = make;
		this.model = model;
		
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		if( carId <= 0 ){
			throw new IllegalArgumentException(
					"carId must be an integer > 0: " + carId );
		}
		this.carId = carId;
	}

	public CarMake getMake() {
		return make;
	}

	public void setMake(CarMake make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		
		// Keep model as upper case for consistency
		this.model = model.toUpperCase();
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		
		if( year < 1908 ){
			throw new IllegalArgumentException(
					"The year must be set greater than 1908: " + year );
		}
		
		
		
		this.year = year;
	}

	public CarColor getColor() {
		return color;
	}

	public void setColor(CarColor color) {
		this.color = color;
	}

	public double getUsdPrice() {
		return usdPrice;
	}

	public void setUsdPrice(double usdPrice) {
		this.usdPrice = usdPrice;
	}

	public CarSize getSize() {
		return size;
	}

	public void setSize(CarSize size) {
		this.size = size;
	}
	
	
}
