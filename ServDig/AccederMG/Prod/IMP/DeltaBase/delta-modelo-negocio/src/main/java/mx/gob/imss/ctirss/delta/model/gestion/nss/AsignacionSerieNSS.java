/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.nss;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

/**
 * @author Lucio Duran Silva
 *
 */
public class AsignacionSerieNSS extends AbstractModel {
	/**
	 * Delegacion
	 */
	private Delegacion delegacion;
	
	/**
	 * Subdelegacion
	 */
	private Subdelegacion subdelegacion;
	
	/**
	 * Serie
	 */
	private Serie serie;

	/**
	 * @return the delegacion
	 */
	public Delegacion getDelegacion() {
		return delegacion;
	}

	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(Delegacion delegacion) {
		this.delegacion = delegacion;
	}

	/**
	 * @return the subdelegacion
	 */
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	/**
	 * @param subdelegacion the subdelegacion to set
	 */
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	/**
	 * @return the serie
	 */
	public Serie getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(Serie serie) {
		this.serie = serie;
	}

}
