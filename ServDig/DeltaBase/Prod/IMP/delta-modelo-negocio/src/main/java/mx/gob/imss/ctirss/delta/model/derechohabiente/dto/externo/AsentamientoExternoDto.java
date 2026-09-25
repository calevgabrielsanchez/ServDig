package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo;

import java.io.Serializable;

public class AsentamientoExternoDto   implements Serializable{ 
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoPostal;
	private String cveAsentamiento;
	private String cveMunicipio;
	private String cveEntidad;
	
	
	
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCveAsentamiento() {
		return cveAsentamiento;
	}
	public void setCveAsentamiento(String cveAsentamiento) {
		this.cveAsentamiento = cveAsentamiento;
	}
	public String getCveMunicipio() {
		return cveMunicipio;
	}
	public void setCveMunicipio(String cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}
	public String getCveEntidad() {
		return cveEntidad;
	}
	public void setCveEntidad(String cveEntidad) {
		this.cveEntidad = cveEntidad;
	}
	
	
	


}
