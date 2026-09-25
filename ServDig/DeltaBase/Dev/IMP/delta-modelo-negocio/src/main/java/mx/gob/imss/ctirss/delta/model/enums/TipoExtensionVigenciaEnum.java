package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoExtensionVigenciaEnum {
	ACUERDO(1),
	VIGENCIA_TEMPORAL(2),
	CONSTANCIA_ESTUDIO(3),
	OBSTETRICO(4),
	CERTIFICADO_SITUACION_CRITICA(5),
	DICTAMEN_BENEFICIARIO_INCAPACITADO(6);
	
	private long id;
	
	TipoExtensionVigenciaEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}
