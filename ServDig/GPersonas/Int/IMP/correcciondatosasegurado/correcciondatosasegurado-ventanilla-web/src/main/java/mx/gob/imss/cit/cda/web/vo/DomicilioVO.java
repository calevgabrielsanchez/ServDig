package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;


public class DomicilioVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Subdelegacion subdelegacion;
	private Domicilio domicilio;
	private EntidadFederativa entidadFederativa;
	
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public EntidadFederativa getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	
}
