package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;

/**
 * 081012
 * 
 * @author 191807
 * 
 */
@Remote
public interface PersonaFisicaServiceBusinessRemote {
	
	Boolean isSocio(Long idPersona);
	
	Boolean isPersonaAutorizada(Long idPersona);

	Map<String, Boolean> getRolesPorPersona(Long idPersona);
	Boolean personaRegistradaComoDerechohabiente(Long idPersona, Boolean activo);
	Fisica getPersonaEnRenapo(String curp) throws CURPNoLocalizadoEnEntidadExternaException, 
	ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException;
	/**
	 * 081012 Metodo encargado de actualizar una persona fisica
	 * 
	 * @param fisica
	 * @return
	 * @throws
	 */
	Fisica actualizar(Fisica fisica);

	/**
	 * 121012 Metodo encargado de registrar una persona fisica
	 * 
	 * @param fisica
	 * @return
	 * @throws RegistroPersonaFisicaException
	 */
	Fisica registrar(Fisica fisica)
			throws RegistroPersonaFisicaException;

	ICADatosRespuesta identificarCambios(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException,
			ErrorComparacionDatosRENAPOException,
			ComparacionSinDiferenciasException, DatosInsuficientesICAException,
			DiferenciasRENAPOContraSAT, PersonaFisicaNoEncontradaException;

	/**
	 * M�todo encargado de integrar los cambios entre las entidades comparadas.
	 * 
	 * @param icaDatosRespuesta
	 *            con las entidades y mensajes
	 * @return objeto con los cambios integrados y mensajes
	 */
	ICADatosRespuesta integrarCambios(ICADatosRespuesta icaDatosRespuesta);

	/**
	 * M�todo para comparar dos personas f�sicas
	 * 
	 * @param fisica
	 * @param entidad
	 * @param mensajes
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 */
	ICADatosRespuesta compararDosPersonasFisicas(Fisica persona1,
			Fisica persona2, Map<String, String> mensajes)
			throws ErrorComparacionDatosRENAPOException;
	
	/**
	 * Servicio para realizar la modificacion manual de una persona f�sica
	 * 
	 * @param mdmDatosEntrada
	 * @return
	 * @throws DatosInsuficientesICAException 
	 * @throws PersonaNoEncontradaException 
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	MDMDatosEntrada modificacionManual(MDMDatosEntrada mdmDatosEntrada)
			throws DatosInsuficientesModificacionException,
			PersonaNoEncontradaException, PersonaFisicaNoEncontradaException;
	
	/**
	 * Servicio que procesa la modificaci�n manual
	 * 
	 * @param mdmDatosEntrada
	 * @return 
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	MDMDatosEntrada procesarModificacionManual(MDMDatosEntrada mdmDatosEntrada) throws ErrorComparacionDatosRENAPOException, PersonaFisicaNoEncontradaException;
	
	/**
	 * Servicio que obtiene el id de una persona f�sica, 
	 * buscado a trav�s del id de la persona
	 * 
	 * @param idPersona
	 * @return
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	Long obtenerIDPersonaFisica(Long idPersona) throws PersonaFisicaNoEncontradaException;


    /**
     * Servicio que obtiene el id de una persona f�sica,
     * buscado a trav�s del id de la persona
     *
     * @param idPersona
     * @return
     * @throws PersonaFisicaNoEncontradaException
     */
    Long obtenerIDPersonaFisicaEscVirtual(Long idPersona) throws PersonaFisicaNoEncontradaException;

    /**
	 * Servicio que crea una persona f�sica, s�lo se crea en dit_persona_fisica,
	 * no crea ninguna relaci�n con otras entidades
	 * 
	 * @param fisica
	 * @return
	 */
	Fisica guardarPersonaFisica(Fisica fisica);
	
	/**
	 * Servicio para modificar una persona fisica, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 07/03/2013
	 * @param datosPersona - incluye la persona fisica, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 * @throws PersonaNoEncontradaException
	 */
	void afectarDatosPersonaFisica(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaFisicaNoEncontradaException;

	
	/**
	 * Expone servicio para comparar los datos basicos RENAPO entre dos personas
	 * fisicas
	 * 
	 * @param fisicaRENAPO
	 * @param fisicaSugerida
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 */
	boolean comparaDatosBasicosRENAPO(Fisica fisicaRENAPO, Fisica fisicaSugerida)
			throws ErrorComparacionDatosRENAPOException;
	
	Integer comparaDiferenciaDatosBasicosRENAPO(Fisica personaRenapo,
			Fisica personaSugerida) throws ErrorComparacionDatosRENAPOException;

	boolean comparaDatosBasicosSAT(Fisica fisicaSat, Fisica fisicaSugerida)
			throws ErrorComparacionDatosSATException;

	/**
	 * Servicio que realiza la b�squeda de persona por datos b�sicos nombre,
	 * primer apellido, segundo apellido (si es null en el objeto evalua que sea null en bd), 
	 * fecha y lugar de nacimiento
	 * y sexo. Primero realiza la b�squeda por fecha de nacimiento es con fecha exacta o solo mes y a�o
	 * En caso de no recibir los par�metros requeridos se lanza una excepci�n
	 * 
	 * @param fisica
	 * @return
	 * @throws DatosInsuficientesParaConsultaException 
	 */
	List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImssConFechaOMesYAniodeNacimiento(Fisica fisica)
			throws DatosInsuficientesParaConsultaException;
	
	/**
	 * Servicio que realiza la b�squeda de persona por datos b�sicos nombre,
	 * primer apellido, segundo apellido (si es null en el objeto evalua que sea null en bd), 
	 * fecha y lugar de nacimiento
	 * y sexo. Primero realiza la b�squeda por fecha de nacimiento es con fecha exacta o solo mes y a�o
	 * En caso de no recibir los par�metros requeridos se lanza una excepci�n
	 * 
	 * @param fisica
	 * @return
	 * @throws DatosInsuficientesParaConsultaException 
	 */
	List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImssConNSS(Fisica fisica)
			throws DatosInsuficientesParaConsultaException;
	
	List<AsignacionNSS> localizarNssPorDatosBasicosEnImss(Fisica fisica)
	throws DatosInsuficientesParaConsultaException;
	/**
	 * Servicio que realiza la b�squeda de persona por datos b�sicos nombre,
	 * primer apellido, segundo apellido (opcional), fecha y lugar de nacimiento
	 * y sexo. Primero realiza la b�squeda por fecha de nacimiento exacta, en caso
	 * de no encontrar resultados, busca s�lo por mes y a�o de la fecha de nacimiento.
	 * En caso de no recibir los par�metros requeridos se lanza una excepci�n
	 * 
	 * @param fisica
	 * @return
	 * @throws DatosInsuficientesParaConsultaException 
	 */
	List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImss(Fisica fisica)
			throws DatosInsuficientesParaConsultaException;
	
	/**
	 * Obtiene los datos de la persona fisica en base al nss
	 * 
	 * @param nss
	 * @return Fisica
	 * @throws NssRelacionadoVariasPersonasException
	 * @throws PersonasNoLocalizadasException
	 */
	Fisica localizarPersonaFisicaPorNss(String nss)
			throws PersonasNoLocalizadasException, NssRelacionadoVariasPersonasException;
	
	/**
	 * Servicio que actualiza el RFC de una persona. Es necesario que esta
	 * persona ya exista ya que actualiza el RFC en DitPersona y
	 * DitPersonaFisica, en caso de que DitPersonaFisica no exista se crea.
	 * No califica a la persona.
	 * 
	 * @param fisica
	 * @throws DatosInsuficientesModificacionException 
	 */
	void actualizarRFC(Fisica fisica) throws DatosInsuficientesModificacionException;
	
	/**
     * dado los datos de una persona que se acaba de crear en BDTU, es necesario sincronizar su 
     * informacion conra el sat y renapo
     * @param idPersona identificador de la persona nueva
     * @param rfc rfc de la persona
     * @param curp el curp de la persona fisica
     * @throws AfectacionDatosPersonaException Error al sincronizar la informacion
     */
    void generarICAPersonaFisica(long idPersona, String rfc, String curp) throws AfectacionDatosPersonaException;
    
    /**
     *  Obtiene los datos (solo datos personales) de la persona fisica en base al idPersonaFisica
     * 
     * @param  idPersonaFisica
     * @return Fisica
     */
    Fisica getPersonaFisica(Long idPersonaFisica);
    
    
    /**
	 * Expone servicio para comparar los datos basicos de un asegeruado en bdtu contraRENAPO 
	 * conciderando que la persona pueda o no taer fecha de nacimiento o mes y a�o de nacimiento
	 * @param fisicaRENAPO
	 * @param fisicaSugerida
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 */
	boolean comparaDatosBasicosAseguradoMesAnioNacRENAPO(Fisica fisicaRENAPO, Fisica fisicaAsegurado)
			throws ErrorComparacionDatosRENAPOException;
	
	void procesarActualizacionesPF(List<Fisica> listaPF);
	
	/**
	 * Expone servicio para verificar si la persona cuenta con rfc y esta como persona fisica.
	 * Actualizar RFC en "dit_persona" sino existe. 
	 * Actualizar RFC en "dit_persona_fisica" sino existe.
	 * Insertar Persona Fisica sino existe.
	 * 
	 * Es requerido el RFC para procesar actualizaciones.
	 * 
	 * @param rfc		(REQUERIDO)
	 * @param idPersona	(OPCIONAL)
	 */
	void complementarDatosPersonas(String rfc, Long idPersona);
	
	/**
	 * Metodo que recupera a la persona con NSS que se encuentra en cl3 sin considerar la fecha de nacimiento. 
	 * @param fisica
	 * @return
	 * @throws DatosInsuficientesParaConsultaException
	 */
	List<AsignacionNSS> localizarNssCl3PorDatosBasicosSinFechaNac(
			Fisica fisica) throws DatosInsuficientesParaConsultaException;
			
	Fisica localizarPersonaFisicaPorNssCertificacion(String nss)throws PersonasNoLocalizadasException, NssRelacionadoVariasPersonasException;

	Long registrarNuevaPersonaCDA(Fisica fisica, Long idPersonaAnterior, Long idAsignacionNSS) throws Exception;

	Fisica revisaSituacionContribuyente(Fisica fisica)
			throws ErrorComparacionDatosSATException,
			RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException;

}
