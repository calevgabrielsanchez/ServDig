package mx.gob.imss.ctirss.delta.gestion.domicilio.service.business.domicilio;

import java.util.List;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.test.DeltaOpenEJBTestCase;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

import org.junit.Test;

public class DomicilioServiceBusinessTest extends DeltaOpenEJBTestCase {

	@Test
	public void xtestRegistrarDomicilio() {
		Object object = null;
		try {
			object = initialContext.lookup("domicilioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof DomicilioServiceBusinessRemote);
		
		final DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		
		Domicilio filter = new Domicilio();
		filter.setClave(963);
		try {
			Domicilio domicilio = ejb.consultarDomicilio(filter );
			System.out.println(domicilio);
			
			domicilio.setClave(null);
			//domicilio.setLatitud(BigDecimal.valueOf(32.0333334));
			//domicilio.setLongitud(BigDecimal.valueOf(-15.6751245));
			
			ejb.registrarDomicilio(domicilio);
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		} catch (DomicilioNoValidoException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void xtestRegistrarDomicilioFiscal(){
	
		Object object = null;
		try {
			object = initialContext.lookup("domicilioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof DomicilioServiceBusinessRemote);
		
		final DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) object;
		assertNotNull(ejb);
				
		Domicilio filter = new Domicilio();
		filter.setClave(963);
		
		try {
			Domicilio domicilio = ejb.consultarDomicilio(filter );
			System.out.println(domicilio);
			
			domicilio.setClave(null);
			
			DomicilioFiscal domicilioFiscal = new DomicilioFiscal();
			
			domicilioFiscal.setClave(domicilio.getClave());
			domicilioFiscal.setCalle(domicilio.getCalle());
			domicilioFiscal.setColonia(domicilio.getColonia());
			domicilioFiscal.setNumExterior1(domicilio.getNumExterior1());
			domicilioFiscal.setNumExteriorAlf(domicilio.getNumExteriorAlf());
			domicilioFiscal.setNumExterior2(domicilio.getNumExterior2());
			domicilioFiscal.setNumInterior(domicilio.getNumInterior());
			domicilioFiscal.setNumInteriorAlf(domicilio.getNumInteriorAlf());
			domicilioFiscal.setLatitud(domicilio.getLatitud());
			domicilioFiscal.setLongitud(domicilio.getLongitud());
			domicilioFiscal.setCodigoPostal(domicilio.getCodigoPostal());
			domicilioFiscal.setAsentamiento(domicilio.getAsentamiento());
			domicilioFiscal.setAmbito(domicilio.getAmbito());
			domicilioFiscal.setVialidadPrimaria(domicilio.getVialidadPrimaria());
			domicilioFiscal.setVialidadReferenciaPrimaria(domicilio.getVialidadReferenciaPrimaria());
			domicilioFiscal.setVialidadReferenciaSecundaria(domicilio.getVialidadReferenciaSecundaria());
			domicilioFiscal.setVialidadReferenciaPosterior(domicilio.getVialidadReferenciaPosterior());
			domicilioFiscal.setTipoDomicilio(domicilio.getTipoDomicilio());
			domicilioFiscal.setDescripcion(domicilio.getDescripcion());
			
			ejb.registrarDomicilioFiscal(domicilioFiscal);
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		} 
		catch (DomicilioNoValidoException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void xtestConsultaDomicilioFiscalPersona(){
		
//		Fisica fisica = new Fisica();
//		fisica.setIdPersona(3676007L);
		
		Moral moral = new Moral();
		moral.setIdPersona(334395454L);
		
		Object object = null;
		try {
			object = initialContext.lookup("domicilioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof DomicilioServiceBusinessRemote);
		
		final DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		try {
			DomicilioFiscal domicilioFiscal = ejb.consultarDomicilioFiscalPersona(moral);
			
			System.out.println("Domicilio Fiscal -> " + domicilioFiscal.toString());
			
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void xtestConsultarDomiciliosPersona(){
		
		Persona persona = new Persona();
		TipoPersona tipoPersona = new TipoPersona();
		
		//fisica
		persona.setIdPersona(7250163L);
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		
		//moral
//		 persona.setIdPersona(334426345678L);
//		 tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
		
		persona.setTipoPersona(tipoPersona);
		
		Object object = null;
		try {
			object = initialContext.lookup("domicilioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof DomicilioServiceBusinessRemote);
		
		final DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		try {
			List<Domicilio> domicilios = ejb.consultarDomiciliosPersonaFisica(persona);
			
			for(Domicilio domicilio : domicilios){
				System.out.println("DOMICILIO -> " + domicilio);
			}
			
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void XtestModificarDomicilio(){
		
		Object object = null;
		try {
			object = initialContext.lookup("domicilioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof DomicilioServiceBusinessRemote);
		
		final DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		Domicilio domicilio = new Domicilio();
		domicilio.setClave(1415);
		
		try {
			domicilio = ejb.consultarDomicilio(domicilio);
			
			domicilio.setNumExterior1(100);
			domicilio.setNumExteriorAlf("CUADRA");
			
			ejb.modificarDomicilio(domicilio);
			
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		} catch (TransformacionException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	@Test
	public void xtestEliminarDomicilio(){
		
		Object object = null;
		try {
			object = initialContext.lookup("domicilioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof DomicilioServiceBusinessRemote);
		
		final DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		try {
			ejb.desasociarEliminarDomicilioPersona(1415L, 3676007L);
		} catch (AsociarDomicilioException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testConsultaMunicipioIMSSS(){
		Object object = null;
		try {
			object = initialContext.lookup("domicilioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof DomicilioServiceBusinessRemote);
		
		final DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		try {
			ejb.getMunicipioIMSSbyEstadoMunCP(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
