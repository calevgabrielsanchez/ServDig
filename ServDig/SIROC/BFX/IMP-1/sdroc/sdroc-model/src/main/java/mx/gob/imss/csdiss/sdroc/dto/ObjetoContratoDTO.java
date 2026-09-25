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
public class ObjetoContratoDTO implements Serializable,Comparable<ObjetoContratoDTO>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3819024874766501491L;
	private Long cveObjetoContrato;
	private String desObjetoContrato;
	
	public ObjetoContratoDTO(){
		
	}
	
	public ObjetoContratoDTO(Long cveObjetoContrato,String desObjetoContrato){
		this.cveObjetoContrato = cveObjetoContrato;
		this.desObjetoContrato = desObjetoContrato;
		
	}

	/**
	 * @return the cveObjetoContrato
	 */
	public Long getCveObjetoContrato() {
		return cveObjetoContrato;
	}

	/**
	 * @param cveObjetoContrato the cveObjetoContrato to set
	 */
	public void setCveObjetoContrato(Long cveObjetoContrato) {
		this.cveObjetoContrato = cveObjetoContrato;
	}

	/**
	 * @return the desObjetoContrato
	 */
	public String getDesObjetoContrato() {
		return desObjetoContrato;
	}

	/**
	 * @param desObjetoContrato the desObjetoContrato to set
	 */
	public void setDesObjetoContrato(String desObjetoContrato) {
		this.desObjetoContrato = desObjetoContrato;
	}


	public int compareTo(ObjetoContratoDTO o) {
		return this.getCveObjetoContrato().compareTo(o.getCveObjetoContrato());
	}
}
