
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ClasificacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.MaquinariaEquipoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.TransporteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.ComponenteBusquedaPatron;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.BienDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.EquipoTransporteDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MaquinariaEquipoDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MateriaMaterialDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.PersonalDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.ProductoServicioDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.ItemClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.web.validator.BienValidator;
import mx.gob.imss.ctirss.delta.web.validator.EquipoTransporteValidator;
import mx.gob.imss.ctirss.delta.web.validator.MaquinariaEquipoValidator;
import mx.gob.imss.ctirss.delta.web.validator.MateriaPrimaValidator;
import mx.gob.imss.ctirss.delta.web.validator.PersonalValidator;
import mx.gob.imss.ctirss.delta.web.validator.ProductoValidator;

@Controller
@RequestMapping(value = "/movPat/clasificacion")
public class MovPatClasificacionController extends AbstractController {

	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	@Autowired
	private MaquinariaEquipoServiceBusinessRemote maquinariaEquipoService;
	@Autowired
	private TransporteServiceBusinessRemote transporteService;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	@Autowired
	private ActividadEcServiceRemote clasificacionServiceBusiness;
	@Autowired
	private ManejadorReportesRemote manejadorReportes;
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionService;
	@Autowired
	private RuleServiceBusinessRemote ruleServiceBusiness;
	@Autowired
	private ClasificacionServiceRemote clasificacionService;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@Autowired
	FirmaDigitalBusinessRemote firmaDigitalBusiness;
	
	/**
	 * Metodo para obtener los datos del sujeto obligado para la mostrarlos en
	 * la cabecera de la solicitud.
	 * 
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String inicio(@ModelAttribute SujetoObligado sujetoObligado,
			@RequestParam("idTramite") String idTramite, Model model,
			@RequestParam("idSolicitud") String idSolicitud, HttpSession session) {
		log.debug("ENTRANDO AL METODO INICIO DE CLASIFICACIONCONTROLER");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		session.setAttribute("usuario", usuario);
		model.addAttribute("esOperador",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		if (sujetoObligado.getNumeroRegistroPatronal() == null){
			if (idSolicitud != null && !StringUtils.isBlank(idSolicitud)
					&& idSolicitud != "") {
				
				log.debug("OBTAINING SUJETOOBLIGADO DATA for " + this.getClass().getName() + "...");
				
				Solicitud sol = solicitudServiceBusiness
						.consultarSolicitudPorId(Long.valueOf(idSolicitud));
				TramiteSujetoObligado tso = (TramiteSujetoObligado) sol
						.getTramites().get(0);
				sujetoObligado = tso.getSujetoObligado();
				sujetoObligado.setNumeroRegistroPatronal(
						  sujetoObligado.getNumeroRegistroPatronal()
						+ sujetoObligado.getModalidad().getNumModalidad()
						+ sujetoObligado.getDigVerificador());
				log.debug("OBTAINED SUJETOOBLIGADO DATA for " + this.getClass().getName() + ".");
			
			}
		}
		
		sujetoObligado = sujetoObligadoService
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		TipoTramiteEnum tipoTramite = TipoTramiteEnum
				.obtenerEnumByName(idTramite);
		SujetoObligado sujetoTramite = new SujetoObligado();

		session.setAttribute("sujetoObligado", null);
		session.setAttribute("sujetoTramite", null);
		session.setAttribute("tipoTramite", null);
		session.setAttribute("idSolicitud", null);
		
		if (TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().toString().equals(idTramite.toString()) && 
				usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
				|| (TipoTramiteEnum.DISPOSICION_DE_LEY.toString().equals(idTramite.toString()) && 
						usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue()) ))
			session.setAttribute("showFinalizarFD","1");
		else
			session.setAttribute("showFinalizarFD","0");
		
		log.debug("El tramite quedo como: [" + session.getAttribute("showFinalizarFD") + "]");
		log.debug("CLASIFICACION ID_SOLICITUD: [" + idSolicitud + "]");

		if (idSolicitud != null && !StringUtils.isBlank(idSolicitud)
				&& idSolicitud != "") {
			Solicitud sol = solicitudServiceBusiness
					.consultarSolicitudPorId(Long.valueOf(idSolicitud));
			TramiteSujetoObligado tso = (TramiteSujetoObligado) sol
					.getTramites().get(0);
			sujetoTramite = tso.getSujetoObligado();
			if(sol.getEstadoSolicitud().getIdEstadoSolicitud().intValue() == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()){
				sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
			}
			tipoTramite = TipoTramiteEnum.obternerEnumById(tso.getTipoTramite()
					.getIdTipoTramite());
			log.debug("SUJETO TRAMITE: [" + sujetoTramite + "]");
			
			sujetoTramite = inicializaSujetoObligadoPresentacion(sujetoTramite);
			inicializarMediosContactoCentroTrabajo(sujetoTramite, model);
			session.setAttribute("idSolicitud", sol.getSolicitudId());
			model.addAttribute("idSolicitud", sol.getSolicitudId());
			model.addAttribute("indRPCInvalido", sol.isIndRpcInvalido());
			model.addAttribute("indReintento", sol.isIndReintentoRpc());
		}else{
			log.error("INICIALIZA EL SUJETO TRAMITE CON EL REGISTRO PATRONAL: "+sujetoObligado.getNumeroRegistroPatronal());
			System.err.println("INICIALIZA EL SUJETO TRAMITE CON EL REGISTRO PATRONAL: "+sujetoObligado.getNumeroRegistroPatronal());
			System.err.println("CLASIFICACION: "+sujetoObligado.getClasificacion());
			sujetoTramite.setNumeroRegistroPatronal(sujetoObligado.getNumeroRegistroPatronal());
			sujetoTramite.setModalidad(sujetoObligado.getModalidad());
			sujetoTramite.setDigVerificador(sujetoObligado.getDigVerificador());
			System.err.println("Se inicializa PSP de clasificacion: "+sujetoObligado.getClasificacion().getIndPrestaServicioPersonal());
			sujetoTramite.getClasificacion().setIndRegPatClase(sujetoObligado.getClasificacion().getIndRegPatClase());
			inicializarMediosContactoCentroTrabajo(sujetoTramite, model);
			sujetoTramite.setCntroTrabajo(crearCentroTrabajoVacio());
			model.addAttribute("indRPCInvalido", false);
			model.addAttribute("indReintento", false);
			
		}
		
		sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
		
		model.addAttribute("idTramite", tipoTramite);
		model.addAttribute("sujetoObligado", sujetoObligado);
		model.addAttribute("sujetoTramite", sujetoTramite);
		log.debug("SUJETO OBLIGADO "+sujetoObligado);
		session.setAttribute("sujetoObligado", sujetoObligado);
		session.setAttribute("sujetoTramite", sujetoTramite);
		session.setAttribute("tipoTramite", tipoTramite);
		return "clasificacion";
	}
	
	private void inicializarMediosContactoCentroTrabajo(SujetoObligado sujetoTramite, Model model){
		String telefonoFijo="";
		String telefonoFijo2="";
		String correoElectronico="";
		
		if(sujetoTramite.getCntroTrabajo()!=null && sujetoTramite.getCntroTrabajo().getMediosContacto()!=null){
			
			for(MedioContacto medio :sujetoTramite.getCntroTrabajo().getMediosContacto()){
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
					if(medio.getIdVista()==1)
						telefonoFijo = medio.getDesFormaContacto();
					else if(medio.getIdVista()==2)
						telefonoFijo2 = medio.getDesFormaContacto();
				}
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
					correoElectronico=medio.getDesFormaContacto();
				}
				
			}
		}
		
		model.addAttribute("ctTelefonoFijo",telefonoFijo);
		model.addAttribute("ctTelefonoFijo2",telefonoFijo2);
		model.addAttribute("ctCorreoElectronico",correoElectronico);
	}
	
	@RequestMapping(value = "/obtenerClasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> obtenerClasificacion(
			@RequestBody Clasificacion inputObject,
			@RequestParam("idTipoTramite") Integer idTipoTramite,
			HttpServletResponse response, HttpSession session, Locale locale) {
		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			inputObject = clasificacionServiceBusiness.obtenerClasificacionEquivalente(inputObject);
			inputObject = validaReglasClasificacion(inputObject, idTipoTramite);
			result.put("clasificacion", inputObject);
		}catch(GestionPatronalBusinessException e){
			log.error("Codigo Error: "+e.getCodigo());
			if(e.getCodigo()!=null && (e.getCodigo().equals(800)|| e.getCodigo().equals(801)))
				message=e.getSituacion();
			else
				message=messageSource.getMessage(e.getMessage(), null, locale);
			
			result.put("mensajeError", message);
			log.error(inputObject, e);
		}catch(Exception e){
			message="Ocurrio un error al intentar obtener la clasificacion.";
			result.put("mensajeError", message);
			log.error(inputObject, e);
		}
		return result;
	}
	
	/**
	 * Valida reglas de modalidad y clasificacion por municipio
	 * @param inputObject
	 * @throws GestionPatronalBusinessException
	 */
	private Clasificacion validaReglasClasificacion(Clasificacion inputObject, Integer idTipoTramite) throws GestionPatronalBusinessException{
		boolean validarActividadPorMunicipio=true;
		boolean validarFraccionObligatoria=false;
		Long idPersona = null;
		Long idTipoPersona = null;
		Long idMunicipioIMSS = null;
		Long idPatronSujetoObligado = inputObject.getSujetoObligado()!=null ? inputObject.getSujetoObligado().getCveIdSujetoObligado() : null;//Este dato solo esta presenta en una modificacion de SRT o centro de trabajo
		String rfc = inputObject.getSujetoObligado()!=null && inputObject.getSujetoObligado().getFisica()!=null ? inputObject.getSujetoObligado().getFisica().getRfc()
				: inputObject.getSujetoObligado()!=null && inputObject.getSujetoObligado().getMoral()!=null ? inputObject.getSujetoObligado().getMoral().getRfc()
					: null;
		if(inputObject.getSujetoObligado()!=null && 
				inputObject.getSujetoObligado().getMunicipioIMSS()!=null && 
				StringUtils.isNotBlank(inputObject.getSujetoObligado().getMunicipioIMSS().getIdMunicipio())){
			idMunicipioIMSS = Long.valueOf(inputObject.getSujetoObligado().getMunicipioIMSS().getIdMunicipio());
			if(inputObject.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				idPersona = inputObject.getSujetoObligado().getFisica().getCveFisica();
				idTipoPersona = TipoPersona.FISICA.longValue();
			}else{
				idPersona = inputObject.getSujetoObligado().getMoral().getIdPersona();
				idTipoPersona = TipoPersona.MORAL.longValue();
				//Si es persona moral y el tr�mite es alta se evalua la fraccion obligatoria
				validarFraccionObligatoria = idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo()) ? true : false;
			}
		}else{
			log.error("No se tiene municipio asignado no se validara la regla de municipio");
			validarActividadPorMunicipio=false;
		}
		
		if(inputObject!=null && inputObject.getFraccion()!=null && inputObject.getFraccion().getId()!=null){
			Long idFraccion = inputObject.getFraccion().getId();
			log.error("Se validara la regla fraccion municipio");
			log.error("idPersona: ["+idPersona+"]");
			log.error("idTipoPersona: ["+idTipoPersona+"]");
			log.error("idMunicipio: ["+idMunicipioIMSS+"]");
			log.error("idFraccion: ["+idFraccion+"]");
			log.error("idRegistroPatronal: ["+idPatronSujetoObligado+"]");
			
			if(inputObject.getSujetoObligado()!=null && inputObject.getSujetoObligado().getCveIdSujetoObligado()!=null){
				log.error("se valida la regla de modalidad y se calcula la prima de pago");
				if( idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()) 
						|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
						|| idTipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
						|| idTipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
				){
					//A solicuitud de usuario normativo REQ1814257 se aplica validacion en caso de tramite por subcontratacion
					log.debug(":::: El tramite es "+idTipoTramite+", se aplica regla de modalidad para clasificacion");					
					ruleServiceBusiness.validarModalidadClasificacion(inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);
				}else{
					log.debug(":::: Se aplica regla de modalidad");
					ruleServiceBusiness.validarModalidad(inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);	
				}
				
				BigDecimal nuevaAsignada = ruleServiceBusiness.calcularPrima(idTipoTramite, inputObject.getSujetoObligado().getCveIdSujetoObligado(), inputObject);
				inputObject.setPrimaSRTActual(nuevaAsignada);
			}
			
			if(inputObject.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
				//Si es persona moral y el tramite es alta se evalua la fraccion obligatoria
				validarFraccionObligatoria = idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo()) ? true : false;
			}
			//TODO el && para solo validar cuando sea alta patronal y no un tramite de modificcion en el SRT
			if(validarActividadPorMunicipio && idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo()))
				ruleServiceBusiness.validarClasificacionPorPatronYMunicipio(rfc, idTipoPersona, idMunicipioIMSS, idFraccion, idPatronSujetoObligado);
			
			if(validarFraccionObligatoria)
				ruleServiceBusiness.validarFraccionObligatoria(inputObject);
		}
		
		return inputObject;
	}
	
	@RequestMapping(value = "/guardarClasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> guardarClasificacion(
			@RequestBody Clasificacion inputObject,
			@RequestParam("indReintento") boolean reintentoRpc,
			@RequestParam("indRPCInvalido") boolean rpcInvalido,
			HttpServletResponse response, HttpSession session, Locale locale) {
		String message = "";
		
		log.debug("Informacion de centro de trabajo: "+inputObject.getSujetoObligado().getCntroTrabajo());
		
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoActual = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);

		TipoTramiteEnum tipoTramite = (TipoTramiteEnum) session.getAttribute("tipoTramite");
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		//TODO: a peticion del usuario todas las solicitudes inician con origen de ventanilla aun cuando se registre por internet
		OrigenSolicitudEnum origenSolicitd = OrigenSolicitudEnum.VENTANILLA; 
		
		boolean esTramitador = 
				usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()) 
				? true : false;
		
		if(idSolicitud==null || (idSolicitud!=null && idSolicitud==0)){
			
			if(!tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)){
				boolean tramiteClasifExistente = solicitudServiceBusiness.existeTramitesClasificacionActivos(
						inputObject.getSujetoObligado().getCveIdSujetoObligado(),esTramitador);
				
				if(tramiteClasifExistente){
					message=messageSource.getMessage("error.tramite.clasificacion.previo",null,locale);
					result.put("mensajeError", message);
					return result;
				}
			}else{
				log.debug("se agrega cntro trabajo a nueva solicitud ");
				sujetoTramite.setCntroTrabajo(inputObject.getSujetoObligado().getCntroTrabajo());
				sujetoTramite.setMunicipioIMSS(inputObject.getSujetoObligado().getMunicipioIMSS());
				sujetoTramite.setSubdelegacion(inputObject.getSujetoObligado().getSubdelegacion());
				if (sujetoTramite.getMunicipioIMSS() != null) {
					sujetoTramite.getMunicipioIMSS().setSubdelegacion(sujetoTramite.getSubdelegacion());
				}
			}
			
		}
		
		if (inputObject != null) {
			try {
				
				if(tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO)) {
					CentroTrabajo cntroTrabajo = sujetoObligadoService.getCentroTrabajo(inputObject.getSujetoObligado().getCveIdSujetoObligado());
					inputObject.getSujetoObligado().setCntroTrabajo(cntroTrabajo);
				}
				
				log.debug("Se asignan los datos del centro de trabajo: "+inputObject.getSujetoObligado().getCntroTrabajo());
				sujetoTramite.setCntroTrabajo(inputObject.getSujetoObligado().getCntroTrabajo());
				sujetoTramite.setMunicipioIMSS(inputObject.getSujetoObligado().getMunicipioIMSS());
				sujetoTramite.setSubdelegacion(inputObject.getSujetoObligado().getSubdelegacion());
				if (sujetoTramite.getMunicipioIMSS() != null) {
					sujetoTramite.getMunicipioIMSS().setSubdelegacion(sujetoTramite.getSubdelegacion());
				}
				Proceso proceso = inputObject.getSujetoObligado().getProceso();
				inputObject.getSujetoObligado().setProceso(null);
				SujetoObligado soProceso = new SujetoObligado();
				SujetoObligado soAuxi = inputObject.getSujetoObligado();
				soProceso.setCveIdSujetoObligado(soAuxi.getCveIdSujetoObligado());
				soProceso.setSubdelegacion(soAuxi.getSubdelegacion());
				proceso.setSujetoObligado(soProceso);
				sujetoTramite.setProceso(proceso);
				sujetoTramite.setDesAfectacion(soAuxi.getDesAfectacion());
				sujetoTramite.setDesUsosBienes(soAuxi.getDesUsosBienes());
				sujetoTramite.setTipoPersonaFiscal(soAuxi.getTipoPersonaFiscal());
				sujetoTramite.setFisica(soAuxi.getFisica());
				sujetoTramite.setMoral(soAuxi.getMoral());
				sujetoTramite.setCveIdSujetoObligado(soAuxi.getCveIdSujetoObligado());
				sujetoTramite.setCuentaConTransporte(soAuxi.getCuentaConTransporte());

				if (sujetoActual.getFisica() != null) {
					sujetoTramite.getFisica().setIdPersona(
							sujetoActual.getFisica().getIdPersona());
				}
				if (sujetoActual.getMoral() != null) {
					sujetoTramite.getMoral().setIdPersona(
							sujetoActual.getMoral().getIdPersona());
				}

				inputObject.setSujetoObligado(soProceso);
				sujetoTramite.setClasificacion(inputObject);
				inputObject.setFecPresentacion(Calendar.getInstance().getTime());
				
				/**
				 * Se guardan los patrones para fusion o sustitucion
				 * 
				 */
				
				List<SujetoObligado> sujetosFusionSust = (List<SujetoObligado>) session.getAttribute(ComponenteBusquedaPatron.KEY_PATRONES_FUSIONADOS);
				
				if(sujetosFusionSust != null) {
					log.debug("::::::::::::Total de registros patronales a sustituir: " + sujetosFusionSust.size());
					log.debug("::::::::::::Tipo tramite: " + tipoTramite);
					for (Iterator<SujetoObligado> iterator = sujetosFusionSust
							.iterator(); iterator.hasNext();) {
						SujetoObligado so = iterator.next();
						log.debug("::::: Registro a sustituir: " + so.getNumeroRegistroPatronal());
					}
					if(tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO)) {
						for(SujetoObligado sujeto : sujetosFusionSust ) {
							String indicadorBaja = sujeto.getDescSituacionBaja() != null ? "2": "0";
							sujeto.getClasificacion().setIndBaja(indicadorBaja);
							
							CentroTrabajo cntroTrabajo = sujetoObligadoService.getCentroTrabajo(sujeto.getCveIdSujetoObligado());
							sujeto.setCntroTrabajo(cntroTrabajo);
							
						}
					}
					sujetoTramite.setSujetosObligados(sujetosFusionSust);
				}else{
					log.debug(":::::::::::: La lista de patrones SUSTITUIDOS no esta en sesion");
				}
				
				TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION;
				if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)){
					tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO;
					log.debug("Datos del centro de trabajo: "+sujetoTramite.getCntroTrabajo());
				}
				
				if (idSolicitud == null) {
					log.debug("TipoTramite modificacion: "+tipoTramite);
					
					log.debug("TipoSolicitud modificacion: "+tipoTramite);
					Solicitud sol =solicitudServiceBusiness.generarSolicitud(
							tipoSolicitud,
							EstadoSolicitudEnum.PENDIENTE_AUTORIZACION, usuario,
							tipoTramite,
							EstadoTramiteEnum.INICIADO,
							sujetoTramite,reintentoRpc, rpcInvalido);
					session.setAttribute("idSolicitud", sol.getSolicitudId());
					log.debug("CREANDO NUEVA SOLICITUD: [" + sol.getSolicitudId() + "]");
					/**TODO a peticion del usuario todas las solicitudes deben tener origen ventanilla aun cuando el tramite se inicie por internet
					 * 
					 
					if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
						sol.setOrigenSolicitud(new OrigenSolicitud());
						sol.getOrigenSolicitud().setIdTipoSolicitud(origenSolicitd.getId());
						sol.getOrigenSolicitud().setDescripcion(origenSolicitd.getDesc());
						sol.setSolicitante(usuario);
						sol = solicitudServiceBusiness.actualizarSolicitud(sol,
								EstadoTramiteEnum.ACTIVO, sujetoTramite);
					} 
					**/
					sol.setOrigenSolicitud(new OrigenSolicitud());
					sol.getOrigenSolicitud().setIdOrigenSolicitud(origenSolicitd.getId());
					sol.getOrigenSolicitud().setDescripcion(origenSolicitd.getDesc());
					sol.setSolicitante(usuario);
					sol = solicitudServiceBusiness.actualizarSolicitud(sol,
							EstadoTramiteEnum.ACTIVO, sujetoTramite);
				
					session.setAttribute("idSolicitud", sol.getSolicitudId());
					result.put("idSolicitud", sol.getSolicitudId());
					result.put("folioSolicitud", sol.getNoFolioSolicitud());
					message = "Se ha guardado la solicitud con folio ["+sol.getNoFolioSolicitud()+"]";
				} else {
					
					result.put("idSolicitud", idSolicitud);
					Solicitud solicitud = new Solicitud();
					solicitud.setSolicitudId(idSolicitud);
					solicitud.setIndReintentoRpc(reintentoRpc);
					solicitud.setIndRpcInvalido(rpcInvalido);
					solicitud.setSolicitante(usuario);
					solicitud.setFechaPresentacion(inputObject.getFecPresentacion());
					solicitud.setOrigenSolicitud(new OrigenSolicitud());
					solicitud.getOrigenSolicitud().setDescripcion(origenSolicitd.getDesc());
					solicitud.getOrigenSolicitud().setIdOrigenSolicitud(origenSolicitd.getId());

					if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
						solicitud = solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoTramite);
					}else{
						solicitud = solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.INICIADO, sujetoTramite);
					}

					message = "Se ha actualizado la solicitud.";
				}
				result.put("caso", 2);
				result.put("mensajeExito", message);
			}catch (GestionPatronalBusinessException e) {
				if(!StringUtils.isBlank(e.getMessage())){
					message = e.getMessage();
				}
				result.put("mensajeError", message);
				log.error(inputObject, e);
			}catch(Exception e){
				message="Ocurrio un error al intentar guardar la solicitud.";
				result.put("mensajeError", message);
				log.error(inputObject, e);
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/guardarClasificacionPrevioRevisar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> guardarClasificacionPrevioRevisar(
			@RequestBody Clasificacion inputObject,
			HttpServletResponse response, HttpSession session, Locale locale) {
		String message = "";
		
		log.debug("Dentro de guardarClasificacionPrevioRevisar =============> : ");
		
		log.debug("Informacion de centro de trabajo: "+inputObject.getSujetoObligado().getCntroTrabajo());
		
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoActual = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);

		TipoTramiteEnum tipoTramite = (TipoTramiteEnum) session.getAttribute("tipoTramite");
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		
		boolean esTramitador = 
				usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()) 
				? true : false;
		
		if(idSolicitud==null || (idSolicitud!=null && idSolicitud==0)){
			
			if(!tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)){
				boolean tramiteClasifExistente = solicitudServiceBusiness.existeTramitesClasificacionActivos(
						inputObject.getSujetoObligado().getCveIdSujetoObligado(),esTramitador);
				
				if(tramiteClasifExistente){
					message=messageSource.getMessage("error.tramite.clasificacion.previo",null,locale);
					result.put("mensajeError", message);
					return result;
				}
			}else{
				log.debug("se agrega cntro trabajo a nueva solicitud ");
				sujetoTramite.setCntroTrabajo(inputObject.getSujetoObligado().getCntroTrabajo());
				sujetoTramite.setMunicipioIMSS(inputObject.getSujetoObligado().getMunicipioIMSS());
				sujetoTramite.setSubdelegacion(inputObject.getSujetoObligado().getSubdelegacion());
				if (sujetoTramite.getMunicipioIMSS() != null) {
					sujetoTramite.getMunicipioIMSS().setSubdelegacion(sujetoTramite.getSubdelegacion());
				}
			}
			
		}
		
		if (inputObject != null) {
			try {
				
				if(tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO)) {
					CentroTrabajo cntroTrabajo = sujetoObligadoService.getCentroTrabajo(inputObject.getSujetoObligado().getCveIdSujetoObligado());
					inputObject.getSujetoObligado().setCntroTrabajo(cntroTrabajo);
				}
				
				log.debug("Se asignan los datos del centro de trabajo: "+inputObject.getSujetoObligado().getCntroTrabajo());
				sujetoTramite.setCntroTrabajo(inputObject.getSujetoObligado().getCntroTrabajo());
				sujetoTramite.setMunicipioIMSS(inputObject.getSujetoObligado().getMunicipioIMSS());
				sujetoTramite.setSubdelegacion(inputObject.getSujetoObligado().getSubdelegacion());
				if (sujetoTramite.getMunicipioIMSS() != null) {
					sujetoTramite.getMunicipioIMSS().setSubdelegacion(sujetoTramite.getSubdelegacion());
				}
				Proceso proceso = inputObject.getSujetoObligado().getProceso();
				inputObject.getSujetoObligado().setProceso(null);
				SujetoObligado soProceso = new SujetoObligado();
				//SujetoObligado soAuxi = inputObject.getSujetoObligado();
				soProceso.setCveIdSujetoObligado(inputObject.getSujetoObligado().getCveIdSujetoObligado());
				soProceso.setSubdelegacion(inputObject.getSujetoObligado().getSubdelegacion());
				proceso.setSujetoObligado(soProceso);
				sujetoTramite.setProceso(proceso);
				sujetoTramite.setDesAfectacion(inputObject.getSujetoObligado().getDesAfectacion());
				sujetoTramite.setDesUsosBienes(inputObject.getSujetoObligado().getDesUsosBienes());
				sujetoTramite.setCveIdSujetoObligado(inputObject.getSujetoObligado().getCveIdSujetoObligado());
				sujetoTramite.setTipoPersonaFiscal(inputObject.getSujetoObligado().getTipoPersonaFiscal());
				sujetoTramite.setFisica(sujetoActual.getFisica());
				sujetoTramite.setMoral(sujetoActual.getMoral());
				sujetoTramite.setCuentaConTransporte(inputObject.getSujetoObligado().getCuentaConTransporte());
				sujetoTramite.setSubdelegacion(sujetoActual.getSubdelegacion());
				

				if (sujetoActual.getFisica() != null) {
					sujetoTramite.getFisica().setIdPersona(
							sujetoActual.getFisica().getIdPersona());
				}
				if (sujetoActual.getMoral() != null) {
					sujetoTramite.getMoral().setIdPersona(
							sujetoActual.getMoral().getIdPersona());
				}
				
				inputObject.setSujetoObligado(soProceso);
				//sujetoTramite.setProceso(proceso);
				//inputObject.setFecPresentacion(Calendar.getInstance().getTime());
				//sujetoTramite.setClasificacion(inputObject);

				inputObject.setSujetoObligado(soProceso);
				sujetoTramite.setClasificacion(inputObject);
				inputObject.setFecPresentacion(Calendar.getInstance().getTime());
				
				/**
				 * Se guardan los patrones para fusion o sustitucion
				 * 
				 */
				
				List<SujetoObligado> sujetosFusionSust = (List<SujetoObligado>) session.getAttribute(ComponenteBusquedaPatron.KEY_PATRONES_FUSIONADOS);
				
				if(sujetosFusionSust != null) {
					log.debug("::::::::::::Total de registros patronales a sustituir: " + sujetosFusionSust.size());
					log.debug("::::::::::::Tipo tramite: " + tipoTramite);
					for (Iterator<SujetoObligado> iterator = sujetosFusionSust
							.iterator(); iterator.hasNext();) {
						SujetoObligado so = iterator.next();
						log.debug("::::: Registro a sustituir: " + so.getNumeroRegistroPatronal());
					}
					if(tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO)) {
						for(SujetoObligado sujeto : sujetosFusionSust ) {
							String indicadorBaja = sujeto.getDescSituacionBaja() != null ? "2": "0";
							sujeto.getClasificacion().setIndBaja(indicadorBaja);
							
							CentroTrabajo cntroTrabajo = sujetoObligadoService.getCentroTrabajo(sujeto.getCveIdSujetoObligado());
							sujeto.setCntroTrabajo(cntroTrabajo);
							
						}
					}
					sujetoTramite.setSujetosObligados(sujetosFusionSust);
				}else{
					log.debug(":::::::::::: La lista de patrones SUSTITUIDOS no esta en sesion");
				}
				
				TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION;
				if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO)){
					tipoSolicitud = TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO;
					log.debug("Datos del centro de trabajo: "+sujetoTramite.getCntroTrabajo());
				}
				
				if (idSolicitud == null) {
					log.debug("TipoTramite modificacion: "+tipoTramite);
					
					log.debug("TipoSolicitud modificacion: "+tipoTramite);
					Solicitud sol =solicitudServiceBusiness.generarSolicitud(
							tipoSolicitud,
							EstadoSolicitudEnum.REGISTRADA, usuario,
							tipoTramite,
							EstadoTramiteEnum.INICIADO,
							sujetoTramite,false, false);
					session.setAttribute("idSolicitud", sol.getSolicitudId());
					log.debug("CREANDO NUEVA SOLICITUD: [" + sol.getSolicitudId() + "]");
					if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
						sol.setSolicitante(usuario);
						solicitudServiceBusiness.actualizarSolicitud(sol,
								EstadoTramiteEnum.ACTIVO, sujetoTramite);
					}
					session.setAttribute("idSolicitud", sol.getSolicitudId());
					result.put("idSolicitud", sol.getSolicitudId());
					result.put("folioSolicitud", sol.getNoFolioSolicitud());
					message = "Se ha guardado la solicitud con folio ["+sol.getNoFolioSolicitud()+"]";
				} else {
					
					result.put("idSolicitud", idSolicitud);
					Solicitud solicitud = new Solicitud();
					solicitud.setSolicitudId(idSolicitud);
					solicitud.setIndReintentoRpc(false);
					solicitud.setIndRpcInvalido(false);
					solicitud.setSolicitante(usuario);
					solicitud.setFechaPresentacion(inputObject.getFecPresentacion());
					if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
						solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoTramite);
					}else{
						solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.INICIADO, sujetoTramite);
					}
					session.setAttribute("sujetoActual", sujetoTramite);
					message = "Se ha actualizado la solicitud.";
				}
				result.put("caso", 2);
				result.put("mensajeExito", message);
			}catch (GestionPatronalBusinessException e) {
				if(!StringUtils.isBlank(e.getMessage())){
					message = e.getMessage();
				}
				result.put("mensajeError", message);
				log.error(inputObject, e);
			}catch(Exception e){
				message="Ocurrio un error al intentar guardar la solicitud.";
				result.put("mensajeError", message);
				log.error(inputObject, e);
			}
		}
		return result;
	}

	@RequestMapping(value = "/finalizarClasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> finalizarClasificacion(
			@RequestBody Clasificacion inputObject,
			@RequestParam("indReintento") boolean reintentoRpc,
			@RequestParam("indRPCInvalido") boolean rpcInvalido,
			Model model, HttpServletResponse response, HttpSession session, Locale locale) {
		String message = "";
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		log.debug("CLASIFICACION [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		SujetoObligado sujetoObligado = (SujetoObligado)session.getAttribute("sujetoObligado");
		SujetoObligado sujetoActual = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		Boolean fueFirmada = (Boolean)session.getAttribute("fueFirmada");
		fueFirmada = (fueFirmada == null) ? false : fueFirmada;
		String fwd="";
		Map<String, Object> mapConcluir = new HashMap<String, Object>();
		
//		if (inputObject != null) {
			try {
//				log.debug("CLASIFICACION DE REGISTRO PATRONAL [ " + inputObject.getSujetoObligado().getNumeroRegistroPatronal() + " ]");
//				Proceso proceso = inputObject.getSujetoObligado().getProceso();
//				inputObject.getSujetoObligado().setProceso(null);
//				SujetoObligado soProceso = new SujetoObligado();
//				soProceso.setCveIdSujetoObligado(inputObject.getSujetoObligado().getCveIdSujetoObligado());
//				proceso.setSujetoObligado(soProceso);
//				sujetoTramite.setProceso(proceso);
//				sujetoTramite.setDesAfectacion(inputObject.getSujetoObligado().getDesAfectacion());
//				sujetoTramite.setDesUsosBienes(inputObject.getSujetoObligado().getDesUsosBienes());
//				sujetoTramite.setCveIdSujetoObligado(inputObject.getSujetoObligado().getCveIdSujetoObligado());
//				sujetoTramite.setTipoPersonaFiscal(inputObject.getSujetoObligado().getTipoPersonaFiscal());
//				sujetoTramite.setFisica(inputObject.getSujetoObligado().getFisica());
//				sujetoTramite.setMoral(inputObject.getSujetoObligado().getMoral());
//				sujetoTramite.setCveIdSujetoObligado(inputObject.getSujetoObligado().getCveIdSujetoObligado());
//				sujetoTramite.setCuentaConTransporte(inputObject.getSujetoObligado().getCuentaConTransporte());
//				sujetoTramite.setCntroTrabajo(inputObject.getSujetoObligado().getCntroTrabajo());
//				sujetoTramite.setMunicipioIMSS(inputObject.getSujetoObligado().getMunicipioIMSS());
//				sujetoTramite.setSubdelegacion(inputObject.getSujetoObligado().getSubdelegacion());
//				if (sujetoTramite.getMunicipioIMSS() != null) {
//					sujetoTramite.getMunicipioIMSS().setSubdelegacion(sujetoTramite.getSubdelegacion());
//				}
//				
//				if (sujetoActual.getFisica() != null) {
//					sujetoTramite.getFisica().setIdPersona(
//							sujetoActual.getFisica().getIdPersona());
//				}
//				if (sujetoActual.getMoral() != null) {
//					sujetoTramite.getMoral().setIdPersona(
//							sujetoActual.getMoral().getIdPersona());
//				}
//				
//				inputObject.setSujetoObligado(soProceso);
//				sujetoTramite.setProceso(proceso);
//				inputObject.setFecPresentacion(Calendar.getInstance().getTime());
//				sujetoTramite.setClasificacion(inputObject);
				String rfc = sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) ? 
						sujetoTramite.getFisica().getRfc() : sujetoTramite.getMoral().getRfc();
//						
//				/**
//				 * Se guardan los patrones para fusion o sustitucion
//				 * 
//				 */
//
//				List<SujetoObligado> sujetosFusionSust = (List<SujetoObligado>) session.getAttribute(ComponenteBusquedaPatron.KEY_PATRONES_FUSIONADOS);
//
//				if(sujetosFusionSust != null) {
//					log.debug("::::::::::::Total de registros patronales a sustituir: " + sujetosFusionSust.size());
//					for (Iterator<SujetoObligado> iterator = sujetosFusionSust
//							.iterator(); iterator.hasNext();) {
//						SujetoObligado so = iterator.next();
//						log.debug("::::: Registro a sustituir: " + so.getNumeroRegistroPatronal());
//					}
//					sujetoTramite.setSujetosObligados(sujetosFusionSust);
//				}else{
//					log.debug(":::::::::::: La lista de patrones SUSTITUIDOS no esta en sesion");
//				}
//					
//				if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
//						CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
//						|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
//								CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue()) ){
//
//					evaluarReglaRPC(idSolicitud, rfc, sujetoTramite);
//					Solicitud solicitud = new Solicitud();
//					solicitud.setSolicitudId(idSolicitud);
//					solicitud.setSolicitante(usuario);
//					solicitud.setFechaPresentacion(inputObject.getFecPresentacion());
//					System.err.println("Fue firmada: "+fueFirmada);
//					if(fueFirmada!=null && fueFirmada){
//						System.err.println("Se agregaron datos de firma y se concluira la solicitud");
//						
//						Solicitud solicitudActual = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
//						solicitud.setTipoSolicitud(solicitudActual.getTipoSolicitud());
////						if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(
////								TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())){
////							solicitud.setEstadoSolicitud(new EstadoSolicitud());
////							solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
////									EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo());
////							solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoTramite);
////							fwd = generarAcuse(model, session, fueFirmada);
////						}else{
//						
//						mapConcluir = concluirClasificacionVentanilla(solicitudActual, inputObject, rfc, 
//									sujetoTramite, sujetoObligado, reintentoRpc, rpcInvalido, 
//									model, session, fueFirmada);
//						fwd = (String) mapConcluir.get("fwd");
//							
//							
//							
//							
////						}
//					}else {
//						solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoTramite);
//						fwd = generarAcuse(response, model, session, fueFirmada);
//					}
//				}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
//						CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
//					Solicitud solicitudActual = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
//					mapConcluir = concluirClasificacionVentanilla(solicitudActual, inputObject, rfc, 
//							sujetoTramite, sujetoObligado, reintentoRpc, 
//							rpcInvalido, model, session,fueFirmada);
//					fwd = (String) mapConcluir.get("fwd");
//							
//				}
				
				guardarClasificacion(inputObject, reintentoRpc, rpcInvalido, response, session, locale);
				Solicitud solicitudActual = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
				mapConcluir = concluirClasificacionVentanilla(solicitudActual, inputObject, rfc, 
						sujetoTramite, sujetoObligado, reintentoRpc, 
						rpcInvalido, model, session);
				
				session.setAttribute("sujetoTramite", null);
				session.setAttribute("tipoTramite", null);
				
				result.put("caso", 3);
				message = "Se ha finalizado la solicitud";
				result.put("mensajeExito", message);
				result.put("fwd", fwd);
				result.put("rfc", rfc);
				result.put("documentosFinalesVentanilla", mapConcluir.get("documentosFinalesVentanilla"));
				result.put("registroPatronal", sujetoObligado.getNumeroRegistroPatronal());
				
			}catch(Exception e){
				message="Ocurrio un error al intentar finalizar la solicitud.";
				result.put("mensajeError", message);
				log.error(inputObject, e);
			}
//		}
		session.removeAttribute("fueFirmada");
		session.removeAttribute("datosFirma");
		return result;
	}
	
	private Map<String,Object> concluirClasificacionVentanilla(Solicitud solicitud, Clasificacion inputObject, String rfc, SujetoObligado sujetoTramite, 
			SujetoObligado sujetoObligado, boolean reintentoRpc, boolean rpcInvalido, 
			Model model, HttpSession session){
		log.debug("CLASIFICACION -- SE AFECTA LA BASE DE DATOS [ "
				+ inputObject + " ]");
		log.debug("CLASIFICACION REGISTRO PATRONAL: "+sujetoTramite.getNumeroRegistroPatronal());
		Map<String, Object> mapConcluir = new HashMap<String, Object>();
		
		//TODO solicitud del usuario para que todos los orgienes sena ventanilla
		OrigenSolicitudEnum origenSolicitd = OrigenSolicitudEnum.VENTANILLA; 
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdOrigenSolicitud(origenSolicitd.getId());
	
		try{
			if(solicitud.getSolicitante() == null) {
				Usuario usuario = (Usuario)session.getAttribute("usuario");
				solicitud.setSolicitante(usuario);
			}
//			Usuario usuario = (Usuario)session.getAttribute("usuario");
			evaluarReglaRPC(solicitud.getSolicitudId(), rfc, sujetoTramite);
			log.debug("REGISTRO PATRO: "+inputObject.getSujetoObligado().getNumeroRegistroPatronal());
			//Inputobject trae toda la informacion nueva del formulario  TODO: Validar que afecte correctamente la informacion 

			sujetoTramite.setClasificacion(inputObject);
			
			solicitud.setSujetoObligado(sujetoObligado);
			solicitud.setIndReintentoRpc(reintentoRpc);
			solicitud.setIndRpcInvalido(rpcInvalido);
		
			solicitud.setSujetoObligado(sujetoTramite);
			solicitud.getSujetoObligado().setClasificacion(inputObject);
			//Nos aseguramos de tener la fecha efecto
			if (CollectionUtils.isNotEmpty(solicitud.getTramites())) {
				TramiteSujetoObligado tso = (TramiteSujetoObligado)solicitud.getTramites().get(0);
				if (tso.getSujetoObligado() != null 
						&& tso.getSujetoObligado().getClasificacion() != null 
						&& tso.getSujetoObligado().getClasificacion().getFecEfecto() == null) {
					tso.getSujetoObligado().getClasificacion().setFecEfecto(inputObject.getFecEfecto());
				} 
				if (tso.getSujetoObligado() != null && tso.getSujetoObligado().getClasificacion() != null 
						&& (tso.getSujetoObligado().getClasificacion().getFraccion() == null 
						|| tso.getSujetoObligado().getClasificacion().getFraccion().getId() == null)) {
					tso.getSujetoObligado().getClasificacion().setFraccion(sujetoTramite.getClasificacion().getFraccion());
				}
				if (tso.getSujetoObligado().getClasificacion().getPrimaSRTSugerida() == null) {
					if (sujetoTramite.getClasificacion() != null && sujetoTramite.getClasificacion().getPrimaSRTSugerida() != null) {
						tso.getSujetoObligado().getClasificacion().setPrimaSRTSugerida(sujetoTramite.getClasificacion().getPrimaSRTSugerida());
					} else {
						tso.getSujetoObligado().getClasificacion().setPrimaSRTSugerida(sujetoTramite.getClasificacion().getPrimaSRTActual());
					}
				}
			}
			//Seteo de la cadena originl
			CommonValidator.getCadenaOriginalVentanilla(solicitud, 
					sujetoObligado.getFisica()!=null?sujetoObligado.getFisica():sujetoTramite.getMoral(),
							sujetoObligado.getNumeroRegistroPatronal()+ sujetoObligado.getModalidad().getNumModalidad() );	
			//se setea la infomracion de sello
			//RespuestaFirmadoSimple firmadoSimple = firmaDigitalBusiness.getSelloDigital(cadenaOriginal, null, null);
			//this.setDatosFirmaElectronica(solicitud, firmadoSimple, cadenaOriginal);
			
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoTramite);
			
			
			log.debug("pase el guardado parcial de la solicitud");
			byte[] reporte = clasificacionService.finalizarModificacionSRTVentanilla(solicitud);
			log.debug("pase la finalizacion de cambio de clasificaion");
			mapConcluir.put("fwd", "modificacion.acuse");
			mapConcluir.put("documentosFinalesVentanilla", reporte);
			
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return mapConcluir;
	}
	
	private EstadoTramiteEnum obtenerEstadoTramiteEnumById(Integer codigo){
		EstadoTramiteEnum estadoTramiteEnum=null;
		if(codigo.equals(EstadoTramiteEnum.ACTIVO.getCodigo()))
			estadoTramiteEnum=EstadoTramiteEnum.ACTIVO;
		else if(codigo.equals(EstadoTramiteEnum.INICIADO.getCodigo()))
			estadoTramiteEnum=EstadoTramiteEnum.INICIADO;
		else
			estadoTramiteEnum=EstadoTramiteEnum.INICIADO;
		
		return estadoTramiteEnum;
	}
	
	@RequestMapping(value = "/carcelarClasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> carcelarClasificacion(
			HttpServletResponse response, HttpSession session) {
		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		log.debug("CANCELAR SOLICITUD [ " + idSolicitud + " ]");
		if (idSolicitud != null) {
			try {
				log.debug("Se cancela la solicitud [ "+ idSolicitud + " ]");
				solicitudServiceBusiness.cancelarSolicitud(idSolicitud);
				result.put("caso", 4);
				message = "Se ha cancelado la solicitud";
				result.put("mensajeExito", message);
			}catch(Exception e){
				message="Ocurrio un error al intentar cancelar la solicitud.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/guardarProcesos", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> guardarProcesos(
			@RequestBody Proceso inputObject, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.info("Guardar PROCESOL [ " + inputObject + " ]");
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		String message = "";
		if (inputObject != null) {
			try {
				sujetoTramite.getProceso().setDesInicial(inputObject.getDesInicial());
				sujetoTramite.getProceso().setDesIntermedio(inputObject.getDesIntermedio());
				sujetoTramite.getProceso().setDesFinal(inputObject.getDesFinal());
				sujetoTramite.getProceso().setClave((long)1);
				result.put("Proceso", sujetoTramite.getProceso());
				
				message = "Se han almacena los procesos.";
				result.put("mensajeExito", message);
				
			}catch(Exception e){
				message="Ocurrio un error al intentar almacenar los procesos.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Producto> paginarProductosServicios(
			@RequestBody ProductoServicioDataTable params, HttpSession session) {
		log.debug("ENTRANDO A paginarProductosServicios");
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		DatosSalidaPaginador<Producto> output = new DatosSalidaPaginador<Producto>();
		DatosEntradaPaginador<Producto> input = new DatosEntradaPaginador<Producto>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(getProductosSalida(sujetoTramite.getProductos()));
		} catch (Exception e) {
			output = new DatosSalidaPaginador<Producto>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output;
	}

	private List<Producto> getProductosSalida(List<Producto> listProducto) {
		List<Producto> listProductoSalida = new ArrayList<Producto>();

		if (listProducto != null) {
			int index = 0;
			for (Producto producto : listProducto) {
				Producto productoSalida = new Producto();
				productoSalida.setId(producto.getId());
				productoSalida.setSujetoObligado(producto.getSujetoObligado());
				productoSalida.setErrorFormGeneral(
						producto.getErrorFormGeneral());

				productoSalida.setDescripcion(
						generarFormatoHtmlSalida(producto.getDescripcion()));

				if (producto.getIdVista() != null) {
					productoSalida.setIdVista(producto.getIdVista());
				} else {
					productoSalida.setIdVista(Long.valueOf(index + 1));
				}

				listProductoSalida.add(productoSalida);
				index++;
			}
		}

		return listProductoSalida;
	}

	@RequestMapping(value = "/agregarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarProductosServicios(
			@RequestBody Producto inputObject, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					agregarItemLista(sujetoTramite.getProductos(), inputObject);
					log.debug("SUJETO TRAMITE AGREGAR PRODUCTO ["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}				
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar agregar el producto/servicio.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/modificarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarProductosServicios(
			@RequestBody Producto inputObject, HttpServletResponse response,
			HttpSession session) {
		log.info("PRODUCTO [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (Producto producto : sujetoTramite.getProductos()) {
			if (producto.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				producto.setSujetoObligado(sujetoObligado);
			}
		}

		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					modificarItemLista(sujetoTramite.getProductos(), inputObject);
					log.debug("SUJETO TRAMITE MODIFICAR PRODUCTO ["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar actualizar el producto/servicio.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/eliminarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarProductosServicios(
			@RequestBody Producto inputObject, HttpServletResponse response,
			HttpSession session) {
		log.info("PRODUCTO [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (Producto producto : sujetoTramite.getProductos()) {
			if (producto.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				producto.setSujetoObligado(sujetoObligado);
			}
		}

		if (inputObject != null) {
			if (inputObject.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				inputObject.setSujetoObligado(sujetoObligado);
			}

			try {
				eliminarItemLista(sujetoTramite.getProductos(), inputObject);
				log.debug("SUJETO TRAMITE ELIMINAR PRODUCTO ["+sujetoTramite+"]");
				result.put("mensajeExito", "");
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar eliminar el producto/servicio.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<MateriaPrima> paginarMateriaMaterial(
			@RequestBody MateriaMaterialDataTable params, HttpSession session) {
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		DatosSalidaPaginador<MateriaPrima> output = new DatosSalidaPaginador<MateriaPrima>();
		DatosEntradaPaginador<MateriaPrima> input = new DatosEntradaPaginador<MateriaPrima>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(getMateriaPrimaSalida(sujetoTramite.getMateriaPrimaMateriales()));
		} catch (Exception e) {
			output = new DatosSalidaPaginador<MateriaPrima>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output;
	}

	private List<MateriaPrima> getMateriaPrimaSalida(List<MateriaPrima> listMateriaPrima) {
		List<MateriaPrima> listMateriaPrimaSalida = new ArrayList<MateriaPrima>();

		if (listMateriaPrima != null) {
			int index = 0;
			for (MateriaPrima materiaPrima : listMateriaPrima) {
				MateriaPrima materiaPrimaSalida = new MateriaPrima();
				materiaPrimaSalida.setId(materiaPrima.getId());
				materiaPrimaSalida.setSujetoObligado(materiaPrima.getSujetoObligado());
				materiaPrimaSalida.setErrorFormGeneral(materiaPrima.getErrorFormGeneral());

				materiaPrimaSalida.setDescripcion(
						generarFormatoHtmlSalida(materiaPrima.getDescripcion()));
				
				if (materiaPrima.getIdVista() != null) {
					materiaPrimaSalida.setIdVista(materiaPrima.getIdVista());
				} else {
					materiaPrimaSalida.setIdVista(Long.valueOf(index + 1));
				}

				listMateriaPrimaSalida.add(materiaPrimaSalida);
				index++;
			}
		}

		return listMateriaPrimaSalida;
	}

	@RequestMapping(value = "/agregarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarMateriaMaterial(
			@RequestBody MateriaPrima inputObject,
			HttpServletResponse response, HttpSession session) {
		log.info("AGREGAR MATERIA PRIMA [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					agregarItemLista(sujetoTramite.getMateriaPrimaMateriales(),
							inputObject);
					log.debug("SUJETO TRAMITE AGREGAR MATERIA MATERIAL ["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar agregar la materia prima.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/modificarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarMateriaMaterial(
			@RequestBody MateriaPrima inputObject,
			HttpServletResponse response, HttpSession session) {
		log.info("MODIFICAR MATERIA PRIMA [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (MateriaPrima materiaPrima : sujetoTramite.getMateriaPrimaMateriales()) {
			if (materiaPrima.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				materiaPrima.setSujetoObligado(sujetoObligado);
			}
		}

		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					modificarItemLista(
							sujetoTramite.getMateriaPrimaMateriales(),
							inputObject);
					log.debug("SUJETO TRAMITE MODIFICAR MATERIA MATERIAL ["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar actualizar la materia prima.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/eliminarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarMateriaMaterial(
			@RequestBody MateriaPrima inputObject,
			HttpServletResponse response, HttpSession session) {
		log.info("ELIMINAR MATERIA PRIMA [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (MateriaPrima materiaPrima : sujetoTramite.getMateriaPrimaMateriales()) {
			if (materiaPrima.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				materiaPrima.setSujetoObligado(sujetoObligado);
			}
		}
		
		if (inputObject != null) {
			if (inputObject.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				inputObject.setSujetoObligado(sujetoObligado);
			}
			
			try {
				eliminarItemLista(sujetoTramite.getMateriaPrimaMateriales(),
						inputObject);
				log.debug("SUJETO TRAMITE ELIMINAR MATERIA MATERIAL ["+sujetoTramite+"]");
					result.put("mensajeExito", "");
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar eliminar la materia prima.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<MaquinariaEquipo> paginarMaquinariaEquipo(
			@RequestBody MaquinariaEquipoDataTable params, HttpSession session) {
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		DatosSalidaPaginador<MaquinariaEquipo> output = new DatosSalidaPaginador<MaquinariaEquipo>();
		DatosEntradaPaginador<MaquinariaEquipo> input = new DatosEntradaPaginador<MaquinariaEquipo>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(getMaquinariaEquipoSalida(sujetoTramite.getEquipos()));
		} catch (Exception e) {
			output = new DatosSalidaPaginador<MaquinariaEquipo>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output;
	}

	private List<MaquinariaEquipo> getMaquinariaEquipoSalida(List<MaquinariaEquipo> listMaquinariaEquipo) {
		List<MaquinariaEquipo> listMaquinariaEquipoSalida = new ArrayList<MaquinariaEquipo>();

		if (listMaquinariaEquipo != null) {
			int index = 0;
			for (MaquinariaEquipo maquinariaEquipo : listMaquinariaEquipo) {
				MaquinariaEquipo maquinariaEquipoSalida = new MaquinariaEquipo();
				maquinariaEquipoSalida.setErrorFormGeneral(maquinariaEquipo.getErrorFormGeneral());
				maquinariaEquipoSalida.setId(maquinariaEquipo.getId());
				maquinariaEquipoSalida.setNumUnidades(maquinariaEquipo.getNumUnidades());
				maquinariaEquipoSalida.setSujetoObligado(maquinariaEquipo.getSujetoObligado());
				maquinariaEquipoSalida.setTipo(maquinariaEquipo.getTipo());

				maquinariaEquipoSalida.setDesUso(generarFormatoHtmlSalida(
						maquinariaEquipo.getDesUso()));
				maquinariaEquipoSalida.setDesNombre(generarFormatoHtmlSalida(
						maquinariaEquipo.getDesNombre()));
				maquinariaEquipoSalida.setDesCapacidadPotencia(generarFormatoHtmlSalida(
						maquinariaEquipo.getDesCapacidadPotencia()));

				if (maquinariaEquipo.getIdVista() != null) {
					maquinariaEquipoSalida.setIdVista(maquinariaEquipo.getIdVista());
				} else {
					maquinariaEquipoSalida.setIdVista(Long.valueOf(index + 1));
				}

				listMaquinariaEquipoSalida.add(maquinariaEquipoSalida);
				index++;
			}
		}

		return listMaquinariaEquipoSalida;
	}

	@RequestMapping(value = "/agregarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarMaquinariaEquipo(
			@RequestBody MaquinariaEquipo inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					agregarItemLista(sujetoTramite.getEquipos(), inputObject);
					log.debug("SUJETO TRAMITE AGREGAR MAQUINARIA EQUIPO["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar agregar la maquinaria/equipo.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/modificarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarMaquinariaEquipo(
			@RequestBody MaquinariaEquipo inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (MaquinariaEquipo maquinariaEquipo : sujetoTramite.getEquipos()) {
			if (maquinariaEquipo.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				maquinariaEquipo.setSujetoObligado(sujetoObligado);
			}
		}

		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					modificarItemLista(sujetoTramite.getEquipos(), inputObject);
					log.debug("SUJETO TRAMITE MODIFICAR MAQUINARIA EQUIPO["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar actualizar la maquinaria/equipo.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/eliminarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarMaquinariaEquipo(
			@RequestBody MaquinariaEquipo inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (MaquinariaEquipo maquinariaEquipo : sujetoTramite.getEquipos()) {
			if (maquinariaEquipo.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				maquinariaEquipo.setSujetoObligado(sujetoObligado);
			}
		}

		if (inputObject != null) {
			if (inputObject.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				inputObject.setSujetoObligado(sujetoObligado);
			}
			
			try {
				eliminarItemLista(sujetoTramite.getEquipos(), inputObject);
				log.debug("SUJETO TRAMITE ELIMINAR MAQUINARIA EQUIPO["+sujetoTramite+"]");
				result.put("mensajeExito", "");
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar eliminar la maquinaria/equipo.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<EquipoTransporte> paginarEquipoTransporte(
			@RequestBody EquipoTransporteDataTable params, HttpSession session) {
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		DatosSalidaPaginador<EquipoTransporte> output = new DatosSalidaPaginador<EquipoTransporte>();
		DatosEntradaPaginador<EquipoTransporte> input = new DatosEntradaPaginador<EquipoTransporte>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(getEquipoTransporteSalida(sujetoTramite.getEquiposTransporte()));
		} catch (Exception e) {
			output = new DatosSalidaPaginador<EquipoTransporte>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output;
	}

	private List<EquipoTransporte> getEquipoTransporteSalida(List<EquipoTransporte> listEquipoTransporte) {
		List<EquipoTransporte> listEquipoTransporteSalida = new ArrayList<EquipoTransporte>();

		if (listEquipoTransporte != null) {
			int index = 0;
			for (EquipoTransporte equipoTransporte : listEquipoTransporte) {
				EquipoTransporte equipoTransporteSalida = new EquipoTransporte();
				equipoTransporteSalida.setErrorFormGeneral(equipoTransporte.getErrorFormGeneral());
				equipoTransporteSalida.setId(equipoTransporte.getId());
				equipoTransporteSalida.setNumUnidades(equipoTransporte.getNumUnidades());
				equipoTransporteSalida.setSujetoObligado(equipoTransporte.getSujetoObligado());
				equipoTransporteSalida.setTipoCombustible(equipoTransporte.getTipoCombustible());

				equipoTransporteSalida.setDesNombre(generarFormatoHtmlSalida(
						equipoTransporte.getDesNombre()));
				equipoTransporteSalida.setDesCapacidadPotencia(generarFormatoHtmlSalida(
						equipoTransporte.getDesCapacidadPotencia()));
				equipoTransporteSalida.setDesUso(generarFormatoHtmlSalida(
						equipoTransporte.getDesUso()));

				if (equipoTransporte.getIdVista() != null) {
					equipoTransporteSalida.setIdVista(equipoTransporte.getIdVista());
				} else {
					equipoTransporteSalida.setIdVista(Long.valueOf(index + 1));
				}

				listEquipoTransporteSalida.add(equipoTransporteSalida);
				index++;
			}
		}

		return listEquipoTransporteSalida;
	}

	@RequestMapping(value = "/agregarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarEquipoTransporte(
			@RequestBody EquipoTransporte inputObject,
			HttpServletResponse response, HttpSession session) {
		log.info("AGREGAR EQUIPO DE TRANSPORTE [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					agregarItemLista(sujetoTramite.getEquiposTransporte(),
							inputObject);
					log.debug("SUJETO TRAMITE AGREGAR EQUIPO TRANSPORTE["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				e.printStackTrace();
				String message = "";
				message="Ocurrio un error al intentar agregar el equipo de transporte.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/modificarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarEquipoTransporte(
			@RequestBody EquipoTransporte inputObject,
			HttpServletResponse response, HttpSession session) {
		log.info("MODIFICAR EQUIPO DE TRANSPORTE [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (EquipoTransporte equipoTransporte : sujetoTramite.getEquiposTransporte()) {
			if (equipoTransporte.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				equipoTransporte.setSujetoObligado(sujetoObligado);
			}
		}

		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					modificarItemLista(sujetoTramite.getEquiposTransporte(),
							inputObject);
					log.debug("SUJETO TRAMITE MODIFICAR EQUIPO TRANSPORTE["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar actualizar el equipo de transporte.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/eliminarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarEquipoTransporte(
			@RequestBody EquipoTransporte inputObject,
			HttpServletResponse response, HttpSession session) {
		log.info("ELIMINAR EQUIPO DE TRANSPORTE [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (EquipoTransporte equipoTransporte : sujetoTramite.getEquiposTransporte()) {
			if (equipoTransporte.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				equipoTransporte.setSujetoObligado(sujetoObligado);
			}
		}
		
		if (inputObject != null) {
			if (inputObject.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				inputObject.setSujetoObligado(sujetoObligado);
			}
			
			try {
				eliminarItemLista(sujetoTramite.getEquiposTransporte(),
						inputObject);
				log.debug("SUJETO TRAMITE ELIMINAR EQUIPO TRANSPORTE["+sujetoTramite+"]");
				result.put("mensajeExito", "");
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar eliminar el equipo de transporte.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Personal> paginarPersonal(
			@RequestBody PersonalDataTable params, HttpSession session) {
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		DatosSalidaPaginador<Personal> output = new DatosSalidaPaginador<Personal>();
		DatosEntradaPaginador<Personal> input = new DatosEntradaPaginador<Personal>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(getPersonalSalida(sujetoTramite.getPersonal()));
		} catch (Exception e) {
			output = new DatosSalidaPaginador<Personal>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output;
	}

	private List<Personal> getPersonalSalida(List<Personal> listPersonal) {
		List<Personal> listPersonalSalida = new ArrayList<Personal>();

		if (listPersonal != null) {
			int index = 0;
			for (Personal personal : listPersonal) {
				Personal personalSalida = new Personal();
				personalSalida.setClave(personal.getClave());
				personalSalida.setErrorFormGeneral(personal.getErrorFormGeneral());
				personalSalida.setNumTrabajadores(personal.getNumTrabajadores());
				personalSalida.setSujetoObligado(personal.getSujetoObligado());

				personalSalida.setOficioOcupacion(generarFormatoHtmlSalida(
						personal.getOficioOcupacion()));

				if (personal.getIdVista() != null) {
					personalSalida.setIdVista(personal.getIdVista());
				} else {
					personalSalida.setIdVista(Long.valueOf(index + 1));
				}

				listPersonalSalida.add(personalSalida);
				index++;
			}
		}

		return listPersonalSalida;
	}

	@RequestMapping(value = "/agregarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarPersonal(
			@RequestBody Personal inputObject, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					agregarItemLista(sujetoTramite.getPersonal(), inputObject);
					log.debug("SUJETO TRAMITE AGREGAR PERSONAL["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar agregar el personal.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/modificarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarPersonal(
			@RequestBody Personal inputObject, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (Personal personal : sujetoTramite.getPersonal()) {
			if (personal.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				personal.setSujetoObligado(sujetoObligado);
			}
		}
		
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					modificarItemLista(sujetoTramite.getPersonal(), inputObject);
					log.debug("SUJETO TRAMITE MODIFICAR PERSONAL["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar actualizar el personal.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/eliminarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarPersonal(
			@RequestBody Personal inputObject, HttpServletResponse response,
			HttpSession session) {
		log.info("ELIMINAR PERSONAL [ " + inputObject + " ]");
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (Personal personal : sujetoTramite.getPersonal()) {
			if (personal.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				personal.setSujetoObligado(sujetoObligado);
			}
		}
		
		if (inputObject != null) {
			if (inputObject.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				inputObject.setSujetoObligado(sujetoObligado);
			}
			
			try {
				eliminarItemLista(sujetoTramite.getPersonal(), inputObject);
				log.debug("SUJETO TRAMITE ELIMINAR PERSONAL["+sujetoTramite+"]");
				result.put("mensajeExito", "");
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar eliminar el personal.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginarBienes", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Bien> paginarBienes(@RequestBody BienDataTable params,
			HttpSession session) {
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		DatosSalidaPaginador<Bien> output = new DatosSalidaPaginador<Bien>();
		DatosEntradaPaginador<Bien> input = new DatosEntradaPaginador<Bien>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(getBienSalida(sujetoTramite.getBienes()));
		} catch (Exception e) {
			output = new DatosSalidaPaginador<Bien>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output;
	}

	private List<Bien> getBienSalida(List<Bien> listBien) {
		List<Bien> listBienSalida = new ArrayList<Bien>();

		if (listBien != null) {
			int index = 0;
			for (Bien bien : listBien) {
				Bien bienSalida = new Bien();
				bienSalida.setId(bien.getId());
				bienSalida.setErrorFormGeneral(bien.getErrorFormGeneral());
				bienSalida.setNumCantidad(bien.getNumCantidad());
				bienSalida.setSujetoObligado(bien.getSujetoObligado());

				bienSalida.setDesBienes(generarFormatoHtmlSalida(
						bien.getDesBienes()));

				if (bien.getIdVista() != null) {
					bienSalida.setIdVista(bien.getIdVista());
				} else {
					bienSalida.setIdVista(Long.valueOf(index + 1));
				}

				listBienSalida.add(bienSalida);
				index++;
			}
		}

		return listBienSalida;
	}

	@RequestMapping(value = "/agregarBien", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarBien(@RequestBody Bien inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					agregarItemLista(sujetoTramite.getBienes(), inputObject);
					log.debug("SUJETO TRAMITE AGREGAR BIEN["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar agregar el bienes muebles/inmueble.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/modificarBien", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarBien(@RequestBody Bien inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (Bien bien : sujetoTramite.getBienes()) {
			if (bien.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				bien.setSujetoObligado(sujetoObligado);
			}
		}
		
		if (inputObject != null) {
			try {
				if (esValido(inputObject, result, response)) {
					modificarItemLista(sujetoTramite.getBienes(), inputObject);
					log.debug("SUJETO TRAMITE MODIFICAR BIEN["+sujetoTramite+"]");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar actualizar el bienes muebles/inmueble.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/eliminarBien", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarBien(@RequestBody Bien inputObject,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		for (Bien bien : sujetoTramite.getBienes()) {
			if (bien.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				bien.setSujetoObligado(sujetoObligado);
			}
		}
		
		if (inputObject != null) {
			if (inputObject.getSujetoObligado() == null) {
				SujetoObligado sujetoObligado = new SujetoObligado();
				sujetoObligado.setCveIdSujetoObligado(sujetoTramite
						.getCveIdSujetoObligado());
				inputObject.setSujetoObligado(sujetoObligado);
			}
			
			try {
				eliminarItemLista(sujetoTramite.getBienes(), inputObject);
				log.debug("SUJETO TRAMITE ELIMINAR BIEN["+sujetoTramite+"]");
				result.put("mensajeExito", "");
			}catch(Exception e){
				String message = "";
				message="Ocurrio un error al intentar eliminar el bienes muebles/inmueble.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/cargarComboTipoMaquinaria", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cargarComboTipoMaquinaria(
			HttpServletResponse response) {
		log.info("EN EL METODO cargarComboTipoMaquinaria");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<TipoMaquinariaEquipo> catalogo = maquinariaEquipoService
					.obtenerTiposMaquinaria();
			result.put("catalogo", catalogo);
		}catch(Exception e){
			String message = "";
			message="Ocurrio un error al obtener los datos del cat&aactue;logo< Tipo de Maquinaria.";
			result.put("mensajeError", message);
		}
		return result;
	}

	@RequestMapping(value = "/cargarComboTipoCombustible", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cargarComboTipoCombustible(
			HttpServletResponse response) {
		log.info("EN EL METODO cargarComboTipoCombustible");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<TipoCombustible> catalogo = transporteService
					.obtenerTiposDeCombustible();
			result.put("catalogo", catalogo);
		}catch(Exception e){
			String message = "";
			message="Ocurrio un error al obtener los datos del cat&aactue;logo Tipo de Combustible.";
			result.put("mensajeError", message);
		}
		return result;
	}

	public boolean esValido(AbstractModel inputObject,
			Map<String, Object> result, HttpServletResponse response) {
		log.debug("ClasificacionController.esValido(" + inputObject + ")");
		Errors errors = new BindException(inputObject, "model");
		if (inputObject instanceof Producto) {
			ProductoValidator.getInstance().validate(inputObject, errors);
		} else if (inputObject instanceof MateriaPrima) {
			MateriaPrimaValidator.getInstance().validate(inputObject, errors);
		} else if (inputObject instanceof MaquinariaEquipo) {
			MaquinariaEquipoValidator.getInstance().validate(inputObject,
					errors);
		} else if (inputObject instanceof EquipoTransporte) {
			EquipoTransporteValidator.getInstance().validate(inputObject,
					errors);
		} else if (inputObject instanceof Personal) {
			PersonalValidator.getInstance().validate(inputObject, errors);
		} else if (inputObject instanceof Bien) {
			BienValidator.getInstance().validate(inputObject, errors);
		}

		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
		}

		return !errors.hasErrors();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void agregarItemLista(List lista, ItemClasificacion inputObject) {
		inputObject.setIdVista(new Long(lista.size() + 1));
		lista.add(inputObject);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void modificarItemLista(List lista, ItemClasificacion inputObject) {
		if (lista.contains(inputObject)) {
			lista.remove(inputObject);
			lista.add(inputObject);
		}
	}

	@SuppressWarnings("rawtypes")
	public void eliminarItemLista(List lista, ItemClasificacion inputObject) {
		if (lista.contains(inputObject)) {
			lista.remove(inputObject);
		}
	}

	private SujetoObligado inicializaSujetoObligadoPresentacion(
			SujetoObligado so) {
		if (so.getBienes() == null) {
			so.setBienes(new ArrayList<Bien>());
		}
		if (so.getEquipos() == null) {
			so.setEquipos(new ArrayList<MaquinariaEquipo>());
		}
		if (so.getEquiposTransporte() == null) {
			so.setEquiposTransporte(new ArrayList<EquipoTransporte>());
		}
		if (so.getMateriaPrimaMateriales() == null) {
			so.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
		}
		if (so.getPersonal() == null) {
			so.setPersonal(new ArrayList<Personal>());
		}
		if (so.getProductos() == null) {
			so.setProductos(new ArrayList<Producto>());
		}
		if(so.getCntroTrabajo() == null){
			so.setCntroTrabajo(crearCentroTrabajoVacio());
		}
		return so;
	}
	
	private CentroTrabajo crearCentroTrabajoVacio(){
		CentroTrabajo ct=new CentroTrabajo();
		ct.setAsentamiento(crearAsentamientoVacio());
		ct.setCodigoPostal(new CodigoPostal());
		ct.setVialidadPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaSecundaria(crearVialidadVacia());
		ct.setVialidadReferenciaPosterior(crearVialidadVacia());
		return ct;
	}
	
	private Asentamiento crearAsentamientoVacio(){
		Asentamiento asent = new Asentamiento();
		asent.setLocalidad(new Localidad());
		asent.getLocalidad().setMunicipio(new Municipio());
		asent.getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		return asent;
	}
	
	private Vialidad crearVialidadVacia(){
		Vialidad vialidad = new Vialidad();
		vialidad.setTipoVialidad(new TipoVialidad());
		return vialidad;
	}
	
	private String tipoTramite(Integer codigo){
		TipoTramiteEnum tipo = TipoTramiteEnum.obternerEnumById(codigo);
		switch(tipo){
			case ACTIVIDAD_ECONOMICA:
					return "D";
			case DISPOSICION_DE_LEY:
					return "E";
			case INCORPORACION_DE_ACTIVIDADES:
					return "F";
			case COMPRA_DE_ACTIVOS:
					return "J";
			case COMODATO:
					return "K";
			case ENAJENACION:
					return "L";
			case ARRENDAMIENTO:
					return "M";
			case FIDEICOMISO_TRASLATIVO:
					return "N";
			case ACTUALIZACION_CENTRO_TRABAJO:
				return "CT";		
			case CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO:
				return "ACD";
			default:
				return "D";
		}
	}
	
	
	@RequestMapping(value = "/mostrarAcuse", method = {RequestMethod.POST})
	public void mostrarAcuse(HttpServletResponse response, HttpSession session){
		
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		log.debug(">>>>>*$$$$$ mostrarAcuse() -  idSolicitud:.."+idSolicitud);
		
//		Usuario usuario = (Usuario) session.getAttribute("usuario");
	
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
		log.debug("En mostrarAcuse EdoSolicitud ============> " + solicitud.getEstadoSolicitud());
		
		solicitud = solicitudServiceBusiness.publicarDocumentosDeSolicitud(solicitud);

		log.debug(">>>>>*$$$$$ mostrarAcuse() -  solicitud:.."+solicitud);
		
		String nombreArchivo = "MC_Acuse_"+solicitud.getNoFolioSolicitud()+".pdf";
		byte[] reporte = solicitud.getDocumentoAcuse(); 
		log.debug("En mostrarAcuse DocumentoAcuse ============> " + solicitud.getDocumentoAcuse());
		try {
			
			if(reporte != null){
				
				log.debug(">>>>>*$$$$$ EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR EN PANTALLA");
				
				log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR y almacenar en la base");
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("application/pdf");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=" + nombreArchivo); 
				response.getOutputStream().write(reporte);
				response.getOutputStream().close();
				
			}else{
				log.debug(" >>>>>*$$$$$ EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
			}
			
		
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {  
	        try { 
	           if (response.getOutputStream() != null) 
	        	   response.getOutputStream().close(); 
	        } catch (IOException ioe) {  
	                ioe.printStackTrace(); 
	        }
			//session.removeAttribute("idSolicitud");
		}
		
	}
	
	@RequestMapping(value = "/mostrarAviso", method = {RequestMethod.POST})
	public void mostrarAviso(HttpServletResponse response, HttpSession session){
		
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
//		Usuario usuario = (Usuario) session.getAttribute("usuario");
		log.debug("idSolicitud enviada ===========> " + idSolicitud);
	
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
		solicitud = solicitudServiceBusiness.publicarDocumentosDeSolicitud(solicitud);
		
		log.debug("Reporte ==========================>" + solicitud.getDocumentoComprobante());

		String nombreArchivo = "MC_Aviso_"+solicitud.getNoFolioSolicitud()+".pdf";
		byte[] reporte = solicitud.getDocumentoComprobante(); 
		
		try {
			
			if(reporte != null){
				
				log.debug(">>>>>*$$$$$ EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR EN PANTALLA");
				
				log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR y almacenar en la base");
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("application/pdf");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=" + nombreArchivo); 
				response.getOutputStream().write(reporte);
				response.getOutputStream().close();
				
			}else{
				log.debug(" >>>>>*$$$$$ EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
			}
			
		
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {  
	        try { 
	           if (response.getOutputStream() != null) 
	        	   response.getOutputStream().close(); 
	        } catch (IOException ioe) {  
	                ioe.printStackTrace(); 
	        }
			session.removeAttribute("idSolicitud");
		}
		
	}
	
	
	@RequestMapping(value = "/mostrarConfirmacion", method = {RequestMethod.POST})
	public String mostrarConfirmacion(@RequestParam("fwd") String fwd, 
			@RequestParam("rfc") String rfc, 
			HttpServletResponse response, 
			Model model, HttpSession session){
		Socio socio = new Socio();
		socio.setRfc(rfc);
		model.addAttribute("socio", socio);
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		return fwd;
	}
	
	@RequestMapping(value = "/presentarAcuse", method = {RequestMethod.GET})
	public String presentarAcuseEmergente(HttpServletResponse response,
			Model model, HttpSession session) {
		return presentarAcuse(response, model, session);
	}

	@RequestMapping(value = "/presentarAcuse", method = {RequestMethod.POST})
	public String presentarAcuse(HttpServletResponse response, Model model, HttpSession session){
		try {
			return generarAcuse(response, model,session, false);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String generarAcuse(HttpServletResponse response, Model model, HttpSession session, boolean fueFirmada) throws GestionPatronalBusinessException{
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		
		SujetoObligado sujeto = (SujetoObligado) session.getAttribute("sujetoActual");
		
		//sujeto=sujetoObligadoService.
		Map<String, Object> parametros = new HashMap<String, Object>();
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		System.err.println("Subiendo model usuario :" +usuario);
		model.addAttribute("usuario", usuario);
		Boolean aviso =  
				usuario.getPerfilUsuario().getIdPerfilUsuario().intValue() == CodigoRolTemporal.TRAMITADOR.getCodigo().intValue()
				|| (usuario.getPerfilUsuario().getIdPerfilUsuario().intValue() == CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().intValue() 
					&&  fueFirmada) 
				|| (usuario.getPerfilUsuario().getIdPerfilUsuario().intValue() == CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().intValue() 
						&&  fueFirmada) ;
		
		log.debug("Aviso ==========>" + aviso);
		
		Tramite tramite = solicitud.getTramites().get(0);
		String rfcPatron = "";
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = " "+formatter.format(solicitud.getFechaSolicitud())+" ";
		parametros.put("P_FECSOLCITUD", fecha);
		parametros.put("P_MOSTRAR_LEY", "1");
		log.debug("P_FECSOLCITUD "+fecha);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		log.debug("P_FOLIO_SOLCT "+solicitud.getNoFolioSolicitud());
		parametros.put("P_CVE_TRAMITE", tipoTramite(tramite.getTipoTramite().getIdTipoTramite()));
		log.debug("P_CVE_TRAMITE D");
		parametros.put("P_DESCTRAMITE", tramite.getTipoTramite().getDescripcion());
		log.debug("P_DESCTRAMITE "+tramite.getTipoTramite().getDescripcion());
		parametros.put("IS_FISICA", false);
		parametros.put("IS_MORAL", false);
		
		
		String tipoModificacion = tramite.getTipoTramite().getDescripcion();
		String numRP = "";
		if(sujeto.getNumeroRegistroPatronal().length() == 8 && sujeto.getModalidad()!= null && sujeto.getDigVerificador()!=null){
			numRP = sujeto.getNumeroRegistroPatronal()
			+sujeto.getModalidad().getNumModalidad()
			+sujeto.getDigVerificador();
		}else{
			numRP = sujeto.getNumeroRegistroPatronal();
		}
		parametros.put("P_REGPATRONAL", numRP);
		log.debug("P_REGPATRONAL "+numRP);
		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			parametros.put("IS_FISICA", true);
			Fisica fisica = sujeto.getFisica();
			String nombreCompleto = ""; 
			nombreCompleto = obtenerNombreCompletoPersonaFisica(fisica);
//			nombreCompleto += ""+fisica.getPrimerApellido()!=null ? fisica.getPrimerApellido() : "" ;
//			nombreCompleto += " "+fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "" ;
//			nombreCompleto += ", "+fisica.getNombre()!=null ? fisica.getNombre() : "" ;
			parametros.put("P_RAZONSOCIAL", " ");
			log.debug("P_RAZONSOCIAL ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			log.debug("P_NOMBRE_PERS "+nombreCompleto);
			if(aviso){
				parametros.put("P_NOMBRE_PERS", fisica.getNombre()!=null ? fisica.getNombre() : "");
				log.debug("P_NOMBRE_PERS "+fisica.getNombre());
				parametros.put("P_PATERNO_PER", fisica.getPrimerApellido()!=null ? fisica.getPrimerApellido() : "");
				log.debug("P_PATERNO_PER "+fisica.getPrimerApellido());
				parametros.put("P_MATERNO_PER", fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "");
				log.debug("P_MATERNO_PER "+fisica.getSegundoApellido());
			}
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			log.debug("P_RFC_PERSONA "+fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
			log.debug("P_CURPPERSONA "+fisica.getCurp());
			parametros.put("P_NOMB_COMERC", sujeto.getClasificacion().getNombreQuienPresenta()); 
			log.debug("P_NOMB_COMERC "+sujeto.getClasificacion().getNombreQuienPresenta());
			
			rfcPatron = fisica.getRfc();
		}else{
			parametros.put("IS_MORAL", true);
			Moral moral = sujeto.getMoral();
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			log.debug("P_RAZONSOCIAL "+moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			log.debug("P_NOMBRE_PERS ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
			log.debug("P_RFC_PERSONA "+moral.getRfc());
			parametros.put("P_NOMB_COMERC", sujeto.getClasificacion().getNombreQuienPresenta());
			log.debug("P_NOMB_COMERC "+sujeto.getClasificacion().getNombreQuienPresenta());
			
			rfcPatron = moral.getRfc();
		}
		
		Socio socio = new Socio();
		socio.setRfc(rfcPatron);
		model.addAttribute("socio",socio);
		
		Subdelegacion subdelegacion = sujeto.getSubdelegacion();
		
		if(subdelegacion != null){
			parametros.put("P_CVE_DELEGAC", subdelegacion.getDelegacion().getClave());
			log.debug("P_CVE_DELEGAC "+subdelegacion.getDelegacion().getClave());
			parametros.put("P_DES_DELEGAC", subdelegacion.getDelegacion().getDescripcion());
			log.debug("P_DES_DELEGAC "+subdelegacion.getDelegacion().getDescripcion());
			parametros.put("P_CVE_SUB_DEL", subdelegacion.getClave());
			log.debug("P_CVE_SUB_DEL "+subdelegacion.getClave());
			parametros.put("P_DES_SUB_DEL", subdelegacion.getDescripcion());
			log.debug("P_DES_SUB_DEL "+subdelegacion.getDescripcion());
		}else{
			parametros.put("P_CVE_DELEGAC", "");
			log.debug("P_CVE_DELEGAC");
			parametros.put("P_DES_DELEGAC", "");
			log.debug("P_DES_DELEGAC");
			parametros.put("P_CVE_SUB_DEL", "");
			log.debug("P_CVE_SUB_DEL");
			parametros.put("P_DES_SUB_DEL", "");
			log.debug("P_DES_SUB_DEL");
		}
		TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
		Clasificacion clasificacion = tso.getSujetoObligado().getClasificacion();
		
		if(clasificacion.getFecPresentacion() == null){
			clasificacion.setFecPresentacion(Calendar.getInstance().getTime());
		}
		
		
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()) 
				|| tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())){
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(clasificacion.getFecPresentacion())+" " : " ";
			log.debug("Se agregan parametros de centro de trabajo: " + tso.getSujetoObligado().getCntroTrabajo());
			CentroTrabajo cTrabajo = tso.getSujetoObligado().getCntroTrabajo();
			parametros.put("CT_CALLE", (cTrabajo.getVialidadPrimaria() != null) ? cTrabajo.getVialidadPrimaria().getNombre(): "");
			
			String numeroExt = cTrabajo.getNumExterior1()!=null && StringUtils.isNotBlank(cTrabajo.getNumExterior1().toString()) ? cTrabajo.getNumExterior1().toString() :"";
			numeroExt +=  cTrabajo.getNumExteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumExteriorAlf()) ? cTrabajo.getNumExteriorAlf() : "";
			
			parametros.put("CT_NUM_EXT", numeroExt);
			
			String numeroInt = cTrabajo.getNumInterior()!=null && StringUtils.isNotBlank(cTrabajo.getNumInterior().toString()) ? cTrabajo.getNumInterior().toString() :"";
			numeroInt +=  cTrabajo.getNumInteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumInteriorAlf()) ? cTrabajo.getNumInteriorAlf() : "";
			parametros.put("CT_NUM_INT", numeroInt);
			
			String entreCalleA= cTrabajo.getVialidadReferenciaPrimaria()!=null 
					? cTrabajo.getVialidadReferenciaPrimaria().getNombre() : "";
			String entreCalleB = cTrabajo.getVialidadReferenciaSecundaria()!=null 
					? cTrabajo.getVialidadReferenciaSecundaria().getNombre() : "";
					
			parametros.put("CT_ENTRE_CALLE_A", entreCalleA);
			parametros.put("CT_ENTRE_CALLE_B", entreCalleB);
			
			String ctColonia = cTrabajo.getAsentamiento() != null ? cTrabajo.getAsentamiento().getNombre() : "";
			String ctLocalidad = cTrabajo.getAsentamiento() != null ? cTrabajo.getAsentamiento().getLocalidad().getNombre() : "";
			String ctDelgacion = cTrabajo.getAsentamiento() != null ? cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre(): "";
			String ctEntidad = cTrabajo.getAsentamiento() != null ? cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre() : "";
			
			parametros.put("CT_COLONIA", ctColonia);
			parametros.put("CT_LOCALIDAD", ctLocalidad);
			parametros.put("CT_DELEGACION", ctDelgacion);
			parametros.put("CT_ENTIDAD", ctEntidad);
			
			String ctCodigoPostal = cTrabajo.getCodigoPostal() != null ? cTrabajo.getCodigoPostal().getCodigoPostal() : "";
			parametros.put("CT_CP", ctCodigoPostal);
			
			String telefonoFijo="";
			String telefonoFijo2="";
			String correoElectronico="";
			if(cTrabajo.getMediosContacto()!=null)
				for(MedioContacto medio : cTrabajo.getMediosContacto()){
					if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
						if(medio.getIdVista()==1)
							telefonoFijo = medio.getDesFormaContacto();
						if(medio.getIdVista()==2)
							telefonoFijo2 = medio.getDesFormaContacto();
					}else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
						correoElectronico = medio.getDesFormaContacto();
					}
				}
			log.debug("CT_TEL_FIJO_A: "+telefonoFijo);
			log.debug("CT_TEL_FIJO_B: "+telefonoFijo2);
			log.debug("CT_CORREO: "+correoElectronico);
			parametros.put("CT_TEL_FIJO_A", telefonoFijo);
			parametros.put("CT_TEL_FIJO_B", telefonoFijo2);
			parametros.put("CT_CORREO", correoElectronico);
		}else{ 
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(tso.getFechaPresentacion())+" " : " ";
		}
		
		parametros.put("P_FECPRESENTA", fecha);
		log.debug("P_FECPRESENTA "+fecha);
		
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()))
			fecha = clasificacion.getFecEfecto()!=null ? " "+formatter.format(clasificacion.getFecEfecto())+" " : " ";
		else
			fecha = clasificacion.getFecEfecto()!=null ? " "+formatter.format(tso.getFechaEfecto())+" " : " ";
		
		parametros.put("P_FECH_EFECTO", fecha);
		log.debug("P_FECH_EFECTO " + fecha);
		StringBuffer nombreUsuarioCompleto = new StringBuffer();
		nombreUsuarioCompleto.append(" ");
		if(usuario.getNomNombre()!=null){
			nombreUsuarioCompleto.append(usuario.getNomNombre());
			if(usuario.getNomPaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomPaterno());
			if(usuario.getNomMaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomMaterno());
		}
//		String tipoTramite = obtenerNombreModificacion(solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite());
			String nombreArchivo = "";
			
			if	(solicitud.getSolicitante() != null) {
				String curp = solicitud.getSolicitante().getUsuario();
				log.debug("CURP CONSULTA:: " + curp);
				log.debug("P_USUARIO "+clasificacionService.consultarMatricula(curp));
				parametros.put("P_USUARIO", clasificacionService.consultarMatricula(curp));
			} 
			else {
				log.debug("EL USUARIO NO TRAE CURP NO SE PUEDE CONSULTAR MATRICULA");
				parametros.put("P_USUARIO", "---");
			}
			
			if(aviso){
				Fraccion fraccAux = clasificacionServiceBusiness.obtenerFraccionPorIdentificador(clasificacion.getFraccion().getId());
				clasificacion.setFraccion(fraccAux);
				Fraccion fracc = clasificacion.getFraccion();
				//parametros.put("P_FRACCION", clasificacion.getFraccion().getDescripcion());
				//log.debug("P_FRACCION" +clasificacion.getFraccion().getDescripcion());
				parametros.put("P_NUM_FRACCIO", fracc.getNumFraccion());
				log.debug("P_NUM_FRACCIO "+fracc.getNumFraccion());
				Grupo grupo = fracc.getGrupo();
				parametros.put("P_NUMER_GRUPO", grupo.getNumGrupo());
				log.debug("P_NUMER_GRUPO "+grupo.getNumGrupo());
				Division division = grupo.getDivision();
				parametros.put("P_NUM_DIVISIO", division.getNumDivision());
				log.debug("P_NUM_DIVISIO "+division.getNumDivision());
				parametros.put("P_CLASE_CLASI", fracc.getClase().getDescripcion());
				log.debug("P_CLASE_CLASI "+fracc.getClase().getDescripcion());
				

				if (clasificacion.getPrimaSRTSugerida() != null) {
					parametros.put("P_PRIMA_CLASI", clasificacion.getPrimaSRTSugerida());	
					sujeto.getClasificacion().setPrimaSRTActual(clasificacion.getPrimaSRTSugerida());			
				} 
				else {
					parametros.put("P_PRIMA_CLASI", clasificacion.getPrimaSRTActual());	
				}
				
				log.debug("P_TIPO_MODIFICACION "+nombreUsuarioCompleto.toString());
				parametros.put("P_TIPO_MODIFICACION", tipoModificacion);
				sujeto.getClasificacion().setFraccion(fracc);
				
				nombreArchivo = "AvisoModificacion_"+solicitud.getNoFolioSolicitud()+".pdf";
				log.debug("NOMBRE REPORTE: "+nombreArchivo);
				sujeto.setNumeroRegistroPatronal(sujeto.getNumeroRegistroPatronal()
						+sujeto.getModalidad().getNumModalidad()+sujeto.getDigVerificador());
				//sujeto=sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
				System.err.println("Finalizo la consulta de sujetoObligadoActividadEconomica: "+sujeto);
				List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();
				sujetos.add(sujeto);
				
				System.err.println("FUE FIRMADA: "+fueFirmada);
				log.debug("Cambio no entra a los datos de la firma  =========================>" );
				if(fueFirmada){
					FirmaElectronica datosFirma = (FirmaElectronica)session.getAttribute("datosFirma");
					parametros.put("P_CAD_ORIG", datosFirma.getCadenaOriginal());
					parametros.put("P_SELLO", datosFirma.getsPKCS7());
					parametros.put("P_SEC_NOT", datosFirma.getSecuenciaNotaria());
					System.err.println("Se agregaron datos de firma a reporte");
					System.err.println("P_CAD_ORIG: "+datosFirma.getCadenaOriginal());
					System.err.println("P_SELLO: "+datosFirma.getsPKCS7());
					System.err.println("P_SEC_NOT: "+datosFirma.getSecuenciaNotaria());
				}
				byte[] reporteRev = manejadorReportes.ejecutaAvisoDeModificacionMovPat(parametros, sujetos);
				log.debug("reporteRev =========================>" + reporteRev );
				
				try {
					
					if(reporteRev != null){
						
						log.debug(">>>>>*$$$$$ EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR EN PANTALLA");
						
						log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR y almacenar en la base");
						response.addHeader("Accept-Ranges","bytes");
						response.addHeader("Cache-Control","public");
						response.addHeader("Cache-Control","must-revalidate");
						response.addHeader("Pragma","public");
						response.setContentType("application/pdf");
						response.addHeader("expires","0");
						response.addHeader("Content-disposition", "inline;filename=" + nombreArchivo); 
						response.getOutputStream().write(reporteRev);
						response.getOutputStream().close();
						
					}else{
						log.debug(" >>>>>*$$$$$ EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
					}
					
				
				} catch (IOException e) {
					e.printStackTrace();
					
				} finally {  
			        try { 
			           if (response.getOutputStream() != null) 
			        	   response.getOutputStream().close(); 
			        } catch (IOException ioe) {  
			                ioe.printStackTrace(); 
			        }
					//session.removeAttribute("idSolicitud");
				}

				//manejadorReportes.ejecutaAcuse(parametros);
				//session.removeAttribute("sujetoObligado");
				//session.removeAttribute("sujetoTramite");
				//session.removeAttribute("tipoTramite");
				model.addAttribute("usuario",usuario);
				return "movPat.modificacion.aviso";
				
			}else{
				nombreArchivo = "Acuse_"+solicitud.getNoFolioSolicitud()+".pdf";
				log.debug("NOMBRE REPORTE: "+nombreArchivo);
				
				
				manejadorReportes.ejecutaAcuse(parametros);
				
				
				log.debug("ENVIANDO CORREO ELECTRONICO: "+nombreArchivo);
				afiliacionService.notificarPorCorreoElectronico(sujeto, idSolicitud, TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue());
				log.debug("SE FINALIZO EL ENVIO DE CORREO ELECTRONICO: "+nombreArchivo);
				
//				session.removeAttribute("sujetoObligado");
//				session.removeAttribute("sujetoTramite");
//				session.removeAttribute("tipoTramite");

				return "movPat.modificacion.acuse";
			}
	}
	
	
	private String obtenerNombreCompletoPersonaFisica(Fisica fisica){

		StringBuffer nombreCompleto = new StringBuffer();
		if(fisica.getPrimerApellido()!=null && fisica.getPrimerApellido()!="null"){
			nombreCompleto.append(fisica.getPrimerApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getSegundoApellido()!=null && fisica.getSegundoApellido()!="null"){
			nombreCompleto.append(fisica.getSegundoApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getNombre()!=null && fisica.getNombre()!="null")
			nombreCompleto.append(fisica.getNombre());
		
		return nombreCompleto.toString();
	}
	
	@RequestMapping(value="/presentarAcusePatronal", method = {RequestMethod.POST})
	public void presentarAcuseDatosPatronales(HttpServletResponse response, HttpSession session, @RequestParam("idSolicitud") String idSolicitud){
//		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		log.info("/**** ID SOLICITUD :: "+Long.parseLong(idSolicitud.split(",")[0]));
		Solicitud solicitud = solicitudServiceBusiness.consultarDetalleSolicitudPorIdentificador(Long.parseLong(idSolicitud.split(",")[0]));
		
		String nombreArchivo = "AcuseModificacionPatronal.pdf";
		
		if(solicitud.getDocumentoAcuse() != null){			
			try {
				log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR");
				response.setContentType("application/pdf"); 
				response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo); 
				response.getOutputStream().write(solicitud.getDocumentoAcuse());
				response.getOutputStream().close();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}else{
			log.debug("EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
		}
	}
	
	@RequestMapping(value = "/evaluarReglaRPC", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> evaluarReglaRPC(
			@RequestBody Clasificacion inputObject,
			@RequestParam("indReintento") boolean reintentoRpc,
			@RequestParam("indRPCInvalido") boolean rpcInvalido,
			HttpServletResponse response, Locale locale) {
		log.info("EN EL METODO validarpc");
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "";
		try{
			String rfc = inputObject.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) ? 
					inputObject.getSujetoObligado().getFisica().getRfc() : inputObject.getSujetoObligado().getMoral().getRfc();
			String numeroRegistroPatronal = inputObject.getSujetoObligado().getNumeroRegistroPatronal()
					+inputObject.getSujetoObligado().getModalidad().getNumModalidad()
					+inputObject.getSujetoObligado().getDigVerificador();
					ruleServiceBusiness.validarRPC_AP_MOD_MAC(rfc, 
							inputObject.getFraccion().getClase().getClave(), 
							numeroRegistroPatronal);
		}catch (GestionPatronalBusinessException gpbe) {
			if(rpcInvalido)//Si hubo un rpc seleccionado previamente y que fue invalido se marca reintento
				reintentoRpc=true;
			else //Si no se habia seleccionado previamente un rpc invalido se marca como reintento
				rpcInvalido=true;
			
			message = messageSource.getMessage(gpbe.getMessage(),null ,locale);
			result.put("mensajeError", message);
			result.put("reintentoRpc", reintentoRpc);
			result.put("rpcInvalido", rpcInvalido);
			return result;
		}
		
		result.put("reintentoRpc", reintentoRpc);
		result.put("rpcInvalido", rpcInvalido);
		return result;
	}
	
	
	private void evaluarReglaRPC(Long idSolicitud, String rfc, SujetoObligado sujetoTramite) 
	throws GestionPatronalBusinessException{
		
		String numeroRPCompleto = sujetoTramite.getNumeroRegistroPatronal();
		
		if (numeroRPCompleto.length() == 8) {
			numeroRPCompleto = numeroRPCompleto + sujetoTramite.getModalidad().getNumModalidad()
					+ sujetoTramite.getDigVerificador();
		}
		
		boolean valido=ruleServiceBusiness.validarRPCPorModificacionSRT(idSolicitud, rfc, 
				sujetoTramite.getClasificacion().getFraccion().getClase().getClave(),
				numeroRPCompleto);
		if(!valido)
			throw new GestionPatronalBusinessException("error.business.clase.existente");
	}

	private String generarFormatoHtmlSalida(String mensaje) {
		mensaje = mensaje.replace("<", "&lt;");
		mensaje = mensaje.replace(">", "&gt;");
		mensaje = mensaje.replace(" ", "&nbsp;");
		mensaje = mensaje.replace("\n", "<br />");
		mensaje = mensaje.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");

		return mensaje;
	}
	
    @RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica inputObject,
			HttpServletResponse response, HttpSession session){
    	session.setAttribute("fueFirmada",true);
    	session.setAttribute("datosFirma", inputObject);
    	System.err.println("Se almacenan los datos de la firma digital de forma temporal");
    	return null;
    }
    
    
    @RequestMapping(value = "/validarSubdelegacionCentroTrabajo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarSubdelegacionCentroTrabajo(@RequestBody CentroTrabajo inputObject,
			@RequestParam("idSubdelegacionOrigen") Long idSubdelegacionOrigen,
			HttpServletResponse response, HttpSession session, Locale locale){
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	try{
    		afiliacionService.validarSubdelegacion(idSubdelegacionOrigen, inputObject);
    		//agregarDelegacionesDisponibles(result, inputObject, session);
    		result.put("error", false);
    	}catch (GestionPatronalBusinessException e) {
    		String mensaje = messageSource.getMessage(e.getMessage(), null, locale);
    		result.put("error", true);
    		result.put("mensajeError", mensaje);
		}
    	
    	return result;
    }
    
    
    @RequestMapping(value = "/validarClasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarClasificacion(@RequestBody Clasificacion inputObject,
			@RequestParam("idTipoTramite") Integer idTipoTramite,
			HttpServletResponse response, HttpSession session, Locale locale){
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
			validaFechaEfecto(inputObject.getFecEfecto(), idTipoTramite);
		} catch (GestionPatronalBusinessException e1) {
			result.put("error", true);
    		result.put("mensajeError", e1.getMessage());
    		return result;
		}
    	
    	
    	try {
    		
			validaReglasClasificacion(inputObject, idTipoTramite);
		} catch (GestionPatronalBusinessException e) {
			String message = "";
			if(e.getCodigo()!=null && ( e.getCodigo().equals(800)|| e.getCodigo().equals(801) ))
				message=e.getSituacion();
			else
				message=messageSource.getMessage(e.getMessage(), null, locale);
			
    		result.put("error", true);
    		result.put("mensajeError", message);
		}
    	return result;
    }
    
    private void validaFechaEfecto(Date fechaEfecto, Integer idTipoTramite) throws GestionPatronalBusinessException{
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		
		Date fechaActual = calendario.getTime();
		
		Calendar calendarioEfecto = Calendar.getInstance();
		calendarioEfecto.setTime(fechaEfecto);
		calendarioEfecto.set(Calendar.HOUR, 0);
		calendarioEfecto.set(Calendar.MINUTE, 0);
		calendarioEfecto.set(Calendar.SECOND, 0);
		calendarioEfecto.set(Calendar.MILLISECOND, 0);
		calendarioEfecto.set(Calendar.HOUR_OF_DAY, 0);
		
		Date fechaEfectoEvaluar=calendarioEfecto.getTime();
		
		Calendar calendarioAnioAnt = Calendar.getInstance();
		calendarioAnioAnt.setTime(fechaActual);
		calendarioAnioAnt.add(Calendar.DATE, -1823);
		calendarioAnioAnt.set(Calendar.HOUR, 0);
		calendarioAnioAnt.set(Calendar.MINUTE, 0);
		calendarioAnioAnt.set(Calendar.SECOND, 0);
		calendarioAnioAnt.set(Calendar.MILLISECOND, 0);
		calendarioAnioAnt.set(Calendar.HOUR_OF_DAY, 0);
		Date fechaAunAnioAnterior = calendarioAnioAnt.getTime();
		
		boolean evaluarFechaAnterior = true;
				
		if(idTipoTramite!=null && idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())){
			evaluarFechaAnterior = false;
		}
		
		
		if(fechaEfectoEvaluar.after(fechaActual)){
			String message = "La fecha de efecto no puede ser mayor a la fecha actual";
			throw new GestionPatronalBusinessException(message);
		}
		if(evaluarFechaAnterior ){
			if(fechaEfectoEvaluar.before(fechaAunAnioAnterior)){
				String message = "La fecha de efecto no puede ser menor a 365 d&iacute;as de la fecha actual";
				throw new GestionPatronalBusinessException(message);
			}
		}
		
	}
        
	@RequestMapping(value="/consulta/solicitud/similar" , method = RequestMethod.POST)
	@ResponseBody
	public String consultaSolicitudSimilar(@RequestParam("nrp") String nrp,
			@RequestParam("cveIdTipoTramite") String cveIdTipoTramite,
			@RequestParam("fechaSurteEfecto") String fechaSurteEfecto,
			HttpSession session) {
		String respuestaConsulta = "";
		try {
			log.debug("::: Consultando solicitudes similares, nrp: " + nrp + ", cveIdTipoTramite: " + cveIdTipoTramite
					+ ". fechaSurteEfecto: " + fechaSurteEfecto);
			respuestaConsulta = clasificacionServiceBusiness.buscaSolicitudesSimilares(nrp, cveIdTipoTramite, fechaSurteEfecto);
			log.debug("::: respuestaConsulta: " + respuestaConsulta);
		} catch (Exception e) {
			log.error("::: Ocurrio un error al consultar solicitudes similares: "
					+ e.getMessage());
			e.printStackTrace();
			respuestaConsulta = "error";
		}
		return respuestaConsulta;
	}
	
	private void setDatosFirmaElectronica(Solicitud solicitud, RespuestaFirmadoSimple firmadoSimple, String cadenaOriginal) {
		
		FirmaElectronica firmaElectronica = firmaDigitalBusiness.convertirRespuestaFirmadoSimple(cadenaOriginal, firmadoSimple);
		solicitud.setFirmadaDigitalmente(true);
		solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
		solicitud.setSecuenciaDeNotaria(firmaElectronica.getReciboNotarial());
		solicitud.setSelloDigital(firmaElectronica.getRecibo());
		solicitud.setUrlAcuseFirma(firmaElectronica.getUrlAcuseFirma());
		
		
	}
    
}
