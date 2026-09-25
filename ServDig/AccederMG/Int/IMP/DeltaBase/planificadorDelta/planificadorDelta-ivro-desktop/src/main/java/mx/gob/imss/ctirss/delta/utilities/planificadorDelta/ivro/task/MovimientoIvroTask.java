package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.ivro.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.ivro.utils.AbstractTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.ivro.utils.MigracionArchivoIvroProperties;

import org.apache.commons.fileupload.FileUploadException;

public class MovimientoIvroTask extends AbstractTask {
	public void ejecutarProcesoMigracionRespaldoIvroTest() {
		System.out.println("Hello!!");
		log.info("Hello");
	}

	public void ejecutarProcesoMigracionRespaldoIvro(
			MigracionArchivoIvroProperties prop) throws IOException, FileUploadException {
		String rutaTemporal = prop.getRutaTemporalIvro();
		String rutaTemporalActual = prop.getRutaTempActualIvro();

		File directorioOrigen = new File(prop.getRutaOrigenIvro());
		File directorioTemp = new File(rutaTemporal);

		if (!directorioOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '"
					+ prop.getRutaOrigenIvro() + "' no existe");
		} else {
			// Se crea directorio de Temporales en caso de no existir
			if (!directorioTemp.exists()) {
				boolean isDirecorioCreado = directorioTemp.mkdir();

				if (!isDirecorioCreado) {
					throw new FileNotFoundException("La ruta de temporales '" + rutaTemporal + "' no existe");
				}
			}

			// Se crea directorio de la fecha actual
			File directorioTempActualIvro = new File(rutaTemporalActual);
			boolean isDirecorioCreado = directorioTempActualIvro.mkdir();

			if (!isDirecorioCreado) {
				throw new FileNotFoundException("La ruta de temporales '"
						+ rutaTemporalActual + "' no existe");
			}

			// Validando archivo layout de IVRO
			System.out.println("---------------> Validacion de Layouts <---------------");
			validarArchivosMovimientoIvro(prop, directorioOrigen,
					directorioTempActualIvro);

			// Moviendo archivos al servidor FTP
			ejecutarTiempoEspera(prop.getTiempoEsperaIvro());
			System.out.println("---------------> Transferencia de Layouts al FTP <---------------");
			migrarArchivosMovimientoIvro(prop, directorioTempActualIvro, directorioTemp);

			System.out.println("---------------> Respaldo de Layouts al FTP <---------------");
            StringBuffer sbRutaDestActualIvro = new StringBuffer();
            sbRutaDestActualIvro.append(prop.getRutaFilesysBaseFtp()).append("/").append(prop.getRutaDestActualFtpIvro());
            respaldarArchivosMovimientoFilesystem(sbRutaDestActualIvro.toString(), prop.getRutaRespaldoIvro(), prop.getStrFechaSistema());

			System.out.println("---------------> Verificacion de Integridad <---------------");
		}
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

	private void validarArchivosMovimientoIvro(MigracionArchivoIvroProperties prop,
			File directorioOrigen, File directorioTemp) throws IOException, FileUploadException {
		String rutaOrigen = directorioOrigen.getAbsolutePath();
		String rutaTemp = directorioTemp.getAbsolutePath();

		// Se obtienen el nombre de los archivos Ciz
		String archivoCiz1 = prop.getNombreArchivoSindoCiz1();
		String archivoCiz2 = prop.getNombreArchivoSindoCiz2();
		String archivoCiz3 = prop.getNombreArchivoSindoCiz3();

		StringBuffer sbRutaArchivoCiz1 = new StringBuffer();
		sbRutaArchivoCiz1.append(rutaOrigen).append("/").append(archivoCiz1);
		StringBuffer sbRutaArchivoCiz2 = new StringBuffer();
		sbRutaArchivoCiz2.append(rutaOrigen).append("/").append(archivoCiz2);
		StringBuffer sbRutaArchivoCiz3 = new StringBuffer();
		sbRutaArchivoCiz3.append(rutaOrigen).append("/").append(archivoCiz3);

		StringBuffer sbRutaArchivoCiz1Temp = new StringBuffer();
		sbRutaArchivoCiz1Temp.append(rutaTemp).append("/").append(archivoCiz1);
		StringBuffer sbRutaArchivoCiz2Temp = new StringBuffer();
		sbRutaArchivoCiz2Temp.append(rutaTemp).append("/").append(archivoCiz2);
		StringBuffer sbRutaArchivoCiz3Temp = new StringBuffer();
		sbRutaArchivoCiz3Temp.append(rutaTemp).append("/").append(archivoCiz3);

		// Lectura de archivos de la raiz
		List<String> listArchivos = new ArrayList<String>();
		File[] lstArchivoPrincipal = directorioOrigen.listFiles();

		for (File archivoPrincipal : lstArchivoPrincipal) {
			if (archivoPrincipal.isFile()) {
				listArchivos.add(archivoPrincipal.getName());
			}
		}

		validarArchivoMovimiento(listArchivos, archivoCiz1,
				sbRutaArchivoCiz1Temp.toString(), sbRutaArchivoCiz1.toString());
		validarArchivoMovimiento(listArchivos, archivoCiz2,
				sbRutaArchivoCiz2Temp.toString(), sbRutaArchivoCiz2.toString());
		validarArchivoMovimiento(listArchivos, archivoCiz3,
				sbRutaArchivoCiz3Temp.toString(), sbRutaArchivoCiz3.toString());
	}
}
