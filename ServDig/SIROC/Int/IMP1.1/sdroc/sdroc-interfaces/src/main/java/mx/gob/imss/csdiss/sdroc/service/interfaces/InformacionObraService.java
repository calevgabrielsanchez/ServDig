package mx.gob.imss.csdiss.sdroc.service.interfaces;

import java.util.List;
import java.util.Map;

import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.RegistroPatronalDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;




/**
 * 
 * Interface que define los metodos del servicio que proporciona los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */

public interface InformacionObraService{
	
	SujetoObligado consultarInfoPatron(String rp);
	
	public Map<String, Object> consultarObrasPorRfcYRp(String cveRfc, String cvRegPatronal, Long inicio, Long fin) throws BusinessException;
	
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcYCveRegPatronal(String cveRfc, String cvRegPatronal) throws BusinessException;
	
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfc(String cveRfc) throws BusinessException;
	
	public List<RegistroPatronalDTO> consultarformacionObraAgrupadaByCveRfc(String cveRfc) throws BusinessException;
	
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRegPatronal(String cvRegPatronal) throws BusinessException;
	
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRegistroObraPrincipal(Long cveRegistroObraPrincipal) throws BusinessException;
	
	public InformacionObraDTO consultarInformacionObraPorCveRegistroObra(String cveRegistroObra) throws BusinessException;
	
	public InformacionObraDTO consultarInformacionObraPorId(Long cveInformacionObra) throws BusinessException;
	
	public List<InformacionObraDTO> consultarTodasInformacionObras() throws BusinessException;
	
	public int consultarNumeroTotalObrasPorCveRfcyAnio(String cveRfc,String anio) throws BusinessException;
	
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcyAnio(String cveRfc,String anio) throws BusinessException;
	
	public InformacionObraDTO insertarInformacionObra(InformacionObraDTO informacionObraDTO) throws BusinessException;
	
	public List<InformacionObraDTO> consultarReporteGeneralObrasPorCveRfcYAnio(String cveRfc, String anio) throws BusinessException;
	
	public List<InformacionObraDTO> consultarReporteGeneralObrasPorCveRegPatronal(String cvRegPatronal) throws BusinessException;
		
	public void actualizarInformacionObra(InformacionObraDTO informacionObraDTO) throws BusinessException;
}
