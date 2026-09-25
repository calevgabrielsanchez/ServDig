package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResumenAseguradoTramiteCda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4012748708806740734L;

	private String refCurp;
	private String lstNssInvolucrados;
	private String nombreCompletoAsegurado;
	private String descEstadoTramite;
	private Date fechaRegistroAlta;



	public String getRefCurp() {
		return refCurp;
	}
	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}
	
	public String getLstNssInvolucrados() {
		return lstNssInvolucrados;
	}
	public void setLstNssInvolucrados(String lstNssInvolucrados) {
		this.lstNssInvolucrados = lstNssInvolucrados;
	}
	public String getNombreCompletoAsegurado() {
		return nombreCompletoAsegurado;
	}
	public void setNombreCompletoAsegurado(String nombreCompletoAsegurado) {
		this.nombreCompletoAsegurado = nombreCompletoAsegurado;
	}
	public String getDescEstadoTramite() {
		return descEstadoTramite;
	}
	public void setDescEstadoTramite(String descEstadoTramite) {
		this.descEstadoTramite = descEstadoTramite;
	}
	public Date getFechaRegistroAlta() {
		return fechaRegistroAlta;
	}
	public void setFechaRegistroAlta(Date fechaRegistroAlta) {
		this.fechaRegistroAlta = fechaRegistroAlta;
	}
	
	
}
