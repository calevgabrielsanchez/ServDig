package mx.gob.imss.cit.cda.service.entity;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.BitacoraMovimientoSindoCDA;

@Remote
public interface EnvioCorreoMovimientosSindoLocal {

	void enviarCorreo(Map<String, List<BitacoraMovimientoSindoCDA>> bitacoraSINDO, String folio);
	
}
