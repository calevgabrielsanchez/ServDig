package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionIncidenciaService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/InformacionIncidenciaServiceEJB", mappedName = "ejb/InformacionIncidenciaServiceEJB")
@Remote(InformacionIncidenciaService.class)
public class InformacionIncidenciaServiceEJB implements InformacionIncidenciaService,Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6101397116127663041L;
	@Autowired
	private InformacionIncidenciaService informacionIncidenciaService;


	public InformacionIncidenciaDTO insertarInformacionIncidencia(InformacionIncidenciaDTO informacionIncidenciaDTO)
			throws BusinessException {
		InformacionIncidenciaDTO informacionIncidencia = null;
		informacionIncidencia = informacionIncidenciaService.insertarInformacionIncidencia(informacionIncidenciaDTO);
		return informacionIncidencia;
		
	}


	public List<InformacionIncidenciaDTO> consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(
			Long cveInformacionObra) throws BusinessException {
		// TODO Auto-generated method stub
		return informacionIncidenciaService.consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(cveInformacionObra);
	}


	public InformacionIncidenciaDTO consultarUltimaIncidenciaPorTipoIncidenciaPorCveInformacionObra(
			Long cveInformacionObra, Long cveTipoIncidencia) throws BusinessException {
		// TODO Auto-generated method stub
		return informacionIncidenciaService.consultarUltimaIncidenciaPorTipoIncidenciaPorCveInformacionObra(cveInformacionObra, cveTipoIncidencia);
	}


	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPorCveInformacionObra(Long cveInformacionObra)
			throws BusinessException {
		// TODO Auto-generated method stub
		return informacionIncidenciaService.consultarUltimoReporteBimestralPorCveInformacionObra(cveInformacionObra);
	}

	

	@Override
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPorCveInformacionObra(
			InformacionObraDTO obra) throws BusinessException {
		// TODO Auto-generated method stub
		return informacionIncidenciaService.consultarUltimoReporteBimestralPorCveInformacionObra(obra);
	}


	public void cargarBimestresExtemporaneos(Date fecInicioObra, Date fecFinObra, Long cveInformacionObra) {
		// TODO Auto-generated method stub
		informacionIncidenciaService.cargarBimestresExtemporaneos(fecInicioObra, fecFinObra, cveInformacionObra);
		
	}
	
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPresentado(Long cveInformacionObra)
			throws BusinessException {
		return informacionIncidenciaService.consultarUltimoReporteBimestralPresentado(cveInformacionObra);
	}


	public String consultarDescripcionMotivoPorClaveMotivo(Long cveMotivo, Long cveTipoIn){
		return informacionIncidenciaService.consultarDescripcionMotivoPorClaveMotivo(cveMotivo,cveTipoIn);
	}


	public void eliminaReporteBimByCveInfoObra(Long cveInformacionObra,
			String fechaFinObra, int annio) {
		informacionIncidenciaService.eliminaReporteBimByCveInfoObra(cveInformacionObra, fechaFinObra, annio);
		
	}


	public void eliminaReporteBimByCveInfoObraDeclarado(Long cveInformacionObra, String fechaFinObra, int annio) {
		informacionIncidenciaService.eliminaReporteBimByCveInfoObraDeclarado(cveInformacionObra, fechaFinObra, annio);
		
	}


	public CalendarioReporteDTO consultarBimestreCorrespondiente(String mes)
			throws BusinessException {		
		return informacionIncidenciaService.consultarBimestreCorrespondiente(mes);
	}

	public List<InformacionIncidenciaDTO> consultarIncidenciasPorCveInformacionObra(String cveInformacionObra) {
		return informacionIncidenciaService.consultarIncidenciasPorCveInformacionObra(cveInformacionObra);
	}


	public void eliminaReportesBimestralesNoPresentados(Long cveInformacionObra) {
		// TODO Auto-generated method stub
		informacionIncidenciaService.eliminaReportesBimestralesNoPresentados(cveInformacionObra);
	}


}
