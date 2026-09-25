/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.business;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de prueba para el motor de calculo ws
 * 
 * @author NOVUTECK1
 * 
 */
public class MotorWsTest {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MotorWsTest.class);
    /**
     * Archivo con el xml que representa el objeto datoscalculo
     */
    private static final String XML = "src/test/resources/DatosCuota.xml";
    /**
     * Url del webservice en mi local
     */
    private static final String URL_SERVICIO = "http://localhost:7001/GestionSUA/MotorProxyService?wsdl";

    @Test
    @Ignore
    public void testMotorCliente() throws Exception {
        DatosCalculoCuota datos = JaxbUtilT.unmarshaller(XML, DatosCalculoCuota.class);
        LOGGER.debug("SDatos calculo {} ", datos);
//        LOGGER.debug("SDatos calculo {} ", JaxbUtilT.marshaller(datos));
        URL url = new URL(URL_SERVICIO);
        QName qname = new QName(SUAConstants.SUA_NAMESSPACE, "cuotaServicePortBindingQSService");
        Service service = Service.create(url, qname);
        CuotaServiceRemote motor = service.getPort(CuotaServiceRemote.class);
        try {
            LOGGER.debug("Salida motor de calculo {}", JaxbUtilT.marshaller(motor.generaCotizacion(datos)));
        } catch (SUAException e) {            
            LOGGER.error("SUA EXCEPTIOn ", e);
        }
    }
}
