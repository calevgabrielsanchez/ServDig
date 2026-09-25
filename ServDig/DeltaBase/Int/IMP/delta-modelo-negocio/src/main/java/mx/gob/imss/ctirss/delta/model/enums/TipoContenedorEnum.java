package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TipoContenedorEnum implements Serializable{

	PORTLET(1), WIDGET(2), MENU(3),WIDGET_TRAMITE(4),WIDGET_PERSONA(5), EMPTY_STATE(7), WIDGET_PERSONA_ASEGURADO(8), WIDGET_PERSONA_DERECHOHABIENTE(9);
	
	private long id;
	
	TipoContenedorEnum(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
}
