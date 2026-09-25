/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.Date;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.PeriodoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;


import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import javax.persistence.NoResultException;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;

/**
 * Servicio para el calculo de periodos de vigencia y vigencia de seguros.
 * @author NOVUTECK1
 *
 */
@Remote
public interface VigenciaIvroServiceRemote {

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
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException
     */
    PeriodoSeguro obtenPeriodoSeguroIndividual(Fisica persona) throws IvroException;
    
    /**
     * Obtiene el periodo de vigencia para un seguro 
     * @param persona el patron  al cal hay que obtener las fechas de vigencia de un seguro nuevo
     * @return El periodo del seguro nuevo
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException
     */
    PeriodoSeguro obtenPeriodoSeguroDomestico(Fisica persona) throws IvroException;
    
    /**
     * Obtiene el periodo de vigencia para un seguro a renovar
     * @param seguro el seguro a renovar
     * @return El periodo del seguro a renovar
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException
     */
    PeriodoSeguro obtenPeriodoRenovacionDomestico(SeguroIvro seguro) throws IvroException;
    
    /**
     * Indica si un seguro se encuentra en fechas de renovacion.
     * @param seguro al cual se verfica la  vigencia
     * @return  true si s encuentra enperiodo de renovacion
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException
     */
    Boolean isPeriodoRenovacion(SeguroIvro seguro) throws IvroException;
    

    /**
     * Obtiene Asignacion NSS Cvro a renovar
     * @param idPersona
     * @return AsignacionNSS
     * @throws java.lang.Exception
     */
    AsignacionNssIvro obtenerAsignacionNss(Long idPersona) throws NoResultException, Exception;
    
    /**
     *
     * @param numNss
     * @return
     * @throws NoResultException
     * @throws Exception
     */
    AsignacionNssIvro obtenerAsignacionNss(String numNss) throws NoResultException, Exception;

    RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(VigenciaSeguroFamiliar vigenciaSeguroFamiliar);

    Date obtenerFechaFinRenovacionIvro(SeguroIvro seguro);
    
    RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(VigenciaTrabajdor vigenciaTrabajdor);
}
