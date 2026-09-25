package mx.gob.imss.ctirss.delta.model.escritoDesacuerdo;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

public class RespuestaEscrito implements Serializable {

	private String motivoDesacuerdo;
	private List<DocumentoProbatorio> documentos;
	
	public RespuestaEscrito() {
		super();
		this.motivoDesacuerdo = null;
		this.documentos=null;
	}
	
	public RespuestaEscrito(String motivoDesacuerdo, List<DocumentoProbatorio> documentos) {
		this();
		this.motivoDesacuerdo = motivoDesacuerdo;
		this.documentos = documentos;
	}

	public String getMotivoDesacuerdo() {
		return motivoDesacuerdo;
	}
	
	public void setMotivoDesacuerdo(String motivoDesacuerdo) {
		this.motivoDesacuerdo = motivoDesacuerdo;
	}
	
	public List<DocumentoProbatorio> getDocumentos() {
		return documentos;
	}
	
	public void setDocumentos(List<DocumentoProbatorio> documentos) {
		this.documentos = documentos;
	}
	
}