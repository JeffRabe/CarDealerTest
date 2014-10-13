package dx.cardealer.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import dx.cardealer.entity.cars.Car;
import dx.cardealer.entity.cars.CarColor;
import dx.cardealer.entity.cars.CarMake;
import dx.cardealer.entity.cars.CarSize;
import dx.cardealer.error.WarehouseEmptyException;
import dx.cardealer.error.WarehouseFullException;


public class CarWarehouseTest {

	
	private Car honda = new Car(CarMake.Honda,
			"CIVIC", 1999, CarColor.Black, 12000.00, CarSize.MidSize);
	
	private Car bmw = new Car(CarMake.BMW, "535i Gran Tourismo",
				2008, CarColor.Blue, 64390.00, CarSize.MidSize);
	
	private Car ford = new Car(CarMake.Ford, "Mustang",
				1990, CarColor.Red, 29000.00, CarSize.Large);
	
	private Car toyota = new Car(CarMake.Toyota, "Camry",
				2012, CarColor.Black, 30999.00, CarSize.MidSize);
	
	private Car gm = new Car(CarMake.Chevrolet, "Camero",
				1981, CarColor.Green, 19000.00, CarSize.Large);
	

	Customer prospect = new Customer("John", "Smith", "Doctor", 200000);
	
	/**
	 * Test the case where a car is added when there is
	 * no space in the warehouse for that car.  This shoul
	 * cause an exception to be thrown.
	 */
	@Test( expected = WarehouseFullException.class)
	public void addCarWithoutSpace_throwsException()
	throws WarehouseFullException
	{
		
		CarWarehouse warehouse = new CarWarehouse(
				new HashMap<CarSize, Integer>());
		
		warehouse.addCar(honda);
	}
	
	/**
	 * Test the case that the reccommendCars method
	 * 	does in fact return a non-zero number of cars
	 * when the warehouse is not empty.
	 */
	@Test
	public void recommendCars_returnsNonZeroList()
	throws WarehouseFullException, WarehouseEmptyException
	{
		ArrayList<Car> cars = new ArrayList<Car>();
		cars.add(honda);
		cars.add(bmw);
		cars.add(toyota);
		cars.add(ford);
		cars.add(gm);
		
		HashMap<CarSize, Integer> sizes = new HashMap<CarSize, Integer>();
		sizes.put(CarSize.Compact, 100);
		sizes.put(CarSize.MidSize, 100);
		sizes.put(CarSize.Large, 100);
		
		CarWarehouse warehouse = new CarWarehouse(sizes, cars);
		
		List<Car> recommendedCars = warehouse.getRecommendedCars(prospect);
		
		assertTrue( recommendedCars.size() > 0 );
	}
	
	/**
	 * Test the case that the reccommendCars method
	 * 	does throws an exception
	 * when the warehouse is empty.
	 */
	@Test(expected = WarehouseEmptyException.class)
	@SuppressWarnings("unused")
	public void recommendCars_empty_throwsException()
	throws WarehouseFullException, WarehouseEmptyException
	{
		
		HashMap<CarSize, Integer> sizes = new HashMap<CarSize, Integer>();
		sizes.put(CarSize.Compact, 100);
		sizes.put(CarSize.MidSize, 100);
		sizes.put(CarSize.Large, 100);
		
		CarWarehouse warehouse = new CarWarehouse(sizes);
		
		List<Car> recommendedCars = warehouse.getRecommendedCars(prospect);
		
	}
	
	/**
	 * Test the case that the addCar Method throws
	 * an exception if the warehouse does not have sufficient
	 * space to store the car in question
	 */
	@Test(expected = WarehouseFullException.class)
	public void addCar_full_throwsException()
	throws WarehouseFullException, WarehouseEmptyException
	{
		
		
		HashMap<CarSize, Integer> sizes = new HashMap<CarSize, Integer>();
		sizes.put(CarSize.Compact, 2);
		sizes.put(CarSize.MidSize, 2);
		sizes.put(CarSize.Large, 2);
		
		CarWarehouse warehouse = new CarWarehouse(sizes);
		warehouse.addCar(honda);
		warehouse.addCar(bmw);
		warehouse.addCar(toyota);
		
	}
	
	
}
