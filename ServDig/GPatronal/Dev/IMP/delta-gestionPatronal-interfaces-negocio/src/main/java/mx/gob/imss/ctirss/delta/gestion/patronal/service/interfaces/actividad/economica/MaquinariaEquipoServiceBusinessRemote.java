package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;

@Remote
public interface MaquinariaEquipoServiceBusinessRemote {
	
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
