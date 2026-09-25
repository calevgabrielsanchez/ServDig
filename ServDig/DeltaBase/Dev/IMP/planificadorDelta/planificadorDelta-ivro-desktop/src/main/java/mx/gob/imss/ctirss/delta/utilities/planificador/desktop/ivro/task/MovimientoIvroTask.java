package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.task;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.utils.MigracionArchivoIvroProperties;

import org.apache.commons.fileupload.FileUploadException;

public interface MovimientoIvroTask {

	void ejecutarProcesoMigracionRespaldoIvroTest();

	void ejecutarProcesoMigracionRespaldoIvro(
			MigracionArchivoIvroProperties prop) throws IOException,
			FileUploadException;

}