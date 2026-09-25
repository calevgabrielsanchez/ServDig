package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CorreccionDTO extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Long> candidatos;
	
	public List<Long> getCandidatos() {
		return candidatos;
	}
	public void setCandidatos(List<Long> candidatos) {
		this.candidatos = candidatos;
	}
	
}
