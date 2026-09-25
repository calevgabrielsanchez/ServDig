package mx.imss.estrados.dto;

import java.io.Serializable;
import java.util.Date;

public class DiasInhabilDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1230568595864969548L;

	/**
	 * 
	 */
	

	public DiasInhabilDTO() {
	}

	public DiasInhabilDTO(String cveDiaInhabil, Date fecFechainhabil, String desDiaInhabil) {
		super();
		this.cveDiaInhabil = cveDiaInhabil;
		this.fecFechainhabil = fecFechainhabil;
		this.desDiaInhabil = desDiaInhabil;
	}

	private String cveDiaInhabil;
	private Date fecFechainhabil;
	private String desDiaInhabil;

	public String getCveDiaInhabil() {
		return cveDiaInhabil;
	}

	public void setCveDiaInhabil(String cveDiaInhabil) {
		this.cveDiaInhabil = cveDiaInhabil;
	}

	public Date getFecFechainhabil() {
		return fecFechainhabil;
	}

	public void setFecFechainhabil(Date fecFechainhabil) {
		this.fecFechainhabil = fecFechainhabil;
	}

	public String getDesDiaInhabil() {
		return desDiaInhabil;
	}

	public void setDesDiaInhabil(String desDiaInhabil) {
		this.desDiaInhabil = desDiaInhabil;
	}

}
