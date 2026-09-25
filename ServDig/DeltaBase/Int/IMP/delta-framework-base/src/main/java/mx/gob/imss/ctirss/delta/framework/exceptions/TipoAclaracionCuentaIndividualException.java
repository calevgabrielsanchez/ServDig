/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SRG
 *
 */
public class TipoAclaracionCuentaIndividualException extends AbstractException
		implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG;

	static {
		LOG = LoggerFactory
				.getLogger(TipoAclaracionCuentaIndividualException.class);
	}

	private static final String mensaje = "No se econtr\u00F3 tipo de aclaraci\u00F3n para el nss: ";

	public TipoAclaracionCuentaIndividualException(String nss) {
		super(mensaje + nss);
		LOG.debug(mensaje + nss);
	}

}
