package mx.gob.imss.csdiss.sdroc.dto;
// default package

/**
 * TipoPatronDTO 
 */
public class TipoPatronDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9056403233264998663L;
	
	private Long cveTipoPatron;
	private String desTipoPatron;

	public TipoPatronDTO() {
	}

	public TipoPatronDTO(Long cveTipoPatron) {
		this.cveTipoPatron = cveTipoPatron;
	}

	public TipoPatronDTO(Long cveTipoPatron, String desTipoPatron) {

		this.cveTipoPatron = cveTipoPatron;
		this.desTipoPatron = desTipoPatron;
	}

	public Long getCveTipoPatron() {
		return this.cveTipoPatron;
	}

	public void setCveTipoPatron(Long cveTipoPatron) {
		this.cveTipoPatron = cveTipoPatron;
	}

	public String getDesTipoPatron() {
		return this.desTipoPatron;
	}

	public void setDesTipoPatron(String desTipoPatron) {
		this.desTipoPatron = desTipoPatron;
	}

}
