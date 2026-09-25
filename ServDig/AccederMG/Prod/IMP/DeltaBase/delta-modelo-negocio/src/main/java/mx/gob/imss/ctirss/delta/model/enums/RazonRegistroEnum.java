package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum RazonRegistroEnum implements Serializable{

	NORMAL(1),RECIEN_NACIDO(2),HASTA_16(3),POR_LAUDO(4),
	POR_AMPARO(5), HASTA_25(6), POR_ACUERDO(7), MAYOR_A_25(8);
	
	private long id;
	
	RazonRegistroEnum(long id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}
}
