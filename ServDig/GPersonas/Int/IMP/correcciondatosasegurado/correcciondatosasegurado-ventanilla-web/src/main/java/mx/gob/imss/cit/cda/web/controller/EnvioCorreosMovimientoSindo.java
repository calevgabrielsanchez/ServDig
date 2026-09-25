package mx.gob.imss.cit.cda.web.controller;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

import mx.gob.imss.cit.cda.service.interfaces.EnvioCorreoMovimientosSindoRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebService(name = "envioCorreosMovimientoSindo", serviceName = "EnvioCorreosMovimientoSindo", portName = "EnvioCorreosMovimientoSindoWSEndPointPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@BindingType(value = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class EnvioCorreosMovimientoSindo extends SpringBeanAutowiringSupport{
	
	@Autowired
	@Qualifier("envioCorreoMovimientosSindoBusiness")
	private EnvioCorreoMovimientosSindoRemote envicoCorreoMovimientosSindoBusiness;

	@WebMethod(operationName = "enviarNotificacionConstancia")
	public Boolean enviarNotificacionesCDA() {
		envicoCorreoMovimientosSindoBusiness.enviarCorreosMovimientoSindoCDA();
		return true;
	}

}
