package mx.gob.imss.cit.cda.service.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.service.entity.BitacoraMovimientosSINDOLocal;
import mx.gob.imss.cit.cda.service.entity.EnvioCorreoMovimientosSindoLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BitacoraMovimientoSindoCDA;

import org.apache.log4j.Logger;

public class DatosEnvioCorreoMovimientosSINDO extends Thread {
    private Logger log = Logger
            .getLogger(DatosEnvioCorreoMovimientosSINDO.class);
    private boolean existeErrorServicio = false;

    private EnvioCorreoMovimientosSindoLocal envioCorreoMovimientosSindoEntity;
    private BitacoraMovimientosSINDOLocal bitacoraMovimientosSINDOEntity;

    @Override
    public void run() {

        try {
            log.info("---CDA--- Thread. El proceso inicia consulta. "
                    + new Date());

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -20);

            //Map<String, List<BitacoraMovimientoSindoCDA>> bitacoraSINDO = bitacoraMovimientosSINDOEntity.obtenerBitacoraMovimientos(new Date());
            Map<String, List<BitacoraMovimientoSindoCDA>> bitacoraSINDO = bitacoraMovimientosSINDOEntity.obtenerBitacoraMovimientosProceadosYErroresSINDO(new Date());

            log.info("---CDA--- Se obtiene lista de movimientos del entity bitacoraMovimientosSINDOEntity y se itera");

            // obtener solicitud
            // iterar bitacora y enviar correos
            for (String folio : bitacoraSINDO.keySet()) {
                envioCorreoMovimientosSindoEntity.enviarCorreo(bitacoraSINDO,
                        folio);
            }

            log.info("---CDA--- Thread. Se finaliza la consulta satisfactoriamente. "
                    + new Date());
        } catch (Exception e) {
            log.error(
                    "---CDA--- Thread. Se genero un error al accesar el webservice",
                    e);
            existeErrorServicio = true;
        }

        log.error("---CDA--- Thread. Fin del thread. " + new Date());
    }

    public boolean isExisteErrorServicio() {
        return existeErrorServicio;
    }

    public void setEnvioCorreoMovimientosSindoEntity(
            EnvioCorreoMovimientosSindoLocal envioCorreoMovimientosSindoEntity) {
        this.envioCorreoMovimientosSindoEntity = envioCorreoMovimientosSindoEntity;
    }

    public void setBitacoraMovimientosSINDOEntity(
            BitacoraMovimientosSINDOLocal bitacoraMovimientosSINDOEntity) {
        this.bitacoraMovimientosSINDOEntity = bitacoraMovimientosSINDOEntity;
    }

}
