/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Documento extends BaseModel {

	private static final long serialVersionUID = 6412672824400316568L;
	String documento;
	String idTramite;
	String idPersona;
	String extension;
	String nombreArchivo;
	String folio;
	String tipoDocumento;
	String idDocBoveda;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setIdDocBoveda(String idDocBoveda) {
		this.idDocBoveda = idDocBoveda;
	}
	
	public String getIdDocBoveda() {
		return idDocBoveda;
	}
}
