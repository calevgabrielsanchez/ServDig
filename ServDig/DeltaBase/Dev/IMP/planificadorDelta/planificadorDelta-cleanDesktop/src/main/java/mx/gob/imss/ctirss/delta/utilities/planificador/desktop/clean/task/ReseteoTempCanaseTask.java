package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.clean.task;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.clean.utils.ReseteoArchivoProperties;

import org.apache.commons.fileupload.FileUploadException;

public interface ReseteoTempCanaseTask {

	void ejecutarProcesoMigracionRespaldoCanaseTest();

	void ejecutarProcesoMigracionRespaldoCanase(ReseteoArchivoProperties prop)
			throws IOException, FileUploadException;

}