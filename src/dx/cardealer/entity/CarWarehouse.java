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
	
	/**
	 * Create a new warehouse with the specified capacities for all
	 * car sizes.
	 * @param initialCapacities a hashmap that contains an integer
	 * 		  representing a capacity for each defined car size.
	 */
	public CarWarehouse( HashMap<CarSize, Integer> initialCapacities ){
		
		warehouseCapacity = new HashMap<CarSize, Integer>();
		
		for( CarSize size : CarSize.values() ){
			if( !initialCapacities.containsKey(size) ){
				warehouseCapacity.put( size, 0 );
				//throw new IllegalArgumentException(
				//		"The following car size was not represented: " + size );
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
	
	/**
	 * Add new cars to the warehouse inventory.
	 * @param newInventory a list of Cars to be added to the
	 * 		  warehouse inventory.
	 * @throws WarehouseFullException if there is not space for all
	 * 		   of the new inventory cars to be added to this warehouse.
	 */
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
	
	/**
	 * Determine if the warehouse has capacity for a list of new cars
	 * 			 to be added to the inventory.
	 * @param newInventory a list of cars to be added to the warehouse
	 * 			inventory.
	 * @return true if the warehouse has capacity, false otherwise.
	 */
	public boolean hasCapacityForCars( List<Car> newInventory ){
		Iterator<Car> carItr = newInventory.iterator();
		
		// Track number of total cars for each car size.
		HashMap<CarSize, Integer> newInventoryTotals = new HashMap<CarSize, Integer>();
		
		for( CarSize size : CarSize.values() )
			newInventoryTotals.put(size, 0);
		
		// Iterate over cars and track how many of each size there are.
		while( carItr.hasNext() ){
			Car currentCar = carItr.next();
			int sizeTotal = newInventoryTotals.get( currentCar.getSize() );
			newInventoryTotals.put(currentCar.getSize(), ++sizeTotal );
		}
		
		// if there are too many cars of any size, return false
		for( CarSize size : newInventoryTotals.keySet() ){
			int newCarTotal = newInventoryTotals.get(size);
			int availableSpaces = getAvailableSpaces(size);
			
			if( newCarTotal > availableSpaces )
				return false;
		}
		
		return true;
	}
	
	/**
	 * Add a Car to the warehouse inventory.
	 * @param newCar the Car to be added to the warehouse.
	 * @throws WarehouseFullException if no space is available in the 
	 * 			warehouse to store the car.
	 */
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
	
	/**
	 * Determine if a space is available for the passed CarSize.
	 * @param size the CarSize in question.
	 * @return true if spaces are available, false otherwise.
	 */
	public boolean spaceAvailable( CarSize size ){
		return getAvailableSpaces(size) > 0;
	}
	
	/**
	 * return the number of available car spaces for the given car size.
	 * @param size the CarSize in question.
	 * @return the integer number of spaces available.
	 */
	public int getAvailableSpaces( CarSize size ){
		return warehouseCapacity.get(size) - warehouseSpacesUsed.get(size);
	}
}
