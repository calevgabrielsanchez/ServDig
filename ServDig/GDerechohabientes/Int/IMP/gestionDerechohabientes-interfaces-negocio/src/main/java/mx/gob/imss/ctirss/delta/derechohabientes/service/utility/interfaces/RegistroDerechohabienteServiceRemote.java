package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;


/**
 * @author Mario Teran Blanco
 * @version 2 Modifica. Juan Manuel Marquez Hernandez 03/07/2012
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */

@Remote
public interface RegistroDerechohabienteServiceRemote {
	
	/**
	 * Metodo para obtener la relacion de persona domicilio
	 * @param idPersona
	 * @param tipoDomicilio
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	PersonaDomicilio getPersonaDom(Long idPersona, Long tipoDomicilio) throws DerechohabientesBusinessException, Exception;
	boolean maximoParentesco(Long idAsignacionNss, Long idPerentesco) throws DerechohabientesBusinessException, Exception;
	boolean modalidadParentesco(Long idModalidad, Long idPerentesco) throws DerechohabientesBusinessException, Exception;
	/**
	 * Metodo para registrar una solicitud a partir de un tramite registro
	 * @param registro
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud registraSolicitud(TramiteRegistroDerechohabiente registro, Long idOrigenSolicitud) throws DerechohabientesBusinessException, SolicitudNoValidaException;
	
	/**
	 * Metodo que finaliza la solicitud desde internet
	 * @param solicitud
	 * @throws DerechohabientesBusinessException
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 */
	Solicitud finalizarSolicitudRegistro(Solicitud solicitud) throws DerechohabientesBusinessException,
	SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException,Exception, ImpactaAlmacenesWSException;
	
	/**
	 * 
	 * @param registro
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	List<GrupoFamiliar> integrantesParentesco(Long idAsignacionNss,Long parentesco) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * 
	 * @param idPersona
	 * @param parentescos
	 * @param idPersonaAsegurado
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	void parentescoSimilarFlag(Long idPersona,List<Long> parentescos, Long idPersonaAsegurado, String nssAsegurado) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Metodo para obtener los parentescos disponibles dentro de un grupo familiar
	 * @param idAsignacionNSS
	 * @return
	 */
	List<Parentesco> getListaParentescoDisponiblesPorIdAsignacionNSS(Long idAsignacionNSS);
	
	/**
	 * Metodo para obtener las razones de registro dependiendo del parentesco
	 * @param idRazonRegistro
	 * @param idParentesco
	 * @return
	 */
	List<RazonRegistro> getListaRazonRegistro(Long idRazonRegistro,Long idParentesco);
	
	/**
	 * Metodo que se usa para buscar a la persona regisrada en otros grupos familiar registrado con el mismo parentesco
	 * y ponerle una bandera en caso de que se encuentre
	 * @param persona
	 * @param parentesco
	 * @throws Exception
	 */
	void setearBanderaDeParentescoSimilar(Fisica persona, Parentesco parentesco, String nssAsegurado) throws Exception;
	
	/**
	 * Metodo para finalizar la solicitud de registro de derechohabientes mediante ventanilla
	 * @param solicitud
	 * @param cabeza
	 * @param modalidades
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 * @throws Exception
	 * @throws ImpactaAlmacenesWSException
	 * @throws SolicitudEnProcesoException
	 */
	Map<String, Object> guardarRegistroDerechohabiente(Solicitud solicitud, CabezaGrupoFamiliar cabeza, List<Modalidad> modalidades) 
	throws DerechohabientesBusinessException, SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, Exception, ImpactaAlmacenesWSException, SolicitudEnProcesoException;
	
	/**
	 * Metodo para guardar el cambio de lcinica y/o circunscripcion dependientes de un tramite de registro de derechohabientes
	 * @param solicitud
	 * @param cabeza
	 * @param integrante
	 * @throws DerechohabientesBusinessException
	 */
	void guardarCircunscripcionCambioUmf(Solicitud solicitud, CabezaGrupoFamiliar cabeza, GrupoFamiliar integrante) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo encargado de finalizar la solicitud de TSPI para registro de beneficiarios
	 * @param solicitud
	 * @param cabeza
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 * @throws Exception
	 * @throws ImpactaAlmacenesWSException
	 */
	Solicitud finalizarSolicitudRegistroTSPI(Solicitud solicitud,
			CabezaGrupoFamiliar cabeza)
			throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException, Exception, ImpactaAlmacenesWSException;
	
	/**
	 * Metodo para actualizar el correo electronico
	 * @param registro
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud actualizacionCorreo(TramiteActualizacionCorreo registro, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, SolicitudNoValidaException;
	
}
