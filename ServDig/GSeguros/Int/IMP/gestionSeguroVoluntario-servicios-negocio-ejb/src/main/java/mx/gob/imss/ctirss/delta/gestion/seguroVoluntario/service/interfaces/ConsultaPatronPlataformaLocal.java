/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.List;
import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.persistence.PptPatronPlataforma;

/**
 * Servicio para la consulta de parametros de ivro
 * @author NOVUTECK1
 *
 */
@Local
public interface ConsultaPatronPlataformaLocal {
    
    /**
     * Obtiene la lista de Patrones de Plataformas digitales con fecha de baja = null
     * 
     * @return la lista de Patrones de Plataformas digitales con fecha de baja = null
     */
    List<PptPatronPlataforma> getPttPatronPlataformaActivos() throws IvroException;

    
}
