package mx.gob.imss.ctirss.infraestructura.framework.servicios;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConexionBD {
	String value();
}
