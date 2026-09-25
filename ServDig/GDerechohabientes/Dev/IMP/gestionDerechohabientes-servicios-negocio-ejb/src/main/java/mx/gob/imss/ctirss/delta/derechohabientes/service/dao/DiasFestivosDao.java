package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;



import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.persistence.DicDiasFestivo;



/**
 * @author Victor Camacho
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Stateless(name = "diasFestivosDao", mappedName = "diasFestivosDao")
public class DiasFestivosDao implements DiasFestivosDaoLocal {
	
	private static final Logger logger = Logger.getLogger(DiasFestivosDao.class);
	
	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em;

	@Override
	public List<DicDiasFestivo> findDiasFestivos() throws Exception {
		
		List<DicDiasFestivo> listaDicDiasFestivo = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
			CriteriaQuery<DicDiasFestivo> cqry= cb.createQuery(DicDiasFestivo.class);  //Step 1
			//se crea lo deseado
	        Root<DicDiasFestivo> root = cqry.from(DicDiasFestivo.class); //Step 2 //se crea la raiz
	        cqry.select(root); //Step 3 se agrega la raiz
	        //No tiene fecha de baja
	       // Predicate pre=cb.equal(root.get("fecRegistroBaja"),null);
	        //cqry.where(pre);
	        listaDicDiasFestivo = em.createQuery(cqry).getResultList();
		}catch (IndexOutOfBoundsException e){
			listaDicDiasFestivo = null;
		}catch (Exception e) {
			logger.error("Error - findDiasFestivos", e);
			throw e;
		}
		
       
		return listaDicDiasFestivo; //Step 6 se obtiene el resultado

	}


	


}
