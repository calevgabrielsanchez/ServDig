package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;

public class DatosAfiliacionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8373803922297185718L;
	
	private String delegacion;
	
	private String subdelegacion;
	
	private String umf;
	
	private String fecValidacionRenapo;
	
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public String getUmf() {
		return umf;
	}
	public void setUmf(String umf) {
		this.umf = umf;
	}
	public String getFecValidacionRenapo() {
		return fecValidacionRenapo;
	}
	public void setFecValidacionRenapo(String fecValidacionRenapo) {
		this.fecValidacionRenapo = fecValidacionRenapo;
	}

	

}
