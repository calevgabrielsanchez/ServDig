package mx.gob.imss.cit.gestion.solicitud.flujo.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaYaAsignadaAlUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.EstadoTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Tarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoCDALocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoCDARemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.utility.FlujoTrabajoUtilityLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Alex Peña
 * 
 */

@Stateless(name = "flujoTrabajoCDABusiness", mappedName = "flujoTrabajoCDABusiness")
public class FlujoTrabajoCDABusiness implements FlujoTrabajoCDARemote {

	private static final Logger LOGGERBPM = LoggerFactory
			.getLogger(FlujoTrabajoCDABusiness.class);

	private static final long TAREA_INICIAL = 1;

	@EJB
	private FlujoTrabajoCDALocal flujoTrabajoCDAEntity;

	@EJB
	private FlujoTrabajoLocal flujoTrabajoEntity;

	@EJB
	private FlujoTrabajoUtilityLocal flujoTrabajoUtility;

	@Override
	public Long obtenerCveTareaUsuarioCDA(Long idInstancia, String Curp)
			throws NoExisteTareaUsuarioException {
		LOGGERBPM.info("obtenerCveTareaUsuarioCDA:::: ");
		List<TareaUsuario> cveTarea = flujoTrabajoCDAEntity
				.buscarTareaUsuarioByCurpInst(idInstancia, Curp);

		if (cveTarea != null && cveTarea.size() > 0) {
			LOGGERBPM.info("obtenemos cveIdTareaUsuario:::: ");
			return cveTarea.get(0).getIdTareaUsuario();

		} else {

			throw new NoExisteTareaUsuarioException();

		}

	}

	@Override
	public Long obtenerCveInstancia(Long idTramite) {

		Instancia instancia = flujoTrabajoEntity
				.buscarInstanciaByTramite(idTramite);

		return instancia.getBpId();
	}

	public void reasignarTareaCDA(final String usuario, final Long idTarea,
			MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException,
			EstadoTareaUsuarioNoValidoException,
			TareaYaAsignadaAlUsuarioException {
		LOGGERBPM.info("reasignarTarea usuario [{}], idTarea [{}]", usuario,
				idTarea);
		TareaUsuario tareaUsuario = flujoTrabajoEntity
				.buscarTareaUsuarioById(idTarea);
		flujoTrabajoUtility.validarTareaUsuarioReasignar(tareaUsuario, usuario);
		TareaUsuario tareaAnterior = tareaUsuario;
		flujoTrabajoUtility.prepararTareaReasignada(tareaUsuario);

		LOGGERBPM.info("se actualiza el estado a la tarea actual");
		flujoTrabajoUtility.prepararbDocAnterior(tareaAnterior, usuario);
		flujoTrabajoEntity.guardarTareaUsuario(tareaAnterior);

		Tarea tarea = tareaUsuario.getTarea();
		if (tarea.getIdTarea() != TAREA_INICIAL) {
			tarea = flujoTrabajoEntity.obtenerTareaPorId(TAREA_INICIAL);
		}

		LOGGERBPM.info("se cambiar la asignacion al nuevo usuario");
		Asignacion asignacion = flujoTrabajoUtility
				.prepararCambioUsuarioAsignacion(tareaUsuario.getInstancia()
						.getBpId(), tarea.getParticipante()
						.getIdParticipante(), usuario);
		flujoTrabajoEntity.guardarAsignacion(asignacion);

		LOGGERBPM.info("se asigna tarea usuario al nuevo usuario");
		TareaUsuario tareaUsuarioAsignar = flujoTrabajoUtility
				.crearTareaReasignarUsuario(tareaUsuario.getInstancia(),
						tarea, usuario, mensajeTarea);
		flujoTrabajoEntity.guardarTareaUsuario(tareaUsuarioAsignar);

		LOGGERBPM.info("Se actualizan los atributos del bdoc");

		flujoTrabajoUtility.actualizarDatosBDocInstancia(tareaUsuario,
				mensajeTarea);
	}

}
