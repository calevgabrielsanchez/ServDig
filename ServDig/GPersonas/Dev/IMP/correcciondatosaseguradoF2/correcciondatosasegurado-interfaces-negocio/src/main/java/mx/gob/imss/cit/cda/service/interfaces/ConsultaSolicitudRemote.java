package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;
import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;

@Remote
public interface ConsultaSolicitudRemote {

    /**
     * Obtiene el origen de los nss capturados en la solicitud por id tramite
     * 
     * @param cveIdTramite
     *            .
     * @return DetalleNss.
     */
    String obtenerOrigenNssCapturado(Long cveIdTramite);

    
    
    Long obtenerTipoNss(Long cveIdTramite);

}
