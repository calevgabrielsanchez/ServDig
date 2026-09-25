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
public class RegistroPatronalDTO implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4164196808678040546L;
	
	private String cveRegPatronal;
	private String nomDelegacion;
	private String nomSubdelegacion;
	private Long numObrasRegistradas;
	private String estatusD32;
	
	
	public RegistroPatronalDTO(){
		
	}
	
	public RegistroPatronalDTO(String cveRegPatronal, String nomDelegacion, String nomSubdelegacion, Long numObrasRegistradas, String estatusD32){
		
		this.cveRegPatronal = cveRegPatronal;
		this.nomDelegacion = nomDelegacion;
		this.nomSubdelegacion = nomSubdelegacion;
		this.numObrasRegistradas = numObrasRegistradas;
		this.estatusD32 = estatusD32;
		
	}

	/**
	 * @return the cveRegPatronal
	 */
	public String getCveRegPatronal() {
		return cveRegPatronal;
	}

	/**
	 * @param cveRegPatronal the cveRegPatronal to set
	 */
	public void setCveRegPatronal(String cveRegPatronal) {
		this.cveRegPatronal = cveRegPatronal;
	}

	/**
	 * @return the nomDelegacion
	 */
	public String getNomDelegacion() {
		return nomDelegacion;
	}

	/**
	 * @param nomDelegacion the nomDelegacion to set
	 */
	public void setNomDelegacion(String nomDelegacion) {
		this.nomDelegacion = nomDelegacion;
	}

	/**
	 * @return the nomSubdelegacion
	 */
	public String getNomSubdelegacion() {
		return nomSubdelegacion;
	}

	/**
	 * @param nomSubdelegacion the nomSubdelegacion to set
	 */
	public void setNomSubdelegacion(String nomSubdelegacion) {
		this.nomSubdelegacion = nomSubdelegacion;
	}

	/**
	 * @return the numObrasRegistradas
	 */
	public Long getNumObrasRegistradas() {
		return numObrasRegistradas;
	}

	/**
	 * @param numObrasRegistradas the numObrasRegistradas to set
	 */
	public void setNumObrasRegistradas(Long numObrasRegistradas) {
		this.numObrasRegistradas = numObrasRegistradas;
	}

	/**
	 * @return the estatusD32
	 */
	public String getEstatusD32() {
		return estatusD32;
	}

	/**
	 * @param estatusD32 the estatusD32 to set
	 */
	public void setEstatusD32(String estatusD32) {
		this.estatusD32 = estatusD32;
	}
	
	
}
