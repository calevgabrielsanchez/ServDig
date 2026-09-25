package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;




public class FileReadVB implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3063247746712592715L;
	
	private Object documentoProbatorio;
	
	private String tituloComp;
	

	public Object getDocumentoProbatorio() {
		return documentoProbatorio;
	}
	public void setDocumentoProbatorio(Object documentoProbatorio) {
		this.documentoProbatorio = documentoProbatorio;
	}
	public String getTituloComp() {
		return tituloComp;
	}
	public void setTituloComp(String tituloComp) {
		this.tituloComp = tituloComp;
	}

}
