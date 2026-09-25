package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;

/**
 * 
 * @author Mario Teran Blanco
 *
 */
public class ValidacionRegDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SES_NAME="miValidacion";
	
	private long parentesco;
	private String razonRegistro;
	private long idRazonRegistro;
	private RazonRegistro tipoRegistro;
	private long idSolicitud;
	private long idPersona;
	private long tipoTramite;
	private long idTramite;
	private MedicoEnTurno medicoEnTurno;
	private String observaciones;
	private String fechaNacimiento;
	private EstadoDerechohabiente estadoDerechohabiente;
	private SubEstadoDerechohabiente subEstadoDerechohabiente;
	private String domDif;
	private String fechaCambioUmf;
	private long umfAsegurado;
	private long umfNvoIntegrante;
	
	
	public long getParentesco() {
		return parentesco;
	}
	public void setParentesco(long parentesco) {
		this.parentesco = parentesco;
	}
	public String getRazonRegistro() {
		return razonRegistro;
	}
	public void setRazonRegistro(String razonRegistro) {
		this.razonRegistro = razonRegistro;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	public long getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(long tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public MedicoEnTurno getMedicoEnTurno() {
		return medicoEnTurno;
	}
	public void setMedicoEnTurno(MedicoEnTurno medicoEnTurno) {
		this.medicoEnTurno = medicoEnTurno;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public long getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(long idTramite) {
		this.idTramite = idTramite;
	}
	public long getIdRazonRegistro() {
		return idRazonRegistro;
	}
	public void setIdRazonRegistro(long idRazonRegistro) {
		this.idRazonRegistro = idRazonRegistro;
	}
	/**
	 * @return the tipoRegistro
	 */
	public RazonRegistro getTipoRegistro() {
		return tipoRegistro;
	}
	/**
	 * @param tipoRegistro the tipoRegistro to set
	 */
	public void setTipoRegistro(RazonRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	/**
	 * @return the estadoDerechohabiente
	 */
	public EstadoDerechohabiente getEstadoDerechohabiente() {
		return estadoDerechohabiente;
	}
	/**
	 * @param estadoDerechohabiente the estadoDerechohabiente to set
	 */
	public void setEstadoDerechohabiente(EstadoDerechohabiente estadoDerechohabiente) {
		this.estadoDerechohabiente = estadoDerechohabiente;
	}
	/**
	 * @return the subEstadoDerechohabiente
	 */
	public SubEstadoDerechohabiente getSubEstadoDerechohabiente() {
		return subEstadoDerechohabiente;
	}
	/**
	 * @param subEstadoDerechohabiente the subEstadoDerechohabiente to set
	 */
	public void setSubEstadoDerechohabiente(
			SubEstadoDerechohabiente subEstadoDerechohabiente) {
		this.subEstadoDerechohabiente = subEstadoDerechohabiente;
	}
	public String getDomDif() {
		return domDif;
	}
	public void setDomDif(String domDif) {
		this.domDif = domDif;
	}
	
	public long getUmfAsegurado() {
		return umfAsegurado;
	}
	public void setUmfAsegurado(long umfAsegurado) {
		this.umfAsegurado = umfAsegurado;
	}
	public long getUmfNvoIntegrante() {
		return umfNvoIntegrante;
	}
	public void setUmfNvoIntegrante(long umfNvoIntegrante) {
		this.umfNvoIntegrante = umfNvoIntegrante;
	}
	/**
	 * @return the fechaCambioUmf
	 */
	public String getFechaCambioUmf() {
		return fechaCambioUmf;
	}
	/**
	 * @param fechaCambioUmf the fechaCambioUmf to set
	 */
	public void setFechaCambioUmf(String fechaCambioUmf) {
		this.fechaCambioUmf = fechaCambioUmf;
	}
		
}
