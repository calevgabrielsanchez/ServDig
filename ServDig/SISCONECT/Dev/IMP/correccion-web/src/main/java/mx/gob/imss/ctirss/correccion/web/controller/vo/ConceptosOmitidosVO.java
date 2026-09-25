package mx.gob.imss.ctirss.correccion.web.controller.vo;


import mx.gob.imss.ctirss.correccion.model.CgcCatConceptoOmitido;

import mx.gob.imss.ctirss.correccion.model.CgcCatSituacionCO;

public class ConceptosOmitidosVO {
	
	private long idProceso;
	private CgcCatSituacionCO pagoRecibido;
	private CgcCatSituacionCO Aclarado;
	private CgcCatConceptoOmitido cgcCatConceptoOmitido;
	private String folio;
	public long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}
	public CgcCatSituacionCO getPagoRecibido() {
		return pagoRecibido;
	}
	public void setPagoRecibido(CgcCatSituacionCO pagoRecibido) {
		this.pagoRecibido = pagoRecibido;
	}
	public CgcCatSituacionCO getAclarado() {
		return Aclarado;
	}
	public void setAclarado(CgcCatSituacionCO aclarado) {
		Aclarado = aclarado;
	}
	public CgcCatConceptoOmitido getCgcCatConceptoOmitido() {
		return cgcCatConceptoOmitido;
	}
	public void setCgcCatConceptoOmitido(CgcCatConceptoOmitido cgcCatConceptoOmitido) {
		this.cgcCatConceptoOmitido = cgcCatConceptoOmitido;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

}
