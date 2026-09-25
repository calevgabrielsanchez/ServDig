package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;


public class SolicitudesAtendidasDto extends AbstractModel implements Serializable{
	/**
	 * 
	 */
	public  static final String SES_NAME="solicitudesAtendidasDto";
	private static final long serialVersionUID = 428466026091851881L;
	
	private Long idPersona;
	private String nss;
	private String folioSol;
	private Long pagStar;
	private Long pagEnd;

	private Long datosTotales;
	private Long datosMostrados;
	

	public Long getDatosTotales() {
		return datosTotales;
	}
	public void setDatosTotales(Long datosTotales) {
		this.datosTotales = datosTotales;
	}
	public Long getDatosMostrados() {
		return datosMostrados;
	}
	public void setDatosMostrados(Long datosMostrados) {
		this.datosMostrados = datosMostrados;
	}
	
	public Long getPagStar() {
		return pagStar;
	}
	public void setPagStar(Long pagStar) {
		this.pagStar = pagStar;
	}
	public Long getPagEnd() {
		return pagEnd;
	}
	public void setPagEnd(Long pagEnd) {
		this.pagEnd = pagEnd;
	}
	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}
	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	/**
	 * @return the nss
	 */
	public String getNss() {
		return nss;
	}
	/**
	 * @param nss the nss to set
	 */
	public void setNss(String nss) {
		this.nss = nss;
	}
	/**
	 * @return the folioSol
	 */
	public String getFolioSol() {
		return folioSol;
	}
	/**
	 * @param folioSol the folioSol to set
	 */
	public void setFolioSol(String folioSol) {
		this.folioSol = folioSol;
	}
}
