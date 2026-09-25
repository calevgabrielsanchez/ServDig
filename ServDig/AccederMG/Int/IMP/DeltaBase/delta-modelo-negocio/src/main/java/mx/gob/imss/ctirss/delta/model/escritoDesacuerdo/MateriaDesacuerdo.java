package mx.gob.imss.ctirss.delta.model.escritoDesacuerdo;

import java.io.Serializable;

public class MateriaDesacuerdo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idMateria;
	private String descMateria;
	
	public MateriaDesacuerdo() {
		super();
	}
	
	public MateriaDesacuerdo(Long idMateria) {
		super();
		this.idMateria = idMateria;
	}
	
	public MateriaDesacuerdo(Long idMateria, String descMateria) {
		this(idMateria);
		this.descMateria = descMateria;
	}

	public Long getIdMateria() {
		return idMateria;
	}
	
	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}
	
	public String getDescMateria() {
		return descMateria;
	}
	
	public void setDescMateria(String descMateria) {
		this.descMateria = descMateria;
	}
	
}
