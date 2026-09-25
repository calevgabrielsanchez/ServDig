package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;

@Local
public interface AcuerdoDerechohabienteEntityLocal {
	
	TramiteAcuerdoDh saveAcuerdoDerechohabiente(TramiteAcuerdoDh tramiteAcuerdo);
	TramiteAcuerdoDh getAcuerdoDerechohabiente(Long idAsignacionNSS, Long idPersona, Long idEstado);
	TramiteAcuerdoDh getTramiteAcuerdoById(Long cveAcuerdo);
	Boolean tieneAcuerdoVigente(Long idAsignacionNSS, Long idPersona);
}
