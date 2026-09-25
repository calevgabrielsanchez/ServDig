package mx.gob.imss.ctirss.delta.model.escritoDesacuerdo;

import java.io.Serializable;

public class MotivosDesacuerdo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idMotivoDes;
	private String descMotivoDes;
	
	public MotivosDesacuerdo() {
		super();
	}
	
	public MotivosDesacuerdo(Long idMotivoDes, String descMotivoDes) {
		super();
		this.idMotivoDes=idMotivoDes;
		this.descMotivoDes = descMotivoDes;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getIdMotivoDes() {
		return idMotivoDes;
	}

	public void setIdMotivoDes(Long idMotivoDes) {
		this.idMotivoDes = idMotivoDes;
	}

	public String getDescMotivoDes() {
		return descMotivoDes;
	}

	public void setDescMotivoDes(String descMotivoDes) {
		this.descMotivoDes = descMotivoDes;
	}
}