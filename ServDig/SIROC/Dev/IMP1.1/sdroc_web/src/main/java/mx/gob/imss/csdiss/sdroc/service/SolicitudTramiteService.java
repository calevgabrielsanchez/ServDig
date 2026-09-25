/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.util.HashMap;

import mx.gob.imss.csdiss.sdroc.dto.SolicitudTramiteDTO;
import mx.gob.imss.csdiss.sdroc.util.GeneraReporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;

public interface SolicitudTramiteService {

	public SolicitudTramiteDTO crearSolicitudTramite(Integer idTipoTramite, FirmaElectronica firmaElectronica);
	
	public byte[] guardarArchivoNotaria(String secuenciaNotaria,GeneraReporte generadorReporte, String pathRegistroObraAcuse,String nombreReporte, String tipoRep, HashMap<String, Object> paramAcuse);
	

}
