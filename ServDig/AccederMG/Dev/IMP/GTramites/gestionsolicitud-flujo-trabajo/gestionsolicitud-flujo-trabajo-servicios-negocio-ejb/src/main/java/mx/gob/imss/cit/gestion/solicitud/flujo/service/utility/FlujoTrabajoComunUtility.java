package mx.gob.imss.cit.gestion.solicitud.flujo.service.utility;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Participante;

@Stateless(mappedName = "flujoTrabajoComunUtility", name = "flujoTrabajoComunUtility")
public class FlujoTrabajoComunUtility implements FlujoTrabajoComunUtilityLocal {

	/**
	 * Metodo para llenar la asignacion
	 * 
	 * @param asignaciones
	 * @param bpId
	 * @param idParticipante
	 * @param usuario
	 */
	public void llenarAsignacion(List<Asignacion> asignaciones, final Long bpId, final Long idParticipante,
			String usuario) {
		if (usuario == null || usuario.isEmpty()) {
			usuario = "bpmadmin";
		}
		asignaciones.add(llenarAsignacion(bpId, idParticipante, usuario));
	}

	public Asignacion llenarAsignacion(final Long bpId, final Long idParticipante, String usuario) {
		Asignacion asignacion = new Asignacion();
		asignacion.setInstancia(new Instancia());
		asignacion.setParticipante(new Participante());
		asignacion.getInstancia().setBpId(bpId);
		asignacion.getParticipante().setIdParticipante((idParticipante));
		asignacion.setUsuario(usuario);
		return asignacion;
	}

	/**
	 * Metodo para llenar la asignacion
	 * 
	 * @param bpId
	 * @param idParticipante
	 * @return
	 */
	public Asignacion llenarAsignacion(final Long bpId, final Long idParticipante) {
		Asignacion asignacion = new Asignacion();
		asignacion.setInstancia(new Instancia());
		asignacion.setParticipante(new Participante());
		asignacion.getInstancia().setBpId(bpId);
		asignacion.getParticipante().setIdParticipante((idParticipante));
		return asignacion;
	}

	/**
	 * Metodo para llenar la asignacion
	 * 
	 * @param bpId
	 * @param idParticipante
	 * @return
	 */
	public Asignacion llenarAsignacion(final Long bpId, final String rol) {
		Asignacion asignacion = new Asignacion();
		asignacion.setInstancia(new Instancia());
		asignacion.setParticipante(new Participante());
		asignacion.getInstancia().setBpId(bpId);
		asignacion.getParticipante().setNombre((rol));
		return asignacion;
	}

}
