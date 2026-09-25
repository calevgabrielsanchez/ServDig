package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.Date;

public class Sav010DTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nss;
	private String curp;
	private String clinica;
	private String umf;
	private String nombreDerechohabiente;
	private String curpDerechohabiente;
	private String patron;
	private Date fechaBaja;
	private String fecha;
	private String lugar;
	private String empleado;
	
	/**
	 * @return the empleado
	 */
	public String getEmpleado() {
		return empleado;
	}
	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}
	/**
	 * @return the nss
	 */
	public String getNss() {
		return nss;
	}
	/**
	 * @param nss the nss to set
	 */
	public void setNss(String nss) {
		this.nss = nss;
	}
	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}
	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}
	/**
	 * @return the clinica
	 */
	public String getClinica() {
		return clinica;
	}
	/**
	 * @param clinica the clinica to set
	 */
	public void setClinica(String clinica) {
		this.clinica = clinica;
	}
	/**
	 * @return the umf
	 */
	public String getUmf() {
		return umf;
	}
	/**
	 * @param umf the umf to set
	 */
	public void setUmf(String umf) {
		this.umf = umf;
	}
	/**
	 * @return the nombreDerechohabiente
	 */
	public String getNombreDerechohabiente() {
		return nombreDerechohabiente;
	}
	/**
	 * @param nombreDerechohabiente the nombreDerechohabiente to set
	 */
	public void setNombreDerechohabiente(String nombreDerechohabiente) {
		this.nombreDerechohabiente = nombreDerechohabiente;
	}
	/**
	 * @return the curpDerechohabiente
	 */
	public String getCurpDerechohabiente() {
		return curpDerechohabiente;
	}
	/**
	 * @param curpDerechohabiente the curpDerechohabiente to set
	 */
	public void setCurpDerechohabiente(String curpDerechohabiente) {
		this.curpDerechohabiente = curpDerechohabiente;
	}
	/**
	 * @return the patron
	 */
	public String getPatron() {
		return patron;
	}
	/**
	 * @param patron the patron to set
	 */
	public void setPatron(String patron) {
		this.patron = patron;
	}
	/**
	 * @return the fechaBaja
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}
	/**
	 * @param fechaBaja the fechaBaja to set
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}
	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	
}
