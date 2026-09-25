package mx.imss.ctirss.web.controller.promocion;

import mx.imss.ctirss.framework.base.model.AbstractModel;

public class PromocionExhortoOrdinarioModel extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private Long cveSelector;
	private String fechaNotificacion;
	private String numeroOficio;
	private String fechaPromocion;
	private String observaciones;
	private String regPatronal;
	private String idCriterioSeleccion;
	
	public Long getCveSelector() {
		return cveSelector;
	}
	
	public String getRegPatronal() {
		return regPatronal;
	}
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}
	public void setCveSelector(Long cveSelector) {
		this.cveSelector = cveSelector;
	}
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getNumeroOficio() {
		return numeroOficio;
	}
	public void setNumeroOficio(String numeroOficio) {
		this.numeroOficio = numeroOficio;
	}
	public String getFechaPromocion() {
		return fechaPromocion;
	}
	public void setFechaPromocion(String fechaPromocion) {
		this.fechaPromocion = fechaPromocion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdCriterioSeleccion() {
		return idCriterioSeleccion;
	}

	public void setIdCriterioSeleccion(String idCriterioSeleccion) {
		this.idCriterioSeleccion = idCriterioSeleccion;
	}

	
	
	
}
