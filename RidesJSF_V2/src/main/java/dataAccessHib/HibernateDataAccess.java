package dataAccessHib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import configuration.UtilDate;
import eredua.domeinua.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class HibernateDataAccess {
	
	
    public HibernateDataAccess()  {

    }
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	/*public void initializeDB(){
		
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		   
		    //Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez", "123");
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga", "123");
			Driver driver3=new Driver("driver3@gmail.com","Test driver", "123");

			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);

			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			
			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);

			
						
			session.persist(driver1);
			session.persist(driver2);
			session.persist(driver3);

	
			session.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}*/
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT DISTINCT r.depart FROM Ride r ORDER BY r.depart");
		List cities = query.list();
		session.getTransaction().commit();
		return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT DISTINCT r.destination FROM Ride r WHERE r.depart= :from ORDER BY r.destination");
		query.setParameter("from", from);
		List arrivingCities = query.list(); 
		session.getTransaction().commit();
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail, String kotxea) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			session.beginTransaction();
			
			Driver driver = (Driver)session.get(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				session.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Kotxea kotxe = (Kotxea)session.get(Kotxea.class, kotxea);
			Ride ride = driver.addRide(from, to, date, nPlaces, price, kotxe);
			//next instruction can be obviated
			session.persist(driver); 
			session.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			session.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT r FROM Ride r WHERE r.depart= :from AND r.destination= :to AND r.data= :date");   
		query.setParameter("from", from);
		query.setParameter("to", to);
		query.setParameter("date", date);
		List<Ride> rides = query.list();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT DISTINCT r.data FROM Ride r WHERE r.depart= :from AND r.destination= :to AND r.data BETWEEN :firstDayMonthDate and :lastDayMonthDate");   
		
		query.setParameter("from", from);
		query.setParameter("to", to);
		query.setParameter("firstDayMonthDate", firstDayMonthDate);
		query.setParameter("lastDayMonthDate", lastDayMonthDate);
		List<Date> dates = query.list();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	session.getTransaction().commit();
	 	return res;
	}
	
	public List<Ride> getPricedRides(float price) {
		List<Ride> res = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT r FROM Ride r WHERE r.price= :price");
		
		query.setParameter("price", price);
		List<Ride> rides = query.list();
		for (Ride r:rides) {
			res.add(r);
		}
		session.getTransaction().commit();
		return res;
	}
	
	public boolean Register(String email, String name, String password, String type) {
		boolean erregistratu = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT u FROM User u WHERE u.email= :email");
			query.setParameter("email", email);
			erregistratu = query.list().isEmpty();
			if(erregistratu) {
				User user;
				switch(type) {
				case "driver":
					user = new Driver(email, name, password);
					session.persist(user);
					break;
				case "traveler":
					user = new Traveler(email, name, password);
					session.persist(user);
					break;
				}
			}
		} finally {
			session.getTransaction().commit();
		}
		return erregistratu;
	}
	
	public User Login(String email, String password) {
		User user = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT u FROM User u WHERE u.email= :email AND u.password= :password");
			query.setParameter("email", email);
			query.setParameter("password", password);
			user = (User) query.list().get(0);
			session.getTransaction().commit();
		} catch(Exception e) {
			session.getTransaction().rollback();
			return user;
		}
		return user;
	}
	
	public List<Kotxea> getDriverKotxeak(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Kotxea> kotxeak = new ArrayList<Kotxea>();
		Query query = session.createQuery("SELECT d.kotxeak FROM Driver d WHERE d.email= :email");
		query.setParameter("email", email);
		List<Kotxea> res = query.list();
		for(Kotxea k:res) {
			kotxeak.add(k);
		}
		return kotxeak;
	}
	
	public boolean RegisterKotxea(String matrikula, String marka, int eserlekuak, String email) {
		boolean erregistratuKotxea = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT k FROM Kotxea k WHERE k.matrikula= :matrikula AND k.driver.email= :email");
			query.setParameter("matrikula", matrikula);
			query.setParameter("email", email);
			erregistratuKotxea = query.list().isEmpty();
			if(erregistratuKotxea) {
				Kotxea k = new Kotxea(matrikula, marka, eserlekuak);
				Driver d = (Driver)session.get(Driver.class, email);
				d.getKotxeak().add(k);
				k.setDriver(d);
				session.persist(d);
				session.persist(k);
			}
		} finally {
			session.getTransaction().commit();
		}
		return erregistratuKotxea;
	}
}