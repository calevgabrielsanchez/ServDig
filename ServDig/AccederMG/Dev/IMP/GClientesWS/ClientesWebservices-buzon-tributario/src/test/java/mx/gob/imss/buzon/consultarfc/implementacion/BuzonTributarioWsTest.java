package mx.gob.imss.buzon.consultarfc.implementacion;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.UsuarioBuzonRespuesta;
import org.junit.Test;

public class BuzonTributarioWsTest {
    @Test
    public void consultaRfc() throws Exception {
        UsuarioBuzonRespuesta usuarioBuzonRespuesta = BuzonTributarioWs.consultaRfc("JUMI820406P69", null);
        usuarioBuzonRespuesta.getMensaje();
    }

}