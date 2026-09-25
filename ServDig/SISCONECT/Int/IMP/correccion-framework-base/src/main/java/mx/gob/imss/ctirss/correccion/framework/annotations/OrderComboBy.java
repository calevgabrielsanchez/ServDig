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
 * @author Marco Antonio Nieto Plett
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 12/12/2011
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OrderComboBy  {
	/**
	 * Nombre de los atributos que se utilizaran en el ordenamiento del combo
	 * @return
	 */
	String[] atributos();
	

}
