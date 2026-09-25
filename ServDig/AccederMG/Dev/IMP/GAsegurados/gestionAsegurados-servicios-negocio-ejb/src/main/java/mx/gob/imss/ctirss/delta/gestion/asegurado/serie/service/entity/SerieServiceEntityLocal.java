/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface SerieServiceEntityLocal {
	/**
	 * Consulta las series activas que esten disponibles dependiendo de los
	 * parametros recibidos.
	 * 
	 * 
	 * @param delegacion
	 *            Si el dato de Delegacion es diferente de nulo o vacio y el
	 *            dato de la Subdelegaci—n es nulo o vacio,entonces obtendra las
	 *            Series activas asociadas unicamente a la Delegaci—n.
	 * @param subdelegacion
	 *            Si el dato de la Subdelegaci—n es diferente de nulo o vacio,
	 *            entonces obtendra las Series activas asociadas a la
	 *            Subdelegacion.
	 *            
	 * @param Anio anioRegistro
	 * 			
	 * 			  Anio de registro de las Series que se desean obtener.
	 * 
	 *            Si los dos parametros son nulos entonces obtendra las Series
	 *            que sean del caracter General que se encuentren activas
	 * @return Lista de Series que se encuentren activas.
	 */
	List<AsignacionSerieNSS> consultarSeriesActivas(Long delegacion, Long subdelegacion,
			Long anioRegistro, Integer idTipoSerie);

	/**
	 * Obtiene el detalle de la Serie.
	 * @param serie
	 * @return
	 */
	AsignacionSerieNSS consultarDetalleSerie(Serie serie);

	/**
	 * Obtiene la Serie (Numero de Serie) que sea del tipo de serie 
	 * solicitado y que se encuentre asignado a la delegacion / subdelegacion o del
	 * ambito general.
	 * 
	 * @param asingacionSerie
	 * @return
	 */
	Serie consultarSerieAsignadaPorTipoDeSerie(
			AsignacionSerieNSS asingacionSerie);

	/**
	 * Obtiene el Folio <AsignacionSerieNSS> correspondiente al tipo de serie y 
	 * al a–o de nacimiento, y que corresponda a la Asignacion del usuario.
	 * 
	 * @param serie <AsignacionSerieNSS> Contiene la Serie y la Asignacion de la misma, esto es contiene los valores de la Delegacion , Subdelegacion.
	 * @param anioNacimiento <Long> Anio de nacimiento del asegurado.
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException 
	 */
	Serie getFolioDeAnioNacimientoSerie(AsignacionSerieNSS serie,
			Long anioNacimiento)
			throws NivelDeAsignacionSerieIndefinidoException;

	/**
	 * Incrementa el Folio del Anio de Nacimiento de la Serie.
	 * @param serie <Serie> Contiene los datos del folio, y del anio de nacimiento.
	 * @param anioNacimiento
	 * @return
	 * @throws SerieNssAgotadaException
	 */
	Long incrementaFolioDeSerie(Serie serie, Long anioNacimiento)
			throws SerieNssAgotadaException;

	/**
	 * 
	 * @param serieAnterior <AsignacionSerieNSS> Datos de la serie anterior, de este objeto se obtiene
	 * los datos de la asignacion de la serie, tipo de serie y numero de serie.
	 * @param anioNacimiento
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException 
	 */
	Serie getSerieInactivaDeFolio(AsignacionSerieNSS serieAnterior,
			Long anioNacimiento)
			throws NivelDeAsignacionSerieIndefinidoException;

	AsignacionNSS asignarNSSAsegurado(AsignacionNSS asignacion);

	/**
	 * Guarda un nuevo registro de Serie.
	 * @param serie
	 * @return
	 * @throws ErrorGuardarSerieException En caso de ocurrir un error al momento de estar guardando la serie.
	 */
	Serie guardarSerie(Serie serie) throws ErrorGuardarSerieException;

	/**
	 * Consulta si el Numero de la Serie ya existe registrado para el a–o de registro.
	 * @param serie
	 * @return
	 * @throws NumeroDeSeriePorAnioRegistroExisteException - En caso de que ya exista el numero de serie.
	 */
	Serie consultarNumSeriePorAnioRegistro(Serie serie)
			throws NumeroDeSeriePorAnioRegistroExisteException;

	/**
	 * Crea los folios de la serie iniciando del a–o de nacimiento 0 al 99.
	 * @param serie
	 * @throws ErrorCrearFoliosDeSerieException - En caso de que ocurra un error al generar los folios.
	 */
	void crearFoliosDeSerie(Serie serie)
			throws ErrorCrearFoliosDeSerieException;

	/**
	 * Crea la asignacion de la serie con las delegacion, subdelegacion o nivel general.
	 * @param asignacionDeSerie
	 * @return
	 * @throws ErrorAsignarSerieException
	 */
	AsignacionSerieNSS asignarSerie(AsignacionSerieNSS asignacionDeSerie)
			throws ErrorAsignarSerieException;

	/**
	 * Activa una serie que se encuentra inactiva.
	 * El activar una serie/folio, es modificar las fechas de baja
	 * la fecha de baja debe ser nula.
	 * @param serie
	 * @throws ErrorAlActivarSerieException
	 */
	void activarFolioDeSerie(Serie serie) throws ErrorAlActivarSerieException;

	/**
	 * 
	 * @param serie
	 * @throws ErrorAlActivarSerieException
	 */
	void desactivarFolioDeSerie(Serie serie)
			throws ErrorAlActivarSerieException;

	/**
	 * Método que checa si existe o no un NSS
	 * 
	 * @param nss
	 * @return
	 */
	boolean existeNSS(String nss);
}
