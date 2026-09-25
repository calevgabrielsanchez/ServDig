package mx.gob.imss.ctirss.delta.derechohabientes.web.jobs;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.reportes.ReportesRemote;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("estatusArchivosJobService")
public class EstatusArchivosJobService {

    private static final Logger log = Logger.getLogger(EstatusArchivosJobService.class);

    @Autowired
    private ReportesRemote reportes;

    public void estatusArchivosJob() {

        log.info("Ejecutando tarea diaria {{Obtencion del estatus de depuracion de reportes AU}}");
        try {
            reportes.estatusDepuracionReportes(new Date());
        }catch (Exception e) {
            log.info("No fue posible ejecutar la tarea diaria {{Obtencion del estatus de depuracion de reportes AU}} " + e.getMessage());
        }

    }
}
