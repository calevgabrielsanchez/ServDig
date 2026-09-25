package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;




public class DocumentoProbatorioCaptura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4879518331659856100L;
	//objeto de documento curp etc
	private Object captura;
	
	private Long idDocumentoCaptura;
	//Documento digitalizado
	private DocumentoProbatorio documentoProbatorio;

	
	
	
	
	public Long getIdDocumentoCaptura() {
		return idDocumentoCaptura;
	}
	public void setIdDocumentoCaptura(Long idDocumentoCaptura) {
		this.idDocumentoCaptura = idDocumentoCaptura;
	}

	public Object getCaptura() {
		return captura;
	}
	public void setCaptura(Object captura) {
		this.captura = captura;
	}
	public DocumentoProbatorio getDocumentoProbatorio() {
		return documentoProbatorio;
	}
	public void setDocumentoProbatorio(DocumentoProbatorio documentoProbatorio) {
		this.documentoProbatorio = documentoProbatorio;
	}
	
}
