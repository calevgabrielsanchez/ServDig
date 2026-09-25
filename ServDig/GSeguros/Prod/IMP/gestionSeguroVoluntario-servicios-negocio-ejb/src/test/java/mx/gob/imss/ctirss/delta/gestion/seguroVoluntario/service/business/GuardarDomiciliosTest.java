/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.persona.Persona;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author softtekop
 */
public class GuardarDomiciliosTest {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(GuardarDomiciliosTest.class);
    }

    private final DomicilioServiceBussinessExternosRemote domicilioExternosServiceBusiness = EjbLocator.find(DomicilioServiceBussinessExternosRemote.class, Ambiente.STAGE);

    @Test
    public void pruebaDomicilio() {
        Persona persona = new Persona();
        persona.setIdPersona(92792L);
        Domicilio domicilio;
        try {
            domicilio = domicilioExternosServiceBusiness.consultarUltimoDomicilioParticilar(persona.getIdPersona());
            System.out.println("Domicilio: " + domicilio.getCalle());
        } catch (DomicilioNoLocalizadoException e) {
            LOGGER.error("ERROR", e);
        } catch (MunicipioImssNoLocalizadoException e) {
            LOGGER.error("ERROR", e);
        }

    }
//    private final transient ComponentesExternosBusinessRemote componentesExternosBusinessRemote = EjbLocator.getComponentesExternosBusinessRemote();
//
//    @Test
//    public void guardaYAsociaDomicilioTest() {
//        mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona personaIndividuo=new mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona();
//        try {
//            componentesExternosBusinessRemote.guardarYAsociarDomiciliosPersona(personaIndividuo);
//        } catch (DomicilioNoValidoException ex) {
//            java.util.logging.Logger.getLogger(GuardarDomiciliosTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    
}
