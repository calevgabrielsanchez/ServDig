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
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.AgregadoMedicoServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EstudiantesServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TurnoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Stateless(name = "estudiantesService", mappedName = "estudiantesService")
public class EstudiantesService extends AbstractServiceBusiness implements
		EstudiantesServiceRemote {

	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name="domicilioServiceBusiness" ,mappedName="domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB
	private CatalogosDaoLocal catalogosDaoLocal;
	@EJB
	private RegistroDerechohabientesDaoLocal registroDerechohabientesDaoLocal;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	@EJB
	private FinalizaSolicitudServiceRemote finalizaSolicitudServiceRemote;
	@EJB
	private MedicoEnTurnoDaoLocal medicoEnTurnoDaoLocal;
	@EJB
	private AgregadoMedicoServiceLocal agregadoMedicoServiceLocal;
	@EJB 
	private IdeeServiceLocal ideeServiceLocal;
	
	private static String MENSAJE_ERROR_REGISTRO = "Ocurri&oacute; un error al registrar al estudiante";
	private static String MENSAJE_ERROR_ACTUALIZACION = "Ocurri&oacute; un error al actualizar al estudiante";
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Map<String, Object> finalizaAsignacionDomicilioUmfEstudiante(TramiteCorreccionDerechohabiente tramite, GrupoFamiliar estudiante, 
			CabezaGrupoFamiliar cabeza, List<Modalidad> modalidades) throws DerechohabientesBusinessException, SolicitudNoValidaException{
		
		Map<String, Object> result = new HashMap<String, Object>();
		AsignacionNSS nss = estudiante.getAsignacionNSS();
		Derechohabiente derechohabiente = estudiante.getDerechohabiente();
		derechohabiente.setAsignacionNSS(nss);
		EstadoDerechohabiente estado = estudiante.getEstadoDerechohabiente();
		SubEstadoDerechohabiente subEstado = estudiante.getSubEstadoDerechohabiente();
		
		MedicoEnTurno medico = medicoEnTurnoDaoLocal.getMedicoEnTurnoById(tramite.getMedicoEnTurno().getIdMedicoContultorioTurno());
		Boolean esNuevoRegistro = false;
		String mensajeError = "";
		tramite.setNss(nss.getNss());
		if(estudiante.getIndRegistrado().equals(0)) {
			estudiante = this.registrarGrupoCL3(tramite,cabeza,modalidades);
			//se obtiene el idee para setearselo al derechohabiente que se seteara para el movimiento
			String idee = estudiante.getDerechohabiente().getExpedienteElectronico();
			//se setea el idee
			derechohabiente.setExpedienteElectronico(idee);
		} else {
			estudiante = actualizaGrupoCL3(estudiante, tramite);
		}
		
		estudiante.setEstadoDerechohabiente(estado);
		estudiante.setSubEstadoDerechohabiente(subEstado);
		estudiante.setAsignacionNSS(nss);
		estudiante.setDerechohabiente(derechohabiente);
		estudiante.setMedicoEnTurno(medico);
		
		mensajeError = esNuevoRegistro ? MENSAJE_ERROR_REGISTRO : MENSAJE_ERROR_ACTUALIZACION;
		
		try {
			finalizaSolicitudServiceRemote.finalizaRegistroEstudiante(estudiante,true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DerechohabientesBusinessException.throwException(mensajeError,mensajeError);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DerechohabientesBusinessException.throwException(mensajeError,mensajeError);
		}
		
		Solicitud solicitud = this.registraSolicitud(tramite);
		result.put("integrante", estudiante);
		result.put("solicitud", solicitud);
		
		
		return result;
	}
	
	private GrupoFamiliar actualizaGrupoCL3(GrupoFamiliar estudiante, TramiteCorreccionDerechohabiente tramite) throws DerechohabientesBusinessException{
		
		Domicilio domicilio = tramite.getDomicilio();
		domicilio = this.guardaDomicilio(domicilio);
		tramite.setDomicilio(domicilio);
		Long idPersonaDomicilio = this.guardaPersonaDomicilio(estudiante.getCvePersonaDomicilio(), estudiante.getDerechohabiente(), domicilio);
		
		estudiante.setFechaRegistroActualizacion(new Date());
		estudiante.setCvePersonaDomicilio(idPersonaDomicilio);
		estudiante.setMedicoEnTurno(tramite.getMedicoEnTurno());
		estudiante.setDomicilio(domicilio);
		try {
			grupoFamiliarDaoLocal.updateIntegranteCL3(estudiante);
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("Ocurrio un error al actualizar al integrante", "Ocurrio un error al actualizar al integrante");
		}
		
		return estudiante;
		
	}
	
	private GrupoFamiliar registrarGrupoCL3(TramiteCorreccionDerechohabiente tramite, CabezaGrupoFamiliar cabeza, List<Modalidad> modalidades) throws DerechohabientesBusinessException {
		
		Domicilio domicilio = tramite.getDomicilio();
		GrupoFamiliar integranteCL3 = new GrupoFamiliar();
		Long idPersonaDomicilio = null;
		
		integranteCL3.setDerechohabiente(new Derechohabiente());
		integranteCL3.getDerechohabiente().setIdPersona(tramite.getPersona().getIdPersona());
		integranteCL3.setMedicoEnTurno(tramite.getMedicoEnTurno());
		
		domicilio = this.guardaDomicilio(domicilio);
		tramite.setDomicilio(domicilio);
		
		idPersonaDomicilio = this.guardaPersonaDomicilio(null, tramite.getPersona(), domicilio);
		
		
		integranteCL3.setDomicilio(domicilio);
		integranteCL3.setCvePersonaDomicilio(idPersonaDomicilio);
		integranteCL3.setAsignacionNSS(new AsignacionNSS());
		integranteCL3.getAsignacionNSS().setIdAsignacionNSS(tramite.getIdAsignacionNss());
		integranteCL3.setFechaCambioTurnoMedico(new Date());
		
		Fisica personaDer = tramite.getPersona();
		//Obtenemos el catalogo del parentesco para saber la calidad maxima y poder calcular el agregado medico
		Parentesco catParentesco = null;
		try {
			catParentesco = catalogosDaoLocal.getCatalogoParentesco(ParentescoEnum.ASEGURADO.getId());
		} catch(Exception e) {
			DerechohabientesBusinessException.throwException("Ocurrio un error al generar la calidad del derechohabiente");
		}
		
		integranteCL3.setParentesco(catParentesco);
		integranteCL3.setCalidad(new BigDecimal(1));
		
		//todas las modalidades efm
		if(modalidades == null || modalidades.size() == 0 ){
			//Patron actual
			modalidades = new ArrayList<Modalidad>();
			modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
		}
		
		//calculamos el agregado de afiliacion
		String agregadoAfiliacion = DeltaUtils.getAgregadoIdentidad(1,
				personaDer.getSexo().getIdSexo(), personaDer.getFechaNacimiento(), personaDer.getAnioRegistroNac());
		
		String agregado = agregadoMedicoServiceLocal.getAgregadoMedico(cabeza, 
				catParentesco.getCalidadMaxima().intValue(), personaDer, null);
		
		integranteCL3.setAgregadoAfiliacion(agregadoAfiliacion);
		integranteCL3.setAgregadoMedico(agregado);
		integranteCL3.setFechaCambioTurnoMedico(new Date());
		integranteCL3.setFechaRegistroAlta(new Date());
		
		log.debug("Se calculara el idee con la fecha: " + personaDer.getFechaNacimiento() + " el mes " + personaDer.getMesRegistroNac() + " y el anio " + personaDer.getAnioRegistroNac());
		
		if(personaDer.getFechaNacimiento() != null || (personaDer.getMesRegistroNac() != null && personaDer.getAnioRegistroNac() != null)) {
			log.debug("se calculara el idee de la persona estudiante con nss " + tramite.getNss());
			Derechohabiente derechohabienteCL3 = new Derechohabiente();
			derechohabienteCL3.setIdPersona(personaDer.getIdPersona());
			//se concatena el nss a 10 posiciones y la calidad del derechohabiente
			String nssCalidad = tramite.getNss() + "|" + integranteCL3.getCalidad();
			//se calcula el idee
			String expedienteElectronico = ideeServiceLocal.generarIDEE(tramite.getNss(), 
					integranteCL3.getCalidad().intValue(), 
					personaDer.getNombre(), 
					personaDer.getPrimerApellido(), 
					personaDer.getSegundoApellido(), 
					personaDer.getFechaNacimiento(), 
					personaDer.getMesRegistroNac(), 
					personaDer.getAnioRegistroNac());
			
			derechohabienteCL3.setExpedienteElectronico(expedienteElectronico);
			derechohabienteCL3.setFechaRegistroAlta(new Date());
			
			integranteCL3.getDerechohabiente().setExpedienteElectronico(expedienteElectronico);
			
			try {
				registroDerechohabientesDaoLocal.saveDerechohabiente(derechohabienteCL3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			log.debug("no se calculara el idee para el estudiante con nss " + tramite.getNss() + " ya que no cuenta con fecha ni con mes y anio de nacimiento");
		}
		
		try {
			grupoFamiliarDaoLocal.updateIntegranteCL3(integranteCL3);
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("Ocurrio un error al actualizar al integrante", "Ocurrio un error al actualizar al integrante");
		}
		
		return integranteCL3;
	}
	
	private Domicilio guardaDomicilio (Domicilio domicilio) throws DerechohabientesBusinessException {
		if(domicilio.getClave() == null) {
			try{
				domicilio = domicilioServiceBusinessRemote.registrarDomicilio(domicilio);
			} catch(Exception e) {
				DerechohabientesBusinessException.throwException("No fue posible registrar el domicilio");
			}
		}
		
		return domicilio;
	}
	
	private Integer formatAnioNac(Integer anioNac){
		if(anioNac != null){
			if(anioNac < 1900)
				return anioNac + 1900;
			else
				return anioNac;
		}else{
			return null;
		}
	}
	
	private Long guardaPersonaDomicilio(Long idCvePersonaDomicilio, Fisica persona, Domicilio domicilio) throws DerechohabientesBusinessException{
		
		PersonaDomicilio personaDomicilio = new PersonaDomicilio();
		
		//Generamos la relacion persona domicilio
		try {
			
			personaDomicilio.setCvePersonaDomicilio(null);
			personaDomicilio.setPersona(persona);
			personaDomicilio.setFechaRegistroAlta(new Date());
			personaDomicilio.setDomicilio(domicilio);	
			personaDomicilio.setTipoDomicilio(new TipoDomicilio());
			Long tipoDomicilio = TipoDomicilioEnum.PARTICULAR.getId();
			personaDomicilio.getTipoDomicilio().setClave(tipoDomicilio.intValue());
			//guardamos la relacion persona domicilio
			personaDomicilio = registroDerechohabientesDaoLocal.savePersonaDomicilio(personaDomicilio);
		} catch(Exception e) {
			DerechohabientesBusinessException.throwException("Error al generar la relacion persona domicilio");
		}
		
		return personaDomicilio.getCvePersonaDomicilio();
		
	}
	

	private Solicitud registraSolicitud(TramiteCorreccionDerechohabiente correccion)
			throws DerechohabientesBusinessException, SolicitudNoValidaException {
		
		Solicitud objSolicitud = new Solicitud();
		Date fechaCreacion = new Date();
		try {					
			
			
			//Establecemos el tipo de solicitud como registro de derechohabiente
			objSolicitud.setTipoSolicitud(new TipoSolicitud());
			objSolicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor().longValue());
			
			//Establecemos el estado de la solicitud como registrada
			objSolicitud.setEstadoSolicitud(new EstadoSolicitud());
			objSolicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId().intValue());
			objSolicitud.setFechaSolicitud(fechaCreacion);
			objSolicitud.setFechaPresentacion(fechaCreacion);
			
			//Si el usuario no es nulo lo establecemos como solicitante
			if(correccion.getUsuario() != null) {
				objSolicitud.setSolicitante(correccion.getUsuario());
			}
			
			objSolicitud.setCitaSolicitud(this.getUmfSolicitud(correccion));
			
			//Establecemos el origen de la solicitud
			objSolicitud.setOrigenSolicitud(new OrigenSolicitud());
			objSolicitud.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
			
			//Se establecen los datos de persona interesada
			PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
			//Establecemos el tipo de persona interesada
			personaIntSol.setTipoPersonaInteresadaSol(new TipoPerInteresadaSol());
			personaIntSol.getTipoPersonaInteresadaSol().setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
			//Establecemos a la persona interesada
			personaIntSol.setPersona(new Persona());
			personaIntSol.getPersona().setIdPersona(correccion.getPersona().getIdPersona());
			//Se la agregamos a la solicitud
			objSolicitud.setPersonaInteresadaSolicitud(personaIntSol);
			
			//seteo de las propiedades del tramite
			objSolicitud.setTramites(new ArrayList<Tramite>());
			
			//razonRes.setIdRazonResultado(RazonResultadoEnum.NORMAL.getId());
			EstadoTramite et = new EstadoTramite();
			Long idEstadoTramite = EstadoTramiteEnum.CERRADO.getId();
			et.setIdEstadoTramitePersona(idEstadoTramite.intValue());
			
			correccion.setRazonResultado(null);
			correccion.setEstadoTramite(et);
			correccion.setObservacion("");
			correccion.setFechaTramite(fechaCreacion);
			correccion.setFechaPresentacion(fechaCreacion);
			
			
			correccion.setFechaPresentacion(new Date());
			
			objSolicitud.getTramites().add(correccion);
			
			objSolicitud = solicitudBusiness.crear(objSolicitud);
		} catch (SolicitudNoValidaException e) {
			throw e;
		}
		catch (Exception e) {
			log.error("Error al llenar objeto Solicitud", e);
			throw new DerechohabientesBusinessException("exception.guardar.solicitud");
		}
		
		return objSolicitud;
	}

	private CitaSolicitud getUmfSolicitud(TramiteCorreccionDerechohabiente tramite){

		/** Programa la cita a la UMF */

		CitaSolicitud cita = null;
		log.debug("Se agrea cita de acuerdo al funcionario:");
		cita = new CitaSolicitud();
		Turno turno = new Turno();
		turno.setIdTurno(TurnoEnum.MATUTINO.getId());
		cita.setUmf(tramite.getMedicoEnTurno().getUnidadMedicaFamiliar());
		log.debug("En la umf id: " + cita.getUmf().getIdUMF() + " y numero: " + cita.getUmf().getDescripcion());
		cita.setTurno(turno);
		cita.setFechaHora(new Date());

		return cita;
	}
}
