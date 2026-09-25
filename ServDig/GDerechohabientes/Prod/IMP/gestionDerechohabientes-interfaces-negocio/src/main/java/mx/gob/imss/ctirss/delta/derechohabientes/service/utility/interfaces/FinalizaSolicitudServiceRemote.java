package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Remote
public interface FinalizaSolicitudServiceRemote {

	void mandaMovimientoWS(GrupoFamiliar grupoFamiliar) throws 
	IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	
	/**
	 * Método que finaliza una solicitud
	 * @param solicitud que se va a finalizar
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	 void finalizaSolicitud(Solicitud solicitud) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	
	 void finalizaSolicitudRegistroVentanilla(Solicitud solicitud, GrupoFamiliar grupoFamiliar)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void finalizaSolicitudRegistroWeb(Solicitud solicitud, GrupoFamiliar grupoFamiliar)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	
	 /**
	  * Método para enviar una baja al WS
	  * @param solicitud de baja
	  * @param baja objeto que contiene el derechohabiente para baja
	  * @param isNuevaBaja True si es un nuevo registro de baja, False si es una actualizacion de un registro de baja
	  * @throws IllegalArgumentException
	  * @throws Exception
	  */
	 void finalizaSolicitudBajaDerechohabiente(Solicitud solicitud, BajaDerechohabienteDto baja, Boolean isNuevaBaja)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void finalizaCorreccionDatosDerechohabienteInternet(GrupoFamiliar grupoFamiliar) throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void modificarCambioTurnoMedioConsultorio(Solicitud solicitud, AsignacionNSS asignacionNSS)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void modificarCambioUMF(String idUsuario, List<Long> candidatos, AsignacionNSS asignacionNSS)throws IllegalArgumentException, ImpactaAlmacenesWSException, ImpactaAlmacenesWSException, Exception;
	 
	 void modificarCambioDerechohabiente(String idUsuario, TramiteCorreccionDerechohabiente derechohabiente, AsignacionNSS asignacionNSS)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void modificarSuspensionCircunscripcion(Solicitud solicitud, String idUsuario, AsignacionNSS asignacionNSS)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void modificarDerechohabientePortal(Solicitud solicitud)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void modificarCambioClinicaPortal(List<GrupoFamiliar> candidatosCambio, Boolean cambioClinica)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void finalizarSolicitudTramites(Solicitud solicitud, String idusuario, AsignacionNSS asignacionNSS)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	 void finalizarSolicitudTramitesConGF(Solicitud solicitud, List<GrupoFamiliar> listaGrupoFamiliar)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 /**
	  * Método para guardar el registro y modificación de estudiantes en los almacenes
	  * @param grupoFamiliar que se va a registrar
	  * @param esNuevoRegistro true si es un nuevo registro false si es modificación
	  * @throws IllegalArgumentException
	  * @throws Exception
	  */
	 void finalizaRegistroEstudiante(GrupoFamiliar grupoFamiliar, Boolean esNuevoRegistro)throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
}
