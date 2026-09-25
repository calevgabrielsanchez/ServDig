package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;



@Local
public interface RazonResultadoEntityLocal {

	/**
	 * Obtiene todas las razones del resultado de solicitudes
	 * 
	 * @return Lista de razones
	 */
	public List<RazonResultado> obtenerRazones();

	
	/**
	 * Obtiene las razones para los id's proporcionados
	 * 
	 * @param idRazones Lista de id's de razon
	 * @return Lista de razones
	 */
	public List<RazonResultado> obtenerRazones(List<Long> idRazones) throws Exception;

}

