package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.AbstractTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.MigracionArchivoProperties;

import org.apache.commons.fileupload.FileUploadException;

public class MovimientoCanaseTask extends AbstractTask {
	public void ejecutarProcesoMigracionRespaldoCanaseTest() {
		System.out.println("Hello!!");
		log.info("Hello");
	}

	public void ejecutarProcesoMigracionRespaldoCanase(MigracionArchivoProperties prop)
			throws IOException, FileUploadException {
		String rutaTemporal = prop.getRutaTemporalCanase();
		String rutaTemporalActual = prop.getRutaTempActualCanase();

		File directorioOrigen = new File(prop.getRutaOrigenCanase());
		File directorioTemp = new File(rutaTemporal);

		if (!directorioOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '"
					+ prop.getRutaOrigenCanase() + "' no existe");
		} else {
			if (!directorioTemp.exists()) {
				boolean isDirecorioCreado = directorioTemp.mkdir();
				
				if(!isDirecorioCreado){
					throw new FileNotFoundException("La ruta de temporales '" + rutaTemporal + "' no existe");
				}
			}

			// Se crea directorio de la fecha actual
			File directorioTempActualCanase = new File(rutaTemporalActual);
			boolean isDirecorioCreado = directorioTempActualCanase.mkdir();

			if (!isDirecorioCreado) {
				throw new FileNotFoundException("La ruta de temporales '"
						+ rutaTemporalActual + "' no existe");
			}

			// Validando archivo layout de CANASE
			System.out.println("---------------> Validacion de Layouts <---------------");
			validarArchivosAsignacionCanase(prop, directorioOrigen,
					directorioTempActualCanase);

			// Moviendo archivos al servidor FTP
			ejecutarTiempoEspera(prop.getTiempoEsperaCanase());
			System.out.println("---------------> Transferencia de Layouts al FTP <---------------");
			migrarArchivosMovimientoCanase(prop, directorioTempActualCanase, directorioTemp);

			System.out.println("---------------> Verificacion de Integridad <---------------");
		}
	}

	private void migrarArchivosMovimientoCanase(
			MigracionArchivoProperties prop, File directorioOrigen,
			File directorioTemp) throws IOException, FileUploadException {
		String rutaDestino = prop.getRutaDestinoFtpCanase();

		// Lectura de archivos (y directorios) de la ruta de origen
		String rutaTemp = directorioTemp.getAbsolutePath();
		log.info("Leyendo los archivos desde : " + directorioOrigen.getName());

		// Transfiriendo archivos no subidos anteriormente
		File[] listDirectoriosTemp = directorioTemp.listFiles();
		String pathDirectorioOrigen = directorioOrigen.getPath();

		for (File directorioTempAnteriorCanase : listDirectoriosTemp) {
			String pathDirectorioTempAnt = directorioTempAnteriorCanase.getPath();
			String strFechaAnterior = directorioTempAnteriorCanase.getName();

			StringBuffer sbRutaDestAnteriorFtpCanase = new StringBuffer();
			sbRutaDestAnteriorFtpCanase.append(rutaDestino).append("/")
					.append(strFechaAnterior);

			if (!pathDirectorioTempAnt.equals(pathDirectorioOrigen)) {
				String rutaRespaldo = prop.getRutaRespaldoCanase();
				respaldarArchivosMovimientoFilesystem(directorioTempAnteriorCanase.getPath(),
						rutaRespaldo, strFechaAnterior);
				transferirArchivoFilesystem(prop, directorioTempAnteriorCanase,
						sbRutaDestAnteriorFtpCanase.toString(), prop.getRutaCompletaBitacora());
			}
		}

		// Transfiriendo archivos correspondientes a la fecha de ejecucion
		try {
			crearBitacoraMovimientosCanase(prop, pathDirectorioOrigen);
		} catch (Exception e) {
			log.error("Error al generar bitacora");
			e.printStackTrace();
		}

		// Realizando respaldo de los archivos copiados (Se mueven los
		// archivos, posteriormente se el arbol de directorios nuevamente)
		System.out.println("---------------> Respaldo de Layouts al FTP <---------------");

		respaldarArchivosMovimientoFilesystem(directorioOrigen.getPath(),
				prop.getRutaRespaldoCanase(), prop.getStrFechaSistema());
		transferirArchivoFilesystem(prop, directorioOrigen,
				prop.getRutaDestActualFtpCanase(), prop.getRutaCompletaBitacora());

		try {
			crearBitacoraTransferenciaCanase(prop, prop.getRutaDestActualFtpCanase());
			crearBitacoraRespaldoCanase(prop, prop.getRutaRespaldoCanase(), prop.getStrFechaSistema());
		} catch (Exception e) {
			log.error("Error al generar bitacora");
			e.printStackTrace();
		}
	}

	private void validarArchivosAsignacionCanase(MigracionArchivoProperties prop,
			File directorioOrigen, File directorioTemp) throws IOException, FileUploadException {
		String rutaOrigen = directorioOrigen.getAbsolutePath();
		String rutaTemp = directorioTemp.getAbsolutePath();

		// Se obtienen el nombre del archivo utilizado para CANASE
		String archivoAsignacionCanase = prop.getNombreArchivoCanase();

		StringBuffer sbRutaArchivoAsignacionCanase = new StringBuffer();
		sbRutaArchivoAsignacionCanase.append(rutaOrigen).append("/")
				.append(archivoAsignacionCanase);

		StringBuffer sbRutaArchivoAsignacionCanaseTemp = new StringBuffer();
		sbRutaArchivoAsignacionCanaseTemp.append(rutaTemp).append("/")
				.append(archivoAsignacionCanase);

		// Lectura de archivos de la raiz
		List<String> listArchivos = new ArrayList<String>();
		File[] lstArchivoPrincipal = directorioOrigen.listFiles();

		for (File archivoPrincipal : lstArchivoPrincipal) {
			if (archivoPrincipal.isFile()) {
				listArchivos.add(archivoPrincipal.getName());
			}
		}

		validarArchivoMovimiento(listArchivos, archivoAsignacionCanase,
				sbRutaArchivoAsignacionCanaseTemp.toString(),
				sbRutaArchivoAsignacionCanase.toString());
	}

	private void verificarIntegridadArchivosMovimientoCanase(
			MigracionArchivoProperties prop, File directorioTempCanase,
			String strFechaEjecucion) throws IOException, FileUploadException {
		// Ruta destino FTP
		StringBuffer sbRutaDestino = new StringBuffer();
		sbRutaDestino.append(prop.getRutaDestinoFtpCanase()).append("/")
				.append(strFechaEjecucion);

		StringBuffer sbRutaRespaldo = new StringBuffer();
		sbRutaRespaldo.append(prop.getRutaRespaldoCanase()).append("/")
				.append(strFechaEjecucion);
		File directorioRespaldo = new File(sbRutaRespaldo.toString());

		// Lectura de nombre de archivos de CANASE
		int numReintentosVerificacion = prop.getNumReintentosCanase();

		String archivoAsignacionCanase = prop.getNombreArchivoCanase();

		// Se realizan reintentos en caso de que la verificacion no sea valida
		boolean archivoValido = verificarIntegridadArchivo(directorioTempCanase,
				archivoAsignacionCanase, sbRutaRespaldo.toString());

		do {
			System.out.println("---------------> Validacion " + numReintentosVerificacion
					+ " <---------------");
			numReintentosVerificacion--;

			if (!archivoValido) {
				log.error("Se transfiere nuevamente al servidor FTP el archivo: "
						+ archivoAsignacionCanase);

				// Transfiriendo archivos correspondientes a la fecha de
				// ejecucion
				transferirArchivoFilesystem(prop, directorioRespaldo,
						sbRutaDestino.toString(), prop.getRutaCompletaBitacora());

				archivoValido = verificarIntegridadArchivo(directorioRespaldo,
						archivoAsignacionCanase, sbRutaRespaldo.toString());

				log.error("Quedan " + numReintentosVerificacion + " posibles reintentos");
			} else {
				log.info("Archivo " + archivoAsignacionCanase + " VERFICADO exitosamente");
			}
		} while (!archivoValido && numReintentosVerificacion > 0);
	}

	private void crearBitacoraMovimientosCanase(MigracionArchivoProperties prop,
			String pathDirectorioOrigen) throws IOException {
		System.out.println("---------------> Generacion de Bitacoras (Antes de Transferencia) <---------------");
		String nombreArchivoCanase = prop.getNombreArchivoCanase();
		// Se crea carpeta del log en caso de no existir
		crearDirectorio(new File(prop.getRutaFilesysBitacora()));

		// Se crea la carpeta donde se escribira el log en caso de no existir
		crearDirectorio(new File(prop.getRutaActualBitacora()));

		StringBuffer sbRutaLayout = new StringBuffer();
		sbRutaLayout.append(pathDirectorioOrigen).append("/").append(nombreArchivoCanase);

		// Se cuenta numero de lineas del archivo temporal
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaLayout.toString(), "ASIGNACION DE NSS", null, "ANTES", true);
	}

	private void crearBitacoraTransferenciaCanase(
			MigracionArchivoProperties prop, String rutaDestino) throws IOException {
		System.out.println("---------------> Generacion de Bitacoras (Despues de Transferencia) <---------------");
		String nombreArchivoCanase = prop.getNombreArchivoCanase();

		StringBuffer sbRutaDirectorioDestino = new StringBuffer();
		sbRutaDirectorioDestino.append(prop.getRutaFilesysBaseFtp())
				.append("/").append(rutaDestino);

		// Se crea carpeta del log en caso de no existir
		crearDirectorio(new File(prop.getRutaFilesysBitacora()));

		// Se crea la carpeta donde se escribira el log en caso de no existir
		crearDirectorio(new File(prop.getRutaActualBitacora()));

		StringBuffer sbRutaDestinoCanase = new StringBuffer();
		sbRutaDestinoCanase.append(sbRutaDirectorioDestino).append("/").append(nombreArchivoCanase);

		// Se cuenta numero de lineas del archivo temporal
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaDestinoCanase.toString(), "ASIGNACION DE NSS", null, "DESPUES", true);
	}

	private void crearBitacoraRespaldoCanase(MigracionArchivoProperties prop,
			String rutaRespaldoCanase, String strFechaEjecucion) throws IOException {
		System.out.println("---------------> Generacion de Bitacoras (Respaldo) <---------------");
		String nombreArchivoCanase = prop.getNombreArchivoCanase();

		// Se crea carpeta del log en caso de no existir
		crearDirectorio(new File(prop.getRutaFilesysBitacora()));

		// Se crea la carpeta donde se escribira el log en caso de no existir
		crearDirectorio(new File(prop.getRutaActualBitacora()));

		StringBuffer sbRutaRespaldoCanase = new StringBuffer();
		sbRutaRespaldoCanase.append(rutaRespaldoCanase).append("/").append(strFechaEjecucion)
				.append("/").append(nombreArchivoCanase);

		// Se cuenta numero de lineas del archivo temporal
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaRespaldoCanase.toString(), "ASIGNACION DE NSS", null, "RESPALDO", true);
	}
}
