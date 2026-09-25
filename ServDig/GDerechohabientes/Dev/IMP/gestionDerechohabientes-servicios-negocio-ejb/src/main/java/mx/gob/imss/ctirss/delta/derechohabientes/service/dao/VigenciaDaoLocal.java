/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ModServPresDerechohab;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ServicioPrestDerechohab;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
/**
 * @author JUAN MANUEL MÁRQUEZ
 *
 */
@Local
public interface VigenciaDaoLocal {
	
	EstadoDerechohabiente getEstadoDerechohabiente(long idPersona,long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	SujetoObligado getPatronSujetoObligado(long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception;
	boolean getDerechoSM(long idModalidad) throws Exception;
	boolean getDerechoINC(long idModalidad) throws Exception;
	List<ModServPresDerechohab> getServiciosByAsegurado(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception ;
	List<ModServPresDerechohab> getServiciosByPensionado(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception ;
	List<ServicioPrestDerechohab> findServicios() throws DerechohabientesBusinessException, Exception;
	Modalidad getModalidMayorByAsignacionNss(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception ;
	List<ModServPresDerechohab> getServiciosPorModalidad(List<Long> idModalidades) throws DerechohabientesBusinessException, Exception;
	
	
}
