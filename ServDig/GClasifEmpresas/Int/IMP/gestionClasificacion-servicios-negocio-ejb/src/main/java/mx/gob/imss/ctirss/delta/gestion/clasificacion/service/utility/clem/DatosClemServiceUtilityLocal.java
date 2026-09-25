/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: H�ctor Lara Andr�s
 *  @Proyecto: delta
 *  @Archivo: DatosClemServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem
 *  @Fecha: 17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.HistoricoDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitDatosClem;
import mx.gob.imss.ctirss.delta.persistence.DitHistDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;

@Local
public interface DatosClemServiceUtilityLocal {
	
	/**
	 * Transforma una entidad DatosClem a un model SmtDatosClem.
	 * @param model Modelo a transformar.
	 * @return Entidad del tipo SmtDatosClem.
	 */
	DitDatosClem convertirModelToEntity(DatosClem model) throws Exception;
	
	/**
	 * Transforma una entidad SmtDatosClem a un model DatosClem.
	 * @param entity Entidad a transformar.
	 * @return Modelo del tipo DatosClem.
	 */
	DatosClem convertirEntityToModel(DitDatosClem entity) throws Exception;
	
	String generaFolioClem(DatosClem datosClem) throws DatosClemException;
	String cambioFolio(DatosClem datosClem) throws DatosClemException;
	Boolean comparacionFraccion (String ultimaFraccion , ReporteClemBean reporteClemBean ) ;
	
	HistoricoDatosClem convertirEntityToModel(DitHistDatosClem entity) throws Exception;
	
	DitHistDatosClem convertirModelToEntityHistoricoCLEM(DatosClem model, Long cveIdTipoCausa);
	
	ReporteClemBean convertirModelsToReporteClem(ReporteClemBean reporteClem, AnalisisClasificacionEmpresas analisisClasifEmp,
			SujetoObligado sujetoObligado);
	
	boolean validaCaracteresPermitidos(String motivos);
}