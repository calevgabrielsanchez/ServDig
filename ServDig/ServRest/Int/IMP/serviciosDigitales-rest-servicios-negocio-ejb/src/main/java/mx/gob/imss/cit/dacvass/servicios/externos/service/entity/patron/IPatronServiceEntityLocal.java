package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.patron;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActividadEcononica;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosEmpresaPermisoConvid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatronQuery;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosSatDetallePatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DetallePatronClasifMovPatQuery;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.MovimientoRegistroPatronal;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.NumTrabajadoresVigentes;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.PatronPlataformaResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.PatronListaBlancaResponse;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface IPatronServiceEntityLocal {
	
	/**
	 * Metodo que consulta registros patronales que puedan tener baja por articulo 251 en tabla DIT_MOVTO_PAT_SUJ_OBLIG
	 * @param lstRegPatronales
	 * @return List<MovimientoRegistroPatronal>
	 * @throws Exception
	 */
	List<MovimientoRegistroPatronal> consultaPatronBaja(List <String> lstRegPatronales, boolean indBaja251) throws Exception;
	
	/**
	 * Metodo encargado de conultar la actividad de un patron by NRP
	 * @param nrp
	 * @return ActividadEcononica con los datos de la divisi�n grupo y fraccion
	 * @throws ServiciosRestException
	 */
	ActividadEcononica getActividadEconocimaByRegPatronal(String nrp) throws Exception;

	/**
	 * Metodo que consulta los trabajadores vigentes asociadps a un NRP
	 * @param nrp
	 * @return
	 * @throws Exception
	 */
	NumTrabajadoresVigentes getTrabajadoresVigentesByRegPatronal(String nrp) throws Exception;
	
	/**
	 * Metodo encargado de consultar los datos generales del patron asi como suy domicilio migrado i el id domiclio
	 * @param nrp
	 * @return
	 * @throws Exception
	 */
	DatosGeneralesPatronQuery getDatosGeneralesPatron(String nrp) throws Exception;
	
	/**
	 * Metodo que devuelve la fecha de huelga que pueda tener un patron incluyendo la modalidad
	 * @param nrp
	 * @return
	 * @throws Exception
	 */
	Date getFechaHuelgaPatron(String nrp)  throws Exception;
	
	/**
	 * Metodo que consulta la informaci�n del la empresa con base al RFC
	 * @param rfc
	 * @return
	 * @throws Exception
	 */
	DatosEmpresaPermisoConvid getDatosEmpresaPermisoCovid(String rfc) throws Exception;
	
	/**
	 * Metodo que revisa una lista de patrones con base al los id de patron general
	 * @param lstIdPatronGeneral
	 * @return
	 * @throws Exception
	 */
	List<DatosGeneralesPatronQuery> getDatosGeneralesPatron(List<Long>lstIdPatronGeneral) throws Exception;
	
	/**Metodo que devuelve una lista los NRP asociados al rfc
	 * @param rfc String 
	 * @return List<String> con los NRP
	 * @throws ServiciosRestException
	 */
	List<String> getRegistrosPatronalesByRfc(String rfc) throws Exception;
	
	/**
	 * Metodo que devuelve el listado de RFC que representa
	 * @param rfc
	 * @return
	 * @throws Exception
	 */
	List<String> getRfcPersonaRepresentadaByRfc(String rfc) throws Exception;
	
	
	SujetoObligado obtenerPatronPMC(String regPatronal);
	
	/**
	 * Consulta que busca los datos de clasificacion y movimiento patronal ya sea pesona fiscia o moral por RFC 
	 *  registro patronal
	 * @param consulta
	 * @return
	 * @throws Exception
	 */
	List<DetallePatronClasifMovPatQuery> getDetallePatronClasifMovPat(ConsultaPatron consulta) throws Exception;
	/**
	 * Consulta el RFC del patron ya sea por nro o rfc y identifica si es persona fisica o moral
	 * @param consulta
	 * @return
	 * @throws Exception
	 */
	DatosSatDetallePatron getRfcTipoPersona(ConsultaPatron consulta) throws Exception;
	
	/**
	 * Servucui qye se encarga de validar si el patron es de plataformas en la tabla 
	 * @param nrp
	 * @return
	 * @throws ServiciosRestException
	 */
	PatronPlataformaResponse validaPatronPlataforma(String nrp)throws Exception;
	/**
	 * Servucui qye se encarga de validar si el patron es de lista blanca en la tabla PPT_PATRON_LISTA_BLANCA_ST 
	 * @param nrp
	 * @return
	 * @throws ServiciosRestException
	 */
	PatronListaBlancaResponse validaPatronListaBlanca(String nrp)throws Exception;
	/**
	 * Consulta en BDTU el nombre o razon social del patron ya sea persona fisica o moral
	 * @param nrp
	 * @return
	 * @throws Exception
	 */
	String getNombreRazonSocial(String nrp)throws Exception;
	
}
