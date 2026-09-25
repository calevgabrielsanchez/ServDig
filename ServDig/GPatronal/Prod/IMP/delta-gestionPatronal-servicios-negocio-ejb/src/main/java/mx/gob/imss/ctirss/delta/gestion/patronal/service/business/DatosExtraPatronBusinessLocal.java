package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.ejb.Local;

@Local
public interface DatosExtraPatronBusinessLocal {

	void ejecutarReanudacionActividades(Long cveIdPatronSujetoObligado);
}
