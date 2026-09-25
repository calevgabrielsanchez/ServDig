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
public class EstatusObraDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8396915556097507464L;
	
	private Long cveEstatusObra;
	private String desEstatusObra;

	public EstatusObraDTO(){
		
	}
	
	public EstatusObraDTO(Long cveEstatusObra, String desEstatusObra){
		this.cveEstatusObra = cveEstatusObra;
		this.desEstatusObra = desEstatusObra;		
	}
	
	/**
	 * @return the cveEstatusObra
	 */
	public Long getCveEstatusObra() {
		return cveEstatusObra;
	}
	/**
	 * @param cveEstatusObra the cveEstatusObra to set
	 */
	public void setCveEstatusObra(Long cveEstatusObra) {
		this.cveEstatusObra = cveEstatusObra;
	}
	/**
	 * @return the desEstatusObra
	 */
	public String getDesEstatusObra() {
		return desEstatusObra;
	}
	/**
	 * @param desEstatusObra the desEstatusObra to set
	 */
	public void setDesEstatusObra(String desEstatusObra) {
		this.desEstatusObra = desEstatusObra;
	}

	
}
