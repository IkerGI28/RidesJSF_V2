package eredua.bean;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.hibernate.classic.Session;

import businessLogicHib.BLFacade;
import dataAccessHib.HibernateUtil;
import eredua.domeinua.*;

@SessionScoped
public class QueryRidesBean {
	
	private String depart;
	private List<String> departs = new ArrayList<String>();
	private String arrival;
	private List<String> arrivals = new ArrayList<String>();
	private Date data = new Date();
	private List<Date> availableDates = new ArrayList<Date>();
	private List<Ride> ridesOnDate = new ArrayList<Ride>();
	private BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private LogInBean log = new LogInBean();
	
	public QueryRidesBean() {

	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public List<String> getDeparts() {
		departs = facadeBL.getDepartCities();
		return departs;
	}

	public void setDeparts(List<String> departs) {
		this.departs = departs;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public List<String> getArrivals() {
		return arrivals;
	}

	public void setArrivals(List<String> arrivals) {
		this.arrivals = arrivals;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public List<Date> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(List<Date> availableDates) {
		this.availableDates = availableDates;
	}

	public List<Ride> getRidesOnDate() {
		return ridesOnDate;
	}

	public void setRidesOnDate(List<Ride> ridesOnDate) {
		this.ridesOnDate = ridesOnDate;
	}
	
	public void updateArrivals(AjaxBehaviorEvent event) {
		if (depart != null && !depart.isEmpty()) {
			System.out.println("sd");
	        arrivals = facadeBL.getDestinationCities(depart); 
	    } 
	}
	
	public void updateAvailableDates(AjaxBehaviorEvent event) {
		if (arrivals != null && arrival != null) {
	    	availableDates = facadeBL.getThisMonthDatesWithRides(depart, arrival, data);	
	    	System.out.println("a");
	    }
	}
	
	public String loadRidesOnDate() {
		if(depart != null && arrival != null) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			ridesOnDate.clear();
			for(Date date: availableDates) {
				ridesOnDate.addAll(facadeBL.getRides(depart, arrival, date));
			}
			session.getTransaction().commit();
		}
		return "";
	}
	
	public String redirectToCorrespondingMenu() {
		String userType = log.getLoggedUserType();
		if("driver".equals(userType))
			return "HomePage?faces-redirect=true";
		else if("traveler".equals(userType))
			return "TravelerPage?faces-redirect=true";
		else
			return "QueryRides?faces-redirect=true";
	}
}
