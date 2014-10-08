package dx.cardealer.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dx.cardealer.entity.cars.Car;
import dx.cardealer.entity.cars.CarMake;
import dx.cardealer.entity.cars.CarSize;
import dx.cardealer.error.WarehouseFullException;

public class CarWarehouse {
	
	private HashMap<CarSize, Integer> warehouseSpacesUsed;
	private HashMap<CarSize, Integer> warehouseCapacity;
	private HashMap<CarMake, ArrayList<Car>> warehouseInventory;
	
	
	public CarWarehouse( HashMap<CarSize, Integer> initialCapacities ){
		
		warehouseCapacity = new HashMap<CarSize, Integer>();
		
		for( CarSize size : CarSize.values() ){
			if( !initialCapacities.containsKey(size) ){
				throw new IllegalArgumentException(
						"The following car size was not represented: " + size );
			}
			
			int sizeCapacity = initialCapacities.get(size);
			warehouseCapacity.put(size, sizeCapacity);
		}
		
		warehouseSpacesUsed = new HashMap<CarSize, Integer>();
		warehouseInventory = new HashMap<CarMake, ArrayList<Car>>();
		for( CarMake make : CarMake.values() ){
			warehouseInventory.put( make, new ArrayList<Car>() );
		}
		
	}
	
	public CarWarehouse( HashMap<CarSize, Integer> initialCapacities,
													List<Car> inventory )
	throws WarehouseFullException
	{
		this( initialCapacities );
		addNewCarInventory( inventory );
	
	}
	
	public void addNewCarInventory( List<Car> newInventory )
	throws WarehouseFullException
	{
		if( !hasCapacityForCars(newInventory) ){
			throw new WarehouseFullException(
					"The warehouse does not have available space "
					+ "for all of these cars."
					);
		}
		
		Iterator<Car> carItr = newInventory.iterator();
		
		while( carItr.hasNext() ){
			Car newCar = carItr.next();
			addCar( newCar );
		}
	}
	
	public boolean hasCapacityForCars( List<Car> newInventory ){
		Iterator<Car> carItr = newInventory.iterator();
		HashMap<CarSize, Integer> newInventoryTotals = new HashMap<CarSize, Integer>();
		
		for( CarSize size : CarSize.values() )
			newInventoryTotals.put(size, 0);
		
		while( carItr.hasNext() ){
			Car currentCar = carItr.next();
			int sizeTotal = newInventoryTotals.get( currentCar.getSize() );
			newInventoryTotals.put(currentCar.getSize(), ++sizeTotal );
		}
		
		for( CarSize size : newInventoryTotals.keySet() ){
			int newCarTotal = newInventoryTotals.get(size);
			int availableSpaces = getAvailableSpaces(size);
			
			if( newCarTotal > availableSpaces )
				return false;
		}
		
		return true;
	}
	
	public void addCar( Car newCar )
	throws WarehouseFullException
	{
		if( !spaceAvailable(newCar.getSize()) ){
			throw new WarehouseFullException("There is no more space for this car: " + newCar.getModel());
		}
		
		ArrayList<Car> manufacturerCars = warehouseInventory.get(newCar.getMake());
		manufacturerCars.add(newCar);
		int spacesUsed = warehouseSpacesUsed.get(newCar.getSize());
		warehouseSpacesUsed.put( newCar.getSize(), ++spacesUsed );
	}
	
	public boolean spaceAvailable( CarSize size ){
		return getAvailableSpaces(size) > 0;
	}
	
	public int getAvailableSpaces( CarSize size ){
		return warehouseCapacity.get(size) - warehouseSpacesUsed.get(size);
	}
}
