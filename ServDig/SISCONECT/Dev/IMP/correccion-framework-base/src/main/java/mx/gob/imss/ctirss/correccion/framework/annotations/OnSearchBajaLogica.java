/**
 * IgnorarAtributosEnCriteria.java
 * @package mx.gob.imss.delta.framework.annotations
 * @project delta-framework-base
 * 
 * Anotacion para indicar que campos o atributos no se deben de tomar en cuenta para la construccion 
 * del criteria para la busqueda.
 * 
 * 	
 */
package mx.gob.imss.ctirss.correccion.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Juan Manuel Lapez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 30/09/2011
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OnSearchBajaLogica  {
	/**
	 * Nombre de los atributos que se restringiran en la basqueda (para bajas lagicas)
	 * @return
	 */
	String[] atributos();
	

}
