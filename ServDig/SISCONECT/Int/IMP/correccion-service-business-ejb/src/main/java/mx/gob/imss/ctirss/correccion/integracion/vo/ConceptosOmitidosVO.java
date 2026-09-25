package mx.gob.imss.ctirss.correccion.integracion.vo;


import java.io.Serializable;

import mx.gob.imss.ctirss.correccion.model.CgcCatConceptoOmitido;

import mx.gob.imss.ctirss.correccion.model.CgcCatSituacionCO;

public class ConceptosOmitidosVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long idProceso;
	private CgcCatSituacionCO pagoRecibido;
	private CgcCatSituacionCO aclarado;
	private CgcCatSituacionCO noAclarado;
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
		return aclarado;
	}
	public void setAclarado(CgcCatSituacionCO aclarado) {
		this.aclarado = aclarado;
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
	public CgcCatSituacionCO getNoAclarado() {
		return noAclarado;
	}
	public void setNoAclarado(CgcCatSituacionCO noAclarado) {
		this.noAclarado = noAclarado;
	}

}
