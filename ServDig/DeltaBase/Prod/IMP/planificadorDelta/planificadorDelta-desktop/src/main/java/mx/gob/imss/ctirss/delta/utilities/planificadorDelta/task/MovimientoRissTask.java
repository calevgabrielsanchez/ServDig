package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.AbstractTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.MigracionArchivoProperties;

public class MovimientoRissTask extends AbstractTask {
	public void ejecutarProcesoMigracionRespaldoRissTest() {
		System.out.println("Hello!!");
		log.info("Hello");
	}

	public void ejecutarProcesoMigracionRespaldoRiss(MigracionArchivoProperties prop)
			throws IOException, FileUploadException {
		String rutaTemporal = prop.getRutaTemporalRiss();
		String rutaTemporalActual = prop.getRutaTempActualRiss();

		File directorioOrigen = new File(prop.getRutaOrigenRiss());
		File directorioTemp = new File(rutaTemporal);

		if (!directorioOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '"
					+ prop.getRutaOrigenRiss() + "' no existe");
		} else {
			if (!directorioTemp.exists()) {
				boolean isDirecorioCreado = directorioTemp.mkdir();
				
				if(!isDirecorioCreado){
					throw new FileNotFoundException("La ruta de temporales '" + rutaTemporal + "' no existe");
				}
			}

			// Se crea directorio de la fecha actual
			File directorioTempActualRiss = new File(rutaTemporalActual);
			boolean isDirecorioCreado = directorioTempActualRiss.mkdir();

			if (!isDirecorioCreado) {
				throw new FileNotFoundException("La ruta de temporales '"
						+ rutaTemporalActual + "' no existe");
			}

			// Validando archivo layout de RISS
			System.out.println("---------------> Validacion de Layouts <---------------");
			validarArchivosAsignacionRiss(prop, directorioOrigen,
					directorioTempActualRiss);

			// Moviendo archivos al servidor FTP
			ejecutarTiempoEspera(prop.getTiempoEsperaRiss());
			System.out.println("---------------> Transferencia de Layouts al FTP <---------------");
			migrarArchivosMovimientoRiss(prop, directorioTempActualRiss, directorioTemp);

			System.out.println("---------------> Verificacion de Integridad <---------------");
		}
	}

	private void migrarArchivosMovimientoRiss(
			MigracionArchivoProperties prop, File directorioOrigen,
			File directorioTemp) throws IOException, FileUploadException {
		String rutaDestino = prop.getRutaDestinoFtpRiss();

		// Lectura de archivos (y directorios) de la ruta de origen
		String rutaTemp = directorioTemp.getAbsolutePath();
		log.info("Leyendo los archivos desde : " + directorioOrigen.getName());

		// Transfiriendo archivos no subidos anteriormente
		File[] listDirectoriosTemp = directorioTemp.listFiles();
		String pathDirectorioOrigen = directorioOrigen.getPath();

		for (File directorioTempAnteriorRiss : listDirectoriosTemp) {
			String pathDirectorioTempAnt = directorioTempAnteriorRiss.getPath();
			String strFechaAnterior = directorioTempAnteriorRiss.getName();

			StringBuffer sbRutaDestAnteriorFtpRiss = new StringBuffer();
			sbRutaDestAnteriorFtpRiss.append(rutaDestino).append("/")
					.append(strFechaAnterior);

			if (!pathDirectorioTempAnt.equals(pathDirectorioOrigen)) {
				String rutaRespaldo = prop.getRutaRespaldoRiss();
				respaldarArchivosMovimientoFilesystem(directorioTempAnteriorRiss.getPath(),
						rutaRespaldo, strFechaAnterior);
				transferirArchivoFilesystem(prop, directorioTempAnteriorRiss,
						sbRutaDestAnteriorFtpRiss.toString(), prop.getRutaCompletaBitacora());
			}
		}

		// Realizando respaldo de los archivos copiados (Se mueven los
		// archivos, posteriormente se el arbol de directorios nuevamente)
		System.out.println("---------------> Respaldo de Layouts al FTP <---------------");
		respaldarArchivosMovimientoFilesystem(directorioOrigen.getPath(),
				prop.getRutaRespaldoRiss(), prop.getStrFechaSistema());
		// Transfiriendo archivos correspondientes a la fecha de ejecucion
		transferirArchivoFilesystem(prop, directorioOrigen,
				prop.getRutaDestActualFtpRiss(), prop.getRutaCompletaBitacora());
	}

	private void validarArchivosAsignacionRiss(MigracionArchivoProperties prop,
			File directorioOrigen, File directorioTemp) throws IOException, FileUploadException {
		String rutaOrigen = directorioOrigen.getAbsolutePath();
		String rutaTemp = directorioTemp.getAbsolutePath();

		// Se obtienen el nombre del archivo utilizado para RISS
		String archivoAsignacionRiss = prop.getNombreArchivoMovRiss();

		StringBuffer sbRutaArchivoAsignacionRiss = new StringBuffer();
		sbRutaArchivoAsignacionRiss.append(rutaOrigen).append("/")
				.append(archivoAsignacionRiss);

		StringBuffer sbRutaArchivoAsignacionRissTemp = new StringBuffer();
		sbRutaArchivoAsignacionRissTemp.append(rutaTemp).append("/")
				.append(archivoAsignacionRiss);

		// Lectura de archivos de la raiz
		List<String> listArchivos = new ArrayList<String>();
		File[] lstArchivoPrincipal = directorioOrigen.listFiles();

		for (File archivoPrincipal : lstArchivoPrincipal) {
			if (archivoPrincipal.isFile()) {
				listArchivos.add(archivoPrincipal.getName());
			}
		}

		validarArchivoMovimiento(listArchivos, archivoAsignacionRiss,
				sbRutaArchivoAsignacionRissTemp.toString(),
				sbRutaArchivoAsignacionRiss.toString());
	}

	private void verificarIntegridadArchivosMovimientoRiss(
			MigracionArchivoProperties prop, File directorioTempRiss,
			String strFechaEjecucion) throws IOException, FileUploadException {
		// Ruta destino FTP
		StringBuffer sbRutaDestino = new StringBuffer();
		sbRutaDestino.append(prop.getRutaDestinoFtpRiss()).append("/")
				.append(strFechaEjecucion);

		StringBuffer sbRutaRespaldo = new StringBuffer();
		sbRutaRespaldo.append(prop.getRutaRespaldoRiss()).append("/")
				.append(strFechaEjecucion);
		File directorioRespaldo = new File(sbRutaRespaldo.toString());

		// Lectura de nombre de archivos de RISS
		int numReintentosVerificacion = prop.getNumReintentosRiss();

		String archivoAsignacionRiss = prop.getNombreArchivoMovRiss();

		// Se realizan reintentos en caso de que la verificacion no sea valida
		boolean archivoValido = verificarIntegridadArchivo(directorioTempRiss,
				archivoAsignacionRiss, sbRutaRespaldo.toString());

		do {
			System.out.println("---------------> Validacion " + numReintentosVerificacion
					+ " <---------------");
			numReintentosVerificacion--;

			if (!archivoValido) {
				log.error("Se transfiere nuevamente al servidor FTP el archivo: "
						+ archivoAsignacionRiss);

				// Transfiriendo archivos correspondientes a la fecha de
				// ejecucion
				transferirArchivoFilesystem(prop, directorioRespaldo,
						sbRutaDestino.toString(), prop.getRutaCompletaBitacora());

				archivoValido = verificarIntegridadArchivo(directorioRespaldo,
						archivoAsignacionRiss, sbRutaRespaldo.toString());

				log.error("Quedan " + numReintentosVerificacion + " posibles reintentos");
			} else {
				log.info("Archivo " + archivoAsignacionRiss + " VERFICADO exitosamente");
			}
		} while (!archivoValido && numReintentosVerificacion > 0);
	}
}
