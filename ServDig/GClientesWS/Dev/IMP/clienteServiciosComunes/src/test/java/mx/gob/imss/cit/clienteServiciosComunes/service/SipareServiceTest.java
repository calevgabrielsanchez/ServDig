package mx.gob.imss.cit.clienteServiciosComunes.service;

import mx.gob.imss.cit.clienteServiciosComunes.services.SipareService;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo.SipareRequest;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo.SipareResponse;
import mx.gob.imss.cit.test.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SipareServiceTest extends BaseTest {
	
	private SipareRequest sipareRequest;
	
	@Autowired
	private SipareService sipareService;
	
	@Before
	public void init() {
		sipareRequest = new SipareRequest();
		sipareRequest.setLineaSua("02Y6099999408 IMS421231I45201608993168CONTINUACION VOLUNTARIA EN EL REGIMEN OBLIGATORIO CONOCIDA                                PIEDAD NARVARTE                         00000000000000000000000050000201607SEGURO CONVENCIONAL                     4058A00N3000003100000000101000000000                03Y6099999408 IMS421231I4520160806725511601AAAA010101   MECP550307HDFDLD0800000000002016072701MEDINA$CALDERON$PEDRO                             010000010310000000000000000000000000004417500000000073625000000000013313100000062000000070100976500034875000149700000000000000000000000000031V52V94VY6004Y6099999408067255116010020160701        000000000                                                                                                                                                                                                                                                    05Y6099999408 IMS421231I4520160899316800000000000000011300000000000000000000000000000000044175000000000000073625000000000000001178000000000000000013310000620000001325250000019452500000000000000219800000000000000000000000000000000000000000000000000000000000000000000000000000000000               06Y6099999408 IMS421231I45201608993168000001130000000014752016071801G3920000001191310000001967230000000000000000000000007855716328254073159000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000                                 000");
		sipareRequest.setTipoPatron(0);
	}
	
	@Test
	public void generaLineaCaptura() {
		SipareResponse sipareResponse = new SipareResponse();
		
		sipareResponse = sipareService.generaLineaCaptura(sipareRequest);
		
		if (sipareResponse.getListExcepciones() != null && sipareResponse.getListExcepciones().length != 0) {
			for (String excepcion : sipareResponse.getListExcepciones()) {
				System.out.println("Error al obtener la linea de captura: "+excepcion);
			}
		} else {
			System.out.println("Linea de Captura: "+sipareResponse.getLineaCaptura());
			System.out.println("Archivo pdf: "+sipareResponse.getPdf());
		}
		
	}

}
