package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.bean.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CorreccionDatosDerechohabienteDto extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private String curp;
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
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	

}
