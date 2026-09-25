/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.ws.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultarVigenciaServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta1;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta2;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta3;

/**
 * @author JUAN MANUEL MARQUEZ
 *
 */
@WebService(name = "consultaVigenciaService",
			portName = "consultaVigenciaPort",
			serviceName = "consultaVigenciaService",
			targetNamespace = "http://mx.gob.imss.ctirss.delta.derechohabientes.ws.service/")
@Stateless
public class ConsultaVigenciaWS extends AbstractServiceBusiness implements ConsultaVigenciaWSRemote {

	
	
	@EJB(mappedName="consultaVigenciaService")
	ConsultarVigenciaServiceRemote consultaVigenciaServiceRemote;
	
	
	@WebMethod
	@WebResult(name="consultaVigencia3Response" , targetNamespace= "http://mx.gob.imss.ctirss.delta.derechohabientes.modelo")
	public Respuesta3 consultaVigencia3(String nss) throws DerechohabientesBusinessException {
			
		Respuesta3 response = new Respuesta3();
		response = this.consultaVigenciaServiceRemote.consultaVigencia3(nss);

		return response;
	}


	@WebMethod
	@WebResult(name="consultaVigencia2Response" , targetNamespace= "http://mx.gob.imss.ctirss.delta.derechohabientes.modelo")
	public Respuesta2 consultaVigencia2(String nss, String umf,
			String delegacion, String cpid)
			throws DerechohabientesBusinessException {
		
		Respuesta2 response = new Respuesta2();
		response = this.consultaVigenciaServiceRemote.consultaVigencia2(nss, umf, delegacion, cpid);
		
		return response;
	}


	@WebMethod
	@WebResult(name="consultaVigencia1Response" , targetNamespace= "http://mx.gob.imss.ctirss.delta.derechohabientes.modelo")
	public Respuesta1 consultaVigencia1(String nss,String umf,
			String delegacion, String cpid) throws DerechohabientesBusinessException {
		
		Respuesta1 response = new Respuesta1();
		
		response = this.consultaVigenciaServiceRemote.consultaVigencia1(nss, umf, delegacion, cpid);
		
		return response;
	}				
	
	
}
