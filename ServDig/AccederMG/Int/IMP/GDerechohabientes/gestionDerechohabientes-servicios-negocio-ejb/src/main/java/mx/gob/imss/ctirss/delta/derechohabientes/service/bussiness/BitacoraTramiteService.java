package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.SolicitudParser;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.SolicitudParserLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BitacoraTramiteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudNssDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudesAtendidasDto;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;

import org.apache.log4j.Logger;

/**
 * @author Juan Manuel Marquez Hernandez
 * 
 */
@Stateless(name = "bitacoraTramiteService", mappedName = "bitacoraTramiteService")
public class BitacoraTramiteService implements BitacoraTramiteServiceRemote {
	
	@EJB private SolicitudDaoLocal solicitudDaoLocal;
	@EJB(name = "grupoFamiliarDao") GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
    @EJB SolicitudParserLocal solicitudParserLocal;
    
    private static final Logger log = Logger.getLogger(BitacoraTramiteService.class);
    
    @Override
	public DatosSalidaPaginador<SolicitudNssDto> listSolicitudesAtendidas(SolicitudesAtendidasDto solicitudesDto)
			throws DerechohabientesBusinessException, Exception {
    	List<DitSolicitud> ditSols=null;
		List<SolicitudNssDto> sols= new ArrayList<SolicitudNssDto>();
		DatosSalidaPaginador<SolicitudNssDto> salidaPaginador=new DatosSalidaPaginador<SolicitudNssDto>();
		try {
			ditSols =  solicitudDaoLocal.consultaSolAtendidasByUsuario(solicitudesDto);
			sols =	solicitudParserLocal.PersistToModelListPartialNss(ditSols);
			for(SolicitudNssDto sol : sols) {
				if(!sol.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.REGISTRO.getId())){
					Long idPersona = sol.getTramites().get(0).getPersonas() == null ? sol.getTramites().get(0).getPersona().getIdPersona()
							: sol.getTramites().get(0).getPersonas().get(0).getIdPersona();
					
					Parentesco parentesco = grupoFamiliarDaoLocal.getParentescoIntegrante(sol.getNumNss(), idPersona);
					sol.setParentesco(parentesco);
				}
			}
		} catch (Exception e) {
			log.debug("error",e);
		}				
				
		//paginacion
		salidaPaginador.setAaData(sols);
		salidaPaginador.setiTotalRecords(solicitudesDto.getDatosTotales().intValue());
		salidaPaginador.setiTotalDisplayRecords(solicitudesDto.getDatosMostrados().intValue());
		//error
		if(salidaPaginador.getAaData().size()==0){
			//this.nssNoEncontrado();
		}
		
		return salidaPaginador;
	}

	@Override
	public void insertBitacoraSegTramite(Tramite tramite) throws Exception {
		solicitudDaoLocal.insertBitacoraSegTramite(tramite);		
	}


}
