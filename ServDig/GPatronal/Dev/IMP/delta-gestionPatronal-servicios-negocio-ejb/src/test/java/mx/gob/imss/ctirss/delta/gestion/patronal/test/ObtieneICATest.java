package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.digital.modelo.persona.Moral;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ObtieneICATest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ObtieneICATest.class);
	}
	
	@Test
	public void obtieneICA(){


		PersonaMoralBusinessRemote personaBusiness = EJBLocator.getPersonaMoralBusinessRemote();

		ICADatosConsulta parametros = new ICADatosConsulta();
		
		try {
			log.debug(":::: Iniciando....");
			mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral m = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral();
			m.setIdPersona(new Long("2955022"));
			m.setRfc("AEC810901298");
			parametros.setPersonaMoral(m);
			ICADatosRespuesta icaDatosRespuesta = personaBusiness.identificarSoloCambios(parametros);
			log.debug(":::: Obtuve EJB");
			log.debug(icaDatosRespuesta.toString());
			log.debug("::::::::::::::::::::::::::::::::::::::::::::::");
			if(icaDatosRespuesta.getPersonaMoralEE().getEscrituraConstitutiva() != null){
				log.debug(icaDatosRespuesta.getPersonaMoralEE().getEscrituraConstitutiva().toString());
			}
			log.debug("::::::::::::::::::::::::::::::::::::::::::::::");
			if(icaDatosRespuesta.getPersonaMoralEE().getRegistroSindicato() != null){
				log.debug(icaDatosRespuesta.getPersonaMoralEE().getRegistroSindicato().toString());
			}
			log.debug("::::::::::::::::::::::::::::::::::::::::::::::");
			log.debug("::::::::::::::::::::::::::::::::::::::::::::::");
			log.debug("::::::::::::::::::::::::::::::::::::::::::::::");
			if(icaDatosRespuesta.getPersonaMoralIMSS().getEscrituraConstitutiva() != null){
				log.debug(icaDatosRespuesta.getPersonaMoralIMSS().getEscrituraConstitutiva().toString());
			}
			log.debug("::::::::::::::::::::::::::::::::::::::::::::::");
			if(icaDatosRespuesta.getPersonaMoralIMSS().getEscrituraConstitutiva() != null){
				log.debug(icaDatosRespuesta.getPersonaMoralIMSS().getRegistroSindicato().toString());
			}
			log.debug("::::::::::::::::::::::::::::::::::::::::::::::");
			log.debug(":::: Termine");
			
			
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		} catch (DatosInsuficientesICAException e) {

			e.printStackTrace();
		}

	}
	
	


}
