package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

@Local
public interface TramiteServiceLocal {
	
	Solicitud consultarSolicitud(Solicitud solicitud) throws SolicitudNoEncontradaException;
	void actualizarSolicitudAConcluida(Solicitud solicitud) throws SolicitudNoEncontradaException ;
	void actualizaXMLTramite(Tramite tramite)throws TramiteNoEncontradoException, IllegalArgumentException ;
	/**
	 * Metodo para crear y guardar una solicitud recibiendo los siguientes parametros
	 * @param derechohabiente - GrupoFamiliar : el integrante que sera afectado por el tramite
	 * @param tipoTramite - TipoTramiteEnum : el tipo del tramite
	 * @param usuario - Usuario : el usuario que esta realizando la solicitud
	 * @param nss - AsignacionNSS : El asegurado cabeza de grupo familiar
	 * @param tipoSolicitud - TipoSolicitudEnum - El tipo de la solicitud
	 * @param tramite - Tramite : Objeto que extiende de tramite para guardarlo como xml
	 * @return Solicitud
	 */
	Solicitud guardarSolicitudConInfoTramite(TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, Tramite tramite, OrigenSolicitudEnum origenSol) throws DerechohabientesBusinessException;
	
	Solicitud guardarBaja(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoBaja, Usuario usuario, AsignacionNSS nss, TramiteBajaDerechohabiente baja, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	
	/**
	 * Este metodo se encarga de guardar el tramite y solicitud de prorroga
	 * @author juan.osorioal
	 * @param derechohabiente
	 * @param tipoBaja
	 * @param usuario
	 * @param nss
	 * @param tramite
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud guardarProrroga(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoBaja, Usuario usuario, AsignacionNSS nss, TramiteProrroga tramite, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	 TramiteBajaDerechohabiente getBaja(Long idTramite) throws DerechohabientesBusinessException;
	Solicitud guardarCorreccion(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud guardarCorreccion(List<GrupoFamiliar> derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud guardarCircunscripcion(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,TramiteCircunscripcionForanea circunscripcion, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	TramiteCorreccionDerechohabiente getCorreccion(Long idTramite) throws DerechohabientesBusinessException;
	TramiteCircunscripcionForanea getCircunscripcion(Long idTramite) throws DerechohabientesBusinessException; 
	 void actualizarSolicitud(Long idSolicitud, EstadoSolicitudEnum estadoSolicitud,EstadoTramiteEnum estadoTramite, Boolean resultado, 
			RazonResultadoEnum razonResultado, String observaciones, Tramite tramite, Fisica personaUsuario) throws DerechohabientesBusinessException ;
	Tramite saveTramiteCorreccionDependiente(Tramite correccion, Long idSolicitud) throws DerechohabientesBusinessException;
	Solicitud guardarSolicitud(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, Tramite tramite, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud guardarSolicitudEstado(GrupoFamiliar derechohabiente,
			TipoTramiteEnum tipoTramite, Usuario usuario, AsignacionNSS nss,
			TipoSolicitudEnum tipoSolicitud, Tramite tramite, OrigenSolicitudEnum origen, Long idEstadoSolicitud, Long idEstadoTramite) throws DerechohabientesBusinessException;
}
