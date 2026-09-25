package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ClasificacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.utility.RegistroPatronalServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.PatronSustitucionFusionBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.SujetoObligadoServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica.ActividadEcServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud.SolicitudServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.global.model.RegistroPatronalTO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.enums.SindoAplicacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import weblogic.utils.StringUtils;


@Stateless(name="clasificacionServiceBusiness", mappedName="clasificacionServiceBusiness")
public class ClasificacionService extends AbstractServiceBusiness implements ClasificacionServiceRemote{
	
	@EJB
	RegistroPatronalServiceUtilityLocal registroPatronalTOUtility;
	
	@EJB
	ActividadEcServiceBusinessLocal clasificacionServiceBusiness;
	
	@EJB
	SolicitudServiceBusinessLocal solicitudService;
	
	@EJB
	AfiliacionServiceBusinessRemote afiliacionService;
	
	@EJB
	mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote gceSolicitudService;
	
	@EJB
	SujetoObligadoServiceBusinessLocal sujetoObligadoService;
	
	@EJB
	PatronSustitucionFusionBusinessLocal patronSustitucionFusionBusinessLocal;
	
	@EJB
	private SolicitudBusinessRemote solicitudServiceBusiness;

	
	@EJB(mappedName = "solicitudServiciosExpuestos")
	private SolicitudServiciosExpuestosRemote solicitudServiciosExpuestos;
	
	private final Integer NUM_DIAS_MAXIMO_CITA =30;
	private final Integer NUM_MAXIMO_CITA =100;
	
	@Override
	public void actualizarClasificacionYActividadEconomica(
			RegistroPatronalTO registroPatronal, Long idSolicitud) throws GestionPatronalBusinessException{
		
		super.log.debug("Updating clasification....");
		
		validaDatosParaActualizacionDeClasificacion(registroPatronal);
		try{
			SujetoObligado sujetoTramite = registroPatronalTOUtility.convertirRegistroPatronalASujetoObligado(registroPatronal);
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			clasificacionServiceBusiness.afectarClasificacionActividadEconomica(sujetoTramite, solicitud);
		}catch(Exception e){
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage(), 100100);
		}	
	}
	
	private void validaDatosParaActualizacionDeClasificacion(RegistroPatronalTO registroPatronal) throws GestionPatronalBusinessException{
		if(StringUtils.isEmptyString(registroPatronal.getNumeroRegistro()) ){
			throw new GestionPatronalBusinessException("El nï¿½mero de registro es un dato obligatorio", 100101);
		}else if(registroPatronal.getNumeroRegistro().length()!=8 ){
			throw new GestionPatronalBusinessException("El nï¿½mero de registro es invï¿½lido, debe contener una longitud exacta de 8 caracteres", 100101);
		}else if( registroPatronal.getModalidad() ==null){
			throw new GestionPatronalBusinessException("La modalidad es un dato requerido", 100102);
		}else if( StringUtils.isEmptyString(registroPatronal.getModalidad().getNumModalidad()) ){
			throw new GestionPatronalBusinessException("La modalidad es un dato requerido", 100102);
		}
	}

	@Override
	public void finalizarModificacionSRT(Long idSolicitud) throws GestionPatronalBusinessException {
		SujetoObligado sujetoTramite = new SujetoObligado();
		Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
		try {
			//Validamos que el tramite y la solicitud contengan el estado adecuado
			validarEstadoYTipoDeSolicitud(solicitud);
			//Obtenemos el tramite de modificacion en el SRT
			TramiteSujetoObligado tso = (TramiteSujetoObligado)solicitud.getTramites().get(0);
			//Obtenemos los datos que capturo el usuarios
			sujetoTramite = tso.getSujetoObligado();
			//Validamos que el tramite sea de actualizacion de centro de trabajo
			if(tso.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())){
				afiliacionService.concluirSolicitud(solicitud.getSolicitudId(), sujetoTramite, solicitud.getSolicitante());
				//afiliacionService.actualizarCentroTrabajo(sujetoTramite, tso, solicitud.getSolicitante());
			} else{
				//Obtenemos los datos actuales del patron
				SujetoObligado sujetoObligadoActual = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
				//afectamos la informacion de clasificacion de emepresS
				clasificacionServiceBusiness.afectarClasificacionActividadEconomica(tso.getSujetoObligado(), solicitud, sujetoObligadoActual);
				//Se agregan los datos de la clasificacion anterior en este atributo
				solicitud.setSujetoObligado(sujetoObligadoActual);
				//generamos la solicitud y le pasamos la solicitud para no volverala a consultar
				solicitudService.generarDocumentos(solicitud);
				
				gceSolicitudService.cancelarAnalisisPorRegistroPatronal(
						obtenerNrpCompleto(sujetoTramite), 
						EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave() ,
						solicitud);
			}
		} catch (Exception e ){
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		} finally{
			if (!StringUtils.isEmptyString(sujetoTramite.getNumeroRegistroPatronal())) {
				if(sujetoTramite.getNumeroRegistroPatronal().length()==8){
					String numeroRPCompleto = sujetoTramite.getNumeroRegistroPatronal();
					if(sujetoTramite.getModalidad()!=null && sujetoTramite.getModalidad().getNumModalidad()!=null){
						numeroRPCompleto += sujetoTramite.getModalidad().getNumModalidad();
						if(sujetoTramite.getDigVerificador()!=null){
							numeroRPCompleto += sujetoTramite.getDigVerificador();
						}
					}
					System.out.println("Se completa el RP: "+numeroRPCompleto);
					sujetoTramite.setNumeroRegistroPatronal(numeroRPCompleto);
				}
			}
		}
	}
	
	@Override
	public byte[] finalizarModificacionSRTVentanilla(Solicitud solicitud) throws GestionPatronalBusinessException {
		SujetoObligado sujetoTramite = new SujetoObligado();
		//TODO se setea  valor en el atributo correoConfirmacion para el usuario para identificar 
		//los tramites de movPat y poder mandarel origen la movimiento a SINDO valor de enum
		if(solicitud.getSolicitante() != null) {
			log.debug("seteando el valor para el origen de movimiento a SINDO ya existe el usuario");
			solicitud.getSolicitante().setCorreoConfirmacion(SindoAplicacionEnum.MOV_PAT.getCodigo()+"");
		}
		else {
			Usuario solicitante = new Usuario();
			solicitante.setCorreoConfirmacion(SindoAplicacionEnum.MOV_PAT.getCodigo()+"");
			solicitud.setSolicitante(solicitante);
			log.debug("creando el usuario y seteando el origen de movimiento a SINDO ");
		}
			
		try {
			//Validamos que el tramite y la solicitud contengan el estado adecuado
			//validarEstadoYTipoDeSolicitud(solicitud);
			//Obtenemos el tramite de modificacion en el SRT
			TramiteSujetoObligado tso = (TramiteSujetoObligado)solicitud.getTramites().get(0);
			//Obtenemos los datos que capturo el usuarios
			sujetoTramite = tso.getSujetoObligado();
			//Validamos que el tramite sea de actualizacion de centro de trabajo
			if(tso.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())){
				afiliacionService.concluirSolicitud(solicitud.getSolicitudId(), sujetoTramite, solicitud.getSolicitante());
				//afiliacionService.actualizarCentroTrabajo(sujetoTramite, tso, solicitud.getSolicitante());
				return clasificacionServiceBusiness.obtenerDoctosResultantesModificacionSRTVentanilla(solicitud);
			} else{
				//Obtenemos los datos actuales del patron
				SujetoObligado sujetoObligadoActual = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
				sujetoObligadoActual.getClasificacion().setSujetoObligado(sujetoObligadoActual);
				//afectamos la informacion de clasificacion de emepresS
				clasificacionServiceBusiness.afectarClasificacionActividadEconomica(tso.getSujetoObligado(), solicitud, sujetoObligadoActual);
				//Se agregan los datos de la clasificacion anterior en este atributo
				solicitud.setSujetoObligado(sujetoObligadoActual);
				//generamos la solicitud y le pasamos la solicitud para no volverala a consultar
				//solicitudService.generarDocumentos(solicitud);
				//Guardamos la información del tramite  en e XML
				
				try {
					solicitudServiceBusiness.actualizarXmlTramite(solicitud.getTramites().get(0));
				}catch (Exception e) {
					log.error("ocurrio un error al actualizar el XML del tramte" ,e );
				}
	
				try {
				gceSolicitudService.cancelarAnalisisPorRegistroPatronal(
						obtenerNrpCompleto(sujetoTramite), 
						EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave() ,
						solicitud);
				}catch(Exception e) {
					log.error("ocurrio un error al cancelar el analisis por registro patronal" ,e );
				}
				return clasificacionServiceBusiness.obtenerDoctosResultantesModificacionSRTVentanilla(solicitud);
			}
		} catch (Exception e ){
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		} finally{
			if (!StringUtils.isEmptyString(sujetoTramite.getNumeroRegistroPatronal())) {
				if(sujetoTramite.getNumeroRegistroPatronal().length()==8){
					String numeroRPCompleto = sujetoTramite.getNumeroRegistroPatronal();
					if(sujetoTramite.getModalidad()!=null && sujetoTramite.getModalidad().getNumModalidad()!=null){
						numeroRPCompleto += sujetoTramite.getModalidad().getNumModalidad();
						if(sujetoTramite.getDigVerificador()!=null){
							numeroRPCompleto += sujetoTramite.getDigVerificador();
						}
					}
					System.out.println("Se completa el RP: "+numeroRPCompleto);
					sujetoTramite.setNumeroRegistroPatronal(numeroRPCompleto);
				}
			}
		}
	}
	
	private String obtenerNrpCompleto(SujetoObligado sujetoTramite){
		String numeroRPCompleto=sujetoTramite.getNumeroRegistroPatronal();
		
		log.debug("el rp a mandar para analisis es " + numeroRPCompleto + " y tiene " + numeroRPCompleto.length() + " posiciones");
		if( numeroRPCompleto != null && numeroRPCompleto.length() > 11){
			log.debug("el nrp tiene mas de 11 posiciones");
			numeroRPCompleto = numeroRPCompleto.substring(0,11);
		}
		sujetoTramite.setNumeroRegistroPatronal(numeroRPCompleto);
		log.debug("Entro a verificar el numero de registro patronal antes de enviarlo a analisis " + numeroRPCompleto);
		if (!StringUtils.isEmptyString(sujetoTramite.getNumeroRegistroPatronal())) {
			if(sujetoTramite.getNumeroRegistroPatronal().length()==8){
				numeroRPCompleto = sujetoTramite.getNumeroRegistroPatronal();
				if(sujetoTramite.getModalidad()!=null && sujetoTramite.getModalidad().getNumModalidad()!=null){
					numeroRPCompleto += sujetoTramite.getModalidad().getNumModalidad();
					if(sujetoTramite.getDigVerificador()!=null){
						numeroRPCompleto += sujetoTramite.getDigVerificador();
					}
				}
			}
		}
		
		return numeroRPCompleto;
	}
	
	private void validarEstadoYTipoDeSolicitud(Solicitud solicitud) throws GestionPatronalBusinessException{
		Long idTipoSolicitud = solicitud.getTipoSolicitud().getIdTipoSolicitud();
		Integer idEstadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
		if(!idTipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().longValue())
			&& !idTipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue()))
				throw new GestionPatronalBusinessException("El tipo de solicitud proporcionado no es vï¿½lido",100102);
		
		if(idEstadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()) 
				||idEstadoSolicitud.equals(EstadoSolicitudEnum.RECHAZADA.getCodigo()) 
				||idEstadoSolicitud.equals(EstadoSolicitudEnum.CANCELADA.getCodigo()))
			throw new GestionPatronalBusinessException("La solicitud proporcionada no contiene un estado vï¿½lido",100103);
		
	}

	@Override
	public Solicitud guardaCitaModificacionSRTVInternet(Solicitud solicitud, SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException {
		log.debug("llegue a generar la cita para la solicid de clasificacion");
		CitaSolicitud cita = new CitaSolicitud();
		try {
			cita.setSubdelegacion(solicitud.getSubdelegacion());
			cita.setRefFolioCita(solicitud.getNoFolioSolicitud());
			cita.setCveIdSolicitud(solicitud.getSolicitudId());
			cita.setNumDiasMaximaCita(NUM_DIAS_MAXIMO_CITA);
			cita.setNumMaximoCitas(NUM_MAXIMO_CITA);
			cita = solicitudServiciosExpuestos.calculaFechaCita(cita);
			log.debug("la fecha calculada es "+ cita.getFechaHora());
			cita = solicitudServiciosExpuestos.guardaCitaSolicitud(cita);
			solicitud.setCitaSolicitud(cita);
			solicitudService.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, null);
		}catch (SolicitudException e) {
			log.error("error al generar la cita", e);
			throw new GestionPatronalBusinessException("error al generar la cita " + e.getMessage());
		}catch (Exception e) {
			log.error("error al guardar la solicitud de movPat", e);
			throw new GestionPatronalBusinessException("error al guardar la solicitud de movPat " + e.getMessage());
		}
		return solicitud;
	}
	
	
	
	
	
}
