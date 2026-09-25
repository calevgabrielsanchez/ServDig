package mx.gob.imss.cit.cda.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;

@Local
public interface DetalleNssCdaLocal {
    
    List<DitDetalleNss> getListNssByFolio(String folio);

    int getCountDetalleNssByIdTramite(Long idTramite, String nss);
    
    DitDetalleNss getDetalleNssByFolioNss(String folio, String nss);
    
    DitDetalleNss buscarNssEnTramiteCorreccion(Long cveIdCorreccionDatosAsegurado, String nss);
    
}
