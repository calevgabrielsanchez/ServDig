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
package mx.imss.ctirss.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Juan Manuel L�pez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 30/09/2011
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OnSearchBajaLogica  {
	/**
	 * Nombre de los atributos que se restringir�n en la b�squeda (para bajas l�gicas)
	 * @return
	 */
	String[] atributos();
	

}
