package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitePersonaFisicaServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Tramite;

/**
 * @author Juan Manuel Marquez 
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless( name = "tramitePersonaFisicaService", mappedName = "tramitePersonaFisicaService")
public class TramitePersonaFisicaService implements TramitePersonaFisicaServiceRemote{

	
	@EJB
	private TramitePersonaFisicaDaoLocal tramitePersonaFisicaDao;

	@Override
	public void insertTramite(Tramite tramite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescripcionTipoTramite(Long tramiteId) throws DerechohabientesBusinessException, Exception {
		return this.tramitePersonaFisicaDao.getTramite(tramiteId).getTipoTramite().getDescripcion();
	}

	@Override
	public String getDescripcionTipoTramiteFromDic(Long idTipoTramite) throws Exception {
		return this.tramitePersonaFisicaDao.getDescripcionTipoTramiteFromDic(idTipoTramite);
	}

	

}
