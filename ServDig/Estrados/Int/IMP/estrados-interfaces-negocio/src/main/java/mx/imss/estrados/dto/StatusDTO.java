package mx.imss.estrados.dto;

import java.io.Serializable;

public class StatusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3778322832836068205L;

	/**
	 * 
	 */
	

	public StatusDTO() {
	}

	public StatusDTO(Integer cveStatus, String desEstatus) {
		super();
		this.cveStatus = cveStatus;
		this.desEstatus = desEstatus;
	}

	private Integer cveStatus;
	private String desEstatus;

	public Integer getCveStatus() {
		return cveStatus;
	}

	public void setCveStatus(Integer cveStatus) {
		this.cveStatus = cveStatus;
	}

	public String getDesEstatus() {
		return desEstatus;
	}

	public void setDesEstatus(String desEstatus) {
		this.desEstatus = desEstatus;
	}

}
