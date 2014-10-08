package dx.cardealer.error;

public class WarehouseFullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -493004709418531101L;

	public WarehouseFullException( String message ){
		super(message);
	}
	
	public WarehouseFullException(){
		this("");
	}
}
