package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ObraSirocModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
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
	
	private String tipoPatron;
	private String numeroRegistroObra;
	private String nombreRazonSocial;
	private String numeroObraContratante;
	
	
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
	public String getTipoPatron() {
		return tipoPatron;
	}
	public void setTipoPatron(String tipoPatron) {
		this.tipoPatron = tipoPatron;
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
	public String getNumeroObraContratante() {
		return numeroObraContratante;
	}
	public void setNumeroObraContratante(String numeroObraContratante) {
		this.numeroObraContratante = numeroObraContratante;
	}
	
	
}
