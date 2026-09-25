package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.RegistrosPatronales34ServiceBusinessRemote;
import mx.gob.imss.digital.modelo.domicilio.Asentamiento;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Municipio;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrosPatronales34Test {
	private static final Logger log = LoggerFactory.getLogger(RegistrosPatronales34Test.class);
	
	private RegistrosPatronales34ServiceBusinessRemote registrosPatronales34ServiceBusiness;

    @Before
    public void before() throws NamingException {
    	registrosPatronales34ServiceBusiness = EjbLocator.getRegistrosPatronales34ServiceBusinessRemote();
        log.debug("servicio: {}", registrosPatronales34ServiceBusiness);
    }
    
    @Test
    public void testObtenerNRPs34PorPersona() throws GestionPatronalBusinessException {
    	Persona persona = new Persona();
    	persona.setRfc("GAAA850325EE2");
    	persona.setTipoPersona(new TipoPersona());
    	persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
    	persona = registrosPatronales34ServiceBusiness.obtenerNRPs34(persona);
    	log.debug("FIN testObtenerNRPs34PorPersona");    	
    }
    
    @Test
    public void testObtenerNRPDomesticoXDomicilioCT() throws GestionPatronalBusinessException {
    	Domicilio domicilio = new Domicilio();
    	domicilio.setCodigoPostal("55130");
    	domicilio.setAsentamiento(new Asentamiento());
    	domicilio.getAsentamiento().setMunicipio(new Municipio());
    	domicilio.getAsentamiento().getMunicipio().setClave("033");
    	domicilio.getAsentamiento().getMunicipio().setEntidadFederativa(new EntidadFederativa());
    	domicilio.getAsentamiento().getMunicipio().getEntidadFederativa().setClave("15");
    	Persona persona = new Persona();
    	persona.setRfc("GAAA850325EE2");
    	
    	RegistroPatronal registroPatronal = registrosPatronales34ServiceBusiness
    		.obtenerNRPDomesticoXDomicilioCT(persona, domicilio);
    	log.debug("FIN testObtenerNRPDomesticoXDomicilioCT " + registroPatronal);
    }
}
