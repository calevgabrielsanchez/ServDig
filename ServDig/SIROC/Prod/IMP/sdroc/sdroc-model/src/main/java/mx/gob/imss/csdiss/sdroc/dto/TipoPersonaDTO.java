package mx.gob.imss.csdiss.sdroc.dto;

/**
 * RocTipoPersonaDTO
 */
public class TipoPersonaDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4994517818595564865L;
	private Long cveTipoPersona;
	private String desTipoPersona;

	public TipoPersonaDTO() {
	}

	public TipoPersonaDTO(Long cveTipoPersona) {
		this.cveTipoPersona = cveTipoPersona;
	}

		public TipoPersonaDTO(Long cveTipoPersona, String desTipoPersona) {
		
		this.cveTipoPersona = cveTipoPersona;
		this.desTipoPersona = desTipoPersona;
	}

	public Long getCveTipoPersona() {
		return this.cveTipoPersona;
	}

	public void setCveTipoPersona(Long cveTipoPersona) {
		this.cveTipoPersona = cveTipoPersona;
	}

	public String getDesTipoPersona() {
		return this.desTipoPersona;
	}

	public void setDesTipoPersona(String desTipoPersona) {
		this.desTipoPersona = desTipoPersona;
	}

		
}
