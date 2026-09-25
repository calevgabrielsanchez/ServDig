package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.File;
import java.io.IOException;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.AbstractTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.ReseteoArchivoProperties;

import org.apache.commons.io.FileUtils;

public class ReseteoTempSindoTask extends AbstractTask {

	public void ejecutarProcesoMigracionRespaldoSindoTest() {
		System.out.println("Hello!!");
		log.info("Hello SINDO");
	}

	public void ejecutarProcesoMigracionRespaldoSindo(ReseteoArchivoProperties prop) throws IOException {
		// Se elimina carpeta de corte
		try {
			crearBitacoraMovimiento(prop);
		} catch (Exception e) {
			log.error("Error al generar archivo de diagnostico del proceso de Borrado de Archivo Temporal de SINDO");
			e.printStackTrace();
		}

		resetearArchivoTemporal(prop);
	}

	private void resetearArchivoTemporal(ReseteoArchivoProperties prop) throws IOException {
		// Ruta del archivo Temporal a borrar
		File archivoOrigen = new File(prop.getRutaCompletaTempSindo());

		// Ruta de respaldo
		crearDirectorio(new File(prop.getRutaBitacoraSindo()));

		StringBuffer sbRutaBitSindo = new StringBuffer();
		sbRutaBitSindo.append(prop.getRutaBitacoraSindo()).append("/")
				.append(prop.getNombreArchivoSindo());
		File archivoDestino = new File(sbRutaBitSindo.toString());

		if (archivoOrigen.exists()) {
			FileUtils.moveFile(archivoOrigen, archivoDestino);
		} else {
			log.error("No existe ningun archivo para borrar");
		}
	}

	private void crearBitacoraMovimiento(ReseteoArchivoProperties prop) throws IOException {
		// Se crea carpeta del log en caso de no existir
		crearDirectorio(new File(prop.getRutaFilesysBitacora()));

		// Se crea la carpeta donde se escribira el log en caso de no existir
		crearDirectorio(new File(prop.getRutaActualBitacora()));

		// Se cuenta numero de lineas del archivo temporal
		escribirResultadoDiagnostico(prop.getRutaCompletaBitacora(), prop.getRutaCompletaTempSindo(), "SINDO");
	}
}
