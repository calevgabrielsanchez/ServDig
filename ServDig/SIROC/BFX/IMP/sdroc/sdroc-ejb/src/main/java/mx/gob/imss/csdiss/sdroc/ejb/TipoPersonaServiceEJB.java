package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.TipoPatronDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoPersonaDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoPatronService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoPersonaService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/TipoPersonaServiceEJB", mappedName = "ejb/TipoPersonaServiceEJB")
@Remote(TipoPersonaService.class)
public class TipoPersonaServiceEJB implements TipoPersonaService,Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3666899464667048835L;
	
	@Autowired
	private TipoPersonaService tipoPersonaService;


	public List<TipoPersonaDTO> consultarTodosTiposPersonas() throws BusinessException {
		return tipoPersonaService.consultarTodosTiposPersonas();
	}

}
