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
 * @author jesus.garcia
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoriaLaboral extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private String nombrePatron;
	private String entidadFederativa;
	private String fechaInscripcion;
	private String fechaBaja;
	private String numeroRegistroPatronal;
	private String domicilioEmpresa;
	private String actividadEmpresa;
	
	public String getNombrePatron() {
		return nombrePatron;
	}
	public void setNombrePatron(String nombrePatron) {
		this.nombrePatron = nombrePatron;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}
	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}
	public String getDomicilioEmpresa() {
		return domicilioEmpresa;
	}
	public void setDomicilioEmpresa(String domicilioEmpresa) {
		this.domicilioEmpresa = domicilioEmpresa;
	}
	public String getActividadEmpresa() {
		return actividadEmpresa;
	}
	public void setActividadEmpresa(String actividadEmpresa) {
		this.actividadEmpresa = actividadEmpresa;
	}
}
