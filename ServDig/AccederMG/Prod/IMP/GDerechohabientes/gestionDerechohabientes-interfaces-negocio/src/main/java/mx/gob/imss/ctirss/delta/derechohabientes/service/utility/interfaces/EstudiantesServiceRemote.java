package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Remote
public interface EstudiantesServiceRemote {

	Map<String, Object> finalizaAsignacionDomicilioUmfEstudiante(TramiteCorreccionDerechohabiente tramite, GrupoFamiliar estudiante, CabezaGrupoFamiliar cabeza, List<Modalidad> modalidades) throws DerechohabientesBusinessException, SolicitudNoValidaException;
	
}
