package dx.cardealer.entity;

import java.util.ArrayList;
import java.util.HashMap;

import dx.cardealer.entity.cars.Car;
import dx.cardealer.entity.cars.CarMake;
import dx.cardealer.entity.cars.CarSize;
import dx.cardealer.error.WarehouseFullException;

public class CarWarehouse {
	
	private HashMap<CarSize, Integer> warehouseSpaces;
	private HashMap<CarSize, Integer> warehouseCapacity;
	private HashMap<CarMake, ArrayList<Car>> warehouseInventory;
	
	
	public void addCar( Car newCar )
	throws WarehouseFullException
	{
		if( !spaceAvailable(newCar.getSize()) ){
			throw new WarehouseFullException("There is no more space for this car: " + newCar.getModel());
		}
		
		ArrayList<Car> manufacturerCars = warehouseInventory.get(newCar.getMake());
		manufacturerCars.add(newCar);
	}
	
	public boolean spaceAvailable( CarSize size ){
		return warehouseCapacity.get(size) > warehouseSpaces.get(size);
	}
}
