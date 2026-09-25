package mx.gob.imss.csdiss.sdroc.service.interfaces;

import java.util.Date;
import java.util.List;

import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;




/**
 * 
 * Interface que define los metodos del servicio que proporciona los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */

public interface InformacionIncidenciaService{
		
	public InformacionIncidenciaDTO insertarInformacionIncidencia(InformacionIncidenciaDTO informacionIncidenciaDTO) throws BusinessException;
	
	public List<InformacionIncidenciaDTO> consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(Long cveInformacionObra) throws BusinessException;
	
	public InformacionIncidenciaDTO consultarUltimaIncidenciaPorTipoIncidenciaPorCveInformacionObra(Long cveInformacionObra,Long cveTipoIncidencia) throws BusinessException;
	
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPorCveInformacionObra(Long cveInformacionObra) throws BusinessException;
	
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPorCveInformacionObra(InformacionObraDTO obra) throws BusinessException;
	
	public void cargarBimestresExtemporaneos(Date fecInicio, Date fecFin, Long cveInformacionObra);
	
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPresentado(Long cveInformacionObra) throws BusinessException;

	public String consultarDescripcionMotivoPorClaveMotivo(Long cveMotivo,Long cveTipoIn);
	
	public void eliminaReporteBimByCveInfoObra(Long cveInformacionObra, String fechaFinObra, int annio);
	
	public CalendarioReporteDTO consultarBimestreCorrespondiente(String mes) throws BusinessException;
	
	public List<InformacionIncidenciaDTO> consultarIncidenciasPorCveInformacionObra(String cveInformacionObra) throws BusinessException;
	
	public void eliminaReporteBimByCveInfoObraDeclarado(Long cveInformacionObra, String fechaFinObra, int annio);
		/**
		 * eliminaReportesBimestralesNoPresentados
		 * @param cveInformacionObra
		 */
		void eliminaReportesBimestralesNoPresentados(Long cveInformacionObra);
	
}
