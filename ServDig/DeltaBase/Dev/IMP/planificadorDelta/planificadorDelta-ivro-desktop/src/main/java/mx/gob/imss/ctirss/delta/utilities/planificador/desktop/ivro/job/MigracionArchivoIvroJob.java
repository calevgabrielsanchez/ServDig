package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.model.enums.TipoProcesoEnum;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.task.MovimientoIvroTask;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.task.MovimientoIvroVacioTask;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.utils.MigracionArchivoIvroProperties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MigracionArchivoIvroJob {
	protected static final Log log = LogFactory.getLog(MigracionArchivoIvroJob.class);
	private static final String GENERAL_CONTEXT = "config/spring/general-context.xml";
	private static final String URL_APPLICATION_PROPERTIES = "config/app/application.properties";
	private static final String MODO_PRUEBA = "test";

	public static void main(String[] args) {
		if (args.length == 1) {
			String tipoProceso = args[0];
			Date fechaSistema = FechaUtils.getFechaSistema();
			ApplicationContext context = new ClassPathXmlApplicationContext(GENERAL_CONTEXT);

			log.info("------------->Hora de ejecucion: " + fechaSistema);
			try {
				MigracionArchivoIvroProperties prop = obtenerPropiedadesEjeucion(fechaSistema);

				log.info("------------->Hora de ejecucion: " + fechaSistema);
				if (MigracionArchivoIvroJob.validarProcesoBloqueado(prop)) {
					log.debug("\n");

					if (tipoProceso.equals(TipoProcesoEnum.PROCESO_ORDINARIO_IVRO.getCodigo())) {
						try {
							MovimientoIvroTask movimientoIvroTask = (MovimientoIvroTask) context.getBean("movimientoIvroTask");
							log.info("Ejecutando proceso ORDINARIO de migracion IVRO");
							movimientoIvroTask.ejecutarProcesoMigracionRespaldoIvro(prop);
						} catch (Exception e) {
							log.error("Error al realizar la migracion ORDINARIA de layouts de IVRO");
							e.printStackTrace();
						}
					} else if (tipoProceso.equals(TipoProcesoEnum.PROCESO_VACIO_IVRO.getCodigo())) {
						try {
							MovimientoIvroVacioTask movimientoIvroVacioTask = (MovimientoIvroVacioTask) context.getBean("movimientoIvroVacioTask");
							log.info("Ejecutando proceso EXTRAORDINARIO de migracion IVRO (Archivos Vacios)");
							movimientoIvroVacioTask.ejecutarProcesoMigracionRespaldoIvro(prop);
						} catch (Exception e) {
							log.error("Error al realizar la migracion EXTRAORDINARIA de layouts de IVRO (Archivos Vacios)");
							e.printStackTrace();
						}
					} else {
						log.error("Opcion no valida, no se ejecutara ningun proceso");
					}
				} else {
					log.error("El proceso ya habia sido ejecutado");
				}
			} catch (Exception e) {
				log.error(e);
			}
		} else {
			log.error("Numero de argumentos no valido; se debe capturar solo un parametro");
		}
	}

	private static boolean validarProcesoBloqueado(
			MigracionArchivoIvroProperties prop) throws IOException {
		boolean procesoBloqueado = false;

		String rutaArchivoEstatus = prop.getRutaBloqueoProceso();
		String nombreArchivoEstatus = "proceso_ivro_" +prop.getStrFechaBloqueo() + ".lock";

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

	private static MigracionArchivoIvroProperties obtenerPropiedadesEjeucion(
			Date fechaSistema) throws IOException {
		MigracionArchivoIvroProperties migracionArchivoIvroProperties = new MigracionArchivoIvroProperties();

		// Obtieniendo propiedades de ejecucion
		InputStream inputStream = MigracionArchivoIvroJob.class
				.getClassLoader().getResourceAsStream(URL_APPLICATION_PROPERTIES);
		Properties prop = new Properties();
		prop.load(inputStream);

		migracionArchivoIvroProperties.setModoAplicacion(prop.getProperty("application.mode"));

		migracionArchivoIvroProperties.setRutaDestinoFtpIvro(prop.getProperty("filesystem.ivro.output.url"));

		migracionArchivoIvroProperties.setRutaOrigenIvro(prop.getProperty("file.ivro.input.url"));

		migracionArchivoIvroProperties.setRutaTemporalIvro(prop.getProperty("file.ivro.input.tmp.url"));

		migracionArchivoIvroProperties.setRutaRespaldoIvro(prop.getProperty("file.ivro.resp.url"));

		migracionArchivoIvroProperties.setRutaBloqueoProceso(prop.getProperty("filesystem.delta.proceso.estatus"));
		migracionArchivoIvroProperties.setNombreArchivoSindoCiz1(prop.getProperty("sindo.file.ciz01"));
		migracionArchivoIvroProperties.setNombreArchivoSindoCiz2(prop.getProperty("sindo.file.ciz02"));
		migracionArchivoIvroProperties.setNombreArchivoSindoCiz3(prop.getProperty("sindo.file.ciz03"));
		migracionArchivoIvroProperties.setRutaFilesysBaseFtp(prop.getProperty("filesystem.ftp.base.output.url"));

		String strTiempoEsperaIvro = prop.getProperty("file.ivro.verif.time");
		migracionArchivoIvroProperties.setTiempoEsperaIvro(Integer.valueOf(strTiempoEsperaIvro));


		String strNumReintentosIvro = prop.getProperty("file.ivro.verif.retry");
		migracionArchivoIvroProperties.setNumReintentosIvro(Integer.valueOf(strNumReintentosIvro));


		// Definicion de fechas
		SimpleDateFormat sdfBloqueo;
		SimpleDateFormat sdfProceso;
		String modalidad = migracionArchivoIvroProperties.getModoAplicacion();
		if (modalidad.equals(MODO_PRUEBA)) {
			sdfBloqueo = new SimpleDateFormat("yyyyMMdd_HH");
			sdfProceso = new SimpleDateFormat("yyyyMMdd_HH");
		} else {
			sdfBloqueo = new SimpleDateFormat("yyyyMMdd");
			sdfProceso = new SimpleDateFormat("yyyyMMdd");
		}

		migracionArchivoIvroProperties.setStrFechaSistema(sdfProceso.format(fechaSistema));
		migracionArchivoIvroProperties.setStrFechaBloqueo(sdfBloqueo.format(fechaSistema));

		// Definicion de rutas actuales
		StringBuffer sbRutaTempIvroCompleta = new StringBuffer();
		sbRutaTempIvroCompleta.append(migracionArchivoIvroProperties.getRutaTemporalIvro())
				.append("/").append(migracionArchivoIvroProperties.getStrFechaSistema());
		migracionArchivoIvroProperties.setRutaTempActualIvro(sbRutaTempIvroCompleta.toString());


		StringBuffer sbRutaDestActualFtpIvro = new StringBuffer();
		sbRutaDestActualFtpIvro.append(migracionArchivoIvroProperties.getRutaDestinoFtpIvro())
				.append("/").append(migracionArchivoIvroProperties.getStrFechaSistema());
		migracionArchivoIvroProperties.setRutaDestActualFtpIvro(sbRutaDestActualFtpIvro.toString());

		return migracionArchivoIvroProperties;
	}
}
