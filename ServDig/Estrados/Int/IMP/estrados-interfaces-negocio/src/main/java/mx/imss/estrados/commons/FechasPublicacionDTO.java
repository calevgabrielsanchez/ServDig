package mx.imss.estrados.commons;

import java.io.Serializable;

public class FechasPublicacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6440544856765660493L;
	/**
	 * 
	 */
	

	private String fechaPublicacion;
	private String fechaInicioPublicacion;
	private String fechaFinPublicacion;
	private String fechaRetiroPublicacion;

	public String getFechaInicioPublicacion() {
		return fechaInicioPublicacion;
	}

	public void setFechaInicioPublicacion(String fechaInicioPublicacion) {
		this.fechaInicioPublicacion = fechaInicioPublicacion;
	}

	public String getFechaFinPublicacion() {
		return fechaFinPublicacion;
	}

	public void setFechaFinPublicacion(String fechaFinPublicacion) {
		this.fechaFinPublicacion = fechaFinPublicacion;
	}

	public String getFechaRetiroPublicacion() {
		return fechaRetiroPublicacion;
	}

	public void setFechaRetiroPublicacion(String fechaRetiroPublicacion) {
		this.fechaRetiroPublicacion = fechaRetiroPublicacion;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

}
