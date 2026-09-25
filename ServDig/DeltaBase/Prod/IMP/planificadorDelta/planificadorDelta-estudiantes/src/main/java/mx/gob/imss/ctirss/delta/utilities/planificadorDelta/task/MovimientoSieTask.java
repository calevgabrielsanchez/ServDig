package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.AbstractTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.FtpUploader;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class MovimientoSieTask extends AbstractTask {
	private static final String URL_APPLICATION_PROPERTIES = "application.properties";

	@Autowired
	private FtpUploader ftpUploader;

	public void ejecutarProcesoMigracionRespaldoSieTest() {
		System.out.println("Hello!!");
		log.info("Hello");
	}

	public void ejecutarProcesoMigracionRespaldoSie() throws IOException, FileUploadException {
		Date fechaSistema = FechaUtils.getFechaSistema();

		// Lectura de propiedades
		InputStream inputStream = MovimientoSieTask.class.getClassLoader()
				.getResourceAsStream(URL_APPLICATION_PROPERTIES);
		Properties prop = new Properties();
		prop.load(inputStream);

		// Moviendo archivos al servidor FTP
		String rutaOrigen = prop.getProperty("file.sie.input.url");
		String rutaDestino = prop.getProperty("filesystem.sie.output.url");
		migrarArchivosMovimientoSie(rutaOrigen, rutaDestino, fechaSistema);

		// Realizando respaldo de los archivos copiados (Se mueven los archivos, posteriormente se el arbol de directorios nuevamente)
		String rutaRespaldo = prop.getProperty("file.sie.resp.url");
		String rutaRespaldoOrigen = prop.getProperty("file.sie.resp.origen");
		List<String> listSubdirectorios = obtenerListaDirectorios(rutaRespaldoOrigen);

		respaldarArchivosMovimiento(rutaRespaldoOrigen, rutaRespaldo , fechaSistema, listSubdirectorios);
	}

	private void migrarArchivosMovimientoSie(String rutaOrigen,
			String rutaDestino, Date fechaEjecucion) throws IOException, FileUploadException {
		// Validacion de nombre de achivos
		if (StringUtils.isBlank(rutaOrigen)) {
			throw new FileNotFoundException("La ruta de origen no puede ser vacia");
		}
		if (StringUtils.isBlank(rutaDestino)) {
			throw new FileNotFoundException("La ruta de destino no puede ser vacia");
		}

		// Lectura de archivos (y directorios) de la ruta de origen
		log.info("Leyendo los archivos desde : " + rutaOrigen);
		File archivoOrigen = new File(rutaOrigen);

		if (!archivoOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '" + rutaOrigen + "' no existe");
		} else {
			// Lectura de propiedades
			InputStream inputStream = MovimientoSieTask.class.getClassLoader()
					.getResourceAsStream(URL_APPLICATION_PROPERTIES);
			Properties prop = new Properties();
			prop.load(inputStream);

			if (validarExistenciaArchivos(archivoOrigen)) {
				transferirArchivo(archivoOrigen, rutaDestino, rutaDestino);
			} else {
				log.info("No se creo ningun archivo en esta ruta : '" + rutaOrigen
						+ "' no se realiza ninguna copia al servidor FTP");
			}
		}
	}

	private boolean validarExistenciaArchivos(File archivoOrigen) {
		boolean existenArchivos = true;
		// Lectura de archivos de la raiz
		File[] lstArchivoPrincipal = archivoOrigen.listFiles();

		if (lstArchivoPrincipal == null || lstArchivoPrincipal.length <= 0) {
			existenArchivos = false;
		}

		return existenArchivos;
	}
}
