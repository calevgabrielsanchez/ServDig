package mx.gob.imss.cit.cda.service.cuentaindividual.bussines;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.cuentaindividual.utility.CuentaIndividualNssUtilityLocal;
import mx.gob.imss.cit.cda.service.cuentaindividual.utility.CuentaIndividualUtilityLocal;
import mx.gob.imss.cit.cda.service.cuentaindividual.utility.CuentaIndividualWsUtilityLocal;
import mx.gob.imss.cit.cda.service.entity.CuentaIndividualLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.MotivosAclaracionErroneosException;
import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.cda.service.utility.FiltrosUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.RespuestaCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual_Service;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TipoAclaracionCuentaIndividualException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.Page;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualCorreccion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualPeriodo;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageCuentaIndividualPeriodo;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.enums.OrigenPeriodoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoMovtoAseguradoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.persistence.DicMovCorrecCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "cuentaIndividualBusiness", mappedName = "cuentaIndividualBusiness")
public class CuentaIndividualBusiness extends AbstractServiceUtility implements
		CuentaIndividualRemote {

	private final Logger LOGGER = LoggerFactory
			.getLogger(CuentaIndividualBusiness.class);
	private static final int ANIO_FINAL = 9999;
	private static final int MES_FINAL = 11;
	private static final int DIA_FINAL = 31;

	private String patronWs;

	@EJB
	private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;

	@EJB
	private FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filtrosUtilityLocal;

	@EJB
	private CuentaIndividualLocal cuentaIndividualLocal;

	@EJB
	private CuentaIndividualUtilityLocal cuentaIndividualUtility;

	@EJB
	private SolicitudBusinessRemote solicitudBusiness;

	@EJB
	private CuentaIndividualWsUtilityLocal cuentaIndividualWsUtilityLocal;

	@EJB
	private CuentaIndividualNssUtilityLocal cuentaIndividualNssUtilityLocal;

	@EJB
	private CorreccionDatosRemote CorreccionDatosBusinness;

	@EJB
	private DetalleNssCdaLocal detalleNssCdaLocal;

	@Override
	public List<PeriodoMovimientoAfiliatorio> consultarMovimientosCuentaIndividual (
			String nss) throws CuentaIndividualNoDisponibleException {

		try {
			List<PeriodoMovimientoAfiliatorio> periodos = cuentaIndividualWsUtilityLocal.obtenerPeriodosMovimientoAfiliatorioNSS(nss);

			List<PeriodoMovimientoAfiliatorio> periodosFormateados;

			if (periodos != null && !periodos.isEmpty()) {
				ordenarCuentasIndividuales(periodos);
				periodosFormateados = filtrarMovimientoAfiliatorios(agruparPeriodosPorPatron(periodos));
				ordenarCuentasIndividualesFormato(periodosFormateados);
			} else {
				throw new CuentaIndividualNoDisponibleException("No hay servicio cuenta individual");
			}

			return periodosFormateados;
		} catch (Exception e) {
			throw new CuentaIndividualNoDisponibleException(nss);
		}

		
	}

	private HashMap<String, List<PeriodoMovimientoAfiliatorio>> agruparPeriodosPorPatron(
			List<PeriodoMovimientoAfiliatorio> cuentas) {
		HashMap<String, List<PeriodoMovimientoAfiliatorio>> hashMap = new HashMap<String, List<PeriodoMovimientoAfiliatorio>>();
		for (PeriodoMovimientoAfiliatorio periodo : cuentas) {
			if (!hashMap.containsKey(periodo.getNrp())) {
				List<PeriodoMovimientoAfiliatorio> list = new ArrayList<PeriodoMovimientoAfiliatorio>();
				list.add(periodo);
				hashMap.put(periodo.getNrp(), list);
			} else {
				hashMap.get(periodo.getNrp()).add(periodo);
			}
		}
		return hashMap;
	}

	private List<PeriodoMovimientoAfiliatorio> filtrarMovimientoAfiliatorios(
			HashMap<String, List<PeriodoMovimientoAfiliatorio>> cuentas) {
		List<PeriodoMovimientoAfiliatorio> periodoMovimientoAfiliatorios = new ArrayList<PeriodoMovimientoAfiliatorio>();

		for (Map.Entry<String, List<PeriodoMovimientoAfiliatorio>> entry : cuentas
				.entrySet()) {
			List<PeriodoMovimientoAfiliatorio> periodos = new ArrayList<PeriodoMovimientoAfiliatorio>();
			periodos.addAll(entry.getValue());

			PeriodoMovimientoAfiliatorio primerMovimientoCuentaIndividual = periodos
					.get(0);
			PeriodoMovimientoAfiliatorio ultimoMovimientoCuentaIndividual = periodos
					.get(periodos.size() - 1);
			correccionAseguradoUtilityLocal.filtraCuentasCDA(periodos,
					filtrosUtilityLocal);
			if (!periodos.isEmpty()) {
				if (TipoMovtoAseguradoEnum.MODIF_SALARIO
						.getIdTipoMovtoAsegurado().equals(
								periodos.get(0).getTipoMovimientoInicial()
										.getIdTipoMvtoAsegurado())) {
					periodos.add(0, primerMovimientoCuentaIndividual);
				}
				if (filtrosUtilityLocal
						.shouldRemoveLast(ultimoMovimientoCuentaIndividual)) {
					periodos.add(ultimoMovimientoCuentaIndividual);
				}
				periodoMovimientoAfiliatorios
						.addAll(prepararListaRespuesta(periodos));
			}
		}
		return periodoMovimientoAfiliatorios;
	}

	private List<PeriodoMovimientoAfiliatorio> prepararListaRespuesta(
			List<PeriodoMovimientoAfiliatorio> cuentas) {
		List<PeriodoMovimientoAfiliatorio> cuentasFormato = new ArrayList<PeriodoMovimientoAfiliatorio>();
		Iterator<PeriodoMovimientoAfiliatorio> cuentasIterator = cuentas
				.iterator();
		while (cuentasIterator.hasNext()) {
			PeriodoMovimientoAfiliatorio cuentaInicial = cuentasIterator.next();
			PeriodoMovimientoAfiliatorio cuentaFinal = null;

			if (TipoMovtoAseguradoEnum.BAJAS.getIdTipoMovtoAsegurado().equals(
					cuentaInicial.getTipoMovimientoFinal()
							.getIdTipoMvtoAsegurado())
					|| TipoMovtoAseguradoEnum.ACTUAL.getIdTipoMovtoAsegurado()
							.equals(cuentaInicial.getTipoMovimientoFinal()
									.getIdTipoMvtoAsegurado())) {
				cuentaFinal = cuentaInicial;
			} else if (cuentasIterator.hasNext()) {
				cuentaFinal = cuentasIterator.next();
			}

			PeriodoMovimientoAfiliatorio periodoIndividual = new PeriodoMovimientoAfiliatorio();
			periodoIndividual.setFechaInicioMovimiento(cuentaInicial
					.getFechaInicioMovimiento());
			periodoIndividual.setTipoMovimientoInicial(cuentaInicial
					.getTipoMovimientoInicial());
			if (cuentaFinal != null) {
				periodoIndividual.setFechaFinalMovimiento(cuentaFinal
						.getFechaFinalMovimiento());
				periodoIndividual.setTipoMovimientoFinal(cuentaFinal
						.getTipoMovimientoFinal());
				periodoIndividual
						.setNrp(generarRegistroPatronalCompleto(cuentaFinal));
				periodoIndividual.setNss(cuentaFinal.getNss());
			} else {
				periodoIndividual
						.setNrp(generarRegistroPatronalCompleto(cuentaInicial));
				periodoIndividual.setNss(cuentaInicial.getNss());
			}
			Calendar fechaFinal = Calendar.getInstance();
			fechaFinal.setTime(new Date());
			fechaFinal.set(ANIO_FINAL, MES_FINAL, DIA_FINAL);

			if (periodoIndividual.getFechaFinalMovimiento() == null
					|| DateUtils.isSameDay(fechaFinal.getTime(),
							periodoIndividual.getFechaFinalMovimiento())) {
				periodoIndividual.setFechaFinalMovimiento(null);
			}

			cuentasFormato.add(periodoIndividual);
		}
		return cuentasFormato;
	}

	//
	@Override
	public List<PeriodoCuentaIndividual> consultarInformacionMovimientosCuentaIndividual(
			String nss, Long cveIdTramite, Long idDetalleNss) {

		WSNssCuentaIndividual_Service service = new WSNssCuentaIndividual_Service();
		WSNssCuentaIndividual port = service.getWSNssCuentaIndividualPort();
		List<PeriodoCuentaIndividual> cuentas = new ArrayList<PeriodoCuentaIndividual>();
		RespuestaCuentaIndividual respuesta = null;
		try {
			if (!cuentaIndividualLocal.existenDatosIdtramite(cveIdTramite)) {
				LOGGER.error(
						"WebserviceCurp. Thread. El proceso inicia consulta a CuentaIndividual. {} ",
						new Date());
				respuesta = port.getCuentaIndividual(nss);
				cuentas = cuentaIndividualUtility
						.convertRespuestaWsToModeloDominio(respuesta,
								idDetalleNss);
				// guardarPeriodos(cuentas,OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());

				LOGGER.debug(
						"WebserviceCurp. Thread. Se finaliza la consulta satisfactoriamente a CuentaIndividual. {} ",
						new Date());
			}
			// consultar a base datos
		} catch (Exception e) {
			LOGGER.error(
					"WebserviceCurp. Thread. Se genero un error al accesar el webservice",
					e);
		}
		LOGGER.debug(
				"PeriodoNSSMovimientoAfiliatorio.consultarInformacionMovimientosCuentaIndividual(String) {} ",
				new Date());

		return cuentas;
	}

	private void ordenarCuentasIndividuales(
			List<PeriodoMovimientoAfiliatorio> periodos) {
		Collections.sort(periodos,
				new Comparator<PeriodoMovimientoAfiliatorio>() {

					@Override
					public int compare(PeriodoMovimientoAfiliatorio o1,
							PeriodoMovimientoAfiliatorio o2) {
						return compareByDate(o1.getFechaFinalMovimiento(),
								o2.getFechaFinalMovimiento()) == 0 ? compareByDate(
								o1.getFechaInicioMovimiento(),
								o2.getFechaInicioMovimiento()) : compareByDate(
								o1.getFechaFinalMovimiento(),
								o2.getFechaFinalMovimiento());
					}
				});
	}

	private void ordenarCuentasIndividualesFormato(
			List<PeriodoMovimientoAfiliatorio> periodos) {
		Collections.sort(periodos,
				new Comparator<PeriodoMovimientoAfiliatorio>() {

					@Override
					public int compare(PeriodoMovimientoAfiliatorio o1,
							PeriodoMovimientoAfiliatorio o2) {
						return -1
								* (compareByDate(o1.getFechaFinalMovimiento(),
										o2.getFechaFinalMovimiento()) == 0 ? compareByDate(
										o1.getFechaInicioMovimiento(),
										o2.getFechaInicioMovimiento())
										: compareByDate(
												o1.getFechaFinalMovimiento(),
												o2.getFechaFinalMovimiento()));
					}
				});
	}

	private int compareByDate(Date date1, Date date2) {
		int fecha = date1 == null ? 1 : 0;
		int fecha2 = date2 == null ? 1 : 0;
		return (fecha + fecha2) == 0 ? date1.compareTo(date2) : fecha - fecha2;
	}

	@Override
	public List<PeriodoMovimientoAfiliatorio> consultarUltimoMovimientoCuentaIndividual(
			String nss) {

		List<PeriodoMovimientoAfiliatorio> periodosMovimientoFinal = null;

		List<PeriodoMovimientoAfiliatorio> periodos = cuentaIndividualWsUtilityLocal.obtenerPeriodosMovimientoAfiliatorioNSS(nss);
		if (periodos != null && !periodos.isEmpty()) {
			ordenarCuentasIndividuales(periodos);
			correccionAseguradoUtilityLocal.filtraCuentasCDAUltimoMovimiento(
					periodos, filtrosUtilityLocal);
			if (!periodos.isEmpty()) {
				periodosMovimientoFinal = prepararRespuestaMovimientoFinal(agruparPeriodosPorFechaFinal(periodos));
			}
		}
		return periodosMovimientoFinal;
	}

	private List<PeriodoMovimientoAfiliatorio> agruparPeriodosPorFechaFinal(
			List<PeriodoMovimientoAfiliatorio> cuentas) {
		List<PeriodoMovimientoAfiliatorio> periodosMovimientoFinal = new ArrayList<PeriodoMovimientoAfiliatorio>();
		periodosMovimientoFinal.add(cuentas.get(cuentas.size() - 1));
		boolean fechaMenor = false;
		int i = cuentas.size() - 2;

		while (i >= 0 && !fechaMenor) {
			if (DateUtils.truncate(cuentas.get(i).getFechaFinalMovimiento(),
					Calendar.DATE).compareTo(
					DateUtils.truncate(periodosMovimientoFinal.get(0)
							.getFechaFinalMovimiento(), Calendar.DATE)) < 0) {
				fechaMenor = true;
			} else {
				periodosMovimientoFinal.add(cuentas.get(i));
			}
			i--;
		}

		return periodosMovimientoFinal;
	}

	private List<PeriodoMovimientoAfiliatorio> prepararRespuestaMovimientoFinal(
			List<PeriodoMovimientoAfiliatorio> ultimosMovimientos) {
		List<PeriodoMovimientoAfiliatorio> periodosMovimientoAfiliatorio = new ArrayList<PeriodoMovimientoAfiliatorio>();

		for (PeriodoMovimientoAfiliatorio ultimoPeriodoMovimientoAfiliatorio : ultimosMovimientos) {
			PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio = new PeriodoMovimientoAfiliatorio();
			if (TipoMovtoAseguradoEnum.ACTUAL.getIdTipoMovtoAsegurado().equals(
					ultimoPeriodoMovimientoAfiliatorio.getTipoMovimientoFinal()
							.getIdTipoMvtoAsegurado())) {
				periodoMovimientoAfiliatorio
						.setTipoMovimientoFinal(ultimoPeriodoMovimientoAfiliatorio
								.getTipoMovimientoInicial());
				periodoMovimientoAfiliatorio
						.setFechaFinalMovimiento(ultimoPeriodoMovimientoAfiliatorio
								.getFechaInicioMovimiento());
			}

			if (TipoMovtoAseguradoEnum.BAJAS.getIdTipoMovtoAsegurado().equals(
					ultimoPeriodoMovimientoAfiliatorio.getTipoMovimientoFinal()
							.getIdTipoMvtoAsegurado())) {
				periodoMovimientoAfiliatorio
						.setTipoMovimientoFinal(ultimoPeriodoMovimientoAfiliatorio
								.getTipoMovimientoFinal());
				periodoMovimientoAfiliatorio
						.setFechaFinalMovimiento(ultimoPeriodoMovimientoAfiliatorio
								.getFechaFinalMovimiento());
			}

			periodoMovimientoAfiliatorio
					.setCveModalidad(ultimoPeriodoMovimientoAfiliatorio
							.getCveModalidad());
			periodoMovimientoAfiliatorio
					.setNrp(ultimoPeriodoMovimientoAfiliatorio.getNrp());
			periodoMovimientoAfiliatorio
					.setNrp(generarRegistroPatronalCompleto(periodoMovimientoAfiliatorio));
			periodoMovimientoAfiliatorio
					.setNss(ultimoPeriodoMovimientoAfiliatorio.getNss());

			periodosMovimientoAfiliatorio.add(periodoMovimientoAfiliatorio);
		}

		return periodosMovimientoAfiliatorio;

	}

	private String generarRegistroPatronalCompleto(
			PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio) {
		StringBuilder nrp = new StringBuilder();
		nrp.append(periodoMovimientoAfiliatorio.getNrp());
		nrp.append(periodoMovimientoAfiliatorio.getCveModalidad().getDesCorta());
		LOGGER.info("---CDA--- NRP sin registro verificador {}", nrp.toString());
		nrp.append(correccionAseguradoUtilityLocal
				.generaDigitoVerificadorRP(nrp.toString()));
		return nrp.toString();
	}

	@Override
	public List<CuentaIndividualVO> obtenerMovimientosActualizados(
			Long cveIdTramite) {

		return cuentaIndividualLocal.obtenerListaNSS(cveIdTramite);
	}

	@Override
	public List<CuentaIndividualVO> consultarInformacionPreviaCuentaIndividual(
			String nss, Long cveIdTramite) {

		WSNssCuentaIndividual_Service service = new WSNssCuentaIndividual_Service();
		WSNssCuentaIndividual port = service.getWSNssCuentaIndividualPort();
		List<CuentaIndividualVO> cuentas = new ArrayList<CuentaIndividualVO>();
		RespuestaCuentaIndividual respuesta = null;
		try {
			if (!cuentaIndividualLocal.existenDatosIdtramite(cveIdTramite)) {
				LOGGER.error(
						"WebserviceCurp. Thread. El proceso inicia consulta a CuentaIndividual. {} ",
						new Date());
				respuesta = port.getCuentaIndividual(nss);
				// cuentas = obtenerCuentaIndividualVO(respuesta, cveIdTramite);
				LOGGER.error(
						"WebserviceCurp. Thread. Se finaliza la consulta satisfactoriamente a CuentaIndividual. {} ",
						new Date());
			}
		} catch (Exception e) {
			LOGGER.error(
					"WebserviceCurp. Thread. Se genero un error al accesar el webservice",
					e);
		}
		LOGGER.debug(
				"PeriodoNSSMovimientoAfiliatorio.consultarInformacionMovimientosCuentaIndividual(String) {} ",
				new Date());

		return cuentas;
	}

	/**
	 * Consulta de periodos para webservice , con persistencia o consulta
	 *
	 * @param folioSolitud
	 * @return
	 **/
	@Override
	public CuentaIndividual findByFolio(String folioSolitud) throws CuentaIndividualNoDisponibleException{
		// Trae de BD CuentaIndividual por folio, con su lista de NSS (todos los
		// tipos asignados)
		CuentaIndividual cuentaIndividual = cuentaIndividualUtility
				.findByFolio(folioSolitud);

		if (cuentaIndividual != null) {
			cuentaIndividual.setListaTipoTramites(cuentaIndividualUtility
					.obtenerMovimientosAclaracion(folioSolitud));
			// Para cada listaCuentaIndividualNss ejecuta 'findByFolioNss'
			cuentaIndividual
					.setListaCuentaIndividualNssAsociado(asignaCuentaIndividualNss(
							folioSolitud, cuentaIndividual
									.getListaCuentaIndividualNssAsociado()));
			cuentaIndividual
					.setListaCuentaIndividualNssCertificador(asignaCuentaIndividualNss(
							folioSolitud, cuentaIndividual
									.getListaCuentaIndividualNssCertificador()));
			cuentaIndividual
					.setListaCuentaIndividualNssNoPertenece(asignaCuentaIndividualNss(
							folioSolitud, cuentaIndividual
									.getListaCuentaIndividualNssNoPertenece()));
		}
		LOGGER.error("Cuenta Individual a entregar: {}", cuentaIndividual);
		return cuentaIndividual;
	}

	private List<CuentaIndividualNss> asignaCuentaIndividualNss(
			String folioSolicitud, List<CuentaIndividualNss> cuentasNssTemp) throws CuentaIndividualNoDisponibleException{
		List<CuentaIndividualNss> cuentasNssActualizadas = new ArrayList<CuentaIndividualNss>();
		if (cuentasNssTemp != null) {
			for (CuentaIndividualNss cuenta : cuentasNssTemp) {
				CuentaIndividualNss cuentaActualizada = findByFolioNss(
						folioSolicitud, cuenta.getNss());
				cuentasNssActualizadas.add(cuentaActualizada);
			}
		}
		return cuentasNssActualizadas;
	}

	/**
	 * Persistencia de periodos para movimientos
	 *
	 * @param cuenta
	 * @return
	 **/
	@Override
	public CuentaIndividual save(CuentaIndividual cuenta) {
		log.error("save: " + cuenta);
		for (CuentaIndividualNss cuentaIndividualNss : cuenta
				.getListaCuentaIndividualNssAsociado()) {
			getCuentaIndividualUtility()
					.actualizarPeriodos(cuentaIndividualNss);
		}

		for (CuentaIndividualNss cuentaIndividualNss : cuenta
				.getListaCuentaIndividualNssCertificador()) {
			getCuentaIndividualUtility()
					.actualizarPeriodos(cuentaIndividualNss);
		}

		for (CuentaIndividualNss cuentaIndividualNss : cuenta
				.getListaCuentaIndividualNssNoPertenece()) {
			getCuentaIndividualUtility()
					.actualizarPeriodos(cuentaIndividualNss);
		}

		return cuenta;
	}

	@Override
	public CuentaIndividualNss findByFolioNss(String folioSolicitud, String nss) throws CuentaIndividualNoDisponibleException{
		// Obtener lista de Nss
		List<DitDetalleNss> listaDetalles = detalleNssCdaLocal
				.getListNssByFolio(folioSolicitud);
		List<String> listaNss = new ArrayList<String>();
		if (listaDetalles != null) {
			for (DitDetalleNss detalle : listaDetalles) {
				listaNss.add(detalle.getNss());
			}
		}

		CuentaIndividualNss cuentaIndividualNss = cuentaIndividualNssUtilityLocal
				.findDetalleByFolioNss(folioSolicitud, nss, listaNss);
		LOGGER.error("Obtenido de la base: {}", cuentaIndividualNss);

		if (cuentaIndividualNss.getListaPeriodosRegistroPatronal().isEmpty()) {

			List<PeriodosRegistroPatronal> periodos = cuentaIndividualWsUtilityLocal
					.buscarPeriodosRegistroPatronalPorNss(nss, listaNss);
			LOGGER.error("Obtenido del WS: {}", cuentaIndividualNss);
			cuentaIndividualNss.setListaPeriodosRegistroPatronal(periodos);
			cuentaIndividualNss.setNss(cuentaIndividualNss.getNss());

      /*
       * Por cada periodos obtenido del WS se calcula el NRP Completo
       */
      
      for( PeriodosRegistroPatronal periodo : periodos ){
        StringBuilder sb = new StringBuilder();
        sb.append(periodo.getNumeroRegistroPatronal());
        sb.append(periodo.getClaveModalidad());
        sb.append( correccionAseguradoUtilityLocal.generaDigitoVerificadorRP( sb.toString() ) );        
        periodo.setNumeroRegistroPatronal(sb.toString());
        
      }
      
			cuentaIndividualUtility.guardarPeriodos(cuentaIndividualNss);
			cuentaIndividualNss = cuentaIndividualNssUtilityLocal
					.findDetalleByFolioNss(folioSolicitud, nss, listaNss);
			LOGGER.error("Obtenido de la base2 : {}", cuentaIndividualNss);
		}

		cuentaIndividualNss
				.setListaTipoRegularizacion(cuentaIndividualNssUtilityLocal
						.getMovimientosAclaracionByFolioNss(folioSolicitud, nss));

		return cuentaIndividualNss;
	}

	/**
	 * Termina la tarea de correccion de responsable y persisten todas las
	 * aclaraciones
	 *
	 * @param folio
	 * @param curp
	 **/
	@Override
	public void complete(String folio, String curp) {

		log.error("avanzar tarea del folio: " + folio);
		try {
			getCorreccionDatosBusinness().avanzarTareaTramite(folio, curp);
		} catch (NoExisteTareaUsuarioException e) {
			log.error("No existe tarea usuario: ", e);
		} catch (EstadoTareaUsuarioNoValidoException e) {
			log.error("Estado de la tarea Invalido: ", e);
		} catch (NoExisteTransicionParaTareaUsuarioException e) {
			log.error("Error Transicion Tarea Usuario: ", e);
		} catch (TereaSinUsuarioAsignadoException e) {
			log.error("Tarea sin usuario asignado: ", e);
		} catch (SolicitudNoEncontradaException e) {
			log.error("Solicitud no encontrada: ", e);
		} catch (TramiteNoEncontradoException e) {
			log.error("Tramite no encontrado: ", e);
		}

	}

	@Override
	public void guardaMovimientoAclaracionCI(String folio) throws TipoAclaracionCuentaIndividualException {
		/* Guarda los movimientos de aclaracion para cuenta individual */
		getCuentaIndividualUtility()
				.guardarMovimientosAclaracionCuentaIndividual(folio);
	}

	public CuentaIndividualUtilityLocal getCuentaIndividualUtility() {
		return cuentaIndividualUtility;
	}

	public String getPatronWs() {
		return patronWs;
	}

	public void setPatronWs(String patronWs) {
		this.patronWs = patronWs;
	}

	public CorreccionDatosRemote getCorreccionDatosBusinness() {
		return CorreccionDatosBusinness;
	}
  
  @Override
  public CuentaIndividual obtenerCuentaIndividual(
          Long cveIdCorreccionDatosAsegurado) {
    CuentaIndividual bean = new CuentaIndividual();
    
    List<CuentaIndividualNss> listaNssDestino;
    
    listaNssDestino = convertListCuentaIndividualNss(
      cuentaIndividualLocal.getListNssByCveIdCorreccionDatosAsegurado(cveIdCorreccionDatosAsegurado) );
    
    bean.setListaNssTipoCertificador( convertListCuentaIndividualNss( 
      cuentaIndividualLocal.getListNssByCveIdCorreccionDatosAsegurado(cveIdCorreccionDatosAsegurado, TipoNSSCorreccionEnum.CERTIFICADOR) ) );
    bean.setListaNssTipoAsociadoAsegurado(convertListCuentaIndividualNss( 
      cuentaIndividualLocal.getListNssByCveIdCorreccionDatosAsegurado(cveIdCorreccionDatosAsegurado, TipoNSSCorreccionEnum.ASOCIADO_AL_CERTIFICADOR) ) );
    bean.setListaNssTipoNoCorrespondeAsegurado(convertListCuentaIndividualNss( 
      cuentaIndividualLocal.getListNssByCveIdCorreccionDatosAsegurado(cveIdCorreccionDatosAsegurado, TipoNSSCorreccionEnum.CORRESPONDE_A_OTRA_PERSONA) ) );

    for( CuentaIndividualNss nss : bean.getListaNssTipoCertificador() ){
      nss.setTipoCorreccion(TipoNSSCorreccionEnum.CERTIFICADOR);
      nss.setListaNssDestino(listaNssDestino);
    }
    for( CuentaIndividualNss nss : bean.getListaNssTipoAsociadoAsegurado()){
      nss.setTipoCorreccion(TipoNSSCorreccionEnum.ASOCIADO_AL_CERTIFICADOR);
      nss.setListaNssDestino(listaNssDestino);
    }
    for( CuentaIndividualNss nss : bean.getListaNssTipoNoCorrespondeAsegurado()){
      nss.setTipoCorreccion(TipoNSSCorreccionEnum.CORRESPONDE_A_OTRA_PERSONA);
      nss.setListaNssDestino(listaNssDestino);
    }
    
    return bean;
  }

  @Override
  public List<CuentaIndividualRegistroPatronal> obtenerListaCuentaIndividualRegistroPatronal(
          Long cveIdDetalleNssCda) {
    List<CuentaIndividualRegistroPatronal> list = cuentaIndividualLocal.getPeriodosRegistroPatronalByIdDetalle(cveIdDetalleNssCda);
    
    for(CuentaIndividualRegistroPatronal registroPatronal : list ){    
      if( registroPatronal.getClaveDelegacionOrigen() != null && registroPatronal.getClaveDelegacionOrigen() != 0 ){
        Delegacion delegacion = solicitudBusiness.getDatosDelegacion(registroPatronal.getClaveDelegacionOrigen());
        if( delegacion != null ){
          registroPatronal.setNombreDelegacionOrigen( delegacion.getDescripcion() );
        }
      }
    }
    
    return list;
  }

  @Override
  public Long registrarCorreccionNssRegistroPatronal(
          CuentaIndividualRegistroPatronal registroPatronal) throws MotivosAclaracionErroneosException {
    /*
     *  0. Validar que exista y obtener el tipo de movimiento
     * 
     *  1. Borrado de las correcciones previas para ese registro patronal
     *     -- Detalle de como identificar las correcciones
     * 
     *  2. Borrado de los movimientos previos para ese registro patronal
     *     ( numeroRegistroPatronal, cveIdDetalleNssCda, origen = CUENTA_INDIVIDUAL )
     * 
     *  3. Obtener los movimientos originales
     *     ( numeroRegistroPatronal, cveIdDetalleNssCda, origen = VENTANILLA )
     * 
     *  4. Para cada movimiento:
     * 
     *    4.1 Crear periodo "copia" con referencia de periodo padre
     *    4.1 Crear registro de correccion de Inclusion
     * 
     *  5. Borrado fisico de los registros marcados como bajas logicas para el NSS en turno, ( otra peticion )?
     */
    
    DitDetalleNss nssDestino = new DitDetalleNss();
    nssDestino.setCveDetalleNss( registroPatronal.getNssDestino().getCveIdDetalleNssCda() );
    DitDetalleNss nssOrigen = cuentaIndividualLocal.findById( registroPatronal.getCveIdDetalleNssCda() );
    //nssOrigen.setCveDetalleNss(registroPatronal.getCveIdDetalleNssCda());
    
    /*Obtener los movimientos registrados para ambos NSS*/
    CuentaIndividualNss cuentaIndivudualNssOrigen = convertCuentaIndividualNss(nssOrigen);
    CuentaIndividualNss cuentaIndivudualNssDestino = convertCuentaIndividualNss(nssDestino);
    
    Long cveIdMovAclaracionNss;
    /* Cuando se selecciona el mismo NSS se eliminan las correcciones previas para revertir los cambios */
    if( nssDestino.getCveDetalleNss().longValue() == nssOrigen.getCveDetalleNss().longValue() ){
      cuentaIndividualLocal.eliminarCorreccionesBycveIdDetalleNssCdaAndRegistroPatronal(
            registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal() );
      return 0L;
    }
    
    cveIdMovAclaracionNss = cuentaIndivudualNssOrigen.getMovimientoAclaracionHomonimia() != null ? cuentaIndivudualNssOrigen.getMovimientoAclaracionHomonimia() : cuentaIndivudualNssOrigen.getMovimientoAclaracionInvasion();
    if( cveIdMovAclaracionNss == null ){
      cveIdMovAclaracionNss = cuentaIndivudualNssDestino.getMovimientoAclaracionHomonimia() != null ? cuentaIndivudualNssDestino.getMovimientoAclaracionHomonimia() : cuentaIndivudualNssDestino.getMovimientoAclaracionInvasion();
    }
    if( cveIdMovAclaracionNss == null ){
      throw new MotivosAclaracionErroneosException("No se encontraron motivos de aclaraci\u00F3n para el NSS origen ni destino para esta operaci\u00F3n");
    }

    cuentaIndividualLocal.eliminarCorreccionesBycveIdDetalleNssCdaAndRegistroPatronal(registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal() );

    int correccionesAnteriores = 0;

    /* Se obtienen las cuentas individuales previamente guardadas para comparar los movimientos que se van a mover */
    List<DitCtaIndNssCda> periodos = cuentaIndividualLocal.obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal( registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal() );
    List<DitCtaIndNssCda> periodosNssDestino = cuentaIndividualLocal.obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal( registroPatronal.getNssDestino().getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal() );

    /* Valores a utilizar en la modificacion tipo Incluir */
    DicOrigenCtaIndCda origen = new DicOrigenCtaIndCda();
    origen.setCveIdOrigenPeridoCtaInd( OrigenPeriodoEnum.VENTANILLA.getId() );    
    DicMovCorrecCtaIndCda tipoMovimientoEliminar = new DicMovCorrecCtaIndCda();
    tipoMovimientoEliminar.setCveIdMovCorreccion( TipoRegularizacionPeriodoEnum.ELIMINAR.getId().charAt(0) );
    DicMovCorrecCtaIndCda tipoMovimientoAgregar = new DicMovCorrecCtaIndCda();
    tipoMovimientoAgregar.setCveIdMovCorreccion( TipoRegularizacionPeriodoEnum.AGREGAR.getId().charAt(0) );
    
    Long consecutivo = cuentaIndividualLocal.obtenerConsecutivoMovimientosByNss(
            nssOrigen.getNss(), registroPatronal.getCveIdDetalleNssCda() );

    for(DitCtaIndNssCda periodo : periodos ) {

		/*
		 * Se valida que el perido no existe en la cuenta individual destino
		 * Si existe se genera un movimiento de eliminacion unicamente
		 */
		boolean indPeriodoExistente = false;

		for (DitCtaIndNssCda periodoDestino : periodosNssDestino) {
			/* Se valida unicamente el registro patronal y la fecha de inicio (la misma validacion que realiza SINDO) */
			if (periodo.getCveRegistroPatronal().equals(periodoDestino.getCveRegistroPatronal()) && periodo.getFecIniMov()
					.equals(periodoDestino.getFecIniMov())) {
				indPeriodoExistente = true;
				break;
			}
		}

		DitCorreccionCtaIndCda correccion = new DitCorreccionCtaIndCda();

		if (!indPeriodoExistente) {
			DitCtaIndNssCda periodoIncluido = new DitCtaIndNssCda();
			periodoIncluido.setCurp(periodo.getCurp());
			periodoIncluido.setCveCiz(periodo.getCveCiz());
			periodoIncluido.setCveConsecPeriodos(periodo.getCveConsecPeriodos());
			periodoIncluido.setCveDelegacionOrigen(periodo.getCveDelegacionOrigen());
			periodoIncluido.setCveEventual(periodo.getCveEventual());
			periodoIncluido.setCveExtConvSusp(periodo.getCveExtConvSusp());
			periodoIncluido.setCveFinMov(periodo.getCveFinMov());
			periodoIncluido.setCveHuelga(periodo.getCveHuelga());
			periodoIncluido.setCveIdCtaInd(null);
			periodoIncluido.setCveIdCtaIndPadre(periodo);
			periodoIncluido.setCveIniMov(periodo.getCveIniMov());
			periodoIncluido.setCveJornadaSemanal(periodo.getCveJornadaSemanal());
			periodoIncluido.setCveModalidad(periodo.getCveModalidad());
			periodoIncluido.setCveRegistroPatronal(periodo.getCveRegistroPatronal());
			periodoIncluido.setCveSubrServicios(periodo.getCveSubrServicios());
			periodoIncluido.setCveTipoFinMov(periodo.getCveTipoFinMov());
			periodoIncluido.setCveTipoIniMov(periodo.getCveTipoIniMov());
			periodoIncluido.setCveTipoSalario(periodo.getCveTipoSalario());
			periodoIncluido.setDicOrigenCtaIndCda(origen);
			periodoIncluido.setDitDetalleNss(periodo.getDitDetalleNss());
			periodoIncluido.setFecActualizacion(periodo.getFecActualizacion());
			periodoIncluido.setFecCarga(periodo.getFecCarga());
			periodoIncluido.setFecFinMov(periodo.getFecFinMov());
			periodoIncluido.setFecIniMov(periodo.getFecIniMov());
			periodoIncluido.setFecRecepcionMov(periodo.getFecRecepcionMov());
			periodoIncluido.setFecRegistroActualizado(null);
			periodoIncluido.setFecRegistroAlta(new Date());
			periodoIncluido.setFecRegistroBaja(null);
			periodoIncluido.setIndHistoricoCentral(periodo.getIndHistoricoCentral());
			periodoIncluido.setNomRazonSocial(periodo.getNomRazonSocial());
			periodoIncluido.setNss(registroPatronal.getNssDestino().getNss());
			periodoIncluido.setSalarioBase(periodo.getSalarioBase());

			cuentaIndividualLocal.crearPeriodo(periodoIncluido);

			correccion.setCveDetalleNssOperDestino(nssDestino);
			correccion.setCveIdMovOperDestino(tipoMovimientoAgregar);
			correccion.setCveIdCtaIndOperDestino(periodoIncluido);
		}

		correccion.setCveDetalleNssOperOrigen(periodo.getDitDetalleNss());
		correccion.setCveIdMovOperOrigen(tipoMovimientoEliminar);
		correccion.setCveIdCtaIndOperOrigen(periodo);
		correccion.setCveIdMovAclaracionNss(cveIdMovAclaracionNss);
		correccion.setFecRegistroAlta(new Date());
		correccion.setFecRegistroActualizado(new Date());
		correccion.setIndConsecutivoMovimiento(consecutivo);

		cuentaIndividualLocal.crearCorreccion(correccion);

		correccionesAnteriores++;
	}
    
    return (long) correccionesAnteriores;
  }

  @Override
  public PageCuentaIndividualPeriodo obtenerPageCuentaIndividualPeriodo(
          CuentaIndividualRegistroPatronal registroPatronal, long pageNumber) {
    
    Long totalOfRecords = cuentaIndividualLocal.obtenerTotalPeriodosByCveIdDetalleNssCdaAndRegistroPatronal( registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal() );    
    List<DitCtaIndNssCda> periodos = cuentaIndividualLocal.obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal( registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal(), pageNumber );
        
    return prepararPagina(periodos, totalOfRecords, pageNumber, registroPatronal);
    
  }
  
  public PageCuentaIndividualPeriodo prepararPagina( List<DitCtaIndNssCda> periodos, Long totalOfRecords , long pageNumber, CuentaIndividualRegistroPatronal registroPatronal){
    
    List<CuentaIndividualPeriodo> cuentaIndividualPeriodos = new ArrayList<CuentaIndividualPeriodo>();
    List<CuentaIndividualCorreccion> modificados = new ArrayList<CuentaIndividualCorreccion>();
    List<CuentaIndividualCorreccion> eliminados = new ArrayList<CuentaIndividualCorreccion>();
    List<CuentaIndividualCorreccion> incluidos = new ArrayList<CuentaIndividualCorreccion>();
    List<CuentaIndividualCorreccion> nuevos = new ArrayList<CuentaIndividualCorreccion>();
    
    if( totalOfRecords > 0 ){
    
      List<Long> ids = new ArrayList<Long>();
          
      for( DitCtaIndNssCda periodo : periodos ){
        ids.add( periodo.getCveIdCtaInd() );      
        cuentaIndividualPeriodos.add( entityToModel( periodo ) );
      }
      
      List<DitCtaIndNssCda> periodosNuevos = cuentaIndividualLocal.obtenerPeriodosNuevosByCveIdDetalleNssCdaAndRegistroPatronal(
                      registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal(), 1);
      for( DitCtaIndNssCda periodo : periodosNuevos ){
        ids.add( periodo.getCveIdCtaInd() );        
      }

      List<DitCorreccionCtaIndCda> correcciones = new ArrayList<DitCorreccionCtaIndCda>();
      if( !ids.isEmpty() ){
        correcciones = cuentaIndividualLocal.obtenerCorreccionesByIdsCtaInd( ids );
      }

      for(DitCorreccionCtaIndCda correccion : correcciones){

        CuentaIndividualCorreccion bean = new CuentaIndividualCorreccion();

        TipoRegularizacionPeriodoEnum movOrigen;
        TipoRegularizacionPeriodoEnum movDestino;

        movOrigen = correccion.getCveIdMovOperOrigen() != null ? TipoRegularizacionPeriodoEnum.getById( String.valueOf( correccion.getCveIdMovOperOrigen().getCveIdMovCorreccion() ) ) : TipoRegularizacionPeriodoEnum.INVALIDA;
        movDestino = correccion.getCveIdMovOperDestino() != null ? TipoRegularizacionPeriodoEnum.getById( String.valueOf( correccion.getCveIdMovOperDestino().getCveIdMovCorreccion() ) ): TipoRegularizacionPeriodoEnum.INVALIDA;

        bean.setCveIdCorreccionCuentaIndividualCda( correccion.getCveIdCorreccionCtaIndCda() );
        bean.setCveIdEstadoMovimientoSindo(correccion.getCveIdEstadoMovSindo());
        bean.setCveIdMovAclaracionNss( correccion.getCveIdMovAclaracionNss() );
        bean.setIndicadorConsecutivoMovimiento( correccion.getIndConsecutivoMovimiento());

        if( correccion.getCveIdCtaIndOperDestino() != null ){
          bean.getDestino().setCuentaIndividualPeriodo( entityToModel( correccion.getCveIdCtaIndOperDestino() ) );
        }
        if( correccion.getCveIdCtaIndOperOrigen() != null ){
          bean.getOrigen().setCuentaIndividualPeriodo( entityToModel( correccion.getCveIdCtaIndOperOrigen() ) );
        }

        if( correccion.getCveDetalleNssOperDestino() != null){
          bean.getDestino().setCuentaIndividualNss( entityToModel( correccion.getCveDetalleNssOperDestino() ));
        }
        if( correccion.getCveDetalleNssOperOrigen() != null){
          bean.getOrigen().setCuentaIndividualNss( entityToModel( correccion.getCveDetalleNssOperOrigen() ));
        }
        bean.getOrigen().setTipoRegularizacionPeriodo(movOrigen);
        bean.getDestino().setTipoRegularizacionPeriodo(movDestino);

        switch( movDestino ){      
          case MODIFICAR: modificados.add(bean); break;
          case INCLUIR: incluidos.add(bean); break;
          case AGREGAR: 
            if( movOrigen.equals(TipoRegularizacionPeriodoEnum.ELIMINAR) ){
              incluidos.add(bean);
            }else{
              nuevos.add(bean); 
            }
            break;
          case INVALIDA: 
            if( movOrigen.equals(TipoRegularizacionPeriodoEnum.AGREGAR) ){
              nuevos.add(bean);
            }else{
              eliminados.add(bean);
            } 
            break;              
        }

      }
    }
                
    PageCuentaIndividualPeriodo page = new PageCuentaIndividualPeriodo();
    page.setCurrentPage( pageNumber );
    page.setPageSize(Page.DEFAULT_PAGE_SIZE);
    page.setTotalOfRecords(totalOfRecords);
    page.setData(cuentaIndividualPeriodos);
    page.setModificados(modificados);
    page.setIncluidos(incluidos);
    page.setEliminados(eliminados);
    page.setNuevos(nuevos);
    page.setRegistroPatronal(registroPatronal);
    return page;
  }

  @Override
  public PageCuentaIndividualPeriodo obtenerPageCuentaIndividualCorreccion(
          CuentaIndividualRegistroPatronal registroPatronal, long pageNumber) {
    Long totalOfRecords = cuentaIndividualLocal.obtenerTotalPeriodosCorreccionByCveIdDetalleNssCdaAndRegistroPatronal( registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal() );    
    List<DitCtaIndNssCda> periodos = cuentaIndividualLocal.obtenerPeriodosCorreccionByCveIdDetalleNssCdaAndRegistroPatronal( registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal(), pageNumber );
        
    return prepararPagina(periodos, totalOfRecords, pageNumber, registroPatronal);
  }

  @Override
  public Long registrarListaCuentaIndividualCorreccion(
          PageCuentaIndividualPeriodo page) throws MotivosAclaracionErroneosException {
    
    /*
     * 1. Eliminar las correcciones anteriores para el OrigenNss y los periodos destino
     * 2. Crear los nuevos periodos
     * 3. Crear las nueva correcciones
     */
    
    Long numeroCambios = 0L;
    
    if( page.getData().isEmpty() ){
      return numeroCambios;
    }
    
    List<Long> ids = new ArrayList<Long>();
    String nss = "";
    Long cveIdDetalleNssCda = 0L;    
    for( CuentaIndividualPeriodo periodo : page.getData()){
      ids.add( periodo.getCveIdPeriodoCuentaIndividual() );      
      nss = periodo.getCuentaIndividualNss().getNss();
      cveIdDetalleNssCda = periodo.getCuentaIndividualNss().getCveIdDetalleNssCda();
    }
    
    DitDetalleNss nssOrigen = new DitDetalleNss();
    nssOrigen.setCveDetalleNss( cveIdDetalleNssCda );
    
    /*Obtener los movimientos registrados para ambos NSS*/
    CuentaIndividualNss cuentaIndivudualNssOrigen = convertCuentaIndividualNss(nssOrigen);

	Long cveIdMovAclaracionNss = cuentaIndivudualNssOrigen.getMovimientoAclaracionHomonimia() != null ? cuentaIndivudualNssOrigen.getMovimientoAclaracionHomonimia() : cuentaIndivudualNssOrigen.getMovimientoAclaracionInvasion();

	if( cveIdMovAclaracionNss == null){
		DitDetalleNss nssDestino = new DitDetalleNss();
		nssDestino.setCveDetalleNss( page.getIncluidos().get(0).getDestino().getCuentaIndividualNss().getCveIdDetalleNssCda());
		CuentaIndividualNss cuentaIndivudualNssDestino = convertCuentaIndividualNss(nssDestino);
		cveIdMovAclaracionNss = cuentaIndivudualNssDestino.getMovimientoAclaracionHomonimia() != null ? cuentaIndivudualNssDestino.getMovimientoAclaracionHomonimia() : cuentaIndivudualNssDestino.getMovimientoAclaracionInvasion();
	}
	if( cveIdMovAclaracionNss == null ){
		throw new MotivosAclaracionErroneosException("No se encontraron motivos de aclaraci\u00F3n para el NSS origen ni destino para esta operaci\u00F3n");
	}
    
    numeroCambios +=cuentaIndividualLocal.eliminarCorreccionesByIdPeriodoOriginal(ids);
    CuentaIndividualRegistroPatronal registroPatronal = page.getRegistroPatronal();
    numeroCambios +=cuentaIndividualLocal.eliminarNuevosByCveIdDetalleNssCdaAndRegistroPatronal(
                    registroPatronal.getCveIdDetalleNssCda(), registroPatronal.getNumeroRegistroPatronal() );
    
    /* Obtener el consecutivo de movimiento */
    Long consecutivo = cuentaIndividualLocal.obtenerConsecutivoMovimientosByNss(
      nss, cveIdDetalleNssCda );
        
    
    DicMovCorrecCtaIndCda movimientoEliminar = new DicMovCorrecCtaIndCda();
    movimientoEliminar.setCveIdMovCorreccion(TipoRegularizacionPeriodoEnum.ELIMINAR.getId().charAt(0));
    DicMovCorrecCtaIndCda movimientoAgregar = new DicMovCorrecCtaIndCda();
    movimientoAgregar.setCveIdMovCorreccion(TipoRegularizacionPeriodoEnum.AGREGAR.getId().charAt(0));
    DicMovCorrecCtaIndCda movimientoModificar = new DicMovCorrecCtaIndCda();
    movimientoModificar.setCveIdMovCorreccion(TipoRegularizacionPeriodoEnum.MODIFICAR.getId().charAt(0));
    DicMovCorrecCtaIndCda movimientoIncluir = new DicMovCorrecCtaIndCda();
    movimientoIncluir.setCveIdMovCorreccion(TipoRegularizacionPeriodoEnum.AGREGAR.getId().charAt(0));
    
    
    DicOrigenCtaIndCda origenCtaIndividual = new DicOrigenCtaIndCda();
    origenCtaIndividual.setCveIdOrigenPeridoCtaInd( OrigenPeriodoEnum.VENTANILLA.getId() );
    
    numeroCambios = 0L;
    
    /* Para eliminar un registro solo se agrega a la tabla de correccion */
    LOGGER.error("Periodos eliminados:" + page.getEliminados() );
    for( CuentaIndividualCorreccion correccion : page.getEliminados()){
      DitCorreccionCtaIndCda entity = new DitCorreccionCtaIndCda();      
      entity.setCveDetalleNssOperOrigen( modelToEntity( correccion.getOrigen().getCuentaIndividualNss() ) );
      entity.setCveIdCtaIndOperOrigen( modelToEntity( correccion.getOrigen().getCuentaIndividualPeriodo() ));      
      entity.setIndConsecutivoMovimiento(consecutivo);
      entity.setCveIdMovAclaracionNss(cuentaIndivudualNssOrigen.getMovimientoAclaracionCuentaIlogica());
      entity.setCveIdMovOperOrigen(movimientoEliminar);
      entity.setFecRegistroAlta( new Date() );
      entity.setFecRegistroActualizado( new Date() );
      numeroCambios++;
      cuentaIndividualLocal.crearCorreccion(entity);
    }
    
    /* Periodos Modificados */
    for( CuentaIndividualCorreccion correccion : page.getModificados() ){
      DitCtaIndNssCda periodoEntity = modelToEntity( correccion.getDestino().getCuentaIndividualPeriodo());
      
      if( "00/00/0000".equals( periodoEntity.getFecFinMov() ) || periodoEntity.getFecFinMov().equals("")  ){
        periodoEntity.setFecFinMov("31/12/9999");
      }
      
      periodoEntity.setDicOrigenCtaIndCda(origenCtaIndividual);
      DitCtaIndNssCda periodoOriginalEntity = cuentaIndividualLocal.obtenerPeriodoById( correccion.getOrigen().getCuentaIndividualPeriodo().getCveIdPeriodoCuentaIndividual() );
      periodoEntity.setDicOrigenCtaIndCda(origenCtaIndividual);
      periodoEntity.setCurp( periodoOriginalEntity.getCurp() );
      periodoEntity.setCveCiz( periodoOriginalEntity.getCveCiz() );
      periodoEntity.setCveDelegacionOrigen( periodoOriginalEntity.getCveDelegacionOrigen() );
      periodoEntity.setCveModalidad( periodoOriginalEntity.getCveModalidad() );
      periodoEntity.setCveRegistroPatronal( periodoOriginalEntity.getCveRegistroPatronal() );
      periodoEntity.setNomRazonSocial( periodoOriginalEntity.getNomRazonSocial() );
      
      periodoEntity.setNss( periodoOriginalEntity.getNss() );
      cuentaIndividualLocal.crearPeriodo(periodoEntity);
      
      DitCorreccionCtaIndCda entity = new DitCorreccionCtaIndCda();      
      entity.setCveDetalleNssOperOrigen( modelToEntity( correccion.getOrigen().getCuentaIndividualNss() ) );
      entity.setCveIdCtaIndOperOrigen( modelToEntity( correccion.getOrigen().getCuentaIndividualPeriodo() )); 
      entity.setCveIdMovOperOrigen(movimientoModificar);
      
      entity.setCveDetalleNssOperDestino(modelToEntity( correccion.getDestino().getCuentaIndividualNss() ) );
      entity.setCveIdCtaIndOperDestino( periodoEntity ); 
      entity.setCveIdMovOperDestino(movimientoModificar);
      
      entity.setIndConsecutivoMovimiento(consecutivo);
      entity.setCveIdMovAclaracionNss(cuentaIndivudualNssOrigen.getMovimientoAclaracionCuentaIlogica());
      entity.setFecRegistroAlta( new Date() );
      entity.setFecRegistroActualizado( new Date() );
      cuentaIndividualLocal.crearCorreccion(entity);
      numeroCambios++;
    }
    /* Periodos Incluidos */
    for( CuentaIndividualCorreccion correccion : page.getIncluidos() ){
      correccion.getDestino().getCuentaIndividualPeriodo().setCuentaIndividualNss( correccion.getDestino().getCuentaIndividualNss() );
      correccion.getOrigen().getCuentaIndividualPeriodo().setCuentaIndividualNss( correccion.getOrigen().getCuentaIndividualNss() );
      LOGGER.error( "Periodo: " + correccion.getDestino().getCuentaIndividualPeriodo() );      
      DitCtaIndNssCda periodoEntity = modelToEntity( correccion.getDestino().getCuentaIndividualPeriodo());
      
      DitCtaIndNssCda periodoOriginalEntity = cuentaIndividualLocal.obtenerPeriodoById( correccion.getOrigen().getCuentaIndividualPeriodo().getCveIdPeriodoCuentaIndividual() );
	  List<DitCtaIndNssCda> periodosNssDestino = cuentaIndividualLocal.obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal( correccion.getDestino().getCuentaIndividualNss()
			  .getCveIdDetalleNssCda(), correccion.getDestino().getCuentaIndividualPeriodo().getNumeroRegistroPatronal());

	  /*
	   * Se valida que el perido no existe en la cuenta individual destino
	   * Si existe se genera un movimiento de eliminacion unicamente
	   */
	  boolean indPeriodoExistente = false;

	  for (DitCtaIndNssCda periodoDestino : periodosNssDestino) {
	    	/* Se valida unicamente el registro patronal y la fecha de inicio (la misma validacion que realiza SINDO) */
		  if (periodoOriginalEntity.getCveRegistroPatronal().equals(periodoDestino.getCveRegistroPatronal()) && periodoOriginalEntity.getFecIniMov()
		     		.equals(periodoDestino.getFecIniMov())) {
		  	indPeriodoExistente = true;
		  	break;
		  }
	  }
	  DitCorreccionCtaIndCda entity = new DitCorreccionCtaIndCda();

	  if (!indPeriodoExistente) {

		  if ("00/00/0000".equals(periodoEntity.getFecFinMov()) || periodoEntity.getFecFinMov().equals("")) {
			  periodoEntity.setFecFinMov("31/12/9999");
		  }
		  periodoEntity.setDicOrigenCtaIndCda(origenCtaIndividual);
		  periodoEntity.setCurp(periodoOriginalEntity.getCurp());
		  periodoEntity.setCveCiz(periodoOriginalEntity.getCveCiz());
		  periodoEntity.setCveDelegacionOrigen(periodoOriginalEntity.getCveDelegacionOrigen());
		  periodoEntity.setCveModalidad(periodoOriginalEntity.getCveModalidad());
		  periodoEntity.setCveRegistroPatronal(periodoOriginalEntity.getCveRegistroPatronal());
		  periodoEntity.setNomRazonSocial(periodoOriginalEntity.getNomRazonSocial());

		  periodoEntity.setNss(correccion.getDestino().getCuentaIndividualNss().getNss());

		  LOGGER.error("Periodo a crear: " + periodoEntity);
		  cuentaIndividualLocal.crearPeriodo(periodoEntity);
		  LOGGER.error("Correccion a crear: " + correccion);

		  entity.setCveDetalleNssOperDestino(modelToEntity(correccion.getDestino().getCuentaIndividualNss()));
		  entity.setCveIdCtaIndOperDestino(periodoEntity);
		  entity.setCveIdMovOperDestino(movimientoIncluir);
	  }
      entity.setCveDetalleNssOperOrigen( modelToEntity( correccion.getOrigen().getCuentaIndividualNss() ) );
      entity.setCveIdCtaIndOperOrigen( periodoOriginalEntity ); 
      entity.setCveIdMovOperOrigen(movimientoEliminar);

      entity.setIndConsecutivoMovimiento(consecutivo);
      entity.setFecRegistroAlta( new Date() );
      entity.setFecRegistroActualizado( new Date() );
      entity.setCveIdMovAclaracionNss( cveIdMovAclaracionNss );
      
      cuentaIndividualLocal.crearCorreccion(entity);
      numeroCambios++;
    }
    /* Periodos Nuevos */
    for( CuentaIndividualCorreccion correccion : page.getNuevos() ){
      DitCtaIndNssCda periodoEntity = modelToEntity( correccion.getOrigen().getCuentaIndividualPeriodo());
      
      if( "00/00/0000".equals( periodoEntity.getFecFinMov() ) || periodoEntity.getFecFinMov().equals("")  ){
        periodoEntity.setFecFinMov("31/12/9999");
      }
      periodoEntity.setDicOrigenCtaIndCda(origenCtaIndividual);
      
      if( page.getRegistroPatronal() != null ){
        periodoEntity.setCveCiz( Long.parseLong( page.getRegistroPatronal().getClaveCiz() ) );
        periodoEntity.setCveDelegacionOrigen( page.getRegistroPatronal().getClaveDelegacionOrigen() );
        periodoEntity.setCveModalidad( page.getRegistroPatronal().getClaveModalidad() );
        periodoEntity.setCveRegistroPatronal( page.getRegistroPatronal().getNumeroRegistroPatronal() );
        periodoEntity.setNomRazonSocial( page.getRegistroPatronal().getNombreRegistroPatronal() );
      }

      cuentaIndividualLocal.crearPeriodo(periodoEntity);
      
      DitCorreccionCtaIndCda entity = new DitCorreccionCtaIndCda();      
      entity.setCveDetalleNssOperOrigen(modelToEntity( correccion.getOrigen().getCuentaIndividualNss() ) );
      entity.setCveIdCtaIndOperOrigen(periodoEntity ); 
      entity.setCveIdMovOperOrigen(movimientoAgregar);      
      entity.setIndConsecutivoMovimiento(consecutivo);
      entity.setCveIdMovAclaracionNss(cuentaIndivudualNssOrigen.getMovimientoAclaracionCuentaIlogica());
      entity.setCveIdCorreccionCtaIndCda( correccion.getCveIdCorreccionCuentaIndividualCda() );
      entity.setFecRegistroAlta( new Date() );
      entity.setFecRegistroActualizado( new Date() );

      cuentaIndividualLocal.crearCorreccion(entity);
      numeroCambios++;
    }
    
    return numeroCambios;
  }
  
  private DitDetalleNss modelToEntity( CuentaIndividualNss bean ){
    DitDetalleNss entity = new DitDetalleNss();
    entity.setCveDetalleNss( bean.getCveIdDetalleNssCda() );
    entity.setNss( bean.getNss() );
    return entity;
  }
  
  private CuentaIndividualNss entityToModel(DitDetalleNss detalleNss ){
    CuentaIndividualNss bean = new CuentaIndividualNss();
    bean.setCveIdDetalleNssCda( detalleNss.getCveDetalleNss() );
    bean.setNss( detalleNss.getNss() );
    return bean;
  }
  
  private DitCtaIndNssCda modelToEntity( CuentaIndividualPeriodo bean){
    DitCtaIndNssCda entity = new DitCtaIndNssCda();
    entity.setCveCiz(bean.getClaveCiz());
    entity.setDitDetalleNss( modelToEntity(bean.getCuentaIndividualNss()) );
    entity.setNss( entity.getDitDetalleNss().getNss() );
    entity.setCveIdCtaInd(bean.getCveIdPeriodoCuentaIndividual());
    entity.setCveEventual(bean.getEventual( ));
    entity.setCveExtConvSusp(bean.getExtemporaneoConvenioSuspension());
    entity.setFecActualizacion( bean.getFechaActualizacion());
    entity.setFecCarga ( bean.getFechaCarga( ));
    entity.setFecFinMov ( bean.getFechaFinalMovimiento( ));
    entity.setFecIniMov ( bean.getFechaInicioMovimiento());
    entity.setFecRecepcionMov ( bean.getFechaRecepcionMovimiento( ));
    entity.setIndHistoricoCentral ( bean.getHistorico( ));
    entity.setCveHuelga ( bean.getHuelga());
    entity.setCveJornadaSemanal ( bean.getJornadaSemanal());
    entity.setCveConsecPeriodos ( bean.getNumeroConsecutivoPeriodos());
    entity.setCveRegistroPatronal ( bean.getNumeroRegistroPatronal());
    entity.setCveFinMov ( bean.getOrigenMovimientoFinal());
    entity.setCveIniMov ( bean.getOrigenMovimientoInicial());
    entity.setSalarioBase ( bean.getSalarioBase());
    entity.setCveSubrServicios ( bean.getSubrogacionServicio());
    entity.setCveTipoFinMov ( bean.getTipoMovimientoFinal());
    entity.setCveTipoIniMov ( bean.getTipoMovimientoInicial());
    entity.setCveTipoSalario ( bean.getTipoSalario());
    if( bean.getCveIdPeriodoAnterior() != null ){
      DitCtaIndNssCda padre = new DitCtaIndNssCda();
      padre.setCveIdCtaInd(bean.getCveIdPeriodoAnterior());
      entity.setCveIdCtaIndPadre(padre);
    }
    
    entity.setFecRegistroAlta( new Date() );
    entity.setFecRegistroActualizado( new Date() );
    
    
    return entity;
  }
    
  private CuentaIndividualPeriodo entityToModel(DitCtaIndNssCda periodo){
    
    CuentaIndividualPeriodo cuentaIndividualPeriodo = new CuentaIndividualPeriodo();
    cuentaIndividualPeriodo.setClaveCiz( periodo.getCveCiz() );
    cuentaIndividualPeriodo.setCuentaIndividualNss(entityToModel(periodo.getDitDetalleNss()));  // TODO: Check
    cuentaIndividualPeriodo.setCveIdPeriodoCuentaIndividual( periodo.getCveIdCtaInd() );    
    cuentaIndividualPeriodo.setEventual( periodo.getCveEventual() );
    cuentaIndividualPeriodo.setExtemporaneoConvenioSuspension( periodo.getCveExtConvSusp() );
    cuentaIndividualPeriodo.setFechaActualizacion( periodo.getFecActualizacion() );
    cuentaIndividualPeriodo.setFechaCarga( periodo.getFecCarga() );
    cuentaIndividualPeriodo.setFechaFinalMovimiento( periodo.getFecFinMov() );
    cuentaIndividualPeriodo.setFechaInicioMovimiento( periodo.getFecIniMov() );    
    cuentaIndividualPeriodo.setFechaRecepcionMovimiento( periodo.getFecRecepcionMov() );
    cuentaIndividualPeriodo.setHistorico( periodo.getIndHistoricoCentral() );
    cuentaIndividualPeriodo.setHuelga( periodo.getCveHuelga() );
    cuentaIndividualPeriodo.setJornadaSemanal( periodo.getCveJornadaSemanal() );
    cuentaIndividualPeriodo.setNumeroConsecutivoPeriodos( periodo.getCveConsecPeriodos() );
    cuentaIndividualPeriodo.setNumeroRegistroPatronal( periodo.getCveRegistroPatronal() );
    cuentaIndividualPeriodo.setOrigenMovimientoFinal( periodo.getCveFinMov() );
    cuentaIndividualPeriodo.setOrigenMovimientoInicial( periodo.getCveIniMov() );
    cuentaIndividualPeriodo.setSalarioBase( periodo.getSalarioBase() );
    cuentaIndividualPeriodo.setSubrogacionServicio( periodo.getCveSubrServicios() );
    cuentaIndividualPeriodo.setTipoMovimientoFinal( periodo.getCveTipoFinMov() );
    cuentaIndividualPeriodo.setTipoMovimientoInicial( periodo.getCveTipoIniMov() );
    cuentaIndividualPeriodo.setTipoSalario( periodo.getCveTipoSalario() );
    cuentaIndividualPeriodo.setCveIdPeriodoAnterior( periodo.getCveIdCtaIndPadre() != null ? periodo.getCveIdCtaIndPadre().getCveIdCtaInd() : null);
      
    return cuentaIndividualPeriodo;  
  }
  
  private List<CuentaIndividualNss> convertListCuentaIndividualNss(
          List<DitDetalleNss> entities) {
    List<CuentaIndividualNss> list = new ArrayList<CuentaIndividualNss>();
    if( entities != null ){
      for(DitDetalleNss entity : entities){        
        list.add( convertCuentaIndividualNss(entity) );
      }
    }
    return list;
  }
    
  private CuentaIndividualNss convertCuentaIndividualNss(
          DitDetalleNss entity) {
    
        CuentaIndividualNss bean = new CuentaIndividualNss();
        bean.setCveIdDetalleNssCda( entity.getCveDetalleNss() );
        bean.setNss( entity.getNss() );
        
        List<DitMovAclaracionNssCda> movimientos = cuentaIndividualLocal.obtenerMovimientosAclaracionByCveIdDetalleNssCda(
              entity.getCveDetalleNss() );
        
        for(DitMovAclaracionNssCda movimiento : movimientos){
          TipoRegularizacionSolicitudCDAEnum key = TipoRegularizacionSolicitudCDAEnum.fromId( movimiento.getCveIdTipoTramCorrecNss().getCveIdTipoTramCorrecNss() );
          switch(key){
            case HOMONIMIA: bean.setMovimientoAclaracionHomonimia( movimiento.getCveIdMovAclaracionNss() ); break;
            case INVASION: bean.setMovimientoAclaracionInvasion( movimiento.getCveIdMovAclaracionNss() ); break;
            case CUENTA_ILOGICA: bean.setMovimientoAclaracionCuentaIlogica( movimiento.getCveIdMovAclaracionNss() ); break;
          }
          if( movimiento.getCveIdTipoNssAclaracion() != null ){
            bean.getListaTipoRegularizacion().add( movimiento.getCveIdTipoNssAclaracion().getDesTipoNss() );
          }
        }
        
        if( entity.getDicTipoNss() != null){
          bean.setTipoCorreccion(TipoNSSCorreccionEnum.fromId( entity.getDicTipoNss().getCveTipoNss() ));
        }
    
    return bean;
  }
  
  public CuentaIndividual existenMovimientoCI(String folioSolitud) {
		// Trae de BD CuentaIndividual por folio, con su lista de NSS (todos los
		// tipos asignados)
		CuentaIndividual cuentaIndividual = cuentaIndividualUtility
				.findByFolio(folioSolitud);
		LOGGER.error("Cuenta Individual a entregar: {}", cuentaIndividual);
		return cuentaIndividual;
	}

	public boolean eliminarMovimientosCuentaIndividual(long cveIdCorreccion) {
		return cuentaIndividualLocal.eliminarMovimientosCuentaIndividual(cveIdCorreccion);
	}

	public List<Long> getListNssByFolioTramite(String folioTramite) {
		return cuentaIndividualLocal.getListNssByFolioTramite(folioTramite);
	}

}
