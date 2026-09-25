package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAseguradoDTO;

@Remote
public interface ConsultaDatosAseguradoServiceRemote {
	
	DatosAseguradoDTO consultaDatosAseguradoPorNSS(String nss) throws DerechohabientesBusinessException,Exception;

}
