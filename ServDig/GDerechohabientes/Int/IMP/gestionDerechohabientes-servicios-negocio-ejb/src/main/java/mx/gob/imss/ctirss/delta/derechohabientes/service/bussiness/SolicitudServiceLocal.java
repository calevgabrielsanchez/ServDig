package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;


@Local
public interface SolicitudServiceLocal {
	
	Solicitud rechazarSolicitud(Long idSolicitud, Long idRazonRechazo, String obervaciones, Fisica usuarioPersona) throws DerechohabientesBusinessException;
	Solicitud guardarSolicitud(GrupoFamiliar derechohabiente, TipoTramiteEnum tipoTramite,Usuario usuario,AsignacionNSS nss, TipoSolicitudEnum tipoSolicitud, String observaciones, Domicilio domicilio, Tramite tramite) throws DerechohabientesBusinessException, Exception;
	void marcarAtendidaSolictud(Long idSolicitud, String observaciones,Fisica personaUsuario) throws DerechohabientesBusinessException;
	void cambiarEdoSolicitud(Long idSolicitud, EstadoSolicitudEnum estadoSol,EstadoTramiteEnum estadoTram, Boolean resultado, RazonResultadoEnum razonRes, String observaciones,Fisica personaUsuario) throws DerechohabientesBusinessException;
	void marcarPendienteAutorizacion(Long idSolicitud, String observaciones,Fisica personaUsuario) throws DerechohabientesBusinessException, Exception;
	void marcarPendienteAutorizacion(Solicitud solicitud, String observaciones,Fisica personaUsuario) throws DerechohabientesBusinessException, Exception;
	TramiteCorreccionDerechohabiente detalleCorreccionDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception;
	void guardarTramiteDependienteCorreccion(Long idSolicidud,Long idPersona, TipoTramiteEnum tipoTramite, String observaciones) throws DerechohabientesBusinessException;
	public Boolean tramitesAdemasDeRegistro(Long idPersona, AsignacionNSS nss);
	void actualizarXmlTramite(Tramite tramite);
}
