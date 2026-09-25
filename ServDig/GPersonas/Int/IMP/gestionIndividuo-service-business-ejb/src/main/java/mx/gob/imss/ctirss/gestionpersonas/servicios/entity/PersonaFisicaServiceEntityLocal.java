package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;

/**
 * 121012
 * @author ICCSRG
 *
 */
@Local
public interface PersonaFisicaServiceEntityLocal {
	
	Boolean isSocio(Long idPersona);
	
	Boolean isPersonaAutorizada(Long idPersona);
	/**
	 * Metodo encargado de registrar una persona fisica en BD
	 * @param ditPersona
	 */
	
	Fisica registrar(DitPersona ditPersona);
	/**
	 * Metodo encargado de actualizar una persona fisica en BD
	 * @param ditPersona
	 */
	void actualizar(DitPersona ditPersona);
	
	
	/**
	 * Método que obtiene el id de una persona física, 
	 * buscado a través del id de la persona
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	Long obtenerIDPersonaFisica(Long idPersona) throws PersonaFisicaNoEncontradaException;

    /**
     * Método que obtiene el id de una persona física,
     * buscado a través del id de la persona
     *
     * @param idPersona
     * @return
     * @throws PersonaFisicaNoEncontradaException
     */
    Long obtenerIDPersonaFisicaEscVirtual(Long idPersona) throws PersonaFisicaNoEncontradaException;

	/**
	 * Método que crea una persona física, sólo se crea en dit_persona_fisica,
	 * no crea ninguna relación con otras entidades
	 * 
	 * @param fisica
	 * @return
	 */
	Fisica guardarPersonaFisica(Fisica fisica);
	
	/**
	 * Método para modificar una persona fisica, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 07/03/2013
	 * @param datosPersona - incluye la persona fisica, así como las banderas 
	 * 		que indican la información a modificar
	 */
	void afectarDatosPersonaFisica(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaFisicaNoEncontradaException;
	
	/**
	 * Método que realiza la búsqueda de persona por datos básicos (nombre,
	 * primer apellido, segundo apellido (opcional), fecha y lugar de nacimiento
	 * y sexo. Primero realiza la búsqueda por fecha de nacimiento exacta y/o  busca sólo por mes y año de la fecha de nacimiento
	 * @param fisica
	 * @return
	 */
	List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImss(Fisica fisica);
	
	
	
	/**
	 * Método que realiza la búsqueda de persona por datos básicos y que tenga NSS (nombre,
	 * primer apellido, segundo apellido (opcional), fecha y lugar de nacimiento
	 * y sexo. Primero realiza la búsqueda por fecha de nacimiento exacta y/o  busca sólo por mes y año de la fecha de nacimiento
	 * @param fisica
	 * @return
	 */
	List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImssConNSS(Fisica fisica);
	
	List<AsignacionNSS> localizarNssPorDatosBasicos(Fisica fisica);
	
	List<Fisica> localizarPersonaFisicaPorDatosBasicosConFechaYSinFechaNacimiento(Fisica fisica);
	
	/**
	 * Se obtiene la persona fisica por nss
	 * @param nss
	 * @return Fisica
	 * @throws NssRelacionadoVariasPersonasException 
	 * @throws PersonasNoLocalizadasException 
	 */
	Fisica localizarPersonaPorNss(String nss)
			throws PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException;
	
    /**
     *  Obtiene los datos (solo datos personales) de la persona fisica en base al idPersonaFisica
     * 
     * @param  idPersonaFisica
     * @return Fisica
     */
    Fisica getPersonaFisica(Long idPersonaFisica);
 
    DitPersona getDitPersona(Long idPersona);
    DitPersonaFisica getDitPersonaFisica(Long idPersona)throws PersonaFisicaNoEncontradaException;
    void actualizarDitPersona(DitPersona ditPersona);
    
    /**
     * Servicio que consulta si una persona se encuentra en en asignacion de nss cl3 activo
     * busca por datos basicos sin considerar la fecha de nacimiento
     * @param fisica
     * @return
     */
    List<AsignacionNSS> localizarNssCl3PorDatosBasicosSinFechaNac(Fisica fisica);
	
	Fisica localizarPersonaPorNssCertificacion(String nss)
	throws PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException;

	Long registrarNuevaPersonaCDA(DitPersona ditPersona, Long idPersonaAnterior, Long idAsignacionNSS) throws Exception;

}
