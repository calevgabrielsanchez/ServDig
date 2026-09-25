package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.sie.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FtpUploader;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.sie.task.MovimientoSieTaskImpl;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class AbstractTask {
	protected static final String MODO_PRUEBA = "test";
	protected static final String URL_APPLICATION_PROPERTIES = "application.properties";

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	protected MessageSource messageSource;
	@Autowired
	protected FtpUploader ftpUploader;

	protected List<String> obtenerListaDirectorios(String rutaOrigen) throws FileNotFoundException {
		List<String> listSubdirectorios = new ArrayList<String>();

		// Validacion de nombre de achivos
		if (StringUtils.isBlank(rutaOrigen)) {
			throw new FileNotFoundException("La ruta de origen no puede ser vacia");
		}

		log.info("Leyendo los archivos desde : " + rutaOrigen);
		File archivoOrigen = new File(rutaOrigen);

		if (!archivoOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '" + rutaOrigen + "' no existe");
		} else {
			for(File directorio : archivoOrigen.listFiles()){
				if(directorio.isDirectory()){
					listSubdirectorios.add(directorio.getAbsolutePath());
				}
			}
		}

		return listSubdirectorios;
	}

	protected void transferirArchivo(File archivoOrigen, String rutaRaiz,
			String rutaDestino) throws IOException, FileUploadException {
		if (archivoOrigen.isDirectory()) {
			log.info("Creando '" + rutaDestino + "'");
			boolean directorioGenerado = ftpUploader.uploadDirPath(rutaRaiz, rutaDestino);
			if (!directorioGenerado) {
				throw new FileUploadException("El directorio no fue creado en el servidor FTP. Intente nuevamente");
			}

			// Transferencia recursiva (es un directorio)
			String lstNombreArchivo[] = archivoOrigen.list();
			for (String nombreArchivo : lstNombreArchivo) {
				File origen = new File(archivoOrigen, nombreArchivo);
				transferirArchivo(origen, rutaRaiz, rutaDestino + "/" + nombreArchivo);
			}
		} else {
			log.info("\t Original : " + archivoOrigen);
			log.info("\t Copiando : " + rutaDestino);
			byte contenidoArchivoOrigen[] = FileUtils.readFileToByteArray(archivoOrigen);
			log.info("Contenido" + contenidoArchivoOrigen);
			boolean archivoGenerado = ftpUploader.uploadFileContent(
					rutaDestino, contenidoArchivoOrigen);

			if (!archivoGenerado) {
				throw new FileUploadException("El archivo no fue almacenado en el servidor FTP. Intente nuevamente");
			}
		}
	}

	protected void respaldarArchivosMovimiento(String rutaOrigen,
			String rutaRespaldo, Date fechaEjecucion, List<String> listSubRespaldo) throws IOException {
		if (StringUtils.isBlank(rutaOrigen)) {
			throw new FileNotFoundException("La ruta de origen no puede ser vacia");
		}
		if (StringUtils.isBlank(rutaRespaldo)) {
			throw new FileNotFoundException("La ruta de respaldo no puede ser vacia");
		}

		log.info("Leyendo los archivos desde : " + rutaOrigen);
		File archivoOrigen = new File(rutaOrigen);

		if (!archivoOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '" + rutaOrigen + "' no existe");
		} else {
			// Lectura de propiedades
			InputStream inputStream = MovimientoSieTaskImpl.class.getClassLoader()
					.getResourceAsStream(URL_APPLICATION_PROPERTIES);
			Properties prop = new Properties();
			prop.load(inputStream);
						
			SimpleDateFormat sdf;
			String modalidad = prop.getProperty("application.mode");;
			if (modalidad.equals(MODO_PRUEBA)) {
				sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			} else {
				sdf = new SimpleDateFormat("yyyyMMdd");
			}

			// Se genera carpeta de respaldo
			StringBuffer sbRutaRaiz = new StringBuffer();
			sbRutaRaiz.append(rutaRespaldo).append("/").append(sdf.format(fechaEjecucion));
			File archivoDestino =new File(sbRutaRaiz.toString());
			FileUtils.moveDirectory(archivoOrigen, archivoDestino);

			File archivoOrigenRestaurado = new File(rutaOrigen);
			boolean isDirecorioRestaurado = archivoOrigenRestaurado.mkdir();
			if (!isDirecorioRestaurado) {
				throw new IOException("El directorio '" + rutaOrigen + "' no fue restaurado");
			} else {
				for(String rutaTemp : listSubRespaldo){
					File archivoSubOrigenRestaurado = new File(rutaTemp);
					boolean isSubDirecorioRestaurado = archivoSubOrigenRestaurado.mkdir();
					if (!isSubDirecorioRestaurado) {
						throw new IOException("El directorio '" + rutaTemp + "' no fue restaurado");
					}
				}
			}
		}
	}
}
