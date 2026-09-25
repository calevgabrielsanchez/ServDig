package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.sie.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FtpUploader;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.sie.utils.AbstractTask;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class MovimientoSieTaskImpl extends AbstractTask implements MovimientoSieTask {
	private static final String URL_APPLICATION_PROPERTIES = "application.properties";

	@Autowired
	private FtpUploader ftpUploader;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.utilities.planificadorDelta.desktop.sie.task.MovimientoSieTask#ejecutarProcesoMigracionRespaldoSieTest()
	 */
	public void ejecutarProcesoMigracionRespaldoSieTest() {
		log.debug("Hello!!");
		log.info("Hello");
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.utilities.planificadorDelta.desktop.sie.task.MovimientoSieTask#ejecutarProcesoMigracionRespaldoSie()
	 */
	public void ejecutarProcesoMigracionRespaldoSie() throws IOException, FileUploadException {
		Date fechaSistema = FechaUtils.getFechaSistema();

		// Lectura de propiedades
		InputStream inputStream = MovimientoSieTaskImpl.class.getClassLoader()
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
			InputStream inputStream = MovimientoSieTaskImpl.class.getClassLoader()
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
