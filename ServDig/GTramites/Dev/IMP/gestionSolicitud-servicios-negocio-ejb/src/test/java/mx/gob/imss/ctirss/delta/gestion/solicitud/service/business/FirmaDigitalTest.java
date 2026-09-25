package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoXmlSimple;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirmaDigitalTest {

    private static final Logger log = LoggerFactory.getLogger(FirmaDigitalTest.class);

    private FirmaDigitalBusinessRemote firmaDigitalBusiness;

    @Before
    public void setUp(){
        log.info("obteniendo referencia a ConcluirAltaPatronalBusinessRemote");
        firmaDigitalBusiness = EjbLocator.getFirmaDigitalBusiness();
        log.info("OK, referencia obtenida");
    }

    @Test
	public void firmarXmlTest() {
		
    	String xml = "<ns2:certificacionRetiroPorDesempleoResponse xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"http://certificacion.service.imss.gob.mx/\"><Respuesta><codigoError>0</codigoError><mensajeError>xito</mensajeError><infoCertificacion><nss>12345678901</nss><curp>SAEM860110HDFNSR01</curp><delegacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><subdelegacion xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><umf xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><apellidoPaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><apellidoMaterno xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><nombre xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><prestacionSolicitada>06</prestacionSolicitada><fechaSolicitud>22/09/2014</fechaSolicitud><numeroResolucion>90000012</numeroResolucion><claveResolucion>03</claveResolucion><claveMensajePROCESARA>00</claveMensajePROCESARA><claveMensajePROCESARB>47</claveMensajePROCESARB><salarioBaseCotizacionIncisoA>0.00</salarioBaseCotizacionIncisoA><salarioPromedioCotizacionIncisoB>0.00</salarioPromedioCotizacionIncisoB><retiroComplementario>0</retiroComplementario><fechaBaja xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/></infoCertificacion></Respuesta></ns2:certificacionRetiroPorDesempleoResponse>";
    	String nombreArchivo = "certifDerecho_12345678901";
    	
    	RespuestaFirmadoXmlSimple response = this.firmaDigitalBusiness.firmarXML(xml, nombreArchivo);
    	
    	log.debug(response.toString());
    	
	}
}
