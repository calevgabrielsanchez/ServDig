package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.Set;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.TramiteDerechoabienteTSPIException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.VarianteRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

@Remote
public interface TramitesDerechohabientesTSPIRemote {
	
	/**
	 * Metodo encargado de hacer las validaciones de prerrequisitos antes de iniciar un tramite de regisro de beneficiarios
	 * @param idAsignacionNSS
	 * @return
	 * @throws TramiteDerechoabienteTSPIException
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	CabezaGrupoFamiliar validaPrerrequisitosAseguradoRegistro(Long idAsignacionNSS) throws TramiteDerechoabienteTSPIException, 
	DerechohabientesBusinessException, Exception;
	
	/**
	 * Valida si ya existe la persona registrada en el grupo familiar
	 * @param fisica
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws TramiteDerechoabienteTSPIException
	 */
	Fisica validaPersonaRegistradaEnGrupoFamiliar(Fisica fisica, Long idAsignacionNss) throws DerechohabientesBusinessException, 
	TramiteDerechoabienteTSPIException;
	
	/**
	 * Genera un maoa con las variantes que puede tener el registro de beneficiarios
	 * @param idParentesco
	 * @param idOrigen
	 * @return
	 */
	Set<VarianteRegistroEnum> getVarianteRegistro(int idParentesco, int idOrigen);
	
	
	/**
	 * Metodo encargado de guardar y finalizar el registro de un derechohabiente identifica el origen para saber en que estatus
	 * dejar la solicitud, realiza las validaciones de negocio respectivas para el registro de beneficiarios
	 * @param tramiteRegistro
	 * @param cabeza
	 * @param idOrigenSolicitud
	 * @param idUmfUsuario
	 * @throws DerechohabientesBusinessException
	 * @throws TramiteDerechoabienteTSPIException
	 */
	Solicitud  guardaSolicitudRegisroDerechohabiente(TramiteRegistroDerechohabiente tramiteRegistro,
	CabezaGrupoFamiliar cabeza, Long idOrigenSolicitud)  throws DerechohabientesBusinessException, 
	TramiteDerechoabienteTSPIException;
	
	/**Metodo encargado de finalizar las solicitudes de prorroga por estudio o registro  de derechohabientes de TSPI por internet
	 * valida los requisitos minimos para dicho tramite
	 * @param folioSolicitud
	 * @return
	 */
	Solicitud finalizaSolicitudDerechohabientesTSPIInternet(String folioSolicitud)
			throws SolicitudNoEncontradaException, IllegalArgumentException, TramiteDerechoabienteTSPIException;

	
	/**
	 * Metodo encargado de buscar una persona por CUPR en RENAPO y BDTU aplica las validacion y reglas de negocio para la busqueda de personas para asignaion por iternet
	 * cachando las exepciones de negocio y las de servicios externos
	 * @param curp
	 * @return Fisica con la informacion de la persona localizada en BDTU o RENAPO
	 * @throws IllegalArgumentException
	 * @throws TramiteDerechoabienteTSPIException
	 * @throws DerechohabientesBusinessException
	 * @throws ClienteWebserviceRenapoCurpException
	 */
	Fisica busquedaFisicaPorCuprTramiteRegistroTCPI(String curp)
			throws IllegalArgumentException, TramiteDerechoabienteTSPIException, DerechohabientesBusinessException, ClienteWebserviceRenapoCurpException;
	
	/**
	 * Metodo encargado de validar los requisitos para la prorroga y su gardado en base de datos cuando el origen es INTERNET TSPI 
	 * solo registra el tramite sin finalizarlo
	 * @param tramiteProrroga
	 * @param usuario
	 * @param idOrigenSolicitud
	 * @return
	 * @throws TramiteDerechoabienteTSPIException
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud guardaProrrogaTSPI(TramiteProrroga tramiteProrroga,
			Usuario usuario, Long idOrigenSolicitud) throws TramiteDerechoabienteTSPIException, DerechohabientesBusinessException;
	
	
}
	
