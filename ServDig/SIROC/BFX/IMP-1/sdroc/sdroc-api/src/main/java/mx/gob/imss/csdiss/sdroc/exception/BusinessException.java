package mx.gob.imss.csdiss.sdroc.exception;

import org.springframework.context.MessageSource;

/**
 * 
 * Excepcion lanzada cuando se detecta un error en la logica de negocio
 * por la cual el proceso de negocio debe ser interrumpido.
 * 
 * @author Brian Hernandez Garcia
 *
 */
public class BusinessException extends BaseException {
		 
	private static final long serialVersionUID = -1128852941580331211L;

	/**
	 * Constructor defaul. No se recomienda su uso ya que se debe
	 * intentar proveer mas informacion por medio de un mensaje.
	 * 
	 */
	public BusinessException(){
        super();
    }

	/**
	 * 
	 * @param message Mensaje literal que brinda mayor informacion sobre el error.
	 */
    public BusinessException(String message){
        super(message);
    }

    /**
     * Constructor que permite envolver una excepcion arbitraria.
     * 
     * @param msg Mensaje literal que brinda mayor informacion sobre el error.
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     */
    public BusinessException(String msg, Throwable t){
        super(msg,t);
    }

    /**
     * 
     * @param message Mensaje literal que brinda mayor informacion sobre el error.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */
    public BusinessException(String message, Object extraInfo){
        super(message, extraInfo);
    }

    /**
     * 
     * @param msg Mensaje literal que brinda mayor informacion sobre el error.
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */
    public BusinessException(String msg, Throwable t, Object extraInfo){
        super(msg,t,extraInfo);
    }

    /**
     * 
     * @param t La excepcion originalmente lanzada. Cuando esta existe es buena practica conservarla y envolverla detro de otro
     * 			tipo de excepcion que tenga mayor significado dentro de la aplicacion.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     */
    public BusinessException(Throwable t, String codigoMensaje, Object[] argumentosMensaje) {
        super(t, codigoMensaje, argumentosMensaje);
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
    public BusinessException(Throwable t, String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje) {
        super(t, codigoMensaje, mensajeDefault, argumentosMensaje);
    }

    /**
     * 
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     */
    public BusinessException(Object[] argumentosMensaje, String codigoMensaje) {
        super(codigoMensaje, argumentosMensaje);
    }

    /**
     * 
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param mensajeDefault Si el codigo del mensaje no puede resuelto se usara el mensaje literal default.
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     */
    public BusinessException(String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje) {
        super(codigoMensaje, mensajeDefault, argumentosMensaje);
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
    public BusinessException(Throwable t, String codigoMensaje, Object[] argumentosMensaje, Object extraInfo) {
        super(t, codigoMensaje, argumentosMensaje, extraInfo);
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
    public BusinessException(Throwable t, String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje, Object extraInfo) {
        super(t, codigoMensaje, mensajeDefault, argumentosMensaje, extraInfo);
    }

    /**
     * 
     * @param argumentosMensaje Los valores que seran sustituidos en los "placeholders" dentro del mensaje. Estos placeholders
     * 						son de la forma {n}. Los valores seran tomados segun el orden dentro del arreglo.
     * @param codigoMensaje Codigo de un mensaje para ser resuelto via alguna implementacion de {@link MessageSource}.
     * @param extraInfo Algun bean que contenga informacion que la aplicacion deba procesar debido al error. Por ejemplo
     * 			{@link ManejadorErroresJson} puede convertirlo a formato json para pasarlo como respuesta al cliente.
     */
    public BusinessException(Object[] argumentosMensaje, String codigoMensaje, Object extraInfo) {
        super(codigoMensaje, argumentosMensaje, extraInfo);
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
    public BusinessException(String codigoMensaje, String mensajeDefault, Object[] argumentosMensaje, Object extraInfo) {
        super(codigoMensaje, mensajeDefault, argumentosMensaje, extraInfo);
    }
}
