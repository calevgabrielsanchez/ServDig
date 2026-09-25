package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface ParametrosServiceBusinessRemote {

	String obtenerParametroDeConfiguracion(String key);
	
	String obtenerParametroDeDelegacionCDA(String idDelegacion);	

	Map<String, String> obtenerGrupoParametros(List<String> llaves);

}
