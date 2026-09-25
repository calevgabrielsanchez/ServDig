package mx.gob.imss.ctirss.delta.model.dto;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CartaTerminosDto extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1444145088753380808L;
	private Date fecha;
	private String firmaDocto;
	private String selloDigital;
	private String cadenaOriginal;
	private String secuenciaNotaria;
	private String numeroSerie;
	private String secuenciaNotariaRepresentado;
	private String numeroSerieRepresentado;
	private String selloDigitalRepresentado;
	private String cadenaOriginalRepresentado;
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getFirmaDocto() {
		return firmaDocto;
	}
	
	public void setFirmaDocto(String firmaDocto) {
		this.firmaDocto = firmaDocto;
	}
	
	public String getSelloDigital() {
		return selloDigital;
	}
	
	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}
	
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getSelloDigitalRepresentado() {
		return selloDigitalRepresentado;
	}

	public void setSelloDigitalRepresentado(String selloDigitalRepresentado) {
		this.selloDigitalRepresentado = selloDigitalRepresentado;
	}

	public String getCadenaOriginalRepresentado() {
		return cadenaOriginalRepresentado;
	}

	public void setCadenaOriginalRepresentado(String cadenaOriginalRepresentado) {
		this.cadenaOriginalRepresentado = cadenaOriginalRepresentado;
	}

	public String getSecuenciaNotaria() {
		return secuenciaNotaria;
	}

	public void setSecuenciaNotaria(String secuenciaNotaria) {
		this.secuenciaNotaria = secuenciaNotaria;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getSecuenciaNotariaRepresentado() {
		return secuenciaNotariaRepresentado;
	}

	public void setSecuenciaNotariaRepresentado(String secuenciaNotariaRepresentado) {
		this.secuenciaNotariaRepresentado = secuenciaNotariaRepresentado;
	}

	public String getNumeroSerieRepresentado() {
		return numeroSerieRepresentado;
	}

	public void setNumeroSerieRepresentado(String numeroSerieRepresentado) {
		this.numeroSerieRepresentado = numeroSerieRepresentado;
	}

}