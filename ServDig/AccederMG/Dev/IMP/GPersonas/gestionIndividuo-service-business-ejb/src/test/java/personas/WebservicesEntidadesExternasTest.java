package personas;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.junit.Test;

import test.EjbLocator;

public class WebservicesEntidadesExternasTest {

    @Test
    public void testBuscarPersonaFisicaMoral() throws SolicitudNoEncontradaException, ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException {
        PersonaBusinessRemote personaBusiness = EjbLocator.getPersonaBusiness();

        // BUSCA EN RENAPO
        //Fisica persona = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo("GUPA840605HDFZRN06");
        Fisica persona = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo("HAKR100508HNEGLN09");
        //Fisica persona = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo("G1");
        System.out.println("persona: " + persona);

        // BUSCA EN SAT
        //Fisica personaFisica = personaBusiness.buscarPersonaFisicaPorRfcEnSat("PODJ7209024R8");
        //System.out.println("personaFisica: " + personaFisica);

        // BUSCA EN SAT
        //Moral personaMoral = personaBusiness.buscarPersonaMoralPorRfcEnSat("ADE0501173H3");
        //System.out.println("personaMoral: " + personaMoral);

    }

}
