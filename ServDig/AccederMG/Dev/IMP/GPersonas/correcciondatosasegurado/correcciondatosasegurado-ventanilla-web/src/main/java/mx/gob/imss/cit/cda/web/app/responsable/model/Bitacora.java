/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import mx.gob.imss.cit.cda.web.support.model.Page;

/**
 * @author yisus
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bitacora extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	private String idTramite;
	private String folio;
	private String origen;
	private Page<Estatus> gridEstatus;
	
	public String getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public Page<Estatus> getGridEstatus() {
		return gridEstatus;
	}
	public void setGridEstatus(Page<Estatus> gridEstatus) {
		this.gridEstatus = gridEstatus;
	}
}
