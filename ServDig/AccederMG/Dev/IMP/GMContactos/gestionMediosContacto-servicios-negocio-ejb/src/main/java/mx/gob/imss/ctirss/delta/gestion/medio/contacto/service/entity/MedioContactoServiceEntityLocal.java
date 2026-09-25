/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:MedioContactoServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity
 *  @Fecha:11/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface MedioContactoServiceEntityLocal {

	
	/**
	 * 
	 * @param mediosDeContacto
	 * @return
	 * @throws RegistrarMedioContactoException
	 */
	List<MedioContacto> registrarMedioDeContacto(
			List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException;
	
	/**
	 * 
	 * @param persona
	 * @return
	 * @throws PersonaSinMedioDeContactoException
	 */
	List<MedioContacto> consultarMediosDePersonaFisica(Persona persona)
			throws PersonaSinMedioDeContactoException;
	
	/**
	 * 
	 * @param persona
	 * @return
	 * @throws PersonaSinMedioDeContactoException
	 */
	List<MedioContacto> consultarMediosDePersonaMoral(Persona persona)
			throws PersonaSinMedioDeContactoException;
	
	/**
	 * 
	 * @param mediosDeContacto
	 * @return
	 * @throws RegistrarMedioContactoException
	 */
	List<MedioContacto> actualizarMedioDeContacto(
			List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException;
	
	/**
	 * 
	 * @param socio
	 * @return
	 */
	List<MedioContacto> consultarMediosSocio(Socio socio);
	
	/**
	 * 
	 * @param representante
	 * @return
	 */
	List<MedioContacto> consultarMediosRepresentanteLegal(RepresentanteLegal representante);
	
	/**
	 * 
	 * @param centroTrabajo
	 * @return
	 */
	List<MedioContacto> consultarMediosCentroTrabajo(CentroTrabajo centroTrabajo);
	
	/**
	 * 
	 * @param idTipoTramite
	 * @return
	 */
	List<Modulo> consultarModulosdeTramitePorTipo(Long idTipoTramite);
	
	/**
	 * Obtiene los tipos de medio de contacto activos
	 */
	List<TipoContacto> consultarTiposContacto();
	
	/**
	 * 
	 * @param persona
	 * @return
	 * @throws PersonaSinMedioDeContactoException
	 */
	List<MedioContacto> consultarMediosDePersona(Persona persona) throws PersonaSinMedioDeContactoException;
	
	/**
	 * Obtiene los medios de contacto fiscales de una persona, 
	 * siempre y cuando tenga un carácter fiscal
	 * 
	 * @param persona con el id_persona setteado
	 * @return lista de medios de contacto fiscales
	 * @throws PersonaSinMedioDeContactoException
	 */
	List<MedioContacto> consultarMediosFiscalesPersona(Persona persona)
			throws PersonaSinMedioDeContactoException;

	
	/**
	 * Método que registra un medio de contacto
	 *  
	 * @param medioContacto
	 * @return
	 * @throws RegistrarMedioContactoException
	 */
	MedioContacto registrarMedioDeContacto(MedioContacto medioContacto)
			throws RegistrarMedioContactoException;
	
	/**
	 * Método que actualiza un medio de contacto
	 * 
	 * @param medioContacto
	 * @return
	 * @throws RegistrarMedioContactoException
	 */
	MedioContacto actualizarMedioDeContacto(MedioContacto medioContacto)
			throws RegistrarMedioContactoException;
	
	/**
	 * Método que elimina un medio de contacto y la relacion que tenga con la
	 * persona (fisica o moral)
	 * 
	 * @param medioContacto
	 */
	void eliminarMedioDeContacto(MedioContacto medioContacto);

	/**
	 * Método que asocia el medio de contacto fiscal a la persona
	 * 
	 * @param cveMedioContacto
	 * @param cvePersonaFisica
	 * @throws RegistrarMedioContactoException
	 */
	void asociarMedioContactoPersona(Long cveMedioContacto,
			Long cvePersona) throws RegistrarMedioContactoException;

	/**
	 * Método que asocia el medio de contacto fiscal a la persona moral
	 * 
	 * @param cveMedioContacto
	 * @param cvePersonaMoral
	 * @throws RegistrarMedioContactoException
	 */
	void asociarMedioContactoPersonaMoral(Long cveMedioContacto,
			Long cvePersonaMoral) throws RegistrarMedioContactoException;
	
	/**
	 * Método que asocia el medio de contacto fiscal a la persona fisica
	 * 
	 * @param cveMedioContacto
	 * @param cvePersonaFisica
	 * @throws RegistrarMedioContactoException
	 */
	DitPersonafContacto registrarMedioContactoPersonafContacto(Long cveMedioContacto,
			Long cvePersona) throws RegistrarMedioContactoException;

	/**
	 * Método que asocia el medio de contacto fiscal a la persona moral
	 * 
	 * @param cveMedioContacto
	 * @param cvePersonaMoral
	 * @throws RegistrarMedioContactoException
	 */
	DitPersonamContacto registrarMedioContactoPersonamContacto(Long cveMedioContacto,
			Long cvePersonaMoral) throws RegistrarMedioContactoException;
		
	/**
	 * Método que asocia el medio de contacto fiscal a la persona física
	 * 
	 * @param cveMedioContacto
	 * @param cvePersonaFisica
	 * @throws RegistrarMedioContactoException
	 */
	void asociarMedioContactoFiscalPersonaFisica(Long cveMedioContacto,
			Long cvePersonaFisica) throws RegistrarMedioContactoException;

	/**
	 * Método que asocia el medio de contacto fiscal a la persona moral
	 * 
	 * @param cveMedioContacto
	 * @param cvePersonaMoral
	 * @throws RegistrarMedioContactoException
	 */
	void asociarMedioContactoFiscalPersonaMoral(Long cveMedioContacto,
			Long cvePersonaMoral) throws RegistrarMedioContactoException;	

	boolean existenciaCorreoPersonaPorId(Long idPersona, String correo);

	void asociarMedioContactoPersona(Long idPersona, String valor, Long tipoMedio);
}
