package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.MaquinariaEquipoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.MaquinariaEquipoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;

@Stateless(name="maquinariaEquipoServiceBusiness", mappedName="maquinariaEquipoServiceBusiness")
public class MaquinariaEquipoServiceBusiness extends AbstractServiceBusiness
		implements MaquinariaEquipoServiceBusinessLocal,
		MaquinariaEquipoServiceBusinessRemote {
	
	@EJB
	MaquinariaEquipoServiceEntityLocal entity;
	
	@Override
	public DatosSalidaPaginador<MaquinariaEquipo> paginarMaquinariaEquipo(
			DatosEntradaPaginador<MaquinariaEquipo> datatablein) {
		
		return entity.paginar(datatablein);
	}

	@Override
	public MaquinariaEquipo agregarMaquinariaEquipo(MaquinariaEquipo instance)
			throws Exception {
		if(instance.getTipo().getId()==null){
			throw new GestionPatronalBusinessException("El tipo de maquinaria es requerido");
		}
		validaMaquinariaEquipoExistente(instance);
		return entity.agrega(instance);
	}

	@Override
	public void eliminarMaquinariaEquipo(MaquinariaEquipo maquinariaEquipo)
			throws Exception {
		if(maquinariaEquipo.getId()==null){
			throw new GestionPatronalBusinessException("Se requiere el identificador del elemento a eliminar");
		}
		entity.elimina(maquinariaEquipo);
	}

	@Override
	public MaquinariaEquipo getMaquinariaEquipo(
			MaquinariaEquipo maquinariaEquipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MaquinariaEquipo modificarMaquinariaEquipo(MaquinariaEquipo instance)
			throws Exception {
		if(instance.getId()==null){
			throw new GestionPatronalBusinessException("Se requiere el identificador del elemento a actualizar");
		}
		validaMaquinariaEquipoExistente(instance);
		return entity.actualizar(instance);
	}
	
	private void validaMaquinariaEquipoExistente(MaquinariaEquipo maquinariaEquipo) throws GestionPatronalBusinessException{		
		MaquinariaEquipo maq = entity.consultarMaquinariaEquipoPorNombre(maquinariaEquipo);
		
		if(maq!=null){
			//Para registros a insertar
			if(maquinariaEquipo.getId()==null){
				throw new GestionPatronalBusinessException("Actualmente se tiene registrado un equipo con la descripción proporcionada");
			}else if(!maquinariaEquipo.getId().equals(maq.getId())){
				//Para actualizar registros
				throw new GestionPatronalBusinessException("Actualmente se tiene un equipo con la misma descripción con la cual solcita actualizar el registro");
			}
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica.MaquinariaEquipoServiceBusinessLocal#obtenerTiposMaquinaria()
	 */
	@Override
	public List<TipoMaquinariaEquipo> obtenerTiposMaquinaria() {
		return entity.consultarTiposDeMaquinariaActivos();
	}
}
