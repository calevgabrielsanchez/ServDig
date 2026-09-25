package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.task;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.MigracionArchivoProperties;

import org.apache.commons.fileupload.FileUploadException;

public interface MovimientoSindoTask {

	void ejecutarProcesoMigracionRespaldoSindoTest();

	void ejecutarProcesoMigracionRespaldoSindo(MigracionArchivoProperties prop)
			throws IOException, FileUploadException;

}