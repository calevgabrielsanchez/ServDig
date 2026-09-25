package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;

@Local
public interface MateriaPrimaServiceEntityLocal {
	

	MateriaPrima agrega(MateriaPrima model) throws Exception;
	
	MateriaPrima actualiza(MateriaPrima model) throws Exception;
	
	void elimina(MateriaPrima model) throws Exception;
	
	MateriaPrima consultaPorClave(MateriaPrima model) throws Exception;
	DatosSalidaPaginador<MateriaPrima> consultarMateriaPrima(
			DatosEntradaPaginador<MateriaPrima> parametrosPaginador);

	int consultarNumRegistros(MateriaPrima model);

	MateriaPrima validaExisteMateriaPrimaMaterial(MateriaPrima materiaPrima) throws Exception;

	int consultarNumRegistrosPorActividadEconomica(Long cveActividadEconomica);

	List<MateriaPrima> consultaPorClaveActividad(MateriaPrima materiaPrima) throws Exception;


}
