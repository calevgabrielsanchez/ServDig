package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

public class EstatusDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long cveSsoestatus;
	private String desEstatus;

	public EstatusDTO()
	{
		super();
	}
	
	public EstatusDTO(long clave)
	{
		cveSsoestatus = clave;
	}
	
	public long getCveSsoestatus() {
		return cveSsoestatus;
	}
	
	public void setCveSsoestatus(long cveSsoestatus) {
		this.cveSsoestatus = cveSsoestatus;
	}
	
	public String getDesEstatus() {
		return desEstatus;
	}
	
	public void setDesEstatus(String desEstatus) {
		this.desEstatus = desEstatus;
	}
}
