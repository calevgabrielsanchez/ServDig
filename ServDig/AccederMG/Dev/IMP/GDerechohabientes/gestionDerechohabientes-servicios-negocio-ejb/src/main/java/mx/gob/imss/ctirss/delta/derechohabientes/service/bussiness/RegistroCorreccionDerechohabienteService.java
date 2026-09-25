/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroCorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

/**
 * @author jorge.garciaca
 *
 */
@Stateless( name = "registroCorreccionDerechohabienteService", mappedName = "registroCorreccionDerechohabienteService")
public class RegistroCorreccionDerechohabienteService extends AbstractServiceBusiness implements
		RegistroCorreccionDerechohabienteServiceRemote {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroCorreccionDerechohabienteServiceRemote#findGrupoFamiliarRegistroCorreccion(java.lang.Long, java.lang.Long, mx.gob.imss.ctirss.delta.model.Usuario)
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarRegistroCorreccion(
			Long idAsignacionNss, Long origenSolicitud, Usuario usuario)
			throws DerechohabientesBusinessException {
		
		throw new DerechohabientesBusinessException("implementame no?!!!");
	}

}
