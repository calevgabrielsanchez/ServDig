/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

/**
 * @author Ivan Cervantes
 *
 */

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.EjbProyectoTransaccionalLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistraSolicitudWSFachada;
import mx.gob.imss.ctirss.delta.model.derechohabientes.AltaTransaccional;
import mx.gob.imss.ctirss.delta.model.derechohabientes.EjecucionServicio;


@WebService(serviceName = "WsProyectoTransaccional")
public class WsProyectoTransaccional implements RegistraSolicitudWSFachada {

	@EJB
	private EjbProyectoTransaccionalLocal ejbProyectoTransaccional;

	@Override
	@WebMethod(operationName = "alta")
	@WebResult(name = "EjecucionServicio")
	public EjecucionServicio alta(
			@WebParam(name = "AltaTransaccional") final AltaTransaccional altaTransaccional) {
		EjecucionServicio respuesta = new EjecucionServicio();
		try {
			// EjbLocator.getEjbRemote().alta(altaTransaccional);
			ejbProyectoTransaccional.alta(altaTransaccional);
		} catch (Exception e) {
			respuesta
					.setMensaje("1 - Se Genero un error al intentar dar de alta");
			respuesta.setDetalle(e.getCause().getMessage());
		}

		return respuesta;
	}
}
