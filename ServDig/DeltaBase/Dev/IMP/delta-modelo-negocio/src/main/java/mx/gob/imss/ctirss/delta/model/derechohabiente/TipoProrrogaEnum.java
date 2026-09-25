package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;

public enum TipoProrrogaEnum implements Serializable{

	ESTUDIOS(1),
	ENFERMEDAD(2),
	PERMANENTE(3),
	TEMPORAL(4),
	ACUERDOS(5),
	OBSTETRICOS(6),
	LAUDO(7);
	
	private Long id;
	
	private TipoProrrogaEnum(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
