package mx.gob.imss.ctirss.delta.portal.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertiesOpciones {
	
	private Map<String,Boolean> opciones;
	
	public PropertiesOpciones() {
		this.opciones = getOpcionesMenu();
	}
	
	public Map<String,Boolean> getOpciones() {
		return opciones;
	}
	
	public void reloadProperties() {
		this.opciones = this.getOpcionesMenu();
	}
	
	private Map<String,Boolean> getOpcionesMenu() {
		
		Map<String,Boolean> opciones = new HashMap<String, Boolean>();
		Boolean porProperties = new Integer(getValorProperties("busqueda_properties")).equals(1);
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
		
		
		Resource res = null;
		InputStream is = null;
		
		
		if(porProperties) {
			String location = getValorProperties("url_archivo");
			Properties properties = new Properties();
			
			
			
			
			try {
				res = applicationContext.getResource(location);
				is = res.getInputStream();//new ClassPathResource("opciones.properties").getInputStream();
				properties.load(is);
				is.close();
				
				for (String key : properties.stringPropertyNames()) {
				      String value = properties.getProperty(key);
				      if(!StringUtils.isEmpty(value) && value.length() == 1) {
				    	  opciones.put(key, Integer.valueOf(value).equals(1));
				      }
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}finally{
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			 
		} else {
			
		}
		
		return opciones;
	}
	
	private String getValorProperties(String propiedad) {
		String propertie = null;
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = new ClassPathResource("opciones.properties").getInputStream();
			properties.load(is);
			is.close();
			
			propertie = properties.getProperty(propiedad);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return propertie;
	}
}
