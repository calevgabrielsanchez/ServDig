/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author antonio
 */
public class HistoricoSolicitudes extends BaseModel {
  /**
	 * 
	 */
  private static final long serialVersionUID = 6228710026290693602L;
  
  private String folio;
  private String fechaSolicitud;
  private String curp;
  private String nssInvolucrados;
  private String origen;
  private String responsable;
  private String autorizo;
  private String estatus;
  private String ultimaActualizacion;
  private String nombreCompleto;
  private String idTramite;
  private String idTarea;
  private String nombreCompletoResponsable;
  private String nombreCompletoAutorizo;
  private String tipo;
  private Boolean esPropietario;

  /**
   * @return the folio
   */
  public String getFolio() {
    return folio;
  }

  /**
   * @param folio the folio to set
   */
  public void setFolio(String folio) {
    this.folio = folio;
  }

  /**
   * @return the fechaSolicitud
   */
  public String getFechaSolicitud() {
    return fechaSolicitud;
  }

  /**
   * @param fechaSolicitud the fechaSolicitud to set
   */
  public void setFechaSolicitud(String fechaSolicitud) {
    this.fechaSolicitud = fechaSolicitud;
  }

  /**
   * @return the nssInvolucrados
   */
  public String getNssInvolucrados() {
    return nssInvolucrados;
  }

  /**
   * @param nssInvolucrados the nssInvolucrados to set
   */
  public void setNssInvolucrados(String nssInvolucrados) {
    this.nssInvolucrados = nssInvolucrados;
  }

  /**
   * @return the origen
   */
  public String getOrigen() {
    return origen;
  }

  /**
   * @param origen the origen to set
   */
  public void setOrigen(String origen) {
    this.origen = origen;
  }

  /**
   * @return the responsable
   */
  public String getResponsable() {
    return responsable;
  }

  /**
   * @param responsable the responsable to set
   */
  public void setResponsable(String responsable) {
    this.responsable = responsable;
  }

  /**
   * @return the autorizo
   */
  public String getAutorizo() {
    return autorizo;
  }

  /**
   * @param autorizo the autorizo to set
   */
  public void setAutorizo(String autorizo) {
    this.autorizo = autorizo;
  }

  /**
   * @return the estatus
   */
  public String getEstatus() {
    return estatus;
  }

  /**
   * @param estatus the estatus to set
   */
  public void setEstatus(String estatus) {
    this.estatus = estatus;
  }

  /**
   * @return the ultimaActualizacion
   */
  public String getUltimaActualizacion() {
    return ultimaActualizacion;
  }

  /**
   * @param ultimaActualizacion the ultimaActualizacion to set
   */
  public void setUltimaActualizacion(String ultimaActualizacion) {
    this.ultimaActualizacion = ultimaActualizacion;
  }
  	/**
   * @return nombreCompleto
   */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	* @param nombreCompleto the nombreCompleto to set
	*/
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public String getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	public String getNombreCompletoResponsable() {
		return nombreCompletoResponsable;
	}

	public void setNombreCompletoResponsable(String nombreCompletoResponsable) {
		this.nombreCompletoResponsable = nombreCompletoResponsable;
	}

	public String getNombreCompletoAutorizo() {
		return nombreCompletoAutorizo;
	}

	public void setNombreCompletoAutorizo(String nombreCompletoAutorizo) {
		this.nombreCompletoAutorizo = nombreCompletoAutorizo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getEsPropietario() {
		return esPropietario;
	}

	public void setEsPropietario(Boolean esPropietario) {
		this.esPropietario = esPropietario;
	}
	
	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp
	 *            the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the TramitesAsignados
	 */
	@Override
	public String toString() {
		return "HistoricoSolicitudes [folio=" + folio + ", fechaSolicitud="
				+ fechaSolicitud + ", curp=" + curp + ", nssInvolucrados="
				+ nssInvolucrados + ", origen=" + origen + ", responsable="
				+ responsable + ", autorizo=" + autorizo + ", estatus="
				+ estatus + ", ultimaActualizacion=" + ultimaActualizacion
				+ ", nombreCompleto=" + nombreCompleto + ", idTramite="
				+ idTramite + ", idTarea=" + idTarea
				+ ", nombreCompletoResponsable=" + nombreCompletoResponsable
				+ ", nombreCompletoAutorizo=" + nombreCompletoAutorizo
				+ ", tipo=" + tipo + ", esPropietario=" + esPropietario + "]";
	}

}
