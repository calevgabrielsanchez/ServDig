/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao.asegurado;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabientes.ReporteSav011;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Servicio;

/**
 * @author ghdolores
 * 
 */
@Local
public interface DitAseguradoDaoLocal {

	ReporteSav011 getAsegurado(Integer idAsegurado);
	List<Servicio> getServiciosByAsegurado(Long idNss) throws DerechohabientesBusinessException, Exception;

}
