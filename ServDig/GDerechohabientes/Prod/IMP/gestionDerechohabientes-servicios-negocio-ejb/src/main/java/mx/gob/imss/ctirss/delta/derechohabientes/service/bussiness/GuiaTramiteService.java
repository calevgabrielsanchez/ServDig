package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;




import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TipoTramiteDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GuiaTramiteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TipoTramiteParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Rol;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;


@Stateless(name = "guiaTramiteService", mappedName = "guiaTramiteService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GuiaTramiteService implements GuiaTramiteServiceRemote {
	private static final Long TRAMITADOR=PerfilesEnum.TRAMITADOR.getId();
	@EJB
	TipoTramiteDaoLocal tipoTramiteDao;




	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TipoTramite getRutaTramite(TipoTramite tipoTramite, Rol rol) throws DerechohabientesBusinessException {

		tipoTramite=TipoTramiteParser.persisToModelConGuia(tipoTramiteDao.findTipoTramite(tipoTramite.getIdTipoTramite().longValue()));

		//Carlos -  no hay guia detallada
		if(rol.getIdRol().equals(new Long(TRAMITADOR))){
			tipoTramite.setGuiaDetallada(null);
		}else{//si no lo es
			tipoTramite.setGuiaRapida(null);
		}
		
		if(tipoTramite != null && tipoTramite.getGuiaDetallada()==null){
			
			//TODO crear un PDF con mensage de que no exist
		}
		
		return tipoTramite;
	}

}
