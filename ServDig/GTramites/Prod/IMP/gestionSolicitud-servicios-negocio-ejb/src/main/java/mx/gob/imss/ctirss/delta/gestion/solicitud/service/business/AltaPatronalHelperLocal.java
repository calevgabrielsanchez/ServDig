package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

@Local
public interface AltaPatronalHelperLocal {

    MovimientoPatronalType createMovimientoPatronalAlta(Solicitud solicitud);
    MovimientoPatronalType creaMovimiento(SujetoObligado sujetoObligado, Persona persona);
}

