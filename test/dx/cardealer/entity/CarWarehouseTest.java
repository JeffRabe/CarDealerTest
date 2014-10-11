package dx.cardealer.entity;

import java.util.HashMap;

import org.junit.Test;

import dx.cardealer.entity.cars.Car;
import dx.cardealer.entity.cars.CarColor;
import dx.cardealer.entity.cars.CarMake;
import dx.cardealer.entity.cars.CarSize;
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
}
