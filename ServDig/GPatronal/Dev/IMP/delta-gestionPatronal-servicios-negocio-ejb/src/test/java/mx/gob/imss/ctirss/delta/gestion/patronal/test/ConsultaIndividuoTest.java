package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;


public class ConsultaIndividuoTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ConsultaIndividuoTest.class);
	}
	
	
//	@Test
//	public void buscarPersonaFisicayDPyDyMCEnIMSS(){
//		try {
//			ServiciosPersonaBusinessRemote pmBs = EJBLocator.getServiciosPersonaBusiness();
//			log.debug(":::: Obtuve EJB ....");
//			Fisica fisica = pmBs.buscarPersonaFisicayDPyDyMCEnIMSS(new Long("240030282"));
//			log.debug(":::: Obtuve datos ....");
//			log.debug("RC: " + fisica.getRfc());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.debug("Finaliza ejecuci�n");
//	}

	
//	@Test
//	public void consultarPersonaFisicaIMSSPorRFC(){
//		try {
//			IndividuoServiceBusinessRemote pmBs = EJBLocator.getIndividuoServiceBusiness();
//			log.debug(":::: Obtuve EJB consultarPersonaFisicaIMSSPorRFC....");
//			Fisica busquedaPersona = new Fisica();
//			busquedaPersona.setRfc("PAVA730831FQ3");			
//			Persona persona = pmBs.consultarPersonaFisicaIMSSPorRFC(busquedaPersona);
//			log.debug(":::: Obtuve datos persona fisica....");
//			log.debug("RC: " + persona.getRfc());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.debug("Finaliza ejecuci�n");
//	}
	
//	@Test
//	public void consultarPersonaMoralIMSSPorRFC(){
//		try {
//			IndividuoServiceBusinessRemote pmBs = EJBLocator.getIndividuoServiceBusiness();
//			log.debug(":::: Obtuve EJB consultarPersonaMoralIMSSPorRFC....");
//			Fisica busquedaPersona = new Fisica();
//			busquedaPersona.setRfc("TYC171220K23");			
//			Persona persona = pmBs.consultarPersonaMoralIMSSPorRFC(busquedaPersona);
//			log.debug(":::: Obtuve datos persona moral....");
//			log.debug("RC: " + persona.getRfc());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.debug("Finaliza ejecuci�n");
//	}	
	
	
	@Test
	public void getPF(){
		Fisica moral = new Fisica();
//		moral.setRfc("MOBB9910087TA");
//		moral.setCurp("MOBB991008MSLJLR03");
		
		moral.setRfc("MOHO7205319H9");
		moral.setCurp("MOHO720531HDFNRM04");
		
		
		try {
			moral=EJBLocator.getConsultaPersonaFisicaServiceBusinessRemote().getPersonaByCurpImssEntidadesExternas(moral);
			System.out.println(moral);
			System.out.println("::: FIN");
		} catch (ClienteWebserviceSatRfcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorComparacionDatosRENAPOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorComparacionDatosSATException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DiferenciasRENAPOContraSAT e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonaSinCalificacionesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
