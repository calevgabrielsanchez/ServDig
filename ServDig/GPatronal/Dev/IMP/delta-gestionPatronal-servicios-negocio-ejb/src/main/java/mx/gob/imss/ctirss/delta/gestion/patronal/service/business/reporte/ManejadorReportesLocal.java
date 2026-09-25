/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.reporte;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

/**
 *
 * @author I
 */
@Local
public interface ManejadorReportesLocal {
    
    /**
     * @param cveSolicitud
     * @param cveAnalisis
     * @param cveCLEM
     * @return ByteArrayOutputStream
     */
	ByteArrayOutputStream ejecutaCLEM04Delegacional(Long cveSolicitud,
			String cveAnalisis, String cveCLEM, String delegacion,
			String subDelegacion, String fraccion, String inciso,
			String psp15A, String psp19, String art20, String art26,
			String art28);
    
    /**
     * 
     * @param cveSolicitud
     * @param cveAnalisis
     * @param cveCLEM
     * @return 
     */
	ByteArrayOutputStream ejecutaCLEM04SubDelegacional(Long cveSolicitud,
			String cveAnalisis, String cveCLEM, String delegacion,
			String subDelegacion, String fraccion, String inciso,
			String psp15A, String psp19, String art20, String art26,
			String art28);
    
	byte[] ejecutaAvisoDeModificacion(Map<String, Object> parametros, List<SujetoObligado> sujetos, Solicitud solicitud);
	byte[] ejecutaAvisoDeModificacion(Map<String, Object> parametros, List<SujetoObligado> sujetos);
	byte[] ejecutaCartaTerminosFiel(Solicitud solicitud);
    
	/**
	 * Metoodo que concatena los pdf que recibe como lista de streams
	 * @param byteArrayOutputStream
	 * @param paginate
	 * @return
	 */
	ByteArrayOutputStream concatenaPDFs(List<ByteArrayOutputStream>  byteArrayOutputStream, boolean paginate );

	byte[] ejecutaAvisoDeModificacionMovPat(Map<String, Object> parametros, List<SujetoObligado> sujetos, Solicitud solicitudRegis);
	byte[] ejecutaAvisoDeModificacionMovPat(Map<String, Object> parametros, List<SujetoObligado> sujetos);
	
}
