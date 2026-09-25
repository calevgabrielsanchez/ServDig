package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.ReporteBimestralObraDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.ReporteBimestralService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/ReporteBimestralServiceEJB", mappedName = "ejb/ReporteBimestralServiceEJB")
@Remote(ReporteBimestralService.class)
public class ReporteBimestralServiceEJB implements ReporteBimestralService,Serializable  {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7472762900255649736L;
	
	@Autowired
	private ReporteBimestralService reporteBimestralService;

	public ReporteBimestralObraDTO consultarUltimoReporteBimestralPorCveInformacionObra(Long cveInformacionObra)
			throws BusinessException {
		return reporteBimestralService.consultarUltimoReporteBimestralPorCveInformacionObra(cveInformacionObra);
	}


	
}
