package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.AfiliacionGlobalServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion.RegistroPatronalServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Certificado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import weblogic.javaee.TransactionTimeoutSeconds;
import weblogic.jws.Transactional;



@TransactionTimeoutSeconds(value=900)
@Stateless(name="afiliacionGlobalServiceBusiness", mappedName="afiliacionGlobalServiceBusiness")
public class AfiliacionGlobalService extends AbstractServiceBusiness implements AfiliacionGlobalServiceRemote{
	
	@EJB
	SolicitudServiceBusinessRemote solicitudService;
	@EJB
	RegistroPatronalServiceEntityLocal registroPatronalEntity;
	@EJB
	AfectarDatosPersonaBusinessRemote personaService;
	@EJB
	AfiliacionServiceBusinessRemote afiliacionService;
	@EJB
	ActividadEcServiceRemote clasificacionService;
	@EJB
	SolicitudBusinessRemote solicitudBusinessService;
	@EJB
	ConcluirAltaPatronalBusinessRemote concluirAltaService;
	@EJB
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	@EJB
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	PersonaMoralBusinessRemote personaMoralServiceBusiness;
	@EJB
	RuleServiceBusinessRemote ruleServiceBusiness;
	@EJB
	mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote gceSolicitudService;
	@EJB
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	@EJB
	PersonasAutorizadasServiceRemote personasAutorizadasService;
	@EJB
	PersonaBusinessRemote personaBusinessService;
    @EJB
    SocioServiceBusinessRemote socioServiceBusiness;
    @EJB 
    private SolicitudServiciosExpuestosRemote solicituServiciosExpuestos;
    
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Transactional(timeout=900)
	@Override
	public void concluirAltaPatronal(Long idSolicitud,
			String numeroRegistroPatronal, String nrpAsociado) throws GestionPatronalBusinessException{
    		log.debug("****ALTAPATRONALOSB.INCIA ALTA PATRONAL cveIdSolcitud" + idSolicitud +   "y NRP " + numeroRegistroPatronal );
			boolean actualizaSolicitud = false;
			boolean existeSolicitud = false;
			String mensajeError = null;
			
			Solicitud solicitud = null;
		try {
			log.debug("****ALTAPATRONALOSB*** sincronizando validacion para cveIdSolcitud " + idSolicitud + ", " + new Date());			
			//validamos si ya se esta atendiendo la solicitud
			solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud = solicitudService.validaEstadoSolicitudEnOSB(solicitud); 

			ruleServiceBusiness.validarNuevoRegistroPatronal(numeroRegistroPatronal);
			List<EstadoSolicitudEnum> estadosValidos = new ArrayList<EstadoSolicitudEnum>();
			estadosValidos.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION);
			//solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
			
			solicitudBusinessService.validarEstadoProcesamiento(solicitud, estadosValidos);
			//se actualizan los datos de la fiel de la persona 
			existeSolicitud = true;
			try {
				concluirTramitesDeAlta(solicitud, numeroRegistroPatronal);
				log.debug("****ALTAPATRONALOSB*** pase concluirTramitesDeAlta cveIdSolcitud" + idSolicitud);
			}catch(GestionPatronalBusinessException e) {
				log.error("****ALTAPATRONALOSB.ERROR concluirTramitesDeAlta **** cveIdSolcitud" + idSolicitud  
						+ " ocurrio un error de tipo GestionPatronalBusinessException del flujo concluirTramitesDeAlta " , e);
				throw e;
			}
			
			Date fechaProcesamiento = Calendar.getInstance().getTime();
			if(solicitud.getFechaPresentacion()==null)
				solicitud.setFechaPresentacion(fechaProcesamiento);
			solicitud.setFechaConclusion(fechaProcesamiento);
			
			try {
				crearAnalisisGCE(numeroRegistroPatronal, solicitud);
				log.debug("****ALTAPATRONALOSB*** pase crearAnalisisGCE cveIdSolcitud" + idSolicitud);
			} catch (ClasificacionException e) {
				e.printStackTrace();
				log.error("****ALTAPATRONALOSB.ERROR crearAnalisisGCE**** ocurrio un error de tipo ClasificacionException cveIdSolcitud" + idSolicitud , e);
				throw new GestionPatronalBusinessException(e.getMessage());
			}
			
			Modalidad modalidadAlta = obtenerModalidadDeAlta(solicitud);
			if(modalidadAlta.getNumModalidad().equals(ModalidadEnum.TREINTA.getNumModalidad())){
				TramiteSujetoObligado tramiteAltaMod30 = null;
				for(Tramite tramite : solicitud.getTramites())
					if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo()))
						tramiteAltaMod30 = (TramiteSujetoObligado)tramite;

				List<RepresentanteLegal> rlsTramite = tramiteAltaMod30.getSujetoObligado().getRepresentantesLegales();
				List<PersonaAutorizada> pasTramite = tramiteAltaMod30.getSujetoObligado().getPersonasAutorizadas();

				log.debug("****ALTAPATRONALOSB*** es modalidad 30 cveIdSolcitud" + idSolicitud);
				try {
					realizarAltaPatronalModulo14(solicitud.getSolicitudId(), 
							numeroRegistroPatronal, nrpAsociado, solicitud.getOrigenSolicitud(), obtenerFechaEfecto(solicitud),rlsTramite,pasTramite);
					log.debug("****ALTAPATRONALOSB*** pase la llamada al proceso de  modalidad 30  realizarAltaPatronalModulo14 cveIdSolcitud" + idSolicitud);
				}catch(GestionPatronalBusinessException e) {
					log.error("****ALTAPATRONALOSB.ERROR concluirTramitesDeAlta ****  cveIdSolcitud" + idSolicitud
							+ " ocurrio un error de tipo GestionPatronalBusinessException del flujo realizarAltaPatronalModulo14 " , e);
					throw e;
				}
			}
			
			try {
				solicitudService.generarDocumentos(solicitud.getNoFolioSolicitud());
				log.debug("****ALTAPATRONALOSB*** pase solicitudService.generarDocumentos cveIdSolcitud" + idSolicitud);
			}catch (Exception e) {
				log.error("****ALTAPATRONALOSB.ERROR concluirAltaPatronal**** ocurrio un error de tipo solicitudService.generarDocumentos  cveIdSolcitud" + idSolicitud , e);
				throw new GestionPatronalBusinessException("Error al generar los documentos del alta patronal solicitudService.generarDocumentos" + e.getMessage());
			}
			
			try {
				solicitudBusinessService.actualizarSolicitudAEstatusConcluida(solicitud);
				log.debug("****ALTAPATRONALOSB*** pase solicitudBusinessService.actualizarSolicitudAEstatusConcluida cveIdSolcitud" + idSolicitud);
			}catch(Exception e) {
				log.error("****ALTAPATRONALOSB.ERROR concluirAltaPatronal**** ocurrio un error de tipo "
						+ "solicitudBusinessService.actualizarSolicitudAEstatusConcluida al finalizar la solicitud  cveIdSolcitud" + idSolicitud , e);
				throw new GestionPatronalBusinessException("Error al al finalizar la solicitud del alta patronal solicitudBusinessService.actualizarSolicitudAEstatusConcluida " + e.getMessage());
			}
			
			//se encola movimiento hacia SINDO por el OSB
			concluirAltaService.concluirAltaPatronal(numeroRegistroPatronal, solicitud);
			log.debug("****ALTAPATRONALOSB*** pase concluirAltaPatronal encola movimiento a OSB cveIdSolcitud" + idSolicitud);
			
			
//AQUI IMPLEMENTAR GUARDADO EN TABLA PARA SOLICITUDES DE VNDI para confirmar que se finalizo el AP
solicitudService.confirmaApVNDI(idSolicitud);
			
		}catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			log.error("****ALTAPATRONALOSB.ERROR validarEstadoProcesamiento**** ocurrio un error de tipo SolicitudNoValidaException "
					+ " la solicitud no cuenta con un estado valido cveIdSolcitud" + idSolicitud , e);
			mensajeError = "la solicitud no cuenta con un estado valido  solicitudBusinessService.validarEstadoProcesamiento" + e.getMessage();
			throw new GestionPatronalBusinessException(" la solicitud no cuenta con un estado valido  solicitudBusinessService.validarEstadoProcesamiento" + e.getMessage());
		}catch (GestionPatronalBusinessException e) {
			log.error("****ALTAPATRONALOSB.ERROR concluirAltaPatronal **** "
					+ "ocurrio un error de tipo GestionPatronalBusinessException del flujo de negocio de alta patronal cveIdSolcitud" + idSolicitud, e);
			actualizaSolicitud = true;
			mensajeError = e.getMessage();
			throw e;
		}catch(Exception e) {
			log.error("****ALTAPATRONALOSB.ERROR concluirAltaPatronal **** "
					+ "ocurrio un error no cachado en alguno de los proceoss del flujo de negocio de alta patronal cveIdSolcitud" + idSolicitud , e);
			actualizaSolicitud = true;
			mensajeError = "ocurrio un error no cachado en alguno de los proceoss del flujo de negocio de alta patronal " + e.getMessage();
			throw new GestionPatronalBusinessException("ocurrio un error no cachado en alguno de los proceoss del flujo de negocio de alta patronal " + e.getMessage());
		}
		log.debug("****ALTAPATRONALOSB.FINALIZA ALTA PATRONAL cveIdSolcitud" + idSolicitud +   "y NRP" + numeroRegistroPatronal );
	}
	
//	private Solicitud validaEstadoSolicitudEnOSB(Solicitud solicitud) throws GestionPatronalBusinessException{
//		log.debug("::: Revisando el estado de la solicitud " + solicitud.getSolicitudId() + ", " + new Date());
//		for(Tramite tramiteAlmacenado: solicitud.getTramites()) {
//			if(tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
//					tramiteAlmacenado.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()) {
//				TramiteSujetoObligado tr = (TramiteSujetoObligado)tramiteAlmacenado;
//				log.debug("::: Estado de Proceso en OSB: " + tr.getSolicitudEnProcesoOSB() + ", solicitud: "+solicitud.getSolicitudId()+", " + new Date());
//				
//				//si el XML del tramite ya contiene el tag con este valor
//				if(tr.getSolicitudEnProcesoOSB() == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()) {
//					log.debug("::: La solicitud " + solicitud.getSolicitudId() + ", ya esta en proceso en el OSB, " + new Date());
//					throw new GestionPatronalBusinessException("La solicitud se esta procesando, por favor espere");
//				}else {
//					log.debug("::: La solicitud " + solicitud.getSolicitudId() + " aun NO es procesada en el OSB, se agrega marca de proceso, " + new Date());
//					solicitudService.actualizarTramiteAltaEnProcesoOSB(solicitud, EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue());
//					log.debug("::: La solicitud "+solicitud.getSolicitudId()+" fue actualizada con la marca de proceso, " + new Date());
//					
//				}
//			}
//		}
//		return solicitud;
//	}

	private Modalidad obtenerModalidadDeAlta(Solicitud solicitud) {
		
		Tramite tramiteAltaSRT =null;
		
		for(Tramite tramite : solicitud.getTramites()){
			TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(tramite.getTipoTramite().getIdTipoTramite());
			log.error("Tipo Tramite en solicitud de Alta: "+tipoTramite);
			switch(tipoTramite){
				case ALTA_SRT:
					tramiteAltaSRT = tramite;
					break;
				case ALTA_SRT_PM:
					tramiteAltaSRT = tramite;
					break;
			}
			
		}
		
		SujetoObligado sujetoObligado = ((TramiteSujetoObligado)tramiteAltaSRT).getSujetoObligado();
		return sujetoObligado.getModalidad();
	}
	
	private Date obtenerFechaEfecto(Solicitud solicitud){
		
		Tramite tramiteAltaSRT =null;
		
		for(Tramite tramite : solicitud.getTramites()){
			TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(tramite.getTipoTramite().getIdTipoTramite());
			log.error("Tipo Tramite en solicitud de Alta: "+tipoTramite);
			switch(tipoTramite){
				case ALTA_SRT:
					tramiteAltaSRT = tramite;
					break;
				case ALTA_SRT_PM:
					tramiteAltaSRT = tramite;
					break;
			}
			
		}
		
		SujetoObligado sujetoObligado = ((TramiteSujetoObligado)tramiteAltaSRT).getSujetoObligado();
		return sujetoObligado.getClasificacion().getFecEfecto();
	}
	
	private void realizarAltaPatronalModulo14(Long idSolicitud, String numeroRegistroPatronalOrigen, String numeroRegistroPatronalDestino, 
			OrigenSolicitud origenSolicitud, Date fecEfecto,
			List<RepresentanteLegal> rlsMod30, List<PersonaAutorizada> pasMod30)throws GestionPatronalBusinessException{
		
		SujetoObligado sujetoTramite = new SujetoObligado();
		
		sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronalOrigen);
		sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		
		sujetoTramite = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		sujetoTramite = afiliacionService.obtenerClonDeSujetoObligadoParaAlta(sujetoTramite);
		sujetoTramite.setNumeroRegistroPatronal(null);
		sujetoTramite.setCveIdSujetoObligado(null);
		sujetoTramite.getClasificacion().setId(null);
		sujetoTramite.getClasificacion().setFecEfecto(fecEfecto);
		sujetoTramite.setRepresentantesLegales(rlsMod30);
		sujetoTramite.setPersonasAutorizadas(pasMod30);
		List<MedioContacto> mediosCT = new ArrayList<MedioContacto>();
		sujetoTramite.getCntroTrabajo().setClave(null);
		for(MedioContacto contacto : sujetoTramite.getCntroTrabajo().getMediosContacto()){
			contacto.setClave(null);
			mediosCT.add(contacto);
		}
		sujetoTramite.getCntroTrabajo().setCveIdPatronSujetoObligado(null);
		sujetoTramite.getCntroTrabajo().setMediosContacto(mediosCT);
		
		TramiteSujetoObligado tramiteAlta = new TramiteSujetoObligado();
		tramiteAlta.setTipoTramite(new TipoTramite());
		tramiteAlta.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ALTA_SRT_PM.getCodigo());
		tramiteAlta.setSujetoObligado(sujetoTramite);
		tramiteAlta.setFechaTramite(Calendar.getInstance().getTime());
		tramiteAlta.setFechaPresentacion(Calendar.getInstance().getTime());
		tramiteAlta.setEstadoTramite(new EstadoTramite());
		tramiteAlta.getEstadoTramite().setIdEstadoTramitePersona(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		solicitudService.agregarTramiteASolicitud(idSolicitud, tramiteAlta);
		tramiteAlta=(TramiteSujetoObligado)concluirTramiteAltaPatronal(tramiteAlta, numeroRegistroPatronalDestino, null, origenSolicitud);
		try {
			solicitudService.actualizarTramiteDeAltaEnSolicitud(tramiteAlta);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		}
		concluirAltaService.reportarMovimientoAltaPatronal(numeroRegistroPatronalDestino, tramiteAlta.getSujetoObligado());
	}
	
	/**
	 * Este metodo se emplea para reducir la incidencia de duplicaci�n de solicitudes, en caso de que una solicitud
	 * sea encolada dos veces por causa de una actualizaci�n en pantalla o un doble clic se pretende verificar el estado de la solicitud justo antes de generar documentos.
	 * @param noFolio
	 * @throws GestionPatronalBusinessException
	 */
	public void validarEstadoPrevioParaAfectar(String noFolio) throws GestionPatronalBusinessException{
		Solicitud pivote = new Solicitud();
		pivote.setNoFolioSolicitud(noFolio);
		pivote = solicitudBusinessService.obtenerEstados(pivote);
		
		if(!pivote.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())){
			throw new GestionPatronalBusinessException("La solicitud fue procesada previamente, espere notificaci�n");
		}
	}
	
	private void crearAnalisisGCE(String numeroRegistroPatronal, Solicitud solicitud) throws ClasificacionException{
		for(Tramite tramite : solicitud.getTramites()){
			TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(tramite.getTipoTramite().getIdTipoTramite());
			log.error("Tipo Tramite en solicitud de Alta: "+tipoTramite);
			switch(tipoTramite){
				case ALTA_SRT:
					TramiteSujetoObligado tso1 = (TramiteSujetoObligado)tramite;
					solicitud.setSujetoObligado(tso1.getSujetoObligado());
					break;
				case ALTA_SRT_PM:
					TramiteSujetoObligado tso2 = (TramiteSujetoObligado)tramite;
					solicitud.setSujetoObligado(tso2.getSujetoObligado());
					break;	
			}
			
		}
		
		gceSolicitudService.cancelarAnalisisPorRegistroPatronal(
				numeroRegistroPatronal, 
				EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave() ,
				solicitud);

	}
	
	/**
	 * Concluye los tramites de alta cuidando el orden de conclusion
	 * @param solicitud
	 * @param numeroRegistroPatronal
	 * @throws GestionPatronalBusinessException
	 */
	private void concluirTramitesDeAlta(Solicitud solicitud, String numeroRegistroPatronal) throws GestionPatronalBusinessException{
		Tramite tramiteAltaSRT=null;
		Tramite tramiteModificacionDatosGenerales=null;
		Tramite tramiteAltaSocio = null;
		log.debug("*****ALTAPATRONALOSB  llegue al metodo  concluirTramitesDeAlta  cveIdSolcitud" + solicitud.getSolicitudId());
		for(Tramite tramite : solicitud.getTramites()){
			TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(tramite.getTipoTramite().getIdTipoTramite());
			log.debug("Tipo Tramite en solicitud de Alta: "+tipoTramite);
			switch(tipoTramite){
			case ALTA_SRT:
				tramiteAltaSRT = tramite;
				break;
			case ALTA_SRT_PM:
				tramiteAltaSRT = tramite;
				break;
			case ACTUALIZACION_DATOS_GENERALES:
				tramiteModificacionDatosGenerales=tramite;
				break;
			case ACTUALIZACION_DENOMINACION_SOCIAL://TODO eliminar cuando se elimine del tipo de tramite
				tramiteModificacionDatosGenerales=tramite;
				break;
			case ACTUALIZACION_DATOS_USUARIO:
				tramiteModificacionDatosGenerales=tramite;
				break;
			case ACTUALIZACION_SOCIO:
				tramiteAltaSocio=tramite;
				break;
			default:
				log.error("Tipo de tramite invalido");
				throw new GestionPatronalBusinessException("Tipo de tr�mite inv�lido");
			}

		}
		try {
			if(tramiteModificacionDatosGenerales!=null)
				tramiteModificacionDatosGenerales = concluirTramiteActualizacionDatosGenerales(tramiteModificacionDatosGenerales);
			if(tramiteAltaSRT!=null)
				tramiteAltaSRT = concluirTramiteAltaPatronal(tramiteAltaSRT, numeroRegistroPatronal, solicitud.getCertificado(), solicitud.getOrigenSolicitud());
			log.debug("ALTAPATRONALOSB pase el metodo concluirTramiteAltaPatronal   cveIdSolcitud" + solicitud.getSolicitudId());		
			//Se actualiza el tr�mite para que el reporte arp tenga la informaci�n del registro patronal

			afiliacionService.actualizarSolicitudDeAltaPatronal(solicitud, ((TramiteSujetoObligado)tramiteAltaSRT).getSujetoObligado());
			concluirTramiteAltaPersonaAutorizada(tramiteAltaSRT, solicitud.getSolicitudId());
			if(tramiteAltaSocio != null){
				socioServiceBusiness.afectarTramiteAltaSocios(tramiteAltaSocio, solicitud);
			}

		}catch(GestionPatronalBusinessException e) {
			log.error("ALTAPATRONALOSB.ERROR concluirTramitesDeAlta se cacho una excepcion de tipo GestionPatronalBusinessException   cveIdSolcitud" + solicitud.getSolicitudId(), e);
			throw e;
		}catch (Exception e) {
			log.error("ALTAPATRONALOSB.ERROR concluirTramitesDeAlta con un error no cachado   cveIdSolcitud" + solicitud.getSolicitudId(), e);
			throw new GestionPatronalBusinessException("Error no especifico en concluirTramitesDeAlta " + e.getMessage());
		}
	}
	
	
	private void concluirTramiteAltaPersonaAutorizada(Tramite tramite, Long idSolicitud) throws GestionPatronalBusinessException{
		TramiteSujetoObligado tramiteAltaSRT = (TramiteSujetoObligado) tramite;
		SujetoObligado so = tramiteAltaSRT.getSujetoObligado();
		Persona persona =so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)?so.getFisica():so.getMoral();
		if(tramiteAltaSRT.getSujetoObligado().getPersonasAutorizadas()!=null 
				&&tramiteAltaSRT.getSujetoObligado().getPersonasAutorizadas().size()>0){
			
			for(PersonaAutorizada pa:tramiteAltaSRT.getSujetoObligado().getPersonasAutorizadas()){
			
				TramitePersonaAutorizada tpa=creaTramitePersonaAutorizada(pa, persona);
				personasAutorizadasService.afectarTramiteAltaPersonaAutorizada(tpa, idSolicitud);
				solicitudService.agregarTramiteASolicitud(idSolicitud, tpa);
			}
//			personasAutorizadasService.agregarPersonasAutorizadas(
//					tramiteAltaSRT.getSujetoObligado().getPersonasAutorizadas());
			
		}
		
		
	}
	
	private TramitePersonaAutorizada creaTramitePersonaAutorizada(PersonaAutorizada pa, Persona persona){
		TramitePersonaAutorizada tpa= new TramitePersonaAutorizada();
		Date fechaTramite = Calendar.getInstance().getTime();
		List<SujetoObligado> rps = new ArrayList<SujetoObligado>();
		rps.add(pa.getSujetoObligado());
		tpa.setSujetosObligados(rps);
		tpa.setPersonaAutorizada(pa.getFisica());
		tpa.setEstadoTramite(new EstadoTramite());
		tpa.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getValor());
		tpa.setTipoTramite(new TipoTramite());
		tpa.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.PERSONAS_AUTORIZADAS.getCodigo());
		tpa.setFechaPresentacion(fechaTramite);
		tpa.setFechaConclusion(fechaTramite);
		tpa.setFechaTramite(fechaTramite);
		if(persona instanceof Fisica)
			tpa.setPersonaFisica((Fisica)persona);
		else
			tpa.setPersonaMoral((Moral)persona);
		return tpa;
	}
	
	private Tramite concluirTramiteActualizacionDatosGenerales(Tramite tramite) throws GestionPatronalBusinessException{
		if(tramite instanceof TramiteFisica){
			TramiteFisica tf = (TramiteFisica)tramite;
			ICADatosRespuesta datosICA = tf.getDatosICA();
			datosICA = personaFisicaServiceBusiness.integrarCambios(datosICA);
			tf.setDatosICA(datosICA);
			tramite = tf;
		}else if(tramite instanceof TramiteMoral){
			TramiteMoral tm = (TramiteMoral)tramite;
			ICADatosRespuesta datosICA = tm.getDatosICA();
			datosICA = personaMoralServiceBusiness.integrarCambios(datosICA);
			tm.setDatosICA(datosICA);
			tramite=tm;
		}else{
			throw new GestionPatronalBusinessException("El tramite de datos generales no es de tipo Tramite Fisica o Moral");
		}
		
//		boolean notificar = evaluarNotificacionSINDO(tramite);
		
		sujetoObligadoService.actualizarDenominacionRazonSocial(tramite, null, true);
		
		return tramite;
	}
	
	/**
	 * Se concluye el tr�mite de alta afectando los datos del registro patronal y asociando el tr�mite al nuevo RP.
	 * Adem�s se agrega el beneficio RISS por defecto al nuevo rp en caso de que tenga este beneficio vigente.
	 * Como paso final se actualiza la informaci�n del certificado digital del firmante (RepresentanteLegal o Patr�n)
	 * @param tramite
	 * @param numeroRegistroPatronal
	 * @param firma
	 * @return Tramite con el nuevo NRP
	 * @throws GestionPatronalBusinessException
	 */
	@Override
	public Tramite concluirTramiteAltaPatronal(Tramite tramite, String numeroRegistroPatronal, 
			Certificado certificado, OrigenSolicitud origenSolicitud) throws GestionPatronalBusinessException{

		try {
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			SujetoObligado so = agregarNumeroRegistroPatronalAlTramite(tso.getSujetoObligado(), numeroRegistroPatronal);
			RegistroPatronal registroPatronal = agregarPatronBasico(so,tso.getFechaEfecto());
			super.log.error("::::Se inserto el registro patronal basico::::");
			super.log.error("Patron Sujeto Obligado Id: "+registroPatronal.getIdRegistroPatronal());
			super.log.error("Clasificacion Id: "+registroPatronal.getClasificacion().getId());
			super.log.error("Patron General Id: "+registroPatronal.getId());
			super.log.error("Fecha Mov: "+tso.getFechaEfecto());
			
			so = agregarIdentificadoresAlTramite(registroPatronal, so);

			tso.setSujetoObligado(so);


			afiliacionService.actualizarCentroTrabajo(tso.getSujetoObligado(), tso, null, false);
			log.debug("ALTAPATRONALOSB pase afiliacionService.actualizarCentroTrabajo nrp" + numeroRegistroPatronal);
			clasificacionService.afectarTramiteModificacionPatronal(tso.getSujetoObligado(), tramite);
			log.debug("ALTAPATRONALOSB pase clasificacionService.afectarTramiteModificacionPatronal nrp" + numeroRegistroPatronal);

			super.log.error("Agregando representantes legales: "+tso.getSujetoObligado().getRepresentantesLegales());
			//		afiliacionService.asociarRepresentantesLegalesANuevoRegistroPatronal(tso.getSujetoObligado());
			solicitudBusinessService.asociarTramiteAltaARegistroPatronal(tramite, registroPatronal.getIdRegistroPatronal());
			log.debug("ALTAPATRONALOSB pase solicitudBusinessService.asociarTramiteAltaARegistroPatronal nrp" + numeroRegistroPatronal);
			//Verificar si se agrega al beneficio RISS
			if(!registroPatronal.getModalidad().getNumModalidad().equals(ModalidadEnum.TREINTAYCUATRO.getNumModalidad())){
				
				try {
					beneficioRissServiceBusiness.heredarBeneficioAltaPatronal(so, origenSolicitud.getIdTipoSolicitud());
					log.debug("ALTAPATRONALOSB pase beneficioRissServiceBusiness.heredarBeneficioAltaPatronal nrp" + numeroRegistroPatronal);
				} catch (Exception e) {
					log.error("ALTAPATRONALOSB.ERROR ocurrio un error en el metodo beneficioRissServiceBusiness.heredarBeneficioAltaPatronal nrp" + numeroRegistroPatronal, e);
					throw new GestionPatronalBusinessException(e.getMessage());
				}
				
			}	

			if(origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId())) {
				actualizarDatosFielFirmante(so, certificado);
				log.debug("ALTAPATRONALOSB pase actualizarDatosFielFirmante nrp" + numeroRegistroPatronal);
			}

			return tso;
		}catch (GestionPatronalBusinessException e) {
			throw e;
		}catch(Exception e) {
			log.error("ALTAPATRONALOSB.ERROR ocurrio un error en el metodo concluirTramiteAltaPatronal nrp" + numeroRegistroPatronal, e);
			throw new GestionPatronalBusinessException("Ocurrio un error en el metodo concluirTramiteAltaPatronal" + e.getMessage());
		}
	}
	
	
	
	
	
	
	private SujetoObligado agregarNumeroRegistroPatronalAlTramite(SujetoObligado sujetoTramite, String numeroRegistroPatronal){
		super.log.error("Separando registro patronal en modalidad y digito verificador: "+numeroRegistroPatronal);
		String nrp = numeroRegistroPatronal.substring(0, 8);
		super.log.error("NRP: "+nrp);
		String numModalidad = numeroRegistroPatronal.substring(8, 10);
		super.log.error("Modalidad: "+numModalidad);
		String digVer = numeroRegistroPatronal.substring(10);
		super.log.error("Digito: "+digVer);
		Modalidad modalidad = registroPatronalEntity.obtenerModalidadPorClave(numModalidad);
		sujetoTramite.setNumeroRegistroPatronal(nrp);
		sujetoTramite.setModalidad(modalidad);
		sujetoTramite.setDigVerificador(digVer);
		return sujetoTramite;
	}
	
	/**
	 * Inserta en base de datos un registro b�sico, es decir con informaci�n m�nima en las siguientes tablas:
	 * DitPatronSujetoObligado
	 * DitPatronGeneral
	 * DitClasificacion
	 * @param so SujetoObligado con la informaci&oacute;n del tr&aacute;mite
	 */
	private RegistroPatronal agregarPatronBasico(SujetoObligado so, Date fecMov)throws GestionPatronalBusinessException{
		super.log.error("Insertando registro Patronal b�sico");
		RegistroPatronal registroPatronal = generarRegistroPatronalBasico(so);
		registroPatronal.setFechaMovimiento(fecMov);
		try {
			return registroPatronalEntity.registrarRegistroPatronal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		}
	}
	
	private RegistroPatronal generarRegistroPatronalBasico(SujetoObligado so){
		super.log.error("Generando registro patronal b�sico");
		RegistroPatronal registroPatronal = new RegistroPatronal();
		registroPatronal.setClasificacion(so.getClasificacion());
		registroPatronal.setModalidad(so.getModalidad());
		registroPatronal.setDigVerificador(so.getDigVerificador());
		Long idPatron = null;
		TipoPersona tipoPersona = new TipoPersona();
		if(so.getTipoPersonaFiscal().getCodigo().equals(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona.FISICA)){
			idPatron = so.getFisica().getIdPersona();
			tipoPersona.setIdTipoPersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona.FISICA.longValue());
		}else{
			idPatron = so.getMoral().getIdPersona();
			tipoPersona.setIdTipoPersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona.MORAL.longValue());
		}
		registroPatronal.setIdPatron(idPatron);
		registroPatronal.setNoRegPatronal(so.getNumeroRegistroPatronal());
		
		registroPatronal.setTipoPersona(tipoPersona);
		registroPatronal.setNombreComercial(so.getNombreComercial());
		registroPatronal.setMunicipioIMSS(so.getMunicipioIMSS());
		return registroPatronal;
	}
		
	private SujetoObligado agregarIdentificadoresAlTramite(RegistroPatronal rp, SujetoObligado sujetoTramite){
		sujetoTramite.setCveIdSujetoObligado(rp.getIdRegistroPatronal());
		sujetoTramite.getCntroTrabajo().setCveIdPatronSujetoObligado(rp.getIdRegistroPatronal());
		sujetoTramite.getClasificacion().setId(rp.getClasificacion().getId());
		sujetoTramite = agregarIdRegistroPatronalAClasificacion(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAProductos(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAMateriaPrima(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAMaquinaria(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAEquipoTransporte(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAPersonal(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalABienes(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAProceso(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalARepresentantesLegal(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAPersonasAutorizadas(sujetoTramite);
		
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalARepresentantesLegal(SujetoObligado sujetoTramite){
		if(sujetoTramite.getRepresentantesLegales()==null)
			return sujetoTramite;
		List<RepresentanteLegal> listaFinalRepresentantes = new ArrayList<RepresentanteLegal>();
		for(RepresentanteLegal rl:sujetoTramite.getRepresentantesLegales()){
			rl.setCveIdPatronSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinalRepresentantes.add(rl);
		}
		sujetoTramite.setRepresentantesLegales(listaFinalRepresentantes);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAPersonasAutorizadas(SujetoObligado sujetoTramite){
		if(sujetoTramite.getPersonasAutorizadas()==null)
			return sujetoTramite;
		List<PersonaAutorizada> listaPersonasAutorizadas = new ArrayList<PersonaAutorizada>();
		for(PersonaAutorizada pa:sujetoTramite.getPersonasAutorizadas()){
			SujetoObligado so = new SujetoObligado();
			so.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			pa.setSujetoObligado(so);
			listaPersonasAutorizadas.add(pa);
		}
		sujetoTramite.setPersonasAutorizadas(listaPersonasAutorizadas);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAClasificacion(SujetoObligado sujetoTramite){
		sujetoTramite.getClasificacion().setSujetoObligado(new SujetoObligado());
		sujetoTramite.getClasificacion().getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAProductos(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a productos: "+sujetoTramite.getCveIdSujetoObligado());
		List<Producto> listaFinalProducto = new ArrayList<Producto>();
		
		for(Producto producto : sujetoTramite.getProductos()){
			producto.setSujetoObligado(new SujetoObligado());
			producto.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinalProducto.add(producto);
		}
		sujetoTramite.setProductos(listaFinalProducto);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAMateriaPrima(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a materias: "+sujetoTramite.getCveIdSujetoObligado());
		List<MateriaPrima> listaFinal = new ArrayList<MateriaPrima>();
		
		for(MateriaPrima model : sujetoTramite.getMateriaPrimaMateriales()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setMateriaPrimaMateriales(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAMaquinaria(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a maquinaria: "+sujetoTramite.getCveIdSujetoObligado());
		List<MaquinariaEquipo> listaFinal = new ArrayList<MaquinariaEquipo>();
		
		for(MaquinariaEquipo model : sujetoTramite.getEquipos()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setEquipos(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAEquipoTransporte(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a equipo transporte: "+sujetoTramite.getCveIdSujetoObligado());
		List<EquipoTransporte> listaFinal = new ArrayList<EquipoTransporte>();
		
		for(EquipoTransporte model : sujetoTramite.getEquiposTransporte()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setEquiposTransporte(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAPersonal(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a personal: "+sujetoTramite.getCveIdSujetoObligado());
		List<Personal> listaFinal = new ArrayList<Personal>();
		
		for(Personal model : sujetoTramite.getPersonal()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setPersonal(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalABienes(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a bienes: "+sujetoTramite.getCveIdSujetoObligado());
		List<Bien> listaFinal = new ArrayList<Bien>();
		
		for(Bien model : sujetoTramite.getBienes()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setBienes(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAProceso(SujetoObligado sujetoTramite){
		sujetoTramite.getProceso().setSujetoObligado(new SujetoObligado());
		sujetoTramite.getProceso().getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
		return sujetoTramite;
	}
	
	@Override
	public void concluirSolicitudDatosPatronales(Long idSolicitud)
			throws GestionPatronalBusinessException {
		afiliacionService.concluirSolicitudAfiliacion(idSolicitud);
	}

	@Override
	public void recuperarInformacionPatronalPorNumeroDeRegistro(
			String numeroRegistroPatronal) {
		SujetoObligado rp = new SujetoObligado();
		rp.setNumeroRegistroPatronal(numeroRegistroPatronal);
		rp=afiliacionService.obtenerDetalleDeRegistroPatronal(rp);
		
	}	
	
	private void actualizarDatosFielFirmante(SujetoObligado so, Certificado certificado){
		Persona persona=null;
		List<RepresentanteLegal> representantes = so.getRepresentantesLegales();
		if(representantes!=null && representantes.size()>0){
			RepresentanteLegal rl = representantes.get(0);
			persona = rl.getPersonaFisica();
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		}else if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			persona=so.getFisica();
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		}
		persona=agregarDatosFielFirmante(persona, certificado);
		personaBusinessService.registrarDatosCertificadoPersona(persona);
		
	}
	
	private Persona agregarDatosFielFirmante(Persona persona, Certificado certificado){
		Fiel fiel = new Fiel();
		fiel.setClaveSerial(certificado.getClaveSerial());
		fiel.setFechaValidaInicio(certificado.getFechaValidaInicio());
		fiel.setFechaValidaFin(certificado.getFechaValidaFin());
		persona.setFiel(fiel);
		
		return persona;
	}
	
	private void agregarTramiteAltaRelacionada(Long idSolicitud, Tramite tramite){
		solicitudService.agregarTramiteASolicitud(idSolicitud, tramite);
	}
	
	private TramiteSujetoObligado creaTramiteAltaPatronal(SujetoObligado sujetoTramite){
		TramiteSujetoObligado tramiteAlta = new TramiteSujetoObligado();
		tramiteAlta.setTipoTramite(new TipoTramite());
		//TODO Revisar si es Alta Patronal Persona Fisica. Si se utiliza para PMse debe condicionar para asignar
		//otro de tramite
		tramiteAlta.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ALTA_SRT.getCodigo());
		
		tramiteAlta.setSujetoObligado(sujetoTramite);
		tramiteAlta.setFechaTramite(Calendar.getInstance().getTime());
		tramiteAlta.setFechaPresentacion(Calendar.getInstance().getTime());
		tramiteAlta.setEstadoTramite(new EstadoTramite());
		tramiteAlta.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getValor());
		log.debug("Terminando de crear el objeto solicitud alta");
		return tramiteAlta;
		
		
	}
}
