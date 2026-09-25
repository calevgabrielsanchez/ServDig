package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;

@Remote
public interface AcuerdoDerechohabienteServiceRemote {

	List<GrupoFamiliar> findCandidatosAcuerdo(Long idAsignacionNSS) throws DerechohabientesBusinessException;
	Solicitud crearSolicitudAcuerdo(GrupoFamiliar integrante,TramiteAcuerdoDh tramite, Usuario usuario, OrigenSolicitudEnum origenSolicitud) throws DerechohabientesBusinessException;
	Solicitud finalizarSolicitudAcuerdo(Solicitud solicitud, AsignacionNSS asignacionNSS) throws SolicitudNoValidaException, SolicitudException;
}
