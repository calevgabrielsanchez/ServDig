/**
 *
 *  @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: delta
 * @Archivo:DomicilioServiceBusiness.java
 * @Paquete:mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio
 * @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
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
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
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
@Remote
public interface DomicilioServiceBusinessRemote {

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
     * Obtiene el detalle del asentamiento
     *
     * @param asentamiento
     * @return
     * @throws DomicilioNoLocalizadoException
     * @throws AsentamientoNoLocalizadoException
     */
    Asentamiento getAsentamiento(Asentamiento asentamiento)
            throws AsentamientoNoLocalizadoException,
            DomicilioNoLocalizadoException;

    /**
     * Obtiene los asentamientos relacionados a la umf
     *
     * @param idUmf
     * @return
     */
    List<Asentamiento> findAsentamientosByUmf(Long idUmf);

    /**
     *
     * @param idUmfUsuario
     * @param idUmfPersona
     * @param codigoPostal
     * @param tipoTramite
     * @return
     * @throws mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException
     */
    Map<String, Object> findAsentamientosByUmfCp(Long idUmfUsuario, Long idUmfPersona,
            String codigoPostal, Long tipoTramite) throws DomicilioNoLocalizadoException;

    /**
     *
     * @param domicilio
     * @return
     * @throws DomicilioNoValidoException
     */
    Domicilio registrarDomicilio(Domicilio domicilio)
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
     * Metodo para obtener la lista de las vialidades de una localidad.
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

    Localidad getLocalidadByVialidad(Vialidad vialidad)
            throws DomicilioNoValidoException;

    Localidad getLocalidad(Localidad localidad) throws LocalidadNoLocalizadoException;

    /**
     * Obtiene la lista de entidades federativas que atiende una delegacion
     *
     * @param idDelegacion
     * @return
     */
    List<EntidadFederativa> findEstadosByDelegacion(Long idDelegacion);

    /**
     * Obtiene los municipios relacionados a un estado y una delegacion
     *
     * @param idDelegacion
     * @param idEstado
     * @return
     */
    List<Municipio> findMunicipiosByDelegacionEstado(Long idDelegacion,
            Long idEstado);

    /**
     * Obtiene la lista de asentamientos a partir de una delgacion, municipio y
     * estado
     *
     * @param idDelegacion
     * @param idEstado
     * @param idMunicipio
     * @return
     */
    List<Asentamiento> finAsentamientosByDelegacionEstadoMunicipio(
            Long idDelegacion, Long idEstado, Long idMunicipio);

    List<Vialidad> getVialidadesPorTipoVialidad(Localidad localidad,
            TipoVialidad tipoVialidad) throws VialidadesNoLocalizadasException;

    List<Asentamiento> getAsentamientosByDelegacionCodigoPostal(
            Long idDelegacion, String codigoPostal)
            throws DomicilioNoLocalizadoException;

    /**
     *
     * @param domicilioFiscal
     * @return
     * @throws DomicilioNoValidoException
     */
    DomicilioFiscal registrarDomicilioFiscal(
            DomicilioFiscal domicilioFiscal) throws DomicilioNoValidoException;

    /**
     *
     * @param domicilioFiscal
     * @return
     * @throws DomicilioNoLocalizadoException
     */
    DomicilioFiscal consultarDomicilioFiscal(
            DomicilioFiscal domicilioFiscal)
            throws DomicilioNoLocalizadoException;

    /**
     * Servicio que obtiene el domicilio fiscal de una persona
     *
     * @param persona con el ID_PERSONA setteado
     * @return domicilioFiscal encontrado
     * @throws DomicilioNoLocalizadoException
     */
    DomicilioFiscal consultarDomicilioFiscalPersona(Persona persona)
            throws DomicilioNoLocalizadoException;

    /**
     * Obtiene los domicilios de una persona fisica o individuo
     *
     * @param persona - con el id de la persona que se desea obtener su
     * domicilio fiscal y con el tipoPersona asignado
     * @return
     * @throws DomicilioNoLocalizadoException
     */
    List<Domicilio> consultarDomiciliosPersonaFisica(Persona persona)
            throws DomicilioNoLocalizadoException;

    /**
     * Obtiene los domicilios de una persona moral
     *
     * @param persona - con el id de la persona que se desea obtener su
     * domicilio fiscal y con el tipoPersona asignado
     * @return
     * @throws DomicilioNoLocalizadoException
     */
    List<Domicilio> consultarDomiciliosPersonaMoral(Persona persona)
            throws DomicilioNoLocalizadoException;

    /**
     * Obtiene los domicilios de una persona fisica que se pueden modificar
     * desde el módulo de Modificación Manual de Datos, los domicilios a
     * modificar son: domicilio particular y domicilios para recibir y oír
     * notificaciones
     *
     * @param persona - con el id de la persona que se desea obtener su
     * domicilio fiscal y con el tipoPersona asignado
     * @return
     * @throws DomicilioNoLocalizadoException
     */
    List<Domicilio> consultarDomiciliosModificablesPersonaFisica(
            Persona persona) throws DomicilioNoLocalizadoException;

    /**
     * Modifica un domicilio fiscal
     *
     * @param domicilioFiscal
     * @throws DomicilioNoValidoException
     */
    void modificarDomicilioFiscal(DomicilioFiscal domicilioFiscal)
            throws DomicilioNoValidoException;

    /**
     * Servicio que crea la relación entre una persona y un domicilio, devuelve
     * el ID de dicha relacion (DitPersonaFDom)
     *
     * @param domicilio
     * @param cvePersona
     * @return 
     * @throws DomicilioNoValidoException
     */
    Long asociarDomicilioPersona(Domicilio domicilio, Long cvePersona)
            throws DomicilioNoValidoException;

    /**
     * Servicio que crea la relación entre una persona moral y un domicilio
     *
     * @param domicilio
     * @param cveMoral
     * @throws DomicilioNoValidoException
     */
    void asociarDomicilioPersonaMoral(Domicilio domicilio, Long cveMoral)
            throws DomicilioNoValidoException;

    /**
     * Servicio que crea la relación entre persona fisica y domicilio fiscal
     *
     * @param cveDomicilioFiscal
     * @param cveFisica
     * @return 
     * @throws AsociarDomicilioException
     */
    long asociarDomicilioFiscalPersonaFisica(Integer cveDomicilioFiscal,
            Long cveFisica) throws AsociarDomicilioException;

    /**
     * Servicio que crea la relación entre persona moral y domicilio fiscal
     *
     * @param cveDomicilioFiscal
     * @param cveMoral
     * @return 
     * @throws AsociarDomicilioException
     */
    long asociarDomicilioFiscalPersonaMoral(Integer cveDomicilioFiscal,
            Long cveMoral) throws AsociarDomicilioException;

    /**
     * Servicio que modifica un domicilio geográfico
     *
     * @param domicilio
     * @throws TransformacionException
     */
    void modificarDomicilio(Domicilio domicilio) throws TransformacionException;

    /**
     * Servicio que elimina un domicilio geográfico y lo desasocia de una
     * persona
     *
     * @param cveDomicilio
     * @param cvePersona
     * @throws AsociarDomicilioException
     */
    void desasociarEliminarDomicilioPersona(Long cveDomicilio, Long cvePersona)
            throws AsociarDomicilioException;

    /**
     * Servicio que elimina un domicilio geográfico y lo desasocia de una
     * persona moral
     *
     * @param cveDomicilio
     * @param cvePersonaMoral
     * @throws AsociarDomicilioException
     */
    void desasociarEliminarDomicilioPersonaMoral(Long cveDomicilio,
            Long cvePersonaMoral) throws AsociarDomicilioException;

    /**
     * Servicio que obtiene los domicilios especificados por tipo, relacionados
     * a una persona
     *
     * @param persona - con el id y tipo de persona especificados
     * @param tiposDomicilio - lista con los id de los tipos de domicilio a
     * buscar
     * @return
     * @throws DomicilioNoLocalizadoException
     */
    List<Domicilio> obtenerDomiciliosPersonaPorTipo(Persona persona,
            List<Long> tiposDomicilio) throws DomicilioNoLocalizadoException;

    /**
     * Metodo que recupera un lista municipios imss junto con la subdelegacion y
     * delegacion
     *
     * @param objMunicipio
     * @param codigoPostal
     * @return
     * @throws MunicipioImssNoLocalizadoException
     */
    List<MunicipioIMSS> getMunicipioIMSSbyEstadoMunCP(Municipio objMunicipio, String codigoPostal) throws
            MunicipioImssNoLocalizadoException;

    /**
     * Metodo que recupera una lista de UMF asociadas a un asentamiento
     * geofráfico
     *
     * @param asentamiento
     * @return lista de UMF con los datos de la delegacion y subdelegacion a la
     * que pertenece llenos
     * @throws UmfNoLocalizadaException
     */
    List<UnidadMedicaFamiliar> getUmfByAsentamientoDomicilio(Asentamiento asentamiento) throws UmfNoLocalizadaException;

    /**
     * Metodo que recupera una lista de UMF asociadas a un codigo postal a 5
     * digitos
     *
     * @param codigoPostal
     * @return lista de UMF con los datos de la delegacion y subdelegacion a la
     * que pertenece llenos
     * @throws UmfNoLocalizadaException
     */
    List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal)
            throws UmfNoLocalizadaException;

    /**
     *
     * @param clave
     * @return 
     */
    MunicipioIMSS getMunicipioIMSSPorClave(String clave);

    /**
     * Servicio que obtiene una delegación a través de su id
     *
     * @param idDelegacion
     * @return
     */
    Delegacion obtenerDelegacionPorId(Long idDelegacion);

    /**
     * Servicio que obtiene una subdelegación a través de su id
     *
     * @param idSubdelegacion
     * @return
     */
    Subdelegacion obtenerSubdelegacionPorId(Long idSubdelegacion);

    /**
     * Obtiene una Entidad Federativa a través de su id
     *
     * @param cveEnt
     * @return
     */
    EntidadFederativa getEstado(String cveEnt);

    /**
     * Servicio para recuperar las localidades asociadas a un municipio
     *
     * @param municipio
     * @return
     * @throws DomicilioNoLocalizadoException
     */
    List<Localidad> getLocalidadesPorMunicipio(Municipio municipio)
            throws DomicilioNoLocalizadoException;

    /**
     * Servicio para obtener las vialidades que coincidan con los par�metros
     * recibidos
     *
     * @param localidad
     * @param periodo
     * @param nomVialidad
     * @return
     * @throws VialidadesNoLocalizadasException
     */
    List<Vialidad> obtenerVialidadesAutocompletar(Localidad localidad,
            int periodo, String nomVialidad) throws VialidadesNoLocalizadasException;

    /**
     * Obtiene la vialidad seleccionada desde el autocompletar, se devuelve un
     * objeto domicilio que contiene la vialidad seleccionada y la localidad a
     * la que pertenece
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
     * Este metodo, se encarga de buscar al asegurado en base al idPersona
     *
     * @param idPersona
     * @return
     * @throws DerechohabientesBusinessException
     */
    GrupoFamiliar getAsegurado(Long idPersona) throws DerechohabientesBusinessException;

    /**
     *
     * @param cveEnt
     * @param cveMun
     * @param codigoPostal
     * @return
     * @throws mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException
     */
    MunicipioIMSS getMunicipioIMSSbyEstadoMunCP(String cveEnt, String cveMun, String codigoPostal)
            throws MunicipioImssNoLocalizadoException;

    List<Asentamiento> findAsentamientosByDelegacionInUmfOrigenUmfDestino(Long idDelegacion, String cp, Long idUmfOrigen, Long idUmfDestino) throws DomicilioNoLocalizadoException;

    /**
     * Metodo que devuelve una UMF defual en base al CP que se envia
     *
     * @param codigoPostal
     * @return
     * @throws UmfNoLocalizadaException
     */
    List<UnidadMedicaFamiliar> getUmfDefaultByCodigoPostal(String codigoPostal) throws UmfNoLocalizadaException;

    /**
     * Metodo q	ue se usa para complementar los datos de vialidad, ya que para
     * domicilio recortado solo se pide el nombre una vez que llega a este
     * metodo se buscara la localidad en base al estado y municipio eligien una
     * calle ninguno por default y poniendola como vialidad primaria los datos
     * requeridos que se validan son: cveEstado codigoPostal cveMunicipio
     * CveAsentamiento calle numeroExteriorAlf numeroInteriorAlf es opcional
     *
     * Este metodo recibe un objeto domicilio del modelo digital, lo transforma
     * y retorna un domicilio del paquete que se usa en todos los proyectos
     *
     * @param domicilio
     * @return
     * @throws mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException
     * @throws IllegalArgumentException Cuando falta algun dato para la consulta
     * @throws DomicilioNoLocalizadoException Cuando no se encuentra la
     * localidad
     */
    Domicilio complementarLocalidadDomicilioDigRecortado(mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio) throws DomicilioNoValidoException, DomicilioNoLocalizadoException;

    /**
     * Metodo q	ue se usa para complementar los datos de vialidad, ya que para
     * domicilio recortado solo se pide el nombre una vez que llega a este
     * metodo se buscara la localidad en base al estado y municipio eligien una
     * calle ninguno por default y poniendola como vialidad primaria los datos
     * requeridos que se validan son: cveEstado codigoPostal cveMunicipio
     * CveAsentamiento calle numeroExteriorAlf numeroInteriorAlf es opcional
     *
     * @param domicilio
     * @return
     * @throws mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException
     * @throws IllegalArgumentException Cuando falta algun dato para la consulta
     * @throws DomicilioNoLocalizadoException Cuando no se encuentra la
     * localidad
     */
    Domicilio complementarLocalidadDomicilioRecortado(Domicilio domicilio) throws DomicilioNoValidoException, DomicilioNoLocalizadoException;
    
    void validarDatosDomicilioRecortado(mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio) throws DomicilioNoValidoException;
    
    Subdelegacion getSubDelegacionPorCodigoPostal(String codigoPostal) throws SubDelegacionNoLocalizadaException;
	
	List<Subdelegacion> getSubDelegacionesPorCodigoPostal(String codigoPostal) throws SubDelegacionNoLocalizadaException;
	
	 List<MunicipioIMSS> getMunicipioImssPorMunicipioIMSS(List<String> municipioIMSS);
	 List<Domicilio> consultarDomiciliosPersonaFisicaAcceder(Persona persona)
			throws DomicilioNoLocalizadoException;
	 
	 /**
		 * Servicio de devuelve el catalogo de delegaciones  activas que contiene el IMSS 
		 * @return
		 */
		List<Delegacion> findDelegacionesActivas(); 
		
		/**
		 * Servicio de devuelve el catalogo de subdelegaciones  activas que contiene una delegacion IMSS
		 * @param idDelegacion
		 * @return
		 */
		List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion);

}
