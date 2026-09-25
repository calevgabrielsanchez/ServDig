/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.util.Date;
import java.util.HashMap;

import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;

/**
 * @author daniel.hernandez
 *
 */
public interface RegistroIncidenciaService {
	/**
	 * Registro de la incidecia y la generacion del reporte correspondiente al registro.
	 * @param informacionIncidencia
	 * @return Ruta del reporte generado
	 */
	public HashMap<String, Object> registraIncidencia(InformacionIncidenciaDTO informacionIncidencia, String pathRegCancelaAcuse, String pathImg, FirmaElectronica firmaElectronica);
	
	
	/**
	 * consultarUltimaIncidenciaRegistrada
	 * @param cveInformacionObra
	 * @param idTipoIncidencia
	 * @return InformacionIncidenciaDTO
	 */
	public InformacionIncidenciaDTO consultarUltimaIncidenciaRegistrada(Long cveInformacionObra, int idTipoIncidencia);
	
	/**
	 * @param mes
	 * @return
	 */
	public Long bimestreCorrespondiente(String mes);

	/**
	 * consultar Ultimo Reporte Bimetral Reportado
	 * @param cveInformacionObra
	 * @param idTipoIncidencia
	 * @return InformacionIncidenciaDTO
	 */
	public InformacionIncidenciaDTO consultarUltimoReporteBimetralReportado(Long cveInformacionObra, int idTipoIncidencia);
	
	byte[] getAcuseIncidencia(InformacionIncidenciaDTO informacionIncidencia, String pathRegCancelaAcuse, String pathImg, Date fecha, Boolean generarCadena);
	
}
