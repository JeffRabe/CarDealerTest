package dx.cardealer.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dx.cardealer.entity.cars.Car;
import dx.cardealer.entity.cars.CarMake;
import dx.cardealer.entity.cars.CarSize;
import dx.cardealer.error.WarehouseEmptyException;
import dx.cardealer.error.WarehouseFullException;

/**
 * This class represents a car dealership warehouse that
 * stores the dealership inventory.  
 * 
 * Currently the warehouse is merely responsible for storing
 * the car inventory based different space for different sized
 * cars, and also determining what cars a prospective or
 * returning customer might want to purchase
 * @author jrabe
 *
 */
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
		warehouseSpacesUsed = new HashMap<CarSize, Integer>();
		warehouseInventory = new HashMap<CarMake, ArrayList<Car>>();
		for( CarSize size : CarSize.values() ){
			if( !initialCapacities.containsKey(size) ){
				warehouseCapacity.put( size, 0 );
				
			}else{
			
				int sizeCapacity = initialCapacities.get(size);
				warehouseCapacity.put(size, sizeCapacity);
			}
			
			warehouseSpacesUsed.put(size, 0);
		}
		

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
	 * Recommend cars based on a Customer / Prospect and the
	 * 			 warehouse's internal statistics and lead algorithms.
	 * @param prospect the Customer in question.
	 * @return A list of cars ordered by price.
	 * @throws WarehouseEmptyException if the warehouse inventory is empty.
	 */
	public List<Car> getRecommendedCars( Customer prospect )
	throws WarehouseEmptyException
	{
		return recommendCars( prospect );
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
	
	/**
	 * Get the total number of cars in the warehouse inventory
	 * @return the total number of cars that the warehouse contains.
	 */
	public int getTotalWarehouseCars(){
		
		int total = 0;
		
		for( CarSize size : CarSize.values() ){
			int totalForSize = warehouseSpacesUsed.get(size);
			total += totalForSize;
		}
		
		return total;
	}
	
	/* *********************** PRIVATE **************************************/
	
	private List<Car> recommendCars( Customer prospect )
	throws WarehouseEmptyException
	{	
		int totalRecommendations = 0;
		int totalCars = getTotalWarehouseCars();
		if( totalCars == 0 )
			throw new WarehouseEmptyException();
		
		
		if( totalCars < 3 ){
			totalRecommendations = totalCars;
		}else{
			
			// Just a random even / odd check based on timestamp
			int timestamp = (int) (System.currentTimeMillis() / 1000L);
			if( (timestamp & 1) == 0 )
				totalRecommendations = 2;
			else
				totalRecommendations = 3;
		}
			
		List<Car> recommendations = getFirstCars( totalRecommendations );
		Collections.sort(recommendations);
		
		return recommendations;
		
	}
	
	/* 
	 * Return the first n cars that are found in the warehouse inventory.
	 * @param numberOfCars the number of cars to return.  If there are less
	 * 		 than numberOfCars in the inventory, the full inventory will be
	 * 		 returned.
	 * @return a list of cars.
	 * @notes this method merely iterates until it finds the requested
	 * 		number or cars.
	 */
	private List<Car> getFirstCars( int numberOfCars ){
		
		ArrayList<Car> firstCars = new ArrayList<Car>();
		
		for( CarMake make : CarMake.values() ){
			ArrayList<Car> makeCars = warehouseInventory.get(make);
			Iterator<Car> itr = makeCars.iterator();
			while( itr.hasNext() ){
				firstCars.add(itr.next());
				if( firstCars.size() == numberOfCars )
					return firstCars;
			}
		}
		
		return firstCars;
	}
}
