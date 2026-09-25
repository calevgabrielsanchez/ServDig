package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.ObjetoContratoDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.ObjetoContratoService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/ObjetoContratoServiceEJB", mappedName = "ejb/ObjetoContratoServiceEJB")
@Remote(ObjetoContratoService.class)
public class ObjetoContratoServiceEJB implements ObjetoContratoService,Serializable  {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -95865538982880220L;
	@Autowired
	private ObjetoContratoService objetoContratoService;


	public List<ObjetoContratoDTO> consultarTodosObjetosContrato() throws BusinessException {
		return objetoContratoService.consultarTodosObjetosContrato();
	}

}
