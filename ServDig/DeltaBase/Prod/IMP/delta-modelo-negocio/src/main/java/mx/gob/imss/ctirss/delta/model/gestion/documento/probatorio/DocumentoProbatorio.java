package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

public class DocumentoProbatorio extends AbstractModel implements Serializable {
 
	private static final long serialVersionUID = 1L;

    
	//base
    //private TipoDocumentoProbatorio tipoDocumentoProbatorio;
	private Integer idDocumentoProbatorio;
	private Date fechaExpedicion;
	private Persona persona;
	private DocumentoPorTipo documentoPorTipo;
	//derechohabiente 
	private byte[] digitalizacion;
	private String cifrado;
	private String nomNombreDocumento;
	private Date fechaBaja;
	private Boolean informacionAdicional;

	/* Atributos para mantener el estado dentro de 
	 * la administración de Documentos Probatorios
	 */
	private EstadoAdministracionEnum estadoAdministracionDocto;
	private EstadoAdministracionEnum estadoAdministracionAnteriorDocto;
	
	private String bovedaDocId;
	
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

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public EstadoAdministracionEnum getEstadoAdministracionDocto() {
		return estadoAdministracionDocto;
	}

	public void setEstadoAdministracionDocto(
			EstadoAdministracionEnum estadoAdministracionDocto) {
		this.estadoAdministracionDocto = estadoAdministracionDocto;
	}

	/**
	 * @return the estadoAdministracionAnteriorDocto
	 */
	public EstadoAdministracionEnum getEstadoAdministracionAnteriorDocto() {
		return estadoAdministracionAnteriorDocto;
	}

	/**
	 * @param estadoAdministracionAnteriorDocto the estadoAdministracionAnteriorDocto to set
	 */
	public void setEstadoAdministracionAnteriorDocto(
			EstadoAdministracionEnum estadoAdministracionAnteriorDocto) {
		this.estadoAdministracionAnteriorDocto = estadoAdministracionAnteriorDocto;
	}

	public String getNomNombreDocumento() {
		return nomNombreDocumento;
	}

	public void setNomNombreDocumento(String nomNombreDocumento) {
		this.nomNombreDocumento = nomNombreDocumento;
	}

	public void setBovedaDocId(String bovedaDocId) {
		this.bovedaDocId = bovedaDocId;
	}
	
	public String getBovedaDocId() {
		return bovedaDocId;
	}
	
	public Date getFechaBaja(){
		return fechaBaja;
	}
	
	public void setFechaBaja(Date fechaBaja){
		this.fechaBaja = fechaBaja;
	}
	
	public Boolean getInformacionAdicional(){
        return informacionAdicional;
    }
    
    public void setInformacionAdicional(Boolean informacionAdicional){
        this.informacionAdicional = informacionAdicional;
    }
}
