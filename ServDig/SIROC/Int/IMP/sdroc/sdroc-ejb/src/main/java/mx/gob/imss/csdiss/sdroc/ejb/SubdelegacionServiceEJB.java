package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.dto.SubDelegacionDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.MotivoService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.SubdelegacionService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/SubdelegacionServiceEJB", mappedName = "ejb/SubdelegacionServiceEJB")
@Remote(SubdelegacionService.class)
public class SubdelegacionServiceEJB implements SubdelegacionService,Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4230112967294605546L;

	@Autowired
	private SubdelegacionService subdelegacionService;

	public SubDelegacionDTO consultarSubdelegacionPorCveCodigo(Long cveCodigo) throws BusinessException {
		return subdelegacionService.consultarSubdelegacionPorCveCodigo(cveCodigo);
	}

	public List<SubDelegacionDTO> consultarTodasSubdelegaciones() throws BusinessException {
		return subdelegacionService.consultarTodasSubdelegaciones();
	}

	public List<SubDelegacionDTO> consultarSubdelegacionesImssByCp(String cveCodigoPostal) throws BusinessException {
		return subdelegacionService.consultarSubdelegacionesImssByCp(cveCodigoPostal);
	}



}
