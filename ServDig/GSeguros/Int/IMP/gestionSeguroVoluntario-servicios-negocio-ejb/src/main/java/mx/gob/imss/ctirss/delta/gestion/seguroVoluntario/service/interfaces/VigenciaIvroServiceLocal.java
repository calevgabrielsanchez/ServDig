/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.Date;
import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.PeriodoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import javax.persistence.NoResultException;

/**
 * Interfaz para el manejo de las vigencias de los segruos y periodos de 
 * renovacion
 * @author NOVUTECK1
 *
 */
@Local
public interface VigenciaIvroServiceLocal {

    /**
     * Obtiene un periodo valido, para generar una cotizacion de seguro, 
     * además de indicar si el periodo es nuevo o se trata de una renovacion
     * 
     * - Si el trabajador tiene beneficio su fecha limite es el 25 del mes anterior 
     *   al inicio de periodo, en caso contrario el el ultimo dia habil 
     *   del mes anterior a iniciar el seguro
     * - Si se trata de una renovacion la fecha limite puede estar dentro del periodo del seguro
     * - Si no es renovacion y la fecha limite de pago es menor a la fecha actual el periodo se mueve un mes mas
     * 
     * @param persona <code>Persona</code> Persona fisica a la cual se verifican sus vigencias
     * @return el objeto <code>PeriodoSeguro</code> que contiene las vechas de inicio y fin del periodo 
     * a cotizar asi como si se trata de una renovacion.
     * @throws IvroException Errores al obtener los periodos de renovacion
     */
    PeriodoSeguro obtenPeriodoSeguroIndividual(Fisica persona) throws IvroException;
    
    /**
     * Obtiene el periodo de vigencia para un seguro 
     * @param persona el patron  al cal hay que obtener las fechas de vigencia de un seguro nuevo
     * @return El periodo del seguro nuevo
     * @throws IvroException Errores al obtener los datos del periodo de seguro domestico
     */
    PeriodoSeguro obtenPeriodoSeguroDomestico(Fisica persona) throws IvroException;
    
    PeriodoSeguro obtenerPeriodoSeguroFamiliar() throws IvroException;
    
    PeriodoSeguro obtenerPeriodoContinuacionVoluntaria() throws IvroException;
    
    /**
     * Obtiene el periodo de vigencia para un seguro a renovar
     * @param seguro el seguro a renovar
     * @return El periodo del seguro a renovar
     * @throws IvroException Errores al validar el periodo de renovacion domestico
     */
    PeriodoSeguro obtenPeriodoRenovacionDomestico(SeguroIvro seguro) throws IvroException;

    /**
     * Indica si un seguro se encuentra en fechas de renovacion.
     * @param seguro al cual se verfica la  vigencia
     * @return  true si s encuentra enperiodo de renovacion
     * @throws IvroException Errores al validar el periodo de renovacion
     */
    Boolean isPeriodoRenovacion(SeguroIvro seguro) throws IvroException;
    
    /**
     * Indica si un seguro se encuentra en fechas de renovacion.
     * @param seguro al cual se verfica la  vigencia
     * @return  true si s encuentra enperiodo de renovacion
     * @throws IvroException Errores al validar el periodo de renovacion
     */
    Boolean isPeriodoRenovacionSSF(SeguroIvro seguro) throws IvroException;
    
    /**
     * Indica si la renovación es extemporanea
     * @param seguro Seguro a Evaluar
     * @return
     * @throws IvroException
     */
    Boolean isRenovacionExtemporanea(SeguroIvro seguro) throws IvroException;
    
    /**
     * Indica si la renovación es extemporanea
     * @param seguro Seguro a Evaluar
     * @return
     * @throws IvroException
     */
    Boolean isRenovacionExtemporaneaSSF(SeguroIvro seguro) throws IvroException;
    
    Boolean aplicaRecargo(Long idModalidad);
    
    AsignacionNssIvro obtenerAsignacionNss(Long idPersona) throws NoResultException, IvroException;
    
    AsignacionNssIvro obtenerAsignacionNss(String numNss) throws NoResultException, IvroException;
    
    Date obtenerFechaFinRenovacionIvro(SeguroIvro seguro);
}
