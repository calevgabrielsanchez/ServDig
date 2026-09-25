package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.enums.TipoCorteAsignacionEnum;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.CorteGeneralAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils.CorteEstadisticoProperties;

import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;

public class CorteEstadisticoGeneralTaskImpl implements CorteEstadisticoGeneralTask {
	@Autowired
	private CorteAsignacionTask corteAsignacionTask;

	@Override
	public void realizarCorteDiaAnterior(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException {
		// Corte de Asignacion (Dia anterior)
		Date fechaCorte = prop.getFechaCorteGeneral();
		CorteGeneralAsignacion corteGeneralAsignacion = corteAsignacionTask
				.realizarCorteGeneral(fechaCorte);
		corteAsignacionTask.enviarCorreoCorte(prop, corteGeneralAsignacion,
				TipoCorteAsignacionEnum.CORTE_TOTAL_DIA_ANTERIOR);
	}

	@Override
	public void realizarCorteDiaActual(CorteEstadisticoProperties prop)
			throws VelocityException, MessagingException, IOException {
		// Corte de Asignacion (Dia actual)
		Date fechaCorte = prop.getFechaCorteGeneral();
		CorteGeneralAsignacion corteGeneralAsignacion = corteAsignacionTask
				.realizarCorteGeneral(fechaCorte);
		corteAsignacionTask.enviarCorreoCorte(prop, corteGeneralAsignacion,
				TipoCorteAsignacionEnum.CORTE_PARCIAL_DIA_ACTUAL);
	}

	@Override
	public void realizarCorteRango() {
		
	}
}
