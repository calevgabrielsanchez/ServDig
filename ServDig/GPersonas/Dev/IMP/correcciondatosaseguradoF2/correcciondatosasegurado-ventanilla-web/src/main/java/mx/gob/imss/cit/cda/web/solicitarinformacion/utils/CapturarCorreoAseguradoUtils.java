package mx.gob.imss.cit.cda.web.solicitarinformacion.utils;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.springframework.stereotype.Component;

@Component
public class CapturarCorreoAseguradoUtils {
    
    /**
     * Metodo para agregar correo electronico del asegurado en solicitud de
     * datos adicionales
     * 
     * @param solicitud
     * @param requestUpdateEvent
     * @return solicitud
     */
    public Solicitud modificarCorreoAsegurado(Solicitud solicitud,
            UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
        List<Tramite> tramites = new ArrayList<Tramite>();

        for (Tramite tramite : solicitud.getTramites()) {

            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;

            /* Tramite principal */
            if (tramiteCda.getPersonaRENAPO() != null) {

                List<MedioContacto> medios = tramiteCda.getPersonaRENAPO()
                        .getMediosContacto();

                /* Si trae medios de contactos previamente */
                if (medios != null) {

                    for (int i = 0; i < medios.size(); i++) {

                        if (medios.get(i) instanceof CorreoElectronico) {
                            CorreoElectronico nuevo = new CorreoElectronico();
                            nuevo.setCorreo(requestUpdateEvent.getData()
                                    .getCorreoAsegurado());
                            medios.remove(medios.get(i));
                            medios.add(nuevo);
                        }

                    }

                    /* Si no tiene medios de contactos */
                } else {

                    medios = new ArrayList<MedioContacto>();
                    MedioContacto medioNuevo = new CorreoElectronico(
                            requestUpdateEvent.getData().getCorreoAsegurado());
                    medios.add(medioNuevo);

                }

                tramiteCda.getPersonaRENAPO().setMediosContacto(medios);

                /* Correo Electronico de la Persona Renapo (Asegurado) */
                CorreoElectronico correo = tramiteCda.getPersonaRENAPO()
                        .getCorreoElectronico();

                if (correo == null) {
                    correo = new CorreoElectronico();
                }

                correo.setCorreo(requestUpdateEvent.getData()
                        .getCorreoAsegurado());
                tramiteCda.getPersonaRENAPO().setCorreoElectronico(correo);

            }
            tramites.add(tramiteCda);

        }

        solicitud.setTramites(tramites);

        return solicitud;
    }

}
