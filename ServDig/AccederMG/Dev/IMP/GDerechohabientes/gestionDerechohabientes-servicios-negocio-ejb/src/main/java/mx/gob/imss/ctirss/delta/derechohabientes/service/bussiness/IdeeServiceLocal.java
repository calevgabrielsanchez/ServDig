package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.IdsIntegranteGrupo;

@Local
public interface IdeeServiceLocal {
	/**
	 * Metodo para generar los idees de un listado de derechohabientes
	 * el objeto IdsIntegranteGrupo debe contener al menos:
	 * 		-idAsignacionNSS : el id del nss (opcional siempre y cuando venga el nss)
	 * 		-nss : el nss (opcional siempre y cuando venga el nss)
	 * 		-idPersona:  el integrante del grupo familiar (obligatorio)
	 * 		-idPersonaDerechohabiente: el id de la tabla DIT_PERSONA_DERECHOHABIENTE (opcional, sino viene se actualizara o se insertara uno nuevo segun sea
	 * 			el caso, en caso de que existiara mas de un registor en la tabla no se actualizara a menos que en este atributo venga cual se quiere actualizar)
	 * 		-estudianteCL3: si es o no estudiante y se encuentra en la tabla CL3 (en caso de que venga nulo se tomara como false)
	 * @param datos
	 * @return
	 */
	List<IdsIntegranteGrupo> generarIdees(List<IdsIntegranteGrupo> datos); 
	
	/**
	 * Metodo para generar los idees de una lista de nss de estudiantes, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	Map<String, String> generarYGuardarIDEEPorNSsCL3(List<String> nssCl3);
	
	/**
	 * Metodo para generar los idees de una lista de ids de nss de estudiantes, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	Map<Long, String> generarYGuardarIDEEsPorIdAsignacionCL3(List<Long> idsNssCl3);
	
	/**
	 * Metodo para generar los idees de una lista de nss, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	Map<String, String> generarYGuardarIDEEPorNSs(List<String> nss);
	
	/**
	 * Metodo para generar los idees de una lista de ids de nss, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	Map<Long, String> generarYGuardarIDEEsPorIdAsignacion(List<Long> idsNss);
	
	String generarYGuardarIDEEPorIdNssCL3(Long idAsignacionNSS);
	
	/**
	 * Metodo para generar y actuzliar o insertar el IDEE
	 * al no especificarse persona, solo se generara para el asegurado o pensionado,
	 * si existiera mas de un integrante dentro del grupo no se generará o en dado caso de que el id de la persona
	 * registrada como asegurado no sea el nmismo que el relacionado al nss
	 * @param nss
	 * @return
	 */
	String generarYGuardarIDEEParaNSSCL3(String nss);
	
	String generarYGuardarIDEEPorIdNss(Long idAsignacionNSS);
	
	/**
	 * Metodo para generar y actuzliar o insertar el IDEE
	 * al no especificarse persona, solo se generara para el asegurado o pensionado,
	 * si existiera mas de un integrante dentro del grupo no se generará o en dado caso de que el id de la persona
	 * registrada como asegurado no sea el nmismo que el relacionado al nss
	 * @param nss
	 * @return
	 */
	String generarYGuardarIDEEParaNSS(String nss);
	/**
	 * Metodo para guardar o actualizar el IDEE de un estudiante a partir del id del nss
	 * @param idNss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	String generarYGuardarOActualizarIdeePorIdNssCL3(Long idNss, Long idPersona, Long idPersonaDerechohabiente);
	/**
	 * Metodo para guardar o actualizar el IDEE de un estudiante a partir del numero nss
	 * @param nss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	String generarYGuardarOActualizarIdeePorNSSCL3(String nss, Long idPersona, Long idPersonaDerechohabiente);
	/**
	 * Metodo para guardar o actualizar el IDEE para algun integrante de un grupo familiar donde el nss no pertenece a un estudiante
	 * @param nss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	String generarYGuardarOActualizarIdeePorIdNss(Long idNss, Long idPersona, Long idPersonaDerechohabiente);
	/**
	 *  Metodo para guardar o actualizar el IDEE para algun integrante de un grupo familiar donde el nss no pertenece a un estudiante
	 * @param nss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	String generarYGuardarOActualizarIdeePorNSS(String nss, Long idPersona, Long idPersonaDerechohabiente);
	
	/**
	 * Metodo para consultar y actualizar los idees de la tabla AUX_PD_IDEEMENOR18_ACTUALIZA
	 * @param numeroFilas
	 * @return
	 */
	Boolean actualizarIDEETablaAux(Integer numeroFilas);
	/**
	 * Metodo para solo generar el IDEE (Identificador De Expediente Electronico) recibiendo los siguientes parametros 
	 * @param nss - El nss del grupo familiar al que pertenece la persona (obligatorio)
	 * @param numCalidad - el numero de calidad del integrante dentro del grupo familiar (obligatorio)
	 * @param primerApellido - El apellido paterno del derechohabiente (obligatorio)
	 * @param segundoApellido - El apellido materno del derechohabiente  (opcional)
	 * @param nombre - el nombre del derechohabiente (obligatorio)
	 * @param fechaNacimiento - La fecha de nacimiento del derechohabiente (opcional si se cuenta con el mes y anio de nacimiento)
	 * @param mes - el mes de nacimiento de la persona a calcular el idee (opcional siempre y cuando se cuente con la fecha de nacimiento)
	 * @param anio - el anio de nacimiento de la persona a calcular el idee (opcional siempre y cuando se cuente con la fecha de nacimiento)
	 * @throws IllegalArgumentException - cuando falta algun dato para el calcula 
	 * @return String - IDEE
	 */
	String generarIDEE(String nss, Integer numCalidad,String nombre,String primerApellido, String segundoApellido,Date fechaNacimiento, Integer mes, Integer anio)
	throws IllegalArgumentException;
}
