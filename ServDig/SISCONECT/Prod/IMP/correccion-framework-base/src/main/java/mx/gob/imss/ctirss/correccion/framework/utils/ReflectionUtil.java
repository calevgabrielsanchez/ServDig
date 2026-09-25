package mx.gob.imss.ctirss.correccion.framework.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class ReflectionUtil {
	
	
	public static Object getValue(Object object, String property) {
		if(object != null){
			Class<?> clazz = object.getClass();
			Class<?> types[] = new Class[0];
			Object values[] = new Object[0];
			Object value = null;
			Method method = null;
			String name = "";
			try {
				
				name = "get" + firstToUpper(property);
				method = clazz.getMethod(name, types);
				value = method.invoke(object, values);
				
			} catch (SecurityException e) {
			
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
			
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
			
				e.printStackTrace();
			} catch (InvocationTargetException e) {
			
				e.printStackTrace();
			}
			
			return value;
			
		}
		return null;
	}

	public static String firstToUpper(String property){
		if(property != null && !property.equalsIgnoreCase("") && property.length() > 1){
			property = property.substring(0,1).toUpperCase() + property.substring(1);
		}	
		return property;
	}
	
	
	
	

}
