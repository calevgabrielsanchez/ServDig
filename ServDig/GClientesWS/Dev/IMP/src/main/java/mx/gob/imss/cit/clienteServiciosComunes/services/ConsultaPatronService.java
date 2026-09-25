package mx.gob.imss.cit.clienteServiciosComunes.services;

import mx.gob.imss.cit.clienteServiciosComunes.model.RespuestaConsultaPatron;

public interface ConsultaPatronService {
	
	RespuestaConsultaPatron obtenerInformacionPatronPorRFC(String rfc);

}
