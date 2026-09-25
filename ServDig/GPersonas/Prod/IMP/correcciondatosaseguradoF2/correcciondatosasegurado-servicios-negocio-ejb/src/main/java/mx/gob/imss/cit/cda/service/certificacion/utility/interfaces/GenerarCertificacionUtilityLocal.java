package mx.gob.imss.cit.cda.service.certificacion.utility.interfaces;

import java.util.Map;
import javax.ejb.Local;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface GenerarCertificacionUtilityLocal {
    
    FirmaElectronica armarFirma(Map<String, String> firma);
    
    Solicitud datosFirmaSolicitud(Map<String, String> firma, Solicitud solicitud);
    
    Solicitud actualizaEstadosSolicitudCertificacion(Solicitud solicitud, String usuario, String asignado);
}
