package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

public class ImpresionReporteDto extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idTramite;
	private Long idSolicitud;
	private Long idPersona;
	private TipoTramite tipoTramite;
	private Long idUmf;
	private Long idUmfUsuario;
	private Boolean rechazado;
	private Boolean pendienteAut;
	private String mensaje;
	private String personas;
	private boolean muestraBotonImpresion = true;
	
	public Long getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public TipoTramite getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(TipoTramite tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public Long getIdUmf() {
		return idUmf;
	}
	public void setIdUmf(Long idUmf) {
		this.idUmf = idUmf;
	}
	public Long getIdUmfUsuario() {
		return idUmfUsuario;
	}
	public void setIdUmfUsuario(Long idUmfUsuario) {
		this.idUmfUsuario = idUmfUsuario;
	}
	public Boolean getRechazado() {
		return rechazado;
	}
	public void setRechazado(Boolean rechazado) {
		this.rechazado = rechazado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Boolean getPendienteAut() {
		return pendienteAut;
	}
	public void setPendienteAut(Boolean pendienteAut) {
		this.pendienteAut = pendienteAut;
	}
	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * @return the personas
	 */
	public String getPersonas() {
		return personas;
	}
	/**
	 * @param personas the personas to set
	 */
	public void setPersonas(String personas) {
		this.personas = personas;
	}
	
	
	public boolean isMuestraBotonImpresion() {
		return muestraBotonImpresion;
	}
	public void setMuestraBotonImpresion(boolean muestraBotonImpresion) {
		this.muestraBotonImpresion = muestraBotonImpresion;
	}
	
	
	
	
}
