package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.sie.task;

import java.io.IOException;

import org.apache.commons.fileupload.FileUploadException;

public interface MovimientoSieTask {

	void ejecutarProcesoMigracionRespaldoSieTest();

	void ejecutarProcesoMigracionRespaldoSie() throws IOException,
			FileUploadException;

}