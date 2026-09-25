package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test.utils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;



public class displayUtils {
	 
	public static void displayBeanList(List<?> beanList){
		for(Object bean:beanList){
			System.out.println("***-List-***");
			displayBean(bean);
			System.out.println("***-List-***");
		}
	}
	
	public static void displayBean(Object bean) {
		Method[] metodos=bean.getClass().getDeclaredMethods();
		String meName=null;
		Object meVal=null;
		System.out.println("**********************"+bean.getClass().getSimpleName()+"*************************");
		if(bean!=null){
			for(int i=0;i<metodos.length;i++){
				meName=metodos[i].getName();
				if(meName.substring(0,2).equals("get")){
					if(meName.equals("getClass")){
						try {
							meVal=metodos[i].invoke(bean, null);
							System.out.println("***"+meName+": "+meVal);
							if(isSerializable(meVal)){
								System.out.println("-------");
								displayBean(meVal);
								System.out.println("-------");
							}
							if(meVal.getClass().equals(List.class)){
								
							}
							

						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	private static boolean isSerializable(Object bean){
		
		Class[] interfaces=null;
		if(bean!=null){
			interfaces=bean.getClass().getInterfaces();
			for(int i=0;i<interfaces.length;i++){
				if(interfaces[i].equals(Serializable.class)){
					return true;
				}
			}
		}
		return false;
	}

}


