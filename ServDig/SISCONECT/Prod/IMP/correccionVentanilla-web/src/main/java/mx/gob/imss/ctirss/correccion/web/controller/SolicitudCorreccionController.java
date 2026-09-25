package mx.gob.imss.ctirss.correccion.web.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.bean.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ParamSolicitudTramite;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CorrecionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.Archivo;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.FirmaElectronicaService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacionRP;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IObraService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;
import mx.gob.imss.ctirss.domiciliosInegi.web.controller.DomGeograficosController;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/solicitud/correcion")
public class SolicitudCorreccionController extends AbstractController {

	public String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	@Autowired
	private IPatronesService patronesService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private PromocionService promocionService;
	@Autowired
	private IObraService obraService;
	@Autowired
	private InvitacionService invitacionService;
	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	@Autowired
	private FirmaElectronicaService firmaElectronicaService;
	@Autowired
	private ICatalogoService<CrtInvitacionRP> invitacionRPServiceBean;

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
		
		SimpleDateFormat sf = new SimpleDateFormat(
				AbstractDocumentoElectronicoModel.FORMATO_FECHA_CADENA_ORIGINAL);

		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		UserSession user = getUsuarioFirmado(request);
		solicitud.setUsuarioFirmado(user);
		solicitud.setFechaCadenaOriginal(sf.format(new Date()));
		if (user.getCveIdPatron() != null && user.getCveIdPatron() != 0) {
			SatPatron patron = this.patronesService.getById(user
					.getCveIdPatron());
			solicitud.setPatronPrincipal(patron);
			solicitud.setPatron(patron.getRegistroPatronal());
		}
		model.addAttribute("crcSolicitud", solicitud);
		model.addAttribute(new DgDomicilioGeografico());
		removeDomicilioInegiSession(request);
		limpiaSession(request.getSession());

		/**
		 * Se inhibe la vista de 'Solicitud de correccion' en el menu
		 * @author eliseo.bonifacio
		 * @date 12/04/2021
		 * Ticket 4209472
		 */
		return determinaURL(request, "error/error", "solicitudCorreccion/main/patron");
		
	}

	@RequestMapping(value = "/consultarDom", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr consultarDom(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();

		SatPatron pat = this.patronesService.validaRegistroPatronalWS(
				patron.getPatron(), true);
		// removeDomicilioInegiSession(request);

		if (pat == null)
			return null;
		patron.setPatronPrincipal(pat);
		session.setAttribute("patronPrincipal", patron.getPatronPrincipal());
		return patron;
	}

	public void validaPromocionInvitacion(CrtSolicitudcorr crtSolicitud,
			HttpSession session) {
		// CrtInvitacion invitacion = null;
		// CrtPromocion promocion = null;
		CrtSolicitudcorr crtSolicitudTemp = null;

		SatPatron patCorr = (SatPatron) session.getAttribute("patronCorregir");
		if (patCorr != null) {
			crtSolicitudTemp = validaInvitacion(crtSolicitud);

			if (crtSolicitudTemp.getInvitacion() != null) {
				session.setAttribute("invitacionEncontrada",
						crtSolicitudTemp.getInvitacion());
			} else {
				crtSolicitudTemp = validaPromocion(crtSolicitud);
				if (crtSolicitudTemp.getPromocion() != null) {
					session.setAttribute("promocionEncontrada",
							crtSolicitudTemp.getPromocion());
				}
			}
		}

		if (crtSolicitudTemp!=null && crtSolicitudTemp.getInvitacion() == null
				&& crtSolicitudTemp.getPromocion() == null) {
			List patrones = (ArrayList) session.getAttribute("patrones");
			if (patrones != null && patrones.size() > 0) {
				Iterator i = patrones.iterator();
				while (i.hasNext()) {
					SatPatron patIns = (SatPatron) i.next();
					crtSolicitudTemp = validaInvitacion(crtSolicitud);
					if (crtSolicitudTemp.getInvitacion() != null) {
						session.setAttribute("invitacionEncontrada",
								crtSolicitudTemp.getInvitacion());
					} else {
						crtSolicitudTemp = validaPromocion(crtSolicitud);
						if (crtSolicitudTemp.getPromocion() != null) {
							session.setAttribute("promocionEncontrada",
									crtSolicitudTemp.getPromocion());
						}
					}
				}
			}
			if (crtSolicitudTemp.getInvitacion() == null)
				session.removeAttribute("invitacionEncontrada");
			if (crtSolicitudTemp.getPromocion() == null)
				session.removeAttribute("promocionEncontrada");
		}
	}

	public Calendar getDay(int year) {
		Calendar c = Calendar.getInstance();
		c.set(c.HOUR_OF_DAY, 0);
		c.set(c.MINUTE, 0);
		c.set(c.SECOND, 0);
		if (year > 0) {
			c.set(c.YEAR, (c.get(c.YEAR) - year));
			c.set(c.MONTH, c.JANUARY);
			c.set(c.DAY_OF_MONTH, 1);
		}
		return c;
	}

	private CrtSolicitudcorr validaInvitacion(CrtSolicitudcorr crtSolicitud) {

		Date periodoInicial = null;
		Date periodoFinal = null;
		if (crtSolicitud.getFecFechaPeriodoIni() == null
				&& crtSolicitud.getFecFechaPeriodoFin() == null) {
			periodoInicial = getDay(2).getTime();
			periodoFinal = getDay(0).getTime();
		} else {
			periodoInicial = crtSolicitud.getFecFechaPeriodoIni();
			periodoFinal = crtSolicitud.getFecFechaPeriodoFin();
		}

		CrtInvitacion invitacion = invitacionService.validaInvitacionExistente(
				crtSolicitud.getPatronCorregir().getCvePK().longValue(),
				periodoInicial, periodoFinal);

		if (invitacion != null) {
			crtSolicitud.setFecFechaPeriodoIni(periodoInicial);
			crtSolicitud.setFecFechaPeriodoFin(periodoFinal);

		}

		crtSolicitud.setInvitacion(invitacion);

		return crtSolicitud;
	}

	private CrtSolicitudcorr validaPromocion(CrtSolicitudcorr crtSolicitud) {

		Date periodoInicial = null;
		Date periodoFinal = null;

		if (crtSolicitud.getFecFechaPeriodoIni() == null
				&& crtSolicitud.getFecFechaPeriodoFin() == null) {
			periodoInicial = getDay(2).getTime();
			periodoFinal = getDay(0).getTime();
		} else {
			periodoInicial = crtSolicitud.getFecFechaPeriodoIni();
			periodoFinal = crtSolicitud.getFecFechaPeriodoFin();
		}

		CrtPromocion promocion = promocionService.validaPromocionExistente(
				crtSolicitud.getPatronCorregir().getCvePK().longValue(),
				periodoInicial, periodoFinal);

		if (promocion != null) {
			crtSolicitud.setFecFechaPeriodoIni(periodoInicial);
			crtSolicitud.setFecFechaPeriodoFin(periodoFinal);

		}

		crtSolicitud.setPromocion(promocion);

		return crtSolicitud;
	}

	
	@RequestMapping(value = "/consultarRolUsuario", method = RequestMethod.POST)
	public @ResponseBody
	UserSession consultaRolUsuario( HttpServletResponse response, HttpServletRequest request) {
		
		UserSession user = getUsuarioFirmado(request);

		return user;
	}
	
	
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr consultar(@RequestBody CrtSolicitudcorr patron, HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Enumeration<?> mas = request.getAttributeNames();
		
		while (mas.hasMoreElements()){
			String key = (String)mas.nextElement();
			System.out.println(" ----------> key -" + key + "- Valor -->" + request.getAttribute(key) + "<--");
		}
		
		removeDomicilioInegiSession(request);
		limpiaSession(request.getSession());

		SatPatron pat = this.patronesService.validaRegistroPatronalWS(
				patron.getPatron(), true);
		UserSession user = getUsuarioFirmado(request);

		if (pat == null)
			return null;

		if (!user.getCveCodigoDelegacion().equals(
				pat.getUbicacion().getMunicipio().getSacSubdelegacion()
						.getSacDelegacion().getCveCodigo())
				|| !user.getCveCodigoSubDelegacion().equals(
						pat.getUbicacion().getMunicipio().getSacSubdelegacion()
								.getCveCodigo())) {
			patron = new CrtSolicitudcorr();
			patron.setError("El Registro Patronal no pertenece a la subdelegación del usuario firmado en el sistema");
			return patron;
		} else {
			patron.setError("");
		}

		patron.setPersonaFisica(validaTipoPersona(pat.getRfc()));
		patron.setPatronCorregir(pat);
		patron.setTipo("ESPONTANEA");
		patron = validaInvitacion(patron);

		if (patron.getInvitacion() != null) {

			patron.setFechaRecepcionOficio(Functions.dateToString(patron
					.getInvitacion().getFecFechaoficioinv()));
			patron.setFolioInvitacion(patron.getInvitacion()
					.getNuFolioInvitacion());
			patron.setTipo("INVITACION");
			patron.setCveAuditorAsignado((patron.getInvitacion()
					.getCveAuditorAsignado() != null) ? patron.getInvitacion()
					.getCveAuditorAsignado() : "");

			CrtInvitacionRP crtInvitacionRP = new CrtInvitacionRP();
			crtInvitacionRP.setCveInvitacion(Long.parseLong(patron
					.getInvitacion().getCveInvitacion().toString()));
			List<CrtInvitacionRP> lsRpsInscritos = invitacionRPServiceBean
					.consultar(crtInvitacionRP);

			if (lsRpsInscritos != null && !lsRpsInscritos.isEmpty()) {
				patron.setUnoVariosRp(CrtSolicitudcorr.SOLICITUD_VARIOS_RP);
				patron.setLsPatronesInscInvitacion(lsRpsInscritos);

			} else {
				patron.setUnoVariosRp(CrtSolicitudcorr.SOLICITUD_UN_RP);
			}

			session.setAttribute("invitacionEncontrada", patron.getInvitacion());

		} else {
			patron = validaPromocion(patron);
			if (patron.getPromocion() != null) {
				patron.setFechaRecepcionOficio(Functions.dateToString(patron
						.getPromocion().getFecFechanotif()));
				patron.setTipo("PROMOCION");
				patron.setCveAuditorAsignado((patron.getPromocion()
						.getCveAuditorAsignado() != null) ? patron
						.getPromocion().getCveAuditorAsignado() : "");
				session.setAttribute("promocionEncontrada",
						patron.getPromocion());
			}
			
			patron.setFecFechaPeriodoFin(getDay(0).getTime());
			patron.setFecFechaPeriodoIni(getDay(1).getTime());
			
		}
		patron.setFecFechaElacoracionCorreccion(new Date());

		if (patron.getPatronCorregir().getRfc() != null
				&& !patron.getPatronCorregir().getRfc().equals(""))
			patron.getPatronCorregir().setRfc(
					patron.getPatronCorregir().getRfc().trim());
		if (patron.getPatronCorregir().getCurp() != null
				&& !patron.getPatronCorregir().getCurp().equals(""))
			patron.getPatronCorregir().setCurp(
					patron.getPatronCorregir().getCurp().trim());

		session.setAttribute("patronCorregir", pat);
		session.setAttribute("fechaLimiteInicial",
				patron.getFecFechaPeriodoIni());
		session.setAttribute("fechaLimiteFinal", patron.getFecFechaPeriodoFin());
		return patron;
	}

	private boolean validaTipoPersona(String rfc) {
		if (rfc != null && rfc.length() > 0) {
			if (alfabeto.indexOf(rfc.toUpperCase().charAt(0)) >= 0) {
				if (alfabeto.indexOf(rfc.toUpperCase().charAt(1)) >= 0) {
					if (alfabeto.indexOf(rfc.toUpperCase().charAt(2)) >= 0) {
						if (alfabeto.indexOf(rfc.toUpperCase().charAt(3)) >= 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@RequestMapping(value = "/consultarObra", method = RequestMethod.POST)
	public @ResponseBody
	SatObra consultarObra(@RequestBody CrtSolicitudcorr patron, HttpServletResponse response, HttpServletRequest request) {

		Enumeration<?> mas = request.getAttributeNames();
		
		while (mas.hasMoreElements()){
			String key = (String)mas.nextElement();
			System.out.println(" ----------> key -" + key + "- Valor -->" + request.getAttribute(key) + "<--");
		}
		
		UserSession user = getUsuarioFirmado(request);
		String ssdd = user.getCveCodigoDelegacion().length() < 2 ? ("0" + user
				.getCveCodigoDelegacion()) : user.getCveCodigoDelegacion();
		ssdd += user.getCveCodigoSubDelegacion().length() < 2 ? ("0" + user
				.getCveCodigoSubDelegacion()) : user
				.getCveCodigoSubDelegacion();

		SatObra obra = new SatObra();

//		if (!patron.getNumeroObra().substring(4, 8).equals(ssdd)) {
//			obra.setError("El n\u00famero de obra no pertenece a la Delegaci\u00f3n/Subdelegación del Registro Patronal a Corregir");
//			return obra;
//		}

		obra = this.obraService.validaObra(patron.getNumeroObra());

		if (obra != null) {
			SatPatron pat = (SatPatron) request.getSession().getAttribute(
					"patronCorregir");
			if (pat != null
					&& obra.getCveFkPatron() != null
					&& pat.getCvePK().intValue() == obra.getCveFkPatron()
							.intValue()) {

				Hashtable<String, Object> ht = (Hashtable) getDomicilioInegiSession(request);

				if (ht != null) {
					ht.remove("DOM_OBRA");
					setDomicilioInegiSession(ht, request);
				}

				request.getSession().setAttribute("ObraSol", obra);

				return obra;
			} else
				return null;
		} else {
			request.getSession().removeAttribute("ObraSol");
		}
		return obra;
	}

	@RequestMapping(value = "/paginar", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<SatPatron> pagina(
			@RequestBody CorrecionWrapperDataTable aoData,
			HttpServletRequest request) {

		DatosEntradaPaginador send = new DatosEntradaPaginador();

		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		HttpSession session = request.getSession();

		List patrones = new ArrayList();
		if (session.getAttribute("patrones") != null) {
			patrones = (List) session.getAttribute("patrones");
		}

		DatosSalidaPaginador<SatPatron> reply = this.solicitudService
				.pagina(patrones);
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());

		return reply;

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr add(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List patrones = new ArrayList();

		if (session.getAttribute("patrones") != null) {
			patrones = (List) session.getAttribute("patrones");
		}
		String rpmy = patron.getPatron();
		if (patrones != null && patrones.size() > 0 && patron != null) {
			for (int i = 0; i < patrones.size(); i++) {
				SatPatron pat = (SatPatron) patrones.get(i);
				String hopat = pat.getRegistroPatronal().substring(0,
						pat.getRegistroPatronal().length() - 1);
				if (hopat.equals(rpmy)) {
					return null;
				}
			}
		}

		SatPatron patronValidado = this.patronesService
				.validaRegistroPatronalWS(patron.getPatron(), false);

		if (patronValidado != null) {
			patrones = this.solicitudService
					.addPatron(patrones, patronValidado);
			session.setAttribute("patrones", patrones);
			patron.setTipo("ESPONTANEA");
			patron.setPatronCorregir(patronValidado);
			validaPromocionInvitacion(patron, session);
			CrtInvitacion invitacion = (CrtInvitacion) session
					.getAttribute("invitacionEncontrada");
			if (invitacion != null) {
				patron.setFechaRecepcionOficio(Functions
						.dateToString(invitacion.getFecFechaoficioinv()));
				patron.setTipo("INVITACION");
				patron.setFecFechaPeriodoFin(getDay(0).getTime());
				patron.setFecFechaPeriodoIni(getDay(2).getTime());
				patron.setFolioInvitacion(invitacion.getNuFolioInvitacion());
			} else {
				patron.setFecFechaPeriodoFin(getDay(0).getTime());
				patron.setFecFechaPeriodoIni(getDay(1).getTime());
				CrtPromocion promocion = (CrtPromocion) session
						.getAttribute("promocionEncontrada");
				if (promocion != null) {
					patron.setFechaRecepcionOficio(Functions
							.dateToString(promocion.getFecFechanotif()));
					patron.setTipo("PROMOCION");
				}
			}
			return patron;
		} else
			return null;
	}

	//

	@RequestMapping(value = "/addRPInvitacion", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr addRPInvitacion(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List patrones = new ArrayList();

		if (session.getAttribute("patrones") != null) {
			patrones = (List) session.getAttribute("patrones");
		}
		SatPatron patronValidado = this.patronesService.getById(Long
				.parseLong(patron.getPatron()));

		patrones = this.solicitudService.addPatron(patrones, patronValidado);
		session.setAttribute("patrones", patrones);

		return patron;
	}

	//

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr del(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List patrones = new ArrayList();
		if (session.getAttribute("patrones") != null) {
			patrones = (List) session.getAttribute("patrones");
		}
		patrones = this.solicitudService
				.delPatron(patrones, patron.getPatron());
		session.setAttribute("patrones", patrones);
		patron.setTipo("ESPONTANEA");
		/*
		 * validaPromocionInvitacion(patron,session); CrtInvitacion invitacion =
		 * (CrtInvitacion)session.getAttribute("invitacionEncontrada");
		 * if(invitacion!=null) {
		 * patron.setFechaRecepcionOficio(Functions.dateToString
		 * (invitacion.getFecFechaoficioinv())); patron.setTipo("INVITACION");
		 * patron.setFecFechaPeriodoFin(getDay(0).getTime());
		 * patron.setFecFechaPeriodoIni(getDay(2).getTime());
		 * patron.setFolioInvitacion(invitacion.getNuFolioInvitacion()); } else
		 * { patron.setFecFechaPeriodoFin(getDay(0).getTime());
		 * patron.setFecFechaPeriodoIni(getDay(1).getTime()); CrtPromocion
		 * promocion =
		 * (CrtPromocion)session.getAttribute("promocionEncontrada");
		 * if(promocion!=null) {
		 * patron.setFechaRecepcionOficio(Functions.dateToString
		 * (promocion.getFecFechanotif())); patron.setTipo("PROMOCION"); } }
		 */
		return patron;

	}

	@RequestMapping(value = "/obtenerMesajeTramite", method = RequestMethod.POST)
	public @ResponseBody
	CrcTramiteMensajes obtenerMesajeTramite(@RequestBody CrcTramiteMensajes mensaje,
			HttpServletResponse response, HttpServletRequest request) {	
		mensaje=firmaElectronicaService.recuperaMensaje(mensaje);
		return mensaje;
	}
	
	
	@RequestMapping(value = "/recuperaFolioTemporal", method = RequestMethod.POST)
	public @ResponseBody
	CrtNroFolio  recuperaFolioTemporal(@RequestBody CrtSolicitudcorr solicitud,
			HttpServletResponse response, HttpServletRequest request) {
		System.out.println("Recuperando folio temporal Controler Ventanilla");
		String folio;
		HttpSession session = request.getSession();
		solicitud.setPatronCorregir((SatPatron) session
				.getAttribute("patronCorregir"));
		TipoCorreccion tipoCorreccion = null;
		
		if(solicitud.getIdTipoSolicitud().equals(CrtSolicitudcorr.SOLICITUD_TIPO_CONSTRUCCION)){
			if(solicitud.getNumeroObra()!=null && !solicitud.getNumeroObra().equals("")){
				solicitud.setCveNumeroRegObra(new BigDecimal(solicitud.getNumeroObra()));
			}
			
			tipoCorreccion = TipoCorreccion.SOLICITUD_CORRECCION_ESPONTANEA_CONSTRUCCION;
		}else{
			tipoCorreccion = TipoCorreccion.SOLICITUD_CORRECCION_ESPONTANEA;
		}
		System.out.println("Se procede generar folio temporal");
		if(solicitud.getNuFolio()!=null && !solicitud.getNuFolio().equals("") && tipoCorreccion!=TipoCorreccion.SOLICITUD_CORRECCION_ESPONTANEA_CONSTRUCCION){
			System.out.println("Es folio de SOLICITUD_CORRECCION_ESPONTANEA_CONSTRUCCION");
			CrtNroFolio fol=new CrtNroFolio();
			fol.setNumFolio(solicitud.getNuFolio());
			return fol;
		}
		System.out.println("GenerarTemporal");
		folio=solicitudService.generaFolioTemporal(solicitud, tipoCorreccion);
		CrtNroFolio fol=new CrtNroFolio();
		fol.setNumFolio(folio);		
		return fol;
		
	}
	
	
	
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr guardar(@RequestBody CrtSolicitudcorr solicitud,
			HttpServletResponse response, HttpServletRequest request) {
		try {

			HttpSession session = request.getSession();
			UserSession userSession = getUsuarioFirmado(request);
			solicitud.setUsuarioFirmado(userSession);
			String acusePDF="";
			List patrones = new ArrayList();
			
			
			

			if (solicitud.getUnoVariosRp() == CrtSolicitudcorr.SOLICITUD_VARIOS_RP) {
				if (session.getAttribute("patrones") != null) {
					patrones = (List) session.getAttribute("patrones");
					
					if (patrones.isEmpty()) {
						solicitud
								.setError("No se han incluido los registros patronales inscritos");
						return solicitud;
					}
				} else {
					solicitud
							.setError("No se han incluido los registros patronales inscritos");
					return solicitud;
				}
			}

			if (solicitud.getNuFolio() != null
					&& !solicitud.getNuFolio().equals("")
					&& solicitud.getNuFolio().contains("CCI")) {
				solicitud
						.setIdTipoSolicitud(CrtSolicitudcorr.SOLICITUD_TIPO_CONSTRUCCION);
			}

			solicitud.setPatrones(patrones);
			solicitud.setPatronPrincipal((SatPatron) session
					.getAttribute("patronPrincipal"));
			solicitud.setPatronCorregir((SatPatron) session
					.getAttribute("patronCorregir"));
			solicitud
					.setDomicilios((Hashtable) getDomicilioInegiSession(request));
			solicitud.setTipo("ESPONTANEA");
			solicitud.setCveTipoCorreccion(1);
			// Date fecLim = (Date)session.getAttribute("fechaLimiteInicial");
			/*
			 * if(fecLim!=null&&solicitud.getFecFechaPeriodoIni().getTime()>
			 * Functions.addDayToDate(fecLim, 1).getTime()) {
			 * solicitud.setError(
			 * "La fecha Incial no puede ser mayor a "+Functions
			 * .dateToString(fecLim)); return solicitud; }
			 */
			if (solicitud.getTipoObra() != null
					&& solicitud.getTipoObra().equals("true")) {
				if (request.getSession().getAttribute("ObraSol") != null) {
					SatObra obra = (SatObra) request.getSession().getAttribute(
							"ObraSol");
					solicitud.setNumeroObra(obra.getCveNroregobra() + "");
				}
			}

			if (validaInformacionCompleta(solicitud)) {
				if (validaDomicilios(solicitud)) {
					if (validaSolicitud(solicitud)) {
						if(solicitudService.validaSolicitud(solicitud)){
							solicitud
							.setError("Existe una solicitud previa para el patron "
									+ solicitud.getPatronCorregir()
											.getRegistroPatronalSD());
								return solicitud;
						}
						
						
						
						UserSession user = getUsuarioFirmado(request);
						if (user.isPatron()) {
							solicitud.setInternet("PRESENTADO POR INTERNET");
						}
//						solicitud.setCveUsuario(user.getCveIdUsuario() + "");
						solicitud.setCveUsuario(user.getCurpUsuario());

						if (session.getAttribute("promocionEncontrada") != null) {
							solicitud.setPromocion((CrtPromocion) session
									.getAttribute("promocionEncontrada"));
							if (!validaCorrecion(solicitud)) {
								solicitud
										.setError("Existe una solicitud previa para el patron "
												+ solicitud
														.getPatronCorregir()
														.getRegistroPatronalSD());
								return solicitud;
							}

							solicitud.setTipo("PROMOCION");
							solicitud
									.setCveTipoCorreccion(solicitud
											.getPromocion().getCveTipocorr()
											.intValue());
							solicitud.getPromocion().setFecFechaAtencion(
									new Date());
							solicitud.getPromocion().setCveUsuario(
									userSession.getCurpUsuario() + "");
							solicitud.getPromocion().setFecFechareg(new Date());
							solicitud
									.getPromocion()
									.setCveEstatus(
											CatEstatus.PROSESO_DE_PRESENTACION_DE_LA_SOLICITUD_DE_CORRECCION
													.getId());
							solicitud.getPromocion().setFecInicialDictamen(
									solicitud.getFecFechaPeriodoIni());
							solicitud.getPromocion().setFecFinalDictamen(
									solicitud.getFecFechaPeriodoFin());

						}
						if (session.getAttribute("invitacionEncontrada") != null) {
							solicitud.setInvitacion((CrtInvitacion) session
									.getAttribute("invitacionEncontrada"));
							solicitud.setTipo("INVITACION");
							solicitud.setCveTipoCorreccion(new Integer(2));

							solicitud.getInvitacion()
									.setFecAtencion(new Date());
							solicitud.getInvitacion().setCveUsuario(
									userSession.getCurpUsuario() + "");
							solicitud.getInvitacion()
									.setFecFechareg(new Date());
							solicitud.getInvitacion()
									.setFechaInicioCorreccionTx(
											solicitud.getFechaInicial());
							solicitud.getInvitacion().setFechaFinCorreccionTx(
									solicitud.getFechaFinal());
						}

						if((userSession.getCveRol()==ConstantesBusiness.ROL_USER_INTERNET)){
							System.out.println("Solicitud de correcion desde internet");
							solicitud.setIdFormaPresenta(1);
						}else{
							System.out.println("Solicitud de correcion desde subdelegacion");
							solicitud.setIdFormaPresenta(0);
						}
						
						solicitud = solicitudService.guardar(solicitud);
						
						if(solicitud==null){
							solicitud=new CrtSolicitudcorr();
							solicitud.setError("No se pudo recuperar el folio correspondiente, favor de intentar nuevamente");
							return solicitud;
						}

						if (solicitud.getCveSolicitudCorr() != null
								&& solicitud.getCveSolicitudCorr() > 0) {
							solicitud.setError("");
						}
						session.removeAttribute("invitacionEncontrada");
						session.removeAttribute("promocionEncontrada");

						 //firmaElectronicaService.validarCertificado(solicitud);
						if (solicitud.getUnoVariosRp().intValue() == CrtSolicitudcorr.SOLICITUD_UN_RP) {
							acusePDF=generaReporte(session,
									obtenParametrosReporte(solicitud), true);
						} else {
							acusePDF=generaReporte(session,
									obtenParametrosReporte(solicitud), false);
						}
						//valores de la firma
						CrcTramiteMensajes ms= solicitud.getMensaje();
						CrtTramitePresentado pres=new CrtTramitePresentado();
						if(ms!=null)
						pres.setCveMensaje(ms.getCveMensaje());
						pres.setCveSolcorr(Long.valueOf(solicitud.getCveSolicitudCorr()));
						if(ms!=null)
						pres.setCveTramite(ms.getCveTramite());
						pres.setIdTramiteRefNotaria(solicitud.getFirmaElectronica());
						pres.setUrlAcuseFirma(solicitud.getUrlAcuseFirma());
						//pres.setCveUsuarioCurp("CURPSEssion");
						pres.setFecFechaReg(Calendar.getInstance().getTime());
						pres.setDesRegPatronal(solicitud.getPatronCorregir().getRegistroPatronal());
					    pres.setDesRazonSocial(solicitud.getPatronCorregir().getRazonSocial());
						firmaElectronicaService.guardarTramitePresentado(pres);
						/*Se registra el tramite del lado de la BDTU*/
						// 1.-Se recupera el sugeto obligado a partir del Registro Patronal
						// 2.-Se genera la estructura del parametro
						SujetoObligado sujetoObligado=firmaElectronicaService.recuperaSujetoObligado(solicitud);
						ParamSolicitudTramite parametro=firmaElectronicaService.generaParametro(solicitud, pres,sujetoObligado);
						Solicitud soli=firmaElectronicaService.generaNuevaSolicitudTramite(parametro);
						soli=firmaElectronicaService.guardarSolicitudTramite(soli, parametro);	
						firmaElectronicaService.cerrarSolicitudBDTU(soli.getSolicitudId());
						System.out.println("Cerrando solicitud BDTU al Iniciar proceso");
						solicitud.setCveIdSolicitudBDTU(soli.getSolicitudId().intValue());

						System.out.println("Actulizar correccion datos BDTU");
						solicitudService.actualizar(solicitud);												
						
						
						//Termina Registro tramite
						
						if(solicitud.getFirmaElectronica()!=null && !solicitud.getFirmaElectronica().equals("")){
							Archivo archivo=new Archivo();
							archivo.setNombre("solicitudDeCorrreccion.pdf");
							archivo.setBuffer(acusePDF);							
							firmaElectronicaService.guardarArchivoFirmado(solicitud.getFirmaElectronica(), archivo);
						}
						
						
					} else {
						solicitud
								.setError("Existe una solicitud previa para el patron "
										+ solicitud.getPatronCorregir()
												.getRegistroPatronalSD());
						return solicitud;
					}
				} else {
					solicitud
							.setError("Existe registro patron sin domicilio corregido");
					return solicitud;
				}
			} else {
				solicitud
						.setError("La información proporcionada en patrones inscritos esta inclompleta");
				return solicitud;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		/*
		 * catch (ServicioRemotoNoDisponibleException e) {
		 * solicitud.setError(e.getMessage()); e.printStackTrace(); return
		 * solicitud; } catch (CertificadoInvalidoException e) {
		 * solicitud.setError(e.getMessage()); e.printStackTrace(); return
		 * solicitud; }
		 */
		return solicitud;
	}
	
	
	@RequestMapping(value = "/recuperaSelloDigital", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr recuperaSelloDigital(@RequestBody CrtSolicitudcorr solicitud,
			HttpServletResponse response, HttpServletRequest request) {
		
		try{
			RespuestaFirmadoSimple sello=firmaElectronicaService.getSelloDigital(solicitud);
			if(sello!=null){
				solicitud.setSelloIMSS(sello.getSello());
				solicitud.setRespuestaObjetoFirmadoSimple(sello);	
			}else{
				System.out.println("No se pudo recuperar el sello");
			}
			
			System.out.println("Sello Digital "+sello);
		}catch(Exception e){
			e.printStackTrace();
			solicitud.setSelloIMSS("-1");
		}
		return solicitud;
	}
	
	
	

	private boolean validaCorrecion(CrtSolicitudcorr solicitudcorr) {
		boolean res = true;

		if (!(solicitudcorr.getFecFechaPeriodoFin() != null
				&& solicitudcorr.getPromocion().getFecInicialDictamen() != null
				&& solicitudcorr.getFecFechaPeriodoIni() != null && solicitudcorr
				.getPromocion().getFecFinalDictamen() != null)) {
			return res;
		}
		if ((solicitudcorr.getFecFechaPeriodoFin().compareTo(
				solicitudcorr.getPromocion().getFecInicialDictamen()) > 0 && solicitudcorr
				.getFecFechaPeriodoFin().compareTo(
						solicitudcorr.getPromocion().getFecFinalDictamen()) < 0)
				|| (solicitudcorr.getFecFechaPeriodoIni().compareTo(
						solicitudcorr.getPromocion().getFecInicialDictamen()) > 0 && solicitudcorr
						.getFecFechaPeriodoIni().compareTo(
								solicitudcorr.getPromocion()
										.getFecFinalDictamen()) < 0)) {
			res = false;
		}
		if ((solicitudcorr.getPromocion().getFecInicialDictamen()
				.compareTo(solicitudcorr.getFecFechaPeriodoIni()) > 0 && solicitudcorr
				.getPromocion().getFecInicialDictamen()
				.compareTo(solicitudcorr.getFecFechaPeriodoFin()) < 0)
				|| (solicitudcorr.getPromocion().getFecFinalDictamen()
						.compareTo(solicitudcorr.getFecFechaPeriodoIni()) > 0 && (solicitudcorr
						.getPromocion().getFecFinalDictamen()
						.compareTo(solicitudcorr.getFecFechaPeriodoFin()) < 0))) {
			res = false;
		}
		return res;
	}

	@RequestMapping(value = "/obtenerFechaServidor.do", method = RequestMethod.POST)
	public @ResponseBody
	String obtenerFechaServidor(HttpServletRequest request) {
		String date = ConstantesBusiness.dateToStringFormat(new Date(),
				ConstantesBusiness.DD_MM_YYYY);

		date = date.replaceAll("/", "-");

		return date;
	}

	// VALIDA QUE NO EXISTA UNA SOLICITUD DE CORRECCION ORDINARIA EN EL MISMO
	// PERIODO DE LA QUE SE INTENTA GENERAR
	// Y SI ES UNA SOLICITUD DE LA CONSTRUCCION VALIDA QUE SI HACE REFERENCIA A
	// LA MISMA OBRA NO SE TRANSLAPEN LOS PERIODOS
	// SI EN NUMERO DE OBRA ES DIFERENTE LAS SOLICITUDES DE CORRECCION SE PUEDEN
	// GENERAR SIN PROBLEMA ALGUNO
	public boolean validaSolicitud(CrtSolicitudcorr sol) {

		@SuppressWarnings("rawtypes")
		boolean res = true;
		List<CrtSolicitudcorr> result = solicitudService
				.validaSolicitudResult(sol);
		// Caso CCI y CI se comprueba que no choque los periodos
//		if ((sol.getNuFolio().contains("CCI") || sol.getNuFolio()
//				.contains("CI")) && sol.getNumeroObra() != null) {
		if ((sol.getNuFolio()
				.contains("CI")) && sol.getNumeroObra() != null) {

			for (CrtSolicitudcorr solicitud : result) {
				if ((sol.getFecFechaPeriodoFin().compareTo(
						solicitud.getFecFechaPeriodoIni()) > 0 && sol
						.getFecFechaPeriodoFin().compareTo(
								solicitud.getFecFechaPeriodoFin()) < 0)
						|| (sol.getFecFechaPeriodoIni().compareTo(
								solicitud.getFecFechaPeriodoIni()) > 0 && sol
								.getFecFechaPeriodoIni().compareTo(
										solicitud.getFecFechaPeriodoFin()) < 0)) {
					res = false;
				}

				if ((solicitud.getFecFechaPeriodoIni().compareTo(
						sol.getFecFechaPeriodoIni()) > 0 && solicitud
						.getFecFechaPeriodoIni().compareTo(
								sol.getFecFechaPeriodoFin()) < 0)
						|| (solicitud.getFecFechaPeriodoFin().compareTo(
								sol.getFecFechaPeriodoIni()) > 0 && (solicitud
								.getFecFechaPeriodoFin().compareTo(
										sol.getFecFechaPeriodoFin()) < 0))) {
					res = false;
				}
			}
		} else if ((sol.getNuFolio().contains("CCE") || sol.getNuFolio().contains("CCI"))
				&& sol.getNumeroObra() != null && !result.isEmpty()) {
			res = false;
		}
		System.out.println("Valor devuelto Validacion Correccion "+res);
		return res;
	}

	// public boolean validaSolicitud(CrtSolicitudcorr sol){
	//
	// @SuppressWarnings("rawtypes")
	// List result = solicitudService.validaSolicitudResult(sol);
	//
	//
	// if(sol.getTipoObra().equals("true")){
	// if(result.size()>0){
	// Iterator i = result.iterator();
	// while(i.hasNext()){
	// CrtSolicitudcorr solicitud = (CrtSolicitudcorr)i.next();
	// System.out.println("Valor del numObra "+solicitud.getNumeroObra());
	// if(solicitud.getNumeroObra()!=null&&
	// solicitud.getNumeroObra().equals(sol.getNumeroObra())){
	// return true;
	// }
	// }
	// }else{
	// return false;
	// }
	// }else{
	// if(result!=null&&result.size()>0){
	// return true;
	// }else{
	// return false;
	// }
	// }
	// return false;
	// }

	private boolean validaDomicilios(CrtSolicitudcorr solicitud) {
		
		if (!existeDomicilioInegi(solicitud.getDomicilios(), solicitud
				.getPatronCorregir().getRegistroPatronalSD()+"C")){//La C indica que es el centro de trabajo
			
			
			
			solicitud.getDomicilios().put(solicitud
				.getPatronCorregir().getRegistroPatronalSD()+"C", solicitud.getDomicilios().get(solicitud
				.getPatronDom()+"F"));
			
			
			
			return true;
		}else {
			Iterator i = solicitud.getPatrones().iterator();
			while (i.hasNext()) {
				SatPatron pat = (SatPatron) i.next();
				if (!existeDomicilioInegi(solicitud.getDomicilios(),
						pat.getRegistroPatronalSD()))
					return false;
			}
			if (!solicitud
					.getPatronCorregir()
					.getRegistroPatronalSD()
					.equals(solicitud.getPatronPrincipal()
							.getRegistroPatronalSD())
					&& solicitud.getUnoVariosRp() == CrtSolicitudcorr.SOLICITUD_VARIOS_RP) {
				if (!validaPatronInscritos(solicitud.getPatrones(), solicitud
						.getPatronPrincipal().getRegistroPatronalSD()))
					return false;
			} else {
				return true;
			}

		}
		return true;
	}

	private boolean validaPatronInscritos(List patrones, String patron) {
		if (patrones != null && patrones.size() > 0) {
			Iterator pats = patrones.iterator();
			while (pats.hasNext()) {
				SatPatron pat = (SatPatron) pats.next();
				if (pat.getRegistroPatronalSD().equals(patron))
					return true;
			}
		}
		return false;
	}

	private boolean validaInformacionCompleta(CrtSolicitudcorr solicitud) {
		if (solicitud.getPatrones() != null
				&& solicitud.getPatrones().size() > 0) {
			Iterator pats = solicitud.getPatrones().iterator();
			while (pats.hasNext()) {
				SatPatron pat = (SatPatron) pats.next();
				if (pat.getActividad() == null
						|| pat.getActividad().length() <= 0)
					return false;
				if (pat.getClase() == null || pat.getClase().length() <= 0)
					return false;
				if (pat.getFraccion() == null
						|| pat.getFraccion().length() <= 0)
					return false;
				if (pat.getPrima() == null || pat.getPrima().length() <= 0)
					return false;
				if (pat.getTrabajadores() == null
						|| pat.getTrabajadores().length() <= 0)
					return false;
			}
		}
		return true;
	}

	private boolean existeDomicilioInegi(Hashtable doms, String patron) {

		if (doms != null && doms.size() > 0) {
			if (doms.get(patron) != null)
				return true;
			else
				return false;
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map obtenParametrosReporte(CrtSolicitudcorr solicitud)
			throws FileNotFoundException, JRException {
		Map parameters = new HashMap();
		List<CrtAnexosolcorrpat> lstAnexo = solicitudService
				.consultarAnexoSolicitudesReport(solicitud
						.getCveSolicitudCorr());
		// DATOS DEL PATRON
		SatPatron patron = patronesService.getById(solicitud.getCvePatron());
		parameters.put("nombrePatron", solicitud.getPatronCorregir()
				.getRazonSocial());
		parameters.put("folioSolicitud", solicitud.getNuFolio());
		parameters.put("registroPatron", solicitud.getPatronCorregir().getRegistroPatronal());
		parameters.put("registroCurp", solicitud.getCurpPatronCorregir());
		parameters.put("registroRFC", solicitud.getRfcPatronCorregir());
		parameters.put("txDelegacion", solicitud.getPatronPrincipal()
				.getUbicacion().getMunicipio().getSacEntidadFederativa()
				.getNomNombre());
		parameters.put("txSubDelegacion", solicitud.getPatronPrincipal()
				.getUbicacion().getMunicipio().getSacSubdelegacion()
				.getNomNombre());
		parameters.put("cadenaOriginal", solicitud.getCadenaOriginal());
		parameters.put("firmaElectronica", solicitud.getFirmaElectronica());
		parameters.put("selloIMSS", solicitud.getSelloIMSS());
		System.out.println("Cadena Original "+solicitud.getCadenaOriginal());
		System.out.println("Firma Electronica "+solicitud.getFirmaElectronica());
		System.out.println("sello Imss "+solicitud.getSelloIMSS());
		// VERIFICA SI ES DE INTERNET
		if (solicitud.getInternet() != null
				&& !solicitud.getInternet().isEmpty()) {
			parameters.put("internet", "1"); // uno
		}

		
		
		if (solicitud.getIdTipoSolicitud() == 0
				&& (solicitud.getNuFolio() != null && !solicitud.getNuFolio()
						.contains("CCI"))) { // ordinario
			if (lstAnexo != null && lstAnexo.size() > 0) {
				for (Iterator iterator = lstAnexo.iterator(); iterator
						.hasNext();) {
					CrtAnexosolcorrpat crtAnexosolcorrpat = (CrtAnexosolcorrpat) iterator
							.next();
					if (crtAnexosolcorrpat.getTipoPatron().equals("F")) {
						// DOMICILIO FISCAL
						if (crtAnexosolcorrpat.getCveDomicilio() != null) {
							DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
							domicilio.setDomicilioId(crtAnexosolcorrpat
									.getCveDomicilio().longValue());
							domicilio = domiciliosInegiServiceBean
									.consultaPorClave(domicilio);
							if (domicilio != null) {
								String numExt = domicilio.getNumextnum() != null ? domicilio
										.getNumextnum().toString() : "";
								if (domicilio.getNumextalf() != null
										&& !domicilio.getNumextalf().equals(
												"null")) {
									numExt += " " + domicilio.getNumextalf();
								}
								String numInt = domicilio.getNumintnum() != null ? domicilio
										.getNumintnum().toString() : "";
								if (domicilio.getNumintalf() != null
										&& !domicilio.getNumintalf().equals(
												"null")) {
									numInt += " " + domicilio.getNumintalf();
								}
								parameters.put("calleDomicilio",
										domicilio.getNomvial());
								parameters.put("numeroExtDomicilio", numExt);
								parameters.put("numeroIntDomicilio", numInt);
								parameters.put("coloniaDomicilio", domicilio
										.getDgAsentamiento().getNomAsen());
								parameters.put("municipioDomicilio", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getNomMun());
								parameters.put("localidadDomicilio", domicilio
										.getDgCatLocalidad().getNomLoc());
								parameters.put("entidadDomicilio", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getDgCatEstado()
										.getNomEnt());
								parameters.put("codigoPostalDomicilio",
										domicilio.getDgCodigosPostales()
												.getId().getCodigo());
							}
						}

					}
					if (crtAnexosolcorrpat.getTipoPatron().equals("C")) {
						// DOMICILIO CENTRO DE TRABAJO

						if (crtAnexosolcorrpat.getCveDomicilio() != null) {
							DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
							domicilio.setDomicilioId(crtAnexosolcorrpat
									.getCveDomicilio().longValue());
							domicilio = domiciliosInegiServiceBean
									.consultaPorClave(domicilio);
							if (domicilio != null) {
								String numExt = domicilio.getNumextnum() != null ? domicilio
										.getNumextnum().toString() : "";
								if (domicilio.getNumextalf() != null
										&& !domicilio.getNumextalf().equals(
												"null")) {
									numExt += " " + domicilio.getNumextalf();
								}
								String numInt = domicilio.getNumintnum() != null ? domicilio
										.getNumintnum().toString() : "";
								if (domicilio.getNumintalf() != null
										&& !domicilio.getNumintalf().equals(
												"null")) {
									numInt += " " + domicilio.getNumintalf();
								}
								parameters.put("calleCentro",
										domicilio.getNomvial());
								parameters.put("numeroExtCentro", numExt);
								parameters.put("numeroIntCentro", numInt);
								parameters.put("coloniaCentro", domicilio
										.getDgAsentamiento().getNomAsen());
								parameters.put("municipioCentro", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getNomMun());
								parameters.put("localidadCentro", domicilio
										.getDgCatLocalidad().getNomLoc());
								parameters.put("entidadCentro", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getDgCatEstado()
										.getNomEnt());
								parameters.put("codigoPostalCentro", domicilio
										.getDgCodigosPostales().getId()
										.getCodigo());
							}
						}
					}

				}
			}
		} else if (solicitud.getIdTipoSolicitud() == 1
				|| (solicitud.getNuFolio() != null && solicitud.getNuFolio()
						.contains("CCI"))) { // construccion
			if (lstAnexo != null && lstAnexo.size() > 0) {
				for (Iterator iterator = lstAnexo.iterator(); iterator
						.hasNext();) {
					CrtAnexosolcorrpat crtAnexosolcorrpat = (CrtAnexosolcorrpat) iterator
							.next();
					if (crtAnexosolcorrpat.getTipoPatron().equals("C")) {
						// DOMICILIO CENTRO DE TRABAJO

						if (crtAnexosolcorrpat.getCveDomicilio() != null) {
							DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
							domicilio.setDomicilioId(crtAnexosolcorrpat
									.getCveDomicilio().longValue());
							domicilio = domiciliosInegiServiceBean
									.consultaPorClave(domicilio);
							if (domicilio != null) {
								String numExt = domicilio.getNumextnum() != null ? domicilio
										.getNumextnum().toString() : "";
								if (domicilio.getNumextalf() != null
										&& !domicilio.getNumextalf().equals(
												"null")) {
									numExt += " " + domicilio.getNumextalf();
								}
								String numInt = domicilio.getNumintnum() != null ? domicilio
										.getNumintnum().toString() : "";
								if (domicilio.getNumintalf() != null
										&& !domicilio.getNumintalf().equals(
												"null")) {
									numInt += " " + domicilio.getNumintalf();
								}
								parameters.put("calleCentro",
										domicilio.getNomvial());
								parameters.put("numeroExtCentro", numExt);
								parameters.put("numeroIntCentro", numInt);
								parameters.put("coloniaCentro", domicilio
										.getDgAsentamiento().getNomAsen());
								parameters.put("municipioCentro", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getNomMun());
								parameters.put("localidadCentro", domicilio
										.getDgCatLocalidad().getNomLoc());
								parameters.put("entidadCentro", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getDgCatEstado()
										.getNomEnt());
								parameters.put("codigoPostalCentro", domicilio
										.getDgCodigosPostales().getId()
										.getCodigo());
							}
						}
					}
					if (crtAnexosolcorrpat.getTipoPatron().equals("F")) {
						// DOMICILIO FISCAL
						if (crtAnexosolcorrpat.getCveDomicilio() != null) {
							DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
							domicilio.setDomicilioId(crtAnexosolcorrpat
									.getCveDomicilio().longValue());
							domicilio = domiciliosInegiServiceBean
									.consultaPorClave(domicilio);
							if (domicilio != null) {
								String numExt = domicilio.getNumextnum() != null ? domicilio
										.getNumextnum().toString() : "";
								if (domicilio.getNumextalf() != null
										&& !domicilio.getNumextalf().equals(
												"null")) {
									numExt += " " + domicilio.getNumextalf();
								}
								String numInt = domicilio.getNumintnum() != null ? domicilio
										.getNumintnum().toString() : "";
								if (domicilio.getNumintalf() != null
										&& !domicilio.getNumintalf().equals(
												"null")) {
									numInt += " " + domicilio.getNumintalf();
								}
								parameters.put("calleDomicilio",
										domicilio.getNomvial());
								parameters.put("numeroExtDomicilio", numExt);
								parameters.put("numeroIntDomicilio", numInt);
								parameters.put("coloniaDomicilio", domicilio
										.getDgAsentamiento().getNomAsen());
								parameters.put("municipioDomicilio", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getNomMun());
								parameters.put("localidadDomicilio", domicilio
										.getDgCatLocalidad().getNomLoc());
								parameters.put("entidadDomicilio", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getDgCatEstado()
										.getNomEnt());
								parameters.put("codigoPostalDomicilio",
										domicilio.getDgCodigosPostales()
												.getId().getCodigo());

							}
						}

					}
					if (crtAnexosolcorrpat.getTipoPatron().equals("O")) {
						// DOMICILIO FISCAL
						if (crtAnexosolcorrpat.getCveDomicilio() != null) {
							DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
							domicilio.setDomicilioId(crtAnexosolcorrpat
									.getCveDomicilio().longValue());
							domicilio = domiciliosInegiServiceBean
									.consultaPorClave(domicilio);
							if (domicilio != null) {
								String numExt = "";
								if (domicilio.getNumextnum() != null) {
									numExt = domicilio.getNumextnum() + " ";
								}
								if (domicilio.getNumextalf() != null
										&& !domicilio.getNumextalf().equals(
												"null")) {
									numExt += " " + domicilio.getNumextalf();
								}
								String numInt = "";
								if (domicilio.getNumintnum() != null) {
									numInt = domicilio.getNumintnum() + " ";
								}
								if (domicilio.getNumintalf() != null
										&& !domicilio.getNumintalf().equals(
												"null")) {
									numInt += " " + domicilio.getNumintalf();
								}
								parameters.put("calleObra",
										domicilio.getNomvial());
								parameters.put("numeroExtObra", numExt);
								parameters.put("numeroIntObra", numInt);
								parameters.put("coloniaObra", domicilio
										.getDgAsentamiento().getNomAsen());
								parameters.put("municipioObra", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getNomMun());
								parameters.put("localidadObra", domicilio
										.getDgCatLocalidad().getNomLoc());
								parameters.put("entidadObra", domicilio
										.getDgCatLocalidad()
										.getDgCatMunicipio().getDgCatEstado()
										.getNomEnt());
								parameters.put("codigoPostalObra", domicilio
										.getDgCodigosPostales().getId()
										.getCodigo());
							}
						}

					}
					if (solicitud.getNumeroObra() != null) { // se busca el
																// domicilio en
																// satic
						SatObra obra = this.obraService.validaObra(solicitud
								.getNumeroObra());
						if (obra != null) {

//							parameters.put("calleObra", obra.getUbicacion()
//									.getCalle());
//							parameters.put("numeroExtObra", obra.getUbicacion()
//									.getNumeroExterior() == null ? "" : obra
//									.getUbicacion().getNumeroExterior());
//							parameters.put("numeroIntObra", obra.getUbicacion()
//									.getNumeroInterior() == null ? "" : obra
//									.getUbicacion().getNumeroInterior());
//							parameters.put("coloniaObra", obra.getUbicacion()
//									.getColonia());
//							parameters.put("municipioObra", obra.getUbicacion()
//									.getMunicipio().getNombre());
//							parameters.put("localidadObra", obra.getUbicacion()
//									.getMunicipio().getNombre());
//							parameters.put("entidadObra", obra.getUbicacion()
//									.getMunicipio().getSacEntidadFederativa()
//									.getNomNombre());
//							parameters.put("codigoPostalObra", obra
//									.getUbicacion().getCodigoPostal());
							
							
							parameters.put("calleCentro", obra.getUbicacion()
							.getCalle());
					parameters.put("numeroExtCentro", obra.getUbicacion()
							.getNumeroExterior() == null ? "" : obra
							.getUbicacion().getNumeroExterior());
					parameters.put("numeroIntCentro", obra.getUbicacion()
							.getNumeroInterior() == null ? "" : obra
							.getUbicacion().getNumeroInterior());
					parameters.put("coloniaCentro", obra.getUbicacion()
							.getColonia());
					parameters.put("municipioCentro", obra.getUbicacion()
							.getMunicipio().getNombre());
					parameters.put("localidadCentro", obra.getUbicacion()
							.getMunicipio().getNombre());
					parameters.put("entidadCentro", obra.getUbicacion()
							.getMunicipio().getSacEntidadFederativa()
							.getNomNombre());
					parameters.put("codigoPostalCentro", obra
							.getUbicacion().getCodigoPostal());
						}
					}

				}

			}

		}

		parameters.put("telefonoDomicilio", solicitud.getTelefonoPatron());
		parameters.put("correoElectronico", solicitud.getEmailPatron());
		parameters.put("subdelegacionImss", solicitud.getPatronPrincipal()
				.getUbicacion().getMunicipio().getSacSubdelegacion()
				.getNomNombre());

		if (solicitud.getTipo().equals("ESPONTANEA")
				|| solicitud.getTipo().equals("PROMOCION")) {
			parameters.put("espontanea", "X");
		} else {
			parameters.put("invitacion", "X");
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			if (solicitud.getInvitacion() != null
					&& solicitud.getInvitacion().getFecFechaoficioinv() != null) {
				String resultado = formato.format(solicitud.getInvitacion()
						.getFecFechaoficioinv());				
				
				String datos[]=resultado.split("/");
				parameters.put("diaRecepOficio", datos[0]);
				parameters.put("mesRecepOficio", datos[1]);
				parameters.put("anioRecepOficio",datos[2]);
				
//				parameters.put("fechaOficio", resultado);
			}

		}

		parameters.put(
				"diaInicioCor",
				Functions.getDato(solicitud.getFecFechaPeriodoIni(),
						Calendar.getInstance().DAY_OF_MONTH));
		parameters.put(
				"mesInicioCor",
				Functions.getDato(solicitud.getFecFechaPeriodoIni(),
						Calendar.getInstance().MONTH));
		parameters.put(
				"anioInicioCor",
				Functions.getDato(solicitud.getFecFechaPeriodoIni(),
						Calendar.getInstance().YEAR));
		parameters.put(
				"diaFinCor",
				Functions.getDato(solicitud.getFecFechaPeriodoFin(),
						Calendar.getInstance().DAY_OF_MONTH));
		parameters.put(
				"mesFinCor",
				Functions.getDato(solicitud.getFecFechaPeriodoFin(),
						Calendar.getInstance().MONTH));
		parameters.put(
				"anioFinCor",
				Functions.getDato(solicitud.getFecFechaPeriodoFin(),
						Calendar.getInstance().YEAR));
		parameters.put("numTrabajadores", solicitud.getNumTrabajadores() + "");

		parameters.put("actividad", solicitud.getActividad());
		parameters.put("clase", solicitud.getClasePatronCorregir());
		parameters.put("fraccion", solicitud.getFraccionPatronCorregir());
		parameters.put("prima", solicitud.getPrimaPatronCorregir());
		parameters.put("representanteLegal", solicitud.getRepresentante());
		parameters.put("lugarElaboracion", solicitud.getPatronPrincipal()
				.getUbicacion().getMunicipio().getSacSubdelegacion()
				.getNomNombre());

		parameters.put("diaElaboracion", Functions.getDato(solicitud.getFecFechaElacoracionCorreccion(),
				Calendar.getInstance().DAY_OF_MONTH));
		parameters.put("mesElaboracion",
				Functions.getDato(solicitud.getFecFechaElacoracionCorreccion(), Calendar.getInstance().MONTH));
		parameters.put("anioElaboracion",
				Functions.getDato(solicitud.getFecFechaElacoracionCorreccion(), Calendar.getInstance().YEAR));

		if (lstAnexo != null && lstAnexo.size() > 0) {
			for (Iterator iterator = lstAnexo.iterator(); iterator.hasNext();) {
				CrtAnexosolcorrpat crtAnexosolcorrpat = (CrtAnexosolcorrpat) iterator
						.next();
				if (crtAnexosolcorrpat.getTipoPatron().equals("I")) {
					parameters.put("mapaSubReporte",
							llenaFormatoAnexo(solicitud));
				}
			}

		}

		return parameters;
	}

	@RequestMapping(value = "/muestraReporte", method = RequestMethod.POST)
	public String muestraReporte(CrtSolicitudcorr solicitud,
			HttpServletResponse response, HttpServletRequest request) {
		return determinaURL(request, "reportes/muestraPDF", "reportes/muestraPDF_Patron");
	}

	public Collection llenaFormatoAnexo(CrtSolicitudcorr solicitud)
			throws FileNotFoundException, JRException {

		List<Map<String, String>> myColl = new ArrayList<Map<String, String>>();

		List lstAnexo = solicitudService
				.consultarAnexoSolicitudesReport(solicitud
						.getCveSolicitudCorr());
		Iterator i = lstAnexo.iterator();
		Map<String, String> registroPatronal = null;
		while (i.hasNext()) {
			CrtAnexosolcorrpat crtAnexosolcorrpat = (CrtAnexosolcorrpat) i
					.next();
			// ANEXOS
			if (crtAnexosolcorrpat.getTipoPatron().equals("I")) {
				// DOMICILIO INSCRITOS
				if (crtAnexosolcorrpat.getCveDomicilio() != null) {
					DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
					domicilio.setDomicilioId(crtAnexosolcorrpat
							.getCveDomicilio().longValue());
					domicilio = domiciliosInegiServiceBean
							.consultaPorClave(domicilio);
					if (domicilio != null) {
						String dom = domicilio.getNomvial() + " ";
						if (domicilio.getNumextnum() != null) {
							dom += " " + domicilio.getNumextnum();
						}
						if (domicilio.getNumextalf() != null
								&& !domicilio.getNumextalf().equals("null")) {
							dom += " " + domicilio.getNumextalf();
						}
						if (domicilio.getNumintnum() != null) {
							dom += " " + domicilio.getNumintnum();
						}
						if (domicilio.getNumintalf() != null
								&& !domicilio.getNumintalf().equals("null")) {
							dom += " " + domicilio.getNumintalf();
						}
						dom += " " + domicilio.getDgAsentamiento().getNomAsen();
						dom += " " + domicilio.getDgCatLocalidad().getNomLoc();
						dom += " "
								+ domicilio.getDgCatLocalidad()
										.getDgCatMunicipio().getDgCatEstado()
										.getNomEnt();
						registroPatronal = new HashMap<String, String>();
						
						registroPatronal.put("cadenaOriginal", solicitud.getCadenaOriginal());
						registroPatronal.put("firmaElectronica", solicitud.getFirmaElectronica());
						registroPatronal.put("selloIMSS", solicitud.getSelloIMSS());
						
						
						registroPatronal.put("domicilioPatron", dom);
						registroPatronal.put("numTrabajadores",
								crtAnexosolcorrpat.getNumTrabajadores() + "");
						registroPatronal.put("actividad",
								crtAnexosolcorrpat.getTxActividad());
						registroPatronal.put("clase",
								crtAnexosolcorrpat.getTxClase());
						registroPatronal.put("fraccion",
								crtAnexosolcorrpat.getTxFraccion());
						registroPatronal.put("prima",
								crtAnexosolcorrpat.getTxPrima());
						registroPatronal.put("txSubDelegacion", solicitud
								.getPatronPrincipal().getUbicacion()
								.getMunicipio().getSacSubdelegacion()
								.getNomNombre());
						registroPatronal.put("txDelegacion", solicitud
								.getPatronPrincipal().getUbicacion()
								.getMunicipio().getSacSubdelegacion()
								.getSacDelegacion().getNomNombre());
						registroPatronal.put("representanteLegal",
								solicitud.getTxRepresentanteLegal());
						registroPatronal.put("razonSocial",
								solicitud.getRazonSocialPatronCorregir());
					}
					if (crtAnexosolcorrpat.getCvePatron() != null) {
						SatPatron patron = this.patronesService
								.getById(crtAnexosolcorrpat.getCvePatron());
						if (patron != null) {
							registroPatronal.put("registroPatronal",
									patron.getRegistroPatronal());
						}
					}
					registroPatronal.put("subdelegacion", solicitud
							.getPatronPrincipal().getUbicacion().getMunicipio()
							.getSacSubdelegacion().getNomNombre());
					registroPatronal.put("delegacion", solicitud
							.getPatronPrincipal().getUbicacion().getMunicipio()
							.getSacEntidadFederativa().getNomNombre());
					myColl.add(registroPatronal);
				}

			}
		}

		return myColl;
	}

	public String generaReporte(HttpSession session, Map parameters,
			boolean patronUnico) throws FileNotFoundException, JRException {
		byte[] bytes = null;
		parameters.put("rutaImagen",
				session.getServletContext().getRealPath("/resources/images/")
						+ "/");
		InputStream reportStream = null;
		if (patronUnico) {
			reportStream = new FileInputStream(
					session.getServletContext()
							.getRealPath(
									"/WEB-INF/views/formatos/solicitudCorrrecionUR.jasper"));
		} else {
			parameters
					.put("REAL_PATH",
							session.getServletContext()
									.getRealPath(
											"/WEB-INF/views/formatos/solicitudCorrrecionA.jasper"));
			reportStream = new FileInputStream(
					session.getServletContext()
							.getRealPath(
									"/WEB-INF/views/formatos/solicitudCorrrecion.jasper"));
		}
		bytes = JasperRunManager.runReportToPdf(reportStream, parameters,
				new JREmptyDataSource());
		final String acusePdf = org.apache.soap.encoding.soapenc.Base64
				.encode(bytes);
		session.setAttribute("documento", acusePdf);
		session.setAttribute("nombreArchivo", "aviso.pdf");
		session.setAttribute("tipoDescarga", "muestra");
		return acusePdf;
	}

	@RequestMapping(value = "/setPatron", method = RequestMethod.POST)
	public @ResponseBody
	SatPatron setPatron(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		SatPatron pat = (SatPatron) this.patronesService
				.validaRegistroPatronalWS(patron.getPatron(), false);
		request.getSession().setAttribute("setPatronCorregirDG", pat);
		// request.getSession().getAttribute("setPatronCorregirDG")
		return pat;
	}

	@RequestMapping(value = "/actualizaRegPat", method = RequestMethod.POST)
	public @ResponseBody
	SatPatron actualizaRegPat(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		Hashtable doms = (Hashtable) getDomicilioInegiSession(request);
		if (doms != null && doms.size() > 0) {
			//Para recuperar el tipo ya se agrega subfijo tipo P
			String patr=patron.getPatron().substring(0,10);
			String tipo=patron.getPatron().substring(10,11);
			System.out.println("PAtron "+patr+" tipo "+tipo);
			SatPatron pat = (SatPatron) this.patronesService
					.validaRegistroPatronalWS(patr, false);
			pat.setDomicilioGeografico((DgDomicilioGeografico) ((Hashtable) getDomicilioInegiSession(request))
					.get(pat.getRegistroPatronalSD()+tipo));
			return pat;
		}
		return null;
	}

	@RequestMapping(value = "/actualizaDomObra", method = RequestMethod.POST)
	public @ResponseBody
	DgDomicilioGeografico actualizaDomObra(
			@RequestBody CrtSolicitudcorr patron, HttpServletResponse response,
			HttpServletRequest request) {
		Hashtable doms = (Hashtable) getDomicilioInegiSession(request);
		if (doms != null && doms.size() > 0) {
			return (DgDomicilioGeografico) ((Hashtable) getDomicilioInegiSession(request))
					.get(patron.getPatron());
		}
		return null;
	}

	@RequestMapping(value = "/actualizaInscritos", method = RequestMethod.POST)
	public @ResponseBody
	SatPatron actualizaInscritos(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List patrones = new ArrayList();
		List result = new ArrayList();
		if (session.getAttribute("patrones") != null) {
			patrones = (List) session.getAttribute("patrones");
			Hashtable<String, ?> doms = (Hashtable) getDomicilioInegiSession(request);
			for (int i = 0; i < patrones.size(); i++) {
				SatPatron p = (SatPatron) patrones.get(i);
				DgDomicilioGeografico dg = (DgDomicilioGeografico) doms.get(p
						.getRegistroPatronalSD());
				if (dg != null) {
					p.setDomicilioGeografico(dg);
				}
			}
		}

		session.setAttribute("patrones", patrones);
		return new SatPatron();
	}

	@RequestMapping(value = "/sessionDomicilioGeografico", method = RequestMethod.POST)
	public @ResponseBody
	DgDomicilioGeografico almacenaSessionDomicilioInegi(
			@RequestBody DgDomicilioGeografico domicilioInegi,
			HttpServletRequest request) {
		return new DomGeograficosController().almacenaSessionDomicilioInegi(
				domicilioInegi, request);
	}

	@RequestMapping(value = "/actualizaPatIns", method = RequestMethod.POST)
	public @ResponseBody
	SatPatron actualizaPatIns(@RequestBody SatPatron patron,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		List patrones = new ArrayList();
		List result = new ArrayList();
		if (session.getAttribute("patrones") != null) {
			patrones = (List) session.getAttribute("patrones");
			if (patrones != null && patrones.size() > 0) {
				Iterator i = patrones.iterator();
				while (i.hasNext()) {
					SatPatron pat = (SatPatron) i.next();
					if (patron.getRegistroPatronal().equals(
							pat.getRegistroPatronalSD())) {
						pat.setActividad(patron.getActividad());
						pat.setTrabajadores(patron.getTrabajadores());
						pat.setClase(patron.getClase());
						pat.setFraccion(patron.getFraccion());
						pat.setPrima(patron.getPrima());
					}
					result.add(pat);
				}
				session.setAttribute("patrones", result);
			}
		}
		return null;
	}

	@RequestMapping(value = "/solicitudDomGeografico", method = RequestMethod.GET)
	public String callDomGeograficos(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		DgDomicilioGeografico dg = new DgDomicilioGeografico();
		SatPatron pat = (SatPatron) session.getAttribute("setPatronCorregirDG");
		session.removeAttribute("setPatronCorregirDG");
		Hashtable doms = (Hashtable) getDomicilioInegiSession(request);

		try {
			dg.setDescripc(pat.getDomicilioCompleto());
		} catch (Exception e) {
			dg.setDescripc("");
		}

		if (doms != null) {
			DgDomicilioGeografico tmp = (DgDomicilioGeografico) doms.get(pat
					.getRegistroPatronalSD());
			if (tmp != null)
				dg = tmp;
			dg.setHastableKeyDG(pat.getRegistroPatronalSD());
		} else {
			dg.setHastableKeyDG(pat.getRegistroPatronalSD());

		}

		if (request.getParameter("bloquearEstado") != null) {
			Boolean bBloquearEstado = new Boolean(
					request.getParameter("bloquearEstado"));
			dg.setBloquearEstado(bBloquearEstado);
		}

		return new DomGeograficosController().getCreateGenericForm(model, dg,
				request);
	}

	@RequestMapping(value = "/solicitudDomGeograficoObra", method = RequestMethod.GET)
	public String callDomGeograficosObra(HttpServletResponse response,
			HttpServletRequest request, Model model) {

		DgDomicilioGeografico dg = new DgDomicilioGeografico();

		Hashtable doms = (Hashtable) getDomicilioInegiSession(request);

		if (doms != null) {
			DgDomicilioGeografico tmp = (DgDomicilioGeografico) doms
					.get("DOM_OBRA");
			if (tmp != null)
				dg = tmp;
		}

		dg.setHastableKeyDG("DOM_OBRA");
		request.getSession().removeAttribute("ObraSol");
		return new DomGeograficosController().getCreateGenericForm(model, dg,
				request);
	}

	public void limpiaSession(HttpSession session) {
		session.removeAttribute("setPatronCorregirDG");
		session.removeAttribute("patrones");
		session.removeAttribute("invitacionEncontrada");
		session.removeAttribute("promocionEncontrada");
		session.removeAttribute("patronCorregir");
		session.removeAttribute("patronPrincipal");
		session.removeAttribute("ObraSol");
		session.removeAttribute("DOM_OBRA");

	}
	
	
	@RequestMapping(value = "/generaDigitoVerificador", method = RequestMethod.POST)
	public @ResponseBody int generaDigitoVerificador(@RequestBody SatPatron regPat) {
        System.out.println("Ekl rp es "+regPat);
		String nrp=regPat.getRegistroPatronal();
		int factorDeConversion = 10;
        int digitoVerificador = 0;
        int paso3 = 0;
        boolean bandera = true;
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String clave = "";
        int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
        if (primeraLetra != -1) {
            clave = (primeraLetra + factorDeConversion) + nrp.substring(1, nrp.length());
        } else {
            clave = nrp;
        }
        int i = clave.length() - 1;
        while (i >= 0) {
            if (bandera) {
                int porDos = Integer.parseInt("" + clave.charAt(i)) * 2;
                if (porDos > 9)// si el resultado es un numero de dos cifras, es necesario tratar
                               // estas por separado.
                {
                    paso3 += (porDos % 10) + (porDos / 10);
                } else {
                    paso3 += porDos;
                }
                bandera = false;
            } else {
                paso3 += Integer.parseInt("" + clave.charAt(i));
                bandera = true;
            }
            i--;
        }
        digitoVerificador = 10 - (paso3 % 10);
        if (digitoVerificador > 9) {
            digitoVerificador = 0;
        }

        return digitoVerificador;
    }
	

}
