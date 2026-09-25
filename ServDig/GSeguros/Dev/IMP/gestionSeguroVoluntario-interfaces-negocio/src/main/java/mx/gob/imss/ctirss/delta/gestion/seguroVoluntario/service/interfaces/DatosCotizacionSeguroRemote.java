/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import javax.ejb.Remote;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Servicio para obtener los datos iniciales de una cotizacion de seguro individual 
 * o desmistca, dada las persona o seguro anterior
 * @author NOVUTECK1
 *
 */
@Remote
public interface DatosCotizacionSeguroRemote {

    /**
     * Obtiene los datos iniciales de una cotizacion de seguro individual
     * ya sea nueva o renovacion
     * @param persona la persona a contratar el seguro
     * @return
     */
    DatosCalculoCuota datosCotizacionIndividual(Persona persona) throws IvroException;
    
    /**
     * Obtiene los datos iniciales de una cotizacion para un seguro domestico
     * a partir de los datos del patron
     * @param persona el patron a comprar el seguro
     * @return los datos de calculo 
     */
    DatosCalculoCuota datosCotizacionDomesticoCompra(Persona persona) throws IvroException;
    
    /**
     * Obtiene los datos iniciales de cotizacion para una renovacion de seguro domestico
     * @param seguro el seguro a partir del cual se va a renovar
     * @return los datos de la cotizacion a renovar
     */
    DatosCalculoCuota datosCotizacionDomesticoRenovacion(SeguroIvro seguro)  throws IvroException;
    
    /**
     * Obtiene los datos iniciales de una cotizacion de seguro familiar
     * ya sea nueva o renovacion
     * @param persona la persona a contratar el seguro
     * @return
     */
    DatosCalculoCuota datosCotizacionSeguroFamiliar(Persona persona) throws IvroException;
    
    /**
     * Obtiene los datos iniciales de una cotizacion de continuacion voluntaria
     * ya sea nueva o renovacion
     * @param persona la persona a contratar el seguro
     * @return
     */
    DatosCalculoCuota datosCotizacionContinuacionVoluntaria(Persona persona) throws IvroException;

    /**
     * Obtiene el salario mínimo dada una zona salarial
     * @param zonaSalarial La zona salarial que se usará para obtener el salario mínimo
     * @return
     */
    BigDecimal getSalarioMinimoDf(String zonaSalarial);

    /**
     * Obtiene el salario mínimo dada una zona salarial y la fecha
     * @param zonaSalarial La zona salarial que se usará para obtener el salario mínimo
     * @param fecha La fecha a utilizar
     * @return
     */
    BigDecimal getSalarioMinimoDfPorFecha(String zonaSalarial, Calendar fecha);
    
    /**
     * Obtiene la UMA a una fecha determinada
     * @param fecha
     * @return BigDecimal UMA asociado a la fecha
     */
    BigDecimal getUma(String fecha);
}
