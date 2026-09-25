package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum RazonResultadoEnum implements Serializable{

	DOCUMENTOS_INCOMPLETOS(1),DOCUMENTOS_APOCRIFOS(2),IMPROCEDENCIA(3),CONVIVENCI_DEPENDENCIA_NO_COMPROBADA(4),
	SOLICITUD_CANCELADA(5),POR_LAUDO(6),POR_ACUERDO(7),NORMAL(8),POR_AMPARO(9);
	
	private long id;
	
	RazonResultadoEnum(long id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}
}
