package mx.gob.imss.ctirss.delta.exception.documento.probatorio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RegistrarDocumentoProbatorioException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static String mensaje = new String("Error al guardar documento probatorio");
	private static Integer codigo = new Integer(12345);

	public RegistrarDocumentoProbatorioException() {
		super(mensaje, codigo);
	}
	
	public RegistrarDocumentoProbatorioException(Throwable e) {
		super(e.getMessage(), codigo);
	}
}
