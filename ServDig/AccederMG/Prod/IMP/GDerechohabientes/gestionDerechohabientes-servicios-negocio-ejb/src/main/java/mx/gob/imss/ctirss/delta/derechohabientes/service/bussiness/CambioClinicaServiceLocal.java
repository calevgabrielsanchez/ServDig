package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Local
public interface CambioClinicaServiceLocal {
	
	Map<String, Object> agregarTramiteCambioClinicaDependiente(TramiteCorreccionDerechohabiente correccion, AsignacionNSS nss, Boolean patronImss, Date fechaCambioMTC,
			Long idSolicitud, GrupoFamiliar afectado, Boolean asignacionDomicilio, Map<String, Object> validacionesFechaCambioMedico)
			throws DerechohabientesBusinessException;
	
	Date getFechaCambioDeMedico(AsignacionNSS nss, MedicoEnTurno medicoEnTurnoDestino, List<Long> idsPersonasExcluirDeConsulta, 
			Boolean asignacionDomicilio, Date fechaCambioMedicoTurno, Boolean isAseguradoConyuge) throws DerechohabientesBusinessException;
	
	List<GrupoFamiliar> getPadresConcubinasParaCambio(AsignacionNSS nss, Integer patronIMSS) throws Exception;
	
	Map<String,Object> getFechaCambioYDatosCambioMedico(AsignacionNSS nss, MedicoEnTurno medicoEnTurnoDestino, List<Long> idsPersonasExcluirDeConsulta, 
			Boolean asignacionDomicilio, Date fechaCambioMedicoTurno, Boolean isAseguradoConyuge) throws DerechohabientesBusinessException;
	
	Solicitud crearSolicitudCambioClinica(TramiteCorreccionDerechohabiente correccion, GrupoFamiliar asegurado, AsignacionNSS nss, 
			CabezaGrupoFamiliar cabeza, Boolean consultar,List<GrupoFamiliar> padresConcubinas, Usuario usuario, OrigenSolicitudEnum origen) throws Exception ;
	
	Solicitud finalizaSolicitudCambioClinica(Solicitud solicitud, AsignacionNSS nss, CabezaGrupoFamiliar cabeza, 
			Map<String, Object> validacionesCambioMedico) throws ImpactaAlmacenesWSException, DerechohabientesBusinessException;
	
}
