package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;
import mx.gob.imss.ctirss.delta.model.asegurado.EstadoAsignacion;

@Remote
public interface SerieServiceWSRemote {
	/**
	 * @param asegurado persona a asignarse
	 * @param sujetoObligado Registro Ptronal a asciarse
	 * @return Asegurado asignado
	 * @throws Exception
	 */
	EstadoAsignacion generarAsegurado(AltaDatosAsignacionNSSType altaDatosAsignacionNSSType);

	/**
	 * @param numExitos Numero de personas registradas exitosamente
	 * @param numErrores Numero de personas no registradas por error
	 * @param idPublicador id del registro patronal
	 * @return resultado de la publicacion
	 */
	Boolean publicarResultadoAsignacion(Integer numExitos, Integer numErrores,
			String idPublicador);

	/**
	 * @param estado resultado de la asignacion
	 * @param idPublicador id del registro patronal
	 * @return resultado de la publicacion
	 */
	Boolean publicarDetalleResultadoAsignacion(EstadoAsignacion estado,
			String idPublicador);

	/**
	 * @param idPublicador id del registro patronal
	 * @return resultado de la publicacion
	 */
	Boolean publicarFinProcesamiento(String idPublicador);
}
