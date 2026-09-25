package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;

@Local
public interface TransporteServiceEntityLocal {
	
	EquipoTransporte agrega(EquipoTransporte model) throws Exception;
	
	
	EquipoTransporte actualiza(EquipoTransporte model) throws Exception;
	
	
	
	void elimina(EquipoTransporte model) throws Exception;
	
	
	EquipoTransporte consultaPorClave(EquipoTransporte model) throws Exception;
	
	
	DatosSalidaPaginador<EquipoTransporte> paginar(
			DatosEntradaPaginador<EquipoTransporte> parametrosPaginador);

	
	
	int consultarNumRegistros(EquipoTransporte model);

	
	void validaExisteTransporte(EquipoTransporte model) throws Exception;

	int consultarNumRegistrosPorActividadEconomica(Long cveActividadEconomica);

	
	void validaLimMaxRegTransporte(EquipoTransporte  transporte) throws Exception;

	
	List<EquipoTransporte> consultaPorClaveActividad(EquipoTransporte model) throws Exception;
	
	/**
	 * Obtiene catalogo de tipos de combustible
	 * @author Hugo Armando Martínez Chamónica
	 * @return List<TipoCombustible>
	 */
	List<TipoCombustible> consultarTiposDeCombustibleActivos();
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return EquipoTransporte
	 */
	EquipoTransporte consultaPorDescripcionSujetoObligado(EquipoTransporte model);
}
