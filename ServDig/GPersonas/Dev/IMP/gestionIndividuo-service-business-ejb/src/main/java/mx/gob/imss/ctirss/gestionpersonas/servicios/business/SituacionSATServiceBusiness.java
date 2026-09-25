package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.situacionSAT.SituacionSATNoValidaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.SituacionSATServiceEntityLocal;

@Stateless(mappedName = "situacionSATServiceBusiness")
public class SituacionSATServiceBusiness extends AbstractServiceBusiness
		implements SituacionSATServiceBusinessLocal {

	@EJB
	private SituacionSATServiceEntityLocal situacionSATServiceEntity;

	@Override
	public SituacionSAT guardar(SituacionSAT situacionSAT)
			throws SituacionSATNoValidaException {

		return this.situacionSATServiceEntity.guardar(situacionSAT);
	}

	@Override
	public void expirar(SituacionSAT situacionSAT)
			throws SituacionSATNoValidaException {
		this.situacionSATServiceEntity.expirar(situacionSAT);
	}

	@Override
	public List<SituacionSAT> obtenerSituacionesPersona(Persona persona,
			boolean obtenerActivas) throws SituacionSATNoValidaException {

		return this.situacionSATServiceEntity.obtenerSituacionesPersona(
				persona, obtenerActivas);
	}
}
