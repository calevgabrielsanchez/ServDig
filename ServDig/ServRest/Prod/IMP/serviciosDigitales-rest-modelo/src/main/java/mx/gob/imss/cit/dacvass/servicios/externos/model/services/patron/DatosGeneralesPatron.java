package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

@XmlRootElement
public class DatosGeneralesPatron implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1488976249113151921L;
	
	private String regPatron;
	private String cveModalidad;
	private String rfc;
	private String nombreRazonSocial;
	private String tipoPersona;
	private BigDecimal cveIdPatronSujetoObligado;
	private Domicilio domicilio;
	private Date fecRegistroAlta;
	
	
	

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public String getRegPatron() {
		return regPatron;
	}
	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}
	public String getCveModalidad() {
		return cveModalidad;
	}
	public void setCveModalidad(String cveModalidad) {
		this.cveModalidad = cveModalidad;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public BigDecimal getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(BigDecimal cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	
	
	
	

}
