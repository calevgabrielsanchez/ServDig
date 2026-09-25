package mx.gob.imss.cit.cda.service.cuentaindividual.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;

@Local
public interface CuentaIndividualNssUtilityLocal {
    
    CuentaIndividualNss findDetalleByFolioNss(String folioSolicitud, String nss, List<String> listaNss);
    
    List<String> getMovimientosAclaracionByFolioNss(String folio, String nss);
}