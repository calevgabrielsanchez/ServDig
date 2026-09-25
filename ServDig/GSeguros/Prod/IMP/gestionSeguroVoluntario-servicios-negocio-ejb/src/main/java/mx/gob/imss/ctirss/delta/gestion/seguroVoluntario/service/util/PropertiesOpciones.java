package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

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
        Boolean porProperties = true;
        InputStream is = null;

        if (porProperties) {
            // String location = getValorProperties("url_archivo");
            Properties properties = new Properties();
            try {
                // res = applicationContext.getResource(location);
                is = PropertiesOpciones.class.getResourceAsStream("/opciones.properties");
                if (is != null) {
                    LOGGER.info("Diferente de nullo  is");
                } else {
                    LOGGER.info("No Diferente de nullo  is");
                }
                properties.load(is);
                is.close();
                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    opciones.put(key, value);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        } finally {
            if (is != null) {
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
