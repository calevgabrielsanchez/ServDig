package mx.gob.imss.csdiss.sdroc.dto;

/**
 * TipoRegistroDTO 
 */

public class TipoRegistroDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5452833648616712273L;
	
	private Long cveTipoRegistro;
	private String desTipoRegistro;


	public TipoRegistroDTO() {
	}

	public TipoRegistroDTO(Long cveTipoRegistro) {
		this.cveTipoRegistro = cveTipoRegistro;
	}

	public TipoRegistroDTO(Long cveTipoRegistro, String desTipoRegistro) {

		this.cveTipoRegistro = cveTipoRegistro;
		this.desTipoRegistro = desTipoRegistro;
	}


	public Long getCveTipoRegistro() {
		return this.cveTipoRegistro;
	}

	public void setCveTipoRegistro(Long cveTipoRegistro) {
		this.cveTipoRegistro = cveTipoRegistro;
	}


	public String getDesTipoRegistro() {
		return this.desTipoRegistro;
	}

	public void setDesTipoRegistro(String desTipoRegistro) {
		this.desTipoRegistro = desTipoRegistro;
	}

}
