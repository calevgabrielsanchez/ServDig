package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo82;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo83;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo84;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;



/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Local
public interface DerechohabienteDaoLocal {
	
	Derechohabiente getDerechohabiente(Long idDerechohabiente) throws DerechohabientesBusinessException, Exception;
	TramiteRegistroDerechohabiente getRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception ;
	List <Articulo82> getArticulo82 () throws DerechohabientesBusinessException, Exception;
	List <Articulo83> getArticulo83 (Integer tiempoEspera) throws DerechohabientesBusinessException, Exception;
	List <Articulo84> getArticulo84 () throws DerechohabientesBusinessException, Exception;
	void updateRegistroDerechohabiente(TramiteRegistroDerechohabiente registro) throws DerechohabientesBusinessException, Exception;
	void updateDerechohabiente(Derechohabiente derechohabiente) throws DerechohabientesBusinessException, Exception;
	Long updateMedicoEnTurnoDerechohabientesbyDomicilio(
			Asentamiento Domicilio,
			MedicoEnTurno medicoEnTurnoM, MedicoEnTurno medicoEnTurnoV, Long idTramite, DitUmfCodPo origen, DitUmfCodPo destino) throws Exception;
}
