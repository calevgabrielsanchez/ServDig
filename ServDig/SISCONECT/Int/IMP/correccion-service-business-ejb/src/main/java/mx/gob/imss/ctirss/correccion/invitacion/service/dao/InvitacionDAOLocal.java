package mx.gob.imss.ctirss.correccion.invitacion.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.invitacion.InvitacionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;

@Local
public interface InvitacionDAOLocal <T extends AbstractModel> extends InvitacionDAO<T>{

	List<CrtInvitacion> consultaInvitacionesSeguimiento(InvitacionSeguimientoVO filtros);
	
	List<CrtInvitacion> consultaInvitacionPorParametros(CrtInvitacion crtInvitacion);

}
