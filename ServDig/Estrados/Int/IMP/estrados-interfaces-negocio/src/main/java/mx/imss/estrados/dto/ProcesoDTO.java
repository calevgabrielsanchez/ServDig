package mx.imss.estrados.dto;

import java.io.Serializable;

public class ProcesoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6890653758775027317L;

	/**
	 * 
	 */
	

	public ProcesoDTO() {
	}

	public ProcesoDTO(Integer cveProceso, String desProceso) {
		super();
		this.cveProceso = cveProceso;
		this.desProceso = desProceso;
	}

	private Integer cveProceso;
	private String desProceso;

	public Integer getCveProceso() {
		return cveProceso;
	}

	public void setCveProceso(Integer cveProceso) {
		this.cveProceso = cveProceso;
	}

	public String getDesProceso() {
		return desProceso;
	}

	public void setDesProceso(String desProceso) {
		this.desProceso = desProceso;
	}

}
