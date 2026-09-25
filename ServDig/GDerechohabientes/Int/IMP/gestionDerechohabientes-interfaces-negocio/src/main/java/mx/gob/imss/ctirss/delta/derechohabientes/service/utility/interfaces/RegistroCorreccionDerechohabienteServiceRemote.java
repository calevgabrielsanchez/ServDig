package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

@Remote
public interface RegistroCorreccionDerechohabienteServiceRemote {
	
	List<GrupoFamiliar> findGrupoFamiliarRegistroCorreccion(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;

}
