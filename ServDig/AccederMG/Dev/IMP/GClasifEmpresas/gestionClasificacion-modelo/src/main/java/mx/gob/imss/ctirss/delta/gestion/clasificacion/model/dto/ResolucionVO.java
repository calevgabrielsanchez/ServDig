package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import java.io.Serializable;

public class ResolucionVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private ClemVO clemVO;
	private String cadOriginal;
	private String folio;
	private String firma;
	private String cadori;
	private String acuse;
	private String rfc;
	private String idAnalisis;
	
	@Override
	public String toString() {
		return "ResolucionVO [clemVO=" + clemVO.toString() + ", rfc=" + rfc + ", folio="
				+ folio + ", acuse=" + acuse + ", firma=" + firma + ", cadori="
				+ cadori + "]";
	}

	public ClemVO getClemVO() {
		return clemVO;
	}
	public void setClemVO(ClemVO clemVO) {
		this.clemVO = clemVO;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getAcuse() {
		return acuse;
	}
	public void setAcuse(String acuse) {
		this.acuse = acuse;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getCadori() {
		return cadori;
	}
	public void setCadori(String cadori) {
		this.cadori = cadori;
	}

	public String getIdAnalisis() {
		return idAnalisis;
	}

	public void setIdAnalisis(String idAnalisis) {
		this.idAnalisis = idAnalisis;
	}

	public String getCadOriginal() {
		return cadOriginal;
	}

	public void setCadOriginal(String cadOriginal) {
		this.cadOriginal = cadOriginal;
	}
	
}
