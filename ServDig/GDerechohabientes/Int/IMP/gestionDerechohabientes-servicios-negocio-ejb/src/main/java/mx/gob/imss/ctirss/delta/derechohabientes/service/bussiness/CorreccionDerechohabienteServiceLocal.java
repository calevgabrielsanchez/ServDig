package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Local
public interface CorreccionDerechohabienteServiceLocal {

	List<Long> guardarTramiteAsignacionUmfDependiente(GrupoFamiliar afectado, Long idSolicitud, Integer patronIMSS, Boolean isRegistro, Boolean asignacionDomicilio) throws DerechohabientesBusinessException, Exception;
	List<Long> guardarTramiteCambioClinicaDependiente(TramiteCorreccionDerechohabiente correccion, Fisica usuario,AsignacionNSS nss, Boolean patronImss, Date fechaCambioMTC, Long idSolicitud, GrupoFamiliar afectado, Boolean asignacionDomicilio) throws DerechohabientesBusinessException;
	List<Long> guardarTramiteCircunscripcionDependiente(TramiteCircunscripcionForanea circunscripcion, Usuario usuario, AsignacionNSS nss, Long idSolicitud, GrupoFamiliar afectado) throws DerechohabientesBusinessException;
	List<Long> guardarTramiteCambioMedicoDependiente(GrupoFamiliar nuevosDatos, Long idSolicitud, Boolean asignacionDomicilio) throws DerechohabientesBusinessException;
	Solicitud finalizarSolicitudCorreccionDatos(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, DerechohabientesBusinessException;
	
}
