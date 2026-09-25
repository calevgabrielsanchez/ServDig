package mx.imss.estrados.service.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.paginado.dto.PaginadoRequest;

@Local
public interface ConsultaExternaDAO {
	
	public Integer contarTotalRegistros();
	public Integer contarRegistrosFiltrados(PaginadoRequest paginadoRequest);
	public List<NeeNotificaciones> filtrar(PaginadoRequest paginadoRequest);
	public SsoVwUsuario obtenerSsoVwUsuario(String cveUsuario);

}
