package mx.gob.imss.ctirss.delta.exception.documento.probatorio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PersonaSinDocumentosException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static String mensaje = new String(
			"Persona sin documentos probatorios");
	private static Integer codigo = new Integer(6789);

	public PersonaSinDocumentosException() {
		super(mensaje, codigo);
	}
}
