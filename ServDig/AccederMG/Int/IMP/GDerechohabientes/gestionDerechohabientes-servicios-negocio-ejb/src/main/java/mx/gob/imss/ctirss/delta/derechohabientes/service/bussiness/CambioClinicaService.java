package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.MedicoEnTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.AcuerdoDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.BajaDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.CircunscripcionEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.CorreccionDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.GrupoFamiliarUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "cambioClinicaService", mappedName = "cambioClinicaService")
public class CambioClinicaService extends AbstractServiceBusiness implements
		CambioClinicaServiceLocal, CambioClinicaServiceRemote {

	private final String MENSAJE_ERROR_AFECTADO = "Ocurrio un error al consultar al integrante afectado por el cambio de clinica";
	private final String MENSAJE_ERROR_CANDIDATOS = "No fue posible localizar a las personas relacionadas al tramite de correccion";
	private final String MENSAJE_ERROR_DOMICILIO = "No fue posible guardar el domicilio.";
	private final String MENSAJE_SOLICITUDES_CANCELADAS = "Solicitud Cancelada por cambio de clinica del asegurado";
	private final String KEY_INTEGRANTES_EN_UMF = "keyIntegrantesEnUmfDestino";
	private final String KEY_EXISTE_CAMBIO_MEDICO = "keyExisteCambioMedicoEnUmfDestino";
	private final String KEY_FECHA_CAMBIO = "keyFechaCambioMedicoTurno";
	
	@EJB
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name = "domicilioServiceBusiness", mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB
	private CambioMedicoServiceLocal cambioMedicoServiceLocal;
	@EJB
	private CorreccionDerechohabienteEntityLocal correccionDerechohabienteEntityLocal;
	@EJB
	private BajaDerechohabienteEntityLocal bajaDerechohabienteEntityLocal;
	@EJB
	private TramiteDocumentosServiceLocal tramiteDocumentosServiceLocal;
	@EJB
	private CatalogosDaoLocal catalogosDaoLocal;
	@EJB
	private FinalizaSolicitudServiceLocal finalizaSolicitudServiceLocal;
	@EJB
	private MedicoEnTurnoDaoLocal medicoEnTurnoDaoLocal;
	@EJB
	private CircunscripcionEntityLocal circunscripcionEntityLocal;
	@EJB
	private BajaDerechohabienteServiceLocal bajaDerechohabienteService;
	@EJB
	private AcuerdoDerechohabienteEntityLocal acuerdoDerechohabienteEntityLocal;
	
	/**
	 * Metodo para obtener a los candidatos para cambio de umf
	 * 
	 * @param nss
	 *            El nss del grupo Familiar
	 * @param perfil
	 *            del usurio actual
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarCambioUmf(AsignacionNSS nss,
			Usuario usuario, Boolean patronImss, Boolean origen)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> candidatos = null;
		GrupoFamiliar cabeza = null;
		// Obtenemos los miembros del grupo Familiar del perfil seleccionado
		try {
			if (usuario  != null && usuario.getPerfilUsuario().getIdPerfilUsuario().longValue() == PerfilesEnum.TRAMITADOR.getId().longValue()) {
				// Obtenemos a los miembros del grupo familiar del asegurado
				candidatos = grupoFamiliarDaoLocal.findGrupoFamiliarByNss(nss.getIdAsignacionNSS());
				cabeza = this.getCabezaGrupoFamiliar(candidatos);
				// Ya que el usuario es un tramitador filtramos a los candidatos
				// que pertenezacan a la misma umf
				if (origen) {
					log.debug("La clinica en donde se buscan los candidatos es en la origen");
					candidatos = this.filtrarPorUmf(candidatos,usuario.getIdUmf());
				} else {
					log.debug("La clinica en donde se buscan los candidatos es en la destino");
					candidatos = this.filtrarPorUmfDistinta(candidatos,usuario.getIdUmf());
					try {
						log.debug("Se quitaran las circunscripciones");
						candidatos = this.quitarCircunscripciones(candidatos);
					} catch (Exception e) {
						log.error(e);
					}
				}
				candidatos = this.quitarPadresConcubina(candidatos, patronImss, cabeza);

			} else {
				// Obtenemos a los miembros del grupo familiar del asegurado
				candidatos = grupoFamiliarDaoLocal.findGrupoFamiliarByNss(nss.getIdAsignacionNSS());
				cabeza = this.getCabezaGrupoFamiliar(candidatos);
				
				try {
					candidatos = this.quitarCircunscripciones(candidatos);
				} catch (Exception e) {
					log.error(e);
				}
				candidatos = this.quitarPadresConcubina(candidatos, patronImss, cabeza);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar andidatos", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION, e.getCause()
							.getMessage());
		}

		// Checamos que la lista sea diferente de nula
		if (candidatos != null) {
			log.debug("Los candidatos son: " + candidatos);
			log.debug("quitaremos a los candidatos en baja");
			// Quitamos los candidatos dados de baja de la lista
			candidatos = this.quitarBajasUMF(candidatos, patronImss);
		}
		// Si es nula mandamos una excepcion indicando que no hay informacion
		// para mostrar
		else {
			throw new DerechohabientesBusinessException(
					"No se encontraron candidatos",
					"No se encontraron candidatos");
		}
		// Si la lista no es nula pero esta vacia despues de los filtros
		// mandamos un exception indicando que no hay informacion para mostrar
		if (candidatos.size() == 0) {
			throw new DerechohabientesBusinessException(
					"No se encontraron candidatos",
					"No se encontraron candidatos");
		}

		candidatos = GrupoFamiliarUtil.ordenarPorCalidad(candidatos);
		return candidatos;
	}
	
	/**
	 * Metodo para crear una solicitud de cambio de clinica
	 * @param TramiteCorreccionDerechobabiente - el objeto que contiene la informaicon del tramite, debe contener medico en turno
	 * @param GrupoFamiliar - El asegurado o pensionado del grupo familiar
	 * @param AsignacionNSS - El nss del grupo familiar debe contar con el nss, el idasignacion y el id de la persona
	 * @param CabezaGrupoFamiliar - El objeto cabeza de gurpo familiar debe contener el indicador de patron imss
	 * @param Boolean - Indicador para saber si se consultar a los padrs y consubinas o se tomaran de la lista
	 * @param List<GrupoFamiliar> - Lista con los integrantes pares o concubinas a cambiar junto con el asegurado
	 * @param Usuario - El usuario que esta realizando la solicitud
	 * @param OrigenSolicitudEnum - El origen de la solicitud
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Solicitud crearSolicitudCambioClinica(
			TramiteCorreccionDerechohabiente correccion, GrupoFamiliar asegurado, AsignacionNSS nss,
			CabezaGrupoFamiliar cabeza, Boolean consultarPadres,
			List<GrupoFamiliar> padresConcubinas, Usuario usuario, OrigenSolicitudEnum origen) throws Exception {
		
		log.debug("Creo la solicitud de cambio de clinica, consultaPadres: " + consultarPadres + " con nss: " +nss.getIdAsignacionNSS() + ","+ nss.getNss());
		List<Long> idsPersonasACambiar = new ArrayList<Long>();
		List<Fisica> personasACambiar = new ArrayList<Fisica>();
		Boolean aseguradoCandidato = false;
		//Verificamos si viene una lista de candidatos
		if(correccion.getCandidatosCambioClinica() != null && !correccion.getCandidatosCambioClinica().isEmpty()) {
			
			for(Long idPersona: correccion.getCandidatosCambioClinica()) {
				if(nss.getIdPersona().equals(idPersona)) {
					aseguradoCandidato = true;
				}
				GrupoFamiliar integrante = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), idPersona);
				idsPersonasACambiar.add(integrante.getDerechohabiente().getIdPersona());
				personasACambiar.add(integrante.getDerechohabiente());
			}
		} else {
			//verificamos si solo viene el asegurado
			if(correccion.getIdPersona().equals(asegurado.getDerechohabiente().getIdPersona())) {
				aseguradoCandidato = true;
				idsPersonasACambiar.add(asegurado.getDerechohabiente().getIdPersona());
				personasACambiar.add(asegurado.getDerechohabiente());
			} else { //si no es el asegurado 
				if(correccion.getPersona() == null) {
					GrupoFamiliar integranteAfectado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), correccion.getIdPersona());
					idsPersonasACambiar.add(integranteAfectado.getDerechohabiente().getIdPersona());
					personasACambiar.add(integranteAfectado.getDerechohabiente());
				}
			}
		}
		
		if(aseguradoCandidato) {
			if(consultarPadres) {
				padresConcubinas = this.getPadresConcubinasParaCambio(nss, cabeza.getPatronImss());
			}
			
			if(padresConcubinas != null && !padresConcubinas.isEmpty()) {
				for(GrupoFamiliar pC : padresConcubinas) {
					idsPersonasACambiar.add(pC.getDerechohabiente().getIdPersona());
					personasACambiar.add(pC.getDerechohabiente());
				}
			}
		}

		correccion.setMedicoEnTurno(medicoEnTurnoDaoLocal.getMedicoEnTurnoById(correccion.getMedicoEnTurno().getIdMedicoContultorioTurno()));
		correccion.setIdPersona(null);
		correccion.setPersona(null);
		
		correccion.setCandidatosCambioClinica(idsPersonasACambiar);
		correccion.setPersonas(personasACambiar);
		
		Solicitud solicitud = null;
		// Guardamos la solicitud de correccion
		solicitud = tramiteServiceLocal.guardarSolicitudConInfoTramite(TipoTramiteEnum.CAMBIO_CLINICA, usuario, nss, 
				TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE, correccion, origen);
		// retornamos la solicitud
		return solicitud;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Solicitud finalizaSolicitudCambioClinica(Solicitud solicitud, AsignacionNSS nss, CabezaGrupoFamiliar cabeza, 
			Map<String, Object> validacionesCambioMedico) throws ImpactaAlmacenesWSException, DerechohabientesBusinessException {
		
		TramiteCorreccionDerechohabiente correccion = TramiteUtil.getTramiteCorreccionPorTipo(solicitud, TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());

        correccion.setIdAsignacionNss(nss.getIdAsignacionNSS());
		
		Long idSolicitud = solicitud.getSolicitudId();
		String folioSolicitud = solicitud.getNoFolioSolicitud();
        TipoSolicitud tipoSolicitud = solicitud.getTipoSolicitud();
        OrigenSolicitud origenSolicitud = solicitud.getOrigenSolicitud();
		Date fechaSolicitud = solicitud.getFechaSolicitud();
		List<GrupoFamiliar> integrantesMovimientos = null;
		Map<String, Object> result = null;
		
		try {
			TipoTramite tipoTramite = catalogosDaoLocal.getTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
			correccion.setTipoTramite(tipoTramite);

            FirmaElectronica firma = null;

			//solo si la firma no ha sido creada chedamos si ya esta
			try {
                firma = tramiteDocumentosServiceLocal.generaFirmaElectronica(nss, solicitud, tipoTramite.getDescripcion());
			} catch(Exception e) {
				log.error("ocurrio un error al generar la firma digital relacionada a la solicitud");
			}
			result =this.guardarFinalizadoTramiteCambioClinica(correccion, nss, cabeza.getPatronImss().equals(1),
					null, null, false, validacionesCambioMedico);
			
			solicitud = TramiteUtil.getSolicitudFromMap(result);
			integrantesMovimientos = TramiteUtil.getAfectadosFromMap(result);

            if(firma != null) {
                solicitud.setCadenaOriginal(firma.getCadenaOriginal());
                solicitud.setSecuenciaDeNotaria(firma.getSecuenciaNotaria());
                solicitud.setSelloDigital(firma.getRecibo());
                solicitud.setNumeroSerieCertificado(firma.getSerialCertificado());
                solicitud.setFirmaElectronica(firma);
            }

			solicitud.setSolicitudId(idSolicitud);
			solicitud.setNoFolioSolicitud(folioSolicitud);
            solicitud.setTipoSolicitud(tipoSolicitud);
            solicitud.setOrigenSolicitud(origenSolicitud);
			this.crearOactualizarTramite(solicitud);
			tramiteServiceLocal.actualizarSolicitudAConcluida(solicitud);

			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
			solicitud.setFechaConclusion(new Date());
			solicitud.setFechaSolicitud(fechaSolicitud);

			log.debug("Se envian los movimientos");
			finalizaSolicitudServiceLocal.mandaMovimientosWS(integrantesMovimientos, true);
			log.debug("Se termina de enviar los movimientos de cambio de clinica");
		} catch(IllegalArgumentException e) {
			log.error("Error en los argumentos del ws");
			DerechohabientesBusinessException.throwException("Datos incompletos para movimiento de vigencia", e.getMessage());
		} catch(ImpactaAlmacenesWSException e) {
			log.error("Ocurrio un error al mandar el movimiento del ws", e);
			throw e;
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al finalizar la solicitu", e);
			throw e;
		} catch(Exception e) {
			log.error("Ocurrio un error general al generarel cambio de clinica", e);
			DerechohabientesBusinessException.throwException("Datos incompletos para movimiento de vigencia", e.getMessage());
		}
		
		return solicitud;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Map<String, Object> agregarTramiteCambioClinicaDependiente(TramiteCorreccionDerechohabiente correccion, AsignacionNSS nss, Boolean patronImss, Date fechaCambioMTC,
			Long idSolicitud, GrupoFamiliar afectado, Boolean asignacionDomicilio, Map<String, Object> validacionesFechaCambioMedico)
			throws DerechohabientesBusinessException {
		Map<String, Object> result = null;
		//Se agrega el tipo de tramite
		correccion.setTipoTramite(new TipoTramite());
		correccion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
		
		
		result = this.guardarFinalizadoTramiteCambioClinica(correccion, nss, patronImss, 
				fechaCambioMTC,afectado,asignacionDomicilio, validacionesFechaCambioMedico);
		
		Solicitud solicitudCoreccion = TramiteUtil.getSolicitudFromMap(result);
		solicitudCoreccion.setSolicitudId(idSolicitud);
		solicitudCoreccion = this.crearOactualizarTramite(solicitudCoreccion);
		result.put(TramiteUtil.KEY_SOLICITUD, solicitudCoreccion);
		
	
		return result;
	}
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	private Solicitud crearOactualizarTramite(Solicitud solicitud) throws DerechohabientesBusinessException {
		
		List<Tramite> tramitesCreados = new ArrayList<Tramite>();
		
		//hasta que todo estï¿½ afectado creamos los tramites de la solicitud
		for(Tramite tramite: solicitud.getTramites()) {
			
			TramiteCorreccionDerechohabiente correccionAux = (TramiteCorreccionDerechohabiente) tramite;

			if(tramite.getTramiteId() == null) {
				correccionAux = (TramiteCorreccionDerechohabiente) tramiteServiceLocal.saveTramiteCorreccionDependiente(correccionAux, solicitud.getSolicitudId());
			} else {
				try {
				tramiteServiceLocal.actualizaXMLTramite(correccionAux);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
			try {
				//insertamos en ditCorreccion
				correccionDerechohabienteEntityLocal.saveCorreccionDerechohabiente(correccionAux);
			} catch (Exception e) {
				// Si ocurrio algun error mandamos una excepcion indicando que no se pudo guardar la solicitud
				e.printStackTrace();
				log.error("No fue posible guardar la solicitud", e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause().getMessage());
			}

			tramitesCreados.add(correccionAux);
		}
		
		solicitud.setTramites(tramitesCreados);
	
		return solicitud;
	}
	
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> guardarFinalizadoTramiteCambioClinica(
			TramiteCorreccionDerechohabiente correccion, AsignacionNSS nss,
			Boolean patronImss, Date fechaCambioMedicoYTurno, GrupoFamiliar integranteAfectado, Boolean asignacionDomicilio,
			Map<String, Object> validacionesFechaIntegrantesCambioMedico) throws DerechohabientesBusinessException {
		
		Map<String, Object> result = new HashMap<String, Object>(); 
		Solicitud solicitudConTramitesACrear = new Solicitud();
		GrupoFamiliar afectado = null;
		List<GrupoFamiliar> afectados = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> integrantesFinales = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> integrantesEnUmfDestino = new ArrayList<GrupoFamiliar>();
		Boolean isAsegurado = false;
		Boolean isConyuge = false;
		Boolean existeCambioDeMedico = false;
		Boolean parentescoPermiteCambioMedicoEnClinicaDestino = false;
		//las personas que no se buscaran en la umf origen para que no agregen doble vez
		List<Long> idsPersonasExclusionConsulta = new ArrayList<Long>();
		//Domicilio nuevo
		Domicilio domicilioNuevo = correccion.getDomicilio();
		
		List<Long> idPersonasCambiadas = correccion.getCandidatosCambioClinica();
		List<Fisica> personasCambiadas = correccion.getPersonas();

		Domicilio domicilioAnterior = null;
		MedicoEnTurno medicoAnterior = null;
		
		try {
			
			// Si la lista de candidatos es nula el tramite se aplico a una sola
			// persona y se obtiene el derechohabiente
			if (correccion.getCandidatosCambioClinica() == null) {
				
				//Se valida si la persona no viene
				if (integranteAfectado == null) {
					afectado = this.getAfectado(correccion);
				}  else {
					afectado = integranteAfectado;
				}
				
				idsPersonasExclusionConsulta.add(afectado.getDerechohabiente().getIdPersona());
				idPersonasCambiadas = new ArrayList<Long>();
				personasCambiadas = new ArrayList<Fisica>();
				
				idPersonasCambiadas.add(afectado.getDerechohabiente().getIdPersona());
				personasCambiadas.add(afectado.getDerechohabiente());
				
				afectados.add(integranteAfectado);
				
			} else {// De lo contrario se buscan a todas las personas afectadas por el tramite
				//Si no es asignacion de domicilio verificaremos cual de todos tiene mayor calidad 
				afectados = new ArrayList<GrupoFamiliar>();

				if(integranteAfectado != null) {
					afectados.add(integranteAfectado);
					idsPersonasExclusionConsulta.add(integranteAfectado.getDerechohabiente().getIdPersona());
				}
				for (Long idPersona : correccion.getCandidatosCambioClinica()) {
					idsPersonasExclusionConsulta.add(idPersona);
					if(integranteAfectado == null || !integranteAfectado.getDerechohabiente().getIdPersona().equals(idPersona)) {
						afectado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), idPersona);
						afectados.add(afectado);
					}
					
				}

				//sacamos al integrante con mayor calidad
				afectado = integranteAfectado == null? TramiteUtil.getIntegranteConMayorCalidad(afectados) : integranteAfectado;
			}
			
			//Se obtienen el parentesco de la persona afectada o de la persona con mayor calidad dentro de los candidatos
			isAsegurado = TramiteUtil.isAsegurado(afectado);
			isConyuge = TramiteUtil.isConyuge(afectado);
			//se verifica si es asegurado o conyuge ya que son los unicos parsntescos que permiten afectar a otras personas
			parentescoPermiteCambioMedicoEnClinicaDestino = isAsegurado || isConyuge;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al recuperar al integrante afectado", e);
			DerechohabientesBusinessException.throwException(this.MENSAJE_ERROR_AFECTADO, e.getCause().getMessage());
		}

		//si no se enviaron las validaciones de cambio de fecha medico y turno
		if(validacionesFechaIntegrantesCambioMedico == null) {
			//Realizamos las validaciones de fecha de cambio
			validacionesFechaIntegrantesCambioMedico = this.getFechaCambioYDatosCambioMedico(nss, 
					correccion.getMedicoEnTurno(), idsPersonasExclusionConsulta, asignacionDomicilio, fechaCambioMedicoYTurno, parentescoPermiteCambioMedicoEnClinicaDestino);
		}
		//obtenemos la fecha de cambio de medico y turno de las validaciones 
		fechaCambioMedicoYTurno = (Date) validacionesFechaIntegrantesCambioMedico.get(KEY_FECHA_CAMBIO);
		//Verificamos si el tramite ocasiona un cambio de medico consultorio y turno
		existeCambioDeMedico = (Boolean) validacionesFechaIntegrantesCambioMedico.get(KEY_EXISTE_CAMBIO_MEDICO);
		//obtenemos a los integrantes en la umf destino
		integrantesEnUmfDestino = (List<GrupoFamiliar>) validacionesFechaIntegrantesCambioMedico.get(KEY_INTEGRANTES_EN_UMF);
		
		//En este punto ya deberia de existir el afectado, ya que se ubico a la persona o al integrante con mayo calidar de la lista de personas
		if (afectado == null) {
			DerechohabientesBusinessException.throwException(this.MENSAJE_ERROR_CANDIDATOS,this.MENSAJE_ERROR_CANDIDATOS);
		}
		//guardamos el domicilio anterior
		domicilioAnterior = afectado.getDomicilio();
		//guardamos el medico en turno anterior
		medicoAnterior = afectado.getMedicoEnTurno();
		
		//Guardamos el domicilio en caso de que no se tenga
		if (domicilioNuevo.getClave() == null && StringUtils.isNotBlank(domicilioNuevo.getAsentamiento().getClave())) {
			try {
				// Si el domicilio cambio registramos el nuevo domicilio
				domicilioNuevo = domicilioServiceBusinessRemote.registrarDomicilio(correccion.getDomicilio());
				// y se lo agregamos a la correccion
				correccion.setDomicilio(domicilioNuevo);
			} catch (DomicilioNoValidoException e) {
				e.printStackTrace();
				throw new DerechohabientesBusinessException(this.MENSAJE_ERROR_DOMICILIO, this.MENSAJE_ERROR_DOMICILIO);
			} catch (Exception e) {
				e.printStackTrace();
				DerechohabientesBusinessException.throwException(this.MENSAJE_ERROR_DOMICILIO, this.MENSAJE_ERROR_DOMICILIO);
			}
		}
		
		log.debug("El parentesco es aseguraodo" + isAsegurado);
		log.debug("ID de la umf donde se buscaran a los hijos en baja " + correccion.getIdUmfOrigen());
		// Checamos si el parentesco es asegurado o pensionado para tambien en
		// caso de haber cambiado de domicilio cambiar a los padres
		if (isAsegurado && asignacionDomicilio && correccion.getIdUmfOrigen() != null) {
			
			//ubicamos a los integrantes que estan dados de baja
			List<GrupoFamiliar> integrantesEnBaja = null;
			
			//buscaramos a las personas que estan dadas de baja en la clinica donde esta el asegurado
			try {
				List<Long> estados = new ArrayList<Long>();
				estados.add(EstadoDerechohabienteEnum.BAJA.getId());
				
				List<Long> parentescos = new ArrayList<Long>();
				parentescos.add(ParentescoEnum.HIJOS.getId());
				
				integrantesEnBaja = grupoFamiliarDaoLocal.findIntegrantesPorUmfEstadoIntegrantes(nss.getIdAsignacionNSS(), correccion.getIdUmfOrigen(),
						estados, idsPersonasExclusionConsulta, parentescos,true);
			} catch (Exception e) {
				log.error("No fue posibles recuperar a las personas en baja que estan en la misma clinica que el asegurado",e);
			}

			if (integrantesEnBaja != null && !integrantesEnBaja.isEmpty()) {
				List<Long> idsPersonasBaja = new ArrayList<Long>();
				
				for (GrupoFamiliar integranteEnBaja : integrantesEnBaja) {
					idsPersonasBaja.add(integranteEnBaja.getDerechohabiente().getIdPersona());
					idPersonasCambiadas.add(integranteEnBaja.getDerechohabiente().getIdPersona());
					personasCambiadas.add(integranteEnBaja.getDerechohabiente());
					afectados.add(integranteEnBaja);
				}

				solicitudTramiteBusinessRemote.buscarYCancelarSolicitudesAbiertasPorPersonasYAsegurado(idsPersonasBaja, nss.getIdPersona(), this.MENSAJE_SOLICITUDES_CANCELADAS);
			}
			
			
		}
		
		//El XML se ira con na lista de personas
		correccion.setPersona(null);
		correccion.setIdPersona(null);
		correccion.setCandidatosCambioClinica(idPersonasCambiadas);
		correccion.setPersonas(personasCambiadas);
		
		MedicoEnTurno destino = medicoEnTurnoDaoLocal.getMedicoEnTurnoById(correccion.getMedicoEnTurno().getIdMedicoContultorioTurno());
		correccion.setMedicoEnTurno(destino);
		
		//en este momento el array de afectados ya contiene a todos, asegurado, bajas , padres, concubina
		//y se procede a actualizar a todos
		for (GrupoFamiliar integrante : afectados) {
			if(integrante.getEstadoDerechohabiente() == null || 
					!integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
				integrante.setDomicilio(correccion.getDomicilio());
			}
			integrante.setFechaRegistroActualizacion(new Date());
			integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
			integrante.setFechaCambioTurnoMedico(fechaCambioMedicoYTurno);
			
			integrantesFinales.add(integrante);
			// y guardamos los nuevos datos del domicilio del derechohabiente
			try {
				grupoFamiliarDaoLocal.updateIntegrante(integrante,true);
			} catch (Exception e) {
				log.error("No se pudo actualizar el integrante", e);
				throw new DerechohabientesBusinessException("error.actualizar.integrante", e.getCause().getMessage());
			}
		}
		
		solicitudConTramitesACrear.setTramites(new ArrayList<Tramite>());
		
		//validamos si habia integrantes en la umf destino y si es necesario hacer un cambio de medico
		if (integrantesEnUmfDestino != null && !integrantesEnUmfDestino.isEmpty() && parentescoPermiteCambioMedicoEnClinicaDestino && existeCambioDeMedico) {
			
			Map<String, Object> resulCambioMedico = cambioMedicoServiceLocal.crearTramiteCambioMedicoDependiente(null, integrantesEnUmfDestino, nss, asignacionDomicilio, 
					parentescoPermiteCambioMedicoEnClinicaDestino, existeCambioDeMedico, correccion);
			
			if(resulCambioMedico != null) {
				List<GrupoFamiliar> integrantesCambioMedico = TramiteUtil.getAfectadosFromMap(resulCambioMedico);
				TramiteCorreccionDerechohabiente tramiteCambioMedico = TramiteUtil.getTramiteCorreccionFromMap(resulCambioMedico);
				
				if(integrantesCambioMedico != null && tramiteCambioMedico != null) {
					integrantesFinales.addAll(integrantesCambioMedico);
					solicitudConTramitesACrear.getTramites().add(tramiteCambioMedico);
				}
			}
		}
		
		correccion.setMedicoEnTurnoNuevo(destino);
		correccion.setDomicilio(domicilioAnterior);
		correccion.setMedicoEnTurno(medicoAnterior);
	
		solicitudConTramitesACrear.getTramites().add(correccion);

		result.put(TramiteUtil.KEY_SOLICITUD, solicitudConTramitesACrear);
		result.put(TramiteUtil.KEY_AFECTADOS, integrantesFinales);

		return result;
	}
	
	
	
	@Override
	public Map<String,Object> getFechaCambioYDatosCambioMedico(AsignacionNSS nss, MedicoEnTurno medicoEnTurnoDestino, List<Long> idsPersonasExcluirDeConsulta, 
			Boolean asignacionDomicilio, Date fechaCambioMedicoTurno, Boolean isAseguradoConyuge) throws DerechohabientesBusinessException{
		
		Long idUmfBusqueda = TramiteUtil.getIdUmfFromMedicoEnTurno(medicoEnTurnoDestino);
		Boolean existeCambioMedico = false;
		List<GrupoFamiliar> integrantesEnUmf = null;
		
		
		if (fechaCambioMedicoTurno == null ) {
			
			log.debug("Se calculara la fecha de cambio de medico a partir de los integrantes que esten en la umf: " + idUmfBusqueda);
			
			try {
				
				
				if( idUmfBusqueda != null) {
					
					// ---------------------------------------------------------------------------------------------	
					// Se consultara a las personas que ya se encuentran en la umf destino y que esten vigentes
					// ---------------------------------------------------------------------------------------------
					List<Long> estados = new ArrayList<Long>();
					estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
					estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
					estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
					
					
					integrantesEnUmf = grupoFamiliarDaoLocal.findIntegrantesPorUmfEstadoIntegrantes(nss.getIdAsignacionNSS(),idUmfBusqueda,estados, idsPersonasExcluirDeConsulta, null,null);

					
					// -------------------------------------------------------------------------------------------------------
					// Si existen integrantes en la clï¿½nica destino, todos deben tener asignado el mismo mï¿½dico y turno.
					// Se verifica si se quiere cambiar el mï¿½dico asignado actualmente.
					// -------------------------------------------------------------------------------------------------------
					if ( integrantesEnUmf != null && !integrantesEnUmf.isEmpty()) {
						existeCambioMedico = !medicoEnTurnoDestino.getIdMedicoContultorioTurno().equals(integrantesEnUmf.get(0).getMedicoEnTurno()
								.getIdMedicoContultorioTurno());
						
						
						// --------------------------------------------------------------------------------------
						// Si alguno de los integrantes tiene asignada la fecha de cambio-medico, se obtiene
						// --------------------------------------------------------------------------------------
						if( !asignacionDomicilio )
							fechaCambioMedicoTurno = TramiteUtil.buscarFechaCambioMedicoEnLista(integrantesEnUmf);
						
					}
					
					
					
				} 
				
				
			} catch (Exception e1) {
				e1.printStackTrace();
				log.error("Error al recuperar al integrante afectado", e1);
				DerechohabientesBusinessException.throwException("error.busqueda.integrante", e1.getCause().getMessage());
			}
			
			
		} 
		
		
		
		return getMapFechasIntegrantes(fechaCambioMedicoTurno, existeCambioMedico, integrantesEnUmf);
		
		
	}
	
	
	
	@Override
	public Date getFechaCambioDeMedico(AsignacionNSS nss, MedicoEnTurno medicoEnTurnoDestino, List<Long> idsPersonasExcluirDeConsulta, Boolean asignacionDomicilio, Date fechaCambioMedicoTurno, Boolean isAseguradoConyuge) throws DerechohabientesBusinessException{
		
		Map<String, Object> result = this.getFechaCambioYDatosCambioMedico(nss, medicoEnTurnoDestino, idsPersonasExcluirDeConsulta, 
				asignacionDomicilio, fechaCambioMedicoTurno, isAseguradoConyuge);
		
		return (Date) result.get(KEY_FECHA_CAMBIO);
		
	}
	
	/**
	 * Obtiene un map con los datos para saber si hay cambio de medico
	 * @param fecha
	 * @param existeCambioMedico
	 * @param integrantesEnUmf
	 * @return
	 */
	private Map<String, Object> getMapFechasIntegrantes(Date fecha, Boolean existeCambioMedico, List<GrupoFamiliar> integrantesEnUmf) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put(KEY_EXISTE_CAMBIO_MEDICO, existeCambioMedico);
		result.put(KEY_FECHA_CAMBIO, fecha);
		result.put(KEY_INTEGRANTES_EN_UMF, integrantesEnUmf);
		
		return result;
	}
	
	/**
	 * Metodo para obtener a los integrantes del grupo que se van a mudar 
	 */
	@Override
	public List<GrupoFamiliar> getPadresConcubinasParaCambio(AsignacionNSS nss, Integer patronIMSS) throws Exception {

		Boolean patronImss = patronIMSS == null ? false : patronIMSS.equals(1);
		List<GrupoFamiliar> concubinasPadres = null;
		
		// --------------------------------------------------------------
		// Concubinas y Padres
		// --------------------------------------------------------------
		List<Long> parentescos = new ArrayList<Long>();
		parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
		parentescos.add(ParentescoEnum.PADRES.getId());
		parentescos.add(ParentescoEnum.ASEGURADO.getId());
		parentescos.add(ParentescoEnum.PENSIONADO.getId());
		
	
		// -----------------------------------------------------------
		// Padres y concubinas en BDTU, sin consultar ws vigencia 
		// -----------------------------------------------------------
		List<GrupoFamiliar> concubinasPadresAux = grupoFamiliarDaoLocal.findGrupoFamiliarPorParentescos(nss.getIdAsignacionNSS(), parentescos, true);
		
		GrupoFamiliar cabeza = getCabezaGrupoFamiliar(concubinasPadresAux);
		
			concubinasPadres = new ArrayList<GrupoFamiliar>();
	
			for(GrupoFamiliar integrante : concubinasPadresAux) {
				Long idParentesco = integrante.getParentesco().getIdParentesco();
				//si es concubina se agrega al candidato
				if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
					concubinasPadres.add(integrante);
				}else if(idParentesco.equals(ParentescoEnum.PADRES.getId()) && !patronImss){
					if (cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.FALLECIDO.getId()){
						if(!acuerdoDerechohabienteEntityLocal.tieneAcuerdoVigente(integrante.getAsignacionNSS().getIdAsignacionNSS(), integrante.getDerechohabiente().getIdPersona())){
							//si no es concubina y no es patron imss, se tiene que tener acuerdo
							concubinasPadres.add(integrante);
						}
					}
					
				}
					
			}
			

		
		// ---------------------------------------------------------
		// Quitamos los que tengan bajas activas de divorcio
		// termino de dependencia
		// ---------------------------------------------------------
		if (concubinasPadres != null && !concubinasPadres.isEmpty()) {
			concubinasPadres = this.quitarBajasDivorcioYTerminoDependencia(nss.getIdAsignacionNSS(), concubinasPadres);
		}
		
		
		return concubinasPadres;
	}
	
	/**
	 * Metodo para validar al afectado del cambio de clinica
	 * @param correccion
	 * @return
	 */
	private GrupoFamiliar getAfectado(TramiteCorreccionDerechohabiente correccion) {
		GrupoFamiliar afectado = null;
		if(correccion.getIdAsignacionNss() != null && correccion.getIdPersona() != null) {
			try {
				afectado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(correccion.getIdPersona(),correccion.getIdPersona());
				correccion.setPersona(afectado.getDerechohabiente());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Ocurrio un error al querer obtener al afectado por el tramite de correccion");
			}
			
		}
		
		return afectado;
	}

	/**
	 * Metodo para quitar de un grupo familiar a los integrantes dados de baja
	 * 
	 * @param idAsignacionNss Long
	 * @param entrada La lista a filtrar
	 * @return Lis<GrupoFamiliar> lista con los integrantes del grupo familiar en estado vigente 
	 */
	private List<GrupoFamiliar> quitarBajasDivorcioYTerminoDependencia(Long idAsignacionNss, List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		try{
			
		
			if( (entrada != null) && ( entrada.size() > 0 ) ){
				
				List<Long> idPersonas = new ArrayList<Long>();
				
				for(GrupoFamiliar integrante: entrada){ 
					idPersonas.add(integrante.getDerechohabiente().getIdPersona());
				}
				
				List<Long> tiposBaja = new ArrayList<Long>();
				tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
				tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
				
				// -----------------------------------------------------------------------
				// Obtenemos todas las personas con bajas activas
				// -----------------------------------------------------------------------
				List<BajaDerechohabienteDto> bajas = bajaDerechohabienteEntityLocal.getBajaDerechohabiente(idAsignacionNss, idPersonas, tiposBaja, true);
			
				
				if( bajas != null && !bajas.isEmpty()){
					
					// --------------------------------------------------
					// Si no tiene baja activa se agreaga a la respuesta
					// --------------------------------------------------
					for(GrupoFamiliar integrante: entrada) {
						if( !bajas.contains(integrante) ){
							salida.add(integrante);
						}
					}
					
				}else{
					
					// -------------------------------
					// Las personas no tienen bajas
					// -------------------------------
					salida = entrada;
				}
				
					
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return salida;
		
	}
	
	/**
	 * Metodo que quita los usuarios en baja que no sean hijos para el cambio de domicilio
	 * @param candidatos
	 * @return
	 */
	private List<GrupoFamiliar> quitarBajasUMF(List<GrupoFamiliar> candidatos, Boolean patronImss) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		Map<Long, GrupoFamiliar> lista = new HashMap<Long, GrupoFamiliar>();
		List<GrupoFamiliar> grupoBaja = new ArrayList<GrupoFamiliar>();
		//obtenemos al pensionado del grupo familiar
		GrupoFamiliar pensionado = this.getPensionado(candidatos);
		//si encontramos al pensionado lo ponemos en el mapa de candidatos
		if(pensionado != null) {
			lista.put(pensionado.getDerechohabiente().getIdPersona(), pensionado);
		}
		// Recorremos la lista de los integrantes del grupo familiar
		for (GrupoFamiliar integrante : candidatos) {
			// Si el estaod del integrante no es baja lo agregaremos a la lista
			if (integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.BAJA.getId()) {
				lista.put(integrante.getDerechohabiente().getIdPersona(), integrante);
			} else if(TramiteUtil.isHijo(integrante)) {
				grupoBaja.add(integrante);
			}
			
		}
		
		//si hay integrantes en baja validaremos que no sea por algun tramite que se le haya hecho
		if(grupoBaja != null && !grupoBaja.isEmpty()) {
			List<Long> tiposBaja = new ArrayList<Long>();
			tiposBaja.add(TipoBajaDerechohabienteEnum.DEFUNCION.getId());
			//obtenemos a los integrantes en baja sin un tramite de baja por defuncion
			grupoBaja = bajaDerechohabienteService.quitarIntegrantesConTramiteBaja(grupoBaja, tiposBaja);
			//si existen integrantes en baja sin ningun tramite de baja por defuncio lo anadimos a la lista
			if(grupoBaja != null && !grupoBaja.isEmpty()) {
				for(GrupoFamiliar grupoB: grupoBaja) {
					lista.put(grupoB.getDerechohabiente().getIdPersona(), grupoB);
				}
			}
		}
		//si la lista no esta vacia agregamos a los integrantes
		if(!lista.isEmpty()) {
			salida.addAll(lista.values());
		}
		
		return salida;
	}
	
	private List<GrupoFamiliar> quitarPadresConcubina(List<GrupoFamiliar> entrada, boolean patronImss, GrupoFamiliar cabeza) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		 
		for (GrupoFamiliar integrante : entrada) {
			Long idParentesco = integrante.getParentesco().getIdParentesco();
			if (!idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId()) && !idParentesco.equals(ParentescoEnum.PADRES.getId())) {
				salida.add(integrante);
			} else if (idParentesco.equals(ParentescoEnum.PADRES.getId()) && patronImss) {
				salida.add(integrante);
			} else if (idParentesco.equals(ParentescoEnum.PADRES.getId())  
					 && cabeza.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().longValue() ==SubestadoDerechohabienteEnum.FALLECIMIENTO.getId()) {
					salida.add(integrante);
			}else if (idParentesco.equals(ParentescoEnum.PADRES.getId()) && !patronImss) {
				log.debug("Buscara acuerdo para el padre : " + integrante.getAsignacionNSS().getIdAsignacionNSS() + ", "+ integrante.getDerechohabiente().getIdPersona());
				//si es padre y no se tiene patron IMSS verificamos que se tenga un acuerdo, si es asi, lo añadimos como candidato
				if(acuerdoDerechohabienteEntityLocal.getAcuerdoDerechohabiente(integrante.getAsignacionNSS().getIdAsignacionNSS(), 
						integrante.getDerechohabiente().getIdPersona(), 1L) != null) {
					salida.add(integrante);
				}
			}; 
		}
 
		return salida;
	}
	
	private List<GrupoFamiliar> filtrarPorUmf(List<GrupoFamiliar> integrantes,
			Long idUmf) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		for (GrupoFamiliar integrante : integrantes) {
			if (integrante.getMedicoEnTurno() != null) {
				if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
					if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
							.getIdUMF().longValue() == idUmf.longValue()) {
						salida.add(integrante);
					}
				}
			}
		}

		return salida;
	}

	private List<GrupoFamiliar> filtrarPorUmfDistinta(
			List<GrupoFamiliar> integrantes, Long idUmf) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		for (GrupoFamiliar integrante : integrantes) {
			if (integrante.getMedicoEnTurno() != null) {
				if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
					if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
							.getIdUMF().longValue() != idUmf.longValue()) {
						salida.add(integrante);
					}
				}
			}
		}

		return salida;
	}
	
	private List<GrupoFamiliar> quitarCircunscripciones(
			List<GrupoFamiliar> entrada)
			throws DerechohabientesBusinessException, Exception {

		List<GrupoFamiliar> candidatos = new ArrayList<GrupoFamiliar>();
		for (GrupoFamiliar integrante : entrada) {
			TramiteCircunscripcionForanea c = circunscripcionEntityLocal
					.getCircunscripcionForanea(integrante.getDerechohabiente()
							.getIdPersona(), integrante.getAsignacionNSS(),
							true);

			if (c == null) {
				log.debug("No se encontro circunscripcion para el integrante " + integrante.getDerechohabiente().getIdPersona());
				candidatos.add(integrante);
			} else {
				log.debug("Se encontro circunscripcion para el integrante " + integrante.getDerechohabiente().getIdPersona());
			}
		}
		return candidatos;

	}
	
	private GrupoFamiliar getPensionado(List<GrupoFamiliar> grupoFamiliar) {
		GrupoFamiliar pensionadoF = null;
		
		if(grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
			for(GrupoFamiliar integrante: grupoFamiliar) {
				if(integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())){
					pensionadoF = integrante;
				}
			}
		}
		return pensionadoF;
	}
	
	private GrupoFamiliar getCabezaGrupoFamiliar(List<GrupoFamiliar> grupoFamiliar) {
		GrupoFamiliar pensionadoF = null;
		
		if(grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
			for(GrupoFamiliar integrante: grupoFamiliar) {
				if(integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())||
						integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())){
					pensionadoF = integrante;
					break;
				}
			}
		}
		return pensionadoF;
	}
}
