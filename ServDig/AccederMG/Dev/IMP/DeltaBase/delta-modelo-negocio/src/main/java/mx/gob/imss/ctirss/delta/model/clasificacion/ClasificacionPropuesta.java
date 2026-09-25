/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;

/**
 * 
 * @author Jorge A. García
 */
public class ClasificacionPropuesta extends AbstractModel {

	/** Serial version */
	private static final long serialVersionUID = 239166984506696099L;
	private Long id;
	private Long cveIdAnalisis;
	private Fraccion fraccion;
	private String desActividadDetectada;

	/**
	 * Constructor
	 */
	public ClasificacionPropuesta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con campos
	 * 
	 * @param id
	 * @param cveIdAnalisis
	 * @param desActividadDetectada
	 * @param cveIdFraccion
	 * @param numFraccion
	 * @param desFraccion
	 * @param cveIdGrupo
	 * @param numGrupo
	 * @param desGrupo
	 * @param cveIdDivision
	 * @param numDivision
	 * @param desDivision
	 * @param cveIdClase
	 * @param numPrimaMedia
	 * @param desClase
	 */
	public ClasificacionPropuesta(final Long id, final Long cveIdAnalisis,
			final String desActividadDetectada, final Long cveIdFraccion,
			final String numFraccion, final String desFraccion,
			final Long cveIdGrupo, final String numGrupo,
			final String desGrupo, final Long cveIdDivision,
			final String numDivision, final String desDivision,
			final Long cveIdClase, final BigDecimal numPrimaMedia,
			final String desClase) {
		super();
		final Division division = new Division();
		division.setId(cveIdDivision);
		division.setNumDivision(numDivision);
		division.setDescripcion(desDivision);

		final Grupo grupo = new Grupo();
		grupo.setId(cveIdGrupo);
		grupo.setNumGrupo(numGrupo);
		grupo.setDescripcion(desGrupo);
		grupo.setDivision(division);

		final Clase clase = new Clase();
		clase.setClave(cveIdClase);
		clase.setDescripcion(desClase);

		this.id = id;
		this.cveIdAnalisis = cveIdAnalisis;
		this.desActividadDetectada = desActividadDetectada;
		this.fraccion = new Fraccion();
		fraccion.setId(cveIdFraccion);
		fraccion.setNumFraccion(numFraccion);
		fraccion.setDescripcion(desFraccion);
		fraccion.setGrupo(grupo);
		fraccion.setClase(clase);
		fraccion.setPrimaSRT(numPrimaMedia);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
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
	 * @return the fraccion
	 */
	public Fraccion getFraccion() {
		return fraccion;
	}

	/**
	 * @param fraccion
	 *            the fraccion to set
	 */
	public void setFraccion(final Fraccion fraccion) {
		this.fraccion = fraccion;
	}

	/**
	 * @return the desActividadDetectada
	 */
	public String getDesActividadDetectada() {
		return desActividadDetectada;
	}

	/**
	 * @param desActividadDetectada
	 *            the desActividadDetectada to set
	 */
	public void setDesActividadDetectada(final String desActividadDetectada) {
		this.desActividadDetectada = desActividadDetectada;
	}

}
