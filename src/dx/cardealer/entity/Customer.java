package dx.cardealer.entity;

/**
 * This class represents a customer or prospect of the 
 * car dealership / warehouse.
 * @author Jeff Rabe
 *
 */
public class Customer {
	
	private String firstName;
	private String lastName;
	private String occupation;
	private int annualIncome;
	
	public Customer( String firstName, String lastName, 
									String occupation, int income ){
		
		if( income < 0 ){
			throw new IllegalArgumentException(
					"Annual Income must be 0 or greater: " + income );
			
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.occupation = occupation;
		this.annualIncome = income;
	}
}
