/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Jonathan Sanchez Montiel
 *
 */
public class RechazarAnalisis extends AbstractModel {

	private static final long serialVersionUID = 1L;

	/*Clave del Analisis*/
	private Integer cveIdAnalisis;
	/*Comentario*/
	private Comentario comentario;
	
	/**
	 * Minimal constructor
	 */
	public RechazarAnalisis(){
	}
	
	/**
	 * Constructor con clave de analisis.
	 * @param cveIdAnalisis
	 */
	public RechazarAnalisis(Integer cveIdAnalisis){
		this.cveIdAnalisis = cveIdAnalisis;
	}
	
	/**
	 * @return the cveIdAnalisis
	 */
	public Integer getCveIdAnalisis() {
		return cveIdAnalisis;
	}
	/**
	 * @param cveIdAnalisis the cveIdAnalisis to set
	 */
	public void setCveIdAnalisis(Integer cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}
	/**
	 * @return the comentario
	 */
	public Comentario getComentario() {
		return comentario;
	}
	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}
	
	
	
	
	
	
	
}
