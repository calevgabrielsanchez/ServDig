package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CorreccionDatosDerechohabienteDto extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private boolean esAseguradoOPatron;
	
	
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public boolean isEsAseguradoOPatron() {
		return esAseguradoOPatron;
	}
	public void setEsAseguradoOPatron(boolean esAseguradoOPatron) {
		this.esAseguradoOPatron = esAseguradoOPatron;
	}
	

}
