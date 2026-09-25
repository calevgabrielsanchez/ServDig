package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mx.gob.imss.ctirss.delta.gestion.beneficio.service.business.base.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.CancelarBeneficioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaCancelacionBeneficio;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanelarBeneficiosTest {

	private static final Logger log = LoggerFactory
		.getLogger(CanelarBeneficiosTest.class);
	
	private CancelarBeneficioServiceBusinessRemote cancelarBeneficioServiceBusinessRemote;

	@Before
	public void setUp() {
		cancelarBeneficioServiceBusinessRemote = EjbLocator.getCancelarBeneficioService();
	}
	
	
	@Test
	public void testCancelaBeneficio() {
		int test = 1;
		RespuestaCancelacionBeneficio respuesta = new RespuestaCancelacionBeneficio();
		if(test==1){
			respuesta = testCancelarBeneficioSatRFC("MAHJ7608082T5");			
		}else if(test==2){
			respuesta = testCancelarBeneficioInfonavitNRP("Y5236304100");
		}else if(test==3){
			respuesta = testCancelarBeneficioInfonavitNSS("03146590041") ;
		}else if(test==4){
			respuesta = testCancelarBeneficioImssNRP("Y5236304100");
		}else if(test==5){
			respuesta = testCancelarBeneficioImssNSS("03146590041");
			
		}else if(test==6){
			respuesta = testCancelarBeneficioSatError();
		}else if(test==7){
			respuesta = testCancelarBeneficioInstitucionError();
		}
		log.debug("ESCENARIO PRUEBA "  + test);
		log.debug("EXITO "  + respuesta.getExito());
		log.debug("CLAVE OPERACION "  + respuesta.getClaveError());
		log.debug("DESCRIPCION OPERACION "  + respuesta.getDescripcion());
	}
	
	
	private RespuestaCancelacionBeneficio testCancelarBeneficioSatRFC(String rfc) {
		Calendar fechaBaja = new GregorianCalendar();
		fechaBaja.set(2014, Calendar.APRIL, 25);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		return cancelarBeneficioServiceBusinessRemote.cancelarBeneficio(rfc, null, null, sdf.format(fechaBaja.getTime()), 1);
	}
	
	private RespuestaCancelacionBeneficio testCancelarBeneficioInfonavitNRP(String nrp) {
		Calendar fechaBaja = new GregorianCalendar();
		fechaBaja.set(2014, Calendar.APRIL, 26);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return cancelarBeneficioServiceBusinessRemote.cancelarBeneficio(null, nrp, null, sdf.format(fechaBaja.getTime()), 2);
	}
	
	private RespuestaCancelacionBeneficio testCancelarBeneficioInfonavitNSS(String nss) {
		Calendar fechaBaja = new GregorianCalendar();
		fechaBaja.set(2014, Calendar.APRIL, 27);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return cancelarBeneficioServiceBusinessRemote.cancelarBeneficio(null,null, nss, sdf.format(fechaBaja.getTime()), 2);
	}
	
	private RespuestaCancelacionBeneficio testCancelarBeneficioImssNRP(String nrp) {
		Calendar fechaBaja = new GregorianCalendar();
		fechaBaja.set(2014, Calendar.APRIL, 28);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return cancelarBeneficioServiceBusinessRemote.cancelarBeneficio(null, nrp, null, sdf.format(fechaBaja.getTime()), 3);
	}

	private RespuestaCancelacionBeneficio testCancelarBeneficioImssNSS(String nss) {
		Calendar fechaBaja = new GregorianCalendar();
		fechaBaja.set(2014, Calendar.APRIL, 27);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return cancelarBeneficioServiceBusinessRemote.cancelarBeneficio(null,null, nss, sdf.format(fechaBaja.getTime()), 4);
	}
	
	
	//Flujos Error
	private RespuestaCancelacionBeneficio testCancelarBeneficioSatError() {
		Calendar fechaBaja = new GregorianCalendar();
		fechaBaja.set(2014, Calendar.APRIL, 25);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return cancelarBeneficioServiceBusinessRemote.cancelarBeneficio(null, "Y5236304100", null, sdf.format(fechaBaja.getTime()), 1);
	}
	
	private RespuestaCancelacionBeneficio testCancelarBeneficioInstitucionError() {
		Calendar fechaBaja = new GregorianCalendar();
		fechaBaja.set(2014, Calendar.APRIL, 25);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return cancelarBeneficioServiceBusinessRemote.cancelarBeneficio("VERA650124DH8", null, null, sdf.format(fechaBaja.getTime()), 5);
	}
	
}
