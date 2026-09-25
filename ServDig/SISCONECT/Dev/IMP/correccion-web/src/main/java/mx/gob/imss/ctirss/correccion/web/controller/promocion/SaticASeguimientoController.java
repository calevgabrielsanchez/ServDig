package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegRegularizarObraGenericoTabVO;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.interfaces.RegularizacionService;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IObraService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.satica.SeguimientoPromocionSaticaVO;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.satica.SeguimientoSaticATabVO;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/promocion/seguimiento/satica")
public class SaticASeguimientoController extends AbstractController {

	@Autowired
	private PromocionService<CrtPromocion> promocionServiceBean;

	@Autowired
	private PromocionService<CgcCatcriterioseleccion> promocionCriteriosServiceBean;

	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;

	@Autowired
	private IPatronesService<?> patronesService;

	@Autowired
	private ICatalogoService<AbstractModel> iCatalogoServiceBean;

	@Autowired
	private IObraService<?> obraService;

	@Autowired
	private RegularizacionService<CrtRegulapagos> regularizacionService;

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(SaticASeguimientoController.class);

	@RequestMapping(value = "/mostrarSatica")
	public @ResponseBody
	SeguimientoPromocionSaticaVO mostrarSatica(
			@RequestBody CrtPromocion promocion, HttpServletRequest request) {
		CrtDeteccion deteccion = new CrtDeteccion();
		CgcCatcriterioseleccion criterios = new CgcCatcriterioseleccion();
		SeguimientoPromocionSaticaVO saticaVO = new SeguimientoPromocionSaticaVO();
		saticaVO.setSaticaSeguimientoTabVO(new SeguimientoSaticATabVO());

		UserSession user = getUsuarioFirmado(request);

		logger.debug("---------------------------------------------------");
		logger.debug("ejecutando: /promocion/seguimiento/satica/mostrar");

		if (promocion.getCvePromocion() > 0) {
			// Se obtienen datos de la promocion
			promocion = this.promocionServiceBean.consultaPorClave(promocion);

			// Se obtiene la descripcion del criterio de seleccion
			if (promocion != null) {
				if (promocion.getIdCriterioSeleccion() != null) {
					criterios.setIdCriterioseleccion(promocion
							.getIdCriterioSeleccion());
					criterios = this.promocionCriteriosServiceBean
							.consultaCriterioPorClave(criterios);
					if (criterios != null) {
						saticaVO.setDescripcionCriterioseleccion(criterios
								.getDescCriterioseleccion());
					}
				}
				saticaVO.setFolioPromocion(promocion.getNuFoliopromocion());
				saticaVO.setFechaOficioPromocion(Functions
						.dateToString(promocion.getFecFechaoficiopro()));
				saticaVO.setNumeroOficioPromocion(promocion.getNuOficiopro());
				saticaVO.setEstatusPromocion(promocion.getCveEstatus()
						.toString());
				saticaVO.setRolUsuario(consultaRolUsuario(user
						.getCveIdUsuario()));
				saticaVO.getSaticaSeguimientoTabVO().setFecNotificacionOficio(
						Functions.dateToString(promocion.getFecFechanotif()));
				saticaVO.getSaticaSeguimientoTabVO()
						.setFecAtencionOficio(
								Functions.dateToString(promocion
										.getFecFechaAtencion()));
				saticaVO.getSaticaSeguimientoTabVO().setObservaciones(
						promocion.getTxObservaciones());
				if (promocion.getCveNroregobraSatic() != null) {
					saticaVO.getSaticaSeguimientoTabVO().setNumRegistroObra(
							promocion.getCveNroregobraSatic().toString());
				}

				// set regularization date
				CrtRegulapagos regulaPago = new CrtRegulapagos();
				regulaPago.setCvePromocion(Long.valueOf(promocion.getCvePromocion()));
				regulaPago = (CrtRegulapagos) this.regularizacionService.consultaPorClavePromocion(regulaPago);
				// exist?
				if (regulaPago.getCveRegulapagos() != 0) {
					SegRegularizarObraGenericoTabVO regulaPagoVO = new SegRegularizarObraGenericoTabVO();
					regulaPagoVO.setCveRegulaPagos(String.valueOf(regulaPago.getCveRegulapagos()));

					// regulaPagoVO.setPeriodoRegDel();
					saticaVO.getSaticaSeguimientoTabVO().setFecIniPeriodo(Functions.dateToString(regulaPago.getFecPeridodini()));
					// regulaPagoVO.setPeriodoRegAl();
					saticaVO.getSaticaSeguimientoTabVO().setFecFinPeriodo(Functions.dateToString(regulaPago.getFecPeriodofin()));
				}
			}

			// logger.info(" desc criterio seleccion->"+promocion.getDescCriterioseleccion());
			if (promocion.getCveDeteccion() != null
					&& promocion.getCveDeteccion().intValue() > 0) {
				deteccion.setCveDeteccion(promocion.getCveDeteccion()
						.longValue());
				deteccion = this.promocionServiceBean
						.obtieneDeteccionporClave(deteccion);
				deteccion.setFechaRegistro(Functions.dateToString(deteccion
						.getFecFechareg()));

				// Se obtienen datos del domicilio
				DgDomicilioGeografico dg = new DgDomicilioGeografico();
				dg.setDomicilioId(deteccion.getDomicilioId());
				dg = domiciliosInegiServiceBean.consultaPorClave(dg);
				if (dg != null) {

					if (dg.getDgCatLocalidad().getDgCatMunicipio()
							.getDgCatEstado().getNomEnt() != null
							&& !dg.getDgCatLocalidad().getDgCatMunicipio()
									.getDgCatEstado().getNomEnt()
									.equalsIgnoreCase("")) {
						saticaVO.setEstado(dg.getDgCatLocalidad()
								.getDgCatMunicipio().getDgCatEstado()
								.getNomEnt());
					}
					if (dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun() != null
							&& !dg.getDgCatLocalidad().getDgCatMunicipio()
									.getNomMun().equalsIgnoreCase("")) {
						saticaVO.setMunicipio(dg.getDgCatLocalidad()
								.getDgCatMunicipio().getNomMun());
					}

					deteccion
							.setRefColonia(dg.getDgAsentamiento().getNomAsen());
					saticaVO.setColonia(dg.getDgAsentamiento().getNomAsen());
					if (dg.getNomvial() != null) {
						if (!dg.getNomvial().equalsIgnoreCase(""))
							deteccion.setDomCalle(dg.getNomvial());
						saticaVO.setCalle(dg.getNomvial());
					} else {
						deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin()
								.getNomVia());
						saticaVO.setCalle(dg.getDgVialidadByCveViaPrin()
								.getNomVia());
					}
					if(dg.getNumextnum()!=null && dg.getNumextnum()!=0){
						deteccion.setNumNroext(dg.getNumextnum().toString());
						saticaVO.setNumExterior(dg.getNumextnum().toString());
					}else{
						deteccion.setNumNroext("");
						saticaVO.setNumExterior("");
					}


					if (dg.getNumextalf() != null) {
						saticaVO.setNumExterior(saticaVO.getNumExterior()
								+ " - " + dg.getNumextalf());
					}

					if (dg.getNumintnum() != null) {
						saticaVO.setNumInterior(dg.getNumintnum().toString());
					}

					if (dg.getNumintalf() != null) {
						saticaVO.setNumInterior(saticaVO.getNumInterior()
								+ " - " + dg.getNumintalf().toString());
					}

					deteccion.setNumCodigopostal(dg.getDgCodigosPostales()
							.getId().getCodigo());
					saticaVO.setCodigoPostal(dg.getDgCodigosPostales().getId()
							.getCodigo());
				}

				if (deteccion.getCveFkPatron() != null) {
					SatPatron patron = this.patronesService.getById(new Long(
							deteccion.getCveFkPatron()));
					saticaVO.setCveFkPatron(deteccion.getCveFkPatron()
							.toString());
					saticaVO.setRegistroPatronalDeteccion(patron
							.getRegistroPatronal());
					saticaVO.setRazonSocialDeteccion(patron.getRazonSocial());
				}

				// Se obtienen los datos del patron
				if (promocion.getCveFkPatron() != null) {
					SatPatron patron = this.patronesService.getById(new Long(
							promocion.getCveFkPatron()));
					saticaVO.setCveFkPatron(promocion.getCveFkPatron()
							.toString());
					saticaVO.setRegistroPatronal(patron.getRegistroPatronal()
							.substring(0,
									patron.getRegistroPatronal().length() - 1));
					saticaVO.setRazonSocial(patron.getRazonSocial());
				}

			}
		}

		return saticaVO;
	}

	@RequestMapping(value = "/validarObraSatica", method = RequestMethod.POST)
	public @ResponseBody
	SatObra validarObraSatica(@RequestBody CrtSolicitudcorr paramObra,
			HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);
		long obraDelegacion = 0L;
		long obraSubDelegacion = 0L;
		SatObra obra = null;
		try {
			obra = this.obraService.validaObra(paramObra.getNumeroObra());
		} catch (Exception e) {
			obra = new SatObra();
			obra.setError("El registro de obra es invalido");
			return obra;
		}

		if (obra != null) {
			SatPatron patron = null;

			obraDelegacion = (long) obra.getUbicacion().getMunicipio()
					.getSacSubdelegacion().getSacDelegacion().getCvePk();
			obraSubDelegacion = obra.getUbicacion().getMunicipio()
					.getSacSubdelegacion().getCvePk();

			logger.debug("obra deleg="
					+ obra.getUbicacion().getMunicipio()
							.getSacEntidadFederativa().getCvePk()
					+ ", obra subdel="
					+ obra.getUbicacion().getMunicipio().getSacSubdelegacion()
							.getSacDelegacion().getCvePk() + ", usr del="
					+ user.getIdDelegacion() + ", usr subdel="
					+ user.getIdSubDelegacion());

			if (obraDelegacion == user.getIdDelegacion()
					&& obraSubDelegacion == user.getIdSubDelegacion()) {
				patron = patronesService.getById(obra.getCveFkPatron()
						.longValue());
			} else {
				obra.setError("La obra no pertenece a la delegacion o subdelegacion");
			}
			if (patron == null) {
				patron = new SatPatron();
			}
			obra.getUbicacion().setPatron(patron);
		}
		return obra;
	}

	@RequestMapping(value = "/guardarSaticaSeguimiento", method = RequestMethod.POST)
	public @ResponseBody
	SeguimientoPromocionSaticaVO guardarSaticaSeguimiento(
			@RequestBody SeguimientoPromocionSaticaVO saticaVO,
			HttpServletResponse response, HttpServletRequest request) {

		CrtPromocion promocion = new CrtPromocion();

		try {
			String clavePromocion = saticaVO.getCvePromocion();
			String strEstatusPromocion = saticaVO.getEstatusPromocion();
			Long estatusPromocion = null;
			boolean tieneDatos = false;

			// Verifica si existen datos por guardar
			if (saticaVO.getSaticaSeguimientoTabVO() != null
					|| saticaVO.getCveFkPatron() != null) {
				tieneDatos = true;
			}

			if (strEstatusPromocion != null && !strEstatusPromocion.equals("")) {
				estatusPromocion = new Long(strEstatusPromocion);
			}

			if (clavePromocion != null && !clavePromocion.equals("")
					&& tieneDatos) {
				UserSession user = getUsuarioFirmado(request);
				promocion.setCvePromocion(new Long(saticaVO.getCvePromocion()));
				promocion = this.promocionServiceBean
						.consultaPorClave(promocion);

				if (saticaVO.getCveFkPatron() != null
						&& !saticaVO.getCveFkPatron().equals("")) {
					promocion
							.setCveFkPatron(new Long(saticaVO.getCveFkPatron()));
				}

				if (estatusPromocion != null) {
					promocion.setCveEstatus(estatusPromocion);
				}

				promocion = getDatosTabSeguimiento(saticaVO, promocion);

				promocion.setFecFechareg(new Date());
				promocion.setCveUsuario(user.getCveIdUsuario().toString());

				this.promocionServiceBean.modificar(promocion);
				logger.debug("termino: /promocion/seguimiento/satica/guardarSaticaSeguimiento");
				saticaVO.setExito("La informaci\u00f3n ha sido guardada");
			} else {
				saticaVO.setError("No se encontraron datos por guardar");
			}
		} catch (Exception e) {
			saticaVO.setError("Ocurri\u00f3 un error: " + e.getMessage());
			e.printStackTrace();
		}
		saticaVO.setExito("La informaci\u00f3n ha sido guardada");

		return saticaVO;
	}

	private CrtPromocion getDatosTabSeguimiento(
			SeguimientoPromocionSaticaVO saticaVO, CrtPromocion promocionParam) {
		if (saticaVO.getSaticaSeguimientoTabVO() != null) {
			String fechaAtencion = saticaVO.getSaticaSeguimientoTabVO()
					.getFecAtencionOficio();
			String fechaNotificacion = saticaVO.getSaticaSeguimientoTabVO()
					.getFecNotificacionOficio();
			String periodoInicio = saticaVO.getSaticaSeguimientoTabVO()
					.getFecIniPeriodo();
			String periodoFin = saticaVO.getSaticaSeguimientoTabVO()
					.getFecFinPeriodo();
			String numRegistroObra = saticaVO.getSaticaSeguimientoTabVO()
					.getNumRegistroObra();
			String observaciones = saticaVO.getSaticaSeguimientoTabVO()
					.getObservaciones();

			if (fechaAtencion != null && !fechaAtencion.equals("")) {
				promocionParam.setFecFechaAtencion(Functions
						.stringToDate(fechaAtencion));
			}
			if (fechaNotificacion != null && !fechaNotificacion.equals("")) {
				promocionParam.setFecFechanotif(Functions
						.stringToDate(fechaNotificacion));
			}
			if (numRegistroObra != null && !numRegistroObra.equals("")) {
				promocionParam.setCveNroregobraSatic(new BigDecimal(
						numRegistroObra));
			}
			if (periodoInicio != null && !periodoInicio.equals("")) {
				promocionParam.setFecInicialDictamen(Functions
						.stringToDate(periodoInicio));
			}
			if (periodoFin != null && !periodoFin.equals("")) {
				promocionParam.setFecFinalDictamen(Functions
						.stringToDate(periodoFin));
			}
			if (observaciones != null && !observaciones.equals("")) {
				promocionParam.setTxObservaciones(observaciones);
			}
		}
		return promocionParam;
	}

	public String consultaRolUsuario(Long idUsuario) {
		String rol = null;
		String queryRol = "select  CVE_ROL from seg_perfil_usuario WHERE CVE_ID_USUARIO = "
				+ idUsuario.toString();

		ArrayList<?> listaFuncionarios = (ArrayList<?>) iCatalogoServiceBean.consultaSQL(queryRol);

		rol = listaFuncionarios.get(0).toString();

		return rol;
	}

}
