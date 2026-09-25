package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.TipoPatronDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoPatronService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/TipoPatronServiceEJB", mappedName = "ejb/TipoPatronServiceEJB")
@Remote(TipoPatronService.class)
public class TipoPatronServiceEJB implements TipoPatronService,Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3666899464667048835L;
	
	@Autowired
	private TipoPatronService tipoPatronService;

	public List<TipoPatronDTO> consultarTodosTiposPatrones() throws BusinessException {
		return tipoPatronService.consultarTodosTiposPatrones();
	}

}
