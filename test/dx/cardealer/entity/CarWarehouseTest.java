package dx.cardealer.entity;

import dx.cardealer.entity.cars.Car;
import dx.cardealer.entity.cars.CarColor;
import dx.cardealer.entity.cars.CarMake;
import dx.cardealer.entity.cars.CarSize;

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
	

}
