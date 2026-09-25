package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UbicacionObra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3902991203337871852L;
	
	private String codigoPostal;
	private String calle;
	private BigDecimal numeroExterior;
	private String numeroExteriorAlfa;
	private String colonia;
	private String municipioAlcaldia;
	private String entidadFederativa;
	
	
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	public String getNumeroExteriorAlfa() {
		return numeroExteriorAlfa;
	}
	public void setNumeroExteriorAlfa(String numeroExteriorAlfa) {
		this.numeroExteriorAlfa = numeroExteriorAlfa;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	
	
	public String getMunicipioAlcaldia() {
		return municipioAlcaldia;
	}
	public void setMunicipioAlcaldia(String municipioAlcaldia) {
		this.municipioAlcaldia = municipioAlcaldia;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public BigDecimal getNumeroExterior() {
		return numeroExterior;
	}
	public void setNumeroExterior(BigDecimal numeroExterior) {
		this.numeroExterior = numeroExterior;
	}
	
	
	
}
