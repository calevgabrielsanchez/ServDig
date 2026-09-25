package mx.gob.imss.ctirss.delta.derechohabientes.service.utility;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudAseguradoServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;


@Stateless( name = "solicitudAseguradoService", mappedName = "solicitudAseguradoService")
public class SolicitudAseguradoService extends AbstractServiceBusiness implements SolicitudAseguradoServiceRemote {
	@EJB(name = "solicitudDao") SolicitudDaoLocal solicitudDaoLocal;
	//private static final Logger log = Logger.getLogger(SolicitudAseguradoService.class);
	
	@Override
	public int tieneSolicitudesAseguradoPorNSS(String strNSS){
		return solicitudDaoLocal.consultaTramitesDerechohabientePorNSS(strNSS);
	}

}
