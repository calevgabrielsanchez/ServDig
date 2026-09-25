/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ComprobanteSeguroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ComprobanteSeguroRemote;
import mx.gob.imss.digital.modelo.seguros.ComprobantesSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

/**
 * Servicio para obtener los comprobantes (pdf )de seguro Ivro
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "comprobanteSeguroBusiness", mappedName = "comprobanteSeguroBusiness")
public class ComprobanteSeguroBusiness implements ComprobanteSeguroRemote {

    /**
     * Servicio para generar los comprobantes
     */
    @EJB
    private ComprobanteSeguroLocal comprobanteSeguroLocal;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ComprobanteSeguroRemote
     * #generaDatosComprobante(mx.gob.imss.digital.modelo.seguros.SegurosIvro)
     */
    @Override
    public ComprobantesSeguroReporte generaDatosComprobante(SegurosIvro seguros) {
        return comprobanteSeguroLocal.generaDatosComprobante(seguros);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ComprobanteSeguroRemote
     * #generaComprobantes(mx.gob.imss.digital.modelo.seguros.SegurosIvro)
     */
    @Override
    public DocumentoSeguro generaComprobantes(SegurosIvro seguros) {
        return comprobanteSeguroLocal.generaComprobantes(seguros);
    }
}
