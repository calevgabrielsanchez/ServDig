/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

/**
 * @author ghdolores
 *
 */
@Local
public interface ProrrogaDaoLocal {
	TramiteProrroga saveProrroga(TramiteProrroga prorroga) throws DerechohabientesBusinessException, Exception;
	ConstanciaEstudio saveConstanciaEstudios(ConstanciaEstudio constancia);
	List<TramiteProrroga> getProrrogasActivas(Long idAsignacionNss, Long idPersona, Long tipoProrroga) throws DerechohabientesBusinessException, Exception;
	TramiteProrroga getProrrogaActivaPersona(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException, Exception;
	TramiteProrroga getProrrogaActiva(Long idAsignacionNSS,Long idPersona,Long idCaracter) throws DerechohabientesBusinessException, Exception;
	void updateProrroga(TramiteProrroga prorroga) throws Exception;
}
