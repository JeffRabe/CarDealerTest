package dx.cardealer.error;

/**
 * This Exception class indicates an error condition when a warehouse needs
 * to contain at least 1 car, but does not.
 * 
 * @author Jeff Rabe
 *
 */
public class WarehouseEmptyException extends Exception {

	public WarehouseEmptyException( String message ){
		super(message);
	}
	
	public WarehouseEmptyException(){
		this("");
	}
}
