package personas;

import java.util.List;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.individuo.NotificacionNoValidaException;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.NotificacionServiceBusinessRemote;

import org.junit.Test;

import test.DeltaOpenEJBTestCase;

public class NotificacionTest extends DeltaOpenEJBTestCase{

	@Test
	public void testGuardarNotificacion (){
		
		Object object = null;
		try {
			object = initialContext.lookup("notificacionServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof NotificacionServiceBusinessRemote);
		
		final NotificacionServiceBusinessRemote ejb = (NotificacionServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		Modulo moduloOrigen = new Modulo();
		moduloOrigen.setIdModulo(ModuloEnum.PATRONES.getCodigo().longValue());
		
		TramiteCambioInformacionPersona tramite = new TramiteCambioInformacionPersona();
		tramite.setTramiteId(5288L);
		
		try {
			ejb.crearNotificacion(tramite, moduloOrigen);
		} catch (NotificacionNoValidaException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void xtestObtenerNotificaciones (){
		
		Object object = null;
		try {
			object = initialContext.lookup("notificacionServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof NotificacionServiceBusinessRemote);
		
		final NotificacionServiceBusinessRemote ejb = (NotificacionServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		Moral persona = new Moral();
		persona.setCveMoral(5L);
		
//		Persona persona = new Persona();
//		persona.setIdPersona(15L);
//		TipoPersona tipoPersona = new TipoPersona();
//		tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
//		persona.setTipoPersona(tipoPersona);
		
//		Fisica persona = new Fisica();
//		persona.setIdPersona(15L);
		
//		Persona persona = new Persona();
//		persona.setIdPersona(15L);
		
		Modulo modulo = new Modulo();
		modulo.setIdModulo(ModuloEnum.PATRONES.getCodigo().longValue());
		
		List<Notificacion> notificaciones = ejb.obtenerNotificacionesPersonaModulo(persona, modulo);
		
		for(Notificacion notificacion : notificaciones){
			System.out.println(notificacion);
		}
		
		
	}
}
