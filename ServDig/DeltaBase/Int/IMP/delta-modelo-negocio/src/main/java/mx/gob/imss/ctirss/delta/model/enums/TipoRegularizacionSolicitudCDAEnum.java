package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoRegularizacionSolicitudCDAEnum {

	CORRECCION_CURP(1L,161L, "CORRECCI\u00d3N CURP"),
	CORRECION_DATOS_BASICOS(2L,162L, "CORRECCI\u00d3N DATOS B\u00C1SICOS"),
	HOMONIMIA(3L,164L,"HOMONIMIA"),
	DUPLICIDAD(4L,60L,"DUPLICIDAD"),
	INVASION(5L,166L,"INVASI\u00d3N"),
	DESVINCULACION(6L,163L,"DESVINCULACI\u00d3N"),
	CUENTA_ILOGICA(7L,165L,"CUENTA IL\u00d3GICA"),
	BLANQUEAMIENTO_CURP(8L,166L,"BLANQUEAMIENTO DE CURP");

	private Long id;
	private Long idTipoTramite;
	private String descripcion;

	private TipoRegularizacionSolicitudCDAEnum(Long id,Long idTipoTramite, String descripcion) {
		this.id = id;
		this.idTipoTramite =idTipoTramite;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return this.id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	
	
	public Long getIdTipoTramite() {
		return idTipoTramite;
	}

	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static TipoRegularizacionSolicitudCDAEnum fromId(long id) {
		TipoRegularizacionSolicitudCDAEnum[] regularizaciones = values();
        for(TipoRegularizacionSolicitudCDAEnum regularizacion : regularizaciones) {
            if (regularizacion.getId() == id) {
                return regularizacion;
            }
        }
        return null;
    }
	
	public static TipoRegularizacionSolicitudCDAEnum fromDesc(String desc) {
		TipoRegularizacionSolicitudCDAEnum[] regularizaciones = values();
        for(TipoRegularizacionSolicitudCDAEnum regularizacion : regularizaciones) {
            if (regularizacion.getDescripcion().equals(desc)) {
                return regularizacion;
            }
        }
        return null;
    }
}
