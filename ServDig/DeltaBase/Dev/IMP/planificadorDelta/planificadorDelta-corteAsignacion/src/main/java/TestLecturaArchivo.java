import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalCanase;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalRissSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.RegistroMovRissSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.RegistroRepetidoCanase;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.task.CorteEstadisticoGeneralTask;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.task.CorteEstadisticoGeneralTaskImpl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestLecturaArchivo {
	protected static final Log log = LogFactory.getLog(TestLecturaArchivo.class);

	public static void main(String[] args) {
		try {
			generarEstadisticaArchivoCanase();
			generarEstadisticaArchivoRiss();
			generarEstadisticaArchivoSindo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generarEstadisticaArchivoRiss() throws IOException, ParseException {
		CorteEstadisticoGeneralTask corteEstadisticoGeneralTask = new CorteEstadisticoGeneralTaskImpl();
		String nombreArchivo = "/Documentacion/Diagnostico/riss_temp/20150312/movimientosRiss.txt";
		File arhivoDescargado = new File(nombreArchivo);

		List<String> lineasArchivos = FileUtils.readLines(arhivoDescargado);
		CorteTotalRissSindo corteTotalRissSindo = corteEstadisticoGeneralTask.generarEstadisticaArchivoRiss(lineasArchivos);
		
		long numMovPatronales = corteTotalRissSindo.getNumMovPatronales();
		long numMovAsegurados = corteTotalRissSindo.getNumMovAsegurados();
		List<RegistroMovRissSindo> lstMovimientosFaltantes = corteTotalRissSindo.getLstRegistroMovRissFaltantes();
		long numMovTotales = numMovPatronales + numMovAsegurados;

		log.debug("\n-------------------- RISS --------------------");
		log.debug("\nSe leyeron " + corteTotalRissSindo.getNumtotalMovimientos() + " registros proveniente de " + numMovTotales  + " movimientos");
		log.debug("Alta de beneficio RISS para registros patronales (personas fisicas) modalidades 10 y 13: " + numMovPatronales);
		log.debug("Patron persona fisica, Trabajador independiente, Modalidades 43,44 y 35:                 " + numMovAsegurados);
		log.debug("-------------------");
		log.debug("Existen " + lstMovimientosFaltantes.size() + " posibles registros que no se han mandado a SINDO");

		if (!lstMovimientosFaltantes.isEmpty()) {
			for (RegistroMovRissSindo registroMovRissSindo : lstMovimientosFaltantes) {
				log.debug("Para el patron-asegurado "
						+ registroMovRissSindo.getNumeroRegistroPatronal()
						+ "-" + registroMovRissSindo.getNumNss()
						+ " falta el movimiento con numero de consecutivo " + registroMovRissSindo.getNumConsecutivo());
			}
		}
	}

	private static void generarEstadisticaArchivoCanase() throws IOException {
		CorteEstadisticoGeneralTask corteEstadisticoGeneralTask = new CorteEstadisticoGeneralTaskImpl();
		String nombreArchivo = "/Documentacion/Diagnostico/canase_temp/20150312/APMVIAJ.txt";
		File arhivoDescargado = new File(nombreArchivo);

		List<String> lineasArchivos = FileUtils.readLines(arhivoDescargado);
		CorteTotalCanase corteTotalCanase = corteEstadisticoGeneralTask.generarEstadisticaArchivoCanase(lineasArchivos);

		log.debug("\n-------------------- CANASE --------------------");
		log.debug("\nSe leyeron " + corteTotalCanase.getNumtotalRegistros() + " registros:\n");
		log.debug("para CIZ 1: " + corteTotalCanase.getNumRegistrosCiz1());
		log.debug("para CIZ 2: " + corteTotalCanase.getNumRegistrosCiz2());
		log.debug("para CIZ 3: " + corteTotalCanase.getNumRegistrosCiz3());
		log.debug("--------");

		Map<String, RegistroRepetidoCanase> lstFecCurpRepetidos = corteTotalCanase.getLstCurpRepetido();
		Set<String> lstCurpRepetido = lstFecCurpRepetidos.keySet();

		log.debug("Existen " + lstCurpRepetido.size() + " personas con mas de un NSS asignado cuyos movimientos se enviaron a SINDO\n");

		for (String curpRepetido : lstCurpRepetido) {
			RegistroRepetidoCanase registroRepetido = lstFecCurpRepetidos.get(curpRepetido);

			StringBuffer sbRegistroRepetido = new StringBuffer();
			sbRegistroRepetido.append(curpRepetido).append(" (")
					.append(registroRepetido.getFrecuencia())
					.append(") = ").append(registroRepetido.getLstNumNss());
			log.debug(sbRegistroRepetido.toString());
		}
	}

	private static void generarEstadisticaArchivoSindo() throws IOException {
		CorteEstadisticoGeneralTask corteEstadisticoGeneralTask = new CorteEstadisticoGeneralTaskImpl();
		String nombreArchivo = "/Documentacion/Diagnostico/sindo_temp/AYPENT_CIZ01.txt";
		File arhivoDescargado = new File(nombreArchivo);

		List<String> lineasArchivos = FileUtils.readLines(arhivoDescargado);
		CorteTotalSindo corteTotalSindo = corteEstadisticoGeneralTask.generarEstadisticaArchivoSindo(lineasArchivos);

		log.debug("\n-------------------- SINDO CIZ 1 --------------------");
		log.debug("\nSe leyeron " + corteTotalSindo.getNumtotalMovimientos() + " registros:\n");
		log.debug("para Alta Patronal:            " + corteTotalSindo.getNumMovAltaPatronal());
		log.debug("para Clasificacion Ventanilla: " + corteTotalSindo.getNumMovClasifVentanilla());
		log.debug("para Clasificacion Internet:   " + corteTotalSindo.getNumMovClasifInternet());
		log.debug("para Cambios de Domicilio:     " + corteTotalSindo.getNumMovCambioDomicilio());
		log.debug("para Cambios de Domicilio de CT:" + corteTotalSindo.getNumMovCambioDomCt());
		log.debug("para Bajas de Seguro:          " + corteTotalSindo.getNumMovBajaSindo());
		log.debug("-------------------------------");
		log.debug("Movimientos Identificados:     "
						+ (corteTotalSindo.getNumMovAltaPatronal()
								+ corteTotalSindo.getNumMovClasifVentanilla()
								+ corteTotalSindo.getNumMovClasifInternet()
								+ corteTotalSindo.getNumMovCambioDomicilio()
								+ corteTotalSindo.getNumMovBajaSindo()
								+ corteTotalSindo.getNumMovCambioDomCt()));
		
		nombreArchivo = "/Documentacion/Diagnostico/sindo_temp/AYPENT_CIZ02.txt";
		arhivoDescargado = new File(nombreArchivo);

		lineasArchivos = FileUtils.readLines(arhivoDescargado);
		corteTotalSindo = corteEstadisticoGeneralTask.generarEstadisticaArchivoSindo(lineasArchivos);

		log.debug("\n-------------------- SINDO CIZ 2 --------------------");
		log.debug("\nSe leyeron " + corteTotalSindo.getNumtotalMovimientos() + " registros:\n");
		log.debug("para Alta Patronal:            " + corteTotalSindo.getNumMovAltaPatronal());
		log.debug("para Clasificacion Ventanilla: " + corteTotalSindo.getNumMovClasifVentanilla());
		log.debug("para Clasificacion Internet:   " + corteTotalSindo.getNumMovClasifInternet());
		log.debug("para Cambios de Domicilio:     " + corteTotalSindo.getNumMovCambioDomicilio());
		log.debug("para Cambios de Domicilio de CT:" + corteTotalSindo.getNumMovCambioDomCt());
		log.debug("para Bajas de Seguro:          " + corteTotalSindo.getNumMovBajaSindo());
		log.debug("-------------------------------");
		log.debug("Movimientos Identificados:     "
						+ (corteTotalSindo.getNumMovAltaPatronal()
								+ corteTotalSindo.getNumMovClasifVentanilla()
								+ corteTotalSindo.getNumMovClasifInternet()
								+ corteTotalSindo.getNumMovCambioDomicilio()
								+ corteTotalSindo.getNumMovBajaSindo()
								+ corteTotalSindo.getNumMovCambioDomCt()));
		
		nombreArchivo = "/Documentacion/Diagnostico/sindo_temp/AYPENT_CIZ03.txt";
		arhivoDescargado = new File(nombreArchivo);

		lineasArchivos = FileUtils.readLines(arhivoDescargado);
		corteTotalSindo = corteEstadisticoGeneralTask.generarEstadisticaArchivoSindo(lineasArchivos);

		log.debug("\n-------------------- SINDO CIZ 3 --------------------");
		log.debug("\nSe leyeron " + corteTotalSindo.getNumtotalMovimientos() + " registros:\n");
		log.debug("para Alta Patronal:            " + corteTotalSindo.getNumMovAltaPatronal());
		log.debug("para Clasificacion Ventanilla: " + corteTotalSindo.getNumMovClasifVentanilla());
		log.debug("para Clasificacion Internet:   " + corteTotalSindo.getNumMovClasifInternet());
		log.debug("para Cambios de Domicilio:     " + corteTotalSindo.getNumMovCambioDomicilio());
		log.debug("para Cambios de Domicilio de CT:" + corteTotalSindo.getNumMovCambioDomCt());
		log.debug("para Bajas de Seguro:          " + corteTotalSindo.getNumMovBajaSindo());
		log.debug("-------------------------------");
		log.debug("Movimientos Identificados:     "
						+ (corteTotalSindo.getNumMovAltaPatronal()
								+ corteTotalSindo.getNumMovClasifVentanilla()
								+ corteTotalSindo.getNumMovClasifInternet()
								+ corteTotalSindo.getNumMovCambioDomicilio()
								+ corteTotalSindo.getNumMovBajaSindo()
								+ corteTotalSindo.getNumMovCambioDomCt()));
	}
}
