package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistroPatronalBusinessTest {
	 private static final Logger log = LoggerFactory.getLogger(SujetoObligadoServiceBusinessTestIt.class);
	 private RegistroPatronalServiceBusinessRemote service;
	 
	 
	@Before
	public void before() throws NamingException {
	    service = EjbLocator.getRegistroPatronalServiceBusiness();
	    log.debug("servicio: {}", service);
	}
	
	@Test
	public void obtenerCorreo(){
		String correo = service.obtenerCorreoDeNotificacion(6694045l);
		System.err.println("correo: "+correo);
		
		String correoPorNumero;
		try {
			correoPorNumero = service.obtenerCorreoDeNotificacion("Y5841365108");
			System.err.println("correo por numero: "+correoPorNumero);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
		
		String correoFiscal = service.obtenerCorreoFiscal(55122l, TipoPersona.TIPO_PERSONA_MORAL);
		System.err.println("Correo Fiscal: "+ correoFiscal);
		
	}
	 
}
