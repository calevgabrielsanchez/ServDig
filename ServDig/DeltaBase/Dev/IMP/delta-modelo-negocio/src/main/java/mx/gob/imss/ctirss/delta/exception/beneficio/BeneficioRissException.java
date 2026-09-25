package mx.gob.imss.ctirss.delta.exception.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class BeneficioRissException extends AbstractException {

	private static final long serialVersionUID = -1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"Error al procesar beneficio RISS.");

	private int code;
	private Long idSolicitud;

	public BeneficioRissException() {
		super(situacion, codigo);
	}

	public BeneficioRissException(String message) {
		super(message);
	}

	public BeneficioRissException(String message, int code) {
		super(message, code);
	}

	public Integer getCode() {
		return this.code;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
}