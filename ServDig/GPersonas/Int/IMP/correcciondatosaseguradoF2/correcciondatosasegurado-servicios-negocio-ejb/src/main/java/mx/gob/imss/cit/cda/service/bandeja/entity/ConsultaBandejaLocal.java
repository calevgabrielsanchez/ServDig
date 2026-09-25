package mx.gob.imss.cit.cda.service.bandeja.entity;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;

import javax.ejb.Local;

@Local
public interface ConsultaBandejaLocal {

    String obtenerCurpPorFolio(String folio);
    
    String obtenerTipoTramite(Long idTramite);

    DataPage consultaTareasCDA(DataPage dataPage, String usuario, String subdelegacion,  Long idsProceso);
    DataPage consultaTareasHistCDA(DataPage dataPage, String usuario, String subdelegacion,  Long idsProceso);
}
