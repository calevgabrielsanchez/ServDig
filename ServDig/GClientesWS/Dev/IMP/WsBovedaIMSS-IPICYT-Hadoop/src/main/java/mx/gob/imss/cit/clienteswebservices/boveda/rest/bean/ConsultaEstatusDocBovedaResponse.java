package mx.gob.imss.cit.clienteswebservices.boveda.rest.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsultaEstatusDocBovedaResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6193334012333384875L;
	
	private String codigo;
	private String descripcion;
	private List<DocumentoBovedaResponse> documentos;
	
	
	public ConsultaEstatusDocBovedaResponse() {
		
	}
	
	public ConsultaEstatusDocBovedaResponse(String codigo, String descripcion,
			List<DocumentoBovedaResponse> documentos) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.documentos = documentos;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<DocumentoBovedaResponse> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<DocumentoBovedaResponse> documentos) {
		this.documentos = documentos;
	}
	
	
}
