package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActividadEcononica;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActualizacionClasificaionPatronalBdtuSindoDto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosEmpresaPermisoConvid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatronPermisoCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosSatDetallePatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.MovimientoRegistroPatronal;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.NumTrabajadoresVigentes;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.PatronPlataformaResponse;
import mx.gob.imss.cit.semanascotizadas.common.model.DatosHuelga;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

@Remote
public interface IPatronServiciosDigitalesServiceRemote  {
	
	/**
	 * Metodo encargado de recuperar la infomración detallada de un patron a traves de su NRP
	 * @param nrp String con el NRP de 10 a 11 posiciónes
	 * @return
	 * @throws ServiciosRestException
	 */
	SujetoObligado consultaDetallePatronSujetoObligadoByRP(String nrp) throws ServiciosRestException;
	
	/**
	 * Metodo encargado de recuperar la infomración basica de un patron a traves de su NRP
	 * @param nrp String con el NRP de 10 a 11 posiciónes
	 * @return
	 * @throws ServiciosRestException
	 */
	SujetoObligado consultaDetallePatronSujetoObligadoByRPPMC(String nrp) throws ServiciosRestException;
	
	/**
	 * Metodo encargado de encolar el movimiento que se va a SINDO por servicios digitales
	 * @param movimiento MovimientoPatronalType
	 * @throws ServiciosRestException
	 */
	void encolaMomvimientoModificacionPatronalSINDO(MovimientoPatronalType movimiento) throws ServiciosRestException;
	
	/**
	 * Metodo encargado de guardar el cambio de clasificación en bdtu y generar el movimiento para SINDO
	 * @param movimientoCalsificacion
	 * @throws ServiciosRestException
	 */
	String actualizarClasidifacionFuentesBdtuSINDO(
			ActualizacionClasificaionPatronalBdtuSindoDto movimientoCalsificacion) throws ServiciosRestException;
	
	/**
	 * Metodo que consulta registros patronales que puedan tener baja por articulo 251 en tabla DIT_MOVTO_PAT_SUJ_OBLIG
	 * @param lstRegPatronales
	 * @return List<MovimientoRegistroPatronal>
	 * @throws Exception
	 */
	List<MovimientoRegistroPatronal> consultaPatronBaja(List <String> lstRegPatronales, boolean indBaja251) throws ServiciosRestException;
	
	
	/**
	 * Metodo encargado de conultar la actividad de un patron by NRP
	 * @param nrp
	 * @return ActividadEcononica con los datos de la división grupo y fraccion
	 * @throws ServiciosRestException
	 */
	ActividadEcononica getActividadEconocimaByRegPatronal(String nrp) throws ServiciosRestException;

	/**
	 * Metodo que consulta los trabajadores vigentes asociadps a un NRP
	 * @param nrp
	 * @return
	 * @throws Exception
	 */
	NumTrabajadoresVigentes getTrabajadoresVigentesByRegPatronal(String nrp) throws ServiciosRestException;
	
	/**
	 * Metodo encargado de consultar los datos generales del patron asi como suy domicilio migrado 
	 * complementa los datos del domicilio de servicios digitales
	 * @param nrp
	 * @return
	 * @throws Exception
	 */
	DatosGeneralesPatron getDatosGeneralesPatron(String nrp) throws ServiciosRestException;
	
	
	/**
	 * Metodo que devuelve la informacón del patron en huelga si es que tiene
	 * @param regPatron
	 * @return
	 * @throws ServiciosRestException
	 */
	DatosHuelga getInfoPatronHuelga(String regPatron)throws  ServiciosRestException;
	
	/**
	 * Metodo que consulta la información del la empresa con base al RFC
	 * @param rfc
	 * @return
	 * @throws Exception
	 */
	DatosEmpresaPermisoConvid getDatosEmpresaPermisoCovid(String rfc) throws ServiciosRestException;
	
	
	/**
	 * Metodo que consulta los datos generales de patones vugentes  del asegurado  y la empresa a la que se asocia por rfc 
	 * @param lstIdPatronGeneral
	 * @return
	 * @throws ServiciosRestException
	 */
	List<DatosGeneralesPatronPermisoCovid> getDatosGeneralesPatronPernisoCovid(Long cveIdAsignacionNSS) throws ServiciosRestException;
	
	/**Metodo que devuelve una lista los NRP asociados al rfc
	 * @param rfc String 
	 * @return List<String> con los NRP
	 * @throws ServiciosRestException
	 */
	List<String> getRegistrosPatronalesByRfc(String rfc) throws ServiciosRestException; 
	
	
	/**
	 * Consulta la informaciÃ³n general de la persona en SAT y el detalle del patron incluenod ultimo movimiento y la actividad econÃ³mica
	 * puede regresar una lista de patrones o solo el que recibe como parametro
	 * @param consultaPat
	 * @return
	 * @throws ServiciosRestException
	 */
	DatosSatDetallePatron getDatosSatDetallePatron(ConsultaPatron consultaPat) throws ServiciosRestException;
	
	/**
	 * Servucui qye se encarga de validar si el patron es de plataformas en la tabla 
	 * @param nrp
	 * @return
	 * @throws ServiciosRestException
	 */
	PatronPlataformaResponse validaPatronPlataforma(String nrp)throws ServiciosRestException;
}

	
