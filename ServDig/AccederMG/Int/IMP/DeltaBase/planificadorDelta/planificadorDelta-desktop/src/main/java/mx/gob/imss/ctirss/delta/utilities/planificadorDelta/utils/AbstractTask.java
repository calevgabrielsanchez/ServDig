package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	protected Map<String, String> mapChecksumFile = new HashMap<String, String>();

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
		File directorioOrigen = new File(rutaOrigen);

		if (!directorioOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '" + rutaOrigen + "' no existe");
		} else {
			for(File directorio : directorioOrigen.listFiles()){
				if(directorio.isDirectory()){
					listSubdirectorios.add(directorio.getAbsolutePath());
				}
			}
		}

		return listSubdirectorios;
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
			byte contenidoArchivoOrigen[] = obtenerBytesArchivo(archivoOrigen);
			//byte contenidoArchivoOrigen[] = FileUtils.readFileToByteArray(archivoOrigen);

			try {
				String checkArchivoOrigen = generarChecksumArchivo(contenidoArchivoOrigen);
				log.debug("Checksum of " + archivoOrigen.getPath() + ": " + checkArchivoOrigen);
				mapChecksumFile.put(archivoOrigen.getAbsolutePath(), checkArchivoOrigen);
			} catch (NoSuchAlgorithmException e) {
				log.error("El algoritmo utilizado para el checksum es incorrecto");
			}
			boolean archivoGenerado = ftpUploader.uploadFileContent(
					rutaDestino, contenidoArchivoOrigen);

			if (!archivoGenerado) {
				throw new FileUploadException("El archivo no fue almacenado en el servidor FTP. Intente nuevamente");
			}
		}
	}

	protected void transferirArchivoFilesystem(MigracionArchivoProperties prop,
			File archivoOrigen, String rutaDestino, String rutaArchivoBitacora) throws FileUploadException,
			IOException {
		log.info("\t Original : " + archivoOrigen);

		StringBuffer sbRutaDirectorioDestino = new StringBuffer();
		sbRutaDirectorioDestino.append(prop.getRutaFilesysBaseFtp())
				.append("/").append(rutaDestino);
		File directorioDestino = new File(sbRutaDirectorioDestino.toString());
		log.info("\t Copiando : " + directorioDestino);

		try {
			escribirResultadoTransferencia(rutaArchivoBitacora, archivoOrigen.getPath(), rutaDestino);
		} catch (Exception e) {
			log.error("Error al generar bitacora");
			e.printStackTrace();
		}

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

	protected void respaldarArchivosMovimiento(String rutaOrigen,
			String rutaRespaldo, List<String> listSubRespaldo,
			String strFechaEjecucion, boolean requiereRestauracion)
			throws IOException {
		if (StringUtils.isBlank(rutaOrigen)) {
			throw new FileNotFoundException("La ruta de origen no puede ser vacia");
		}
		if (StringUtils.isBlank(rutaRespaldo)) {
			throw new FileNotFoundException("La ruta de respaldo no puede ser vacia");
		}

		log.info("Leyendo los archivos desde : " + rutaOrigen);
		log.info("Se copiaran hacia : " + rutaRespaldo);
		File directorioOrigen = new File(rutaOrigen);

		if (!directorioOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '" + rutaOrigen + "' no existe");
		} else {
			// Se genera carpeta de respaldo
			StringBuffer sbRutaRaiz = new StringBuffer();
			sbRutaRaiz.append(rutaRespaldo).append("/").append(strFechaEjecucion);
			File directorioDestino =new File(sbRutaRaiz.toString());
			FileUtils.moveDirectory(directorioOrigen, directorioDestino);

			if (requiereRestauracion) {
				File archivoOrigenRestaurado = new File(rutaOrigen);
				boolean isDirecorioRestaurado = archivoOrigenRestaurado.mkdir();

				if (!isDirecorioRestaurado) {
					throw new IOException("El directorio '" + rutaOrigen + "' no fue restaurado");
				} else {
					for(String rutaTemp : listSubRespaldo){
						File directorioSubOrigenRestaurado = new File(rutaTemp);
						boolean isSubDirecorioRestaurado = directorioSubOrigenRestaurado.mkdir();

						if (!isSubDirecorioRestaurado) {
							throw new IOException("El directorio '" + rutaTemp + "' no fue restaurado");
						}
					}
				}
			}
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

	protected boolean verificarIntegridadArchivo(File directorioReferencia,
			String archivoLayout, String rutaRespaldoCompleto) throws IOException {
		boolean archivoValido = false;
		String rutaReferencia = directorioReferencia.getAbsolutePath();

		// Se obtienen el checksum del archivo enviado al servidor FTP
		StringBuffer sbRutaArchivoReferencia = new StringBuffer();
		sbRutaArchivoReferencia.append(rutaReferencia).append("/")
				.append(archivoLayout);
		File archivoReferencia = new File(sbRutaArchivoReferencia.toString());
		String checksumReferencia = mapChecksumFile.get(archivoReferencia
				.getAbsolutePath());

		// Se obtienen el checksum del archivo de respaldo
		StringBuffer sbRutaArchivoRespaldo = new StringBuffer();
		sbRutaArchivoRespaldo.append(rutaRespaldoCompleto).append("/")
				.append(archivoLayout);
		File archivoRespaldo = new File(sbRutaArchivoRespaldo.toString());

		byte contenidoArchivoRespaldo[] = obtenerBytesArchivo(archivoRespaldo);
		//byte contenidoArchivoRespaldo[] = FileUtils.readFileToByteArray(archivoRespaldo);
		try {
			String checksumRespaldoArchivo = generarChecksumArchivo(contenidoArchivoRespaldo);
			log.debug("Checksum of " + archivoRespaldo.getPath() + ": " + checksumRespaldoArchivo);

			if (StringUtils.isNotBlank(checksumReferencia)
					&& StringUtils.isNotBlank(checksumRespaldoArchivo)
					&& checksumRespaldoArchivo.equals(checksumReferencia)) {
				log.info("El contenido del archivo " + archivoLayout
						+ " es IGUAL al depositado en el servidor FTP");
				archivoValido = true;
			} else {
				log.error("El contenido del archivo " + archivoLayout
						+ " es DISTINTO al depositado en el servidor FTP");
			}
		} catch (NoSuchAlgorithmException e) {
			log.error("El algoritmo utilizado para el checksum es incorrecto");
		}

		return archivoValido;
	}

	protected String generarChecksumArchivo(byte[] contenidoArchivoOrigen)
			throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

		messageDigest.update(contenidoArchivoOrigen);
		byte[] digestBytes = messageDigest.digest();

		// Se convierte de formato byte a hexadecimal
		StringBuffer sbCheckSumCanase = new StringBuffer("");
		for (int i = 0; i < digestBytes.length; i++) {
			sbCheckSumCanase.append(Integer.toString(
					(digestBytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sbCheckSumCanase.toString();
	}

	protected void ejecutarTiempoEspera(int tiempoEsperaSistema) {
		try {
			log.debug("Esperando " + tiempoEsperaSistema
					+ " ms antes de la transferencia de archivos al servidor FTP");
			Thread.sleep(tiempoEsperaSistema);
		} catch (InterruptedException e) {
			log.error("No se pudo programar tiempo de espera");
		}
	}

	protected byte[] obtenerBytesArchivo(File archivoOrigen) throws IOException {
		FileInputStream fileInput = new FileInputStream(archivoOrigen);
		ArrayList<Byte> listDataTypes = new ArrayList<Byte>();
		byte[] dataBytes = new byte[1024];

		int bytesRead = 0;
		int bloques = 0;

		while ((bytesRead = fileInput.read(dataBytes)) != -1) {
			bloques++;

			for (int i = 0; i < bytesRead; i++) {
				listDataTypes.add(dataBytes[i]);
			}
		}
		log.debug("Tamano archivo (byte X byte): "
				+ listDataTypes.size() + " Tamano archivo: "
				+ archivoOrigen.length());
		byte[] totalDataBytes = new byte[listDataTypes.size()];

		for (int i = 0; i < listDataTypes.size(); i++) {
			totalDataBytes[i] = listDataTypes.get(i);
		}

		return totalDataBytes;
	}

	protected void crearDirectorio(File directorio) throws FileNotFoundException {
		if (!directorio.exists()) {
			boolean isDirecorioCreado = directorio.mkdir();

			if (!isDirecorioCreado) {
				throw new FileNotFoundException("La ruta '" + directorio + "' no fue creada");
			}
		}
	}

	protected void escribirResultadoDiagnostico(String rutaArchivoBitacora,
			String rutaArchivoTemporal, String movimiento,
			String observaciones, String momentoMovimiento, boolean isInicio)
			throws IOException {
		File arhivoDescargado = new File(rutaArchivoTemporal);
		int numeroLineasArchivo = 0;
		int numeroLineasArchivoNoVacias = 0;

		if (arhivoDescargado.exists()) {
			List<String> lineasArchivos = FileUtils.readLines(arhivoDescargado);

			for (String lineaArchivo : lineasArchivos) {
				if (StringUtils.isNotBlank(lineaArchivo)) {
					numeroLineasArchivoNoVacias++;
				}
			}

			numeroLineasArchivo = lineasArchivos.size();
		}

		// Se escribe resultado de la operacion en la bitacora
		Date fechaActual = new Date();
		String strFechaCorte = DateFormat.getDateInstance(DateFormat.FULL,
				new Locale("es", "MX")).format(fechaActual);
		SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm:ss");
		String strHoraCorte = sdfh.format(fechaActual);

		StringBuffer sbContenidoBitacora = new StringBuffer();

		if (isInicio) {
			sbContenidoBitacora.append("\r\n");
		}
		sbContenidoBitacora.append("\r\n").append("******* Datos del layot de la operacion ").append(movimiento)
				.append(" (").append(momentoMovimiento).append(" de migracion) *********")
				.append("\r\n").append("Hora Ejecucion:              ").append(strFechaCorte).append("-").append(strHoraCorte)
				.append("\r\n").append("Nombre del archivo:          ").append(rutaArchivoTemporal)
				.append("\r\n").append("Existe archivo temporal? :   ").append(arhivoDescargado.exists())
				.append("\r\n").append("No. de Lineas:               ").append(numeroLineasArchivo)
				.append("\r\n").append("No de Registros (No vacios): ").append(numeroLineasArchivoNoVacias);

		if (StringUtils.isNotBlank(observaciones)) {
			sbContenidoBitacora.append("\r\n").append("Observaciones:               ").append(observaciones);
		}

		sbContenidoBitacora.append("\r\n").append("**********************************************************************").append("\r\n");

		LayoutCallOut.generateLayout(rutaArchivoBitacora, sbContenidoBitacora.toString().getBytes());
	}

	private void escribirResultadoTransferencia(String rutaArchivoBitacora,
			String rutaOrigen, String rutaDestino) throws IOException {
		// Se escribe resultado de la operacion en la bitacora
		Date fechaActual = new Date();
		String strFechaCorte = DateFormat.getDateInstance(DateFormat.FULL,
				new Locale("es", "MX")).format(fechaActual);
		SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm:ss");
		String strHoraCorte = sdfh.format(fechaActual);

		StringBuffer sbContenidoBitacora = new StringBuffer();
		sbContenidoBitacora.append("\r\n").append("******* Transferencia FTP *******")
				.append("\r\n").append("Hora Ejecucion:              ").append(strFechaCorte).append("-").append(strHoraCorte)
				.append("\r\n").append("Ruta de Origen (Fylesystem): ").append(rutaOrigen)
				.append("\r\n").append("Ruta de Destino (FTP):       ").append(rutaDestino)
				.append("\r\n").append("**********************************").append("\r\n");

		LayoutCallOut.generateLayout(rutaArchivoBitacora, sbContenidoBitacora
				.toString().getBytes());
	}
}
