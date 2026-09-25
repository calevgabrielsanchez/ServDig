package mx.imss.estrados.service.interfaces;

import javax.ejb.Remote;

import mx.imss.estrados.paginado.dto.PaginadoRequest;
import mx.imss.estrados.paginado.dto.PaginadoResponse;

@Remote
public interface ConsultaExternaServiceRemote {
	
	public PaginadoResponse consultaExternaPaginada(PaginadoRequest paginadoRequest);

}
