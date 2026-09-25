package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;

@Local
public interface MaquinariaEquipoServiceEntityLocal {
	
	MaquinariaEquipo agrega(MaquinariaEquipo model) throws Exception;	
		
	void elimina(MaquinariaEquipo model) throws Exception;	
	
	List<MaquinariaEquipo> consultaPorClaveActividad(MaquinariaEquipo model) throws Exception;	
	
	DatosSalidaPaginador<MaquinariaEquipo> paginar(DatosEntradaPaginador<MaquinariaEquipo> parametrosPaginador);

	void validaLimMinRegMaquinariaEquipo(MaquinariaEquipo maquinariaEquipo) throws Exception;
	
	MaquinariaEquipo consultarPorClave(MaquinariaEquipo model) throws Exception;
	
	MaquinariaEquipo actualizar(MaquinariaEquipo model) throws Exception;
	
	MaquinariaEquipo consultarMaquinariaEquipoPorNombre(MaquinariaEquipo maquinariaEquipo);
	
	void validaLimMaxRegMaquinariaEquipo(MaquinariaEquipo maquinariaEquipo) throws Exception;

	int consultarNumRegistrosPorActividadEconomica(Long cveActividadEconomica);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @return List<TipoMaquinariaEquipo>
	 */
	List<TipoMaquinariaEquipo> consultarTiposDeMaquinariaActivos();
}
