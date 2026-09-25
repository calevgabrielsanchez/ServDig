package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.task.MovimientoCanaseTask;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.task.MovimientoRissTask;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.task.MovimientoSindoTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.MigracionArchivoProperties;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MigracionArchivoJob {
	protected static final Log log = LogFactory.getLog(MigracionArchivoJob.class);
	private static final String GENERAL_CONTEXT = "config/spring/general-context.xml";
	private static final String URL_APPLICATION_PROPERTIES = "config/app/application.properties";
	private static final String MODO_PRUEBA = "test";

	public static void main(String[] args) {
		Date fechaSistema = FechaUtils.getFechaSistema();
		ApplicationContext context = new ClassPathXmlApplicationContext(GENERAL_CONTEXT);

		MovimientoSindoTask movimientoSindoTask = (MovimientoSindoTask) context.getBean("movimientoSindoTask");
		MovimientoCanaseTask movimientoCanaseTask = (MovimientoCanaseTask) context.getBean("movimientoCanaseTask");
		MovimientoRissTask movimientoRissTask = (MovimientoRissTask) context.getBean("movimientoRissTask");

		try {
			MigracionArchivoProperties prop = obtenerPropiedadesEjeucion(fechaSistema);

			log.info("------------->Hora de ejecucion: " + fechaSistema);
			if (MigracionArchivoJob.validarProcesoBloqueado(prop)) {
				log.debug("\n");
				try {
					log.info("Ejecutando proceso de migracion SINDO");
					movimientoSindoTask.ejecutarProcesoMigracionRespaldoSindo(prop);
				} catch (Exception e) {
					log.error("Error al realizar la migracion de layouts de SINDO");
					e.printStackTrace();
				}
				log.debug("\n");
				try {
					log.info("Ejecutando proceso de migracion CANASE");
					movimientoCanaseTask.ejecutarProcesoMigracionRespaldoCanase(prop);
				} catch (Exception e) {
					log.error("Error al realizar la migracion de layouts de CANASE");
					e.printStackTrace();
				}
				log.debug("\n");
				try {
					log.info("Ejecutando proceso de migracion RISS");
					movimientoRissTask.ejecutarProcesoMigracionRespaldoRiss(prop);
				} catch (Exception e) {
					log.error("Error al realizar la migracion de layouts de RISS");
					e.printStackTrace();
				}
			} else {
				log.error("El proceso ya habia sido ejecutado");
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static boolean validarProcesoBloqueado(MigracionArchivoProperties prop)
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

	private static MigracionArchivoProperties obtenerPropiedadesEjeucion(Date fechaSistema)
			throws IOException {
		MigracionArchivoProperties migracionArchivoProperties = new MigracionArchivoProperties();

		// Obtieniendo propiedades de ejecucion
		InputStream inputStream = MovimientoSindoTask.class.getClassLoader()
				.getResourceAsStream(URL_APPLICATION_PROPERTIES);
		Properties prop = new Properties();
		prop.load(inputStream);

		migracionArchivoProperties.setModoAplicacion(prop.getProperty("application.mode"));

		migracionArchivoProperties.setRutaDestinoFtpCanase(prop.getProperty("filesystem.canase.output.url"));
		migracionArchivoProperties.setRutaDestinoFtpSindo(prop.getProperty("filesystem.sindo.output.url"));
		migracionArchivoProperties.setRutaDestinoFtpRiss(prop.getProperty("filesystem.riss.output.url"));

		migracionArchivoProperties.setRutaOrigenCanase(prop.getProperty("file.canase.input.url"));
		migracionArchivoProperties.setRutaOrigenSindo(prop.getProperty("file.sindo.input.url"));
		migracionArchivoProperties.setRutaOrigenRiss(prop.getProperty("file.riss.input.url"));

		migracionArchivoProperties.setRutaTemporalCanase(prop.getProperty("file.canase.input.tmp.url"));
		migracionArchivoProperties.setRutaTemporalSindo(prop.getProperty("file.sindo.input.tmp.url"));
		migracionArchivoProperties.setRutaTemporalRiss(prop.getProperty("file.riss.input.tmp.url"));

		migracionArchivoProperties.setRutaRespaldoCanase(prop.getProperty("file.canase.resp.url"));
		migracionArchivoProperties.setRutaRespaldoSindo(prop.getProperty("file.sindo.resp.url"));
		migracionArchivoProperties.setRutaRespaldoRiss(prop.getProperty("file.riss.resp.url"));

		migracionArchivoProperties.setRutaBloqueoProceso(prop.getProperty("filesystem.delta.proceso.estatus"));
		migracionArchivoProperties.setNombreArchivoCanase(prop.getProperty("canase.file.asignacion"));
		migracionArchivoProperties.setNombreArchivoSindoCiz1(prop.getProperty("sindo.file.ciz01"));
		migracionArchivoProperties.setNombreArchivoSindoCiz2(prop.getProperty("sindo.file.ciz02"));
		migracionArchivoProperties.setNombreArchivoSindoCiz3(prop.getProperty("sindo.file.ciz03"));
		migracionArchivoProperties.setNombreArchivoMovRiss(prop.getProperty("riss.file.movimiento"));
		migracionArchivoProperties.setNombreArchivoBitacora(prop.getProperty("global.log.file"));
		migracionArchivoProperties.setRutaFilesysBitacora(prop.getProperty("filesystem.global.tmp.log.url"));
		migracionArchivoProperties.setRutaFilesysBaseFtp(prop.getProperty("filesystem.ftp.base.output.url"));

		String strTiempoEsperaCanase = prop.getProperty("file.canase.verif.time");
		migracionArchivoProperties.setTiempoEsperaCanase(Integer.valueOf(strTiempoEsperaCanase));

		String strTiempoEsperaSindo = prop.getProperty("file.sindo.verif.time");
		migracionArchivoProperties.setTiempoEsperaSindo(Integer.valueOf(strTiempoEsperaSindo));
		
		String strTiempoEsperaRiss = prop.getProperty("file.riss.verif.time");
		migracionArchivoProperties.setTiempoEsperaRiss(Integer.valueOf(strTiempoEsperaRiss));


		String strNumReintentosCanase = prop.getProperty("file.canase.verif.retry");
		migracionArchivoProperties.setNumReintentosCanase(Integer.valueOf(strNumReintentosCanase));

		String strNumReintentosSindo = prop.getProperty("file.sindo.verif.retry");
		migracionArchivoProperties.setNumReintentosSindo(Integer.valueOf(strNumReintentosSindo));

		String strNumReintentosRiss = prop.getProperty("file.riss.verif.retry");
		migracionArchivoProperties.setNumReintentosRiss(Integer.valueOf(strNumReintentosRiss));


		// Definicion de fechas
		SimpleDateFormat sdfBloqueo;
		SimpleDateFormat sdfProceso;
		String modalidad = migracionArchivoProperties.getModoAplicacion();
		if (modalidad.equals(MODO_PRUEBA)) {
			sdfBloqueo = new SimpleDateFormat("yyyyMMdd_HHmm");
			sdfProceso = new SimpleDateFormat("yyyyMMdd_HH");
		} else {
			sdfBloqueo = new SimpleDateFormat("yyyyMMdd");
			sdfProceso = new SimpleDateFormat("yyyyMMdd");
		}

		migracionArchivoProperties.setStrFechaSistema(sdfProceso.format(fechaSistema));
		migracionArchivoProperties.setStrFechaBloqueo(sdfBloqueo.format(fechaSistema));

		// Definicion de rutas actuales
		StringBuffer sbRutaTempSindoCompleta = new StringBuffer();
		sbRutaTempSindoCompleta.append(migracionArchivoProperties.getRutaTemporalSindo())
				.append("/").append(migracionArchivoProperties.getStrFechaSistema());
		migracionArchivoProperties.setRutaTempActualSindo(sbRutaTempSindoCompleta.toString());

		StringBuffer sbRutaTempCanaseCompleta = new StringBuffer();
		sbRutaTempCanaseCompleta.append(migracionArchivoProperties.getRutaTemporalCanase())
				.append("/").append(migracionArchivoProperties.getStrFechaSistema());
		migracionArchivoProperties.setRutaTempActualCanase(sbRutaTempCanaseCompleta.toString());

		StringBuffer sbRutaTempRissCompleta = new StringBuffer();
		sbRutaTempRissCompleta.append(migracionArchivoProperties.getRutaTemporalRiss())
				.append("/").append(migracionArchivoProperties.getStrFechaSistema());
		migracionArchivoProperties.setRutaTempActualRiss(sbRutaTempRissCompleta.toString());


		StringBuffer sbRutaDestActualFtpSindo = new StringBuffer();
		sbRutaDestActualFtpSindo.append(migracionArchivoProperties.getRutaDestinoFtpSindo())
				.append("/").append(migracionArchivoProperties.getStrFechaSistema());
		migracionArchivoProperties.setRutaDestActualFtpSindo(sbRutaDestActualFtpSindo.toString());

		StringBuffer sbRutaDestActualFtpCanase = new StringBuffer();
		sbRutaDestActualFtpCanase.append(migracionArchivoProperties.getRutaDestinoFtpCanase())
				.append("/").append(migracionArchivoProperties.getStrFechaSistema());
		migracionArchivoProperties.setRutaDestActualFtpCanase(sbRutaDestActualFtpCanase.toString());
		
		StringBuffer sbRutaDestActualFtpRiss = new StringBuffer();
		sbRutaDestActualFtpRiss.append(migracionArchivoProperties.getRutaDestinoFtpRiss())
				.append("/").append(migracionArchivoProperties.getStrFechaSistema());
		migracionArchivoProperties.setRutaDestActualFtpRiss(sbRutaDestActualFtpRiss.toString());

		StringBuffer sbRutaActualBitacora = new StringBuffer();
		sbRutaActualBitacora.append(migracionArchivoProperties.getRutaFilesysBitacora())
				.append("/").append(migracionArchivoProperties.getStrFechaSistema());
		migracionArchivoProperties.setRutaActualBitacora(sbRutaActualBitacora.toString());

		StringBuffer sbRutaArchivoBitacora = new StringBuffer();
		sbRutaArchivoBitacora.append(migracionArchivoProperties.getRutaActualBitacora())
				.append("/").append(migracionArchivoProperties.getNombreArchivoBitacora());
		migracionArchivoProperties.setRutaCompletaBitacora(sbRutaArchivoBitacora.toString());

		return migracionArchivoProperties;
	}
}
