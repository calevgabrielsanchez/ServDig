/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.test;


import java.math.BigDecimal;
import java.util.Date;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;

import org.junit.Test;

/**
 * @author vanderluk
 *
 */
public class RepresentanteLegalTest extends DeltaTestCaseBase {

	/**
	 * Test method for {@link mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.rep.legal.RepresentanteLegalServiceEntity#persistir(mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testPersistir() {
		RepresentanteLegalServiceBusinessRemote service = null;
		
		System.err.println("Testereando persistir de Representonto legal...");
		
		try {
		service	= (RepresentanteLegalServiceBusinessRemote) this
					.getCtx()
					.lookup("representanteLegalServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote");
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		RepresentanteLegal model = new RepresentanteLegal();
		model.setFecRegistroActualizado(new Date());
		model.setFecRegistroAlta(new Date());
		model.setFecRegistroBaja(new Date());
		model.setIndActAdmonDominio(new BigDecimal(1));
		model.setIndEstatus(new BigDecimal(1));
		model.setCveIdRlDomicilioList(null);
		model.setCveIdRlFacultadList(null);
		
		
		
		try {
			service.agregarRepresentanteLegal(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.err.println("Testereando persistir de Representonto legal... DONE!");
		
		
	}

}
