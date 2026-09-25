package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.PersonalServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.PersonalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;

@Stateless(name="personalServiceBusiness", mappedName="personalServiceBusiness")
public class PersonalServiceBusiness extends AbstractServiceBusiness implements
		PersonalServiceBusinessRemote, PersonalServiceBusinessLocal {
	
	@EJB
	private PersonalServiceEntityLocal personalServiceEntity;

	@Override
	public DatosSalidaPaginador<Personal> paginarPersonal(
			DatosEntradaPaginador<Personal> datatablein) {
		return this.personalServiceEntity.paginar(datatablein);
	}

	@Override
	public Personal agregarPersonal(Personal instance) throws Exception {

		this.personalServiceEntity.validaExistePersonal(instance);
		this.personalServiceEntity.agregar(instance);

		return instance;
	}

	@Override
	public void eliminarPersonal(Personal personal) throws Exception {
		this.personalServiceEntity.eliminar(personal);
	}

	@Override
	public Personal getPersonal(Personal personal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personal modificarPersonal(Personal instance) throws Exception{
		return this.personalServiceEntity.actualizar(instance);
	}



}
