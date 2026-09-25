package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.clean.task;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.utilities.planificador.desktop.clean.utils.ReseteoArchivoProperties;

public interface ReseteoTempSindoTask {

	void ejecutarProcesoMigracionRespaldoSindoTest();

	void ejecutarProcesoMigracionRespaldoSindo(ReseteoArchivoProperties prop)
			throws IOException;

}