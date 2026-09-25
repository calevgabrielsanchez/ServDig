/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizadorEntityLocal;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Implementacion de los servicio para el manejo de cotizaciones
 * @author NOVUTECK1
 *
 */
@Stateless(name = "cotizacionServiceBusiness", mappedName = "cotizacionServiceBusiness")
public class CotizacionServiceBusiness implements CotizacionServiceRemote {
    /**
     * Servicio de cotizacion
     */
    @EJB
    private CotizadorEntityLocal cotizadorEntityLocal;

    /**
     * Busca las cotizacion asociada al id ingresado como parametro
     * @param cveIdCotizacion Id de la cotizacion persitente
     * @return LA cotizacion encontrada
     * @throws SUAException Error al no encontrar una cotizacion
     */
    public Cotizacion findCotizacion(long cveIdCotizacion) throws SUAException {
        return cotizadorEntityLocal.findCotizacion(cveIdCotizacion);
    }
    
    /**
     * Guarda una cotizacion en la BD a partir de los datos de la cotizacion
     * @param cotizacion los datos de la cotizacion a ser guardados
     * @return la cotizacion ya persistida
     * @throws SUAException Error al guardar la cotizacion
     */
    public Cotizacion guardaCotizacion(Cotizacion cotizacion) throws SUAException {
        return cotizadorEntityLocal.guardaCotizacion(cotizacion);
    }
    
    @Override
    public Cotizacion actualizaCotizacion(Cotizacion cotizacion) throws SUAException {
        return cotizadorEntityLocal.actualizaCotizacion(cotizacion);
    }

}
