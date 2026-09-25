package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.velocity.exception.VelocityException;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.CorteEstadisticoProperties;

public interface CorteEstadisticoGeneralTask {
	void realizarCorteDiaAnterior(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException;

	void realizarCorteDiaActual(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException;

	void realizarCorteRango();
}
