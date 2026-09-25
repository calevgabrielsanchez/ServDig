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
public class TipoObraDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8396915556097507464L;
	
	private Long cveTipoObra;
	private String desTipoObra;
	private ClasificacionObraDTO clasificacionObraDTO;
	
	public TipoObraDTO(){
		
	}
	
	public TipoObraDTO(Long cveTipoObra,String desTipoObra){
		this.cveTipoObra = cveTipoObra;
		this.desTipoObra = desTipoObra;
		
	}
	
	public TipoObraDTO(Long cveTipoObra,String desTipoObra,ClasificacionObraDTO clasificacionObraDTO){
		this.cveTipoObra = cveTipoObra;
		this.desTipoObra = desTipoObra;
		this.clasificacionObraDTO = clasificacionObraDTO;
	}
	
	
	
	/**
	 * @return the cveTipoObra
	 */
	public Long getCveTipoObra() {
		return cveTipoObra;
	}
	/**
	 * @param cveTipoObra the cveTipoObra to set
	 */
	public void setCveTipoObra(Long cveTipoObra) {
		this.cveTipoObra = cveTipoObra;
	}
	/**
	 * @return the desTipoObra
	 */
	public String getDesTipoObra() {
		return desTipoObra;
	}
	/**
	 * @param desTipoObra the desTipoObra to set
	 */
	public void setDesTipoObra(String desTipoObra) {
		this.desTipoObra = desTipoObra;
	}

	/**
	 * @return the clasificacionObraDTO
	 */
	public ClasificacionObraDTO getClasificacionObraDTO() {
		return clasificacionObraDTO;
	}

	/**
	 * @param clasificacionObraDTO the clasificacionObraDTO to set
	 */
	public void setClasificacionObraDTO(ClasificacionObraDTO clasificacionObraDTO) {
		this.clasificacionObraDTO = clasificacionObraDTO;
	}
	
}
