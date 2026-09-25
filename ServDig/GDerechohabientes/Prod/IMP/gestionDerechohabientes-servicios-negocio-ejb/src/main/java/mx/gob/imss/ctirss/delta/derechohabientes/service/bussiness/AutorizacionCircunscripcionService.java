package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.MedicoEnTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.CircunscripcionEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AutorizacionCircunscripcionServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Stateless(name = "autorizacionCircunscripcionService", mappedName = "autorizacionCircunscripcionService")
public class AutorizacionCircunscripcionService extends AbstractServiceBusiness
		implements AutorizacionCircunscripcionServiceRemote {

	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB
	private MedicoEnTurnoDaoLocal medicoEnTurnoDaoLocal;
	@EJB
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB
	private CambioClinicaServiceLocal cambioClinicaServiceLocal;
	@EJB
	private CambioMedicoServiceLocal cambioMedicoServiceLocal;
	@EJB
	private CircunscripcionEntityLocal circunscripcionEntityLocal;
	@EJB
	private TramiteDocumentosServiceLocal tramiteDocumentosServiceLocal;
	@EJB
	private CatalogosDaoLocal catalogosDaoLocal;
	
	@EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	
	
	/**
	 * Se crea el tramite de circunscripcion foranea
	 * @param Long - idDerechohabiente de la persona a la que se le va a hacer el tramite
	 * @param GrupoFamiliar - afectado , puede venir nulo en caso de ser asi se onsultara por el id del derechohabiente
	 * @param Usuario - usuario que esta realizando el tramite
	 * @param AsignaiconNSS - asignacion nss
	 * @return Solicitud - la solicitud creada
	 */
	@Override
	public Solicitud saveCircunscripcionAutorizacionDerechohabiente(
			Long idDerechohabiente, GrupoFamiliar afectado, Usuario usuario,
			AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		
		//verificamos si el afectado no viene nulo o si no concuerda con el id de la persona
		if(afectado == null || !afectado.getDerechohabiente().getIdPersona().equals(idDerechohabiente)) {
			try {
				afectado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), idDerechohabiente);
			} catch (Exception e1) {
				log.error("Error al recuperar al integrante afectado", e1);
				DerechohabientesBusinessException.throwException("error.busqueda.integrante", e1.getCause().getMessage());
			}
		}

		//si el afectado no fue encontrado lanzamos una excepcion
		if (afectado == null) {
			DerechohabientesBusinessException.throwException("No se encontro al integrante del grupo familiar","No se encontro al integrante del grupo familiar");
		}
		
		// parseamos lo necesario a la entidad de circunscripcion
		TramiteCircunscripcionForanea circunscripcion = new TramiteCircunscripcionForanea();
		// El domicilio de origen sera el que se tiene guardado en grupo familiar
		circunscripcion.setDomicilioOrigen(afectado.getDomicilio());
		// establecemos el medico en turno origen que es el que tienen el derechohabiente
		circunscripcion.setMedicoEnTurnoOrigen(afectado.getMedicoEnTurno());
		//obtenemos las descripciones del medico en turno destino
		MedicoEnTurno medicoEnTurnoDestino = medicoEnTurnoDaoLocal.getMedicoEnTurnoById(correccion.getMedicoEnTurno().getIdMedicoContultorioTurno());
		// Colocamos el medico en turno que sea el que trae la correccion
		circunscripcion.setMedicoEnTurnoDestino(medicoEnTurnoDestino);
		// colocamos el id de la persona
		circunscripcion.setPersona(afectado.getDerechohabiente());
		// colocmos el tipo del tramite
		circunscripcion.setTipoTramite(new TipoTramite());
		circunscripcion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo());
		circunscripcion.setObservacion(correccion.getObservacion());
		//Se setea el domicilio destino
		circunscripcion.setDomicilioDestino(correccion.getDomicilio());
		// y guardamos la circunscripcion
		// establecemos la fecha de incicio
		circunscripcion.setFecInicioCircunscripcion(new Date());
		// establecemos el indicador de la circunscripcion
		circunscripcion.setIndCircunscripcionActiva(0);

		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitud = null;
		
		//Se crea la solicitud de circunscripcion foranea
		try {
			solicitud = tramiteServiceLocal.guardarCircunscripcion(
							afectado,TipoTramiteEnum.AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA,
							usuario, nss, circunscripcion, origen);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("Error al guardar la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		return solicitud;
	}

	/**
	 * Metodo para obtener a los candidatos para cambio de circunscripcion
	 * @param nss El nss del grupo Familiar
	 * @param perfil del usurio actual
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarCircunscripcion(AsignacionNSS nss, CabezaGrupoFamiliar cabeza, Usuario usuario) throws DerechohabientesBusinessException {
		List<GrupoFamiliar> candidatos = null;
		Boolean patronIMSS = false;
		
		try {
			// buscamos a los miembros del grupo familiar del asegurado
			candidatos = grupoFamiliarDaoLocal.findGrupoFamiliarCircunscripcion(nss,EstadoDerechohabienteEnum.VIGENTE, true, true);
		} catch (Exception e) {
			log.error("No se pudieron recuperar candidatos", e);
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION, e.getCause().getMessage());
		}
		
		if (candidatos != null && !candidatos.isEmpty()) {
			patronIMSS = cabeza.getPatronImss() != null ? cabeza.getPatronImss().equals(1) : false;
			
			candidatos = this.quitarPadresConcubina(candidatos, patronIMSS, cabeza);
			
			// quitamos a los que no esten en la misma umf que el tramitador
			// candidatos = this.filtrarPorUmf(candidatos, usuario.getIdUmf());
			// Quitamos los candidatos dados de baja de la lista
			candidatos = this.quitarAsegurado(candidatos);
		}
		// Si es nula mandamos una excepcion indicando que no hay informacion
		// para mostrar
		else {
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION,"No se encontraron candidatos");
		}

		// Si la lista no es nula pero esta vacia despues de los filtros
		// mandamos un exception indicando que no hay informacion para mostrar
		if (candidatos.size() == 0) {
			throw new DerechohabientesBusinessException(ExceptionMessages.SIN_INFORMACION,"No se encontraron candidatos");
		}

		return candidatos;
	}
	
	
	@Override
	public Solicitud finalizaSolicitudCircunscripcion(Solicitud solicitud,
			AsignacionNSS nss, GrupoFamiliar afectado, Map<String, Object> validacionesCambioMedico) throws DerechohabientesBusinessException {
		
		if(solicitud != null && solicitud.getSolicitudId() != null) {
			
			TramiteCircunscripcionForanea tramiteCircunscripcionForanea = TramiteUtil.getCircunscrcipcionFromSolicitud(solicitud);
			//se genrera la firma digital
			try {
				TipoTramite tipoTramiteModel = catalogosDaoLocal.getTipoTramite(tramiteCircunscripcionForanea.getTipoTramite().getIdTipoTramite().longValue());
				tramiteCircunscripcionForanea.setTipoTramite(tipoTramiteModel);
				tramiteDocumentosServiceLocal.generaFirmaElectronica(nss, solicitud, tipoTramiteModel.getDescripcion());
			} catch (DocumentoException e) {
				e.printStackTrace();
			}
			solicitud = this.guardaCambiosCircunscripcion(solicitud, nss, tramiteCircunscripcionForanea, afectado, validacionesCambioMedico);
			
			try {
				tramiteServiceLocal.actualizarSolicitudAConcluida(solicitud);
			} catch (SolicitudNoEncontradaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return solicitud;
	}

	/**
	 * Metodo para finalizar el tramite de circunscripcion
	 * @param solicitud
	 * @param nss
	 * @param circunscripcion
	 * @param afectado
	 * @param validacionesCambioMedico
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	private Solicitud guardaCambiosCircunscripcion(
			Solicitud solicitud, AsignacionNSS nss,
			TramiteCircunscripcionForanea circunscripcion,
			GrupoFamiliar afectado, Map<String, Object> validacionesCambioMedico) throws DerechohabientesBusinessException {

		Date fechaCambioMedicoYTurno = null;
		List<GrupoFamiliar> integrantesEnUmfDestino = null;
		Boolean isAsegurado = false;
		Boolean isConyuge = false;
		Boolean parentescoPermiteCambioMedicoEnClinicaDestino = false;
		Boolean existeCambioDeMedico = false;
		Domicilio domicilioNuevo = null;
		
		//obtenemos los datos del afectado
		if(afectado == null) {
			try {
				afectado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), circunscripcion.getPersona().getIdPersona());
			} catch (Exception e) {
				DerechohabientesBusinessException.throwException("No fue posible localizar al integrante afectado", "No fue posible localizar al integrante afectado");
			}
		}
		
		//Verificamos si viene algun tramite
		if(solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
			if(circunscripcion == null) {
				circunscripcion = TramiteUtil.getCircunscrcipcionFromSolicitud(solicitud);
			}
		} else { //si no vienen tramites consultaremos la solicitud
			if(circunscripcion == null) {
				try {
					solicitud = tramiteServiceLocal.consultarSolicitud(solicitud);
					circunscripcion = TramiteUtil.getCircunscrcipcionFromSolicitud(solicitud);
				} catch (SolicitudNoEncontradaException e) {
					e.printStackTrace();
					DerechohabientesBusinessException.throwException("No fue posible localizar la solicitud con el id solicitado", "No fue posible localizar la solicitud con el id solicitado");
				}
				
			}
		}
		
		if(circunscripcion == null) {
			throw new DerechohabientesBusinessException("Error en la busqueda de la solicitud","No se encontro la solicitud");
		}
		
		//obtenemos el domicilio destino
		domicilioNuevo = circunscripcion.getDomicilioDestino();
		//Verificamos si no esta guardado
		if(domicilioNuevo.getClave() == null) {
			//guardamos el nuevo domicilio
			try {
				domicilioNuevo = domicilioServiceBusinessRemote.registrarDomicilio(domicilioNuevo);
				circunscripcion.setDomicilioDestino(domicilioNuevo);
			} catch (DomicilioNoValidoException e) {
				log.error("No fue posible guardar el nuevo domicilio", e);
				DerechohabientesBusinessException.throwException(e.getSituacion(), e.getSituacion());
			} catch (Exception e) {
				log.error("No fue posible guardar el nuevo domicilio", e);
				DerechohabientesBusinessException.throwException("No fue posible guardar el domicilio","No fue posible guardar el domicilio");
			}
		}
		
		//Verificamos el parebtesco del afectadp
		isAsegurado = TramiteUtil.isAsegurado(afectado);
		isConyuge = TramiteUtil.isConyuge(afectado);
		//se verifica si es asegurado o conyuge ya que son los unicos parsntescos que permiten afectar a otras personas
		parentescoPermiteCambioMedicoEnClinicaDestino = isAsegurado || isConyuge;
		
		if(validacionesCambioMedico == null) {
			List<Long> idsPersonasExclusionConsulta = new ArrayList<Long>();
			idsPersonasExclusionConsulta.add(afectado.getDerechohabiente().getIdPersona());
			//Realizamos las validaciones de fecha de cambio
			validacionesCambioMedico = cambioClinicaServiceLocal.getFechaCambioYDatosCambioMedico(nss, 
					circunscripcion.getMedicoEnTurnoDestino(), idsPersonasExclusionConsulta, false, fechaCambioMedicoYTurno, parentescoPermiteCambioMedicoEnClinicaDestino);
		}
		//obtenemos la fecha de cambio de medico y turno de las validaciones 
		fechaCambioMedicoYTurno = (Date) validacionesCambioMedico.get(TramiteUtil.KEY_FECHA_CAMBIO);
		//Verificamos si el tramite ocasiona un cambio de medico consultorio y turno
		existeCambioDeMedico = (Boolean) validacionesCambioMedico.get(TramiteUtil.KEY_EXISTE_CAMBIO_MEDICO);
		//obtenemos a los integrantes en la umf destino
		integrantesEnUmfDestino = (List<GrupoFamiliar>) validacionesCambioMedico.get(TramiteUtil.KEY_INTEGRANTES_EN_UMF);
		// ahora guardamos los nuevos datos de domicilio y umf donde
		// corresponden
		afectado.setDomicilio(circunscripcion.getDomicilioDestino());
		afectado.setMedicoEnTurno(circunscripcion.getMedicoEnTurnoDestino());
		// actualizamos la fecha del cambio de medico
		afectado.setFechaCambioTurnoMedico(fechaCambioMedicoYTurno);
		//seteamos la fecha de actualizacion
		afectado.setFechaRegistroActualizacion(new Date());
		
		try {
			grupoFamiliarDaoLocal.updateIntegrante(afectado);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No pudo actualizar el integrante", e);
			throw new DerechohabientesBusinessException(
					"error.actualizar.integrante", e.getCause().getMessage());
		}
		
		circunscripcion.setFechaTramite(new Date());
		circunscripcion.setFechaPresentacion(new Date());
		circunscripcion.setFecInicioCircunscripcion(new Date());
		circunscripcion.setIndCircunscripcionActiva(1);
		
		try {
			log.debug("Se actualiza el XML del tramite de circunscripcion");
			tramiteServiceLocal.actualizaXMLTramite(circunscripcion);
		} catch (TramiteNoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//guardamos la circunscripcion foranea
		try {
			log.debug("Se guarda la circunscripcion");
			circunscripcionEntityLocal.saveCircunscripcionForanea(circunscripcion);
		} catch (Exception e1) {
			log.error("Error al actualizar tramite", e1);
			DerechohabientesBusinessException.throwException("error.actualizar.tramite", e1.getCause().getMessage());
		}
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(circunscripcion);

		//validamos si habia integrantes en la umf destino y si es necesario hacer un cambio de medico
		if (integrantesEnUmfDestino != null && !integrantesEnUmfDestino.isEmpty() && parentescoPermiteCambioMedicoEnClinicaDestino && existeCambioDeMedico) {
			
			TramiteCorreccionDerechohabiente tramiteCambioMedico = new TramiteCorreccionDerechohabiente();
			//quitamos los datos de las personas anteriores
			tramiteCambioMedico.setMedicoEnTurno(circunscripcion.getMedicoEnTurnoDestino());
			tramiteCambioMedico.setNss(nss.getNssStr());
			tramiteCambioMedico.setIdAsignacionNss(nss.getIdAsignacionNSS());
			
			Map<String, Object> resulCambioMedico = cambioMedicoServiceLocal.crearTramiteCambioMedicoDependiente(null, integrantesEnUmfDestino, nss, false, 
					parentescoPermiteCambioMedicoEnClinicaDestino, existeCambioDeMedico, tramiteCambioMedico);
			
			if(resulCambioMedico != null) {
				tramiteCambioMedico = TramiteUtil.getTramiteCorreccionFromMap(resulCambioMedico);
				
				if(tramiteCambioMedico != null) {
					solicitud.getTramites().add(tramiteCambioMedico);
				}
			}
		}

		return solicitud;
	}
	/**
	 * 
	 * @param entrada
	 * @param patronImss
	 * @return
	 */
	private List<GrupoFamiliar> quitarPadresConcubina(
			List<GrupoFamiliar> entrada, boolean patronImss, CabezaGrupoFamiliar cabeza) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		for (GrupoFamiliar integrante : entrada) {
			//El Sistema valida regla de negocio [RNGD05082 Padres con Registro de Acuerdo del Consejo Consultivo]
			//Si el parentesco del integrante {grupoFamiliar.parentesco} dentro de su grupo familiar es “Padres”; 
			//su estado debe ser “Vigente / Baja (exceptuando baja por fallecimiento y Baja Administrativa)”
			//y debe de contar con un trámite: Registro de Acuerdo del Consejo Consultivo y con estatus de cerrado.
			if (integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())){
				if (catalogosDaoLocal.getCircunscripcion(integrante.getDerechohabiente().getAsignacionNSS().getIdPersona(),integrante.getDerechohabiente().getIdPersona())) {
					salida.add(integrante);
				} else {
					if (integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())
						&& (patronImss || 
						cabeza.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().longValue() == SubestadoDerechohabienteEnum.FALLECIMIENTO.getId())){
						salida.add(integrante);
					}
				}
			}else{
				salida.add(integrante);
			}
		}
		return salida;
	}
	
	/**
	 * Metodo para quitar el asegurado del grupo familiar
	 * 
	 * @param entrada
	 * @return
	 */
	private List<GrupoFamiliar> quitarAsegurado(List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		// Recorremos la lista de los integrantes del grupo familiar
		for (GrupoFamiliar integrante : entrada) {
			// Si su estado es baja o fallecido lo quitamos de la lista de
			// integrante
			if (integrante.getParentesco().getIdParentesco() != ParentescoEnum.ASEGURADO
					.getId()
					&& integrante.getParentesco().getIdParentesco() != ParentescoEnum.PENSIONADO
							.getId())
				salida.add(integrante);
		}

		return salida;
	}
}
