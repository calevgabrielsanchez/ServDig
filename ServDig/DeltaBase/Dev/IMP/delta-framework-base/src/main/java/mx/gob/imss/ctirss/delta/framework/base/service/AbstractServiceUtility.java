/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:AbstractServiceUtility.java
 *  @Paquete:mx.gob.imss.ctirss.delta.framework.base.service
 *  @Fecha:14/02/2012
 */
package mx.gob.imss.ctirss.delta.framework.base.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;

/**
 * @author Lucio Duran Silva
 *
 */
public class AbstractServiceUtility extends AbstractService {

	
	
	/**
	 * 
	 * @param beanOrigen
	 * @param beanDestino
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void copyBeans(Object beanOrigen, Object beanDestino)
			throws IllegalAccessException, InvocationTargetException {
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		BeanUtils.copyProperties(beanDestino, beanOrigen);

	}

	/**
	 * Metodo para convertir una lista de objetos de tipo Entity a una lista de
	 * objetos tipo Especifico por el <T> Type
	 * 
	 * @param origen
	 *            : Lista de objetos de tipo Entity a convertir
	 * @param destino
	 *            : LIsta de objetos (Modelo) a crear
	 */
	protected <T> void copyListOfBeanTypeEntity(List<?> origen,
			List<T> destino, Class<T> claz)throws InstantiationException , IllegalAccessException , InvocationTargetException, IllegalArgumentException {
		this.copyListOfBean(origen, destino, claz);
	}
	
	/**
	 * 
	 * @param <T> : AbstractEntity
	 * @param origen : Lista de objetos de tipo Model
	 * @param destino: Lista convertida de objetos tipo Entity a partir de la lista de Model
	 * @param claz : Class a convertir
	 */
	protected <T> void copyListOfBeanTypeModel(List<?> origen,
			List<T> destino, Class<T> claz)throws InstantiationException , IllegalAccessException , InvocationTargetException, IllegalArgumentException {
		this.copyListOfBean(origen, destino, claz);
	}
	
	/**
	 * 
	 * @param origen
	 * @param destino
	 * @param claz
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	private   void copyListOfBean(List origen,
			List destino, Class claz)throws InstantiationException , IllegalAccessException , InvocationTargetException, IllegalArgumentException{
		
		if (origen == null) {
			log.error("Parametro Lista origen es nulo.");
			throw new IllegalArgumentException(
					"Parametro origen (LIst) no puede ser nulo.");
		}
		if (destino == null) {
			// Creamos la lista
			log.info("La lista destino es nula, creamos una instancia");
			destino = new ArrayList();
		}

		Iterator<? > itOrg = origen.iterator();
		while (itOrg.hasNext()) {
			Object org = itOrg.next();
			try {
				Object instance =  claz.newInstance();
				this.copyBeans(org, instance);
				destino.add(instance);
			} catch (InstantiationException e) {
				log.error(e.getMessage() , e);
				throw e;
			} catch (IllegalAccessException e) {
				log.error(e.getMessage() , e);
				throw e;
			} catch (InvocationTargetException e) {
				log.error(e.getMessage() , e);
				throw e;
			}

		}
		
	}
	

	
}
