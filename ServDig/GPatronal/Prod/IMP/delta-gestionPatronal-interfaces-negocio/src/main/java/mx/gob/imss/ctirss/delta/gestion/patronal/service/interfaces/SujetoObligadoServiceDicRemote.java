package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;


@Remote
public interface SujetoObligadoServiceDicRemote {

	
	List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalRemote(Long cveIdPersona);
}
