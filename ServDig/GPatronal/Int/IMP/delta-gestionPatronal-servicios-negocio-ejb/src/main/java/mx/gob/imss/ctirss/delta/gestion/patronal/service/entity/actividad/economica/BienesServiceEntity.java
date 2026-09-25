package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.BienesServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.persistence.DitBiene;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class BienesServiceEntity extends AbstractServiceEntity implements
		BienesServiceEntityLocal {
	
	@EJB
	private BienesServiceUtilityLocal bienesServiceUtility;

	@Override
	public Bien agregar(Bien bien) throws Exception {
		DitBiene entity = null;
		
		entity = this.bienesServiceUtility.convertirModelToEntity(bien);
		entity.setFecRegistroAlta(Calendar.getInstance().getTime());
		em.persist(entity);
		bien = this.bienesServiceUtility.convertirEntityToModel(entity);

		return bien;
	}

	@Override
	public Bien actualizar(Bien bien) throws Exception {
//		DitBiene entity = new DitBiene();		
//		entity = this.bienesServiceUtility.convertirModelToEntity(bien);
//		this.em.merge(entity);
		DitBiene objFound = this.em.find(DitBiene.class, bien.getId());
		
		if (objFound != null){
//			objFound.setDesAfectacion(bien.getDesAfectacion());
			objFound.setDesBienes(bien.getDesBienes());
//			objFound.setDesUsosBienes(bien.getDesUsosBienes());
			objFound.setFecRegistroActualizado(new Date());
			objFound.setNumCantidad(bien.getNumCantidad());
		}
		return bien;
	}

	@Override
	public void eliminar(Bien bien) throws Exception {
		DitBiene entity = null;
		entity = (DitBiene)this.getSession().load(DitBiene.class, bien.getId());
		entity.setFecRegistroBaja(Calendar.getInstance().getTime());
		this.getSession().update(entity);
	}

	@Override
	public Bien obtener(Bien bien) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bien> obtenerComoLista(Bien bien) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<Bien> paginar(
			DatosEntradaPaginador<Bien> parametrosPaginador) {
		DatosSalidaPaginador<Bien> response = new DatosSalidaPaginador<Bien>();
		List<Bien> result = null;		
		
		/*Objeto con los filtros seleccionados en la vista*/
		Bien filtro =  parametrosPaginador.getModelo();
		
		
		Criteria criteria = this.getSession().createCriteria(DitBiene.class);
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
		
		criteria.addOrder(Order.asc("desBienes"));
		
		 
		List<DitBiene> entities = criteria
				.setFirstResult(parametrosPaginador.getiDisplayStart())
				.setMaxResults(parametrosPaginador.getiDisplayLength()).list();
		
		
		try {
			result = this.bienesServiceUtility.convertListOfEntitiesToListOfModel(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);

		return response;
	}

	@Override
	public void validaExisteBien(Bien bien) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validaLimMinRegBien(Bien bien) throws Exception {
		// TODO Auto-generated method stub

	}
	
	@SuppressWarnings("unused")
	private DitBiene findById(Long cveIdBienes,
			Class<DitBiene> class1) {
		
		DitBiene ditBiene = null;
		
		Query queryDitBiene = em.createNamedQuery("DitBiene.findByCveIdBienes");
		queryDitBiene.setParameter("cveIdBienes", cveIdBienes);
		
		Object singleResult = queryDitBiene.getSingleResult();
		
		if (singleResult != null){
			ditBiene = (DitBiene) singleResult;
		}
		return ditBiene;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.BienesServiceEntityLocal#findBienByDescription(mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien)
	 */
	@Override
	public Bien findBienByDescription(Bien bien) {
		Criteria criteria = this.getSession().createCriteria(DitBiene.class);
		criteria.add(Restrictions.eq("desBienes", bien.getDesBienes()));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		
		DitBiene cBien = (DitBiene)criteria.uniqueResult();
		if(cBien != null){
			return bienesServiceUtility.convertirEntityToModel(cBien);
		}
		return null;
	}
	
	
}
