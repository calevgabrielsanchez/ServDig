package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;


public class FormaPagoVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cveFormaPago;
	private String descripcion;
	private String nombreArchivo;
	
	public int getCveFormaPago() {
		return cveFormaPago;
	}
	public void setCveFormaPago(int cveFormaPago) {
		this.cveFormaPago = cveFormaPago;
	}
	public String getDescripcion() {
		return descripcion!=null ? descripcion.toUpperCase():descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombreArchivo() {
		return nombreArchivo!=null ? nombreArchivo.toUpperCase():nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	

}
