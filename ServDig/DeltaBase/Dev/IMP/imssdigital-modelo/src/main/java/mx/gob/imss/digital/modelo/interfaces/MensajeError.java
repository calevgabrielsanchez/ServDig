/**
 * 
 */
package mx.gob.imss.digital.modelo.interfaces;

/**
 * Interfaz que define las propiedades para el manejo de mensajes de error
 * @author NOVUTECK1
 *
 */
public interface MensajeError {
    
    /**
     * Obtiene el mensaje de error
     * @return
     */
    String getErrorFormGeneral();

    /**
     * Fija el mensaje de error
     * @param errorFormGeneral
     */
    void setErrorFormGeneral(String errorFormGeneral);

}
