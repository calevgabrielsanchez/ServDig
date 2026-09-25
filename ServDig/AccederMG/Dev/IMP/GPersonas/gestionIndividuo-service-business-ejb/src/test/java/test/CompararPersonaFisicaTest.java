/**
 * 
 */
package test;

import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;

import org.junit.Test;

/**
 * @author Shinji
 *
 */
public class CompararPersonaFisicaTest extends DeltaOpenEJBTestCase {

	private transient static PersonaFisicaServiceBusinessRemote personaFisicaBusiness = EjbLocator.getPersonaFisicaServiceBusinessRemote();
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testComparaPersonaFisica () throws Exception {
		
//		final Object obj = initialContext.lookup("personaFisicaServiceBusiness");
												  
		ICADatosRespuesta resultado = null;
		
//		assertNotNull(obj);
//		assertTrue(obj instanceof PersonaFisicaServiceBusinessRemote);
		
//		final PersonaFisicaServiceBusinessRemote ejb = (PersonaFisicaServiceBusinessRemote) obj;
//		assertNotNull(ejb);
		
		ICADatosConsulta parametros = new ICADatosConsulta();
		
		Fisica fisica = new Fisica();
		Long idPersona = new Long(1660);
		fisica.setIdPersona(idPersona);
		
		fisica.setCurp("AITA771105HDFRRL05");
		fisica.setRfc("AITA771105");
		
		fisica.setSegundoApellido("TORRES");
		
		parametros.setPersonaFisica(fisica);
		parametros.setIndicadorConsultaRENAPO(true);
		parametros.setIndicadorConsultaSAT(false);
	
		resultado = personaFisicaBusiness.identificarCambios(parametros);
		
		assertNotNull(resultado);
	}
	
}
