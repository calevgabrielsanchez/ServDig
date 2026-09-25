package mx.gob.imss.cit.cda.service.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class PropertiesOpciones.
 */
public class PropertiesEndpoints {

    /** The opciones. */
    private Map<String, String> opciones;
    
    private static final String ERROR = "Ocurrio un error {}";

    private static final Log LOGGER = LogFactory.getLog(PropertiesEndpoints.class);

    /**
     * Instantiates a new properties opciones.
     */
    public PropertiesEndpoints() {
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
            is = PropertiesEndpoints.class.getResourceAsStream("/endpoints.properties");
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
            LOGGER.error(ERROR, e);
            return null;
        } catch (Exception e) {
            LOGGER.error(ERROR, e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("Ocurrio un error al cerrar el Stream{}", e);
                }
            }
        }

        return opciones;
    }

}

