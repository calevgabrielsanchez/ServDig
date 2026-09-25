/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class SubdelegacionDTO  implements Serializable{
	
	/** Identificador único de la versión de la clase */
	private static   long serialVersionUID = 3515802327994150187L;
	/** cve delegacion */
	private long cveSubelegacion;
	/** Nombre Delegacion */
	private String nombreSubelegacion;
	/**
	 * @return the cveSubelegacion
	 */
	public long getCveSubelegacion() {
		return cveSubelegacion;
	}
	/**
	 * @param cveSubelegacion the cveSubelegacion to set
	 */
	public void setCveSubelegacion(long cveSubelegacion) {
		this.cveSubelegacion = cveSubelegacion;
	}
	/**
	 * @return the nombreSubelegacion
	 */
	public String getNombreSubelegacion() {
		return nombreSubelegacion;
	}
	/**
	 * @param nombreSubelegacion the nombreSubelegacion to set
	 */
	public void setNombreSubelegacion(String nombreSubelegacion) {
		this.nombreSubelegacion = nombreSubelegacion;
	}
	
}