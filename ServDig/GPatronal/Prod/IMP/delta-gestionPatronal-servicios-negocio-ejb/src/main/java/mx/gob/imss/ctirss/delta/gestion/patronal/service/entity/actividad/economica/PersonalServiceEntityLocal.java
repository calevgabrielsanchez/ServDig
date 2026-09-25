package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;

@Local
public interface PersonalServiceEntityLocal {

	Personal agregar(Personal personal) throws Exception;

	Personal actualizar(Personal personal) throws Exception;

	void eliminar(Personal personal) throws Exception;

	Personal obtener(Personal personal) throws Exception;

	List<Personal> obtenerComoLista(Personal personal) throws Exception;

	DatosSalidaPaginador<Personal> paginar(
			DatosEntradaPaginador<Personal> parametrosPaginador);

	int consultarNumRegistrosPorActividadEconomica(
			Long cveActividadEconomica);

	// void validaExistePersonal(Personal personal) throws
	// PersonalInvalidoException, OficioOcupacionYaExisteException,
	// ModelAccessException;
	void validaExistePersonal(Personal personal) throws Exception;

	// void validaLimMinRegPersonal(Personal personal) throws
	// PersonalLimiteMinRegExcedidoException, ModelAccessException;
	void validaLimMinRegPersonal(Personal personal) throws Exception;

}
