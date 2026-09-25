package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 * Servicio encargado de obtener la lista de los dias feriados
 *
 */
@Remote
public interface DiasFeriadosServiceRemote {

    List<Date> getDiasFeriados();
}
