package mx.gob.imss.cit.cda.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;

@Local
public interface MovimientoCuentaIndividualLocal {
    
    void guardarMovimiento(DitCorreccionCtaIndCda movimiento);
    
    void actualizarMovimiento(DitCorreccionCtaIndCda movimiento);
    
    Long obtenerConsecutivoMovimientos(Long nss);
    
    List<DitCorreccionCtaIndCda> obtenerMovimientosByFolio(String folio);
    
    List<DitCorreccionCtaIndCda> obtenerMovimientosEliminar(Long cveIdNss);

}
