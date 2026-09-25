/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;

/**
 *
 * @author I
 */
@Remote
public interface ManejadorReportesRemote {
    
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

    /**
     * 
     * @param parametros
     * @return
     */
	byte[] ejecutaAvisoDeModificacion(Map<String, Object> parametros, List<SujetoObligado> sujetos);
	
	/**
     * 
     * @param parametros
     * @return
     */
	byte[] ejecutaAvisoDeModificacionMovPat(Map<String, Object> parametros, List<SujetoObligado> sujetos);

    /**
     * 
     * @param parametros
     * @return
     */
	byte[] ejecutaAcuse(Map<String, Object> parametros) throws GestionPatronalBusinessException;    
    
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	byte[] ejecutaAcuseModificacionDatosPatronales(Map<String, Object> map);
	
	/**
	 * 
	 * @param mapData
	 * @return
	 */
	byte[] ejecutaAcuseModificacionDatosPatronalesPortal(Map<String, Object> mapData);
	
	byte[] ejecutaCartaTerminosFiel(Solicitud solicitud);
	
	byte[] ejecutaCartaTerminosFielRepresentante(Solicitud solicitud);
	
	byte[] generaReporteSemanasCotizadas(HldaVO hlda, FirmaElectronica datosFirma);
	
	/**
	 * Metoodo que concatena los pdf que recibe como lista de streams
	 * @param byteArrayOutputStream
	 * @param paginate
	 * @return
	 */
	ByteArrayOutputStream concatenaPDFs(List<ByteArrayOutputStream>  byteArrayOutputStream, boolean paginate );
	
	/**
     * 
     * @param parametros
     * @return
     */
	byte[] ejecutaAcuseVentanilla(Map<String, Object> parametros) throws GestionPatronalBusinessException;
	
	byte[] ejecutaAcuseVentanillaSustFusion(Map<String, Object> parametros)
			throws GestionPatronalBusinessException;

	byte[] ejecutaAcuseCancelacion(Map<String, Object> parametros) throws GestionPatronalBusinessException;
	
	byte[] ejecutaComprobanteCita(Map<String, Object> parametros, String reporte,
			boolean pasarConexion) throws GestionPatronalBusinessException;
}
