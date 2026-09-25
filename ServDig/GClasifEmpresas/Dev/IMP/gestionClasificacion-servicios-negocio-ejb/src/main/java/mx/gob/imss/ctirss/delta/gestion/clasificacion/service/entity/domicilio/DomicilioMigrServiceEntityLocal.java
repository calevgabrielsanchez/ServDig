package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface DomicilioMigrServiceEntityLocal{
	SujetoObligado obtenerMunicipioMigr(Long cveIdPatronSujetoObligado);
}
