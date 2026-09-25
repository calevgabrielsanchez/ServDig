/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.WebServiceClient;

import mx.gob.imss.ctirss.delta.model.derechohabientes.AltaTransaccional;
import mx.gob.imss.ctirss.delta.model.derechohabientes.EjecucionServicio;

/**
 * @author Ivan Cervantes
 * 
 */
@WebServiceClient
public interface RegistraSolicitudWSFachada {

	@WebMethod(operationName = "alta")
	@WebResult(name = "EjecucionServicio")
	public EjecucionServicio alta(
			@WebParam(name = "AltaTransaccional") final AltaTransaccional altaTransaccional);

}
