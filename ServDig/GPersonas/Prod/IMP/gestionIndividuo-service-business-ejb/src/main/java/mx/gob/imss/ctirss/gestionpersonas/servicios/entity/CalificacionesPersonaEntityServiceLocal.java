package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;

/**
 * 111012
 * 
 * @author ICCSRG
 * 
 */
@Local
public interface CalificacionesPersonaEntityServiceLocal {

	/**
	 * Método encargado de realizar el registro de la calificación a una persona
	 * física
	 * 
	 * @param ditHistPersonaCalificacion
	 */
	void registrar(DitHistPersonaCalificacion ditHistPersonaCalificacion);
	
	/**
	 * Método encargado de realizar el registro de la calificación a una persona
	 * moral
	 * 
	 * @param ditHistPersonaMoralCalific
	 */
	void registrar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific);

	/**
	 * Método encargado de realizar la actualizacion de la fecha de la
	 * calificación a una persona física, siempre se settea la fecha actual.
	 * 
	 * @param ditHistPersonaCalificacion
	 * @return
	 */
	void actualizar(DitHistPersonaCalificacion ditHistPersonaCalificacion);
	
	/**
	 * Método encargado de realizar la actualizacion de la fecha de la
	 * calificación a una persona moral, siempre se settea la fecha actual.
	 * 
	 * @param ditHistPersonaMoralCalific
	 * @return
	 */
	void actualizar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific);
	
	/**
	 * Método encargado de expirar la calificación a una persona física
	 * 
	 * @param ditHistPersonaCalificacion
	 * @return
	 */
	void expirar(DitHistPersonaCalificacion ditHistPersonaCalificacion);
	
	/**
	 * Método encargado de expirar la calificación a una persona moral
	 * 
	 * @param ditHistPersonaMoralCalific
	 * @return
	 */
	void expirar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific);

	/**
	 * Método que reactiva una calificación expirada, es decir, actualiza el campo
	 * FEC_REGISTRO_ACTUALIZADO y nulea la fecha de baja a una persona física
	 * 
	 * @param ditHistPersonaCalificacion
	 */
	void reactivar(DitHistPersonaCalificacion ditHistPersonaCalificacion);
	
	/**
	 * Método que reactiva una calificación expirada, es decir, actualiza el campo
	 * FEC_REGISTRO_ACTUALIZADO y nulea la fecha de baja a una persona moral
	 * 
	 * @param ditHistPersonaMoralCalific
	 */
	void reactivar(DitHistPersonaMoralCalific ditHistPersonaMoralCalific);
	
	/**
	 * Consulta las calificaciones de una persona física, si recibe el parametro
	 * necesario, solo consultará las vigentes
	 * 
	 * @param fisica
	 * @param getSoloVigentes
	 * @return
	 */
	List<DitHistPersonaCalificacion> consultarCalificacionesPersona(
			Fisica fisica, boolean getSoloVigentes);
	
	/**
	 * Consulta las calificaciones de una persona moral, si recibe el parametro
	 * necesario, solo consultará las vigentes
	 * 
	 * @param moral
	 * @param getSoloVigentes
	 * @return
	 */
	List<DitHistPersonaMoralCalific> consultarCalificacionesPersonaMoral(
			Moral moral, boolean getSoloVigentes);
}
