package mx.gob.imss.cit.clienteswebservices.boveda.rest.bean;

import java.io.Serializable;

public class ConsultaEstatusDocBovedaRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2002899341151097371L;
	
	private String docId;
	private String folio;
	private String tipoDocumental;
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getTipoDocumental() {
		return tipoDocumental;
	}
	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}
	

}
