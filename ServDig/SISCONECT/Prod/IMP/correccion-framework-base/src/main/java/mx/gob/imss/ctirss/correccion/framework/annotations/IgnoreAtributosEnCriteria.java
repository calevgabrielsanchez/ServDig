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
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 29/08/2011
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IgnoreAtributosEnCriteria  {
	/**
	 * Nombre de los atributos a ignorar.
	 * @return
	 */
	String[] atributos();
	

}
