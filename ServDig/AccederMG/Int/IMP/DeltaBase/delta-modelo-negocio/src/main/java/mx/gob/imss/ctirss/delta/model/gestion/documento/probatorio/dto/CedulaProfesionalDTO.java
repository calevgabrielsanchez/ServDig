package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CedulaProfesional;

public class CedulaProfesionalDTO extends CedulaProfesional {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3683081636513641686L;
	private String fechaExpedicionString;

	public String getFechaExpedicionString() {
		return fechaExpedicionString;
	}

	public void setFechaExpedicionString(String fechaExpedicionString) {
		this.fechaExpedicionString = fechaExpedicionString;
	}
	
	

}
