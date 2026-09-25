/**
 * AbstractRespository.java
 * @package mx.gob.imss.delta.framework.base.repository
 * @project delta-framework-base	
 */
package mx.gob.imss.ctirss.correccion.framework.base.repository;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.IgnoreAtributosEnCriteria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchBajaLogica;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchFiltro;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchFiltro2;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.Log;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;



/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public abstract class AbstractRespository{	
	
	protected final Log logger = Log.getLog(getClass());
	
	@PersistenceContext(unitName="deltaPersistenceUnit")
	private EntityManager em;	
	
	protected Session getSession(){
		return (Session)em.getDelegate();
	}	
	
	/**
	 * Metodo que genera un example a partir de la clase del modelo.
	 * @param model
	 * @return
	 */
	public static <T extends AbstractModel> Example createExampleOf(T model){
		Example example = Example.create(model);
		Annotation annotation =  model.getClass().getAnnotation(IgnoreAtributosEnCriteria.class);
		if(annotation!=null){
			IgnoreAtributosEnCriteria dAnnotation = (IgnoreAtributosEnCriteria)annotation;
			String[] atributos = dAnnotation.atributos();
			for(String att: atributos){
				example.excludeProperty(att);
			}
		}
		example.enableLike(MatchMode.ANYWHERE);
		return example;
	}
	
	public static <T extends AbstractModel> ArrayList<String> getFiltrosBajaLogica(T model){
		ArrayList<String> alFiltrosBajaLogica = new ArrayList<String>();
		Annotation annotation =  model.getClass().getAnnotation(OnSearchBajaLogica.class);
		if(annotation!=null){
			OnSearchBajaLogica dAnnotation = (OnSearchBajaLogica)annotation;
			String[] atributos = dAnnotation.atributos();
			for(String att: atributos){
				alFiltrosBajaLogica.add(att);
			}
		}
		return alFiltrosBajaLogica;		
	}
	
	public static <T extends AbstractModel> ArrayList<String> getLlavePrimaria(T model){
		ArrayList<String> alLlavePrimaria = new ArrayList<String>();
		Annotation annotation =  model.getClass().getAnnotation(OnSearchLlavePrimaria.class);
		if(annotation!=null){
			OnSearchLlavePrimaria dAnnotation = (OnSearchLlavePrimaria)annotation;
			String[] atributos = dAnnotation.atributos();
			for(String att: atributos){
				alLlavePrimaria.add(att);
			}
		}
		return alLlavePrimaria;		
	}	
	
	public static ArrayList<String> getLlavePrimaria(Class claz){
		ArrayList<String> alLlavePrimaria = new ArrayList<String>();
		Annotation annotation =  claz.getAnnotation(OnSearchLlavePrimaria.class);
		if(annotation!=null){
			OnSearchLlavePrimaria dAnnotation = (OnSearchLlavePrimaria)annotation;
			String[] atributos = dAnnotation.atributos();
			for(String att: atributos){
				alLlavePrimaria.add(att);
			}
		}
		return alLlavePrimaria;		
	}		
	
	
	public static ArrayList<String> getOrdenCombo(Class claz){
		ArrayList<String> aOrdenCombo = new ArrayList<String>();
		Annotation annotation =  claz.getAnnotation(OrderComboBy.class);
		if(annotation!=null){
			OrderComboBy dAnnotation = (OrderComboBy)annotation;
			String[] atributos = dAnnotation.atributos();
			for(String att: atributos){
				aOrdenCombo.add(att);
			}
		}
		return aOrdenCombo;		
	}	
	
	public static String getDescripcionComponenteCombo(Class claz){
		String sDescripcionCombo = null;
		Annotation annotation =  claz.getAnnotation(ComponentComboCampoDescripcion.class);
		if(annotation!=null){
			ComponentComboCampoDescripcion dAnnotation = (ComponentComboCampoDescripcion)annotation;
			sDescripcionCombo = dAnnotation.atributo();
		}
		return sDescripcionCombo;		
	}//getDescripcionComponenteCombo
		
	public static String getFiltro(Class claz){
		String sDescripcionCombo = null;
		Annotation annotation =  claz.getAnnotation(OnSearchFiltro.class);
		if(annotation!=null){
			OnSearchFiltro dAnnotation = (OnSearchFiltro)annotation;
			sDescripcionCombo = dAnnotation.atributo();
		}
		return sDescripcionCombo;		
	}//getFiltro
	
	public static String getFiltro2(Class claz){
		String sDescripcionCombo = null;
		Annotation annotation =  claz.getAnnotation(OnSearchFiltro2.class);
		if(annotation!=null){
			OnSearchFiltro2 dAnnotation = (OnSearchFiltro2)annotation;
			sDescripcionCombo = dAnnotation.atributo();
		}
		return sDescripcionCombo;		
	}//getFiltro2
	
}
