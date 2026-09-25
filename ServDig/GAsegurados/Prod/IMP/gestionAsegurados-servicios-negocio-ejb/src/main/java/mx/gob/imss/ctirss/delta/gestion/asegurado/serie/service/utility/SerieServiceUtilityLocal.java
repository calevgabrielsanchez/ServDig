/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility;

import java.util.Date;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.persistence.DicSeriesNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionSerie;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface SerieServiceUtilityLocal {
	
	
	
	/**
	 * Transforma de un objeto tipo entity de la Serie 
	 * a un objeto de tipo modelo Serie.
	 * @param entity
	 * @return
	 */
	Serie transformarSerieModelo( DicSeriesNss entity );
	
	
	/**
	 * Transforma el objeto de la capa de modelo al de entity.
	 * @param modelo
	 * @return
	 */
	DicSeriesNss transformarSerieEntity( Serie modelo);
	
	
	
	
	
	
	
	/**
	 * Obtiene un numero Long de las ultimos dos digitos 
	 * del a–o de la fecha proporcionada.
	 * @param fecha
	 * @return
	 */
	Long obtenerDosDigitosDeAnio(Date fecha);

	
	/**
	 * Metodo que genera el NSS con el digito verificador.
	 * @param serie
	 * @return
	 */
	String generaNSS(Serie serie);
	
	
	
	/**
	 * Metodo para determinar el Nivel de Asignacion de la serie.
	 * 
	 * @param asignacionSerie 1: Nivel Subdelegacion , 2: Nivel Delegacion, 3: Nivel Centrarl / general
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException : En caso de no recibir los parametros adecuados.
	 */
	Integer getNivelAsignacion(AsignacionSerieNSS asignacionSerie)
			throws NivelDeAsignacionSerieIndefinidoException;

	AsignacionSerieNSS transformarAsignacionNSSModelo(DitAsignacionSerie ditAsignacionSerie);

	DitAsignacionSerie transformarAsignacionNSSEntity(AsignacionSerieNSS modelo);
}
