package eredua.domeinua;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Kotxea {
	@Id
	private String matrikula;
	private String marka;
	private int eserlekuak;
	@ManyToOne(targetEntity=Driver.class, cascade=CascadeType.PERSIST)
	private Driver driver;
	
	public Kotxea() {
		
	}
	
	public Kotxea(String matrikula, String marka, int eserlekuak) {
		this.matrikula = matrikula;
		this.marka = marka;
		this.eserlekuak = eserlekuak;
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
	
	public Driver getDriver() {
		return driver;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Kotxea kotxea = (Kotxea) obj;
	    return Objects.equals(matrikula, kotxea.matrikula); // O cualquier campo único para la comparación
	}

	@Override
	public int hashCode() {
	    return Objects.hash(matrikula);  // O cualquier campo único
	}
}