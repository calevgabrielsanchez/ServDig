/**
 * ComponentComboCampoDescripcion.java
 * @package mx.gob.imss.delta.framework.annotations
 * @project delta-framework-base
 * 
 * Anotacion para indicar el campo 'descripcion' para la generacian de un componente combo
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
 * @date 08/10/2011
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentComboCampoDescripcion  {
	/**
	 * Nombre del atributo que sera la descripcion del componente combo
	 * @return
	 */
	String atributo();
	

}
