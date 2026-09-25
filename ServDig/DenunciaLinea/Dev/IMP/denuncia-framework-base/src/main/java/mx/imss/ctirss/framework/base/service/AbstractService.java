/**
 * AbstractService.java
 * @package mx.gob.imss.delta.framework.base.service
 * @project delta-framework-base	
 */
package mx.imss.ctirss.framework.base.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;


import mx.imss.ctirss.framework.annotations.OnDeleteAsignaFechaSistema;
import mx.imss.ctirss.framework.annotations.OnInsertAsignaFechaSistema;
import mx.imss.ctirss.framework.annotations.OnUpdateAsignaFechaSistema;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.utils.Log;

import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public abstract class AbstractService {
	
	protected final Log logger = Log.getLog(getClass());
	
	
	protected static <T extends AbstractModel> T  setFieldsBeforeInsert (T model){
		//Obtenemos la anotacion de EnInsertarAsignaFechaSistema
		OnInsertAsignaFechaSistema a = (OnInsertAsignaFechaSistema) AbstractService.getAnnotation(OnInsertAsignaFechaSistema.class,model.getClass());
		if(a!=null){
			String[] atributos = a.atributos();
			AbstractService.setDatesToFields(atributos, model);			
		}

		return model;
	}
	
	
protected static <T extends AbstractModel> T  setFieldsBeforeUpdate(T model){
		
		//Obtenemos la anotacion de EnInsertarAsignaFechaSistema
		OnUpdateAsignaFechaSistema  a = (OnUpdateAsignaFechaSistema) AbstractService
				.getAnnotation(OnUpdateAsignaFechaSistema.class,
						model.getClass());
		if(a!=null){
			String[] atributos = a.atributos();
			AbstractService.setDatesToFields(atributos, model);			
		}
		
		return model;
	}


protected static <T extends AbstractModel> T  setFieldsBeforeDelete(T model){
	
	//Obtenemos la anotacion de EnInsertarAsignaFechaSistema
	OnDeleteAsignaFechaSistema   a = (OnDeleteAsignaFechaSistema) AbstractService
			.getAnnotation(OnDeleteAsignaFechaSistema.class,
					model.getClass());
	if(a!=null){
		String[] atributos = a.atributos();
		AbstractService.setDatesToFields(atributos, model);
	}
	
	return model;
}
	
	
	
	/**
	 * 
	 * @param atributos
	 * @param model
	 */
	private static void setDatesToFields(String[] atributos , AbstractModel model){
		
		for(String att : atributos){
			BeanUtilsBean utils = BeanUtilsBean.getInstance();
			try {
				utils.setProperty(model,  att , new Date());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * @param clazAnnotation
	 * @return
	 */
	private static Annotation getAnnotation(Class clazAnnotation, Class <? extends AbstractModel> fromClaz ){
		Annotation a = fromClaz.getAnnotation(clazAnnotation);
		return a;
	}

}
