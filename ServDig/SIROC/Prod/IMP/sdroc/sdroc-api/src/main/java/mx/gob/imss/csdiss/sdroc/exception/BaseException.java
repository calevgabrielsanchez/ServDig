package mx.gob.imss.csdiss.sdroc.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Excepcion base para el manejo de errores dentro de la aplicacion.
 * 
 * Siguiendo las recomendaciones de diseno de la comunidad spring-java se usa 
 * {@link RuntimeException} para no obligar al desarrollador a siempre cachar las
 * excepciones.
 * 
 * Los mensajes colocados dentro de las excepciones de este tipo pueden ser codigos que
 * posteriormente sean resueltos por medio de algun {@link MessageSource}
 * 
 * Ya que esta clase implementa la interfaz {@link MessageSourceResolvable} cualquier instancia
 * de una excepcion que herede de esta clase puede ser enviada a cualquier estrategia de resolucion
 * de mensajes que implemente {@link MessageSource} por ejemplo {@link ResourceBundleMessageSource}.
 * 
 * @author Brian Hernandez Garcia
 *  
 */
public abstract class BaseException
        extends RuntimeException
        implements MessageSourceResolvable {

	private static final long serialVersionUID = 7217445484129502645L;

	private String[] codes;

    private Object[] arguments;

    private String defaultMessage;

    private boolean resolvable;

    private boolean mensajeResuelto;

    private Object extraInfo;

    public BaseException() {
        super();
    }

    private void setComunes(String codigoMensaje, Object[] argumentosMensaje) {
        this.codes = new String[1];
        this.codes[0] = codigoMensaje;
        this.arguments = argumentosMensaje;
        this.resolvable = true;
    }

	/**
	 * 
	 * @param message Mensaje literal que brinda mayor informacion sobre el error.
	 */    
    public BaseException(String message) {
        super(message);
    }

    /**
     * Constructor que permite envolver una excepcion arbitraria.
     * 
     * @param msg Mensaje literal que brinda mayor informacion sobre el error.
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena pratica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     */    
    public BaseException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * 
     * @param message Mensaje literal que brinda mayor informacion sobre el error.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */    
    public BaseException(String message, Object extraInfo) {
        super(message);
        this.extraInfo = extraInfo;
    }

    /**
     * 
     * @param msg Mensaje literal que brinda mayor informacion sobre el error.
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */    
    public BaseException(String msg, Throwable t, Object extraInfo) {
        super(msg, t);
        this.extraInfo = extraInfo;
    }

    /**
     * 
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     */    
    public BaseException(Throwable t, String codigoMensaje, Object[] argumentosMensaje) {
        super(t);
        this.setComunes(codigoMensaje, argumentosMensaje);
    }

    /**
     * 
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param mensajeDefault Si el codigo del mensaje no puede resuelto se usara el mensaje literal default.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     */    
    public BaseException(Throwable t, String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje) {
        super(mensajeDefault, t);
        this.defaultMessage = mensajeDefault;
        this.setComunes(codigoMensaje, argumentosMensaje);
    }

    /**
     * 
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     */    
    public BaseException(String codigoMensaje, Object[] argumentosMensaje) {
        super();
        this.setComunes(codigoMensaje, argumentosMensaje);
    }

    /**
     * 
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param mensajeDefault Si el codigo del mensaje no puede resuelto se usara el mensaje literal default.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     */    
    public BaseException(String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje) {
        super(mensajeDefault);
        this.defaultMessage = mensajeDefault;
        this.setComunes(codigoMensaje, argumentosMensaje);
    }

    /**
     * 
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */    
    public BaseException(Throwable t, String codigoMensaje, Object[] argumentosMensaje, Object extraInfo) {
        this(t, codigoMensaje, argumentosMensaje);
        this.extraInfo = extraInfo;
    }

    /**
     * 
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param mensajeDefault Si el codigo del mensaje no puede resuelto se usara el mensaje literal default.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */    
    public BaseException(Throwable t, String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje, Object extraInfo) {
        this(t, codigoMensaje, mensajeDefault, argumentosMensaje);
        this.extraInfo = extraInfo;
    }

    /**
     * 
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */    
    public BaseException(String codigoMensaje, Object[] argumentosMensaje, Object extraInfo) {
        this(codigoMensaje, argumentosMensaje);
        this.extraInfo = extraInfo;
    }

    /**
     * 
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param mensajeDefault Si el codigo del mensaje no puede resuelto se usara el mensaje literal default.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */    
    public BaseException(String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje, Object extraInfo) {
        this(codigoMensaje, mensajeDefault, argumentosMensaje);
        this.extraInfo = extraInfo;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getCodes() {
        return this.codes;
    }

    /**
     * {@inheritDoc}
     */    
    public Object[] getArguments() {
        return this.arguments;
    }

    /**
     * {@inheritDoc}
     */    
    public String getDefaultMessage() {
        return this.getMessage();
    }

    public Object getExtraInfo() {
        return this.extraInfo;
    }

    /**
     * {@inheritDoc}
     */    
    public boolean isResolvable() {
        return this.resolvable;
    }

    /**
     * Indica que el mensaje fue resuelto por un set.
     *
     * @return mensajeResuelto
     */
    public boolean isMensajeResuelto() {
        return mensajeResuelto;
    }

    /**
     * @param mensajeResuelto the mensajeResuelto por un  set
     */
    public void setMensajeResuelto(boolean mensajeResuelto) {
        this.mensajeResuelto = mensajeResuelto;
    }
}

