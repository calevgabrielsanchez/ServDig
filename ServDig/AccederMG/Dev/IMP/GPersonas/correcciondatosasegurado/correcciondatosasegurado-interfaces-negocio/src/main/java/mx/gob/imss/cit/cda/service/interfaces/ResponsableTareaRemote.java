package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Date;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface ResponsableTareaRemote {

	void avanzarSolitudInformacion(Solicitud solicitud, String idTarea, String observacion,String usuario, int tipoUsr)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException;
	
	void rechazarSolicitud(Solicitud solicitud, String idTarea,String usuario)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException;
	
	void autorizarSolicitud(Solicitud solicitud, String idTarea, String usuario )
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException;

	void avanzarTareaResponsable(Solicitud solicitud, String idTarea) throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException, NumberFormatException;

	void cancelarTareaResponsable(Solicitud solicitud, String idTarea)
			throws SolicitudException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException, NumberFormatException,
			SolicitudNoEncontradaException,TramiteNoEncontradoException;
	
	void cancelarTareaAutorizador(Solicitud solicitud, String idTarea, String usuario)
			throws SolicitudException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException, NumberFormatException,BPMException,
			SolicitudNoEncontradaException,TramiteNoEncontradoException;

	Long iniciarWorkFlow(Long bp, InicioTramite inicioTramite, Solicitud solicitud)
			throws TareaInicialException, TereaSinUsuarioAsignadoException;
	
	void reasignarTareaAutorizador(Solicitud solicitud,String idTarea, String observacion ,String usuario)throws SolicitudNoEncontradaException,TramiteNoEncontradoException,BPMException;
	
	void actualizarEstadoBdocInstancia(String idTarea,String estado,Date fecha) throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException;
	
	void actualizarBdocInstanciaCertificacion(String idTarea,String estado, String usuario) throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException;
}
