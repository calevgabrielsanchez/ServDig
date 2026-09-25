package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.afiliacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud.SolicitudServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion.AfiliacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.MailConfigUtil;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoMovtoPatSujObligEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TramiteTipoConclusionEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.utilmail.service.MailService;
import mx.gob.imss.utilmail.service.impl.MailServiceImpl;
import mx.gob.imss.utilmail.util.MailProperties;
import weblogic.utils.StringUtils;

/**
 * 
 * @author Hugo Martinez
 * @Projecto: delta-gestionPatronal-servicios-negocio-ejb
 * @Package: mx.gob.imss.ctirss.delta.gestion.patronal.service.business.afiliacion
 * @Archivo: AfiliacionServiceBusiness.java
 * @Fecha: 25/07/2012 10:33:47
 *
 */
@Stateless(name="afiliacionServiceBusiness" ,mappedName="afiliacionServiceBusiness")
public class AfiliacionServiceBusiness extends AbstractServiceBusiness implements
		AfiliacionServiceBusinessRemote {

	@EJB
	AfiliacionServiceEntityLocal afiliacionEntity;
	
	@EJB 
	DomicilioServiceBusinessRemote domicilioServiceBusiness;
		
	@EJB
	SolicitudServiceBusinessLocal solicitudBusinessService;
	
	@EJB
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;	
	
	@EJB
	MediosContactoServiceBusinessRemote mediosContactoService;
	
	MailService mailService;
	
	@EJB
	RepresentanteLegalServiceBusinessRemote representanteService;
	
	@EJB
	ActividadEcServiceRemote clasificacionServiceBusiness;
	
	@EJB
	SolicitudServiceBusinessLocal solicitudService;
	
	@EJB
	ManejadorReportesRemote manejadorReportesBusiness;
	
	@EJB
	PersonasAutorizadasServiceRemote personasAutorizadasService;
	
	@EJB
	TramiteServiceEntityLocal tramiteServiceEntityLocal;
	@EJB
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

	@EJB
	private SolicitudBusinessRemote solicitudBusiness;
	
	@EJB
	private mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote gceSolicitudService;
	
	@EJB
	PersonaBusinessRemote personaBusiness;
	@EJB
	SocioServiceBusinessRemote socioServiceBusinessRemote;
	
	@EJB( mappedName = "envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusiness; 
	
	@Override
	public Map<String, Object> gestionarTramiteActualizacionAfiliacion(Long idSolicitud,
			SujetoObligado sujetoTramite, TipoTramiteEnum tipoTramite, 
			Usuario usuario, boolean indRatificado) throws GestionPatronalBusinessException {
		Map<String, Object> mapAccion = new HashMap<String, Object>();
		TipoSolicitudEnum tipoSolicitud=solicitudBusinessService.obtenerTipoSolicitudEnBaseAlTipoTramite(tipoTramite);
		EstadoTramiteEnum estadoTramite = obtenerEstadoTramitePorRol(usuario);
		System.err.println("Consultando solicitud activa");
		Solicitud solicitudActiva = 
				idSolicitud != null && idSolicitud > 0 ? 
				solicitudBusinessService.consultarSolicitudPorId(idSolicitud) : null;
				//solicitudBusinessService.obtenerSolicitudActiva(sujetoTramite, tipoSolicitud, usuario);
		super.log.error("Solicitud activa: "+solicitudActiva);
		System.err.println("Solicitud activa: "+solicitudActiva);
		Boolean esTramitador = esTramitador(usuario);
		
		if(!esTramitador && idSolicitud==0){
			Solicitud solicitudPrevia = solicitudBusinessService.obtenerSolicitudActiva(sujetoTramite, tipoSolicitud, usuario,tipoTramite);
			if(solicitudPrevia!=null){
				throw new GestionPatronalBusinessException("error.solicitud.afil.previa");
			}
		}
		
				
		if(solicitudActiva == null){
			System.err.println("No existe solicitud activa para el tipo "+tipoSolicitud);
			solicitudActiva = inicializarSolicitud(tipoSolicitud, EstadoSolicitudEnum.REGISTRADA, usuario, sujetoTramite);
			Tramite tramite = inicializarTramite(tipoTramite, EstadoTramiteEnum.INICIADO, sujetoTramite, indRatificado, esTramitador);
			List<Tramite> tramites = new ArrayList<Tramite>();
			tramites.add(tramite);
			solicitudActiva.setTramites(tramites);
			/**
			 * Debido a que el servicio de crear solicitud inicializa por defecto el estatus de la solicitud
			 * como REGISTRADA y el(los) tramite(s) con estatus de INICIADO, para crear una nueva solicitud
			 * con un estatus distinto se requiere actualizar los estatus inmediatamente despues de haber creado
			 * la solicitud
			 * 
			 */
			if(esTramitador){
				solicitudActiva.getEstadoSolicitud().setIdEstadoSolicitud(
						EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo());
				solicitudActiva.getTramites().get(0).getEstadoTramite().setIdEstadoTramitePersona(
						EstadoTramiteEnum.ACTIVO.getCodigo());
				solicitudActiva.getTramites().get(0).setFechaPresentacion(Calendar.getInstance().getTime());
			}
			solicitudActiva = solicitudBusinessService.crearNuevaSolicitud(solicitudActiva);							
			mapAccion.put("operacion", TipoAccionAfectacionEnum.MOSTRAR_MENSAJE_SOLICITUD_CREADA);
			mapAccion.put("folio", solicitudActiva.getNoFolioSolicitud());
			mapAccion.put("idSolicitud", solicitudActiva.getSolicitudId());
		}else{
//			validaCondicionesDeActualizacionDeSolicitud(solicitudActiva, usuario);
			solicitudActiva = gestionarTipoTramite(solicitudActiva, tipoTramite, sujetoTramite, estadoTramite, indRatificado, esTramitador);			
			solicitudBusinessService.actualizarTramites(solicitudActiva);
			mapAccion.put("operacion", TipoAccionAfectacionEnum.MOSTRAR_MENSAJE_TRAMITE_ACTUALIZADO);
			mapAccion.put("folio", solicitudActiva.getNoFolioSolicitud());
			mapAccion.put("idSolicitud", solicitudActiva.getSolicitudId());
		}
		if(indRatificado){
			mapAccion.put("operacion", TipoAccionAfectacionEnum.MOSTRAR_MENSAJE_RATIFICACION);
		}
		return mapAccion;
	}
	
	@Override
	public void validaCondicionesDeActualizacionDeSolicitud(Long idSolicitud, Usuario usuario, String rfc) throws GestionPatronalBusinessException{
		SujetoObligado patron=null;
		if(idSolicitud == null || (idSolicitud != null && idSolicitud == 0)){
			SujetoObligado sujetoObligado = new SujetoObligado();
			TipoPersonaFiscal tipoPersonaFiscal = obtenerTipoPersonaFiscal(rfc);
			sujetoObligado.setTipoPersonaFiscal(tipoPersonaFiscal);
			boolean bFisica = tipoPersonaFiscal.equals(TipoPersonaFiscal.FISICA);
			if (bFisica) {
				Fisica pFisica = new Fisica();
				pFisica.setRfc(rfc);
				sujetoObligado.setFisica(pFisica);
			} else {
				Moral pMoral = new Moral();
				pMoral.setRfc(rfc);
				sujetoObligado.setMoral(pMoral);
			}
			patron =sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado).get(0);
		}
		
		if(idSolicitud!=null && idSolicitud!=0){
			Solicitud solicitud = solicitudBusinessService.consultarDetalleSolicitudPorIdentificador(idSolicitud);
			patron = solicitud.getSujetoObligado();
			if( (usuario.getPerfilUsuario().getIdPerfilUsuario().equals(RolEnum.PATRON_SUJETO_OBLIGADO.getCodigo().longValue()) || 
					usuario.getPerfilUsuario().getIdPerfilUsuario().equals(RolEnum.REPRESENTANTE_LEGAL.getCodigo().longValue()))
					&& !solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA.getCodigo())){
				throw new GestionPatronalBusinessException("error.solicitud.modificada");
			}
		}
		
		if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(RolEnum.REPRESENTANTE_LEGAL.getCodigo().longValue())){
			TipoPersonaEnum tipoPersonaRepresentada = patron.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)
					? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL;
			
			Long cveIdPersonaRepresentada = tipoPersonaRepresentada.equals(TipoPersonaEnum.FISICA) 
					? patron.getFisica().getIdPersona()
					: patron.getMoral().getIdPersona();
			
			boolean esRepresentanteValido = representanteService.esRepresentanteDePersona(usuario.getFisica().getIdPersona(), cveIdPersonaRepresentada, tipoPersonaRepresentada);
			
			if(!esRepresentanteValido)
				throw new GestionPatronalBusinessException("error.representante.invalido");
		}
	}
	
	/**
	 * Inserta una nueva solicitud del tipo y con el estado proporcionados
	 * 
	 * @author Hugo Armando MartÃ­nez Cham&oacute;nica
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param usuario
	 * @return Solicitud
	 */
	private Solicitud inicializarSolicitud(TipoSolicitudEnum tipoSolicitud,
			EstadoSolicitudEnum estadoSolicitud, Usuario usuario, SujetoObligado sujetoTramite) {
		System.out.println("Iniciando la creaci&oacute;n de  la solicitud");
		System.err.println("Sujeto en la solicitud: "+sujetoTramite);
		Solicitud solicitud = new Solicitud();
		EstadoSolicitud estado = new EstadoSolicitud();
		estado.setIdEstadoSolicitud(estadoSolicitud.getCodigo());
		solicitud.setEstadoSolicitud(estado);
		solicitud.setFechaSolicitud(Calendar.getInstance().getTime());
		TipoSolicitud tipo = new TipoSolicitud();
		tipo.setIdTipoSolicitud(tipoSolicitud.getValor().longValue());
		solicitud.setTipoSolicitud(tipo);
		solicitud.setSolicitante(usuario);
		Persona persona = 
				sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) 
				? sujetoTramite.getFisica() : sujetoTramite.getMoral();
		persona.setTipoPersona(new TipoPersona());
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			System.err.println("Asignando tipo persona fisica");
		}else{
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			System.err.println("Asignando tipo persona moral");
		}
		Domicilio domicilio = sujetoObligadoService.obtenerDomicilioFiscalPatron(persona);
		Subdelegacion subdelegacion=null;
		
		if(tipoSolicitud.getValor().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor())){
			log.debug("Se obtiene subdelegacion de centro de trabajo");
			subdelegacion=sujetoObligadoService.obtenerSubdelegacion(sujetoTramite.getCveIdSujetoObligado());
		}else{
			log.debug("Se obtiene subdelegacion de domicilio fiscal");
			if(domicilio!=null){
				try {
					subdelegacion = obtenerSubdelegacionPorDomicilio(domicilio);
				} catch (GestionPatronalBusinessException e) {
					e.printStackTrace();
				}
			}
		}
		solicitud.setSubdelegacion(subdelegacion);
		solicitud.setSolicitante(usuario);
		log.debug("Terminando de crear el objeto solicitud");
		return solicitud;
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param tipo TipoTramite
	 * @param estado EstadoTramite
	 * @param sujetoObligado SujetoObligado
	 * @return TramiteSujetoObligado
	 */
	private Tramite inicializarTramite(TipoTramiteEnum tipo,
			EstadoTramiteEnum estado, SujetoObligado sujetoObligado, Boolean indRatificado, Boolean esTramitador) {
		
		
		if(tipo.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL) ||
				tipo.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO) ||
				tipo.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA) ||
				tipo.equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO) ||
				tipo.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL) ||
				tipo.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO) ){
			
			if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				TramiteFisica tramitePF = new TramiteFisica();
				tramitePF.setFisica(sujetoObligado.getFisica());
				tramitePF.setIndRatificado(indRatificado);
				if(tipo.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
					tramitePF.setDatosICA(sujetoObligado.getDatosICA());
					tramitePF.setDatosMDM(sujetoObligado.getDatosMDM());
				}
				complementarTramite(tramitePF, tipo, estado, esTramitador);
				return tramitePF;
			}else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
				TramiteMoral tramitePM = new TramiteMoral();
				tramitePM.setMoral(sujetoObligado.getMoral());
				tramitePM.setIndRatificado(indRatificado);
				if(tipo.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
					tramitePM.setDatosICA(sujetoObligado.getDatosICA());	
					tramitePM.setDatosMDM(sujetoObligado.getDatosMDM());
				}
				complementarTramite(tramitePM, tipo, estado, esTramitador);
				return tramitePM;
			}
			
		}else{
			TramiteSujetoObligado tramiteSO = new TramiteSujetoObligado();
			tramiteSO.setSujetoObligado(sujetoObligado);
			tramiteSO.setIndRatificado(indRatificado);
			complementarTramite(tramiteSO, tipo, estado, esTramitador);
			return tramiteSO;
			
		}
		
		System.err.println("No se asigno ningún tipo de tramite");
		return null;

	}
	
	private Solicitud gestionarTipoTramite(Solicitud solicitud, TipoTramiteEnum tipoTramite, SujetoObligado sujetoTramite, EstadoTramiteEnum estadoTramite, Boolean indRatificado, Boolean esTramitador){
		Tramite tramite = obtenerTramitePorTipo(solicitud, tipoTramite);
		System.err.println("tramite actual: "+tramite);
		if(tramite == null){
			tramite = inicializarTramite(tipoTramite, estadoTramite, sujetoTramite, indRatificado, esTramitador);
			solicitud = agregarTramiteASolicitud(solicitud, tramite);
		}else{
			tramite.setIndRatificado(indRatificado);
			tramite.getEstadoTramite().setIdEstadoTramitePersona(estadoTramite.getCodigo());
			actualizarTramiteEnSolicitud(solicitud, tramite, sujetoTramite);
		}
		return solicitud;
	}
	
	private Tramite obtenerTramitePorTipo(Solicitud solicitud, TipoTramiteEnum tipoTramite){
		
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getTipoTramite().getIdTipoTramite().equals(tipoTramite.getCodigo())){
				return tramite;
			}
		}
		return null;
	}
	
	private Solicitud actualizarTramiteEnSolicitud(Solicitud solicitud, Tramite tramite, SujetoObligado sujetoTramite){
		System.err.println("Tramite antes de eliminar: "+tramite);
		eliminarTramiteDeSolicitud(solicitud, tramite);
		System.err.println("Tramite: "+tramite);
		tramite = actualizarInformacionTramite(tramite, sujetoTramite);
		solicitud = agregarTramiteASolicitud(solicitud, tramite);
		return solicitud;
	}
	
	private Tramite actualizarInformacionTramite(Tramite tramite, SujetoObligado sujetoTramite){
		System.err.println("Tipo Tramite Actual: "+tramite.getTipoTramite().getIdTipoTramite());		
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())
				){
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				TramiteFisica tf = (TramiteFisica)tramite;
				tf.setFisica(sujetoTramite.getFisica());
				if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
					tf.setDatosICA(sujetoTramite.getDatosICA());
					tf.setDatosMDM(sujetoTramite.getDatosMDM());
				}
				return tf;
			}else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
				TramiteMoral tm = (TramiteMoral)tramite;
				tm.setMoral(sujetoTramite.getMoral());
				if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
					tm.setDatosICA(sujetoTramite.getDatosICA());
					tm.setDatosMDM(sujetoTramite.getDatosMDM());
				}
				return tm;
			}
		}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.COMODATO.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ENAJENACION.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ESCISION.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.FUSION.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
		){
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			tso.setSujetoObligado(sujetoTramite);
			return tso;
		}		
		
		System.err.println("El tipo de tramite no es valido");
		return null;
	}
	
	private Solicitud agregarTramiteASolicitud(Solicitud solicitud, Tramite tramite){
		if(solicitud.getTramites() == null)
			solicitud.setTramites(new ArrayList<Tramite>());		
		solicitud=remueveTramiteActSind(solicitud, tramite);		
		solicitud.getTramites().add(tramite);
		return solicitud;		
	}
	
	private Solicitud remueveTramiteActSind(Solicitud solicitud, Tramite tramite){
		if (tramite.getTipoTramite().getIdTipoTramite()==TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo()){				
			if (solicitud.getTramites().size()>0){
				int index=0;
				for (Tramite tramite2 : solicitud.getTramites()){
					if (tramite2.getTipoTramite().getIdTipoTramite()==TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo()){
						solicitud.getTramites().remove(index);
						break;
					}
					index++;
				}
			}
		}
		else{
			if (tramite.getTipoTramite().getIdTipoTramite()==TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo()){	
				if (solicitud.getTramites().size()>0){
					int index=0;
					for (Tramite tramite2 : solicitud.getTramites()){
						if (tramite2.getTipoTramite().getIdTipoTramite()==TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo()){
							solicitud.getTramites().remove(index);
							break;
						}
						index++;
					}
				}
			}
		}
		return solicitud;
	}
	
	private void eliminarTramiteDeSolicitud(Solicitud solicitud, Tramite tramite){
		solicitud.getTramites().remove(tramite);
	}
	
	private void complementarTramite(Tramite tramite, TipoTramiteEnum tipo,
			EstadoTramiteEnum estado, Boolean esTramitador){
		if(esTramitador)
			tramite.setFechaPresentacion(Calendar.getInstance().getTime());
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(estado.getCodigo());
		TipoTramite tipoTramite = solicitudBusinessService.obtenerTipoTramite(tipo.getCodigo().longValue());
		
		tramite.setEstadoTramite(estadoTramite);
		tramite.setTipoTramite(tipoTramite);
	}

	@Override
	public Map<String, Object> gestionarDatosDeTramite(
			SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, Usuario usuario, boolean esNuevaSolicitud) {
		System.err.println("Estoy en gestionar tramite ......");
		TipoSolicitudEnum tipoSolicitud=solicitudBusinessService.obtenerTipoSolicitudEnBaseAlTipoTramite(tipoTramite);
		Solicitud solicitudActiva=null;
			if(!esNuevaSolicitud)
				solicitudActiva=solicitudBusinessService.obtenerSolicitudActiva(sujetoObligado, tipoSolicitud, usuario, tipoTramite);
		System.err.println("Solicitud activa al gestionarDatos Tramite: "+solicitudActiva);
		System.err.println("Inicializo el tramite con sujetoObligado ......");
		SujetoObligado sujetoTramite = null;
		boolean tramiteActivo=false;
		boolean tramiteRatificado=false;
		Map<String, Object> result = new HashMap<String, Object>();
		if(solicitudActiva!=null){
			System.err.println("Si hay solicitud activa ......");
			System.err.println("Tramites: "+solicitudActiva.getTramites().size());
			for(Tramite tramite : solicitudActiva.getTramites()){
				System.err.println("Comparando tramite: "+tipoTramite +" con todos");
					if(tipoTramite.getCodigo().equals(tramite.getTipoTramite().getIdTipoTramite())){
						tramiteActivo=true;
						System.err.println("Tramite activo de tipo : "+tipoTramite +" tramite: "+tramite);
						System.err.println("Indicador de ratificado Tramite tipo : "+tipoTramite +" ratificado: "+tramite.getIndRatificado());
						tramiteRatificado=tramite.getIndRatificado()!= null ? tramite.getIndRatificado() : false;
						if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO) ){
							System.err.println("Encontre tramite de persona ......");

							if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
								System.err.println("Encontre tramite de persona fisica......");
								System.err.println("nombre comercial objeto base: "+sujetoObligado.getNombreComercial());
								System.err.println("****************************************************************************");
//								sujetoTramite = sujetoObligado;
								System.err.println("nombre comercial objeto base: "+sujetoObligado.getNombreComercial());
								System.err.println("nombre comercial objeto Tramite: "+sujetoObligado.getNombreComercial());
								sujetoTramite = new SujetoObligado();
								TramiteFisica tramiteFisica = (TramiteFisica)tramite;
								sujetoTramite.setFisica(tramiteFisica.getFisica());
								break;
							}
							if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
								System.err.println("Encontre tramite de persona moral......");
								System.err.println("nombre comercial objeto base: "+sujetoObligado.getNombreComercial());
								System.err.println("****************************************************************************");
//								sujetoTramite = sujetoObligado;
								System.err.println("nombre comercial objeto base: "+sujetoObligado.getNombreComercial());
								System.err.println("nombre comercial objeto Tramite: "+sujetoObligado.getNombreComercial());
								sujetoTramite = new SujetoObligado();
								TramiteMoral tramiteMoral = (TramiteMoral)tramite;
								sujetoTramite.setMoral(tramiteMoral.getMoral());
								break;
							}
						}else if(tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA) ||
								tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY) ||
								tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES) ||
								tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS) ||
								tipoTramite.equals(TipoTramiteEnum.COMODATO) ||
								tipoTramite.equals(TipoTramiteEnum.ENAJENACION) ||
								tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO) ||
								tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO) ||
								tipoTramite.equals(TipoTramiteEnum.ESCISION) ||
								tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL) ||
								tipoTramite.equals(TipoTramiteEnum.FUSION) ||
								tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO) ||
								tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION) ||
								tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO)
								){							
								System.err.println("Encontre tramite de sujetoObligado ......");
								TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
								sujetoTramite = tso.getSujetoObligado();
								break;
							
						}
					}	
			}
		}
		
		if(!tramiteActivo){
			
			Persona persona = new Persona();
			TipoPersona tipoPersona = new TipoPersona();
			if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				persona.setIdPersona(sujetoObligado.getFisica().getIdPersona());
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			}else{
				persona.setIdPersona(sujetoObligado.getMoral().getIdPersona());
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			}
			persona.setTipoPersona(tipoPersona);
			sujetoTramite=inicializarSujetoObligadoPorTramite(persona, tipoTramite);
			//Si no hay tr�mite se agrega el objeto de centro de trabajo vigente al centro de trabajo en tr�mite
			if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)){
				sujetoTramite=sujetoObligado;
//				sujetoTramite.setNombreComercial(sujetoObligado.getNombreComercial());
//				sujetoTramite.setCntroTrabajo(sujetoObligado.getCntroTrabajo());
				
			}
		}		
		
		result.put("tramiteActivo", tramiteActivo);
		result.put("tramiteData", sujetoTramite);
		result.put("tramiteRatificado", tramiteRatificado);
		return result;
	}

	@Override
	public SujetoObligado obtenerDatosFiscales(Persona persona) {
		SujetoObligado sujetoObligado = new SujetoObligado();
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			Fisica fisica = afiliacionEntity.obtenerDatosFiscalesPersonaFisica(persona.getIdPersona());
			sujetoObligado.setFisica(fisica);
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		}else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			Moral moral = afiliacionEntity.obtenerDatosFiscalesPersonaMoral(persona.getIdPersona());
			sujetoObligado.setMoral(moral);
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
		
		return sujetoObligado;
	}
	
	
	
	@Override
	public Solicitud enviarSolicitudAlInstituto(SujetoObligado sujetoTramite, TipoSolicitudEnum tipoSolicitud, FirmaElectronica datosFirma)
			throws GestionPatronalBusinessException {
		Solicitud solicitud = solicitudBusinessService.obtenerSolicitudEnCaptura(sujetoTramite, tipoSolicitud, null);
		if(solicitud==null)
			throw new GestionPatronalBusinessException("No Existe ninguna solicitud En captura para el tipo de solicitud proporcionado");
		boolean fueFirmada=false;
		if(datosFirma!=null){
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, datosFirma);
			fueFirmada=true;
		}
		
//		validarCompletesDeTramites(solicitud, sujetoTramite.getTipoPersonaFiscal());
		EstadoSolicitudEnum estadoSolicitud = evaluarEstadoDeSolicitud(solicitud, fueFirmada);
		
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
				estadoSolicitud.getCodigo());

		List<Tramite> tramitesActualizados = new ArrayList<Tramite>();

		for(Tramite tramite : solicitud.getTramites()){
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());
			tramite.setFechaPresentacion(Calendar.getInstance().getTime());
			tramitesActualizados.add(tramite);
		}
		solicitud.setTramites(tramitesActualizados);
		solicitud.setFechaPresentacion(Calendar.getInstance().getTime());
		//Se evalúa si el resultado de evaluarEstadoDeSolicitud es ATENDIDA
		//Significa que se debe concluir en línea de lo contrario
		//solo se actualizan los estatus correspondientes
		if(estadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA)){
			//Significa que se puede concluir en línea
			concluirSolicitud(solicitud.getSolicitudId(), sujetoTramite, null);
		}else{
			solicitudBusinessService.actualizarEstados(solicitud);
			solicitudBusinessService.actualizarDatosGeneralesDeSolicitud(solicitud);
		}
		
		
//		System.err.println("Se enviara el correo electronico");
//		notificarPorCorreoElectronico(sujetoTramite, solicitud.getSolicitudId(), TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue());
		return solicitud;
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 12/10/2012
	 * @param sujetoTramite
	 * @param idSolicitud
	 * @throws GestionPatronalBusinessException
	 */
	public void notificarPorCorreoElectronico(SujetoObligado sujetoTramite, Long idSolicitud, Integer indFileToAttach) throws GestionPatronalBusinessException{
		Long idPersona=null;
		TipoPersona tipoPersona = new TipoPersona();
		Solicitud solicitud = solicitudBusinessService.consultarDetalleSolicitudPorIdentificador(idSolicitud);
		String nombreTramites = "";
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			idPersona=sujetoTramite.getFisica().getIdPersona();
		}else{
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			idPersona=sujetoTramite.getMoral().getIdPersona();
		}
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(tipoPersona);
		String subject="Solicitud de trámite IMSS "+solicitud.getNoFolioSolicitud();
		
		for(Tramite tramite : solicitud.getTramites()){
			nombreTramites +=tramite.getTipoTramite().getDescripcion()+"/";
		}
		
		StringBuffer body= new StringBuffer();
		body.append("Se le informa que su Solicitud de Trámite ante el IMSS número ");
		body.append(solicitud.getNoFolioSolicitud());
		body.append(" referente al(los) trámite(s) ");
		body.append(nombreTramites);
		body.append(" se encuentra bajo el estatus ");
		body.append(solicitud.getEstadoSolicitud().getDescripcion());
		System.err.println("Mail Body: "+body.toString());
		
		byte[] documentToAttach = null;
		String fileName=null;
		if(indFileToAttach!=null){
			if(indFileToAttach.equals(TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue())){
				documentToAttach = solicitud.getDocumentoAcuse();
				fileName="ACUSE_"+solicitud.getNoFolioSolicitud()+".pdf";
			}else if(indFileToAttach.equals(TipoAccionAfectacionEnum.ATTACH_COMPROBANTE_TRAMITE.getValor().intValue())){
				documentToAttach = solicitud.getDocumentoComprobante();
				fileName="COMPROBANTE_"+solicitud.getNoFolioSolicitud()+".pdf";
			}
		}
		
		enviarCorreo(persona, subject, body.toString(), fileName, documentToAttach, "application/pdf");
	}
	
	/**
	 * Si la Solicitud esta Firmada Digitalmente y el Tr&aacute;mite Relacionado corresponde a la 
	 * Conclusi&oacute;n en LÃ­nea (RV-004-01),se ejecuta lo siguiente:
	 * 		a.	Se cambia el estatus a â€œProcesado en LÃ­neaâ€�
	 * Si la Solicitud esta Firmada Digitalmente y el Tr&aacute;mite Relacionado corresponde a la 
	 * Conclusi&oacute;n Electr&oacute;nica (RV-003-01), se ejecuta lo siguiente:
	 *		a.	Se cambia el estatus de la solicitud a â€œPara procesarse en BackOfficeâ€�
	 * Si la Solicitud NO esta Firmada Digitalmente o el Tr&aacute;mite Relacionado corresponde a la Conclusi&oacute;n Presencial (RV-003-02), se ejecuta lo siguiente:
	 *		a.	Se cambia el estatus de la solicitud a â€œPor presentarse a Ventanillaâ€�
	 *
	 * @author Hugo Martinez
	 * @Date 05/09/2012
	 * @param solicitud
	 * @return EstadoSolicitudEnum
	 */
	private EstadoSolicitudEnum evaluarEstadoDeSolicitud(Solicitud solicitud, boolean fueFirmada){
		EstadoSolicitudEnum estadoPorAsignar = EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE;
		boolean concluirEnLinea = true;
		for(Tramite tramite : solicitud.getTramites()){
			Integer tipoConclusion = tramite.getTipoTramite().getIndTipoConclusion();
			if(tipoConclusion.equals(TramiteTipoConclusionEnum.VENTANILLA.getCodigo())
					|| tipoConclusion.equals(TramiteTipoConclusionEnum.BACKOFFICE.getCodigo())){
				concluirEnLinea = false;
				if(tipoConclusion.equals(TramiteTipoConclusionEnum.VENTANILLA.getCodigo())){
					estadoPorAsignar=EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA;
				}
				break;
			}
		}
		
		if(fueFirmada)
			estadoPorAsignar = EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE;
		
		if(concluirEnLinea == true){
			estadoPorAsignar=EstadoSolicitudEnum.ATENDIDA;
		}
		
		return estadoPorAsignar;
	}
	
	/**
	 * Verifica que todos los tr&aacute;mites de datos generales
	 * han sido enviados 
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param solicitud
	 */
	private void validarCompletesDeTramites(Solicitud solicitud, TipoPersonaFiscal tipoPersona) throws GestionPatronalBusinessException{
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.obtenerEnumById(solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue());
		boolean tramiteActualizaDenominacionSocial = false;
		boolean tramiteActualizaRepresentanteLegal = false;
		boolean tramiteActualizaSocio = false;
		boolean tramiteActualizaRegistroSindicato = false;
		boolean tramiteActualizaEscrituraConstitutiva = false;
		boolean tramiteActualizaCentroTrabajo = false;
		
		
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getTipoTramite().getIdTipoTramite().equals(
					TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
				tramiteActualizaDenominacionSocial=true;
			}
			if(tramite.getTipoTramite().getIdTipoTramite().equals(
					TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
				tramiteActualizaRepresentanteLegal=true;
			}
			if(tramite.getTipoTramite().getIdTipoTramite().equals(
					TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
				tramiteActualizaSocio=true;
			}
			if(tramite.getTipoTramite().getIdTipoTramite().equals(
					TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
				tramiteActualizaRegistroSindicato=true;
			}
			if(tramite.getTipoTramite().getIdTipoTramite().equals(
					TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
				tramiteActualizaEscrituraConstitutiva=true;
			}
			if(tramite.getTipoTramite().getIdTipoTramite().equals(
					TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())){
				tramiteActualizaCentroTrabajo=true;
			}
		}
		
		if(tipoSolicitud.equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO)){
			if(!tramiteActualizaCentroTrabajo){
				throw new GestionPatronalBusinessException("Se requiere los siguientes tr&aacute;mites esten contenidos dentro de la solicitud: <br>" +
						"* Actualizaci&oacute;n de Centro de Trabajo.");
			}else{
				return;//Debe saltar las siguientes validaciones por ello se incluye el return
			}
		}
		
		if(tipoPersona.equals(TipoPersonaFiscal.FISICA)){
			if(!(tramiteActualizaDenominacionSocial && tramiteActualizaRepresentanteLegal)){
				throw new GestionPatronalBusinessException("Se requiere que los siguientes tr&aacute;mites esten contenidos dentro de la solicitud: <br>" +
						"* Actualizaci&oacute;n de Denominacion/Razon Social. <br>" +
						"* Actualizaci&oacute;n de representantes Legales. <br>");
			}
		}else if(!(tramiteActualizaDenominacionSocial && 
				tramiteActualizaRepresentanteLegal && 
				tramiteActualizaSocio && 
				(tramiteActualizaRegistroSindicato || tramiteActualizaEscrituraConstitutiva))){
			throw new GestionPatronalBusinessException("Se requiere que los siguientes tr&aacute;mites esten contenidos dentro de la solicitud: <br>" +
					"* Actualizaci&oacute;n de Denominacion/Razon Social. <br>" +
					"* Actualizaci&oacute;n de Representantes Legales. <br>" +
					"* Actualizaci&oacute;n de Socios. <br>" +
					"* Actualizaci&oacute;n de Registro Sindicato o Escritura Constitutiva");
		}
		
		
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param persona
	 * @param tipoTramite
	 * @return SujetoObligado
	 */
	private SujetoObligado inicializarSujetoObligadoPorTramite(Persona persona, TipoTramiteEnum tipoTramite){
		SujetoObligado sujetoTramite = obtenerDatosFiscales(persona);
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL)){
			if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA))
				sujetoTramite.getFisica().setRepresentantesLegales(new ArrayList<RepresentanteLegal>());
			else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL))
				sujetoTramite.getMoral().setRepresentantesLegales(new ArrayList<RepresentanteLegal>());
		}
		
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO)){
			if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA))
				sujetoTramite.getFisica().setSocios(new ArrayList<Socio>());
			else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL))
				sujetoTramite.getMoral().setSocios(new ArrayList<Socio>());
		}
		
		return sujetoTramite;
	}

	@Override
	public Map<String, Object> validarTramiteAfiliacionActivo(Long idSolicitud, SujetoObligado sujetoTramite, TipoTramiteEnum tipoTramite, Usuario usuario) {
//		TipoSolicitudEnum tipoSolicitud=solicitudBusinessService.obtenerTipoSolicitudEnBaseAlTipoTramite(tipoTramite);
		Map<String, Object> validationMap = new HashMap<String, Object>();
//		Solicitud solicitud = solicitudBusinessService.obtenerSolicitudActiva(sujetoTramite, tipoSolicitud, usuario);
		if(idSolicitud==null || (idSolicitud!=null && idSolicitud == 0)){
			validationMap.put("operacion", TipoAccionAfectacionEnum.AGREGAR_SOLICITUD);
		}else{
			Solicitud solicitud = solicitudBusinessService.consultarSolicitudPorId(idSolicitud);
			Tramite tramite = solicitudBusinessService.obtenerTramitePorTipo(solicitud, tipoTramite);
//			Tramite tramite = solicitudBusinessService.obtenerTramiteDeSolicitudActivaPorTipo(sujetoTramite, tipoTramite, usuario);
			if(tramite == null){
				validationMap.put("operacion", TipoAccionAfectacionEnum.AGREGAR_TRAMITE_SOLICITUD);
				validationMap.put("folio", solicitud.getNoFolioSolicitud());
			}else{
				validationMap.put("operacion", TipoAccionAfectacionEnum.ACTUALIZAR_TRAMITE);
				validationMap.put("folio", solicitud.getNoFolioSolicitud());
			}
		}

		return validationMap;
	}

	@Override
	public Solicitud obtenerSolicitudEnProceso(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud) {
		return  solicitudBusinessService.obtenerSolicitudEnProceso(sujetoObligado, tipoSolicitud, null);
	}
	
	
	
	private EstadoTramiteEnum obtenerEstadoTramitePorRol(Usuario usuario){
		EstadoTramiteEnum estadoTramite = EstadoTramiteEnum.INICIADO;
		if(esTramitador(usuario)){
			estadoTramite = EstadoTramiteEnum.ACTIVO;
		}
		return estadoTramite;
	}
	
	private boolean esTramitador(Usuario usuario){
		return usuario.getPerfilUsuario().getIdPerfilUsuario().equals(1L);
	}

	@Override
	public List<Solicitud> listarSolicitudesEnProceso(
			SujetoObligado sujetoObligado, boolean esTramitador) {
		return solicitudBusinessService.listarSolicitudesEnProceso(sujetoObligado, esTramitador);
	}

	@Override
	public void concluirSolicitud(Long idSolcitud, SujetoObligado sujetoTramite, Usuario usuario) throws GestionPatronalBusinessException{
		Solicitud solicitud = solicitudBusinessService.consultarSolicitudPorId(idSolcitud);
		System.err.println("Fecha de presentacion al concluir: "+solicitud.getFechaPresentacion());
		if(solicitud.getFechaPresentacion()==null){
			System.err.println("Se asignara fecha de presentacion pues es solicitud en ventanilla");
			solicitud.setFechaPresentacion(Calendar.getInstance().getTime());
		}
		solicitud.setFechaConclusion(Calendar.getInstance().getTime());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
				EstadoSolicitudEnum.ATENDIDA.getCodigo());	
		try {
			concluirTramites(solicitud, usuario);
			solicitudBusinessService.actualizarEstados(solicitud);
			solicitudBusinessService.actualizarDatosGeneralesDeSolicitud(solicitud);
			solicitudService.generarDocumentos(solicitud);		
			log.error("Se solicita generacion de analisis de la solicitud: "+solicitud.getSolicitudId());
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud().longValue()==TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue()){
				try {
					log.error("Se solicita actualizacion final de gce");
					//agregamos SO para que no truene al crear tramite para MACII
					if(solicitud.getSujetoObligado() == null && sujetoTramite != null){
						log.debug("::Agregando sujeto obligado");
						solicitud.setSujetoObligado(sujetoTramite);
					}								
					gceSolicitudService.cancelarAnalisisPorRegistroPatronal(
							obtenerNrpCompleto(sujetoTramite), 
							EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave() ,
							solicitud);
				} catch (PersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClasificacionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			log.error("error al genera concluir la solicitud " , e);
			throw new GestionPatronalBusinessException( "Error al concluir la soliitud " + e.getMessage());
		}
	}
	
	
	/**
	 * Ejecuta la afectaci&oacute;n por tipo de tr&aacute;mite y cambia el estatus a cerrado.
	 * @author Hugo Martinez
	 * @Date 22/08/2012
	 * @param solicitud
	 */
	private void concluirTramites(Solicitud solicitud, Usuario usuario){
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum
			.getById(solicitud.getOrigenSolicitud().getIdOrigenSolicitud());
		
		for(Tramite tramite : solicitud.getTramites()){
			tramite.getEstadoTramite().setIdEstadoTramitePersona(
					EstadoTramiteEnum.CERRADO.getCodigo());
			tramite.setFechaConclusion(Calendar.getInstance().getTime());
			if(tramite.getIndRatificado()!=null 
					&& tramite.getIndRatificado()){
				continue;
			}
			try{
				if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
					sujetoObligadoService.actualizarDenominacionRazonSocial(tramite, usuario,true);
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
					sujetoObligadoService.actualizarEscrituraConstitutiva(tramite, usuario);
					TramiteMoral tMoral = (TramiteMoral)tramite;
					sujetoObligadoService.eliminarSindicatoDePersona(tMoral.getMoral().getIdPersona());
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
					sujetoObligadoService.actualizarRegistroSindicato(tramite, usuario);
					TramiteMoral tMoral = (TramiteMoral)tramite;
					sujetoObligadoService.eliminarActaConstitutivaDePersona(tMoral.getMoral().getIdPersona());
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
					sujetoObligadoService.actualizarDatosDeContacto(tramite, usuario);
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
					socioServiceBusinessRemote.afectarTramiteAltaSocios(tramite, solicitud.getSolicitudId());
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.BAJA_SOCIO.getCodigo())){
					socioServiceBusinessRemote.afectarTramiteBajaSocios(tramite, solicitud.getSolicitudId());					
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
					
					if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET))
						tramite=actualizarDatosFielTramite(tramite, solicitud.getFirmaElectronica());
					
					sujetoObligadoService.actualizarRepresentanteLegal(tramite, usuario, solicitud.getSolicitudId(), origenSolicitud);
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo())){
					representanteService.afectarTramiteBajaRepresentantesLegales(tramite);
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PERSONAS_AUTORIZADAS.getCodigo())){
					personasAutorizadasService.afectarTramiteAltaPersonaAutorizada(tramite, solicitud.getSolicitudId());
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.BAJA_PERSONA_AUTORIZADA.getCodigo())){
					personasAutorizadasService.afectarTramiteBajaPersonaAutorizada(tramite);
				}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())){
					System.err.println("<OTIKA>Se procede a actualizar centro de trabajo " + solicitud.getSujetoObligado() +"\n");
						if (solicitud.getTramites().size()>0){
							TramiteSujetoObligado tso = (TramiteSujetoObligado)solicitud.getTramites().get(0);
							SujetoObligado sujetoConsulta = tso.getSujetoObligado();
							SujetoObligado sujetoObligadoActual = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoConsulta);
							tso.getSujetoObligado().getCntroTrabajo().setCveIdPatronSujetoObligado(
									tso.getSujetoObligado().getCveIdSujetoObligado());						
								actualizarCentroTrabajo(tso.getSujetoObligado(),tramite, usuario, true);
								clasificacionServiceBusiness.afectarClasificacionActividadEconomica(tso.getSujetoObligado(), solicitud);
								solicitud.setSujetoObligado(sujetoObligadoActual);
								log.error("Se actualiza estado de la solicitud");
								solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
										EstadoSolicitudEnum.ATENDIDA.getCodigo());	
								solicitudBusinessService.actualizarEstados(solicitud);
								log.error("Se actualizo solicitud centro trabajo a concluida");
								log.error("Se solicita generacion de analisis de la solicitud: "+solicitud.getSolicitudId());
								gceSolicitudService.cancelarAnalisisPorRegistroPatronal(
										obtenerNrpCompleto(tso.getSujetoObligado()), 
										EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave() ,
										solicitud);
						}
				}
			}catch(GestionPatronalBusinessException gpbe){
				gpbe.printStackTrace();
				System.err.println("Se presento un error al afectar la base de datos con el tr&aacute;mite: "+
							tramite.getTipoTramite().getIdTipoTramite());	
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
	}
	
	private Tramite actualizarDatosFielTramite(Tramite tramite, FirmaElectronica firma){
		TramiteRepresentanteLegal trl = (TramiteRepresentanteLegal)tramite;
		if(trl.getFisicaRepresentada()!=null)
			trl.setFisicaRepresentada((Fisica)actualizarInformacionFiel(trl.getFisicaRepresentada(), firma));
		else 
			trl.setMoralRepresentada((Moral)actualizarInformacionFiel(trl.getMoralRepresentada(), firma));
		
		return trl;
	}
		
	private Persona actualizarInformacionFiel(Persona persona, FirmaElectronica firma){
		
		Fiel fiel = new Fiel();
		fiel.setClaveSerial(firma.getSerialCertificado());
		fiel.setFechaValidaInicio(firma.getIniciaVigenciaCertificado());
		fiel.setFechaValidaFin(firma.getFinVigenciaCertificado());
		persona.setFiel(fiel);
		
		return persona;
	}
	
	private String obtenerNrpCompleto(SujetoObligado sujetoTramite){
		String numeroRPCompleto=sujetoTramite.getNumeroRegistroPatronal();
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
	
	
	public String validarMediosContacto(List<MedioContacto> mediosContacto){
		for (MedioContacto medioContacto : mediosContacto){
			if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO.longValue()) || medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.longValue()) ){
				return "";
			}
		}
		return "<font color=red>Debe dar de alta por lo menos uno de los siguientes datos de contacto:<br><br>" +
				"* Tel&eacute;fono fijo <br>" +
				"* Correo electr&oacute;nico</font><br><br>";
	}	
	
	public void validarSubdOrigenSubDestino(SujetoObligado sujetoObligado) throws GestionPatronalBusinessException{
		
		Subdelegacion subdelegacion = sujetoObligadoService.obtenerSubdelegacion(sujetoObligado.getCveIdSujetoObligado());
		if(subdelegacion==null)
			return;
		
		if(subdelegacion.getClave().equals(sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getClave()))
			return;
		
		List<Subdelegacion> subdelegacionesCompatibles = sujetoObligadoService.obtenerSubdelegacionesCompatibles(subdelegacion.getId());
		boolean subdelegacionValida=false;
		for(Subdelegacion subdelegacionCompatible:subdelegacionesCompatibles){
			super.log.debug("Subdelegacion nueva: "+sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getClave());
			super.log.debug("Subdelegacion compatible: "+sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getClave());
			if(subdelegacionCompatible.getClave().equalsIgnoreCase(
					sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getClave())){
				super.log.debug("Subdelegacion origen valida");
				subdelegacionValida=true;
				break;
			}
		}
		
		if(!subdelegacionValida)
			throw new GestionPatronalBusinessException("error.subdel.incompatible");
	}

	@Override
	public void actualizarCentroTrabajo(SujetoObligado sujetoObligado,Tramite tramite, Usuario usuario, boolean notificarSindo){		
		try {
						
			//Actualizar el nombre comercial
			sujetoObligadoService.actualizarNombreComercial(sujetoObligado);
			Domicilio domicilio = sujetoObligado.getCntroTrabajo();
			//Asociar el nuevo domicilio al sujetoObligado
			domicilio=generarObjetoDomicilio(domicilio);
			domicilio=domicilioServiceBusiness.registrarDomicilio(domicilio);
			System.err.println("New ID de domicilio:"+domicilio.getClave());							
			CentroTrabajo ct = (CentroTrabajo)domicilio;
			ct.setCveIdPatronSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
			
			Subdelegacion subdelegacionDestino = sujetoObligado.getClasificacion()!=null 
					&& sujetoObligado.getClasificacion().getSujetoObligado()!=null
					&& sujetoObligado.getClasificacion().getSujetoObligado().getSubdelegacion()!=null 
					? sujetoObligado.getClasificacion().getSujetoObligado().getSubdelegacion()
					: sujetoObligado.getSubdelegacion();
			ct=this.sujetoObligadoService.actualizarCentroTrabajo(ct, usuario, subdelegacionDestino, notificarSindo, sujetoObligado.getMunicipioIMSS().getIdMunicipio());
		} catch (DomicilioNoValidoException e) {
			e.printStackTrace();
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
	}
	
	private Domicilio generarObjetoDomicilio(Domicilio domicilio){
		TipoDomicilio tipoDomicilio=new TipoDomicilio();
		TipoAmbito ambito = new TipoAmbito();
		ambito.setClave(1l);
		domicilio.setAmbito(ambito);			
		tipoDomicilio.setClave(3);
		domicilio.setTipoDomicilio(tipoDomicilio);
		domicilio.setClave(null);
		return domicilio;
	}
	
	@Override
	public SujetoObligado obtenerDetalleDeRegistroPatronal(
			SujetoObligado sujetoObligado) {
		return sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
	}

	@Override
	public boolean existeSolicitudPendienteDeAsignacion(
			SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud) {
		System.err.println("Verificando solicitud pendiente de asignaci&oacute;n...");
		Solicitud solicitudPorAsignar = solicitudBusinessService.obtenerSolicitudPendienteDeAsignar(sujetoObligado, tipoSolicitud);
		if(solicitudPorAsignar!=null)
			return true;
		
		return false;
	}

	@Override
	public Solicitud cancelarSolicitud(Solicitud solicitud) {
		Usuario solicitante = solicitud.getSolicitante();
		solicitud = solicitudBusinessService.consultarSolicitudPorId(solicitud.getSolicitudId());
		List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
		for(Tramite tramite : solicitud.getTramites()){
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
			tramitesActualizados.add(tramite);
		}
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
		solicitud.setTramites(tramitesActualizados);
		solicitudBusinessService.actualizarEstatus(solicitud);
		solicitud.setSolicitante(solicitante);
		solicitudBusinessService.actualizarDatosGeneralesDeSolicitud(solicitud);
		return solicitud;
	}
	
	@Override
	public Solicitud rechazarSolicitud(Solicitud solicitud) {
		RazonCancelacion razon = solicitud.getRazonCancelacion();
		Usuario solicitante = solicitud.getSolicitante();
		solicitud = solicitudBusinessService.consultarSolicitudPorId(solicitud.getSolicitudId());
		List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
		for(Tramite tramite : solicitud.getTramites()){
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
			tramitesActualizados.add(tramite);
		}
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.RECHAZADA.getCodigo());
		solicitud.setTramites(tramitesActualizados);
		solicitudBusinessService.actualizarEstatus(solicitud);
		solicitud.setSolicitante(solicitante);
		solicitud.setRazonCancelacion(razon);
		solicitudBusinessService.actualizarDatosGeneralesDeSolicitud(solicitud);
		return solicitud;
	}
	
	@Override
	public Map<String, Object> validaRegistroPatronalValidoPorRFC(
			SujetoObligado sujetoObligado, Usuario usuario) {
		String rfcTrabajo = sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) 
				? sujetoObligado.getFisica().getRfc() : sujetoObligado.getMoral().getRfc();
		System.err.println("RFC Trabajo: "+rfcTrabajo);
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado soEncontrado = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		if(soEncontrado==null){
			result.put("mensajeErrorKey", "error.registro.patronal.inexistente");
			return result;
		}else{
			String rfcEncontrado = soEncontrado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) 
					? soEncontrado.getFisica().getRfc() : soEncontrado.getMoral().getRfc();
			
			System.err.println("RFC Trabajo: "+rfcTrabajo+" RFC Encontrado"+rfcEncontrado);		
			
			if(rfcEncontrado.equalsIgnoreCase(rfcTrabajo)){
				return result;
			}else{
				log.debug("Perfil de usuario: "+usuario.getPerfilUsuario().getIdPerfilUsuario().longValue());
				log.debug("Rol Tramitador Identificador: "+RolEnum.TRAMITADOR.getCodigo().longValue());
				if(usuario.getPerfilUsuario().getIdPerfilUsuario().longValue() == RolEnum.TRAMITADOR.getCodigo().longValue()){
					log.debug("Es tramitador");
					result.put("mensajeErrorKey", "error.registro.patronal.propietario");
					result.put("parametrosMensaje", rfcEncontrado);
				}else{
					result.put("mensajeErrorKey", "error.registro.patronal.inaccesible");
				}
			}
		}
		return result;
	}

	@Override
	public Solicitud obtenerSolicitudPendientePorAsignar(
			SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud) {
		return solicitudBusinessService.obtenerSolicitudPendienteAsignacion(sujetoObligado, tipoSolicitud);
	}

	@Override
	public Map<String, Object> obtenerInformacionDeSolicitud(Long idSolicitud) {
		log.info("/**** OBTNER INFORMACION DE LA SOLICITUD :: "+idSolicitud+" ****/");
		Solicitud solicitud = solicitudBusinessService.consultarSolicitudPorId(idSolicitud);
		TramiteSujetoObligado tramiteSujetoObligado = inicializarInformacionSujetoTramite(solicitud.getTramites().get(0));
		SujetoObligado sujetoTramite = tramiteSujetoObligado.getSujetoObligado();
		
		Map<String, Object> mapData = inicializarMapaDatos();
		for(Tramite tramite : solicitud.getTramites()){
			integrarInformacionDeTramite(tramite, sujetoTramite,mapData);
		}
		solicitud.setTramites(new ArrayList<Tramite>());
		tramiteSujetoObligado.setSujetoObligado(sujetoTramite);
		solicitud.getTramites().add(tramiteSujetoObligado);
		
		mapData.put("solicitudData", solicitud);
		return mapData;
	}
	
	private Map<String, Object> inicializarMapaDatos(){
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("isTramiteDenominacion", false);
		mapData.put("isTramiteDatosContacto", false);
		mapData.put("isTramiteActaConstitutiva", false);
		mapData.put("isTramiteRegistroSindicato", false);
		mapData.put("isTramiteSocio", false);
		mapData.put("isTramiteRepresentanteLegal", false);
		return mapData;
	}
	
	@Override
	public TramiteSujetoObligado inicializarInformacionSujetoTramite(Tramite tramite){
		TramiteSujetoObligado tramiteSujetoObligado = new TramiteSujetoObligado();
		
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())){
			tramiteSujetoObligado = (TramiteSujetoObligado)tramite;
		}else{
			SujetoObligado sujetoTramite = obtenerInformacionTramite(tramite);
			Fisica fisica = null;
			Moral moral = null;
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				fisica =afiliacionEntity.obtenerDatosFiscalesPersonaFisica(sujetoTramite.getFisica().getIdPersona());
			}else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
				moral = afiliacionEntity.obtenerDatosFiscalesPersonaMoral(sujetoTramite.getMoral().getIdPersona());
			}
			sujetoTramite.setFisica(fisica);
			sujetoTramite.setMoral(moral);
			tramiteSujetoObligado.setSujetoObligado(sujetoTramite);
		}
		tramiteSujetoObligado.setFechaPresentacion(tramite.getFechaPresentacion());
		tramiteSujetoObligado.setFechaEfecto(tramite.getFechaEfecto());
		tramiteSujetoObligado.setFechaConclusion(tramite.getFechaConclusion());
		tramiteSujetoObligado.setFechaTramite(tramite.getFechaTramite());
		
		return tramiteSujetoObligado;
	}
	
	private void integrarInformacionDeTramite(Tramite tramite, SujetoObligado sujetoTramiteBase, Map<String, Object> mapData){
		SujetoObligado sujetoTramite = null;
		if(tramite.getIndRatificado())
			return;
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
			sujetoTramite = obtenerInformacionTramite(tramite);
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				sujetoTramiteBase.getFisica().setNombre(sujetoTramite.getFisica().getNombre());
				sujetoTramiteBase.getFisica().setPrimerApellido(sujetoTramite.getFisica().getPrimerApellido());
				sujetoTramiteBase.getFisica().setSegundoApellido(sujetoTramite.getFisica().getSegundoApellido());
				sujetoTramiteBase.getFisica().setCurp(sujetoTramite.getFisica().getCurp());
				sujetoTramiteBase.getFisica().setRfc(sujetoTramite.getFisica().getRfc());
			}else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
				sujetoTramiteBase.setMoral(sujetoTramite.getMoral());
				sujetoTramiteBase.getMoral().setRfc(sujetoTramite.getMoral().getRfc());
				sujetoTramiteBase.getMoral().setRazonSocial(sujetoTramite.getMoral().getRazonSocial());
				sujetoTramiteBase.getMoral().setTipoSociedad(sujetoTramite.getMoral().getTipoSociedad());
			}
			mapData.put("isTramiteDenominacion", true);
		}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
			sujetoTramite = obtenerInformacionTramite(tramite);
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				sujetoTramiteBase.getFisica().setMediosContacto(sujetoTramite.getFisica().getMediosContacto());
			else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL))
				sujetoTramiteBase.getMoral().setMediosContacto(sujetoTramite.getMoral().getMediosContacto());
			mapData.put("isTramiteDatosContacto", true);
		}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
			sujetoTramite = obtenerInformacionTramite(tramite);
			sujetoTramiteBase.getMoral().setEscrituraConstitutiva(sujetoTramite.getMoral().getEscrituraConstitutiva());
			mapData.put("isTramiteActaConstitutiva", true);
		}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
			sujetoTramite = obtenerInformacionTramite(tramite);
			sujetoTramiteBase.getMoral().setRegistroSindicato(sujetoTramite.getMoral().getRegistroSindicato());
			mapData.put("isTramiteRegistroSindicato", true);
		}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
			sujetoTramite = obtenerInformacionTramite(tramite);
			sujetoTramiteBase.getMoral().setSocios(sujetoTramite.getMoral().getSocios());
			mapData.put("isTramiteSocio", true);
		}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
			sujetoTramite = obtenerInformacionTramite(tramite);
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				sujetoTramiteBase.getFisica().setRepresentantesLegales(sujetoTramite.getFisica().getRepresentantesLegales());
			else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL))
				sujetoTramiteBase.getMoral().setRepresentantesLegales(sujetoTramite.getMoral().getRepresentantesLegales());
			mapData.put("isTramiteRepresentanteLegal", true);
		}
	}
	
	private SujetoObligado obtenerInformacionTramite(Tramite tramite){
		SujetoObligado sujetoTramite = new SujetoObligado();
		
		if(tramite instanceof TramiteFisica){
			TramiteFisica tramitePF = (TramiteFisica)tramite;
			sujetoTramite.setFisica(tramitePF.getFisica());
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		}else if(tramite instanceof TramiteMoral){
			TramiteMoral tramitePM = (TramiteMoral)tramite;
			sujetoTramite.setMoral(tramitePM.getMoral());
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
		
			
		return sujetoTramite;
	}
	
	/**
	 * 
	 * Retorna la subdelegación en base al código postal y codigo de asentamiento del domicilio proporcionado
	 * 
	 * Si alguno de los parametros no es proporcionado se envía el código de error correspondiente:
	 * parametro.codigo.postal.requerido ó parametro.clave.asentamiento.requerido
	 * asegurese de tener una descripción internacionalizada para este codigo de error.
	 * 
	 * Si no existe un municipio inegi para el código postal y cve de asentamiento 
	 * proporcionado se envia el código: municipio.inegi.inexistente
	 * 
	 * Si no existe un municipio imss asociado al municipio inegi relacionado al cp proporcionado
	 * se envia el mensaje de error: municipio.imss.inexistente
	 * 
	 * Si no existe una subdelegación configurada para el municipio imss relacionado con el municipio
	 * inegi al cual pertenece el codigo postal proporcionado se envía el mensaje de error: subdelegacion.inexistente
	 * 
	 * @author Hugo Martinez
	 * @Date 11/10/2012
	 * @param domicilio Se requiere el código Postal y la clave de asentamiento
	 * @return Subdelegacion
	 * @throws GestionPatronalBusinessException 
	 */
	public Subdelegacion obtenerSubdelegacionPorDomicilio(Domicilio domicilio) throws GestionPatronalBusinessException{
		Subdelegacion subdel = new Subdelegacion();
		String codigoPostal = domicilio.getCodigoPostal().getCodigoPostal();
		String codigoAsentamiento = domicilio.getAsentamiento().getClave();
		Municipio municipioInegi = afiliacionEntity.obtenerMunicipioInegiPorCodigoPostal(codigoPostal,codigoAsentamiento);
		if(municipioInegi== null){
			throw new GestionPatronalBusinessException("municipio.inegi.inexistente");
		}
		
		Long cveIdMunImss=afiliacionEntity.obtenerIdentificadorMunicipioImssPorMunicipioInegi(municipioInegi);
		if(cveIdMunImss==null){
			throw new GestionPatronalBusinessException("municipio.imss.inexistente");
		}
		
		subdel = afiliacionEntity.obtenerSubdelegacionPorMunicipioImss(cveIdMunImss);
		if(subdel==null){
			throw new GestionPatronalBusinessException("subdelegacion.inexistente");
		}
		
		return subdel;
	}
	
	private void enviarCorreo(Persona persona, String subject, String body, String nombreArchivo, byte[] archivo, String contentType) throws GestionPatronalBusinessException{
		System.err.println("Enviando mail.....");
		
		MailProperties mailProperties = new MailProperties();
		String correoDestino=null;
		try {
			List<MedioContacto> contactos = mediosContactoService.consultarMedioDeContactoPersona(persona);
			for(MedioContacto contacto : contactos){
				if(contacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())){
					correoDestino = contacto.getDesFormaContacto();
				}
			}
			
		} catch (PersonaSinMedioDeContactoException e) {
			System.err.println("La persona no tiene medios de contacto");
		}
		System.err.println("Mail properties..."+mailProperties);
		
		String toAddress= "ignacio.espinosav@imss.gob.mx"; 
		
		if(correoDestino == null){
//			throw new GestionPatronalBusinessException("error.correo.destino.inexistente", 10001);
			System.err.println("No hay correo de envío");
		}else{
			toAddress+= ", "+correoDestino;
		}
		
		String host=MailConfigUtil.host;
		String mailUser= MailConfigUtil.mailUser;
		String mailUserPassword=MailConfigUtil.mailUserPassword;
		String fromAddress=MailConfigUtil.fromAddress;
		
		if(mailService==null){
			System.err.println("No se inyecto");
			mailService = new MailServiceImpl();
		}
		try{
			if(archivo!=null){
				mailService.sendMailWithAttach(host, mailUser, mailUserPassword, subject, fromAddress, toAddress, "", "", body, nombreArchivo, archivo, contentType);
			}else{
				mailService.sendMail(host, mailUser, mailUserPassword, subject, fromAddress, toAddress, "", "", body);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.err.println("Se envío el correo...");
	}
	
	@Override
	public void validarSolicitudCompletes(SujetoObligado sujetoTramite, TipoSolicitudEnum tipoSolicitud)
			throws GestionPatronalBusinessException {
		Solicitud solicitud = solicitudBusinessService.obtenerSolicitudEnCaptura(sujetoTramite, tipoSolicitud, null);
		if(solicitud==null)
			throw new GestionPatronalBusinessException("No Existe ninguna solicitud En captura para el tipo de solicitud proporcionado");
		
		validarCompletesDeTramites(solicitud, sujetoTramite.getTipoPersonaFiscal());		
	}
	
	
	
	@Override
	public Map<String, Object> cargarDatosDeTramite(TipoTramiteEnum tipoTramite, Persona persona, Long idSolicitud) {
		System.err.println("Estoy en gestionar tramite ......");
		Solicitud solicitudActiva=solicitudBusinessService.consultarSolicitudPorId(idSolicitud);
		System.err.println("Solicitud activa al gestionarDatos Tramite: "+solicitudActiva);
		System.err.println("Inicializo el tramite con sujetoObligado ......");
		SujetoObligado sujetoTramite = null;
		boolean tramiteActivo=false;
		boolean tramiteRatificado=false;
		Map<String, Object> result = new HashMap<String, Object>();
		if(solicitudActiva!=null){
			System.err.println("Si hay solicitud activa ......");
			System.err.println("Tramites: "+solicitudActiva.getTramites().size());
			result.put("folioSolicitud", solicitudActiva.getNoFolioSolicitud());
			for(Tramite tramite : solicitudActiva.getTramites()){
				System.err.println("Comparando tramite: "+tipoTramite +" con todos");
					if(tipoTramite.getCodigo().equals(tramite.getTipoTramite().getIdTipoTramite())){
						tramiteActivo=true;
						System.err.println("Tramite activo de tipo : "+tipoTramite +" tramite: "+tramite);
						System.err.println("Indicador de ratificado Tramite tipo : "+tipoTramite +" ratificado: "+tramite.getIndRatificado());
						tramiteRatificado=tramite.getIndRatificado()!= null ? tramite.getIndRatificado() : false;
						if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_SOCIO) ){
							System.err.println("Encontre tramite de persona ......");

							if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA) ){
								sujetoTramite = new SujetoObligado();
								TramiteFisica tramiteFisica = (TramiteFisica)tramite;
								sujetoTramite.setFisica(tramiteFisica.getFisica());
								if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
									sujetoTramite.setDatosICA(tramiteFisica.getDatosICA());
									sujetoTramite.setDatosMDM(tramiteFisica.getDatosMDM());
								}
								break;
							}
							if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
								sujetoTramite = new SujetoObligado();
								TramiteMoral tramiteMoral = (TramiteMoral)tramite;
								sujetoTramite.setMoral(tramiteMoral.getMoral());
								if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
									sujetoTramite.setDatosICA(tramiteMoral.getDatosICA());
									sujetoTramite.setDatosMDM(tramiteMoral.getDatosMDM());
								}
								break;
							}
						}else if(tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA) ||
								tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY) ||
								tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES) ||
								tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS) ||
								tipoTramite.equals(TipoTramiteEnum.COMODATO) ||
								tipoTramite.equals(TipoTramiteEnum.ENAJENACION) ||
								tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO) ||
								tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO) ||
								tipoTramite.equals(TipoTramiteEnum.ESCISION) ||
								tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL) ||
								tipoTramite.equals(TipoTramiteEnum.FUSION) ||
								tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES) ||
								tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO) ||
								tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION) ||
								tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO)
								){							
								System.err.println("Encontre tramite de sujetoObligado ......");
								TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
								sujetoTramite = tso.getSujetoObligado();
								break;							
						}
					}	
			}
		}
		
		if(!tramiteActivo){
			sujetoTramite=inicializarSujetoObligadoPorTramite(persona, tipoTramite);
		}		
		
		result.put("tramiteActivo", tramiteActivo);
		result.put("tramiteData", sujetoTramite);
		result.put("tramiteRatificado", tramiteRatificado);
		return result;
	}
	
	private TipoPersonaFiscal obtenerTipoPersonaFiscal(String rfc) {
		if (rfc.length() == 13)
			return TipoPersonaFiscal.FISICA;
		else if (rfc.length() == 12)
			return TipoPersonaFiscal.MORAL;
		else
			return null;
	}

	@Override
	public Solicitud crearSolicitudDeAltaPatronal(SujetoObligado sujetoTramite, Usuario usuario, OrigenSolicitudEnum origenSolicitud) throws GestionPatronalBusinessException{
		Solicitud solicitud = inicializarSolicitudAlta(EstadoSolicitudEnum.REGISTRADA, usuario, sujetoTramite, origenSolicitud);
		solicitud = solicitudBusinessService.crearNuevaSolicitud(solicitud);
		return solicitud;
	}

	@Override
	public Solicitud crearSolicitudDeAltaPatronalConEstado(
			SujetoObligado sujetoTramite, EstadoSolicitudEnum estadoSol,
			Usuario usuario, OrigenSolicitudEnum origenSolicitud)
			throws GestionPatronalBusinessException {
		Solicitud solicitud = inicializarSolicitudAlta(estadoSol, usuario, sujetoTramite, origenSolicitud);
		solicitud = solicitudBusinessService.crearNuevaSolicitud(solicitud);
		return solicitud;	}

	@Override
	public Solicitud actualizarSolicitudDeAltaPatronal(Solicitud solicitud, SujetoObligado sujetoTramite) throws GestionPatronalBusinessException{
		solicitudBusinessService.actualizarDatosGeneralesDeSolicitud(solicitud);
		return solicitudBusinessService.actualizarTramiteAlta(solicitud, sujetoTramite);
	}
	
	@Override
	public void finalizarSolicitudDeAltaPatronal(Solicitud solicitud, SujetoObligado sujetoTramite, FirmaElectronica firmaElectronica, 
			boolean encolarSolicitud) throws GestionPatronalBusinessException,
			SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		actualizarSolicitudDeAltaPatronal(solicitud, sujetoTramite);
		if(encolarSolicitud)
			solicitudBusiness.enviarSolicitudAProceso(solicitud, firmaElectronica);
		
	}

	/**
	 * Inserta una nueva solicitud del tipo y con el estado proporcionados
	 * 
	 * @author Hugo Armando MartÃ­nez Cham&oacute;nica
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param usuario
	 * @return Solicitud
	 */
	@Override
	public Solicitud inicializarSolicitudAlta(EstadoSolicitudEnum estadoSolicitud, Usuario usuario,
			SujetoObligado sujetoTramite, OrigenSolicitudEnum origenSolicitud) {
		System.out.println("Iniciando la creaci&oacute;n de  la solicitud de alta");
		System.err.println("Sujeto en la solicitud de alta: " + sujetoTramite);

		Solicitud solicitud;
		try {
			Subdelegacion subdelegacion = null;

			Persona persona = sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)
					? sujetoTramite.getFisica() : sujetoTramite.getMoral();
			persona.setTipoPersona(new TipoPersona());

			TipoTramiteEnum tipoTramiteAP;
			if (sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)) {
				persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				tipoTramiteAP = TipoTramiteEnum.ALTA_SRT;
				System.err.println("Asignando tipo persona fisica");
			} else {
				persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				tipoTramiteAP = TipoTramiteEnum.ALTA_SRT_PM;
				System.err.println("Asignando tipo persona moral");
			}

			if (sujetoTramite.getClasificacion() == null) {
				sujetoTramite.setClasificacion(new Clasificacion());
			}
			if (sujetoTramite.getClasificacion().getFecPresentacion() == null) {
				sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
			}

			EstadoTramiteEnum estadoTramiteInicial = estadoSolicitud.equals(EstadoSolicitudEnum.EDICION_VENTANILLA)
					? EstadoTramiteEnum.ACTIVO : EstadoTramiteEnum.INICIADO;

			TramiteSujetoObligado tramiteAlta = new TramiteSujetoObligado();
			tramiteAlta.setSujetoObligado(sujetoTramite);
			tramiteAlta.setAcuseVentanilla(new AcuseVentanilla());
			
			if (usuario != null) {
				tramiteAlta.getAcuseVentanilla().setUsuarioVentanilla(usuario.getUsuario());

				UsuarioFuncionario usuarioFuncionario = usuario.getUsuarioFuncionario();
				if (usuarioFuncionario != null && usuarioFuncionario.getSubdelegacion() != null) {
					tramiteAlta.getAcuseVentanilla().setIdSubdelegacion(
							usuarioFuncionario.getSubdelegacion().getId());
				}
			}

			// Inicializacion de solicitud
			solicitud = solicitudBusiness.crearSolicitudInicialPorEnum(estadoSolicitud, TipoSolicitudEnum.ALTA_PATRONAL,
					origenSolicitud, usuario);			
			solicitud.setSubdelegacion(subdelegacion);

			// Inicializacion de tramite
			solicitud = solicitudBusiness.asociarTramiteSolicitudPorEnum(solicitud, tramiteAlta, tipoTramiteAP,
					estadoTramiteInicial);

			log.debug("Terminando de crear el objeto solicitud alta");
		} catch (SolicitudNoValidaException e) {
			solicitud = null;
		}

		return solicitud;
	}

	@Override
	public Solicitud crearSolicitudAutomaticaDeBaja(
			String numeroRegistroPatronal, Integer idCausa, Usuario usuario)throws GestionPatronalBusinessException {
		
		if(numeroRegistroPatronal.length()!=10 && numeroRegistroPatronal.length()!=11)
			throw new GestionPatronalBusinessException("registro.patronal.invalido");
		
		SujetoObligado sujetoTramite = sujetoObligadoService.consultarPorNumeroRegistroPatronal(numeroRegistroPatronal);
		
		if(sujetoTramite==null)
			throw new GestionPatronalBusinessException("error.registro.patronal.inexistente");
		
		sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronal);
		sujetoTramite = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		
		
		Solicitud solicitudActiva = inicializarSolicitud(TipoSolicitudEnum.BAJA_PATRONAL, EstadoSolicitudEnum.ATENDIDA, usuario, sujetoTramite);
		Tramite tramite = inicializarTramite(TipoTramiteEnum.BAJA_PATRONAL, EstadoTramiteEnum.CERRADO, sujetoTramite, false, true);
		List<Tramite> tramites = new ArrayList<Tramite>();
		tramites.add(tramite);
		solicitudActiva.setTramites(tramites);
		solicitudActiva = solicitudBusinessService.crearNuevaSolicitud(solicitudActiva);							
		
		
		return solicitudActiva;
	}
	
	@Override
	public Subdelegacion obtenerSubdelegacion(Long cveIdSubdelegacion){
		return afiliacionEntity.obtenerSubdelegacionPorId(cveIdSubdelegacion);
	}
	
	
	@Override
	public void validarSubdelegacion(Long idSubdelegacionOrigen, CentroTrabajo centroTrabajo) throws GestionPatronalBusinessException{
		
		List<Subdelegacion> subdelegacionesCompatibles = sujetoObligadoService.obtenerSubdelegacionesCompatibles(idSubdelegacionOrigen);
		
		Subdelegacion subdelegacionDestino = obtenerSubdelegacionPorDomicilio(centroTrabajo);
		super.log.debug("Subdelegacion origen: "+idSubdelegacionOrigen);
		super.log.debug("Subdelegacion destino: "+subdelegacionDestino.getClave() + "_"+ subdelegacionDestino.getDescripcion());
		if(idSubdelegacionOrigen.equals(subdelegacionDestino.getId()) ){
			super.log.debug("Misma subdelegacion origen y destino");
			return;
		}
		
		boolean subdelegacionValida=false;
		for(Subdelegacion subdelegacionCompatible:subdelegacionesCompatibles){
			super.log.debug("Subdelegacion destino: "+subdelegacionDestino.getClave() + "_"+ subdelegacionDestino.getDescripcion());
			super.log.debug("Subdelegacion compatible: "+subdelegacionCompatible.getClave()+"_"+subdelegacionCompatible.getDescripcion());
			if(subdelegacionCompatible.getId().equals(
					subdelegacionDestino.getId())){
				super.log.debug("Subdelegacion origen valida");
				subdelegacionValida=true;
				break;
			}
		}
		
		if(!subdelegacionValida)
			throw new GestionPatronalBusinessException("error.subdel.incompatible");
		
	}

	@Override
	public byte[] generarDocumentoModificacionDatosPatronales(Long idSolicitud, Integer idTipoDocumento) {
		
		super.log.error("/**** OBTENER MAP PARA REPORTE DE MODIFICCION PATRONAL ****/"+idSolicitud);
		Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
		return generarDocumentoModificacionDatosPatronales(solicitud, idTipoDocumento);
	}
	
	@Override
	public byte[] generarDocumentoModificacionDatosPatronales(Solicitud solicitud, Integer idTipoDocumento) {
		
		Usuario usuario = (Usuario)solicitud.getSolicitante();
		TramiteSujetoObligado tso = inicializarInformacionSujetoTramite(solicitud.getTramites().get(0));
		Map<String,Object> mapData = new HashMap<String, Object>();
		solicitud.setSujetoObligado(tso.getSujetoObligado());
		
		mapData.put("varTitulo", obtenerLeyendaTipoDocumentoAGenerar(idTipoDocumento));
		
		super.log.error("Folio: "+solicitud.getNoFolioSolicitud());
		super.log.error("Usuario reporte: "+usuario);
		
		
		mapData.put("solicitudData",solicitud);
		byte[] reporte = manejadorReportesBusiness.ejecutaAcuseModificacionDatosPatronalesPortal(mapData);
		
		/**TODO revisar el envio de notifiaciones
		if(reporte != null){
			try {
				super.log.error("Se enviara el correo electronico");
				notificarPorCorreoElectronico(solicitud.getSujetoObligado(), solicitud.getSolicitudId(), TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue());
			} catch(GestionPatronalBusinessException gpe){
				gpe.printStackTrace();
			}		
		}else{
			log.debug("EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
		} **/			
		return reporte;
	}

	
	private String obtenerLeyendaTipoDocumentoAGenerar(Integer idTipoDocumento){
		TipoDocumentoTramiteEnum tipoDocumento = 
				TipoDocumentoTramiteEnum.obternerEnumById(idTipoDocumento);
		
		return tipoDocumento.name();
	}

	@Override
	public byte[] obtenerDocumentoModificacionDatosPatronales (
			Solicitud solicitud, Integer idTipoDocumento) throws GestionPatronalBusinessException {
		
		byte[] documento=null;
		Tramite tramite = solicitud.getTramites().get(0);
		String secuenciaNotaria = null;
		
		if(solicitud.getCadenaOriginal() != null) {
			RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(solicitud.getCadenaOriginal(),solicitud.getSecuenciaDeNotaria(), null);
			secuenciaNotaria = solicitud.getSecuenciaDeNotaria();
			if(selloDigital != null) {
				solicitud.setSelloDigital(selloDigital.getSello());
				solicitud.setSecuenciaDeNotaria(selloDigital.getId());
				solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
			}
		}
		
		TipoDocumentoTramiteEnum tipo=TipoDocumentoTramiteEnum.obternerEnumById(idTipoDocumento);
		switch(tipo){
			case ACUSE:
				if(solicitud.getDocumentoAcuse()!=null)
					documento=solicitud.getDocumentoAcuse();
				else
					documento=generarDocumentoModificacionDatosPatronales(solicitud, idTipoDocumento);
				break;
			case COMPROBANTE:
				
				documento= (byte[])tramiteServiceEntityLocal.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId());
				
				if(documento == null) {
					documento=generarDocumentoModificacionDatosPatronales(solicitud, idTipoDocumento);
					log.warn("secuencia de notaria del tramite: " + secuenciaNotaria);
					log.warn("secuencia de notaria del sello: " + solicitud.getSecuenciaDeNotaria());
					if(solicitud.getSecuenciaDeNotaria() != null) {
						firmaDigitalBusinessRemote.guardarArchivoFirmado(secuenciaNotaria, "avisoDeModificacion.pdf", documento);
					}
				}
				break;
			default:
				throw new GestionPatronalBusinessException("El tipo de documento solicitado no es valido");
		}
		
		return documento;
	}

	@Override
	public byte[] obtenerDocumentoModificacionDatosPatronales(Long idSolicitud,
			Integer idTipoDocumento) throws GestionPatronalBusinessException {
		Solicitud solicitud = solicitudBusinessService.consultarSolicitudPorId(idSolicitud);
		return obtenerDocumentoModificacionDatosPatronales(solicitud, idTipoDocumento);
	}

	@Override
	public void asociarRepresentantesLegalesANuevoRegistroPatronal (
			SujetoObligado nuevoRegistroPatronal)
			throws GestionPatronalBusinessException {
		log.error("Se asociar�n representantes legales");
		
		List<SujetoObligado> registrosPatronales = afiliacionEntity.obtenerInformacionBasicaDeRegistrosPatronalesDelPatron(nuevoRegistroPatronal);
		List<SujetoObligado> registrosPatronalesPrevios = new ArrayList<SujetoObligado>();
		boolean isFisica = nuevoRegistroPatronal.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA);
		
		if(registrosPatronales!=null){
			log.error("Se encontraron registros patronales previos para la persona: "+registrosPatronales.size());
			for(SujetoObligado rp : registrosPatronales){
				log.error("RegistroPatronal encontrado: "+rp.getCveIdSujetoObligado());
				log.error("RegistroPatronal nuevo: "+nuevoRegistroPatronal.getCveIdSujetoObligado());
				if(!rp.getCveIdSujetoObligado().equals(nuevoRegistroPatronal.getCveIdSujetoObligado())){
					log.error("Agregando RP Previo");
					registrosPatronalesPrevios.add(rp);
				}
			}
		}
		
		Integer numeroRPPrevios = registrosPatronalesPrevios.size();
		log.error("Existen registros patronales previos: "+numeroRPPrevios);
		
		if(numeroRPPrevios>0){
			Long idRegistroPatronalPivote = registrosPatronalesPrevios.get(0).getCveIdSujetoObligado();
			log.error("RP Pivote: "+idRegistroPatronalPivote);
			asociarRepresentantesLegalesActualesAlRP(nuevoRegistroPatronal, idRegistroPatronalPivote, registrosPatronalesPrevios, isFisica);
		}else{
			log.error("No hay RP previo:::: ");
			agregarRepresentanteLegalAlRegistroPatronal(nuevoRegistroPatronal);
		}
		
		
	}
	
	
	/**
	 * Asocia todos los representantes legales que actualmente estan asociados a los registros
	 * patronales existentes del patron al nuevo registro patronal.
	 * 
	 * En el caso extraordinario en el que existan registros patronales previos pero no cuenten
	 * con ning�n rl asociado, se obtiene la lista de representantes del sujetoTramite y se agregan
	 * todos los representantes registrados en el tr�mite a todos los registros patronales existentes.
	 * 
	 * @param nuevoRegistroPatronal
	 * @param idRegistroPatronalPivote
	 * @param listaRegistrosPatronalesPrevios
	 */
	private void asociarRepresentantesLegalesActualesAlRP(SujetoObligado nuevoRegistroPatronal, Long idRegistroPatronalPivote, 
			List<SujetoObligado> listaRegistrosPatronalesPrevios, boolean isFisica) throws GestionPatronalBusinessException{
		log.error("Se buscara asociar representantes previamente registrados");
		List<RepresentanteLegal> representantesLegales = representanteService.obtenerRepresentanteLegalPorSujetoObligado(idRegistroPatronalPivote);
		
		List<RepresentanteLegal> nuevosRl = new ArrayList<RepresentanteLegal>();
		if(representantesLegales!=null && representantesLegales.size()>0){
			log.error("Si existen Representantes Legales actuales: "+representantesLegales.size());
			for(RepresentanteLegal rl : representantesLegales){
				rl.setCveIdRepresentanteLegal(null);
				rl.setCveIdPatronSujetoObligado(nuevoRegistroPatronal.getCveIdSujetoObligado());
				nuevosRl.add(rl);
			}
			representanteService.agregarRepresentantesLegalesARegistroPatronal(nuevosRl);
		}else{
			//En este caso se tienen registros patronales anteriores sin embargo no se tiene asociado ningun representante
			//legal en estos registos patronales, en caso de que para el nuevo regisro se proporcione un rl, este
			//se agregar� a todos los registros previos y al actual.
			log.error("NO existen Representantes Legales actuales: ");
			List<RepresentanteLegal> nuevosRepresentantes = nuevoRegistroPatronal.getRepresentantesLegales();
			if(nuevosRepresentantes!=null){
				log.error("Se agregaran nuevos representantes Legales a rp previos y actual: "+nuevosRepresentantes.size());
				List<SujetoObligado> regPatronalesTotales = new ArrayList<SujetoObligado>(); 
				regPatronalesTotales.addAll(listaRegistrosPatronalesPrevios);
				regPatronalesTotales.add(nuevoRegistroPatronal); //se agrega el nuevo rl
				for(RepresentanteLegal representanteLegal : nuevosRepresentantes){
					for(SujetoObligado regPatronalPrevio : listaRegistrosPatronalesPrevios){
						log.error("Se agregar� la persona ["+representanteLegal.getCveIdPersona()+"] como RL del registro patronal ["+regPatronalPrevio.getCveIdSujetoObligado()+"]");
						representanteLegal.setCveIdPatronSujetoObligado(regPatronalPrevio.getCveIdSujetoObligado());
						log.error("Se agregan actos de admon dominio");
						representanteLegal.setIndActAdmonDominio(BigDecimal.ONE);
						representanteService.agregarRepresentanteLegalARegistroPatronal(representanteLegal);
					}
				}
				
				
			}else{
				if(!isFisica)
					throw new GestionPatronalBusinessException("Se debe agregar un representante legal al tr�mite");
			}
		}
			
	}
	
	/**
	 * Cuando no existen registros patronales previos asociados a la persona el tr�mite debe contener
	 * al menos un representante legal el cual inmediatamente se da de alta como rl del nuevo
	 * registro patronal
	 * @param nuevoRegistroPatronal
	 * @throws GestionPatronalBusinessException
	 */
	private void agregarRepresentanteLegalAlRegistroPatronal(SujetoObligado nuevoRegistroPatronal)throws GestionPatronalBusinessException{
		List<RepresentanteLegal> rls = nuevoRegistroPatronal.getRepresentantesLegales();
		log.error("Registro patronal unico, se agrega rl del tramite");
		
		
		if(rls==null || (rls!=null && rls.size()<=0))
			if(nuevoRegistroPatronal.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
				throw new GestionPatronalBusinessException("Se requiere de al menos un representante legal dentro de la informaci�n del tr�mite");
			}else{
				rls = new ArrayList<RepresentanteLegal>();
				log.error("No se agregar� ning�n representante legal al nuevo registro patronal");
			}
		for(RepresentanteLegal rl : rls){
			log.error("Persona a agregar como RL: "+rl.getCveIdPersona());
			rl.setCveIdPatronSujetoObligado(nuevoRegistroPatronal.getCveIdSujetoObligado());
			if(rl.getIndActAdmonDominio()==null) 
				rl.setIndActAdmonDominio(BigDecimal.ONE);
			representanteService.agregarRepresentanteLegalARegistroPatronal(rl);
		}
	}

	@Override
	public void concluirSolicitudAfiliacion(Long idSolcitud)
			throws GestionPatronalBusinessException {
		this.concluirSolicitud(idSolcitud, null, null);
	}

	@Override
	public void validarNoAdedudoPorBaja251(String rfc)
			throws GestionPatronalBusinessException {
		List<SujetoObligado> registrosPatronalesConBaja = afiliacionEntity.obtenerRegistrosPatronalesConBaja(rfc);
		validarNoAdedudoPorBaja251(rfc, registrosPatronalesConBaja, true);
	}
	
	@Override
	public void validarNoAdedudoPorBaja251(SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException {
		String rfc=null;
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			rfc=sujetoObligado.getFisica().getRfc();
		}else{
			rfc=sujetoObligado.getMoral().getRfc();
		}
		List<SujetoObligado> registrosPatronalesConBaja = afiliacionEntity.obtenerRegistrosPatronalesConBaja(sujetoObligado);
		validarNoAdedudoPorBaja251(rfc, registrosPatronalesConBaja, true);
	}
	
	@Override
	public List<SujetoObligado> validarNoAdedudoPorBaja251(String rfc,
			List<SujetoObligado> registrosPatronalesConBaja, boolean lanzarError)
			throws GestionPatronalBusinessException {
		
		List<SujetoObligado> patBaja251 = new ArrayList<SujetoObligado>();
		
		for(SujetoObligado registroPatronal : registrosPatronalesConBaja){
			StringBuffer nrpCompleto=new StringBuffer();
			nrpCompleto.append(registroPatronal.getNumeroRegistroPatronal());
			if(registroPatronal.getModalidad()!=null){
				nrpCompleto.append(registroPatronal.getModalidad().getNumModalidad());
				if(registroPatronal.getDigVerificador()!=null)
					nrpCompleto.append(registroPatronal.getDigVerificador());
			}
			MovimientoAfiliatorio ultimoMovimiento = 
					afiliacionEntity.obtenerUltimoMovimiento(registroPatronal.getCveIdSujetoObligado());
			if(ultimoMovimiento!=null){
				if(ultimoMovimiento.getTipoMovimiento()!=null 
					&& ultimoMovimiento.getTipoMovimiento().intValue()==TipoMovtoPatSujObligEnum.BAJA_PATRONAL.getId()){
					if(ultimoMovimiento.getCveCausa()!=null 
						&& ultimoMovimiento.getCveCausa().intValue() == CausaBajaPatronEnum.ART_251_LEY_IMSS.getClave()){
						if (lanzarError) {
							throw new GestionPatronalBusinessException("El patr�n con RFC ["+
								rfc+"] se encuentra en BAJA conforme al art�culo 251 de la Ley Organica del IMSS con el Registro Patronal ["+
								nrpCompleto.toString()+"] ");
						} else {
							patBaja251.add(registroPatronal);
						}
					}		
				}
			}	
		}	
		
		return patBaja251;
	}
	
	@Override
	public List<SujetoObligado> obtenerPatronesConAdeudoPorBaja251(SujetoObligado sujetoObligado) {
		String rfc=null;
		List<SujetoObligado> patBaja251 = null;
		
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			rfc=sujetoObligado.getFisica().getRfc();
		}else{
			rfc=sujetoObligado.getMoral().getRfc();
		}
		List<SujetoObligado> registrosPatronalesConBaja = afiliacionEntity.obtenerRegistrosPatronalesConBaja(sujetoObligado);
		
		try {
			patBaja251 = validarNoAdedudoPorBaja251(rfc, registrosPatronalesConBaja, false);
		} catch (GestionPatronalBusinessException e) {
			/*
			 * Esta excepci�n no deber�a de pasar ya que se est� mandando la
			 * bandera para que se devuelva la lista de patrones en lugar de
			 * lanzar excepci�n
			 */
			this.log.fatal(e);
			patBaja251 = new ArrayList<SujetoObligado>();
		}
		
		return patBaja251;
	}

	@Override
	public Solicitud generarSolicitudDeAltaPatronalParaRegistroDeEventualesCaneros(
			String numeroRegistroPatronalOrigen, Long tipoPersona, Usuario usuario)
			throws GestionPatronalBusinessException {
		
		SujetoObligado sujetoTramite = new SujetoObligado();
		
		sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronalOrigen);
		
		if(tipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA))
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		else
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		
		sujetoTramite = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		sujetoTramite = obtenerClonDeSujetoObligadoParaAlta(sujetoTramite);
		
		Solicitud solicitud = crearSolicitudDeAltaPatronal(sujetoTramite, usuario, OrigenSolicitudEnum.VENTANILLA);
		return solicitud;
	}
	
	/**
	 * Elimina todos los identificadores de los objetos de clasificacion y actividad economica para que el proceso de culminaci�n
	 * de alta patronal pueda realizar la inserci�n de la informaci�n de forma correcta.
	 * @param so
	 * @return SujetoObligado
	 */
	@Override
	public SujetoObligado obtenerClonDeSujetoObligadoParaAlta(SujetoObligado sujetoTramite)throws GestionPatronalBusinessException{
		sujetoTramite.setCveIdSujetoObligado(null);
		sujetoTramite.getClasificacion().setId(null);
//		sujetoTramite = agregarIdRegistroPatronalAClasificacion(sujetoTramite);
		sujetoTramite = eliminarIdProductos(sujetoTramite);
		sujetoTramite = eliminarIdMateriaPrima(sujetoTramite);
		sujetoTramite = eliminarIdAMaquinaria(sujetoTramite);
		sujetoTramite = eliminarIdAEquipoTransporte(sujetoTramite);
		sujetoTramite = eliminarIdAPersonal(sujetoTramite);
		sujetoTramite = eliminarIdABienes(sujetoTramite);
		sujetoTramite = eliminarIdAProceso(sujetoTramite);
		sujetoTramite.setRepresentantesLegales(null);
		sujetoTramite.getModalidad().setIdModalidad(ModalidadEnum.CATORCE.getId());
		sujetoTramite.getModalidad().setNumModalidad(ModalidadEnum.CATORCE.getNumModalidad());
		sujetoTramite = eliminarIdCentroTrabajo(sujetoTramite);
//		sujetoTramite = agregarIdRegistroPatronalAPersonasAutorizadas(sujetoTramite);
		
		return sujetoTramite;
	}
	
	
private SujetoObligado eliminarIdProductos(SujetoObligado sujetoTramite){
		
		super.log.error("::Eiminando id a productos: "+sujetoTramite.getCveIdSujetoObligado());
		List<Producto> listaFinalProducto = new ArrayList<Producto>();
		
		for(Producto producto : sujetoTramite.getProductos()){
			producto.setSujetoObligado(null);
			producto.setId(null);
			listaFinalProducto.add(producto);
		}
		sujetoTramite.setProductos(listaFinalProducto);
		return sujetoTramite;
	}
	
	private SujetoObligado eliminarIdMateriaPrima(SujetoObligado sujetoTramite){
		
		super.log.error("::Eliminando i a materias: "+sujetoTramite.getCveIdSujetoObligado());
		List<MateriaPrima> listaFinal = new ArrayList<MateriaPrima>();
		
		for(MateriaPrima model : sujetoTramite.getMateriaPrimaMateriales()){
			model.setSujetoObligado(null);
			model.setId(null);
			listaFinal.add(model);
		}
		sujetoTramite.setMateriaPrimaMateriales(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado eliminarIdAMaquinaria(SujetoObligado sujetoTramite){
		
		super.log.error("::Eliminando id a maquinaria: "+sujetoTramite.getCveIdSujetoObligado());
		List<MaquinariaEquipo> listaFinal = new ArrayList<MaquinariaEquipo>();
		
		for(MaquinariaEquipo model : sujetoTramite.getEquipos()){
			model.setSujetoObligado(null);
			model.setId(null);
			listaFinal.add(model);
		}
		sujetoTramite.setEquipos(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado eliminarIdAEquipoTransporte(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a equipo transporte: "+sujetoTramite.getCveIdSujetoObligado());
		List<EquipoTransporte> listaFinal = new ArrayList<EquipoTransporte>();
		
		for(EquipoTransporte model : sujetoTramite.getEquiposTransporte()){
			model.setSujetoObligado(null);
			model.setId(null);
			listaFinal.add(model);
		}
		sujetoTramite.setEquiposTransporte(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado eliminarIdAPersonal(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a personal: "+sujetoTramite.getCveIdSujetoObligado());
		List<Personal> listaFinal = new ArrayList<Personal>();
		
		for(Personal model : sujetoTramite.getPersonal()){
			model.setSujetoObligado(null);
			model.setClave(null);
			listaFinal.add(model);
		}
		sujetoTramite.setPersonal(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado eliminarIdABienes(SujetoObligado sujetoTramite){
		
		super.log.error("::Agregando idSujetoObligado a bienes: "+sujetoTramite.getCveIdSujetoObligado());
		List<Bien> listaFinal = new ArrayList<Bien>();
		
		for(Bien model : sujetoTramite.getBienes()){
			model.setSujetoObligado(null);
			model.setId(null);
			listaFinal.add(model);
		}
		sujetoTramite.setBienes(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado eliminarIdAProceso(SujetoObligado sujetoTramite){
		if(sujetoTramite.getProceso()==null)
			return sujetoTramite;
		
		sujetoTramite.getProceso().setSujetoObligado(null);
		sujetoTramite.getProceso().setClave(null);
		return sujetoTramite;
	}
	
	private SujetoObligado eliminarIdCentroTrabajo(SujetoObligado sujetoTramite) throws GestionPatronalBusinessException{
		if(sujetoTramite.getCntroTrabajo()==null)
			throw new GestionPatronalBusinessException("error.rp.sin.ct");
			
		sujetoTramite.getCntroTrabajo().setClave(null);
		sujetoTramite.getCntroTrabajo().setCveIdPatronSujetoObligado(null);
		sujetoTramite.getCntroTrabajo().setCveIdPersonafDom(null);
		List<MedioContacto> mediosLimpios = new ArrayList<MedioContacto>();
		for(MedioContacto mc :sujetoTramite.getCntroTrabajo().getMediosContacto()){
			mc.setClave(null);
			mediosLimpios.add(mc);
		}
		
		sujetoTramite.getCntroTrabajo().setMediosContacto(mediosLimpios);
		return sujetoTramite;
	}

	@Override
	public List<SujetoObligado> obtenerNRPCanerosCandidatosParaAmpliacionEventuales(
			Long cveIdPersonaMoral) {
		return afiliacionEntity.obtenerRegistrosPatronalesCanerosSinEventualesporPersona(cveIdPersonaMoral);
	}
	
	@Override
	public Persona obtenerDatosBasicosPersona(Persona persona) {
		log.debug("::: Buscando la persona Moral en IMSS-BDTU: obtenerDatosBasicosPersona");
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			persona = afiliacionEntity.obtenerDatosBasicosPersonaFisica(persona.getIdPersona());
		}else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			persona = afiliacionEntity.obtenerDatosBasicosPersonaMoral(persona.getIdPersona());
		}		
		return persona;
	}

}