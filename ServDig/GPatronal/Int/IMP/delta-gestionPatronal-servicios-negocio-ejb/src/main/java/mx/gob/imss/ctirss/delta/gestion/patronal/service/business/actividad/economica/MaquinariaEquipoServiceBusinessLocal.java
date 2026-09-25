package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;

@Local
public interface MaquinariaEquipoServiceBusinessLocal {
	
	DatosSalidaPaginador<MaquinariaEquipo> paginarMaquinariaEquipo( DatosEntradaPaginador<MaquinariaEquipo> datatablein);
	
	MaquinariaEquipo agregarMaquinariaEquipo(MaquinariaEquipo instance) throws Exception;
	
	void eliminarMaquinariaEquipo(MaquinariaEquipo maquinariaEquipo) throws Exception;
	
	MaquinariaEquipo getMaquinariaEquipo(MaquinariaEquipo maquinariaEquipo);
	
	MaquinariaEquipo modificarMaquinariaEquipo(MaquinariaEquipo instance) throws Exception;

	/**
	 * Obtiene los tipos de maquinaria activos
	 * @return List<TipoMaquinariaEquipo>
	 */
	List<TipoMaquinariaEquipo> obtenerTiposMaquinaria();
}
