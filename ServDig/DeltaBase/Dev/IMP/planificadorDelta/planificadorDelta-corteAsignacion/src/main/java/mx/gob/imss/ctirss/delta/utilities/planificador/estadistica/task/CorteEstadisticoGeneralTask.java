package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.task;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;

import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalCanase;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalRissSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.CorteTotalSindo;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils.CorteEstadisticoProperties;

import org.apache.velocity.exception.VelocityException;

public interface CorteEstadisticoGeneralTask {
	void realizarCorteDiaAnterior(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException;

	void realizarCorteDiaActual(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException;

	void realizarCorteRango();

	CorteTotalCanase generarEstadisticaArchivoCanase(List<String> lineasArchivos);

	CorteTotalRissSindo generarEstadisticaArchivoRiss(List<String> lineasArchivos) throws ParseException;

	CorteTotalSindo generarEstadisticaArchivoSindo(List<String> lineasArchivos);
}
