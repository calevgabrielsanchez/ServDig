/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;

/**
 * Servicios para elmanejo de tramite en ivro, asi como la generacion de compras
 * @author NOVUTECK1
 *
 */
@Local
public interface TramiteIvroServiceLocal {

    /**
     * GEnera un Seguro Ivro a partir de su tramite
     * @param tramite el tramite de seguro para generar el seguro
     * @return el seguro generado
     * @throws Errores al generar el seguro ivro
     */
    SeguroIvro generaSeguro(TramiteSeguroIvro tramite) throws IvroException;
    
    /**
     * Genera una lista de seguros a partir de su tramite y las cotizaciones a asociar
     * @param tramite el tramite del seguro
     * @param compras las compras de cada empleado
     * @return lal ista de seguros generados
     * @throws IvroException Errores al generar el seguro
     */
    List<SeguroIvro> generaSegurosDomesticos(TramiteSeguroIvro tramite, List<Compra> compras) throws IvroException;
    
    /**
     * Genera una lista de seguros a partir de su tramite y las cotizaciones a asociar
     * @param tramite el tramite del seguro
     * @param compras las compras de cada empleado
     * @return lal ista de seguros generados
     * @throws IvroException Errores al generar el seguro
     */
    List<SeguroIvro> generaSegurosFamiliares(TramiteSeguroIvro tramite, List<Compra> compras) throws IvroException;

    /**
     * Obtiene la umf asociada de una persona
     * @param nss de la persona 
     * @param id de la persona
     * @return grupo familiar
     */
    UnidadMedicaFamiliar getUnidadMedicoFamiliarByAsignacion(String numNss, Long cveIdPersona);

    SujetoObligado consultarPorRegistroPatronalBasic(String registroPatronal);
}
