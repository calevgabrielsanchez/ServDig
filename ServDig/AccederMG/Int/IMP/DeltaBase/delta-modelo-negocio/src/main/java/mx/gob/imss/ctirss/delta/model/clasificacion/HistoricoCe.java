/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: HistoricoCe.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.clasificacion.anexov
 *  @Fecha: 22/10/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.sql.Timestamp;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;

public class HistoricoCe extends AbstractModel {

	/** Serial version */
	private static final long serialVersionUID = -2528859776444503986L;
	private Long cveIdEstatusAnalisis;
	private Long cveIdAnalisis;
	private String cveIdUsuario;
	private Long cveIdSubdelegacion;
	private Timestamp fechaHistorico;
	private String desComentario;
	private Fraccion fraccionActual;
	private Fraccion fraccionAnterior;

	/**
	 * @return the cveIdEstatusAnalisis
	 */
	public Long getCveIdEstatusAnalisis() {
		return cveIdEstatusAnalisis;
	}

	/**
	 * @param cveIdEstatusAnalisis
	 *            the cveIdEstatusAnalisis to set
	 */
	public void setCveIdEstatusAnalisis(final Long cveIdEstatusAnalisis) {
		this.cveIdEstatusAnalisis = cveIdEstatusAnalisis;
	}

	/**
	 * @return the cveIdAnalisis
	 */
	public Long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	/**
	 * @param cveIdAnalisis
	 *            the cveIdAnalisis to set
	 */
	public void setCveIdAnalisis(final Long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	/**
	 * @return the cveIdUsuario
	 */
	public String getCveIdUsuario() {
		return cveIdUsuario;
	}

	/**
	 * @param cveIdUsuario
	 *            the cveIdUsuario to set
	 */
	public void setCveIdUsuario(final String cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	/**
	 * @return the cveIdSubdelegacion
	 */
	public Long getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	/**
	 * @param cveIdSubdelegacion
	 *            the cveIdSubdelegacion to set
	 */
	public void setCveIdSubdelegacion(final Long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	/**
	 * @return the fechaHistorico
	 */
	public Timestamp getFechaHistorico() {
		return fechaHistorico;
	}

	/**
	 * @param fechaHistorico
	 *            the fechaHistorico to set
	 */
	public void setFechaHistorico(final Timestamp fechaHistorico) {
		this.fechaHistorico = fechaHistorico;
	}

	/**
	 * @return the desComentario
	 */
	public String getDesComentario() {
		return desComentario;
	}

	/**
	 * @param desComentario
	 *            the desComentario to set
	 */
	public void setDesComentario(final String desComentario) {
		this.desComentario = desComentario;
	}

	/**
	 * @return the fraccionActual
	 */
	public Fraccion getFraccionActual() {
		return fraccionActual;
	}

	/**
	 * @param fraccionActual
	 *            the fraccionActual to set
	 */
	public void setFraccionActual(final Fraccion fraccionActual) {
		this.fraccionActual = fraccionActual;
	}

	/**
	 * @return the fraccionAnterior
	 */
	public Fraccion getFraccionAnterior() {
		return fraccionAnterior;
	}

	/**
	 * @param fraccionAnterior
	 *            the fraccionAnterior to set
	 */
	public void setFraccionAnterior(final Fraccion fraccionAnterior) {
		this.fraccionAnterior = fraccionAnterior;
	}

}
