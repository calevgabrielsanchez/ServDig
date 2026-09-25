/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * @author JUAN MANUEL M�RQUEZ
 *
 */
public class CabezaGrupoFamiliar extends AbstractModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected long asignacionNSS;
	protected Parentesco calidadParentesco;
	protected Date fechaUltimoMovAfiliacion;
	protected Integer patronImss;
	protected SujetoObligado patronSujetoObligado;
	protected TipoMovtoAsegurado tipoMovtoAsegurado;
	protected EstadoDerechohabiente estadoDerechohabiente;
	protected SubEstadoDerechohabiente subEstadoDerechohabiente;
	protected Date fechaInicioVigencia;
	protected Date fechaFinVigencia;
	protected Date fechaRegistroActualizado;
	protected Long idModalidad;
	protected Integer cveEstadoInconsistencia;
	protected String conDerechoSm;
	
	
	
	
	
	
	
	public Integer getCveEstadoInconsistencia() {
		return cveEstadoInconsistencia;
	}
	public void setCveEstadoInconsistencia(Integer cveEstadoInconsistencia) {
		this.cveEstadoInconsistencia = cveEstadoInconsistencia;
	}
	protected boolean esEstudiante = false;
	
	/*
	 * Campo para almacenar la fecha de la validez de la constancia de vigencia
	 */
	protected Date fechaValidezConstancia;
	
	/**
	 * @return the asignacionNSS
	 */
	public long getAsignacionNSS() {
		return asignacionNSS;
	}
	/**
	 * @param asignacionNSS the asignacionNSS to set
	 */
	public void setAsignacionNSS(long asignacionNSS) {
		this.asignacionNSS = asignacionNSS;
	}
	/**
	 * @return the calidadParentesco
	 */
	public Parentesco getCalidadParentesco() {
		return calidadParentesco;
	}
	/**
	 * @param calidadParentesco the calidadParentesco to set
	 */
	public void setCalidadParentesco(Parentesco calidadParentesco) {
		this.calidadParentesco = calidadParentesco;
	}
	/**
	 * @return the fechaUltimoMovAfiliacion
	 */
	public Date getFechaUltimoMovAfiliacion() {
		return fechaUltimoMovAfiliacion;
	}
	/**
	 * @param fechaUltimoMovAfiliacion the fechaUltimoMovAfiliacion to set
	 */
	public void setFechaUltimoMovAfiliacion(Date fechaUltimoMovAfiliacion) {
		this.fechaUltimoMovAfiliacion = fechaUltimoMovAfiliacion;
	}
	
	
	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}
	
	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}
	
	public Date getFechaRegistroActualizado() {
		return fechaRegistroActualizado;
	}
	public void setFechaRegistroActualizado(Date fechaRegistroActualizado) {
		this.fechaRegistroActualizado = fechaRegistroActualizado;
	}
	/**
	 * @return the patronImss
	 */
	public Integer getPatronImss() {
		return patronImss;
	}
	/**
	 * @param patronImss the patronImss to set
	 */
	public void setPatronImss(Integer patronImss) {
		this.patronImss = patronImss;
	}
	/**
	 * @return the patronSujetoObligado
	 */
	public SujetoObligado getPatronSujetoObligado() {
		return patronSujetoObligado;
	}
	/**
	 * @param patronSujetoObligado the patronSujetoObligado to set
	 */
	public void setPatronSujetoObligado(SujetoObligado patronSujetoObligado) {
		this.patronSujetoObligado = patronSujetoObligado;
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
	/**
	 * @return the fechaInicioVigencia
	 */
	public Date getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}
	/**
	 * @param fechaInicioVigencia the fechaInicioVigencia to set
	 */
	public void setFechaInicioVigencia(Date fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}
	/**
	 * @return the tipoMovtoAsegurado
	 */
	public TipoMovtoAsegurado getTipoMovtoAsegurado() {
		return tipoMovtoAsegurado;
	}
	/**
	 * @param tipoMovtoAsegurado the tipoMovtoAsegurado to set
	 */
	public void setTipoMovtoAsegurado(TipoMovtoAsegurado tipoMovtoAsegurado) {
		this.tipoMovtoAsegurado = tipoMovtoAsegurado;
	}
	public Long getIdModalidad() {
		return idModalidad;
	}
	public void setIdModalidad(Long idModalidad) {
		this.idModalidad = idModalidad;
	}
	public Date getFechaValidezConstancia() {
		return fechaValidezConstancia;
	}
	public void setFechaValidezConstancia(Date fechaValidezConstancia) {
		this.fechaValidezConstancia = fechaValidezConstancia;
	}
	
	public boolean getEsEstudiante() {
		return esEstudiante;
	}
	public void setEsEstudiante(boolean esEstudiante) {
		this.esEstudiante = esEstudiante;
	}

	public String getConDerechoSm() {
		return conDerechoSm;
	}
	public void setConDerechoSm(String conDerechoSm) {
		this.conDerechoSm = conDerechoSm;
	}
	
	
	
	
}
