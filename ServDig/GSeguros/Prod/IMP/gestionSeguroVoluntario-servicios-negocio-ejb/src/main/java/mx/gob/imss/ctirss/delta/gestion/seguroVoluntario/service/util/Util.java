package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Dj Leo 28/11/2014 The Class Utils.
 */
public class Util {
    /**
     * Constructor privado
     */
    private Util() {
        
    }
    /** El local idioma. */
    public static final Locale IDIOMA_ES = new Locale("es", "MX");

    /**
     * La constante BLANCO.
     */
    public static final String BLANCO = "";

    /**
     * La constante MENSAJE_SOLO_VALIDO_IS_NULL.
     */
    public static final String MENSAJE_SOLO_VALIDO_IS_NULL = "Objecto no soportado por esta funcion"
            + " return false, solo se valido que no es null";

    /** La constante FECHA_FORMATO. */
    public static final String FECHA_FORMATO = "dd/MM/yyyy";

    /** The log. */
    private static final Log LOGGER = LogFactory.getLog(Util.class);

    /**
     * Verifica Solamente si es null en un tipo Object y en un tipo String si es
     * null o vacia.
     *
     * @param objecto el valor de: objecto
     * @return true, Si se cumple la condicion
     */
    public static boolean isEmpty(Object objecto) {
        boolean resultado = false;
        String cadenaCasteada;
        if (objecto == null) {
            return true;
        } else {
            if (objecto instanceof String) {
                cadenaCasteada = (String) objecto;
                if (BLANCO.equals(cadenaCasteada)) {
                    resultado = true;
                }
                if (cadenaCasteada.length() <= 0) {
                    resultado = true;
                }
            } else {
                LOGGER.info(objecto.getClass().getName() + " " + MENSAJE_SOLO_VALIDO_IS_NULL);
            }
        }
        return resultado;
    }

}
