package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

import java.io.Serializable;

public class MatriculaConsularDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String numeroDoc;
	private String autoridadEmiteMat;
	private String fechaExpedicion;
	private String fechaVencimiento;
	private String calidadMigratoria;

	public String getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}

	public String getAutoridadEmiteMat() {
		return autoridadEmiteMat;
	}

	public void setAutoridadEmiteMat(String autoridadEmiteMat) {
		this.autoridadEmiteMat = autoridadEmiteMat;
	}
	
	public String getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
	
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String getCalidadMigratoria() {
		return calidadMigratoria;
	}
	
	public void setCalidadMigratoria(String calidadMigratoria) {
		this.calidadMigratoria = calidadMigratoria;
	}
	
}

