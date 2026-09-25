/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: H�ctor Lara Andr�s
 *  @Proyecto: delta
 *  @Archivo:DatosClemServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem
 *  @Fecha:13/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem;

import java.io.ByteArrayOutputStream;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClemCaracterException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;

@Remote
public interface DatosClemServiceBusinessRemote {

	DatosClem insertaClem(DatosClem datosClem, ReporteClemBean reporteClemBean, Boolean insert) throws DatosClemException;
	
	/**
	 * Consulta de la taba DIT_DATOS_CLEM
	 * @param model
	 * @return
	 * @throws DatosClemException
	 */
	DatosClem consultaClem(DatosClem model) throws DatosClemException ;
	
	DatosClem insertaActualizacionClem(DatosClem model, ReporteClemBean reporteClemBean) throws DatosClemException ;
	
	DatosClem actualizaEstadoClem(DatosClem model) throws DatosClemException;
	
	/**
	 * Borra datos relacionados al CLEM y Clasificacion Propuesta al hacer una modificacion de autorizacion
	 * @param cveIdClem
	 * @throws DatosClemException
	 */
	void elimina(Long cveIdClem) throws DatosClemException;
	
	/**
	 * Obtiene todos los parametros necesarios para generar un archivo PDF referente al CLEM
	 * @param reporteClemBean
	 * @param nomReporte
	 * @param imgPath
	 * @return
	 * @throws DatosClemException
	 */
	ByteArrayOutputStream generaReporteClem(ReporteClemBean reporteClemBean, String nomReporte, String imgPath) throws DatosClemException;
	
	/**
	 * A partir del Detalle de Analisis, realiza consultas para verificar si ya existen datos de Rectificacion Autorizada
	 * @param reporteClemBean
	 * @return
	 * @throws DatosClemException
	 */
	ReporteClemBean consultaDatosClem(ReporteClemBean reporteClemBean) throws DatosClemException;
	
	/**
	 * Autoriza la Rectificacion y genera el PDF del CLEM, a demas de modificar CLEM
	 * @param reporteClemBean
	 * @param usuario
	 * @param imgPath
	 * @throws Exception
	 */
	void generacionClem(ReporteClemBean reporteClemBean, Usuario usuario, String imgPath, Long cveIdPatronDictamen)throws ClemCaracterException, DatosClemException, Exception;
	
	/**
	 * Actualiza registro de la clem con datos de la firma electronica
	 * @param model
	 * @return
	 * @throws DatosClemException
	 */
	void actualizaDatosFirmaClem(FirmaClemDTO firmaClemDTO)
			throws DatosClemException;

	/**
	 * Valida caracteres permitidos en la Clem
	 * @param model
	 * @return
	 * @throws ClemCaracterException
	 * @throws Exception 
	 */
	boolean validaCaracteresPermitidosClem(ReporteClemBean reporteClemBean) throws Exception;

} 
 
