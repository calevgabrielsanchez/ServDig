/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class DelegacionDTO  implements Serializable{
	
	/** Identificador único de la versión de la clase */
	private static   long serialVersionUID = 3515802327994150187L;
	
	/** cve delegacion */
	private long cveDelegacion;
	
	/** Nombre Delegacion */
	private String nombreDelegacion;
	/**
	 * @return the cveDelegacion
	 */
	public long getCveDelegacion() {
		return cveDelegacion;
	}
	/**
	 * @param cveDelegacion the cveDelegacion to set
	 */
	public void setCveDelegacion(long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	/**
	 * @return the nombreDelegacion
	 */
	public String getNombreDelegacion() {
		return nombreDelegacion;
	}
	/**
	 * @param nombreDelegacion the nombreDelegacion to set
	 */
	public void setNombreDelegacion(String nombreDelegacion) {
		this.nombreDelegacion = nombreDelegacion;
	}
	
	

}
