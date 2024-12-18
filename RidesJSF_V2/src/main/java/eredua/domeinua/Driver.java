package eredua.domeinua;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Driver extends User {

	@OneToMany(fetch=FetchType.EAGER, mappedBy="driver", cascade=CascadeType.ALL)
	private List<Ride> rides=new ArrayList<Ride>();
	@OneToMany(fetch=FetchType.LAZY, mappedBy="driver", cascade=CascadeType.ALL)
	private List<Kotxea> kotxeak=new ArrayList<Kotxea>();

	public Driver() {
		super();
	}
	
	public Driver(String email, String name, String password) {
		super(email, name, password);
	}
	
	public Driver(String email, String name, String password, List<Ride> rides) {
		super(email, name, password);
		this.rides = rides;
	}
	
	public List<Kotxea> getKotxeak() {
		return kotxeak;
	}
	
	public void setKotxeak(List<Kotxea> kotxeak) {
		this.kotxeak = kotxeak;
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price, Kotxea kotxea)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this, kotxea);
        rides.add(ride);
        return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String depart, String destination, Date data)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getDepart(),depart)) && (java.util.Objects.equals(r.getDestination(),destination)) && (java.util.Objects.equals(r.getData(),data)) )
			 return true;
		
		return false;
	}

	public Ride removeRide(String depart, String destination, Date data) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getDepart(),depart)) && (java.util.Objects.equals(r.getDestination(),destination)) && (java.util.Objects.equals(r.getData(),data)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	
}
