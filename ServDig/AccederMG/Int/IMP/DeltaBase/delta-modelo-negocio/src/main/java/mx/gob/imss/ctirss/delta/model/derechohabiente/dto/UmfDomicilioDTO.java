package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class UmfDomicilioDTO extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5635920539285697064L;
	private MedicoEnTurno medicoEnTurno;
	private Domicilio domicilio;
	private Fisica fisica;
	
	public MedicoEnTurno getMedicoEnTurno() {
		return medicoEnTurno;
	}
	public void setMedicoEnTurno(MedicoEnTurno medicoEnTurno) {
		this.medicoEnTurno = medicoEnTurno;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public Fisica getFisica() {
		return fisica;
	}
	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

}