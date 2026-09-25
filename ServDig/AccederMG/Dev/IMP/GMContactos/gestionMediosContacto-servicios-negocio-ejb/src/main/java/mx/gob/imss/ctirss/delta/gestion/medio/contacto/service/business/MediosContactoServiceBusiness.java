/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.ParametroContactoRequeridoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity.MedioContactoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility.MedioContactoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PersonaContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;


/**
 * @author vanderluk
 *
 */
@Stateless(name="mediosContactoServiceBusiness" ,mappedName="mediosContactoServiceBusiness")
public class MediosContactoServiceBusiness extends AbstractServiceBusiness
		implements MediosContactoServiceBusinessRemote {
		
	@EJB MedioContactoServiceEntityLocal entity;
	
	@EJB MedioContactoServiceUtilityLocal utility;
	
	@EJB
	private SolicitudBusinessRemote solicitudService;

	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote#guardar()
	 */
	public List<MedioContacto> registrarMedioDeContacto(
			List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException {
		
		this.log.debug(" SERVICIO: Registrar medios de contacto: [" + mediosDeContacto + "]");
		return this.entity.registrarMedioDeContacto(mediosDeContacto);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote#consultarMedioDeContactoPersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	public List<MedioContacto> consultarMedioDeContactoPersona(
			Persona persona) throws PersonaSinMedioDeContactoException {
		
		List<MedioContacto> medios = null;
		this.log.debug("Consultando los medios de la persona");
		
		if(persona == null){
			this.log.error("No ser recibio la persona");
			throw new PersonaSinMedioDeContactoException();
		}
		
		Long idPersona = persona.getIdPersona();
		if(idPersona == null){
			this.log.error("No ser recibio el Id de la persona");
			throw new PersonaSinMedioDeContactoException();
		}
		
		TipoPersona tipo = persona.getTipoPersona();
		if(tipo == null){
			this.log.error("No se recibio el Tipo de la persona");
			throw new PersonaSinMedioDeContactoException();
		}
				
		if(tipo.getIdTipoPersona().longValue() ==   TipoPersona.TIPO_PERSONA_FISICA.longValue()){
			medios = this.entity.consultarMediosDePersonaFisica(persona);
		}else{
			medios = this.entity.consultarMediosDePersonaMoral(persona);
		}
		
		
		return medios;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote#consultarMedioDeContactoPersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	public List<MedioContacto> consultarMedioContactoPersona(
			Persona persona) throws PersonaSinMedioDeContactoException {
		
		List<MedioContacto> medios = null;
		this.log.debug("Consultando los medios de la persona");
		
		if(persona == null){
			this.log.error("No ser recibio la persona");
			throw new PersonaSinMedioDeContactoException();
		}
		
		Long idPersona = persona.getIdPersona();
		if(idPersona == null){
			this.log.error("No ser recibio el Id de la persona");
			throw new PersonaSinMedioDeContactoException();
		}
		
		TipoPersona tipo = persona.getTipoPersona();
		if(tipo == null){
			this.log.error("No se recibio el Tipo de la persona");
			throw new PersonaSinMedioDeContactoException();
		}
		
		
		medios = this.entity.consultarMediosDePersona(persona);
		
		return medios;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote#guardar()
	 */
	public List<MedioContacto> actualizarMedioDeContacto(
			List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException {
		
		this.log.debug(" SERVICIO: Actualiza medios de contacto: [" + mediosDeContacto + "]");
		return this.entity.actualizarMedioDeContacto(mediosDeContacto);
	}

	@Override
	public List<MedioContacto> consultarMediosContactoPorTipoPropietario(
			Long idPropiertarioContacto, Long idSolicitud,
			PropietarioMedioContactoEnum propietario, 
//			TipoPersona tipoPersona, 
			Long idPatronSujetoObligado)
			throws PersonaSinMedioDeContactoException, ParametroContactoRequeridoException, SolicitudNoEncontradaException {
		this.log.error("Obteniendo Medios............");
		System.err.println("Obteniendo Medios............");
		List<MedioContacto> mediosContacto = new ArrayList<MedioContacto>();
		if(idPropiertarioContacto == null || propietario == null)
			throw new ParametroContactoRequeridoException();
		
		if(idSolicitud == null){
			this.log.info("CONSULTANDO MEDIOS SIN SOLICITUD [idSolicitud]     :"+idSolicitud);
			System.err.println("CONSULTANDO MEDIOS SIN SOLICITUD [idSolicitud]     :"+idSolicitud);
//			mediosContacto = obtenerDatosContactoActuales(idPropiertarioContacto, propietario, tipoPersona, idPatronSujetoObligado);
			mediosContacto = obtenerDatosContactoActuales(idPropiertarioContacto, propietario, idPatronSujetoObligado);
		}else{
//			obtenerDatosContactoTramite(idPropiertarioContacto, idSolicitud, propietario, tipoPersona, idPatronSujetoObligado);
			this.log.info("CONSULTANDO MEDIOS CON SOLICITUD [idSolicitud]     :"+idSolicitud);
			this.log.error("CONSULTANDO MEDIOS CON SOLICITUD [idSolicitud]     :"+idSolicitud);
			System.err.println("CONSULTANDO MEDIOS CON SOLICITUD [idSolicitud]     :"+idSolicitud);
			mediosContacto = obtenerDatosContactoTramite(idPropiertarioContacto, idSolicitud, propietario, idPatronSujetoObligado);
		}
		return mediosContacto;
	}
	
	/**
	 * Obtiene los medios de contacto por tipo de propietario
	 * @param idPropiertarioContacto
	 * @param propietario
	 * @param tipoPersona
	 * @param idPatronSujetoObligado
	 * @return
	 * @throws ParametroContactoRequeridoException
	 * @throws PersonaSinMedioDeContactoException
	 */
	private List<MedioContacto> obtenerDatosContactoActuales(Long idPropiertarioContacto, 
			PropietarioMedioContactoEnum propietario, 
//			TipoPersona tipoPersona, 
			Long idPatronSujetoObligado) 
					throws ParametroContactoRequeridoException, PersonaSinMedioDeContactoException {
		List<MedioContacto> mediosContacto=new ArrayList<MedioContacto>();
		if(propietario.equals(PropietarioMedioContactoEnum.PERSONA_FISICA) || propietario.equals(PropietarioMedioContactoEnum.PERSONA_MORAL)){
//			if(tipoPersona == null)
//				throw new ParametroContactoRequeridoException();
			Persona persona = new Persona();
			persona.setIdPersona(idPropiertarioContacto);
			TipoPersona tipoPersona = obtenerTipoPersonaPorPropietario(propietario);
			persona.setTipoPersona(tipoPersona);
			mediosContacto = consultarMedioContactoPersona(persona);
		}else if(propietario.equals(PropietarioMedioContactoEnum.SOCIO)){
//			if(idPatronSujetoObligado== null)
//				throw new ParametroContactoRequeridoException();
			Socio socio = new Socio();
			socio.setIdSocio(idPropiertarioContacto);
			mediosContacto = consultarMedioContactoDeSocio(socio);
		}else if(propietario.equals(PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL)){
			if(idPatronSujetoObligado== null)
				throw new ParametroContactoRequeridoException();
			RepresentanteLegal repLegal = new RepresentanteLegal();
			repLegal.setCveIdRepresentanteLegal(idPropiertarioContacto);
			repLegal.setCveIdPatronSujetoObligado(idPatronSujetoObligado);
			mediosContacto = consultarMedioContactoDeRepresentanteLegal(repLegal);
		}else if(propietario.equals(PropietarioMedioContactoEnum.CENTRO_TRABAJO)){
			if(idPatronSujetoObligado== null)
				throw new ParametroContactoRequeridoException();
			CentroTrabajo centroTrabajo = new CentroTrabajo();
			centroTrabajo.setCveIdPatronSujetoObligado(idPatronSujetoObligado);
			mediosContacto = consultarMedioContactoDeCentroTrabajo(centroTrabajo);
		}
		
		mediosContacto = mediosContacto == null ? new ArrayList<MedioContacto>() : mediosContacto ; 
		return mediosContacto;
	}
	
	private List<MedioContacto> obtenerDatosContactoTramite(Long idPropiertarioContacto, Long idSolicitud,
			PropietarioMedioContactoEnum propietario, 
//			TipoPersona tipoPersona, 
			Long idPatronSujetoObligado)
			throws PersonaSinMedioDeContactoException, ParametroContactoRequeridoException, SolicitudNoEncontradaException {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		solicitud = solicitudService.consultar(solicitud);
		
		Tramite tramite = null;
		
		TipoTramiteEnum tipoTramiteAbuscarEnSolicitud = null;
		
		if(propietario.equals(PropietarioMedioContactoEnum.CENTRO_TRABAJO)){
			tipoTramiteAbuscarEnSolicitud = TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO;
		}else if(propietario.equals(PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL)){
			tipoTramiteAbuscarEnSolicitud = TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL;
		}else if(propietario.equals(PropietarioMedioContactoEnum.SOCIO)){
			tipoTramiteAbuscarEnSolicitud = TipoTramiteEnum.ACTUALIZACION_SOCIO;
		}else{ 
			tipoTramiteAbuscarEnSolicitud = TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO;
		}
		
		System.err.println("Tipo Tramite a buscar: "+tipoTramiteAbuscarEnSolicitud);
		
		for(Tramite tramiteActual : solicitud.getTramites()){
			if(tramiteActual.getTipoTramite().getIdTipoTramite().equals(
				tipoTramiteAbuscarEnSolicitud.getCodigo()))
			tramite = tramiteActual;
		}
		
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		
		if(tramite == null){
			this.log.debug("No se encontro tramite del tipo "+tipoTramiteAbuscarEnSolicitud+" en la solicitud");
			medios = obtenerDatosContactoActuales(idPropiertarioContacto, propietario, idPatronSujetoObligado);
			return medios;
		}
		
		if(!propietario.equals(PropietarioMedioContactoEnum.PERSONA_FISICA) && !propietario.equals(PropietarioMedioContactoEnum.PERSONA_MORAL) &&
				!propietario.equals(PropietarioMedioContactoEnum.SOCIO) && !propietario.equals(PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL)
				){
			this.log.error("CONSULTANDO MEDIOS DE TRAMITE DE CENTRO DE TRABAJO......");
			System.err.println("CONSULTANDO MEDIOS DE TRAMITE DE CENTRO DE TRABAJO......");
			System.err.println("Tramite encontrado.. " +tramite);
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			SujetoObligado so = tso.getSujetoObligado();
			medios=obtenerMediosDeSujetoObligadoPorPropietario(so, propietario, idPropiertarioContacto);
		}else if(propietario.equals(PropietarioMedioContactoEnum.PERSONA_FISICA) || propietario.equals(PropietarioMedioContactoEnum.PERSONA_MORAL)){
			log.info("CONSULTANDO MEDIOS DE TRAMITE DE PERSONA......");
			medios=obtenerMediosDePersonaDeTramite(tramite, obtenerTipoPersonaPorPropietario(propietario));
		}else if(propietario.equals(PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL) ){
			log.info("CONSULTANDO MEDIOS DE REPRESENTANTE......");
			medios=obtenerMediosDeRepresentanteLegalTramite(tramite, idPropiertarioContacto, idPatronSujetoObligado);
		}else if(propietario.equals(PropietarioMedioContactoEnum.SOCIO) ){
			log.info("CONSULTANDO MEDIOS DE SOCIOS......");
			medios=obtenerMediosDeSocioTramite(tramite, idPropiertarioContacto);
		}
		
		return medios;
	}
	
	/**
	 * 
	 * @param so
	 * @param propietario
	 * @return
	 */
	private List<MedioContacto> obtenerMediosDeSujetoObligadoPorPropietario(SujetoObligado so, 
			PropietarioMedioContactoEnum propietario, Long idPropietario
//			, TipoPersona tipoPersona
			){
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		
		if(propietario.getCodigo().equals(PropietarioMedioContactoEnum.SOCIO.getCodigo())){
			for(Socio socio : so.getSocios()){
				if(socio.getIdSocio()!= null && socio.getIdSocio().equals(idPropietario)){
					medios=socio.getMediosContacto();
					break;
				}else if(socio.getIdSocio() == null 
						&& socio.getIdVista() != null 
						&& socio.getIdVista().equals(idPropietario)){
					medios=socio.getMediosContacto();
				}
			}
		}
		
		
		if(propietario.getCodigo().equals(PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL.getCodigo())){
			for(RepresentanteLegal legal : so.getRepresentantesLegales()){
				if(legal.getCveIdRepresentanteLegal() != null && legal.getCveIdRepresentanteLegal().equals(idPropietario)){
					medios=legal.getMediosContacto();
					break;
				}else if(legal.getCveIdRepresentanteLegal() == null 
						&& legal.getIdVista() != null 
						&& legal.getIdVista().equals(idPropietario)){
					medios=legal.getMediosContacto();
				}
			}
		}
		
		
		if(propietario.getCodigo().equals(PropietarioMedioContactoEnum.CENTRO_TRABAJO.getCodigo())){
			medios=so.getCntroTrabajo().getMediosContacto();	
		}
		
		if(propietario.equals(PropietarioMedioContactoEnum.PERSONA_FISICA)
				|| propietario.equals(PropietarioMedioContactoEnum.PERSONA_MORAL)){
			
			TipoPersona tipoPersona = obtenerTipoPersonaPorPropietario(propietario);
			medios= obtenerMedioContactoPersonaDeSujetoObligado(so, tipoPersona);
		}	
		
		return medios;
	}
	
	
	/**
	 * 
	 * @param so
	 * @param tipoPersona
	 * @return
	 */
	private List<MedioContacto> obtenerMedioContactoPersonaDeSujetoObligado(SujetoObligado so, TipoPersona tipoPersona){
		Persona persona = null;
		if(tipoPersona.getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			persona = so.getFisica();
		}else if(tipoPersona.getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			persona = so.getMoral();
		}

		return persona.getMediosContacto();
	}
	
	
	
	/**
	 * 
	 */
	private List<MedioContacto> obtenerMediosDePersonaDeTramite(Tramite tramite, TipoPersona tipoPersona){
		Persona persona = null;
		
		if(tipoPersona.getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			TramiteFisica tf = (TramiteFisica)tramite;
			persona = tf.getFisica();
		}else if(tipoPersona.getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			TramiteMoral tm = (TramiteMoral)tramite;
			persona = tm.getMoral();
		}
		

		return persona.getMediosContacto();
	}
	
	
	/**
	 * 
	 */
	private List<MedioContacto> obtenerMediosDeRepresentanteLegalTramite(Tramite tramite, Long idPropietario, Long idSujetoObligado){
		Persona persona = null;
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		if(tramite instanceof TramiteFisica){
			TramiteFisica tf = (TramiteFisica)tramite;
			log.info("Procesando la información de fisica... ");
			persona = tf.getFisica();
		}else if(tramite instanceof TramiteMoral){
			TramiteMoral tm = (TramiteMoral)tramite;
			log.info("Procesando la información de moral... ");
			persona = tm.getMoral();
		}
		
		for(RepresentanteLegal repLegal : persona.getRepresentantesLegales()){
			log.info("Se compara el idRepLegal = "+repLegal.getCveIdRepresentanteLegal()+" con el propietario: "+idPropietario);
			if(repLegal.getCveIdRepresentanteLegal().equals(idPropietario)){
				log.info("Asignando medios de representante...");
				medios = repLegal.getMediosContacto();
			}
				
		}
		log.info("Medios de Tramite Representante: "+medios);
		
		if(medios.size()==0){
			log.info("No hay medios en trámite para ese Representante aunque el trámite existe. ");
			log.info("Se inicializa el gris con los datos actuales. ");
			try {
				medios = obtenerDatosContactoActuales(idPropietario, PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL, idSujetoObligado);
			} catch (ParametroContactoRequeridoException e) {
				e.printStackTrace();
			} catch (PersonaSinMedioDeContactoException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return medios != null ? medios : new ArrayList<MedioContacto>();
	}
	
	/**
	 * 
	 */
	private List<MedioContacto> obtenerMediosDeSocioTramite(Tramite tramite, Long idPropietario){
		Persona persona = null;
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		if(tramite instanceof TramiteFisica){
			TramiteFisica tf = (TramiteFisica)tramite;
			persona = tf.getFisica();
		}else if(tramite instanceof TramiteMoral){
			TramiteMoral tm = (TramiteMoral)tramite;
			persona = tm.getMoral();
		}
		
		for(Socio socio : persona.getSocios()){
			if(socio.getIdSocio().equals(idPropietario)){
				log.info("Asignando medios de socio...");
				medios = socio.getMediosContacto();
			}
		}
		
		if(medios.size()==0){
			log.info("No hay medios en trámite para ese socio aunque el trámite existe. ");
			log.info("Se inicializa el gris con los datos actuales. ");
			try {
				medios = obtenerDatosContactoActuales(idPropietario, PropietarioMedioContactoEnum.SOCIO, null);
			} catch (ParametroContactoRequeridoException e) {
				e.printStackTrace();
			} catch (PersonaSinMedioDeContactoException e) {
				e.printStackTrace();
			}
		}
		
		return medios != null ? medios : new ArrayList<MedioContacto>();
	}


	/**
	 * 
	 * @param socio
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<MedioContacto>  consultarMedioContactoDeSocio(Socio socio){
		return this.entity.consultarMediosSocio(socio);
	}
	
	
	/**
	 * 
	 * @param representante
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<MedioContacto> consultarMedioContactoDeRepresentanteLegal(RepresentanteLegal representante){
		return this.entity.consultarMediosRepresentanteLegal(representante);
	}
	
	/**
	 * 
	 * @param centro
	 * @return
	 */
	@Override
	public List<MedioContacto> consultarMedioContactoDeCentroTrabajo(CentroTrabajo centro){
		return this.entity.consultarMediosCentroTrabajo(centro);
	}
	
	/**
	 * Obtiene los tipos de contacto activos configurados en la base de datos
	 * @return
	 */
	@Override
	public List<TipoContacto> consultarTipoContacto(){ 
		//test de commit
		return this.entity.consultarTiposContacto();
	}
	
	
	/**
	 * 
	 * @param propietario
	 * @return
	 */
	private TipoPersona obtenerTipoPersonaPorPropietario(PropietarioMedioContactoEnum propietario){
		TipoPersona tipoPersona = new TipoPersona();
		if(propietario.equals(PropietarioMedioContactoEnum.PERSONA_FISICA)){
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		}else if(propietario.equals(PropietarioMedioContactoEnum.PERSONA_MORAL)){
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		}
		return tipoPersona;
	}

	@Override
	public List<MedioContacto> consultarMediosFiscalesPersona(Persona persona)
			throws PersonaSinMedioDeContactoException {
		
		List<MedioContacto> medios = null;
		this.log.debug("Consultando los medios fiscales de la persona");
		
		if(persona == null){
			this.log.error("No ser recibio la persona");
			throw new PersonaSinMedioDeContactoException();
		}
		
		
		if(persona instanceof Fisica && ((Fisica) persona).getCveFisica() == null && persona.getIdPersona() == null){
			this.log.error("No ser recibio el Id de la persona fisica");
			throw new PersonaSinMedioDeContactoException();
		}else if(persona instanceof Moral && ((Moral) persona).getCveMoral() == null && persona.getIdPersona() == null){
			this.log.error("No ser recibio el Id de la persona moral");
			throw new PersonaSinMedioDeContactoException();
		}
				
		medios = this.entity.consultarMediosFiscalesPersona(persona);
		
		return medios;	
	}
	
	@Override
	public MedioContacto registrarAsociarMedioContactoPersona(
			MedioContacto medioContacto, Long cvePersona)
			throws RegistrarMedioContactoException {

		this.log.debug(" Se guarda el medio de contacto ["+ medioContacto + "]");
		
		medioContacto = this.entity.registrarMedioDeContacto(medioContacto);
		
		this.log.debug(" Se asocia el medio de contacto ["
				+ medioContacto.getClave() + "] a persona [" + cvePersona
				+ "]");
		
		this.entity.asociarMedioContactoPersona(medioContacto.getClave(), cvePersona);
		
		return medioContacto;
	}

	@Override
	public MedioContacto registrarAsociarMedioContactoPersonaMoral(
			MedioContacto medioContacto, Long cvePersonaMoral)
			throws RegistrarMedioContactoException {

		this.log.debug(" Se guarda el medio de contacto ["+ medioContacto + "]");
		
		medioContacto = this.entity.registrarMedioDeContacto(medioContacto);
		
		this.log.debug(" Se asocia el medio de contacto ["
				+ medioContacto.getClave() + "] a persona fisica [" + cvePersonaMoral
				+ "]");
		
		this.entity.asociarMedioContactoPersonaMoral(medioContacto.getClave(), cvePersonaMoral);
		
		return medioContacto;
	}
	
	@Override
	public PersonaContacto registrarMedioContactoPersonafContacto(MedioContacto medioContacto, Long cvePersona) throws RegistrarMedioContactoException {
		
		PersonaContacto personaContacto = null;

		this.log.debug(" Se guarda el medio de contacto ["+ medioContacto + "]");
		
		medioContacto = this.entity.registrarMedioDeContacto(medioContacto);
		
		this.log.debug(" Se asocia el medio de contacto ["+ medioContacto.getClave() + "] a persona [" + cvePersona + "]");
		
		DitPersonafContacto ditPersonafContacto = this.entity.registrarMedioContactoPersonafContacto(medioContacto.getClave(), cvePersona);
		
		personaContacto = new PersonaContacto();
		personaContacto.setMedioContacto(medioContacto);
		personaContacto.setCveIdPersonafContacto(ditPersonafContacto.getCveIdPersonafContacto());
		
		return personaContacto;
	}

	@Override
	public PersonaContacto registrarMedioContactoPersonamContacto(MedioContacto medioContacto, Long cvePersonaMoral) throws RegistrarMedioContactoException {
		
		PersonaContacto personaContacto = null;

		this.log.debug(" Se guarda el medio de contacto ["+ medioContacto + "]");
		
		medioContacto = this.entity.registrarMedioDeContacto(medioContacto);
		
		this.log.debug(" Se asocia el medio de contacto ["+ medioContacto.getClave() + "] a persona fisica [" + cvePersonaMoral	+ "]");
		
		DitPersonamContacto ditPersonamContacto = this.entity.registrarMedioContactoPersonamContacto(medioContacto.getClave(), cvePersonaMoral);
		
		personaContacto = new PersonaContacto();
		personaContacto.setMedioContacto(medioContacto);
		personaContacto.setCveIdPersonamContacto(ditPersonamContacto.getCveIdPersonamContacto());
		
		return personaContacto;
	}
	
	@Override
	public MedioContacto registrarAsociarMedioContactoFiscalPersonaFisica(MedioContacto medioContacto,
			Long cvePersonaFisica) throws RegistrarMedioContactoException {
		
		this.log.debug(" Se guarda el medio de contacto fiscal ["+ medioContacto + "]");
		
		medioContacto = this.entity.registrarMedioDeContacto(medioContacto);
		
		this.log.debug(" Se asocia el medio de contacto ["
				+ medioContacto.getClave() + "] a persona fisica [" + cvePersonaFisica
				+ "]");
		
		this.entity.asociarMedioContactoFiscalPersonaFisica(medioContacto.getClave(), cvePersonaFisica);
		
		return medioContacto;
	}

	@Override
	public MedioContacto registrarAsociarMedioContactoFiscalPersonaMoral(MedioContacto medioContacto,
			Long cvePersonaMoral) throws RegistrarMedioContactoException {
		
		this.log.debug(" Se guarda el medio de contacto fiscal ["+ medioContacto + "]");
		
		medioContacto = this.entity.registrarMedioDeContacto(medioContacto);
		
		this.log.debug(" Se asocia el medio de contacto ["
				+ medioContacto.getClave() + "] a persona fisica [" + cvePersonaMoral
				+ "]");
		
		this.entity.asociarMedioContactoFiscalPersonaMoral(medioContacto.getClave(), cvePersonaMoral);
		
		return medioContacto;		
	}
	
	@Override
	public MedioContacto actualizarMedioDeContacto(MedioContacto medioContacto)
			throws RegistrarMedioContactoException {
		
		this.log.debug(" SERVICIO: Actualizar medio de contacto: [" + medioContacto + "]");
		return this.entity.actualizarMedioDeContacto(medioContacto);
	}

	@Override
	public void eliminarMedioDeContacto(Long cveMedioContacto)
			throws RegistrarMedioContactoException {
			
		this.log.debug(" Se elimina el medio de contacto ["+ cveMedioContacto + "]");
		
		MedioContacto medioContacto = new MedioContacto();
		medioContacto.setClave(cveMedioContacto);
		this.entity.eliminarMedioDeContacto(medioContacto);
		
	}
	
	@Override
	public List<MedioContacto> retomarTramiteAdmonMedios(Long idSolicitud, boolean isFiscal)
			throws SolicitudNoEncontradaException {
		
		List<MedioContacto> medios = null;
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		
		solicitud = this.solicitudService.consultar(solicitud);
		
		/*
		 * Se checa que la solicitud encontrada tenga información de
		 * Modificación Manual
		 */
		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteFisica) {
				TramiteFisica tramiteFisica = (TramiteFisica) tramite;
				if (tramiteFisica.getDatosMDM() != null) {
					
					MDMDatosEntrada datosModif = tramiteFisica.getDatosMDM(); 
					
					if(!isFiscal) {
						medios = datosModif.getPersonaFisica().getMediosContacto();
					} else {
						medios = datosModif.getPersonaFisica().getMediosContactoFiscales();
					}
					
					break;
				}
			} else if (tramite instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) tramite;
				if (tramiteMoral.getDatosMDM() != null) {
					
					MDMDatosEntrada datosModif = tramiteMoral.getDatosMDM(); 
					
					if(!isFiscal) {
						medios = datosModif.getPersonaMoral().getMediosContacto();
					} else {
						medios = datosModif.getPersonaMoral().getMediosContactoFiscales();
					}
				}
			}
		}
		
		return medios;
	}

	@Override
	public List<MedioContacto> consultarMedioContactoDeRegistroPatronal(
			Long idPatronSujetoObligado)
			throws PersonaSinMedioDeContactoException {
		List<MedioContacto> medios = null;
		this.log.debug("Consultando los medios de registro patronal");
		
		if(idPatronSujetoObligado == null){
			this.log.error("No se recibio identificador de registro patronal");
			throw new PersonaSinMedioDeContactoException();
		}
		CentroTrabajo centroTrabajo = new CentroTrabajo(); 
		medios = this.entity.consultarMediosCentroTrabajo(centroTrabajo);
		
		
		return medios;
	
	}

	@Override
	public mx.gob.imss.digital.modelo.persona.Persona obtenerListaDeMediosContacto(
			mx.gob.imss.digital.modelo.persona.Persona persona) {
		Persona personaDelta = new Persona();
		personaDelta.setIdPersona(persona.getIdPersona());
		personaDelta.setTipoPersona(new TipoPersona());
		personaDelta.getTipoPersona().setIdTipoPersona(persona.getTipoPersona()!=null ? persona.getTipoPersona().getIdTipoPersona() : null);
		List<MedioContacto> mediosDelta = null;
		try {
			mediosDelta = this.consultarMedioContactoPersona(personaDelta);
		} catch (PersonaSinMedioDeContactoException e) {
			log.error("La persona no cuenta con medios de contacto");
		}
		
		if(mediosDelta!=null && !mediosDelta.isEmpty()){
			List<mx.gob.imss.digital.modelo.medio.contacto.MedioContacto> mediosID = utility.transformarMediosDeltaAImssDigital(mediosDelta);
			mx.gob.imss.digital.modelo.medio.contacto.MedioContacto[] arrayMediosID = mediosID.toArray(new mx.gob.imss.digital.modelo.medio.contacto.MedioContacto[mediosID.size()]);
			persona.setMediosContacto(arrayMediosID);
		}
		
		return persona;
	}

	@Override
	public boolean validarExistenciaCorreoElectronicoPersona(Persona persona) {
		boolean tieneCorreo = false;
		int contadorCorreos = 0;

		try {
			List<MedioContacto> mediosContacto = consultarMedioDeContactoPersona(persona);

			for (MedioContacto medio : mediosContacto) {
				if (medio instanceof CorreoElectronico) {
					contadorCorreos++;
				}
			}

			if (contadorCorreos > 0) {
				log.error("La persona tiene correos " + contadorCorreos + " electronicos asociados");
				tieneCorreo = true;
			} else {
				log.error("La persona tiene medios de contacto asociados pero no cuenta con ningun correo electronico");
			}
		} catch (PersonaSinMedioDeContactoException e) {
			log.error("La persona no tiene ningun Medio de Contacto asociado");
			this.log.warn(e);
		}

		return tieneCorreo;
	}
	
	@Override
	public void asociarCorreoMedioContactoPersona(Long idPersona, String correo){
		boolean existeMedioContacto = entity.existenciaCorreoPersonaPorId(idPersona, correo);
		if(!existeMedioContacto){
			entity.asociarMedioContactoPersona(idPersona, correo, TipoMedioContacto.TIPO_CORREO_ELECTRONICO);
			log.debug("Se asocia medio de contacto " + correo + " a la persona " + idPersona);
		}
		
	}
	
	
}
