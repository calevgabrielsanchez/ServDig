package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica;

import javax.ejb.Local;

@Local
public interface DatosExtrasPatronEntityLocal {
	
	void setTipoMovimientoPatron(Long cveIdPatronGeneral, Integer tipoMovimiento);

}
