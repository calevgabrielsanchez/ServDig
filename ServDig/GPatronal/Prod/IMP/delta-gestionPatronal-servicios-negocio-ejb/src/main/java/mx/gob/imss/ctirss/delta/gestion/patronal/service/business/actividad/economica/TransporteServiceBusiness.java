package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.TransporteServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.TransporteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;

@Stateless(name="transporteServiceBusiness", mappedName="transporteServiceBusiness")
public class TransporteServiceBusiness extends AbstractServiceBusiness
		implements TransporteServiceBusinessRemote,
		TransporteServiceBusinessLocal {
	@EJB
	TransporteServiceEntityLocal entity;
	
	@Override
	public DatosSalidaPaginador<EquipoTransporte> paginarTransporte(
			DatosEntradaPaginador<EquipoTransporte> datatablein) {
		return entity.paginar(datatablein);
	}

	@Override
	public EquipoTransporte agregarTransporte(EquipoTransporte instance)
			throws Exception {
		if(instance.getTipoCombustible().getClave()==null)
			throw new GestionPatronalBusinessException("El tipo de combustible es requerido");
		validaTransporteExistente(instance);
		return entity.agrega(instance);
	}

	@Override
	public void eliminarTransporte(EquipoTransporte transporte)
			throws Exception {
		if(transporte.getId()==null){
			throw new GestionPatronalBusinessException("Se requiere el identificador del elemento a eliminar");
		}
		entity.elimina(transporte);
	}

	@Override
	public EquipoTransporte getTransporte(EquipoTransporte transporte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EquipoTransporte modificarTransporte(EquipoTransporte instance)
			throws Exception {
		if(instance.getId()==null){
			throw new GestionPatronalBusinessException("Se requiere el identificador del elemento a actualizar");
		}
		validaTransporteExistente(instance);
		return entity.actualiza(instance);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.TransporteServiceBusinessRemote#obtenerTiposDeCombustible()
	 */
	@Override
	public List<TipoCombustible> obtenerTiposDeCombustible() {
		return entity.consultarTiposDeCombustibleActivos();
	}
	
	/**
	 * Valida que no existan transportes con la misma descripción
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 */
	private void validaTransporteExistente(EquipoTransporte model) throws GestionPatronalBusinessException{
		EquipoTransporte result = entity.consultaPorDescripcionSujetoObligado(model);
		
		if(result!=null){
			//Para registros a insertar
			if(model.getId()==null){
				throw new GestionPatronalBusinessException("Actualmente se tiene registrado un equipo con la descripción proporcionada");
			}else if(!model.getId().equals(result.getId())){
				//Para actualizar registros
				throw new GestionPatronalBusinessException("Actualmente se tiene un equipo con la misma descripción con la cual solcita actualizar el registro");
			}
		}
		
	}

}
