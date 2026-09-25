package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;

public class DocumentoProbatorio extends Documento implements Serializable{

	private static final long serialVersionUID = -1753374178261138473L;
	
	private String nombre;
	private Integer tipoDocumento;
	private long idDocumentoPorTipo;
	private String idDocBoveda;
	
	public long getIdDocumentoPorTipo() {
		return idDocumentoPorTipo;
	}
	public void setIdDocumentoPorTipo(long idDocumentoPorTipo) {
		this.idDocumentoPorTipo = idDocumentoPorTipo;
	}
	public Integer getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setIdDocBoveda(String idDocBoveda) {
		this.idDocBoveda = idDocBoveda;
	}
	
	public String getIdDocBoveda() {
		return idDocBoveda;
	}
	
	@Override
	public String toString() {
		return "DocumentoProbatorio [claveTipoDocumento=" + cveIdDocumento
				+ ", desDocumento=" + desDocumento + ", nombre=" + nombre
				+ ", idDocumentoPorTipo=" + idDocumentoPorTipo 
                                + ", idDocBoveda=" + idDocBoveda 
				+ "]";
	}	
	
	

}
