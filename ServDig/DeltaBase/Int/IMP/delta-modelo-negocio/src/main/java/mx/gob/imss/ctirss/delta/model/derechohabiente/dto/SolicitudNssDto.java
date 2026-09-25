package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

public class SolicitudNssDto extends Solicitud {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numNss;
	private Parentesco parentesco;

	public String getNumNss() {
		return numNss;
	}

	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}
	
	
	
}
