/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.DatosMovSeguro;
import mx.gob.imss.digital.modelo.sindo.MovimientoTrabajadorSindo;
import mx.gob.imss.digital.modelo.sindo.MovimientosTrabajadorSindo;

/**
 * Definiciion de los servicio pra general los datos de un movimiento de alta o baja de un trabajdor en sindo
 * @author NOVUTECK1
 *
 */
@Local
public interface GeneradorMovimientoLocal {

    /**
     * Genera la lista de movimientos de alta asociados los seguros activados 
     * @param datos los datos para poder generar los movimientos de alta
     * @return la lista de movimientos;
     * @throws IvroException errores al generar los movimientos de alta
     */
    MovimientosTrabajadorSindo generaMovimientosAlta(List<DatosMovSeguro> datos) throws IvroException;
    
    /**
     * Genera la lista de movimientos de baja asociados a seguros terminados o vencidos
     * @param datos los datos para generar los movimientos
     * @return la lista de movimientos que causan baja
     * @throws IvroException errores al generar los movimietos de baja
     */
    List<MovimientoTrabajadorSindo> generaMovimientosBaja(List<DatosMovSeguro> datos) throws IvroException;
    
}
