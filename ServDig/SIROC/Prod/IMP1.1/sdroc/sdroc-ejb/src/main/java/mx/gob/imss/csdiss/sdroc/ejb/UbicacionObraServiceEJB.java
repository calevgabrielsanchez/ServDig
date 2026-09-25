package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.service.interfaces.UbicacionObraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/UbicacionObraServiceEJB", mappedName = "ejb/UbicacionObraServiceEJB")
@Remote(UbicacionObraService.class)
public class UbicacionObraServiceEJB implements UbicacionObraService,Serializable  {

	private static final long serialVersionUID = -5834011167169028901L;
	
	@Autowired
	private UbicacionObraService ubicacionObraService;

	public boolean isValidoCpUbicacionObra(String cveRp, String cveCodigoPostal) throws BusinessException {
		return ubicacionObraService.isValidoCpUbicacionObra(cveRp, cveCodigoPostal);
	}

	@Override
	public boolean validCircunscripcion(String codigoPostal, Long idDelegacion,Long idSubdelegacion) throws BusinessException {
		return ubicacionObraService.validCircunscripcion(codigoPostal, idDelegacion, idSubdelegacion);
	}

}
