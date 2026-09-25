package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.AvisoObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.AvisoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;

@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/AvisoObraServiceEJB", mappedName = "ejb/AvisoObraServiceEJB")
@Remote(AvisoObraService.class)
public class AvisoObraServiceEJB implements AvisoObraService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7155317811478285076L;

	@Autowired
	private AvisoObraService avisoObraService;

	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(String cveAvisoObra) throws BusinessException {
		// TODO Auto-generated method stub
		return avisoObraService.consultarAvisoObraPorCveAvisoObra(cveAvisoObra);
	}

	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(String cveAvisoObra, String rfc, String registroPatronal)
			throws BusinessException {
		// TODO Auto-generated method stub
		return avisoObraService.consultarAvisoObraPorCveAvisoObra(cveAvisoObra, rfc, registroPatronal);
	}

	public List<AvisoObraDTO> consultarAvisosObraPorCveRfc(String cveRfc) throws BusinessException {
		// TODO Auto-generated method stub
		return avisoObraService.consultarAvisosObraPorCveRfc(cveRfc);
	}

	public AvisoObraDTO insertarAvisoObra(AvisoObraDTO avisoObraDTO) throws BusinessException {
		// TODO Auto-generated method stub
		return avisoObraService.insertarAvisoObra(avisoObraDTO);
	}
	
	public void actualizarAvisoObra(Long cveAvisoObra) throws BusinessException {
		// TODO Auto-generated method stub
		avisoObraService.actualizarAvisoObra(cveAvisoObra);
	}

}
