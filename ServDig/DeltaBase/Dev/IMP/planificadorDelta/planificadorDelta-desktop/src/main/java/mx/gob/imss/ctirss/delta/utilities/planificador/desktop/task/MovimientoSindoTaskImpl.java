package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.AbstractTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.MigracionArchivoProperties;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class MovimientoSindoTaskImpl extends AbstractTask implements MovimientoSindoTask {
	private static final int IDX_INI_CADENA_MOV = 5;
	private static final int IDX_FIN_CADENA_MOV = 7;

	private String IND_ALTA_PATRONAL = "01";
	private String IND_MODIF_CLASIF = "06";

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.utilities.planificadorDelta.desktop.task.MovimientoSindoTask#ejecutarProcesoMigracionRespaldoSindoTest()
	 */
	public void ejecutarProcesoMigracionRespaldoSindoTest(){
		log.debug("Hello!!");
		log.info("Hello");
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.utilities.planificadorDelta.desktop.task.MovimientoSindoTask#ejecutarProcesoMigracionRespaldoSindo(mx.gob.imss.ctirss.delta.utilities.planificadorDelta.desktop.utils.MigracionArchivoProperties)
	 */
	public void ejecutarProcesoMigracionRespaldoSindo(MigracionArchivoProperties prop)
			throws IOException, FileUploadException {
		String rutaTemporal = prop.getRutaTemporalSindo();
		String rutaTemporalActual = prop.getRutaTempActualSindo();

		File directorioOrigen = new File(prop.getRutaOrigenSindo());
		File directorioTemp = new File(rutaTemporal);

		if (!directorioOrigen.exists()) {
			throw new FileNotFoundException("La ruta de origen '"
					+ prop.getRutaOrigenSindo() + "' no existe");
		} else {
			// Se crea directorio de Temporales en caso de no existir
			if (!directorioTemp.exists()) {
				boolean isDirecorioCreado = directorioTemp.mkdir();

				if (!isDirecorioCreado) {
					throw new FileNotFoundException("La ruta de temporales '"
							+ rutaTemporal + "' no existe");
				}
			}

			// Se crea directorio de la fecha actual
			File directorioTempActualSindo = new File(rutaTemporalActual);
			boolean isDirecorioCreado = directorioTempActualSindo.mkdir();

			if (!isDirecorioCreado) {
				throw new FileNotFoundException("La ruta de temporales '"
						+ rutaTemporalActual + "' no existe");
			}

			// Validando archivo layout de SINDO
			log.debug("---------------> Validacion de Layouts <---------------");
			validarArchivosMovimientoCizSindo(prop, directorioOrigen,
					directorioTempActualSindo);

			// Moviendo archivos al servidor FTP
			ejecutarTiempoEspera(prop.getTiempoEsperaSindo());
			log.debug("---------------> Transferencia de Layouts al FTP <---------------");
			migrarArchivosMovimientoSindo(prop, directorioTempActualSindo, directorioTemp);

			log.debug("---------------> Verificacion de Integridad <---------------");
		}
	}

	private void migrarArchivosMovimientoSindo(MigracionArchivoProperties prop,
			File directorioOrigen, File directorioTemp) throws IOException,
			FileUploadException {
		String rutaDestino = prop.getRutaDestinoFtpSindo();

		// Lectura de archivos (y directorios) de la ruta de origen
		String rutaTemp = directorioTemp.getAbsolutePath();
		log.info("Leyendo los archivos desde : " + directorioOrigen.getName());

		// Transfiriendo archivos no subidos anteriormente
		File[] listDirectoriosTemp = directorioTemp.listFiles();
		String pathDirectorioOrigen = directorioOrigen.getPath();

		for (File directorioTempAnteriorSindo : listDirectoriosTemp) {
			String pathDirectorioTempAnt = directorioTempAnteriorSindo.getPath();
			String strFechaAnterior = directorioTempAnteriorSindo.getName();

			StringBuffer sbRutaDestAnteriorFtpSindo = new StringBuffer();
			sbRutaDestAnteriorFtpSindo.append(rutaDestino).append("/")
					.append(strFechaAnterior);

			if (!pathDirectorioTempAnt.equals(pathDirectorioOrigen)) {
				String rutaRespaldo = prop.getRutaRespaldoSindo();
				respaldarArchivosMovimientoFilesystem(directorioTempAnteriorSindo.getPath(),
						rutaRespaldo, strFechaAnterior);
				transferirArchivoFilesystem(prop, directorioTempAnteriorSindo,
						sbRutaDestAnteriorFtpSindo.toString(), prop.getRutaCompletaBitacora());
			}
		}

		// Transfiriendo archivos correspondientes a la fecha de ejecucion
		try {
			crearBitacoraMovimientosSindo(prop, pathDirectorioOrigen);
		} catch (Exception e) {
			log.error("Error al generar bitacora");
			e.printStackTrace();
		}

		// Realizando respaldo de los archivos copiados (Se mueven los
		// archivos, posteriormente se el arbol de directorios nuevamente)
		log.debug("---------------> Respaldo de Layouts al FTP <---------------");
		respaldarArchivosMovimientoFilesystem(directorioOrigen.getPath(),
				prop.getRutaRespaldoSindo(), prop.getStrFechaSistema());
		transferirArchivoFilesystem(prop, directorioOrigen,
				prop.getRutaDestActualFtpSindo(), prop.getRutaCompletaBitacora());

		try {
			crearBitacoraTransferenciaSindo(prop, prop.getRutaDestActualFtpSindo());
			crearBitacoraRespaldoSindo(prop, prop.getRutaRespaldoSindo(), prop.getStrFechaSistema());
		} catch (Exception e) {
			log.error("Error al generar bitacora");
			e.printStackTrace();
		}
	}

	private void validarArchivosMovimientoCizSindo(MigracionArchivoProperties prop,
			File directorioOrigen, File directorioTemp)
			throws IOException, FileUploadException {
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

	private void verificarIntegridadArchivosMovimientoSindo(
			MigracionArchivoProperties prop, File directorioTempSindo,
			String strFechaEjecucion)
			throws IOException, FileUploadException {
		// Ruta destino FTP
		StringBuffer sbRutaDestino = new StringBuffer();
		sbRutaDestino.append(prop.getRutaDestinoFtpSindo()).append("/")
				.append(strFechaEjecucion);

		StringBuffer sbRutaRespaldo = new StringBuffer();
		sbRutaRespaldo.append(prop.getRutaRespaldoSindo()).append("/")
				.append(strFechaEjecucion);
		File directorioRespaldo = new File(sbRutaRespaldo.toString());

		// Lectura de nombre de archivos de SINDO
		int numReintentosVerificacion = prop.getNumReintentosSindo();

		String archivoCiz1 = prop.getNombreArchivoSindoCiz1();
		String archivoCiz2 = prop.getNombreArchivoSindoCiz2();
		String archivoCiz3 = prop.getNombreArchivoSindoCiz3();

		// Se realizan reintentos en caso de que la verificacion no sea valida
		// (CIZ1)
		boolean archivoValidoCiz1 = verificarIntegridadArchivo(directorioTempSindo,
				archivoCiz1, sbRutaRespaldo.toString());
		boolean archivoValidoCiz2 = verificarIntegridadArchivo(directorioTempSindo,
				archivoCiz2, sbRutaRespaldo.toString());
		boolean archivoValidoCiz3 = verificarIntegridadArchivo(directorioTempSindo,
				archivoCiz3, sbRutaRespaldo.toString());

		do {
			log.debug("---------------> Validacion " + numReintentosVerificacion
					+ " <---------------");
			numReintentosVerificacion--;

			if (!archivoValidoCiz1 || !archivoValidoCiz2 || !archivoValidoCiz3) {
				log.error("Se transfiere nuevamente al servidor FTP los archivo: "
						+ archivoCiz1 + ", " + archivoCiz2 + ", " + archivoCiz3);

				// Transfiriendo archivos correspondientes a la fecha de
				// ejecucion
				/*transferirArchivo(directorioRespaldo, sbRutaDestino.toString(),
						sbRutaDestino.toString());*/
				transferirArchivoFilesystem(prop, directorioRespaldo,
						sbRutaDestino.toString(), prop.getRutaCompletaBitacora());

				archivoValidoCiz1 = verificarIntegridadArchivo(directorioRespaldo,
						archivoCiz1, sbRutaRespaldo.toString());
				archivoValidoCiz2 = verificarIntegridadArchivo(directorioRespaldo,
						archivoCiz2, sbRutaRespaldo.toString());
				archivoValidoCiz3 = verificarIntegridadArchivo(directorioRespaldo,
						archivoCiz3, sbRutaRespaldo.toString());

				log.error("Quedan " + numReintentosVerificacion + " posibles reintentos");
			} else {
				log.info("Archivos " + archivoCiz1 + ", " + archivoCiz2 + ", "
						+ archivoCiz3 + " VERFICADOS exitosamente");
			}
		} while ((!archivoValidoCiz1 || !archivoValidoCiz2 || !archivoValidoCiz3)
				&& numReintentosVerificacion > 0);
	}

	private void crearBitacoraMovimientosSindo(MigracionArchivoProperties prop,
			String pathDirectorioOrigen) throws IOException {
		log.debug("---------------> Generacion de Bitacoras (Antes de Transferencia) <---------------");
		String nombreArchivoCiz1 = prop.getNombreArchivoSindoCiz1();
		String nombreArchivoCiz2 = prop.getNombreArchivoSindoCiz2();
		String nombreArchivoCiz3 = prop.getNombreArchivoSindoCiz3();

		// Se crea carpeta del log en caso de no existir
		crearDirectorio(new File(prop.getRutaFilesysBitacora()));

		// Se crea la carpeta donde se escribira el log en caso de no existir
		crearDirectorio(new File(prop.getRutaActualBitacora()));

		StringBuffer sbRutaLayoutCiz1 = new StringBuffer();
		sbRutaLayoutCiz1.append(pathDirectorioOrigen).append("/").append(nombreArchivoCiz1);

		StringBuffer sbRutaLayoutCiz2 = new StringBuffer();
		sbRutaLayoutCiz2.append(pathDirectorioOrigen).append("/").append(nombreArchivoCiz2);

		StringBuffer sbRutaLayoutCiz3 = new StringBuffer();
		sbRutaLayoutCiz3.append(pathDirectorioOrigen).append("/").append(nombreArchivoCiz3);

		// Se cuenta numero de lineas del archivo temporal
		String observaciones = clasificarMovimientos(sbRutaLayoutCiz1.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaLayoutCiz1.toString(), "ALTAS PATRONALES CIZ1", observaciones, "ANTES", true);

		observaciones = clasificarMovimientos(sbRutaLayoutCiz2.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaLayoutCiz2.toString(), "ALTAS PATRONALES CIZ2", observaciones, "ANTES", false);

		observaciones = clasificarMovimientos(sbRutaLayoutCiz3.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaLayoutCiz3.toString(), "ALTAS PATRONALES CIZ3", observaciones, "ANTES", false);
	}

	private String clasificarMovimientos(String rutaArchivoTemporal) throws IOException {
		StringBuffer sbObservaciones = new StringBuffer();

		File arhivoSindoDescargado = new File(rutaArchivoTemporal);
		int numeroAltasPatronales = 0;
		int numeroModificacionesClasificacion = 0;
		int numeroOotrosMovimientos = 0;

		if (arhivoSindoDescargado.exists()) {
			List<String> lineasArchivos = FileUtils.readLines(arhivoSindoDescargado);

			for (String lineaArchivo : lineasArchivos) {
				if (StringUtils.isNotBlank(lineaArchivo)) {
					String indMovimiento = lineaArchivo.substring(IDX_INI_CADENA_MOV, IDX_FIN_CADENA_MOV);
					if (indMovimiento.equals(IND_ALTA_PATRONAL)) {
						numeroAltasPatronales++;
					} else if (indMovimiento.equals(IND_MODIF_CLASIF)) {
						numeroModificacionesClasificacion++;
					} else {
						numeroOotrosMovimientos++;
					}
				}
			}
		}

		sbObservaciones.append("Altas Patronales: ").append(numeroAltasPatronales)
				.append(", Modificaciones de Clasificacion: ").append(numeroModificacionesClasificacion)
				.append(", Otros Movimientos: ").append(numeroOotrosMovimientos);

		return sbObservaciones.toString();
	}

	private void crearBitacoraTransferenciaSindo(
			MigracionArchivoProperties prop, String rutaDestino) throws IOException {
		log.debug("---------------> Generacion de Bitacoras (Despues de Transferencia) <---------------");
		String nombreArchivoCiz1 = prop.getNombreArchivoSindoCiz1();
		String nombreArchivoCiz2 = prop.getNombreArchivoSindoCiz2();
		String nombreArchivoCiz3 = prop.getNombreArchivoSindoCiz3();

		StringBuffer sbRutaDirectorioDestino = new StringBuffer();
		sbRutaDirectorioDestino.append(prop.getRutaFilesysBaseFtp())
				.append("/").append(rutaDestino);

		// Se crea carpeta del log en caso de no existir
		crearDirectorio(new File(prop.getRutaFilesysBitacora()));

		// Se crea la carpeta donde se escribira el log en caso de no existir
		crearDirectorio(new File(prop.getRutaActualBitacora()));

		StringBuffer sbRutaDestinoCiz1 = new StringBuffer();
		sbRutaDestinoCiz1.append(sbRutaDirectorioDestino).append("/").append(nombreArchivoCiz1);
		
		StringBuffer sbRutaDestinoCiz2 = new StringBuffer();
		sbRutaDestinoCiz2.append(sbRutaDirectorioDestino).append("/").append(nombreArchivoCiz2);
		
		StringBuffer sbRutaDestinoCiz3 = new StringBuffer();
		sbRutaDestinoCiz3.append(sbRutaDirectorioDestino).append("/").append(nombreArchivoCiz3);

		// Se cuenta numero de lineas del archivo temporal
		String observaciones = clasificarMovimientos(sbRutaDestinoCiz1.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaDestinoCiz1.toString(), "ALTAS PATRONALES CIZ1", observaciones, "DESPUES", true);

		// Se cuenta numero de lineas del archivo temporal
		observaciones = clasificarMovimientos(sbRutaDestinoCiz2.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaDestinoCiz2.toString(), "ALTAS PATRONALES CIZ2", observaciones, "DESPUES", false);

		// Se cuenta numero de lineas del archivo temporal
		observaciones = clasificarMovimientos(sbRutaDestinoCiz3.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaDestinoCiz3.toString(), "ALTAS PATRONALES CIZ3", observaciones, "DESPUES", false);
	}

	private void crearBitacoraRespaldoSindo(MigracionArchivoProperties prop,
			String rutaRespaldoSindo, String strFechaEjecucion)
			throws IOException {
		log.debug("---------------> Generacion de Bitacoras (Respaldo) <---------------");
		String nombreArchivoCiz1 = prop.getNombreArchivoSindoCiz1();
		String nombreArchivoCiz2 = prop.getNombreArchivoSindoCiz2();
		String nombreArchivoCiz3 = prop.getNombreArchivoSindoCiz3();

		StringBuffer sbRutaDirectorioRespaldo = new StringBuffer();
		sbRutaDirectorioRespaldo.append(rutaRespaldoSindo).append("/").append(strFechaEjecucion);

		// Se crea carpeta del log en caso de no existir
		crearDirectorio(new File(prop.getRutaFilesysBitacora()));

		// Se crea la carpeta donde se escribira el log en caso de no existir
		crearDirectorio(new File(prop.getRutaActualBitacora()));

		StringBuffer sbRutaRespaldoCiz1 = new StringBuffer();
		sbRutaRespaldoCiz1.append(sbRutaDirectorioRespaldo).append("/").append(nombreArchivoCiz1);
		
		StringBuffer sbRutaRespaldoCiz2 = new StringBuffer();
		sbRutaRespaldoCiz2.append(sbRutaDirectorioRespaldo).append("/").append(nombreArchivoCiz2);
		
		StringBuffer sbRutaRespaldoCiz3 = new StringBuffer();
		sbRutaRespaldoCiz3.append(sbRutaDirectorioRespaldo).append("/").append(nombreArchivoCiz3);

		// Se cuenta numero de lineas del archivo temporal
		String observaciones = clasificarMovimientos(sbRutaRespaldoCiz1.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaRespaldoCiz1.toString(), "ALTAS PATRONALES CIZ1", observaciones, "RESPALDO", true);

		// Se cuenta numero de lineas del archivo temporal
		observaciones = clasificarMovimientos(sbRutaRespaldoCiz2.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaRespaldoCiz2.toString(), "ALTAS PATRONALES CIZ2", observaciones, "RESPALDO", false);

		// Se cuenta numero de lineas del archivo temporal
		observaciones = clasificarMovimientos(sbRutaRespaldoCiz3.toString());
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(),
				sbRutaRespaldoCiz3.toString(), "ALTAS PATRONALES CIZ3", observaciones, "RESPALDO", false);
	}
}
