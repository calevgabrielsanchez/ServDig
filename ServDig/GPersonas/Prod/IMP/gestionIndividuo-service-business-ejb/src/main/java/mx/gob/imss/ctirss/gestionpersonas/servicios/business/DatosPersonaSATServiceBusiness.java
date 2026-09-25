package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.DatosPersonaSATNoValidosException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.DatosPersonaSATServiceEntityLocal;

@Stateless(mappedName = "datosPersonaSATServiceBusiness")
public class DatosPersonaSATServiceBusiness extends AbstractServiceBusiness
		implements DatosPersonaSATServiceBusinessLocal {

	@EJB
	private DatosPersonaSATServiceEntityLocal datosPersonaSATServiceEntity;

	@Override
	public void guardar(DatosPersonaSAT datosPersonaSAT)
			throws DatosPersonaSATNoValidosException {
		this.datosPersonaSATServiceEntity.guardar(datosPersonaSAT);
	}

	@Override
	public DatosPersonaSAT obtenerDatosSAT(Persona persona)
			throws DatosPersonaSATNoValidosException {
		
		return this.datosPersonaSATServiceEntity.obtenerDatosSAT(persona);
	}
}
