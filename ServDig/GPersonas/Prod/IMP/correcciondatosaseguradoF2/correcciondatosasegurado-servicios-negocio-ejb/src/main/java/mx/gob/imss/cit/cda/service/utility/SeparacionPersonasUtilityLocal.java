package mx.gob.imss.cit.cda.service.utility;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitBitSeparacionPersonas;

import javax.ejb.Local;

@Local
public interface SeparacionPersonasUtilityLocal {

    DitBitSeparacionPersonas convertModelToEntity(Fisica fisica);

    Fisica convertEntityToModel(DitBitSeparacionPersonas bitacora);

}
