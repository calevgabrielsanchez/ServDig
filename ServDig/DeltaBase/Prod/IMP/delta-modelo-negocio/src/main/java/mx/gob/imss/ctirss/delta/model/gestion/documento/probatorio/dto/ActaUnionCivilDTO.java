package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

import java.io.Serializable;

public class ActaUnionCivilDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String lugarEmision;
	private String fechaEmision;
	private String autoridadEmisora;
	private String nombreAutoridadEmisora;
	private String entidad;
	private String nombreEntidad;
	private String noReferencia;
	
	public String getLugarEmision() {
		return lugarEmision;
	}
	public void setLugarEmision(String lugarEmision) {
		this.lugarEmision = lugarEmision;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getAutoridadEmisora() {
		return autoridadEmisora;
	}
	public void setAutoridadEmisora(String autoridadEmisora) {
		this.autoridadEmisora = autoridadEmisora;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getNoReferencia() {
		return noReferencia;
	}
	public void setNoReferencia(String noReferencia) {
		this.noReferencia = noReferencia;
	}
	public String getNombreAutoridadEmisora() {
		return nombreAutoridadEmisora;
	}
	public void setNombreAutoridadEmisora(String nombreAutoridadEmisora) {
		this.nombreAutoridadEmisora = nombreAutoridadEmisora;
	}
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	
	
}

