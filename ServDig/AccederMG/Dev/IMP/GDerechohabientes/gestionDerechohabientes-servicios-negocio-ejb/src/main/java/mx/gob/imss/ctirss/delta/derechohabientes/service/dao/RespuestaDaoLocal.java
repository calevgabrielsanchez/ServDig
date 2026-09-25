/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Respuesta;

/**
 * @author ghdolores
 *
 */
@Local
public interface RespuestaDaoLocal {

	Respuesta getRespuesta(Long idRespuesta);
}
