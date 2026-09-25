package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.ActividadComplementariaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.ActComplementaria;

@Stateless(name="actividadComplementariaServiceBusiness", mappedName="actividadComplementariaServiceBusiness")
public class ActividadComplementariaServiceBusiness extends
		AbstractServiceBusiness implements
		ActividadComplementariaServiceBusinessLocal,
		ActividadComplementariaServiceBusinessRemote {

	@Override
	public ActComplementaria getActividadComplementaria(
			ActComplementaria actividadComplementaria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActComplementaria modificarActividadComplementaria(
			ActComplementaria actividadComplementaria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
