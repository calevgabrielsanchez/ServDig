package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.rectificacion.RectificacionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudDocumentoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;

public class ReglaRPCTest extends GestionClasifEmpresasTestCaseBase{
	@Test
	public void testAutorizarRectificacion() throws Exception {
		
		Object object = initialContext.lookup("rectificacionServiceBusiness");
		assertNotNull(object);
		assertTrue(object instanceof RectificacionBusinessRemote);
		final RectificacionBusinessRemote rectificacionBusinnes=(RectificacionBusinessRemote)object;
		assertNotNull(rectificacionBusinnes);
		//System.out.println(solDocBusinessRemote.pruebaReglaRPC("AAP110707ST5", 2L, "Y5447652"));
		//System.out.println(solDocBusinessRemote.pruebaReglaRPC("AAP110707ST5", 3L, "Y5447654"));
		
		
		//boolean a=rectificacionBusinnes.validaReglaRPC("AAL1003243G8", "3", "E2389875");
		
//		if(a){
//			System.out.println("Todo bien");
//		}else{
//			System.out.println("Debe ser rechazado");
//		}
//		
		
		/**
		 * RP: Y5447532, RFC: AAP110707ST5
		 * AEL830318R42 (1)
		 * ATL470221C97 (1, 4)
		 * AEG270427UE5 (2)
		 * AFM650423PZ0	(5)
		 * 
		 */
	}
}
 