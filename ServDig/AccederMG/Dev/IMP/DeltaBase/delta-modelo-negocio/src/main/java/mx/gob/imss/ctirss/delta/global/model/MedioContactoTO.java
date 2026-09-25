package mx.gob.imss.ctirss.delta.global.model;

import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;

public class MedioContactoTO {
	
	//La Clave del medio de contacto.
	private Long clave;
	
	//El tipo de medio de contacto
	private TipoMedioContacto tipoMedioContacto;
	//
	private String desFormaContacto;

	public Long getClave() {
		return clave;
	}
	public void setClave(Long clave) {
		this.clave = clave;
	}
	public TipoMedioContacto getTipoMedioContacto() {
		return tipoMedioContacto;
	}
	public void setTipoMedioContacto(TipoMedioContacto tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}
	public String getDesFormaContacto() {
		return desFormaContacto;
	}
	public void setDesFormaContacto(String desFormaContacto) {
		this.desFormaContacto = desFormaContacto;
	}
	
	
	
}
