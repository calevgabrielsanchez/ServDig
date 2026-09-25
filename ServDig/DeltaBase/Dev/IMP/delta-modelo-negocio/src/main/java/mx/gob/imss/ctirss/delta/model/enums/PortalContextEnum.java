package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum PortalContextEnum implements Serializable {
	INDIVIDUO(1), EMPRESA(2), PATRONAL(3), ASEGURADO(4), DERECHOHABIENTE(5);

	private long id;

	PortalContextEnum(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
}
