package mx.gob.imss.ctirss.delta.derechohabientes.service.utility;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.BajaDerechohabienteServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.CorreccionDerechohabienteServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.ProrrogaServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.RegistroDerechohabienteServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.OrquestadorFinalizaSolicitudDerechohabienteRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Stateless(name = "orquestadorFinalizaSolicitudDerechohabiente", mappedName = "orquestadorFinalizaSolicitudDerechohabiente")
public class OrquestadorFinalizaSolicitudDerechohabiente extends AbstractServiceBusiness 
									implements OrquestadorFinalizaSolicitudDerechohabienteLocal, OrquestadorFinalizaSolicitudDerechohabienteRemote{
	
	@EJB
	SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB
	ProrrogaServiceLocal prorrogaService;
	@EJB
	BajaDerechohabienteServiceLocal bajaDerechohabienteService;

	@EJB
	RegistroDerechohabienteServiceLocal registroDerechohabienteService;
	
	@EJB
	CorreccionDerechohabienteServiceLocal correccionDerechohabienteService;
	
	@Override
	 public Solicitud finalizaSolicitudDerechohabiente(Long solicitudId) throws DerechohabientesBusinessException,
		SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException,Exception, ImpactaAlmacenesWSException{
		Solicitud solicitud = null;
		if(solicitudId != null){
			solicitud = new Solicitud();
			solicitud.setSolicitudId(solicitudId);
		}
		//Consultamos la solicitud a finalizar
		
		solicitud = solicitudBusinessRemote.consultar(solicitud);
		
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue() == 
				TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor().intValue()){
			solicitud = correccionDerechohabienteService.finalizarSolicitudCorreccionDatos(solicitud);
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue() == 
				TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor().intValue()){
			solicitud = registroDerechohabienteService.finalizarSolicitudRegistro(solicitud);
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue() == 
				TipoSolicitudEnum.BAJA_DERECHOHABIENTES.getValor().intValue()){
			solicitud = bajaDerechohabienteService.finalizarSolicitudBaja(solicitud);
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue() == 
				TipoSolicitudEnum.PRORROGA.getValor().intValue()){
			GrupoFamiliar integrante = new GrupoFamiliar();
			solicitud = prorrogaService.finalizarSolicitudProrroga(solicitud);
		}else{
			throw new DerechohabientesBusinessException("El tipo de solicitud no es valido");
		}
			
		
		return solicitud;
	}

	@Override
	public void concluirSolicitudDerechohabientes(Long solicitudId)
			throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException, Exception, ImpactaAlmacenesWSException {
		finalizaSolicitudDerechohabiente(solicitudId);
		
	}

}
