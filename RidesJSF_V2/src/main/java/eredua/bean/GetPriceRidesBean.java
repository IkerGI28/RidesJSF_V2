package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.classic.Session;

import businessLogicHib.BLFacade;
import dataAccessHib.HibernateUtil;
import eredua.domeinua.Ride;

public class GetPriceRidesBean{
	
	private double prezioa;
	private BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private List<Ride> ridesWithPrice = new ArrayList<Ride>();
	private LogInBean log = new LogInBean();
	
	public GetPriceRidesBean() {
		
	}
	
	public double getPrezioa() {
		return prezioa;
	}
	
	public void setPrezioa(double prezioa) {
		this.prezioa = prezioa;
	}
	
	public List<Ride> getRidesWithPrice() {
		if(prezioa >= 0.0) {
			ridesWithPrice = facadeBL.getPricedRides((float)prezioa);
			return ridesWithPrice;
		}
		return new ArrayList<Ride>();
	}
	
	public void setRidesWithPrice(List<Ride> ridesWithPrice) {
		this.ridesWithPrice = ridesWithPrice;
	}
	
	public String redirectToCorrespondingMenu() {
		String userType = (String)log.getLoggedUserType();
		if("driver".equals(userType)) 
			return "HomePage?faces-redirect=true";
		else if("traveler".equals(userType))
			return "TravelerPage?faces-redirect=true";
		else
			return "PriceButton?faces-redirect=true";
			
	}
	
	/*public String loadPricedRides() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		if(prezioa >= 0.0) {
			List<Ride> rides = facadeBL.getPricedRides((float)prezioa);
			for(Ride r:rides) {
				ridesWithPrice.add(r);
			}
		}
		session.getTransaction().commit();
		return "PriceRidesTable?faces-redirect=true";
	}*/
}