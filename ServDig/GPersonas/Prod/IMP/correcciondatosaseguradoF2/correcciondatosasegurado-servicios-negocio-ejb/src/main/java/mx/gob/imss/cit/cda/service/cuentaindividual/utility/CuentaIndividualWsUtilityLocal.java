package mx.gob.imss.cit.cda.service.cuentaindividual.utility;

import java.util.List;
import javax.ejb.Local;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

@Local
public interface CuentaIndividualWsUtilityLocal {
    
    List<PeriodosRegistroPatronal> buscarPeriodosRegistroPatronalPorNss(String nss, List<String> listaNss) throws CuentaIndividualNoDisponibleException;

    List<PeriodoMovimientoAfiliatorio> obtenerPeriodosMovimientoAfiliatorioNSS(String nss);

}
