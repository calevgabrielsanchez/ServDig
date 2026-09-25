package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.bitacora;

import javax.ejb.Local;

@Local
public interface BitacoraServiceEntityLocal {
    Boolean permitedRfc(String rfc);
}
