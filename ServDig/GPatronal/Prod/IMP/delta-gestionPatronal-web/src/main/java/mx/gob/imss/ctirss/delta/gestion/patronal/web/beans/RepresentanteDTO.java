package mx.gob.imss.ctirss.delta.gestion.patronal.web.beans;

import java.io.Serializable;

public class RepresentanteDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rfc;
	private String nombreRazonSocial;
	private String curp;
	
	public RepresentanteDTO(String rfc,String nombreRazonSocial,String curp){
		this.rfc=rfc;
		this.nombreRazonSocial=nombreRazonSocial;
		this.curp=curp;		
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	
	
	

}
