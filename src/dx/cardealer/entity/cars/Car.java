package dx.cardealer.entity.cars;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * This class represents an individual Car stored
 * by a dealership.
 * @author jrabe
 *
 */
public class Car implements Comparable<Car> {

	private CarMake make;
	private String model;
	private int year;
	private CarColor color;
	private double usdPrice;
	private CarSize size;
	
	public Car( CarMake make, String model, int year, 
								CarColor color, double price, CarSize size){
		setMake(make);
		setModel(model);
		setYear(year);
		setColor(color);
		setUsdPrice(price);
		setSize(size);
		
		
	}

	/**
	 * Return the name of the car in the form year, make, model --
	 * 		e.g. "1999 Ford Taurus"
	 * @return the string representation of the car name.
	 */
	public String getCarName(){
		return year + " " + make + " " + model;
	}
	
	/**
	 * Implementation of Comparable.compareTo().  This compare implementation
	 * only compares the car price.
	 * @return -1, 0, 1 for less than, equal to, or more than respectively
	 */
	public int compareTo( Car other ){
		if( other.getUsdPrice() == this.getUsdPrice() )
			return 0;
		
		return other.getUsdPrice() < this.getUsdPrice() ? 1 : -1;
	}
	
	/* ************* Getter / Setter **************************************/

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
