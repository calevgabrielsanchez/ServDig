package mx.gob.imss.ctirss.delta.cobranza.enums;

public enum TotalesCobranzaEnum {
	
	DATA_SOURCE ("dataSource"),
	TOTAL_ENF_MAT ("totalEnfermedadesMaternidad"),
	TOTAL_SAL_IV ("totalSalIV"),
	TOTAL_SAL_RT ("totalSalRt"),
	TOTAL_SAL_GUAR("totalSalGuar"),
	TOTAL_SAL_TOT("totalSalTot"),
	TOTAL_ACT("totalAct"),
	TOTAL_INT("totalInt"),
	PORCENTAJE_RECARGOS("porcentajeRecargos"),
	TOTAL_CYV("totalCyV"),
	TOTAL_SAL_RET("totalSalRet"),
	TOTAL_ACTUA("totalActua"),
	TOTAL_RECAR("totalRecar"),
	SUMA_TOTALES("sumaTotales");
	
	private String key;

	TotalesCobranzaEnum(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
