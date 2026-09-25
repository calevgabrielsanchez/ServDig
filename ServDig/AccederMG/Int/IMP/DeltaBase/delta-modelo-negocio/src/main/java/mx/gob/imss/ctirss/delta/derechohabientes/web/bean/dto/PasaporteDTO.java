package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

public class PasaporteDTO {

	private String noPasaporte;
	private String fechaCaducidad;
	private String fechaExpedicion;
	
	
	public String getFechaExpedicion() {
		return fechaExpedicion;
	}
	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
	/**
	 * @return the noPasaporte
	 */
	public String getNoPasaporte() {
		return noPasaporte;
	}
	/**
	 * @param noPasaporte the noPasaporte to set
	 */
	public void setNoPasaporte(String noPasaporte) {
		this.noPasaporte = noPasaporte;
	}
	/**
	 * @return the fechaCaducidad
	 */
	public String getFechaCaducidad() {
		return fechaCaducidad;
	}
	/**
	 * @param fechaCaducidad the fechaCaducidad to set
	 */
	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	
	
	
}
