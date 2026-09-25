package mx.gob.imss.ctirss.delta.model.escritoDesacuerdo;

import java.io.Serializable;

public class CausaDesacuerdo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idCausaDes;
	private String descCausaDes;
	private MateriaDesacuerdo materiaDesacuerdo;
	
	public CausaDesacuerdo() {
		super();
	}
	
	public CausaDesacuerdo(Long idCausaDes, String descCausaDes) {
		super();
		this.idCausaDes=idCausaDes;
		this.descCausaDes = descCausaDes;
	}
	
	public CausaDesacuerdo(Long idMateriaDet, String descCausaDes,MateriaDesacuerdo materiaDesacuerdo) {
		this(idMateriaDet, descCausaDes);
		this.materiaDesacuerdo = materiaDesacuerdo;
	}
	
	public Long getIdCausaDes() {
		return idCausaDes;
	}

	public void setIdCausaDes(Long idCausaDes) {
		this.idCausaDes = idCausaDes;
	}

	public String getDescCausaDes() {
		return descCausaDes;
	}

	public void setDescCausaDes(String descCausaDes) {
		this.descCausaDes = descCausaDes;
	}

	public MateriaDesacuerdo getMateriaDesacuerdo() {
		return materiaDesacuerdo;
	}

	public void setMateriaDesacuerdo(MateriaDesacuerdo materiaDesacuerdo) {
		this.materiaDesacuerdo = materiaDesacuerdo;
	}
	
}