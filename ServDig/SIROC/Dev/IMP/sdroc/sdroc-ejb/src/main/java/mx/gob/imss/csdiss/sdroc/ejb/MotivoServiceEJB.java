package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.MotivoService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/MotivoServiceEJB", mappedName = "ejb/MotivoServiceEJB")
@Remote(MotivoService.class)
public class MotivoServiceEJB implements MotivoService,Serializable  {

	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3486558632160555538L;
	
	@Autowired
	private MotivoService motivoService;


	public List<MotivoDTO> consultarMotivosPorTipoIncidencia(Long cveTipoIncidencia) throws BusinessException {
		return motivoService.consultarMotivosPorTipoIncidencia(cveTipoIncidencia);
	}

}
