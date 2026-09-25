/**
 * EnBorrarAsignaFechaSistema.java
 * @package mx.gob.imss.delta.framework.annotations
 * @project delta-framework-base	
 * 
 * Anotacion para indicar los atributos a asignar la fecha del sistema al momento de Borrar 
 * el elemento del catalogo.
 * 
 */
package mx.gob.imss.ctirss.delta.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/10/2011
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OnDeleteExcluyePropiedades {

	/**
	 * Nombre de los atributos a excluir en la eliminaci-n(al ejecutar el evento 'delete').
	 * @return
	 */
	String[] atributos();
	
	
}
