package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceDicRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Stateless(name="sujetoObligadoServiceDIC" ,mappedName="sujetoObligadoServiceDIC" )
public class SujetoObligadoServiceDicImpl extends AbstractServiceEntity
		implements SujetoObligadoServiceDicRemote {
	
	@EJB
	private SujetoObligadoServiceEntityLocal sujetoObligadoEntity;

	@Override
	public List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalRemote(
			Long cveIdPersona) {
		// TODO Auto-generated method stub
		return sujetoObligadoEntity.consultarSujetosRepresentadosPorRepresentanteLegal(cveIdPersona);
	}

}
