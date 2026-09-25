package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.sie.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.sie.task.MovimientoSieTask;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MigracionArchivoEstudianteJob {
	protected static final Log log = LogFactory
			.getLog(MigracionArchivoEstudianteJob.class);
	private static final String GENERAL_CONTEXT = "config/spring/general-context.xml";
	private static final String URL_APPLICATION_PROPERTIES = "config/app/application.properties";

	public static void main(String[] args) {
		Date fechaSistema = FechaUtils.getFechaSistema();
		ApplicationContext context = new ClassPathXmlApplicationContext(GENERAL_CONTEXT);
		MovimientoSieTask movimientoSieTask = (MovimientoSieTask) context.getBean("movimientoSieTask");

		try {
			log.info("------------->Hora de ejecucion: " + fechaSistema);
			if (MigracionArchivoEstudianteJob.validarProcesoBloqueado(fechaSistema)) {
				log.info("Ejecutando proceso de migracion");
				movimientoSieTask.ejecutarProcesoMigracionRespaldoSie();
			} else {
				log.error("El proceso ya habia sido ejecutado");
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static boolean validarProcesoBloqueado(Date fechaSistema)
			throws IOException, FileUploadException {
		boolean procesoBloqueado = false;
		InputStream inputStream = MovimientoSieTask.class.getClassLoader()
				.getResourceAsStream(URL_APPLICATION_PROPERTIES);
		Properties prop = new Properties();
		prop.load(inputStream);

		// Validar si existe un archivo de bloqueo
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String rutaArchivoEstatus = prop.getProperty("filesystem.delta.proceso.estatus");
		String nombreArchivoEstatus = "proceso_sie_" + sdf.format(fechaSistema) + ".lock";

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
}
