package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatosGeneralesPatronQuery implements Serializable{


	private static final long serialVersionUID = 8741221353095544449L;

	private String regPatron;
	private String cveModalidad;
	private String rfc;
	private String nombreRazonSocial;
	private BigDecimal domicilioId;
	private String codigoPostal;
	private String desDomicilio;
	private String nombreLocalidad;
	private String tipoPersona;
	private BigDecimal cveIdPatronSujetoObligado;
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
	public BigDecimal getDomicilioId() {
		return domicilioId;
	}
	public void setDomicilioId(BigDecimal domicilioId) {
		this.domicilioId = domicilioId;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getDesDomicilio() {
		return desDomicilio;
	}
	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
	}
	public String getNombreLocalidad() {
		return nombreLocalidad;
	}
	public void setNombreLocalidad(String nombreLocalidad) {
		this.nombreLocalidad = nombreLocalidad;
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
	
	
	

}
