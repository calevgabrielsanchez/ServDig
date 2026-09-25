package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.ParametrosServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;

@Stateless(mappedName = "parametrosServiceBusiness", name = "parametrosServiceBusiness")
public class ParametrosServiceBusiness extends AbstractServiceBusiness
		implements ParametrosServiceBusinessRemote,
		ParametrosServiceBusinessLocal {

	@EJB
	private ParametrosServiceEntityLocal parametrosServiceEntity;

	@Override
	public String obtenerParametroDeConfiguracion(String key) {
		return parametrosServiceEntity.obtenerValorDeParametroPorLlave(key);
	}
	
	@Override
	public String obtenerParametroDeDelegacionCDA(String idDelegacion){
		return parametrosServiceEntity.obtenerValorDeParametroDeDelegacionCDA(idDelegacion);
	}

	@Override
	public Map<String, String> obtenerGrupoParametros(List<String> llaves) {
		return parametrosServiceEntity.obtenerGrupoParametrosPorLlave(llaves);
	}
}
