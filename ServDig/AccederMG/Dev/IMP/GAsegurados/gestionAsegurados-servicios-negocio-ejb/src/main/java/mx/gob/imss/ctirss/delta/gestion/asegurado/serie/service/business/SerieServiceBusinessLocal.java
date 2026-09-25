/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SerieNoExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;

/**
 * @author vanderluk
 *
 */
@Local
public interface SerieServiceBusinessLocal {

	
	
	/**
	 * 
	 * @param delegacion
	 * @param subdelegacion
	 * @return
	 * @throws SeriesNoLocalizadasException
	 */
	List<Serie> obtenerSeriesActivas(Long delegacion, Long subdelegacion) throws SeriesNoLocalizadasException;
	
	
	
	
	/**
	 * 
	 * @param serie
	 * @return
	 * @throws SerieNoExisteException
	 */
	Serie obtenerDetalleDeSerie(Serie serie) throws SerieNoExisteException;
	
	
	
	/**
	 * Metodo para generar y asignar el NSS a una persona fisica.
	 * 
	 * 
	 * @param asignacion <AsignacionSerieNSS> Datos de la Serie (Tipo Serie, Delegacion, Subdelegacion).
	 * @param asegurado
	 * @return
	 */
	Serie asignarNSS(AsignacionSerieNSS asignacion, Fisica asegurado);
	
	
	
	/**
	 * 
	 * @param asignacionSerie
	 * @return
	 * @throws NumeroDeSeriePorAnioRegistroExisteException 
	 * @throws ErrorGuardarSerieException 
	 * @throws ErrorCrearFoliosDeSerieException 
	 * @throws ErrorAsignarSerieException 
	 */
	Serie crearSerie( AsignacionSerieNSS asignacionSerie) throws NumeroDeSeriePorAnioRegistroExisteException, ErrorGuardarSerieException, ErrorCrearFoliosDeSerieException, ErrorAsignarSerieException;
}
