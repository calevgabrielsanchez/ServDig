package mx.gob.imss.cit.cda.service.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * The Class PropertiesOpciones.
 */
public class PropertiesOpciones {

    /** The opciones. */
    private Map<String, String> opciones;
    
    private static final String ERROR = "Ocurrio un error {}";

    private static final Log LOGGER = LogFactory.getLog(PropertiesOpciones.class);

    /**
     * Instantiates a new properties opciones.
     */
    public PropertiesOpciones() {
        this.opciones = getOpcionesMenu();
    }

    /**
     * Gets the opciones.
     * 
     * @return the opciones
     */
    public Map<String, String> getOpciones() {
        return opciones;
    }

    /**
     * Reload properties.
     */
    public void reloadProperties() {
        this.opciones = this.getOpcionesMenu();
    }

    /**
     * Gets the opciones menu.
     * 
     * @return the opciones menu
     */
    private Map<String, String> getOpcionesMenu() {

        Map<String, String> opciones = new HashMap<String, String>();
        InputStream is = null;
            Properties properties = new Properties();
            try {
                is = PropertiesOpciones.class.getResourceAsStream("/boveda.properties");
                if (is != null) {
					properties.load(is);
					is.close();
					for (String key : properties.stringPropertyNames()) {
						String value = properties.getProperty(key);
						opciones.put(key, value);
					}
                    LOGGER.info("Diferente de nullo  is");
                } else {
                    LOGGER.info("No Diferente de nullo  is");
                }
            } catch (IOException e) {
            	LOGGER.error(ERROR,e);
                return null;
            } catch (Exception e) {
            	LOGGER.error(ERROR,e);
                return null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    	LOGGER.error("Ocurrio un error al cerrar el Stream{}",e);
                    }
                }
            }

        
        return opciones;
    }

    /**
     * Gets the valor properties.
     * 
     * @param propiedad
     *            the propiedad
     * @return the valor properties
     */
    @SuppressWarnings("unused")
    private String getValorProperties(String propiedad) {
        String propertie = null;
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = new ClassPathResource("boveda.properties").getInputStream();
            properties.load(is);
            is.close();

            propertie = properties.getProperty(propiedad);
        } catch (IOException e) {
            LOGGER.error(ERROR,e);
            return "";
        } catch (Exception e) {
        	LOGGER.error(ERROR,e);
            return "";
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                	LOGGER.error("Ocurrio un error al cerrar el Stream{}",e);
                }
            }
        }
        return propertie;
    }
}

