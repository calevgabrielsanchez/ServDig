/**
 * TestAnnotation.java
 * @package mx.gob.imss.ctirss.correccion.framework.base
 * @project correccion-framework-base	
 */
package mx.gob.imss.ctirss.correccion.framework.base;


import java.lang.annotation.Annotation;
import java.util.Date;

import mx.gob.imss.ctirss.correccion.framework.annotations.IgnoreAtributosEnCriteria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnInsertAsignaFechaSistema;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 29/08/2011
 */
public class TestAnnotation {
	
	
	public static void main(String args[]){
		
		
		FraccionModel model = new FraccionModel();
		model.setFechaAlta(new Date());
		model.setFechaBaja(new Date());
		model.setId(new Integer(101));
		model.setNombre("Model");
		
		System.out.println("iniciando..");
		try {
//			for(Method m : Class.forName("mx.gob.imss.delta.framework.base.model.FraccionModel").getMethods()){
//				//System.out.println(m);
//				if(m.isAnnotationPresent(IgnoredFieldCriteria.class)){
//					
//					IgnoredFieldCriteria a =  m.getAnnotation(IgnoredFieldCriteria.class);
//					System.out.println("Ignorando el campo: " + a.name());
//				}
//				
//				
//			}
			
			
//			for(Field m : Class.forName("mx.gob.imss.delta.framework.base.model.FraccionModel").getFields()){
//				//System.out.println(m);
//				if(m.isAnnotationPresent(IgnoredFieldCriteria.class)){
//					System.out.println("Ignorando el campo: " + m.getName());
//				}
//				
//				
//			}
		
			
			
			
			
			Annotation[] annotations = FraccionModel.class.getAnnotations();
			System.out.println(annotations);
			
			for(Annotation annotation : annotations){
			//System.out.println(annotation);
				if(annotation instanceof IgnoreAtributosEnCriteria){
			    	IgnoreAtributosEnCriteria myAnnotation = (IgnoreAtributosEnCriteria) annotation;
			    	String atts [] = myAnnotation.atributos();
			        for(String att : atts){
			        	System.out.println("Ignorando :" + att);
			        }
			       
			    }
				
				if(annotation instanceof OnInsertAsignaFechaSistema){
					OnInsertAsignaFechaSistema myAnnotation = (OnInsertAsignaFechaSistema) annotation;
			    	String atts [] = myAnnotation.atributos();
			        for(String att : atts){
			        	System.out.println("Asignando :" + att);
			        }
			       
			    }
				
			}
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}

}
