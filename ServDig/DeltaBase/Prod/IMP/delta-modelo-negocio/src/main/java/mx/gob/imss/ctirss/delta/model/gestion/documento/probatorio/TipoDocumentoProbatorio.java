package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;


public class TipoDocumentoProbatorio implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer idTipoDocumentoProbatorio;
	private String descripcion;
	

	public Integer getIdTipoDocumentoProbatorio() {
		return idTipoDocumentoProbatorio;
	}

	public void setIdTipoDocumentoProbatorio(Integer idTipoDocumentoProbatorio) {
		this.idTipoDocumentoProbatorio = idTipoDocumentoProbatorio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoDocumentoProbatorio [idTipoDocumentoProbatorio="
				+ idTipoDocumentoProbatorio + ", descripcion=" + descripcion
				+ "]";
	}

}
