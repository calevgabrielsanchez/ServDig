/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoActualizadaRenapoException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.usuario.ActualizaUsuarioEsquemaSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoRegistradoEnEsquemaDeSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioRegistradoSSOException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * @author Lucio Duran Silva
 *
 */
@Remote
public interface ConsultaPersonaFisicaServiceBusinessRemote {

	/**
	 * Consulta los candidatos de persona fisica a traves
	 * de realizar las consultas en el IMSS con CURP, RFC y por datos basicos
	 * @param fisica
	 * @return
	 * @throws PersonasNoLocalizadasException
	 */
	List<Candidato> consultarPersonaFisica(Fisica fisica) throws PersonasNoLocalizadasException;
	
	/**
	 * Consulta los candidatos de persona fisica en el IMSS a traves
	 * del dato de CURP
	 * @param fisica
	 * @return
	 */
	List<Candidato> consultarPorCURPEnIMSS(Fisica fisica);
	
	/**
	 * Consulta los candidatos de persona fisica en el IMSS a traves
	 * del dato de RFC
	 * @param fisica
	 * @return
	 */
	List<Candidato> consultarPorRFCEnIMSS(Fisica fisica);
	
	/**
	 * Consulta los candidatos de persona fisica en el IMSS a traves
	 * del dato de los datos de los nombre, apellido 1 y apellido 2.
	 * @param fisica
	 * @return
	 */
	List<Candidato> consultarPorDatosBasicosEnIMSS(Fisica fisica);
	
	/**
	 * Metodo que se encarga de validar las reglas de negocio para el regisrto de un usuario persona fisica
	 * en caso de no complir con las condiciones arroja una excepcion
	 * @param fisica
	 * @param fisica
	 * @return
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws ErrorComparacionDatosSATException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws UsuarioRegistradoSSOException
	 * @throws DiferenciasRENAPOContraSAT
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException 
	 * @throws PersonaSinCalificacionesException 
	 */
	Fisica validaPersonaRegistroUsuario(Fisica fisica)
			throws ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException,
			ClienteWebserviceRenapoCurpException,
			ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			CURPNoLocalizadoEnEntidadExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			UsuarioRegistradoSSOException, DiferenciasRENAPOContraSAT,
			CURPNoActualizadaRenapoException,
			ErrorValidacionDatosConsultaEnEntidaExternaException, 
			EsquemaSegurdiadException;

	/**
	 * 
	 * @param fisica
	 * @return
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws ErrorComparacionDatosSATException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws UsuarioRegistradoSSOException
	 * @throws DiferenciasRENAPOContraSAT
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException 
	 * @throws PersonaSinCalificacionesException 
	 */
	Fisica getPersonaByCurpImssEntidadesExternas(Fisica fisica)
			throws ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException,
			ClienteWebserviceRenapoCurpException,
			ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			CURPNoLocalizadoEnEntidadExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			DiferenciasRENAPOContraSAT,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException;

	/**
	 * Metodo que realiza las validaciones pertinentes para actualizar el password y la llave de una cuenta en caso de que cambie
	 * @param objUsuario
	 * @return Fisica con los datos de la persona original a cambiar
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws ErrorComparacionDatosSATException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws UsuarioRegistradoSSOException
	 * @throws UsuarioNoRegistradoEnEsquemaDeSeguridadException
	 * @throws EsquemaSegurdiadException
	 * @throws UsuarioNoEncontradoException
	 * @throws ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException
	 * @throws ActualizaUsuarioEsquemaSeguridadException
	 */
	 Fisica validaActualizarUsuarioEnEsquemaSeguridad(Usuario objUsuario) 
				throws
				ClienteWebserviceRenapoCurpException,
				ClienteWebserviceSatRfcException,
				ClienteWebserviceRenapoCurpException,
				ErrorComparacionDatosRENAPOException,
				ErrorComparacionDatosSATException,
				CURPNoLocalizadoEnEntidadExternaException,
				RFCNoLocalizadoEnEntidadExternaException,
				UsuarioRegistradoSSOException,
				UsuarioNoRegistradoEnEsquemaDeSeguridadException,
				EsquemaSegurdiadException, UsuarioNoEncontradoException, ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException, 
				ActualizaUsuarioEsquemaSeguridadException;
	/**
	 *  * Metodo encargado de validar la pesona registrada en AM contra BDTU por ID y actualiza los datos del nombre cuando en BDTU los apellidos son nulos
	 * @param objUsuario
	 * @param objUsuario
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws UsuarioNoRegistradoEnEsquemaDeSeguridadException
	 * @throws EsquemaSegurdiadException
	 * @throws UsuarioNoEncontradoException
	 * @throws ActualizaUsuarioEsquemaSeguridadException
	 */
	 void actualizaNombreUsuarioPatronBDTU(Usuario objUsuario) 
				throws
				ClienteWebserviceRenapoCurpException,
				CURPNoLocalizadoEnEntidadExternaException,
				UsuarioNoRegistradoEnEsquemaDeSeguridadException,
				EsquemaSegurdiadException, UsuarioNoEncontradoException,  
				ActualizaUsuarioEsquemaSeguridadException;
	 
	 /**
	  * 
	  * @param fisicaBdtu Persona con la informacion que existe en Servicios Digitales
	  * @param personaLocalizadaRenapo Persona con la informacion que existe en RENAPO
	  * @return Indica si la persona se actualizo
	  */
	 void actualizarNombreUsuarioBDTU(Fisica fisicaBdtu, Fisica personaLocalizadaRenapo);
}
