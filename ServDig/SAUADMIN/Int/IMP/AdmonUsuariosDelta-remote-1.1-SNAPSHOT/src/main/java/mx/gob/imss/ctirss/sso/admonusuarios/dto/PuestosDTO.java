package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

public class PuestosDTO implements Serializable{
	
	private static final long serialVersionUID = 1654265L;
	
	private long cveSsopuesto;
	private String desPuesto;
	
	public long getCveSsopuesto() {
		return cveSsopuesto;
	}
	public void setCveSsopuesto(long cveSsopuesto) {
		this.cveSsopuesto = cveSsopuesto;
	}
	public String getDesPuesto() {
		return desPuesto;
	}
	public void setDesPuesto(String desPuesto) {
		this.desPuesto = desPuesto;
	}	
}
