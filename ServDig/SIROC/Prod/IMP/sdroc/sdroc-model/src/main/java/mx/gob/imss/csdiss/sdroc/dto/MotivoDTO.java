/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.dto;

import java.io.Serializable;


/**
 * 
 * Clase que implementa el patron value object, el cual nos permite abtraer el
 * contenido de los valores del objeto parametro para obtener los parametros del
 * sistema, para transportarlo entre las diferentes capas de la apliacion
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public class MotivoDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6261200646316851082L;

	
	private Long cveMotivo;
	private String desMotivo;
	
	public MotivoDTO(){
		
	}
	
	public MotivoDTO(Long cveMotivo,String desMotivo){
		this.cveMotivo = cveMotivo;
		this.desMotivo = desMotivo;
	}
	

	/**
	 * @return the cveMotivo
	 */
	public Long getCveMotivo() {
		return cveMotivo;
	}
	/**
	 * @param cveMotivo the cveMotivo to set
	 */
	public void setCveMotivo(Long cveMotivo) {
		this.cveMotivo = cveMotivo;
	}
	/**
	 * @return the desMotivo
	 */
	public String getDesMotivo() {
		return desMotivo;
	}
	/**
	 * @param desMotivo the desMotivo to set
	 */
	public void setDesMotivo(String desMotivo) {
		this.desMotivo = desMotivo;
	}
	
}
