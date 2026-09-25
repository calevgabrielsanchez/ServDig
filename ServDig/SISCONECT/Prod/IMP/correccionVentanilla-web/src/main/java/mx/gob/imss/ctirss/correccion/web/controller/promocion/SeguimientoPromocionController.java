/**
 * SeguimientoPromocionController.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.promocion
 * @project Correccion-web	
 */
package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.CatalogosService;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.PromocionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoPago;
import mx.gob.imss.ctirss.correccion.model.CrtCorrPromInvita;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.promocion.base.paginador.model.CrtPromocionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.paginador.model.CrtRegulapagosdetWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.interfaces.RegularizacionService;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.ordinario.SeguimientoPromocionVO;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.satica.SeguimientoPromocionSaticaVO;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb.SeguimientoPromocionSaticbVO;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Oscar Beltran Ortega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 25/05/2012
 */
@Controller
@RequestMapping(value = "/promocion/consulta")
public class SeguimientoPromocionController extends AbstractController {

	@Autowired
	private PromocionService<CrtPromocion> promocionServiceBean;

	@Autowired
	private PromocionService<CrtInvitacion> invitacionServiceBean;

	@Autowired
	private PromocionService<CrtCorrPromInvita> promInviaServiceBean;

	@Autowired
	private PromocionService<CgcCatcriterioseleccion> promocionCriteriosServiceBean;

	@Autowired
	private PromocionService<CgtAnexoPago> promocionReplicaBean;

	@Autowired
	private DeteccionService<CrtDeteccion> deteccionServicBean;

	@Autowired
	private IPatronesService patronesService;

	@Autowired
	private InvitacionService<CrtPromocion> invitacionService;

	@Autowired
	private RegularizacionService<CrtRegulapagos> regularizacionService;

	@Autowired
	private RegularizacionService<CrtRegulapagosdet> regularizacionDetService;

	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;

	@Autowired
	private CatalogosService catalogosServiceBean;

	@Autowired
	private ICatalogoService<AbstractModel> iCatalogoServiceBean;

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(SeguimientoPromocionController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {

		PromocionSeguimientoGenericoVO seguimientoGenericoVO = new PromocionSeguimientoGenericoVO();

		SeguimientoPromocionVO seguimientoPromoVO = (model.asMap().get("seguimientoPromoVO") != null ? (SeguimientoPromocionVO) model.asMap().get("seguimientoPromoVO"): new SeguimientoPromocionVO());

		UserSession user = getUsuarioFirmado(request);
		seguimientoGenericoVO.setNombreFuncionarioRegistra(user.getNombreCompleto());
		seguimientoGenericoVO.setSubdelegacionFuncionarioReg(user.getIdSubDelegacion().toString());

		SeguimientoPromocionSaticbVO saticbVO = new SeguimientoPromocionSaticbVO();
		saticbVO.setNombreFuncionario(user.getNombreCompleto());

		model.addAttribute("seguimientoPromoVO", seguimientoPromoVO);
		model.addAttribute("seguimientoSaticbVO", saticbVO);
		model.addAttribute("seguimientoGenericoVO", seguimientoGenericoVO);
		model.addAttribute("seguimientoSaticaVO",new SeguimientoPromocionSaticaVO());

		return "promocionConsulta";
	}

	/**
	 * Recupera el catalogo de la BD para el combo de Tipos de Promocion
	 * -Peticion Json
	 * 
	 * @return
	 */
	@RequestMapping(value = "/llenarTiposCorreccion", method = RequestMethod.POST)
	public @ResponseBody
	List<CrcTipoCorr> llenarTiposCorreccion() {

		List<CrcTipoCorr> lstEjercicio = new ArrayList<CrcTipoCorr>();

		lstEjercicio = this.catalogosServiceBean.getTiposCorreccion(2L);

		return lstEjercicio;
	}

	@RequestMapping(value = "/setupPromocion", method = RequestMethod.POST)
	public String setupPromocion(Model model, HttpServletRequest request) {

		SeguimientoPromocionVO spoVO = new SeguimientoPromocionVO();
		spoVO.setTipoPromocion(Integer.parseInt(String.valueOf(request
				.getParameter("tipoPromocion"))));
		model.addAttribute("seguimientoPromoVO", spoVO);

		return new SeguimientoPromocionController().getCreateForm(model,
				request);
	}

	/**
	 * Metodo que se encarga de llenar el Datagrid de la seccion de consulta de
	 * promociones
	 * 
	 * @param aoData
	 *            Datos de los filtros selelccionados por el usuario
	 * @param response
	 * @param request
	 * @return Objeto DatosSalidaPaginador
	 * @author Oscar Beltran Ortega
	 * @version 1.0.1
	 */
	@RequestMapping(value = "/paginar", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<CrtPromocion> pagina(
			@RequestBody CrtPromocionWrapperDataTable aoData,
			HttpServletResponse response, HttpServletRequest request) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");

		UserSession user = getUsuarioFirmado(request);
		DatosEntradaPaginador send = new DatosEntradaPaginador();

		if (aoData.getoForm().getFechaIncial() != null
				&& aoData.getoForm().getFechaFinal() != null) {
			aoData.getoForm().setFecFechaemisionpro(
					Functions.stringToDate(aoData.getoForm().getFechaIncial()));
			aoData.getoForm().setFecFechanotif(
					Functions.stringToDate(aoData.getoForm().getFechaFinal()));

		}
		// se agrega el usuario para la busqueda de auditor asignado
		aoData.getoForm().setUsuarioFirmado(user);
		aoData.getoForm().setSdelegOrig(user.getIdSubDelegacion());
		aoData.getoForm().setCveEstatus(
				CatEstatus.EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION.getId());

		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());

		DatosSalidaPaginador<CrtPromocion> reply = new DatosSalidaPaginador<CrtPromocion>();

		reply = this.promocionServiceBean.consultaPromocion(send);

		if (reply != null && reply.getAaData() != null
				&& reply.getAaData().size() > 0) {
			CgcCatcriterioseleccion criterios = new CgcCatcriterioseleccion();
			for (Iterator iterator = reply.getAaData().iterator(); iterator
					.hasNext();) {
				CrtPromocion type = (CrtPromocion) iterator.next();

				if (type.getIdCriterioSeleccion() != null) {
					criterios.setIdCriterioseleccion(type
							.getIdCriterioSeleccion());
					criterios = this.promocionCriteriosServiceBean
							.consultaCriterioPorClave(criterios);
					if (criterios != null) {
						type.setDescCriterioseleccion(criterios
								.getDescCriterioseleccion());
					}
				}

				if (type.getRegPatron() != null
						&& type.getRegPatron().length() >= 10) {
					type.setRegPatron(type.getRegPatron().substring(0, 10));
				}
				if (type.getFecFechaemisionpro() != null) {
					type.setFechaOficio(Functions.dateToString(type.getFecFechaemisionpro()));
					// type.setFecFechaemisionpro(fecFechaemisionpro)
				}
			}
		}

		logger.debug(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());

		return reply;
	}

	@RequestMapping(value = "/agregaInvitacion")
	public @ResponseBody
	CrtInvitacion agregaPromocion(@RequestBody CrtInvitacion invitacion,
			HttpServletResponse response, HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		invitacion.setFecFechaoficioinv(Functions.FormateaFecha(
				invitacion.getFechaEmision(), "-"));
		invitacion.setFecFechaemision(Functions.FormateaFecha(
				invitacion.getFechaEmision(), "-"));
		invitacion.setFecFechareg(Functions.stringToDate(dateFormat
				.format(new Date())));
		if (invitacion.getPatron() != null
				&& !invitacion.getPatron().equalsIgnoreCase("")) {
			invitacion.setSatPatron(this.patronesService.getById(new Long(
					invitacion.getPatron())));
		}
		if (invitacion.getFolioPromocion().substring(5, 11).equals("SATICB")
				|| invitacion.getFolioPromocion().substring(5, 11)
						.equals("SATICA")
				|| invitacion.getFolioPromocion().substring(5, 7).equals("EX")) {
			invitacion.setNuFolioInvitacion(generaFolioInvitacion(
					new Long(user.getCveCodigoDelegacion()),
					new Long(user.getCveCodigoSubDelegacion()),
					"CCI",
					invitacion.getFechaEmision().substring(6,
							invitacion.getFechaEmision().length())));
		} else {
			invitacion.setNuFolioInvitacion(generaFolioInvitacion(
					new Long(user.getCveCodigoDelegacion()),
					new Long(user.getCveCodigoSubDelegacion()),
					"CC",
					invitacion.getFechaEmision().substring(6,
							invitacion.getFechaEmision().length())));
		}
		invitacion.setCveUsuario(user.getCurpUsuario().toString());
		invitacion.setCveFkSubdelegacion(new BigDecimal(user
				.getIdSubDelegacion()));
		this.invitacionService.guardar(invitacion);
		return invitacion;
	}

	@RequestMapping(value = "/consultaPorClave")
	public @ResponseBody
	CrtPromocion consultaPorClave(@RequestBody CrtPromocion promocion) {
		logger.debug("CVE ID A BUSCAR:" + promocion.getCvePromocion());
		if (promocion.getCvePromocion() > 0) {
			promocion = promocionServiceBean.consultaPorClave(promocion);
			SatPatron patron = this.patronesService.getById(promocion
					.getCveFkPatron());
			if (patron != null) {
				promocion.setRegPatron(patron.getRegistroPatronal());
				promocion.setRazonSocial(patron.getRazonSocial());
			}
		}
		return promocion;
	}
	
	
	@RequestMapping(value = "/consultaPorNumFolio")
	public @ResponseBody
	CrtPromocion consultaPorNumFolio(@RequestBody CrtPromocion promocion,HttpServletResponse response, HttpServletRequest request) {
		logger.debug("NU FOLIO a buscar:" + promocion.getNuFoliopromocion());
		UserSession userSession = this.getUsuarioFirmado(request);
		
		if (promocion.getNuFoliopromocion() != null && !promocion.getNuFoliopromocion().equals("") ) {
			promocion.setSdelegOrig(userSession.getIdSubDelegacion());
			promocion = promocionServiceBean.consultaPorFolio(promocion);
			
			/*SatPatron patron = this.patronesService.getById(promocion
					.getCveFkPatron());
			if (patron != null) {
				promocion.setRegPatron(patron.getRegistroPatronal());
				promocion.setRazonSocial(patron.getRazonSocial());
			}*/
		}
		return promocion;
	}

	@RequestMapping(value = "/consultar")
	public @ResponseBody
	List<CrtRegulapagosdet> consultar(@RequestBody CrtRegulapagosdet pago) {
		logger.debug("CVE ID A BUSCAR:" + pago.getCveBusqueda());
		List<CrtRegulapagosdet> pagos = new ArrayList<CrtRegulapagosdet>();
		if (pago.getCveBusqueda() > 0) {
			pago.setCrtRegulapagos(new CrtRegulapagos());
			pago.getCrtRegulapagos().setCveRegulapagos(pago.getCveBusqueda());
			pagos = regularizacionDetService.consultaPagosDetPorCvePago(pago);
		}
		return pagos;
	}

	@RequestMapping(value = "/consultaPago")
	public @ResponseBody
	CrtRegulapagosdet consultaPago(@RequestBody CrtRegulapagosdet pago) {
		logger.debug("CVE ID A BUSCAR:" + pago.getCveRegulapagosdet());
		if (pago.getCveRegulapagosdet() > 0) {
			pago = regularizacionDetService.consultaPorClavePago(pago);
			pago.setFechaPago(Functions.dateToString(pago.getFecFechapago()));
			if (pago != null) {
				CgtAnexoPago anexoPago = new CgtAnexoPago();
				CrtRegulapagos regulaPago = new CrtRegulapagos();
				regulaPago.setCveRegulapagos(pago.getCrtRegulapagos()
						.getCveRegulapagos());
				regulaPago = (CrtRegulapagos) this.regularizacionService
						.consultaPorClave(regulaPago);
				CrtPromocion promocionReplica = new CrtPromocion();
				promocionReplica.setCvePromocion(regulaPago.getCvePromocion());
				promocionReplica = (CrtPromocion) this
						.consultaPorClave(promocionReplica);
				anexoPago.setFolio(promocionReplica.getNuFoliopromocion());
				if (promocionReplica.getCveFkPatron() != null) {
					SatPatron patron = patronesService.getById(promocionReplica
							.getCveFkPatron());
					anexoPago.setCvePatron(patron.getRegistroPatronal());
				}
				anexoPago.setFoliosua(pago.getNumFoliosua() != null ? pago
						.getNumFoliosua().toString() : null);
				anexoPago
						.setFolioordeningreso(pago.getNumOrdeningreso() != null ? pago
								.getNumOrdeningreso() : null);
				anexoPago.setNocredito(pago.getNumCredito() != null ? pago
						.getNumCredito() : null);
				anexoPago.setFechapago(pago.getFecFechapago() != null ? pago
						.getFecFechapago() : null);
				anexoPago
						.setCopperiodo(pago.getNumPeriodoCop() != null ? new BigDecimal(
								pago.getNumPeriodoCop()) : null);
				anexoPago.setCopsp(pago.getImpCopsp() != null ? pago
						.getImpCopsp() : null);
				anexoPago.setCopact(pago.getImpCopact() != null ? pago
						.getImpCopact() : null);
				anexoPago.setCoprec(pago.getImpCoprec() != null ? pago
						.getImpCoprec() : null);
				anexoPago.setCopmultas(pago.getImpMultasCop() != null ? pago
						.getImpMultasCop() : null);
				anexoPago.setRcvsp(pago.getImpRcvsp() != null ? pago
						.getImpRcvsp() : null);
				anexoPago.setRcvact(pago.getImpRcvact() != null ? pago
						.getImpRcvact() : null);
				anexoPago.setRcvrec(pago.getImpRcvrec() != null ? pago
						.getImpRcvrec() : null);
				anexoPago.setRcvmultas(pago.getImpMultasRcv() != null ? pago
						.getImpMultasRcv() : null);
				anexoPago.setFecFechareg(Functions.stringToDate(Functions
						.dateToString(new Date())));
				anexoPago.setCveUsuario(promocionReplica.getCveUsuario());
				if (promocionReplica.getNuFoliopromocion().substring(5, 11)
						.equalsIgnoreCase("SATICA")
						|| (promocionReplica.getNuFoliopromocion().length() == 17 && promocionReplica
								.getNuFoliopromocion().substring(5, 11)
								.equalsIgnoreCase("EX"))) {
					anexoPago = this.promocionReplicaBean
							.consultaPagoReplicadoPorFolio(anexoPago);
					pago.setIdPagoCaratula(anexoPago.getIdPago());
				}
			}
		}
		return pago;
	}

	@RequestMapping(value = "/eliminaPago")
	public @ResponseBody
	CrtRegulapagosdet eliminaPago(@RequestBody CrtRegulapagosdet pago) {
		logger.debug("CVE ID A ELIMINAR:" + pago.getCveRegulapagosdet());
		if (pago.getCveRegulapagosdet() > 0) {
			this.promocionReplicaBean.replicaEliminaPago(pago);
			this.regularizacionDetService.eliminar(pago);
		}
		return pago;
	}

	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public @ResponseBody
	CrtPromocion cancelar(@RequestBody CrtPromocion promocion,
			HttpServletResponse response) {
		BigDecimal idCance = promocion.getIdMotivoCancelacion();
		String numMotivo = promocion.getNuVolanteCancela();
		String fechaCancela = promocion.getFechaCancelacion();
		promocion = this.consultaPorClave(promocion);
		promocion.setFecFechaCancela(Functions.stringToDate(fechaCancela));
		promocion.setIdMotivoCancelacion(idCance);
		promocion.setNuVolanteCancela(numMotivo);
		this.promocionServiceBean.modificar(promocion);
		return promocion;
	}

	@RequestMapping(value = "/validaPatron", method = RequestMethod.POST)
	public @ResponseBody
	SatPatron validaPatron(@RequestBody String parametro) {
		String valor = parametro.substring(1, parametro.length() - 1);
		SatPatron model = new SatPatron();
		model = patronesService.validaRegistroPatronalWS(valor, false);

		return model;
	}

	@RequestMapping(value = "/guardarPago", method = RequestMethod.POST)
	public @ResponseBody
	CrtRegulapagos guardarPago(@RequestBody CrtRegulapagos pago,
			HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);

		pago.setCveUsuario(user.getCurpUsuario().toString());
		pago.setFecPeridodini(Functions.FormateaFecha(pago.getFecPerIni(), "-"));
		pago.setFecPeriodofin(Functions.FormateaFecha(pago.getFecPerFin(), "-"));

		pago = this.regularizacionService.agregar(pago);

		this.actualizaPromocion(pago, response, request);

		return pago;
	}

	@RequestMapping(value = "/guardarPagoDet", method = RequestMethod.POST)
	public @ResponseBody
	CrtRegulapagosdet guardarPagoDet(@RequestBody CrtRegulapagosdet pagodet,
			HttpServletResponse response, HttpServletRequest request) {

		pagodet.setFecFechapago(Functions.FormateaFecha(pagodet.getFechaPago(),
				"-"));

		pagodet = this.regularizacionDetService.agregar(pagodet);

		CrtRegulapagos regulaPago = new CrtRegulapagos();
		regulaPago.setCveRegulapagos(pagodet.getCrtRegulapagos()
				.getCveRegulapagos());
		regulaPago = (CrtRegulapagos) this.regularizacionService
				.consultaPorClave(regulaPago);
		CrtPromocion promocion = new CrtPromocion();
		promocion.setCvePromocion(regulaPago.getCvePromocion());
		promocion = promocionServiceBean.consultaPorClave(promocion);

		if (promocion.getNuFoliopromocion().substring(5, 11)
				.equalsIgnoreCase("SATICA")
				|| (promocion.getNuFoliopromocion().length() == 17 && promocion
						.getNuFoliopromocion().substring(5, 7)
						.equalsIgnoreCase("EX"))) {
			this.promocionServiceBean.replicaPago(pagodet);
		}

		return pagodet;
	}

	@RequestMapping(value = "/paginarPagoDet", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<CrtRegulapagosdet> paginarPagoDet(
			@RequestBody CrtRegulapagosdetWrapperDataTable aoData) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();

		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		DatosSalidaPaginador<CrtRegulapagosdet> reply = new DatosSalidaPaginador<CrtRegulapagosdet>();
		if (aoData.getoForm().isBandera()) {
			reply = this.regularizacionDetService.paginaPagos(send);

			if (reply.getAaData().size() > 0) {
				List<CrtRegulapagosdet> lstPagos = new ArrayList<CrtRegulapagosdet>();
				Iterator<CrtRegulapagosdet> itera = reply.getAaData()
						.iterator();
				CrtPromocion prom = new CrtPromocion();
				while (itera.hasNext()) {
					CrtRegulapagosdet pago = itera.next();
					prom.setCvePromocion(pago.getCrtRegulapagos()
							.getCvePromocion());
					prom = this.promocionServiceBean.consultaPorClave(prom);
					SatPatron pat = this.patronesService.getById(prom
							.getCveFkPatron());
					pago.setRegpat(pat.getRegistroPatronal());
					pago.setFechaPago(Functions.dateToString(pago
							.getFecFechapago()));
					lstPagos.add(pago);
				}
				reply.setAaData(lstPagos);
			}
		} else
			reply.setAaData(new ArrayList<CrtRegulapagosdet>());

		logger.debug(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());

		return reply;
	}

	@RequestMapping(value = "/consultaRegularizacion")
	public @ResponseBody
	CrtRegulapagos consultaRegularizacion(@RequestBody CrtPromocion promocion,
			HttpServletResponse response, HttpServletRequest request) {
		CrtRegulapagos pago = new CrtRegulapagos();
		if (promocion.getCvePromocion() > 0) {
			promocion = promocionServiceBean.consultaPorClave(promocion);
			SatPatron patron = this.patronesService.getById(promocion
					.getCveFkPatron());
			pago.setCvePromocion(promocion.getCvePromocion());
			pago = this.regularizacionService.consultaPorClavePromocion(pago);
			pago.setRegPatron(patron.getRegistroPatronal());
			if (promocion.getNuFoliopromocion().length() == 17) {
				pago.setTipoProm(promocion.getNuFoliopromocion()
						.substring(5, 7));
			} else if (promocion.getNuFoliopromocion().length() == 18) {
				pago.setTipoProm(promocion.getNuFoliopromocion()
						.substring(5, 8));
			} else {
				pago.setTipoProm(promocion.getNuFoliopromocion().substring(5,
						11));
			}
			if (pago.getFecPeridodini() != null) {
				pago.setFecPerIni(Functions.dateToString(pago
						.getFecPeridodini()));
			}
			if (pago.getFecPeriodofin() != null) {
				pago.setFecPerFin(Functions.dateToString(pago
						.getFecPeriodofin()));
			}
			if (promocion.getFecFechaAtencion() != null) {
				pago.setFechaAtencionPro(Functions.dateToString(promocion
						.getFecFechaAtencion()));
			}
			if (promocion.getFecFechapai() != null) {
				pago.setFechaPAI(Functions.dateToString(promocion
						.getFecFechapai()));
			}
		}
		return pago;
	}

	@RequestMapping(value = "/actualizaPromocion")
	public @ResponseBody
	CrtRegulapagos actualizaPromocion(@RequestBody CrtRegulapagos pagoPro,
			HttpServletResponse response, HttpServletRequest request) {
		CrtPromocion promocion = new CrtPromocion();
		if (pagoPro.getCvePromocion() > 0) {
			promocion.setCvePromocion(pagoPro.getCvePromocion());
			promocion = promocionServiceBean.consultaPorClave(promocion);
			if (pagoPro.getFechaAtencionPro() != null
					&& !pagoPro.getFechaAtencionPro().equalsIgnoreCase("")) {
				promocion.setFecFechaAtencion(Functions.stringToDate(pagoPro
						.getFechaAtencionPro()));
			}
			if (pagoPro.getFechaPAI() != null
					&& !pagoPro.getFechaPAI().equalsIgnoreCase("")) {
				promocion.setFecFechapai(Functions.stringToDate(pagoPro
						.getFechaPAI()));
			}
			this.promocionServiceBean.modificar(promocion);

			this.replicaPromocion(promocion);
		}
		return pagoPro;
	}

	@RequestMapping(value = "/mostrar")
	public @ResponseBody
	CrtDeteccion mostrar(@RequestBody CrtPromocion promocion) {
		CrtDeteccion deteccion = new CrtDeteccion();
		if (promocion.getCvePromocion() > 0) {
			promocion = this.promocionServiceBean.consultaPorClave(promocion);
			if (promocion.getCveDeteccion() != null
					&& promocion.getCveDeteccion().intValue() > 0) {
				deteccion.setCveDeteccion(promocion.getCveDeteccion()
						.longValue());
				deteccion = this.promocionServiceBean
						.obtieneDeteccionporClave(deteccion);
				deteccion.setFechaRegistro(Functions.dateToString(deteccion
						.getFecFechareg()));
				DgDomicilioGeografico dg = new DgDomicilioGeografico();
				dg.setDomicilioId(deteccion.getDomicilioId());
				dg = domiciliosInegiServiceBean.consultaPorClave(dg);
				if (dg != null) {
					deteccion
							.setRefColonia(dg.getDgAsentamiento().getNomAsen());
					if (dg.getDgCatLocalidad().getDgCatMunicipio()
							.getDgCatEstado().getNomEnt() != null
							&& !dg.getDgCatLocalidad().getDgCatMunicipio()
									.getDgCatEstado().getNomEnt()
									.equalsIgnoreCase(""))
						deteccion.setEstado(dg.getDgCatLocalidad()
								.getDgCatMunicipio().getDgCatEstado()
								.getNomEnt());
					if (dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun() != null
							&& !dg.getDgCatLocalidad().getDgCatMunicipio()
									.getNomMun().equalsIgnoreCase(""))
						deteccion.setMunicipio(dg.getDgCatLocalidad()
								.getDgCatMunicipio().getNomMun());
					if (dg.getNomvial() != null) {
						if (!dg.getNomvial().equalsIgnoreCase(""))
							deteccion.setDomCalle(dg.getNomvial());
					} else {
						deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin()
								.getNomVia());
					}
					deteccion.setNumNroext(dg.getNumextnum().toString());
					if (dg.getNumintalf() != null)
						deteccion.setNumNroint(dg.getNumintalf().toString());
					deteccion.setNumCodigopostal(dg.getDgCodigosPostales()
							.getId().getCodigo());
				}
				if (deteccion.getCveFkPatron() != null) {
					SatPatron patron = this.patronesService.getById(new Long(
							deteccion.getCveFkPatron()));
					deteccion.setRegPatron(patron.getRegistroPatronal()
							.substring(0,
									patron.getRegistroPatronal().length() - 1));
				}
				if (deteccion.getCvePkTipObra() != null) {
					deteccion.setTipoObra(catalogosServiceBean
							.getTipoObraById(deteccion.getCvePkTipObra()));
				}
				if (deteccion.getCvePkFaseConst() != null) {
					deteccion.setFaseObra(catalogosServiceBean
							.getFaseObraById(deteccion.getCvePkFaseConst()));
				}
				deteccion.setFechaDeteccion(Functions.dateToString(deteccion
						.getFecFechadeteccionFc()));
				if (promocion.getFecFechaAtencion() != null) {
					deteccion.setFechaAtencion(Functions.dateToString(promocion
							.getFecFechaAtencion()));
				}
				if (promocion.getFecFechanotif() != null) {
					deteccion.setFechaNotificacion(Functions
							.dateToString(promocion.getFecFechanotif()));
				}
				if (promocion.getFecFechaemisionpro() != null) {
					deteccion.setFechaEmision(Functions.dateToString(promocion
							.getFecFechaemisionpro()));
				}
			} else if (promocion.getCveTipocorr() == 6L) {

			}
		}

		return deteccion;
	}

	// @RequestMapping(value="/muestraSatic")
	/*
	 * public @ResponseBody CrtDeteccion muestraSatic(@RequestBody CrtPromocion
	 * promocion, HttpServletResponse response,HttpServletRequest request){
	 * CrtDeteccion deteccion = new CrtDeteccion(); UserSession user =
	 * getUsuarioFirmado(request); if(promocion.getCvePromocion()>0){ promocion
	 * = this.promocionServiceBean.consultaPorClave(promocion);
	 * deteccion.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
	 * deteccion.setNumRegObra(promocion.getCveNroregobraSatic().toString());
	 * deteccion =
	 * this.promocionServiceBean.obtieneObraporNumeroRegistro(deteccion);
	 * if(deteccion.getCveFkPatron()!=null){ SatPatron patron =
	 * this.patronesService.getById(deteccion.getCveFkPatron());
	 * deteccion.setNomRazonsocial(patron.getRazonSocial());
	 * deteccion.setTxRfcpatron(patron.getRfc());
	 * deteccion.setTxCurppatron(patron.getCurp()); deteccion.setCveTipocorr(5);
	 * } deteccion.setCvePromocion(promocion.getCvePromocion()); } return
	 * deteccion; }
	 */

	/**
	 * Metodo que consulta con la cve_promocion y llena los datos del
	 * seguimiento de promocion SBC
	 * 
	 * @author Enrique Duran Jimenez
	 * @since 05/06/2012
	 * @param promocion
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/muestraSBC")
	public @ResponseBody
	CrtPromocion muestraSBC(@RequestBody CrtPromocion promocion,
			HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);
		DgDomicilioGeografico dom = new DgDomicilioGeografico();
		CgcCatcriterioseleccion criterios = new CgcCatcriterioseleccion();
		if (promocion.getCvePromocion() > 0) {
			promocion = this.promocionServiceBean.consultaPorClave(promocion);
			promocion.setAuditor(user.getNombreCompleto());
			if (promocion != null && promocion.getCveDomGeoPatron() != null) {
				dom.setDomicilioId(promocion.getCveDomGeoPatron().longValue());
				dom = this.domiciliosInegiServiceBean.consultaPorClave(dom);
				if (dom != null) {
					String numExt = "";
					String numInt = "";
					promocion.setDomCalle(dom.getNomvial());
					promocion.setRefColonia(dom.getDgAsentamiento().getNomAsen());
					if(dom.getNumextnum() != null){
						numExt = dom.getNumextnum().toString();
					}
					if(dom.getNumextalf() != null && !dom.getNumextalf().equals("")){
						numExt += "- " + dom.getNumextalf();
					}
					promocion.setNumNroext(numExt);
					if(dom.getNumintnum() != null){
						numInt = dom.getNumintnum().toString();
					}
					if(dom.getNumintalf() != null && !dom.getNumintalf().equals("")){
						numInt += "- " + dom.getNumintalf();
					}
					promocion.setNumNroint(numInt);
					promocion.setNumCodigopostal(dom.getDgCodigosPostales().getId().getCodigo());
				}
				if (promocion.getIdCriterioSeleccion() != null) {
					criterios.setIdCriterioseleccion(promocion
							.getIdCriterioSeleccion());
					criterios = this.promocionCriteriosServiceBean
							.consultaCriterioPorClave(criterios);
					if (criterios != null) {
						promocion.setDescCriterioseleccion(criterios
								.getDescCriterioseleccion());
					}
				}
				if (promocion.getFecFechaoficiopro() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaOficio(formato.format(promocion
							.getFecFechaoficiopro()));
				}

				if (promocion.getFecFechaemisionpro() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaEmision(formato.format(promocion
							.getFecFechaemisionpro()));
				}
				if (promocion.getCveFkPatron() != null) {
					SatPatron patron = this.patronesService.getById(promocion
							.getCveFkPatron());
					if (patron != null) {
						promocion.setRegPatron(patron.getRegistroPatronal());
						promocion.setRazonSocial(patron.getRazonSocial());
						promocion.setCveFkPatron(patron.getCvePK());
					}
				}
				// Fecha notificacion
				if (promocion.getFecFechanotif() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaNotificacion(formato.format(promocion
							.getFecFechanotif()));
				}
				// Fecha atencion
				if (promocion.getFecFechaAtencion() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaAtencion(formato.format(promocion
							.getFecFechaAtencion()));
				}
				// Fecha inicio
				if (promocion.getFecInicialDictamen() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaInicio(formato.format(promocion
							.getFecInicialDictamen()));
				}
				// Fecha final
				if (promocion.getFecFinalDictamen() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaFin(formato.format(promocion
							.getFecFinalDictamen()));
				}
//				promocion.setRolUsuario(consultaRolUsuario(user
//						.getCveIdUsuario()));
				
				promocion.setRolUsuario(String.valueOf(user.getCveRol()));
			}

		}
		return promocion;
	}

	/**
	 * Metodo que consulta con la cve_promocion y llena los datos del
	 * seguimiento Construccion
	 * 
	 * @author Enrique Duran Jimenez
	 * @since 10/07/2012
	 * @param promocion
	 * @param response
	 * @param request
	 * @return CrtPromocion
	 */
	@RequestMapping(value = "/muestraConstruccion")
	public @ResponseBody
	CrtPromocion muestraConstruccion(@RequestBody CrtPromocion promocion,
			HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);
		DgDomicilioGeografico dom = new DgDomicilioGeografico();
		CgcCatcriterioseleccion criterios = new CgcCatcriterioseleccion();
		if (promocion.getCvePromocion() > 0) {
			promocion = this.promocionServiceBean.consultaPorClave(promocion);
			promocion.setAuditor(user.getNombreCompleto());
			if (promocion != null && promocion.getCveDomGeoPatron() != null) {
				dom.setDomicilioId(promocion.getCveDomGeoPatron().longValue());
				dom = this.domiciliosInegiServiceBean.consultaPorClave(dom);
				if (dom != null) {
					String numExt = "";
					String numInt = "";
					promocion.setDomCalle(dom.getNomvial());
					promocion.setRefColonia(dom.getDgAsentamiento().getNomAsen());
					if(dom.getNumextnum() != null){
						numExt = dom.getNumextnum().toString();
					}
					if(dom.getNumextalf() != null && !dom.getNumextalf().equals("")){
						numExt += "- " + dom.getNumextalf();
					}
					promocion.setNumNroext(numExt);
					if(dom.getNumintnum() != null){
						numInt = dom.getNumintnum().toString();
					}
					if(dom.getNumintalf() != null && !dom.getNumintalf().equals("")){
						numInt += "- " + dom.getNumintalf();
					}
					promocion.setNumNroint(numInt);
					promocion.setNumCodigopostal(dom.getDgCodigosPostales().getId().getCodigo());
				}
				if (promocion.getIdCriterioSeleccion() != null) {
					criterios.setIdCriterioseleccion(promocion
							.getIdCriterioSeleccion());
					criterios = this.promocionCriteriosServiceBean
							.consultaCriterioPorClave(criterios);
					if (criterios != null) {
						promocion.setDescCriterioseleccion(criterios
								.getDescCriterioseleccion());
					}
				}
				if (promocion.getFecFechaoficiopro() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaOficio(formato.format(promocion
							.getFecFechaoficiopro()));
				}

				if (promocion.getFecFechaemisionpro() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaEmision(formato.format(promocion
							.getFecFechaemisionpro()));
				}
				if (promocion.getCveFkPatron() != null) {
					SatPatron patron = this.patronesService.getById(promocion
							.getCveFkPatron());
					if (patron != null) {
						promocion.setRegPatron(patron.getRegistroPatronal());
						promocion.setRazonSocial(patron.getRazonSocial());
						promocion.setCveFkPatron(patron.getCvePK());
					}
				}
				// Fecha notificacion
				if (promocion.getFecFechanotif() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaNotificacion(formato.format(promocion
							.getFecFechanotif()));
				}
				// Fecha atencion
				if (promocion.getFecFechaAtencion() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaAtencion(formato.format(promocion
							.getFecFechaAtencion()));
				}
				// Fecha inicio
				if (promocion.getFecInicialDictamen() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaInicio(formato.format(promocion
							.getFecInicialDictamen()));
				}
				// Fecha final
				if (promocion.getFecFinalDictamen() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaFin(formato.format(promocion
							.getFecFinalDictamen()));
				}
//				promocion.setRolUsuario(consultaRolUsuario(user
//						.getCveIdUsuario()));
				
				promocion.setRolUsuario(String.valueOf(user.getCveRol()));
			}

		}
		return promocion;
	}

	/**
	 * Metodo que obtiene la informacion a detalle de la promocion seleccionada
	 * en la pantalla de consulta
	 * 
	 * @param promocion
	 *            , con la cve promocion seleccionada
	 * @param response
	 * @param request
	 * @return objeto CrtPromocion lleno con la informacion
	 * @author Oscar Beltran Ortega
	 * @Version 1.0.1
	 */
	@RequestMapping(value = "/muestraEXO")
	public @ResponseBody
	CrtPromocion muestraEXO(@RequestBody CrtPromocion promocion,
			HttpServletResponse response, HttpServletRequest request) {

		CgcCatcriterioseleccion criterios = new CgcCatcriterioseleccion();
		DgDomicilioGeografico dom = new DgDomicilioGeografico();

		if (promocion != null && promocion.getCvePromocion() != null) {

			promocion = this.promocionServiceBean.consultaPorClave(promocion);
			if (promocion != null) {
				if (promocion.getIdCriterioSeleccion() != null) {
					criterios.setIdCriterioseleccion(promocion.getIdCriterioSeleccion());
					criterios = this.promocionCriteriosServiceBean.consultaCriterioPorClave(criterios);
					if (criterios != null) {
						promocion.setDescCriterioseleccion(criterios.getDescCriterioseleccion());
					}
				}
			}
			if (promocion.getCveFkPatron() != null) {
				SatPatron patron = this.patronesService.getById(promocion.getCveFkPatron());
				if (patron != null) {

					promocion.setRazonSocial(patron.getRazonSocial() != null ? patron.getRazonSocial() : "");

					if (patron.getRegistroPatronal() != null && patron.getRegistroPatronal().length() >= 10) {
						promocion.setRegPatron(patron.getRegistroPatronal().substring(0, 10));
					}

					promocion.setCveFkPatron(patron.getCvePK());
				}
			}
			if (promocion.getCveDomGeoPatron() != null) {
				dom.setDomicilioId(promocion.getCveDomGeoPatron().longValue());
				dom = this.domiciliosInegiServiceBean.consultaPorClave(dom);
				if (dom != null) {
					promocion.setDomCalle(dom.getNomvial());
					promocion.setRefColonia(dom.getDgAsentamiento().getNomAsen());
					promocion.setNumNroext(dom.getNumextnum() + (dom.getNumextalf() != null ? (" - " +dom.getNumextalf()): ""));
					String numInteriorCompleto = "";
					if(dom.getNumintnum() != null ){
						numInteriorCompleto = dom.getNumintnum().toString();
						if(dom.getNumintalf() != null){
							numInteriorCompleto = numInteriorCompleto +" - " +dom.getNumintalf();
						}
					}else{
						
					}
					promocion.setNumNroint(numInteriorCompleto);
					promocion.setNumCodigopostal(dom.getDgCodigosPostales().getId().getCodigo());
				}
			}
			if (promocion.getFecFechaoficiopro() != null) {
				promocion.setFechaOficio(Functions.dateToString(promocion
						.getFecFechaoficiopro()));
			}
			CrtCorrPromInvita promInvita = new CrtCorrPromInvita();
			promInvita.setCrtPromocion(promocion);
			promInvita = this.promInviaServiceBean
					.consultaPromoInvita(promInvita);
			if (promInvita != null && promInvita.getCrtSolicitudcorr() != null) {
				if (promInvita.getCrtSolicitudcorr()
						.getFecFechaElacoracionCorreccion() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFecSolCorr(formato.format(promInvita
							.getCrtSolicitudcorr()
							.getFecFechaElacoracionCorreccion()));
				}
				if (promInvita.getCrtSolicitudcorr().getFecFechaPeriodoIni() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaIncial(formato.format(promInvita
							.getCrtSolicitudcorr().getFecFechaPeriodoIni()));
				}
				if (promInvita.getCrtSolicitudcorr().getFecFechaPeriodoFin() != null) {
					SimpleDateFormat formato = new SimpleDateFormat(
							"dd-MM-yyyy");
					promocion.setFechaFinal(formato.format(promInvita
							.getCrtSolicitudcorr().getFecFechaPeriodoFin()));
				}
			}

			if (promocion.getFecFechanotif() != null) {
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				promocion.setFechaNotificacion(formato.format(promocion
						.getFecFechanotif()));
			}

			if (promocion.getFecFechaAtencion() != null) {
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				promocion.setFechaAtencion(formato.format(promocion
						.getFecFechaAtencion()));
			}

			CrtInvitacion invitacion = new CrtInvitacion();
			invitacion.setCvePromocion(BigDecimal.valueOf(promocion
					.getCvePromocion()));
			invitacion = this.invitacionServiceBean
					.consultaInvitacionPromocion(invitacion);
			if (invitacion != null && invitacion.getFecFechaemision() != null) {
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				promocion.setFecOficioInvitacion(formato.format(invitacion
						.getFecFechaemision()));
			}

			UserSession user = getUsuarioFirmado(request);
			promocion.setCveUsuario(user.getNomNombre());
			//promocion.setRolUsuario(consultaRolUsuario(user.getCveIdUsuario()));
			promocion.setRolUsuario(String.valueOf(user.getCveRol()));
		}

		return promocion;
	}

	@RequestMapping(value = "/periodos")
	public @ResponseBody
	ArrayList periodos(@RequestBody CrtDeteccion deteccion,
			HttpServletResponse response, HttpServletRequest request) {
		ArrayList lista = new ArrayList();
		// Boolean agrega = true;
		Date fechaInicial;
		Date fechaFinal;
		Integer anioInicial;
		Integer anios;
		Integer mes;
		Integer meses = null;
		String periodo;

		UserSession user = getUsuarioFirmado(request);
		deteccion.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		deteccion = this.promocionServiceBean
				.obtieneObraporNumeroRegistro(deteccion);
		List<CrtDeteccion> lstDet = this.promocionServiceBean
				.obtieneObraporNumReg(deteccion);
		Iterator iterator = lstDet.iterator();

		if (deteccion.getFechaEstimIncio() != null
				&& deteccion.getFechaEstTerm() != null
				&& !deteccion.getFechaEstimIncio().equalsIgnoreCase("")
				&& !deteccion.getFechaEstTerm().equalsIgnoreCase("")) {
			fechaInicial = Functions.stringToDate(deteccion
					.getFechaEstimIncio());
			fechaFinal = Functions.stringToDate(deteccion.getFechaEstTerm());
			if (fechaInicial.before(fechaFinal)) {
				anioInicial = new Integer(deteccion.getFechaEstimIncio()
						.substring(6, 10));
				anios = ((new Integer(deteccion.getFechaEstTerm().substring(6,
						10)) - anioInicial));
				for (int i = 0; i < anios; i++) {
					if (i == 0) {
						mes = new Integer(deteccion.getFechaEstimIncio()
								.substring(3, 5));
						if (mes > 6 && anios > 0)
							meses = 13 - mes;
						else if (mes < 6 && anios > 0)
							meses = mes;
						else if (anios == 0) {
							meses = 0;
							for (int y = mes; y <= new Integer(deteccion
									.getFechaEstTerm().substring(3, 5)); y++) {
								meses = meses + 1;
							}
						}
						for (int x = 0; x < meses; x++) {
							periodo = (anioInicial + i)
									+ ""
									+ ((mes + x) < 10 ? "0" + (mes + x) : mes
											+ x);
							iterator = lstDet.iterator();
							while (iterator.hasNext()) {
								CrtDeteccion det = (CrtDeteccion) iterator
										.next();
								if (det.getPeriodo().equals(periodo))
									periodo = periodo + "1";
							}
							if (periodo.length() == 6) {
								periodo = periodo + "0";
							}
							lista.add(periodo);
						}
					} else if (i == anios) {
						meses = new Integer(deteccion.getFechaFinal()
								.substring(3, 5));
						for (int x = 0; x < meses; x++) {
							periodo = (anioInicial + i) + ""
									+ ((x + 1) < 10 ? "0" + (x + 1) : x + 1);
							iterator = lstDet.iterator();
							while (iterator.hasNext()) {
								CrtDeteccion det = (CrtDeteccion) iterator
										.next();
								if (det.getPeriodo().equals(periodo))
									periodo = periodo + "1";
							}
							if (periodo.length() == 6) {
								periodo = periodo + "0";
							}
							lista.add(periodo);
						}
					} else {
						meses = 13;
						for (int x = 0; x < meses; x++) {
							periodo = (anioInicial + i) + ""
									+ ((x + 1) < 10 ? "0" + (x + 1) : x + 1);
							iterator = lstDet.iterator();
							while (iterator.hasNext()) {
								CrtDeteccion det = (CrtDeteccion) iterator
										.next();
								if (det.getPeriodo().equals(periodo))
									periodo = periodo + "1";
							}
							if (periodo.length() == 6) {
								periodo = periodo + "0";
							}
							lista.add(periodo);
						}
					}
				}
			}
		} else {
			fechaInicial = Functions.stringToDate(deteccion.getFechaIncial());
			fechaFinal = Functions.stringToDate(deteccion.getFechaFinal());
			if (fechaInicial.before(fechaFinal)) {
				anioInicial = new Integer(deteccion.getFechaIncial().substring(
						6, 10));
				anios = ((new Integer(deteccion.getFechaFinal()
						.substring(6, 10)) - anioInicial));
				for (int i = 0; i <= (anios); i++) {
					if (i == 0) {
						mes = new Integer(deteccion.getFechaIncial().substring(
								3, 5));
						if (mes > 6 && anios > 0)
							meses = 13 - mes;
						else if (mes < 6 && anios > 0)
							meses = mes;
						else if (anios == 0) {
							meses = 0;
							for (int y = mes; y <= new Integer(deteccion
									.getFechaFinal().substring(3, 5)); y++) {
								meses = meses + 1;
							}
						}
						for (int x = 0; x < meses; x++) {
							periodo = (anioInicial + i)
									+ ""
									+ ((mes + x) < 10 ? "0" + (mes + x) : mes
											+ x);
							iterator = lstDet.iterator();
							while (iterator.hasNext()) {
								CrtDeteccion det = (CrtDeteccion) iterator
										.next();
								if (det.getPeriodo().equals(periodo))
									periodo = periodo + "1";
							}
							if (periodo.length() == 6) {
								periodo = periodo + "0";
							}
							lista.add(periodo);
						}
					} else if (i == anios) {
						meses = new Integer(deteccion.getFechaFinal()
								.substring(3, 5));
						for (int x = 0; x < meses; x++) {
							periodo = (anioInicial + i) + ""
									+ ((x + 1) < 10 ? "0" + (x + 1) : x + 1);
							iterator = lstDet.iterator();
							while (iterator.hasNext()) {
								CrtDeteccion det = (CrtDeteccion) iterator
										.next();
								if (det.getPeriodo().equals(periodo))
									periodo = periodo + "1";
							}
							if (periodo.length() == 6) {
								periodo = periodo + "0";
							}
							lista.add(periodo);
						}
					} else {
						meses = 13;
						for (int x = 0; x < meses; x++) {
							periodo = (anioInicial + i) + ""
									+ ((x + 1) < 10 ? "0" + (x + 1) : x + 1);
							iterator = lstDet.iterator();
							while (iterator.hasNext()) {
								CrtDeteccion det = (CrtDeteccion) iterator
										.next();
								if (det.getPeriodo().equals(periodo))
									periodo = periodo + "1";
							}
							if (periodo.length() == 6) {
								periodo = periodo + "0";
							}
							lista.add(periodo);
						}
					}
				}
			}
		}
		return lista;
	}

	@RequestMapping(value = "/actualiza")
	public @ResponseBody
	CrtPromocion actualizaDet(@RequestBody CrtDeteccion deteccion) {
		CrtPromocion promocion = new CrtPromocion();

		promocion.setCvePromocion(deteccion.getCvePromocion().longValue());
		promocion = this.promocionServiceBean.consultaPorClave(promocion);
		promocion.setCveFkPatron(deteccion.getCveFkPatron());

		if (deteccion.getCvePkFaseConst() != null
				&& deteccion.getCvePkFaseConst() == -1)
			deteccion.setCvePkFaseConst(null);
		if (deteccion.getCvePkTipObra() != null
				&& deteccion.getCvePkTipObra() == -1)
			deteccion.setCvePkTipObra(null);
		if (deteccion.getCveFkZona() != null && deteccion.getCveFkZona() == -1)
			deteccion.setCveFkZona(null);

		if (deteccion.getFechaEstimIncio() != null
				&& !deteccion.getFechaEstimIncio().equalsIgnoreCase("")) {
			deteccion.setFecFechainicioEst(Functions.stringToDate(deteccion
					.getFechaEstimIncio()));
		}
		if (deteccion.getFechaEstTerm() != null
				&& !deteccion.getFechaEstTerm().equalsIgnoreCase("")) {
			deteccion.setFecFechaterminoEst(Functions.stringToDate(deteccion
					.getFechaEstTerm()));
		}
		if (deteccion.getFechaAtencion() != null
				&& !deteccion.getFechaAtencion().equalsIgnoreCase("")) {
			promocion.setFecFechaAtencion(Functions.stringToDate(deteccion
					.getFechaAtencion()));
			promocion.setFechaAtencion(deteccion.getFechaAtencion());
		}
		if (deteccion.getFechaNotificacion() != null
				&& !deteccion.getFechaNotificacion().equalsIgnoreCase("")) {
			promocion.setFecFechanotif(Functions.stringToDate(deteccion
					.getFechaNotificacion()));
		}

		deteccion.setFecFechadeteccionFc(Functions.FormateaFecha(
				deteccion.getFechaDeteccion(), "-"));
		deteccion.setFecFechareg(Functions.stringToDate(deteccion
				.getFechaRegistro()));

		this.promocionServiceBean.modificar(promocion);

		this.deteccionServicBean.modificar(deteccion);

		return promocion;
	}

	public void replicaPromocion(CrtPromocion promocion) {
		this.promocionServiceBean.replicaPromocion(promocion);
	}

	private String generaFolioInvitacion(Long del, Long sDel, String origen,
			String fecha) {

		Long ultimoValor;
		String consecutivo = this.promocionServiceBean.obtieneFolioInvitacion(
				del, sDel, origen, fecha);

		if (consecutivo != "") {
			ultimoValor = new Long(consecutivo.substring(
					consecutivo.length() - 4, consecutivo.length())) + 1;
			consecutivo = ultimoValor.toString();
			consecutivo = Functions.llenaCeros(consecutivo, 4);
			consecutivo = (del < 10 ? "0" + del.toString() : del.toString())
					+ (sDel < 10 ? "0" + sDel.toString() : sDel.toString())
					+ "/" + origen + "/" + fecha + "/" + consecutivo;
		} else
			consecutivo = (del < 10 ? "0" + del.toString() : del.toString())
					+ (sDel < 10 ? "0" + sDel.toString() : sDel.toString())
					+ "/" + origen + "/" + fecha + "/0001";

		return consecutivo;
	}

	@RequestMapping(value = "/validaRegPatron", method = RequestMethod.POST)
	public @ResponseBody
	CrtDeteccion consultarPat(@RequestBody CrtDeteccion deteccion) {
		SatPatron pat = this.patronesService.validaRegistroPatronalWS(
				deteccion.getRegPatron(), false);
		if (pat != null) {
			deteccion.setCveFkPatron(pat.getCvePK());
			deteccion.setTxRfcpatron(pat.getRfc());
			deteccion.setTxCurppatron(pat.getCurp());
			deteccion.setNomRazonsocial(pat.getRazonSocial());
			deteccion.setActividad(pat.getActividad());
		} else {
			deteccion.setCveFkPatron(null);
			deteccion.setTxRfcpatron(null);
			deteccion.setTxCurppatron(null);
			deteccion.setNomRazonsocial(null);
			deteccion.setActividad(null);
		}
		return deteccion;
	}

	@RequestMapping(value = "/actualizaPromocionSBC")
	public @ResponseBody
	CrtPromocion actualizaPromocionSBC(@RequestBody CrtPromocion pagoPro,
			HttpServletResponse response, HttpServletRequest request) {
		CrtPromocion promocion = new CrtPromocion();
		if (pagoPro.getCvePromocion() > 0) {
			promocion.setCvePromocion(pagoPro.getCvePromocion());
			promocion = promocionServiceBean.consultaPorClave(promocion);
			if (pagoPro.getFechaAtencion() != null
					&& !pagoPro.getFechaAtencion().equalsIgnoreCase("")) {
				promocion.setFecFechaAtencion(Functions.stringToDate(pagoPro
						.getFechaAtencion()));
			}
			if (pagoPro.getFechaNotificacion() != null
					&& !pagoPro.getFechaNotificacion().equalsIgnoreCase("")) {
				promocion.setFecFechanotif(Functions.stringToDate(pagoPro
						.getFechaNotificacion()));
			}
			this.promocionServiceBean.modificar(promocion);

			this.replicaPromocion(promocion);

		}
		return pagoPro;
	}

	/**
	 * Metodo que invoca al servico de actualizacion de la promocion de Exhorto
	 * Extraordinario , invocado desde los flujos de los TABs de seguimiento de
	 * la promocion,
	 * 
	 * @param pagoPro
	 * @param response
	 * @param request
	 * @return CrtPromocion actualizado
	 * @author Oscar Beltran Ortega
	 * @version 1.0.1
	 */
	@RequestMapping(value = "/actualizaPromocionEXO")
	public @ResponseBody
	CrtPromocion actualizaPromocionEXO(@RequestBody CrtPromocion pagoPro,
			HttpServletResponse response, HttpServletRequest request) {
		CrtPromocion promocion = new CrtPromocion();
		UserSession usuario = getUsuarioFirmado(request);

		if (pagoPro.getCvePromocion() != null) {
			promocion.setCvePromocion(pagoPro.getCvePromocion());
			promocion = this.promocionServiceBean.consultaPorClave(promocion);
			if (promocion != null) {
				if (pagoPro.getFechaNotificacion() != null
						&& pagoPro.getFechaNotificacion() != "") {
					promocion.setFecFechanotif(Functions.stringToDate(pagoPro
							.getFechaNotificacion()));
				}
				if (pagoPro.getFechaAtencion() != null
						&& pagoPro.getFechaAtencion() != "") {
					promocion.setFecFechaAtencion(Functions
							.stringToDate(pagoPro.getFechaAtencion()));
				} else {
					promocion.setFecFechaAtencion(null);
				}
				// referencia de cancelacion
				if (pagoPro.getNuVolanteCancela() != null) {
					promocion
							.setNuVolanteCancela(pagoPro.getNuVolanteCancela());
				}
				if (pagoPro.getFechaCancelacion() != null) {
					promocion.setFecFechaCancela(Functions.stringToDate(pagoPro
							.getFechaCancelacion()));
				}
				if (pagoPro.getIdMotivoCancelacion() != null) {
					promocion.setIdMotivoCancelacion(pagoPro
							.getIdMotivoCancelacion());
				}
				if (pagoPro.getFechaAvisoDictamen() != null) {
					promocion.setFecAvisoDictamen(Functions
							.stringToDate(pagoPro.getFechaAvisoDictamen()));
				}
				if (pagoPro.getNumAvisoDictamen() != null) {
					promocion
							.setNumAvisoDictamen(pagoPro.getNumAvisoDictamen());
				}
				if (pagoPro.getFechaInicio() != null) {
					promocion.setFecInicialDictamen(Functions
							.stringToDate(pagoPro.getFechaInicio()));
				}
				if (pagoPro.getFechaFin() != null) {
					promocion.setFecFinalDictamen(Functions
							.stringToDate(pagoPro.getFechaFin()));
				}
				
				
				if(pagoPro.getCveFuncionarioAutoriza()!=null){
					promocion.setCveFuncionarioAutoriza(pagoPro.getCveFuncionarioAutoriza());
				}
				/*
				 * if(pagoPro.getBandera()!=null && pagoPro.getBandera()!= ""){
				 * if("cancelacion".equals(pagoPro.getBandera())){
				 * promocion.setCveEstatus
				 * (ConstantesBusiness.PROMOCION_CANCELADA); }else
				 * if("autorizaDictamen".equals(pagoPro.getBandera())){
				 * promocion
				 * .setCveEstatus(ConstantesBusiness.PROMOCION_AUTORIZADA_AVISO_DICT
				 * ); }
				 * 
				 * 
				 * }if(pagoPro.getCveEstatus()!=null &&
				 * ConstantesBusiness.PROMOCION_INVITACION
				 * .toString().equals(pagoPro.getCveEstatus().toString())){
				 * promocion
				 * .setCveEstatus(ConstantesBusiness.PROMOCION_INVITACION); }
				 */

				// establece el valor del estatus
				promocion.setCveEstatus(defineEstatusPromocion(pagoPro));

				promocion.setCveUsuario(usuario.getCveIdFuncionario()
						.toString());
				promocion.setFecFechareg(new Date());
				promocion.setTxObservaciones(pagoPro.getTxObservaciones());
			}
			promocion = this.promocionServiceBean.agregar(promocion);

			// Replicamos en CgtPromocion

			//this.promocionServiceBean.replicaPromocion(promocion);

		}

		return pagoPro;
	}

	@RequestMapping(value = "/consultaPorClaveUser")
	public @ResponseBody
	CrtPromocion consultaPorClaveUser(@RequestBody CrtPromocion promocion,
			HttpServletRequest request) {
		logger.debug("CVE ID A BUSCAR:" + promocion.getCvePromocion());
		UserSession user = getUsuarioFirmado(request);
		if (promocion.getCvePromocion() > 0) {
			promocion = promocionServiceBean.consultaPorClave(promocion);
			SatPatron patron = this.patronesService.getById(promocion
					.getCveFkPatron());
			if (patron != null) {
				promocion.setRegPatron(patron.getRegistroPatronal());
				promocion.setRazonSocial(patron.getRazonSocial());
			}

			if (user != null) {
				promocion.setRazonSocial(user.getNombreCompleto());
			}
		}
		return promocion;
	}

	@RequestMapping(value = "/consultaInvitacion")
	public @ResponseBody
	CrtInvitacion consultaInvitacion(@RequestBody CrtPromocion promocion) {

		CrtInvitacion invitacion = new CrtInvitacion();
		if (promocion != null & promocion.getCvePromocion() != null) {
			invitacion.setCvePromocion(BigDecimal.valueOf(promocion
					.getCvePromocion()));
			invitacion = this.invitacionServiceBean
					.consultaInvitacionPromocion(invitacion);
			promocion = promocionServiceBean.consultaPorClave(promocion);
			if (invitacion != null && invitacion.getCvePromocion() != null) {
				if (promocion != null) {
					SatPatron patron = this.patronesService.getById(promocion
							.getCveFkPatron());
					if (patron != null) {
						invitacion.setPatron(patron.getRazonSocial());
						invitacion.setRegPatronal(patron.getRegistroPatronal());
					}
					invitacion.setFolioPromocion(promocion
							.getNuFoliopromocion());
					invitacion.setFechaEmision(Functions
							.dateToString(invitacion.getFecFechaemision()));
				}

			} else {
				invitacion = new CrtInvitacion();
				if (promocion.getCvePromocion() != null) {
					invitacion.setCvePromocion(BigDecimal.valueOf(promocion
							.getCvePromocion()));
				}
				if (promocion.getCveDeteccion() != null) {
					invitacion.setCveDeteccion(promocion.getCveDeteccion());
				}
				invitacion.setFolioPromocion(promocion.getNuFoliopromocion());
			}
		}

		return invitacion;
	}

	@RequestMapping(value = "/obtenFuncionariosCanc")
	public @ResponseBody
	CrtPromocion consultaFuncionariosCancelacion(
			@RequestBody CrtPromocion promocion, HttpServletResponse response,
			HttpServletRequest request) {

		UserSession usuario = getUsuarioFirmado(request);
		// checar si es patron , no poner usuario

		promocion.setCveUsuario(usuario.getCveIdFuncionario().toString());

		// hacer aqui la consulta para ver de que catalogo
		// promocion.setCveFuncionarioAutoriza(cveFuncionarioAutoriza)f

		return promocion;
	}

	/**
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con
	 * el objetivo de evitar de fallas en los calculos de fechas. Cabe la
	 * posibilidad de que la creacion y obtencion de las fechas actuales sean
	 * incorrectas si esta operacion se le delega a JAVASCRIPT ya que con
	 * cambiar la fecha en la maquina local los calendarios generados se
	 * reajustaran a esta fecha local, en cambio si la fecha del dia se pide al
	 * servidor no habra este tipo de errores, claro a menos de que la fecha del
	 * servidor tambien este mal.
	 * 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value = "/obtenerFechaServidor.do", method = RequestMethod.POST)
	public @ResponseBody
	String obtenerFechaServidor(HttpServletRequest request) {
		return ConstantesBusiness.dateToStringFormat(new Date(), "dd-MM-yyyy");
	}

	/**
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con
	 * el objetivo de evitar de fallas en los calculos de fechas. Cabe la
	 * posibilidad de que la creacion y obtencion de las fechas actuales sean
	 * incorrectas si esta operacion se le delega a JAVASCRIPT ya que con
	 * cambiar la fecha en la maquina local los calendarios generados se
	 * reajustaran a esta fecha local, en cambio si la fecha del dia se pide al
	 * servidor no habra este tipo de errores, claro a menos de que la fecha del
	 * servidor tambien este mal.
	 * 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value = "/obtenerFechaServidorMinima.do", method = RequestMethod.POST)
	public @ResponseBody
	String obtenerFechaServidorMinima(HttpServletRequest request) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia,
				ConstantesBusiness.dd_mm_yyyy);
	}

	/**
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con
	 * el objetivo de evitar de fallas en los calculos de fechas. Cabe la
	 * posibilidad de que la creacion y obtencion de las fechas actuales sean
	 * incorrectas si esta operacion se le delega a JAVASCRIPT ya que con
	 * cambiar la fecha en la maquina local los calendarios generados se
	 * reajustaran a esta fecha local, en cambio si la fecha del dia se pide al
	 * servidor no habra este tipo de errores, claro a menos de que la fecha del
	 * servidor tambien este mal.
	 * 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value = "/obtenerFechaServidorMinimaFormato.do", method = RequestMethod.POST)
	public @ResponseBody
	String obtenerFechaServidorMinimaFormato(HttpServletRequest request) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia,
				ConstantesBusiness.DD_MM_YYYY);
	}

	@RequestMapping(value = "/obtenerFechaServidorFormato.do", method = RequestMethod.POST)
	public @ResponseBody
	String obtenerFechaServidorFormato(HttpServletRequest request) {
		return ConstantesBusiness.dateToStringFormat(new Date(),
				ConstantesBusiness.DD_MM_YYYY);
	}

	/**
	 * Metodo que establece el estatus del seguimiento de la promocion de
	 * acuerdo a los estatus manejados en la Caratula para la promocion
	 * 
	 * @author Oscar Beltran
	 * @see <code>CatEstatus</code>
	 */
	private Long defineEstatusPromocion(CrtPromocion crtPromocion) {
		Long resultadoEstatus = 0L;
		if (crtPromocion.getFechaNotificacion() != null	&& !crtPromocion.getFechaNotificacion().equals("")
				&& crtPromocion.getFechaCancelacion() == null && crtPromocion.getFechaAvisoDictamen() == null  && crtPromocion.getFechaAtencion() !="") {
			resultadoEstatus = CatEstatus.EN_PROCESO_ATENCION_OFICIO_PROMOCION.getId();
		} else if (crtPromocion.getFechaCancelacion() != null && !crtPromocion.getIdMotivoCancelacion().equals("-1")
				&& crtPromocion.getNuVolanteCancela() != null) {
			resultadoEstatus = CatEstatus.FOLIO_CANCELADO.getId();
		} else if (crtPromocion.getFecOficioInvitacion() != null) {
			resultadoEstatus = CatEstatus.ADHERIDO_AL_PROGRAMA_CORRECCION_INVITACION.getId();
		} else if (crtPromocion.getFechaAtencion() != "" && crtPromocion.getFechaAvisoDictamen() == null) {
			resultadoEstatus = CatEstatus.OFICIO_INIVITACION_ATENDIDO.getId();
		} else if (crtPromocion.getFechaAvisoDictamen() != null && crtPromocion.getNumAvisoDictamen() != null
				&& crtPromocion.getFechaInicio() != null && crtPromocion.getFechaFin() != null) {
			resultadoEstatus = CatEstatus.CORRECCION_DERIVADA_DICTAMEN.getId();
		}else if(crtPromocion.getFechaNotificacion() != null && crtPromocion.getFechaAtencion() ==""){
			resultadoEstatus = CatEstatus.EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION.getId();
		}
		return resultadoEstatus;
		

	}

	public String consultaRolUsuario(Long idUsuario) {
		String rol = null;
		String queryRol = "select  CVE_ROL from seg_perfil_usuario WHERE CVE_ID_USUARIO = "
				+ idUsuario.toString();

		ArrayList listaFuncionarios = (ArrayList) iCatalogoServiceBean
				.consultaSQL(queryRol);

		rol = listaFuncionarios.get(0).toString();

		return rol;
	}

}
