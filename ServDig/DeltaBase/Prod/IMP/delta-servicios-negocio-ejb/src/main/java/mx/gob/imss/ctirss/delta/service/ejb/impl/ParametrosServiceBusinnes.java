/**
 * 
 */
package mx.gob.imss.ctirss.delta.service.ejb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.persistence.DicParametros;
import mx.gob.imss.ctirss.delta.service.interfaces.ParametrosServiceRemote;

/**
 * Servicio para el manejo de los parametros desde la BD
 * @author NOVUTECK1
 *
 */
@Stateless(name = "parametrosServiceComun", mappedName = "parametrosServiceComun")
public class ParametrosServiceBusinnes implements ParametrosServiceRemote {

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;
    
    /**
     * Otiene el valor de un parametro por su llave
     * @param llave la llave del paramero
     * @return El valor de la llave encontrado
     */
    public String obtenValorPorLlave(String llave) {
        
        String sqlQuery = "select param.desValorParametro from DicParametros param where param.desLlaveParametro = :llave";
        Query query = entityManager.createQuery(sqlQuery);
        query.setParameter("llave", llave);
        String valor = null;
        try {
            valor = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            valor = null;
        }                
        return valor;
        
    }

    /**
     * Obtiene un mapa de valores "Llave, ValorParametro" con lal ista de llaves recibidas
     * @param llaves las llaves a buscar sus valores
     * @return un mapa cono las llaves y sus valores
     */
    public Map<String, String> obtenVariosParametrosPorLlave(List<String> llaves) {
        
        String sqlQuery = "select param from DicParametros param where param.desLlaveParametro in (:llaves)";        
        Query query = entityManager.createQuery(sqlQuery);
        query.setParameter("llaves", llaves);        
        List<DicParametros> parametrosTmp = query.getResultList();        
        Map<String, String> parametros = new HashMap<String, String>();       
        for (DicParametros parametro : parametrosTmp) {
            parametros.put(parametro.getDesLlaveParametro(), parametro.getDesValorParametro());
        }
        return parametros;
    }

}
