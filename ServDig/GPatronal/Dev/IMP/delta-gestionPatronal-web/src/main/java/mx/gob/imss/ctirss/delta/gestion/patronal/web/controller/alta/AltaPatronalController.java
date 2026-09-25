package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.alta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.beans.MedioContactoForm;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.AfiliacionController;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.ClasificacionController;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.BienDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.EquipoTransporteDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MaquinariaEquipoDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MateriaMaterialDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MedioContactoDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.PersonalDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.ProductoServicioDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.RepresentanteLegalDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.SociosDataTable;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.ItemClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/afiliacion/alta")
public class AltaPatronalController extends AbstractController{
	
	public final Logger log = Logger.getLogger(AfiliacionController.class);
	
	@Autowired
	ClasificacionController clasificacionController;
	
	@Autowired
	RepresentanteLegalAltaController representanteLegalAltaController;
	
	@Autowired
	AfiliacionServiceBusinessRemote afiliacionService;
	
	@Autowired
	SolicitudServiceBusinessRemote solicitudService;
	
	@Autowired
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String inicio(Model model, HttpSession session){
		
		SujetoObligado sujetoTramite = inicializaSujetoTramite();
		
		model.addAttribute("sujetoTramite", sujetoTramite);
		session.setAttribute("sujetoTramite", sujetoTramite);
		model.addAttribute("sujetoObligado", new SujetoObligado());
		model.addAttribute("esOperador", true);
		model.addAttribute("solicitud", new Solicitud());
		model.addAttribute("claveMun",-1);
		model.addAttribute("claveEdo",-1);
		model.addAttribute("medioContactoFormAlta", new MedioContactoForm());
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(
				RolEnum.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())){
			
			
			SujetoObligado sujetoObligado = new SujetoObligado();
			String rfc = usuario.getUsuario();
			TipoPersonaFiscal tipoPersonaFiscal = obtenerTipoPersonaFiscal(rfc);
			
			sujetoObligado.setTipoPersonaFiscal(tipoPersonaFiscal);
			boolean bFisica = tipoPersonaFiscal.equals(TipoPersonaFiscal.FISICA);
			if (bFisica) {
				Fisica pFisica = new Fisica();
				pFisica.setRfc(rfc);
				usuario.setFisica(pFisica);
				sujetoObligado.setFisica(pFisica);
			} else {
				Moral pMoral = new Moral();
				pMoral.setRfc(rfc);
				usuario.setMoral(pMoral);
				sujetoObligado.setMoral(pMoral);
			}
			List<SujetoObligado> sujetosObligados;
			try {
				sujetosObligados = sujetoObligadoService
						.obtenerDetalleSujetoObligado(sujetoObligado);
				sujetoObligado=sujetosObligados.get(0);
			}catch (GestionPatronalBusinessException gpbe) {
				gpbe.printStackTrace();
			}
			sujetoTramite.setTipoPersonaFiscal(sujetoObligado.getTipoPersonaFiscal());
			sujetoTramite.setNombreComercial(sujetoObligado.getNombreComercial());
			sujetoTramite.setFisica(sujetoObligado.getFisica());
			sujetoTramite.setMoral(sujetoObligado.getMoral());
			model.addAttribute("sujetoTramite", sujetoTramite);
			session.setAttribute("sujetoTramite", sujetoTramite);
			
			return "vista.altanrp";
		}
		return "vista.alta";
	}
	
	@RequestMapping(value="/cargarSolicitud", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargarSolicitud(Model model, @RequestParam("idSolicitud") Long idSolicitud,  HttpSession session){
		
		Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
		TramiteSujetoObligado tramiteAlta = (TramiteSujetoObligado)solicitud.getTramites().get(0);
		Integer cveEdo = 	tramiteAlta.getSujetoObligado().getMoral()!=null 
				&& tramiteAlta.getSujetoObligado().getMoral().getEscrituraConstitutiva()!=null 
				&& tramiteAlta.getSujetoObligado().getMoral().getEscrituraConstitutiva().getLugarExpedicion()!=null 
				&& tramiteAlta.getSujetoObligado().getMoral().getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa()!=null ?
				Integer.valueOf( tramiteAlta.getSujetoObligado().getMoral().getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getClave())
				: -1;
		
		Integer cveDelMun = tramiteAlta.getSujetoObligado().getMoral()!=null 
				&& tramiteAlta.getSujetoObligado().getMoral().getEscrituraConstitutiva()!=null 
				&& tramiteAlta.getSujetoObligado().getMoral().getEscrituraConstitutiva().getLugarExpedicion()!=null ?
				Integer.valueOf(tramiteAlta.getSujetoObligado().getMoral().getEscrituraConstitutiva().getLugarExpedicion().getClave()) 
				:-1;
				
		model.addAttribute("sujetoTramite", tramiteAlta.getSujetoObligado());
		//session.setAttribute("sujetoTramite", tramiteAlta.getSujetoObligado());
		model.addAttribute("sujetoObligado", new SujetoObligado());
		model.addAttribute("esOperador", true);
		model.addAttribute("solicitud", solicitud);
		model.addAttribute("claveMun",cveDelMun);
		model.addAttribute("claveEdo",cveEdo);
		// TODO: pendiente para combo de medios de contacto, marzo 7, 2013
//		model.addAttribute("idTipoMedioContacto",idTipoMedioContacto);
		
		return "vista.alta";
	}
	
	@RequestMapping(value="/actualizarSolicitud", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> actualizarInformacion(@RequestParam("idSolicitud") Long idSolicitud,
			@RequestBody SujetoObligado datosAfiliacion , Model model, HttpSession session, Locale locale){
		
		return actualizarInformacionTramiteAlta(idSolicitud, datosAfiliacion, model, session, locale);
	
	}
	
	@RequestMapping(value="/enviarSolicitud", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> enviarSolicitud(@RequestParam("idSolicitud") Long idSolicitud,
			@RequestBody SujetoObligado datosAfiliacion , Model model, HttpSession session, Locale locale){
		
		Map<String, Object> result = actualizarInformacionTramiteAlta(idSolicitud, datosAfiliacion, model, session, locale);
		Solicitud solicitud = (Solicitud)result.get("solicitud");
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo());
		for(Tramite tramite : solicitud.getTramites()){
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());
		}
		
		solicitudService.actualizarEstatus(solicitud);
		
		
		return result;
	}
	
	@RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitud(
			@RequestBody Solicitud inputObject,
			HttpServletResponse response, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();
		String mensaje = "";
		Long idSolicitud = inputObject.getSolicitudId();
		if(idSolicitud == null || idSolicitud <= 0){
			mensaje=messageSource.getMessage("error.solicitud.cancelar.inexistente", null, locale);
			result.put("mensajeError", mensaje);
			return result;
		}
		
		Solicitud solicitudCancelada = afiliacionService.cancelarSolicitud(inputObject);
		mensaje=messageSource.getMessage("msg.confirma.cancelacion.solicitud", 
				new Object[]{ solicitudCancelada.getNoFolioSolicitud()}, locale);
		result.put("mensajeExito", mensaje);
		return result;
	}
	
	@RequestMapping(value = "/cargaMediosContacto", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<MedioContacto> cargaMediosContacto(
			@RequestBody MedioContactoDataTable params, HttpSession session, Locale locale) {
		DatosSalidaPaginador<MedioContacto> output = new DatosSalidaPaginador<MedioContacto>();
		output.setAaData(new ArrayList<MedioContacto>());
		output.setiTotalDisplayRecords(0);
		output.setiTotalRecords(0);
		return output;
	}
	
	@RequestMapping(value = "/visualizarRepresentantes", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<RepresentanteLegal> visualizarRepresentantes(
			@RequestBody RepresentanteLegalDataTable params, HttpSession session, Locale locale) {
		
		return representanteLegalAltaController.visualizarRepresentantes(params, session, locale);
				
	}
	
	@RequestMapping(value = "/visualizarSocios", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<Socio> visualizarSocios(
			@RequestBody SociosDataTable params, HttpSession session, Locale locale) {
		DatosSalidaPaginador<Socio> output = new DatosSalidaPaginador<Socio>();
		output.setAaData(new ArrayList<Socio>());
		output.setiTotalDisplayRecords(0);
		output.setiTotalRecords(0);
		return output;
	}
	

	@RequestMapping(value = "/paginarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Producto> paginarProductosServicios(
			@RequestBody ProductoServicioDataTable params, HttpSession session) {
		return clasificacionController.paginarProductosServicios(params, session);
	}
	
	@RequestMapping(value = "/agregarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarProductosServicios(
			@RequestBody Producto inputObject, HttpServletResponse response,
			HttpSession session) {
			return clasificacionController.agregarProductosServicios(inputObject, response, session);
	}

	@RequestMapping(value = "/modificarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarProductosServicios(
			@RequestBody Producto inputObject, HttpServletResponse response,
			HttpSession session) {
		return clasificacionController.modificarProductosServicios(inputObject, response, session);
	}

	@RequestMapping(value = "/eliminarProductosServicios", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarProductosServicios(
			@RequestBody Producto inputObject, HttpServletResponse response,
			HttpSession session) {
		
		return clasificacionController.eliminarProductosServicios(inputObject, response, session);
	}

	
	@RequestMapping(value = "/paginarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<MateriaPrima> paginarMateriaMaterial(
			@RequestBody MateriaMaterialDataTable params, HttpSession session) {
		
		return clasificacionController.paginarMateriaMaterial(params, session);
	}
	
	@RequestMapping(value = "/agregarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarMateriaMaterial(
			@RequestBody MateriaPrima inputObject,
			HttpServletResponse response, HttpSession session) {
		
		return clasificacionController.agregarMateriaMaterial(inputObject, response, session);
	}

	@RequestMapping(value = "/modificarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarMateriaMaterial(
			@RequestBody MateriaPrima inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.modificarMateriaMaterial(inputObject, response, session);
	}

	@RequestMapping(value = "/eliminarMateriaMaterial", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarMateriaMaterial(
			@RequestBody MateriaPrima inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.eliminarMateriaMaterial(inputObject, response, session);
	}
	
	@RequestMapping(value = "/paginarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<MaquinariaEquipo> paginarMaquinariaEquipo(
			@RequestBody MaquinariaEquipoDataTable params, HttpSession session) {
		return clasificacionController.paginarMaquinariaEquipo(params, session);
	}
	
	@RequestMapping(value = "/agregarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarMaquinariaEquipo(
			@RequestBody MaquinariaEquipo inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.agregarMaquinariaEquipo(inputObject, response, session);
	}

	@RequestMapping(value = "/modificarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarMaquinariaEquipo(
			@RequestBody MaquinariaEquipo inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.modificarMaquinariaEquipo(inputObject, response, session);
	}

	@RequestMapping(value = "/eliminarMaquinariaEquipo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarMaquinariaEquipo(
			@RequestBody MaquinariaEquipo inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.eliminarMaquinariaEquipo(inputObject, response, session);
	}


	@RequestMapping(value = "/paginarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<EquipoTransporte> paginarEquipoTransporte(
			@RequestBody EquipoTransporteDataTable params, HttpSession session) {
		return clasificacionController.paginarEquipoTransporte(params, session);
	}
	
	@RequestMapping(value = "/agregarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarEquipoTransporte(
			@RequestBody EquipoTransporte inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.agregarEquipoTransporte(inputObject, response, session);
	}

	@RequestMapping(value = "/modificarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarEquipoTransporte(
			@RequestBody EquipoTransporte inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.modificarEquipoTransporte(inputObject, response, session);
	}

	@RequestMapping(value = "/eliminarEquipoTransporte", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarEquipoTransporte(
			@RequestBody EquipoTransporte inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.eliminarEquipoTransporte(inputObject, response, session);
	}

	
	@RequestMapping(value = "/paginarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Personal> paginarPersonal(
			@RequestBody PersonalDataTable params, HttpSession session) {
		return clasificacionController.paginarPersonal(params, session);
	}
	
	@RequestMapping(value = "/agregarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarPersonal(
			@RequestBody Personal inputObject, HttpServletResponse response,
			HttpSession session) {
		return clasificacionController.agregarPersonal(inputObject, response, session);
	}

	@RequestMapping(value = "/modificarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarPersonal(
			@RequestBody Personal inputObject, HttpServletResponse response,
			HttpSession session) {
		return clasificacionController.modificarPersonal(inputObject, response, session);
	}

	@RequestMapping(value = "/eliminarPersonal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarPersonal(
			@RequestBody Personal inputObject, HttpServletResponse response,
			HttpSession session) {
		return clasificacionController.eliminarPersonal(inputObject, response, session);
	}
	
	@RequestMapping(value = "/paginarBienes", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Bien> paginarBienes(@RequestBody BienDataTable params,
			HttpSession session) {
		return clasificacionController.paginarBienes(params, session);
	}
	
	@RequestMapping(value = "/agregarBien", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregarBien(@RequestBody Bien inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.agregarBien(inputObject, response, session);
	}

	@RequestMapping(value = "/modificarBien", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> modificarBien(@RequestBody Bien inputObject,
			HttpServletResponse response, HttpSession session) {
		return clasificacionController.modificarBien(inputObject, response, session);
	}

	@RequestMapping(value = "/eliminarBien", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> eliminarBien(@RequestBody Bien inputObject,
			HttpServletResponse response, HttpSession session) {
		
		return clasificacionController.eliminarBien(inputObject, response, session);
	}

	
	@RequestMapping(value = "/obtenerClasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> obtenerClasificacion(
			@RequestBody Clasificacion inputObject,
			HttpServletResponse response, HttpSession session, Locale locale) {
		return clasificacionController.obtenerClasificacion(inputObject, TipoTramiteEnum.ALTA_SRT.getCodigo(), response, session, locale);
	}
	
	
	@RequestMapping(value = "/cargarComboTipoMaquinaria", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cargarComboTipoMaquinaria(
			HttpServletResponse response) {
		return clasificacionController.cargarComboTipoMaquinaria(response);
	}
	
	@RequestMapping(value = "/cargarComboTipoCombustible", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cargarComboTipoCombustible(
			HttpServletResponse response) {
		log.info("EN EL METODO cargarComboTipoCombustible");
		return clasificacionController.cargarComboTipoCombustible(response);
	}
	

	@SuppressWarnings("rawtypes")
	public void eliminarItemLista(List lista, ItemClasificacion inputObject) {
		if (lista.contains(inputObject)) {
			lista.remove(inputObject);
		}
	}
	
	private SujetoObligado inicializaSujetoTramite(){
		SujetoObligado sujetoTramite = new SujetoObligado();
		Fisica fisica = new Fisica();
		RepresentanteLegal representanteLegalAux = new RepresentanteLegal();
		sujetoTramite.setBienes(new ArrayList<Bien>());
		sujetoTramite.setEquipos(new ArrayList<MaquinariaEquipo>());
		sujetoTramite.setEquiposTransporte(new ArrayList<EquipoTransporte>());
		sujetoTramite.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
		sujetoTramite.setPersonal(new ArrayList<Personal>());
		sujetoTramite.setProductos(new ArrayList<Producto>());
		sujetoTramite.setCuentaConTransporte(0);
		sujetoTramite.setSocios(new ArrayList<Socio>());
		sujetoTramite.setRepresentantesLegales(new ArrayList<RepresentanteLegal>());
		representanteLegalAux.setPersonaFisica(fisica);
		sujetoTramite.setRepresentanteLegalAux(representanteLegalAux);
		return sujetoTramite;
	}
	
	private TipoPersonaFiscal obtenerTipoPersonaFiscal(String rfc) {
		System.err.println("Se proceso el RFC: "+rfc);
		
		if (rfc.length() == 13)
			return TipoPersonaFiscal.FISICA;
		else if (rfc.length() == 12)
			return TipoPersonaFiscal.MORAL;
		else
			return null;
	}
	
	private Map<String, Object> actualizarInformacionTramiteAlta(Long idSolicitud,
			SujetoObligado datosAfiliacion , Model model, HttpSession session, Locale locale){
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		Solicitud solicitud = new Solicitud();
		String mensaje = null;
		String code = "";
		log.debug("Datos de solicitud de alta: "+datosAfiliacion);
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.INTERNET;
		try{
			if(idSolicitud==null || (idSolicitud!=null && idSolicitud==0)){
				log.debug("Se creara una nueva solicitud");
				solicitud = afiliacionService.crearSolicitudDeAltaPatronal(datosAfiliacion, usuario, origen);
				code="msg.confirma.creacion.solicitud";
			}else{
				log.debug("Se actualizara la solicitud");
				solicitud.setSolicitudId(idSolicitud);
				solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(solicitud, datosAfiliacion);
				code="msg.confirma.actualizacion.solicitud";
			}
		}catch(GestionPatronalBusinessException gpbe){
			mensaje=messageSource.getMessage(gpbe.getCode().toString(),null,locale);
			result.put("mensajeError", mensaje);
			return result;
		}
		mensaje = messageSource.getMessage(code,new Object[]{solicitud.getNoFolioSolicitud()},locale);
		result.put("solicitud", solicitud);
		result.put("mensajeExito", mensaje);
		
		return result;
	}
}
