package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MateriaPrimaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class MateriaPrimaServiceEntity extends AbstractServiceEntity implements
		MateriaPrimaServiceEntityLocal {
	
	@EJB
	private MateriaPrimaServiceUtilityLocal materiaPrimaServiceUtility;

	@Override
	public MateriaPrima agrega(MateriaPrima model) throws Exception {
		
		DitMateriaPrimaMaterial entity = null;
		
		entity = this.materiaPrimaServiceUtility.convertirModelToEntity(model);
		this.em.persist(entity);

		return model;
	}

	@Override
	public MateriaPrima actualiza(MateriaPrima model) throws Exception {
		Query qry = null;
		DitMateriaPrimaMaterial resultado = null;
		
		qry = em.createQuery("Select p from DitMateriaPrimaMaterial p where p.cveIdMateriaPrimaMaterial = :id ");
		qry.setParameter("id", model.getId());
		resultado = (DitMateriaPrimaMaterial) qry.getSingleResult();
		
		
		resultado.setDesMateriaPrimaMaterial(model.getDescripcion());
		
		this.em.merge(resultado);
		model = this.materiaPrimaServiceUtility.convertirEntityToModel(resultado);
		
		return model;
	}

	@Override
	public void elimina(MateriaPrima model) throws Exception {
		Query qry = null;
		
		qry = em.createQuery("Select p from DitMateriaPrimaMaterial p where p.cveIdMateriaPrimaMaterial = :id and p.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :id2");
		qry.setParameter("id", model.getId());
		qry.setParameter("id2", model.getSujetoObligado().getCveIdSujetoObligado());
		
		this.em.remove((DitMateriaPrimaMaterial) qry.getSingleResult());
	}

	@Override
	public MateriaPrima consultaPorClave(MateriaPrima model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<MateriaPrima> consultarMateriaPrima(
			DatosEntradaPaginador<MateriaPrima> parametrosPaginador) {
		DatosSalidaPaginador<MateriaPrima> response = new DatosSalidaPaginador<MateriaPrima>();
		List<MateriaPrima> result = null;
		
		
		/*Objeto con los filtros seleccionados en la vista*/
		MateriaPrima filtro =  parametrosPaginador.getModelo();
		
		Criteria criteria = this.getSession().createCriteria(DitMateriaPrimaMaterial.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", filtro.getSujetoObligado().getCveIdSujetoObligado()));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		
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
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		List<DitMateriaPrimaMaterial> entities = criteria
				.setFirstResult(parametrosPaginador.getiDisplayStart())
				.setMaxResults(parametrosPaginador.getiDisplayLength()).list();
		
		try {
			result = this.materiaPrimaServiceUtility.convertListOfEntitiesToListOfModel(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);

		return response;
	}

	@Override
	public int consultarNumRegistros(MateriaPrima model) {
		Query qry = null;
		@SuppressWarnings("rawtypes")
		List resultados = null;
		
		qry = em.createQuery("Select p from DitMateriaPrimaMaterial p where p.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :id ");
		qry.setParameter("id", model.getSujetoObligado().getCveIdSujetoObligado());
		resultados = qry.getResultList();

		return resultados.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public MateriaPrima validaExisteMateriaPrimaMaterial(
			MateriaPrima materiaPrima) throws Exception {
		
		Criteria criteria = this.getSession().createCriteria(DitMateriaPrimaMaterial.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", materiaPrima.getSujetoObligado().getCveIdSujetoObligado()));
        criteria.add(Restrictions.ilike("desMateriaPrimaMaterial", materiaPrima.getDescripcion().toLowerCase(), MatchMode.EXACT));

        if(materiaPrima.getId() != null){
			//SI es diferente de nulo debemos de agregar el filtro por clave de materia prima
			criteria.add(  Restrictions.ne ( "cveIdMateriaPrimaMaterial", materiaPrima.getId()));
		}
        
        List<DitMateriaPrimaMaterial> result = criteria.list();
        
        if (result != null && !result.isEmpty()) {
		    String msg = "Existe más de un registro con la misma descripción para MateriaPrimaMaterial.";
			this.log.debug(msg);
		    //materiaPrima = this.materiaPrimaServiceUtility.convertirEntityToModel(result.get(0));
		    throw new Exception(msg);
		    
		}
        return materiaPrima;
	}

	@Override
	public int consultarNumRegistrosPorActividadEconomica(
			Long cveActividadEconomica) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MateriaPrima> consultaPorClaveActividad(
			MateriaPrima materiaPrima) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
