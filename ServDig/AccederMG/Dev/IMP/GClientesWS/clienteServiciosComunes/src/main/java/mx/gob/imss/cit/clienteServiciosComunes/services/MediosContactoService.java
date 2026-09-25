package mx.gob.imss.cit.clienteServiciosComunes.services;

import java.util.List;

import mx.gob.imss.cit.clienteServiciosComunes.model.MedioContacto;

public interface MediosContactoService {
	
	List<MedioContacto> recuperaMediosContacto(String rfc);

}
