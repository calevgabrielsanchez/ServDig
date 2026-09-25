package mx.gob.imss.cit.cda.service.interfaces;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;

import javax.ejb.Remote;

@Remote
public interface ConsultaBandejaRemote {

    /**
     * Obtiene la CURP relacionada a la solicitud por el folio de la solicitud
     * 
     * @param folio
     *            .
     * @return curp.
     */
    String obtenerCurpPorFolio(String folio);
    
    String obtenerTipoTramite(Integer idTramite);

    DataPage obtenerTareasCDA(DataPage dataPage, String usuario, String subdelegacion, Long idProceso);
 
    DataPage obtenerTareasHistCDA(DataPage dataPage, String usuario, String subdelegacion, Long idProceso);
 

}
