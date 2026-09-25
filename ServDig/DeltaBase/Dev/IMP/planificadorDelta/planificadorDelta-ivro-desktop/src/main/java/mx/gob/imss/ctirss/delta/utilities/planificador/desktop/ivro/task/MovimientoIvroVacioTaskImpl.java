package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.utils.AbstractTask;
import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.utils.MigracionArchivoIvroProperties;

import org.apache.commons.fileupload.FileUploadException;

public class MovimientoIvroVacioTaskImpl extends AbstractTask implements MovimientoIvroVacioTask {
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.utilities.planificadorDelta.desktop.ivro.task.MovimientoIvroVacioTask#ejecutarProcesoMigracionRespaldoIvro(mx.gob.imss.ctirss.delta.utilities.planificadorDelta.desktop.ivro.utils.MigracionArchivoIvroProperties)
	 */
	public void ejecutarProcesoMigracionRespaldoIvro(
			MigracionArchivoIvroProperties prop) throws IOException, FileUploadException {
		String rutaTemporal = prop.getRutaTemporalIvro();
		String rutaTemporalActual = prop.getRutaTempActualIvro();

		File directorioTemp = new File(rutaTemporal);

		// Se crea directorio de Temporales en caso de no existir
		if (!directorioTemp.exists()) {
			boolean isDirecorioCreado = directorioTemp.mkdir();

			if (!isDirecorioCreado) {
				throw new FileNotFoundException("La ruta de temporales '"
						+ rutaTemporal + "' no existe");
			}
		}

		// Se crea directorio de la fecha actual
		File directorioTempActualIvro = new File(rutaTemporalActual);
		boolean isDirecorioCreado = directorioTempActualIvro.mkdir();

		if (!isDirecorioCreado) {
			throw new FileNotFoundException("La ruta de temporales '"
					+ rutaTemporalActual + "' no existe");
		}

		// Creando archivo layout vacios de IVRO temporales
		log.debug("---------------> Creacion de Layouts Vacios <---------------");
		crearArchivosVaciosMovimientoIvro(prop, directorioTempActualIvro);

		// Moviendo archivos al servidor FTP
		ejecutarTiempoEspera(prop.getTiempoEsperaIvro());
		log.debug("---------------> Transferencia de Layouts al FTP <---------------");

		migrarArchivosMovimientoIvro(prop, directorioTempActualIvro, directorioTemp);

		log.debug("---------------> Respaldo de Layouts al FTP <---------------");
        StringBuffer sbRutaDestActualIvro = new StringBuffer();
        sbRutaDestActualIvro.append(prop.getRutaFilesysBaseFtp()).append("/").append(prop.getRutaDestActualFtpIvro());
        respaldarArchivosMovimientoFilesystem(sbRutaDestActualIvro.toString(), prop.getRutaRespaldoIvro(), prop.getStrFechaSistema());

		log.debug("---------------> Verificacion de Integridad <---------------");
	}

	private void migrarArchivosMovimientoIvro(
			MigracionArchivoIvroProperties prop, File directorioOrigen,
			File directorioTemp) throws IOException, FileUploadException {
		String rutaDestino = prop.getRutaDestinoFtpIvro();

		// Lectura de archivos (y directorios) de la ruta de origen
		String rutaTemp = directorioTemp.getAbsolutePath();
		log.info("Leyendo los archivos desde : " + directorioOrigen.getName());

		// Transfiriendo archivos no subidos anteriormente
		File[] listDirectoriosTemp = directorioTemp.listFiles();
		String pathDirectorioOrigen = directorioOrigen.getPath();

		for (File directorioTempAnteriorIvro : listDirectoriosTemp) {
			String pathDirectorioTempAnt = directorioTempAnteriorIvro.getPath();
			String strFechaAnterior = directorioTempAnteriorIvro.getName();

			StringBuffer sbRutaDestAnteriorFtpIvro = new StringBuffer();
			sbRutaDestAnteriorFtpIvro.append(rutaDestino).append("/")
					.append(strFechaAnterior);

			if (!pathDirectorioTempAnt.equals(pathDirectorioOrigen)) {
				transferirArchivoFilesystem(prop, directorioTempAnteriorIvro, sbRutaDestAnteriorFtpIvro.toString());

				StringBuffer sbRutaTempAnteriorIvro = new StringBuffer();
				sbRutaTempAnteriorIvro.append(rutaTemp).append("/").append(strFechaAnterior);
				String rutaRespaldo = prop.getRutaRespaldoIvro();

				StringBuffer sbRutaDestAnteriorIvro = new StringBuffer();
				sbRutaDestAnteriorIvro.append(prop.getRutaFilesysBaseFtp()).append("/")
						.append(sbRutaDestAnteriorFtpIvro.toString());
				respaldarArchivosMovimientoFilesystem(sbRutaDestAnteriorIvro.toString(),
						rutaRespaldo, strFechaAnterior);
			}
		}

		// Transfiriendo archivos correspondientes a la fecha de ejecucion
		transferirArchivoFilesystem(prop, directorioOrigen, prop.getRutaDestActualFtpIvro());
	}

	private void crearArchivosVaciosMovimientoIvro(MigracionArchivoIvroProperties prop,
			File directorioTemp) throws IOException, FileUploadException {
		String rutaTemp = directorioTemp.getAbsolutePath();

		// Se obtienen el nombre de los archivos Ciz
		String archivoCiz1 = prop.getNombreArchivoSindoCiz1();
		String archivoCiz2 = prop.getNombreArchivoSindoCiz2();
		String archivoCiz3 = prop.getNombreArchivoSindoCiz3();

		StringBuffer sbRutaArchivoCiz1Temp = new StringBuffer();
		sbRutaArchivoCiz1Temp.append(rutaTemp).append("/").append(archivoCiz1);
		StringBuffer sbRutaArchivoCiz2Temp = new StringBuffer();
		sbRutaArchivoCiz2Temp.append(rutaTemp).append("/").append(archivoCiz2);
		StringBuffer sbRutaArchivoCiz3Temp = new StringBuffer();
		sbRutaArchivoCiz3Temp.append(rutaTemp).append("/").append(archivoCiz3);

		crearArchivoMovimientoVacio(archivoCiz1, sbRutaArchivoCiz1Temp.toString());
		crearArchivoMovimientoVacio(archivoCiz2, sbRutaArchivoCiz2Temp.toString());
		crearArchivoMovimientoVacio(archivoCiz3, sbRutaArchivoCiz3Temp.toString());
	}

	private void crearArchivoMovimientoVacio(String archivoCiz,
			String rutaArchivoCizTemp) throws FileUploadException, IOException {

		File archivoCizVacio = new File(rutaArchivoCizTemp);
		boolean archivoGenerado = archivoCizVacio.createNewFile();

		if (!archivoGenerado) {
			throw new FileUploadException("El archivo " + archivoCiz + " no fue creado");
		} else {
			log.info("Se crea el archivo vacio " + archivoCiz);
		}
	}
}
