/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

/**
 * Clase utilitaria para operar ramas de calculo
 * 
 * @author NOVUTECK1
 * 
 */
public abstract class RamaCalculoUtil {

    /**
     * Suma las cuotas de calculos de diferentes periodos de tiempo
     * 
     * @param cuotasCalculadas
     *            las cuotas que guardaran el valor calculado
     * @param cuotasNuevas
     *            los valores de las cuotas a calcular
     */
    public static final void sumaCuotas(List<RamaCalculo> cuotasCalculadas,
            List<RamaCalculo> cuotasNuevas) {
        for (RamaCalculo cuotaC : cuotasCalculadas) {
            for (RamaCalculo cuotaN : cuotasNuevas) {
                if (cuotaC.getIdRama().equals(cuotaN.getIdRama())
                        && cuotaC.getIdTipoAportacion().equals(cuotaN.getIdTipoAportacion())) {
                    cuotaC.setAportacion(cuotaC.getAportacion().add(cuotaN.getAportacion()));
                }
            }
        }
    }

    /**
     * Copia los valores de una lista de ramas a una nueva
     * 
     * @param ramas
     *            la lista de ramas a ser copiada
     * @return una copia de la lista enviada
     */
    public static final List<RamaCalculo> copiaRamas(List<RamaCalculo> ramas) {
        List<RamaCalculo> ramasNuevas = new ArrayList<RamaCalculo>();
        for (RamaCalculo rama : ramas) {
            ramasNuevas.add(copiaRama(rama));
        }
        return ramasNuevas;
    }

    /**
     * Genera una copia pura una rama de pago
     * 
     * @param rama
     *            la rama a ser copiada
     * @return la rama copiada
     */
    public static final RamaCalculo copiaRama(RamaCalculo rama) {
        RamaCalculo ramaNueva = new RamaCalculo();
        ramaNueva.setAportacion(rama.getAportacion());
        ramaNueva.setIdRama(rama.getIdRama());
        ramaNueva.setIdTipoAportacion(rama.getIdTipoAportacion());
        return ramaNueva;
    }

    /**
     * Indica si dos ramas de calculo indican el mismo tipo de cuota, es decir
     * son del mismo tipo de aportacion y mismo id rama
     * 
     * @param rama1
     *            la rama a compara
     * @param rama2
     *            la segunda rama a comparar
     * @return true si se trata del mismo tipo de cuota
     */
    public static final boolean mismaCuota(RamaCalculo rama1, RamaCalculo rama2) {
        return rama1.getIdRama().equals(rama2.getIdRama())
                && rama1.getIdTipoAportacion().equals(rama2.getIdTipoAportacion());
    }

}
