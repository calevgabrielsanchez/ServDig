/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Servicio;

/**
 * @author guillermo.hernandezd
 *
 */
@Local
public interface ServiciosDaoLocal {

	List<Servicio> findServicios() throws DerechohabientesBusinessException, Exception;
	
}
