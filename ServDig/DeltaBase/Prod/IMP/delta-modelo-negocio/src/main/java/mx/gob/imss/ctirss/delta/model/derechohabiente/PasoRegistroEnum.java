package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;

public enum PasoRegistroEnum implements Serializable{

	CAPTURA_DATOS_PERSONALES(1L),
	CAPTURA_DOMICILIO(2L),
	CAPTURA_UMF(3L);
	
	Long id;
	
	private PasoRegistroEnum(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
}
