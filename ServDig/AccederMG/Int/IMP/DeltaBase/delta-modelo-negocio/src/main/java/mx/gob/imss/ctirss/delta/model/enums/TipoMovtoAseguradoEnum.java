package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoMovtoAseguradoEnum {

	ACTUAL(0L, ""),
	ALTAS(1L, "ALTAS"),
	BAJAS(2L, "BAJAS"),
	AUTORIZACIONES_PERMANENTES(3L,"AUTORIZACIONES PERMANENTES"),
	CAMBIO_UMF(4L, "CAMBIO UMF"),
	CORRECCION_NSS(5L, "CORRECCION NSS"),
	CORRECCION_NOMBRE_DATOS_ESTADISTICOS(6L, "CORREC. NOMBRE O DATOS ESTADISTICOS"),
	MODIF_SALARIO(7L, "MODIF. SALARIO"),
	REINGRESO(8L, "REINGRESO"),
	IDENTIFIC_PENSION(10L, "IDENTIFIC. PENSION");
	
	
	private Long idTipoMovtoAsegurado;
	private String desTipoMvtoAsegurado;
	
	private TipoMovtoAseguradoEnum(Long id, String descripcion) {
		this.idTipoMovtoAsegurado = id;
		this.desTipoMvtoAsegurado = descripcion;
	}

	public Long getIdTipoMovtoAsegurado() {
		return idTipoMovtoAsegurado;
	}

	public void setIdTipoMovtoAsegurado(Long idTipoMovtoAsegurado) {
		this.idTipoMovtoAsegurado = idTipoMovtoAsegurado;
	}

	public String getDesTipoMvtoAsegurado() {
		return desTipoMvtoAsegurado;
	}

	public void setDesTipoMvtoAsegurado(String desTipoMvtoAsegurado) {
		this.desTipoMvtoAsegurado = desTipoMvtoAsegurado;
	}
	
	public static TipoMovtoAseguradoEnum getById(Long id){
		for (TipoMovtoAseguradoEnum tipo : TipoMovtoAseguradoEnum.values()) {
			if(tipo.getIdTipoMovtoAsegurado().equals(id)){
				return tipo;
			}
		}
		return null;
	}
	
}
