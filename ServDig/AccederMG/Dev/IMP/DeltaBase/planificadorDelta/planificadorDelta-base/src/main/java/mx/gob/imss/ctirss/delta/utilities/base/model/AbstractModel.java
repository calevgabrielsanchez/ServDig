/**
 * AbstractModel.java
 * @package mx.gob.imss.delta.framework.base.model
 * @project delta-framework-base	
 */
package mx.gob.imss.ctirss.delta.utilities.base.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public abstract class AbstractModel implements Serializable {
	private static final long serialVersionUID = 992321351302280430L;

	/**
	 * Atributo para el control de las validaciones de Forma realizadas con el
	 * Validator, para poder asignar mensajes Genericos.
	 */
	private String errorFormGeneral;

	public String getErrorFormGeneral() {
		return errorFormGeneral;
	}

	public void setErrorFormGeneral(String errorFormGeneral) {
		this.errorFormGeneral = errorFormGeneral;
	}

	public String toString() {
		return "\n" + ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) + "\n";
	}

}
