package mx.gob.imss.cit.cda.service.autorizar.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Local
public interface AutorizarSolicitudUtilityLocal {
    
    MensajeTarea crearMensajeTarea(String usuario);
    
    List<Fisica> normalizarFisicasHistoricas(List<Fisica> fisicasHistoricas);

}
