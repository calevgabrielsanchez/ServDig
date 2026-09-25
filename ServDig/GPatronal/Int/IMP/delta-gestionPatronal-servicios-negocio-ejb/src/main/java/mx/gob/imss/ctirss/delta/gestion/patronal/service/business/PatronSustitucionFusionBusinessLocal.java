package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface PatronSustitucionFusionBusinessLocal {
	
	Boolean validarExistenciaFusion(Long idPatronGeneral, Long idPatronGeneralFusionado);
	Boolean validarPatronExisteComoFusionado(Long idPatronAValidar);
	void insertarPatronFusionado (SujetoObligado sujeto);
}
