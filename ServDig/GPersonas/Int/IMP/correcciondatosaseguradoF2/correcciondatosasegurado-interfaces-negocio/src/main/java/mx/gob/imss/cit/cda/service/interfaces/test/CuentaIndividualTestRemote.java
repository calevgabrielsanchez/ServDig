package mx.gob.imss.cit.cda.service.interfaces.test;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;

@Remote
public interface CuentaIndividualTestRemote {

    void getListNssByFolio(String folio);
    
    List<PeriodosRegistroPatronal> buscarPeriodosRegistroPatronalPorNss(String nss);
    
    void persistirPeriodo(CuentaIndividualNss cuentaIndividualNss);
    
    void persistirPeriodosModificados(CuentaIndividualNss cuentaIndividualNss);
    
    Long obtenerConsecutivo(Long nss);
    
    void obtenerMovimientosByFolio(String folio);
    
    void guardarAclaraciones(String folio);
    
    void obtenerTipoTramiteAclaracion(String folio);
    
    List<String> obtenerTramitesPorFolio(String folio);
    
    List<String> obtenerTramitesPorFolioyNss(String folio, String nss);
    
}

