package mx.gob.imss.ctirss.delta.gestion.domicilio.service.business.domicilio;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.test.EJBLocator;
import mx.gob.imss.digital.modelo.domicilio.Asentamiento;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Localidad;
import mx.gob.imss.digital.modelo.domicilio.Municipio;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomicilioServiceTest {
	
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(DomicilioServiceTest.class);
	}
	
	@Test
	public void testCompletarDomicilioDigRecortado() {
		DomicilioServiceBusinessRemote ejb = EJBLocator.getDomicilioService();
		
		Domicilio domicilioDigRecortado = new Domicilio();
		domicilioDigRecortado.setNumExteriorAlf("1 A");
		domicilioDigRecortado.setCalle("Calle falsa");
		domicilioDigRecortado.setCodigoPostal("07700");
		domicilioDigRecortado.setAsentamiento(new Asentamiento());
		domicilioDigRecortado.getAsentamiento().setClave("011858");
		domicilioDigRecortado.getAsentamiento().setLocalidad(new Localidad());
		domicilioDigRecortado.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().setClave("005");
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave("09");
		
		try {
			LOG.debug("Se inicia el proceso de completar un domicilio dig");
			LOG.debug("El domicilio que se completara es: " + domicilioDigRecortado.toString());
			mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domActual = ejb.complementarLocalidadDomicilioDigRecortado(domicilioDigRecortado);
			
			LOG.debug("El domicilio recortado es: " + domActual.toString());
		} catch (DomicilioNoValidoException e) {
			LOG.error("Ocurrio un error " + e.getMessage());
			e.printStackTrace();
			
		} catch (DomicilioNoLocalizadoException e) {

			LOG.error("Ocurrio un error " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}

}
