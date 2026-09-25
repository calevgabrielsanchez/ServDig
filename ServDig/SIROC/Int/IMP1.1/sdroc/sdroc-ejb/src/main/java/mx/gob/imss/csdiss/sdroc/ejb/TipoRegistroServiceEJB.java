package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.TipoRegistroDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoRegistroService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/TipoRegistroServiceEJB", mappedName = "ejb/TipoRegistroServiceEJB")
@Remote(TipoRegistroService.class)
public class TipoRegistroServiceEJB implements TipoRegistroService,Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3666899464667048835L;
	
	@Autowired
	private TipoRegistroService tipoRegistroService;


	public List<TipoRegistroDTO> consultarTodosTiposRegistros() throws BusinessException {
		return tipoRegistroService.consultarTodosTiposRegistros();
	}

}
