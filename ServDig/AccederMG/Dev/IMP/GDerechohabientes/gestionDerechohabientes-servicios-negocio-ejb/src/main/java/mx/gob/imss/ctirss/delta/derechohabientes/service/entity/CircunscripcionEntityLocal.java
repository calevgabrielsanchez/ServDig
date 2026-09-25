package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;

@Local
public interface CircunscripcionEntityLocal {

	TramiteCircunscripcionForanea getCircunscripcionForanea(Long idCircunscripcion) throws Exception;
	TramiteCircunscripcionForanea getCircunscripcionForanea(Long idPersona, AsignacionNSS nss, boolean autorizacion) throws DerechohabientesBusinessException, Exception;
	TramiteCircunscripcionForanea getSuspencionCircunscripcionForane(Long idTramiteSuspencion) throws Exception;
	void updateCircunscripcionForanea(TramiteCircunscripcionForanea circunscripcion) throws DerechohabientesBusinessException,Exception;
	void saveCircunscripcionForanea(TramiteCircunscripcionForanea circunscripcion) throws DerechohabientesBusinessException,Exception;
}
