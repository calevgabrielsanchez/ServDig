package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

@Remote
public interface AltaPatronalHelperRemote {

    MovimientoPatronalType createMovimientoPatronalAlta(Solicitud solicitud);
    MovimientoPatronalType creaMovimiento(SujetoObligado sujetoObligado, Persona persona);
}

