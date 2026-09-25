/**
 * EnInsertarAsignaFechaSistema.java
 * @package mx.gob.imss.delta.framework.annotations
 * @project delta-framework-base	
 * 
 * Anotacion para indicar los atributos a asignar la fecha del sistema al momento de insertar 
 * el elemento del catalogo.
 * 
 */
package mx.gob.imss.ctirss.delta.framework.annotations;

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
public @interface OnInsertAsignaFechaSistema {

	String[] atributos();
	
}
