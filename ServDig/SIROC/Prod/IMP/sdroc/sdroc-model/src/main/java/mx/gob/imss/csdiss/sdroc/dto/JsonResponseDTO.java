/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.dto;

/**
 * @author daniel.hernandez
 *
 */
public class JsonResponseDTO {
	
	private String estatus = null; 
	private Object resultado = null;
	private String reporte = null;
	private String mensaje = null;
	private String fechaInicio = null;
	private String fechaFin = null;
	private String certificacionObra = null;
	
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
	 * @return the resultado
	 */
	public Object getResultado() {
		return resultado;
	}
	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}
	/**
	 * @return the reporte
	 */
	public String getReporte() {
		return reporte;
	}
	/**
	 * @param reporte the reporte to set
	 */
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the certificacionObra
	 */
	public String getCertificacionObra() {
		return certificacionObra;
	}
	/**
	 * @param certificacionObra the certificacionObra to set
	 */
	public void setCertificacionObra(String certificacionObra) {
		this.certificacionObra = certificacionObra;
	} 

}
