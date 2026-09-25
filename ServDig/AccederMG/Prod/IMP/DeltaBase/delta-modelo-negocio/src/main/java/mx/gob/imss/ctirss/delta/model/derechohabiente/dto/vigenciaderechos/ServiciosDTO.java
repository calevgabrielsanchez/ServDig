package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos;


import java.io.Serializable;

public class ServiciosDTO implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 194336867363217439L;
	private Long idServicio;
	private String servicio;
	private String siNo;
	private String restricciones;
	
	
	
	public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getSiNo() {
		return siNo;
	}
	public void setSiNo(String siNo) {
		this.siNo = siNo;
	}
	public String getRestricciones() {
		return restricciones;
	}
	public void setRestricciones(String restricciones) {
		this.restricciones = restricciones;
	}
	
	
}
