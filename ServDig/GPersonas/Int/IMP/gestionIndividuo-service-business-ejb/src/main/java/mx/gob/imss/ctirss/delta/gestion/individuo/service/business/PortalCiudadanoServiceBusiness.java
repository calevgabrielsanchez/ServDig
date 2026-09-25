package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.Movimiento06CorreccionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.persistence.DitCiudadanoCurpCorreo;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PortalCiudadanoServiceEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityLocal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;


@Stateless(name = "portalCiudadanoServiceBusiness", mappedName = "portalCiudadanoServiceBusiness")
public class PortalCiudadanoServiceBusiness extends AbstractServiceBusiness
		implements PortalCiudadanoServiceBusinessRemote {
	
	
	@EJB
	private PortalCiudadanoServiceEntityLocal portalEntity;

	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;

	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

	@EJB
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;

	@EJB
	private AfectarDatosPersonaUtilityLocal afectarDatosPersonaUtility;

	@EJB
    private SolicitudBusinessRemote solicitudBusiness;

	@EJB
    private PersonaBusinessLocal personaBusiness;

	@EJB
	private Movimiento06CorreccionBusinessRemote movimiento06Correccion;

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");

	@Override
	public CiudadanoCurpCorreo validarInicioCurpCorreo(String curp, String correo, Boolean aceptaTerminos) throws PortalCiudadanoException{
		List<DitCiudadanoCurpCorreo> listaCiudadanos = portalEntity.obtenerCiudadanoPorCorreo(correo);
		if(listaCiudadanos == null || listaCiudadanos.size() == 0){
			DitCiudadanoCurpCorreo ditCiudadano = new DitCiudadanoCurpCorreo();
			ditCiudadano.setFecRegistroAlta(new Date());
			ditCiudadano.setRefCorreoElectronico(correo);
			ditCiudadano.setRefCurp(curp);
			ditCiudadano.setIndAceptoTerminosCondicione(aceptaTerminos== true ? new BigDecimal(1) : new BigDecimal(0));
			ditCiudadano = portalEntity.guardarCiudadano(ditCiudadano);
			return transformarEntityAModel(ditCiudadano);
		}else{
			DitCiudadanoCurpCorreo ditCiudadano = listaCiudadanos.get(0);
			if(ditCiudadano.getRefCurp().equals(curp)){
				return transformarEntityAModel(ditCiudadano);
			}else{
				throw new PortalCiudadanoException("El correo que ingresó ya se encuentra asociado a otro CURP.");
			}
		}
	}

	private CiudadanoCurpCorreo transformarEntityAModel(DitCiudadanoCurpCorreo ditCiudadano){
		CiudadanoCurpCorreo ciudadano = new CiudadanoCurpCorreo();
		ciudadano.setCveIdCiudadanoCurpCorreo(ditCiudadano.getCveIdCiudadanoCurpCorreo());
		ciudadano.setFecRegistroActualizado(ditCiudadano.getFecRegistroActualizado());
		ciudadano.setFecRegistroAlta(ditCiudadano.getFecRegistroAlta());
		ciudadano.setFecRegistroBaja(ditCiudadano.getFecRegistroBaja());
		ciudadano.setIndAceptoTerminosCondicione(ditCiudadano.getIndAceptoTerminosCondicione().equals(new BigDecimal(1)) ? true : false);
		ciudadano.setRefCorreoElectronico(ditCiudadano.getRefCorreoElectronico());
		ciudadano.setRefCurp(ditCiudadano.getRefCurp());
		return ciudadano;
	}

	@Override
	public CiudadanoCurpCorreo consultarCurpCorreo(String curp, String correo) throws PortalCiudadanoException {
		List<DitCiudadanoCurpCorreo> listaCiudadanos = portalEntity.obtenerCiudadanoPorCorreo(correo);
		CiudadanoCurpCorreo ciudadanoCurp = null; 
		for (DitCiudadanoCurpCorreo ditCiudadanoCurpCorreo : listaCiudadanos) {
			if(curp.equals(ditCiudadanoCurpCorreo.getRefCurp())){
				ciudadanoCurp = transformarEntityAModel(ditCiudadanoCurpCorreo);
				break;
			}
		}
		return ciudadanoCurp;
	}
	
	

	@Override
	public void actualizarCurpACorreo(String correo, String curp)
			throws PortalCiudadanoException {
		
		log.debug("Se actualizara el curp " + curp +" al correo " + correo);
		try {
			portalEntity.actualizarCurpACorreo(correo, curp);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PortalCiudadanoException("No fue posible actualizar la curp "+curp+" al correo " + correo);
		}
		
	}

	@Override
	public CiudadanoCurpCorreo actualizarTerminos(Long id, boolean terminos)throws PortalCiudadanoException {
		DitCiudadanoCurpCorreo ditCiudadano = portalEntity.getCiudadanoById(id);
		ditCiudadano.setIndAceptoTerminosCondicione(terminos == true ? new BigDecimal(1) : new BigDecimal(0));
		ditCiudadano.setFecRegistroActualizado(dateFormat.format(new Date()));
		return transformarEntityAModel(portalEntity.guardarCiudadano(ditCiudadano));
	}

	@Override
	public void actualizarDatosCiudadano(Fisica fisicaIMSS,
			boolean requiereActualizacionCurp,
			boolean requiereActualizacionFechaNac)
			throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException, PersonaSinCalificacionesException,
			PersonaFisicaNoEncontradaException, PersonaNoEncontradaException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException,
			ErrorComparacionDatosRENAPOException,
			DatosInsuficientesICAException, DiferenciasRENAPOContraSAT,
			AfectacionDatosPersonaException, SolicitudNoValidaException {
		Date fechaRegistro = new Date();
		List<Tramite> lstTramiteReg = new ArrayList<Tramite>();

		Fisica fisicaActualizar = new Fisica();
		if (requiereActualizacionCurp) {
			fisicaActualizar.setIdPersona(fisicaIMSS.getIdPersona());
			fisicaActualizar.setCurp(fisicaIMSS.getCurp());
		}

		if (requiereActualizacionFechaNac) {
			fisicaActualizar.setIdPersona(fisicaIMSS.getIdPersona());
			fisicaActualizar.setFechaNacimiento(fisicaIMSS.getFechaNacimiento());
		}

		//se califica a la persona existente
		calificacionesPersonaBusinessService.calificarRENAPO(fisicaIMSS);

		// Se realiza proceso de Identificacion de Cambios Automaticos para realizar las modificaciones
		if (requiereActualizacionCurp || requiereActualizacionFechaNac) {
			log.debug("Aplicando actualizaciones");
			personaBusiness.actualizarPersona(fisicaActualizar);

			// Se califica con RENAPO a la persona
			if (fisicaActualizar.getPersonaCalificaciones() == null
					|| fisicaActualizar.getPersonaCalificaciones().isEmpty()) {
				try {
					log.info("La persona [idPersona: " + fisicaActualizar.getIdPersona()
							+ "] no cuenta con calificaciones, por lo tanto, se va a calificar con RENAPO ");

					calificacionesPersonaBusinessService.calificarRENAPO(fisicaActualizar);
				} catch (PersonaSinCalificacionesException e) {
					log.error("Error al calificar con RENAPO a la persona [idPersona: "
									+ fisicaActualizar.getIdPersona() + "]", e);
				}
			}

			// Se genera movimiento de SINDO si es un asegurado y se requiere
			// actualizar la CURP
			if (requiereActualizacionCurp && StringUtils.isNotBlank(fisicaIMSS.getNss())) {
				log.info("Se manda a SINDO la correccion realizada al asegurado");
				
				MovCorreccionesDatosAseguradoType movCorrecion = afectarDatosPersonaUtility
						.generarMovimientoActualizacionAseguradoSINDO(fisicaIMSS, null, null);
				log.debug("Movimiento 06 generado -> " + movCorrecion);
				movimiento06Correccion.encolarMovimiento06CorrecconAsegurado(movCorrecion);
			}

			log.debug("modificaciones de persona aplicadas");
		}

		//se setea el usuario a la solicitud
		Usuario usuario = new Usuario();
		usuario.setFisica(fisicaIMSS);
		usuario.setUsuario(fisicaIMSS.getCurp());

		//se actualiza el estado del tramite y la solicitud para que se den por atendidas
		Solicitud solicitud = solicitudBusiness.crearSolicitudInicialPorEnum(
				EstadoSolicitudEnum.ATENDIDA, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
				OrigenSolicitudEnum.PORTAL_CIUDADANO, usuario);

		// se realiza la actualizacion de tramite solicitud y generacion de tramite persona fisica
		TramiteFisica tramiteFisica = new TramiteFisica();
		tramiteFisica.setFisica(fisicaIMSS);
		tramiteFisica.setResultado(true);

		RazonResultado objRazonReslt = new RazonResultado();
		objRazonReslt.setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramiteFisica.setRazonResultado(objRazonReslt);

		solicitud = solicitudBusiness.asociarTramiteSolicitudPorEnum(solicitud, tramiteFisica,
				TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES, EstadoTramiteEnum.CERRADO);

		// Se guarda la solicitud
		Solicitud solicitudCreada = solicitudBusiness.crear(solicitud);
		log.debug("Solicitud creada ...." + solicitudCreada);

		// Se agregan los datos de la firma digital a la solicitud
		solicitud.setSolicitudId(solicitudCreada.getSolicitudId());
		solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitudCreada);
	}

	@Override
	public Fisica crearCiudadanoNuevo(Fisica fisica) throws DomicilioNoValidoException {
		Fisica fisicaRegistrada = personaBusiness.altaPersonaFisica(fisica);

		fisica.setIdPersona(fisicaRegistrada.getIdPersona());
		if (fisica.getPersonaCalificaciones() == null || fisica.getPersonaCalificaciones().isEmpty()) {
			// Se califica con RENAPO a la persona
			try {
				log.info("La persona [idPersona: " + fisica.getIdPersona()
						+ "] no cuenta con calificaciones, por lo tanto, se va a calificar con RENAPO ");

				calificacionesPersonaBusinessService.calificarRENAPO(fisica);
			} catch (PersonaSinCalificacionesException e) {
				log.error("Error al calificar con RENAPO a la persona [idPersona: "
								+ fisica.getIdPersona() + "]", e);
			}
		}

		return fisicaRegistrada;
	}
	

	@Override
	public CiudadanoCurpCorreo validaRegistroCurpCorreoCiudadano(String curp, String correo) throws PortalCiudadanoException {
		List<DitCiudadanoCurpCorreo> listaCiudadanos = portalEntity.obtenerCiudadanoPorCorreo(correo);
		CiudadanoCurpCorreo ciudadanoCurp = null; 
		boolean existeRegistro = false;
		for (DitCiudadanoCurpCorreo ditCiudadanoCurpCorreo : listaCiudadanos) {
			existeRegistro = true;
			if(curp.equals(ditCiudadanoCurpCorreo.getRefCurp())){
				ciudadanoCurp = transformarEntityAModel(ditCiudadanoCurpCorreo);
				return ciudadanoCurp;
			}
		}
		if (existeRegistro){
			throw new  PortalCiudadanoException("El correo que ingresó ya se encuentra asociado a otro CURP.");
		}
		
		return ciudadanoCurp;
	}

	@Override
	public boolean validaRegistroCurpCorreoCiudadano(final String curp, final String correo, Date fechaLimite) {
		List<DitCiudadanoCurpCorreo> found = portalEntity.obtenerCiudadanoPorCorreoYFechaLimite(correo, fechaLimite);
			
		List<String> curps = new ArrayList<String>();

		CollectionUtils.collect(found, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((DitCiudadanoCurpCorreo) input).getRefCurp();
			}
		}, curps);
		
		
		return found.isEmpty() || CollectionUtils.exists(curps, new Predicate() {
			@Override
			public boolean evaluate(Object c) {
				return curp.equals(c);
			}
		});
	}
 	
}
