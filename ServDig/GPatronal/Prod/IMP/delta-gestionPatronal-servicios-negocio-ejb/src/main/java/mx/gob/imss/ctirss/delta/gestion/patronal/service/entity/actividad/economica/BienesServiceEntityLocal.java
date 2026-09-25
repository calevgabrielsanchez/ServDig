package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;

@Local
public interface BienesServiceEntityLocal {
	
	Bien agregar(Bien bien) throws Exception;

	Bien actualizar(Bien bien) throws Exception;

	void eliminar(Bien bien) throws Exception;

	Bien obtener(Bien bien) throws Exception;

	List<Bien> obtenerComoLista(Bien bien) throws Exception;

	DatosSalidaPaginador<Bien> paginar(
			DatosEntradaPaginador<Bien> parametrosPaginador);

	void validaExisteBien(Bien bien) throws Exception;

	void validaLimMinRegBien(Bien bien) throws Exception;
	
	Bien findBienByDescription(Bien bien);

}
