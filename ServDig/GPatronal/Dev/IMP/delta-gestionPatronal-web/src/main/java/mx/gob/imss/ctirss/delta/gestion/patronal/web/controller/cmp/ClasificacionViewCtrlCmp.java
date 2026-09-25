package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.cmp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.ClasificacionController;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Hugo Martinez
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/cmp/clasificacion")
public class ClasificacionViewCtrlCmp extends AbstractController {
	
	private static final String KEY_ORIGEN_SOLICITUD = "idOrigenSolicitud";
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	@Autowired
	private ClasificacionController clasificacionController;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@RequestMapping(value="/msrt/{numeroRegistroPatronal}", method = {RequestMethod.GET, RequestMethod.POST})
	public String inicio(@PathVariable String numeroRegistroPatronal, Model model,
			HttpServletResponse response, HttpSession session, Locale locale){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		model.addAttribute("esOperador",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		SujetoObligado sujetoTramite = new SujetoObligado();
		sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronal);
		
		log.debug(":: Consulta Clasificacion msrt "+sujetoTramite.getNumeroRegistroPatronal());
		System.err.println(":: Consulta Clasificacion msrt "+sujetoTramite.getNumeroRegistroPatronal());
		
		sujetoTramite = sujetoObligadoService
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		
		Solicitud solicitudEnCaptura = solicitudServiceBusiness.obtenerSolicitudActiva(sujetoTramite, 
			TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION, usuario, null);
		if(solicitudEnCaptura!=null){
			model.addAttribute("idSolicitud",solicitudEnCaptura.getSolicitudId());
			model.addAttribute("idTipoTramite",solicitudEnCaptura.getTramites().get(0).getTipoTramite().getIdTipoTramite());
			model.addAttribute("sujetoTramite",sujetoTramite);
			return "opcionesSolicitud";
		}
		model.addAttribute("sujetoTramite",sujetoTramite);
		return "listaTramitesSRT";
	}
	
	
	@RequestMapping(value="/generarSolicitud", method = {RequestMethod.POST})
	public String generaSolicitud(@ModelAttribute SujetoObligado sujetoTramite,
			@RequestParam("idTipoTramite") Integer idTipoTramite,
			Model model,
			HttpServletResponse response, 
			HttpSession session, Locale locale){
		log.debug(":: Numero Registro Patronal para generar solicitud: "+sujetoTramite.getNumeroRegistroPatronal());
		log.debug(":: Identificador tipo tramite: "+idTipoTramite);
		System.err.println(":: Numero Registro Patronal para generar solicitud: "+sujetoTramite.getNumeroRegistroPatronal());
		System.err.println(":: Identificador tipo tramite: "+idTipoTramite);
		
		log.debug(":: Consulta Clasificacion genera solicitud "+sujetoTramite.getNumeroRegistroPatronal());
		System.err.println(":: Consulta Clasificacion genera solicitud "+sujetoTramite.getNumeroRegistroPatronal());
		sujetoTramite = sujetoObligadoService
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		sujetoTramite.getClasificacion().setSujetoObligado(sujetoTramite);
		
		sujetoTramite = inicializaInformacionActividadEconomica(sujetoTramite);
		session.setAttribute("sujetoTramite", sujetoTramite);
		session.setAttribute("tipoTramite", TipoTramiteEnum.obternerEnumById(idTipoTramite));
		
		//Map<String, ? extends Object> trModSrt = clasificacionController.guardarClasificacion(sujetoTramite.getClasificacion(), false, false, response, session, locale);
		Map<String, ? extends Object> trModSrt = clasificacionController.guardarClasificacion(inicializarObjetoDeTramite(sujetoTramite), false, false, response, session, locale);
		Long idSolicitud = (Long)trModSrt.get("idSolicitud");
		
		super.log.debug("Id solicitud creada: "+idSolicitud);
		
		return inicializa(sujetoTramite, idTipoTramite, model, idSolicitud, session);
	}
	
	private Clasificacion inicializarObjetoDeTramite(SujetoObligado sujetoTramite){
		Clasificacion clasificacion = sujetoTramite.getClasificacion();
		clasificacion.setGiro("");
		Division division = new Division();
		Grupo grupo = new Grupo();
		grupo.setDivision(division);
		Fraccion fraccion = new Fraccion();
		fraccion.setClase(new Clase());
		fraccion.setGrupo(grupo);
		clasificacion.setFraccion(fraccion);
		clasificacion.getSujetoObligado().setProceso(new Proceso());
		clasificacion.getSujetoObligado().setDesAfectacion("");
		clasificacion.getSujetoObligado().setDesUsosBienes("");
		
		return clasificacion;
	}
	
	private SujetoObligado inicializaInformacionActividadEconomica(SujetoObligado sujetoTramite){
			sujetoTramite.setBienes(new ArrayList<Bien>());
			sujetoTramite.setEquipos(new ArrayList<MaquinariaEquipo>());
			sujetoTramite.setEquiposTransporte(new ArrayList<EquipoTransporte>());
			sujetoTramite.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
			sujetoTramite.setPersonal(new ArrayList<Personal>());
			sujetoTramite.setProductos(new ArrayList<Producto>());
			sujetoTramite.setCntroTrabajo(crearCentroTrabajoVacio());
		
		
		return sujetoTramite;
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
	@RequestMapping(value="/cargarSolicitudSRT", method = {RequestMethod.POST})
	public String cargarSolicitudSRT(@ModelAttribute SujetoObligado sujetoObligado,
			@RequestParam("idTipoTramite") Integer idTipoTramite, Model model,
			@RequestParam("idSolicitud") Long idSolicitud, HttpSession session){
		return inicializa(sujetoObligado, idTipoTramite, model, idSolicitud, session);
	}
	
	public String inicializa(SujetoObligado sujetoObligado,
			Integer idTramite, Model model,
			Long idSolicitud, HttpSession session) {
		log.debug("ENTRANDO AL METODO INICIO DE CLASIFICACIONCONTROLER");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		session.setAttribute("usuario", usuario);
		model.addAttribute("esOperador",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		if (sujetoObligado.getNumeroRegistroPatronal() == null){
			if (idSolicitud != null && idSolicitud>0) {
				
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
				.obternerEnumById(idTramite);
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
		
		log.debug("El trámite quedó como: [" + session.getAttribute("showFinalizarFD") + "]");
		log.debug("CLASIFICACION ID_SOLICITUD: [" + idSolicitud + "]");
		Long idOrigenSolicitud = 2L;
		if (idSolicitud != null && idSolicitud > 0 ) {
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
			session.setAttribute("idSolicitud", sol.getSolicitudId());
			model.addAttribute("idSolicitud", sol.getSolicitudId());
			model.addAttribute("indRPCInvalido", sol.isIndRpcInvalido());
			model.addAttribute("indReintento", sol.isIndReintentoRpc());
			idOrigenSolicitud = sol.getOrigenSolicitud().getIdTipoSolicitud();
		}else{
			log.error("INICIALIZA EL SUJETO TRAMITE CON EL REGISTRO PATRONAL: "+sujetoObligado.getNumeroRegistroPatronal());
			System.err.println("INICIALIZA EL SUJETO TRAMITE CON EL REGISTRO PATRONAL: "+sujetoObligado.getNumeroRegistroPatronal());
			System.err.println("CLASIFICACION: "+sujetoObligado.getClasificacion());
			sujetoTramite.setNumeroRegistroPatronal(sujetoObligado.getNumeroRegistroPatronal());
			sujetoTramite.setModalidad(sujetoObligado.getModalidad());
			sujetoTramite.setDigVerificador(sujetoObligado.getDigVerificador());
			System.err.println("Se inicializa PSP de clasificacion: "+sujetoObligado.getClasificacion().getIndPrestaServicioPersonal());
			sujetoTramite.getClasificacion().setIndRegPatClase(sujetoObligado.getClasificacion().getIndRegPatClase());
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
		session.setAttribute(KEY_ORIGEN_SOLICITUD, idOrigenSolicitud);
		
		return "modificacionSRT";
	}

	
	
	@RequestMapping(value = "/cancelarSolicitudClasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudClasificacion(
			@ModelAttribute SujetoObligado sujetoTramite,
			@RequestParam("idSolicitud") Long idSolicitud,
			HttpServletResponse response, HttpSession session, Locale locale) {
		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("CANCELAR SOLICITUD [ " + idSolicitud + " ]");
		if (idSolicitud != null) {
			try {
				log.debug("Se cancela la solicitud [ "+ idSolicitud + " ]");
				solicitudServiceBusiness.cancelarSolicitud(idSolicitud);
				message = messageSource.getMessage("msg.cancelacion.concluida", null, locale);
				message +="<br>"+messageSource.getMessage("msg.seleccione.tramite", null, locale);
				result.put("mensajeExito", message);
			}catch(Exception e){
				message="Ocurrio un error al intentar cancelar la solicitud.";
				result.put("mensajeError", message);
			}
		}
		return result;
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

		return so;
	}

}
