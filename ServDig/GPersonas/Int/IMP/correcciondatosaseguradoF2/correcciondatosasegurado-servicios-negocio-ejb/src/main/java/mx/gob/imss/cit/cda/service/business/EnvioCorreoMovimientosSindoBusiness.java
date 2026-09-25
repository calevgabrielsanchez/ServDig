package mx.gob.imss.cit.cda.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.entity.BitacoraMovimientosSINDOLocal;
import mx.gob.imss.cit.cda.service.entity.EnvioCorreoMovimientosSindoLocal;
import mx.gob.imss.cit.cda.service.interfaces.EnvioCorreoMovimientosSindoRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "envioCorreoMovimientosSindoBusiness", mappedName = "envioCorreoMovimientosSindoBusiness")
public class EnvioCorreoMovimientosSindoBusiness extends AbstractServiceUtility
        implements EnvioCorreoMovimientosSindoRemote {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @EJB(name = "envioCorreoMovimientosSindo", mappedName = "envioCorreoMovimientosSindo")
    private EnvioCorreoMovimientosSindoLocal envioCorreoMovimientosSindoEntity;

    @EJB
    private BitacoraMovimientosSINDOLocal bitacoraMovimientosSINDOEntity;

    @Override
    public void enviarCorreosMovimientoSindoCDA() {
        log.info("---CDA--- Inicio de procesamiento de correos ODI-SINDO ");
        enviarCorreo();
        log.info("---CDA--- Finaliza envio de procesamiento de correos ODI-SINDO ");
    }

    private void enviarCorreo() {
        boolean result = false;
        DatosEnvioCorreoMovimientosSINDO thread = new DatosEnvioCorreoMovimientosSINDO();
        thread.setEnvioCorreoMovimientosSindoEntity(envioCorreoMovimientosSindoEntity);
        thread.setBitacoraMovimientosSINDOEntity(bitacoraMovimientosSINDOEntity);
        thread.start();

        log.info("ResponsablesDelegacionDTO: " + result);
    }

}
