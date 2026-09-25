/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.business;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business.GeneraLineaCapturaBusiness;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.Pagos;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de prueba pars el cliente ws del motor de calculo
 * @author NOVUTECK1
 *
 */
public class GeneraLineaCapturaBusinessTest {
    
    /**
     * Looger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneraLineaCapturaBusinessTest.class);    

    /**
     * Archivo xml con los datos del calculo de cuotas
     */
    private static final String XML = "src/test/resources/CalculoCuota.xml";
    
    /**
     * Archivo xml con los datos del calculo de cuotas
     */
    private static final String XML_RECARGO = "src/test/resources/CalculoCuotaRecargo.xml";
    /**
     * Archivo de prueba con los datos de calculo cuotas con descuento
     */
    private static final String XML_DESCUENTO = "src/test/resources/CalculoCuotaDescuento.xml";
    /**
     * Archivo con multiples trabjadores
     */
    private static final String XML_MULTIPLE = "src/test/resources/CalculoCuotaMultipleTrabajadores.xml";
    /**
     * Url deñ webservice en mi local
     */
    private static final String URL_SERVICIO = "http://localhost:7001/GestionSUA/GeneraLCPagos";
    
    
    @Test(expected = SUAException.class)
    public void testProcesoClienteMalUrl() throws Exception {
        GeneraLineaCapturaBusiness proceso = new GeneraLineaCapturaBusiness();
        proceso.generaLineasCaptura(new Pago[]{}, "url:mal");
    }
    
    @Test(expected = SUAException.class)
    public void testProcesoClienteNoUrl() throws Exception {
        GeneraLineaCapturaBusiness proceso = new GeneraLineaCapturaBusiness();
        proceso.generaLineasCaptura(new Pago[]{}, null);
    }
    
    @Test
    @Ignore
    public void testProcesoCliente() throws Exception {
        GeneraLineaCapturaBusiness proceso = new GeneraLineaCapturaBusiness();
        Pagos pagos = JaxbUtilT.unmarshaller(XML, Pagos.class);
        Pago[] lineas = proceso.generaLineasCaptura(pagos.getPago(), URL_SERVICIO);
        Pagos pagosObt = new Pagos();
        pagosObt.setPago(lineas);
        LOGGER.debug("Respuesta del servicio  {}", JaxbUtilT.marshaller(pagosObt));
        for(Pago linea : lineas) {
            String fileName  = "target/"+ linea.getLineaCaptura()+".pdf";
            FileOutputStream outputStream = new FileOutputStream(new File(fileName), true);
            outputStream.write(linea.getPdf());
            outputStream.close();
        }
    }
}
