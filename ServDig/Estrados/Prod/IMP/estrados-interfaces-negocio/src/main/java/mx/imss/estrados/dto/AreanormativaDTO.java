package mx.imss.estrados.dto;

import java.io.Serializable;

public class AreanormativaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1965624835478806919L;

	/**
	 * 
	 */
	

	public AreanormativaDTO() {
	}
	
	public AreanormativaDTO(Integer cveAreanorma, String desAreanorma) {
		super();
		this.cveAreanorma = cveAreanorma;
		this.desAreanorma = desAreanorma;
	}
	
	private Integer cveAreanorma;
	private String desAreanorma;
	
	public Integer getCveAreanorma() {
		return cveAreanorma;
	}

	public void setCveAreanorma(Integer cveAreanorma) {
		this.cveAreanorma = cveAreanorma;
	}

	public String getDesAreanorma() {
		return desAreanorma;
	}

	public void setDesAreanorma(String desAreanorma) {
		this.desAreanorma = desAreanorma;
	}

}
