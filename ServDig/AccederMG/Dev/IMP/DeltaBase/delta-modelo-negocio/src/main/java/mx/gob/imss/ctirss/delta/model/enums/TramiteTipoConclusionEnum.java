package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TramiteTipoConclusionEnum implements Serializable{
	EN_LINEA(1), VENTANILLA(2), BACKOFFICE(3);
	
	private Integer codigo;
	
	TramiteTipoConclusionEnum(Integer tipoConclusion){
		codigo = tipoConclusion;
	}

	public Integer getCodigo() {
		return codigo;
	}	
}
