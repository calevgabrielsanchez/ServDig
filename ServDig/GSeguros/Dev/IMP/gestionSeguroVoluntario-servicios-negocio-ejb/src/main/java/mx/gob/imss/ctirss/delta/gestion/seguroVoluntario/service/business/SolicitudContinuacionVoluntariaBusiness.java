package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SolicitudContinuacionVoluntariaRemote;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

@Stateless(name = "solicitudContinuacionVoluntariaBusiness", mappedName = "solicitudContinuacionVoluntariaBusiness")
public class SolicitudContinuacionVoluntariaBusiness implements
		SolicitudContinuacionVoluntariaRemote {
	@EJB
	private SeguroIvroServiceLocal seguroEntity;

	@Override
	public boolean existeRechazo(Long idPersona) {
		return seguroEntity.existeRechazo(idPersona, ModalidadEnum.CUARENTA.getId());
	}

}
