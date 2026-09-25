package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface ParametrosServiceEntityLocal {

	String obtenerValorDeParametroPorLlave(String llave);
	
	String obtenerValorDeParametroDeDelegacionCDA(String idDelegacion);

	Map<String, String> obtenerGrupoParametrosPorLlave(List<String> llaves);

}
