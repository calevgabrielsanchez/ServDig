package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * 
 * @author Hugo Martinez
 * Date: 25/07/2012 09:35:11
 * Project: delta-gestionPatronal-servicios-negocio-ejb
 */
@Local
public interface AfiliacionServiceEntityLocal {
	
	/**
	 * Obtiene la informaci�n general de la persona f�sica incluyendo
	 * sus datos fiscales:
	 * 
	 * Representante Legal
	 * 
	 * @author Hugo Martinez
	 * @Date 27/07/2012
	 * @param idPersona
	 * @return Fisica
	 */
	Fisica obtenerDatosFiscalesPersonaFisica(Long idPersona);
	
	
	/**
	 * Obtiene la informaci�n general de la persona f�sica incluyendo
	 * sus datos fiscales:
	 * 
	 * Representante Legal
	 * Socios
	 * Acta Constitutiva
	 * Registro Sindicato
	 * 
	 * @author Hugo Martinez
	 * @Date 27/07/2012
	 * @param idPersona
	 * @return Moral
	 */
	Moral obtenerDatosFiscalesPersonaMoral(Long idPersona);
	
	/**
	 * Obtiene el municipio inegi en base al codigo postal del domicilio
	 * @author Hugo Martinez
	 * @Date 11/10/2012
	 * @param codigoPostal
	 * @return
	 */
	Municipio obtenerMunicipioInegiPorCodigoPostal(String codigoPostal, String Asentamiento);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 11/10/2012
	 * @param municipioInegi
	 * @return
	 */
	Long obtenerIdentificadorMunicipioImssPorMunicipioInegi(Municipio municipioInegi);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 11/10/2012
	 * @param cveIdMunImss
	 * @return
	 */
	Subdelegacion obtenerSubdelegacionPorMunicipioImss(Long cveIdMunImss);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 11/10/2012
	 * @param cveIdMunImss
	 * @return Subdelegacion
	 */
	Subdelegacion obtenerSubdelegacionPorId(Long idSubdelegacion);
	
	/**
	 * Obtiene la lista de registros patronales con sus datos b�sicos:
	 * Numero de registro patronal por subdelegacion, modalidad, digito verificador
	 * identificadores de la base delta.
	 * @param sujetoObligado
	 * @return List<SujetoObligado> registrosPatronales
	 */
	List<SujetoObligado> obtenerInformacionBasicaDeRegistrosPatronalesDelPatron(SujetoObligado sujetoObligado);
	
	/**
	 * Obtiene todos los registros patronales cuya baja este reportada mediante la fecha de baja en la 
	 * tabla DIT_PATRON_SUJETO_OBLIGADO o mediante la tabla DIT_DTS_EXTRA_PATRON donde el tipo de movimiento es 
	 * BAJA
	 * @param rfc RFC de la persona fisica
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> obtenerRegistrosPatronalesConBaja(String rfc);
	
	/**
	 * Obtiene todos los registros patronales cuya baja este reportada mediante la fecha de baja en la 
	 * tabla DIT_PATRON_SUJETO_OBLIGADO o mediante la tabla DIT_DTS_EXTRA_PATRON donde el tipo de movimiento es 
	 * BAJA
	 * @param SujetoObligado sujetoObligado 
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> obtenerRegistrosPatronalesConBaja(SujetoObligado sujetoObligado);
	
	/**
	 * Devuelve la informaci�n b�sica del �ltimo movimiento almacenado en la tabla DIT_MOVTO_PAT_SUJ_OBLIG
	 * asociado al identificador proporcionado
	 * @param cveIdPatron CveIdPatronSujetoObligado
	 * @return MovimientoAfiliatorio
	 */
	MovimientoAfiliatorio obtenerUltimoMovimiento(Long cveIdPatron);
	
	/**
	 * Obtiene los registros patronales modalidad 30 que no tiene un registro patronla eventual
	 * esto en base a buscar en DitPatronGneral si el nrp modalidad 30 esta como patronAsociado
	 * de alg�n otro registro patronal
	 * @param idPersona
	 * @return
	 */
	List<SujetoObligado> obtenerRegistrosPatronalesCanerosSinEventualesporPersona(Long idPersona);

	
	/**
	 * Obtiene la informacion general de la persona fisica 
	 * @param idPersona
	 * @return Fisica
	 */
	Fisica obtenerDatosBasicosPersonaFisica(Long idPersona);

	/**
	 * Obtiene la informacion general de la persona moral 
	 * @param idPersona
	 * @return Moral
	 */
	Moral obtenerDatosBasicosPersonaMoral(Long idPersona);

}
