package mx.gob.imss.ctirss.delta.portal.web.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DatosEntradaDetalleIdentidadSujeto extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int idTipoPersona;
	private int idTipoSujeto;

	// Comun
	private Long idPersona;

	// Para patrones
	private String nrp;

	// Para asegurados
	private String nss;
	private Long idAsignacionNss;

	// Para validar si la identidad está completa
	private int idTramite;
	
	public int getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public int getIdTipoSujeto() {
		return idTipoSujeto;
	}

	public void setIdTipoSujeto(int idTipoSujeto) {
		this.idTipoSujeto = idTipoSujeto;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public Long getIdAsignacionNss() {
		return idAsignacionNss;
	}

	public void setIdAsignacionNss(Long idAsignacionNss) {
		this.idAsignacionNss = idAsignacionNss;
	}

	public int getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(int idTramite) {
		this.idTramite = idTramite;
	}

}
