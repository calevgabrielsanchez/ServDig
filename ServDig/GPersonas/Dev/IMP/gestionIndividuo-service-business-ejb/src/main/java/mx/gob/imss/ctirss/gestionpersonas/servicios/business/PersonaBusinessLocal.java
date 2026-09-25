package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ValidacionIdentidadTramite;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Local
public interface PersonaBusinessLocal {

    Fisica getDatosComplementariosPersonaFisica(Long idPersona);

    Fisica altaPersonaFisica(Fisica personaFisica) throws DomicilioNoValidoException;

    List<Fisica> buscarPersonaFisicaPorRfcEnImss(String rfc);

    List<Fisica> buscarPersonaFisicaPorCurpEnImss(String curp);

    Fisica buscarPersonaFisicaPorRfcEnSat(final String rfc) throws ClienteWebserviceSatRfcException;

    Moral buscarPersonaMoralPorRfcEnSat(final String rfc) throws ClienteWebserviceSatRfcException;

    DatosSalidaPaginador<Fisica> getPersonaFisicaFiltro(DatosEntradaPaginador<Fisica> paramsPager) throws NumeroMaximoResultadosSuperadoException;

    Fisica buscarPersonaFisicaPorCurpEnRenapo(String curp) throws ClienteWebserviceRenapoCurpException;

    Fisica buscarPersonaFisicaPorDatosBasicosEnRenapo(String sNombres, String sPrimerApellido, String sSegundoApellido, int iIdSexo, Date fechaNacimiento, int iIdEntidadFederativa) throws ClienteWebserviceRenapoCurpException;

    Fisica getPersonaFisica(Long idPersona);

    List<Fisica> buscarPersonaFisicaPorDatosBasicosEnImss(Fisica personaFisica);
            
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
     * 191807 211112
     * Metodo que busca una persona fisica en el IMSS y en entidades externas, que regresa un objeto para peticiones asincronas
     * -/persona/fisica/busqueda-embebida
     * @param pf
     * @return
     * @throws NumeroMaximoResultadosSuperadoException
     * @throws ClienteWebserviceSatRfcException
     * @throws ClienteWebserviceRenapoCurpException
     */
    DatosSalidaPaginador<Fisica> buscarPersonaFisicaEnIMSSyEE(Fisica pf) throws NumeroMaximoResultadosSuperadoException, ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException;

    Fisica buscarPersnaPorID(Long idPersona) throws PersonaNoEncontradaException;
    
	/**
	 * Servicio para modificar una persona, especialmente creado para el
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
	 * Servicio para modificar las calificaciones de una persona, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona fisica, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 */
	void afectarCalificacionesPersona(AfectarDatosPersonaWrapper datosPersona);
	
	/**
	 * Servicio para modificar los identificadores de una persona, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona fisica, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 * 
	 * @param datosPersona
	 */
	void afectarIdentificadoresPersona(AfectarDatosPersonaWrapper datosPersona);

	/**
	 * Servicio que obtiene el nombre de una persona 
	 * 
	 * @param persona con el id de la persona y el tipo de persona setteado
	 * @return
	 */
	String obtenerNombrePersona(Persona persona);
	
	/**
	 * Servicio que realiza la afectaci�n/creaci�n de una persona,
	 * dependiendo lo que reciba en el tramite, adem�s crea un tr�mite 
	 * relacionado a la acci�n realizada (creaci�n o modificaci�n de persona)
	 * y lo relaciona a la persona afectada/creada
	 * 
	 * @param tramite
	 * @param idSolicitud
	 * @param idModulo
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 */
	void ejecutarTramiteCambioInfoPersona(Tramite tramite, Long idSolicitud,
			Long idModulo) throws AfectacionDatosPersonaException,
			PersonaNoEncontradaException, RegistroPersonaFisicaException;
	

	
	/**
	 * Metodo que se encarga de consultar a una persona registrada con calificacion en caso de tener mas de un registro de 
	 * tipo DiiPersona arroja una excepcion
	 * @param String curp
	 * @return
	 * @throws ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException
	 */
	Fisica consutalPersonaByCurpConCalificion (String strCURP)throws ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException;
	
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
	 * Obtiene el NSS de una persona a trav�s de su ID, lanza una excepci�n en
	 * caso de que el mismo ID cuenta con m�s de un NSS o que no tenga NSS, no
	 * discrimina entre vigentes y no vigentes.
	 * 
	 * @param idPersona
	 * @throws PersonaConVariosNSSException
	 * @throws PersonaSinNSSException
	 */
	String obtenerNssPersona(Long idPersona)
			throws PersonaConVariosNSSException, PersonaSinNSSException;

	/**
	 * Obtiene el NSS vigente de una persona a trav�s de su ID, lanza una
	 * excepci�n en caso de que el mismo ID cuenta con m�s de un NSS vigente o
	 * que no tenga NSS vigente
	 * 
	 * @param idPersona
	 * @throws PersonaConVariosNSSException
	 * @throws PersonaSinNSSException
	 */
	String obtenerNssVigentePersona(Long idPersona)
			throws PersonaConVariosNSSException, PersonaSinNSSException;
	
	/**
	 * Registra/actualiza los datos de la fiel de la persona fisica o moral
	 * @param persona
	 */
	void registrarDatosCertificadoPersona(Persona persona);
	
	/**
	 * Obtiene los datos de la fiel en base al id de persona(fisica o moral)
	 * y el tipo de persona
	 * @param persona (idPersona y cve fisica en caso de pf requerido).
	 * @return Fiel
	 */
	Fiel obtenerDatosFiel(Persona persona);
	
	/**
	 * Mediante este metodo se reporta que la persona fisica o moral
	 * ha sido acreditada ante las distintas instituciones, es por ello
	 * que se emplea exclusivamente al realizar la conclusi�n
	 * de una solicitud datos generales.
	 * @param persona
	 */
	void reportarAcreditacion(Persona persona);
	
	/**
	 * Obtener el total de registro de persona fisica o persona moral
	 * asociados al RFC.
	 * Se requiere rfc y tipo de persona.
	 * 
	 * @param persona
	 * @return 
	 */
	int totalRegistroPersonaFMPorRFC(Persona persona);
	
	/**
	 * Servicio que valida que la persona recibida cuente con la informaci�n 
	 * necesaria para poder iniciar un tr�mite
	 * 
	 * @param persona
	 * @param tipoTramite
	 * @return
	 */
	ValidacionIdentidadTramite validarIdentidad(Persona persona, int tipoTramite);
	
	/**
	 * Servicio que crea una persona nueva a partir de las entidades externas RENAPO y/o SAT.
	 * <br>Recibe una instancia de persona fisica o moral. 
	 * <br>Para persona fisica requiera CURP y/o RFC.
	 * <br>Para persona moral requiere RFC.
	 * <br>Para ambos casos califica la persona de acuerdo a las entidades consultadas
	 * <br>Puede crear la solicitud de registro de persona si lo requiere.
	 * 
	 * @param persona
	 * @param crearSolicitud
	 * @return
	 * @throws RegistroPersonaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws RegistroPersonaFisicaException
	 * @throws SolicitudNoValidaException
	 * @throws PersonaNoEncontradaException 
	 * @throws DomicilioNoValidoException 
	 */
	Persona registrarPersonaConEntidadesExternas(Persona persona,
			boolean crearSolicitud) throws RegistroPersonaException,
			ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException, RegistroPersonaFisicaException,
			SolicitudNoValidaException, PersonaNoEncontradaException, DomicilioNoValidoException;
	
	
	/**
	 * Metodo que busca a la persona que hizo el registro de portal con fiel en casp de no encontrar datos regresa nulo
	 * @param crup
	 * @return
	 * @throws Exception
	 */
	Fisica getFisicaBySolicitudRegistroPortalConFiel(String curp) throws Exception;

	/**
	 * Metodo que busca a la persona fisica en SAT con datos para determinar la situacion del contribuyente
	 * INC363848
	 * @param crup
	 * @return
	 * @throws Exception
	 */
	Fisica buscarPFPorRfcEnSatSitCont(String rfc) throws ClienteWebserviceSatRfcException;
	
	/**
	 * Metodo que busca a la persona moral en SAT con datos para determinar la situacion del contribuyente
	 * INC363848
	 * @param crup
	 * @return
	 * @throws Exception
	 */
	Moral buscarPMPorRfcEnSatSitCont(String rfc) throws ClienteWebserviceSatRfcException;
	
}
