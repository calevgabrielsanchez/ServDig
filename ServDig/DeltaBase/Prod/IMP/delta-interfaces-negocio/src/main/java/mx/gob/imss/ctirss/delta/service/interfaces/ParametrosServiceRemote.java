/**
 * 
 */
package mx.gob.imss.ctirss.delta.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

/**
 * Servicio para el manejo de los parametros desde la BD
 * @author NOVUTECK1
 *
 */
@Remote
public interface ParametrosServiceRemote {

    /**
     * Otiene el valor de un parametro por su llave
     * @param llave la llave del paramero
     * @return El valor de la llave encontrado
     */
    String obtenValorPorLlave(String llave);

    /**
     * Obtiene un mapa de valores "Llave, ValorParametro" con lal ista de llaves recibidas
     * @param llaves las llaves a buscar sus valores
     * @return un mapa cono las llaves y sus valores
     */
    Map<String, String> obtenVariosParametrosPorLlave(List<String> llaves);
}
