package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaMoralNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;

@Remote
public interface PersonaMoralBusinessRemote {

    Moral getDatosComplementariosPersonaMoral(Long idPersona);	
	
    List<Moral> getPersonaMoral(Moral personaMoral);

    /**
     * Si el id de la persona a guardar no es nulo, no puede ejecutar el save y
     * regresa null.
     * 
     * @param personaMoral
     * @return
     */
    Moral altaPersonaMoral(Moral personaMoral);

    /**
     * Samuel R G puse este metodo para que no me fallara el
     * RegistroPersonaMoralController pero obviamente se puede quitar si asi lo
     * consideran
     * 
     * @param rfc
     * @return
     * @throws NumeroMaximoResultadosSuperadoException
     */
    List<Moral> buscarPersonaMoralPorRfcEnImss(String rfc);

    DatosSalidaPaginador<Moral> getPersonaMoralFiltro(DatosEntradaPaginador<Moral> parametrosPaginador) throws NumeroMaximoResultadosSuperadoException;

    Moral getPersonaMoral(Long idPersonaMoral);
    
    
    /**
     * 
     * @param moral
     * @throws PersonaNoEncontradaException
     */
    void actualizarPersonaMoral(Moral moral) throws PersonaNoEncontradaException;
    
    /**
     * 
     * @param parametros
     * @return
     * @throws PersonaNoEncontradaException
     * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
     * @throws RFCNoLocalizadoEnEntidadExternaException
     * @throws ClienteWebserviceSatRfcException
     * @throws ComparacionSinDiferenciasException
     * @throws DatosInsuficientesICAException 
     */
	ICADatosRespuesta identificarCambios(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException,
			ComparacionSinDiferenciasException, DatosInsuficientesICAException;
	
	/**
	 * 
	 * @param icaDatosRespuesta
	 */
	ICADatosRespuesta integrarCambios(ICADatosRespuesta icaDatosRespuesta);
	
	/**
	 * 
	 * @param persona1
	 * @param persona2
	 * @param mensajes
	 * @return
	 */
	ICADatosRespuesta compararDosPersonasMorales(Moral persona1,
			Moral persona2, Map<String, String> mensajes);
	
	/**
	 * Servicio para realizar la modificacion manual de una persona f�sica
	 * 
	 * @param mdmDatosEntrada
	 * @return
	 * @throws DatosInsuficientesICAException 
	 * @throws PersonaNoEncontradaException 
	 */
	MDMDatosEntrada modificacionManual(MDMDatosEntrada mdmDatosEntrada)
			throws DatosInsuficientesModificacionException, PersonaNoEncontradaException;
	
	/**
	 * Servicio para modificar una persona fisica, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 08/03/2013
	 * @param datosPersona - incluye la persona moral, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 * @throws PersonaNoEncontradaException
	 */
	void afectarDatosPersonaMoral(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaMoralNoEncontradaException;

	/**
	 * Servicio que procesa la modificaci�n manual
	 * 
	 * @param mdmDatosEntrada
	 * @return 
	 */
	MDMDatosEntrada procesarModificacionManual(MDMDatosEntrada mdmDatosEntrada);
	
	/**
	 * Servicio para modificar las calificaciones de una persona moral, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona moral, as� como las banderas 
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
	 * Contiene la misma logica de identificaci�n de cambios pero sin
	 * lanzar la excepci�n de Comparaci�n sin diferencias
	 * @param parametros
	 * @return ICADatosRespuesta
	 * @throws PersonaNoEncontradaException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws DatosInsuficientesICAException
	 */
	ICADatosRespuesta identificarSoloCambios(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException, DatosInsuficientesICAException;
	
	/**
	 * Metodo que consulta si una persona moral tiene un acta constitutiva
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	Integer consultaActaConstitutivaPersonaMoral(Long cveIdPersona);
	
	/**
	 * Metodo que consulta si una persona moral tiene un idSindicato
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	Integer consultaSindicatoPersonaMoral(Long cveIdPersona);

	/**
     * Dada una persona moral creada porque que no se habia encontrado en BDTU
     * Se manda a ser la sincronizacion de datos con el sat 
     * @param idPersona Identificador de la persona moral
     * @param rfc Rfc de la persona moral
     * @throws GestionPatronalBusinessException Error al sincronizar la persona moral
     */
    void generarICAPersonaMoral(long idPersona, String rfc) throws AfectacionDatosPersonaException;

	/**
     * Dada una persona moral creada porque que no se habia encontrado en BDTU
     * Se manda a ser la sincronizacion de datos con el sat 
     * @param idPersona Identificador de la persona moral
     * @param rfc Rfc de la persona moral
     * @throws GestionPatronalBusinessException Error al sincronizar la persona moral
     */
    void generarICAPersonaMoral_AP(long idPersona, String rfc) throws AfectacionDatosPersonaException;    
    
    /**
	 * Metodo que valida si una persona moral tiene indAcreditado
	 * 
	 * @param cveIdPersona
	 * @return
	 */
    Integer validaAcreditadoPersonaMoral(Long cveIdPersona);

	/**
	 * Metodo que actualiza la razon social y tipo de sociedad de una PM
	 * Solo si los parametros no son nulos
	 * Se agrega cambio para version de produccion
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	void actualizaRazonSocialTipoSociedad(Long cveIdPersona, String nombreRazonSocial, TipoSociedad tipoSociedad);

    /**
     * 
     * @param parametros
     * @return
     * @throws PersonaNoEncontradaException
     * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
     * @throws RFCNoLocalizadoEnEntidadExternaException
     * @throws ClienteWebserviceSatRfcException
     * @throws ComparacionSinDiferenciasException
     * @throws DatosInsuficientesICAException 
     */
	ICADatosRespuesta identificarCambios_AP(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException,
			ComparacionSinDiferenciasException, DatosInsuficientesICAException;

	Moral getPersonaMoral_AP(Long idPersonaMoral);

    /**
     * 
     * @param parametros
     * @return
     * @throws PersonaNoEncontradaException
     * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
     * @throws RFCNoLocalizadoEnEntidadExternaException
     * @throws ClienteWebserviceSatRfcException
     * @throws ComparacionSinDiferenciasException
     * @throws DatosInsuficientesICAException 
     */
	ICADatosRespuesta identificarSoloCambios_AP(ICADatosConsulta parametros) throws PersonaNoEncontradaException,
			RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException,
			ErrorValidacionDatosConsultaEnEntidaExternaException, DatosInsuficientesICAException;

    /**
     * @param rfc
     * @return
     * @throws NumeroMaximoResultadosSuperadoException
     */
	List<Moral> buscarPersonaMoralPorRfcEnImss_AP(String rfc);

	/**
	 * Expone servicio para revisar la situacion del contribuyente en SAT 
	 * @param moral
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 **/
	Moral revisaSituacionContribuyente(Moral moral)
			throws ErrorComparacionDatosSATException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException;
	
    
}