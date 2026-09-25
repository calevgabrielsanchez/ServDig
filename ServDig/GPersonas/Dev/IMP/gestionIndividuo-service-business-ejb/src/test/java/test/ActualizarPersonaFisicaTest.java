package test;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.junit.Test;

public class ActualizarPersonaFisicaTest extends DeltaOpenEJBTestCase {
	
	
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testActualizarPersonaFisica() throws Exception{
		
		
		final Object object = initialContext.lookup("personaBusiness");
		
		assertNotNull(object);
		assertTrue(object instanceof PersonaBusinessRemote);
		
		final PersonaBusinessRemote ejb = (PersonaBusinessRemote) object;
		assertNotNull(ejb);
		
		Fisica fisica = new Fisica();
		Long idPersona = new Long(25128497);
		fisica.setIdPersona(idPersona);
		
		fisica.setCurp("CURP0128797904");
		fisica.setRfc("RFC000000004");
		
		fisica.setSegundoApellido("Segundo apellido 4");
		
		List<PersonaCalificacion> calificaiones = new ArrayList<PersonaCalificacion>();
		
		Calificacion calificacion = new Calificacion();
		calificacion.setIdCalificacion(new Long(5));
		
		Calificacion calificacion1 = new Calificacion();
		calificacion1.setIdCalificacion(new Long(2));
		
		PersonaCalificacion c1 = new PersonaCalificacion();
		c1.setCalificacion(calificacion);
		c1.setPersona(fisica);
		
		PersonaCalificacion c2 = new PersonaCalificacion();
		c2.setCalificacion(calificacion1);
		c2.setPersona(fisica);
		
		
		calificaiones.add(c1);
		calificaiones.add(c2);
		
		fisica.setPersonaCalificaciones(calificaiones);
		
		
		
		assertNotNull(fisica);
		ejb.actualizarPersona(fisica);
		
	}
	
	@Test
	public void testActualizaPersonaMoral() throws NamingException{
		
		
		final Object object = initialContext.lookup("personaMoralBusiness");
		
		assertNotNull(object);
		assertTrue(object instanceof PersonaMoralBusinessRemote);
		
		final PersonaMoralBusinessRemote ejb = (PersonaMoralBusinessRemote) object;
		assertNotNull(ejb);
		List<PersonaCalificacion> calificaciones = new ArrayList<PersonaCalificacion>();
		
		Moral moral = new Moral();
		moral.setRazonSocial("BAR CANTINA CTIRSS");
		moral.setIdPersona(new Long(1));
		
		Calificacion calificacion = new Calificacion();
		calificacion.setIdCalificacion(new Long(5));
		
		Calificacion calificacion1 = new Calificacion();
		calificacion1.setIdCalificacion(new Long(2));
		
		PersonaCalificacion c1 = new PersonaCalificacion();
		c1.setCalificacion(calificacion);
		c1.setPersona(moral);
		
		PersonaCalificacion c2 = new PersonaCalificacion();
		c2.setCalificacion(calificacion1);
		c2.setPersona(moral);
		
		calificaciones.add(c1);
		calificaciones.add(c2);
		
		moral.setPersonaCalificaciones(calificaciones);
		
		
		try {
			ejb.actualizarPersonaMoral(moral);
		} catch (PersonaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
