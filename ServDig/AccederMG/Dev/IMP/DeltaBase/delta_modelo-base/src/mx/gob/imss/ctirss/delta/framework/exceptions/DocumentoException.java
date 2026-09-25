/*
 * Exception creada el 25/04/204, por Juan Osorio Alvarez,
 * Esta clase se encargara d elas excepciones ocurridas en el servicio
 * mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.TramiteDocumentoService
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class DocumentoException extends AbstractException {

	private static final long serialVersionUID = 3107834731827178099L;

	public DocumentoException(String mensaje) {
		super(mensaje);
	}
}
