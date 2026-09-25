package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;


public class SolicitudesPendientesAutorizacionDto extends AbstractModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 428466026091851881L;
	private String numNss;
	private Long idSubDelegacion;
	private String folio;
	private Long idUmf;
	
	private Long pagStar;
	private Long pagEnd;

	private Long datosTotales;
	private Long datosMostrados;
	AsignacionNSS asignacionNSS;

	
	
	public AsignacionNSS getAsignacionNSS() {
		return asignacionNSS;
	}

	public void setAsignacionNSS(AsignacionNSS asignacionNSS) {
		this.asignacionNSS = asignacionNSS;
	}
	

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
	public Long getIdSubDelegacion() {
		return idSubDelegacion;
	}
	public void setIdSubDelegacion(Long idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
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
	public String getNumNss() {
		return numNss;
	}
	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public Long getIdUmf() {
		return idUmf;
	}
	public void setIdUmf(Long idUmf) {
		this.idUmf = idUmf;
	}
	
	
}
