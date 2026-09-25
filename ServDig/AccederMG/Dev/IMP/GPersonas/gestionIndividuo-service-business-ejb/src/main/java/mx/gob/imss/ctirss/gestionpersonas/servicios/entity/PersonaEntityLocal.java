package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.persistence.DicTipoIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Local
public interface PersonaEntityLocal {

    Fisica altaPersonaFisica(Fisica personaFisica);

    List<Fisica> buscarPersonaFisica(Fisica personaFisica, int iRegistroInicial, int iRegistrosPorPagina);

	String getRazonSocial(Long idPersona, Long tipoPersona);
	
	String getRazonSocial(String rfc, String razonSocial);

    Long contarNumPersonasTotal();
    
    int getTotalRegistrosBusquedaPersonaFisica();
    
    //ESTE METODO HABRA QUE QUITARLO POSTERIORMENTE PORQUE EL SERVICIO DE LUCIO IMPLEMENTARA ESTA FUNCIONALIDAD PASANDOLE UNICAMENTE UN OBJETO Persona con el idPersona
    /**
     * SRG 100512
     * Metodo que busca todos los domicilios relacionados con un idPersona
     * @param idPersona
     * @return
     */
    List<Domicilio> buscarDomiciliosPersona(Long idPersona);
    
    //ESTE METODO YA NO ES NECESARIO DE NUESTRO LADO YA QUE LUCIO IMPLEMENTA ESTA FUNCIONALIDAD EN SU SERVICIO
    /**
     * SRG 110512
     * Metodo que busca todos los medios de contacto relacionados con un idPersona
     * @param idPersona
     * @return
     */
//    List<MedioContacto> buscarMediosContactoPersona(Long idPersona);
    
    
    /**
     * Servicio para actualizar una Persona, pero de momento s�lo se actulizar� la fecha de defunci�n
     * @param persona
     */
    void actualizarPersona(Fisica fisica) throws PersonaNoEncontradaException;
    
    List<Serie> getSeriesNss(Long idDelegacion, Long idSubDelegacion);
    
	/**
	 * Este metodo recibe un objeto Serie del cual extraemos el idSerie y regresamos un objeto Serie completo
	 * @param serie
	 * @return
	 */
    Serie getSerie(Serie serie);
    
    /**
     * 191807 140912
     * Metodo encargado de registrar un identificador relacionado con una persona fisica
     * @param fisica
     */
    List<DicTipoIdentificador> registrarIdentificador(Fisica fisica);
    
    /**
     * 191807 081012
     * Metodo encargado de actualizar una persona fisica en BD
     * @param fisica
     */
    Fisica actualizarPersonaFisica(Fisica fisica);

	Fisica buscarPersonaPorId(Long idPersona);
   
	/**
	 * M�todo para modificar una persona fisica, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona fisica, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 * @throws PersonaNoEncontradaException
	 */
	void afectarDatosPersona(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaNoEncontradaException;
	
	/**
	 * M�todo para modificar las calificaciones de una persona, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona fisica, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 * @throws PersonaNoEncontradaException
	 */
	void afectarCalificacionesPersona(AfectarDatosPersonaWrapper datosPersona);

	/**
	 * Obtiene el nombre de una persona f�sica o moral
	 * 
	 * @param persona con el id de la persona y el tipo de persona setteado 
	 * @return
	 */
	String obtenerNombrePersona(Persona persona);
	
	 /**
	  * Metodo que busca las personas que cuentan con una calificaci�n por curp 
	  * @return
	  * @throws PersonaNoEncontradaException
	  */
	 List <DitPersona> getPersonasConCalificacionByCurp(String curp);

	/**
	 * Metodo que busca las personas que cuentan con una calificaci�n por curp
	 * @return
	 * @throws PersonaNoEncontradaException
	 */
	List <DitPersona> getPersonaEscVirtualByCurp(String curp);

	/**
	 * Obtiene la(s) persona(s) con su NSS a trav�s del CURP.
	 * Devuelve una lista con las personas encontradas ya con
	 * su NSS setteado.
	 * 
	 * @param curp
	 * @return
	 */
	List<Fisica> obtenerPersonaNssByCurp(String curp);

    /**
     * Obtiene la(s) persona(s) con su NSS a trav�s del CURP sin validar el campo IND_ACTIVO.
     * Devuelve una lista con las personas encontradas con su NSS.
     * @param curp Curp de la persona
     * @return List<Fisica> La lista de personas encontradas
     */
    List<Fisica> obtenerPersonaNssByCurpNoIndActivo(String curp);

	/**
	 * Obtiene el (los) NSS(s) de una persona a trav�s de su ID, si la bandera
	 * soloVigente se recibe en true, la consulta s�lo considerar� como vigente
	 * el (los) NSS(s) con fecRegistroBaja nula e indActivo igual a 1 o nulo
	 * 
	 * @param idPersona
	 * @param soloVigente
	 * @return
	 * @throws PersonaConVariosNSSException 
	 * @throws PersonaSinNSSException 
	 */
	List<String> obtenerNssPersona(Long idPersona, boolean soloVigente)
			throws PersonaConVariosNSSException, PersonaSinNSSException;

	/**
	 * Obtiene la CURP de una perosna a traves de su ID
	 * @param idPersona
	 * @return
	 */
	String obtenerCurpPersona(Long idPersona);

	/**
	 * Obtiene la edad de una persona a traves de su ID
	 * @param idPersona
	 * @return
	 */
	Integer obtenerEdadPersona(Long idPersona);
	
	List<AsignacionNSS> obtenerNsssByCurp(String curp);
	/**
	 * Inserta los datos de la fiel contenidos en el objeto persona
	 * en la tabla DIT_DATOS_CERTIFICADO_FIEL
	 * @param persona
	 */
	void registrarFiel(Persona persona);
	
	/**
	 * Obtiene los datos de la tabla DIT_DATOS_CERTIFICADO_FIEL
	 * asociados  a la persona, este metodo busca por medio del cve_id_persona_fisica
	 * @param persona (id y tipo persona requeridos)
	 * @return Fiel
	 */
	Fiel obtenerDatosFiel(Persona persona);
	
	/**
	 * Metodo original para obtiener los datos de la tabla DIT_DATOS_CERTIFICADO_FIEL
	 * asociados  a la persona, este metodo busca por medio del cve_id_persona
	 * @param persona (id y tipo persona requeridos)
	 * @return Fiel
	 */
	Fiel obtenerDatosFielVersionOriginal (Persona persona);
	
	
	/**
	 * Agrega RFC a la tabla Dit_Persona_Fisica
	 * @param personaFisica
	 */
	void agregarDatosPersonaFisica(Fisica personaFisica);
	
	/**
	 * Actualiza el indicador de acreditado de la persona fisica o moral
	 * @param persona
	 */
	void actualizarIndAcreditado(Persona persona);
	
	
	/**
	 * Busca personas en la tabla DitPersona y DitGrupoFamiliar en base a la curp y el idAsignacionNss
	 * 
	 * @param curp
	 * @param idAsignacionNss
	 * @return
	 */
	public List<Fisica> buscarEnPersonaYGrupoFamiliar(final String curp, Long idAsignacionNss);
	
	
	
	int totalRegistroPersonaFMPorRFC(Persona persona);
	
	boolean registradoConFiel(long idPersona);
	
	Object actualizaFechaBajaEntidad(Object entidad);
	Fisica obtenerPersonaPorId(Long idPersona);
	
	Fisica obtenerInformacionDatosAsegurado(String nss, String curp);

	/**
	 * Metodo que busca a la persona que hizo el registro de portal con fiel en casp de no encontrar datos regresa nulo
	 * @param crup
	 * @return
	 * @throws Exception
	 */
	Fisica getFisicaBySolicitudRegistroPortalConFiel(String curp) throws Exception;
	
}

