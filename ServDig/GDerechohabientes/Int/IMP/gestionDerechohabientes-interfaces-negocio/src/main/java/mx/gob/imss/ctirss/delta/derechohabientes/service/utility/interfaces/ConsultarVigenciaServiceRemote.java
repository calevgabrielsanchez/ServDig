package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta1;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta2;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta3;

/**
 * @author JUAN MANUEL MÁRQUEZ
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 15/06/2012
 */

@Remote
public interface ConsultarVigenciaServiceRemote {
	
	public Respuesta3 consultaVigencia3(String nss);
	public Respuesta2 consultaVigencia2(String nss, String umf, String delegacion, String cpid);
	public Respuesta1 consultaVigencia1(String nss, String umf, String delegacion, String cpid);
}
