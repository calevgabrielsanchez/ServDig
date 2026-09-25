/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SeqGeneralesServiceEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.secuenciasgenerales
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.secuenciasgenerales;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;

@Stateless
public class SeqGeneralesServiceEntity extends AbstractServiceEntity implements
		SeqGeneralesServiceEntityLocal {

	@Override
	public BigInteger obtieneClaveNueva(String var) throws PersistenceException {
		BigInteger response =null;
		int value = 0;
		try{
			Query query = 
				em.createNativeQuery("UPDATE DIT_SEQ_GENERALES " +
									 "SET NUM_SECUENCIA = (NUM_SECUENCIA + 1) " +
									 "WHERE CVE_SECUENCIA = :id");
			query.setParameter("id", var);
			query.executeUpdate();

			query = 
				em.createNativeQuery("SELECT Max(NUM_SECUENCIA) " +
									 "from DIT_SEQ_GENERALES " +
									 "where CVE_SECUENCIA = :id ");
			query.setParameter("id", var);
			value = ((BigDecimal) query.getSingleResult()).intValue();
			value = value -1;
			response = BigInteger.valueOf(value);
			
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
		return response;
	}
	
	
	@Override
	public Boolean existeSecuencia(String var) throws PersistenceException {
		Boolean response = Boolean.FALSE;
		BigDecimal data =null;
		try{
			Query query = 
				em.createNativeQuery("SELECT Max(NUM_SECUENCIA) " +
						 			 "from DIT_SEQ_GENERALES " +
						 			 "where CVE_SECUENCIA = :id ");
			query.setParameter("id", var);
			
			data = ((BigDecimal) query.getSingleResult());
			if(data!=null){
				log.debug("Secuencia encontrada: " + var);
				return Boolean.TRUE;
			}
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
		return response;
	}
 
	
	@Override
	public Boolean creaSecuencia(String id, int value, String desc) throws PersistenceException {
		Boolean response = Boolean.FALSE;
		int rows=0;
		try{
			Query query = 
				em.createNativeQuery("INSERT INTO DIT_SEQ_GENERALES " +
									 "VALUES (:id, :initValue, :desc)");
			query.setParameter("id", id);
			query.setParameter("initValue", value);
			query.setParameter("desc", desc);
			rows = query.executeUpdate();
			if(rows > 0){
				log.debug("Secuencia "+ id+" creada.");
				return Boolean.TRUE;
			}
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
		return response;
	}
}
