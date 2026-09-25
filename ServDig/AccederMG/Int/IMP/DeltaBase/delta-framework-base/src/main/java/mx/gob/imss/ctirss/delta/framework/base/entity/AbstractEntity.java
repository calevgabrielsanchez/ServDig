/**
 * AbstractEntity.java
 * @package mx.gob.imss.ctirss.delta.framework.base.entity
 * @project delta	
 */
package mx.gob.imss.ctirss.delta.framework.base.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public abstract class AbstractEntity implements Serializable {

    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) + "\n";
    }

}
