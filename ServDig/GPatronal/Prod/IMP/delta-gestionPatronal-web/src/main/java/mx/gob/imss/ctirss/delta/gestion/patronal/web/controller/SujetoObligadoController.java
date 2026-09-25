/**
 * delta-gestionPatronal-web26/04/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.controller26/04/2012
 * PatronController.java
 * 26/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.PersonaDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.TramiteSolicitudDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FormaContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.tramite.service.interfaces.TramiteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.web.validator.DatosContactoValidator;
import mx.gob.imss.ctirss.delta.web.validator.EscrituraConstitutivaValidator;
import mx.gob.imss.ctirss.delta.web.validator.NombreComercialValidator;
import mx.gob.imss.ctirss.delta.web.validator.RFCValidator;
import mx.gob.imss.ctirss.delta.web.validator.RegistroSindicatoValidator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Hugo Armando Martínez Instituto Mexicano del Seguro Social
 */
@Controller
@RequestMapping(value = "/sujetoObligado")
public class SujetoObligadoController extends AbstractController {

	@Autowired
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	
	@Autowired
	TramiteServiceBusinessRemote tramiteService;
	
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@Autowired
	private ManejadorReportesRemote manejadorReportes;
	
	
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionService;
	
	@Autowired
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusiness;
	
	@Autowired
	private AfiliacionController afiliacionController;
	
	private String mensajeActualizacion="Su solicitud se ha enviado para ser procesada.<BR>" +
			"El número de folio de la solicitud es el siguiente[ varNumeroFolio ]" +
			"<BR> Por favor tome nota de su número de folio para su seguimiento";
	/**
	 * Metodo para obtener los productos de la solicitud.
	 * 
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String inicio(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		this.log.debug("Usuario: " + usuario);
		model.addAttribute("socio", new Socio());
		model.addAttribute("sujetoObligado", new SujetoObligado());
		model.addAttribute("representanteLegal", new RepresentanteLegal());
		model.addAttribute("filtroSolicitud", new FiltroSolicitud());
		model.addAttribute("isRL", usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue()));
		return "buscar.patron.rfc";
	}
	
	/**
	 * Carga el grid de RFC disponibles para un representante legal
	 * @param params
	 * @param session
	 * @return JSON Object
	 */
	@RequestMapping(value = "/cargaRFC", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<Persona> cargaGrid(@RequestBody PersonaDataTable params, HttpSession session) {
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		return sujetoObligadoService.obtenerPersonasRepresentadasPorRepresentanteLegal(usuario.getFisica().getIdPersona());
	}

	/**
	 * (Para que el jugo no se confunda) Desde la pagina de busqueda de RFC donde se listan todas las solicitudes
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/detalleSujetoObligado", method = RequestMethod.POST)
	public String buscarSujetoObligadoPorRFC(
			@ModelAttribute Socio sujetoOrigen, BindingResult result,
			Model model, HttpSession session) {
		log.debug("****************************\n************************************buscar Sujeto Obligado por RFC");
		
		if(result==null)
			result = new BindException(sujetoOrigen,"model");
		
		new RFCValidator().validate(sujetoOrigen, result);
		if (result.hasErrors()) {
			System.err.println("Se encontraron los sig errores: "+result);
			return "buscar.patron.rfc";
		}
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		SujetoObligado sujetoObligado = new SujetoObligado();
		String rfc = sujetoOrigen.getRfc();
		System.out.println("RFC: "+rfc);
		log.info("RFC: "+rfc);
		TipoPersonaFiscal tipoPersonaFiscal = obtenerTipoPersonaFiscal(rfc);
		if (tipoPersonaFiscal == null) {
			log.debug("****************************\n2************************************");
			result.addError(new ObjectError("rfc", "RFC incorrecta"));
			return "buscar.patron.rfc";
		}
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
		List<SujetoObligado> sujetosObligados;
		try {
			sujetosObligados = sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado);
			if (sujetosObligados.isEmpty()) {
				result.addError(new ObjectError("rfc",
						"No hay algún RP asociado a este RFC"));
				return "buscar.patron.rfc";
			}
			
			sujetoObligado = sujetosObligados.get(0);
			
			sujetoObligado.setSujetosObligados(sujetosObligados);
			
			if (bFisica) {
				session.setAttribute("cveIdPatronSO", sujetoObligado.getFisica().getIdPersona());
			} else {
				session.setAttribute("cveIdPatronSO", sujetoObligado.getMoral().getIdPersona());
			}
			
		} catch (AbstractException e) {
			result.addError(new ObjectError("rfc", e.getMessage()));
			return "buscar.patron.rfc";
		}
		model.addAttribute("sujetoObligado", sujetoObligado);
		session.setAttribute("sujetoTramiteForRepLegal", sujetoObligado);
		log.debug("<OTIKA>!!!!!!!!!!!!!");
		model.addAttribute("bFisica", bFisica);
		boolean isOperadorIMSS = usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		if(!isOperadorIMSS){
			Solicitud solicitudActiva = solicitudServiceBusiness.obtenerSolicitudActiva(sujetoObligado, 
				TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, usuario,null);
			model.addAttribute("existeSolicitudActiva", solicitudActiva!=null);
		}else{
			model.addAttribute("existeSolicitudActiva", false);
		}
		
		System.err.println("evaluando si este usuario es un RL con actos de admon. o dominio");
		if (usuario != null && "legal".equalsIgnoreCase(usuario.getUsuario())){
			boolean cuentaRLConActosDeAdmonDominio = this.cuentaRLConActosDeAdmonDominio(bFisica ? sujetoObligado.getFisica().getIdPersona() : sujetoObligado.getMoral().getIdPersona(), bFisica ? TipoPersonaEnum.FISICA : TipoPersonaEnum.MORAL, usuario );
			System.err.println("Este usuario es RL, actos de admon. o dominio: " + cuentaRLConActosDeAdmonDominio);
			session.setAttribute("cuentaRLConActosDeAdmonDominio", cuentaRLConActosDeAdmonDominio);
		} else {
			// aun si no es el usuario un RL se manda true para que se muestre la opcion de agregar nuevo rl.
			System.err.println("Este usuario NO es RL.");
			session.setAttribute("cuentaRLConActosDeAdmonDominio", true);
		}
		
		model.addAttribute("isOperadosIMSS",isOperadorIMSS);
		model.addAttribute("isRL", usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue()));
		return "mostrar.so.afiliacion";
	}
	
	
	private void gestionarDatosTramite(Model model, SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, String varNombreTramite, String varStatusTramite, String varTramiteRatificado, HttpSession session){
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Map<String,Object> result = afiliacionService.gestionarDatosDeTramite(sujetoObligado, tipoTramite, usuario,false);
		System.err.println("subiendo al model tramiteActivo"+ (Boolean)result.get("tramiteActivo"));
		System.err.println("subiendo al model tramiteData"+(SujetoObligado)result.get("tramiteData"));
		System.err.println("subiendo al model tramiteRatificado"+(Boolean)result.get("tramiteRatificado"));
		model.addAttribute(varStatusTramite,(Boolean)result.get("tramiteActivo"));
		model.addAttribute(varNombreTramite, (SujetoObligado)result.get("tramiteData"));
		model.addAttribute(varTramiteRatificado, (Boolean)result.get("tramiteRatificado"));
		
		if (TipoTramiteEnum.ACTUALIZACION_SOCIO.name().equals(varNombreTramite)){
			session.setAttribute(varNombreTramite, (SujetoObligado)result.get("tramiteData"));
		}
		if (TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name().equals(varNombreTramite)){
			SujetoObligado sujetoTramite = (SujetoObligado) result.get("tramiteData");
			sujetoTramite.setTipoPersonaFiscal(sujetoObligado.getTipoPersonaFiscal());
			session.setAttribute("sujetoTramiteForRepLegal", sujetoTramite);
			session.setAttribute(varNombreTramite, sujetoTramite);
		}
		
		
		
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA)){
			SujetoObligado sujetoTr=(SujetoObligado)result.get("tramiteData");
			
			if(sujetoTr!= null && sujetoTr.getMoral()!=null && sujetoTr.getMoral().getEscrituraConstitutiva()!= null 
					&& sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion()!= null){
			
			model.addAttribute("claveMun",sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion().getClave());
			model.addAttribute("claveEdo",sujetoTr.getMoral().getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getClave());
			}
		}
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
			
			Long idTipoSociedad = 0L;
			List<MedioContacto> mFiscales = null;
			try{
				SujetoObligado sujetoTr=(SujetoObligado)result.get("tramiteData");
				this.log.error("Tipo Persona Tramite: "+ sujetoObligado.getTipoPersonaFiscal());
				System.err.println("Tipo Persona Tramite: "+ sujetoObligado.getTipoPersonaFiscal());
				if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
					idTipoSociedad = sujetoTr.getMoral().getTipoSociedad().getIdTipoSociedad();
					System.err.println("TIPO SOCIEDAD DE DATOS GENERALES..."+idTipoSociedad);
					model.addAttribute("idTipoSociedadVar", idTipoSociedad);	
					mFiscales = sujetoTr.getMoral().getMediosContactoFiscales() !=null ?
							sujetoTr.getMoral().getMediosContactoFiscales() : new ArrayList<MedioContacto>();
					System.err.println("Medios Fiscales: "+sujetoTr.getMoral().getMediosContactoFiscales());
					session.setAttribute("listaMediosContactoFiscales", mFiscales);
					model.addAttribute("listaMediosContactoFiscales",mFiscales);
				}else{
					mFiscales = sujetoTr.getFisica().getMediosContactoFiscales() !=null ?
							sujetoTr.getFisica().getMediosContactoFiscales() : new ArrayList<MedioContacto>();
					session.setAttribute("listaMediosContactoFiscales", mFiscales);
					model.addAttribute("listaMediosContactoFiscales",mFiscales);
				}
			}catch(NullPointerException npe){
				log.error("Error al tratar Tipo Sociedad para persona fisica, parece que no sabes!: " + npe.getMessage());
			} finally{
				model.addAttribute("idTipoSociedadVar", idTipoSociedad);
			}
		}
	}
	
	private boolean gestionarTramiteEnProceso(Model model, SujetoObligado sujetoObligado){
		System.err.println("Consultar Solicitud en Proceso........");
		Solicitud solicitudEnProceso = afiliacionService.obtenerSolicitudEnProceso(sujetoObligado, TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES);
		if(solicitudEnProceso != null){
			System.err.println("Si hay solicitud en Proceso........");
			return true;
		}
		System.err.println("No hay solicitud en Proceso........");
		return false;
	}
		
	/**
	 * (Para que el jugo no se confunda) Desde la pagina de Login
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/detallePatron", method = {RequestMethod.GET, RequestMethod.POST})
	public String obtenerDetalleSujetoObligadoPorRFC(Model model, HttpSession session) {
		System.err.println("****************************\n************************************buscar Sujeto Obligado por RFC  desde Login");
		
		Socio sujetoOrigen = null;
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())){
			sujetoOrigen = new Socio();
			String rfcOrigen = usuario.getFisica()!=null ?  usuario.getFisica().getRfc() : usuario.getMoral().getRfc();
			System.out.println("\n\n\n\n\nRFC A CARGAR:"+rfcOrigen);
			sujetoOrigen.setRfc(rfcOrigen);
		}
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		String rfc = sujetoOrigen.getRfc();

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
		List<SujetoObligado> sujetosObligados;
		try {
			sujetosObligados = sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado);
			if (sujetosObligados.isEmpty()) {
				System.out.println("\n\n\n\n\n\n\n NO HAY SUJETOS OBLIGADOS ASOCIADOS A ESE RP");
				return "buscar.patron.rfc";
			}
			sujetoObligado.setSujetosObligados(sujetosObligados);
			sujetoObligado.setMoral(sujetosObligados.get(0).getMoral());
			sujetoObligado.setFisica(sujetosObligados.get(0).getFisica());
		} catch (AbstractException e) {
			e.printStackTrace();
			return "buscar.patron.rfc";
		}
		model.addAttribute("sujetoObligado", sujetoObligado);
		
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL,TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.name(),"tramiteDenominacionActivo", "tramiteDenominacionRatificado", session);
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO,TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.name(),"tramiteDatosContactoActivo", "tramiteDatosContactoRatificado", session);
		//Los datos de REPRESENTANTE LEGAL Y SOCIOS se gestionan porque se debe saber si existe un trámite y si esta ratificado para ocultar o mostrar ciertos botones
		System.err.println("Se gestionan datos del tramite de rep legal");
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL,TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name(),"tramiteRepresentanteLegalActivo", "tramiteRepresentanteLegalRatificado",session);
		
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_SOCIO,TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),"tramiteSocioActivo", "tramiteSocioRatificado",session);
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA,TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.name(),"tramiteEscrituraConstitutivaActivo", "tramiteEscrituraConstitutivaRatificado", session);
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO,TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.name(),"tramiteRegistroSindicatoActivo", "tramiteRegistroSindicatoRatificado", session);
		}
		model.addAttribute("bFisica", bFisica);
		model.addAttribute("solicitudEnProceso", gestionarTramiteEnProceso(model, sujetoObligado));
		model.addAttribute("isOperadosIMSS",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		
//		return "mostrar.sujeto.obligado";
		return "mostrar.so.afiliacion";
	}

	
	/**
	 * Muestra la página de detalle de RP
	 * 
	 * @param cveIdPatronSujetoObligado
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/detalleRP", method = RequestMethod.POST)
	public String mostrarDetalleRegistroPatronal(
			@ModelAttribute SujetoObligado sujetoObligado,
			BindingResult result, Model model, HttpSession session) {
		log.debug("mostrar detalle registro patronal");
		Long idSO = sujetoObligado.getCveIdSujetoObligado();
		if (idSO == null) {
			result.addError(new ObjectError("rp", "Seleccione un registro patronal"));
			return "mostrar.sujeto.obligado";
		}
		if(sujetoObligado.getSujetosObligados()!=null && !sujetoObligado.getSujetosObligados().isEmpty())
			sujetoObligado = sujetoObligado.getSujetosObligados().get(idSO.intValue());
		sujetoObligado = sujetoObligadoService
				.obtenerDetalleRegistroPatronalPorClaveTipoPersona(
						sujetoObligado.getCveIdSujetoObligado(), sujetoObligado
								.getTipoPersonaFiscal());
		System.out.println("DOMICILIO FISCAL DEL RP: "+sujetoObligado.getDomicilioFiscal());
		
		if (sujetoObligado.getFormasContacto() != null){
			System.err.println("sujetoObligado.formasContacto: " + sujetoObligado.getFormasContacto().size());
			
			for (FormaContacto iterable_element : sujetoObligado.getFormasContacto()) {
				System.err.println("iterable_element: " + iterable_element);
			}
			
		}
		
		model.addAttribute("sujetoObligado", sujetoObligado);
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL,TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.name(),"tramiteDenominacionActivo", "tramiteDenominacionRatificado", session);
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO,TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.name(),"tramiteDatosContactoActivo", "tramiteDatosContactoRatificado", session);
		//Los datos de REPRESENTANTE LEGAL Y SOCIOS se gestionan porque se debe saber si existe un trámite y si esta ratificado para ocultar o mostrar ciertos botones
		System.err.println("Se gestionan datos del tramite de rep legal");
		gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL,TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.name(),"tramiteRepresentanteLegalActivo", "tramiteRepresentanteLegalRatificado",session);
		
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_SOCIO,TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),"tramiteSocioActivo", "tramiteSocioRatificado",session);
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA,TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.name(),"tramiteEscrituraConstitutivaActivo", "tramiteEscrituraConstitutivaRatificado", session);
			gestionarDatosTramite(model, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO,TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.name(),"tramiteRegistroSindicatoActivo", "tramiteRegistroSindicatoRatificado", session);			
		}
		model.addAttribute("bFisica", TipoPersonaFiscal.FISICA.equals(sujetoObligado.getTipoPersonaFiscal()));
		model.addAttribute("idTramite", -1);
//		return "detalle.registro.patronal";
		return "detalle.rp.afiliacion";
	}

	private TipoPersonaFiscal obtenerTipoPersonaFiscal(String rfc) {
		if (rfc.length() == 13)
			return TipoPersonaFiscal.FISICA;
		else if (rfc.length() == 12)
			return TipoPersonaFiscal.MORAL;
		else
			return null;
	}

	/**
	 * Muestra la página de Datos Generales del patron SO
	 * 
	 * @param cveIdPatronSujetoObligado
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/datosGenerales", method = RequestMethod.POST)
	public String mostrarDatosGeneralesPatronSO(
			@ModelAttribute SujetoObligado sujetoObligado, Model model,
			HttpSession session, HttpServletRequest request) {
		log.debug(" Ejecutando consulta de datos generales ");
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		sujetoObligado = sujetoObligadoService
				.obtenerDatosGeneralesPatron(sujetoObligado);
		boolean bFisica = TipoPersonaFiscal.FISICA.equals(sujetoObligado
				.getTipoPersonaFiscal());
		if(bFisica){
			if(sujetoObligado.getFisica().getTelefonoFijo()!=null){
				StringTokenizer st = new StringTokenizer(sujetoObligado.getFisica().getTelefonoFijo().getNumero(), "-");
				if(st.hasMoreTokens()){
					sujetoObligado.getFisica().getTelefonoFijo().setNumero(st.nextToken());
					if(st.hasMoreTokens())
						sujetoObligado.getFisica().getTelefonoFijo().setExtension(st.nextToken());
				}else{
					sujetoObligado.getFisica().getTelefonoFijo().setNumero("");
				}
			}
		}else{
			if(sujetoObligado.getMoral().getTelefonoFijo()!=null){
				StringTokenizer st = new StringTokenizer(sujetoObligado.getMoral().getTelefonoFijo().getNumero(), "-");
				if(st.hasMoreTokens()){
					sujetoObligado.getMoral().getTelefonoFijo().setNumero(st.nextToken());
					if(st.hasMoreTokens())
						sujetoObligado.getMoral().getTelefonoFijo().setExtension(st.nextToken());
				}else{
					sujetoObligado.getMoral().getTelefonoFijo().setNumero("");
				}
			}
			model.addAttribute("escrituraConstitutiva", sujetoObligado
					.getEscrituraConstitutiva()== null ? 
						new EscrituraConstitutiva() : 
						sujetoObligado
							.getEscrituraConstitutiva());
			sujetoObligado.getEscrituraConstitutiva().setNumeroRegistroPatronal(sujetoObligado.getNumeroRegistroPatronal());
			model.addAttribute("registroSindicato", sujetoObligado
					.getRegistroSindicato() == null ?
					new RegistroSindicato() :
						sujetoObligado
						.getRegistroSindicato()	);
			sujetoObligado.getRegistroSindicato().setNumeroRegistroPatronal(sujetoObligado.getNumeroRegistroPatronal());
			String cveEstado = "-1";
			String cveMun = "-1";
			if(sujetoObligado
					.getEscrituraConstitutiva().getLugarExpedicion() != null){
				if(sujetoObligado
						.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa()!= null){
					cveEstado = sujetoObligado
					.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getClave();
				}
			}
				
			model.addAttribute("claveEdo", cveEstado);
			
			if(sujetoObligado
					.getEscrituraConstitutiva().getLugarExpedicion()!= null){
				cveMun=sujetoObligado
				.getEscrituraConstitutiva().getLugarExpedicion().getClave();
			}
			
			model.addAttribute("claveMun", cveMun);
			
			if(sujetoObligado
					.getEscrituraConstitutiva().getCveIdPersonaMoral()==0){
				sujetoObligado
				.getEscrituraConstitutiva().setCveIdPersonaMoral(sujetoObligado.getMoral().getIdPersona());
			}
			
			if(sujetoObligado
					.getRegistroSindicato().getCveIdPersonaMoral()==0){
				sujetoObligado
				.getRegistroSindicato().setCveIdPersonaMoral(sujetoObligado.getMoral().getIdPersona());
			}
			
		}
		
		popularTiposTramiteActivosPorPatron(request, usuario, sujetoObligado.getCveIdSujetoObligado());
		
		System.out.println("SUJETO OBLIGADO DEVUELTO EN DATOS GENERALES: "+sujetoObligado);
		
		model.addAttribute("sujetoObligado", sujetoObligado);
		model.addAttribute("sujetoTramite",sujetoObligado);
		model.addAttribute("escrituraConstitutivaTramite",sujetoObligado.getEscrituraConstitutiva());
		model.addAttribute("registroSindicatoTramite",sujetoObligado.getRegistroSindicato());
		model.addAttribute("bFisica", bFisica);
//		model.addAttribute("idTramite","vacio");
		model.addAttribute("idSolicitud","vacio");
		return "datos.generales.patron";
	}

	/**
	 * Actualiza los datos de la escritura constitutiva asociada al sujeto
	 * obligado
	 * 
	 * @param sujetoObligado
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/actualizarEscrituraConstitutiva/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarEscrituraConstitutiva(
			@PathVariable String idSolicitud,
			@RequestBody EscrituraConstitutiva escrituraConstitutiva,
			HttpServletResponse response, HttpSession session) {
		log.debug(" Ejecutando actualizar Escritura Constitutiva ");
		log.debug("****************************"+escrituraConstitutiva);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		this.log.debug("Usuario: " + usuario);
		Map<String, Object> result = new HashMap<String, Object>();
		Errors errors = new BindException(escrituraConstitutiva, "model");
		new EscrituraConstitutivaValidator().validate(escrituraConstitutiva,
				errors);
		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		try {
			if(escrituraConstitutiva.getCveEscrituraConstitutiva()!= null && escrituraConstitutiva.getCveEscrituraConstitutiva()==0)
				escrituraConstitutiva.setCveEscrituraConstitutiva(null);
			
			SujetoObligado sujetoObligado = new SujetoObligado();
			sujetoObligado.setCveIdSujetoObligado(escrituraConstitutiva.getCveIdPatronSujetoObligado());
			sujetoObligado.setEscrituraConstitutiva(escrituraConstitutiva);
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			sujetoObligado.setNumeroRegistroPatronal(escrituraConstitutiva.getNumeroRegistroPatronal());
			Moral moral = new Moral();
			moral.setIdPersona(escrituraConstitutiva.getCveIdPersonaMoral());
			sujetoObligado.setMoral(moral);
			
			
			boolean afectar = administrarSolicitud(idSolicitud, usuario, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA, TipoSolicitudEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA, result, session);
//			if(idSolicitud==null || "vacio".equals(idSolicitud)){
//				log.debug("ACTUALIZAR ESCRITURA CONSTITUTIVA -- SE REGISTRA UNA NUEVA SOLICITUD [ " + sujetoObligado + " ]");
//				Solicitud solicitud = solicitudServiceBusiness.generarSolicitud(TipoSolicitudEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA, 
//						EstadoSolicitudEnum.REGISTRADA, usuario, TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA, 
//						EstadoTramiteEnum.INICIADO, sujetoObligado);
//				solicitudServiceBusiness.actualizarSolicitud(solicitud.getSolicitudId(), EstadoTramiteEnum.ACTIVO, sujetoObligado);
//				
//				result.put("mensajeExito", agregarFolioAMensaje(solicitud.getNoFolioSolicitud()));
//			}else{
//				sujetoObligadoService.actualizarEscrituraConstitutiva(escrituraConstitutiva, usuario);
//				log.debug("ACTUALIZAR ESCRITURA CONSTITUTIVA -- SE ACTUALIZARA LA SOLICITUD ACTUAL [ " + sujetoObligado + " ]");
//				solicitudServiceBusiness.actualizarSolicitud(Long.valueOf(idSolicitud), EstadoTramiteEnum.CERRADO, sujetoObligado);
//			}
			
			if(afectar){
				// LUDS 15/03/2013: Se modifico ya que estaba marcando errores de
				// compilacion, y al parecer se cambio la interfaz
				sujetoObligadoService.actualizarEscrituraConstitutiva(escrituraConstitutiva);
			}
				
			
//			escrituraConstitutiva = this.sujetoObligadoService
//					.actualizarEscrituraConstitutiva(escrituraConstitutiva, usuario);
			result.put("oModel", escrituraConstitutiva);
		} catch (GestionPatronalBusinessException e) {
			e = new GestionPatronalBusinessException(this.messageSource
					.getMessage("msg.business.exception", null, e.getMessage(),
							Locale.getDefault()));
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		}
		return result;
	}

	/**
	 * Actualiza los datos de registro de sindicato mostrados en la pantalla de
	 * datos generales
	 * 
	 * @param RegistroSindicato
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/actualizarRegistroSindicato/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarRegistroSindicato(
			@PathVariable String idSolicitud,
			@RequestBody RegistroSindicato registroSindicato,
			HttpServletResponse response, HttpSession session) {
		log.debug("actualizar Registro Sindicato");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		this.log.debug("Usuario: " + usuario);
		Map<String, Object> result = new HashMap<String, Object>();
		Errors errors = new BindException(registroSindicato, "model");
		new RegistroSindicatoValidator().validate(registroSindicato, errors);
		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		try {
			
			SujetoObligado sujetoObligado = new SujetoObligado();
			sujetoObligado.setRegistroSindicato(registroSindicato);
			sujetoObligado.setCveIdSujetoObligado(registroSindicato.getCveIdPatronSujetoObligado());
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			sujetoObligado.setNumeroRegistroPatronal(registroSindicato.getNumeroRegistroPatronal());
			Moral moral = new Moral();
			moral.setIdPersona(registroSindicato.getCveIdPersonaMoral());
			sujetoObligado.setMoral(moral);
			
			
			boolean afectar = administrarSolicitud(idSolicitud, usuario, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO, TipoSolicitudEnum.ACTUALIZACION_REGISTRO_SINDICATO, result, session);
//			if(idSolicitud==null || "vacio".equals(idSolicitud)){
//				log.debug("ACTUALIZAR REGISTRO SINDICATO -- SE REGISTRA UNA NUEVA SOLICITUD [ " + sujetoObligado + " ]");
//				Solicitud solicitud = solicitudServiceBusiness.generarSolicitud(
//						TipoSolicitudEnum.ACTUALIZACION_REGISTRO_SINDICATO, 
//						EstadoSolicitudEnum.REGISTRADA, usuario, 
//						TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO, 
//						EstadoTramiteEnum.INICIADO, sujetoObligado);
//				solicitudServiceBusiness.actualizarSolicitud(solicitud.getSolicitudId(), EstadoTramiteEnum.ACTIVO, sujetoObligado);
//				
//				result.put("mensajeExito", agregarFolioAMensaje(solicitud.getNoFolioSolicitud()));
//			}else{
//				sujetoObligadoService.actualizarRegistroSindicato(registroSindicato, usuario);
//				log.debug("ACTUALIZAR REGISTRO SINDICATO -- SE ACTUALIZARA LA SOLICITUD ACTUAL [ " + sujetoObligado + " ]");
//				solicitudServiceBusiness.actualizarSolicitud(Long.valueOf(idSolicitud), EstadoTramiteEnum.CERRADO, sujetoObligado);
//			}
			
			if(afectar){
				// LUDS 15/03/2013: Se modifico ya que estaba marcando errores de
				// compilacion, y al parecer se cambio la interfaz
				sujetoObligadoService.actualizarRegistroSindicato(registroSindicato);
			}
				
			
			
			
//			registroSindicato = this.sujetoObligadoService
//					.actualizarRegistroSindicato(registroSindicato, usuario);
			result.put("oModel", registroSindicato);
		} catch (GestionPatronalBusinessException e) {
//			e = new GestionPatronalBusinessException(this.messageSource
//					.getMessage("msg.business.exception", null, e.getMessage(),
//							Locale.getDefault()));
			this.procesarErrorDeNegocio(e, result, response);
		}
		return result;
	}

	/**
	 * Actualiza los datos de registro de sindicato mostrados en la pantalla de
	 * datos generales
	 * 
	 * @param RegistroSindicato
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/actualizarRazonDenominacionSocial/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarRazonDenominacionSocial(
			@PathVariable String idSolicitud,
			@RequestBody SujetoObligado sujetoTramite,
			HttpSession session, HttpServletResponse response) {
		log.debug("**********************************actualizar Razon Denominacion Social" +
				"\n***********************************************\nidSolicitud="+idSolicitud);
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		Map<String, Object> result = new HashMap<String, Object>();
		Errors errors = new BindException(sujetoTramite, "model");
		new NombreComercialValidator().validate(sujetoTramite, errors);
		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		try {
			
			log.debug("********************qwerty1"+sujetoTramite);
			boolean afectar = administrarSolicitud(idSolicitud, usuario, sujetoTramite, TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL, TipoSolicitudEnum.ACTUALIZACION_RAZON_DENOMINACION_SOCIAL, result, session);
			
//			if(idSolicitud==null || "vacio".equals(idSolicitud)){
//				log.debug("RAZON DENOMINACION SOCIAL -- SE CREARA UNA NUEVA SOLICITUD [ " + sujetoTramite + " ]");
//					Solicitud solicitud = solicitudServiceBusiness.generarSolicitud(
//							TipoSolicitudEnum.ACTUALIZACION_RAZON_DENOMINACION_SOCIAL,
//							EstadoSolicitudEnum.REGISTRADA, usuario, TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL,
//							EstadoTramiteEnum.INICIADO, sujetoTramite);
//					System.out.println("Se comparara si el usuario firmado es tramitador");
//					solicitudServiceBusiness.actualizarSolicitud(solicitud.getSolicitudId(), EstadoTramiteEnum.ACTIVO, sujetoTramite);
//					result.put("mensajeExito", agregarFolioAMensaje(solicitud.getNoFolioSolicitud()));
//			}else{
//				log.debug("RAZON DENOMINACION SOCIAL -- AFECTANDO BASE DE DATOS [ " + sujetoTramite + " ]");
//				sujetoObligadoService.actualizarDenominacionRazonSocial(sujetoTramite, usuario);
//				log.debug("RAZON DENOMINACION SOCIAL -- SE CERRARA LA SOLICITUD ACTUAL [ " + sujetoTramite + " ]");
//				solicitudServiceBusiness.actualizarSolicitud(Long.valueOf(idSolicitud), EstadoTramiteEnum.CERRADO, sujetoTramite);
//			}
			
			if(afectar)
				sujetoObligadoService.actualizarDenominacionRazonSocial(sujetoTramite, usuario);
			
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
		result.put("oModel", sujetoTramite);
		return result;
	}
    
    /**
     * 
     * @param sujetoObligado
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/actualizarDatosContacto/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarDatosContacto(
			@PathVariable String idSolicitud,
			@RequestBody SujetoObligado sujetoObligado,
			HttpServletResponse response, HttpSession session) {
		log.debug("Ejecutando actualizar datos de contacto ");
		log.debug(sujetoObligado);
		Map<String, Object> result = new HashMap<String, Object>();
		Errors errors = new BindException(sujetoObligado, "model");
		new DatosContactoValidator().validate(sujetoObligado,errors);
		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		this.log.debug("Usuario: " + usuario);
//		try {
//			solicitudServiceBusiness.generarSolicitud(TipoSolicitudEnum.ACTUALIZACION_DATOS_CONTACTO, EstadoSolicitudEnum.REGISTRADA, usuario, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO, EstadoTramiteEnum.EN_ESPERA_TRAMITADOR, so);	
//			this.sujetoObligadoService.modificarDatosContacto(sujetoObligado, usuario);
//		} catch (GestionPatronalBusinessException e) {
//			e.printStackTrace();
//		}
		
		try {
			
			
			
			boolean afectar = administrarSolicitud(idSolicitud, usuario, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES, result, session);
			
//			if(idSolicitud==null || "vacio".equals(idSolicitud)){
//				log.debug("DATOS DE CONTACTO -- SE CREARA UNA NUEVA SOLICITUD [ " + sujetoObligado + " ]");
//				
//					Solicitud solicitud = solicitudServiceBusiness.generarSolicitud(
//							TipoSolicitudEnum.ACTUALIZACION_DATOS_CONTACTO,
//							EstadoSolicitudEnum.REGISTRADA, usuario, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO,
//							EstadoTramiteEnum.INICIADO, sujetoObligado);
//					solicitudServiceBusiness.actualizarSolicitud(solicitud.getSolicitudId(), EstadoTramiteEnum.ACTIVO, sujetoObligado);
//					
//					result.put("mensajeExito", agregarFolioAMensaje(solicitud.getNoFolioSolicitud()));
//			}else{
//				this.sujetoObligadoService.modificarDatosContacto(sujetoObligado, usuario);
//				log.debug("DATOS CONTACTO -- SE ACTUALIZARA LA SOLICITUD ACTUAL [ " + sujetoObligado + " ]");
//				solicitudServiceBusiness.actualizarSolicitud(Long.valueOf(idSolicitud), EstadoTramiteEnum.CERRADO, sujetoObligado);
//			}
			
			if(afectar)
				sujetoObligadoService.modificarDatosContacto(sujetoObligado, usuario);
			
		} catch (GestionPatronalBusinessException e) {
			procesarErrorDeNegocio(e, result, response);
			e.printStackTrace();
		}
		
		result.put("oModel", sujetoObligado);
		return result;
	}
    
    /**
	 * Carga el grid de Solicitudes disponibles para un representante legal
	 * @param params
	 * @param session
	 * @return JSON Object
	 */
	@RequestMapping(value = "/cargaSolicitudes", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<TramiteSolicitud> cargaGridSolicitudes(@RequestBody TramiteSolicitudDataTable params, HttpSession session) {
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		System.out.println("IdPatron: "+params.getoForm().getCveIdPatronSujetoObligado());
		System.err.println("/********************************************\n\n cargaSolicitudes ................... \n");
		DatosSalidaPaginador<TramiteSolicitud> tramites = null;
		if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
			tramites = tramiteService.obtenerTramitesDeSujetoObligadoPorUsuario(params.getoForm().getCveIdPatronSujetoObligado(), new Long(usuario.getCveIdUsuario()).longValue());
			System.out.println("\n\n\n\n\n\nCargue solicitudes para tramitador:"+tramites.getAaData().size());
			
		}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
				CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
				|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
						CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())){
			tramites = tramiteService.obtenerTramitesPorSujetoObligado(params.getoForm().getCveIdPatronSujetoObligado());
			System.out.println("\n\n\n\n\n\nCargue solicitudes para sujeto obligado: "+tramites.getAaData().size());
		}
		
		return tramites;
	}
	
	/**
	 * Carga el grid de Solicitudes
	 * @param params
	 * @param session
	 * @return JSON Object
	 */
	@RequestMapping(value = "/cargaTodasLasSolicitudes", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody DatosSalidaPaginador<TramiteSolicitud> cargaTodasLasSolicitudes(@RequestBody TramiteSolicitudDataTable params, HttpSession session) {
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		System.err.println("usuario: " + usuario);
		System.out.println("IdPatron: "+params.getoForm().getCveIdPatronSujetoObligado());
		System.err.println("/********************************************\n\n cargaTodasLasSolicitudes ................... \n");
		DatosSalidaPaginador<TramiteSolicitud> tramites = null;
		if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue())){
			//tramites = tramiteService.obtenerTramitesDeSujetoObligadoPorUsuario(params.getoForm().getCveIdPatronSujetoObligado(), usuario.getCveIdUsuario());
			tramites = tramiteService.consultarTramitesActivos();
			System.out.println("\n\n\n\n\n\nTotal de trámites activos: "+tramites.getAaData().size());
			
		}else if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
				CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
				|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
						CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())){
			tramites = tramiteService.obtenerTramitesPorSujetoObligado(params.getoForm().getCveIdPatronSujetoObligado());
			System.out.println("\n\n\n\n\n\nTotal de trámites activos: "+tramites.getAaData().size());
		}
		
		return tramites;
	}
	
	@RequestMapping(value = "/tramiteDenominacionSocial", method = RequestMethod.POST)
	public String tramiteDenominacionSocial(@RequestParam("idTramite") String idTramite,
			@RequestParam("idSolicitud") String idSolicitud, Model model) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		if (idSolicitud != null && !StringUtils.isBlank(idSolicitud) && idSolicitud != "") {
			Solicitud sol = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
			TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
			sujetoTramite = tso.getSujetoObligado();
			log.debug("SUJETO OBLIGADO: [" + sujetoTramite + "]");
		}
		
		boolean bFisica = sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA);
		System.out.println("bFisica: "+bFisica);
		model.addAttribute("bFisica",bFisica);
		
		model.addAttribute("sujetoTramite",sujetoTramite);
		SujetoObligado sujetoObligadoBD = sujetoObligadoService.obtenerDetalleRegistroPatronalPorClaveTipoPersona(sujetoTramite.getCveIdSujetoObligado(), sujetoTramite.getTipoPersonaFiscal()); 
		model.addAttribute("sujetoObligado",sujetoObligadoBD);
		model.addAttribute("idTramite",idTramite);
		model.addAttribute("idSolicitud",idSolicitud);
		
		return "tramite.denominacion.social";
	}
	
	@RequestMapping(value = "/tramiteDatosContacto", method = RequestMethod.POST)
	public String tramiteDatosContacto(@RequestParam("idTramite") String idTramite,
			@RequestParam("idSolicitud") String idSolicitud, Model model) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		if (idSolicitud != null && !StringUtils.isBlank(idSolicitud) && idSolicitud != "") {
			Solicitud sol = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
			TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
			sujetoTramite = tso.getSujetoObligado();
			log.debug("SUJETO TRAMITE: [" + sujetoTramite + "]");
		}
		
		model.addAttribute("sujetoTramite",sujetoTramite);
		SujetoObligado sujetoObligadoBD = sujetoObligadoService.obtenerDatosGeneralesPatron(sujetoTramite);
		boolean bFisica = TipoPersonaFiscal.FISICA.equals(sujetoObligadoBD
				.getTipoPersonaFiscal());
		if(bFisica){
			if(sujetoObligadoBD.getFisica().getTelefonoFijo()!=null){
				StringTokenizer st = new StringTokenizer(sujetoObligadoBD.getFisica().getTelefonoFijo().getNumero(), "-");
				if(st.hasMoreTokens()){
					sujetoObligadoBD.getFisica().getTelefonoFijo().setNumero(st.nextToken());
					if(st.hasMoreTokens())
						sujetoObligadoBD.getFisica().getTelefonoFijo().setExtension(st.nextToken());
				}else{
					sujetoObligadoBD.getFisica().getTelefonoFijo().setNumero("");
				}
			}
		}else{
			if(sujetoObligadoBD.getMoral().getTelefonoFijo()!=null){
				StringTokenizer st = new StringTokenizer(sujetoObligadoBD.getMoral().getTelefonoFijo().getNumero(), "-");
				if(st.hasMoreTokens()){
					sujetoObligadoBD.getMoral().getTelefonoFijo().setNumero(st.nextToken());
					if(st.hasMoreTokens())
						sujetoObligadoBD.getMoral().getTelefonoFijo().setExtension(st.nextToken());
				}else{
					sujetoObligadoBD.getMoral().getTelefonoFijo().setNumero("");
				}
			}
		}
		
		System.out.println("bFisica: "+bFisica);
		model.addAttribute("bFisica",bFisica);
		
		model.addAttribute("sujetoObligado",sujetoObligadoBD);
		model.addAttribute("idTramite",idTramite);
		model.addAttribute("idSolicitud",idSolicitud);
		System.out.println("SUJETO TRAMITE JUSTO ANTES DEL FORWARD: "+sujetoTramite);
		return "tramite.denominacion.social";
	}
	
	@RequestMapping(value = "/tramiteActaconstitutiva", method = RequestMethod.POST)
	public String tramiteActaconstitutiva(@RequestParam("idTramite") String idTramite,
			@RequestParam("idSolicitud") String idSolicitud, Model model) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		if (idSolicitud != null && !StringUtils.isBlank(idSolicitud) && idSolicitud != "") {
			Solicitud sol = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
			TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
			sujetoTramite = tso.getSujetoObligado();
			log.debug("SUJETO OBLIGADO: [" + sujetoTramite + "]");
		}
		model.addAttribute("escrituraConstitutivaTramite",sujetoTramite.getEscrituraConstitutiva());
		SujetoObligado sujetoObligadoBD = sujetoObligadoService.obtenerDatosGeneralesPatron(sujetoTramite);
		boolean bFisica = TipoPersonaFiscal.FISICA.equals(sujetoObligadoBD
				.getTipoPersonaFiscal());
		if(!bFisica){
			model.addAttribute("escrituraConstitutiva", sujetoObligadoBD
					.getEscrituraConstitutiva()== null ? 
						new EscrituraConstitutiva() : 
							sujetoObligadoBD 
							.getEscrituraConstitutiva());
			
			String cveEstado = "-1";
			String cveMun = "-1";
			if(sujetoTramite
					.getEscrituraConstitutiva().getLugarExpedicion() != null){
				if(sujetoTramite
						.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa()!= null){
					cveEstado = sujetoTramite
					.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getClave();
				}
			}
				
			model.addAttribute("claveEdo", cveEstado);
			
			if(sujetoTramite
					.getEscrituraConstitutiva().getLugarExpedicion()!= null){
				cveMun=sujetoTramite
				.getEscrituraConstitutiva().getLugarExpedicion().getClave();
			}
			
			model.addAttribute("claveMun", cveMun);
			
			if(sujetoTramite
					.getEscrituraConstitutiva().getCveIdPersonaMoral()==0){
				sujetoTramite
				.getEscrituraConstitutiva().setCveIdPersonaMoral(sujetoTramite.getMoral().getIdPersona());
			}	
		}
		model.addAttribute("idTramite",idTramite);
		model.addAttribute("idSolicitud",idSolicitud);
		return "tramite.denominacion.social";
	}
	
	@RequestMapping(value = "/tramiteRegistroSindicato", method = RequestMethod.POST)
	public String tramiteRegistroSindicato(@RequestParam("idTramite") String idTramite,
			@RequestParam("idSolicitud") String idSolicitud, Model model) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		if (idSolicitud != null && !StringUtils.isBlank(idSolicitud) && idSolicitud != "") {
			Solicitud sol = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
			TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
			sujetoTramite = tso.getSujetoObligado();
			log.debug("SUJETO OBLIGADO: [" + sujetoTramite + "]");
		}
		model.addAttribute("registroSindicatoTramite",sujetoTramite.getRegistroSindicato());
		SujetoObligado sujetoObligadoBD = sujetoObligadoService.obtenerDatosGeneralesPatron(sujetoTramite);
		boolean bFisica = TipoPersonaFiscal.FISICA.equals(sujetoObligadoBD
				.getTipoPersonaFiscal());
		if(!bFisica){
			model.addAttribute("registroSindicato", sujetoObligadoBD
					.getRegistroSindicato() == null ?
					new RegistroSindicato() :
						sujetoObligadoBD
						.getRegistroSindicato()	);
			if(sujetoObligadoBD
					.getRegistroSindicato().getCveIdPersonaMoral()==0){
				sujetoObligadoBD
				.getRegistroSindicato().setCveIdPersonaMoral(sujetoObligadoBD.getMoral().getIdPersona());
			}
		}
		model.addAttribute("idTramite",idTramite);
		model.addAttribute("idSolicitud",idSolicitud);
		return "tramite.denominacion.social";
	}
	
	/**
	 * Agrega el número de folio al mensaje de confirmación de creación de solicitud
	 * @param noFolio
	 * @return String Mensaje de confirmación con el número de folio
	 */
	private String agregarFolioAMensaje(String noFolio){
		return mensajeActualizacion.replaceFirst("varNumeroFolio", noFolio);
	}
	
	private void popularTiposTramiteActivosPorPatron(HttpServletRequest request, Usuario usuario, Long cveIdPatronSujetoObligado){
		
		System.out.println("\n\n\n\n\n*********************BUSCANDO TRAMITES ACTIVOS");
		boolean mostrarOpcionActualizacionRazonSocial=true;
		boolean mostrarOpcionActualizacionDatosContacto=true;
		boolean mostrarOpcionActualizacionEscrituraConstitutiva=true;
		boolean mostrarOpcionActualizacionRegistroSindicato=true;	
			
		List<TramiteSolicitud> tramites = tramiteService.obtenerTramitesActivosPorSujetoObligado(cveIdPatronSujetoObligado);
		
		for(TramiteSolicitud tramite:tramites){
			System.out.println("Tramite activo: "+tramite.getTramiteId()+" Tipo: "+tramite.getTipoTramite().getDescripcion()+" SujetoObligado: "+tramite.getCveIdPatronSujetoObligado());
			if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
				System.out.println("\n\n\n\n\n******************No se mostrara boton de razon social");
				mostrarOpcionActualizacionRazonSocial=false;
			}
			if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
				System.out.println("\n\n\n\n\n******************No se mostrara boton de Datos de contacto");
				mostrarOpcionActualizacionDatosContacto=false;
			}
			else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
				System.out.println("\n\n\n\n\n******************No se mostrara boton de Escritura constitutiva");
				mostrarOpcionActualizacionEscrituraConstitutiva=false;
			}
			else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
				System.out.println("\n\n\n\n\n******************No se mostrara boton de Registro sindicato");
				mostrarOpcionActualizacionRegistroSindicato=false;
			}
		}
			
			
		request.setAttribute("mostrarOpcionActualizacionRazonSocial", mostrarOpcionActualizacionRazonSocial);
		request.setAttribute("mostrarOpcionActualizacionDatosContacto", mostrarOpcionActualizacionDatosContacto);
		request.setAttribute("mostrarOpcionActualizacionEscrituraConstitutiva", mostrarOpcionActualizacionEscrituraConstitutiva);
		request.setAttribute("mostrarOpcionActualizacionRegistroSindicato", mostrarOpcionActualizacionRegistroSindicato);
		
	}
	
	@RequestMapping(value = "/cierraTramite/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cierraTramite(
			@PathVariable String idSolicitud) {
		log.debug("idSolicitud=*"+idSolicitud+"*");
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(Long.valueOf(idSolicitud));
		solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.CANCELADO, null);
		Map<String, Object> result = new HashMap<String, Object>();
		return result;
	}
	
	
	@RequestMapping(value = "/desplegarAcuse", method = RequestMethod.POST)
	public void presentarAcuse(HttpServletResponse response, HttpSession session){
		log.debug("*********************************\n********************************************");
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		SujetoObligado sujeto = (SujetoObligado) session.getAttribute("sujetoObligado");
		log.debug("*********************************\nidSolicitud="+idSolicitud+"********************************************");
		log.debug("*********************************\nsujeto="+sujeto+"********************************************");
		sujeto=sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
		Tramite tramite = solicitud.getTramites().get(0);
		Map<String, Object> parametros = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		String fecha = " "+formatter.format(solicitud.getFechaSolicitud())+" ";
		parametros.put("P_FECSOLCITUD", fecha);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		parametros.put("P_DESCTRAMITE", tramite.getTipoTramite().getDescripcion());
		parametros.put("P_REGPATRONAL", sujeto.getNumeroRegistroPatronal());
		
		//Se eliminan los datos de la sesion
		session.removeAttribute("idSolicitud");
		session.removeAttribute("sujetoObligado");
		
		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			Fisica fisica = sujeto.getFisica();
			String nombreCompleto = fisica.getNombre();
			nombreCompleto += " "+fisica.getPrimerApellido();
			nombreCompleto += " "+fisica.getSegundoApellido();
			parametros.put("P_RAZONSOCIAL", " ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
		}else{
			Moral moral = sujeto.getMoral();
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
		}
		Subdelegacion subdelegacion = sujeto.getSubdelegacion();
		if(subdelegacion != null){
			parametros.put("P_CVE_DELEGAC", subdelegacion.getDelegacion().getClave());
			parametros.put("P_DES_DELEGAC", subdelegacion.getDelegacion().getDescripcion());
			parametros.put("P_CVE_SUB_DEL", subdelegacion.getClave());
			parametros.put("P_DES_SUB_DEL", subdelegacion.getDescripcion());
		}else{
			parametros.put("P_CVE_DELEGAC", "");
			parametros.put("P_DES_DELEGAC", "");
			parametros.put("P_CVE_SUB_DEL", "");
			parametros.put("P_DES_SUB_DEL", "");
		}
		 try {
			String nombreArchivo = "Acuse_"+solicitud.getNoFolioSolicitud()+".pdf";
			byte[] reporte = manejadorReportes.ejecutaAcuse(parametros);
			if(reporte != null){
				log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR ");
			}else{
				log.debug("EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
			}
			response.setContentType("application/pdf"); 
			response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo); 
			response.getOutputStream().write(reporte);
			response.getOutputStream().close();
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
               if (response.getOutputStream() != null) 
            	   response.getOutputStream().close();
            } catch (IOException ioe) {
                    ioe.printStackTrace(); 
            }

		}
	}
	
	
	private boolean administrarSolicitud(String idSolicitud, Usuario usuario, SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, TipoSolicitudEnum tipoSolicitud, Map<String, Object> result, HttpSession session) throws GestionPatronalBusinessException{
		
		
		boolean afectar = false;
		if(idSolicitud==null || "vacio".equals(idSolicitud)){
			log.debug(" -- SE REGISTRA UNA NUEVA SOLICITUD [ " + sujetoObligado + " ]");
			Solicitud solicitud = solicitudServiceBusiness.generarSolicitud(tipoSolicitud, 
					EstadoSolicitudEnum.REGISTRADA, usuario, tipoTramite, 
					EstadoTramiteEnum.INICIADO, sujetoObligado, false, false);
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoObligado);
			
			//Se agregan los datos a sesion para la impresion de reporte
			session.setAttribute("idSolicitud", solicitud.getSolicitudId());
			session.setAttribute("sujetoObligado", sujetoObligado);
			log.debug("********************qwerty2"+sujetoObligado);
			result.put("mensajeExito", agregarFolioAMensaje(solicitud.getNoFolioSolicitud()));
		}else{
//			sujetoObligadoService.actualizarEscrituraConstitutiva(escrituraConstitutiva, usuario);
			log.debug("ACTUALIZAR SOLICITUD -- SE ACTUALIZARA LA SOLICITUD ACTUAL [ " + sujetoObligado + " ]");
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(Long.valueOf(idSolicitud));
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.CERRADO, sujetoObligado);
			afectar = true;
		}
		return afectar;
	}
	
	
	@RequestMapping(value = "/validaTramiteClasificacionExistente", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarExisteTramiteClasificacion(
			@RequestBody Long cveIdPatronSujetoObligado, HttpServletResponse response, HttpSession session) {
		System.err.println("Estoy validando.....");
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		boolean esTramitador = 
				usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()) 
				? true : false;
		boolean tramiteClasifExistente = solicitudServiceBusiness.existeTramitesClasificacionActivos(cveIdPatronSujetoObligado,esTramitador);
		result.put("existeTramiteClasificacion", tramiteClasifExistente);
		return result;
	
	}
	
	@RequestMapping(value = "/validarTramiteClasificacionEnCurso", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarExisteTramiteClasificacion(
			@RequestBody String numeroRegistroPatronal, HttpServletResponse response, HttpSession session) {
		System.err.println("Estoy validando.....");
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		SujetoObligado patron = sujetoObligadoService.consultarPorNumeroRegistroPatronal(numeroRegistroPatronal);
		
		boolean esTramitador = 
				usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()) 
				? true : false;
		boolean tramiteClasifExistente = solicitudServiceBusiness.existeTramitesClasificacionActivos(patron.getCveIdSujetoObligado(),esTramitador);
		result.put("existeTramiteClasificacion", tramiteClasifExistente);
		return result;
	
	}
	
	@RequestMapping(value = "/redireccionarTramite", method = RequestMethod.POST)
	public String redireccionarTramite(
			@ModelAttribute Socio socio,
			BindingResult result,
			Model model,
			@RequestParam("idTramite") String idTramite,
			@RequestParam("idSolicitud") String idSolicitud,
			HttpSession session) {
		System.err.println("redireccionarTramite invocao!!!!, socio.PatronSujetoObligado: " + socio.getCveIdPatronSujetoObligado() + ", idTramite: "  + idTramite + ", idSolicitud: " + idSolicitud);
		return "";
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 17/08/2012
	 * @param rfcParam
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/recargarTramites", method = {RequestMethod.POST, RequestMethod.GET})
	public String recargarTramites(
			Model model,
			@RequestParam String rfc,
			HttpSession session) {
		log.error("****************************\n************************************buscar Sujeto Obligado por RFC");
		String rfcParam = null;
		
		rfcParam = rfc;//(String)session.getAttribute("rfcActual");
		
		session.removeAttribute("rfcActual");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		SujetoObligado sujetoObligado = new SujetoObligado();
		
		TipoPersonaFiscal tipoPersonaFiscal = obtenerTipoPersonaFiscal(rfcParam);
		
		sujetoObligado.setTipoPersonaFiscal(tipoPersonaFiscal);
		boolean bFisica = tipoPersonaFiscal.equals(TipoPersonaFiscal.FISICA);
		if (bFisica) {
			System.err.println("RECARGAR TIPO PERSONA FISICA");
			Fisica pFisica = new Fisica();
			pFisica.setRfc(rfcParam);
			sujetoObligado.setFisica(pFisica);
		} else {
			System.err.println("RECARGAR TIPO PERSONA MORAL");
			Moral pMoral = new Moral();
			pMoral.setRfc(rfcParam);
			sujetoObligado.setMoral(pMoral);			
		}
		List<SujetoObligado> sujetosObligados;
		try {
			sujetosObligados = sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado);
			sujetoObligado = sujetosObligados.get(0); 
			sujetoObligado.setSujetosObligados(sujetosObligados);
		} catch (AbstractException e) {
			e.printStackTrace();
			return "buscar.patron.rfc";
		}
		
		model.addAttribute("sujetoObligado", sujetoObligado);
		boolean isOperadorIMSS = usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		if(!isOperadorIMSS){
			Solicitud solicitudActiva = solicitudServiceBusiness.obtenerSolicitudActiva(sujetoObligado, 
				TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, usuario, null);
			model.addAttribute("existeSolicitudActiva", solicitudActiva!=null);
			model.addAttribute("idSolicitudDatosPatronales", null);
		}else{
			model.addAttribute("existeSolicitudActiva", false);
		}
		
		
		model.addAttribute("bFisica", bFisica);
		model.addAttribute("socio", new Socio());
		model.addAttribute("representanteLegal", new RepresentanteLegal());
		model.addAttribute("isOperadosIMSS",isOperadorIMSS);
		model.addAttribute("rolPatronSujetoObligado", CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());
		model.addAttribute("isRL", usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue()));
		return "mostrar.so.afiliacion";
//		return "mostrar.sujeto.obligado";
	}
	
	@RequestMapping(value = "/validaRFCExistente", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validaRFCExistente(
			@RequestBody String rfc, HttpServletResponse response, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoObligado = new SujetoObligado();
		System.out.println("RFC: "+rfc);
		log.info("RFC: "+rfc);
		TipoPersonaFiscal tipoPersonaFiscal = obtenerTipoPersonaFiscal(rfc);
		if (tipoPersonaFiscal == null) {
			log.debug("****************************\n2************************************");
			result.put("mensajeError", "El registro federal de causantes proporcionado es incorrecto");
			return result;
		}
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
		List<SujetoObligado> sujetosObligados;
		try {
			sujetosObligados = sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado);
			if (sujetosObligados.isEmpty()) {
				result.put("mensajeError","El registro federal de causante no existe.");
				return result;
			}
			result.put("sujetoObligado", sujetosObligados.get(0));
			
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("mensajeError",mensaje);
		}

		return result;
	}
	
	@RequestMapping(value = "/crearSolicitudAfiliacion", method = RequestMethod.POST)
	public String crearSolicitud(
			@ModelAttribute SujetoObligado inputObject, Model model, HttpServletResponse response, HttpSession session) {
		
		try {
			List<SujetoObligado> rps = sujetoObligadoService
					.obtenerDetalleSujetoObligado(inputObject);
			
			Long idPatronSO = 	rps.get(0).getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) ? 
								rps.get(0).getFisica().getIdPersona() :
								rps.get(0).getMoral().getIdPersona();
					
			session.setAttribute("cveIdPatronSO",idPatronSO);
			
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
		return afiliacionController.crearSolicitud(inputObject, model, response, session);
	}
	
	private boolean cuentaRLConActosDeAdmonDominio(Long idPersona, TipoPersonaEnum tipoPersonaEnum, Usuario usuario){
		boolean result = false;
		RepresentanteLegal repLegalporIdPersonaFisica;
		
		try {
			repLegalporIdPersonaFisica = this.representanteLegalServiceBusiness.obtenerRLporTipoPatronYIdPersonaRepresentante(idPersona, tipoPersonaEnum, usuario.getFisica().getIdPersona());
			if (repLegalporIdPersonaFisica != null){
				result = repLegalporIdPersonaFisica.getIndActAdmonDominio().intValue() == 1 ? true : false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
