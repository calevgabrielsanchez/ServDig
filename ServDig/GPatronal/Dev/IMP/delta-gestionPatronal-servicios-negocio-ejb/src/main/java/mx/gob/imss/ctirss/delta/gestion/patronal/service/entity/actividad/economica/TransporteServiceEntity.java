package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.TransporteServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class TransporteServiceEntity extends AbstractServiceEntity implements
		TransporteServiceEntityLocal {
	
	@EJB
	TransporteServiceUtilityLocal utility;
	
	@Override
	public EquipoTransporte agrega(EquipoTransporte model) throws Exception {
		DitEquipoTransporte equipoTransporte = utility.convertirModelToEntity(model);
		DicTipoCombustible tipo = this.em.find(DicTipoCombustible.class, model.getTipoCombustible().getClave());
		DitPatronSujetoObligado so = this.em.find(DitPatronSujetoObligado.class, model.getSujetoObligado().getCveIdSujetoObligado());
		equipoTransporte.setDicTipoCombustible(tipo);
		equipoTransporte.setDitPatronSujetoObligado(so);
		equipoTransporte.setFecRegistroAlta(Calendar.getInstance().getTime());
		this.em.persist(equipoTransporte);
		model.setId(equipoTransporte.getCveIdEquipoTransporte());
		return model;
	}

	@Override
	public EquipoTransporte actualiza(EquipoTransporte model) throws Exception {
		DitEquipoTransporte equipoActual = (DitEquipoTransporte)this.getSession().load(DitEquipoTransporte.class, model.getId());
		DicTipoCombustible tipoNuevo = (DicTipoCombustible)this.getSession().load(DicTipoCombustible.class, model.getTipoCombustible().getClave());
		DitPatronSujetoObligado soNuevo = this.em.find(DitPatronSujetoObligado.class, model.getSujetoObligado().getCveIdSujetoObligado());
		DitEquipoTransporte equipoNuevo=utility.convertirModelToEntity(model);
		utility.mergeEntities(equipoNuevo, equipoActual);
		equipoActual.setDicTipoCombustible(tipoNuevo);
		equipoActual.setDitPatronSujetoObligado(soNuevo);
		equipoActual.setFecRegistroActualizado(Calendar.getInstance().getTime());
		this.getSession().update(equipoActual);
		return model;
	}

	@Override
	public void elimina(EquipoTransporte model) throws Exception {
		DitEquipoTransporte equipoActual = (DitEquipoTransporte)this.getSession().load(DitEquipoTransporte.class, model.getId());
		equipoActual.setFecRegistroBaja(Calendar.getInstance().getTime());
		this.getSession().update(equipoActual);
	}

	@Override
	public EquipoTransporte consultaPorClave(EquipoTransporte model)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<EquipoTransporte> paginar(
			DatosEntradaPaginador<EquipoTransporte> parametrosPaginador) {
		DatosSalidaPaginador<EquipoTransporte> response = new DatosSalidaPaginador<EquipoTransporte>();
		List<EquipoTransporte> result = null;		
		
		/*Objeto con los filtros seleccionados en la vista*/
		EquipoTransporte filtro =  parametrosPaginador.getModelo();
		
		
		Criteria criteria = this.getSession().createCriteria(DitEquipoTransporte.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", filtro.getSujetoObligado().getCveIdSujetoObligado()));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		/**
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
		
		 
		List<DitEquipoTransporte> entities = criteria
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
	public int consultarNumRegistros(EquipoTransporte model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void validaExisteTransporte(EquipoTransporte model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int consultarNumRegistrosPorActividadEconomica(
			Long cveActividadEconomica) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void validaLimMaxRegTransporte(EquipoTransporte transporte)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EquipoTransporte> consultaPorClaveActividad(
			EquipoTransporte model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.TransporteServiceEntityLocal#consultarTiposDeCombustibleActivos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoCombustible> consultarTiposDeCombustibleActivos() {
		Criteria criteria = this.getSession().createCriteria(DicTipoCombustible.class);
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		List<DicTipoCombustible> tiposCombustible = criteria.list();
		return utility.convertListOfEntitiesToListOfModelTipoCombustible(tiposCombustible);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.TransporteServiceEntityLocal#consultaPorDescripcionSujetoObligado(mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte)
	 */
	@Override
	public EquipoTransporte consultaPorDescripcionSujetoObligado(
			EquipoTransporte model) {
		Criteria criteria = this.getSession().createCriteria(DitEquipoTransporte.class);
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado",model.getSujetoObligado().getCveIdSujetoObligado()));
		criteria.add(Restrictions.eq("desNombre",model.getDesNombre()));
		
		DitEquipoTransporte equipo = (DitEquipoTransporte)criteria.uniqueResult();
		EquipoTransporte result = null;
		if(equipo!= null){
			result = utility.convertirEntityToModel(equipo);
		}
		
		return result;
	}

}
