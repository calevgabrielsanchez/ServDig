package mx.gob.imss.cit.cda.service.cuentaindividual.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DicTipoNssAclaracion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramCorreccionNss;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

@Local
public interface MovimientoAclaracionLocal {
    
    DitMovAclaracionNssCda guardarMovimiento(DitMovAclaracionNssCda movimiento);
    
    DitMovAclaracionNssCda actualizarMovimiento(DitMovAclaracionNssCda movimiento);
    
    DitMovAclaracionNssCda elimianrMovimiento(DitMovAclaracionNssCda movimiento);
    
    List<DitMovAclaracionNssCda> buscarMovExistentes(Long folio);
    
    List<DitMovAclaracionNssCda> obtenerMovimientosAclaracion(String refFolio);
    
    List<DitMovAclaracionNssCda> obtenerMovimientosAclaracionByFolioNss(String refFolio,  String nss);

	List<Object> obtenerTipoNSSAclaracion(Long nssrefLOng);
	
	List<Object> eliminarTipoNSSAclaracion(DitMovAclaracionNssCda movimiento);

	DicTipoTramCorreccionNss CveIdTipoTramCorrecNss(long cve);

	DicTipoNssAclaracion CveIdTipoNssAclaracion(long cve);

	long CveIdMovAclaracionNssGet(long cve);


	
}
