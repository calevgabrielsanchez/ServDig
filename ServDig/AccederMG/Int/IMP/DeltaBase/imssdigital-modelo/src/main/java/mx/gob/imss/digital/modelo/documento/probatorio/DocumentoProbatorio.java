package mx.gob.imss.digital.modelo.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoProbatorio", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
@XmlRootElement(name = "documentoProbatorio", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
public class DocumentoProbatorio implements Serializable {
 
    /**
     * Serial version UID
     */
	private static final long serialVersionUID = 1L;

    
	//base
    //private TipoDocumentoProbatorio tipoDocumentoProbatorio;
	private Integer idDocumentoProbatorio;
	private Date fechaExpedicion;
	private DocumentoPorTipo documentoPorTipo;
	//derechohabiente 
	private byte[] digitalizacion;
	private String cifrado;

	
	public DocumentoPorTipo getDocumentoPorTipo() {
		return documentoPorTipo;
	}

	public void setDocumentoPorTipo(DocumentoPorTipo documentoPorTipo) {
		this.documentoPorTipo = documentoPorTipo;
	}

	public byte[] getDigitalizacion() {
		return digitalizacion;
	}

	public void setDigitalizacion(byte[] digitalizacion) {
		this.digitalizacion = digitalizacion != null ? digitalizacion.clone() : null;
	}

	public String getCifrado() {
		return cifrado;
	}

	public void setCifrado(String cifrado) {
		this.cifrado = cifrado;
	}

	


	public Integer getIdDocumentoProbatorio() {
		return idDocumentoProbatorio;
	}

	public void setIdDocumentoProbatorio(Integer idDocumentoProbatorio) {
		this.idDocumentoProbatorio = idDocumentoProbatorio;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

		
}
