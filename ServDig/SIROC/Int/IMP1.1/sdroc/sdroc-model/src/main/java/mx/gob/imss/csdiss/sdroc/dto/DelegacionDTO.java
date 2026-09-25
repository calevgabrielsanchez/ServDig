package mx.gob.imss.csdiss.sdroc.dto;

/**
 * DelegacionDTO 
 */
public class DelegacionDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cveDelegacion;
	private String nomDelegacion;

	public DelegacionDTO() {
	}


	public DelegacionDTO(Long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}



	
	public DelegacionDTO(Long cveDelegacion, String nomDelegacion) {	
		this.cveDelegacion = cveDelegacion;
		this.nomDelegacion = nomDelegacion;		
	}

	public Long getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(Long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getNomDelegacion() {
		return this.nomDelegacion;
	}

	public void setNomDelegacion(String nomDelegacion) {
		this.nomDelegacion = nomDelegacion;
	}

	
}
