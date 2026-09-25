package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alan Garcia
 * @version 1.0
 */

public class ActivaCuentaDTO  implements Serializable{
	
	private Long claveActivaCuenta;
	private SolicitudDTO solicitud;
	private EstatusDTO estatus;
	private String claveMD5;
	private Date fechaRegistro;
	private Date fechaVigencia;
	private Date fechaActiva;

	
	/** Identificador único de la versión de la clase */
	private static final long serialVersionUID = 3515802327994150187L;
	
	
	public ActivaCuentaDTO(){
	}


	public Long getClaveActivaCuenta() {
		return claveActivaCuenta;
	}


	public void setClaveActivaCuenta(Long claveActivaCuenta) {
		this.claveActivaCuenta = claveActivaCuenta;
	}


	public SolicitudDTO getSolicitud() {
		return solicitud;
	}


	public void setSolicitud(SolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}


	public EstatusDTO getEstatus() {
		return estatus;
	}


	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}


	public String getClaveMD5() {
		return claveMD5;
	}


	public void setClaveMD5(String claveM5) {
		this.claveMD5 = claveM5;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public Date getFechaVigencia() {
		return fechaVigencia;
	}


	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}


	public Date getFechaActiva() {
		return fechaActiva;
	}


	public void setFechaActiva(Date fechaActiva) {
		this.fechaActiva = fechaActiva;
	}
	
	public String getEstatusN(){
		if(getFechaVigencia()!=null)
		{
			Date hoy = new Date();
			if(hoy.getTime()>getFechaVigencia().getTime())
				return "VENCIDA";
			else
				return "VIGENTE";
		}
		else
			return "";
	}
	
	
}
