package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogicHib.BLFacade;

public class RegisterBean {

	private String email;
	private String name;
	private String password;
	private String type;
	private BLFacade facadeBL = FacadeBean.getBusinessLogic();
	
	public RegisterBean() {
		
	}
	 
	public String getEmail() {
		return email;
	}
	 
	public void setEmail(String email) {
		this.email = email;
	}
	 
	public String getName() {
		return name;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getPassword() {
		return password;
	}
	 
	public void setPassword(String password) {
		this.password = password;
	}
	 
	public String getType() {
		return type;
	}
	 
	public void setType(String type) {
		this.type = type;
	}
	 
	public void Erregistratu() {
		if(email != null && password != null && type != null) {
			boolean erregistratu = facadeBL.Register(email, name, password, type);
				if(erregistratu) 
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registered succesfully."));
				else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registration went wrong."));
		 }
	 }
}
