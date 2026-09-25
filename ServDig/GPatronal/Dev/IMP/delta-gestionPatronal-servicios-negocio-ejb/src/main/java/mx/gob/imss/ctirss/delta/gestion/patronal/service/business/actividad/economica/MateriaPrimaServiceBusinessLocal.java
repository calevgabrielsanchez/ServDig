package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;

@Local
public interface MateriaPrimaServiceBusinessLocal {
	
	void agregarMateriaPrima( MateriaPrima  materiaPrima) throws Exception;
	
	void eliminarMateriaPrima(MateriaPrima  materiaPrima) throws Exception;

	void modificarMateriaPrima(MateriaPrima  materiaPrima) throws Exception;
	
	MateriaPrima getMateriaPrima(MateriaPrima  materiaPrima);

	DatosSalidaPaginador<MateriaPrima> paginarMateriasPrimas( DatosEntradaPaginador<MateriaPrima> datatablein);
	
}
