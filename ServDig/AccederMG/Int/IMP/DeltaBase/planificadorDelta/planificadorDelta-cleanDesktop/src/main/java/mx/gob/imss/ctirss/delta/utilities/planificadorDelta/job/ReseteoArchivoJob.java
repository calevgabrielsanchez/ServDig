package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task.ReseteoTempCanaseTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task.ReseteoTempSindoTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.ReseteoArchivoProperties;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReseteoArchivoJob {
	private static final String MODO_PRUEBA = "test";
	protected static final Log log = LogFactory.getLog(ReseteoArchivoJob.class);
	private static final String GENERAL_CONTEXT = "config/spring/general-context.xml";
	private static final String URL_APPLICATION_PROPERTIES = "config/application.properties";

	public static void main(String[] args) {
		Date fechaSistema = FechaUtils.getFechaSistema();
		ApplicationContext context = new ClassPathXmlApplicationContext(GENERAL_CONTEXT);
		ReseteoTempCanaseTask reseteoTempCanaseTask = (ReseteoTempCanaseTask) context.getBean("reseteoTempCanaseTask");
		ReseteoTempSindoTask reseteoTempSindoTask = (ReseteoTempSindoTask) context.getBean("reseteoTempSindoTask");

		log.info("------------->Hora de ejecucion: " + fechaSistema);
		try {
			ReseteoArchivoProperties prop = obtenerPropiedadesEjecucion(fechaSistema);

			if (ReseteoArchivoJob.validarProcesoBloqueado(prop)) {
				log.info("Ejecutando limpieza de temporales CANASE");
				try {
					reseteoTempCanaseTask.ejecutarProcesoMigracionRespaldoCanase(prop);
				} catch (Exception e) {
					log.error("Error al realizar limpieza de temporales de CANASE");
					e.printStackTrace();
				}
				System.out.println("\n");
				log.info("Ejecutando limpieza de temporales SINDO");
				try {
					reseteoTempSindoTask.ejecutarProcesoMigracionRespaldoSindo(prop);
				} catch (Exception e) {
					log.error("Error al realizar limpieza de temporales de SINDO");
					e.printStackTrace();
				}
			} else {
				log.error("El proceso ya habia sido ejecutado");
			}
		} catch (Exception e) {
			log.error("Error al realizar la validacion de bloqueos");
			e.printStackTrace();
		}

	}

	public static boolean validarProcesoBloqueado(ReseteoArchivoProperties prop)
			throws IOException, FileUploadException {
		boolean procesoBloqueado = false;

		String rutaArchivoEstatus = prop.getRutaBloqueoProceso();
		String nombreArchivoEstatus = "proceso_" +prop.getStrFechaBloqueo() + ".lock";

		// Validar si existe un archivo de bloqueo
		log.info("Validando proceso: " + nombreArchivoEstatus);
		File archivoEstatusVacio = new File(rutaArchivoEstatus + "/" + nombreArchivoEstatus);
		if (!archivoEstatusVacio.exists()) {
			archivoEstatusVacio.createNewFile();
			borrarArchivosPasados(rutaArchivoEstatus, nombreArchivoEstatus);
			procesoBloqueado = true;
		}

		return procesoBloqueado;
	}

	private static void borrarArchivosPasados(String rutaArchivoEstatus,
			String nombreArchivoEstatus) {
		// Lectura de archivos de la raiz
		File archivoOrigen = new File(rutaArchivoEstatus);
		List<String> listArchivos = new ArrayList<String>();
		File[] lstArchivoPrincipal = archivoOrigen.listFiles();

		for (File archivoPrincipal : lstArchivoPrincipal) {
			if (archivoPrincipal.isFile()) {
				listArchivos.add(archivoPrincipal.getName());
			}
		}

		log.info("Limpiando bloqueos anteriores");
		try {
			for (String nombreArchivo : listArchivos) {
				if (!nombreArchivo.equals(nombreArchivoEstatus)) {
					File archivoPasado = new File(rutaArchivoEstatus + "/" + nombreArchivo);
					log.info("Borrando " + archivoPasado);
					FileUtils.forceDelete(archivoPasado);
				}
			}
		} catch (IOException e) {
			log.error("Limpieza fallida" + e);
		}
	}

	private static ReseteoArchivoProperties obtenerPropiedadesEjecucion(
			Date fechaSistema) throws IOException {
		ReseteoArchivoProperties reseteoArchivoProperties = new ReseteoArchivoProperties();

		// Obtieniendo propiedades de ejecucion
		InputStream inputStream = ReseteoArchivoJob.class.getClassLoader()
				.getResourceAsStream(URL_APPLICATION_PROPERTIES);
		Properties prop = new Properties();
		prop.load(inputStream);

		reseteoArchivoProperties.setModoAplicacion(prop.getProperty("application.mode"));

		reseteoArchivoProperties.setRutaTemporalFtpCanase(prop.getProperty("filesystem.canase.tmp.output.url"));
		reseteoArchivoProperties.setRutaTemporalFtpSindo(prop.getProperty("filesystem.sindo.tmp.output.url"));

		reseteoArchivoProperties.setNombreDirectorioCanase(prop.getProperty("filesystem.global.tmp.log.dir.canase"));
		reseteoArchivoProperties.setNombreDirectorioSindo(prop.getProperty("filesystem.global.tmp.log.dir.sindo"));

		reseteoArchivoProperties.setRutaBloqueoProceso(prop.getProperty("filesystem.delta.proceso.estatus"));
		reseteoArchivoProperties.setNombreArchivoCanase(prop.getProperty("canase.file.asignacion"));
		reseteoArchivoProperties.setNombreArchivoSindo(prop.getProperty("sindo.file.ciztemp"));
		reseteoArchivoProperties.setNombreArchivoBitacora(prop.getProperty("global.log.file"));
		reseteoArchivoProperties.setRutaFilesysBitacora(prop.getProperty("filesystem.global.tmp.log.url"));
		reseteoArchivoProperties.setRutaFilesysBaseFtp(prop.getProperty("filesystem.ftp.base.output.url"));

		// Definicion de fechas
		SimpleDateFormat sdfBloqueo;
		SimpleDateFormat sdfProceso;
		String modalidad = reseteoArchivoProperties.getModoAplicacion();
		if (modalidad.equals(MODO_PRUEBA)) {
			sdfBloqueo = new SimpleDateFormat("yyyyMMdd_HH");
			sdfProceso = new SimpleDateFormat("yyyyMMdd_HH");
		} else {
			sdfBloqueo = new SimpleDateFormat("yyyyMMdd");
			sdfProceso = new SimpleDateFormat("yyyyMMdd");
		}

		reseteoArchivoProperties.setStrFechaSistema(sdfProceso.format(fechaSistema));
		reseteoArchivoProperties.setStrFechaBloqueo(sdfBloqueo.format(fechaSistema));

		StringBuffer sbRutaActualBitacora = new StringBuffer();
		sbRutaActualBitacora.append(reseteoArchivoProperties.getRutaFilesysBitacora())
				.append("/").append(reseteoArchivoProperties.getStrFechaSistema());
		reseteoArchivoProperties.setRutaActualBitacora(sbRutaActualBitacora.toString());

		StringBuffer sbRutaBitSindo = new StringBuffer();
		sbRutaBitSindo.append(reseteoArchivoProperties.getRutaActualBitacora())
				.append("/").append(reseteoArchivoProperties.getNombreDirectorioSindo());
		reseteoArchivoProperties.setRutaBitacoraSindo(sbRutaBitSindo.toString());

		StringBuffer sbRutaBitCanase = new StringBuffer();
		sbRutaBitCanase.append(reseteoArchivoProperties.getRutaActualBitacora())
				.append("/").append(reseteoArchivoProperties.getNombreDirectorioCanase());
		reseteoArchivoProperties.setRutaBitacoraCanase(sbRutaBitCanase.toString());

		StringBuffer sbRutaArchivoBitacora = new StringBuffer();
		sbRutaArchivoBitacora.append(reseteoArchivoProperties.getRutaActualBitacora())
				.append("/").append(reseteoArchivoProperties.getNombreArchivoBitacora());
		reseteoArchivoProperties.setRutaCompletaBitacora(sbRutaArchivoBitacora.toString());
		
		StringBuffer sbRutaArchivoTemporalCanase = new StringBuffer();
		sbRutaArchivoTemporalCanase.append(reseteoArchivoProperties.getRutaFilesysBaseFtp()).append("/")
				.append(reseteoArchivoProperties.getRutaTemporalFtpCanase())
				.append("/").append(reseteoArchivoProperties.getNombreArchivoCanase());
		reseteoArchivoProperties.setRutaCompletaTempCanase(sbRutaArchivoTemporalCanase.toString());

		StringBuffer sbRutaArchivoTemporalSindo = new StringBuffer();
		sbRutaArchivoTemporalSindo.append(reseteoArchivoProperties.getRutaFilesysBaseFtp()).append("/")
				.append(reseteoArchivoProperties.getRutaTemporalFtpSindo())
				.append("/").append(reseteoArchivoProperties.getNombreArchivoSindo());
		reseteoArchivoProperties.setRutaCompletaTempSindo(sbRutaArchivoTemporalSindo.toString());

		return reseteoArchivoProperties;
	}
}
