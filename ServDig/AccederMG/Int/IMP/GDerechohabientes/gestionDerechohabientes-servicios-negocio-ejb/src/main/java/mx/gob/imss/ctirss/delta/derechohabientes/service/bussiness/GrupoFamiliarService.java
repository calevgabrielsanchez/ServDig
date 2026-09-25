package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NonUniqueResultException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.AseguradoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.AsignacionNssDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.PatronDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UsuarioDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.VigenciaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.PrestacionesEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.ws.bussiness.DerechohabienteWSClientRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ModServPresDerechohab;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Prestacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PrestacionPorModalidad;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ServicioPrestDerechohab;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.PrestacionesDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RegistroDto;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoInconsistenciaVigenciaEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.ServiciosPrestacionesEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPrestacionDerechoHabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.DetallePeriodoMovimientoAfiliatorioPatron;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteConsultaVigencia;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.vigenciaderechos.VigenciaDerechosWSClientRemote;

@Stateless( name = "grupoFamiliarService", mappedName = "grupoFamiliarService")
public class GrupoFamiliarService extends AbstractServiceBusiness implements GrupoFamiliarServiceRemote,GrupoFamiliarServiceLocal{
	
	@EJB GrupoFamiliarDaoLocal grupoFamiliarDao;
	@EJB AsignacionNssDaoLocal asignacionNssDao;
	@EJB TramitePersonaFisicaDaoLocal tramitePersonaFisicaDao;
	@EJB UsuarioDaoLocal usuarioDaoLocal;
	@EJB VigenciaDaoLocal vigenciaDaoLocal;
	@EJB AseguradoDaoLocal aseguradoDaoLocal;
	@EJB PatronDaoLocal patronDao;
	@EJB CatalogosDaoLocal catalogosDao; 
	@EJB ProrrogaDaoLocal prorrogaDao;
	@EJB PrestacionesEntityLocal prestacionesEntityLocal;
	@EJB TramitePersonaFisicaDaoLocal tramitePersonaFisicaDaoLocal;
	@EJB(name="derechohabienteWSClientService" ,mappedName="derechohabienteWSClientService") DerechohabienteWSClientRemote wsClient;//derechohabienteWSClientService
	@EJB(name="sujetoObligadoServiceBusiness" ,mappedName="sujetoObligadoServiceBusiness") SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	@EJB(name = "personaBusiness", mappedName = "personaBusiness") PersonaBusinessRemote personaBusinessRemote;
	@EJB(name = "serviceBusiness", mappedName = "serviceBusiness") ServiceBusinessRemote serviceBusinessRemote;
	@EJB(name="representanteLegalServiceBusiness" ,mappedName="representanteLegalServiceBusiness")
	RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness") 
	SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name = "vigenciaDerechosWSClientService", mappedName = "vigenciaDerechosWSClientService") VigenciaDerechosWSClientRemote vigenciaDerechosWSClientRemote;
	
	private final String NUM_MODALIDAD_35 = "35";
	private final String NUM_MODALIDAD_00 = "00";
	
	@Override
	public List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByIdAsignacionNss(
			Long idAsignacionNSS, Boolean conVigencia, Boolean mostrarAsegurado,
			Boolean incluirDatosUmf)  throws DerechohabientesBusinessException, IllegalArgumentException {
		List<GrupoFamiliar> encontrados = grupoFamiliarDao.findDatosBasicosIntegrantesGrupoByIdAsignacionNss(idAsignacionNSS, conVigencia, mostrarAsegurado, incluirDatosUmf,null,null);
		return encontrados;
	}

	@Override
	public List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByNumNss(
			String numNSS, Boolean conVigencia, Boolean mostrarAsegurado, Boolean incluirDatosUmf) throws DerechohabientesBusinessException, IllegalArgumentException {
		
		List<GrupoFamiliar> encontrados = grupoFamiliarDao.findDatosBasicosIntegrantesGrupoByNumNss(numNSS, conVigencia, mostrarAsegurado, incluirDatosUmf,null,null);
		
		return encontrados;
	}
	
	@Override
	public CabezaGrupoFamiliar getCabezaWS(Long idAsignacion)
			throws Exception {
		return grupoFamiliarDao.getCabezaGrupoFamiliarWS(idAsignacion);
	}

	@Override
	public Boolean tienePatronImss(Long idAsignacionNSS) throws DerechohabientesBusinessException{
		
		Boolean patronIMSS = false;
		try {
			patronIMSS = grupoFamiliarDao.tienePatronImss(idAsignacionNSS);
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("Ocurrio un error al consultar si se tiene patron IMSS",
					"Ocurrio un error al consultar si se tiene patron IMSS");
			
		}
		
		return patronIMSS;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarPorEstados(
			Long idAsignacionNss, List<Long> idEstados) throws DerechohabientesBusinessException, Exception {
		
		
		return grupoFamiliarDao.findGrupoFamiliarByEstado(idAsignacionNss, idEstados);
	}

	@Override
	public Solicitud guardarSolicitudConsultaVigencia(AsignacionNSS nss, Usuario usuario)
			throws DerechohabientesBusinessException, Exception {
		
		Date fechaCreacionSolicitud = new Date();
		TramiteConsultaVigencia tramiteConsulta = new TramiteConsultaVigencia();
		tramiteConsulta.setNss(nss);

		List<GrupoFamiliar> integrantes = null;
		
		try {
			integrantes = grupoFamiliarDao.findGrupoFamiliarByNssWS(nss.getIdAsignacionNSS());
		} catch(DerechohabientesBusinessException e) {
			log.error("Error al consultar el ws de vigencia para traer a los candidatos",e);
		}
		
		if(integrantes == null){
			try {
				CabezaGrupoFamiliar cabeza = grupoFamiliarDao.getCabezaGrupoFamiliarWS(nss.getIdAsignacionNSS());
				if( cabeza != null) {
					integrantes = new ArrayList<GrupoFamiliar>();
					
					GrupoFamiliar grupoFamiliar = new GrupoFamiliar();
					grupoFamiliar.setAsignacionNSS(new AsignacionNSS());
					grupoFamiliar.getAsignacionNSS().setIdAsignacionNSS(nss.getIdAsignacionNSS());
					grupoFamiliar.setEstadoDerechohabiente(cabeza.getEstadoDerechohabiente());
					grupoFamiliar.setSubEstadoDerechohabiente(cabeza.getSubEstadoDerechohabiente());
					grupoFamiliar.setFechaInicioVigencia(cabeza.getFechaInicioVigencia());
					grupoFamiliar.setFechaFinVigencia(cabeza.getFechaFinVigencia());
					grupoFamiliar.setDerechohabiente(new Derechohabiente());
					grupoFamiliar.getDerechohabiente().setIdPersona(nss.getIdPersona());
					
					integrantes.add(grupoFamiliar);
				}
			} catch(Exception e) {
				log.error("Error al consultar la cabeza de grupo familiar", e);
			}
		}
		
		tramiteConsulta.setIntegrantes(integrantes);
		
		tramiteConsulta.setResultado(true);
		tramiteConsulta.setRazonResultado(new RazonResultado());
		tramiteConsulta.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramiteConsulta.setEstadoTramite(new EstadoTramite());
		Long idEstadoTramite = EstadoTramiteEnum.CERRADO.getId();
		tramiteConsulta.getEstadoTramite().setIdEstadoTramitePersona(idEstadoTramite.intValue());
		tramiteConsulta.setFechaPresentacion(fechaCreacionSolicitud);
		tramiteConsulta.setFechaRegistroActualizacion(fechaCreacionSolicitud);
		tramiteConsulta.setObservacion("Consulta de segundo y tercer nivel");
		tramiteConsulta.setTipoTramite(new TipoTramite());
		tramiteConsulta.getTipoTramite().setIdTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.CONSULTA_DE_VIGENCIA_DE_DERECHOS.getCodigo());
		
		//Creamos la solicitud a guardar
		Solicitud solicitud = new Solicitud();
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
		solicitud.setOrigenSolicitud(origenSolicitud);
		//Colocamos todos los estados necesarios de la nueva solicitud
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId().intValue());
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());
		solicitud.setFechaSolicitud(fechaCreacionSolicitud);
		solicitud.setSolicitante(usuario);
		solicitud.setFechaPresentacion(fechaCreacionSolicitud);
		
		tramiteConsulta.setFechaConclusion(new Date());
		solicitud.setFechaConclusion(new Date());
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(tramiteConsulta);
		
		PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
		Fisica fisica = new Fisica();
		fisica.setIdPersona(nss.getIdPersona());
		personaIntSol.setPersona(fisica);
		TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
		tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
		personaIntSol.setTipoPersonaInteresadaSol(tipoPersona);
		
		solicitud.setPersonaInteresadaSolicitud(personaIntSol);
		
		
		solicitud = solicitudBusinessRemote.crear(solicitud);
		
		return solicitud;
	}

	@Override
	public List<GrupoFamiliar> findGruposFamiliaresPorPersonaYPersonaInteresada(
			Long idPersona, Long idPersonaInteresada)
			throws DerechohabientesBusinessException, IllegalArgumentException,
			Exception {
		List<GrupoFamiliar> grupos = null;
		List<GrupoFamiliar> gruposActivos = null;
		List<GrupoFamiliar> gruposBaja = null;
		
		
		grupos = this.getGruposFamiliaresPorPersona(idPersona, null);
		
		if(grupos != null) {
			if(idPersonaInteresada != null && !idPersona.equals(idPersonaInteresada)) {
				gruposActivos = new ArrayList<GrupoFamiliar>();
				for(GrupoFamiliar grupo: grupos) {
					if(grupo.getDerechohabiente().getIdPersona().equals(idPersona) && idPersonaInteresada.equals(grupo.getAsignacionNSS().getIdPersona())) {
						gruposActivos.add(grupo);
						return gruposActivos;
					}
				}
			} else{
				gruposActivos = new ArrayList<GrupoFamiliar>();
				gruposBaja = new ArrayList<GrupoFamiliar>();
				for(GrupoFamiliar grupo: grupos) {
					EstadoDerechohabiente estadoD = grupo.getEstadoDerechohabiente();
					if( estadoD != null && estadoD.getIdEstadoDerechohabiente() != null) {
						if(estadoD.getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
							gruposBaja.add(grupo);
						} else {
							gruposActivos.add(grupo);
						}
					}
				}
			}
		} else{
			return null;
		}
		
		if((gruposBaja == null || gruposBaja.isEmpty()) && (gruposActivos == null || gruposActivos.isEmpty())){
			return null;
		}
		
		if(gruposActivos != null && !gruposActivos.isEmpty()) {
			return gruposActivos;
		} else {
			return gruposBaja;
		}
		
	}

	/**
	 * Metodo con el que sabemos si una persona esta registrada como derechohabiente en estado activo,baja o ambos
	 * en caso de mandar el segundo atributo como nulo
	 */
	@Override
	public Boolean personaRegistradaComoDerechohabiente(Long idPersona,
			Boolean activo) throws DerechohabientesBusinessException, IllegalArgumentException, Exception {
		log.debug("Entramos a verificar si la persona esta registrada como derechohabiente");
		Boolean registrado = false;
		
		List<GrupoFamiliar> grupos = null;
		
		grupos = this.getGruposFamiliaresPorPersona(idPersona, activo);
		
		if(grupos != null && !grupos.isEmpty()) {
			log.debug("Si esta registrado como derechohabiente la persona : " + idPersona);
			registrado = true;
		}
		
		return registrado;
	}

	/**
	 * Metodo con el que obtenemos alos grupos familiares en los que se encuentra registrado una persona
	 * ya sea en estado activo, en baja o en ambos
	 */
	@Override
	public List<GrupoFamiliar> getGruposFamiliaresPorPersona(Long idPersona,
			Boolean activo) throws DerechohabientesBusinessException, IllegalArgumentException, Exception {
		
		if(idPersona == null) {
			throw new IllegalArgumentException("El id de persona no puede ser nulo");
		}
		
		log.debug("Entramos a verificar la existencia de la persona dentro de un grupo familiar");
		List<GrupoFamiliar> grupos = null;
		grupos = grupoFamiliarDao.getGruposFamiliaresPorPersona(idPersona, null, null);
		List<GrupoFamiliar> gruposEstados = null;
		if(grupos != null && !grupos.isEmpty()) {
			log.debug("Se encontro a la persona en al menos un grupo familiar");
			if(activo != null) {
				log.debug("El atributo activo no es null, se buscara al integrante con estado activo?: "+activo);
				gruposEstados = new ArrayList<GrupoFamiliar>();
				if(activo) {
					log.debug("se buscara la persona " + idPersona + " en estado activo en cualquier grupo familiar");
					for(GrupoFamiliar grupo: grupos) {
						if(grupo.getEstadoDerechohabiente() != null && grupo.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != null 
								&& !grupo.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId()))  {
							gruposEstados.add(grupo);
						}
					}
				} else {
					log.debug("se buscara la persona " + idPersona + " en estado activo en cualquier grupo familiar");
					for(GrupoFamiliar grupo: grupos) {
						if(grupo.getEstadoDerechohabiente() != null && grupo.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != null 
								&& !grupo.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId()))  {
							gruposEstados.add(grupo);
						}
					}
				}
			} else {
				gruposEstados = grupos;
			}	
		}
		
		return gruposEstados;
	}

	/**
	 * Obtiene el numero de integrantes registrados dentro de un grupo familiar por parentesco
	 * o si el parametro de parentesco va nulo, muestra el numero total de integrantes registrados en el grupo
	 * @param idAsignacionNss
	 * @param idParentesco - puede ir nulo y no se filtrarta por parentesco
	 * @return
	 */
	@Override
	public Long getNumeroIntegrantesRegistrsdosPorParentesco(
			Long idAsignacionNss, Long idParentesco) {
		
		return grupoFamiliarDao.getNumeroDeIntegrantesPorParentesco(idAsignacionNss, idParentesco);
	}

	
	@Override
	public Long getNumeroDeIntegrantesPorListParentesco(Long idAsignacionNss,
			List<Long> idParentesco) {
		// TODO Auto-generated method stub
		return grupoFamiliarDao.getNumeroDeIntegrantesPorListParentesco(idAsignacionNss, idParentesco);
	}

	@Override
	public GrupoFamiliar getIntegranteGrupoFamiliarPorIdPersona(
			Long idAsignacionNss, Long idPersona)
			throws DerechohabientesBusinessException {
		
		return grupoFamiliarDao.getIntegranteGrupoFamiliar(idAsignacionNss, idPersona);
	}

	@Override
	public List<GrupoFamiliar> getFechaYMedicoExistenteEnUmf(
			Long idAsignacionNss, Long idUmf) {
		
		List<GrupoFamiliar> grupoFamiliar = null;
		try{
			grupoFamiliar = grupoFamiliarDao.getMedicoYFechaEnUmf(idAsignacionNss, idUmf, false,1);
			if(grupoFamiliar == null || grupoFamiliar.isEmpty()) {
				grupoFamiliar = grupoFamiliarDao.getMedicoYFechaEnUmf(idAsignacionNss, idUmf, true,1);
			}
		}catch(Exception e) {
			log.error("No fue posible obtener el medico disponible");
		}
		
		return grupoFamiliar;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception {
		List<GrupoFamiliar> grupoFamiliar = grupoFamiliarDao.findGrupoFamiliar(idAsignacionNss); 
		return grupoFamiliar;
	}

	@Override
	public List<GrupoFamiliar> getIntegranteGrupoFamiliar(Long idPersona,Long idEstadoDerechohabiente) throws DerechohabientesBusinessException {
		List<GrupoFamiliar> integrantes = null;
		try {
			integrantes = grupoFamiliarDao.getIntegranteGrupoFamiliarEstado(idPersona,idEstadoDerechohabiente);
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("error.sinDatos", ""+e.getCause());
		}
		
		return integrantes;
	}
	
	@Override
	public GrupoFamiliar getIntegranteGrupoFamiliarByAsignacionNss(
			Long idAsignacionNss, Long idPersona, Long idEstadoDerechohabiente)
			throws DerechohabientesBusinessException, Exception {		
		GrupoFamiliar integrante = null;
		
		integrante = grupoFamiliarDao.getIntegranteGrupoFamiliarByAsignacionNss(idAsignacionNss, idPersona, idEstadoDerechohabiente);
		
		
		return integrante;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarByParentesco(Long idAsignacionNss,Long parentesco) throws DerechohabientesBusinessException,Exception{		
		List<GrupoFamiliar> grupoFamiliar = grupoFamiliarDao.findGrupoFamiliarByParentesco(idAsignacionNss, parentesco); 
		return grupoFamiliar;
	}
	
	@Override
	public GrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception {
		GrupoFamiliar grupoFamiliar = null;
		
			grupoFamiliar = grupoFamiliarDao.getCabezaGrupoFamiliar(idAsignacionNss, ParentescoEnum.ASEGURADO.getId());
			if(grupoFamiliar == null){
				grupoFamiliar = grupoFamiliarDao.getCabezaGrupoFamiliar(idAsignacionNss, ParentescoEnum.PENSIONADO.getId());
			}
		 
		return grupoFamiliar;
	}
	
	@Override
	public boolean existeSolicitudRegistro(String idAsignacionNss) throws Exception  {
		boolean resp=false;
		resp=this.asignacionNssDao.existSolicitudRegistrobyNSS(idAsignacionNss); 
		return resp;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarParentescoEstado(
			Long idAsignacionNss, Long parentesco, Long estado) throws Exception {
		List<GrupoFamiliar> grupoFamiliar = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacionNss, parentesco, estado); 				
		return grupoFamiliar;
	}
	//msg05
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarEstadoSubestado(
			Long idAsignacionNss, Long parentesco, Long estado,Long idSubestado) throws Exception {
		List<GrupoFamiliar> grupoFamiliar = grupoFamiliarDao.findGrupoFamiliarEstadoSubestado(idAsignacionNss, parentesco, estado, idSubestado); 
		if(grupoFamiliar==null || grupoFamiliar.isEmpty())
			throw new DerechohabientesBusinessException("error.sinDatos");
		
		return grupoFamiliar;
	}
	
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarProrroga(
			Long idAsignacionNss, Long parentesco, Long estado,Long idSubestado) throws Exception {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		GrupoFamiliar grupoFamiliarRespaldo=null;
		Long edad =0L;
		List<GrupoFamiliar> grupoFamiliar = grupoFamiliarDao.findGrupoFamiliarEstadoSubestado(idAsignacionNss, parentesco, estado, idSubestado); 
		
		if(parentesco.longValue()==ParentescoEnum.HIJOS.getId()){
			for (int i=0;i<grupoFamiliar.size();i++) {
				grupoFamiliarRespaldo= grupoFamiliar.get(i);
				edad =DateUtils.getEdad(grupoFamiliarRespaldo.getDerechohabiente().getFechaNacimiento());
				if(edad>=16 && edad<=25)
				{
					salida.add(grupoFamiliarRespaldo);
				}
				
			}
		}else{
			salida.addAll(grupoFamiliar);
		}
			
		if(salida==null || salida.isEmpty())
			throw new DerechohabientesBusinessException("error.sinDatos");
		
		return salida;
	}
	
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarPorEstado(Long idAsignacionNss,
			Long estado) throws DerechohabientesBusinessException, Exception {
		List<GrupoFamiliar> integrantes = grupoFamiliarDao.findGrupoFamiliarPorEstado(idAsignacionNss, estado);
		
		return integrantes;
	}

	
	@Override
	public DatosSalidaPaginador<GrupoFamiliar> paginarGrupoFamiliar(
			DatosEntradaPaginador<AsignacionNSS> entrada) throws DerechohabientesBusinessException, Exception  {
		
		DatosSalidaPaginador<GrupoFamiliar> paginado = grupoFamiliarDao.paginarGrupoFamiliar(entrada, null);
		//TODO se quita consulta de circunscripcion para no realizar consultas a BD
		//la consulta de deja del lado de la vista mediante una llamada ajax
		//List<GrupoFamiliar> grupoFamiliar = paginado.getAaData();
		//verifica si algunos de los integrantes del grupo familiar tiene circunscripcion foranea
		/*
		if(grupoFamiliar != null && grupoFamiliar.size() > 0){
			
			TramiteCircunscripcionForanea c = null;
			
			for(int i = 0 ; i < grupoFamiliar.size(); i++){
				try{
					
					c = tramitePersonaFisicaDao.getCircunscripcionForanea(
							grupoFamiliar.get(i).getDerechohabiente().getIdPersona(), 
							grupoFamiliar.get(i).getAsignacionNSS(), 
							true);
					
					if (c != null){
						grupoFamiliar.get(i).setCircunscripcionForaneaActiva(true);
					}else{
						grupoFamiliar.get(i).setCircunscripcionForaneaActiva(false);
					}
				}catch(Exception e){
					grupoFamiliar.get(i).setCircunscripcionForaneaActiva(false);
					e.printStackTrace();
				}
				  
				
			}
			
		}*/
		
		return paginado;
	}

	@Override
	public AsignacionNSS getAsignacionNss(String nss, long idPersona) throws DerechohabientesBusinessException , Exception {
		AsignacionNSS asigancion = null;
		try{
			asigancion = grupoFamiliarDao.getAsignacionNss(nss,idPersona);
		}catch (NonUniqueResultException e) {
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_DATOS, ""+e.getCause());
		}
		return asigancion;
	}
	
	@Override
	public List<AsignacionNSS> getAsignacionNss(long idPersona) throws DerechohabientesBusinessException {
		return grupoFamiliarDao.getAsignacionNss(idPersona);
	}

	
	@Override
	public AsignacionNSS getAsignacionNssSinPersona(String nss, Boolean segundoYTercerNivel)
			throws DerechohabientesBusinessException, Exception  {
		return this.getGrupoFamiliar(nss, segundoYTercerNivel).getAsignacionNSS();
	}

	
	@Override
	public GrupoFamiliar getGrupoFamiliar(String nss, Boolean segundoYTercerNivel)
			throws DerechohabientesBusinessException, Exception  {
		AsignacionNSS asignacion = null;
		GrupoFamiliar grupoFamiliar = new GrupoFamiliar();


		try{
			
			asignacion = grupoFamiliarDao.getAsignacionNss(nss);

			if(segundoYTercerNivel) {
				//Si no encontramos el nss en BDTU
				if(asignacion == null) {
					//Checamos si el asignacionNSS esta en los estudiantes
					asignacion = grupoFamiliarDao.getAsignacionNssCL3(nss);
					//Si no se encontro en los estudiantes
					if(asignacion == null) {
						//Checamos si esta en los inconsistentes
						GrupoFamiliar aseguradoInconsistente = null;
						
						try {
							aseguradoInconsistente = this.getAseguradoInconsistente(nss);
						} catch(Exception e){
							e.printStackTrace();
							DerechohabientesBusinessException.throwException(
									"No fue posible validar si el asegurado se encontraba consistente.", 
									"No fue posible validar si el asegurado se encontraba consistente.");
						}
						
						if( aseguradoInconsistente == null || aseguradoInconsistente.getAsignacionNSS().getEstadoInconsistencia().equals(EstadoInconsistenciaVigenciaEnum.NO_EXISTE_NSS.getId()) ){

							DerechohabientesBusinessException.throwException("No se encontr&oacute; el NSS","No se encontr&oacute; el NSS");
						} else {
							asignacion = aseguradoInconsistente.getAsignacionNSS();
							grupoFamiliar.setAsignacionNSS(asignacion);
							grupoFamiliar.setDerechohabiente(aseguradoInconsistente.getDerechohabiente());
							grupoFamiliar.setAgregadoMedico(aseguradoInconsistente.getAgregadoMedico());
							grupoFamiliar.setMedicoEnTurno(aseguradoInconsistente.getMedicoEnTurno());
							grupoFamiliar.setConDerechoInc(aseguradoInconsistente.getConDerechoInc());
							grupoFamiliar.setConDerechoSm(aseguradoInconsistente.getConDerechoSm());
							
						}
					} else {
						asignacion.setEstadoInconsistencia(EstadoInconsistenciaVigenciaEnum.ESTUDIANTES.getId());
						asignacion.setEstudiante(true);
					}
					
				} else {
					
					//Si encontramos el NSS en bdtu
					//Verificamos si tiene alguna inconsistencia
					GrupoFamiliar aseguradoInconsistente = null;
					Boolean sinUMF = false;
					try {
						aseguradoInconsistente = this.getAseguradoInconsistente(nss);
					} catch(Exception e) {
						sinUMF = true;
						e.printStackTrace();
						log.warn("Ocurrio un error al consultar al ws conpleto con el nss: " + nss);
					}
					
					CabezaGrupoFamiliar cabeza = this.cabezaGrupoFamiliar(asignacion.getIdAsignacionNSS());

					if(aseguradoInconsistente != null) {
						
						// -------------------------------------------------
						// Esta en BDTU
						// Tiene estado 4 de inconsistencia
						// -------------------------------------------------
						if(aseguradoInconsistente.getAsignacionNSS().getEstadoInconsistencia().intValue() == EstadoInconsistenciaVigenciaEnum.BENEFICIARIOS.getId()   )
							asignacion.setEstadoInconsistencia(EstadoInconsistenciaVigenciaEnum.ENBDTU.getId());
						else{
							if(cabeza != null && cabeza.getCveEstadoInconsistencia().intValue() == EstadoInconsistenciaVigenciaEnum.SIN_INCONSISTENCIA.getId())
								asignacion.setEstadoInconsistencia(new Integer(EstadoInconsistenciaVigenciaEnum.SIN_INCONSISTENCIA.getId()));
							else
								asignacion.setEstadoInconsistencia(aseguradoInconsistente.getAsignacionNSS().getEstadoInconsistencia());
						}
						asignacion.setTipoPension(aseguradoInconsistente.getAsignacionNSS().getTipoPension());
						grupoFamiliar.setAsignacionNSS(asignacion);
						grupoFamiliar.setDerechohabiente(aseguradoInconsistente.getDerechohabiente());
						grupoFamiliar.setAgregadoMedico(aseguradoInconsistente.getAgregadoMedico());
						grupoFamiliar.setMedicoEnTurno(aseguradoInconsistente.getMedicoEnTurno());
						grupoFamiliar.setConDerechoInc(aseguradoInconsistente.getConDerechoInc());
						grupoFamiliar.setConDerechoSm(aseguradoInconsistente.getConDerechoSm());

					} else {
						sinUMF = true;
					}

					if ( sinUMF ){
						//En caso de no estar adscrito a una UMF, se toma el valor del tag <ConDerechoSm> que regresa
						//el WS de cabeza de grupo
						if ( cabeza != null){
							grupoFamiliar.setConDerechoSm(cabeza.getConDerechoSm());
						}
					}
				}
				
				
			} else {
				if(asignacion == null) {
					DerechohabientesBusinessException.throwException("No se encontr&oacute; el NSS","No se encontr&oacute; el NSS");
				}
			}
				
		}catch(NonUniqueResultException e){
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_DATOS, ""+e.getCause());
		}
		
		grupoFamiliar.setAsignacionNSS(asignacion);
		return grupoFamiliar;
		
		
	}
	
	

	@Override
	public AsignacionNSS getAsignacionNssByIdAsignacion(Long idAsignacionNss)
			throws DerechohabientesBusinessException {
		
		AsignacionNSS asignacion = null;
		
		asignacion = grupoFamiliarDao.getAsignacionNssByIdAsignacionNss(idAsignacionNss);
		
		return asignacion;
	}

	/**
	 * Obtiene una lista de tramites correspondientes a un grupo familiar y un grupo de estados
	 * @param personas
	 * @param estados
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public List<Tramite> getTramitesPorEstadoGrupoFamiliar(AsignacionNSS asignacion, List<Long> estados) throws DerechohabientesBusinessException , Exception {
		//List<GrupoFamiliar> grupoFamiliar = findGrupoFamiliarEnBaja(idAsignacionNSS);
		Long numIntegrantes = this.getNumeroIntegrantesRegistrsdosPorParentesco(asignacion.getIdAsignacionNSS() , null);
		List<GrupoFamiliar> grupoFamiliar  = new ArrayList<GrupoFamiliar>();
		if(numIntegrantes >0){
			 grupoFamiliar = findGrupoFamiliar(asignacion.getIdAsignacionNSS());
		}
		//Crea la lista de persona
		List<Long> personas = new ArrayList<Long>();
		for(GrupoFamiliar gf : grupoFamiliar){
			personas.add(gf.getDerechohabiente().getIdPersona());
		}
		
		if(grupoFamiliar == null || grupoFamiliar.size() == 0){
			if(asignacion.getIdPersona() != null ){
				personas.add(asignacion.getIdPersona());
			}
		}
		
		return tramitePersonaFisicaDao.getTramitesPersonas(personas, estados, asignacion.getIdPersona()); 
	}
	
	/**
	 * Obtiene a un integrante del grupo familiar que se encuentre en alguno de los estados que se envian
	 * @param idAsignacionNSS
	 * @param estados
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public GrupoFamiliar getIntegranteGrupoFamiliarByEstados(Long idPersona, List<Long> estados, Long idAsignacionNss) throws DerechohabientesBusinessException, Exception  {
		GrupoFamiliar grupoFamiliar = null;
		
			log.debug("Se consultara en la bdtu al integrante: - IdAsignacion : " + idAsignacionNss + " - IdPersona" + idPersona);
			grupoFamiliar = grupoFamiliarDao.getIntegranteGrupoFamiliarByEstados(idPersona, estados, idAsignacionNss);
		
			return grupoFamiliar;
	}
	
	@Override
	public CabezaGrupoFamiliar cabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception {
		CabezaGrupoFamiliar objCabezaGrupoFamiliar = null;
		try{

			objCabezaGrupoFamiliar = grupoFamiliarDao.getCabezaGrupoFamiliar(idAsignacionNss);
			
			if(objCabezaGrupoFamiliar == null) {
				DerechohabientesBusinessException.throwException(ExceptionMessages.NSS_NO_ENCONTRADO,"El NSS no fue encontrado");
			}
		}catch(DerechohabientesBusinessException e) {
			log.error("Ocurrio un error " , e);
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro no esperado la consulta del cabeza gpo fam ", e);
			throw e;
		}
		
		return objCabezaGrupoFamiliar;
	}

	
	@Override
	public List<Modalidad> getModalidadesActivas(Long idAsignacionNss)
			throws DerechohabientesBusinessException {
		List<Modalidad> modalidades = new ArrayList<Modalidad>();;
		
		try{
			List<Long> ids = wsClient.getIDsPatronesActivosPorAsignacionNSS(idAsignacionNss);
			List<Long> idsSO = new ArrayList<Long>();
			//Si no existen patrones activos se hace una busqueda al servicio
			//de informacion cabeza grupofamiliar, el cual contiene al patron general
			// y en base a ese valor se obtiene su modalidad
			/*if( ids == null || ids.isEmpty() ){
				modalidades = new ArrayList<Modalidad>();
				
				SujetoObligado sujeto = cabezaGF.getPatronSujetoObligado();
				
				if( sujeto != null && sujeto.getCveIdSujetoObligado() != null ){
					Modalidad modalidad = sujetoObligadoServiceBusinessRemote.getModalidadPatron(sujeto.getCveIdSujetoObligado());
					if(modalidad != null) {
						modalidades.add(modalidad);
					}
				}
			} else {*/
			
			
			for(Long id : ids) {
				Long cveIdPatronSO = sujetoObligadoServiceBusinessRemote.getCvePatronSujetoObligadoPorCveIdPatronGeneral(id);
				
				idsSO.add(cveIdPatronSO);
			}
			
			if(idsSO != null && !idsSO.isEmpty()) {
			
				Modalidad modalidad = null;
				for(Long idPatron:  idsSO) {
					modalidad = sujetoObligadoServiceBusinessRemote.getModalidadPatron(idPatron);
					if(modalidad != null) {
						modalidades.add(modalidad);
					}
				}
			}
			//}
			/*se adiciona la modalidad 35 que no viene asociada al los patrones vigentes */
			CabezaGrupoFamiliar cabezaGrupoFamiliar = grupoFamiliarDao.getCabezaGrupoFamiliarWS(idAsignacionNss.longValue());
			if(
				cabezaGrupoFamiliar.getPatronSujetoObligado() != null && 
				cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null && (
						(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) &&
								cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE.getId()) ||
						(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_00) &&
						cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId())
						)){
				modalidades.add(cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad());
			}
			if(modalidades.isEmpty()){
				modalidades = null;
			}
			
		}catch (DerechohabientesWebSserviceException e) {
			log.debug("Ocurrio un error al consultar a los patrones con el ws");
			DerechohabientesBusinessException.throwException(e.getMessage(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException
			.throwException("Ocurri&oacute; un error al consultar las modalidades", ""+e.getCause());
		}
	
		return modalidades;
	}

	private List<ServiciosDTO> getServiciosGrupoFam(AsignacionNSS objAsegurado) throws DerechohabientesBusinessException{
		List<ServiciosDTO> serviciosDTO = new ArrayList<ServiciosDTO>();
		ServiciosDTO servicioDTO =null;
		List<ServicioPrestDerechohab> servicios =  null;
		List<ModServPresDerechohab> servicioByAsegurado = null;
		
		Long idAsignacionNss = objAsegurado.getIdAsignacionNSS();
		CabezaGrupoFamiliar cabezaGF = null;
		
		try {
			cabezaGF = grupoFamiliarDao.getCabezaGrupoFamiliarWS(idAsignacionNss);
		} catch(DerechohabientesWebSserviceException e) {
			this.lanzarExcepcionWsVigencia(e);
		} catch(Exception e){
			this.lanzarExcepcionWsVigencia(new DerechohabientesWebSserviceException(e.getMessage()));
		}
		
		
		try{
			servicios = vigenciaDaoLocal.findServicios();
		}catch(Exception e){
			e.printStackTrace();
			DerechohabientesBusinessException
			.throwException(ExceptionMessages.SERVICIOS_ASEGURADO_ERROR, ""+e.getCause());
		}
		
		
		if(cabezaGF == null) {
			DerechohabientesBusinessException.throwException("Usted no cuenta con servicios activos", "Usted no cuenta con servicios activos");
		} 
		else {
				if(cabezaGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId()) || 
						cabezaGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.FALLECIDO.getId())) {
					//Se cambia la excepci�n para que se regrese el listado con todos los servicios con NO
							//DerechohabientesBusinessException.throwException("Usted no cuenta con servicios activos", "Usted no cuenta con servicios activos");
					for (ServicioPrestDerechohab servicio : servicios) {
						servicioDTO = new ServiciosDTO();
						servicioDTO.setIdServicio(servicio.getCveIdServicioDerechohab());
						servicioDTO.setServicio(servicio.getNomServicioDerechohab());
								servicioDTO.setRestricciones("NO");
								servicioDTO.setSiNo("NO");
						serviciosDTO.add(servicioDTO);
					}
					return serviciosDTO;
					
					
				}
		}
		
		
		
		try{
			List<SujetoObligado> patrones = wsClient.getPatronesVigentesPorAsignacionNSS(idAsignacionNss);
			List<Long> idsModalidad = null;
			idsModalidad = new ArrayList<Long>();
			//Si no existen patrones activos se hace una busqueda al servicio
			//de informacion cabeza grupofamiliar, el cual contiene al patron general
			// y en base a ese valor se obtiene su modalidad
			if( patrones != null | !patrones.isEmpty() ){
				for(SujetoObligado sujeto : patrones) {
					idsModalidad.add( sujeto.getModalidad().getIdModalidad() );
				}
			}
			
			if(cabezaGF.getCalidadParentesco().getIdParentesco() == ParentescoEnum.ASEGURADO.getId()){
				
				if(cabezaGF.getPatronSujetoObligado() != null && cabezaGF.getPatronSujetoObligado().getModalidad() != null 
						&& cabezaGF.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) && 
						cabezaGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE.getId()){
					idsModalidad.add(cabezaGF.getPatronSujetoObligado().getModalidad().getIdModalidad());
				} else {
					if(cabezaGF.getPatronSujetoObligado().getModalidad() != null ){
						idsModalidad.add(cabezaGF.getPatronSujetoObligado().getModalidad().getIdModalidad());
					}
				}
				
				servicioByAsegurado = vigenciaDaoLocal.getServiciosPorModalidad(idsModalidad);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			DerechohabientesBusinessException
			.throwException(ExceptionMessages.SERVICIOS_RESTRICCIONES_ASEGURADO_ERROR, ""+e.getCause());
		}
		/** Se setean los servicios por defaul de un pensionado sin importar so modalidad**/
		if(cabezaGF.getCalidadParentesco().getIdParentesco() == ParentescoEnum.PENSIONADO.getId()){
			
			for (ServicioPrestDerechohab servicio : servicios) {
				
				servicioDTO = new ServiciosDTO();
				servicioDTO.setIdServicio(servicio.getCveIdServicioDerechohab());
				servicioDTO.setServicio(servicio.getNomServicioDerechohab());
			
				if(ServiciosPrestacionesEnum.SERVICIO_MEDICO.getId() == servicio.getCveIdServicioDerechohab()){
						servicioDTO.setRestricciones("NO");
						servicioDTO.setSiNo("SI");
				} else if(ServiciosPrestacionesEnum.REGISTRO_BENEFICIARIOS.getId() == servicio.getCveIdServicioDerechohab()){
						servicioDTO.setRestricciones("NO");
						servicioDTO.setSiNo("SI");
				} else if(ServiciosPrestacionesEnum.EXPEDICION_INCAPACIDAD_TRABAJO.getId() == servicio.getCveIdServicioDerechohab()){
						servicioDTO.setRestricciones("NO");
						servicioDTO.setSiNo("NO");
				} else if(ServiciosPrestacionesEnum.SERVICIO_GUARDERIA.getId() == servicio.getCveIdServicioDerechohab()){
						servicioDTO.setRestricciones("NO");
						servicioDTO.setSiNo("NO");
				}	
				serviciosDTO.add(servicioDTO);
			}
		
			//se iteran los servicios del asegurado para completar los servicios asignados al pensionado		
			if(!serviciosDTO.isEmpty()) {
				if(servicioByAsegurado != null && servicioByAsegurado.size() > 0 ){
				//Itera los servicios disponibles en el catalogo hasta este momento 4
					for (ServiciosDTO servicio : serviciosDTO) {
					//Itera los servicios registrados para el asegurado de acuerdo a su modalidad
						for (ModServPresDerechohab servicioAsignado : servicioByAsegurado) {
							//SOLO SE CONTEMPLAN LOS SERVICIOS FALTANTES PARA UN PENSIONADO
								if(ServiciosPrestacionesEnum.EXPEDICION_INCAPACIDAD_TRABAJO.getId() == servicio.getIdServicio()){
									servicio.setSiNo("SI");
									if(servicioAsignado.getNumValorServicio().intValue() != Constants.VALOR_EXPEDICION_DE_INCAPACIDADES){
										servicio.setRestricciones("SI");
									}	else {
										servicio.setRestricciones("NO");
									}
									
								}
								else if(ServiciosPrestacionesEnum.SERVICIO_GUARDERIA.getId() == servicio.getIdServicio()){
									if(objAsegurado.getSexo().getIdSexo().intValue() == SexoEnum.MUJER.getId()){
										servicio.setSiNo("SI");
									}else{
										servicio.setSiNo("NO");
									}
										
									if(servicioAsignado.getNumValorServicio().intValue() != Constants.VALOR_SERVICIO_DE_GUARDERIAS){
										servicio.setRestricciones("SI");
									}	
									else {
										servicio.setRestricciones("NO");
									}
								}
							}
						}
					}
				}
		}
	/**aqui se itera si no tuvo servicios como pensionado y solo se interan los asegurado**/
		else{
			if(servicioByAsegurado != null && servicioByAsegurado.size() > 0 ){
				//Itera los servicios disponibles en el catalogo hasta este momento 4
				for (ServicioPrestDerechohab servicio : servicios) {
					
					servicioDTO = new ServiciosDTO();
					servicioDTO.setIdServicio(servicio.getCveIdServicioDerechohab());
					servicioDTO.setServicio(servicio.getNomServicioDerechohab());
					servicioDTO.setSiNo("NO");
					servicioDTO.setRestricciones("NO");
					//Itera los servicios registrados para el asegurado de acuerdo a su modalidad
					for (ModServPresDerechohab servicioAsignado : servicioByAsegurado) {
							//Si el asegurado cuenta con un servicio del catalogo 
							if(servicioAsignado.getServicioPrestDerechohab().getCveIdServicioDerechohab()==servicio.getCveIdServicioDerechohab()){
								servicioDTO.setSiNo("SI");
								//Se verifica si el valor del servicio el diferente al maximo establecido
								// Se ser afirmativo se indica que que tiene restricciones en el servicio
								if(ServiciosPrestacionesEnum.SERVICIO_MEDICO.getId() == servicio.getCveIdServicioDerechohab()){
									if(servicioAsignado. getNumValorServicio().intValue() != Constants.VALOR_PRESTACIONES_EN_ESPECIE){
										servicioDTO.setRestricciones("SI");
									}	
									
								} else if(ServiciosPrestacionesEnum.REGISTRO_BENEFICIARIOS.getId() == servicio.getCveIdServicioDerechohab()){
									if(servicioAsignado. getNumValorServicio().intValue() != Constants.VALOR_REGISTRO_DE_BENEFICIARIOS){
										servicioDTO.setRestricciones("SI");
									}	
									
								}else if(ServiciosPrestacionesEnum.EXPEDICION_INCAPACIDAD_TRABAJO.getId() == servicio.getCveIdServicioDerechohab()){
									if(servicioAsignado. getNumValorServicio().intValue() != Constants.VALOR_EXPEDICION_DE_INCAPACIDADES){
										servicioDTO.setRestricciones("SI");
									}	
									
								}
								else if(ServiciosPrestacionesEnum.SERVICIO_GUARDERIA.getId() == servicio.getCveIdServicioDerechohab()){
									if(objAsegurado.getSexo().getIdSexo().intValue() == SexoEnum.MUJER.getId()){
										servicioDTO.setSiNo("SI");
									}else{
										servicioDTO.setSiNo("NO");
									}
									
									if(servicioAsignado. getNumValorServicio().intValue() != Constants.VALOR_SERVICIO_DE_GUARDERIAS){
										servicioDTO.setRestricciones("SI");
									}	
									
								}
								break;
								
							}
					}
					serviciosDTO.add(servicioDTO);
				}
			}else{
				for (ServicioPrestDerechohab servicio : servicios) {
					
					servicioDTO = new ServiciosDTO();
					servicioDTO.setIdServicio(servicio.getCveIdServicioDerechohab());
					servicioDTO.setServicio(servicio.getNomServicioDerechohab());
							servicioDTO.setRestricciones("NO");
							servicioDTO.setSiNo("NO");
					serviciosDTO.add(servicioDTO);
				}
			}
		}
		
		return serviciosDTO;
	}
	
	/**
	 * Obtiene los servicios totales del asegurado.
	 * @param nss
	 * @return
	 */
	@Override
	public List<ServiciosDTO> getServiciosGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception  {
		AsignacionNSS ojbAsegurado = this.getAsignacionNssByIdAsignacion(idAsignacionNss);
		return this.getServiciosGrupoFam(ojbAsegurado);
	}
	
	

	@Override
	public List<ServiciosDTO> getServiciosGrupoFamiliarByAsignacionNSS(
			AsignacionNSS asignacionNSS)
			throws DerechohabientesBusinessException, Exception {
		
		return this.getServiciosGrupoFam(asignacionNSS);
	}

	/**
	 * Obtiene los servicios totales del asegurado.
	 * @param nss
	 * @return
	 */
	@Override
	public List<PrestacionesDTO> getPrestacionesAsegurado(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception  {
		List<PrestacionesDTO> lstPrestacionesDTO = new ArrayList<PrestacionesDTO>();
		PrestacionesDTO prestacionDTO =null;
		List<Prestacion> lstPrestacion =  null;
		
		List<PrestacionPorModalidad> prestacionesByAsegurado = null;
	
		CabezaGrupoFamiliar cabezaGF = null;
		try {
			cabezaGF = grupoFamiliarDao.getCabezaGrupoFamiliarWS(idAsignacionNss);
		} catch(DerechohabientesWebSserviceException e) {
			this.lanzarExcepcionWsVigencia(e);
		} catch(Exception e){
			this.lanzarExcepcionWsVigencia(new DerechohabientesWebSserviceException(e.getMessage()));
		}
		
		try{
			lstPrestacion = prestacionesEntityLocal.getCatalogoPrestaciones();
			this.log.debug("pase la consulta de servicios generales");
		}catch(Exception e){
			e.printStackTrace();
			DerechohabientesBusinessException
			.throwException(ExceptionMessages.SERVICIOS_ASEGURADO_ERROR, ""+e.getCause());
		}
		
		
		if(cabezaGF == null) {
			DerechohabientesBusinessException.throwException("No se encontr� informacion del Asegurado", "Usted no cuenta con servicios activos");
		} 
		else {
				if(cabezaGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId()) || 
						cabezaGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.FALLECIDO.getId())) {
					//Se cambia la excepci�n para que se regrese el listado con todos los servicios con NO
							//DerechohabientesBusinessException.throwException("Usted no cuenta con servicios activos", "Usted no cuenta con servicios activos");
					for (Prestacion objPrestacion : lstPrestacion) {
						
						prestacionDTO = new PrestacionesDTO();
						prestacionDTO.setNomPrestacion(objPrestacion.getNomPrestacion());
						prestacionDTO.setIdPrestacion(objPrestacion.getIdPrestacion());
						prestacionDTO.setPrestacionDinero("-");
						prestacionDTO.setPrestacionEspecie("-");
						lstPrestacionesDTO.add(prestacionDTO);
					}
					this.log.debug("asegurado en baja devuelve default sin servicios");
					return lstPrestacionesDTO;
					
					
				}
		}

		
		
		try{
			List<SujetoObligado> patrones = wsClient.getPatronesVigentesPorAsignacionNSS(idAsignacionNss);
			List<Long> idsModalidad = null;
			idsModalidad = new ArrayList<Long>();
			/*Si no existen patrones activos se hace una busqueda al servicio
				de informacion cabeza grupofamiliar, el cual contiene al patron general
			 	y en base a ese valor se obtiene su modalidad
			 */
			if( patrones != null && !patrones.isEmpty() ){
				for(SujetoObligado sujeto : patrones) {
					idsModalidad.add( sujeto.getModalidad().getIdModalidad() );
				}
			}
			
			if(cabezaGF.getCalidadParentesco().getIdParentesco() == ParentescoEnum.ASEGURADO.getId()){
				
				if(cabezaGF.getPatronSujetoObligado() != null && cabezaGF.getPatronSujetoObligado().getModalidad() != null &&
						cabezaGF.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) && 
						cabezaGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE.getId()){
					idsModalidad.add(cabezaGF.getPatronSujetoObligado().getModalidad().getIdModalidad() );
				}
				prestacionesByAsegurado = prestacionesEntityLocal.getPrestacionesPorModalidades(idsModalidad);
				
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			DerechohabientesBusinessException
			.throwException(ExceptionMessages.SERVICIOS_RESTRICCIONES_ASEGURADO_ERROR, ""+e.getCause());
		}
		
		
		
		
			if(prestacionesByAsegurado != null && prestacionesByAsegurado.size() > 0 ){
				this.log.debug("si trae servicios el asegurado y se interan");
				//Itera los servicios disponibles en el catalogo hasta este momento 4
				for (Prestacion objPrestacion : lstPrestacion) {
					
					
					prestacionDTO = new PrestacionesDTO();
					prestacionDTO.setNomPrestacion(objPrestacion.getNomPrestacion());
					prestacionDTO.setIdPrestacion(objPrestacion.getIdPrestacion());
					prestacionDTO.setPrestacionDinero("-");
					prestacionDTO.setPrestacionEspecie("-");
					//Itera las prestaciones registrados para el asegurado de acuerdo a su modalidad
					for (PrestacionPorModalidad prestacionAsegurado : prestacionesByAsegurado) {
						//Si el asegurado cuenta con un servicio del catalogo 
						if(prestacionAsegurado.getPrestacion().getIdPrestacion().intValue() == objPrestacion.getIdPrestacion().intValue()){
							//se valida si cuenta con alguno tipo de prestacion en especie o dinero
							if(TipoPrestacionDerechoHabienteEnum.ESPECIE.getId() == 
								prestacionAsegurado.getTipoPrestacion().getIdTipoPrestacion().longValue()){
								log.debug("la prestacion " + prestacionDTO.getNomPrestacion() + " es en especie");
								prestacionDTO.setPrestacionEspecie("SI");
							}
							if(TipoPrestacionDerechoHabienteEnum.DINERO.getId() == 
								prestacionAsegurado.getTipoPrestacion().getIdTipoPrestacion().longValue()){
								log.debug("la prestacion " + prestacionDTO.getNomPrestacion() + " es en dinero");
								prestacionDTO.setPrestacionDinero("SI");
							}
							
						}
						
					}
					lstPrestacionesDTO.add(prestacionDTO);
				}
			}else{
				this.log.debug("no hubo servicios se setea default -");
				for (Prestacion objPrestacion : lstPrestacion) {
					
					prestacionDTO = new PrestacionesDTO();
					prestacionDTO.setNomPrestacion(objPrestacion.getNomPrestacion());
					prestacionDTO.setIdPrestacion(objPrestacion.getIdPrestacion());
					prestacionDTO.setPrestacionDinero("-");
					prestacionDTO.setPrestacionEspecie("-");
					lstPrestacionesDTO.add(prestacionDTO);
				}
			}
		
		return lstPrestacionesDTO;
	}
	
	
	private void lanzarExcepcionWsVigencia(DerechohabientesWebSserviceException e) throws DerechohabientesBusinessException{
		log.error("Ocurrio un error al consultar el ws de vigencia", e);
		String situacion = e.getMessage() != null ? e.getMessage() : "Ocurri&oacute; un error al consultar el ws de vigencia";
		DerechohabientesBusinessException.throwException(situacion, situacion );
	}
	
	/**
	 * Obtiene los patrones a los cuales esta relacionado un asegurado
	 */
	@Override
	public List<SujetoObligado> getPatronesAsegurado(AsignacionNSS nss) throws DerechohabientesBusinessException, Exception {
		List<SujetoObligado> patrones = null;
		log.debug("Se consultara a los patrones con idAsignacionNss: " + nss.getIdAsignacionNSS());
		
		List<Long> ids = null;
		
		try {
			ids = wsClient.getIDsPatronesActivosPorAsignacionNSS(nss.getIdAsignacionNSS());
		} catch (DerechohabientesWebSserviceException e) {
			log.error("Ocurrio un error al consultar a los patrones con el ws");
			DerechohabientesBusinessException.throwException("Ocurrio error al consultar a los patrones", e.getMessage());
		}
		
		try{
			if(ids != null && !ids.isEmpty()){
				
				patrones = new ArrayList<SujetoObligado>();
				for(Long id : ids) {
					SujetoObligado sujeto = sujetoObligadoServiceBusinessRemote.getDatosBasicosPatronPorIdPatronGeneral(id);
					if(sujeto != null) {
						patrones.add(sujeto);
					}
				}
			}
			CabezaGrupoFamiliar	cabezaGF = grupoFamiliarDao.getCabezaGrupoFamiliarWS(nss.getIdAsignacionNSS());
			if(cabezaGF.getPatronSujetoObligado() != null && cabezaGF.getPatronSujetoObligado().getModalidad() != null 
					&& cabezaGF.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(NUM_MODALIDAD_35) && 
					cabezaGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE.getId()){
				
				SujetoObligado sujetoO = sujetoObligadoServiceBusinessRemote.getDatosBasicosPatronPorIdPatronSujetoObligado(cabezaGF.getPatronSujetoObligado().getCveIdSujetoObligado());
								   
				sujetoO.setModalidad(cabezaGF.getPatronSujetoObligado().getModalidad());
					try{
						sujetoO.setDigVerificador(DeltaUtils.generaDigitoVerificadorRP(sujetoO.getNumeroRegistroPatronal()+cabezaGF.getPatronSujetoObligado().getModalidad().getNumModalidad())+"");
					}catch(Exception e){
						log.error("error al calcular el DV", e);
						sujetoO.setDigVerificador("");
					}
					
					if(patrones == null)
						patrones = new ArrayList<SujetoObligado>();
					patrones.add(sujetoO);
			}
		}catch(Exception e){
			e.printStackTrace();
			DerechohabientesBusinessException.throwException(ExceptionMessages.SERVICIOS_RESTRICCIONES_ASEGURADO_ERROR, ""+e.getCause());
		}
		return patrones;
	}
	
	@Override
	public List<GrupoFamiliar> getIntegrantesSinMedico(String nss) throws DerechohabientesBusinessException , Exception {		
		List<GrupoFamiliar> integrantes =grupoFamiliarDao.findGrupoFamiliarSinMedico(nss);
		
		if(integrantes==null || integrantes.isEmpty())
			throw new DerechohabientesBusinessException("error.sinDatos");
		return integrantes;
	}

	@Override
	public List<Parentesco> getParentescos() throws Exception {		
		List<Parentesco> datos =catalogosDao.findParentesco();
		
		if(datos==null || datos.isEmpty())
			throw new DerechohabientesBusinessException("error.sinDatos");
		return datos;
	}
	
	/**
	 * Obtiene el ultimo tramite de una persona de una lista de tipos 
	 * @param idPersona
	 * @param tramites
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Tramite getUltimoTramitePorTipos(Long idPersona, List<Long> tramites) throws DerechohabientesBusinessException, Exception  {		
		Tramite tramite = null;
		
		tramite =  tramitePersonaFisicaDao.getUltimoTramitePersona(idPersona, tramites);
		
		return tramite;
	}

	@Override
	public Boolean existeIntegranteGF(
			Long idPersona, List<Long> parentescos, List<Long> estados) throws DerechohabientesBusinessException, Exception  {		
		return grupoFamiliarDao.existeIntegranteGF(idPersona, parentescos, estados);
	}

	@Override
	public GrupoFamiliar llenaGrupoFamiliar(Derechohabiente derecho,
			AsignacionNSS an, RegistroDto registro,
			EstadoDerechohabiente estadoDer,
			SubEstadoDerechohabiente subEstadoDer) throws Exception {
		
		GrupoFamiliar gf = new GrupoFamiliar();
		long idParentesco = registro.getTramiteRegistro().getParentesco().getIdParentesco();
		long idSexo = registro.getTramiteRegistro().getFisica().getSexo().getIdSexo();
		long maxima = 0;
		BigDecimal numCalidad = registro.getTramiteRegistro().getParentesco().getCalidadMinima();
		BigDecimal calidadMaxima = registro.getTramiteRegistro().getParentesco().getCalidadMaxima();
		
		gf.setDerechohabiente(derecho);
		gf.setAsignacionNSS(an);
		gf.setDomicilio(registro.getDomicilio());
		gf.setEstadoDerechohabiente(estadoDer);
		gf.setFechaInicioVigencia(new Date());
		gf.setFechaRegistroAlta(new Date());	
		gf.setSubEstadoDerechohabiente(subEstadoDer);
		gf.setParentesco(registro.getTramiteRegistro().getParentesco());
		
		if(!registro.getTramiteRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId()) && !registro.getTramiteRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())){
			List<GrupoFamiliar> listaintegrantes = null;					
			if(idParentesco == ParentescoEnum.PADRES.getId()){
				if(idSexo == SexoEnum.HOMBRE.getId()){
					numCalidad = calidadMaxima;
				}
			}else{
				try {
					listaintegrantes = findGrupoFamiliarByParentesco(an.getIdAsignacionNSS(), idParentesco);
				} catch (DerechohabientesBusinessException e) {
					listaintegrantes = null;		
				}	
				
				if(listaintegrantes.size() > 0){			
					for(GrupoFamiliar integrante : listaintegrantes){			
						maxima = integrante.getCalidad().longValue()+1;							
					}
					numCalidad = new BigDecimal(maxima);
				}
				if(numCalidad.intValue() > calidadMaxima.intValue()){
					numCalidad = calidadMaxima;
				}
			}
		}		
		gf.setCalidad(numCalidad);
		return gf;
	}

	@Override
	public Fisica validaPersonaIMSS(RegistroDto miRegistro)
			throws DerechohabientesBusinessException {
		
		Fisica unaPersona = new Fisica();
		if(miRegistro.getTramiteRegistro().getFisica().getIdPersona() == null){					
			
			try {
				unaPersona = personaBusinessRemote.altaPersonaFisica(miRegistro.getTramiteRegistro().getFisica());
				miRegistro.getTramiteRegistro().getFisica().setIdPersona(unaPersona.getIdPersona());	
			} catch (Exception e) {				
				throw new DerechohabientesBusinessException(ExceptionMessages.ALTA_PERSONA_FISICA);
			}																					
		}else{		
			try {
				personaBusinessRemote.actualizarPersona(miRegistro.getTramiteRegistro().getFisica());
			} catch (PersonaNoEncontradaException e) {
				throw new DerechohabientesBusinessException(ExceptionMessages.ACTUALIZA_PERSONA_FISICA);	
			}														
		}
		return miRegistro.getTramiteRegistro().getFisica();	
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarActivoPorUmf(
			Long idAsignacionNss, Long idUmf)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> grupoFamiliar = null;
		List<Long> estados = new ArrayList<Long>();
		estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
		try {
			grupoFamiliar = grupoFamiliarDao.findIntegrantesPorUmfEstado(idAsignacionNss, idUmf, estados,null, null);
		} catch (Exception e) {
			log.error("No se pudo recuperar el grupo familiar perteneciente a la umf ", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(),ExceptionMessages.GRUPO_FAMILIAR_CONSULTA_ERROR);
		}
		
		return grupoFamiliar;
	}

	
	
	@Override
	public GrupoFamiliar getIntegranteEnUmf(Long idUmf, Long idAsignacionNss, Long idPersonaAsegurado) throws DerechohabientesBusinessException{
		
		List<GrupoFamiliar> grupoFamiliar = null;
		List<Long> estados = new ArrayList<Long>();
		estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
		try {
			grupoFamiliar = grupoFamiliarDao.findIntegrantesPorUmfEstado(idAsignacionNss, idUmf, estados,null, null);
		
		
			if(grupoFamiliar != null) {
				return this.getMayorCalidad(grupoFamiliar);
			} else {
				estados = new ArrayList<Long>();
				estados.add(EstadoDerechohabienteEnum.BAJA.getId());
				grupoFamiliar = grupoFamiliarDao.findIntegrantesPorUmfEstado(idAsignacionNss, idUmf, estados,null, null);
				
				if(grupoFamiliar != null) {
					for(GrupoFamiliar gf : grupoFamiliar){
						
						List<Long> estadoTramite = new ArrayList<Long>();
						estadoTramite.add(EstadoTramiteEnum.CERRADO.getId());

						List<Long> tipoTramite = new ArrayList<Long>();												
						tipoTramite.add(TipoTramiteEnum.BAJA_CONCUBINATO.getCodigo().longValue());
						tipoTramite.add(TipoTramiteEnum.BAJA_DIVORCIO.getCodigo().longValue());
						tipoTramite.add(TipoTramiteEnum.BAJA_DEFUNCION.getCodigo().longValue());

						List<Long> idPersona = new ArrayList<Long>();	
						idPersona.add(gf.getDerechohabiente().getIdPersona());

						List<Tramite> misTramites = tramitePersonaFisicaDaoLocal.getTramitePersona(idPersona, tipoTramite, estadoTramite, null, null, idPersonaAsegurado,true);
						if(misTramites.size() == 0) {
							return gf;
						}
					}
				}
				else {
					return null;
				}
			}
		
		} catch (Exception e) {
			log.error("No se pudo recuperar el grupo familiar perteneciente a la umf ", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(),ExceptionMessages.GRUPO_FAMILIAR_CONSULTA_ERROR);
		}
		
		return null;
	}

	@Override
	public Date getFechaCambioMedico(Long idAsignacionNss, Long idUmf)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> grupoFamiliar = null;
		List<Long> estados = new ArrayList<Long>();
		estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
		try {
			Date fechaCambioMedico = new Date();
			grupoFamiliar = grupoFamiliarDao.findIntegrantesPorUmfEstado(idAsignacionNss, idUmf, estados,null, null);
			
			if(grupoFamiliar != null){
				if(!grupoFamiliar.isEmpty()) {
					fechaCambioMedico = this.buscarFechaCambioMedico(grupoFamiliar);
					fechaCambioMedico = fechaCambioMedico == null ? new Date() : fechaCambioMedico;
				}
			}
			
			return fechaCambioMedico;
		} catch (Exception e) {
			log.error("No se pudo recuperar el grupo familiar perteneciente a la umf ", e);
			DerechohabientesBusinessException.throwException(e.getCause().getMessage(),ExceptionMessages.GRUPO_FAMILIAR_CONSULTA_ERROR);
		}
		
		return null;
	}
	
	
	private Date buscarFechaCambioMedico(List<GrupoFamiliar> entrada) {
		
		for(GrupoFamiliar integrante: entrada) {
			if(integrante.getFechaCambioTurnoMedico() != null)
				return integrante.getFechaCambioTurnoMedico();
		}
		return null;
	}
	
	private GrupoFamiliar getMayorCalidad(List<GrupoFamiliar> integrantes) {
		GrupoFamiliar mayor = null;
		if(!integrantes.isEmpty()) {
			mayor = integrantes.get(0);
			for(GrupoFamiliar integrante: integrantes) {
				if(integrante.getCalidad().intValue() < mayor.getCalidad().intValue()) {
					mayor = integrante;
				}
			}
		}
		return mayor;
	}

	/**
	 * Metodo para buscar a una persona a partir del curp, que debe venir en el atributo curp de un objeto 
	 * de tipo Fisica, el idAsignacionNss se pide para verificar que la persona que se buscara no se encuentre
	 * ya registrada en el grupo familiar de ese nss, de ser asi se mandara una excepcion, de no encontrarse
	 * registrada en el grupo se retornara a la primera persona encontrada en caso de existir mas de una
	 * 
	 * @param idAsignacionNss - Long el id de la tabla asignacionNss
	 * @param fisica - Fisica persona que se buscara, debe contener el atributo curp
	 * @return Fisica - persona encontrada con la curp proporcionada
	 */
	@Override
	public List<Fisica> buscarPersonaPorCurpValidaExistenciaEnGrupo(
			Long idAsignacionNss, Fisica fisica)
			throws DerechohabientesBusinessException {
		
		List<Fisica> personasEncontradas = null;
		List<Long> idPersonas = null;
		
		try {
			personasEncontradas = serviceBusinessRemote.localizarPersonaFisica(fisica);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error("error al consultar a la persona", e);
			DerechohabientesBusinessException.throwException("", e.getSituacion());
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error("error al consultar a la persona", e);
			DerechohabientesBusinessException.throwException("", e.getSituacion());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error("error al consultar a la persona", e);
			DerechohabientesBusinessException.throwException("", e.getSituacion());
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error("error al consultar a la persona", e);
			DerechohabientesBusinessException.throwException("", e.getSituacion());
		} catch (DatosInsuficientesParaConsultaException e) {
			log.error("error al consultar a la persona", e);
			DerechohabientesBusinessException.throwException("", e.getSituacion());
		}
		
		if(personasEncontradas != null && !personasEncontradas.isEmpty()) {
			idPersonas = new ArrayList<Long>();
			
			for(Fisica fisicaE: personasEncontradas) {
				if(fisicaE.getIdPersona() != null) {
					idPersonas.add(fisicaE.getIdPersona());
				}
			}
			
			if(idPersonas.isEmpty()) {
				return personasEncontradas;
			}  else {
				List<GrupoFamiliar> integrantes = grupoFamiliarDao.getIntegrantesGrupoFamiliarByAsignacionNss(
							idAsignacionNss, 
							idPersonas, 
							null);
				
				if(integrantes != null && integrantes.size() > 0) {
					DerechohabientesBusinessException.throwException("", "La persona que intenta registrar ya forma parte de su grupo familiar");
				} else {
					return personasEncontradas;
				}
			}
		}
		
		return personasEncontradas;
	}

	@Override
	public Boolean esAsegurado(Long idPersona)
			throws DerechohabientesBusinessException, Exception {
		List<AsignacionNSS> nsss = grupoFamiliarDao.getAsignacionNss(idPersona);
		
		if(nsss != null && !nsss.isEmpty()) {
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean esPatron(Long idPersona)
			throws DerechohabientesBusinessException, Exception {
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			
		List<SujetoObligado> patrones =  sujetoObligadoServiceBusinessRemote.listarRegistrosPatronalesPorPersona(persona);
			
		if(patrones != null && !patrones.isEmpty()) {
			return true;
		} 
		
		return false;
	}

	@Override
	public Boolean esRepresentanteLegal(Long idPersona)
			throws DerechohabientesBusinessException, Exception {
		
		return representanteLegalServiceBusinessRemote.isRepresentanteLegal(idPersona);
	}
	
	@Override
	public Map<String, Boolean> getRolesPorPersona(Long idPersona, Boolean parentescoAsegurado) throws DerechohabientesBusinessException, Exception {
		Map<String,Boolean> result = new HashMap<String, Boolean>();
		
		parentescoAsegurado = parentescoAsegurado == null ? false : parentescoAsegurado;
		Boolean isAsegurado = !parentescoAsegurado ? this.esAsegurado(idPersona) : parentescoAsegurado;
		Boolean isPatron = this.esPatron(idPersona);
		Boolean isRL = this.esRepresentanteLegal(idPersona);
		
		result.put("isAsegurado", isAsegurado);
		result.put("isPatron", isPatron);
		result.put("isRL", isRL);
		
		return result;
	}

	@Override
	public Map<String, Object> esAseguradoPatronORLConDescripcion(Long idPersona)
			throws DerechohabientesBusinessException, Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isAPRL = false;
		
		isAPRL = this.esAsegurado(idPersona);
		
		if(!isAPRL) {
			isAPRL = this.esPatron(idPersona);
			
			if(!isAPRL) {
				isAPRL =  this.esRepresentanteLegal(idPersona);
				if(isAPRL) {
					result.put("rol", "Representante Legal");
				} else {
					result.put("rol", "Ninguno");
				}
			} else {
				result.put("rol", "Patr&oacute;n");
			}
		} else {
			result.put("rol", "Asegurado");
		}
		
		result.put("isAPRL", isAPRL);
		return result;
	}

	/**
	 * Metodo para verifica si la persona tiene asignado un nss o cuenta con algun patron activo
	 * se retorna un true cuando es patron o tiene nss y un false cuando no cuenta con ninguno de los dos
	 * @param idPersona - id de la persona que se buscara
	 * @return boolean 
	 */
	@Override
	public Boolean esAseguradoOPatronORepresentanteLegal(Long idPersona) throws DerechohabientesBusinessException, Exception {

		Boolean isAPRL = false;
		
		isAPRL = this.esAsegurado(idPersona);
		
		if(!isAPRL) {
			isAPRL = this.esPatron(idPersona);
			
			if(!isAPRL) {
				return this.esRepresentanteLegal(idPersona);
			}
		}
		
		return isAPRL;
		
	}

	 
	@Override
	public GrupoFamiliar getDatosVigenciaPorNss(String numNSS)
			throws DerechohabientesBusinessException {
		GrupoFamiliar asegurado = null;
		AsignacionNSS nss = null;
		
		try {
			nss = grupoFamiliarDao.getAsignacionNss(numNSS);
			
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("No fue posible localizar el nss", "No fue posible localizar el nss");
		}
		
		if(nss != null ) {
			asegurado = this.getDatosVigenciaPorAsignaicionNss(nss);
		}
		
		return asegurado;
	}

	
	@Override
	public GrupoFamiliar getDatosVigenciaPorIdAsignacion(Long idAsignacionNSS)
			throws DerechohabientesBusinessException {
		
		GrupoFamiliar asegurado = null;
		AsignacionNSS nss = null;
		
		try {
			nss = grupoFamiliarDao.getAsignacionNssByIdAsignacionNss(idAsignacionNSS);
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("No fue posible localizar el nss", "No fue posible localizar el nss");
		}
		
		if(nss != null) {
			asegurado = this.getDatosVigenciaPorAsignaicionNss(nss);
		}
		
		return asegurado;
	}

	@Override
	/**
	 * Metodo para obtener los datos de la vigencia y adscripcion del asegurado pensionado
	 * @param idPersona - El id de la persona a la que se le buscara su nss
	 * @return GrupoFamiliar - El objeto grupo familiar que incluye los datos del asegurado
	 */
	public GrupoFamiliar getDatosWidgetVigencia(Long idPersona)
			throws DerechohabientesBusinessException {
		List<AsignacionNSS> nssEncontrados = null;
		GrupoFamiliar asegurado = null;
		
		try {
			nssEncontrados = grupoFamiliarDao.getAsignacionNss(idPersona);
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("La persona no cuenta con nss", "La persona no cuenta con nss");
		}
		
		if(nssEncontrados != null && nssEncontrados.size() > 0) {
			if(nssEncontrados.size() == 1) {
				
				asegurado = this.getDatosVigenciaPorAsignaicionNss(nssEncontrados.get(0));
				
			} else {
				DerechohabientesBusinessException.throwException("La persona cuenta con mas de un nss asociado", "La persona cuenta con mas de un nss asociado");
			}
		} else {
			DerechohabientesBusinessException.throwException("La persona no cuenta con nss", "La persona no cuenta con nss");
		}
		
		return asegurado;
	}
	
	private GrupoFamiliar getDatosVigenciaPorAsignaicionNss(AsignacionNSS nss) throws DerechohabientesBusinessException{
		Long idAsignacion = nss.getIdAsignacionNSS();
		GrupoFamiliar asegurado = null;
		CabezaGrupoFamiliar cabeza = null;
		
		try {
			cabeza = this.cabezaGrupoFamiliar(idAsignacion);
		} catch(DerechohabientesBusinessException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Ocurrio un error al querer obtener los datos de la cabeza del grupo familiar",e);
			DerechohabientesBusinessException.throwException("", "No fue posible encontrar los datos de la cabeza de grupo familiar");
		}
		
		log.debug("Los datos de la cabeza son: " + cabeza);
		if(cabeza != null) {
			asegurado = new GrupoFamiliar();
			List<Long> parentescos = new ArrayList<Long>();
			parentescos.add(ParentescoEnum.ASEGURADO.getId());
			parentescos.add(ParentescoEnum.PENSIONADO.getId());
			
			//Verificamos cuantos integrantes estan registrados en el grupo
			Long numeroDeIntegrantesRegistrados = grupoFamiliarDao.getNumeroDeIntegrantesPorListParentesco(idAsignacion, parentescos);


			log.debug("Se encontraron " + numeroDeIntegrantesRegistrados + " integrantes en el grupo");
			//si la consulta regresa algo quiere decir que por lo menos el asegurado ya esta registrado
			if(numeroDeIntegrantesRegistrados.intValue() > 0) {
				asegurado.setIndRegistrado(1);
			} else {
				//si la consulta regresa 0 es que no hay nadie registrado
				asegurado.setIndRegistrado(0);
			}

			
			asegurado.setAsignacionNSS(nss);

			//Se agregan los datos que se tienen hasta antes del registro
			Derechohabiente derechohabiente =  new Derechohabiente();
			derechohabiente.setIdPersona(nss.getIdPersona());
			derechohabiente.setCurp(nss.getCurp());
			derechohabiente.setSexo(nss.getSexo());
			derechohabiente.setNombre(nss.getNombre());
			derechohabiente.setPrimerApellido(nss.getPrimerApellido());
			derechohabiente.setSegundoApellido(nss.getSegundoApellido());
			derechohabiente.setLugarNacimiento(nss.getLugarNacimiento());
			derechohabiente.setFechaNacimiento(nss.getFechaNacimiento());

			asegurado.setParentesco(cabeza.getCalidadParentesco());
			asegurado.setEstadoDerechohabiente(cabeza.getEstadoDerechohabiente());
			asegurado.setSubEstadoDerechohabiente(cabeza.getSubEstadoDerechohabiente());
			asegurado.setDerechohabiente(derechohabiente); 
			asegurado.setFechaInicioVigencia(cabeza.getFechaInicioVigencia());
			asegurado.setFechaFinVigencia(cabeza.getFechaFinVigencia());


		} else {
			DerechohabientesBusinessException.throwException("", "No fue posible encontrar los datos de la cabeza de grupo familiar");
		}
		
		return asegurado;
		
	}

	@Override
	/**
	 * Metodo para obtener los grupos familiares en los que se encuentra registrada una persona
	 * @param nssActual, el numero de seguridad social del grupo familiar actual - puede ir nulo, sirve para incluir o noincluir el grupo familiar con ese nss
	 * @param incluirActual - boolean, sirve para indicar si se quiere que el nssActual aparezca dentro de la lista dde grupos familiares - puede ir nulo
	 * @return List<GrupoFamiliar> - Lista con los grupos familiares en los que se encuentra la persona
	 */
	public List<GrupoFamiliar> getGruposFamiliaresPorPersona(Long idPersona,
			String nssActual, Boolean incluirActual)
			throws DerechohabientesBusinessException {
		
		List<GrupoFamiliar> gruposFamiliaresPersona = null;
		
		try {
			gruposFamiliaresPersona = grupoFamiliarDao.getGruposFamiliaresPorPersona(idPersona, nssActual, incluirActual);
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error: " + e.getSituacion(), e);
			throw e;
		} catch (Exception e) {
			log.error("error al buscar otros grupos familiares", e);
			DerechohabientesBusinessException.throwException("", "Error al buscar otros grupos familiares relacionados");
		}
		
		return gruposFamiliaresPersona;
	}
	
	/**
	 * Metodo que devuelve un grupo familiar en base al asignacion que recibe y setea atributo para indicar si ya esta
	 * registrado en BD
	 * @param idAsignacion
	 * @return GrupoFamiliar 
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	 public GrupoFamiliar getCabezaGrupaFamilarRegistrada(Long idAsignacion) throws DerechohabientesBusinessException, Exception {
		 CabezaGrupoFamiliar cabeza = null;
		 GrupoFamiliar asegurado = null;
		 ArrayList<Long> parentescoCabeza = new ArrayList<Long>();
		 
		 parentescoCabeza.add(new Long(ParentescoEnum.ASEGURADO.getId()));
		 parentescoCabeza.add(new Long(ParentescoEnum.PENSIONADO.getId()));
		 try {
				cabeza = this.cabezaGrupoFamiliar(idAsignacion);
			} catch(DerechohabientesBusinessException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Ocurrio un error al querer obtener los datos de la cabeza del grupo familiar",e);
				DerechohabientesBusinessException.throwException("", "No fue posible encontar los datos de la cabeza de grupo familiar");
			}
			
			log.debug("Los datos de la cabeza son: " + cabeza);
			if(cabeza != null) {
				
				//Verificamos cuantos integrantes estan registrados en el grupo
				Long numeroDeIntegrantesRegistrados = grupoFamiliarDao.getNumeroDeIntegrantesPorListParentesco(idAsignacion, parentescoCabeza);
				if(numeroDeIntegrantesRegistrados.intValue() > 0) {
					asegurado = this.getCabezaGrupoFamiliar(idAsignacion);
					asegurado.setIndRegistrado(1);
				} else {
					AsignacionNSS nss = this.getAsignacionNssByIdAsignacion(idAsignacion);
					asegurado = this.llenarGrupoConCabezaYNSs(cabeza, nss);
					asegurado.setIndRegistrado(0);
				}
		
			} else {
				DerechohabientesBusinessException.throwException("", "No fue posible encontrar los datos de la cabeza de grupo familiar");
			}
	
	
	
	return asegurado;

	 
	 }

	 private GrupoFamiliar llenarGrupoConCabezaYNSs(CabezaGrupoFamiliar miCabezaGF, AsignacionNSS miAsignacionNss) {
			
			GrupoFamiliar miGrupoFamiliar = new GrupoFamiliar();
			miGrupoFamiliar.setAsignacionNSS(miAsignacionNss);
			
			//Se agregan los datos que se tienen hasta antes del registro
			Derechohabiente derechohabiente =  new Derechohabiente();
			derechohabiente.setCurp(miAsignacionNss.getCurp());
			derechohabiente.setSexo(miAsignacionNss.getSexo());
			derechohabiente.setNombre(miAsignacionNss.getNombre());
			derechohabiente.setPrimerApellido(miAsignacionNss.getPrimerApellido());
			derechohabiente.setSegundoApellido(miAsignacionNss.getSegundoApellido());
			derechohabiente.setLugarNacimiento(miAsignacionNss.getLugarNacimiento());
			derechohabiente.setFechaNacimiento(miAsignacionNss.getFechaNacimiento());
			derechohabiente.setMesRegistroNac(miAsignacionNss.getMesRegistroNac());
			derechohabiente.setAnioRegistroNac(miAsignacionNss.getAnioRegistroNac());
			derechohabiente.setIdPersona(miAsignacionNss.getIdPersona());
			
			miGrupoFamiliar.setEstadoDerechohabiente(miCabezaGF.getEstadoDerechohabiente());
			miGrupoFamiliar.setSubEstadoDerechohabiente(miCabezaGF.getSubEstadoDerechohabiente());
			
			miGrupoFamiliar.setDerechohabiente(derechohabiente); 
			
			return miGrupoFamiliar;
			
		}
	@Override
	public Boolean tieneDomicilioYUMF(Long idAsignacionNss, Long idPersona) {
		return grupoFamiliarDao.validarDomicilioYUmfIntegrante(idAsignacionNss, idPersona, true, true);
	}
	 
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Parentesco obtenerParentesco(Long idParentesco) throws DerechohabientesBusinessException, Exception{
		
		if( idParentesco != null )
			return catalogosDao.getCatalogoParentesco(idParentesco);
		
		return null;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarPorParentescos(
			Long idAsignacionNss, List<Long> idParentescos,
			Boolean consultaVigencia) throws DerechohabientesBusinessException,
			Exception{
		
		return grupoFamiliarDao.findGrupoFamiliarPorParentescos(idAsignacionNss, idParentescos, consultaVigencia);
		
	}
	
	
	@Override
	public List<GrupoFamiliar> obtenerIntegrantesEnLista(List<Long> idPersonas, Long idAsignacionNss) throws DerechohabientesBusinessException {
		return grupoFamiliarDao.getIntegranteEnLista(idPersonas, idAsignacionNss);
	}
	
	
	@Override
	public Map<String,Object> buscarMedicoEnTurnoActivo(GrupoFamiliar grupoFamiliar){
		log.debug("Se buscara el medico en turno activo");
		Map<String,Object> result = new HashMap<String, Object>();
		
		AsignacionNSS asignacionNss = grupoFamiliar.getAsignacionNSS();
		
		MedicoEnTurno medicoEnTurnoActivo = null;
		Boolean encontrado = false;
		Boolean cambioPosible = true;
		Boolean fechaEncontrada = false;
		Date fechaCambioMedico = null;
		
		try {
			List<GrupoFamiliar> integrantesEnUmf = this.getFechaYMedicoExistenteEnUmf(asignacionNss.getIdAsignacionNSS(), grupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			if(integrantesEnUmf != null) {
				log.debug("los integrantes no vienen nulos");
				Long parentesco = grupoFamiliar.getParentesco().getIdParentesco();
				log.debug("El parentesco de la persona es" + parentesco);
				if(!integrantesEnUmf.isEmpty()) {
					if(parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId()) || parentesco.equals(ParentescoEnum.CONYUGE.getId())) {
						log.debug("El parentesco permite el cambio de medico");
						Date fechaUltimoCambio = null;
						
						for(GrupoFamiliar integrante: integrantesEnUmf) {
							if(integrante.getFechaCambioTurnoMedico() != null) {
								fechaUltimoCambio =  integrante.getFechaCambioTurnoMedico();
							}
						}
						
						fechaCambioMedico = fechaUltimoCambio != null ? fechaUltimoCambio : fechaCambioMedico;
						
						
						Long dias = null;
						
						if(fechaCambioMedico!= null){
							long tiempo1 = new Date().getTime();
							
							long tiempo2 = fechaCambioMedico.getTime();
							Long diasTranscrurridos = (tiempo1 - tiempo2) /(24 * 60 * 60 * 1000);
							dias =  diasTranscrurridos;
						}
						if(dias != null) {
							fechaEncontrada = true;
							if(dias < 365){
								encontrado = true;
								cambioPosible = false;
								medicoEnTurnoActivo = integrantesEnUmf.get(0).getMedicoEnTurno();
							} else {
								encontrado = true;
								cambioPosible = true;
								medicoEnTurnoActivo = integrantesEnUmf.get(0).getMedicoEnTurno();
							}
						} else {
							fechaEncontrada = false;
							fechaCambioMedico = null;
							encontrado = true;
							cambioPosible = true;
							medicoEnTurnoActivo = integrantesEnUmf.get(0).getMedicoEnTurno();
						}
					} else {
						encontrado = true;
						cambioPosible = false;
						if(integrantesEnUmf.get(0).getFechaCambioTurnoMedico() != null) {
							fechaEncontrada = true;
							fechaCambioMedico = integrantesEnUmf.get(0).getFechaCambioTurnoMedico();
						}
						
						medicoEnTurnoActivo = integrantesEnUmf.get(0).getMedicoEnTurno();
					}
				}
			}
			
			result.put("error", false);
		} catch (Exception e) {
			e.printStackTrace();
			medicoEnTurnoActivo = null;
			result.put("error", true);
			result.put("mensaje", "Ocurrio un error al consultar la existencia de integrantes dentro de la UMF seleccionada");
		}
		
		result.put("encontrado", encontrado);
		result.put("medico", medicoEnTurnoActivo);
		result.put("fechaCambio", fechaCambioMedico != null ? DateUtils.dateToStringConFormato(fechaCambioMedico, "dd/MM/yyyy") : null);
		result.put("cambioPosible", cambioPosible);
		result.put("fechaEncontrada", fechaEncontrada);
		
		return result;
	}

	@Override
	public GrupoFamiliar getAseguradoInconsistente(String nss) throws DerechohabientesWebSserviceException {
		GrupoFamiliar aseguradoInconsistente = null;
			aseguradoInconsistente = vigenciaDerechosWSClientRemote.getInfoAsegurado(nss);
			
//			if(aseguradoInconsistente!=null && aseguradoInconsistente.getDerechohabiente().getIdPersona() != null) {
//
//					try{
//						Fisica fisica = personaBusinessRemote.getPersonaFisica(aseguradoInconsistente.getDerechohabiente().getIdPersona());
//						if(fisica != null) {
//							Derechohabiente der = new Derechohabiente();
//							der.setExpedienteElectronico(aseguradoInconsistente.getDerechohabiente().getExpedienteElectronico());
//							der.setAsignacionNSS(aseguradoInconsistente.getAsignacionNSS());
//							der.setCurp(fisica.getCurp());
//							der.setNombre(fisica.getNombre());
//							der.setPrimerApellido(fisica.getPrimerApellido());
//							der.setSegundoApellido(fisica.getSegundoApellido());
//							der.setSexo(fisica.getSexo());
//							der.setLugarNacimiento(fisica.getLugarNacimiento());
//							der.setFechaNacimiento(fisica.getFechaNacimiento());
//							der.setEstadoCivil(fisica.getEstadoCivil());
//							
//							aseguradoInconsistente.setDerechohabiente(der);
//						}
//					} catch(Exception e) {
//						e.printStackTrace();
//					}
//					
//			}
		return aseguradoInconsistente;
	}
	

	/**
	 * Obtiene un SujetoObligado en base a la clave general del patron 
	 * 
	 * @param id Long cveIdPatronGeneral
	 * @return SujetoObligado
	 */
	public SujetoObligado obtenerObligadoDeGeneral(Long id){
		
		Long cveIdPatronSO = sujetoObligadoServiceBusinessRemote.getCvePatronSujetoObligadoPorCveIdPatronGeneral(id);
		
		SujetoObligado sujeto = new SujetoObligado();
		sujeto.setCveIdSujetoObligado(cveIdPatronSO);
		
		return sujeto;
		
	}
	
	@Override
	public AsignacionNSS getAsignacionNssSinPersonaCL3(String nss, Boolean segundoYTercerNivel)
			throws DerechohabientesBusinessException, Exception  {
		AsignacionNSS asignacion = null;
		try{
			
			asignacion = grupoFamiliarDao.getAsignacionNssCL3(nss);

			if(segundoYTercerNivel) {
				//siempre consultamos para saber si trae inconsistencia
				GrupoFamiliar aseguradoInconsistente = this.getAseguradoInconsistente(nss);
				
				if(asignacion == null) {
					if( aseguradoInconsistente == null || aseguradoInconsistente.getAsignacionNSS().getEstadoInconsistencia().equals(EstadoInconsistenciaVigenciaEnum.NO_EXISTE_NSS.getId()) ){
						DerechohabientesBusinessException.throwException("No se encontr&oacute; el NSS","No se encontr&oacute; el NSS");
					} else {
						asignacion = aseguradoInconsistente.getAsignacionNSS();
					}
				} else {
					if(aseguradoInconsistente != null) {
						asignacion.setEstadoInconsistencia(aseguradoInconsistente.getAsignacionNSS().getEstadoInconsistencia());
					}
				}
			} else {
				if(asignacion == null) {
					DerechohabientesBusinessException.throwException("No se encontr&oacute; el NSS","No se encontr&oacute; el NSS");
				}
			}
				
		}catch(NonUniqueResultException e){
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_DATOS, ""+e.getCause());
		}
		
		asignacion.setEstudiante(true);
		
		return asignacion;
	}

	@Override
	public Long getNumeroDeIntegrantesPorListParentescoCL3(Long idAsignacionNss, List<Long> idParentesco) {
		return grupoFamiliarDao.getNumeroDeIntegrantesPorListParentescoCL3(idAsignacionNss, idParentesco);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public GrupoFamiliar getIntegranteGrupoFamiliarSinVigencia(Long idAsignacionNss, Long idPersona) throws Exception{
		return grupoFamiliarDao.getIntegranteSinVigencia(idAsignacionNss, idPersona);
	}
	
	public GrupoFamiliar getIntegranteGrupoFamiliarSinVigenciaCL3(Long idAsignacionNss, Long idPersona) throws Exception{
		return grupoFamiliarDao.getIntegranteSinVigenciaCL3(idAsignacionNss, idPersona);
	}
	

	
	@Override
	public GrupoFamiliar getCabezaGrupaFamilarRegistrada(AsignacionNSS asignacionNss, CabezaGrupoFamiliar cabeza) throws DerechohabientesBusinessException {
		 
		GrupoFamiliar asegurado = null;
		ArrayList<Long> parentescoCabeza = new ArrayList<Long>();
		Long numeroDeIntegrantesRegistrados = 0L;
		String mensajeError = null;
		
		parentescoCabeza.add(new Long(ParentescoEnum.ASEGURADO.getId()));
		 	
		if(cabeza != null && asignacionNss != null ) {
			
			
			
			
			if(cabeza.getEsEstudiante()){
				// ----------------------------------------
				// Busqueda en dit_grupo_familiar_cl3
				// ----------------------------------------
				numeroDeIntegrantesRegistrados = this.getNumeroDeIntegrantesPorListParentescoCL3(cabeza.getAsignacionNSS(), parentescoCabeza);
				
			}else{
				
				// ----------------------------------------
				// Busqueda en dit_grupo_familiar
				// ----------------------------------------
				parentescoCabeza.add(new Long(ParentescoEnum.PENSIONADO.getId()));
				numeroDeIntegrantesRegistrados = this.getNumeroDeIntegrantesPorListParentesco(cabeza.getAsignacionNSS(), parentescoCabeza);
			}
			
			
			
			log.debug("numeroDeIntegrantesRegistrados: "+numeroDeIntegrantesRegistrados);
			
			if(numeroDeIntegrantesRegistrados.intValue() > 0) {
				
				//-------------------------------------------------------------------------
				// Existe un asegurado en GrupoFamiliar
				// Puede ser que no sea la misma persona de la AsignacionNSS
				//-------------------------------------------------------------------------
				
				
				if(cabeza.getEsEstudiante()){
					try{
						
						asegurado = this.getIntegranteGrupoFamiliarSinVigenciaCL3(cabeza.getAsignacionNSS(), asignacionNss.getIdPersona());
					}catch (Exception e){
						log.debug("ocurio un error cachado al consultar el grupo familiar", e);
					}
					
				}else{
					
					try{
						asegurado = this.getIntegranteGrupoFamiliarSinVigencia(cabeza.getAsignacionNSS(), asignacionNss.getIdPersona());
					}catch (Exception e){
						log.debug("ocurio un error cachado al consultar el grupo familiar CL3", e);
					}
					
				}
				
				
				
				if(asegurado == null) {
					mensajeError = "No fue posible localizar al integrante asegurado/pensionado dentro del grupo familiar con el NSS proporcionado";
					DerechohabientesBusinessException.throwException(mensajeError, mensajeError);
							
				} else {
					try{
						grupoFamiliarDao.getIntegranteWs(cabeza.getAsignacionNSS(), asignacionNss.getIdPersona());
					} catch (DerechohabientesBusinessException e) {
						mensajeError = "El asegurado/pensionado se encuentra registrado dentro del grupo familiar, pero no pudo obtenerse informaci&oacute;n acerca de su vigencia.";
						DerechohabientesBusinessException.throwException(mensajeError, mensajeError);
					}
				}
				
				
				asegurado.setIndRegistrado(1);
				
				asegurado.setEstadoDerechohabiente(cabeza.getEstadoDerechohabiente());
				asegurado.setSubEstadoDerechohabiente(cabeza.getSubEstadoDerechohabiente());
				asegurado.setFechaInicioVigencia(cabeza.getFechaInicioVigencia());
				asegurado.setFechaFinVigencia(cabeza.getFechaFinVigencia());
				
				
				
				
			} else {
				//AsignacionNSS nss = this.getAsignacionNssByIdAsignacion(idAsignacion);
				
				if(cabeza.getEstadoDerechohabiente() != null) {
					/*List<Long> estadosNoValidos = new ArrayList<Long>();
					estadosNoValidos.add(EstadoDerechohabienteEnum.BAJA.getId());
					
					if(cabeza.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())) {
						estadosNoValidos.add(EstadoDerechohabienteEnum.FALLECIDO.getId());
					}
					//Long idEstadoCabeza = cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
					
					if(estadosNoValidos.contains(idEstadoCabeza)) {
						mensajeError = "La cabeza de grupo familiar no se encuentra registrada como derechohabiente, no es posible realizar el registro " +
								"ya que no cuenta con un estado v&aacute;lido";
					} else*/
					if(asignacionNss != null && asignacionNss.getLugarNacimiento() == null) {
						mensajeError = "El asegurado o pensionado no cuenta con lugar de nacimiento, acuda a su subdelegaci&oacute;n";
					}
					
					if(mensajeError != null) {
						DerechohabientesBusinessException.throwException(mensajeError,mensajeError);
					}
					
					asegurado = this.llenarGrupoConCabezaYNSs(cabeza, asignacionNss);
					asegurado.setIndRegistrado(0);
					
				} else {
					
					DerechohabientesBusinessException.throwException("La cabeza de grupo familiar no tiene un estado v&aacute;lido",
							"La cabeza de grupo familiar no tiene un estado v&aacute;lido");
				}
				
			}
			
			
		} else {
			DerechohabientesBusinessException.throwException("", "No fue posible encontrar los datos de la cabeza de grupo familiar");
		}



		return asegurado;

	 
	 }

	@Override
	public Boolean actualizarIDEESIncorrectos(Integer numeroFilas) {
		
		return grupoFamiliarDao.actualizarIDEE(numeroFilas);
		
	}

	@Override
	public Boolean actualizarIDEESCL3Incorrectos(Integer numeroFilas) {
		return grupoFamiliarDao.actualizarIDEECL3(numeroFilas);
	}
	
	

	@Override
	public Boolean actualizarIDEESAUX(Integer numeroFilas) {
		return grupoFamiliarDao.actualizarIDEETablaAux(numeroFilas);
	}

	@Override
	public List<GrupoFamiliar> getIntegrantesPorCurp(Long idAsignacionNSS,
			String curp, Boolean consultarPorDatosBasicosRenapo) throws DerechohabientesBusinessException {
		
		List<GrupoFamiliar> grupo =null;
		
		grupo = grupoFamiliarDao.getIntegrantePorCurp(idAsignacionNSS, curp);
		
		if(grupo == null || grupo.isEmpty()) {
			if(consultarPorDatosBasicosRenapo) {
				
			}
		}
		
		return grupo;
	}

	@Override
	public Long generarArchivoConCurps(Integer numeroFilas, Long idMinimo) {
		
		idMinimo = grupoFamiliarDao.generarArchivoConCurp(numeroFilas,idMinimo);
		
		return idMinimo;
		
	}
	
	
	/**
	  * Metotodo encargado de validar si existe una persona en algun grupo familiar con la lista de parentescos que se enlista
	  * @param idPersona
	  * @param parentesco
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	public boolean validaPersonaExisteEnGruposFamiliaresPorParentesco(Long idPersona,	List<Long> parentesco) throws DerechohabientesBusinessException, Exception{
		 if(idPersona == null){
			 return false;
		 }
		 return grupoFamiliarDao.validaPersonaExisteEnGruposFamiliaresPorParentesco(idPersona, parentesco);
	 }

	@Override
	public GrupoFamiliar getInfoAsegurado(String nss)
			throws DerechohabientesWebSserviceException {
		GrupoFamiliar aseguradoInconsistente = null;
		aseguradoInconsistente = vigenciaDerechosWSClientRemote.getInfoAsegurado(nss);
		
		return aseguradoInconsistente;
	}

	@Override
	public void generarArchivoNSSVigentesDomicilio() {
		grupoFamiliarDao.getNSSActivosConDomicilio();
		
	}
	
	@Override
	public List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByNss(String numNSS) throws DerechohabientesBusinessException,Exception {
		
		List<GrupoFamiliar> result = grupoFamiliarDao.findDatosBasicosintegrantes(numNSS);
		
		return result;
	}
	
	@Override
	public PersonaDomicilio findDomicilioByIdPersona(Long idPersona) {
		
		PersonaDomicilio personaDomicilio = grupoFamiliarDao.getPersonaFDom(idPersona);
		
		return personaDomicilio;
	}
	
	
	/**
     * Servicio que consulta en almacenes los ultimos 3 movimientos de los asegurados
     * devuelve las fechas del movimiento y los datos basicos del patron
     * acutalmente solo movimientos de baja
     * @param strNss String con el NSS a 11 posiciones
     * @return List<DetallePeriodoMovimientoAfiliatorioPatron> con el detalle del movimiento y el patron
     * @throws DerechohabientesWebSserviceException
     */
	@Override
	public List<DetallePeriodoMovimientoAfiliatorioPatron> getUltimosMovimientosPatronesAsegurado (String strNss) throws DerechohabientesBusinessException, Exception {
		
		List<DetallePeriodoMovimientoAfiliatorioPatron> lstMovimientos= null;
		List<DetallePeriodoMovimientoAfiliatorioPatron> lstMovimientosFinal= null;
		
		log.debug("Se consultara a los patrones con nss [" + strNss +"]");
		
		
		try {
			lstMovimientos  = wsClient.getUltimosMovimientosPatronesAsegurado(strNss);
		} catch (DerechohabientesWebSserviceException e) {
			log.error("Ocurrio un error al consultar la lista de patrones en el WS" , e);
			DerechohabientesBusinessException.throwException("Ocurrio un error al consultar la lista de patrones en el WS", e.getMessage());
		}
		
		try{
			if(lstMovimientos != null && !lstMovimientos.isEmpty()){
				lstMovimientosFinal = new ArrayList<DetallePeriodoMovimientoAfiliatorioPatron>();
				for(DetallePeriodoMovimientoAfiliatorioPatron movimiento : lstMovimientos) {
					SujetoObligado patron = movimiento.getSujetoObligado();
					if(movimiento.getCveIdPatronGeneral() != null){
						log.debug("el id que llego fue [" +movimiento.getCveIdPatronGeneral()+ "]");
						 patron = sujetoObligadoServiceBusinessRemote.getDatosBasicosPatronPorIdPatronGeneral(movimiento.getCveIdPatronGeneral());
					}else{
						if(patron.getModalidad().getNumModalidad().equalsIgnoreCase("37/")) {
							patron.setModalidad(sujetoObligadoServiceBusinessRemote.getModalidad(
								patron.getModalidad().getNumModalidad()));
						}
						patron.setDigVerificador(DeltaUtils.generaDigitoVerificadorRP(patron.getNumeroRegistroPatronal()+patron.getModalidad().getNumModalidad())+"");
						
					}
					
					//Aqui deberia ir la seccion para buscar el tipo de movimiento pero no fue requerido
					movimiento.setSujetoObligado(patron);
					lstMovimientosFinal.add(movimiento);
					
				}
			}
		}catch(Exception e){
			log.error("Ocurrio un error la llenar a los patrones del WS de ulitmos Patrones", e);
			DerechohabientesBusinessException.throwException("Ocurrio un error la llenar a los patrones del WS de ulitmos Patrones", e.getMessage());
		}
		return lstMovimientosFinal;
	}

	@Override
	public GrupoFamiliar getVigenciaYservicioMedicoWsIntegranteGf(String nss, Long idAsignacionNSS, Long idPersona)
			throws DerechohabientesBusinessException {
		
		GrupoFamiliar integrante = grupoFamiliarDao.getIntegranteWs(idAsignacionNSS, idPersona);
		if(integrante != null)
			integrante.setConDerechoSm(grupoFamiliarDao.getServicioMedicoDerechohabiente(nss, idPersona));
		return integrante;
	}
	
	
}

