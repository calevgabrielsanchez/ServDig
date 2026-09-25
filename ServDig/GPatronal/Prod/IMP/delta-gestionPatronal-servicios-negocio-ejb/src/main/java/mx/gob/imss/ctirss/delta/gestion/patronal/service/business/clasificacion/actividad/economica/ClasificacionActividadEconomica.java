package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica;

import javax.ejb.Remote;

@Remote
public interface ClasificacionActividadEconomica {

    String crearSolicitudAltaPatronal(String numeroRegistroPatronal);

    String bajaPatronal(String numeroRegistroPatronal);

}
