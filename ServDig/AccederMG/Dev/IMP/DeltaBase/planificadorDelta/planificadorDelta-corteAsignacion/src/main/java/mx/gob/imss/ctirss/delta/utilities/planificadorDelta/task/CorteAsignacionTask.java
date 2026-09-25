package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.velocity.exception.VelocityException;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.enums.TipoCorteAsignacionEnum;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.CorteGeneralAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.CorteEstadisticoProperties;

public interface CorteAsignacionTask {
	void realizarCorteRango() throws IOException, ParseException;

	CorteGeneralAsignacion realizarCorteGeneral(Date fechaCorte);

	void enviarCorreoCorte(CorteEstadisticoProperties prop,
			CorteGeneralAsignacion corteGeneralAsignacion,
			TipoCorteAsignacionEnum tipoCorteAsignacion)
			throws VelocityException, MessagingException, IOException;
}
