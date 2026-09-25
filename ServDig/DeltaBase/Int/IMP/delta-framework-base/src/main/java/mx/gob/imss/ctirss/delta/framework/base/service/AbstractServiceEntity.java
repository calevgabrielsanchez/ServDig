/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:AbstractServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.framework.base.service
 *  @Fecha:14/02/2012
 */
package mx.gob.imss.ctirss.delta.framework.base.service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.IgnoreAtributosEnCriteria;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchBajaLogica;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

/**
 * @author Lucio Duran Silva
 *
 */
public class AbstractServiceEntity extends AbstractService {

	
	
	@PersistenceContext(unitName = "deltaPersistenceUnit")
	protected EntityManager em;

	protected Session getSession(){
		
		//LA FORMA DE RECUPERAR LA SESION DE HIBERNATE CAMBIA ENTRE APLICATION SERVERS, NO EXISTE UNA FORMA UNIFICADA
		Session session = null;
	    if (em.getDelegate() instanceof org.hibernate.ejb.HibernateEntityManager) {
	    	//ESTA FORMA DE RECUPERAR LA SESION LA UTILIZA GLASSFISH
	    	session = ((org.hibernate.ejb.HibernateEntityManager) em.getDelegate()).getSession();
	    }
	    else {
	    	//LA SIGUIENTE FORMA DE RECUPERAR LA SESION LA UTILIZA WEBLOGIC
	    	session = (Session) em.getDelegate();
	    }
	    return session;
	}

	/**
	 * Metodo que genera un example a partir de la clase del modelo.
	 * 
	 * @param model
	 * @return
	 */
	public static <T extends AbstractModel> Example createExampleOf(T model) {
		Example example = Example.create(model);
		Annotation annotation = model.getClass().getAnnotation(IgnoreAtributosEnCriteria.class);
		if (annotation != null) {
			IgnoreAtributosEnCriteria dAnnotation = (IgnoreAtributosEnCriteria) annotation;
			String[] atributos = dAnnotation.atributos();
			for (String att : atributos) {
				example.excludeProperty(att);
			}
		}
		example.enableLike(MatchMode.ANYWHERE);
		return example;
	}

	public static <T extends AbstractModel> ArrayList<String> getFiltrosBajaLogica(T model) {
		ArrayList<String> alFiltrosBajaLogica = new ArrayList<String>();
		Annotation annotation = model.getClass().getAnnotation(OnSearchBajaLogica.class);
		if (annotation != null) {
			OnSearchBajaLogica dAnnotation = (OnSearchBajaLogica) annotation;
			String[] atributos = dAnnotation.atributos();
			for (String att : atributos) {
				alFiltrosBajaLogica.add(att);
			}
		}
		return alFiltrosBajaLogica;
	}

	public static <T extends AbstractModel> ArrayList<String> getLlavePrimaria(T model) {
		ArrayList<String> alLlavePrimaria = new ArrayList<String>();
		Annotation annotation = model.getClass().getAnnotation(OnSearchLlavePrimaria.class);
		if (annotation != null) {
			OnSearchLlavePrimaria dAnnotation = (OnSearchLlavePrimaria) annotation;
			String[] atributos = dAnnotation.atributos();
			for (String att : atributos) {
				alLlavePrimaria.add(att);
			}
		}
		return alLlavePrimaria;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// TODO - Suppress Warn!
	public static ArrayList<String> getLlavePrimaria(Class claz) {
		ArrayList<String> alLlavePrimaria = new ArrayList<String>();
		Annotation annotation = claz.getAnnotation(OnSearchLlavePrimaria.class);
		if (annotation != null) {
			OnSearchLlavePrimaria dAnnotation = (OnSearchLlavePrimaria) annotation;
			String[] atributos = dAnnotation.atributos();
			for (String att : atributos) {
				alLlavePrimaria.add(att);
			}
		}
		return alLlavePrimaria;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// TODO - Suppress Warn!
	public static String getDescripcionComponenteCombo(final Class clazz) {
		String sDescripcionCombo = null;
		Annotation annotation = clazz.getAnnotation(ComponentComboCampoDescripcion.class);
		if (annotation != null) {
			ComponentComboCampoDescripcion dAnnotation = (ComponentComboCampoDescripcion) annotation;
			sDescripcionCombo = dAnnotation.atributo();
		}
		return sDescripcionCombo;
	}// getDescripcionComponenteCombo

	
	
	/**
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return em;
	}
	
}
