/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:TitularSuplente.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.model.persona
 *  @Fecha:18/06/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class TitularSuplente extends AbstractModel implements Serializable{
	private static final long serialVersionUID = -172501883897538177L;
	
	private Long cveIdPersona;
	
	private String desCargo;
	private Long cveIdDelegacion;
	private Long cveIdSubdelegacion;
	private String nomNombre;
	private String nomPrimerApellido;
	private String nomSegundoApellido;
	/**
	 * @return the cveIdPersona
	 */
	public Long getCveIdPersona() {
		return cveIdPersona;
	}
	/**
	 * @param cveIdPersona the cveIdPersona to set
	 */
	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}
	/**
	 * @return the desCargo
	 */
	public String getDesCargo() {
		return desCargo;
	}
	/**
	 * @param desCargo the desCargo to set
	 */
	public void setDesCargo(String desCargo) {
		this.desCargo = desCargo;
	}
	/**
	 * @return the cveIdDelegacion
	 */
	public Long getCveIdDelegacion() {
		return cveIdDelegacion;
	}
	/**
	 * @param cveIdDelegacion the cveIdDelegacion to set
	 */
	public void setCveIdDelegacion(Long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	/**
	 * @return the cveIdSubdelegacion
	 */
	public Long getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	/**
	 * @param cveIdSubdelegacion the cveIdSubdelegacion to set
	 */
	public void setCveIdSubdelegacion(Long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	/**
	 * @return the nomNombre
	 */
	public String getNomNombre() {
		return nomNombre;
	}
	/**
	 * @param nomNombre the nomNombre to set
	 */
	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}
	/**
	 * @return the nomPrimerApellido
	 */
	public String getNomPrimerApellido() {
		return nomPrimerApellido;
	}
	/**
	 * @param nomPrimerApellido the nomPrimerApellido to set
	 */
	public void setNomPrimerApellido(String nomPrimerApellido) {
		this.nomPrimerApellido = nomPrimerApellido;
	}
	/**
	 * @return the nomSegundoApellido
	 */
	public String getNomSegundoApellido() {
		return nomSegundoApellido;
	}
	/**
	 * @param nomSegundoApellido the nomSegundoApellido to set
	 */
	public void setNomSegundoApellido(String nomSegundoApellido) {
		this.nomSegundoApellido = nomSegundoApellido;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TitularSuplente [cveIdPersona=" + cveIdPersona + ", desCargo="
				+ desCargo + ", cveIdDelegacion=" + cveIdDelegacion
				+ ", cveIdSubdelegacion=" + cveIdSubdelegacion + ", nomNombre="
				+ nomNombre + ", nomPrimerApellido=" + nomPrimerApellido
				+ ", nomSegundoApellido=" + nomSegundoApellido + "]";
	}
}
