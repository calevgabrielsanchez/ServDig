package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.ivro.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractTask {
	protected final Log log = LogFactory.getLog(getClass());

	protected void ejecutarTiempoEspera(int tiempoEsperaSistema) {
		try {
			log.debug("Esperando "
					+ tiempoEsperaSistema
					+ " ms antes de la transferencia de archivos al servidor FTP");
			Thread.sleep(tiempoEsperaSistema);
		} catch (InterruptedException e) {
			log.error("No se pudo programar tiempo de espera");
		}
	}

	protected void respaldarArchivosMovimientoFilesystem(String rutaOrigen,
			String rutaRespaldo, String strFechaEjecucion) throws IOException {
		if (StringUtils.isBlank(rutaOrigen)) {
			throw new FileNotFoundException("La ruta de origen no puede ser vacia");
		}
		if (StringUtils.isBlank(rutaRespaldo)) {
			throw new FileNotFoundException("La ruta de respaldo no puede ser vacia");
		}

		log.info("Leyendo los archivos desde : " + rutaOrigen);
		log.info("Se copiaran hacia : " + rutaRespaldo);
		File archivoOrigen = new File(rutaOrigen);

		StringBuffer sbRutaRespaldo = new StringBuffer();
		sbRutaRespaldo.append(rutaRespaldo).append("/").append(strFechaEjecucion);
		File directorioRespaldo = new File(sbRutaRespaldo.toString());

		if (!archivoOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '" + rutaOrigen
					+ "' no existe");
		} else {
			if (!directorioRespaldo.exists()) {
				boolean isDirecorioCreado = directorioRespaldo.mkdir();

				if (!isDirecorioCreado) {
					throw new IOException("El directorio de respaldo'" + rutaRespaldo + "' no fue creado");
				}
			}

			if (archivoOrigen.isDirectory()) {
				FileUtils.copyDirectory(archivoOrigen, directorioRespaldo);
			} else {
				StringBuffer sbRutaDirectorioDestino = new StringBuffer();

				sbRutaDirectorioDestino.append(rutaRespaldo).append("/")
						.append(archivoOrigen.getName());
				File archivoDestino = new File(sbRutaDirectorioDestino.toString());
				FileUtils.copyFile(archivoOrigen, archivoDestino);
			}
		}
	}

	protected void transferirArchivoFilesystem(MigracionArchivoIvroProperties prop,
			File archivoOrigen, String rutaDestino) throws FileUploadException,
			IOException {
		log.info("\t Original : " + archivoOrigen);

		StringBuffer sbRutaDirectorioDestino = new StringBuffer();
		sbRutaDirectorioDestino.append(prop.getRutaFilesysBaseFtp())
				.append("/").append(rutaDestino);
		File directorioDestino = new File(sbRutaDirectorioDestino.toString());
		log.info("\t Copiando : " + directorioDestino);

		if (archivoOrigen.isDirectory()) {
			FileUtils.moveDirectory(archivoOrigen, directorioDestino);
		} else {
			if (!directorioDestino.exists()) {
				boolean isDirecorioCreado = directorioDestino.mkdir();

				if (!isDirecorioCreado) {
					throw new FileUploadException("El directorio no fue creado en el servidor FTP. Intente nuevamente");
				}
			}

			// Se mueve el archivo a la ruta del FTP (Filesystem)
			sbRutaDirectorioDestino.append("/").append(archivoOrigen.getName());
			File archivoDestino = new File(sbRutaDirectorioDestino.toString());
			FileUtils.moveFile(archivoOrigen, archivoDestino);
		}
	}

	protected void validarArchivoMovimiento(List<String> listArchivos,
			String archivoCiz, String rutaArchivoCizTemp, String rutaArchivoCiz)
			throws IOException, FileUploadException {
		if (!listArchivos.contains(archivoCiz)) {
			// Si no existe el archivo se crea un archivo vacio.
			File archivoCizVacio = new File(rutaArchivoCizTemp);
			boolean archivoGenerado = archivoCizVacio.createNewFile();

			if (!archivoGenerado) {
				throw new FileUploadException("El archivo " + archivoCiz + " no fue creado");
			} else {
				log.info("El archivo " + archivoCiz + " no existe, se crea un archivo vacio");
			}
		} else {
			// Si existe el archivo se renombra para realizar operaciones con el
			// a partir de la fecha de corte.
			File archivoCizMov = new File(rutaArchivoCiz);
			File archivoCizTemp = new File(rutaArchivoCizTemp);

			FileUtils.moveFile(archivoCizMov, archivoCizTemp);
		}
	}
}
