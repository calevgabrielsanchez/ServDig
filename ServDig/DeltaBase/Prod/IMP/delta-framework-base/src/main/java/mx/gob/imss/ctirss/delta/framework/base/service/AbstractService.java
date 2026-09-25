/**
 * AbstractService.java
 * @package mx.gob.imss.delta.framework.base.service
 * @project delta-framework-base	
 */
package mx.gob.imss.ctirss.delta.framework.base.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;


import mx.gob.imss.ctirss.delta.framework.annotations.OnDeleteAsignaFechaSistema;
import mx.gob.imss.ctirss.delta.framework.annotations.OnDeleteExcluyePropiedades;
import mx.gob.imss.ctirss.delta.framework.annotations.OnInsertAsignaFechaSistema;
import mx.gob.imss.ctirss.delta.framework.annotations.OnUpdateAsignaFechaSistema;
import mx.gob.imss.ctirss.delta.framework.annotations.OnUpdateExcluyePropiedades;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public abstract class AbstractService {
	
	private static final int _EVENTO_UPDATE = 1;
	private static final int _EVENTO_DELETE = 2;
	
	
	protected final Log log = LogFactory.getLog(getClass());
	
	
	
	protected static <T extends AbstractModel> T  setFieldsBeforeInsert(T model){
		//Obtenemos la anotacion de OnInsertAsignaFechaSistema
		OnInsertAsignaFechaSistema a = (OnInsertAsignaFechaSistema) AbstractService.getAnnotation(OnInsertAsignaFechaSistema.class,model.getClass());
		if(a!=null){
			String[] atributos = a.atributos();
			AbstractService.setDatesToFields(atributos, model);			
		}

		return model;
	}
	
	
	protected static <T extends AbstractModel> T  setFieldsBeforeUpdate(T model){
		
		//Obtenemos la anotacion de OnUpdateAsignaFechaSistema
		OnUpdateAsignaFechaSistema  a = (OnUpdateAsignaFechaSistema) AbstractService.getAnnotation(OnUpdateAsignaFechaSistema.class,model.getClass());
		if(a!=null){
			String[] atributos = a.atributos();
			AbstractService.setDatesToFields(atributos, model);			
		}
		
		return model;
	}

	protected static <T extends AbstractModel> T  setExcludedFieldsBeforeUpdate(T modelModificado, T modelPersistido){
		return setExcludedFieldsBeforeEvent(_EVENTO_UPDATE, modelModificado, modelPersistido);
	}//setExcludedFieldsBeforeUpdate
	
	protected static <T extends AbstractModel> T  setExcludedFieldsBeforeDelete(T modelModificado, T modelPersistido){
		return setExcludedFieldsBeforeEvent(_EVENTO_DELETE, modelModificado, modelPersistido);
	}//setExcludedFieldsBeforeDelete	
	
	protected static <T extends AbstractModel> T  setExcludedFieldsBeforeEvent(int iEvento, T modelModificado, T modelPersistido){
		
		OnUpdateExcluyePropiedades  aupd = null;
		OnDeleteExcluyePropiedades  adel = null;
		
		String[] atributos = null;
		
		if(iEvento == _EVENTO_UPDATE){
			//Obtenemos la anotacion de OnUpdateExcluyePropiedades
			aupd = (OnUpdateExcluyePropiedades) AbstractService.getAnnotation(OnUpdateExcluyePropiedades.class,modelModificado.getClass());
			if(aupd!=null)
				atributos = aupd.atributos();
		}
		else
		  if(iEvento == _EVENTO_DELETE){
			//Obtenemos la anotacion de OnUpdateExcluyePropiedades
			adel =  (OnDeleteExcluyePropiedades) AbstractService.getAnnotation(OnDeleteExcluyePropiedades.class,modelModificado.getClass());
			if(adel!=null)
				atributos = adel.atributos();				
		}
		if(atributos!=null)
		  for(String atributo: atributos){
			BeanUtilsBean utils = BeanUtilsBean.getInstance();
			try {
				boolean bModificarAcceso = false;
				Field f = modelPersistido.getClass().getSuperclass().getDeclaredField(atributo);
				if(!f.isAccessible()){
					f.setAccessible(true);
					bModificarAcceso = true;
				}
				if(f.get(modelPersistido)!=null)
					utils.setProperty(modelModificado,  atributo , f.get(modelPersistido));
				if(bModificarAcceso)
				  f.setAccessible(false);
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		  }//for(String atributo: atributos){				

		
		return modelModificado;
	}//setExcludedFieldsBeforeUpdate	


	protected static <T extends AbstractModel> T  setFieldsBeforeDelete(T model){
	
		//Obtenemos la anotacion de EnInsertarAsignaFechaSistema
		OnDeleteAsignaFechaSistema   a = (OnDeleteAsignaFechaSistema) AbstractService.getAnnotation(OnDeleteAsignaFechaSistema.class,model.getClass());
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
	
	protected void procesarMensaje(Connection connection, Session session,  MessageProducer messageProducer, 
			ConnectionFactory connectionFactory, Queue queue, String xmlMessage){
		connection=null;
		try{
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			messageProducer = session.createProducer(queue);			
			TextMessage message = session.createTextMessage(xmlMessage);
			procesamientoExtra(message);
			messageProducer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();			
		} finally {
			if (connection != null) {
				try {                	
					messageProducer.close();
					messageProducer = null;
					queue = null;
					session.close();
					session = null;
					connection.close();
					connection = null;
					connectionFactory = null;
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	protected void procesamientoExtra(TextMessage message) throws JMSException{
		//Implementar si se requiere. EMailProducerBusiness requiere parseo antes de enviar message.
	}
	
}
