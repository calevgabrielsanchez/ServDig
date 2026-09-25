package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.PersonalServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonal;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class PersonalServiceEntity extends AbstractServiceEntity implements
		PersonalServiceEntityLocal {
	
	@EJB
	private PersonalServiceUtilityLocal personalServiceUtility;

	@Override
	public Personal agregar(Personal personal) throws Exception {
		DitPersonal entity = null;
		System.out.println("Personal model para agregar: "+personal);
		entity = this.personalServiceUtility.convertirModelToEntity(personal);
		System.out.println("Personal entity trabajadores: "+entity.getNumeroTrabajadores());
		em.persist(entity);
		personal = this.personalServiceUtility.convertirEntityToModel(entity);

		return personal;
	}

	@Override
	public Personal actualizar(Personal personal) throws Exception {
		DitPersonal entity = new DitPersonal();
		
		entity = this.personalServiceUtility.convertirModelToEntity(personal);
		this.em.merge(entity);
//		this.personalServiceUtility.convertirEntityToModel(entity);
		
		return personal;
	}

	@Override
	public void eliminar(Personal personal) throws Exception {
		DitPersonal entity = null;
		
		entity = this.findById(personal.getClave(), DitPersonal.class);
		em.remove(entity);
//		model = personalUtility.convertirEntityToModel(entity);
		
	}

	@Override
	public Personal obtener(Personal personal) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personal> obtenerComoLista(Personal personal) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<Personal> paginar(
			DatosEntradaPaginador<Personal> parametrosPaginador) {
		DatosSalidaPaginador<Personal> response = new DatosSalidaPaginador<Personal>();
		List<Personal> result = null;		
		
		/*Objeto con los filtros seleccionados en la vista*/
		Personal filtro =  parametrosPaginador.getModelo();
		
		
		Criteria criteria = this.getSession().createCriteria(DitPersonal.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", filtro.getSujetoObligado().getCveIdSujetoObligado()));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
//		criteria.add(Restrictions.isNotNull("regPatron"));
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		 criteria.setProjection(Projections.rowCount());
		
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		int iTotalDisplayRecords = 0;
		
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		criteria.addOrder(Order.asc("cveIdPersonal"));
		
		 
		List<DitPersonal> entities = criteria
				.setFirstResult(parametrosPaginador.getiDisplayStart())
				.setMaxResults(parametrosPaginador.getiDisplayLength()).list();
		
		
		try {
			result = this.personalServiceUtility.convertListOfEntitiesToListOfModel(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);

		return response;
	}

	@Override
	public int consultarNumRegistrosPorActividadEconomica(
			Long cveActividadEconomica) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void validaExistePersonal(Personal personal) throws Exception {
		DitPersonal entity = null;
		
		entity = this.personalServiceUtility.convertirModelToEntity(personal);

		
		DitPersonal result = this.consultarPersonalPorOficioOcupacion(entity);
	
		if(result != null){
			//Si existe un producto entonces no se cumple con la regla.
			throw new Exception("El oficio u ocupaci\u00F3n ya existe.");
		}
		
	}

	@Override
	public void validaLimMinRegPersonal(Personal personal) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	private DitPersonal consultarPersonalPorOficioOcupacion(DitPersonal entity){

		this.log.debug("consultarPersonalPorOficioOcupacion ["
				+ entity.getOficioOcupacion() + " ]");

		DitPersonal entityResponse = null;
		
		Criteria criteria =  this.getSession().createCriteria(DitPersonal.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado()));
		criteria.add(  Restrictions.ilike( "oficioOcupacion", entity.getOficioOcupacion().toLowerCase() , MatchMode.ANYWHERE));

		List<DitPersonal> result = criteria.list();

		if (result != null && !result.isEmpty()) {
			this.log.debug("Si existe mas de un oficio con esa descripci�n");
			entityResponse = result.get(0);
		}

		return entityResponse;
	}
	
	private DitPersonal findById(Long cveIdPersonal,
			Class<DitPersonal> class1) {
		
		DitPersonal ditPersonal = null;
		
		Query queryDitPersonal = em.createNamedQuery("DitPersonal.findByCveIdPersonal");
		queryDitPersonal.setParameter("cveIdPersonal", cveIdPersonal);
		
		Object singleResult = queryDitPersonal.getSingleResult();
		
		if (singleResult != null){
			ditPersonal = (DitPersonal) singleResult;
		}
		return ditPersonal;
	}

}
