package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.bitacora;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitBitacoraIngreso;
import mx.gob.imss.ctirss.delta.persistence.DitRfcBloqueados;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class BitacoraServiceEntity extends AbstractServiceEntity implements BitacoraServiceEntityLocal{

	    
    @SuppressWarnings("unchecked")
    @Override
    public Boolean permitedRfc(String rfc) {
    	
    	 String query ="SELECT ID_RFC_BLOQ FROM DIT_RFC_BLOQUEADOS "
         		+ " WHERE DES_RFC = :rfc "
         		+ " AND FEC_BAJA IS NULL";

         try {
        	 SQLQuery sqlQuery = getSession().createSQLQuery(query);
             sqlQuery.setParameter("rfc", rfc);
             
             List<Object> rfcBloqueado = (List<Object>)sqlQuery.list();

    
            if (rfcBloqueado != null && !rfcBloqueado.isEmpty()) {
            	long rfcB = Long.valueOf(rfcBloqueado.get(0).toString());
            	log.debug("Se obtuvo "+ rfcB);
            	guardarBitacora(rfcB);
                log.debug("Se obtuvo "+ rfcB);
                return  false;
            }
        }catch (Exception e) {
            log.error("error al consultar el rfc ", e);
        }
        return true;
    }
    

    private void guardarBitacora(long rfc) throws PersistenceException {
    	try {
    		
    	DitBitacoraIngreso entity = null;
    	DitRfcBloqueados ditRfcBloqueado = new DitRfcBloqueados();
    	ditRfcBloqueado.setIdRfcBloq(rfc);
    	
    	
    	entity = new DitBitacoraIngreso();
    	entity.setDitRfcBloqueados(ditRfcBloqueado);
    	Timestamp timestamp = new Timestamp(new Date().getTime());
    	entity.setFechaConsulta(timestamp);
    	em.persist(entity);    	   	
    	}
    	catch (Exception e) {
            log.error("error al registrar en bitacora ", e);
        }
 }
}
