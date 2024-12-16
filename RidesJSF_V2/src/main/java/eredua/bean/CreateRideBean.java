package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import businessLogicHib.BLFacade;
import eredua.domeinua.Driver;
import eredua.domeinua.Kotxea;

@SessionScoped
public class CreateRideBean {
	
	private String depart;
	private String arrival;
	private int seats;
	private double price;
	private Date data;
	private BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private Driver loggedDriver;
	private String kotxea;
	private List<Kotxea> kotxeak = new ArrayList<Kotxea>();

	public CreateRideBean () {
		
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getKotxea() {
		return kotxea;
	}
	
	public void setKotxea(String kotxea) {
		this.kotxea = kotxea;
	}
	
	public List<Kotxea> getKotxeak() {
	    if (kotxeak == null || kotxeak.isEmpty()) {
	        kotxeak = facadeBL.getDriverKotxeak(getLoggedDriver().getEmail());
	    }
	    return kotxeak;
	}
	
	public void setKotxeak(List<Kotxea> kotxeak) {
		this.kotxeak = kotxeak;
	}
	
	public Driver getLoggedDriver() {
		 if (loggedDriver == null) {
		        FacesContext context = FacesContext.getCurrentInstance();
		        loggedDriver = (Driver) context.getExternalContext().getSessionMap().get("loggedDriver");
		    }
		 return loggedDriver;
	}
	
	public void setLoggedDriver(Driver driver) {
		this.loggedDriver = driver;
	}
	
	public void bidaiaSortu() {
		try {
			
			if(depart == null || depart.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Departing city is required."));
			} else	if(arrival == null || arrival.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Destination city is required."));
			}else if(seats <= 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Seat amount is required and must be greater than 0."));
			}else if(price <= 0.0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Price is required and must be greater than 0.0."));
			}else if(data == null || data.before(new Date())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ride must be later than today."));
			}else {
				facadeBL.createRide(depart, arrival, data, seats, (float)price, getLoggedDriver().getEmail(), kotxea);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ride created successfully."));
			}
		
		} catch(Exception e) {
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error while trying to create ride."));			
			e.printStackTrace();
		}
	}
	
	public void updateSeats(AjaxBehaviorEvent event) {
	    if (kotxea != null) {
	        for (Kotxea k : kotxeak) {
	            if (k.getMatrikula().equals(kotxea)) {
	                seats = k.getEserlekuak();
	                break;
	            }
	        }
	    }
	}

}
