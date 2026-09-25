package mx.gob.imss.ctirss.delta.portal.derechohabiente.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.service.entity.ExpedienteServiceEntityLocal;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.service.interfaces.ExpedienteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

/**
 * 
 * @author Lucio Duran Silva
 * 
 * 
 */
@Stateless(name = "expedienteServiceBusiness", mappedName = "expedienteServiceBusiness")
public class ExpedienteServiceBusiness extends AbstractServiceBusiness
		implements ExpedienteServiceBusinessRemote {

	@EJB
	private ExpedienteServiceEntityLocal expedienteServiceEntity;

	@Override
	public DatosSalidaPaginador<TipoTramite> listarTipoTramitesDummy(
			DatosEntradaPaginador<TipoTramite> input, FiltroSolicitud filtro) {

		return this.expedienteServiceEntity.obtenerCatalogoTipoTramite(input,
				filtro);
	}

}
