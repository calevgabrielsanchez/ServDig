package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.AttributeComparator;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.enums.TipoCorteAsignacionEnum;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteGeneralAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalCanase;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalRissSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.DetalleRegistroMovRissSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.RegistroCanase;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.RegistroMovRissSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.RegistroMovSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.RegistroRepetidoCanase;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils.CorteEstadisticoProperties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;

public class CorteEstadisticoGeneralTaskImpl implements CorteEstadisticoGeneralTask {
	private static final String MOV_ALTA_SINDO = "01";
	private static final String MOV_BAJA_SINDO = "02";
	private static final String MOV_MODIFDOM_SINDO = "04";
	private static final String MOV_MODIF_SINDO = "06";

	private static final String NRP_ASEGURADO = "B0799992430";
	private static final String CURP_CERO = "000000000000000000";

	private SimpleDateFormat sdfh = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private CorteAsignacionTask corteAsignacionTask;

	@Override
	public void realizarCorteDiaAnterior(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException {
		// Corte de Asignacion (Dia anterior)
		Date fechaCorte = prop.getFechaCorteGeneral();
		CorteGeneralAsignacion corteGeneralAsignacion = corteAsignacionTask
				.realizarCorteGeneral(fechaCorte);
		corteAsignacionTask.enviarCorreoCorte(prop, corteGeneralAsignacion,
				TipoCorteAsignacionEnum.CORTE_TOTAL_DIA_ANTERIOR);
	}

	@Override
	public void realizarCorteDiaActual(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException {
		// Corte de Asignacion (Dia actual)
		Date fechaCorte = prop.getFechaCorteGeneral();
		CorteGeneralAsignacion corteGeneralAsignacion = corteAsignacionTask
				.realizarCorteGeneral(fechaCorte);
		corteAsignacionTask.enviarCorreoCorte(prop, corteGeneralAsignacion,
				TipoCorteAsignacionEnum.CORTE_PARCIAL_DIA_ACTUAL);
	}

	@Override
	public void realizarCorteRango() {
		
	}

	@Override
	public CorteTotalCanase generarEstadisticaArchivoCanase(List<String> lineasArchivos) {
		CorteTotalCanase corteTotalCanase = new CorteTotalCanase();
		List<RegistroCanase> lstRegistroCanase = new ArrayList<RegistroCanase>();
		Map<String, RegistroRepetidoCanase> lstFrecuenciaCurpRepetido = new HashMap<String, RegistroRepetidoCanase>();

		// Clasificacion de CIZ y Movimientos a CANASE
		long contadorCiz1 = 0;
		long contadorCiz2 = 0;
		long contadorCiz3 = 0;
		for (String lineaArchivo : lineasArchivos) {
			RegistroCanase registroCanase = new RegistroCanase();

			String strCiz = lineaArchivo.substring(0, 1);
			String strNss = lineaArchivo.substring(12, 23);
			String strCurp = lineaArchivo.substring(212, 230);

			registroCanase.setCiz(strCiz);
			registroCanase.setNss(strNss);
			registroCanase.setCurp(strCurp);
			lstRegistroCanase.add(registroCanase);

			if (strCiz.equals("1")) {
				contadorCiz1++;
			} else if (strCiz.equals("2")) {
				contadorCiz2++;
			} else if (strCiz.equals("3")) {
				contadorCiz3++;
			}
		}

		corteTotalCanase.setNumRegistrosCiz1(contadorCiz1);
		corteTotalCanase.setNumRegistrosCiz2(contadorCiz2);
		corteTotalCanase.setNumRegistrosCiz3(contadorCiz3);

		// Se realiza analisis por cada registro
		for (RegistroCanase registroCanase : lstRegistroCanase) {
			String strCurpLocal = registroCanase.getCurp();
			long frecuenciaCurpLocal = 0;

			// Se realiza validacion (Se agrega a la estadistica solo si el
			// registro se analiza por primera vez)
			List<String> lstNssLocal = new ArrayList<String>();
			if (StringUtils.isNotBlank(strCurpLocal)
					&& !strCurpLocal.equals(CURP_CERO)
					&& !lstFrecuenciaCurpRepetido.containsKey(strCurpLocal)) {
				// Busqueda de CURP repetido
				for (RegistroCanase registroCanaseBusqueda : lstRegistroCanase) {
					String strCurpBusqueda = registroCanaseBusqueda.getCurp();

					if (strCurpLocal.equals(strCurpBusqueda)) {
						lstNssLocal.add(registroCanaseBusqueda.getNss());
						frecuenciaCurpLocal++;
					}
				}

				if (frecuenciaCurpLocal > 1) {
					RegistroRepetidoCanase registroRepetido = new RegistroRepetidoCanase();
					registroRepetido.setFrecuencia(frecuenciaCurpLocal);
					registroRepetido.setLstNumNss(lstNssLocal);

					lstFrecuenciaCurpRepetido.put(strCurpLocal, registroRepetido);
				}
			}
		}

		corteTotalCanase.setNumtotalRegistros(lineasArchivos.size());
		corteTotalCanase.setLstCurpRepetido(lstFrecuenciaCurpRepetido);
		return corteTotalCanase;
	}

	@Override
	public CorteTotalRissSindo generarEstadisticaArchivoRiss(List<String> lineasArchivos) throws ParseException {
		String nrpAsegurado = NRP_ASEGURADO;
		CorteTotalRissSindo corteTotalRissSindo = new CorteTotalRissSindo();
		List<RegistroMovRissSindo> listRegistroRiss = new ArrayList<RegistroMovRissSindo>();
		Map<String, DetalleRegistroMovRissSindo> lstMovimientosPatrones = new HashMap<String, DetalleRegistroMovRissSindo>();
		Map<String, DetalleRegistroMovRissSindo> lstMovimientosAsegurados = new HashMap<String, DetalleRegistroMovRissSindo>();

		// Clasificacion de Movimientos RISS
		for (String lineaArchivo : lineasArchivos) {
			RegistroMovRissSindo registroMovRissSindo = new RegistroMovRissSindo();

			String strRegistroPatronal = lineaArchivo.substring(0, 11);
			String strRfc = lineaArchivo.substring(11, 24);
			String strNss = lineaArchivo.substring(24, 35);
			String strCurp = lineaArchivo.substring(35, 53);
			String strTipPatPerFis = lineaArchivo.substring(53, 54);
			String strConsecutivo = lineaArchivo.substring(54, 57);
			String strFechaInicioRif = lineaArchivo.substring(57, 67);
			String strFechaBajaRif = lineaArchivo.substring(67, 77);
			String strFechaInicioRiss = lineaArchivo.substring(77, 87);
			String strFechaBajaRiss = lineaArchivo.substring(87, 97);
			String strMotivoBaja = lineaArchivo.substring(97, 98);
			String strPorcentaje = lineaArchivo.substring(98, 100);
			String strFechaMovimiento = lineaArchivo.substring(100, 110);

			registroMovRissSindo.setNumeroRegistroPatronal(strRegistroPatronal);
			registroMovRissSindo.setRfc(strRfc);
			registroMovRissSindo.setNumNss(strNss);
			registroMovRissSindo.setCurp(strCurp);
			registroMovRissSindo.setTipPatPerFis(strTipPatPerFis);
			String strNumConsecutivo = StringUtils.stripStart(strConsecutivo, "0");
			registroMovRissSindo.setNumConsecutivo(NumberUtils.toInt(strNumConsecutivo));
			registroMovRissSindo.setPorcentaje(NumberUtils.toInt(strPorcentaje));
			registroMovRissSindo.setFechaMovimiento(sdfh.parse(strFechaMovimiento));

			listRegistroRiss.add(registroMovRissSindo);
		}

		for (RegistroMovRissSindo registroMovRiss : listRegistroRiss) {
			String strNrpLocal = registroMovRiss.getNumeroRegistroPatronal();
			String strNssLocal = registroMovRiss.getNumNss();
			int frecuenciaNrpLocal = 0;
			int frecuenciaNssLocal = 0;

			// Validacion de Movimientos
			List<RegistroMovRissSindo> lstRegistroMovRissLocal = new ArrayList<RegistroMovRissSindo>();

			// Se realiza validacion para mov riss de Asegurados
			if (strNrpLocal.equals(nrpAsegurado) && !lstMovimientosAsegurados.containsKey(strNssLocal)) {
				for (RegistroMovRissSindo registroMovRissBusqueda : listRegistroRiss) {
					String nssBusqueda = registroMovRissBusqueda.getNumNss();
					String nrpBusqueda = registroMovRissBusqueda.getNumeroRegistroPatronal();

					if (strNssLocal.equals(nssBusqueda) && nrpBusqueda.equals(nrpAsegurado)) {
						lstRegistroMovRissLocal.add(registroMovRissBusqueda);
						frecuenciaNssLocal++;
					}
				}

				if (frecuenciaNssLocal > 1) {
					List<RegistroMovRissSindo> lstRegistroMovRissFaltantes = obtenerMovRissFaltantes(lstRegistroMovRissLocal);
					if (!lstRegistroMovRissFaltantes.isEmpty()) {
						corteTotalRissSindo.getLstRegistroMovRissFaltantes().addAll(lstRegistroMovRissFaltantes);
					}

					DetalleRegistroMovRissSindo detalleRegistroMovRissSindo = new DetalleRegistroMovRissSindo();
					detalleRegistroMovRissSindo.setNumMovimientos(frecuenciaNssLocal);
					detalleRegistroMovRissSindo.setLstRegistroMovRiss(lstRegistroMovRissLocal);
					detalleRegistroMovRissSindo.setLstRegistroMovRissNoEnviado(lstRegistroMovRissFaltantes);

					lstMovimientosAsegurados.put(strNssLocal, detalleRegistroMovRissSindo);
				}
			}

			// Se realiza validacion para mov riss Patronales
			if (!strNrpLocal.equals(nrpAsegurado) && !lstMovimientosPatrones.containsKey(strNrpLocal)) {
				for (RegistroMovRissSindo registroMovRissBusqueda : listRegistroRiss) {
					String nrpBusqueda = registroMovRissBusqueda.getNumeroRegistroPatronal();

					if (strNrpLocal.equals(nrpBusqueda)) {
						lstRegistroMovRissLocal.add(registroMovRissBusqueda);
						frecuenciaNrpLocal++;
					}
				}

				if (frecuenciaNrpLocal > 1) {
					List<RegistroMovRissSindo> lstRegistroMovRissFaltantes = obtenerMovRissFaltantes(lstRegistroMovRissLocal);
					if (!lstRegistroMovRissFaltantes.isEmpty()) {
						corteTotalRissSindo.getLstRegistroMovRissFaltantes().addAll(lstRegistroMovRissFaltantes);
					}

					DetalleRegistroMovRissSindo detalleRegistroMovRissSindo = new DetalleRegistroMovRissSindo();
					detalleRegistroMovRissSindo.setNumMovimientos(frecuenciaNrpLocal);
					detalleRegistroMovRissSindo.setLstRegistroMovRiss(lstRegistroMovRissLocal);
					detalleRegistroMovRissSindo.setLstRegistroMovRissNoEnviado(lstRegistroMovRissFaltantes);

					lstMovimientosPatrones.put(strNrpLocal, detalleRegistroMovRissSindo);
				}
			}

		}

		corteTotalRissSindo.setNumtotalMovimientos(lineasArchivos.size());
		corteTotalRissSindo.setNumMovAsegurados(lstMovimientosAsegurados.size());
		corteTotalRissSindo.setNumMovPatronales(lstMovimientosPatrones.size());

		return corteTotalRissSindo;
	}

	private List<RegistroMovRissSindo> obtenerMovRissFaltantes(
			List<RegistroMovRissSindo> lstRegistroMovRissLocal) {
		List<RegistroMovRissSindo> lstRegistroMovRissFaltantes = new ArrayList<RegistroMovRissSindo>();
		List<String> lstAtributos = new ArrayList<String>();
		lstAtributos.add("numeroRegistroPatronal");
		lstAtributos.add("rfc");
		lstAtributos.add("curp");
		lstAtributos.add("numConsecutivo");

		AttributeComparator.sort(lstAtributos , lstRegistroMovRissLocal);

		for (int index = 0; index < lstRegistroMovRissLocal.size(); index++) {
			RegistroMovRissSindo registroMovRissLocal = lstRegistroMovRissLocal.get(index);
			int numConsecutivoAct = registroMovRissLocal.getNumConsecutivo();

			if (index == 0 && numConsecutivoAct != 1) {
				RegistroMovRissSindo registroMovRissFaltante = new RegistroMovRissSindo();
				registroMovRissFaltante.setNumeroRegistroPatronal(registroMovRissLocal.getNumeroRegistroPatronal());
				registroMovRissFaltante.setNumNss(registroMovRissLocal.getNumNss());
				registroMovRissFaltante.setCurp(registroMovRissLocal.getCurp());
				registroMovRissFaltante.setRfc(registroMovRissLocal.getRfc());
				registroMovRissFaltante.setTipPatPerFis(registroMovRissLocal.getTipPatPerFis());
				registroMovRissFaltante.setNumConsecutivo(1);
				
				lstRegistroMovRissFaltantes.add(registroMovRissFaltante);
			} else if ((index + 1) <= (lstRegistroMovRissLocal.size() - 1)) {
				RegistroMovRissSindo registroMovRissLocalSig = lstRegistroMovRissLocal.get(index + 1);
				int numConsecutivoSig = registroMovRissLocalSig.getNumConsecutivo();
				
				if ((numConsecutivoAct + 1) != numConsecutivoSig) {
					RegistroMovRissSindo registroMovRissFaltante = new RegistroMovRissSindo();
					registroMovRissFaltante.setNumeroRegistroPatronal(registroMovRissLocal.getNumeroRegistroPatronal());
					registroMovRissFaltante.setNumNss(registroMovRissLocal.getNumNss());
					registroMovRissFaltante.setCurp(registroMovRissLocal.getCurp());
					registroMovRissFaltante.setRfc(registroMovRissLocal.getRfc());
					registroMovRissFaltante.setTipPatPerFis(registroMovRissLocal.getTipPatPerFis());
					registroMovRissFaltante.setNumConsecutivo(numConsecutivoAct + 1);
					
					lstRegistroMovRissFaltantes.add(registroMovRissFaltante);
				}
			}
		}

		return lstRegistroMovRissFaltantes;
	}

	@Override
	public CorteTotalSindo generarEstadisticaArchivoSindo(
			List<String> lineasArchivos) {
		CorteTotalSindo corteTotalSindo = new CorteTotalSindo();
		List<RegistroMovSindo> lstRegistroSindo = new ArrayList<RegistroMovSindo>();

		for (String lineaArchivo : lineasArchivos) {
			RegistroMovSindo registroMovSindo = new RegistroMovSindo();

			String strTipoMovimiento = lineaArchivo.substring(5, 7);
			String strRegistroPatronal = lineaArchivo.substring(15, 26);
			String strCurp = lineaArchivo.substring(42, 60);

			registroMovSindo.setNumeroRegistroPatronal(strRegistroPatronal);
			registroMovSindo.setCurp(strCurp);
			registroMovSindo.setTipoMovimiento(strTipoMovimiento);
			if (strTipoMovimiento.equals(MOV_MODIF_SINDO)) {
				String strTipoClasificacion = lineaArchivo.substring(265, 267);
				String strNumTipoClasificacion = StringUtils.stripStart(strTipoClasificacion, "0");
				registroMovSindo.setTipoClasificacion(NumberUtils.toInt(strNumTipoClasificacion));
			}

			lstRegistroSindo.add(registroMovSindo);
		}

		long numMovAltaPatronal = 0;
		long numMovModifClasifInternet = 0;
		long numMovModifClasifVentanilla = 0;
		long numMovCambioDomicilio = 0;
		long numMovCambioDomCt = 0;
		long numMovBajaSindo = 0;

		for (RegistroMovSindo registroMovSindo : lstRegistroSindo) {
			String tipoMovimientoLocal = registroMovSindo.getTipoMovimiento();

			if (tipoMovimientoLocal.equals(MOV_ALTA_SINDO)) {
				numMovAltaPatronal++;
			} else if (tipoMovimientoLocal.equals(MOV_MODIF_SINDO)) {
				int tipoClasificacion = registroMovSindo.getTipoClasificacion();

				if (tipoClasificacion >= 30 && tipoClasificacion <= 45) {
					numMovModifClasifVentanilla++;
				} else if (tipoClasificacion >= 1 && tipoClasificacion <= 11) {
					numMovModifClasifInternet++;
				} else if (tipoClasificacion == 0) {
					numMovModifClasifVentanilla++;
				} else if (tipoClasificacion == 12){
					numMovCambioDomCt++;
				}
			}  else if (tipoMovimientoLocal.equals(MOV_MODIFDOM_SINDO)) {
				numMovCambioDomicilio++;
			} else if (tipoMovimientoLocal.equals(MOV_BAJA_SINDO)) {
				numMovBajaSindo++;
			}
		}

		corteTotalSindo.setNumMovAltaPatronal(numMovAltaPatronal);
		corteTotalSindo.setNumMovClasifInternet(numMovModifClasifInternet);
		corteTotalSindo.setNumMovClasifVentanilla(numMovModifClasifVentanilla);
		corteTotalSindo.setNumMovCambioDomicilio(numMovCambioDomicilio);
		corteTotalSindo.setNumMovCambioDomCt(numMovCambioDomCt);
		corteTotalSindo.setNumMovBajaSindo(numMovBajaSindo);
		corteTotalSindo.setNumtotalMovimientos(lineasArchivos.size());

		return corteTotalSindo;
	}
}
