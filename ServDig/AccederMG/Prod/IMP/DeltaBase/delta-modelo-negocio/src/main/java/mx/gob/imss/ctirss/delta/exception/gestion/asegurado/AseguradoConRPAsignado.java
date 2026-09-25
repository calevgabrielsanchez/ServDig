package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback=true)
public class AseguradoConRPAsignado extends AbstractException {
	private static final long serialVersionUID = -7498433916369894460L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("El asegurado ya tiene asignado un Registro Patronal.");

	public AseguradoConRPAsignado() {
		super(situacion, codigo);
	}
}
