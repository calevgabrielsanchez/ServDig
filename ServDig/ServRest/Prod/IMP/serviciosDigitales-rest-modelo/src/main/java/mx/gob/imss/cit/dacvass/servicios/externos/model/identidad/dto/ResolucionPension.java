package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;



public class ResolucionPension  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5539846187320394363L;
	private String curp;
	private String nss;
	private String clabe;
	private String fecInicioPension;
	private String resolucion1;
	private String resolucion2;
	private String fecSolicitudPension;
	private String fecSolicitudPension2;
	private Long salarioProm;
	private Long incrementos;
	private Long semCotizadas;
	
	
	
	
	
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getResolucion1() {
		return resolucion1;
	}
	public void setResolucion1(String resolucion1) {
		this.resolucion1 = resolucion1;
	}
	public String getResolucion2() {
		return resolucion2;
	}
	public void setResolucion2(String resolucion2) {
		this.resolucion2 = resolucion2;
	}

	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getClabe() {
		return clabe;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	public String getFecInicioPension() {
		return fecInicioPension;
	}
	public void setFecInicioPension(String fecInicioPension) {
		this.fecInicioPension = fecInicioPension;
	}
		public String getFecSolicitudPension() {
		return fecSolicitudPension;
	}
	public void setFecSolicitudPension(String fecSolicitudPension) {
		this.fecSolicitudPension = fecSolicitudPension;
	}
	public String getFecSolicitudPension2() {
		return fecSolicitudPension2;
	}
	public void setFecSolicitudPension2(String fecSolicitudPension2) {
		this.fecSolicitudPension2 = fecSolicitudPension2;
	}
	public Long getSalarioProm() {
		return salarioProm;
	}
	public void setSalarioProm(Long salarioProm) {
		this.salarioProm = salarioProm;
	}
	public Long getIncrementos() {
		return incrementos;
	}
	public void setIncrementos(Long incrementos) {
		this.incrementos = incrementos;
	}
	public Long getSemCotizadas() {
		return semCotizadas;
	}
	public void setSemCotizadas(Long semCotizadas) {
		this.semCotizadas = semCotizadas;
	}
	
	
	
	
		
	
}
