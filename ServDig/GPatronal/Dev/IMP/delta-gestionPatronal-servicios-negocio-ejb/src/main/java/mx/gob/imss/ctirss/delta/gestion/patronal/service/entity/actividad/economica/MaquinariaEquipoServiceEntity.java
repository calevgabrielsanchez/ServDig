package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MaquinariaEquipoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class MaquinariaEquipoServiceEntity extends AbstractServiceEntity
		implements MaquinariaEquipoServiceEntityLocal {
	
	@EJB
	MaquinariaEquipoServiceUtilityLocal utility;
	
	@Override
	public MaquinariaEquipo agrega(MaquinariaEquipo model) throws Exception {
		
		DitMaquinariaEquipo maq=utility.convertirModelToEntity(model);
		maq.setFecRegistroAlta(Calendar.getInstance().getTime());
		DicTipoMaquinariaEquipo tipo = this.em.find(DicTipoMaquinariaEquipo.class, model.getTipo().getId());
		maq.setDicTipoMaquinariaEquipo(tipo);
		this.em.persist(maq);
		model.setId(maq.getCveIdMaquinariaEquipo());
		this.log.debug("Clave generada para maquinaria: "+maq.getCveIdMaquinariaEquipo());
		return model;
	}

	@Override
	public MaquinariaEquipo actualizar(MaquinariaEquipo model) throws Exception {
		this.log.debug("Se actualizará la maquinaria: "+model.getId());
		DitMaquinariaEquipo maqToUpdate=utility.convertirModelToEntity(model);
		DitMaquinariaEquipo entity = (DitMaquinariaEquipo)this.getSession().load(DitMaquinariaEquipo.class, model.getId());
		DicTipoMaquinariaEquipo entityTipo = (DicTipoMaquinariaEquipo)this.getSession().load(DicTipoMaquinariaEquipo.class, model.getTipo().getId());
		utility.mergeEntities(maqToUpdate, entity);
		entity.setDicTipoMaquinariaEquipo(entityTipo);
		entity.setFecRegistroActualizado(Calendar.getInstance().getTime());
		this.getSession().update(entity);
		this.log.debug("Se actualizó la maquinaria: "+entity.getCveIdMaquinariaEquipo());
		return model;
	}

	@Override
	public void elimina(MaquinariaEquipo model) throws Exception {
		DitMaquinariaEquipo entity = (DitMaquinariaEquipo)this.getSession().load(DitMaquinariaEquipo.class, model.getId());
		entity.setFecRegistroBaja(Calendar.getInstance().getTime());
		this.getSession().update(entity);
	}

	@Override
	public List<MaquinariaEquipo> consultaPorClaveActividad(
			MaquinariaEquipo model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<MaquinariaEquipo> paginar(
			DatosEntradaPaginador<MaquinariaEquipo> parametrosPaginador) {
		DatosSalidaPaginador<MaquinariaEquipo> response = new DatosSalidaPaginador<MaquinariaEquipo>();
		List<MaquinariaEquipo> result = null;		
		
		/*Objeto con los filtros seleccionados en la vista*/
		MaquinariaEquipo filtro =  parametrosPaginador.getModelo();
		
		
		Criteria criteria = this.getSession().createCriteria(DitMaquinariaEquipo.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", 
				filtro.getSujetoObligado().getCveIdSujetoObligado()));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		/**s
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		criteria.setProjection(Projections.rowCount());
		try{
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		 
		List<DitMaquinariaEquipo> entities = criteria
				.setFirstResult(parametrosPaginador.getiDisplayStart())
				.setMaxResults(parametrosPaginador.getiDisplayLength()).list();
		
		try {
			result = utility.convertListOfEntitiesToListOfModel(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setAaData(result);
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(iTotalRecords);

		return response;
	}

	@Override
	public void validaLimMinRegMaquinariaEquipo(
			MaquinariaEquipo maquinariaEquipo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MaquinariaEquipo consultarPorClave(MaquinariaEquipo model)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

 
	@Override
	public MaquinariaEquipo consultarMaquinariaEquipoPorNombre(
			MaquinariaEquipo maquinariaEquipo){
		Criteria criteria = this.getSession().createCriteria(DitMaquinariaEquipo.class);
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado",maquinariaEquipo.getSujetoObligado().getCveIdSujetoObligado()));
		criteria.add(Restrictions.eq("desNombre",maquinariaEquipo.getDesNombre()));
		
		
		DitMaquinariaEquipo result = (DitMaquinariaEquipo)criteria.uniqueResult();
		MaquinariaEquipo mResult = null;
		if(result != null){
			mResult=utility.convertirEntityToModel(result);
		}
		return mResult;
	}

	@Override
	public void validaLimMaxRegMaquinariaEquipo(
			MaquinariaEquipo maquinariaEquipo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int consultarNumRegistrosPorActividadEconomica(
			Long cveActividadEconomica) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.MaquinariaEquipoServiceEntityLocal#consultarTiposDeMaquinariaActivos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoMaquinariaEquipo> consultarTiposDeMaquinariaActivos() {
		Criteria criteria = this.getSession().createCriteria(DicTipoMaquinariaEquipo.class);
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		List<DicTipoMaquinariaEquipo> tipos = criteria.list();
		return utility.convertListOfEntitiesToListOfModelTipoMaquinaria(tipos);
	}

}
