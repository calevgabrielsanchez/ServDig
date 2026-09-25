package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja;

import java.util.List;
import java.util.Date;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * @author Hugo Martinez
 * @Projecto: delta-gestionPatronal-interfaces-negocio
 * @Package: mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja
 * @Archivo: BajaPatronalServiceBusinessRemote.java
 * @Fecha: 10/01/2013 09:37:34
 */
@Remote
public interface RegistroPatronalServiceBusinessRemote {
	
	/**
	 * @author Hugo Martinez
	 * @Fecha: 10/01/2013 09:39:24
	 * Obtiene los registros patronales asociados al rfc proporcionado
	 * y muestra obtiene los elementos de la página correspondiente.
	 * Este método trabaja conjuntamente con el componente JQuery
	 * Datatable.
	 */
	DatosSalidaPaginador<SujetoObligado> paginarRegistrosPatronales(
			DatosEntradaPaginador<SujetoObligado> input);
	
	/**
	 * Utilizado para dar de baja un conjunto de registros patronales proporcionados
	 * @param registroPatronal
	 * @throws GestionPatronalBusinessException
	 */
	void darDeBajaRPs(List <String> registrosPatronales) throws GestionPatronalBusinessException;
	
	/**
	 * Utilizado para registrar un nuevo registro patronal asociado al RFC en cuestión
	 * @param RegistroPatronal
	 * @throws GestionPatronalBusinessException
	 */
	void registrarRegistroPatronal(RegistroPatronal RegistroPatronal) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene numero de registro patronal, modalidad, digito verificador y clasificacion
	 * @param cveIdPatronSujetoObligado Identificador de rp en delta
	 * @return RegistroPatronal
	 */
	RegistroPatronal obtenerDatosRegistroPatronal(Long cveIdPatronSujetoObligado);
	
	/**
	 * Obtiene el correo del centro de trabajo asociado al registro patronal
	 * @param cveIdPatronSujetoObligado Identificador del Registro Patronal
	 * @return Correo
	 */
	String obtenerCorreoDeNotificacion(Long cveIdPatronSujetoObligado);
	
	/**
	 * Obtiene el correo del centro de trabajo asociado al registro patronal
	 * @param numeroRegistroPatronal Número de registro patronal
	 * @return Correo
	 */
	String obtenerCorreoDeNotificacion(String numeroRegistroPatronal) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene el correo fiscal de una persona.
	 * @param idPersona
	 * @return Correo fiscal registrado en el SAT
	 */
	String obtenerCorreoFiscal(Long idPersona, Long idTipoPersona);
	
	/**
     *  Busca y retorna el registro patronal 34 de la persona física en base al municipioImss asociado 
     *  a su domicilio particular y el RFC de la persona cuyo identificador es proporcionado. 
     *  Debido a que la búsqueda del rp se realiza en base al rfc y no al idPersona puede
     *  darse el caso en el cuál se localize un nrp 34 asociado al mismo rfc pero a otro idPersona
     *  en ese caso se genera una excepción indicando la inconsistencia de datos.
     *  
     *  Para corregir esta situación debería realizar primero la recuperación del registro patronal
     *  y posteriormente solicitar nuevamente el trámite que se requiera llevar acabo.
     * 
     * @param idPersona
     * @return Nrp
     */
    String obtenerNrpModalidad34PersonaFisica(Long idPersona) throws GestionPatronalBusinessException;
    
    String obtenerNrpConvencionalPorDomicilioYModalidad(String cveMun,
			String cveEnt, String cp, String numModalidad)
			throws GestionPatronalBusinessException;
    
    /**
     * Obtiene el nrp convencional activo por municipio y modalidad
     * @param cveMunImss
     * @param numModalidad
     * @return
     */
    String obtenerNrpConvencionalPorModalidad(String cveMunImss, String numModalidad)
    		throws GestionPatronalBusinessException;
    
    /**
     * Obtiene el nrp convencional basodo en el municipio y codigo postal del domicilio
     * particular registrado para la persona cuyo identificador es proporcionado como
     * parámetro al invocar el servicio.
     * @param idPersona Identificador de la persona
     * @param numModalidad clave de modalidad
     * @return Número de registro patronal convencional del municipio asignado.
     * @throws DomicilioNoLocalizadoException
     * @throws MunicipioImssNoLocalizadoException
     */
    String obtenerNrpConvencionalPorDomicilioParticularYModalidad(Long idPersona, String numModalidad) 
    		throws GestionPatronalBusinessException;

    /**
     * Este metodo se emplea para crear un nrp modalidad 34 en caso de no existir uno en el municipio Imss
     * asociado a la dirección particular de la persona proporcionada. Realiza el alta en bdtu y además genera 
     * el mensaje para sincronizar dicha alta con sindo, agrega el trámite de alta a la solicitud proporcionada
     * ocasionando con lo anterior que se generen los documentos resultantes requeridos al solicitar el servicio
     * de generaDocumentos.
     * @param nrp Nuevo número de registro patronal cálculad mediante el servicio de calculoRPNP
     * @param idSolicitud identificador de la solicitud a la cual se agregará el trámite de alta
     * @param idPersona Identificador de la persona en bdtu
     * @return Nuevo numero de registro patronal
     * @throws GestionPatronalBusinessException
     */
    String crearNrpDomestico(String nrp, Long idSolicitud, Long idPersona) throws GestionPatronalBusinessException;
    
    /**
     * Obtiene la clave del municipio imss asociado al domicilio particular de la persona
     * cuyo identificador es proporcionado como parámetro.
     * @param idPersona
     * @return
     * @throws GestionPatronalBusinessException
     */
    String obtenerClaveMunicipioImssDeDomicilioParticularPorIdPersona(Long idPersona) throws GestionPatronalBusinessException;

    /**
     * Se consulta el municipioIMSS en base al codigo postal y municipio geográfico
     * @param cveMun
     * @param cveEnt
     * @param cp
     * @return Cve Municipio IMSS
     * @throws GestionPatronalBusinessException
     */
	String obtenerClaveMunicipioImss(String cveMun, String cveEnt, String cp) throws GestionPatronalBusinessException;

    MunicipioIMSS obtenerMunicipioImssDeDomicilioParticularPorIdPersona(Long idPersona)
            throws DomicilioNoLocalizadoException, MunicipioImssNoLocalizadoException;

    Date obtenerEstadoHuelga(String regPatronal);
	
	List<RegistroPatronal> obtenerPatronesConMunicipiosImss(List<String> registrosPatronales);
}
