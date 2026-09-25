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
public class ClasificacionObraDTO implements Serializable{



	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1530544546613067471L;
	private Long cveClasificacionObra;
	private String desClasificacionObra;
	
	public ClasificacionObraDTO(){
		
	}
	
	public ClasificacionObraDTO(Long cveClasificacionObra,String desClasificacionObra){
		this.cveClasificacionObra = cveClasificacionObra;
		this.desClasificacionObra = desClasificacionObra;
		
	}
	
	/**
	 * @return the cveTipoObra
	 */
	public Long getCveClasificacionObra() {
		return cveClasificacionObra;
	}
	/**
	 * @param cveTipoObra the cveTipoObra to set
	 */
	public void setCveClasificacionObra(Long cveClasificacionObra) {
		this.cveClasificacionObra = cveClasificacionObra;
	}
	/**
	 * @return the desTipoObra
	 */
	public String getDesClasificacionObra() {
		return desClasificacionObra;
	}
	/**
	 * @param desTipoObra the desTipoObra to set
	 */
	public void setDesClasificacionObra(String desClasificacionObra) {
		this.desClasificacionObra = desClasificacionObra;
	}

	
}
