package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;


import java.math.BigDecimal;
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
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.RegistroDerechohabientesDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.BajaDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.AgregadoMedicoServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TurnoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
/**
 * @version 2 Modificado Juan Manuel Marquez Hernandez 03/07/2012
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Stateless( name = "registroDerechohabienteService", mappedName = "registroDerechohabienteService")
public class RegistroDerechohabienteService extends AbstractServiceBusiness implements RegistroDerechohabienteServiceRemote, 
						RegistroDerechohabienteServiceLocal	{

	@EJB
	private SolicitudServiceLocal solicitudServiceLocal;
	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDao;
	@EJB
	private RegistroDerechohabientesDaoLocal registroDerechohabienteDaoLocal;
	@EJB
	private CatalogosDaoLocal catalogosDao;
	@EJB
	private UmfServiceLocal umfServiceLocal;
	@EJB
	private MedicoEnTurnoDaoLocal medicoEnTurnoDaoLocal;
	@EJB 
	private AgendarCitaServiceLocal agendarCitaService;
	@EJB 
	private CorreccionDerechohabienteServiceLocal correccionDerechohabienteServiceLocal;
	@EJB 
	private BajaDerechohabienteEntityLocal bajaDerechohabiente;
	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	@EJB
	private FinalizaSolicitudServiceLocal finalizaSolicitudServiceLocal;
	@EJB
	private TramiteDocumentosServiceLocal tramiteDocumentosServiceLocal;
	@EJB
	private RequisitosMinimosServiceLocal requisitosMinimosServiceLocal;
	@EJB
	private CambioClinicaServiceLocal cambioClinicaServiceLocal;
	@EJB
	private ConsumidorServiciosMediosContactoLocal consumidorServiciosMediosContactoLocal;
	@EJB
	private AgregadoMedicoServiceLocal agregadoMedicoServiceLocal;
	@EJB
	private IdeeServiceLocal ideeServiceLocal;
	@EJB
	private BajaDerechohabienteServiceLocal bajaDerechohabienteServiceLocal;
	
	@EJB(name="domicilioServiceBusiness" ,mappedName="domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	@EJB(name = "documentoProbatorioServiceBusiness",mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@EJB(name = "personaBusiness", mappedName = "personaBusiness") 
	private PersonaBusinessRemote personaBusinessRemote;
	@EJB(name = "calificacionesPersonaBusinessService", mappedName = "calificacionesPersonaBusinessService")
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	
	@Override
	public PersonaDomicilio getPersonaDom(Long idPersona, Long tipoDomicilio) throws Exception {		
		return registroDerechohabienteDaoLocal.getPersonaDom(idPersona, tipoDomicilio);
	}

	@Override
	public boolean maximoParentesco(Long idAsignacionNss, Long idParentesco) throws Exception {
		boolean validado = false;
		List<GrupoFamiliar> listaParentesco = null;
		
		listaParentesco = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacionNss, idParentesco, EstadoDerechohabienteEnum.VIGENTE.getId());
		if(listaParentesco == null)
			listaParentesco = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacionNss, idParentesco, EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		if(listaParentesco == null)
			listaParentesco = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacionNss, idParentesco, EstadoDerechohabienteEnum.CON_DERECHO.getId());
		try {
			if(idParentesco.equals(ParentescoEnum.HIJOS.getId())){
				if(listaParentesco.size() < Constants.HIJOS)
					validado = true;
			}else
			if(idParentesco.equals(ParentescoEnum.PADRES.getId())){
				if(listaParentesco.size() < Constants.PADRES)
					validado = true;
			}else
			if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())){
				if(listaParentesco.size() < Constants.CONYUGUE)
					validado = true;
			}else
			if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())){			
				if(listaParentesco.size() < Constants.CONCUBINA){
					listaParentesco = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacionNss, ParentescoEnum.CONYUGE.getId(),EstadoDerechohabienteEnum.VIGENTE.getId());
					if(listaParentesco.size() == 0){
						validado = true;
					}
				}
			}
		} catch (Exception e) {
			log.error("exception.RNGD0022.25", e);
			throw new DerechohabientesBusinessException("exception.RNGD0022.25");
		}
		
		return validado;
	}

	@Override
	public boolean modalidadParentesco(Long idModalidad, Long idParentesco) throws Exception {				
		String modalidades = null;
		try {
			if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId())){
				modalidades = Constants.MOD_ASEG;
			}else{
				modalidades = Constants.MOD_RN;
			}
		} catch (Exception e) {
			throw new DerechohabientesBusinessException("exception.RNGD0138.143");
		}					
		
			
		return registroDerechohabienteDaoLocal.findModalidadParentesco(idModalidad, modalidades);
	}
	
	@Override
	public Solicitud registraSolicitud(TramiteRegistroDerechohabiente registro, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, SolicitudNoValidaException {
		
		Solicitud objSolicitud = new Solicitud();
		Date fechaCreacion = new Date();
		try {					
			
			
			//Establecemos el tipo de solicitud como registro de derechohabiente
			objSolicitud.setTipoSolicitud(new TipoSolicitud());
			objSolicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor().longValue());
			
			//Establecemos el estado de la solicitud como registrada
			objSolicitud.setEstadoSolicitud(new EstadoSolicitud());
			objSolicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
			objSolicitud.setFechaSolicitud(fechaCreacion);
			objSolicitud.setFechaPresentacion(fechaCreacion);
			
			//Si el usuario no es nulo lo establecemos como solicitante
			if(registro.getUsuario() != null && StringUtils.isNotBlank(registro.getUsuario().getCveIdUsuario())) {
				log.debug("El usuario no viene null y es " + registro.getUsuario().getCveIdUsuario());
				objSolicitud.setSolicitante(registro.getUsuario());
			} else if(StringUtils.isNotBlank(registro.getDatosAsegurado().getCurp())) {
				objSolicitud.setSolicitante(new Usuario());
				objSolicitud.getSolicitante().setCveIdUsuario(registro.getDatosAsegurado().getCurp());
				objSolicitud.getSolicitante().setUsuario(registro.getDatosAsegurado().getCurp());
			}
			//Se crea la solicitud
			if(idOrigenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
				objSolicitud.setCitaSolicitud(this.getUmfSolicitud(registro));
				
				try {
					if(registro.getFisica().getSexo() != null && registro.getFisica().getSexo().getIdSexo() != null) {
						Sexo sexo = catalogosDao.getCatalogoSexo(registro.getFisica().getSexo().getIdSexo().longValue());
						registro.getFisica().setSexo(sexo);
					}
					
					if(registro.getFisica().getLugarNacimiento() != null && registro.getFisica().getLugarNacimiento().getClave() != null) {
						EntidadFederativa lugarNacimiento = catalogosDao.getCatalogoEntidadFed(registro.getFisica().getLugarNacimiento().getClave());
						registro.getFisica().setLugarNacimiento(lugarNacimiento);
					}
				} catch(DerechohabientesBusinessException e) {
					log.debug("No se pudieron consultar los catalogos de sexo o de lugar de nacimiento");
				}
			}
			
			
			//Establecemos el origen de la solicitud
			objSolicitud.setOrigenSolicitud(new OrigenSolicitud());
			objSolicitud.getOrigenSolicitud().setIdTipoSolicitud(idOrigenSolicitud);
			
			//Se establecen los datos de persona interesada
			if(registro.getDatosAsegurado() != null) {
				PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
				//Establecemos el tipo de persona interesada
				personaIntSol.setTipoPersonaInteresadaSol(new TipoPerInteresadaSol());
				personaIntSol.getTipoPersonaInteresadaSol().setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
				//Establecemos a la persona interesada
				personaIntSol.setPersona(new Persona());
				personaIntSol.getPersona().setIdPersona(registro.getDatosAsegurado().getIdPersona());
				//Se la agregamos a la solicitud
				objSolicitud.setPersonaInteresadaSolicitud(personaIntSol);
			}
			
			//seteo de las propiedades del tramite
			objSolicitud.setTramites(new ArrayList<Tramite>());
			
			long parentesco = registro.getParentesco().getIdParentesco();
			Parentesco catalogoParentesco = catalogosDao.getCatalogoParentesco(parentesco);
			
			registro.setParentesco(catalogoParentesco);
			
			//razonRes.setIdRazonResultado(RazonResultadoEnum.NORMAL.getId());
			EstadoTramite et = new EstadoTramite();
			et.setIdEstadoTramitePersona(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
			
			Long idTipoTramite = requisitosMinimosServiceLocal.getTipoTramitePorParentesco(parentesco);
			TipoTramite tt = catalogosDao.getTipoTramite(idTipoTramite);
			
			registro.setTipoTramite(tt);
			registro.setRazonResultado(null);
			registro.setEstadoTramite(et);
			registro.setObservacion("");
			registro.setFechaTramite(fechaCreacion);
			registro.setFechaPresentacion(fechaCreacion);
			
			
			
			//seteo de datos especificos del tramite de registro de derehohabiente
			
			
			if(registro.getRazonRegistro() != null && registro.getRazonRegistro().getIdRazonRegistro() != null) {
				Long idRazonRegistro = registro.getRazonRegistro().getIdRazonRegistro();
				RazonRegistro razonReg = catalogosDao.getCatalogoRazonRegistro(idRazonRegistro);
				registro.setRazonRegistro(razonReg);
			}
				
			
			registro.setFechaPresentacion(new Date());
			
			objSolicitud.getTramites().add(registro);
			
			objSolicitud = solicitudBusiness.crear(objSolicitud);
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error al llenar objeto Solicitud", e);
			throw new DerechohabientesBusinessException("exception.guardar.solicitud");
		}
		
		return objSolicitud;
	}
	
	private CitaSolicitud getUmfSolicitud(TramiteRegistroDerechohabiente registro)
			throws DerechohabientesBusinessException, Exception {
		
		/** Programa la cita a la UMF */
		
		CitaSolicitud cita = null;
		
		if(registro.getUsuario().getPerfilUsuario().getIdPerfilUsuario().longValue() == PerfilesEnum.TRAMITADOR.getId().longValue()
				|| registro.getUsuario().getPerfilUsuario().getIdPerfilUsuario().longValue() == PerfilesEnum.AUTORIZADOR.getId().longValue()){
			
			log.debug("Se agrea cita de acuerdo al funcionario:");
			cita = new CitaSolicitud();
			Turno turno = new Turno();
			turno.setIdTurno(TurnoEnum.MATUTINO.getId());
			cita.setUmf(registro.getUsuario().getUsuarioFuncionario().getUnidadMedicaFamiliar());
			log.debug("En la umf id: " + cita.getUmf().getIdUMF() + " y numero: " + cita.getUmf().getDescripcion());
			cita.setTurno(turno);
			cita.setFechaHora(new Date());
			
		}else{
			log.debug("Se generara cita a partir del domicilio");
			cita = agendarCitaService.getCita(registro.getDomicilio());
			log.debug("La cita se gener� en la umf con id: " + cita.getUmf().getIdUMF() + " y numero: " + cita.getUmf().getDescripcion());
			
		}
		
		return cita;
	}

	@Override
	public Solicitud finalizarSolicitudRegistro(Solicitud solicitud)
			throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException, Exception, ImpactaAlmacenesWSException {
		Map<String, Object> resultado;
		resultado = this.finalizaSolicitudRegistroCommon(solicitud, null, null, true, true, false);
		solicitud = (Solicitud) TramiteUtil.getSolicitudFromMap(resultado);
		
		return solicitud;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Map<String, Object> guardarRegistroDerechohabiente(Solicitud solicitud, CabezaGrupoFamiliar cabeza, List<Modalidad> modalidades) throws DerechohabientesBusinessException,
	SolicitudNoValidaException, SolicitudNoEncontradaException,
	SolicitudException , Exception, ImpactaAlmacenesWSException, SolicitudEnProcesoException {
	
		return this.finalizaSolicitudRegistroCommon(solicitud, cabeza, modalidades, false, false, true);
	}
	
	
	
	@Override
	public Solicitud finalizarSolicitudRegistroMovil(Solicitud solicitud,
			CabezaGrupoFamiliar cabeza)
			throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException, Exception, ImpactaAlmacenesWSException {
		Map<String, Object> result = this.finalizaSolicitudRegistroCommon(solicitud, cabeza, null, false, true, true);
		
		return (Solicitud) result.get("solicitud");
	}
	
	@Override
	public Solicitud finalizarSolicitudRegistroTSPI(Solicitud solicitud,
			CabezaGrupoFamiliar cabeza)
			throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException, Exception, ImpactaAlmacenesWSException {
		Map<String, Object> result = this.finalizaSolicitudRegistroCommon(solicitud, cabeza, null, false, false, true);
		
		return (Solicitud) result.get("solicitud");
	}

	private Map<String,Object> finalizaSolicitudRegistroCommon(Solicitud solicitud, CabezaGrupoFamiliar cabeza, 
			List<Modalidad> modalidades, Boolean consultarSolicitud, Boolean consultarModalidades, Boolean generarFirma) throws
	SolicitudNoValidaException, SolicitudNoEncontradaException,
	SolicitudException , Exception, ImpactaAlmacenesWSException, SolicitudEnProcesoException{
		
		Boolean origenInternetCiudadano =false;
		Boolean firmaElectronicaCreada = false;
		
		boolean personaLocalizada = false;
		List<BajaDerechohabienteDto> bajas  = null;
		
		Long idSolicitud = solicitud.getSolicitudId();
		FirmaElectronica firma = null;
		Date fechaInicio = new Date();
		TramiteRegistroDerechohabiente tramiteRegistro = null;
		log.debug("Comienza la finalizacion de registro con id " + idSolicitud + " a las " + fechaInicio);
		//Objeto que se guardara en ditGrupoFamiliar
		GrupoFamiliar grupo = null;
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		//Guardamos los datos de la firma electronica
		if(solicitud.getFirmaElectronica() != null) {
			firma = solicitud.getFirmaElectronica();
			try {
				tramiteDocumentosServiceLocal.insertarFirmaDigital(solicitud);
			} catch(Exception e){
				throw new SolicitudException("No fue posible guardar la firma digital.");
			}
			//si se crea la firma indicamos que ya esta
			firmaElectronicaCreada = true;
		}
		//verificamos si es necesario consultar la solicitud
		if(consultarSolicitud) {
			log.debug("se consultara la solicitud de registro con id: " + solicitud.getSolicitudId());
			//Consultamos la solicitud a finalizar
			solicitud = solicitudBusiness.consultar(solicitud);
		}
		
		//Si la solicitud no es nula 
		if(solicitud != null) {
			
			origenInternetCiudadano = solicitud.getOrigenSolicitud() != null && (solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId())
					|| solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()));
			//Verificamos que la solicitud contenga al menos un tramite
			if(solicitud.getTramites().isEmpty()) {
				throw new SolicitudNoValidaException("La solicitud no contiene tramites");
			} else {
				
				tramiteRegistro = this.getTramiteRegistroFromSolicitud(solicitud);
				
				//Si no encontramos ningun tramite de registro lanzamos una excepcion
				if(tramiteRegistro == null) {
					throw new SolicitudNoValidaException("No existen tramites de registro en la solicitud");
				}
				
				if(tramiteRegistro.getTramiteId() == null) {
					throw new SolicitudNoValidaException("No es posible procesar lar solicitud ya que tanto la solicitud como el tr&aacute;mite no cuentan con identificador.");
				}
				
				Boolean existeRegistro = registroDerechohabienteDaoLocal.existeRegistroDerechohabiente(tramiteRegistro.getTramiteId());
				
				if(existeRegistro) {
					throw new SolicitudEnProcesoException("La solicitud de "+tramiteRegistro.getTipoTramite().getDescripcion()+" con folio <strong>" + solicitud.getNoFolioSolicitud() + "</strong> ya ha sido procesada. " +
							"Para ver los documentos resultantes del tr&aacute;mite es necesario ver el detalle de la solicitud en la secci&oacute;n de solicitudes atendidas" +
							" en la pantalla de grupo familiar.");
				}
				
				//obtenemos el asignacion nss 
				AsignacionNSS nss = tramiteRegistro.getDatosAsegurado();
				
				//consultamos la cabeza en caso de ser necesario
				if(cabeza == null) {
					cabeza = grupoFamiliarServiceLocal.cabezaGrupoFamiliar(nss.getIdAsignacionNSS());
				}
				
				//Es el asegurado al que se registrara?
				Long idParentesco = tramiteRegistro.getParentesco().getIdParentesco();
				//Boolean isAsegurado = TramiteUtil.isAseguradoPensionado(idParentesco);
				//Boolean isConyuge =  TramiteUtil.isConyuge(idParentesco);
				Boolean recienNacido = tramiteRegistro.getRazonRegistro().getIdRazonRegistro().equals(RazonRegistroEnum.RECIEN_NACIDO.getId());
				
				Fisica personaDer = tramiteRegistro.getFisica();
				
				//Calculamos el agregado medico, en base a los datos de la persona y los de la cabeza de grupo familiar 
				if(personaDer.getFechaNacimiento() == null && personaDer.getAnioRegistroNac() == null){
					DerechohabientesBusinessException.throwException("No se pudo concluir el registro, es forzoso la fecha o a�o de nacimiento.");
				}
						
				try {
					//Se registra o se actualiza a la persona, sus medios de contacto y sus calificaciones ante el institudo
					personaDer = this.guardaDatosPersonalesDerechohabiente(personaDer, origenInternetCiudadano);
					//actualizamos la persona del XML
					tramiteRegistro.setFisica(personaDer);
				} catch (Exception e) {
					log.error("Error al registrar a la persona",e);
					DerechohabientesBusinessException.throwException("Ocurrio un error al registrar a la persona - " + e.getCause().getMessage());
				}
				
				try {	
					//Una vez que ya creamos o actualizamos a la persona, la asociamos al tramite
					solicitudBusiness.agregarPersonaATramite(tramiteRegistro.getTramiteId(), personaDer.getIdPersona());
				} catch (TramiteNoEncontradoException e) {
					log.error("Error al asociar a la persona al tramite",e);
					DerechohabientesBusinessException.throwException("Ocurrio un error al asociar a la persona al tramite");
				}
				
				//Ahora registramos el domicilio en caso de que no se tenga
				Domicilio domicilio = tramiteRegistro.getDomicilio();
				
				Boolean guardarDomicilio = StringUtils.isNotBlank(domicilio.getAsentamiento().getClave());
				
				if(guardarDomicilio && domicilio.getClave() == null) {
					try{
						domicilio = domicilioServiceBusinessRemote.registrarDomicilio(domicilio);
						tramiteRegistro.setDomicilio(domicilio);
					} catch(Exception e) {
						DerechohabientesBusinessException.throwException("No fue posible registrar el domicilio");
					}
				}
				
				PersonaDomicilio personaDomicilio = new PersonaDomicilio();
				
				if(guardarDomicilio) {
					//Generamos la relacion persona domicilio
					try {
						
						personaDomicilio.setCvePersonaDomicilio(tramiteRegistro.getCvePersonaDomicilio());
						personaDomicilio.setPersona(personaDer);
						personaDomicilio.setFechaRegistroAlta(new Date());
						personaDomicilio.setDomicilio(domicilio);	
						personaDomicilio.setTipoDomicilio(new TipoDomicilio());
						Long tipoDomicilio = TipoDomicilioEnum.PARTICULAR.getId();
						personaDomicilio.getTipoDomicilio().setClave(tipoDomicilio.intValue());
						//guardamos la relacion persona domicilio
						personaDomicilio = registroDerechohabienteDaoLocal.savePersonaDomicilio(personaDomicilio);
					} catch(Exception e) {
						DerechohabientesBusinessException.throwException("Error al generar la relacion persona domicilio");
					}
				}
				
				//Obtenemos el catalogo del parentesco para saber la calidad maxima y poder calcular el agregado medico
				Parentesco catParentesco = null;
				try {
					catParentesco = catalogosDao.getCatalogoParentesco(idParentesco);
				} catch(Exception e) {
					DerechohabientesBusinessException.throwException("Ocurrio un error al generar la calidad del derechohabiente");
				}
				
				//solo si las modalidades son nulas, vacias o se envia la bandera de consultar las modalidades
				if(modalidades == null || modalidades.isEmpty() || consultarModalidades) {
					modalidades = grupoFamiliarServiceLocal.getModalidadesActivas(nss.getIdAsignacionNSS());
				}
				
				SujetoObligado patronUltimoMov = cabeza.getPatronSujetoObligado();
				
				//todas las modalidades efm
				if(modalidades == null || modalidades.isEmpty()){
					//Patron actual
					if(patronUltimoMov != null) {
						modalidades = new ArrayList<Modalidad>();
						modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
					}
				}
				
				//Establecemos todos los datos del parentesco
				tramiteRegistro.setParentesco(catParentesco);
				
				//Calculamos el agregado medico
				String agregado = agregadoMedicoServiceLocal.getAgregadoMedico(cabeza, catParentesco.getCalidadMaxima().intValue(), personaDer, null);
				
				//TODO se deja la fecha que traiga el XML ya que en el registro ya no se va a poner la fecha de cambio de consultorio al dia de hoy
				/*
				//fecha de cambio de medico
				Date fechaCambioMedico = null;
				//Si el parentesco es conyuge o asegurado si la fecha es nula el cambio de medico sera el dia de hoy
				if(isAsegurado || isConyuge) {
					fechaCambioMedico = tramiteRegistro.getFechaCambioMedico() == null ? new Date() : tramiteRegistro.getFechaCambioMedico();
				} else {
					//si es cualquier otro parentesco la fecha de cambio de medico sera la que se haya obtenido desde un principio aunque sea nula
					fechaCambioMedico = tramiteRegistro.getFechaCambioMedico() == null && tramiteRegistro.getIndSeleccionMedico().equals(1) ? new Date(): tramiteRegistro.getFechaCambioMedico();
				}
				//Modificamos la fecha que se tiene en el xml para que quede con los cambios mas recientes
				tramiteRegistro.setFechaCambioMedico(fechaCambioMedico);*/
				Derechohabiente derechohabiente = new Derechohabiente();		
				try {
					//llenamos el derechohabiente
					
					derechohabiente.setFechaRegistroAlta(new Date());
					derechohabiente.setIdPersona(personaDer.getIdPersona());
					derechohabiente.setNombre(personaDer.getNombre());
					derechohabiente.setPrimerApellido(personaDer.getPrimerApellido());
					derechohabiente.setSegundoApellido(personaDer.getSegundoApellido());
					derechohabiente.setSexo(personaDer.getSexo());
					derechohabiente.setLugarNacimiento(personaDer.getLugarNacimiento());
					derechohabiente.setAsignacionNSS(nss);
					derechohabiente.setCurp(personaDer.getCurp());
					derechohabiente.setFechaNacimiento(personaDer.getFechaNacimiento());
					derechohabiente.setFacebook(personaDer.getFacebook());
					derechohabiente.setCorreoElectronico(personaDer.getCorreoElectronico());
					derechohabiente.setTelefonoFijo(personaDer.getTelefonoFijo());
					derechohabiente.setTelefonoMovil(personaDer.getTelefonoMovil());
					derechohabiente.setTwitter(personaDer.getTwitter());
					
					//Obtenemos los datos del grupo familiar
					grupo = this.llenaGrupoFamiliar(tramiteRegistro, derechohabiente, cabeza, agregado);
					//lo ponemos porque en este momento ya se consulto y ya trae todos los atributos
					tramiteRegistro.setMedicoEnTurno(grupo.getMedicoEnTurno());
					//Se setea el domicilio
					if(guardarDomicilio) {
						grupo.setCvePersonaDomicilio(personaDomicilio.getCvePersonaDomicilio());
					}
					//se verifica que en caso de que la razon de registro sea recien nacido el nombre
					//tambien sea RECIEN NACIDO, sino lo es, se quitara la marca de RN
					recienNacido = recienNacido ? personaDer.getNombre().trim().equals("RECIEN NACIDO") : recienNacido;
					//Se setea si es recien nacido
					grupo.setIndRecienNacido(recienNacido ? 1 : null);
					//Se crea el string de expediente electronico
					String expedienteElectronico = ideeServiceLocal.generarIDEE(nss.getNssStr(), 
							grupo.getCalidad().intValue(), 
							personaDer.getNombre(), 
							personaDer.getPrimerApellido(), 
							personaDer.getSegundoApellido(), 
							personaDer.getFechaNacimiento(), 
							personaDer.getMesRegistroNac(), 
							personaDer.getAnioRegistroNac());
					
					//Se setea el expediente electronico antes de guardar al derechohabiente
					derechohabiente.setExpedienteElectronico(expedienteElectronico);
					//guardamos o actualizamos al derechohabiente
					registroDerechohabienteDaoLocal.saveDerechohabiente(derechohabiente);
				
					//se cambia el servicio para validar si ya existe el integrante del grupo familiar para actualizarlo
					List<Long> idPersonas = new ArrayList<Long>();
					List<Long> lstTipoBaja = new ArrayList<Long>();
					idPersonas.add(personaDer.getIdPersona());
					lstTipoBaja.add(TipoBajaDerechohabienteEnum.ADMIN.getId());
					
					bajas = bajaDerechohabiente.getBajaDerechohabiente(nss.getIdAsignacionNSS(), idPersonas, lstTipoBaja, true);
					
					
					if(bajas != null && !bajas.isEmpty()) {
						personaLocalizada = true;
					}
					
						
					
					
					if(personaLocalizada){
						registroDerechohabienteDaoLocal.actualizaFechaBajaGrupoFamiliartoNull(nss.getIdAsignacionNSS().longValue(), personaDer.getIdPersona().longValue() );
						registroDerechohabienteDaoLocal.actualizaGrupoFamiliar(grupo);
					}else{
						//Una vez que hemos guardado al derechohabiente, creamos al grupo familiar
						registroDerechohabienteDaoLocal.saveGrupoFamiliar(grupo);
					}
					
					
				} catch(Exception e) {
					e.printStackTrace();
					//log.error("Ocurrio un error al registrar a la persona dentro del grupo",e);
					DerechohabientesBusinessException.throwException("Ocurrio un error al registrar a la persona dentro del grupo familiar");
				}
				
				//Se guarda la informacion al momento del tramite
				PersonaDomicilio personaDomicilioAsegurado = new PersonaDomicilio();
				personaDomicilioAsegurado = registroDerechohabienteDaoLocal.getPersonaDom(nss.getIdPersona(), (long)4);

				tramiteRegistro.setDomicilioIdAsegurado(personaDomicilioAsegurado.getDomicilio().getClave().longValue());
				tramiteRegistro.setDomicilioIdBeneficiario(tramiteRegistro.getDomicilio().getClave().longValue());
				tramiteRegistro.setCveIdUmf(tramiteRegistro.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
				tramiteRegistro.setCveIdDelegacion(tramiteRegistro.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getId());
				tramiteRegistro.setCveIdSubdelegacion(tramiteRegistro.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getId());
				tramiteRegistro.setCveEstadoAsegurado(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
				tramiteRegistro.setCveEstadoBeneficiario(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente());  //JAS ¿De donde se obtien este estado?

				//Se obtiene indicador de mismo sexo para registro de Conyuge o Concubinario
				String mismoSexoConcubinario = "";
				String mismoSexoConyuge = "";
				String mismoSexo = "";
				if( idParentesco == ParentescoEnum.CONYUGE.getId()  || idParentesco == ParentescoEnum.CONCUBINARIO.getId() ){
					Integer idSexoDerechohabiente = personaDer.getSexo().getIdSexo();
					if (nss.getCurp() != null ) {
						String sexoCurp = nss.getCurp().substring(10,11);
						String sexoDer = idSexoDerechohabiente == 1 ? "H" : "M"; 
						mismoSexo = sexoCurp.equals(sexoDer) ? "1" : "0";
					}	 else {
						if ( nss.getSexo() != null) {
							mismoSexo = nss.getSexo().getIdSexo() == idSexoDerechohabiente ? "1" : "0";
						}
					}
					if( idParentesco == ParentescoEnum.CONYUGE.getId() ){
						mismoSexoConyuge = mismoSexo;
						log.debug("Indicador de mismo sexo Conyuge = " + mismoSexoConyuge);
					} else {
						mismoSexoConcubinario = mismoSexo;
						log.debug("Indicador de mismo sexo Concubinario = " + mismoSexoConcubinario);
					}	
				}
				tramiteRegistro.setIndConcubinarioMismoSexo(mismoSexoConcubinario); 
				tramiteRegistro.setIndConyugeMismoSexo(mismoSexoConyuge); 

				//guardamos en la tabla de DIT_REGISTRO_DERECHOABIENTE
				try {
					registroDerechohabienteDaoLocal.saveRegistroDerechohabiente(tramiteRegistro);
				} catch(Exception e) {
					DerechohabientesBusinessException.throwException("No fue posible guardar el registro de derechohabiente");
				}
				
				//Solo si el origen no es internet generamos la firma, ya que para internet
				if(generarFirma && !firmaElectronicaCreada) {
					log.debug("Se generara el sello para el registro de derechohabiente");
					try {
						firma = tramiteDocumentosServiceLocal.generaFirmaElectronica(tramiteRegistro.getDatosAsegurado(),solicitud, tramiteRegistro.getTipoTramite().getDescripcion());
					} catch(Exception e) {
						log.error("ocurrio un error al generar la firma digital relacionada a la solicitud",e);
					}
				}
				
				if(firma != null) {
					solicitud.setCadenaOriginal(firma.getCadenaOriginal());
					solicitud.setSecuenciaDeNotaria(firma.getSecuenciaNotaria());
					solicitud.setSelloDigital(firma.getRecibo());
					solicitud.setNumeroSerieCertificado(firma.getSerialCertificado());
				}
				
				result.put("grupo", grupo);
				//verificamos si te sienen que guardar los documentos
				if(origenInternetCiudadano) {
					//guardamos los documentos probatorios del tramite
					try {
						documentoProbatorioServiceBusinessRemote.guardarDocumentosCapturados(solicitud);
					} catch(DocumentoProbatorioException e) {
						log.error("Ocurrio un error al guardar los documentos",e);
					} catch (TramiteNoEncontradoException e) {
						log.error("No se encontro tramite", e);
					}
				}
				
				tramiteRegistro.setEstadoTramite(new EstadoTramite());
				Long idEstadoTramite = EstadoTramiteEnum.CERRADO.getId();
				tramiteRegistro.getEstadoTramite().setIdEstadoTramitePersona(idEstadoTramite.intValue());
				
				solicitud.setFirmaElectronica(firma);
				solicitud.setTramites(new ArrayList<Tramite>());
				solicitud.getTramites().add(tramiteRegistro);
				
				//solo si el origen es internet se guardara la circunscripcion y cambio de clinica en la misma transaccion
				if(origenInternetCiudadano) {
					//Verificamos si el tramite requiere circunscripcion o cambio de clinica7
					this.guardarCircunscripcionCambioUmf(solicitud, cabeza, grupo);
				}
				
				solicitudServiceLocal.actualizarXmlTramite(tramiteRegistro);
				
				//mandamos a llamar al servicio local para que se establezcan el resultado y la razon del resultado
				try{
					String observaciones = null;
					if (StringUtils.isNotBlank(tramiteRegistro.getObservacion())) {
						observaciones = tramiteRegistro.getObservacion().length() > 500 ? tramiteRegistro.getObservacion().substring(0,500) : tramiteRegistro.getObservacion();
					}
					solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), observaciones, null);
					solicitud.setEstadoSolicitud(new EstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId().intValue()));
				} catch (DerechohabientesBusinessException e) {
					log.error("Ocurrio un error al actulizar la solicitud");
					throw new SolicitudException();
				}
				

				result.put("solicitud", solicitud);
				
				//Se manda llamar al WS
				try {
					Date fechaInicioWS = new Date();
					this.log.debug("antes de mandar llamar al servcio de registro para el nss " + nss.getNssStr() + " y la persona "+ personaDer.getIdPersona() +  " a las " + fechaInicioWS);
					finalizaSolicitudServiceLocal.finalizaSolicitudRegistroWeb(solicitud, grupo);
					if(personaLocalizada){
						try {
							//Actualizamos la baja
							BajaDerechohabienteDto bajaDto = bajas.get(0);
							bajaDto.setIndBajaActiva(null);
							bajaDto.setFecRegistroActualizaco(new Date());
							bajaDerechohabienteServiceLocal.actualizarInsertarBaja(bajaDto);

							// -----------------------------------------
							// En el WebService se env�a 0
							// Localmente se asigna a null
							// -----------------------------------------
							bajaDto.setIndBajaActiva(0L);
							TramiteBajaDerechohabiente tramite = new TramiteBajaDerechohabiente(bajaDto.getCveIdTramite());
							tramite.setPersona(derechohabiente);
							Solicitud solicitudBaja = new Solicitud(tramite);
							finalizaSolicitudServiceLocal.finalizaSolicitudBajaDerechohabiente(solicitudBaja, bajaDto, false);
					
						} catch (Exception e1) {
							log.error("Ocurrio un error al actualizar la baja del derechohabiente", e1);
							DerechohabientesBusinessException.throwException("Ocurrio un error al actualizar la baja administrativa [" +e1.getMessage()+"]");
						}
					}
					Date fechaFinWS = new Date();
					this.log.debug("regrese de mandar llamar al servcio de registro para el nss " + nss.getNssStr() + " y la persona "+ personaDer.getIdPersona() +  " a las" + fechaFinWS);
				} catch (IllegalArgumentException e) {
					log.error(e);
					DerechohabientesBusinessException.throwException(e.getMessage());
				} catch (ImpactaAlmacenesWSException e) {
					log.error("Ocurrio un error al mandar el movimiento de registro al ws, con el nss " + nss.getNssStr());
					throw e;
				}
				
				Date fechaFin = new Date();
				log.debug("Termina la finalizacion de registro con id " + idSolicitud + " a las " + fechaFin);
				this.log.debug("El tiempo que se tomo en finalizar el registro fueron " + (fechaFin.getTime() - fechaInicio.getTime()) + " mlsegundos para el nss " + nss.getNssStr());
				
			}
		}
		
		return result;
	}
	
	/**
	 * Metodo para insertar o actualizar a la persona que se registrara como derechohabiente
	 * Asi como actualizar sus medios de contacto y actualizar sus calificaciones
	 * @param personaDer
	 * @return
	 * @throws Exception
	 */
	private Fisica guardaDatosPersonalesDerechohabiente(Fisica personaDer, Boolean actualizarMedios) throws Exception{
		//setearemos los medios en una lista 
		this.preparaMediosContacto(personaDer);
		//Se registra o se actualiza a la persona
		if(personaDer.getIdPersona() == null) {
			CorreoElectronico correo = personaDer.getCorreoElectronico();
			TelefonoFijo telefono = personaDer.getTelefonoFijo();
			log.debug("se creara a la persona ya que no tiene un id de persona");
			//se da de alta a la persona
			personaDer = personaBusinessRemote.altaPersonaFisica(personaDer);
			//Verificamos si viene la curp para poner la calificacion necesaria
			if(StringUtils.isNotBlank(personaDer.getCurp())) {
				//ponemos la calificacion de RENAPO
				calificacionesPersonaBusinessService.calificarRENAPO(personaDer);
			} else {
				//si no venia la curp y la persona es nueva se califica por el imss
				calificacionesPersonaBusinessService.calificarIMSS(personaDer);
			}
			
			personaDer.setTelefonoFijo(telefono);
			personaDer.setCorreoElectronico(correo);
		} else if(personaDer.getIdPersona() != null ) {
			log.debug("Se actualizara la fecha de nacimiento y el estado civil de la persona porque ya tiene un id de persona");
			//Solo se setean los campos que se pueden editar para el asegurado
			Fisica actualizacion = new Fisica();
			//Se actualiza persona
			if(StringUtils.isNotBlank(personaDer.getCurp())) {
				actualizacion.setCurp(personaDer.getCurp());
			}
			//se setea el id de la persona que se actualizara
			actualizacion.setIdPersona(personaDer.getIdPersona());
			//se setea el estaod civil
			actualizacion.setEstadoCivil(personaDer.getEstadoCivil());
			//Se setea la fecha de nacimiento
			actualizacion.setFechaNacimiento(personaDer.getFechaNacimiento());
			//se manda a actualizar la persona
			personaBusinessRemote.actualizarPersona(actualizacion);
			if(actualizarMedios) {
				log.debug("Servicio de registro: Se actualizaran los medios de la persona " + personaDer.getIdPersona());
				//Ponemos la calificacion del IMSS
				//calificacionesPersonaFisicaServiceBusinessRemote.calificarIMSS(personaDer);
				//Se actualizan los medios de contacto de la persona
				consumidorServiciosMediosContactoLocal.procesaActualizacionEnMedios(personaDer);
			}
		}
		//se retorna a la persona creada
		return personaDer;
	}
	
	@Override
	public void setearBanderaDeParentescoSimilar(Fisica persona,Parentesco parentesco) throws Exception {
		Long idParentesco = parentesco.getIdParentesco();
		//Si la persona que se registrara es conyuge o concubinario en otro grupo familiar, le pondremo un indicador
		if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())  || idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
			List<Long> parentescos = new ArrayList<Long>();
			parentescos.add(ParentescoEnum.CONYUGE.getId());
			parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
			try {
				//Buscamos parentescos similares
				parentescoSimilarFlag(persona.getIdPersona(), parentescos, null);
			} catch (Exception e) {
				log.error("Error al consultar parentescos similares", e);
				DerechohabientesBusinessException.throwException("Ocurrio un error al buscar parentescos similares en otros grupos");
			}
		}
	}
	
	/**
	 * Cuando se crea a la persona se setean sus medios
	 * @param fisica
	 */
	private void preparaMediosContacto(Fisica fisica) {
		try {
			//Setearemos todos los 
			fisica.setMediosContacto(new ArrayList<MedioContacto>());
			//Agregamos los medios de contacto
			if(this.setBanderaMedioContacto(fisica.getFacebook()) != null) {
				fisica.getMediosContacto().add(fisica.getFacebook());
			} else {
				fisica.setFacebook(null);
			}
			if(this.setBanderaMedioContacto(fisica.getTwitter()) != null) {
				fisica.getMediosContacto().add(fisica.getTwitter());
			} else {
				fisica.setTwitter(null);
			}
			if(this.setBanderaMedioContacto(fisica.getCorreoElectronico()) != null) {
				fisica.getMediosContacto().add(fisica.getCorreoElectronico());
			} else {
				fisica.setCorreoElectronico(null);
			}
			if(this.setBanderaMedioContacto(fisica.getTelefonoFijo()) != null) {
				fisica.getMediosContacto().add(fisica.getTelefonoFijo());
			} else {
				fisica.setTelefonoFijo(null);
			}
			if(this.setBanderaMedioContacto(fisica.getTelefonoMovil()) != null) {
				fisica.getMediosContacto().add(fisica.getTelefonoMovil());
			} else {
				fisica.setTelefonoMovil(null);
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al preparar los medios de contacto ", e);
		}
	}
	
	/**
	 * Metodo para setear la bandera de medios de contacto
	 * @param medio
	 * @return
	 */
	private MedioContacto setBanderaMedioContacto(MedioContacto medio) {
		if(medio != null) {
			if (medio.getClave() != null) {
				if (medio instanceof CorreoElectronico) {
					CorreoElectronico correo = (CorreoElectronico) medio;
					if (StringUtils.isNotBlank(correo.getCorreo())) {
						correo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return correo;
					} else {
						correo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return correo;
					}
				} else if (medio instanceof Facebook) {
					Facebook facebook = (Facebook) medio;
					if (StringUtils.isNotBlank(facebook.getCuenta())) {
						facebook.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return facebook;
					} else {
						facebook.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return facebook;
					}
				} else if (medio instanceof TelefonoFijo) {
					TelefonoFijo telefonoFijo = (TelefonoFijo) medio;
					if (StringUtils.isNotBlank(telefonoFijo.getClaveLada())) {
						telefonoFijo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return telefonoFijo;
					} else {
						telefonoFijo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return telefonoFijo;
					}
				} else if (medio instanceof TelefonoMovil) {
					TelefonoMovil telefonoMovil = (TelefonoMovil) medio;
					if (StringUtils.isNotBlank(telefonoMovil.getNumero())) {
						telefonoMovil.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return telefonoMovil;
					} else {
						telefonoMovil.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return telefonoMovil;
					}
				} else if (medio instanceof Twitter) {
					Twitter twitter = (Twitter) medio;
					if (StringUtils.isNotBlank(twitter.getCuenta())) {
						twitter.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return twitter;
					} else {
						twitter.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return twitter;
					}
				}
			} else {
				if (medio instanceof CorreoElectronico) {
					CorreoElectronico correo = (CorreoElectronico) medio;
					if (StringUtils.isNotBlank(correo.getCorreo())) {
						correo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return correo;
					} else {
						return null;
					}
				} else if (medio instanceof Facebook) {
					Facebook facebook = (Facebook) medio;
					if (StringUtils.isNotBlank(facebook.getCuenta())) {
						facebook.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return facebook;
					} else {
						return null;
					}
				} else if (medio instanceof TelefonoFijo) {
					TelefonoFijo telefonoFijo = (TelefonoFijo) medio;
					if (StringUtils.isNotBlank(telefonoFijo.getClaveLada())) {
						telefonoFijo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return telefonoFijo;
					} else {
						return null;
					}
				} else if (medio instanceof TelefonoMovil) {
					TelefonoMovil telefonoMovil = (TelefonoMovil) medio;
					if (StringUtils.isNotBlank(telefonoMovil.getNumero())) {
						telefonoMovil.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return telefonoMovil;
					} else {
						return null;
					}
				} else if (medio instanceof Twitter) {
					Twitter twitter = (Twitter) medio;
					if (StringUtils.isNotBlank(twitter.getCuenta())) {
						twitter.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return twitter;
					} else {
						return null;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Se crea nuevo metodo que sera llamado por el anterior para incluir los datos que sean necesarios
	 * @param registro
	 * @param cabeza
	 * @param integrante
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public void guardarCircunscripcionCambioUmf(Solicitud solicitud, CabezaGrupoFamiliar cabeza, GrupoFamiliar integrante) throws DerechohabientesBusinessException {
		
		TramiteRegistroDerechohabiente registro = null;
		
		registro = this.getTramiteRegistroFromSolicitud(solicitud);
		
		Long parentesco = registro.getParentesco().getIdParentesco();
		AsignacionNSS nss = registro.getDatosAsegurado();
		integrante.setAsignacionNSS(nss);
		Boolean isAsegurado = parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId());
		//Solo si el que se esta registrando no es el asegurado verificaremos si es necesario crear un tramite de 
		//autorizacion para recibir servicios en circunscripcion foranea o de cambio de clinica
		if(!isAsegurado) {
			MedicoEnTurno adscripcionAsegurado = null;
			GrupoFamiliar registroAsegurado = null;
			
			try {
				adscripcionAsegurado = grupoFamiliarDao.getMedicoEnTurnoPorIntegrante(nss.getIdAsignacionNSS(), nss.getIdPersona());
			} catch(Exception e) {
				DerechohabientesBusinessException.throwException("No fue posible localizar los datos de adscripcion del asegurado/pensionado");
			}
			
			if(adscripcionAsegurado == null) {
				DerechohabientesBusinessException.throwException("No fue posible localizar los datos de adscripcion del asegurado/pensionado");
			}
			
			//Obtenemos la umf del asegurado y del integrante a registrar
			Long idUmfAsegurado = adscripcionAsegurado.getUnidadMedicaFamiliar().getIdUMF();
			Long idUmfIntegrante = integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
			
			//Verificamos si las umf son distinatas de ser asi verificamos que tramite tenemos que hacer
			if(!idUmfAsegurado.equals(idUmfIntegrante)) {
				//Verificamos si las umfs estan dentro de la misma circunscripcion
				Boolean mismaCircunscripcion = umfServiceLocal.mismaCircunscripcion(idUmfAsegurado,idUmfIntegrante);
				//Si las umfs estan dentro de la misma circunscripcion crearemos un tramite de cambio de clinica
				if(mismaCircunscripcion) {
					
					
					//Tramiteque hara el cambio de clinica
					TramiteCorreccionDerechohabiente tramiteCambio = new TramiteCorreccionDerechohabiente();
					tramiteCambio.setIdPersona(registro.getFisica().getIdPersona());
					tramiteCambio.setPersona(integrante.getDerechohabiente());
					tramiteCambio.setMedicoEnTurno(registro.getMedicoEnTurno());
					tramiteCambio.setDomicilio(registro.getDomicilio());
					
					cambioClinicaServiceLocal.agregarTramiteCambioClinicaDependiente(tramiteCambio, nss, cabeza.getPatronImss().equals(1), 
							null, solicitud.getSolicitudId(), integrante, false, null);
				} else { //De lo contrario si no estan dentro de la misma delegacion crearemos un tramite de autorizacion 
					//para recibir servicios en circunscripcion foranea
					try{
						registroAsegurado = grupoFamiliarServiceLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
					}catch (Exception e) {
						DerechohabientesBusinessException.throwException("No fue posible consultar al integrante asegurado/pensionado del grupo familiar");
					}
					
					TramiteCircunscripcionForanea circunscripcion = new TramiteCircunscripcionForanea();
					circunscripcion.setMedicoEnTurnoOrigen(registroAsegurado.getMedicoEnTurno());
					circunscripcion.setMedicoEnTurnoDestino(integrante.getMedicoEnTurno());
					circunscripcion.setDomicilioOrigen(registroAsegurado.getDomicilio());
					circunscripcion.setDomicilioDestino(integrante.getDomicilio());
					circunscripcion.setPersona(integrante.getDerechohabiente());
					
					Usuario usuario = registro.getUsuario();
					nss = registro.getDatosAsegurado();
					correccionDerechohabienteServiceLocal.guardarTramiteCircunscripcionDependiente(circunscripcion, usuario, nss, solicitud.getSolicitudId(), integrante);
					
				}
			} else {//si no es circunscripcion o cambio de clinica
				
				correccionDerechohabienteServiceLocal.guardarTramiteCambioMedicoDependiente(integrante, solicitud.getSolicitudId(), false);
			}
		}
	}
	
	/**
	 * 
	 * @param registro
	 * @param derechohabiente
	 * @param cabeza
	 * @param agregadoMedico
	 * @return
	 * @throws Exception
	 */
	private GrupoFamiliar llenaGrupoFamiliar(TramiteRegistroDerechohabiente registro,Derechohabiente derechohabiente, CabezaGrupoFamiliar cabeza, String agregadoMedico) throws Exception{
		
		GrupoFamiliar gf = new GrupoFamiliar();
		Fisica fisica = registro.getFisica();
		
		Long idParentesco = registro.getParentesco().getIdParentesco();
		Integer idSexo = fisica.getSexo().getIdSexo();
		Long numCalidad = registro.getParentesco().getCalidadMinima().longValue();
		Long calidadMaxima = registro.getParentesco().getCalidadMaxima().longValue();
		
		gf.setDerechohabiente(derechohabiente);
		gf.setAsignacionNSS(registro.getDatosAsegurado());
		gf.setDomicilio(registro.getDomicilio());
		gf.setFechaRegistroAlta(new Date());	
		gf.setParentesco(registro.getParentesco());
		
		MedicoEnTurno medicoEnTurno = medicoEnTurnoDaoLocal.getMedicoEnTurnoById(registro.getMedicoEnTurno().getIdMedicoContultorioTurno());
		gf.setMedicoEnTurno(medicoEnTurno);
		
		gf.setFechaCambioTurnoMedico(registro.getFechaCambioMedico());
		gf.setAgregadoMedico(agregadoMedico);
		
	
		Boolean isAsegurado = idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		
		if(!isAsegurado){			
			if(idParentesco == ParentescoEnum.PADRES.getId()){
				if(idSexo.longValue() == SexoEnum.MUJER.getId()){
					numCalidad = calidadMaxima;
				}
			}else{
				
				Long calidadMasAlta = grupoFamiliarDao.getCalidadMasAltaRegistradaPorParentesco(registro.getDatosAsegurado().getIdAsignacionNSS(),
						idParentesco);
				
				if(calidadMasAlta != null) {
					
					numCalidad = calidadMasAlta.longValue() + 1L;
					
					if(numCalidad.longValue() > calidadMaxima.longValue()) {
						numCalidad = calidadMaxima;
					}
				} 
			}
		} else  {
			numCalidad = 1L;
		}
		
		//calculamos el agregado de afiliacion
		String agregadoAfiliacion = DeltaUtils.getAgregadoIdentidad(numCalidad.intValue(),
				registro.getFisica().getSexo().getIdSexo(), registro.getFisica().getFechaNacimiento(), registro.getFisica().getAnioRegistroNac());
		//seteamos el agregado de afiliacion al grupo familiar
		gf.setAgregadoAfiliacion(agregadoAfiliacion);
		gf.setCalidad(new BigDecimal(numCalidad));
		return gf;
	}
		
	@Override
	public List<GrupoFamiliar> integrantesParentesco(Long idAsignacionNss,
			Long parentesco) throws DerechohabientesBusinessException,
			Exception {		
		return grupoFamiliarDao.findGrupoFamiliarByParentesco(idAsignacionNss, parentesco);		
	}

	@Override
	public void parentescoSimilarFlag(Long idPersona, List<Long> parentescos, Long idPersonaAsegurado)
			throws DerechohabientesBusinessException, Exception {
		
		//Busco integrantes Conyuge o Concubina en otros grupos familiares
		List<GrupoFamiliar> integrantes = grupoFamiliarDao.findIntegrantesDuplicados(idPersona, parentescos, EstadoDerechohabienteEnum.BAJA.getId());
		if(integrantes.size() > 0){			
			List<Long> tiposTramites = new ArrayList<Long>();
			tiposTramites.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
			tiposTramites.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
			
			for(GrupoFamiliar gf : integrantes){
					List<Long> dhs = new ArrayList<Long>();
					dhs.add(gf.getDerechohabiente().getIdPersona());
					List<BajaDerechohabienteDto> bajas = bajaDerechohabiente.getBajaDerechohabiente(gf.getAsignacionNSS().getIdAsignacionNSS(), dhs, tiposTramites, true);

					if(bajas == null || bajas.size() == 0){
						//No hay tramites, entonces registro como duplicado
						gf.setIndSimilarCalDifGpoFam(new Date());
						gf.setFechaRegistroActualizacion(new Date());
						grupoFamiliarDao.updateIntegrante(gf);
					}			
			}
		}
		
	}

	private TramiteRegistroDerechohabiente getTramiteRegistroFromSolicitud(Solicitud solicitud) {
		TramiteRegistroDerechohabiente registro = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteRegistroDerechohabiente) {
				registro = (TramiteRegistroDerechohabiente) tramite;
				break;
			}
		}
		return registro;
	}

	private Parentesco getParentesco(List<Parentesco> parentescos, Long idParentesco) {
		Parentesco parentesco = null;
		
		for(Parentesco par: parentescos) {
			if(par.getIdParentesco().equals(idParentesco)) {
				parentesco = par;
				break;
			}
		}
		return parentesco;
	}
	@Override
	public List<Parentesco> getListaParentescoDisponiblesPorIdAsignacionNSS(
			Long idAsignacionNSS) {
		
		List<Parentesco> catalogoParentescos = null;
		List<Parentesco> parentescos = new ArrayList<Parentesco>();
		
		
		try {
			catalogoParentescos = catalogosDao.getCatalogoParentescos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Long> parentescosAsegurados = new ArrayList<Long>();
		parentescosAsegurados.add(ParentescoEnum.ASEGURADO.getId());
		parentescosAsegurados.add(ParentescoEnum.PENSIONADO.getId());
		
		//se valida si ya cuenta con registro como derechohabiente
		Boolean existeAsegurado = false;
		
		existeAsegurado = !grupoFamiliarDao.getNumeroDeIntegrantesPorListParentesco(idAsignacionNSS, parentescosAsegurados).equals(0L);
		//si no existe el asegurado seteamos todos los parentescos como disponibles
		if(!existeAsegurado) {
			parentescos.add(this.getParentesco(catalogoParentescos, ParentescoEnum.ASEGURADO.getId()));
			parentescos.add(this.getParentesco(catalogoParentescos, ParentescoEnum.PENSIONADO.getId()));
		} else {
			//anadimos el parentesco hijo
			parentescos.add(this.getParentesco(catalogoParentescos,ParentescoEnum.HIJOS.getId()));
			//Checamos i podemos anadir el parentesco padre
			Long numeroIntegrantes = null;
			numeroIntegrantes = grupoFamiliarDao.getNumeroDeIntegrantesPorParentesco(idAsignacionNSS, ParentescoEnum.PADRES.getId());
			//si el numero de padre es menor a dos agregamos el parentesco padre
			if(numeroIntegrantes < 2) {
				parentescos.add(this.getParentesco(catalogoParentescos, ParentescoEnum.PADRES.getId()));
			}
			//checamos si existe la consyuge
			Boolean existeConyuge = false;
			Long idEstado = EstadoDerechohabienteEnum.VIGENTE.getId();
			Long idEstadoBaja = EstadoDerechohabienteEnum.BAJA.getId();
			Long idSubEstadoSuspension = SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId();
			
			try {
				existeConyuge = requisitosMinimosServiceLocal.existePersonaConParentesco(idAsignacionNSS, ParentescoEnum.CONYUGE.getId(),idEstado.intValue() , false, null, null);
				if(!existeConyuge){
					existeConyuge = requisitosMinimosServiceLocal.existePersonaConParentesco(idAsignacionNSS, ParentescoEnum.CONYUGE.getId(),idEstadoBaja.intValue() , false, null, idSubEstadoSuspension);
				}
			} catch (DerechohabientesWebSserviceException e) {
				e.printStackTrace();
			}
			
			if(!existeConyuge) {
				parentescos.add(this.getParentesco(catalogoParentescos, ParentescoEnum.CONYUGE.getId()));
				//verificamos si existe la concubina
				Boolean existeConcu = false;
				try {
					existeConcu = requisitosMinimosServiceLocal.existePersonaConParentesco(idAsignacionNSS, ParentescoEnum.CONCUBINARIO.getId(),idEstado.intValue() , false, null, null);
					if(!existeConcu){
						existeConcu = requisitosMinimosServiceLocal.existePersonaConParentesco(idAsignacionNSS, ParentescoEnum.CONCUBINARIO.getId(),idEstadoBaja.intValue() , false, null, idSubEstadoSuspension);
					}
				} catch (DerechohabientesWebSserviceException e) {
					e.printStackTrace();
				}
				
				if(!existeConcu) {
					parentescos.add(this.getParentesco(catalogoParentescos, ParentescoEnum.CONCUBINARIO.getId()));
				}
			} else {
				parentescos.add(this.getParentesco(catalogoParentescos, ParentescoEnum.CONCUBINARIO.getId()));
			}
		}
		
		return parentescos;
	}


	@Override
	public List<RazonRegistro> getListaRazonRegistro(Long idRazonRegistro,
			Long idParentesco) {
		List<RazonRegistro> listaFinal = new ArrayList<RazonRegistro>();
		List<RazonRegistro> listaAux = registroDerechohabienteDaoLocal.findRazonRegistro();
		//bandera para saber si se anadiran las razones laudo, acuerdo, amparo
		Boolean anadirLaudo = false;
		if(idParentesco == null) {
			listaFinal = listaAux;
		} else {
			if(idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
				if(idRazonRegistro == null || idRazonRegistro.equals(-1L) || idRazonRegistro.equals(RazonRegistroEnum.RECIEN_NACIDO.getId())
						|| idRazonRegistro.equals(RazonRegistroEnum.HASTA_16.getId())) {
					listaFinal = listaAux;
				} else if(idRazonRegistro.equals(RazonRegistroEnum.HASTA_25.getId())) {
					listaFinal.add(this.getRazonRegistro(RazonRegistroEnum.HASTA_25.getId(), listaAux));
					anadirLaudo = true;
				} else if(idRazonRegistro.equals(RazonRegistroEnum.MAYOR_A_25.getId())) {
					listaFinal.add(this.getRazonRegistro(RazonRegistroEnum.MAYOR_A_25.getId(), listaAux));
					anadirLaudo = true;
				} else {
					listaFinal.add(this.getRazonRegistro(RazonRegistroEnum.HASTA_25.getId(), listaAux));
					listaFinal.add(this.getRazonRegistro(RazonRegistroEnum.MAYOR_A_25.getId(), listaAux));
					anadirLaudo = true;
				}
			} else {
				listaFinal.add(this.getRazonRegistro(RazonRegistroEnum.NORMAL.getId(), listaAux));
				anadirLaudo = true;
			}
		}
		
		//Si es necesario anadir las razones de laudo
		if(anadirLaudo) {
			//las obtenemos de la lista
			List<RazonRegistro> razonesLaudo = this.getRazonRegistroLaudo(listaAux);
			//si no estan en baja las anadiremos
			if(razonesLaudo != null && !razonesLaudo.isEmpty()) {
				listaFinal.addAll(razonesLaudo);
			}
		}
		
		return listaFinal;
	}
	
	private List<RazonRegistro> getRazonRegistroLaudo(List<RazonRegistro> catalogo) {
		List<RazonRegistro> razones = new ArrayList<RazonRegistro>();
		
		for(RazonRegistro razon: catalogo) {
			Long idRazon = razon.getIdRazonRegistro();
			if(idRazon.equals(RazonRegistroEnum.POR_ACUERDO.getId()) || idRazon.equals(RazonRegistroEnum.POR_AMPARO.getId())
					|| idRazon.equals(RazonRegistroEnum.POR_LAUDO.getId())) {
				
				razones.add(razon);
				
			}
		}
		
		return razones;
	}
	
	private RazonRegistro getRazonRegistro(Long idRazonRegistro, List<RazonRegistro> razones) {
		RazonRegistro razon = null;
		for(RazonRegistro raz : razones) {
			if(raz.getIdRazonRegistro().equals(idRazonRegistro)) {
				razon = raz;
				
				break;
			}
		}
			
		return razon;
	}
}