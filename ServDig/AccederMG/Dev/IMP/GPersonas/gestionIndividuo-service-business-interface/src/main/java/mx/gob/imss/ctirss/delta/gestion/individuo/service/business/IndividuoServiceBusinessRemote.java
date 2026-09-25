package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;

/**
 * 
 * Project: gestionIndividuo-service-business-interface
 * IndividuoServiceBusinessRemote.java
 * @author Hugo Armando Martinez Chamonica
 * 19/07/2012 09:46:55
 */
@Remote
public interface IndividuoServiceBusinessRemote {
	
	/**
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 09:47:14
	 * 
	 * Consulta la informaci�n general de la persona en base al identificador de la persona:
	 * Datos generales, Domicilios, Medios de Contacto
	 * 
	 * 
	 * 
	 * @param persona (Parametro Requerido: idPersona)
	 * @return Persona
	 */
	Fisica consultarDatosBasicosPersonaFisica(Fisica persona);
	
	/**
	 * 
	 * 
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 11:02:54
	 * @param persona
	 * @return
	 */
	Moral consultarDatosBasicosPersonaMoral(Moral persona);
	
	/**
	 * Retorna la persona f�sica encontrada en la BDTU con el RFC proporcionado, 
	 * si encuentra m�s de una persona que cumpla con el criterio retorna la persona
	 * que este calificada por SAT si hay m�s de una persona calificada por SAT retorna
	 * la primera encontrada, si no existe ninguna persona calificada por SAT retorna
	 * la primera persona encontrada.
	 * 
	 * La consulta se ordena en forma ascendente por id de persona por lo cual se retorna
	 * la persona cuyo registro es el primero dado de alta en dbtu.
	 * 
	 * @param persona (RFC y tipo de persona)
	 * @return Fisica
	 */
	Fisica consultarPersonaFisicaIMSSPorRFC(Persona persona) throws PersonasNoLocalizadasException, ClienteWebserviceRenapoCurpException, 
	ClienteWebserviceSatRfcException, ErrorComparacionDatosRENAPOException, 
	ErrorComparacionDatosSATException, CURPNoLocalizadoEnEntidadExternaException, RFCNoLocalizadoEnEntidadExternaException, 
	DiferenciasRENAPOContraSAT, ErrorValidacionDatosConsultaEnEntidaExternaException, PersonaSinCalificacionesException;
	
	/**
	 * Retorna la persona moral encontrada en la BDTU con el RFC proporcionado, 
	 * si encuentra m�s de una persona que cumpla con el criterio retorna la persona
	 * que este calificada por SAT si hay m�s de una persona calificada por SAT retorna
	 * la primera encontrada, si no existe ninguna persona calificada por SAT retorna
	 * la primera persona encontrada.
	 * 
	 * La consulta se ordena en forma ascendente por id de persona por lo cual se retorna
	 * la persona cuyo registro es el primero dado de alta en dbtu.
	 * 
	 * @param persona (RFC y tipo de persona)
	 * @return Moral
	 */
	Moral consultarPersonaMoralIMSSPorRFC(Persona persona) throws PersonasNoLocalizadasException;

	/**
	 * Retorna la persona moral encontrada en la BDTU con el RFC proporcionado, 
	 * si encuentra m�s de una persona que cumpla con el criterio retorna la persona
	 * que este calificada por SAT si hay m�s de una persona calificada por SAT retorna
	 * la primera encontrada, si no existe ninguna persona calificada por SAT retorna
	 * la primera persona encontrada.
	 * 
	 * La consulta se ordena en forma ascendente por id de persona por lo cual se retorna
	 * la persona cuyo registro es el primero dado de alta en dbtu.
	 * 
	 * @param persona (RFC y tipo de persona)
	 * @return Moral
	 */
	Moral consultarPersonaMoralIMSSPorRFC_AP(Persona persona) throws PersonasNoLocalizadasException;

	void revisaSituacionContribuyenteSAT(List<SituacionSAT> situacionesSAT,
			String rfc, long tipoPersona) throws ErrorComparacionDatosSATException;

	void revisaSituacionContribuyenteRENAPO(String rfc, String curpSAT,
			Fisica personaEntidadREN) throws ErrorComparacionDatosSATException;

}
