package mx.gob.imss.ctirss.delta.global.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class MensajeProcesoTO extends AbstractModel{
	
	/**
	 * serial
	 */
	private static final long serialVersionUID = 4225173503534763719L;
	
	private MensajeTO[] mensaje;
	
	public MensajeTO[] getMensaje() {
		return mensaje;
	}
	public void setMensaje(MensajeTO[] mensajes) {
		this.mensaje = mensajes != null ? mensajes.clone() : null;
	}

}
