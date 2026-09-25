package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.TipoObraDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/TipoObraServiceEJB", mappedName = "ejb/TipoObraServiceEJB")
@Remote(TipoObraService.class)
public class TipoObraServiceEJB implements TipoObraService  {

	
	private static final Logger logger = LoggerFactory.getLogger(TipoObraServiceEJB.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 2951346090483717980L;
	
	@Autowired(required=true)
	private TipoObraService tipoObraService;


	public List<TipoObraDTO> consultarTiposDeObras() throws BusinessException {
		logger.info("Entre al consultar Tipos Obras EJB en el back");
		return tipoObraService.consultarTiposDeObras();		
	}


	public List<TipoObraDTO> consultarTiposDeObrasPorClasificacionObra(Long cveClasificacionObra)
			throws BusinessException {
		return tipoObraService.consultarTiposDeObrasPorClasificacionObra(cveClasificacionObra);
	}

}
