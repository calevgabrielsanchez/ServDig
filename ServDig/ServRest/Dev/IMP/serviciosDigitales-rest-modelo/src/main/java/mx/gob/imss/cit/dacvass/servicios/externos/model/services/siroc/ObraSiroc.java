package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObraSiroc implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 152115362055399165L;
	private String numObra;
	//Nuevos parametros entrada 
	private String anioFiscal;
	private String idDelegacion;
	private String idSubdelegacion;
	private String idClaseObra;
	private String idTipoPatron;
	private String idEstatusObra;
	private String idTipoIncidencia;
	//Parametros de salida
	private String numeroRegistroObra;
	private String nombreRazonSocial;
	private String rp;
	private String claseObra;
	private String tipoPatron;
	private String estatusObra;
	private Date fechaRegistro;
	
	private String numeroAviso;
	private String rpRegistra;
	private String rfcPatron;
	private String estatus;
	
	public String getNumObra() {
		return numObra;
	}
	public void setNumObra(String numObra) {
		this.numObra = numObra;
	}
	public String getAnioFiscal() {
		return anioFiscal;
	}
	public void setAnioFiscal(String anioFiscal) {
		this.anioFiscal = anioFiscal;
	}
	public String getIdDelegacion() {
		return idDelegacion;
	}
	public void setIdDelegacion(String idDelegacion) {
		this.idDelegacion = idDelegacion;
	}
	public String getIdSubdelegacion() {
		return idSubdelegacion;
	}
	public void setIdSubdelegacion(String idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}
	public String getIdClaseObra() {
		return idClaseObra;
	}
	public void setIdClaseObra(String idClaseObra) {
		this.idClaseObra = idClaseObra;
	}
	public String getIdTipoPatron() {
		return idTipoPatron;
	}
	public void setIdTipoPatron(String idTipoPatron) {
		this.idTipoPatron = idTipoPatron;
	}
	public String getIdEstatusObra() {
		return idEstatusObra;
	}
	public void setIdEstatusObra(String idEstatusObra) {
		this.idEstatusObra = idEstatusObra;
	}
	public String getIdTipoIncidencia() {
		return idTipoIncidencia;
	}
	public void setIdTipoIncidencia(String idTipoIncidencia) {
		this.idTipoIncidencia = idTipoIncidencia;
	}
	public String getNumeroRegistroObra() {
		return numeroRegistroObra;
	}
	public void setNumeroRegistroObra(String numeroRegistroObra) {
		this.numeroRegistroObra = numeroRegistroObra;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getClaseObra() {
		return claseObra;
	}
	public void setClaseObra(String claseObra) {
		this.claseObra = claseObra;
	}
	public String getTipoPatron() {
		return tipoPatron;
	}
	public void setTipoPatron(String tipoPatron) {
		this.tipoPatron = tipoPatron;
	}
	public String getEstatusObra() {
		return estatusObra;
	}
	public void setEstatusObra(String estatusObra) {
		this.estatusObra = estatusObra;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getNumeroAviso() {
		return numeroAviso;
	}
	public void setNumeroAviso(String numeroAviso) {
		this.numeroAviso = numeroAviso;
	}
	public String getRpRegistra() {
		return rpRegistra;
	}
	public void setRpRegistra(String rpRegistra) {
		this.rpRegistra = rpRegistra;
	}
	public String getRfcPatron() {
		return rfcPatron;
	}
	public void setRfcPatron(String rfcPatron) {
		this.rfcPatron = rfcPatron;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
 
}
