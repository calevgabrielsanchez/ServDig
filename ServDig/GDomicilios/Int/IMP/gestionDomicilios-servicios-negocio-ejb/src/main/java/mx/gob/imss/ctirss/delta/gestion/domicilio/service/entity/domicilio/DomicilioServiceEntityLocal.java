/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.domicilio.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.entity.domicilio;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.LocalidadNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.SubDelegacionNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

/**
 * @author Lucio Duran Silva
 * 
 */
@Local
public interface DomicilioServiceEntityLocal {

	/**
	 * Consulta los asentamientos que correspondan al codigo postal requerido.
	 * 
	 * @param codigo
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Asentamiento> getAsentamientoPorCodigoPosta(CodigoPostal codigo)
			throws DomicilioNoLocalizadoException;

	/**
	 * Consulta los asentamientos que correspondan al municipio requerido.
	 * 
	 * @param municipio
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Asentamiento> getAsentamientoPorMunicipio(Municipio municipio)
			throws DomicilioNoLocalizadoException;
	
	/**
	 * Consulta las localidades que correspondan al municipio requerido.
	 * 
	 * @param municipio
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Localidad> getLocalidadesPorMunicipio(Municipio municipio)
			throws DomicilioNoLocalizadoException;

	/**
	 * Obtiene el asentamiento apartir de los datos del asentamiento recibido,
	 * localidad, entidad y municipio.
	 * 
	 * @param asentamiento
	 * @return
	 * @throws AsentamientoNoLocalizadoException
	 */
	Asentamiento getAsentamiento(Asentamiento asentamiento)
			throws AsentamientoNoLocalizadoException;

	/**
	 * Obtiene el codigo postal de un asentamiento.
	 * 
	 * @param asentamiento
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	CodigoPostal getCodigoPostalDeAsentamiento(Asentamiento asentamiento)
			throws DomicilioNoLocalizadoException;

	/**
	 * 
	 * @param domicilio
	 * @return
	 * @throws DomicilioNoValidoException
	 */
	Domicilio guardarDomicilio(Domicilio domicilio)
			throws DomicilioNoValidoException;

	/**
	 * 
	 * @param domicilio
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	Domicilio consultarDomicilio(Domicilio domicilio)
			throws DomicilioNoLocalizadoException;

	/**
	 * Obtiene la lista de las vialidades a partir de la localidad.
	 * 
	 * @param localidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	List<Vialidad> getVialidades(Localidad localidad)
			throws VialidadesNoLocalizadasException;

	/**
	 * 
	 * @param vialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	Vialidad getVialidad(Vialidad vialidad)
			throws VialidadesNoLocalizadasException;

	Localidad getLocalidadByVialidad(Vialidad vialidad) throws DomicilioNoValidoException;

	/**
	 * Obtiene las vialidades de una localidad y por el tipo especificado.
	 * 
	 * @param localidad
	 * @param tipoVialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	List<Vialidad> getVialidadesPorTipoVialidad(Localidad localidad,
			TipoVialidad tipoVialidad) throws VialidadesNoLocalizadasException;

	/**
	 * 
	 * @param domicilioFiscal
	 * @return
	 * @throws DomicilioNoValidoException
	 */
	DomicilioFiscal guardarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal) throws DomicilioNoValidoException;

	/**
	 * Consulta un domicilio fiscal
	 * 
	 * @param domicilioFiscal
	 *            - con el id del domicilio que se desea buscar
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	DomicilioFiscal consultarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal)
			throws DomicilioNoLocalizadoException;

	/**
	 * Consulta el domicilio fiscal de una persona en particular
	 * 
	 * @param persona
	 *            - con el id de la persona que se desea obtener su domicilio
	 *            fiscal y debe ser una instancia de Moral o Fisica
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	DomicilioFiscal consultarDomicilioFiscalPersona(Persona persona)
			throws DomicilioNoLocalizadoException;

	/**
	 * Obtiene los domicilios de una persona fisica o individuo
	 * 
	 * @param persona
	 *            - con el id de la persona que se desea obtener su domicilio
	 *            fiscal y con el tipoPersona asignado
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Domicilio> consultarDomiciliosPersonaFisica(Persona persona)
			throws DomicilioNoLocalizadoException;

	/**
	 * Obtiene los domicilios de una persona moral
	 * 
	 * @param persona
	 *            - con el id de la persona que se desea obtener su domicilio
	 *            fiscal y con el tipoPersona asignado
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Domicilio> consultarDomiciliosPersonaMoral(Persona persona)
			throws DomicilioNoLocalizadoException;

	/**
	 * Obtiene los domicilios de una persona fisica que se pueden modificar
	 * desde el mï¿½dulo de Modificaciï¿½n Manual de Datos, los domicilios a
	 * modificar son: domicilio particular y domicilios para recibir y oï¿½r
	 * notificaciones
	 * 
	 * @param persona
	 *            - con el id de la persona que se desea obtener su domicilio
	 *            fiscal y con el tipoPersona asignado
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Domicilio> consultarDomiciliosModificablesPersonaFisica(
			Persona persona) throws DomicilioNoLocalizadoException;

	/**
	 * Modifica un domicilio fiscal
	 * 
	 * @param domicilioFiscal
	 * @return
	 * @throws DomicilioNoValidoException
	 */
	void modificarDomicilioFiscal(DomicilioFiscal domicilioFiscal)
			throws DomicilioNoValidoException;

	/**
	 * Mï¿½todo que crea la relaciï¿½n entre una persona y un domicilio, y devuelve el ID de dicha relacion
	 * (DitPersonafDom)
	 * 
	 * @param cveDomicilio
	 * @param cvePersona
	 * @throws DomicilioNoValidoException
	 */
	Long asociarDomicilioPersona(Domicilio domicilio, Long cvePersona)
			throws DomicilioNoValidoException;

	/**
	 * Mï¿½todo que crea la relaciï¿½n entre una persona moral y un domicilio
	 * 
	 * @param cveDomicilio
	 * @param cvePersona
	 * @throws DomicilioNoValidoException
	 */
	void asociarDomicilioPersonaMoral(Domicilio domicilio, Long cveMoral)
			throws DomicilioNoValidoException;

	/**
	 * Mï¿½todo que crea la relaciï¿½n entre persona fisica y domicilio fiscal
	 * 
	 * @param cveDomicilioFiscal
	 * @param cveFisica
	 * @throws AsociarDomicilioException
	 */
	long asociarDomicilioFiscalPersonaFisica(Integer cveDomicilioFiscal,
			Long cveFisica) throws AsociarDomicilioException;

	/**
	 * Mï¿½todo que crea la relaciï¿½n entre persona moral y domicilio fiscal
	 * 
	 * @param cveDomicilioFiscal
	 * @param cveMoral
	 * @throws AsociarDomicilioException
	 */
	long asociarDomicilioFiscalPersonaMoral(Integer cveDomicilioFiscal,
			Long cveMoral) throws AsociarDomicilioException;

	/**
	 * Mï¿½todo que modifica un domicilio
	 * 
	 * @param domicilio
	 * @throws TransformacionException
	 */
	void modificarDomicilio(Domicilio domicilio) throws TransformacionException;

	/**
	 * Mï¿½todo que elimina un domicilio y la relaciï¿½n con la persona
	 * 
	 * @param cveDomicilio
	 * @param cvePersona
	 * @throws AsociarDomicilioException
	 */
	void desasociarEliminarDomicilioPersona(Long cveDomicilio, Long cvePersona)
			throws AsociarDomicilioException;

	/**
	 * Mï¿½todo que elimina un domicilio y la relaciï¿½n con la persona moral
	 * 
	 * @param cveDomicilio
	 * @param cvePersona
	 * @throws AsociarDomicilioException
	 */
	void desasociarEliminarDomicilioPersonaMoral(Long cveDomicilio,
			Long cvePersona) throws AsociarDomicilioException;

	/**
	 * Obtiene los domicilios de los tipos especifados, relacionados a una
	 * persona fï¿½sica
	 * 
	 * @param persona
	 * @param tiposDomicilio
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Domicilio> consultarDomiciliosPersonaFisicaPorTipo(Persona persona,
			List<Long> tiposDomicilio) throws DomicilioNoLocalizadoException;

	/**
	 * Obtiene los domicilios de los tipos especifados, relacionados a una
	 * persona moral
	 * 
	 * @param persona
	 * @param tiposDomicilio
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Domicilio> consultarDomiciliosPersonaMoralPorTipo(Persona persona,
			List<Long> tiposDomicilio) throws DomicilioNoLocalizadoException;
	
	/**
	 * Consulta y recupera un listado de municipios IMSS asociados a un codigo postal filtrando por entidad federativa y municipio inegi
	 * @param objMunicipio
	 * @param codigoPostal
	 * @return
	 * @throws MunicipioImssNoLocalizadoException
	 */
	List<MunicipioIMSS> getMunicipioIMSSbyEstadoMunCP(Municipio objMunicipio, String codigoPostal)throws MunicipioImssNoLocalizadoException;
	
	/**
	 * Metodo encargado de recuperar una umf a partir de un asentamiento del domicilio
	 * @param asentamiento
	 * @return UnidadMedicaFamiliar con una lista de umf con subdelegacion y delegacion
	 * @throws UmfNoLocalizadaException
	 */
	List<UnidadMedicaFamiliar> getUmfByAsentamientoDomicilio(Asentamiento asentamiento) throws UmfNoLocalizadaException;
	
	/**
	 * Metodo encargado de recuperar una umf a partir de un codigo postal
	 * @param String codigoPostal
	 * @return UnidadMedicaFamiliar con una lista de umf con subdelegacion y delegacion
	 * @throws UmfNoLocalizadaException
	 */
	List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal)
			throws UmfNoLocalizadaException;
	
	/**
	 */
	MunicipioIMSS getMunicipioIMSSPorClave(String clave);

	/**
	 * Obtiene la delegaciï¿½n a travï¿½s de su id
	 *  
	 * @param idDelegacion
	 * @return
	 */
	Delegacion obtenerDelegacionPorId(Long idDelegacion);
	
	/**
	 * Obtiene la subdelegaciï¿½n a travï¿½s de su id
	 *  
	 * @param idSubdelegacion
	 * @return
	 */
	Subdelegacion obtenerSubdelegacionPorId(Long idSubdelegacion);

	/**
	 * Obtiene una Entidad Federativa a travï¿½s de su id
	 * 
	 * @param cveEnt
	 * @return
	 */
	EntidadFederativa getEstado(String cveEnt);
	
	/**
	 * Metodo que recupera la localidad de la base de datos
	 * @param localidad
	 * @return
	 * @throws LocalidadNoLocalizadoException
	 */
	Localidad getLocalidad(Localidad localidad)
			throws LocalidadNoLocalizadoException;
	
	/**
	 * 
	 * @param codigoPostal
	 * @return
	 * @throws UmfNoLocalizadaException
	 */
	UnidadMedicaFamiliar getUMFDefaultPorCP(String codigoPostal) throws UmfNoLocalizadaException;

	/**
	 * Obtiene las coincidencias que se encuentren en vialidades a través del
	 * nombre recibido
	 * 
	 * @param localidad
	 * @param periodo
	 * @param nomVialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException 
	 */
	List<Vialidad> obtenerVialidadesAutocompletar(Localidad localidad,
			int periodo, String nomVialidad)
			throws VialidadesNoLocalizadasException;

	/**
	 * Obtiene la vialidad seleccionada desde el autocompletar, 
	 * se devuelve un objeto domicilio que contiene la vialidad
	 * seleccionada y la localidad a la que pertenece
	 * 
	 * @param localidad
	 * @param periodo
	 * @param vialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	Domicilio obtenerVialidadElegida(Localidad localidad, int periodo,
			Vialidad vialidad) throws VialidadesNoLocalizadasException;

    /**
     *
     * @param persona
     * @param tiposDomicilio
     * @param orderType
     * @return
     * @throws DomicilioNoLocalizadoException
     */
    List<Domicilio> consultarDomiciliosPersonaFisicaPorTipoOrdenadoPorFecha(Persona persona,
			List<Long> tiposDomicilio, String orderType) throws DomicilioNoLocalizadoException;
    
    /**
     *
     * @param codigoPostal
     * @return
     * @throws SubDelegacionNoLocalizadaException
     */
    Subdelegacion getSubDelegacionPorCP(String codigoPostal) throws SubDelegacionNoLocalizadaException;
    
     /**
     *
     * @param codigoPostal
     * @return
     * @throws SubDelegacionNoLocalizadaException
     */
    List<Subdelegacion> getSubDelegacionesPorCP(String codigoPostal) throws SubDelegacionNoLocalizadaException;
    
    List<DicMunicipioImss> getMunicipioImssPorMunicipioIMSS(List<String> municipioIMSS);
	List<Domicilio> consultarDomiciliosPersonaFisicaAcceder(Persona persona)
			throws DomicilioNoLocalizadoException;
	
	  /**
		 * Servicio de devuelve el catalogo de subdelegaciones  activas que contiene una delegacion IMSS
		 * @param idDelegacion
		 * @return
		 */
	    List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion);
	    
	    /**
		 * Servicio de devuelve el catalogo de delegaciones  activas que contiene el IMSS 
		 * @return
		 */
	    List<Delegacion> findDelegacionesActivas();
}