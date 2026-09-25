package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UbicacionOutput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String calle;
	private BigDecimal numExt;
	private String numExtALF;
	private BigDecimal numExtDos;
	private BigDecimal numInterior;
	private String numInteriorALF;
	private String colonia;
	private String municipioAlcaldia;
	private String codigoPostal;
	private String entidadFederativa;
	private String observacionUbicacion;
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public BigDecimal getNumExt() {
		return numExt;
	}
	public void setNumExt(BigDecimal numExt) {
		this.numExt = numExt;
	}
	public String getNumExtALF() {
		return numExtALF;
	}
	public void setNumExtALF(String numExtALF) {
		this.numExtALF = numExtALF;
	}
	public BigDecimal getNumExtDos() {
		return numExtDos;
	}
	public void setNumExtDos(BigDecimal numExtDos) {
		this.numExtDos = numExtDos;
	}
	public BigDecimal getNumInterior() {
		return numInterior;
	}
	public void setNumInterior(BigDecimal numInterior) {
		this.numInterior = numInterior;
	}
	public String getNumInteriorALF() {
		return numInteriorALF;
	}
	public void setNumInteriorALF(String numInteriorALF) {
		this.numInteriorALF = numInteriorALF;
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
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getObservacionUbicacion() {
		return observacionUbicacion;
	}
	public void setObservacionUbicacion(String observacionUbicacion) {
		this.observacionUbicacion = observacionUbicacion;
	}
	
	
	
	
	
}
