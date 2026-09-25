/**
 * 
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion;

import gob.imss.tecnologia.comunes.excepciones.ExcepcionIMSS;

import javax.ejb.Stateless;
import javax.jws.WebService;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.AsignacionNSSBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.ResponseMainFrameBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.exception.NoHayConsecutivoException;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.interfaces.InicioLlamadaTransServiceRemote;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.proceso.CanaseProceso;

import org.apache.log4j.Logger;

/**
 * @author fernando.castellanos
 *
 */
@WebService
@Stateless
public class InicioLlamadaTransService implements
		InicioLlamadaTransServiceRemote {
	
	private static Logger log = Logger.getLogger(InicioLlamadaTransService.class);

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.ws.asignacion.implementacion.interfaces.InicioLlamadaTransServiceRemote#ejecutarAlta(mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.AsignacionNSSBean, java.lang.String, java.lang.String)
	 */
	@Override
	public ResponseMainFrameBean ejecutarAlta(AsignacionNSSBean forma) {
		ResponseMainFrameBean objResponse = null;
		CanaseProceso objCanase = new CanaseProceso();
		
		//obtenemos la serie
		String elemento = "-1";//req.getParameter("elemento");
		AsignacionNSSBean asegurado = new AsignacionNSSBean();
		//CanaseProceso canaseProceso = new CanaseProceso();
		Integer tipoTransaccion = new Integer(2);
		if(elemento.equals("-1")) {
			// En este caso no se eligió un NSS ya existente, en vez de eso
			// se ha solicitado asignar un nuevo NSS
			log.debug("el tramite que traigo es: HOLAAA" + tipoTransaccion);
			log.debug("la umf capturada es:     " + forma.getUMF());
			try {
				objResponse = objCanase.alta(forma);
			} catch (ExcepcionIMSS e) {
				log.error(e.getMensaje(), e);
				e.printStackTrace();
			} catch (NoHayConsecutivoException e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
			asegurado.setIdTransaccion(tipoTransaccion.intValue());
			if (objResponse != null) {
				log.debug("Finaliza la ejecución del servicio de asignación de NSS y me dio el nss" + objResponse.getStrResultado());
			}			
		}
		return objResponse;
	}

}
