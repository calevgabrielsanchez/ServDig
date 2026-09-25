package mx.gob.imss.ws.estatusvigencia.individual.implementacion;

import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;
import org.junit.Test;

public class EstatusVigenciaIndividualTest {

    @Test
    public void consultaVigenciaSeguroIndividualTest(){
        EstatusVigenciaIndividual estatusVigenciaIndividual = new EstatusVigenciaIndividual();

        VigenciaTrabajdor vigenciaTrabajdor = estatusVigenciaIndividual.consultaVigenciaSeguroIndividual("35783");

        System.out.println("vigenciaTrabajdor = " + vigenciaTrabajdor);

    }

}
