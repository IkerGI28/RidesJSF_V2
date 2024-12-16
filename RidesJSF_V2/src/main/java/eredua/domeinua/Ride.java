package eredua.domeinua;

import java.io.*;
import java.util.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
public class Ride implements Serializable {
	@Id 
	@GeneratedValue
	private Integer rideNumber;
	private String depart;
	private String destination;
	private int nPlaces;
	private Date data;
	private float price;
	@ManyToOne(targetEntity=Driver.class, cascade=CascadeType.PERSIST)
	private Driver driver; 
	@ManyToOne(targetEntity=Kotxea.class, cascade=CascadeType.PERSIST)
	private Kotxea kotxea;
	
	public Ride(){
		super();
	}
	
	public Ride(Integer rideNumber, String depart, String destination, Date data, int nPlaces, float price, Driver driver) {
		super();
		this.rideNumber = rideNumber;
		this.depart = depart;
		this.destination = destination;
		this.nPlaces = nPlaces;
		this.data=data;
		this.price=price;
		this.driver = driver;
	}

	

	public Ride(String depart, String destination,  Date data, int nPlaces, float price, Driver driver) {
		super();
		this.depart = depart;
		this.destination = destination;
		this.nPlaces = nPlaces;
		this.data=data;
		this.price=price;
		this.driver = driver;
	}
	
	public Ride(String depart, String destination,  Date data, int nPlaces, float price, Driver driver, Kotxea kotxea) {
		super();
		this.depart = depart;
		this.destination = destination;
		this.nPlaces = nPlaces;
		this.data=data;
		this.price=price;
		this.driver = driver;
		this.kotxea = kotxea;
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}


	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public String getDepart() {
		return depart;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	
	
	public void setDepart(String depart) {
		this.depart = depart;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public String getDestination() {
		return destination;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getData() {
		return data;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setData(Date data) {
		this.data = data;
	}

	
	public float getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Kotxea getKotxea() {
		return kotxea;
	}
	
	public void setKotxea(Kotxea kotxea) {
		this.kotxea = kotxea;
	}

	public String toString(){
		return rideNumber+";"+";"+depart+";"+destination+";"+data;  
	}




	
}