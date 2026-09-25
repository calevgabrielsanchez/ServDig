package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class DocumentosNss extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4426472817402420551L;
	
	private String nss;
	private List<Documento> documentosNss;
	private String origen;
	
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public List<Documento> getDocumentosNss() {
		return documentosNss;
	}
	public void setDocumentosNss(List<Documento> documentosNss) {
		this.documentosNss = documentosNss;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	@Override
	public String toString() {
		return "DocumentosNss [nss=" + nss + ", documentosNss=" + documentosNss
				+ ", origen=" + origen + "]";
	}
	
}
