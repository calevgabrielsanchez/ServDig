package mx.gob.imss.ctirss.delta.model.asegurado;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;

public class SolicitudNssCorreo extends AbstractModel {

	private static final long serialVersionUID = -566262188266363336L;
	
	private CorreoElectronico correo;
	private String curp;
	private Long numConsultasPeriodo;
	private Date fechaConsulta;
	private Long cveIdTipoTramite;
	private Long cveIdTipoSolicitud;
	

	/*
	 * Atributo que no pertenece al modelo, pero se utiliza para almacenar la
	 * operaci�n a ejecutar (crear, reiniciar o registrar consulta) con el
	 * correo relacionado al NSS
	 */
	private int operacionEjecutar;

	/**
	 * @return the correo
	 */
	public CorreoElectronico getCorreo() {
		return correo;
	}

	/**
	 * @param correo
	 *            the correo to set
	 */
	public void setCorreo(CorreoElectronico correo) {
		this.correo = correo;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp
	 *            the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the numConsultasPeriodo
	 */
	public Long getNumConsultasPeriodo() {
		return numConsultasPeriodo;
	}

	/**
	 * @param numConsultasPeriodo
	 *            the numConsultasPeriodo to set
	 */
	public void setNumConsultasPeriodo(Long numConsultasPeriodo) {
		this.numConsultasPeriodo = numConsultasPeriodo;
	}

	/**
	 * @return the fechaConsulta
	 */
	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	/**
	 * @param fechaConsulta
	 *            the fechaConsulta to set
	 */
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public int getOperacionEjecutar() {
		return operacionEjecutar;
	}

	public void setOperacionEjecutar(int operacionEjecutar) {
		this.operacionEjecutar = operacionEjecutar;
	}
	
	public Long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

	public Long getCveIdTipoSolicitud() {
		return cveIdTipoSolicitud;
	}

	public void setCveIdTipoSolicitud(Long cveIdTipoSolicitud) {
		this.cveIdTipoSolicitud = cveIdTipoSolicitud;
	}

}
