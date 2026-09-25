package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Respuesta;

@Stateless(name = "respuestaDao", mappedName = "respuestaDao")
public class RespuestaDao implements RespuestaDaoLocal{

	@Override
	public Respuesta getRespuesta(Long idRespuesta) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
