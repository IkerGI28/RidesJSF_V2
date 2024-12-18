package businessLogicHib;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import dataAccessHib.HibernateDataAccess;
import eredua.domeinua.*;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
public class BLFacadeImplementation  implements BLFacade {
	HibernateDataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new HibernateDataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(HibernateDataAccess da)  {
		
		/*System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();*/
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    public List<String> getDepartCities(){
		
		 List<String> departLocations=dbManager.getDepartCities();		

		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	public List<String> getDestinationCities(String from){
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail, String kotxea) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail, kotxea);		
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	public List<Ride> getRides(String from, String to, Date date){
		List<Ride> rides=dbManager.getRides(from, to, date);
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		return dates;
	}
	
	public List<Ride> getPricedRides(float price){
		List<Ride> pricedRides=dbManager.getPricedRides(price);
		return pricedRides;
	}
	
	public boolean Register(String email, String name, String password, String type) {
		boolean erregistratu=dbManager.Register(email, name, password, type);
		return erregistratu;
	}
	
	public User Login(String email, String password) {
		User user=dbManager.Login(email, password);
		return user;
	}
	
	public List<Kotxea> getDriverKotxeak(String email) {
		List<Kotxea> matrikulak=dbManager.getDriverKotxeak(email);
		return matrikulak;
	}
	
	public boolean RegisterKotxea(String matrikula, String marka, int eserlekuak, String email) {
		boolean erregistratuKotxea=dbManager.RegisterKotxea(matrikula, marka, eserlekuak, email);
		return erregistratuKotxea;
	}
	
	/*public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}*/

	/**
	 * {@inheritDoc}
	 */
	 /*public void initializeBD(){
		dbManager.initializeDB();
	 }*/
}