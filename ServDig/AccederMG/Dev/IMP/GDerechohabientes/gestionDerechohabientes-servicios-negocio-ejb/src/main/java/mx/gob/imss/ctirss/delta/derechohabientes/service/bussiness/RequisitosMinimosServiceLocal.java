package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

@Local
public interface RequisitosMinimosServiceLocal {

	/**
	 * Metodo para saber si un nss puede o no realizar un tipo de tramite, el metodo consulta el parentesco con el que cuenta 
	 * el idasignacion nss y en caso de ser pensionado busca los tramites que puede hacer el pensionado, en caso de ser un asegurado
	 * se buscaran las modalidades que tiene activas y se vera cual de ellas puede hacer el tramite solicitado, en caso de que ninguna
	 * de las modalidades lo pueda hacer, el metodo retorna false
	 * @param idAsignacionNss
	 * @param idTipoTramite
	 * @param idModalidadUltimoMovimiento -  se usara en caso de que venga cuando el asegurado no tenga patrones vigentes, en caso de no venir
	 * y que el asegurado no tenga patrones vigentes, se buscar en base a la cabeza de grupo familiar
	 * @return
	 */
	Map<String, Object> tramitePermitidoParaAseguradoPensionado(Long idAsignacionNss, Long idTipoTramite) throws DerechohabientesBusinessException;
	
	Map<String, Object> tramitePermitidoParaAseguradoPensionado(CabezaGrupoFamiliar cabeza, Long idTipoTramite, Boolean consultarModalidades, List<Long> idsModalidades) throws DerechohabientesBusinessException;
	/*
	* Metodo para saber si un tramite es permitido para las modalidades
	 * @param idTipoTramite
	 * @param idModalidades
	 * @return
	 */
	Boolean tramitePermiditoPorModalidades(Long idTipoTramite, List<Long> idModalidades);
	
	/**
	 * Metodo para saber si el pensionado puede hacer un tipo de tramite en particular
	 * @param idTipoTramite
	 * @return
	 */
	Boolean tramitePermitidoParaPensionado(Long idTipoTramite);
	
	/**
	 * Metodo que reemplazara al metodo que recibe un tramite, un domicilio, un sujeto obligado y asignacion nss y la marca de patron imss
	 * por este que solo recibe un tramite registro que contiene el domicilio y el asignacionNss, y la cabeza que contiene al patron y el indicador
	 * valida los requisitos para poder hacer el tramite de registro y regresa un map con un atributo que puede ser true o false y un mensaje
	 * @param registro
	 * @param cabeza
	 * @return
	 */
	Map<String, Object> requisitosMinimosRegistro(TramiteRegistroDerechohabiente registro, CabezaGrupoFamiliar cabeza, Long idOrigenSolicitud, Long idUmfUsuario, Boolean consultarModalidades,List<Long> idsModalidades) throws DerechohabientesBusinessException;
	Map<String, Object> requisitosMinimosCorreccion(GrupoFamiliar integrante,AsignacionNSS asignacionNss, CabezaGrupoFamiliar cabeza,Boolean consultarMod, List<Long> idsModalidad) throws DerechohabientesBusinessException;
	/**
	 * Metodo que retornara un map con un valor correcto que puede ser true o false
	 * y un atributo mensaje que contendra el mensaje de error, se pretende eliminar validaPersonaRegistrada
	 * por eso de se puso la anotacion deprecated
	 * @param fisica
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Map<String, Object> validaPersonaRegistrada(Fisica fisica, Long idAsignacionNss) throws DerechohabientesBusinessException ;
	Map<String, Object> validacionesEdadPadresHijos(Fisica asegurado, Fisica integrantes, Long idParentesco, Long idOrigenSolicitud) throws IllegalArgumentException;
	Map<String, Object> validarExistenciaConcubinaConyugeEnOtroGrupo(Fisica integranteValidar, Long idAsignacionActual) throws Exception;
	Map<String,Object> validarSolicitudRegistroRechazada(AsignacionNSS asegurado, Long idParentesco) throws IllegalArgumentException;
	Long getTipoTramitePorParentesco(Long idParentesco);
	Boolean existePersonaConParentesco(Long idAsignacionNss, Long idParentesco,Integer estadoDerechohabiente, Boolean correccion, Long idPersona, Long subEstadoDerechohabiente) throws DerechohabientesWebSserviceException;
	
	
	/**
	 * Metodo para validar si ya exisgte una solicitud de registro de asegurado o pensionado, recibiendo los siguientes
	 * parametros
	 * @param asignacionNSS
	 * @param idOrigenSolicitud - el id del origen desde donde se esta realizando la solicitud
	 * @return map<string, object> con los siguientes atributos
	 * correcto - true o false(en caso de que ya exista una solicitud pero haya sido iniciada desde otro medio)
	 * mensaje - El mensaje que mando el metodo
	 * solicitudActiva - en caso de que exista una solicitud de registro de esegurado o pensionado
	 */
	Map<String, Object> obtenerSolicitudRegistroAseguradoPensionado(AsignacionNSS asignacionNSS, Long idOrigenSolicitud);
	
	
	Map<String, Object> obtenerSolicitudCambioClinica(AsignacionNSS asignacionNSS, Long idOrigenSolicitud);
}
