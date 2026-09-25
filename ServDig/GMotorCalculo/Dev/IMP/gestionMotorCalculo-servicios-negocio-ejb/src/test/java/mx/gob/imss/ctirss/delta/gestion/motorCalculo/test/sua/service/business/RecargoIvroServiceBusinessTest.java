/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.business;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.RecargoIvroServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;

import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prueba unitaria para la generacion de recargos
 * @author NOVUTECK1
 *
 */
public class RecargoIvroServiceBusinessTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RecargoIvroServiceBusinessTest.class);
    /**
     * Archivo xml con los datos del calculo de cuotas
     */
    private static final String XML_RECARGO = "src/test/resources/CalculoCuotaRecargo.xml";
    /**
     * PRueba DUmmy de recargos hasta que se definan correctamente
     * @throws Exception
     */
    @Ignore
    public void testGeneraRecargos() throws Exception {
        RecargoServiceLocal recargos = new RecargoIvroServiceEntity();
        CalculoCuota calculoCuota = JaxbUtilT.unmarshaller(XML_RECARGO, CalculoCuota.class);
        CalculoCuota calculo = recargos.generaRecargos(calculoCuota);
        LOGGER.debug("Calculo de recargos {}", JaxbUtilT.marshaller(calculo));
    }

}
