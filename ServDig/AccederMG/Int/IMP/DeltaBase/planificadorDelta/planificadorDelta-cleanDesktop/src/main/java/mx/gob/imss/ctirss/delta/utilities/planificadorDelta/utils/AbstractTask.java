package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractTask {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private FtpUploader ftpUploader;

	protected void crearDirectorio(File directorio) throws FileNotFoundException {
		if (!directorio.exists()) {
			boolean isDirecorioCreado = directorio.mkdir();

			if (!isDirecorioCreado) {
				throw new FileNotFoundException("La ruta '" + directorio + "' no fue creada");
			}
		}
	}

	protected void escribirResultadoDiagnostico(String rutaArchivoBitacora, String rutaArchivoTemporal, String movimiento) throws IOException{
		File arhivoCanaseDescargado = new File(rutaArchivoTemporal);
		int numeroLineasArchivo = 0;
		int numeroLineasArchivoNoVacias = 0;

		if (arhivoCanaseDescargado.exists()) {
			List<String> lineasArchivos = FileUtils.readLines(arhivoCanaseDescargado);

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
		sbContenidoBitacora.append("\r\n").append("******* DATOS DEL ARCHIVO TEMPORAL ").append(movimiento).append(" (ANTES DE BORRADO) *********")
				.append("\r\n").append("Hora Ejecucion:              ").append(strFechaCorte).append("-").append(strHoraCorte)
				.append("\r\n").append("Nombre del archivo:          ").append(rutaArchivoTemporal)
				.append("\r\n").append("Existe archivo temporal? :   ").append(arhivoCanaseDescargado.exists())
				.append("\r\n").append("No. de Lineas:               ").append(numeroLineasArchivo)
				.append("\r\n").append("No de Registros (No vacios): ").append(numeroLineasArchivoNoVacias)
				.append("\r\n").append("**********************************************************************").append("\r\n");

		LayoutCallOut.generateLayout(rutaArchivoBitacora, sbContenidoBitacora.toString().getBytes());
	}
}
