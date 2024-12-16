package eredua.bean;

import javax.faces.context.FacesContext;

import businessLogicHib.BLFacade;
import eredua.domeinua.Driver;
import eredua.domeinua.Traveler;
import eredua.domeinua.User;

public class LogInBean {
	
	private String email;
	private String password;
	private BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private Driver driver;
	private Traveler traveler;
	
	public LogInBean() {
		
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Driver getDriver() {
		return driver;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public Traveler getTraveler() {
		return traveler;
	}
	
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	
	public String getLoggedUser() {
		if(email != null && password != null) {
			User user = facadeBL.Login(email, password);
			if(user != null) {
				if(user instanceof Driver) {
					driver = (Driver) user;
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "driver");
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedDriver", driver);
					return "HomePage?faces-redirect=true";
				}
				else {
					traveler = (Traveler) user;
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "traveler");
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedTraveler", traveler);
					return "TravelerPage?faces-redirect=true";
				}
			}
			return "Login?faces-redirect=true";
		}
		return "Login?faces-redirect=true";
	}
	
	public String getLoggedUserType() {
		return (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userType");
	}
}