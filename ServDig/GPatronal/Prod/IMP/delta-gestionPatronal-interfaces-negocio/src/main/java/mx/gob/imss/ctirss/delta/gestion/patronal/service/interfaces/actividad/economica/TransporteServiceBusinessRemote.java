package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;

@Remote
public interface TransporteServiceBusinessRemote {
	
	DatosSalidaPaginador<EquipoTransporte> paginarTransporte( DatosEntradaPaginador<EquipoTransporte> datatablein);
	
	EquipoTransporte agregarTransporte(EquipoTransporte instance) throws Exception;
	
	void eliminarTransporte(EquipoTransporte transporte) throws Exception;
	
	EquipoTransporte getTransporte(EquipoTransporte transporte);
	
	EquipoTransporte modificarTransporte(EquipoTransporte instance) throws Exception;
	
	/**
	 * Obtiene el catalogo de combustibles activos de la tabla DIC_TIPO_COMBUSTIBLE
	 * @return List<TipoCombustible>
	 */
	List<TipoCombustible> obtenerTiposDeCombustible();

}
