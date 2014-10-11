package dx.cardealer.entity.car;

import org.junit.Test;

import dx.cardealer.entity.cars.Car;
import dx.cardealer.entity.cars.CarColor;
import dx.cardealer.entity.cars.CarMake;
import dx.cardealer.entity.cars.CarSize;

public class CarTest {


	/**
	 * Test that passing a negative price to the constructor
	 * throws an exception
	 */
	@Test( expected = IllegalArgumentException.class)
	@SuppressWarnings("unused")
	public void negativePrice_throwsException()
	{
		
		Car car = new Car(CarMake.Honda, "CIVIC", 1993, CarColor.Black,
				-1000.00, CarSize.MidSize);
	}
	
	/**
	 * Test that passing a negative year to the constructor
	 * throws an exception
	 */
	@Test( expected = IllegalArgumentException.class)
	@SuppressWarnings("unused")
	public void negativeYear_throwsException()
	{
		
		Car car = new Car(CarMake.Honda, "CIVIC", -100, CarColor.Black,
				-1000.00, CarSize.MidSize);
	}
	
	/**
	 * Test that passing an unreasonable year (too early)
	 * 		to the constructor
	 * throws an exception
	 */
	@Test( expected = IllegalArgumentException.class)
	@SuppressWarnings("unused")
	public void earlyYear_throwsException()
	{
		
		Car car = new Car(CarMake.Honda, "CIVIC", 1200, CarColor.Black,
				-1000.00, CarSize.MidSize);
	}
	
	/**
	 * Test that passing a year in the future to the constructor
	 * throws an exception
	 */
	@Test( expected = IllegalArgumentException.class)
	@SuppressWarnings("unused")
	public void futureYear_throwsException()
	{
		
		Car car = new Car(CarMake.Honda, "CIVIC", 20202, CarColor.Black,
				-1000.00, CarSize.MidSize);
	}

}
