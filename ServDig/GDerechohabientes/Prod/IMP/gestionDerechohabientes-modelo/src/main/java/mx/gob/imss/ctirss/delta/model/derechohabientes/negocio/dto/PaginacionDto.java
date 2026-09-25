package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

public class PaginacionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1577332107545153513L;
	private Long pagStar;
	private Long pagEnd;

	private Long datosTotales;
	private Long datosMostrados;
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
	
	
}
