/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NSSYaExistenteException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SerieNoExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

/**
 * @author vanderluk
 * 
 */
@Remote
public interface SerieServiceBusinessRemote {
	/**
	 * 
	 * @param delegacion
	 * @param subdelegacion
	 * @return
	 * @throws SeriesNoLocalizadasException
	 */
	List<AsignacionSerieNSS> obtenerSeriesActivas(Long delegacion,
			Long subdelegacion) throws SeriesNoLocalizadasException;

	/**
	 * @param delegacion
	 * @param subdelegacion
	 * @param anioRegistro
	 * @param idTipoSerie
	 * @return
	 */
	List<AsignacionSerieNSS> obtenerSeriesActivas(Long delegacion,
			Long subdelegacion, Long anioRegistro, Integer idTipoSerie);

	/**
	 * 
	 * @param serie
	 * @return
	 * @throws SerieNoExisteException
	 */
	AsignacionSerieNSS obtenerDetalleDeSerie(Serie serie)
			throws SerieNoExisteException;

	/**
	 * Metodo para generar y asignar el NSS a una persona fisica.
	 * 
	 * 
	 * @param asignacion
	 *            <AsignacionSerieNSS> Datos de la Serie (Tipo Serie,
	 *            Delegacion, Subdelegacion).
	 * @param asegurado
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 * @throws NSSYaExistenteException 
	 * @throws SerieNssAgotadaException
	 */
	AsignacionNSS asignarNSS(AsignacionSerieNSS asignacion, Fisica asegurado)
			throws NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException, NSSYaExistenteException, SerieNssAgotadaException;

	/**
	 * @param asegurado persona a asignarse
	 * @param sujetoObligado Registro Ptronal a asciarse
	 * @return Asegurado asignado
	 * @throws PersonaNoEncontradaException 
	 * @throws Exception
	 */
	Asegurado generarAsegurado(Fisica asegurado, SujetoObligado sujetoObligado)
			throws Exception;

	/**
	 * @param asegurado persona a asignarse
	 * @return AsignacionNSS asignacionGenerada
	 * @throws PersonaNoEncontradaException 
	 * @throws Exception
	 */
	AsignacionNSS registrarAsegurado(Fisica asegurado,
			AsignacionSerieNSS asignacionNSS, TramiteAsegurado tramite)
			throws ArgumentosInvalidosException, DomicilioNoValidoException,
			ClienteWebserviceSatRfcException,
			ClienteWebserviceRenapoCurpException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			PersonaNoEncontradaException;

	/**
	 * 
	 * @param asignacionSerie
	 * @return
	 * @throws NumeroDeSeriePorAnioRegistroExisteException
	 * @throws ErrorGuardarSerieException
	 * @throws ErrorCrearFoliosDeSerieException
	 * @throws ErrorAsignarSerieException
	 */
	Serie crearSerie(AsignacionSerieNSS asignacionSerie)
			throws NumeroDeSeriePorAnioRegistroExisteException,
			ErrorGuardarSerieException, ErrorCrearFoliosDeSerieException,
			ErrorAsignarSerieException;

	/**
	 * Servicio para crear la trama con la informaci&oacute;n del asegurado
	 * para escribir en el archivo para CANASE
	 * 
	 * @param asegurado
	 * @param serie
	 * @return
	 */
	String generarXmlMovAsignacionNSS(TramiteAsegurado tramiteAsegurado, boolean encolarMovimiento);
	
	
	/**
	 * Metodo encargado de calcular un NSS validando que no exista en BDTU
	 * @param asignacion
	 * @param asegurado
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 * @throws NSSYaExistenteException
	 * @throws SerieNssAgotadaException
	 */
	String  calculaNSS(AsignacionSerieNSS asignacion,
			Fisica asegurado) throws NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			NSSYaExistenteException, SerieNssAgotadaException;
	/**
	 * Metodo encargado de guardar un DitAsignacionNSS y generar el movimiento a SINDO
	 * @param asegurado
	 * @return
	 * @throws Exception
	 */
	AsignacionNSS guardaAseguradoConNSS(Fisica asegurado,  Serie serie)
			throws Exception;
			
	
}
