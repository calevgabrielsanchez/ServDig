package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless(name = "DocumentoCapturadoDao", mappedName = "DocumentoCapturadoDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentoCapturadoDao implements DocumentoCapturadoDaoLocal {
	
	private static final Logger logger = Logger.getLogger(DocumentoCapturadoDao.class);
	@PersistenceContext()
	private EntityManager em;
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DocumentoCapturadoDaoLocal#save(java.lang.Object)
	 */
	@Override
	public Object save(Object obj) throws Exception{
		try {
			em.persist(obj);
			em.flush();
		} catch (Exception e) {
			logger.error("Error - save", e);
			throw e;
		}
		
		return obj;
	}
}
