package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;






import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;

@Stateless(name = "umfTurnoDao", mappedName = "umfTurnoDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UmfTurnoDao extends AbstractServiceEntity implements UmfTurnoDaoLocal {
	
	private static final Logger logger = Logger.getLogger(UmfTurnoDao.class);
	/**
	@PersistenceContext()
	private EntityManager em;
	**/
	
	@Override
	public DicUmf getUmfById(Long idUmf) throws Exception {
		// TODO Auto-generated method stub
		DicUmf dicUmf = null;
		
		dicUmf = em.find(DicUmf.class, idUmf);
		
		return dicUmf;
	}


	@Override
	public BigInteger getCapacidadUmfTurno(DitUmfTurno ditUmfTurno) throws Exception {

		//crea criteria
		CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
		CriteriaQuery<BigInteger> cqry= cb.createQuery(BigInteger.class);  //Aqui se pone que tipo esperamos recivir
	
		try {
			Root<DitUmfTurno> root = cqry.from(DitUmfTurno.class); //Step 2 //se crea el from
		       
		       
	        cqry.select(root.get("numCita").as(BigInteger.class)); //Step 3 se agrega al select y el selec se puede limitar a un prametro 
	        Predicate conjunction = cb.conjunction();//se crea unoa conjuntion para poder hacer and
	        
	        //turno
	        conjunction.getExpressions().add( cb.equal(root.get("dicTurno"), ditUmfTurno.getDicTurno())); 
	        //dicUMF
	        conjunction.getExpressions().add(cb.equal(root.get("dicUmf"), ditUmfTurno.getDicUmf())); 
	        //fecha de baja sea Null
	        conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));        
	        cqry.where(conjunction); //Step 5 se agrega el predicado
		} catch (NoResultException e){
			return null;
		} catch (Exception e) {
			logger.error("Error - getCapacidadUmfTurno", e);
			throw e;
		}
        
        //Se Obtiene el resultado
        return em.createQuery(cqry).getSingleResult();
       

	}



	
}
