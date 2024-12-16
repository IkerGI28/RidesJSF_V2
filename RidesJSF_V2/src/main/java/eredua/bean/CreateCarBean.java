package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogicHib.BLFacade;
import eredua.domeinua.Driver;

public class CreateCarBean {

	private String matrikula;
	private String marka;
	private int eserlekuak;
	private BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private Driver loggedDriver;
	
	public CreateCarBean() {
		
	}
	 
	public String getMatrikula() {
		return matrikula;
	}
	 
	public void setMatrikula(String matrikula) {
		this.matrikula = matrikula;
	}
	 
	public String getMarka() {
		return marka;
	}
	 
	public void setMarka(String marka) {
		this.marka = marka;
	}
	 
	public int getEserlekuak() {
		return eserlekuak;
	}
	 
	public void setEserlekuak(int eserlekuak) {
		this.eserlekuak = eserlekuak;
	}
	
	public Driver getLoggedDriver() {
		FacesContext context = FacesContext.getCurrentInstance();
		loggedDriver = (Driver) context.getExternalContext().getSessionMap().get("loggedDriver");
		return loggedDriver;
	}
	
	public void setLoggedDriver(Driver driver) {
		this.loggedDriver = driver;
	}
	 
	public void erregistratuKotxea() {
		if(matrikula != null && marka != null && eserlekuak >= 1) {
			boolean erregistratu = facadeBL.RegisterKotxea(matrikula, marka, eserlekuak, getLoggedDriver().getEmail());
				if(erregistratu) 
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vehicle registered succesfully."));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vehicle registration went wrong."));
		 }
	 }
}