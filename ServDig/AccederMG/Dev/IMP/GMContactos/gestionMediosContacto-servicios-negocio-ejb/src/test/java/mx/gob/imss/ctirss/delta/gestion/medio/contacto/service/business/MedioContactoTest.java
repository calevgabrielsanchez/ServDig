package mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.test.DeltaOpenEJBTestCase;
import mx.gob.imss.ctirss.delta.test.EJBLocator;

import org.junit.Test;

public class MedioContactoTest {

	@Test
	public void xxxtestGuardarMediosContacto(){
		
//		Object object = null;
//		try {
//			object = initialContext.lookup("mediosContactoServiceBusiness");
//		} catch (NamingException e1) {
//			e1.printStackTrace();
//		}
//		
//		assertNotNull(object);
//		assertTrue(object instanceof MediosContactoServiceBusinessRemote);
//		
//		final MediosContactoServiceBusinessRemote ejb = (MediosContactoServiceBusinessRemote) object;
//		assertNotNull(ejb);
//		
		
		List<MedioContacto> mediosDeContacto = new ArrayList<MedioContacto>();
		CorreoElectronico correo = new CorreoElectronico();
		correo.setCorreo("marco.sanchez@novutek.com");
		mediosDeContacto.add(correo);
		
		TelefonoFijo telFijo = new TelefonoFijo();
		telFijo.setNumero("47511965");
		telFijo.setClaveLada("55");
		mediosDeContacto.add(telFijo);
		
		TelefonoMovil telMovil = new TelefonoMovil();
		telMovil.setNumero("0445532277366");
		mediosDeContacto.add(telMovil);
		
//		try {
//			ejb.registrarMedioDeContacto(mediosDeContacto);
//		} catch (RegistrarMedioContactoException e) {
//			e.printStackTrace();
//		}
		
	}
	
	@Test
	public void xtestConsultarMediosContactoFiscales() {
		
		
		MediosContactoServiceBusinessRemote service = null;
        try {
            service = (MediosContactoServiceBusinessRemote) EJBLocator.getContextoLocal()
                .lookup("mediosContactoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote");
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
		
		
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(3676007L);
		
		List<MedioContacto> medios = null;
		try {
			medios = service.consultarMediosFiscalesPersona(fisica);
		} catch (PersonaSinMedioDeContactoException e) {
			e.printStackTrace();
		}
		
		if(medios != null){
			Iterator<MedioContacto> it = medios.iterator();
			MedioContacto medio = null;
			
			while(it.hasNext()){
				medio = it.next();
				System.out.println("MEDIO -> " + medio);
			}
		}
		
		Moral moral = new Moral();
		moral.setIdPersona(334395L);
		
		medios = null;
		try {
			medios = service.consultarMediosFiscalesPersona(moral);
		} catch (PersonaSinMedioDeContactoException e) {
			e.printStackTrace();
		}
		
		if(medios != null){
			Iterator<MedioContacto> it = medios.iterator();
			MedioContacto medio = null;
			
			while(it.hasNext()){
				medio = it.next();
				System.out.println("MEDIO -> " + medio);
			}
		}
		
	}
	
	@Test
	public void testEliminarMedioContacto(){
		
//		Object object = null;
//		try {
//			object = initialContext.lookup("mediosContactoServiceBusiness");
//		} catch (NamingException e1) {
//			e1.printStackTrace();
//		}
//		
//		assertNotNull(object);
//		assertTrue(object instanceof MediosContactoServiceBusinessRemote);
//		
//		final MediosContactoServiceBusinessRemote ejb = (MediosContactoServiceBusinessRemote) object;
//		assertNotNull(ejb);
//		
//		try {
//			ejb.eliminarMedioDeContacto(4048L);
//		} catch (RegistrarMedioContactoException e) {
//			e.printStackTrace();
//		}
//		
		
	}
	
	
	
	
	@Test
	public void xtestConsultarMediosCentroTrabajo() {
		
		
		
		CentroTrabajo ct = new CentroTrabajo();
		ct.setCveIdPatronSujetoObligado(292l);
		List<MedioContacto> medios = null;
		
		medios = EJBLocator.getMediosBusinessRemote().consultarMedioContactoDeCentroTrabajo(ct);
				
		if(medios != null){
			Iterator<MedioContacto> it = medios.iterator();
			MedioContacto medio = null;
			
			while(it.hasNext()){
				medio = it.next();
				System.out.println("MEDIO -> " + medio);
			}
		}
		
		
	}
	
}
