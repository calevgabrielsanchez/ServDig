package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.utils;

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

public class OpcionesProperties {

	private Map<String,Boolean> opciones;

	public OpcionesProperties() {
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
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext();

		Resource res = null;
		InputStream is = null;

		String location = getRutaOpciones();
		Properties properties = new Properties();
		
		try {
			res = applicationContext.getResource(location);
			is = res.getInputStream();
			properties.load(is);
			is.close();

			for (String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				if(!StringUtils.isBlank(value) && value.length() == 1) {
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
		
		return opciones;
	}

	private String getRutaOpciones() {

		return this.getValorProperties("url_archivo");
	}

	private String getValorProperties(String propiedad) {
		String propertie = null;
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = new ClassPathResource("ruta.properties").getInputStream();
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
