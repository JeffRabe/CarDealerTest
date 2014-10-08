package dx.cardealer.entity.cars;

import java.text.DecimalFormat;
import java.util.Calendar;

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
		
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		if( year > thisYear )
			throw new IllegalArgumentException(
					"The year entered is in the future: " + year );
		
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
		if( usdPrice <= 0 )
			throw new IllegalArgumentException(
					"The price of cars must be greater than $0.00: " + usdPrice
					);
		
		// truncate after 2 decimal places
		DecimalFormat priceFormat = new DecimalFormat("#.00");
		this.usdPrice = Double.valueOf(priceFormat.format(usdPrice));
	}

	public CarSize getSize() {
		return size;
	}

	public void setSize(CarSize size) {
		this.size = size;
	}
	
	
}
