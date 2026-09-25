package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.AseguradoVigentePermisoCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolave;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolaveCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoCuentaIndividual;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoPensionado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAsegurado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoMarcaAfiliatoria;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

@Remote
public interface IAseguradoServiciosDigitalesServiceRemote  {
	
	/**
	 * Metodo que consulta los periodos de cuenta individual de un asegurado en almacenes
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	List<AseguradoCuentaIndividual> consultarMovimientosCuentaIndividual(String nss) throws ServiciosRestException;
	
	
	/**
	 *Consulta la información de un grupo familiar incluyendo la vigencia 
	 * @param nss
	 * @return List<GrupoFamiliar>
	 * @throws ServiciosRestException
	 */
	List<DerechohabienteDTO> getGrupoFamiliarByNSS(String nss)throws ServiciosRestException;
	
	
	/**
	 * Consulta la información del asegurado del grupo familiar son el bdtu sin vigencia
	 * @@param nss, filtroBajaLogica true si la fecha de baja debe ser nula, false no se aplica filtro
	 * @return DerechohabienteDTO con la información del asegurado
	 * @throws ServiciosRestException
	 */
	DerechohabienteDTO getAseguradoGrupoFamiliarByNSSSinVigencia(String nss, boolean filtroBajaLogica)throws ServiciosRestException;
	
	/**
	 * Metodo que valida la relacion laboral entre asegurado y patron cuando el aseugrado esta vigente
	 * @param nss, filtroBajaLogica true si la fecha de baja debe ser nula, false no se aplica filtro
	 * @param nrp
	 * @return
	 * @throws ServiciosRestException
	 */
	Boolean validaRelacionLaboralAseguradoPatron(String nss , String nrp)  throws ServiciosRestException;
	
	
	/**
	 * Metodo para consultar la información del asegurado y su vigencia en BDTU
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	DerechohabienteSinolave getAseguradoVigenteSinolave(String nss) throws ServiciosRestException;	

	/**
	 * Metodo que consulta si el asegurado tiene fecha de baja en la tabla de DIT_ASIGNACION_NSS
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	String getFechaBajaDitAsignacionNss(String nss)throws ServiciosRestException;
	
	/**Metodo que valida si el NSS se encuentra con una marca de paso al cambio al o marca de confirmar
	 * Regresa 0 en caso de que no tenga alguna marca regresa 1 en caso de que si 
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	String isAseguradoPasoAlCambioAlPendiente(String nss) throws ServiciosRestException;
	
	/**
	 * Metodo que consulta la informacion del asegurado sin conciderar la fecha de baja y adicioal consulta la informacón
	 * del la pension si es que llega a tener
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	AseguradoPensionado getAseguradoPensionado(String nss) throws ServiciosRestException;
	
	
	/**¨
	 * Metodo encargado de buscar a la peronsa en BDTU por NSS
	 * @param String nss
	 * @return Persona
	 * @throws ServiciosRestException
	 */
	Persona getPersonaServiciosDigitalesByNSS(String nss) throws ServiciosRestException;
	
	/**
	 * Metodo que recupera el domicilio de una asegurado registrado en un grupo familar
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	mx.gob.imss.digital.modelo.domicilio.Domicilio consultaDomicilioAseguradoGrupoFamiliar(String nss) throws ServiciosRestException;
	
	/**Metodo que devuelve una lista de asegurado con base a la que recibe como parametro
	 * 
	 * @param lstNss
	 * @return
	 * @throws ServiciosRestException
	 */
	List<DatosGeneralesAsegurado> getDatosGeneralesAseguradoByNss(List<String> lstNss) throws ServiciosRestException;
	
	/**Metodo que devuelve una lista de asegurado con base a la que recibe como parametro
	 * 
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	DatosGeneralesAsegurado getDatosGeneralesAseguradoByNss(String nss) throws ServiciosRestException;
	
	/**Metodo que devuelve los datos del asegurado con base a la que recibe como parametro como fuente canase
	 * 
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	DatosGeneralesAsegurado getCanaseDatosGeneralesAseguradoByNss(String nss) throws ServiciosRestException;

	
	DatosGeneralesAseguradoMarcaAfiliatoria getMarcaAfiliatoria(String nss) throws ServiciosRestException;
	
	DatosGeneralesAsegurado getDatosGeneralesAseguradoByNss(Long idPersona)
			throws ServiciosRestException;
	
	
	/**
	 * Metodo para consultar la información del asegurado y su vigencia en BDTU con info del patron y clinica en BDTU
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	DerechohabienteSinolaveCovid getAseguradoVigenteSinolaveCovid(String nss, String curp) throws ServiciosRestException;	
	

	/**
	 * Metodo para consultar la información del asegurado y su vigencia en BDTU con info del patron y clinica en BDTU
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	AseguradoVigentePermisoCovid getAseguradoVigentePermisoCovid(String nss, String curp) throws ServiciosRestException;
	
	 
	 /**
	  * Metodo que busca a un integrante de grupo familiar por CURP asociado al NSS y su vigencia 
	  * @param nss cabeza de grupo familiar
	  * @param curp del integrate del grupo familar
	  * @return GrupoFamiliar objeto que trae al integrante con adscripción y vigencia
	  * @throws ServiciosRestException
	  */
	 GrupoFamiliar getIntegranteGrupoFamiliarByCurp(String nss, String curp)throws ServiciosRestException;
	 
	

}
