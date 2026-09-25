package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface ParametrosServiceBusinessLocal {

	String obtenerParametroDeConfiguracion(String key);
	
	String obtenerParametroDeDelegacionCDA(String idDelegacion);

	Map<String, String> obtenerGrupoParametros(List<String> llaves);
}
