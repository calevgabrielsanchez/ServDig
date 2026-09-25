package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.List;
import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.MovimientoAsignacionSIMEType;

@Remote
public interface MovimientoAsignacionSIMEBusinessRemote {

    String procesarMovimientoAsignacionSIME(MovimientoAsignacionSIMEType movimiento);

    List<String> procesarMovimientosAsignacionSIME(List<MovimientoAsignacionSIMEType> movimientos);
}
