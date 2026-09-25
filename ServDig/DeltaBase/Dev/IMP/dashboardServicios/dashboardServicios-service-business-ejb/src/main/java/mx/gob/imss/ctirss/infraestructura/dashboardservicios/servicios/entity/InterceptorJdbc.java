package mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.entity;

import javax.interceptor.AroundInvoke;
import java.lang.annotation.Annotation;
import javax.interceptor.InvocationContext;
import mx.gob.imss.ctirss.infraestructura.framework.servicios.AbstractServiceJdbcEntity;
import mx.gob.imss.ctirss.infraestructura.framework.servicios.ConexionBD;

public class InterceptorJdbc {

    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception{    	    	
    	boolean bAbrirConexion = true;
    	AbstractServiceJdbcEntity ejb = null;
    	Annotation[] listaAnotaciones = context.getMethod().getAnnotations();
    	for (Annotation anotacion : listaAnotaciones){
			if (anotacion instanceof ConexionBD){
				ConexionBD conexion = (ConexionBD)anotacion;
				ejb = (AbstractServiceJdbcEntity)context.getTarget();
				ejb.abreConexion(conexion.value());
				bAbrirConexion = true;
			}
    	}

        //EJECUTA EL METODO DEL EJB
        Object respuesta = context.proceed();
        
        if (bAbrirConexion){
			if (ejb != null){
				ejb.cierraConexion();	
			}
        }
        return respuesta;
    }
}
