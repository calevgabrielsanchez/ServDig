package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.MovimientoReingresoType;

@Remote
public interface ReingresoServiceBusinessRemote {
    void encolarMovimientoReingreso(MovimientoReingresoType movimientoReingresoType);
}
