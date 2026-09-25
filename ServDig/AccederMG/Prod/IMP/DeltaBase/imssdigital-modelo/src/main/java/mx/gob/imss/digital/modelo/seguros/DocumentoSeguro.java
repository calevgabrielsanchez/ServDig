/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Clase para representar los documentos que genera los seguros ivro
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoSeguro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "documentoSeguro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class DocumentoSeguro implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del documento si fue persistido
     */
    private Long idDocumento;
    /**
     * Nombre del archivo
     */
    private String nombreArchivo;
    
    /**
     * Archivo 
     */
    private byte[] archivo;
    
    private ComprobanteSeguroReporte[] listComprobanteSeguro;

    /**
     * @return the idDocumento
     */
    public Long getIdDocumento() {
        return idDocumento;
    }
    /**
     * @param idDocumento the idDocumento to set
     */
    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }
    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    /**
     * @return the archivo
     */
    public byte[] getArchivo() {
        return archivo;
    }
    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo != null ? archivo.clone() : null;
    }

	public ComprobanteSeguroReporte[] getListComprobanteSeguro() {
		return listComprobanteSeguro;
	}

	public void setListComprobanteSeguro(ComprobanteSeguroReporte[] listComprobanteSeguro) {
		this.listComprobanteSeguro = listComprobanteSeguro != null ? listComprobanteSeguro.clone() : null;
	}
}
